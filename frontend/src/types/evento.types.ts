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