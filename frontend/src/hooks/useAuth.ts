import { useMemo } from 'react';

interface TokenPayload {
  sub: string;
  role: string;
  exp: number;
}

export function useAuth() {
  return useMemo(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      return { isAuthenticated: false, role: null, token: null };
    }

    try {
      const payload: TokenPayload = JSON.parse(atob(token.split('.')[1]));
      return { isAuthenticated: true, role: payload.role, token };
    } catch {
      return { isAuthenticated: false, role: null, token: null };
    }
  }, []);
}