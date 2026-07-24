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
  numeroMembresia?: string;
  emailIeee?: string;
  tipoMembresia?: string;
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