import { api } from './client';
import type { PaginatedResponse } from '@/types/pagination.types';
import type { Cargo } from '@/types/gestao.types';

export const cargoService = {
  listarTodos: async (): Promise<Cargo[]> => {
    const response = await api.get<PaginatedResponse<Cargo>>('/api/cargos');
    return response.data.content;
  },
  criar: async (dados: { nome: string; descricao: string }) => {
    const response = await api.post('/api/cargos', dados);
    return response.data;
  },
  deletar: async (id: number) => {
    const response = await api.delete(`/api/cargos/${id}`);
    return response.data;
  },
};