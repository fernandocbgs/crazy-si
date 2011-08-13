import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlgoritmoGenetico {

	protected int totalGenesDoCromossomo = 48;
	protected int totalCromossomos; // número de cromossomos
	protected List<Cromossomo> listaDeCromossomos;
	private float taxaCrossover;
	private float taxaMutacao;
	private int[] roleta;
	private int tamanhoRoleta;
	private int totalGeracoes;

	public AlgoritmoGenetico(int total_cromossomos,float taxa_crossover, float taxa_mutacao, int totalGeracoes) {
		taxaCrossover = taxa_crossover;
		taxaMutacao = taxa_mutacao;
		listaDeCromossomos = new ArrayList<Cromossomo>(total_cromossomos);
		this.totalGeracoes = totalGeracoes;
		
		
		//inicia a população
		
		for (int i = 0; i < total_cromossomos; i++) {
			listaDeCromossomos.add(new Cromossomo());
		}

		ordenar();

		// define a roleta:
		tamanhoRoleta = 0;
		for (int i = 0; i < totalGenesDoCromossomo; i++) {
			tamanhoRoleta += i + 1;
		}

		roleta = new int[tamanhoRoleta];
		int total_tentativas = totalGenesDoCromossomo;
		int index = 0;
		for (int i = 0; i < totalCromossomos; i++) {
			for (int j = 0; j < total_tentativas; j++) {
				roleta[index++] = i;
			}
			total_tentativas--;
		}
	}

	/**
	 * Método principal de execucão do algoritmo genético Nele são executadas as
	 * acões ou jogadas
	 */
	public void executar() {
		for(int i = 0; i <= totalGeracoes; i++)
		{
			calcularFitness();
			ordenar();
			crossovers();
			mutacoes();
			//fiz esse método porque alguns cromossomos ficam clonados
			//ainda não reimplementei, depois faço isso
			removerDuplicacoes();
		}
	}
	
	public void calcularFitness()
	{
		for(Cromossomo cromossomo : listaDeCromossomos)
		{
			cromossomo.calcularFitness();
		}
	}

	public void ordenar() {
		Collections.sort(listaDeCromossomos);
	}

	public void crossovers() {
		int num = (int) (totalCromossomos * taxaCrossover);
		for (int i = num - 1; i >= 0; i--) {
			// 8/11/2008: don't overwrite the "best" chromosome from current
			// generation:
			int c1 = 1 + (int) ((tamanhoRoleta - 1) * Math.random() * 0.9999f);
			int c2 = 1 + (int) ((tamanhoRoleta - 1) * Math.random() * 0.9999f);
			c1 = roleta[c1];
			c2 = roleta[c2];
			if (c1 != c2) {
				// Trocando uma acão qualquer de c1 por outra de c2
				//ops! C1 e c2 são os índices dos cromossomos
				Cromossomo cr1 = listaDeCromossomos.get(c1);
				Cromossomo cr2 = listaDeCromossomos.get(c2);
				//faz o crossover
				crossOver(cr1, cr2);
			}
		}
	}
	
	public void crossOver(Cromossomo cr1, Cromossomo cr2){
		//sorteia uma posi��o
		int posicao_crossover = sorteiaPosCrossover();
		BitSet bitsCr1 = cr1.getBitsMutacao(posicao_crossover);
		BitSet bitsCr2 = cr2.getBitsMutacao(posicao_crossover);
		//faz o crossover
		cr1.trocarBits(posicao_crossover, bitsCr2);
		cr2.trocarBits(posicao_crossover, bitsCr1);
		//e fez
	}

	public void mutacoes() {
		int num = (int) (totalCromossomos * taxaMutacao);
		for (int i = 0; i < num; i++) {
            listaDeCromossomos.get(i).mutacao();
		}
	}

	public void removerDuplicacoes() {
		for (int i = totalCromossomos - 1; i > 3; i--) {
			for (int j = 0; j < i; j++) {
				while (listaDeCromossomos.get(i).equals(listaDeCromossomos.get(j))) {
					listaDeCromossomos.set(j, new Cromossomo());
				}
			}
		}
	}
	
	public int sorteiaPosCrossover(){
		//necess�rio sortear um n�mero
		//entre 0, 6, 12, 18, 24
		int numeroRainha = (int)(6.0*(Math.random()))+1;
		//multiplicando por 6
		return numeroRainha*6;
	}
}