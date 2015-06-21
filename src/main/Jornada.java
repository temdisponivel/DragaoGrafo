package main;

import main.Grafo.Vertice;

public class Jornada
{
	public class Golpe
	{
		int dano = 0;
		int recuperacao = 0;
		int diferenca = 0;
		
		public Golpe(int dano, int recuperacao)
		{
			this.dano = dano;
			this.recuperacao = recuperacao;
			this.diferenca = this.dano - this.recuperacao;
		}
		
		@Override
		public String toString()
		{
			return "DANO: " + dano + " | RECURAÇÃO: " + recuperacao + " | DIFERENÇA: " + diferenca;
		}
	}
	
	public class GrupoGolpe
	{
		Golpe[] golpes = null;
		Golpe melhor = null;
		
		public GrupoGolpe(Golpe... golpes)
		{
			this.golpes = golpes;
			
			for (int i = 0; i < golpes.length; i++)
			{
				
				if (melhor == null)
				{
					melhor = golpes[i];
					continue;
				}
				
				if (melhor.diferenca < golpes[i].diferenca)
				{
					melhor = golpes[i];
				}
			}
		}
	}
	
	public void inicia()
	{
		Grafo grafo = new Grafo();
		GrupoGolpe grupo = new GrupoGolpe(new Golpe[]{new Golpe(15, 24), new Golpe(17, 2), new Golpe(20, 14), new Golpe(5, 17)});
		int cabecas = 100;

		//controi raiz
		Vertice anterior, aux;
		Golpe golpeAux, atual = null;
		
		grafo.SetInicio(anterior = new Vertice(grupo.melhor));
		atual = grupo.melhor;
		cabecas -= atual.dano;
		
		while (cabecas > 0 && cabecas < 1000)
		{
			cabecas += atual.recuperacao;
			
			atual = grupo.melhor;
			
			if (cabecas < atual.dano)
			{
				Golpe melhor = null;
				for (int i = 0; i < grupo.golpes.length; i++)
				{
					if (melhor == null)
					{
						melhor = grupo.golpes[i];
						continue;
					}
					
					golpeAux = grupo.golpes[i];
					
					if (golpeAux.dano < cabecas && golpeAux.diferenca > melhor.diferenca)
					{
						melhor = golpeAux;
					}
				}
				
				atual = melhor;
			}
			
			cabecas -= atual.dano;
			
			grafo.Adicionar(anterior, aux = new Vertice(atual));
			anterior = aux;
		}
		
		System.out.println("CABEÇAS: " + cabecas);
		grafo.PercursoProfundidade();
	}
}
