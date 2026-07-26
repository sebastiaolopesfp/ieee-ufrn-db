import React, { useState, useRef, useEffect } from 'react';
import logoWhite from '../assets/logo_white.png';
import {
    ExternalLink,
    UserRound,
    Code2,
    Mail,
    LogIn,
    ChevronDown,
    LogOut,
    IdCardLanyard,
} from 'lucide-react';
import { FaLinkedin, FaYoutube, FaInstagram } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

interface MainLayoutProps {
    children: React.ReactNode;
}

export function MainLayout({ children }: MainLayoutProps) {
    const { user, isAuthenticated, logout } = useAuth();
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);

    // Fecha o menu suspenso se o usuário clicar fora dele
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (
                dropdownRef.current &&
                !dropdownRef.current.contains(event.target as Node)
            ) {
                setDropdownOpen(false);
            }
        }
        document.addEventListener('mousedown', handleClickOutside);
        return () =>
            document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <div className="min-h-screen flex flex-col bg-white text-gray-800">
            {/* HEADER */}
            <header className="sticky top-0 z-50 bg-brand-primary text-white px-6 md:px-12 py-4 grid grid-cols-2 md:grid-cols-3 items-center shadow-md border-b border-white/10">
                {/* 1. LOGO */}
                <div className="flex justify-start">
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
                    </Link>
                </div>

                {/* 2. NAVEGAÇÃO INTERNA */}
                <nav className="hidden md:flex justify-center items-center gap-8 text-base font-medium">
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

                {/* 3. PERFIL OU LOGIN DINÂMICO */}
                <div className="flex justify-end relative" ref={dropdownRef}>
                    {isAuthenticated && user ? (
                        /* USUÁRIO AUTENTICADO: MENU DROPDOWN */
                        <div className="relative">
                            <button
                                onClick={() => setDropdownOpen(!dropdownOpen)}
                                className="flex items-center gap-2.5 px-4 py-2 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all cursor-pointer focus:outline-none"
                            >
                                <UserRound
                                    size={18}
                                    className="text-brand-secondary"
                                />
                                <span className="text-xs md:text-sm font-medium text-white">
                                    Olá,{' '}
                                    <strong className="font-semibold">
                                        {user.nomeExibicao}
                                    </strong>
                                </span>
                                <ChevronDown
                                    size={14}
                                    className={`text-brand-secondary transition-transform duration-200 ${
                                        dropdownOpen ? 'rotate-180' : ''
                                    }`}
                                />
                            </button>

                            {/* CAIXA SUSPENSA (DROPDOWN) */}
                            {dropdownOpen && (
                                <div className="absolute right-0 mt-2 w-56 bg-white rounded-lg shadow-xl border border-gray-100 py-2 z-50 animate-in fade-in slide-in-from-top-2 duration-150">
                                    <div className="px-4 py-2 border-b border-gray-100">
                                        <p className="text-xs text-gray-500 font-medium">
                                            Conectado como
                                        </p>
                                        <p className="text-sm font-semibold text-gray-900 truncate">
                                            {user.email}
                                        </p>
                                    </div>

                                    <Link
                                        to="/dashboard"
                                        onClick={() => setDropdownOpen(false)}
                                        className="w-full flex items-center gap-2.5 px-4 py-2.5 text-sm font-medium text-gray-700 hover:bg-gray-50 transition-colors"
                                    >
                                        <IdCardLanyard
                                            size={16}
                                            className="text-brand-primary"
                                        />
                                        Perfil
                                    </Link>

                                    <button
                                        onClick={() => {
                                            setDropdownOpen(false);
                                            logout();
                                        }}
                                        className="w-full flex items-center gap-2.5 px-4 py-2.5 text-sm font-medium text-red-600 hover:bg-red-50 transition-colors text-left"
                                    >
                                        <LogOut size={16} />
                                        Sair do Sistema
                                    </button>
                                </div>
                            )}
                        </div>
                    ) : (
                        /* USUÁRIO NÃO AUTENTICADO: BOTÃO LOGIN */
                        <Link
                            to="/login"
                            className="inline-flex items-center gap-2 px-4 py-2 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all text-white font-semibold text-xs md:text-sm"
                        >
                            <LogIn size={18} className="text-brand-secondary" />
                            Acessar Sistema
                        </Link>
                    )}
                </div>
            </header>

            {/* CONTEÚDO PRINCIPAL */}
            <main className="flex-1">{children}</main>

            {/* FOOTER */}
            <footer className="bg-brand-primary text-white border-t border-white/10">
                <div className="w-full max-w-7xl mx-auto px-6 md:px-12 py-8 grid grid-cols-1 md:grid-cols-3 gap-8 text-brand-secondary text-sm">
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
                        </ul>
                    </div>

                    <div className="flex flex-col gap-3">
                        <h4 className="font-bold uppercase tracking-wider">
                            Contatos
                        </h4>
                        <a
                            href="mailto:sb.ufrn@ieee.org"
                            className="hover:text-white hover:underline flex items-center gap-2 transition-colors w-fit font-medium"
                        >
                            <Mail size={16} className="text-brand-secondary" />
                            sb.ufrn@ieee.org
                        </a>

                        <div className="flex items-center gap-3 mt-1">
                            <a
                                href="https://www.instagram.com/ieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaInstagram size={18} />
                            </a>
                            <a
                                href="https://www.linkedin.com/company/ramoieeeufrn/"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaLinkedin size={18} />
                            </a>
                            <a
                                href="https://www.youtube.com/@ramoieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-brand-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaYoutube size={18} />
                            </a>
                        </div>
                    </div>
                </div>

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
