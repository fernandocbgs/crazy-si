/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redesneuraissi;
import org.neuroph.core.*;
/**
 * Classe usada para treinar e testar a rede neural
 * @author Emerson
 * @author Lucas
 */
public class TestaRedeNeural {
	Utils metodos = new Utils();
	//porcentagem de teste
	double porcentagemTeste = 0.1;
	/**
	 * Erro mape para testes
	 */
	private ErroMape erroMape;
	/**
	 * Referência para a rede neural
	 */
    private NeuralNetwork neuralN;
    /**
     * Entradas utilizadas no treinamento
     */
    private EntradasList entradas;
    
    
    /**
     * Cria um teste de rede neural
     */
    public TestaRedeNeural(EntradasList entradas, ErroMape erroMape) {
    	this.entradas = entradas;
        neuralN = NeuralNetwork.load("MLP.nnet");
        this.erroMape = erroMape;
    }

     /**
     * Testa a rede neural na etapa de treinamento
     */
    public void testarRedeNeural(){
    	adicionaEntradasTreinamentoClasse1();
		adicionaEntradasTreinamentoClasse2();
		adicionaEntradasTreinamentoClasse3();
		adicionaEntradasTreinamentoClasse4();
    }
    
    public static double getDecimal(double [] valoresBinarios){
    	  int potencia = 1;
    	  double decimal = 0.0;
    	  for(int i = valoresBinarios.length - 1; i >= 0; i--){
    		  decimal += potencia*valoresBinarios[i];
    		  potencia *= 2;
    	  }
    	  return decimal;
    }
    
    /**
	 * Para a classe 1 do artigo, correspondente a ação pegar a bola, treina a
	 * rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse1() {
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (1512.0 * porcentagemTeste);
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
			if(entradas.contem(valoresTreinamento))
			{
				//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
				continue;
			}
			//testa
	    	neuralN.setInput(valoresTreinamento);
	    	neuralN.calculate();
	    	double encontrado = getDecimal(neuralN.getOutput());
	    	double esperado = 1.0;
	    	//calcula o erro
	    	erroMape.adicionarErro(valoresTreinamento, encontrado, esperado);
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
		int totalTreinamento = (int) (99 * porcentagemTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			// pega o vetor de posições
			// a primeira posição é a bola
			int[] posicoes = metodos.gerar6ValoresDistintosCamposDefesaMeio();
			int posicaoBola = posicoes[0];
			if (metodos.naDefesa(posicaoBola)) {
				double [] valoresEntrada = new double[] {posicaoBola, 0, 3, 6, metodos.gerarPosicaoGoleiro()};
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
		    	neuralN.setInput(valoresEntrada);
		    	neuralN.calculate();
		    	double encontrado = getDecimal(neuralN.getOutput());
		    	double esperado = 0.0;
		    	//calcula o erro
		    	erroMape.adicionarErro(valoresEntrada, encontrado, esperado);
			} else {
				double [] valoresEntrada = new double[] {posicaoBola, posicaoBola, posicoes[1], posicoes[2],
						metodos.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
		    	neuralN.setInput(valoresEntrada);
		    	neuralN.calculate();
		    	double encontrado = getDecimal(neuralN.getOutput());
		    	double esperado = 0.0;
		    	//calcula o erro
		    	erroMape.adicionarErro(valoresEntrada, encontrado, esperado);
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 3 do artigo, correspondente a ação andar com a bola, treina
	 * a rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse3() {
		int totalTreinamento = (int) (405 * porcentagemTeste);
		int contagemTreinamento = 0;

		while (contagemTreinamento < totalTreinamento) {
			// chuncho
			double valorAleatorio = Math.random();
			if (valorAleatorio > 0.5) {
			
				double [] valoresEntrada = new double[] {
						metodos.getValorDefesa(), metodos.getValorDefesa(),
						metodos.getValorAtaque(), metodos.getValorMeio(),
						metodos.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
		    	neuralN.setInput(valoresEntrada);
		    	neuralN.calculate();
		    	double encontrado = getDecimal(neuralN.getOutput());
		    	double esperado = 2.0;
		    	//calcula o erro
		    	erroMape.adicionarErro(valoresEntrada, encontrado, esperado);
				
			} else {
				double [] valoresEntrada = new double[] {
						metodos.getValorMeio(), metodos.getValorDefesa(),
						metodos.getValorAtaque(), metodos.getValorMeio(),
						metodos.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
		    	neuralN.setInput(valoresEntrada);
		    	neuralN.calculate();
		    	double encontrado = getDecimal(neuralN.getOutput());
		    	double esperado = 2.0;
		    	//calcula o erro
		    	erroMape.adicionarErro(valoresEntrada, encontrado, esperado);
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 4 do artigo, correspondente a ação chutar a gol, treina a
	 * rede para 10% dos casos
	 */
	public void adicionaEntradasTreinamentoClasse4() {
		int totalTreinamento = (int) (252 * porcentagemTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			int[] valoresDistintos = metodos.gerar4ValoresDistintos0a8();
			int valorNoAtaque;
			for (valorNoAtaque = 0; valorNoAtaque < valoresDistintos.length; valorNoAtaque++) {
				if (metodos.noAtaque(valoresDistintos[valorNoAtaque])) {
					break;
				}
			}

			double [] valoresEntrada = new double[]{valoresDistintos[valorNoAtaque],
	    			valoresDistintos[valorNoAtaque],
	    			valoresDistintos[valorNoAtaque + 1],
	    			valoresDistintos[valorNoAtaque + 2],
	    			metodos.gerarPosicaoGoleiro()};
	
			if(entradas.contem(valoresEntrada))
			{
				//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
				continue;
			}
			
	    	neuralN.setInput(valoresEntrada);
	    	neuralN.calculate();
	    	double encontrado = getDecimal(neuralN.getOutput());
	    	double esperado = 3.0;
	    	//calcula o erro
	    	erroMape.adicionarErro(valoresEntrada, encontrado, esperado);
	    	//
			contagemTreinamento++;
		}
	}
}
