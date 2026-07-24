import { api } from './client';
import type { PaginatedResponse } from '@/types/pagination.types';
import type { Evento, Sessao } from '@/types/evento.types';

export const eventoService = {
  listarTodos: async (): Promise<Evento[]> => {
    const response = await api.get<PaginatedResponse<Evento>>('/api/eventos');
    return response.data.content;
  },
  buscarPorId: async (id: number): Promise<Evento> => {
    const response = await api.get(`/api/eventos/${id}`);
    return response.data;
  },
  criarLocal: async (dados: any): Promise<Evento> => {
    const response = await api.post('/api/eventos/local', dados);
    return response.data;
  },
  importarVTools: async (vtoolsId: string, unidadeCodigo: string): Promise<Evento> => {
    const response = await api.post(`/api/eventos/vtools?vtoolsId=${vtoolsId}&unidadeCodigo=${unidadeCodigo}`);
    return response.data;
  },
  atualizarLocal: async (id: number, dados: any): Promise<Evento> => {
    const response = await api.put(`/api/eventos/local/${id}`, dados);
    return response.data;
  },
  deletar: async (id: number): Promise<void> => {
    await api.delete(`/api/eventos/${id}`);
  },
  listarCategorias: async (): Promise<Record<string, string>> => {
    const response = await api.get('/api/eventos/categorias');
    return response.data;
  },
  listarSubcategorias: async (): Promise<Record<string, string>> => {
    const response = await api.get('/api/eventos/subcategorias');
    return response.data;
  },
};

export const sessaoService = {
  listarPorEvento: async (eventoId: number): Promise<Sessao[]> => {
    const response = await api.get(`/api/sessoes/evento/${eventoId}`);
    return response.data;
  },
  criar: async (dados: any): Promise<Sessao> => {
    const response = await api.post('/api/sessoes', dados);
    return response.data;
  },
  registrarPresenca: async (sessaoId: number, voluntarioIds: number[]): Promise<Sessao> => {
    const response = await api.post(`/api/sessoes/${sessaoId}/presenca`, { voluntarioIds });
    return response.data;
  },
};