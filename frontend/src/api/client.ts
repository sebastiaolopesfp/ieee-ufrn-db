import axios, { type InternalAxiosRequestConfig } from 'axios';
import { getAccessToken, setAccessToken } from './tokenStore';

const baseURL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const api = axios.create({
  baseURL,
  withCredentials: true,
});

api.interceptors.request.use((config) => {
  const token = getAccessToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

interface RequisicaoComRetry extends InternalAxiosRequestConfig {
  _retry?: boolean;
}

let refreshEmAndamento: Promise<string> | null = null;

export async function renovarAccessToken(): Promise<string> {
  const response = await axios.post<{ token: string; tipo: string }>(
    `${baseURL}/api/auth/refresh`,
    {},
    { withCredentials: true }
  );
  const novoToken = response.data.token;
  setAccessToken(novoToken);
  return novoToken;
}

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const requisicaoOriginal = error.config as RequisicaoComRetry;

    const ehErroDeAutenticacao = error.response?.status === 401;
    const ehChamadaDeRefresh = requisicaoOriginal?.url?.includes('/api/auth/refresh');
    const jaTentouRenovar = requisicaoOriginal?._retry;

    if (!ehErroDeAutenticacao || ehChamadaDeRefresh || jaTentouRenovar) {
      return Promise.reject(error);
    }

    requisicaoOriginal._retry = true;

    try {
      if (!refreshEmAndamento) {
        refreshEmAndamento = renovarAccessToken().finally(() => {
          refreshEmAndamento = null;
        });
      }

      const novoToken = await refreshEmAndamento;
      requisicaoOriginal.headers.Authorization = `Bearer ${novoToken}`;
      return api(requisicaoOriginal);
    } catch (erroDeRefresh) {
      setAccessToken(null);
      window.location.href = '/login';
      return Promise.reject(erroDeRefresh);
    }
  }
);