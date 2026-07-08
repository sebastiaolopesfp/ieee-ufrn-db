import { useEffect, useState } from 'react';
import { Calendar, MapPin, Activity, Plus } from 'lucide-react';
import { MainLayout } from '../layouts/MainLayout';
import { eventoService, type Evento } from '../services/api';
import { Button } from '@/components/ui/button';

import { EventoFormModal } from '../components/EventoFormModal';
import { EventoPainel } from '../components/EventoPainel';

export function Eventos() {
  const [eventos, setEventos] = useState<Evento[]>([]);
  const [loading, setLoading] = useState(true);
  
  // Controle de Permissão
  const token = localStorage.getItem('token');
  let userRole = 'VOLUNTARIO';
  if (token) {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      userRole = payload.role;
    } catch (e) {}
  }
  const temPermissaoEdicao = ['ADMIN', 'DIRETOR_RAMO', 'DIRETOR_CAPITULO'].includes(userRole);

  // Estados de Interface
  const [painelAberto, setPainelAberto] = useState(false);
  const [eventoSelecionado, setEventoSelecionado] = useState<Evento | null>(null);
  
  const [modalFormAberto, setModalFormAberto] = useState(false);
  const [modoForm, setModoForm] = useState<'criar' | 'editar'>('criar');
  const [loadingAcao, setLoadingAcao] = useState(false);

  const carregarEventos = () => {
    setLoading(true);
    eventoService.listarTodos().then(setEventos).finally(() => setLoading(false));
  };

  useEffect(() => { carregarEventos(); }, []);

  // Handlers para abrir modais
  const abrirCriacao = () => {
    setModoForm('criar');
    setEventoSelecionado(null);
    setModalFormAberto(true);
  };

  const abrirEdicao = () => {
    setModoForm('editar');
    setModalFormAberto(true);
  };

  // Funções de submissão repassadas para o Modal
  const salvarEventoLocal = async (dadosForm: any) => {
    setLoadingAcao(true);
    try {
      const formatarDataParaInstant = (data: string) => {
        if (!data) return data;
        if (data.length === 16) return `${data}:00Z`;
        if (data.length === 19) return `${data}Z`;
        return data;
      };

      // Monta o payload base com as datas corrigidas
      const payload: any = {
        ...dadosForm,
        dataInicio: formatarDataParaInstant(dadosForm.dataInicio), 
        dataFim: formatarDataParaInstant(dadosForm.dataFim),
      };
      
      // Só envia as unidades pro backend se o usuário tiver selecionado alguma
      if (dadosForm.unidadeCodigo && dadosForm.unidadeCodigo !== '') {
        payload.unidadesCodigos = [dadosForm.unidadeCodigo];
      }
      
      // Limpa a sujeira do form antes de mandar pro Java
      delete payload.unidadeCodigo;
      
      if (modoForm === 'criar') {
        await eventoService.criarLocal(payload);
      } else if (eventoSelecionado) {
        const atualizado = await eventoService.atualizarLocal(eventoSelecionado.id, payload);
        setEventoSelecionado(atualizado);
      }
      
      setModalFormAberto(false);
      carregarEventos();
    } catch (error: any) {
      console.error("ERRO COMPLETO:", error.response?.data);
      alert(`Erro ao salvar: ${error.response?.data?.message || 'Verifique o console do navegador.'}`);
    } finally {
      setLoadingAcao(false);
    }
  };

  const importarVTools = async (vtoolsId: string, unidadeCodigo: string) => {
    setLoadingAcao(true);
    try {
      await eventoService.importarVTools(vtoolsId, unidadeCodigo);
      setModalFormAberto(false);
      carregarEventos();
    } catch (error) {
      alert("Erro ao importar do vTools. Verifique o ID.");
    } finally {
      setLoadingAcao(false);
    }
  };

  const deletarEvento = async () => {
    if (!eventoSelecionado || !confirm("Tem certeza que deseja apagar este evento?")) return;
    try {
      await eventoService.deletar(eventoSelecionado.id);
      setPainelAberto(false);
      carregarEventos();
    } catch (error) {
      alert("Erro ao apagar evento.");
    }
  };

  return (
    <MainLayout titulo="Gestão de Eventos">
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-gray-500 font-medium">Próximas atividades e histórico do Ramo</h2>
        {temPermissaoEdicao && (
          <Button onClick={abrirCriacao} className="bg-[#0F81CA] hover:bg-[#0c6ba8] text-white rounded-sm font-medium">
            <Plus size={16} className="mr-2" /> Criar Evento
          </Button>
        )}
      </div>

      {loading ? (
        <div className="text-gray-400 animate-pulse font-medium">A carregar o calendário...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {eventos.map((ev) => (
            <div 
              key={ev.id} 
              onClick={() => { setEventoSelecionado(ev); setPainelAberto(true); }}
              className="bg-white border border-gray-200 shadow-sm rounded-sm p-6 hover:border-[#0F81CA] hover:shadow-md transition-all cursor-pointer group relative overflow-hidden"
            >
              <div className="absolute left-0 top-0 bottom-0 w-1 bg-[#0F81CA] opacity-0 group-hover:opacity-100 transition-opacity"></div>
              
              <div className="flex justify-between items-start mb-4">
                <span className="bg-[#9DCDE6]/30 text-[#0F81CA] border border-[#9DCDE6] text-xs font-semibold px-2.5 py-1 rounded-sm">
                  {ev.categoria}
                </span>
                {ev.statusSincronizacao === 'SINCRONIZADO' ? (
                  <span className="text-emerald-700 flex items-center gap-1 text-xs font-medium bg-emerald-50 px-2 py-1 rounded-sm border border-emerald-200">
                    <Activity size={12} /> VTools
                  </span>
                ) : (
                  <span className="text-gray-500 flex items-center gap-1 text-xs font-medium bg-gray-50 px-2 py-1 rounded-sm border border-gray-200">
                    Local
                  </span>
                )}
              </div>
              <h3 className="text-lg font-bold text-gray-900 mb-2 group-hover:text-[#0F81CA] transition-colors line-clamp-2">
                {ev.titulo}
              </h3>
              <div className="space-y-2 mt-4">
                <div className="flex items-center gap-2 text-sm text-gray-600">
                  <Calendar size={16} className="text-[#0F81CA]" />
                  <span>{new Date(ev.dataInicio).toLocaleDateString('pt-BR')}</span>
                </div>
                <div className="flex items-center gap-2 text-sm text-gray-600">
                  <MapPin size={16} className="text-[#ED7630]" />
                  <span>{ev.locationType}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Componentes extraídos */}
      <EventoPainel 
        evento={eventoSelecionado} 
        isOpen={painelAberto} 
        onClose={setPainelAberto}
        temPermissaoEdicao={temPermissaoEdicao}
        onEdit={abrirEdicao}
        onDelete={deletarEvento}
      />

      <EventoFormModal 
        isOpen={modalFormAberto}
        onClose={setModalFormAberto}
        modo={modoForm}
        eventoEdicao={eventoSelecionado}
        loading={loadingAcao}
        onSalvarLocal={salvarEventoLocal}
        onImportarVTools={importarVTools}
      />
    </MainLayout>
  );
}