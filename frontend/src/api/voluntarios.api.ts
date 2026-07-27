import { api } from './client';
import type { PaginatedResponse } from '@/types/pagination.types';
import type { Voluntario, VoluntarioPerfil } from '@/types/voluntario.types';

export const voluntarioService = {
  listarTodos: async (): Promise<Voluntario[]> => {
    const response = await api.get<PaginatedResponse<Voluntario>>('/api/voluntarios');
    return response.data.content;
  },
  obterMeuPerfil: async (): Promise<VoluntarioPerfil> => {
    const response = await api.get('/api/voluntarios/me/perfil');
    return response.data;
  },
  obterPerfilPorId: async (id: number): Promise<VoluntarioPerfil> => {
    const response = await api.get(`/api/voluntarios/${id}/perfil`);
    return response.data;
  },
  cadastrar: async (dados: any) => {
    const response = await api.post('/api/voluntarios/cadastro', dados);
    return response.data;
  },
  promoverAMembro: async (id: number, dados: any) => {
    const response = await api.post(`/api/voluntarios/${id}/promover-membro`, dados);
    return response.data;
  },
  promoverADiretor: async (id: number, dados: any) => {
    const response = await api.post(`/api/voluntarios/${id}/promover-diretor`, dados);
    return response.data;
  },
  removerMembro: async (id: number) => {
    const response = await api.delete(`/api/voluntarios/${id}/remover-membro`);
    return response.data;
  },
  removerDiretor: async (id: number) => {
    const response = await api.delete(`/api/voluntarios/${id}/remover-diretor`);
    return response.data;
  },
};