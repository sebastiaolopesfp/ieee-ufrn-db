import { api } from './client';
import type { PaginatedResponse } from '@/types/pagination.types';
import type { Instituicao, Curso, Vinculo } from '@/types/academico.types';

export const instituicaoService = {
  listarTodas: async (): Promise<Instituicao[]> => {
    const response = await api.get<PaginatedResponse<Instituicao>>('/api/instituicoes');
    return response.data.content;
  },
  criar: async (dados: { nome: string; sigla: string }) => {
    const response = await api.post('/api/instituicoes', dados);
    return response.data;
  },
  listarCursosPorInstituicao: async (id: number): Promise<Curso[]> => {
    // ATENÇÃO: /api/instituicoes/{id}/cursos não existe hoje no
    // InstituicaoController — mismatch pré-existente, não introduzido
    // agora. Mantive o comportamento idêntico; registrar para resolver
    // quando essa tela for reconstruída.
    const response = await api.get(`/api/instituicoes/${id}/cursos`);
    return response.data;
  },
};

export const cursoService = {
  listarTodos: async (): Promise<Curso[]> => {
    const response = await api.get<PaginatedResponse<Curso>>('/api/cursos');
    return response.data.content;
  },
  criar: async (dados: { nome: string; instituicaoId: number }) => {
    const response = await api.post('/api/cursos', dados);
    return response.data;
  },
};

export const vinculoAcademicoService = {
  obterPorVoluntario: async (voluntarioId: number): Promise<Vinculo> => {
    const response = await api.get(`/api/vinculos/${voluntarioId}`);
    return response.data;
  },
  salvar: async (dados: { voluntarioId?: number; cursoId: number; matricula: string; emailInstitucional: string }) => {
    const response = await api.post('/api/vinculos', dados);
    return response.data;
  },
};