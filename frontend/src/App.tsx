import { type JSX } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import { Home } from './pages/Home';
import { Login } from './pages/Login';
import { AuthProvider, useAuth } from './contexts/AuthContext';

const RotaPrivada = ({ children }: { children: JSX.Element }) => {
    const { isAuthenticated } = useAuth();
    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />

                    {/* Redirecionamento para rotas desconhecidas */}
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}
