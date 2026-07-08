import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080', 
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  login: async (credenciais: { emailPessoal: string; senha: string }) => {
    const response = await api.post('/api/auth/login', credenciais);
    return response.data; 
  }
};

export const voluntarioService = {
  listarTodos: async () => {
    const response = await api.get('/api/voluntarios');
    return response.data;
  },
  obterPerfil: async (id: number) => {
    const response = await api.get(`/api/voluntarios/${id}/perfil`);
    return response.data;
  }
};

export const eventoService = {
  listarTodos: async () => {
    const response = await api.get('/api/eventos');
    return response.data;
  },
  buscarPorId: async (id: number) => {
    const response = await api.get(`/api/eventos/${id}`);
    return response.data;
  }
};