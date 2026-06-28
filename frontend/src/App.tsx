import { useState } from "react"
import { Button } from "@/components/ui/button"

function App() {
  const [contador, setContador] = useState(0)

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-zinc-950 text-zinc-50 p-6">
      <div className="w-full max-w-md p-8 rounded-2xl bg-zinc-900 border border-zinc-800 shadow-xl text-center space-y-6">
        
        {/* Cabeçalho */}
        <div className="space-y-2">
          <h1 className="text-3xl font-extrabold tracking-tight text-blue-800">
            IEEE UFRN DATABASE
          </h1>
          <p className="text-zinc-400 text-sm">
            Verificação de ambiente e dependências do Frontend
          </p>
        </div>

        {/* Status das Dependências */}
        <div className="text-left space-y-3 bg-zinc-950/50 p-4 rounded-lg border border-zinc-800/50 text-sm">
          <div className="flex justify-between">
            <span className="text-zinc-400">React + TypeScript:</span>
            <span className="text-blue-800 font-semibold">✓ Ativo</span>
          </div>
          <div className="flex justify-between">
            <span className="text-zinc-400">TailwindCSS Engine:</span>
            <span className="text-blue-800 font-semibold">✓ Estilizado</span>
          </div>
          <div className="flex justify-between">
            <span className="text-zinc-400">Shadcn/ui (Radix):</span>
            <span className="text-blue-800 font-semibold">✓ Pronto</span>
          </div>
        </div>

        {/* Teste Interativo */}
        <div className="space-y-4">
          <p className="text-sm text-zinc-300">
            Cliques registrados: <span className="font-bold text-xl text-white">{contador}</span>
          </p>
          
          <div className="flex gap-3 justify-center">
            {/* Botão padrão do Shadcn/ui */}
            <Button 
              onClick={() => setContador(contador + 1)}
              className="bg-blue-800 hover:bg-blue-700 hover:cursor-pointer text-white font-bold px-6"
            >
              Incrementar
            </Button>

            {/* Botão secundário do Shadcn/ui */}
            <Button 
              onClick={() => setContador(0)}
              className="bg-blue-800 hover:bg-blue-700 hover:cursor-pointer text-white font-bold px-6"
            >
              Resetar
            </Button>
          </div>
        </div>

      </div>
    </div>
  )
}

export default App