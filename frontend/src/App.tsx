import { type JSX } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { Perfil } from './pages/Perfil';
import { AuthProvider, useAuth } from './contexts/AuthContext';

const RotaPrivada = ({ children }: { children: JSX.Element }) => {
    const { isAuthenticated, isLoading } = useAuth();

    if (isLoading) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <div className="w-8 h-8 border-4 border-brand-primary/30 border-t-brand-primary rounded-full animate-spin" />
            </div>
        );
    }

    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />

                    <Route
                        path="/perfil"
                        element={
                            <RotaPrivada>
                                <Perfil />
                            </RotaPrivada>
                        }
                    />

                    <Route
                        path="/voluntarios/:id"
                        element={
                            <RotaPrivada>
                                <Perfil />
                            </RotaPrivada>
                        }
                    />

                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}
