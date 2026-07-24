import { Users, DollarSign, Trash2, Edit } from 'lucide-react';
import { Sheet, SheetContent, SheetHeader, SheetTitle } from '@/components/ui/sheet';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Button } from '@/components/ui/button';
import type { Evento } from '@/types/evento.types';

interface EventoPainelProps {
  evento: Evento | null;
  isOpen: boolean;
  onClose: (open: boolean) => void;
  temPermissaoEdicao: boolean;
  onEdit: () => void;
  onDelete: () => void;
}

export function EventoPainel({ evento, isOpen, onClose, temPermissaoEdicao, onEdit, onDelete }: EventoPainelProps) {
  if (!evento) return null;

  return (
    <Sheet open={isOpen} onOpenChange={onClose}>
      <SheetContent className="w-[400px] sm:w-[540px] p-0 flex flex-col border-l border-[#C6EBFF]">
        <SheetHeader className="p-6 pb-4 border-b border-[#C6EBFF] bg-[#F9FCFF]">
          <div className="flex justify-between items-start pr-8">
            <SheetTitle className="text-[#0F81CA] text-xl text-left leading-tight">
              {evento.titulo}
            </SheetTitle>
          </div>
          
          {evento.statusSincronizacao === 'LOCAL_APENAS' && temPermissaoEdicao && (
            <div className="flex gap-2 mt-4">
              <Button variant="outline" size="sm" onClick={onEdit} className="h-8 text-gray-600 hover:text-[#0F81CA]">
                <Edit size={14} className="mr-1"/> Editar
              </Button>
              <Button variant="outline" size="sm" onClick={onDelete} className="h-8 text-red-600 border-red-200 hover:bg-red-50">
                <Trash2 size={14} className="mr-1"/> Excluir
              </Button>
            </div>
          )}
        </SheetHeader>
        
        <Tabs defaultValue="detalhes" className="flex-1 flex flex-col overflow-hidden">
          <div className="px-6 pt-4 border-b border-gray-100">
            <TabsList className="grid w-full grid-cols-3">
              <TabsTrigger value="detalhes">Detalhes</TabsTrigger>
              <TabsTrigger value="sessoes">Sessões</TabsTrigger>
              <TabsTrigger value="orcamento">Orçamento</TabsTrigger>
            </TabsList>
          </div>
          
          <div className="flex-1 overflow-y-auto p-6">
            <TabsContent value="detalhes" className="space-y-6 mt-0">
              <div className="text-gray-700 text-sm leading-relaxed bg-gray-50 p-4 rounded-sm border border-gray-100 prose prose-sm max-w-none" dangerouslySetInnerHTML={{ __html: evento.descricao }} />
              
              <div className="grid grid-cols-2 gap-4">
                <div className="bg-white border border-[#C6EBFF] p-4 rounded-sm shadow-sm">
                  <div className="flex items-center gap-2 text-gray-500 mb-2">
                    <Users size={16} className="text-[#0F81CA]" />
                    <span className="text-xs font-semibold uppercase">Público Total</span>
                  </div>
                  <div className="text-2xl font-bold text-gray-900">
                    {evento.qtdMembros + evento.qtdNaoMembros}
                  </div>
                  <div className="text-xs text-gray-400 mt-1">
                    ({evento.qtdMembros} IEEE / {evento.qtdNaoMembros} Convidados)
                  </div>
                </div>

                <div className="bg-white border border-[#C6EBFF] p-4 rounded-sm shadow-sm">
                  <div className="flex items-center gap-2 text-gray-500 mb-2">
                    <DollarSign size={16} className="text-emerald-600" />
                    <span className="text-xs font-semibold uppercase">Orçamento</span>
                  </div>
                  <div className="text-2xl font-bold text-emerald-700">
                    R$ {evento.orcamentoEstimado.toFixed(2)}
                  </div>
                </div>
              </div>
            </TabsContent>

            <TabsContent value="sessoes" className="mt-0">
              {/* Sessões virão aqui futuramente */}
            </TabsContent>

            <TabsContent value="orcamento" className="mt-0">
              {/* Financeiro virá aqui futuramente */}
            </TabsContent>
          </div>
        </Tabs>
      </SheetContent>
    </Sheet>
  );
}