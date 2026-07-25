import React from 'react';
import logoWhite from '../assets/Logo_white.png';
import { ExternalLink, UserRound, Code2, Mail } from 'lucide-react';
import { FaLinkedin, FaYoutube, FaInstagram } from 'react-icons/fa';
import { Link } from 'react-router-dom';

interface MainLayoutProps {
    children: React.ReactNode;
}

export function MainLayout({ children }: MainLayoutProps) {
    return (
        <div className="min-h-screen flex flex-col bg-white text-gray-800">
            {/* HEADER */}
            <header className="sticky top-0 z-50 bg-brand-primary text-white px-6 md:px-12 py-4 flex justify-between items-center shadow-md border-b border-white/10">
                {/* LOGO */}
                <Link
                    to="/"
                    onClick={() =>
                        window.scrollTo({ top: 0, behavior: 'smooth' })
                    }
                    className="flex items-center gap-3 group"
                >
                    <img
                        src={logoWhite}
                        alt="Logo IEEE UFRN"
                        className="h-10 w-auto object-contain"
                    />
                    <span className="font-bold text-lg md:text-xl text-white tracking-tight">
                        Coord<span className="text-brand-secondary">IEEE</span>
                        na Hub
                    </span>
                </Link>

                {/* NAVEGAÇÃO INTERNA */}
                <nav className="hidden md:flex items-center gap-8 text-base font-medium">
                    <a
                        href="#sobre"
                        className="text-brand-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Sobre
                    </a>
                    <a
                        href="#sistema"
                        className="text-brand-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Arquitetura
                    </a>
                    <a
                        href="#documentos"
                        className="text-brand-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Documentos
                    </a>
                </nav>

                {/* USUÁRIO / PERFIL */}
                <div className="flex items-center gap-2.5 px-4 py-2 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all cursor-pointer">
                    <UserRound size={18} className="text-brand-secondary" />
                    <span className="text-xs md:text-sm font-medium text-white">
                        Olá,{' '}
                        <strong className="font-semibold">Sebastião</strong>
                    </span>
                </div>
            </header>

            {/* CONTEÚDO PRINCIPAL */}
            <main className="flex-1">{children}</main>

            {/* FOOTER */}
            <footer className="bg-brand-primary text-white border-t border-white/10">
                {/* SEÇÃO SUPERIOR */}
                <div className="w-full max-w-7xl mx-auto px-6 md:px-12 py-8 grid grid-cols-1 md:grid-cols-3 gap-8 text-brand-secondary text-sm">
                    {/* Coluna 1: Logo e Sobre */}
                    <div className="flex flex-col gap-4">
                        <div className="flex items-center gap-3">
                            <img
                                src={logoWhite}
                                alt="Logo IEEE UFRN"
                                className="h-10 w-auto object-contain"
                            />
                        </div>
                        <p className="leading-relaxed max-w-sm">
                            Avançando tecnologia para humanidade.
                        </p>
                    </div>

                    {/* Coluna 2: Sites Importantes */}
                    <div className="flex flex-col gap-3">
                        <h4 className="font-bold uppercase tracking-wider">
                            Sites Importantes
                        </h4>
                        <ul className="flex flex-col gap-2.5">
                            <li>
                                <a
                                    href="https://edu.ieee.org/br-rio-grande-do-norte/"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="hover:text-white hover:underline flex items-center gap-1.5 transition-colors"
                                >
                                    <ExternalLink size={16} />
                                    Site Oficial do Ramo Estudantil IEEE UFRN
                                </a>
                            </li>
                            <li>
                                <a
                                    href="https://linktr.ee/ieeeufrn"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="hover:text-white hover:underline flex items-center gap-1.5 transition-colors"
                                >
                                    <ExternalLink size={16} />
                                    Linktree do Ramo Estudantil IEEE UFRN
                                </a>
                            </li>
                            <li>
                                <a
                                    href="https://www.ieee.org/"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="hover:text-white hover:underline flex items-center gap-1.5 transition-colors"
                                >
                                    <ExternalLink size={16} />
                                    Site Oficial do IEEE
                                </a>
                            </li>
                            <li>
                                <a
                                    href="https://www.ieee.org/sitemap.html"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="hover:text-white hover:underline flex items-center gap-1.5 transition-colors"
                                >
                                    <ExternalLink size={16} />
                                    Mais sites do IEEE
                                </a>
                            </li>
                        </ul>
                    </div>

                    {/* Coluna 3: Contatos */}
                    <div className="flex flex-col gap-3">
                        <h4 className="font-bold uppercase tracking-wider">
                            Contatos
                        </h4>
                        <p>
                            Fale conosco ou siga nossas redes para acompanhar
                            eventos e oportunidades:
                        </p>

                        {/* E-mail oficial */}
                        <a
                            href="mailto:sb.ufrn@ieee.org"
                            className="hover:text-white hover:underline flex items-center gap-2 transition-colors w-fit font-medium"
                        >
                            <Mail size={16} className="text-brand-secondary" />
                            sb.ufrn@ieee.org
                        </a>

                        {/* Redes Sociais */}
                        <div className="flex items-center gap-3 mt-1">
                            <a
                                href="https://www.instagram.com/ieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                aria-label="Instagram IEEE UFRN"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaInstagram size={18} />
                            </a>
                            <a
                                href="https://www.linkedin.com/company/ramoieeeufrn/"
                                target="_blank"
                                rel="noopener noreferrer"
                                aria-label="LinkedIn IEEE UFRN"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaLinkedin size={18} />
                            </a>
                            <a
                                href="https://www.youtube.com/@ramoieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                aria-label="Youtube IEEE UFRN"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaYoutube size={18} />
                            </a>
                        </div>
                    </div>
                </div>

                {/* SEÇÃO INFERIOR (Créditos / Direitos) */}
                <div className="bg-black/20 border-t border-white/5 py-4">
                    <div className="w-full max-w-7xl mx-auto px-6 md:px-12 flex flex-col md:flex-row justify-between items-center gap-2 text-xs text-brand-secondary/80">
                        <p>
                            &copy; {new Date().getFullYear()} Ramo Estudantil
                            IEEE UFRN. Todos os direitos reservados.
                        </p>
                        <p className="flex items-center gap-1.5 text-center md:text-right">
                            <Code2
                                size={14}
                                className="text-brand-secondary shrink-0"
                            />
                            <span>
                                Desenvolvido por{' '}
                                <strong className="text-white font-medium">
                                    Liriel Felix, Natham Fernandes e Sebastião
                                    Lopes
                                </strong>
                            </span>
                        </p>
                    </div>
                </div>
            </footer>
        </div>
    );
}
