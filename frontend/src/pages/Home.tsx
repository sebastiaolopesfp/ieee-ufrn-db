import { MainLayout } from '../layouts/MainLayout';
import { Link } from 'react-router-dom';
import {
    ExternalLink,
    ShieldCheck,
    Users,
    Layers,
    LogIn,
    Trophy,
    Coins,
    HeartHandshake,
    FolderKanban,
    ClipboardCheck,
    FileText,
    Code2,
    Globe,
    Server,
    BookOpen,
    Database,
} from 'lucide-react';
import { FaLinkedin, FaGithub } from 'react-icons/fa';
import heroBg from '../assets/hero.jpeg';
import { useAuth } from '@/contexts/AuthContext';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

export function Home() {
    const apiBaseUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';
    const { isAuthenticated } = useAuth();

    const desenvolvedores = [
        {
            nome: 'Liriel Felix',
            funcao: 'Idealizadora e Desenvolvedora Fullstack',
            github: 'https://github.com',
            linkedin: 'https://linkedin.com',
        },
        {
            nome: 'Natham Fernandes',
            funcao: 'Idealizador e Desenvolvedor Fullstack',
            github: 'https://github.com',
            linkedin: 'https://linkedin.com',
        },
        {
            nome: 'Sebastião Lopes',
            funcao: 'Idealizador e Desenvolvedor Fullstack',
            github: 'https://github.com',
            linkedin: 'https://linkedin.com',
        },
    ];

    const documentos = [
        {
            icon: FileText,
            titulo: 'Estatuto',
            href: 'https://drive.google.com/file/d/1xfLzy1BwT4JOS4X-h_5FjwT6iaD_40EF/view?usp=sharing',
        },
        {
            icon: ClipboardCheck,
            titulo: 'Termo de Compromisso de Voluntariado',
            href: 'https://drive.google.com/file/d/1SB6-WUMFWz6CDVEIigfEk2pcEdGaT05B/view?usp=sharing',
        },
        {
            icon: FolderKanban,
            titulo: 'Formulário de Justificativa de Ausência',
            href: 'https://forms.gle/h9qvBjeCiyhwtbRj9',
        },
        {
            icon: ShieldCheck,
            titulo: 'Guia de Identidade Visual',
            href: 'https://drive.google.com/drive/folders/1aiOGXYdGhftj9H_gKJLxsUI41Xdc5PI8?usp=sharing',
        },
    ];

    return (
        <MainLayout>
            <section className="relative h-120 flex items-center overflow-hidden bg-gray-900">
                <img
                    src={heroBg}
                    alt="Hero Ramo IEEE UFRN"
                    className="absolute inset-0 w-full h-full object-cover opacity-40"
                />
                <div className="relative z-10 max-w-7xl mx-auto px-6 md:px-12 w-full text-white">
                    <div className="max-w-2xl">
                        <h1 className="text-4xl md:text-5xl font-black uppercase mb-4 leading-tight tracking-tight">
                            Bem-vindo ao{' '}
                            <span className="text-secondary">CoordIEEEna</span>
                        </h1>
                        <p className="text-base md:text-lg opacity-90 mb-8">
                            Plataforma de Gestão Interna do Ramo Estudantil IEEE
                            UFRN para centralizar o controle de voluntários,
                            eventos e relatórios em um só lugar.
                        </p>
                        {!isAuthenticated && (
                            <Button
                                asChild
                                size="lg"
                                className="rounded-full shadow-lg hover:scale-105 transition-all"
                            >
                                <Link to="/login">
                                    <LogIn size={20} />
                                    Acessar Sistema
                                </Link>
                            </Button>
                        )}
                    </div>
                </div>
            </section>

            <section className="bg-primary py-6 px-6 border-y border-white/10">
                <div className="max-w-7xl mx-auto grid grid-cols-2 md:grid-cols-5 gap-6 text-secondary">
                    <div className="flex items-center gap-3">
                        <Users size={36} />
                        <div className="text-left">
                            <p className="text-3xl lg:text-4xl font-black leading-none text-white">
                                +190
                            </p>
                            <p className="text-xs uppercase font-bold tracking-wider mt-1">
                                Voluntários Ativos
                            </p>
                        </div>
                    </div>
                    <div className="flex items-center gap-3">
                        <Layers size={36} />
                        <div className="text-left">
                            <p className="text-3xl lg:text-4xl font-black leading-none text-white">
                                5
                            </p>
                            <p className="text-xs uppercase font-bold tracking-wider mt-1">
                                Capítulos Técnicos
                            </p>
                        </div>
                    </div>
                    <div className="flex items-center gap-3">
                        <HeartHandshake size={36} />
                        <div className="text-left">
                            <p className="text-3xl lg:text-4xl font-black leading-none text-white">
                                1
                            </p>
                            <p className="text-xs uppercase font-bold tracking-wider mt-1">
                                Grupo de Afinidade
                            </p>
                        </div>
                    </div>
                    <div className="flex items-center gap-3">
                        <Coins size={36} />
                        <div className="text-left">
                            <p className="text-3xl lg:text-4xl font-black leading-none text-white">
                                44
                            </p>
                            <p className="text-xs uppercase font-bold tracking-wider mt-1">
                                Financiamentos
                            </p>
                        </div>
                    </div>
                    <div className="flex items-center gap-3">
                        <Trophy size={36} />
                        <div className="text-left">
                            <p className="text-3xl lg:text-4xl font-black leading-none text-white">
                                41
                            </p>
                            <p className="text-xs uppercase font-bold tracking-wider mt-1">
                                Prêmios int. e nacionais
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            <section
                id="sobre"
                className="scroll-mt-10 py-12 px-6 max-w-7xl mx-auto"
            >
                <div className="text-center max-w-3xl mx-auto space-y-4">
                    <h2 className="text-3xl font-black uppercase tracking-tight text-gray-900">
                        Sobre o Sistema
                    </h2>
                    <p className="leading-relaxed text-gray-700 text-justify">
                        Sistema web fullstack desenvolvido para o Ramo
                        Estudantil IEEE UFRN, focado no gerenciamento de
                        voluntários, controle de membresias, alocação em
                        unidades organizacionais (Capítulos e Grupos de
                        Afinidade), planejamento de eventos, gestão orçamentária
                        e emissão de relatórios. O objetivo principal é
                        automatizar processos administrativos e descentralizar o
                        controle de permissões através de uma arquitetura
                        robusta e segura.
                    </p>
                    <p className="leading-relaxed text-gray-700 text-justify">
                        O sistema centraliza o controle de mais de 190
                        voluntários ativos divididos entre a Diretoria
                        Executiva, Capítulos Técnicos (CS, EMBS, IES, PES, RAS)
                        e Grupos de Afinidade/Locais (WIE, Entrepreneurship).
                        Ele assegura controle rigoroso de membresia, prestação
                        de contas no vTools, gestão orçamentária e relatórios
                        automatizados.
                    </p>
                </div>
            </section>

            <section
                id="sistema"
                className="scroll-mt-10 bg-gray-100 py-16 px-6 border-y border-gray-200"
            >
                <div className="max-w-7xl mx-auto space-y-10">
                    <div className="text-center max-w-2xl mx-auto">
                        <h2 className="text-2xl font-black uppercase tracking-tight text-gray-900">
                            Arquitetura & Desenvolvimento
                        </h2>
                        <p className="text-sm text-gray-600 mt-2">
                            Conheça as tecnologias, a documentação da API e a
                            equipe responsável pela plataforma.
                        </p>
                    </div>

                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <Code2 className="text-primary" size={24} />
                            Tecnologias & Stack
                        </h3>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            <Card className="hover:shadow-md transition-shadow">
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-3 text-primary text-lg">
                                        <Globe size={20} /> Frontend
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                        <li>
                                            <strong>React:</strong> Biblioteca
                                            principal para construção da
                                            interface SPA.
                                        </li>
                                        <li>
                                            <strong>Tailwind CSS:</strong>{' '}
                                            Framework utilitário para
                                            estilização ágil e responsiva.
                                        </li>
                                        <li>
                                            <strong>Shadcn/ui:</strong>{' '}
                                            Componentes acessíveis,
                                            reutilizáveis e customizáveis.
                                        </li>
                                        <li>
                                            <strong>
                                                Lucide & React Icons:
                                            </strong>{' '}
                                            Conjunto moderno de ícones
                                            vetoriais.
                                        </li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card className="hover:shadow-md transition-shadow">
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-3 text-primary text-lg">
                                        <Server size={20} /> Backend & API
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                        <li>
                                            <strong>Spring Boot:</strong>{' '}
                                            Framework Java de alta performance
                                            para a API RESTful.
                                        </li>
                                        <li>
                                            <strong>Autenticação JWT:</strong>{' '}
                                            Proteção de rotas com stateless Json
                                            Web Tokens.
                                        </li>
                                        <li>
                                            <strong>Spring Security:</strong>{' '}
                                            Controle rigoroso de acessos e
                                            autorizações.
                                        </li>
                                        <li>
                                            <strong>
                                                Arquitetura em Camadas:
                                            </strong>{' '}
                                            Separação limpa entre Controllers,
                                            Services e Repositories.
                                        </li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card className="hover:shadow-md transition-shadow">
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-3 text-primary text-lg">
                                        <Database size={20} /> Banco de Dados &
                                        Infraestrutura
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                        <li>
                                            <strong>
                                                Supabase (PostgreSQL):
                                            </strong>{' '}
                                            Banco de dados relacional hospedado
                                            na nuvem.
                                        </li>
                                        <li>
                                            <strong>
                                                Integridade Relacional:
                                            </strong>{' '}
                                            Tabelas otimizadas com chaves
                                            estrangeiras e relacionamentos.
                                        </li>
                                        <li>
                                            <strong>RBAC:</strong> Controle de
                                            acesso baseado nas funções (cargos)
                                            dos voluntários.
                                        </li>
                                    </ul>
                                </CardContent>
                            </Card>
                        </div>
                    </div>

                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <BookOpen className="text-primary" size={24} />
                            Documentação da API & Código Fonte
                        </h3>
                        <Card>
                            <CardContent className="flex flex-col lg:flex-row items-start lg:items-center justify-between gap-10 p-6 md:p-8">
                                <div className="max-w-3xl">
                                    <h4 className="font-bold text-lg text-gray-900 mb-2">
                                        Documentação da API via Swagger
                                        (Springdoc)
                                    </h4>
                                    <p className="text-sm text-gray-600 leading-relaxed text-justify">
                                        Acesso a interface interativa do Swagger
                                        UI para consultar e testar em tempo real
                                        todos os endpoints da aplicação. A
                                        documentação segue a especificação
                                        OpenAPI 3.0, exibindo os contratos REST,
                                        parâmetros de requisição, corpos de
                                        resposta e o esquema de autenticação por
                                        token JWT para os testes.
                                    </p>
                                </div>
                                <div className="flex flex-wrap sm:flex-nowrap gap-3 shrink-0 w-full lg:w-auto">
                                    <Button
                                        asChild
                                        size="lg"
                                        className="flex-1 sm:flex-none shadow-md"
                                    >
                                        <a
                                            href="https://github.com/sebastiaolopesfp/ieee-ufrn-db.git"
                                            target="_blank"
                                            rel="noopener noreferrer"
                                        >
                                            <Code2 size={18} /> Repositório
                                            GitHub <ExternalLink size={14} />
                                        </a>
                                    </Button>
                                    <Button
                                        asChild
                                        size="lg"
                                        className="flex-1 sm:flex-none shadow-md"
                                    >
                                        <a
                                            href={`${apiBaseUrl}/swagger-ui/index.html`}
                                            target="_blank"
                                            rel="noopener noreferrer"
                                        >
                                            <BookOpen size={18} /> Documentação
                                            API <ExternalLink size={14} />
                                        </a>
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </div>

                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <Users className="text-primary" size={24} />
                            Equipe de Desenvolvimento
                        </h3>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            {desenvolvedores.map((dev, index) => (
                                <Card
                                    key={index}
                                    className="hover:shadow-md transition-all"
                                >
                                    <CardContent className="flex items-center justify-between">
                                        <div>
                                            <h4 className="font-bold text-gray-900 text-lg">
                                                {dev.nome}
                                            </h4>
                                            <p className="text-xs text-primary font-semibold mt-0.5">
                                                {dev.funcao}
                                            </p>
                                        </div>
                                        <div className="flex items-center gap-2">
                                            <a
                                                href={dev.github}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                className="p-2 bg-gray-100 hover:bg-gray-200 rounded-full transition-colors text-gray-700"
                                                title="GitHub"
                                            >
                                                <FaGithub size={18} />
                                            </a>
                                            <a
                                                href={dev.linkedin}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                className="p-2 bg-gray-100 hover:bg-gray-200 rounded-full transition-colors text-primary"
                                                title="LinkedIn"
                                            >
                                                <FaLinkedin size={18} />
                                            </a>
                                        </div>
                                    </CardContent>
                                </Card>
                            ))}
                        </div>
                    </div>
                </div>
            </section>

            <section
                id="documentos"
                className="scroll-mt-10 py-12 px-6 max-w-7xl mx-auto"
            >
                <h2 className="text-3xl font-black uppercase mb-2 tracking-tight text-gray-900">
                    Documentos de Apoio
                </h2>
                <p className="text-gray-600 mb-6 leading-relaxed">
                    Acesso rápido a regimentos, formulários e guias oficiais do
                    Ramo Estudantil IEEE UFRN:
                </p>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {documentos.map((doc) => (
                        <a
                            key={doc.titulo}
                            href={doc.href}
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            <Card className="hover:bg-primary/5 transition-all hover:border-primary/30">
                                <CardContent className="flex items-center justify-between font-semibold text-gray-800">
                                    <span className="flex items-center gap-3">
                                        <doc.icon
                                            size={20}
                                            className="text-primary shrink-0"
                                        />
                                        {doc.titulo}
                                    </span>
                                    <ExternalLink size={16} />
                                </CardContent>
                            </Card>
                        </a>
                    ))}
                </div>
            </section>
        </MainLayout>
    );
}
