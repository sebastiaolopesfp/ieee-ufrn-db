import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { MainLayout } from '@/layouts/MainLayout';
import { voluntarioService } from '@/api/voluntarios.api';
import type { VoluntarioPerfil } from '@/types/voluntario.types';
import { obterIniciais } from '@/lib/utils';
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
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Button } from '@/components/ui/button';

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

    return (
        <MainLayout>
            <div className="max-w-4xl mx-auto px-6 py-10">
                {!visualizandoProprioPerfil && (
                    <Button
                        variant="ghost"
                        onClick={() => navigate(-1)}
                        className="mb-6 px-0 text-primary hover:text-primary"
                    >
                        <ArrowLeft size={16} /> Voltar para a lista
                    </Button>
                )}

                {carregando && <PerfilSkeleton />}

                {erro && (
                    <div className="text-red-700 text-sm font-medium bg-red-50 border border-red-200 py-3 px-4 rounded-md">
                        {erro}
                    </div>
                )}

                {perfil && (
                    <div className="space-y-6">
                        <Card>
                            <CardContent className="flex flex-col md:flex-row items-center gap-6">
                                <Avatar className="w-20 h-20 border-2 border-secondary/40 shadow-md">
                                    <AvatarFallback className="bg-primary text-white font-bold text-2xl">
                                        {obterIniciais(
                                            perfil.primeiroNome,
                                            perfil.ultimoNome,
                                        )}
                                    </AvatarFallback>
                                </Avatar>

                                <div className="text-center md:text-left flex-1">
                                    <div className="flex flex-col md:flex-row md:items-center gap-2 mb-1">
                                        <h1 className="text-2xl md:text-3xl font-extrabold text-gray-900">
                                            {perfil.primeiroNome}{' '}
                                            {perfil.ultimoNome}
                                        </h1>
                                        <Badge className="self-center md:self-auto bg-secondary/40 text-primary border border-primary/20 uppercase tracking-wider">
                                            {perfil.tipoUsuario}
                                        </Badge>
                                    </div>
                                    <p className="text-gray-500 text-sm">
                                        {visualizandoProprioPerfil
                                            ? 'Sua conta e permissões registradas no CoordIEEEna.'
                                            : 'Informações detalhadas de outro voluntário.'}
                                    </p>
                                </div>
                            </CardContent>
                        </Card>

                        <Card>
                            <CardHeader>
                                <CardTitle className="text-base flex items-center gap-2 border-b border-gray-100 pb-3">
                                    <UserRound
                                        size={18}
                                        className="text-primary"
                                    />
                                    Dados Pessoais & Acadêmicos
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
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
                                                icon={
                                                    <GraduationCap size={18} />
                                                }
                                                label="Curso"
                                                valor={
                                                    perfil.vinculos[0].cursoNome
                                                }
                                            />
                                            <InfoLinha
                                                icon={<Hash size={18} />}
                                                label="Matrícula"
                                                valor={
                                                    perfil.vinculos[0]
                                                        .numMatricula
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
                            </CardContent>
                        </Card>

                        <Card>
                            <CardHeader>
                                <CardTitle className="text-base flex items-center gap-2 border-b border-gray-100 pb-3">
                                    <Layers
                                        size={18}
                                        className="text-primary"
                                    />
                                    Vínculo Institucional & Unidades
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <InfoLinha
                                        icon={<Building2 size={18} />}
                                        label="Ramo Estudantil"
                                        valor="Ramo Estudantil IEEE UFRN"
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
                                                perfil.capitulos.map((cap) => (
                                                    <Badge
                                                        key={cap}
                                                        variant="outline"
                                                        className="bg-gray-50"
                                                    >
                                                        {cap}
                                                    </Badge>
                                                ))
                                            ) : (
                                                <span className="text-sm text-gray-500 italic">
                                                    Nenhum Capítulo
                                                    Técnico/Grupo de Afinidade
                                                    vinculado no momento.
                                                </span>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        <Card>
                            <CardHeader>
                                <CardTitle className="text-base flex items-center gap-2 border-b border-gray-100 pb-3">
                                    <IdCard
                                        size={18}
                                        className="text-primary"
                                    />
                                    Membresia IEEE
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
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
                                            Este voluntário não possui registro
                                            de membresia ativa do IEEE vinculada
                                            ao sistema.
                                        </p>
                                    </div>
                                )}
                            </CardContent>
                        </Card>

                        <Card>
                            <CardHeader>
                                <CardTitle className="text-base flex items-center gap-2 border-b border-gray-100 pb-3">
                                    <Calendar
                                        size={18}
                                        className="text-primary"
                                    />
                                    Histórico de Mandatos & Cargos
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                {perfil.historicoMandatos &&
                                perfil.historicoMandatos.length > 0 ? (
                                    <div className="relative border-l-2 border-secondary/60 ml-3 space-y-6 my-2">
                                        {perfil.historicoMandatos.map(
                                            (mandato) => (
                                                <div
                                                    key={mandato.id}
                                                    className="relative pl-6"
                                                >
                                                    <div
                                                        className={`absolute -left-2.25 top-1 w-4 h-4 rounded-full border-2 bg-white ${
                                                            mandato.ativo
                                                                ? 'border-emerald-600 bg-emerald-600'
                                                                : 'border-primary'
                                                        }`}
                                                    />
                                                    <div className="flex flex-col md:flex-row md:items-center justify-between gap-1">
                                                        <span className="font-bold text-gray-900 text-sm">
                                                            {mandato.nomeCargo}
                                                        </span>
                                                        <span className="text-xs text-gray-500 flex items-center gap-2">
                                                            {mandato.dataInicio}{' '}
                                                            — {mandato.dataFim}
                                                            {mandato.ativo && (
                                                                <Badge className="bg-emerald-100 text-emerald-800 text-[10px] uppercase">
                                                                    Vigente
                                                                </Badge>
                                                            )}
                                                        </span>
                                                    </div>
                                                </div>
                                            ),
                                        )}
                                    </div>
                                ) : (
                                    <p className="text-sm text-gray-500 italic">
                                        Nenhum histórico de mandatos/cargos
                                        registrado até o momento.
                                    </p>
                                )}
                            </CardContent>
                        </Card>
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
            <div className="p-2 rounded-md bg-secondary/30 text-primary shrink-0 mt-0.5">
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

function PerfilSkeleton() {
    return (
        <div className="space-y-6">
            <Card>
                <CardContent className="flex items-center gap-6">
                    <Skeleton className="w-20 h-20 rounded-full" />
                    <div className="space-y-2 flex-1">
                        <Skeleton className="h-6 w-48" />
                        <Skeleton className="h-4 w-64" />
                    </div>
                </CardContent>
            </Card>
            <Card>
                <CardContent className="space-y-3 pt-6">
                    <Skeleton className="h-4 w-full" />
                    <Skeleton className="h-4 w-3/4" />
                </CardContent>
            </Card>
        </div>
    );
}
