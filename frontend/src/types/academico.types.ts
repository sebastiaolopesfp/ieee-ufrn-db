export interface Curso {
  id: number;
  nome: string;
}

export interface Instituicao {
  id: number;
  nome: string;
  sigla: string;
  cursos: Curso[];
}

export interface Vinculo {
  id: number;
  voluntarioId: number;
  voluntarioNomeCompleto: string;
  instituicaoNome: string;
  cursoNome: string;
  numMatricula: string;
  emailAcademico: string;
  anoIngresso: number;
  statusAcademico: string;
}