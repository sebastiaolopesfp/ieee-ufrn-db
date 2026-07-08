// src/pages/VinculosAcademicos.tsx
import { useEffect, useState } from 'react';
import { Building2, BookOpen } from 'lucide-react';
import { MainLayout } from '../layouts/MainLayout';
import { instituicaoService, cursoService } from '../services/api';

export function VinculosAcademicos() {
  const [instituicoes, setInstituicoes] = useState<any[]>([]);
  const [cursos, setCursos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Forms
  const [formInst, setFormInst] = useState({ nome: '', sigla: '' });
  const [formCurso, setFormCurso] = useState({ nome: '', instituicaoId: 0 });

  const carregarDados = () => {
    setLoading(true);
    instituicaoService.listarTodas()
      .then(res => setInstituicoes(Array.isArray(res) ? res : []))
      .catch(console.error)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleSelecionarInstituicao = async (instId: number) => {
    setFormCurso(prev => ({ ...prev, instituicaoId: instId }));
    try {
      const listaCursos = await instituicaoService.listarCursosPorInstituicao(instId);
      setCursos(Array.isArray(listaCursos) ? listaCursos : []);
    } catch (err) {
      console.error(err);
      setCursos([]);
    }
  };

  const handleCriarInstituicao = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await instituicaoService.criar(formInst);
      alert('Instituição cadastrada com sucesso!');
      setFormInst({ nome: '', sigla: '' });
      carregarDados();
    } catch (err) {
      alert('Erro ao cadastrar instituição.');
    }
  };

  const handleCriarCurso = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formCurso.instituicaoId) return alert('Selecione uma instituição primeiro.');
    try {
      // Mapeia corretamente para a rota POST /api/cursos exigida pelo seu backend
      await cursoService.criar(formCurso);
      alert('Curso cadastrado com sucesso!');
      setFormCurso(prev => ({ ...prev, nome: '' }));
      handleSelecionarInstituicao(formCurso.instituicaoId);
    } catch (err) {
      alert('Erro ao cadastrar curso.');
    }
  };

  const safeInstituicoes = Array.isArray(instituicoes) ? instituicoes : [];
  const safeCursos = Array.isArray(cursos) ? cursos : [];

  return (
    <MainLayout titulo="Vínculos Acadêmicos">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        
        {/* COLUNA 1: Gestão de Instituições */}
        <div className="bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 bg-[#F9FCFF]">
            <h3 className="font-bold text-lg text-[#0F81CA] flex items-center gap-2">
              <Building2 size={20} /> Cadastrar Instituição
            </h3>
            <p className="text-xs text-gray-500 mt-1">Insira as faculdades parceiras (Ex: UFRN, IFRN).</p>
          </div>

          <form onSubmit={handleCriarInstituicao} className="p-6 space-y-4">
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Nome da Instituição</label>
              <input required type="text" placeholder="Universidade Federal do Rio Grande do Norte" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formInst.nome} onChange={e => setFormInst({...formInst, nome: e.target.value})} />
            </div>
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Sigla</label>
              <input required type="text" placeholder="UFRN" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none uppercase" value={formInst.sigla} onChange={e => setFormInst({...formInst, sigla: e.target.value})} />
            </div>
            <button type="submit" className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8] text-white py-2.5 text-sm font-semibold rounded-sm transition-colors shadow-sm">
              Salvar Instituição
            </button>
          </form>

          <div className="p-6 border-t border-gray-100 bg-gray-50">
            <h4 className="text-xs font-bold text-gray-400 uppercase mb-3 tracking-wider">Parceiras Registradas</h4>
            {loading ? (
              <p className="text-xs text-gray-400 animate-pulse">Carregando...</p>
            ) : safeInstituicoes.length === 0 ? (
              <p className="text-xs text-gray-400 italic">Nenhuma instituição cadastrada.</p>
            ) : (
              <div className="space-y-2">
                {safeInstituicoes.map(inst => (
                  <div key={inst.id} onClick={() => handleSelecionarInstituicao(inst.id)} className={`p-3 bg-white border rounded-sm flex justify-between items-center cursor-pointer transition-all ${formCurso.instituicaoId === inst.id ? 'border-[#0F81CA] bg-[#F4FAFF]' : 'border-gray-200 hover:border-gray-300'}`}>
                    <span className="font-bold text-sm text-gray-900">{inst.nome} ({inst.sigla})</span>
                    <span className="text-xs text-[#0F81CA] font-medium">&rarr; Ver Cursos</span>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* COLUNA 2: Gestão de Cursos */}
        <div className="bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 bg-[#F9FCFF]">
            <h3 className="font-bold text-lg text-[#0F81CA] flex items-center gap-2">
              <BookOpen size={20} /> Cadastrar Cursos
            </h3>
            <p className="text-xs text-gray-500 mt-1">Vincule os cursos à instituição selecionada ao lado.</p>
          </div>

          <form onSubmit={handleCriarCurso} className="p-6 space-y-4">
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Instituição Selecionada</label>
              <select required className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-gray-50 text-gray-700 focus:outline-none" value={formCurso.instituicaoId} onChange={e => handleSelecionarInstituicao(parseInt(e.target.value))}>
                <option value={0} disabled>Clique numa instituição na coluna ao lado...</option>
                {safeInstituicoes.map(inst => (
                  <option key={inst.id} value={inst.id}>{inst.nome}</option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Nome do Curso</label>
              <input required type="text" placeholder="Engenharia de Computação" className="w-full border border-gray-300 rounded-sm p-2 text-sm focus:border-[#0F81CA] focus:outline-none" value={formCurso.nome} onChange={e => setFormCurso({...formCurso, nome: e.target.value})} />
            </div>
            <button type="submit" className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8] text-white py-2.5 text-sm font-semibold rounded-sm transition-colors shadow-sm">
              Adicionar Curso
            </button>
          </form>

          <div className="p-6 border-t border-gray-100 bg-gray-50">
            <h4 className="text-xs font-bold text-gray-400 uppercase mb-3 tracking-wider">Cursos da Instituição Ativa</h4>
            {safeCursos.length === 0 ? (
              <p className="text-xs text-gray-400 italic">Selecione uma instituição para ver seus cursos.</p>
            ) : (
              <div className="space-y-2">
                {safeCursos.map(curso => (
                  <div key={curso.id} className="p-3 bg-white border border-gray-200 rounded-sm text-sm font-medium text-gray-800">
                    {curso.nome}
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