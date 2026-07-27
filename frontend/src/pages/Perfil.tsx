import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { MainLayout } from '@/layouts/MainLayout';
import { voluntarioService } from '@/api/voluntarios.api';
import type { VoluntarioPerfil } from '@/types/voluntario.types';
import {
    UserRound,
    Mail,
    ShieldCheck,
    IdCard,
    ArrowLeft,
    GraduationCap,
    Building2,
    Layers,
    Calendar,
    Award,
    Hash,
} from 'lucide-react';

export function Perfil() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const [perfil, setPerfil] = useState<VoluntarioPerfil | null>(null);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState('');

    const visualizandoProprioPerfil = id === undefined;

    useEffect(() => {
        async function carregarPerfil() {
            setCarregando(true);
            setErro('');
            try {
                const dados = visualizandoProprioPerfil
                    ? await voluntarioService.obterMeuPerfil()
                    : await voluntarioService.obterPerfilPorId(Number(id));
                setPerfil(dados);
            } catch {
                setErro('Não foi possível carregar este perfil.');
            } finally {
                setCarregando(false);
            }
        }

        carregarPerfil();
    }, [id, visualizandoProprioPerfil]);

    // Função para extrair as iniciais (Ex: Sebastião Lopes -> SL)
    const obterIniciais = (primeiroNome: string, ultimoNome: string) => {
        const i1 = primeiroNome?.[0] || '';
        const i2 = ultimoNome?.[0] || '';
        return `${i1}${i2}`.toUpperCase();
    };

    return (
        <MainLayout>
            <div className="max-w-4xl mx-auto px-6 py-10">
                {!visualizandoProprioPerfil && (
                    <button
                        onClick={() => navigate(-1)}
                        className="flex items-center gap-1.5 text-sm text-brand-primary hover:underline mb-6 font-medium"
                    >
                        <ArrowLeft size={16} /> Voltar para a lista
                    </button>
                )}

                {carregando && (
                    <div className="flex justify-center items-center py-20 text-gray-500 font-medium">
                        Carregando informações do perfil...
                    </div>
                )}

                {erro && (
                    <div className="text-red-700 text-sm font-medium bg-red-50 border border-red-200 py-3 px-4 rounded-md">
                        {erro}
                    </div>
                )}

                {perfil && (
                    <div className="space-y-6">
                        {/* CABEÇALHO DO PERFIL COM AVATAR DE INICIAIS */}
                        <div className="bg-white border border-gray-200 rounded-md p-6 shadow-sm flex flex-col md:flex-row items-center gap-6">
                            {/* Avatar com Iniciais */}
                            <div className="w-20 h-20 rounded-full bg-brand-primary text-white flex items-center justify-center font-bold text-2xl shadow-md border-2 border-brand-secondary/40 shrink-0">
                                {obterIniciais(
                                    perfil.primeiroNome,
                                    perfil.ultimoNome,
                                )}
                            </div>

                            {/* Detalhes do Título */}
                            <div className="text-center md:text-left flex-1">
                                <div className="flex flex-col md:flex-row md:items-center gap-2 mb-1">
                                    <h1 className="text-2xl md:text-3xl font-extrabold text-gray-900">
                                        {perfil.primeiroNome}{' '}
                                        {perfil.ultimoNome}
                                    </h1>
                                    <span className="self-center md:self-auto px-3 py-0.5 text-xs font-semibold rounded-full bg-brand-secondary/40 text-brand-primary border border-brand-primary/20 uppercase tracking-wider">
                                        {perfil.tipoUsuario}
                                    </span>
                                </div>
                                <p className="text-gray-500 text-sm">
                                    {visualizandoProprioPerfil
                                        ? 'Sua conta e permissões registradas no CoordIEEEna.'
                                        : 'Informações detalhadas de outro voluntário.'}
                                </p>
                            </div>
                        </div>

                        {/* SEÇÃO 1: DADOS PESSOAIS & ACADÊMICOS */}
                        <div className="bg-white border border-gray-200 rounded-md shadow-sm p-6">
                            <h2 className="text-base font-bold text-gray-900 border-b border-gray-100 pb-3 mb-5 flex items-center gap-2">
                                <UserRound
                                    size={18}
                                    className="text-brand-primary"
                                />
                                Dados Pessoais & Acadêmicos
                            </h2>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <InfoLinha
                                    icon={<UserRound size={18} />}
                                    label="Nome Completo"
                                    valor={`${perfil.primeiroNome} ${perfil.ultimoNome}`}
                                />
                                <InfoLinha
                                    icon={<Mail size={18} />}
                                    label="E-mail Pessoal"
                                    valor={perfil.emailPessoal}
                                />

                                {/* Exibição condicional dos dados de vínculo acadêmico */}
                                {perfil.vinculos &&
                                perfil.vinculos.length > 0 ? (
                                    <>
                                        <InfoLinha
                                            icon={<Building2 size={18} />}
                                            label="Instituição de Ensino"
                                            valor={
                                                perfil.vinculos[0]
                                                    .instituicaoNome
                                            }
                                        />
                                        <InfoLinha
                                            icon={<GraduationCap size={18} />}
                                            label="Curso"
                                            valor={perfil.vinculos[0].cursoNome}
                                        />
                                        <InfoLinha
                                            icon={<Hash size={18} />}
                                            label="Matrícula"
                                            valor={
                                                perfil.vinculos[0].numMatricula
                                            }
                                        />
                                        <InfoLinha
                                            icon={<Mail size={18} />}
                                            label="E-mail Acadêmico"
                                            valor={
                                                perfil.vinculos[0]
                                                    .emailAcademico
                                            }
                                        />
                                    </>
                                ) : (
                                    <div className="md:col-span-2 p-3 bg-gray-50 rounded border border-gray-200 text-xs text-gray-500 italic">
                                        Nenhum vínculo acadêmico específico
                                        detalhado no cadastro.
                                    </div>
                                )}
                            </div>
                        </div>

                        {/* SEÇÃO 2: VÍNCULO INSTITUCIONAL & UNIDADES */}
                        <div className="bg-white border border-gray-200 rounded-md shadow-sm p-6">
                            <h2 className="text-base font-bold text-gray-900 border-b border-gray-100 pb-3 mb-5 flex items-center gap-2">
                                <Layers
                                    size={18}
                                    className="text-brand-primary"
                                />
                                Vínculo Institucional & Unidades
                            </h2>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <InfoLinha
                                    icon={<Building2 size={18} />}
                                    label="Ramo Estudantil"
                                    valor={
                                        perfil.ramo ||
                                        'Ramo Estudantil IEEE UFRN'
                                    }
                                />
                                <InfoLinha
                                    icon={<ShieldCheck size={18} />}
                                    label="Perfil de Acesso (RBAC)"
                                    valor={perfil.tipoUsuario}
                                />
                                <div className="md:col-span-2">
                                    <p className="text-xs text-gray-500 font-medium uppercase tracking-wide mb-2">
                                        Capítulos e Grupos de Afinidade
                                        Vinculados
                                    </p>
                                    <div className="flex flex-wrap gap-2">
                                        {perfil.capitulos &&
                                        perfil.capitulos.length > 0 ? (
                                            perfil.capitulos.map((cap, idx) => (
                                                <span
                                                    key={idx}
                                                    className="px-3 py-1 bg-gray-100 text-gray-800 text-xs font-semibold rounded-md border border-gray-200"
                                                >
                                                    {cap}
                                                </span>
                                            ))
                                        ) : (
                                            <span className="text-sm text-gray-500 italic">
                                                Nenhum Capítulo Técnico/Grupo de
                                                Afinidade vinculado no momento.
                                            </span>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* SEÇÃO 3: MEMBRESIA IEEE */}
                        <div className="bg-white border border-gray-200 rounded-md shadow-sm p-6">
                            <h2 className="text-base font-bold text-gray-900 border-b border-gray-100 pb-3 mb-5 flex items-center gap-2">
                                <IdCard
                                    size={18}
                                    className="text-brand-primary"
                                />
                                Membresia IEEE
                            </h2>
                            {perfil.numeroMembresia ? (
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <InfoLinha
                                        icon={<IdCard size={18} />}
                                        label="Número de Membresia IEEE"
                                        valor={perfil.numeroMembresia}
                                    />
                                    <InfoLinha
                                        icon={<Award size={18} />}
                                        label="Tipo de Membresia"
                                        valor={
                                            perfil.tipoMembresia ||
                                            'Estudantil (Student Member)'
                                        }
                                    />
                                    {perfil.emailIeee && (
                                        <InfoLinha
                                            icon={<Mail size={18} />}
                                            label="E-mail Institucional IEEE"
                                            valor={perfil.emailIeee}
                                        />
                                    )}
                                </div>
                            ) : (
                                <div className="p-4 bg-gray-50 rounded-md border border-gray-200 text-center md:text-left">
                                    <p className="text-sm text-gray-600">
                                        Este voluntário não possui registro de
                                        membresia ativa do IEEE vinculada ao
                                        sistema.
                                    </p>
                                </div>
                            )}
                        </div>

                        {/* SEÇÃO 4: HISTÓRICO DE MANDATOS */}
                        <div className="bg-white border border-gray-200 rounded-md shadow-sm p-6">
                            <h2 className="text-base font-bold text-gray-900 border-b border-gray-100 pb-3 mb-5 flex items-center gap-2">
                                <Calendar
                                    size={18}
                                    className="text-brand-primary"
                                />
                                Histórico de Mandatos & Cargos
                            </h2>

                            {perfil.historicoMandatos &&
                            perfil.historicoMandatos.length > 0 ? (
                                <div className="relative border-l-2 border-brand-secondary/60 ml-3 space-y-6 my-2">
                                    {perfil.historicoMandatos.map((mandato) => (
                                        <div
                                            key={mandato.id}
                                            className="relative pl-6"
                                        >
                                            {/* Indicador do Timeline */}
                                            <div
                                                className={`absolute -left-2.25 top-1 w-4 h-4 rounded-full border-2 bg-white ${
                                                    mandato.ativo
                                                        ? 'border-emerald-600 bg-emerald-600'
                                                        : 'border-brand-primary'
                                                }`}
                                            />
                                            <div className="flex flex-col md:flex-row md:items-center justify-between gap-1">
                                                <span className="font-bold text-gray-900 text-sm">
                                                    {mandato.nomeCargo}
                                                </span>
                                                <span className="text-xs text-gray-500 flex items-center gap-2">
                                                    {mandato.dataInicio} —{' '}
                                                    {mandato.dataFim}
                                                    {mandato.ativo && (
                                                        <span className="px-2 py-0.5 rounded text-[10px] bg-emerald-100 text-emerald-800 font-bold uppercase">
                                                            Vigente
                                                        </span>
                                                    )}
                                                </span>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            ) : (
                                <p className="text-sm text-gray-500 italic">
                                    Nenhum histórico de mandatos/cargos
                                    registrado até o momento.
                                </p>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </MainLayout>
    );
}

function InfoLinha({
    icon,
    label,
    valor,
}: {
    icon: React.ReactNode;
    label: string;
    valor: string;
}) {
    return (
        <div className="flex items-start gap-3">
            <div className="p-2 rounded-md bg-brand-secondary/30 text-brand-primary shrink-0 mt-0.5">
                {icon}
            </div>
            <div>
                <p className="text-xs text-gray-500 font-medium uppercase tracking-wide">
                    {label}
                </p>
                <p className="text-sm font-semibold text-gray-900 mt-0.5">
                    {valor}
                </p>
            </div>
        </div>
    );
}
