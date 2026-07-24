import { api } from './client';
import type { PaginatedResponse } from '@/types/pagination.types';
import type { Capitulo, RamoEstudantil, GrupoDeAfinidade } from '@/types/organizacional.types';

export const capituloService = {
  listarTodos: async (): Promise<Capitulo[]> => {
    const response = await api.get<PaginatedResponse<Capitulo>>('/api/capitulos');
    return response.data.content;
  },
  criar: async (dados: { unidadeCodigo: string; nome: string; email: string; anoCriacao: number; ramoCodigo: string }) => {
    const response = await api.post('/api/capitulos', dados);
    return response.data;
  },
  deletar: async (id: string) => {
    const response = await api.delete(`/api/capitulos/${id}`);
    return response.data;
  },
};

export const ramoEstudantilService = {
  listarTodos: async (): Promise<RamoEstudantil[]> => {
    const response = await api.get<PaginatedResponse<RamoEstudantil>>('/api/ramos-estudantis');
    return response.data.content;
  },
};

export const grupoAfinidadeService = {
  listarTodos: async (): Promise<GrupoDeAfinidade[]> => {
    const response = await api.get<PaginatedResponse<GrupoDeAfinidade>>('/api/grupos-afinidade');
    return response.data.content;
  },
};

export const unidadeService = {
  listarTodas: async (): Promise<Capitulo[]> => {
    const response = await api.get<PaginatedResponse<Capitulo>>('/api/capitulos');
    return response.data.content;
  },
};