package geral;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import java.util.ArrayList;
import utils.EntradasList;
import utils.EntradasRN;
import interfaces.IRN;

/**
 * Classe abstrata que representa uma rede neural<br />
 * entradas de teste e de treinamento
 * 
 * @author Emerson / Lucas
 * */
public abstract class ARedeNeuralExecutor {
	
	protected IRN rn;
	public enum TIPO_EXECUTOR {
		LINEAR, NAO_LINEAR, FUTEBOL_ROBOS
	}	
	public ARedeNeuralExecutor(IRN redeNeural){
		this.rn = redeNeural;
	}
	
	public void executa(){
		adicionaEntradasTreinamento();
		rn.treina();
		ArrayList<double []> entradasTeste = new ArrayList<double []>();
		ArrayList<double []> saidasTeste = new ArrayList<double []>();
		adicionaEntradasTeste(entradasTeste, saidasTeste);
		rn.testa(entradasTeste, saidasTeste);
	}
	public abstract void adicionaEntradasTreinamento();
	public abstract void adicionaEntradasTeste(ArrayList<double []> entradasTeste, ArrayList<double []> saidasTeste);
	
	public static ARedeNeuralExecutor instancia(TIPO_EXECUTOR executor, IRN rn){
		switch(executor){
			case LINEAR: return new RedeNeuralExecutorLinear(rn); 
			case NAO_LINEAR: return new RedeNeuralExecutorNaoLinear(rn);
			case FUTEBOL_ROBOS: return new RedeNeuralFutebolRobos(rn);
		}
		return null;
	}
}

/**
 * Rede Neural Linear - AND
 * */
class RedeNeuralExecutorLinear extends ARedeNeuralExecutor{

	public RedeNeuralExecutorLinear(IRN redeNeural) {
		super(redeNeural);
	}

	@Override
	public void adicionaEntradasTreinamento() {
		for(double i = 0.1; i <= 0.2; i+=0.01){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{1, 0});
		}
		for(double i = 0.3; i <= 0.4; i+=0.01){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{1, 0});
		}
		for(double i = 0.5; i <= 0.6; i+=0.01){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{0, 1});
		}
		for(double i = 0.8; i <= 0.9; i+=0.01){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{0, 1});
		}
	}

	@Override
	public void adicionaEntradasTeste(ArrayList<double []> entradasTeste, ArrayList<double []> saidasTeste) {
		for(double i = 0.4; i <= 0.5; i+=0.01){
			entradasTeste.add(new double[]{i, i});
			saidasTeste.add(new double[]{1, 0});
		}
		for(double i = 0.6; i <= 0.7; i+=0.01){
			entradasTeste.add(new double[]{i, i});
			saidasTeste.add(new double[]{0, 1});
		}		
	}
}

/**
 * Rede Neural Não linear - função de 2º grau<br />
 * @see figura 'exemploNaoLinearExplicacao.png'
 * */
class RedeNeuralExecutorNaoLinear extends ARedeNeuralExecutor{

	public RedeNeuralExecutorNaoLinear(IRN redeNeural) {
		super(redeNeural);
	}

	@Override
	public void adicionaEntradasTreinamento() {
		for(double i = -200.0; i <= 100.0; i++){
			for(double j = -200.0; j <= 100.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2+3*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) < pow(j,valorAleatorio)){
					rn.insereEntradaSupervisionada(new double[]{i, j},new double[]{1, 1});
				}
			}
		}
		for(double i = -200.0; i <= 100.0; i++){
			for(double j = -200.0; j <= 100.0; j++){
				//sorteia um número aleatório entre 0 e 2 para sair acima da curva
				double b = 2*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) > pow(j,valorAleatorio)){
					rn.insereEntradaSupervisionada(new double[]{i, j},new double[]{1, 1});
				}
			}
		}			
	}
	@Override
	public void adicionaEntradasTeste(ArrayList<double[]> entradasTeste,ArrayList<double[]> saidasTeste) {
		for(double i = 300.0; i <= 320.0; i++){
			for(double j = 300.0; j <= 320.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2+3*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) > pow(j,valorAleatorio)){
					entradasTeste.add(new double[]{i, i});
					saidasTeste.add(new double[]{1, 1});
				}
			}
		}
		for(double i = 300.0; i <= 320.0; i++){
			for(double j = 300.0; j <= 320.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) < pow(j,valorAleatorio)){
					entradasTeste.add(new double[]{i, i});
					saidasTeste.add(new double[]{0, 0});
				}
			}
		}
	}	
}

