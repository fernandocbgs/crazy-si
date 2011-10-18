package geral;
import java.util.ArrayList;

import funcoes.FuncaoCossenoidal;
import funcoes.FuncaoIdentidade;
import funcoes.FuncaoSigmoide;
import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;

public class RNPerceptron implements IRN {
	
	public enum TIPO_FUNCAO {
		sigmodal,
		cossenoidal
	}
	
	public enum TIPO_RN{
		UMA_CAMADA, DUAS_CAMADAS
	}
	
	protected double taxaDeAprendizado;
	protected ITreinador treinador;
	protected ITestador testador;
	protected IFuncaoAtivacao funcaoAtivacao;
	protected IFuncaoAtivacao funcaoIdentidade = new FuncaoIdentidade();
	protected ArrayList<double []> entradasSupervisionadas = new ArrayList<double []>();
	protected ArrayList<double []> saidasDesejadas = new ArrayList<double []>();
	protected ArrayList<INeuronioBackPropagation[]> camadasRN = new ArrayList<INeuronioBackPropagation[]>();
	
	protected RNPerceptron(ArrayList<Integer> camadas, ITreinador treinador, ITestador testador, TIPO_FUNCAO tpfuncao, double taxaDeAprendizado){
		this.treinador = treinador;
		treinador.setRN(this);
		this.testador = testador;
		testador.setRN(this);
		this.taxaDeAprendizado = taxaDeAprendizado;
		this.funcaoAtivacao = getFuncaoAtivacao(tpfuncao);
		instancia(camadas);
	}
	
	@Override
	public void instancia(ArrayList<Integer> camadas) {
	    for(int k = 0; k < camadas.size(); k++){
	    	INeuronioBackPropagation[] neuronios = new INeuronioBackPropagation[camadas.get(k)];
	    	for(int i = 0; i < camadas.get(k); i++){
	    		if(k == 0){
	    			//na primeira camada o número de entradas = número de neurônios desta camada
	    		    neuronios[i] =  new NeuronioBackPropagation(camadas.get(i), taxaDeAprendizado, funcaoAtivacao);
	    		}
	    		else{
	    			//quando não é a primeira camada, o número de entradas é o número de neurônios na camada anterior
	    			neuronios[i] = new NeuronioBackPropagation(camadas.get(i-1), taxaDeAprendizado, funcaoAtivacao);
	    		}
	    	}
	    	camadasRN.add(neuronios);
	    }
	}

	@Override
	public void treina() {
	    treinador.treinaRede();			
	}
	
	@Override
	public void testa(ArrayList<double[]> entradasTeste,ArrayList<double[]> saidasTeste) {
	   testador.testa(entradasTeste, saidasTeste);			
	}	

	@Override
	public void insereEntradaSupervisionada(double[] entrada, double[] saida) {
		entradasSupervisionadas.add(entrada);	
		saidasDesejadas.add(saida);
	}

	@Override
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao) {
		this.funcaoAtivacao = funcaoAtivacao;		
	}

	@Override
	public void setTreinador(ITreinador treinador) {
		this.treinador = treinador;		
	}	
	
	@Override
	public ArrayList<double[]> getEntradasSupervisionadas() {
		return entradasSupervisionadas;
	}

	@Override
	public ArrayList<INeuronioBackPropagation[]> getNeuronios() {
		return camadasRN;
	}

	@Override
	public ArrayList<double[]> getSaidasDesejadas() {
		return saidasDesejadas;
	}
	
	/**
	 * @return uma funcao sigmodal ou cossenoidal
	 * */
	private IFuncaoAtivacao getFuncaoAtivacao(TIPO_FUNCAO tp) {
		switch (tp) {
			case sigmodal: return new FuncaoSigmoide();
			case cossenoidal: return new FuncaoCossenoidal();
		}
		return new FuncaoSigmoide();
	}
	
	public static RNPerceptron getInstancia(TIPO_RN tipo, ArrayList<Integer> camadas, ITreinador treinador, ITestador testador, TIPO_FUNCAO tpfuncao, double taxaDeAprendizado){
		switch(tipo){
		case UMA_CAMADA : 
			return new RNPerceptron(camadas, treinador, testador, tpfuncao, taxaDeAprendizado);
		case DUAS_CAMADAS:
			return new RNPerceptron2Camadas(camadas, treinador, testador, tpfuncao, taxaDeAprendizado);
		}
		return null;
	}

	@Override
	public double[] getSaida(double[] entrada) {
		
		double [] entradaProximaCamada = null;
		INeuronioBackPropagation [] neuronios;
		
		for(int i = 0; i < camadasRN.size(); i++){
			neuronios = camadasRN.get(i);
			//calcula a saida para cada camada
			entradaProximaCamada = new double[neuronios.length];
			for(int j = 0; j < neuronios.length; j++){
				//iguala cada saida a saida de cada neurônio da camada
				entradaProximaCamada[j] = neuronios[j].getSaida(entrada);
			}
			//joga na próxima camada
			entrada = entradaProximaCamada;
		}
		return entradaProximaCamada;
	}
}

class RNPerceptron2Camadas extends RNPerceptron{

	protected RNPerceptron2Camadas(ArrayList<Integer> camadas,
			ITreinador treinador, ITestador testador, TIPO_FUNCAO tpfuncao,
			double taxaDeAprendizado) {
		super(camadas, treinador, testador, tpfuncao, taxaDeAprendizado);
	}
	
	@Override
	public void instancia(ArrayList<Integer> camadas) {
	    for(int k = camadas.size()-1; k >= 0; k--){
	    	INeuronioBackPropagation[] neuronios = new INeuronioBackPropagation[camadas.get(k)];
	    	for(int i = 0; i < camadas.size(); i++){
	    		if(k == 0){
	    			neuronios[i] =  new NeuronioBackPropagationCamadaIntermediaria(camadas.get(1), taxaDeAprendizado, funcaoAtivacao,camadasRN.get(camadasRN.size()-1));
	    		}
                if(k == 1){
	    			//quando não é a primeira camada, o número de entradas é o número de neurônios na camada anterior
	    			neuronios[i] =  new NeuronioBackPropagationUltimaCamada(camadas.get(0), taxaDeAprendizado, funcaoAtivacao);
	    		}
	    	}
	    	camadasRN.add(neuronios);
	    }
	}	
}