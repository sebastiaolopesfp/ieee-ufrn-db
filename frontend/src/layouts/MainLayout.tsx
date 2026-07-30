import React from 'react';
import logoWhite from '../assets/logo_white.png';
import {
    ExternalLink,
    Code2,
    Mail,
    LogIn,
    LogOut,
    IdCardLanyard,
} from 'lucide-react';
import { FaLinkedin, FaYoutube, FaInstagram } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Button } from '@/components/ui/button';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { obterIniciais } from '@/lib/utils';

interface MainLayoutProps {
    children: React.ReactNode;
}

export function MainLayout({ children }: MainLayoutProps) {
    const { user, isAuthenticated, isLoading, logout } = useAuth();

    const nomes = user?.nomeExibicao.split(' ') || [];
    const primeiroNome = nomes[0];
    const ultimoNome = nomes.length > 1 ? nomes[nomes.length - 1] : undefined;

    return (
        <div className="min-h-screen flex flex-col bg-white text-gray-800">
            <header className="sticky top-0 z-50 bg-primary text-white px-6 md:px-12 py-4 grid grid-cols-2 md:grid-cols-3 items-center shadow-md border-b border-white/10">
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

                <nav className="hidden md:flex justify-center items-center gap-8 text-base font-medium">
                    <a
                        href="#sobre"
                        className="text-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Sobre
                    </a>
                    <a
                        href="#sistema"
                        className="text-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Arquitetura
                    </a>
                    <a
                        href="#documentos"
                        className="text-secondary hover:text-white hover:underline transition-all underline-offset-4"
                    >
                        Documentos
                    </a>
                </nav>

                <div className="flex justify-end">
                    {isLoading ? (
                        <div className="w-36 h-9" />
                    ) : isAuthenticated && user ? (
                        <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                                <button className="flex items-center gap-2.5 px-3 py-1.5 rounded-full border border-secondary/40 bg-transparent hover:bg-white/10 transition-all cursor-pointer focus:outline-none">
                                    <Avatar className="h-7 w-7">
                                        <AvatarFallback className="bg-secondary text-primary text-xs font-bold">
                                            {obterIniciais(
                                                primeiroNome,
                                                ultimoNome,
                                            )}
                                        </AvatarFallback>
                                    </Avatar>
                                    <span className="text-xs md:text-sm font-medium text-white">
                                        {user.nomeExibicao}
                                    </span>
                                </button>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent align="end" className="w-56">
                                <DropdownMenuLabel className="font-normal">
                                    <p className="text-xs text-muted-foreground">
                                        Conectado como
                                    </p>
                                    <p className="text-sm font-semibold truncate">
                                        {user.email}
                                    </p>
                                </DropdownMenuLabel>
                                <DropdownMenuSeparator />
                                <DropdownMenuItem asChild>
                                    <Link
                                        to="/perfil"
                                        className="cursor-pointer"
                                    >
                                        <IdCardLanyard />
                                        Perfil
                                    </Link>
                                </DropdownMenuItem>
                                <DropdownMenuItem
                                    onClick={logout}
                                    variant="destructive"
                                    className="cursor-pointer"
                                >
                                    <LogOut className="mr-2 h-4 w-4" />
                                    Sair do Sistema
                                </DropdownMenuItem>
                            </DropdownMenuContent>
                        </DropdownMenu>
                    ) : (
                        <Button
                            asChild
                            variant="outline"
                            className="rounded-full border-secondary/40 bg-transparent hover:bg-white/10 text-white"
                        >
                            <Link to="/login">
                                <LogIn className="text-secondary" />
                                Acessar Sistema
                            </Link>
                        </Button>
                    )}
                </div>
            </header>

            <main className="flex-1">{children}</main>

            <footer className="bg-primary text-white border-t border-white/10">
                <div className="w-full max-w-7xl mx-auto px-6 md:px-12 py-8 grid grid-cols-1 md:grid-cols-3 gap-8 text-secondary text-sm">
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

                    <div className="flex flex-col gap-3">
                        <h4 className="font-bold uppercase tracking-wider">
                            Contatos
                        </h4>
                        <p>
                            Fale conosco ou siga nossas redes para acompanhar
                            eventos e oportunidades:
                        </p>
                        <a
                            href="mailto:sb.ufrn@ieee.org"
                            className="hover:text-white hover:underline flex items-center gap-2 transition-colors w-fit font-medium"
                        >
                            <Mail size={16} className="text-secondary" />
                            sb.ufrn@ieee.org
                        </a>

                        <div className="flex items-center gap-3 mt-1">
                            <a
                                href="https://www.instagram.com/ieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaInstagram size={18} />
                            </a>
                            <a
                                href="https://www.linkedin.com/company/ramoieeeufrn/"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaLinkedin size={18} />
                            </a>
                            <a
                                href="https://www.youtube.com/@ramoieeeufrn"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="p-2.5 rounded-full border border-secondary/40 bg-transparent hover:bg-white/10 transition-all"
                            >
                                <FaYoutube size={18} />
                            </a>
                        </div>
                    </div>
                </div>

                <div className="bg-black/20 border-t border-white/5 py-4">
                    <div className="w-full max-w-7xl mx-auto px-6 md:px-12 flex flex-col md:flex-row justify-between items-center gap-2 text-xs text-secondary/80">
                        <p>
                            &copy; {new Date().getFullYear()} Ramo Estudantil
                            IEEE UFRN. Todos os direitos reservados.
                        </p>
                        <p className="flex items-center gap-1.5 text-center md:text-right">
                            <Code2
                                size={14}
                                className="text-secondary shrink-0"
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
