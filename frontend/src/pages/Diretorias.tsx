// src/pages/Diretorias.tsx
import { useEffect, useState } from 'react';
import { ShieldAlert, Trash2, Award, Users } from 'lucide-react';
import { MainLayout } from '../layouts/MainLayout';
import { voluntarioService } from '@/api/voluntarios.api';
import { cargoService } from '@/api/gestao.api';
import { unidadeService } from '@/api/organizacional.api';

export function Diretorias() {
  const [voluntarios, setVoluntarios] = useState<any[]>([]);
  const [cargos, setCargos] = useState<any[]>([]);
  const [unidades, setUnidades] = useState<any[]>([]); // Estado dinâmico para os capítulos/grupos
  const [loading, setLoading] = useState(true);

  const [membroId, setMembroId] = useState<number>(0);
  const [formDiretor, setFormDiretor] = useState({ 
    tipoDiretor: 'DIRETOR_CAPITULO', 
    cargoId: 0, 
    unidadeCodigo: '', // Agora guarda o código/id vindo do banco
    dataInicio: '', 
    dataFim: '' 
  });

  const carregarDados = () => {
    voluntarioService.listarTodos().then(setVoluntarios).catch(console.error).finally(() => setLoading(false));
    cargoService.listarTodos().then(setCargos).catch(console.error);
    unidadeService.listarTodas().then(setUnidades).catch(err => {
      console.error("Erro ao carregar capítulos", err);
      setUnidades([]);
    });
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const apenasMembros = voluntarios.filter(v => v.tipoUsuario === 'MEMBRO' || v.tipoUsuario.includes('DIRETOR'));
  const diretoresAtuais = voluntarios.filter(v => v.tipoUsuario.includes('DIRETOR'));

  const handleNomearDiretor = async (e: React.FormEvent) => {
    e.preventDefault();
    if (membroId === 0) return alert('Selecione um Membro.');
    if (formDiretor.cargoId === 0) return alert('Selecione um Cargo oficial.');
    if (formDiretor.tipoDiretor === 'DIRETOR_CAPITULO' && !formDiretor.unidadeCodigo) {
      return alert('Selecione a qual Capítulo ou Grupo este diretor pertence.');
    }

    try {
      await voluntarioService.promoverADiretor(membroId, formDiretor);
      alert('Mandato oficializado com sucesso!');
      setMembroId(0);
      setFormDiretor({ tipoDiretor: 'DIRETOR_CAPITULO', cargoId: 0, unidadeCodigo: '', dataInicio: '', dataFim: '' });
      carregarDados();
    } catch (error) {
      alert('Erro ao oficializar o mandato.');
    }
  };

  const handleDestituir = async (id: number) => {
    if (!window.confirm('Tem certeza que deseja destituir este Diretor?')) return;
    try {
      await voluntarioService.removerDiretor(id);
      alert('Diretor destituído.');
      carregarDados();
    } catch (error) {
      alert('Erro ao destituir.');
    }
  };

  return (
    <MainLayout titulo="Gestão de Lideranças">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        
        {/* COLUNA ESQUERDA: Formulário */}
        <div className="lg:col-span-1 bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 bg-orange-50">
            <h3 className="font-bold text-lg text-[#ED7630] flex items-center gap-2">
              <ShieldAlert size={20} /> Nova Nomeação
            </h3>
            <p className="text-xs text-gray-500 mt-1">Vincule o membro ao cargo e unidade correspondente.</p>
          </div>
          
          <form onSubmit={handleNomearDiretor} className="p-6 space-y-4">
            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Selecione o Membro</label>
              <select required className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-white focus:border-[#ED7630] focus:outline-none" value={membroId} onChange={e => setMembroId(parseInt(e.target.value))}>
                <option value={0} disabled>Escolha na lista...</option>
                {apenasMembros.map(m => (
                  <option key={m.id} value={m.id}>{m.primeiroNome} {m.ultimoNome}</option>
                ))}
              </select>
            </div>

            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="block text-xs font-semibold text-gray-600 mb-1">Nível</label>
                <select className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-white focus:border-[#ED7630] focus:outline-none" value={formDiretor.tipoDiretor} onChange={e => setFormDiretor({...formDiretor, tipoDiretor: e.target.value})}>
                  <option value="DIRETOR_CAPITULO">Capítulo / Grupo</option>
                  <option value="DIRETOR_RAMO">Ramo Geral</option>
                </select>
              </div>

              {/* Select Dinâmico de Capítulos vindo do Banco */}
              {formDiretor.tipoDiretor === 'DIRETOR_CAPITULO' && (
                <div className="animate-in fade-in duration-200">
                  <label className="block text-xs font-semibold text-gray-600 mb-1">Unidade / Capítulo</label>
                  <select 
                    required 
                    className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-white focus:border-[#ED7630] focus:outline-none" 
                    value={formDiretor.unidadeCodigo} 
                    onChange={e => setFormDiretor({...formDiretor, unidadeCodigo: e.target.value})}
                  >
                    <option value="" disabled>Selecione...</option>
                    {unidades.map(u => (
                      <option key={u.id || u.codigo || u.unidadeCodigo} value={u.codigo || u.unidadeCodigo || u.id}>
                        {u.nome || u.descricao}
                      </option>
                    ))}
                  </select>
                </div>
              )}
            </div>

            <div>
              <label className="block text-xs font-semibold text-gray-600 mb-1">Cargo Oficial</label>
              <select required className="w-full border border-gray-300 rounded-sm p-2 text-sm bg-white focus:border-[#ED7630] focus:outline-none" value={formDiretor.cargoId} onChange={e => setFormDiretor({...formDiretor, cargoId: parseInt(e.target.value)})}>
                <option value={0} disabled>Selecione um Cargo...</option>
                {cargos.map(cargo => (
                  <option key={cargo.id} value={cargo.id}>{cargo.nome}</option>
                ))}
              </select>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">Início</label><input required type="date" className="w-full border border-gray-300 rounded-sm p-2 text-sm text-gray-600" value={formDiretor.dataInicio} onChange={e => setFormDiretor({...formDiretor, dataInicio: e.target.value})} /></div>
              <div><label className="block text-xs font-semibold text-gray-600 mb-1">Fim</label><input required type="date" className="w-full border border-gray-300 rounded-sm p-2 text-sm text-gray-600" value={formDiretor.dataFim} onChange={e => setFormDiretor({...formDiretor, dataFim: e.target.value})} /></div>
            </div>

            <button type="submit" className="w-full bg-[#ED7630] hover:bg-[#d66524] text-white py-2.5 text-sm font-semibold rounded-sm mt-4 transition-colors">
              Oficializar Mandato
            </button>
          </form>
        </div>

        {/* COLUNA DIREITA: Lista de Diretores Ativos */}
        <div className="lg:col-span-2 bg-white border border-gray-200 rounded-sm shadow-sm h-fit">
          <div className="p-6 border-b border-gray-200 flex justify-between items-center bg-gray-50">
            <h3 className="font-bold text-lg text-gray-900 flex items-center gap-2"><Users size={20} className="text-[#0F81CA]" /> Diretoria Vigente</h3>
            <span className="bg-white text-gray-600 border border-gray-200 py-1 px-3 rounded-full text-xs font-bold shadow-sm">{diretoresAtuais.length} Lideranças</span>
          </div>

          <div className="p-6">
            {loading ? (
              <p className="text-gray-400 text-sm animate-pulse">Carregando lideranças...</p>
            ) : diretoresAtuais.length === 0 ? (
              <div className="text-center py-10 border border-dashed border-gray-200 rounded-sm">
                <Award size={40} className="mx-auto text-gray-300 mb-3" />
                <p className="text-gray-500 text-sm">Nenhum diretor nomeado no momento.</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {diretoresAtuais.map(dir => (
                  <div key={dir.id} className="flex justify-between items-start p-4 border border-gray-100 bg-white rounded-sm hover:border-[#0F81CA] hover:shadow-sm transition-all relative overflow-hidden group">
                    <div className="absolute left-0 top-0 bottom-0 w-1 bg-[#ED7630]"></div>

                    <div className="pl-2">
                      <h4 className="font-bold text-gray-900 leading-tight">{dir.primeiroNome} {dir.ultimoNome}</h4>
                      <p className="text-xs text-gray-500 mt-1">{dir.emailPessoal}</p>
                      
                      <div className="flex gap-2 mt-3">
                        <span className="inline-block text-[10px] font-bold bg-gray-100 text-gray-700 px-2 py-0.5 rounded-sm border border-gray-200">
                          {dir.cargoAtualNome || 'Cargo Atribuído'}
                        </span>
                        <span className="inline-block text-[10px] font-bold bg-orange-50 text-[#ED7630] px-2 py-0.5 rounded-sm border border-orange-200">
                          {dir.unidadeCodigo || dir.tipoUsuario.replace('_', ' ')}
                        </span>
                      </div>
                    </div>
                    
                    <button 
                      onClick={() => handleDestituir(dir.id)}
                      className="text-gray-300 hover:text-red-600 p-1.5 rounded-sm opacity-0 group-hover:opacity-100 transition-all"
                      title="Destituir do Cargo"
                    >
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