import type { ReactNode } from 'react';
import { Link } from 'react-router-dom';
import { Users, Calendar, DollarSign, Settings, LogOut, LayoutGrid, ShieldAlert } from 'lucide-react';

interface MainLayoutProps {
  children: ReactNode;
  titulo: string;
}

export function MainLayout({ children, titulo }: MainLayoutProps) {
  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/login';
  };

  return (
    <div className="flex h-screen bg-gray-100 text-gray-900 overflow-hidden font-sans">
      
      {/* Barra Lateral (Sidebar) - Mais institucional, fundo branco, linhas retas */}
      <aside className="w-64 bg-white border-r border-gray-200 flex flex-col hidden md:flex z-20">
        <div className="p-6 border-b border-gray-200 flex items-center gap-3">
          {/* Espaço reservado para a sua logo: */}
          <img src="/logo-ieeeufrn.png" alt="Logo" className="h-8" />
          
          <div>
            <h2 className="text-lg font-bold text-gray-900 leading-tight">Ramo IEEE UFRN</h2>
            <p className="text-[10px] text-gray-500 uppercase tracking-wider font-semibold">Database System</p>
          </div>
        </div>
        
        <nav className="flex-1 p-4 space-y-1 overflow-y-auto">
          <Link 
            to="/dashboard" 
            className="flex items-center gap-3 text-gray-700 hover:bg-gray-100 px-3 py-2.5 rounded-sm font-medium transition-colors border-l-4 border-transparent hover:border-[#0F81CA]"
          >
            <Users size={18} className="text-[#0F81CA]" /> 
            <span>Voluntários</span>
          </Link>
          <Link 
            to="/diretorias" 
            className="flex items-center gap-3 text-gray-700 hover:bg-gray-100 px-3 py-2.5 rounded-sm font-medium transition-colors border-l-4 border-transparent hover:border-[#ED7630]"
          >
            <ShieldAlert size={18} className="text-[#ED7630]" /> 
            <span>Diretorias</span>
          </Link>
          <Link 
            to="/eventos" 
            className="flex items-center gap-3 text-gray-700 hover:bg-gray-100 px-3 py-2.5 rounded-sm font-medium transition-colors border-l-4 border-transparent hover:border-[#0F81CA]"
          >
            <Calendar size={18} className="text-[#0F81CA]" /> 
            <span>Eventos</span>
          </Link>
          <Link 
            to="#" 
            className="flex items-center gap-3 text-gray-700 hover:bg-gray-100 px-3 py-2.5 rounded-sm font-medium transition-colors border-l-4 border-transparent hover:border-[#0F81CA]"
          >
            <DollarSign size={18} className="text-[#0F81CA]" /> 
            <span>Financeiro</span>
          </Link>
          <Link 
            to="#" 
            className="flex items-center gap-3 text-gray-700 hover:bg-gray-100 px-3 py-2.5 rounded-sm font-medium transition-colors border-l-4 border-transparent hover:border-[#0F81CA]"
          >
            <Settings size={18} className="text-[#0F81CA]" /> 
            <span>Configurações</span>
          </Link>
        </nav>

        <div className="p-4 border-t border-gray-200">
          <button 
            onClick={handleLogout}
            className="flex items-center gap-3 text-gray-600 hover:bg-red-50 hover:text-red-700 w-full px-3 py-2.5 rounded-sm font-medium transition-colors"
          >
            <LogOut size={18} /> Sair do Sistema
          </button>
        </div>
      </aside>

      {/* Conteúdo Principal */}
        <main className="flex-1 flex flex-col overflow-hidden bg-gray-100">
        
        {/* Header Superior Topo */}
        <header className="h-16 border-b border-gray-200 flex items-center px-8 bg-white shadow-sm z-10">
          <div className="flex items-center gap-2 text-gray-500">
            <LayoutGrid size={18} />
            <span className="text-sm font-medium">/ {titulo}</span>
          </div>
        </header>
        
        {/* Área útil da página */}
        <div className="flex-1 overflow-auto p-8 relative">
          <div className="max-w-7xl mx-auto">
            <h1 className="text-3xl font-bold text-gray-900 mb-6">{titulo}</h1>
            {children}
          </div>
        </div>
      </main>
    </div>
  );
}