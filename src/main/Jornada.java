package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Grafo.Vertice;

public class Jornada
{
	public class Golpe
	{
		int dano = 0;
		int recuperacao = 0;
		int diferenca = 0;
		int usado = 0;
		
		public Golpe(int dano, int recuperacao)
		{
			this.dano = dano;
			this.recuperacao = recuperacao;
			this.diferenca = this.dano - this.recuperacao;
		}
		
		@Override
		public String toString()
		{
			usado++;
			return "";
			//return "GOLPE USADO " + (++usado) + " VEZES";
			//return "DANO: " + dano + " | RECURAÇÃO: " + recuperacao + " | DIFERENÇA: " + diferenca;
		}
	}
	
	public class GrupoGolpe
	{
		List<Golpe> golpes = null;
		Golpe melhor = null;
		
		public GrupoGolpe(List<Golpe> golpe)
		{
			this.golpes = golpe;
			
			for (int i = 0; i < golpes.size(); i++)
			{
				
				if (melhor == null)
				{
					melhor = golpes.get(i);
					continue;
				}
				
				if (melhor.diferenca < golpes.get(i).diferenca)
				{
					melhor = golpes.get(i);
				}
			}
		}
	}
	
	public void inicia()
	{
		Grafo grafo = new Grafo();
		GrupoGolpe grupo = null;
		
		List<Golpe> golpes = null;
		Golpe golpeAuxIn = null;
		
		int[] CiVector = null, NiVector = null;
		int G, somaGolpes;
		
		Scanner in = new Scanner(System.in);

		while(true){
			System.out.println("INSIRA O NUMERO DE GOLPES: ");
			G = in.nextInt();
			
			 if(G <= 0)
			    break;
			 
			 golpes = new ArrayList<Golpe>();
			 
			 CiVector = new int [G];
			 NiVector = new int [G];	
			    
			for(int i = 0; i < G; i++){
				System.out.println("INSIRA O DANO DO " + (i+1) + "º GOLPE: ");
				CiVector[i] = in.nextInt();
			}
			
			for(int i = 0; i < G; i++){
				System.out.println("INSIRA A RECUPERACAO DO " + (i+1) + "º GOLPE: ");
				NiVector[i] = in.nextInt();
			}
			
			
			
			for(int i = 0; i < G; i ++){
				golpeAuxIn = new Golpe(CiVector[i], NiVector[i]);
				golpes.add(golpeAuxIn);
			}
			
			grupo = new GrupoGolpe(golpes);
				
			//constroi raiz
			Vertice<Golpe> anterior, aux;
			Golpe golpeAux, atual = null;
			
			int cabecas = 100;
			
			grafo.SetInicio(anterior = new Vertice<Golpe>(grupo.melhor));
			atual = grupo.melhor;
			cabecas -= atual.dano;
			
			while (cabecas > 0 && cabecas < 1000)
			{
				cabecas += atual.recuperacao;
				
				atual = grupo.melhor;
				
				if (cabecas < atual.dano)
				{
					Golpe melhor = null;
					for (int i = 0; i < grupo.golpes.size(); i++)
					{
						if (melhor == null)
						{
							melhor = grupo.golpes.get(i);
							continue;
						}
						
						golpeAux = grupo.golpes.get(i);
						
						if (golpeAux.dano < cabecas && golpeAux.diferenca > melhor.diferenca)
						{
							melhor = golpeAux;
						}
					}
					
					atual = melhor;
				}
				
				cabecas -= atual.dano;
				
				grafo.Adicionar(anterior, aux = new Vertice<Golpe>(atual));
				anterior = aux;
			}
			
			//System.out.println("CABEÇAS: " + cabecas);
			grafo.PercursoProfundidade();
			
			somaGolpes = 0;
			
			//Mostra a soma do uso de todos os golpes
			for(int i = 0; i < golpes.size(); i++){
				somaGolpes += golpes.get(i).usado;
				golpes.get(i).usado = 0;
			}
			System.out.println("QUANTIDADE DE GOLPES REALIZADOS: " + somaGolpes);
			System.out.println("============================");
		}
		
		//System.out.println("cavaleiro morreu");
		
		in.close(); //fecha scanner

	}
	
	
}
