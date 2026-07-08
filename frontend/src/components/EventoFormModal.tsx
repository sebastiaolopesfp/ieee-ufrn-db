import { useState, useEffect } from 'react';
import { DownloadCloud } from 'lucide-react';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from './ui/dialog';
import { Tabs, TabsContent, TabsList, TabsTrigger } from './ui/tabs';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Button } from './ui/button';
import { type Evento, eventoService, unidadeService } from './../services/api';

interface EventoFormModalProps {
  isOpen: boolean;
  onClose: (open: boolean) => void;
  modo: 'criar' | 'editar';
  eventoEdicao?: Evento | null;
  loading: boolean;
  onSalvarLocal: (dados: any) => void;
  onImportarVTools: (vtoolsId: string, unidadeCodigo: string) => void;
}

export function EventoFormModal({ isOpen, onClose, modo, eventoEdicao, loading, onSalvarLocal, onImportarVTools }: EventoFormModalProps) {
  const [vtoolsIdInput, setVtoolsIdInput] = useState('');
  const [vtoolsUnidade, setVtoolsUnidade] = useState('');
  
  // Estados dos dicionários vindos do Back-end
  const [categorias, setCategorias] = useState<Record<string, string>>({});
  const [subcategorias, setSubcategorias] = useState<Record<string, string>>({});
  const [unidades, setUnidades] = useState<any[]>([]);
  
  const defaultForm = {
    titulo: '',
    descricao: '',
    dataInicio: '',
    dataFim: '',
    locationType: 'PHYSICAL',
    categoria: '',
    subcategoria: '',
    qtdMembros: 0,
    qtdNaoMembros: 0,
    orcamentoEstimado: 0,
    unidadeCodigo: ''
  };

  const [formData, setFormData] = useState(defaultForm);

  // Carrega os dados dos Selects dinamicamente do banco e do CategoryMapper
  useEffect(() => {
    if (isOpen) {
      eventoService.listarCategorias().then(setCategorias).catch(console.error);
      eventoService.listarSubcategorias().then(setSubcategorias).catch(console.error);
      unidadeService.listarTodas().then(setUnidades).catch(console.error);
    }
  }, [isOpen]);

  // Monitora se é criação ou edição
  useEffect(() => {
    if (modo === 'editar' && eventoEdicao) {
      const formatDataInput = (isoString: string) => {
        if (!isoString) return '';
        const d = new Date(isoString);
        return new Date(d.getTime() - d.getTimezoneOffset() * 60000).toISOString().slice(0, 16);
      };

      setFormData({
        titulo: eventoEdicao.titulo,
        descricao: eventoEdicao.descricao,
        dataInicio: formatDataInput(eventoEdicao.dataInicio),
        dataFim: formatDataInput(eventoEdicao.dataFim),
        locationType: eventoEdicao.locationType,
        categoria: eventoEdicao.categoria,
        subcategoria: eventoEdicao.subcategoria || '',
        qtdMembros: eventoEdicao.qtdMembros,
        qtdNaoMembros: eventoEdicao.qtdNaoMembros,
        orcamentoEstimado: eventoEdicao.orcamentoEstimado,
        unidadeCodigo: ''
      });
    } else {
      setFormData(defaultForm);
      setVtoolsIdInput('');
      setVtoolsUnidade('');
    }
  }, [modo, eventoEdicao, isOpen]);

  const handleSubmitLocal = (e: React.FormEvent) => {
    e.preventDefault();
    onSalvarLocal(formData);
  };

  const handleSubmitVTools = (e: React.FormEvent) => {
    e.preventDefault();
    onImportarVTools(vtoolsIdInput, vtoolsUnidade);
  };

  const inputClass = "flex h-10 w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50";

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[600px] max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{modo === 'criar' ? 'Registrar Novo Evento' : 'Editar Detalhes do Evento'}</DialogTitle>
        </DialogHeader>
        
        {modo === 'criar' ? (
          <Tabs defaultValue="vtools" className="mt-4">
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="vtools">Importar do vTools</TabsTrigger>
              <TabsTrigger value="local">Evento Local</TabsTrigger>
            </TabsList>
            
            <TabsContent value="vtools" className="space-y-4 pt-4">
              <form onSubmit={handleSubmitVTools} className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="vtoolsId">ID do Evento no vTools</Label>
                  <Input 
                    id="vtoolsId" placeholder="Ex: 483968" value={vtoolsIdInput}
                    onChange={(e) => setVtoolsIdInput(e.target.value)} required
                  />
                </div>
                <div className="space-y-2">
                  <Label>Unidade Organizacional Vinculada</Label>
                  <select 
                    className={inputClass} required value={vtoolsUnidade} 
                    onChange={e => setVtoolsUnidade(e.target.value)}
                  >
                    <option value="" disabled>Selecione a Unidade</option>
                    {unidades.map((u: any) => (
                      <option key={u.unidadeCodigo} value={u.unidadeCodigo}>{u.nome}</option>
                    ))}
                  </select>
                </div>
                <Button type="submit" className="w-full bg-[#0F81CA]" disabled={loading}>
                  {loading ? 'Importando...' : <><DownloadCloud size={16} className="mr-2"/> Importar</>}
                </Button>
              </form>
            </TabsContent>
            
            <TabsContent value="local" className="pt-4">
               <FormularioLocal 
                  formData={formData} setFormData={setFormData} onSubmit={handleSubmitLocal} 
                  loading={loading} inputClass={inputClass} unidades={unidades} 
                  categorias={categorias} subcategories={subcategorias} isEditing={false}
               />
            </TabsContent>
          </Tabs>
        ) : (
          <div className="pt-4">
             <FormularioLocal 
                formData={formData} setFormData={setFormData} onSubmit={handleSubmitLocal} 
                loading={loading} inputClass={inputClass} unidades={unidades} 
                categorias={categorias} subcategories={subcategorias} isEditing={true}
             />
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
}

// Subcomponente interno de Formulário para evitar duplicação de HTML
function FormularioLocal({ formData, setFormData, onSubmit, loading, inputClass, unidades, categorias, subcategories, isEditing }: any) {
  return (
    <form onSubmit={onSubmit} className="space-y-4">
      <div className="space-y-2">
        <Label>Título do Evento</Label>
        <Input required value={formData.titulo} onChange={e => setFormData({...formData, titulo: e.target.value})} />
      </div>
      
      <div className="space-y-2">
        <Label>Descrição</Label>
        <textarea 
          className="flex w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm shadow-sm focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring"
          required rows={3} value={formData.descricao} onChange={e => setFormData({...formData, descricao: e.target.value})} 
        />
      </div>
      
      {!isEditing && (
        <div className="space-y-2">
          <Label>Unidade Promotora</Label>
          <select 
            className={inputClass} required value={formData.unidadeCodigo} 
            onChange={e => setFormData({...formData, unidadeCodigo: e.target.value})}
          >
            <option value="" disabled>Selecione a Unidade</option>
            {unidades.map((u: any) => (
              <option key={u.unidadeCodigo} value={u.unidadeCodigo}>{u.nome}</option>
            ))}
          </select>
        </div>
      )}

      <div className="grid grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label>Data Início</Label>
          <Input type="datetime-local" required value={formData.dataInicio} onChange={e => setFormData({...formData, dataInicio: e.target.value})} />
        </div>
        <div className="space-y-2">
          <Label>Data Fim</Label>
          <Input type="datetime-local" required value={formData.dataFim} onChange={e => setFormData({...formData, dataFim: e.target.value})} />
        </div>
      </div>

      <div className="grid grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label>Categoria</Label>
          <select 
            className={inputClass} required value={formData.categoria} 
            onChange={e => setFormData({...formData, categoria: e.target.value})}
          >
            <option value="" disabled>Selecione a Categoria</option>
            {Object.entries(categorias).map(([id, nome]) => (
              <option key={id} value={nome as string}>{nome as string}</option>
            ))}
          </select>
        </div>
        
        <div className="space-y-2">
          <Label>Subcategoria</Label>
          <select 
            className={inputClass} value={formData.subcategoria} 
            onChange={e => setFormData({...formData, subcategoria: e.target.value})}
          >
            <option value="">Nenhuma Subcategoria</option>
            {Object.entries(subcategories).map(([id, nome]) => (
              <option key={id} value={nome as string}>{nome as string}</option>
            ))}
          </select>
        </div>
      </div>

      <div className="grid grid-cols-3 gap-4">
        <div className="space-y-2">
          <Label>Membros IEEE</Label>
          <Input type="number" min="0" required value={formData.qtdMembros} onChange={e => setFormData({...formData, qtdMembros: parseInt(e.target.value) || 0})} />
        </div>
        <div className="space-y-2">
          <Label>Convidados</Label>
          <Input type="number" min="0" required value={formData.qtdNaoMembros} onChange={e => setFormData({...formData, qtdNaoMembros: parseInt(e.target.value) || 0})} />
        </div>
        <div className="space-y-2">
          <Label>Orçamento (R$)</Label>
          <Input type="number" min="0" step="0.01" value={formData.orcamentoEstimado} onChange={e => setFormData({...formData, orcamentoEstimado: parseFloat(e.target.value) || 0})} />
        </div>
      </div>

      <Button type="submit" className="w-full bg-[#0F81CA] hover:bg-[#0c6ba8]" disabled={loading}>
        {loading ? 'Salvando...' : !isEditing ? 'Criar Evento' : 'Salvar Alterações'}
      </Button>
    </form>
  );
}