import React, { createContext, useContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';

interface UserData {
    email: string;
    role: string;
    nomeExibicao: string;
}

interface JwtPayloadCustom {
    sub: string;
    role?: string;
    nome?: string;
    exp?: number;
}

interface AuthContextType {
    user: UserData | null;
    isAuthenticated: boolean;
    login: (token: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const [user, setUser] = useState<UserData | null>(null);

    const extrairDadosDoToken = (token: string): UserData | null => {
        try {
            const decoded = jwtDecode<JwtPayloadCustom>(token);

            // Verifica se o token expirou
            if (decoded.exp && decoded.exp * 1000 < Date.now()) {
                localStorage.removeItem('token');
                return null;
            }

            const email = decoded.sub;
            // Usa o primeiro nome do JWT ou fallback para o que antecede o @ no email caso não venha a claim
            const nomeFormatado =
                decoded.nome || email.split('@')[0].split('.')[0];
            const nomeCapitalizado =
                nomeFormatado.charAt(0).toUpperCase() + nomeFormatado.slice(1);

            return {
                email: email,
                role: decoded.role || 'USER',
                nomeExibicao: nomeCapitalizado,
            };
        } catch {
            localStorage.removeItem('token');
            return null;
        }
    };

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            const dados = extrairDadosDoToken(token);
            setUser(dados);
        }
    }, []);

    const login = (token: string) => {
        localStorage.setItem('token', token);
        const dados = extrairDadosDoToken(token);
        setUser(dados);
    };

    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        window.location.href = '/';
    };

    return (
        <AuthContext.Provider
            value={{ user, isAuthenticated: !!user, login, logout }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
