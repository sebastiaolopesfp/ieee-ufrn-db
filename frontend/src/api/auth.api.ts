import { api } from './client';

interface LoginPayload {
  emailPessoal: string;
  senha: string;
  manterConectado?: boolean;
}

interface LoginResponse {
  token: string;
  tipo: string;
}

export const authService = {
  login: async (credenciais: LoginPayload): Promise<LoginResponse> => {
    const response = await api.post<LoginResponse>('/api/auth/login', credenciais);
    return response.data;
  },
};