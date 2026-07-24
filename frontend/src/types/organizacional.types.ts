export interface Capitulo {
  unidadeCodigo: string;
  nome: string;
  email: string;
  anoCriacao: number;
  ramoCodigo: string;
  nomeRamo?: string;
}

export interface RamoEstudantil {
  unidadeCodigo: string;
  nome: string;
  email: string;
  anoCriacao: number;
}

export interface GrupoDeAfinidade {
  unidadeCodigo: string;
  nome: string;
  email: string;
  anoCriacao: number;
  ramoCodigo: string;
  nomeRamo?: string;
}