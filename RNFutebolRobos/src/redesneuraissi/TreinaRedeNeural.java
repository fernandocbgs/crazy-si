/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redesneuraissi;

import java.util.ArrayList;
import java.util.Random;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.DynamicBackPropagation;

/**
 * Cria uma rede neural e um conjunto de treinamento
 * 
 * @author Emerson
 * @author Lucas
 */
public class TreinaRedeNeural {

	Utils metodos = new Utils();

	Random geradorNumerosAleatorios = new Random();

	double porcentagemTreinamento = 0.1;
	/**
	 * Número de entradas para a rede neural
	 */
	int numeroDeEntradas = 5;
	/**
	 * Número de saídas para a rede neural
	 */
	int numeroDeSaidas = 2;
	/**
	 * Arquivo correspondente a rede neural
	 */
	String MLP_FILE = "MLP.nnet";
	/**
	 * Nome do arquivo de testes
	 */
	String TS_FILE = "Set.set";
	/**
	 * Conjunto de treinamento com 5 campos de entrada (vide problema) e 2
	 * campos de saida
	 * 
	 * 5 Entradas: 1 para a bola, 3 para os jogadores e 1 para saída
	 */
	TrainingSet training = new TrainingSet(numeroDeEntradas, numeroDeSaidas);
	/**
	 * Classe necessária para realizar o backpropagation
	 */
	DynamicBackPropagation learning;
	/**
	 * MultilayerPerceptron ou rede neural
	 */
	MultiLayerPerceptron mlp;

	/**
	 * Cria uma rede neural para testes O conteúdo dela é salvo em um arquivo
	 */
	public TreinaRedeNeural() {
		// necessário para realizar o backPropagation
		learning = new DynamicBackPropagation();
		learning.setMaxIterations(500);
		learning.setLearningRate(0.7);
		// Número de camadas
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		// camada 1 : 2 neuronios
		camadas.add(numeroDeEntradas);
		// Camada 2 : 12 neuronios
//		camadas.add(12);
		// Camada 3 : 2 neuronios
		camadas.add(numeroDeSaidas);
		// Cria um perceptron
		mlp = new MultiLayerPerceptron(camadas);
		// adiciona as entradas de treinamento
		adicionaEntradasDeTreinamento();
		// treina a rede
		treinarRede();
		// salvando arquivos
		mlp.save(MLP_FILE);
		training.save(TS_FILE);
	}

	/**
	 * Adiciona entradas para fazer o treinamento
	 */
	public void adicionaEntradasDeTreinamento() {
		adicionaEntradasTreinamentoClasse1();
		adicionaEntradasTreinamentoClasse2();
		adicionaEntradasTreinamentoClasse3();
		adicionaEntradasTreinamentoClasse4();
		// aqui são adicionadas algumas entradas aleatórias e calculadas algumas
		// saídas
	}

	/**
	 * Para a classe 1 do artigo, correspondente a ação pegar a bola, treina a
	 * rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse1() {
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (1512.0 * porcentagemTreinamento);
		// Algoritmo de treinamento
		int numeroValoresTreinamentoAdicionados = 0;
		// chuta uma posição pra bola
		while (numeroValoresTreinamentoAdicionados < totalTreinamento) {
			int[] valoresDistintos = metodos.gerar4ValoresDistintos0a8();

			double valoresTreinamento[] = new double[5];

			for (int i = 0; i < valoresTreinamento.length; i++) {
				valoresTreinamento[i] = valoresDistintos[i];
			}
			valoresTreinamento[4] = metodos.gerarPosicaoGoleiro();
			// cria a entrada para o treinamento
			training.addElement(new SupervisedTrainingElement(
					valoresTreinamento, new double[] { 0.0, 1.0 }));
			numeroValoresTreinamentoAdicionados++;
		}
	}

	/**
	 * Para a classe 2 do artigo, correspondente a ação andar com a bola, treina
	 * a rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse2() {
		// Casos tratados:
		// Todos os jogadores no meio
		// Alguns no meio e alguns na defesa (bola em algum dos jogadores do
		// meio)
		// todos na defesa
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (180 * porcentagemTreinamento);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			// pega o vetor de posições
			// a primeira posição é a bola
			int[] posicoes = metodos.gerar6ValoresDistintosCamposDefesaMeio();
			int posicaoBola = posicoes[0];
			if (metodos.naDefesa(posicaoBola)) {
				// treina com todo mundo na defesa
				training.addElement(new SupervisedTrainingElement(new double[] {
						posicaoBola, 0, 3, 6, metodos.gerarPosicaoGoleiro() },
						new double[] { 0.0, 0.0 }));
			} else {
				// treina com um na posição da bola e os demais podendo ficar no
				// meio ou defesa
				training.addElement(new SupervisedTrainingElement(new double[] {
						posicaoBola, posicaoBola, posicoes[1], posicoes[2],
						metodos.gerarPosicaoGoleiro() }, new double[] { 0.0,
						0.0, 0.0 }));
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 3 do artigo, correspondente a ação andar com a bola, treina
	 * a rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse3() {
		int totalTreinamento = (int) (180 * porcentagemTreinamento);
		int contagemTreinamento = 0;

		while (contagemTreinamento < totalTreinamento) {
			// chuncho
			double valorAleatorio = Math.random();
			if (valorAleatorio > 0.5) {
				training.addElement(new SupervisedTrainingElement(new double[] {
						metodos.getValorDefesa(), metodos.getValorDefesa(),
						metodos.getValorAtaque(), metodos.getValorMeio(),
						metodos.gerarPosicaoGoleiro() }, new double[] { 1.0,
						0.0 }));
			} else {
				training.addElement(new SupervisedTrainingElement(new double[] {
						metodos.getValorMeio(), metodos.getValorDefesa(),
						metodos.getValorAtaque(), metodos.getValorMeio(),
						metodos.gerarPosicaoGoleiro() }, new double[] { 1.0,
						0.0 }));
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 4 do artigo, correspondente a ação chutar a gol, treina a
	 * rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse4() {
		int totalTreinamento = (int) (180 * porcentagemTreinamento);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			int[] valoresDistintos = metodos.gerar4ValoresDistintos0a8();
			int valorNoAtaque;
			for (valorNoAtaque = 0; valorNoAtaque < valoresDistintos.length; valorNoAtaque++) {
				if (metodos.noAtaque(valoresDistintos[valorNoAtaque])) {
					break;
				}
			}
			// adiciona para o treinamento
			training.addElement(new SupervisedTrainingElement(new double[] {
					valoresDistintos[valorNoAtaque],
					valoresDistintos[valorNoAtaque],
					valoresDistintos[valorNoAtaque + 1],
					valoresDistintos[valorNoAtaque + 2],
					metodos.gerarPosicaoGoleiro() }, new double[] { 1.0, 1.0 }));
			contagemTreinamento++;
		}
	}

	/**
	 * Treina a rede e salva no arquivo do conjunto de treinamento
	 */
	public void treinarRede() {
		// Treina a rede!
		mlp.learnInSameThread(training, learning);
	}
}
