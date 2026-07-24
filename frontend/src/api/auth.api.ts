import { api } from './client';

export const authService = {
  login: async (credenciais: { emailPessoal: string; senha: string }) => {
    const response = await api.post('/api/auth/login', credenciais);
    return response.data;
  },
};