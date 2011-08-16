import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class AlgoritmoGenetico {

	protected int totalGenesDoCromossomo = 48;
	protected int totalCromossomos; // n�mero de cromossomos
	protected List<Cromossomo> listaDeCromossomos;
	private float taxaCrossover;
	private float taxaMutacao;
	private int[] roleta;
	private int tamanhoRoleta;
	private int totalGeracoes;
	private int totalIndividuosCriadosGeracao = 0;

	public AlgoritmoGenetico(int total_cromossomos,float taxa_crossover, float taxa_mutacao, int totalGeracoes) {
		taxaCrossover = taxa_crossover;
		taxaMutacao = taxa_mutacao;
		listaDeCromossomos = new ArrayList<Cromossomo>(total_cromossomos);
		this.totalGeracoes = totalGeracoes;
		totalCromossomos = total_cromossomos;
		
		
		//inicia a populaÃ§Ã£o
		
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
		for (int i = totalCromossomos-1; i >= 0 ; i--) {
			for (int j = 0; j < total_tentativas; j++) {
				roleta[index++] = i;
			}
			total_tentativas--;
		}
	}

	/**
	 * MÃ©todo principal de execucÃ£o do algoritmo genÃ©tico Nele sÃ£o executadas as
	 * acÃµes ou jogadas
	 */
	public void executar() {
		for(int i = 0; i <= totalGeracoes; i++)
		{
			totalIndividuosCriadosGeracao = 0;
			calcularFitness();
			//continua o algoritmo para saber quais são mais aptos
			ordenar();
			//resultado
			System.out.println("Gera��o: " + i+1 + " Cromossomo mais apto: " + listaDeCromossomos.get(totalCromossomos-1).getFitness() + " com n�mero de colis�es: " + listaDeCromossomos.get(totalCromossomos - 1).getNumeroDeColisoes());
			System.out.println("Gera��o: " + i+1 + " Cromossomo menos apto: " + listaDeCromossomos.get(0).getFitness() + " com n�mero de colis�es: " + listaDeCromossomos.get(0).getNumeroDeColisoes());
			//verifica se algu�m atingiu a condi��o de parada, se sim, para
			if(listaDeCromossomos.get(totalCromossomos-1).getFitness() == 1.0d)
			{
				System.out.println("Parou na gera��o " + i);
				listaDeCromossomos.get(totalCromossomos-1).printRainhas();
				break;
			}
			crossovers();
			mutacoes();
			//fiz esse mÃ©todo porque alguns cromossomos ficam clonados
			//ainda nÃ£o reimplementei, depois faÃ§o isso
			removerDuplicacoes();
		}
		calcularFitness();
		ordenar();
		printCromossomos();
	}
	
	public void printCromossomos()
	{
		for(Cromossomo cromo : listaDeCromossomos)
		{
			cromo.printRainhas();
			System.out.println(" com fitness = " + cromo.getFitness());
		}
	}
	
	public void removerDuplicacoes() {
		//pega o total de indivÃ­duos criados via mutação / crossover
		//esse total é fornecido como porcentagem em função do valor
		System.out.println("Total de indivíduos criados na geração: " + totalIndividuosCriadosGeracao);		
		//remove o mesmo tanto da lista
		for(int i = 0; i < totalIndividuosCriadosGeracao; i++)
		{
			listaDeCromossomos.remove(i);
		}
		System.out.println("Número de indivíduos " + listaDeCromossomos.size());
		//pronto! Agora tiramos os menos aptos e colocamos filhos na geração 
		
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
//		for(int i = 0; i < totalCromossomos; i++)
//		{
//			System.out.println(listaDeCromossomos.get(i).getFitness());
//		}
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
				// Trocando uma acÃ£o qualquer de c1 por outra de c2
				//ops! C1 e c2 sÃ£o os Ã­ndices dos cromossomos
				Cromossomo cr1 = listaDeCromossomos.get(c1);
				Cromossomo cr2 = listaDeCromossomos.get(c2);
				//faz o crossover
				crossOver(cr1, cr2);
			}
		}
	}
	
	public void crossOver(Cromossomo cr1, Cromossomo cr2){
		Cromossomo filho1 = cr1.clone();
		Cromossomo filho2= cr2.clone();
		//sorteia uma posi��o
		int [] rainhaPosicaoCromossomo1 = filho1.getValorParaCrossOver();
		int [] rainha2PosicaoCromossomo1 = filho1.getValorParaCrossOver();
		int rainhaCromossomo2 = filho2.getRainhaPorPosicao(rainhaPosicaoCromossomo1[1]);
		int posicaoRainhaCromossomo2 = filho2.getPosicaoRainha(rainhaCromossomo2);
		int rainha2Cromossomo2 = filho2.getRainhaPorPosicao(rainha2PosicaoCromossomo1[1]);
		int posicao2RainhaCromossomo2 = filho2.getPosicaoRainha(rainha2Cromossomo2);
		//faz o crossover
		//o crossover n�o�o elimina os mais aptos, apenas os substitui
		//temos 2 filhos
		filho1.setPosicaoRainha(rainhaPosicaoCromossomo1[0], posicao2RainhaCromossomo2);
		filho1.setPosicaoRainha(rainha2PosicaoCromossomo1[0], posicaoRainhaCromossomo2);
		filho2.setPosicaoRainha(rainhaCromossomo2, rainha2PosicaoCromossomo1[1]);
		filho2.setPosicaoRainha(rainha2Cromossomo2, rainhaPosicaoCromossomo1[1]);
		//e agora adicionamos na lista
		listaDeCromossomos.add(filho1);
		listaDeCromossomos.add(filho2);
		//agora incrementamos o valor da variável
		totalIndividuosCriadosGeracao+=2;
	}

	public void mutacoes() {
		int num = (int) (totalCromossomos * taxaMutacao);
		for (int i = 0; i < num; i++) {
            Cromossomo mutado = listaDeCromossomos.get(i).mutacao();
            listaDeCromossomos.add(mutado);
            totalIndividuosCriadosGeracao++;
		}
	}
	
	public int sorteiaPosCrossover(){
		//necessï¿½rio sortear um nï¿½mero
		//entre 0, 6, 12, 18, 24
		int numeroRainha = (int)(6.0*(Math.random()))+1;
		//multiplicando por 6
		return numeroRainha*6;
	}
}