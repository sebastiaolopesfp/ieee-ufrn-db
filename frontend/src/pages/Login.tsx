// src/pages/Login.tsx
import { useState } from 'react';
import { authService } from '@/api/auth.api';

export function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [loading, setLoading] = useState(false);

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setErro('');
        setLoading(true);

        try {
            const data = await authService.login({
                emailPessoal: email,
                senha: senha,
            });
            localStorage.setItem('token', data.token);
            window.location.href = '/dashboard';
        } catch (err: any) {
            console.error(err);
            setErro('Credenciais inválidas. Verifique seu e-mail e senha.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100 font-sans p-6">
            {/* Card do Formulário */}
            <div className="w-full max-w-md p-8 rounded-sm bg-white border border-gray-200 shadow-sm space-y-8">
                <div className="space-y-2 text-center flex flex-col items-center">
                    {/* Tag "Logo" temporária até você colocar a imagem real */}
                    <img src="/logo-ieeeufrn.png" alt="Logo" className="h-20" />
                    <h1 className="text-2xl font-bold tracking-tight text-gray-900">
                        Acesso ao Sistema
                    </h1>
                    <p className="text-gray-500 text-sm font-medium">
                        Ramo Estudantil UFRN
                    </p>
                </div>

                <form className="space-y-5" onSubmit={handleLogin}>
                    <div className="space-y-4">
                        <div>
                            <label
                                htmlFor="email"
                                className="block text-sm font-semibold text-gray-700 mb-1.5"
                            >
                                E-mail Pessoal
                            </label>
                            <input
                                id="email"
                                type="email"
                                required
                                className="w-full rounded-sm border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 placeholder-gray-400 focus:border-[#0F81CA] focus:outline-none focus:ring-1 focus:ring-[#0F81CA] transition-colors"
                                placeholder="seu.email@ieee.org"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                        <div>
                            <label
                                htmlFor="senha"
                                className="block text-sm font-semibold text-gray-700 mb-1.5"
                            >
                                Senha
                            </label>
                            <input
                                id="senha"
                                type="password"
                                required
                                className="w-full rounded-sm border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 placeholder-gray-400 focus:border-[#0F81CA] focus:outline-none focus:ring-1 focus:ring-[#0F81CA] transition-colors"
                                placeholder="•••••••••"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                        </div>
                    </div>

                    {erro && (
                        <div className="text-red-700 text-sm text-center font-medium bg-red-50 border border-red-200 py-2.5 rounded-sm">
                            {erro}
                        </div>
                    )}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8] text-white font-semibold py-3 rounded-sm text-sm transition-colors shadow-sm disabled:opacity-70"
                    >
                        {loading ? 'A autenticar...' : 'Entrar no Sistema'}
                    </button>
                </form>
            </div>
        </div>
    );
}
