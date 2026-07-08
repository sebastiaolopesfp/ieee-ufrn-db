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