// src/pages/Eventos.tsx
import { useEffect, useState } from 'react';
import { Calendar, MapPin, Clock, DollarSign, X, Activity, Users } from 'lucide-react';
import { MainLayout } from '../layouts/MainLayout';
import { eventoService } from '../services/api';

export function Eventos() {
  const [eventos, setEventos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  
  const [painelAberto, setPainelAberto] = useState(false);
  const [eventoSelecionado, setEventoSelecionado] = useState<any | null>(null);

  useEffect(() => {
    eventoService.listarTodos()
      .then((dados) => {
        setEventos(dados);
        setLoading(false);
      })
      .catch((erro) => {
        console.error("Erro ao carregar eventos:", erro);
        setLoading(false);
      });
  }, []);

  const abrirPainel = async (evento: any) => {
    setEventoSelecionado(evento);
    setPainelAberto(true);
  };

  return (
    <MainLayout titulo="Gestão de Eventos">
      
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-gray-500 font-medium">Próximas atividades e histórico do Ramo</h2>
        <button className="bg-[#0F81CA] hover:bg-[#0c6ba8] text-white px-4 py-2 rounded-sm font-medium text-sm transition-colors shadow-sm">
          + Criar Evento
        </button>
      </div>

      {loading ? (
        <div className="text-gray-400 animate-pulse font-medium">A carregar o calendário...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {eventos.map((ev) => (
            <div 
              key={ev.id} 
              onClick={() => abrirPainel(ev)}
              className="bg-white border border-gray-200 shadow-sm rounded-sm p-6 hover:border-[#0F81CA] hover:shadow-md transition-all cursor-pointer group relative overflow-hidden"
            >
              {/* Barra lateral interativa */}
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
              
              <h3 className="text-lg font-bold text-gray-900 mb-2 group-hover:text-[#0F81CA] transition-colors">
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
          
          {eventos.length === 0 && (
            <div className="col-span-full text-center py-12 text-gray-400 bg-white border border-dashed border-[#C6EBFF] rounded-sm">
              Nenhum evento registado. Prepare o seu primeiro evento (ex: o TechX Natal26)!
            </div>
          )}
        </div>
      )}

      {/* PAINEL LATERAL (Light Mode) */}
      <div 
        className={`fixed inset-y-0 right-0 w-full md:w-[500px] bg-white border-l border-[#C6EBFF] shadow-2xl transform transition-transform duration-300 ease-in-out z-50 flex flex-col
        ${painelAberto ? 'translate-x-0' : 'translate-x-full'}`}
      >
        <div className="p-6 border-b border-[#C6EBFF] flex justify-between items-center bg-[#F9FCFF]">
          <h2 className="text-lg font-bold text-[#0F81CA] truncate pr-4">{eventoSelecionado?.titulo}</h2>
          <button 
            onClick={() => setPainelAberto(false)}
            className="p-2 hover:bg-[#C6EBFF]/50 rounded-full text-gray-400 hover:text-[#0F81CA] transition-colors flex-shrink-0"
          >
            <X size={20} />
          </button>
        </div>

        {eventoSelecionado && (
          <div className="flex-1 overflow-y-auto p-6 space-y-6 bg-white">
            
            <p className="text-gray-700 text-sm leading-relaxed bg-gray-50 p-4 rounded-sm border border-gray-100">
              {eventoSelecionado.descricao}
            </p>

            <div className="grid grid-cols-2 gap-4">
              <div className="bg-white border border-[#C6EBFF] p-4 rounded-sm shadow-sm">
                <div className="flex items-center gap-2 text-gray-500 mb-2">
                  <Users size={16} className="text-[#0F81CA]" />
                  <span className="text-xs font-semibold uppercase">Público Esperado</span>
                </div>
                <div className="text-2xl font-bold text-gray-900">
                  {eventoSelecionado.qtdMembros + eventoSelecionado.qtdNaoMembros} <span className="text-sm font-normal text-gray-500">pessoas</span>
                </div>
              </div>

              <div className="bg-white border border-[#C6EBFF] p-4 rounded-sm shadow-sm">
                <div className="flex items-center gap-2 text-gray-500 mb-2">
                  <DollarSign size={16} className="text-emerald-600" />
                  <span className="text-xs font-semibold uppercase">Orçamento</span>
                </div>
                <div className="text-2xl font-bold text-emerald-700">
                  R$ {eventoSelecionado.orcamentoEstimado.toFixed(2)}
                </div>
              </div>
            </div>

            <div className="pt-6 border-t border-gray-100 space-y-3">
              <h3 className="text-sm font-bold text-gray-900 mb-4">Ações Operacionais</h3>
              
              <button className="w-full flex items-center justify-between bg-white hover:bg-[#F4FAFF] p-4 rounded-sm border border-gray-200 hover:border-[#C6EBFF] transition-colors group">
                <div className="flex items-center gap-3">
                  <Clock size={20} className="text-[#0F81CA]" />
                  <span className="font-medium text-sm text-gray-700 group-hover:text-[#0F81CA]">Cronograma e Sessões</span>
                </div>
                <span className="text-gray-400 text-xs group-hover:text-[#0F81CA]">&rarr;</span>
              </button>

              <button className="w-full flex items-center justify-between bg-white hover:bg-[#F4FAFF] p-4 rounded-sm border border-gray-200 hover:border-[#C6EBFF] transition-colors group">
                <div className="flex items-center gap-3">
                  <DollarSign size={20} className="text-emerald-600" />
                  <span className="font-medium text-sm text-gray-700 group-hover:text-emerald-700">Itens de Orçamento</span>
                </div>
                <span className="text-gray-400 text-xs group-hover:text-emerald-600">&rarr;</span>
              </button>
            </div>
          </div>
        )}
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