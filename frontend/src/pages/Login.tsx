import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authService } from '@/api/auth.api';
import { useAuth } from '@/contexts/AuthContext';
import logoAzul from '../assets/logo_azul.png';

export function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [manterConectado, setManterConectado] = useState(false);
    const [erro, setErro] = useState('');
    const [loading, setLoading] = useState(false);

    const { login } = useAuth();
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setErro('');
        setLoading(true);

        try {
            const data = await authService.login({
                emailPessoal: email,
                senha: senha,
                manterConectado,
            });

            login(data.token);
            navigate('/');
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
                <div className="text-center flex flex-col items-center">
                    <img
                        src={logoAzul}
                        alt="Logo"
                        className="h-10 w-auto mb-4"
                    />
                    <h1 className="text-2xl font-bold tracking-tight text-gray-900">
                        Acesso ao Sistema
                    </h1>
                    <p className="text-gray-500 text-sm font-medium">
                        CoordIEEEna
                    </p>
                </div>

                <form className="space-y-4" onSubmit={handleLogin}>
                    <div className="space-y-4">
                        <div>
                            <label
                                htmlFor="email"
                                className="block text-sm font-semibold text-gray-700 mb-1.5"
                            >
                                E-mail
                            </label>
                            <input
                                id="email"
                                type="email"
                                required
                                className="w-full rounded-sm border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 placeholder-gray-400 focus:border-brand-primary focus:outline-none focus:ring-1 focus:ring-brand-primary transition-colors"
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
                                className="w-full rounded-sm border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 placeholder-gray-400 focus:border-brand-primary focus:outline-none focus:ring-1 focus:ring-brand-primary transition-colors"
                                placeholder="•••••••••"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                        </div>
                    </div>

                    {/* OPÇÕES: LEMBRAR DE MIM E ESQUECEU A SENHA */}
                    <div className="flex items-center justify-between text-sm">
                        <label className="flex items-center gap-2 cursor-pointer select-none text-gray-600 hover:text-gray-900">
                            <input
                                type="checkbox"
                                checked={manterConectado}
                                onChange={(e) =>
                                    setManterConectado(e.target.checked)
                                }
                                className="rounded border-gray-300 text-brand-primary focus:ring-brand-primary h-4 w-4 cursor-pointer"
                            />
                            <span>Lembrar de mim</span>
                        </label>

                        <Link
                            to="/"
                            className="text-brand-primary hover:underline font-medium"
                        >
                            Esqueceu a senha?
                        </Link>
                    </div>

                    {erro && (
                        <div className="text-red-700 text-sm text-center font-medium bg-red-50 border border-red-200 py-2.5 rounded-sm">
                            {erro}
                        </div>
                    )}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-brand-primary hover:bg-brand-primary/90 text-white font-semibold py-3 rounded-sm text-sm transition-colors shadow-sm disabled:opacity-70"
                    >
                        {loading ? 'Entrando...' : 'Entrar no Sistema'}
                    </button>
                </form>

                {/* OPÇÃO DE CADASTRO */}
                <div className="text-center text-sm text-gray-600 border-t border-gray-100 pt-4">
                    Ainda não tem uma conta?{' '}
                    <Link
                        to="/"
                        className="text-brand-primary font-semibold hover:underline"
                    >
                        Cadastre-se
                    </Link>
                </div>
            </div>
        </div>
    );
}
