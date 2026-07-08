import axios from 'axios';

export interface Evento {
  id: number;
  titulo: string;
  descricao: string;
  vtoolsId?: string;
  dataInicio: string;
  dataFim: string;
  categoria: string;
  subcategoria?: string;
  qtdMembros: number;
  qtdNaoMembros: number;
  orcamentoEstimado: number;
  statusSincronizacao: string;
  locationType: string;
  published: boolean;
  reported: boolean;
}

export interface Sessao {
  id: number;
  eventoId: number;
  tituloAtividade: string;
  data: string;
  horaInicio: string;
  horaFim: string;
  local: string;
  voluntariosPresentesIds: number[];
}

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
  }
};

export const eventoService = {
  listarTodos: async (): Promise<Evento[]> => {
    const response = await api.get('/api/eventos');
    return response.data;
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
  }
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
    const response = await api.post(`/api/sessoes/${sessaoId}/presenca`, voluntarioIds);
    return response.data;
  }
};

export const capituloService = {
  listarTodos: async () => {
    const response = await api.get('/api/capitulos');
    return response.data;
  },
  criar: async (dados: { unidadeCodigo: string; nome: string; email: string; anoCriacao: number; ramoCodigo: string }) => {
    const response = await api.post('/api/capitulos', dados);
    return response.data;
  },
  deletar: async (id: string) => {
    const response = await api.delete(`/api/capitulos/${id}`);
    return response.data;
  }
};

export const ramoEstudantilService = {
  listarTodos: async () => {
    const response = await api.get('/api/ramos-estudantis');
    return response.data;
  }
};

export const grupoAfinidadeService = {
  listarTodos: async () => {
    const response = await api.get('/api/grupos-afinidade');
    return response.data;
  }
};

export const cargoService = {
  listarTodos: async () => {
    const response = await api.get('/api/cargos');
    return response.data;
  },
  criar: async (dados: { nome: string; descricao: string }) => {
    const response = await api.post('/api/cargos', dados);
    return response.data;
  },
  deletar: async (id: number) => {
    const response = await api.delete(`/api/cargos/${id}`);
    return response.data;
  }
};

export const unidadeService = {
  listarTodas: async () => {
    const response = await api.get('/api/capitulos'); 
    return response.data;
  }
};

export const instituicaoService = {
  listarTodas: async () => {
    const response = await api.get('/api/instituicoes');
    return response.data;
  },
  criar: async (dados: { nome: string; sigla: string }) => {
    const response = await api.post('/api/instituicoes', dados);
    return response.data;
  },
  listarCursosPorInstituicao: async (id: number) => {
    const response = await api.get(`/api/instituicoes/${id}/cursos`);
    return response.data;
  }
};

export const cursoService = {
  listarTodos: async () => {
    const response = await api.get('/api/cursos');
    return response.data;
  },
  criar: async (dados: { nome: string; instituicaoId: number }) => {
    const response = await api.post('/api/cursos', dados);
    return response.data;
  }
};

export const vinculoAcademicoService = {
  obterPorVoluntario: async (voluntarioId: number) => {
    const response = await api.get(`/api/vinculos/${voluntarioId}`);
    return response.data;
  },
  salvar: async (dados: { voluntarioId?: number; cursoId: number; matricula: string; emailInstitucional: string }) => {
    const response = await api.post('/api/vinculos', dados);
    return response.data;
  }
};