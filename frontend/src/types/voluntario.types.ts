import type { Vinculo } from './academico.types';

export interface Voluntario {
  id: number;
  primeiroNome: string;
  ultimoNome: string;
  emailPessoal: string;
  tipoUsuario: string;
  ativo: boolean;
}

export interface VoluntarioPerfil {
  id: number;
  primeiroNome: string;
  ultimoNome: string;
  emailPessoal: string;
  tipoUsuario: string;
  // Membresia IEEE
  numeroMembresia?: string;
  emailIeee?: string;
  tipoMembresia?: string;
  // Vínculos Acadêmicos e Unidades
  vinculos?: Vinculo[];
  capitulos?: string[];
  // Mandatos
  historicoMandatos?: Mandato[];
}

export interface Mandato {
  id: number;
  cargoId: number;
  nomeCargo: string;
  dataInicio: string;
  dataFim: string;
  ativo: boolean;
}