import React, { createContext, useContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';
import { api, renovarAccessToken } from '@/api/client';
import { setAccessToken } from '@/api/tokenStore';

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
    isLoading: boolean;
    login: (token: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

function extrairDadosDoToken(token: string): UserData | null {
    try {
        const decoded = jwtDecode<JwtPayloadCustom>(token);

        if (decoded.exp && decoded.exp * 1000 < Date.now()) {
            return null;
        }

        return {
            email: decoded.sub,
            role: decoded.role || 'USER',
            nomeExibicao: decoded.nome || 'Voluntário',
        };
    } catch {
        return null;
    }
}

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
    children,
}) => {
    const [user, setUser] = useState<UserData | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        async function restaurarSessao() {
            try {
                const token = await renovarAccessToken();
                setUser(extrairDadosDoToken(token));
            } catch {
                setUser(null);
            } finally {
                setIsLoading(false);
            }
        }

        restaurarSessao();
    }, []);

    const login = (token: string) => {
        setAccessToken(token);
        setUser(extrairDadosDoToken(token));
    };

    const logout = async () => {
        try {
            await api.post('/api/auth/logout');
        } catch {
            // Ignora erros no logout (ex: token já expirado)
        } finally {
            setAccessToken(null);
            setUser(null);
            window.location.href = '/';
        }
    };

    return (
        <AuthContext.Provider
            value={{ user, isAuthenticated: !!user, isLoading, login, logout }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