/**
 * Rede Neural de Futebol de Robos
 * */
class RedeNeuralFutebolRobos extends ARedeNeuralExecutor{
	EntradasList listaDeValores = new EntradasList(); //realiza o controle dos dados de entrada, para evitar dados repetidos
	private EntradasList entradas = new EntradasList(); //realiza o controle dos dados de entrada, para evitar dados repetidos
	
	double porcentagemTreinamentoTeste = 0.1;
	private EntradasRN _entradasRN = new EntradasRN();
	
	public RedeNeuralFutebolRobos(IRN redeNeural) {
		super(redeNeural);
	}
	
	/**
	 * Adiciona as entradas de Treinamento da Rede Neural<br />
	 * Treinamento supervisionado
	 * */
	@Override
	public void adicionaEntradasTreinamento() {
		adicionaEntradasTreinamentoClasse1();
		adicionaEntradasTreinamentoClasse2();
		adicionaEntradasTreinamentoClasse3();
		adicionaEntradasTreinamentoClasse4();
	}
	
	/**
	 * Adiciona as entradas de Teste da Rede Neural
	 * */
	@Override
	public void adicionaEntradasTeste(ArrayList<double[]> entradasTeste, ArrayList<double[]> saidasTeste) {
		adicionaEntradasTesteClasse1(entradasTeste, saidasTeste);
		adicionaEntradasTesteClasse2(entradasTeste, saidasTeste);
		adicionaEntradasTesteClasse3(entradasTeste, saidasTeste);
		adicionaEntradasTesteClasse4(entradasTeste, saidasTeste);
	}

	//------------------ ENTRADAS DAS CLASSES DE TREINAMENTO -------------------------------------
	/**
	 * Para a classe 1 do artigo, correspondente a andar até a bola
	 */
	private void adicionaEntradasTreinamentoClasse1() {
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (1512.0 * porcentagemTreinamentoTeste);
		// Algoritmo de treinamento
		int numeroValoresTreinamentoAdicionados = 0;
		// chuta uma posição pra bola
		while (numeroValoresTreinamentoAdicionados < totalTreinamento) {
			int[] valoresDistintos = _entradasRN.gerar4ValoresDistintos0a8();

			double valoresTreinamento[] = new double[5];

			for (int i = 0; i < valoresTreinamento.length; i++) {
				valoresTreinamento[i] = valoresDistintos[i];
			}
			valoresTreinamento[4] = _entradasRN.gerarPosicaoGoleiro();
			
			
			if(listaDeValores.contem(valoresTreinamento)) {
				//não adiciona
			    continue;
			}
			
			// cria a entrada para o treinamento
			rn.insereEntradaSupervisionada(valoresTreinamento, new double[] { 0.0, 1.0 });
			numeroValoresTreinamentoAdicionados++;
			listaDeValores.adicionarEntrada(valoresTreinamento);
		}
	}
	
