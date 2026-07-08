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
  // 1. Cadastrar novo voluntário (Rota Pública no backend)
  cadastrar: async (dados: any) => {
    const response = await api.post('/api/voluntarios/cadastro', dados);
    return response.data;
  },
  // 2. Promover Voluntário a Membro IEEE
  promoverAMembro: async (id: number, dados: any) => {
    const response = await api.post(`/api/voluntarios/${id}/promover-membro`, dados);
    return response.data;
  },
  // 3. Nomear Membro para a Diretoria
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

export const cargoService = {
  // 2. CORREÇÃO: A rota correta do CargoController é /api/cargos
  listarTodos: async () => {
    const response = await api.get('/api/cargos');
    return response.data;
  }
};

export const unidadeService = {
  listarTodas: async () => {
    // Altere a rota '/api/capitulos' se o seu Controller mapear para outro nome (ex: /api/unidades)
    const response = await api.get('/api/capitulos'); 
    return response.data;
  }
};

// --- MÓDULO DE EVENTOS E SESSÕES ---
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
    // Passando via Query Params
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