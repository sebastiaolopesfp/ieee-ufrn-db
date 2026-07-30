import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

// Extrai iniciais para uso em Avatar
export function obterIniciais(primeiroNome?: string, ultimoNome?: string): string {
  const i1 = primeiroNome?.[0] ?? '';
  const i2 = ultimoNome?.[0] ?? '';
  return `${i1}${i2}`.toUpperCase() || '?';
}