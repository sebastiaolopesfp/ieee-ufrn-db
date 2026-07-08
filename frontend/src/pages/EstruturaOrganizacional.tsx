// src/pages/EstruturaOrganizacional.tsx
import { useEffect, useState } from 'react';
import { Network, Trash2, Briefcase } from 'lucide-react';
import { MainLayout } from '../layouts/MainLayout';
import { capituloService, ramoEstudantilService, cargoService } from '../services/api';

export function EstruturaOrganizacional() {
  const [capitulos, setCapitulos] = useState<any[]>([]);
  const [ramos, setRamos] = useState<any[]>([]);
  const [cargos, setCargos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Form para Capítulo mapeando exatamente o CapituloRequestDTO do backend
  const [formCapitulo, setFormCapitulo] = useState({ 
    unidadeCodigo: '', 
    nome: '', 
    email: '', 
    anoCriacao: new Date().getFullYear(), 
    ramoCodigo: '' 
  });

  const [formCargo, setFormCargo] = useState({ nome: '', descricao: '' });

  const carregarDados = () => {
    setLoading(true);
    Promise.all([
      capituloService.listarTodos().catch(() => []),
      ramoEstudantilService.listarTodos().catch(() => []),
      cargoService.listarTodos().catch(() => [])
    ])
      .then(([resCapitulos, resRamos, resCargos]) => {
        setCapitulos(Array.isArray(resCapitulos) ? resCapitulos : []);
        setRamos(Array.isArray(resRamos) ? resRamos : []);
        setCargos(Array.isArray(resCargos) ? resCargos : []);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleCriarCapitulo = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formCapitulo.ramoCodigo) {
      return alert('Selecione um Ramo Estudantil ao qual este capítulo se vincula.');
    }
    try {
      await capituloService.criar(formCapitulo);
      alert('Capítulo cadastrado com sucesso!');
      setFormCapitulo({ 
        unidadeCodigo: '', 
        nome: '', 
        email: '', 
        anoCriacao: new Date().getFullYear(), 
        ramoCodigo: '' 
      });
      carregarDados();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Erro ao cadastrar capítulo. Verifique se o código do ramo existe.');
    }
  };

  const handleDeletarCapitulo = async (id: string) => {
    if (!window.confirm('Deseja excluir este capítulo?')) return;
    try {
      await capituloService.deletar(id);
      carregarDados();
    } catch (err) {
      alert('Erro ao remover capítulo.');
    }
  };

  const handleCriarCargo = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await cargoService.criar(formCargo);
      alert('Cargo cadastrado no catálogo com sucesso!');
      setFormCargo({ nome: '', descricao: '' });
      carregarDados();
    } catch (err) {
      alert('Erro ao cadastrar cargo.');
    }
  };

  const handleDeletarCargo = async (id: number) => {
    if (!window.confirm('Deseja excluir este cargo?')) return;
    try {
      await cargoService.deletar(id);
      carregarDados();
    } catch (err) {
      alert('Erro ao remover cargo.');
    }
  };

  const safeCapitulos = Array.isArray(capitulos) ? capitulos : [];
  const safeRamos = Array.isArray(ramos) ? ramos : [];
  const safeCargos = Array.isArray(cargos) ? cargos : [];

  return (
    <MainLayout titulo="Estrutura Organizacional & Cargos">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        
        {/* COLUNA 1: Gestão de Capítulos */}
        <div className="bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 bg-[#F9FCFF]">
            <h3 className="font-bold text-lg text-[#0F81CA] flex items-center gap-2">
              <Network size={20} /> Cadastrar Capítulo
            </h3>
            <p className="text-xs text-gray-500 mt-1">Insira os dados da unidade e escolha o Ramo correspondente.</p>
          </div>

          <form onSubmit={handleCriarCapitulo} className="p-6 space-y-4">
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Ramo Estudantil (Vínculo Pai)</label>
              <select 
                required 
                className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-white focus:border-[#0F81CA] focus:outline-none" 
                value={formCapitulo.ramoCodigo} 
                onChange={e => setFormCapitulo({...formCapitulo, ramoCodigo: e.target.value})}
              >
                <option value="">Selecione o Ramo...</option>
                {safeRamos.map(r => (
                  <option key={r.unidadeCodigo || r.id} value={r.unidadeCodigo || r.id}>
                    {r.nome || r.unidadeCodigo}
                  </option>
                ))}
              </select>
            </div>

            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="block text-xs font-semibold text-gray-600 mb-1">Código Único (ID)</label>
                <input required type="text" placeholder="Ex: CS, PES" className="w-full border border-gray-300 rounded-sm p-2 text-sm uppercase focus:border-[#0F81CA] focus:outline-none" value={formCapitulo.unidadeCodigo} onChange={e => setFormCapitulo({...formCapitulo, unidadeCodigo: e.target.value})} />
              </div>
              <div>
                <label className="block text-xs font-semibold text-gray-600 mb-1">Ano de Criação</label>
                <input required type="number" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formCapitulo.anoCriacao} onChange={e => setFormCapitulo({...formCapitulo, anoCriacao: parseInt(e.target.value) || 2026})} />
              </div>
            </div>

            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Nome Completo</label>
              <input required type="text" placeholder="IEEE Computer Society" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formCapitulo.nome} onChange={e => setFormCapitulo({...formCapitulo, nome: e.target.value})} />
            </div>

            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">E-mail da Unidade</label>
              <input required type="email" placeholder="cs@ufrn.br" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formCapitulo.email} onChange={e => setFormCapitulo({...formCapitulo, email: e.target.value})} />
            </div>

            <button type="submit" className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8] text-white py-2.5 text-sm font-semibold rounded-sm transition-colors shadow-sm">
              Adicionar Capítulo
            </button>
          </form>

          <div className="p-6 border-t border-gray-100 bg-gray-50">
            <h4 className="text-xs font-bold text-gray-400 uppercase mb-3 tracking-wider">Capitulos Cadastrados</h4>
            {loading ? (
              <p className="text-xs text-gray-400 animate-pulse">Carregando...</p>
            ) : safeCapitulos.length === 0 ? (
              <p className="text-xs text-gray-400 italic">Nenhum capítulo cadastrado.</p>
            ) : (
              <div className="space-y-2">
                {safeCapitulos.map(u => (
                  <div key={u.unidadeCodigo} className="p-3 bg-white border border-gray-200 rounded-sm flex justify-between items-center">
                    <div>
                      <span className="font-bold text-sm text-gray-900">{u.nome} ({u.unidadeCodigo})</span>
                      <span className="block text-[10px] text-[#0F81CA] font-semibold mt-0.5">Ramo Pai: {u.ramoCodigo}</span>
                    </div>
                    <button onClick={() => handleDeletarCapitulo(u.unidadeCodigo)} className="text-gray-300 hover:text-red-600 p-1">
                      <Trash2 size={16} />
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* COLUNA 2: Gestão do Catálogo de Cargos */}
        <div className="bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 bg-[#F9FCFF]">
            <h3 className="font-bold text-lg text-[#0F81CA] flex items-center gap-2">
              <Briefcase size={20} /> Catálogo de Cargos
            </h3>
            <p className="text-xs text-gray-500 mt-1">Cadastre e gerencie as funções oficiais disponíveis.</p>
          </div>

          <form onSubmit={handleCriarCargo} className="p-6 space-y-4">
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Nome do Cargo</label>
              <input required type="text" placeholder="Diretor de Projetos" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formCargo.nome} onChange={e => setFormCargo({...formCargo, nome: e.target.value})} />
            </div>

            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Descrição das Atribuições</label>
              <textarea placeholder="Responsável por liderar as frentes..." className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none h-20 resize-none" value={formCargo.descricao} onChange={e => setFormCargo({...formCargo, descricao: e.target.value})} />
            </div>

            <button type="submit" className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8] text-white py-2.5 text-sm font-semibold rounded-sm transition-colors shadow-sm">
              Cadastrar Cargo
            </button>
          </form>

          <div className="p-6 border-t border-gray-100 bg-gray-50">
            <h4 className="text-xs font-bold text-gray-400 uppercase mb-3 tracking-wider">Cargos Disponíveis</h4>
            {safeCargos.length === 0 ? (
              <p className="text-xs text-gray-400 italic">Nenhum cargo cadastrado no catálogo.</p>
            ) : (
              <div className="space-y-2">
                {safeCargos.map(c => (
                  <div key={c.id} className="p-3 bg-white border border-gray-200 rounded-sm flex justify-between items-center">
                    <div>
                      <span className="font-bold text-sm text-gray-900">{c.nome}</span>
                      <p className="text-xs text-gray-500 mt-0.5 line-clamp-1">{c.descricao || 'Sem descrição'}</p>
                    </div>
                    <button onClick={() => handleDeletarCargo(c.id)} className="text-gray-300 hover:text-red-600 p-1">
                      <Trash2 size={16} />
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

      </div>
    </MainLayout>
  );
}