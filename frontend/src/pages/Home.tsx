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

    return (
        <MainLayout>
            {/* SEÇÃO 1: HERO BANNER */}
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
                            <span className="text-brand-secondary">
                                CoordIEEEna
                            </span>
                        </h1>
                        <p className="text-base md:text-lg opacity-90 mb-8">
                            Plataforma de Gestão Interna do Ramo Estudantil IEEE
                            UFRN para centralizar o controle de voluntários,
                            eventos e relatórios em um só lugar.
                        </p>
                        {!isAuthenticated && (
                            <Link
                                to="/login"
                                className="inline-flex items-center gap-2 bg-brand-primary hover:bg-brand-primary/90 text-white font-bold px-6 py-3 rounded-full border border-brand-secondary/40 shadow-lg transition-all hover:scale-105"
                            >
                                <LogIn size={20} />
                                Acessar Sistema
                            </Link>
                        )}
                    </div>
                </div>
            </section>

            {/* SEÇÃO 2: MÉTRICAS DO RAMO */}
            <section className="bg-brand-primary py-6 px-6 border-y border-white/10">
                <div className="max-w-7xl mx-auto grid grid-cols-2 md:grid-cols-5 gap-6 text-brand-secondary">
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

            {/* SEÇÃO 3: SOBRE O SISTEMA */}
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

            {/* SEÇÃO 4: ARQUITETURA E DESENVOLVIMENTO */}
            <section
                id="sistema"
                className="scroll-mt-10 bg-gray-100 py-16 px-6 border-y border-gray-200"
            >
                <div className="max-w-7xl mx-auto space-y-10">
                    {/* Cabeçalho do Bloco */}
                    <div className="text-center max-w-2xl mx-auto">
                        <h2 className="text-2xl font-black uppercase tracking-tight text-gray-900">
                            Arquitetura & Desenvolvimento
                        </h2>
                        <p className="text-sm text-gray-600 mt-2">
                            Conheça as tecnologias, a documentação da API e a
                            equipe responsável pela plataforma.
                        </p>
                    </div>

                    {/* SUBSEÇÃO 4.1: TECH STACK */}
                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <Code2 className="text-brand-primary" size={24} />
                            Tecnologias & Stack
                        </h3>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            {/* Card: Frontend */}
                            <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition-shadow">
                                <div className="flex items-center gap-3 text-brand-primary font-bold text-lg mb-3">
                                    <Globe size={20} /> Frontend
                                </div>
                                <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                    <li>
                                        <strong>React:</strong> Biblioteca
                                        principal para construção da interface
                                        SPA.
                                    </li>
                                    <li>
                                        <strong>Tailwind CSS:</strong> Framework
                                        utilitário para estilização ágil e
                                        responsiva.
                                    </li>
                                    <li>
                                        <strong>Shadcn/ui:</strong> Componentes
                                        acessíveis, reutilizáveis e
                                        customizáveis.
                                    </li>
                                    <li>
                                        <strong>Lucide & React Icons:</strong>{' '}
                                        Conjunto moderno de ícones vetoriais.
                                    </li>
                                </ul>
                            </div>

                            {/* Card: Backend */}
                            <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition-shadow">
                                <div className="flex items-center gap-3 text-brand-primary font-bold text-lg mb-3">
                                    <Server size={20} /> Backend & API
                                </div>
                                <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                    <li>
                                        <strong>Spring Boot:</strong> Framework
                                        Java de alta performance para a API
                                        RESTful.
                                    </li>
                                    <li>
                                        <strong>Autenticação JWT:</strong>{' '}
                                        Proteção de rotas com stateless Json Web
                                        Tokens.
                                    </li>
                                    <li>
                                        <strong>Spring Security:</strong>{' '}
                                        Controle rigoroso de acessos e
                                        autorizações.
                                    </li>
                                    <li>
                                        <strong>Arquitetura em Camadas:</strong>{' '}
                                        Separação limpa entre Controllers,
                                        Services e Repositories.
                                    </li>
                                </ul>
                            </div>

                            {/* Card: Banco de Dados */}
                            <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition-shadow">
                                <div className="flex items-center gap-3 text-brand-primary font-bold text-lg mb-3">
                                    <Database size={20} /> Banco de Dados &
                                    Infraestrutura
                                </div>
                                <ul className="space-y-2 text-sm text-gray-600 list-disc list-inside">
                                    <li>
                                        <strong>Supabase (PostgreSQL):</strong>{' '}
                                        Banco de dados relacional hospedado na
                                        nuvem.
                                    </li>
                                    <li>
                                        <strong>Integridade Relacional:</strong>{' '}
                                        Tabelas otimizadas com chaves
                                        estrangeiras e relacionamentos.
                                    </li>
                                    <li>
                                        <strong>RBAC:</strong> Controle de
                                        acesso baseado nas funções (cargos) dos
                                        voluntários.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    {/* SUBSEÇÃO 4.2: DOCUMENTAÇÃO DA API */}
                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <BookOpen
                                className="text-brand-primary"
                                size={24}
                            />
                            Documentação da API & Código Fonte
                        </h3>
                        <div className="bg-white p-6 md:p-8 rounded-xl border border-gray-200 shadow-sm flex flex-col lg:flex-row items-start lg:items-center justify-between gap-10">
                            <div className="max-w-3xl">
                                <h4 className="font-bold text-lg text-gray-900 mb-2">
                                    Documentação da API via Swagger (Springdoc)
                                </h4>
                                <p className="text-sm text-gray-600 leading-relaxed text-justify">
                                    Acesse a interface interativa do Swagger UI
                                    para consultar e testar em tempo real todos
                                    os endpoints da aplicação. A documentação
                                    segue a especificação OpenAPI 3.0, exibindo
                                    os contratos REST, parâmetros de requisição,
                                    corpos de resposta e o esquema de
                                    autenticação por token JWT para os testes.
                                </p>
                            </div>
                            <div className="flex flex-wrap sm:flex-nowrap gap-3 shrink-0 w-full lg:w-auto">
                                <a
                                    href="https://github.com/sebastiaolopesfp/ieee-ufrn-db.git"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="flex-1 sm:flex-none inline-flex items-center justify-center gap-2 bg-brand-primary hover:bg-brand-primary/90 text-white font-semibold px-5 py-3 rounded-lg text-sm transition-all shadow-md"
                                >
                                    <Code2 size={18} /> Repositório GitHub
                                    <ExternalLink size={14} />
                                </a>
                                <a
                                    href={`${apiBaseUrl}/swagger-ui/index.html`}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="flex-1 sm:flex-none inline-flex items-center justify-center gap-2 bg-brand-primary hover:bg-brand-primary/90 text-white font-semibold px-5 py-3 rounded-lg text-sm transition-all shadow-md"
                                >
                                    <BookOpen size={18} /> Documentação API
                                    <ExternalLink size={14} />
                                </a>
                            </div>
                        </div>
                    </div>

                    {/* SUBSEÇÃO 4.3: EQUIPE */}
                    <div>
                        <h3 className="text-lg font-bold text-gray-900 mb-6 flex items-center gap-2">
                            <Users className="text-brand-primary" size={24} />
                            Equipe de Desenvolvimento
                        </h3>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            {desenvolvedores.map((dev, index) => (
                                <div
                                    key={index}
                                    className="bg-white p-6 rounded-xl border border-gray-200 flex items-center justify-between shadow-sm hover:shadow-md transition-all"
                                >
                                    <div>
                                        <h4 className="font-bold text-gray-900 text-lg">
                                            {dev.nome}
                                        </h4>
                                        <p className="text-xs text-brand-primary font-semibold mt-0.5">
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
                                            className="p-2 bg-gray-100 hover:bg-gray-200 rounded-full transition-colors text-brand-primary"
                                            title="LinkedIn"
                                        >
                                            <FaLinkedin size={18} />
                                        </a>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </section>

            {/* SEÇÃO 5: DOCUMENTOS DE APOIO */}
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
                    <a
                        href="https://drive.google.com/file/d/1xfLzy1BwT4JOS4X-h_5FjwT6iaD_40EF/view?usp=sharing"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="flex items-center justify-between p-4 bg-gray-50 hover:bg-brand-primary/5 rounded-lg border border-gray-200 transition-all font-semibold text-gray-800 hover:text-brand-primary"
                    >
                        <span className="flex items-center gap-3">
                            <FileText
                                size={20}
                                className="text-brand-primary shrink-0"
                            />
                            Estatuto
                        </span>
                        <ExternalLink size={16} />
                    </a>

                    <a
                        href="https://drive.google.com/file/d/1SB6-WUMFWz6CDVEIigfEk2pcEdGaT05B/view?usp=sharing"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="flex items-center justify-between p-4 bg-gray-50 hover:bg-brand-primary/5 rounded-lg border border-gray-200 transition-all font-semibold text-gray-800 hover:text-brand-primary"
                    >
                        <span className="flex items-center gap-3">
                            <ClipboardCheck
                                size={20}
                                className="text-brand-primary shrink-0"
                            />
                            Termo de Compromisso de Voluntariado
                        </span>
                        <ExternalLink size={16} />
                    </a>

                    <a
                        href="https://forms.gle/h9qvBjeCiyhwtbRj9"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="flex items-center justify-between p-4 bg-gray-50 hover:bg-brand-primary/5 rounded-lg border border-gray-200 transition-all font-semibold text-gray-800 hover:text-brand-primary"
                    >
                        <span className="flex items-center gap-3">
                            <FolderKanban
                                size={20}
                                className="text-brand-primary shrink-0"
                            />
                            Formulário de Justificativa de Ausência
                        </span>
                        <ExternalLink size={16} />
                    </a>

                    <a
                        href="https://drive.google.com/drive/folders/1aiOGXYdGhftj9H_gKJLxsUI41Xdc5PI8?usp=sharing"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="flex items-center justify-between p-4 bg-gray-50 hover:bg-brand-primary/5 rounded-lg border border-gray-200 transition-all font-semibold text-gray-800 hover:text-brand-primary"
                    >
                        <span className="flex items-center gap-3">
                            <ShieldCheck
                                size={20}
                                className="text-brand-primary shrink-0"
                            />
                            Guia de Identidade Visual
                        </span>
                        <ExternalLink size={16} />
                    </a>
                </div>
            </section>
        </MainLayout>
    );
}