	/**
	 * Para a classe 2 do artigo, correspondente a ação andar com a bola
	 */
	private void adicionaEntradasTreinamentoClasse2() {
		// Casos tratados:
		// Todos os jogadores no meio
		// Alguns no meio e alguns na defesa (bola em algum dos jogadores do
		// meio)
		// todos na defesa
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (99 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			// pega o vetor de posições
			// a primeira posição é a bola
			int[] posicoes = _entradasRN.gerar6ValoresDistintosCamposDefesaMeio();
			int posicaoBola = posicoes[0];
			if (_entradasRN.naDefesa(posicaoBola)) {
				// treina com todo mundo na defesa
				double [] entradaTreinamento = new double[] {
						posicaoBola, 0, 3, 6, _entradasRN.gerarPosicaoGoleiro() };
				//para pular se já contem
				if(listaDeValores.contem(entradaTreinamento))
					continue;
				//adiciona
				rn.insereEntradaSupervisionada(entradaTreinamento, new double[] { 0.0, 0.0 });
				
				listaDeValores.adicionarEntrada(entradaTreinamento);
				//adiciona na lista
			} else {
				// treina com um na posição da bola e os demais podendo ficar no
				// meio ou defesa
				double [] entradaTreinamento = new double[] {
						posicaoBola, posicaoBola, posicoes[1], posicoes[2],
						_entradasRN.gerarPosicaoGoleiro()};
				//para pular se já contem
				if(listaDeValores.contem(entradaTreinamento))
					continue;
				
				rn.insereEntradaSupervisionada(entradaTreinamento, new double[] { 0.0, 0.0 });
				listaDeValores.adicionarEntrada(entradaTreinamento);
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 3 do artigo, correspondente a ação passar a bola
	 */
	private void adicionaEntradasTreinamentoClasse3() {
		int totalTreinamento = (int) (405 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;

		while (contagemTreinamento < totalTreinamento) {
			// chuncho
			double valorAleatorio = Math.random();
			if (valorAleatorio > 0.5) {
				double [] entradaTreinamento = new double[] {
						_entradasRN.getValorDefesa(), _entradasRN.getValorDefesa(),
						_entradasRN.getValorAtaque(), _entradasRN.getValorMeio(),
						_entradasRN.gerarPosicaoGoleiro() };
				if(listaDeValores.contem(entradaTreinamento))
					continue;
				rn.insereEntradaSupervisionada(entradaTreinamento, new double[] { 1.0, 0.0 });
				listaDeValores.adicionarEntrada(entradaTreinamento);
				
			} else {
				double [] entradaTreinamento = new double[] {
						_entradasRN.getValorMeio(), _entradasRN.getValorDefesa(),
						_entradasRN.getValorAtaque(), _entradasRN.getValorMeio(),
						_entradasRN.gerarPosicaoGoleiro() };
				if(listaDeValores.contem(entradaTreinamento))
					continue;
				
				rn.insereEntradaSupervisionada(entradaTreinamento, new double[] { 1.0, 0.0 });
				listaDeValores.adicionarEntrada(entradaTreinamento);
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 4 do artigo, correspondente a ação chutar a gol
	 */
	private void adicionaEntradasTreinamentoClasse4() {
		int totalTreinamento = (int) (252 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			int[] valoresDistintos = _entradasRN.gerar4ValoresDistintos0a8();
			int valorNoAtaque;
			for (valorNoAtaque = 0; valorNoAtaque < valoresDistintos.length; valorNoAtaque++) {
				if (_entradasRN.noAtaque(valoresDistintos[valorNoAtaque])) {
					break;
				}
			}
			double [] entradaTreinamento = new double[] {
					valoresDistintos[valorNoAtaque],
					valoresDistintos[valorNoAtaque],
					valoresDistintos[valorNoAtaque + 1],
					valoresDistintos[valorNoAtaque + 2],
					_entradasRN.gerarPosicaoGoleiro() };
			if(listaDeValores.contem(entradaTreinamento))
				continue;
			// adiciona para o treinamento
			rn.insereEntradaSupervisionada(entradaTreinamento, new double[] { 1.0, 1.0 });
			contagemTreinamento++;
			listaDeValores.adicionarEntrada(entradaTreinamento);
		}
	}
	
	//--------------------------------- ENTRADAS DE TESTE ---------------------
	/**
	 * Para a classe 1 do artigo, correspondente a andar até a bola
	 */
	public void adicionaEntradasTesteClasse1(ArrayList<double[]> entradasTeste,
			ArrayList<double[]> saidasTeste) {
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (1512.0 * porcentagemTreinamentoTeste);
		// Algoritmo de treinamento
		int numeroValoresTreinamentoAdicionados = 0;
		// chuta uma posição pra bola
		while (numeroValoresTreinamentoAdicionados < totalTreinamento) {
			int[] valoresDistintos = _entradasRN.gerar4ValoresDistintos0a8();
			double valoresTreinamento[] = new double[5];
			for (int i = 0; i < valoresTreinamento.length; i++) {
				valoresTreinamento[i] = valoresDistintos[i];
			}
			valoresTreinamento[4] = _entradasRN.gerarPosicaoGoleiro();
			if(entradas.contem(valoresTreinamento))
			{
				//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
				continue;
			}
			
			//testa
			entradasTeste.add(valoresTreinamento);
			saidasTeste.add(new double[] { 0.0, 1.0 });
			
	    	numeroValoresTreinamentoAdicionados++;
		}
	}

	/**
	 * Para a classe 2 do artigo, correspondente a ação andar com a bola
	 */
	public void adicionaEntradasTesteClasse2(ArrayList<double[]> entradasTeste,
			ArrayList<double[]> saidasTeste) {
		// Casos tratados:
		// Todos os jogadores no meio
		// Alguns no meio e alguns na defesa (bola em algum dos jogadores do
		// meio)
		// todos na defesa
		// Nessa classe os jogadores ocupam uma posição diferente da bola
		int totalTreinamento = (int) (99 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			// pega o vetor de posições
			// a primeira posição é a bola
			int[] posicoes = _entradasRN.gerar6ValoresDistintosCamposDefesaMeio();
			int posicaoBola = posicoes[0];
			if (_entradasRN.naDefesa(posicaoBola)) {
				double [] valoresEntrada = new double[] {posicaoBola, 0, 3, 6, _entradasRN.gerarPosicaoGoleiro()};
				if(entradas.contem(valoresEntrada)) {
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
				entradasTeste.add(valoresEntrada);
				saidasTeste.add(new double[] { 0.0, 0.0 });
			} else {
				double [] valoresEntrada = new double[] {posicaoBola, posicaoBola, posicoes[1], posicoes[2],
						_entradasRN.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
				entradasTeste.add(valoresEntrada);
				saidasTeste.add(new double[] { 0.0, 0.0 });
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 3 do artigo, correspondente a ação passar a bola
	 */
	public void adicionaEntradasTesteClasse3(ArrayList<double[]> entradasTeste,
			ArrayList<double[]> saidasTeste) {
		int totalTreinamento = (int) (405 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;

		while (contagemTreinamento < totalTreinamento) {
			// chuncho
			double valorAleatorio = Math.random();
			if (valorAleatorio > 0.5) {
			
				double [] valoresEntrada = new double[] {
						_entradasRN.getValorDefesa(), _entradasRN.getValorDefesa(),
						_entradasRN.getValorAtaque(), _entradasRN.getValorMeio(),
						_entradasRN.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
				entradasTeste.add(valoresEntrada);
				saidasTeste.add(new double[] { 1.0, 0.0 });
			
			} else {
				double [] valoresEntrada = new double[] {
						_entradasRN.getValorMeio(), _entradasRN.getValorDefesa(),
						_entradasRN.getValorAtaque(), _entradasRN.getValorMeio(),
						_entradasRN.gerarPosicaoGoleiro() };
				if(entradas.contem(valoresEntrada))
				{
					//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
					continue;
				}
				//testa
				entradasTeste.add(valoresEntrada);
				saidasTeste.add(new double[] { 1.0, 0.0 });
			}
			contagemTreinamento++;
		}
	}

	/**
	 * Para a classe 4 do artigo, correspondente a ação chutar a gol
	 */
	public void adicionaEntradasTesteClasse4(ArrayList<double[]> entradasTeste,
			ArrayList<double[]> saidasTeste) {
		int totalTreinamento = (int) (252 * porcentagemTreinamentoTeste);
		int contagemTreinamento = 0;
		while (contagemTreinamento < totalTreinamento) {
			int[] valoresDistintos = _entradasRN.gerar4ValoresDistintos0a8();
			int valorNoAtaque;
			for (valorNoAtaque = 0; valorNoAtaque < valoresDistintos.length; valorNoAtaque++) {
				if (_entradasRN.noAtaque(valoresDistintos[valorNoAtaque])) {
					break;
				}
			}

			double [] valoresEntrada = new double[]{valoresDistintos[valorNoAtaque],
	    			valoresDistintos[valorNoAtaque],
	    			valoresDistintos[valorNoAtaque + 1],
	    			valoresDistintos[valorNoAtaque + 2],
	    			_entradasRN.gerarPosicaoGoleiro()};
	
			if(entradas.contem(valoresEntrada))
			{
				//não pode fazer o teste com variáveis que já foram utilizadas no treinamento
				continue;
			}
			
			entradasTeste.add(valoresEntrada);
			saidasTeste.add(new double[] { 1.0, 1.0 });			
	    	//
			contagemTreinamento++;
		}
	}
}