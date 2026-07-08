// src/App.tsx
import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { X, UserCheck, ShieldAlert, Award, Plus, Trash2 } from 'lucide-react';
import { MainLayout } from './layouts/MainLayout';
import { voluntarioService, cargoService } from './services/api';
import { Login } from './pages/Login';
import { Eventos } from './pages/Eventos';
import { Diretorias } from './pages/Diretorias';

const RotaPrivada = ({ children }: { children: JSX.Element }) => {
  return localStorage.getItem('token') ? children : <Navigate to="/login" />;
};

function GestaoVoluntarios() {
  const [voluntarios, setVoluntarios] = useState<any[]>([]);
  const [cargos, setCargos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  
  const [painelAberto, setPainelAberto] = useState(false);
  const [usuarioSelecionado, setUsuarioSelecionado] = useState<any | null>(null);
  const [detalhesUsuario, setDetalhesUsuario] = useState<any | null>(null);

  const [modalCadastro, setModalCadastro] = useState(false);
  const [modalMembro, setModalMembro] = useState(false);
  const [modalDiretor, setModalDiretor] = useState(false);

  const [formCadastro, setFormCadastro] = useState({ primeiroNome: '', ultimoNome: '', emailPessoal: '', senha: '', telefone: '', cpf: '' });
  const [formMembro, setFormMembro] = useState({ numeroMembresia: '', tipoMembresia: 'STUDENT_MEMBER', emailIeee: '' });
  const [formDiretor, setFormDiretor] = useState({ tipoDiretor: 'DIRETOR_CAPITULO', cargoId: 0, dataInicio: '', dataFim: '' });

  const carregarDados = () => {
    voluntarioService.listarTodos().then(setVoluntarios).catch(console.error).finally(() => setLoading(false));
    cargoService.listarTodos().then(setCargos).catch(console.error);
  };

  useEffect(() => {
    carregarDados();
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

  // --- Funções de Ação ---
  const handleCadastrar = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await voluntarioService.cadastrar(formCadastro);
      alert('Voluntário convidado com sucesso!');
      setModalCadastro(false);
      carregarDados();
    } catch (error) {
      alert('Erro ao cadastrar voluntário.');
    }
  };

  const handlePromoverMembro = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await voluntarioService.promoverAMembro(usuarioSelecionado.id, formMembro);
      alert('Promovido a Membro com sucesso!');
      setModalMembro(false);
      setPainelAberto(false); // Fecha o painel para forçar a atualização da tabela
      carregarDados();
    } catch (error) {
      alert('Erro ao promover. Verifique as regras de negócio.');
    }
  };

  const handlePromoverDiretor = async (e: React.FormEvent) => {
    e.preventDefault();
    if (formDiretor.cargoId === 0) return alert('Selecione um cargo válido.');
    try {
      await voluntarioService.promoverADiretor(usuarioSelecionado.id, formDiretor);
      alert('Nomeado para a Diretoria com sucesso!');
      setModalDiretor(false);
      setPainelAberto(false);
      carregarDados();
    } catch (error) {
      alert('Erro ao nomear diretor. Verifique as datas do mandato.');
    }
  };

  const handleRemoverMembro = async () => {
    if (!window.confirm('Tem certeza que deseja revogar a membresia deste usuário? Ele perderá acesso de Diretor caso tenha.')) return;
    try {
      await voluntarioService.removerMembro(usuarioSelecionado.id);
      alert('Membresia revogada.');
      setPainelAberto(false);
      carregarDados();
    } catch (error) {
      alert('Erro ao revogar membresia.');
    }
  };

  const handleRemoverDiretor = async () => {
    if (!window.confirm('Tem certeza que deseja destituir este usuário da diretoria?')) return;
    try {
      await voluntarioService.removerDiretor(usuarioSelecionado.id);
      alert('Diretoria destituída.');
      setPainelAberto(false);
      carregarDados();
    } catch (error) {
      alert('Erro ao remover diretoria.');
    }
  };

  return (
    <MainLayout titulo="Hub de Voluntários">
      <div className="bg-white border border-gray-200 rounded-sm overflow-hidden shadow-sm">
        <div className="p-6 border-b border-gray-200 flex justify-between items-center bg-white">
          <h3 className="font-semibold text-lg text-gray-900">Diretório Ativo</h3>
          <button onClick={() => setModalCadastro(true)} className="flex items-center gap-2 bg-[#0F81CA] hover:bg-[#0c6ba8] text-white px-4 py-2 rounded-sm font-medium text-sm transition-colors shadow-sm">
            <Plus size={16} /> Cadastrar Novo
          </button>
        </div>
        
        <table className="w-full text-sm text-left">
          <thead className="text-xs text-gray-500 uppercase bg-gray-50 border-b border-gray-200">
            <tr>
              <th className="px-6 py-4 font-semibold">Nome Completo</th>
              <th className="px-6 py-4 font-semibold">Acesso / Papel</th>
            </tr>
          </thead>
          <tbody>
            {voluntarios.map((vol) => (
              <tr key={vol.id} onClick={() => abrirPainel(vol)} className="border-b border-gray-100 hover:bg-[#F4FAFF] transition-colors cursor-pointer group">
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
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* PAINEL LATERAL DINÂMICO */}
      <div className={`fixed inset-y-0 right-0 w-full md:w-[450px] bg-white border-l border-gray-200 shadow-2xl transform transition-transform duration-300 ease-in-out z-40 flex flex-col ${painelAberto ? 'translate-x-0' : 'translate-x-full'}`}>
        <div className="p-6 border-b border-gray-200 flex justify-between items-center bg-gray-50">
          <h2 className="text-xl font-bold text-[#0F81CA]">Ficha do Voluntário</h2>
          <button onClick={() => setPainelAberto(false)} className="p-2 hover:bg-gray-200 rounded-full text-gray-500 transition-colors"><X size={20} /></button>
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
            <div className="text-center text-gray-400 py-10 animate-pulse font-medium">A carregar base de dados...</div>
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
                  <p className="text-sm text-gray-500">Este utilizador não tem registo de membresia ativo.</p>
                )}
              </div>

              {/* Botões Inteligentes Baseados no Papel (Tipo de Usuario) */}
              <div className="grid gap-3">
                
                {usuarioSelecionado?.tipoUsuario === 'VOLUNTARIO' && (
                  <button onClick={() => setModalMembro(true)} className="flex items-center gap-4 bg-white hover:bg-[#F4FAFF] text-left p-4 rounded-sm border border-gray-200 hover:border-[#C6EBFF] transition-colors group">
                    <div className="bg-[#C6EBFF]/50 p-2.5 rounded-sm group-hover:bg-[#0F81CA] transition-colors"><Award size={20} className="text-[#0F81CA] group-hover:text-white" /></div>
                    <div>
                      <h4 className="font-bold text-sm text-gray-900 group-hover:text-[#0F81CA]">Promover a Membro</h4>
                      <p className="text-xs text-gray-500 mt-0.5">Registar número da IEEE e ativar</p>
                    </div>
                  </button>
                )}

                {usuarioSelecionado?.tipoUsuario.includes('DIRETOR') && (
                  <button onClick={handleRemoverDiretor} className="flex items-center justify-center gap-2 bg-white text-red-500 hover:bg-red-50 p-3 rounded-sm border border-red-200 transition-colors font-semibold text-sm">
                    <Trash2 size={16} /> Destituir da Diretoria
                  </button>
                )}

                {detalhesUsuario?.numeroMembresia && (
                  <button onClick={handleRemoverMembro} className="flex items-center justify-center gap-2 bg-white text-gray-500 hover:bg-gray-100 p-3 rounded-sm border border-gray-200 transition-colors font-semibold text-sm mt-2">
                    Revogar Membresia IEEE
                  </button>
                )}

              </div>
            </div>
          )}
        </div>
      </div>

      {/* MODAL CADASTRAR */}
      {modalCadastro && (
        <div className="fixed inset-0 bg-gray-900/40 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-sm shadow-xl w-full max-w-md border border-gray-200">
            <div className="p-6 border-b border-gray-100 flex justify-between items-center">
              <h3 className="font-bold text-lg text-gray-900">Novo Voluntário</h3>
              <button onClick={() => setModalCadastro(false)} className="text-gray-400 hover:text-gray-700"><X size={20}/></button>
            </div>
            <form onSubmit={handleCadastrar} className="p-6 space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-xs font-semibold text-gray-600 mb-1">Primeiro Nome</label><input required className="w-full border p-2 text-sm" value={formCadastro.primeiroNome} onChange={e => setFormCadastro({...formCadastro, primeiroNome: e.target.value})} /></div>
                <div><label className="block text-xs font-semibold text-gray-600 mb-1">Último Nome</label><input required className="w-full border p-2 text-sm" value={formCadastro.ultimoNome} onChange={e => setFormCadastro({...formCadastro, ultimoNome: e.target.value})} /></div>
              </div>
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">E-mail</label><input required type="email" className="w-full border p-2 text-sm" value={formCadastro.emailPessoal} onChange={e => setFormCadastro({...formCadastro, emailPessoal: e.target.value})} /></div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-xs font-semibold text-gray-600 mb-1">Telefone</label><input required className="w-full border p-2 text-sm" value={formCadastro.telefone} onChange={e => setFormCadastro({...formCadastro, telefone: e.target.value})} /></div>
                <div><label className="block text-xs font-semibold text-gray-600 mb-1">CPF</label><input required className="w-full border p-2 text-sm" value={formCadastro.cpf} onChange={e => setFormCadastro({...formCadastro, cpf: e.target.value})} /></div>
              </div>
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">Senha Temporária</label><input required type="password" className="w-full border p-2 text-sm" value={formCadastro.senha} onChange={e => setFormCadastro({...formCadastro, senha: e.target.value})} /></div>
              <button type="submit" className="w-full bg-[#0F81CA] text-white py-2.5 text-sm font-semibold rounded-sm mt-4">Cadastrar</button>
            </form>
          </div>
        </div>
      )}

      {/* MODAL MEMBRO */}
      {modalMembro && (
        <div className="fixed inset-0 bg-gray-900/40 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-sm shadow-xl w-full max-w-sm border border-gray-200">
            <div className="p-6 border-b border-gray-100 flex justify-between items-center bg-[#F4FAFF]">
              <h3 className="font-bold text-lg text-[#0F81CA]">Promover a Membro</h3>
              <button onClick={() => setModalMembro(false)} className="text-gray-400 hover:text-[#0F81CA]"><X size={20}/></button>
            </div>
            <form onSubmit={handlePromoverMembro} className="p-6 space-y-4">
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">Nº Membresia IEEE</label><input required className="w-full border p-2 text-sm" value={formMembro.numeroMembresia} onChange={e => setFormMembro({...formMembro, numeroMembresia: e.target.value})} /></div>
                <div>
                    <label className="block text-xs font-semibold text-gray-600 mb-1">Categoria de Membro</label>
                    <select 
                    className="w-full border p-2 text-sm bg-white focus:border-[#0F81CA] focus:outline-none" 
                    value={formMembro.tipoMembresia} 
                    onChange={e => setFormMembro({...formMembro, tipoMembresia: e.target.value})}
                    >
                    <option value="STUDENT_MEMBER">Student Member</option>
                    <option value="GRADUATED_STUDENT_MEMBER">Graduate Student Member</option>
                    <option value="MEMBER">Member</option>
                    <option value="SENIOR_MEMBER">Senior Member</option>
                    <option value="LIFE_MEMBER">Life Member</option>
                    <option value="FELLOW">Fellow</option>
                    </select>
                </div>
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">E-mail IEEE ou preferencial</label><input required type="email" className="w-full border p-2 text-sm" value={formMembro.emailIeee} onChange={e => setFormMembro({...formMembro, emailIeee: e.target.value})} /></div>
              <button type="submit" className="w-full bg-[#0F81CA] text-white py-2.5 text-sm font-semibold rounded-sm mt-4">Salvar</button>
            </form>
          </div>
        </div>
      )}

      {painelAberto && <div className="fixed inset-0 bg-gray-900/20 backdrop-blur-sm z-30" onClick={() => setPainelAberto(false)} />}
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
        <Route path="/diretorias" element={<RotaPrivada><Diretorias /></RotaPrivada>} />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;