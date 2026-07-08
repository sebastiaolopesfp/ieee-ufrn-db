import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { X, UserCheck, ShieldAlert, Award } from 'lucide-react';
import { MainLayout } from './layouts/MainLayout';
import { voluntarioService } from './services/api';
import { Login } from './pages/Login';
import { Eventos } from './pages/Eventos';

const RotaPrivada = ({ children }: { children: JSX.Element }) => {
    return localStorage.getItem('token') ? children : <Navigate to="/login" />;
};

function GestaoVoluntarios() {
    const [voluntarios, setVoluntarios] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    const [painelAberto, setPainelAberto] = useState(false);
    const [usuarioSelecionado, setUsuarioSelecionado] = useState<any | null>(null);
    const [detalhesUsuario, setDetalhesUsuario] = useState<any | null>(null);

    useEffect(() => {
        voluntarioService.listarTodos().then((dados) => {
            setVoluntarios(dados);
            setLoading(false);
        });
    }, []);

    const abrirPainel = async (voluntario: any) => {
        setUsuarioSelecionado(voluntario);
        setPainelAberto(true);
        setDetalhesUsuario(null);

        try {
            const perfilCompleto = await voluntarioService.obterPerfil(voluntario.id);
            setDetalhesUsuario(perfilCompleto);
        } catch (error) {
            console.error("Erro ao buscar detalhes", error);
        }
    };

    return (
        <MainLayout titulo="Hub de Voluntários">
            <div className="bg-white border border-gray-200 rounded-sm overflow-hidden shadow-sm">
                <div className="p-6 border-b border-gray-200 flex justify-between items-center bg-white">
                    <h3 className="font-semibold text-lg text-gray-900">Diretório Ativo</h3>
                    <button className="bg-[#0F81CA] hover:bg-[#0c6ba8] text-white px-4 py-2 rounded-sm font-medium text-sm transition-colors shadow-sm">
                        + Convidar Novo
                    </button>
                </div>

                <table className="w-full text-sm text-left">
                    <thead className="text-xs text-gray-500 uppercase bg-gray-50 border-b border-[#C6EBFF]">
                        <tr>
                            <th className="px-6 py-4 font-semibold">Nome Completo</th>
                            <th className="px-6 py-4 font-semibold">Acesso / Papel</th>
                            <th className="px-6 py-4 text-right font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {voluntarios.map((vol) => (
                            <tr
                                key={vol.id}
                                onClick={() => abrirPainel(vol)}
                                className="border-b border-gray-100 hover:bg-[#F4FAFF] transition-colors cursor-pointer group"
                            >
                                <td className="px-6 py-4">
                                    <div className="font-bold text-gray-900">{vol.primeiroNome} {vol.ultimoNome}</div>
                                    <div className="text-gray-500 text-xs mt-1">{vol.emailPessoal}</div>
                                </td>
                                <td className="px-6 py-4">
                                    <span className={`text-xs font-semibold px-2.5 py-1 rounded-sm border 
                    ${vol.tipoUsuario === 'ADMIN' ? 'bg-purple-50 text-purple-700 border-purple-200' :
                                            vol.tipoUsuario.includes('DIRETOR') ? 'bg-orange-50 text-[#ED7630] border-orange-200' :
                                                'bg-[#C6EBFF]/50 text-[#0F81CA] border-[#9DCDE6]'}`}>
                                        {vol.tipoUsuario.replace('_', ' ')}
                                    </span>
                                </td>
                                <td className="px-6 py-4 text-right">
                                    <span className="text-[#0F81CA] font-medium opacity-0 group-hover:opacity-100 transition-opacity">
                                        Gerir &rarr;
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {/* PAINEL LATERAL DE VOLUNTÁRIOS */}
            <div
                className={`fixed inset-y-0 right-0 w-full md:w-[450px] bg-white border-l border-[#C6EBFF] shadow-2xl transform transition-transform duration-300 ease-in-out z-50 flex flex-col
        ${painelAberto ? 'translate-x-0' : 'translate-x-full'}`}
            >
                <div className="p-6 border-b border-[#C6EBFF] flex justify-between items-center bg-[#F9FCFF]">
                    <h2 className="text-xl font-bold text-[#0F81CA]">Ficha do Voluntário</h2>
                    <button
                        onClick={() => setPainelAberto(false)}
                        className="p-2 hover:bg-[#C6EBFF]/50 rounded-full text-gray-400 hover:text-[#0F81CA] transition-colors"
                    >
                        <X size={20} />
                    </button>
                </div>

                <div className="flex-1 overflow-y-auto p-6 space-y-8 bg-white">
                    {usuarioSelecionado && (
                        <div className="text-center space-y-3">
                            <div className="w-20 h-20 bg-[#F4FAFF] rounded-full mx-auto flex items-center justify-center border-2 border-[#C6EBFF]">
                                <UserCheck size={32} className="text-[#0F81CA]" />
                            </div>
                            <div>
                                <h3 className="text-2xl font-bold text-gray-900">{usuarioSelecionado.primeiroNome} {usuarioSelecionado.ultimoNome}</h3>
                                <p className="text-gray-500 text-sm mt-1">{usuarioSelecionado.emailPessoal}</p>
                            </div>
                        </div>
                    )}

                    {!detalhesUsuario ? (
                        <div className="text-center text-gray-400 py-10 animate-pulse font-medium">
                            A carregar base de dados...
                        </div>
                    ) : (
                        <div className="space-y-6">

                            <div className="bg-gray-50 rounded-sm p-5 border border-gray-200">
                                <h4 className="text-xs font-bold text-gray-400 uppercase mb-4 tracking-wider">Status IEEE</h4>

                                {detalhesUsuario.numeroMembresia ? (
                                    <div className="space-y-3">
                                        <div className="flex justify-between">
                                            <span className="text-gray-600 font-medium">Nº Membresia</span>
                                            <span className="font-mono text-[#0F81CA] font-bold">{detalhesUsuario.numeroMembresia}</span>
                                        </div>
                                        <div className="flex justify-between">
                                            <span className="text-gray-600 font-medium">E-mail IEEE</span>
                                            <span className="text-gray-900">{detalhesUsuario.emailIeee}</span>
                                        </div>
                                    </div>
                                ) : (
                                    <p className="text-sm text-gray-500">Este utilizador ainda não tem registo de membresia ativo no Ramo.</p>
                                )}
                            </div>

                            <div className="grid gap-3">
                                <button className="flex items-center gap-4 bg-white hover:bg-[#F4FAFF] text-left p-4 rounded-sm border border-gray-200 hover:border-[#C6EBFF] transition-colors group">
                                    <div className="bg-[#C6EBFF]/50 p-2.5 rounded-sm group-hover:bg-[#0F81CA] transition-colors">
                                        <Award size={20} className="text-[#0F81CA] group-hover:text-white" />
                                    </div>
                                    <div>
                                        <h4 className="font-bold text-sm text-gray-900 group-hover:text-[#0F81CA] transition-colors">Promover a Membro</h4>
                                        <p className="text-xs text-gray-500 mt-0.5">Registar número da IEEE e ativar</p>
                                    </div>
                                </button>

                                <button className="flex items-center gap-4 bg-white hover:bg-orange-50 text-left p-4 rounded-sm border border-gray-200 hover:border-orange-200 transition-colors group">
                                    <div className="bg-orange-100 p-2.5 rounded-sm group-hover:bg-[#ED7630] transition-colors">
                                        <ShieldAlert size={20} className="text-[#ED7630] group-hover:text-white" />
                                    </div>
                                    <div>
                                        <h4 className="font-bold text-sm text-gray-900 group-hover:text-[#ED7630] transition-colors">Nomear para Diretoria</h4>
                                        <p className="text-xs text-gray-500 mt-0.5">Atribuir cargo e mandato oficial</p>
                                    </div>
                                </button>
                            </div>

                        </div>
                    )}
                </div>
            </div>

            {painelAberto && (
                <div
                    className="fixed inset-0 bg-gray-900/20 backdrop-blur-sm z-40"
                    onClick={() => setPainelAberto(false)}
                />
            )}
        </MainLayout>
    );
}

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={<RotaPrivada><GestaoVoluntarios /></RotaPrivada>} />
                <Route path="/eventos" element={<RotaPrivada><Eventos /></RotaPrivada>} />
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;