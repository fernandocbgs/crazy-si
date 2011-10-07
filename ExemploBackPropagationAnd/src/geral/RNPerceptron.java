package geral;
import java.util.ArrayList;
import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;

public class RNPerceptron implements IRN {
	
	private double taxaDeAprendizado;
	private ITreinador treinador;
	private ITestador testador;
	private IFuncaoAtivacao funcaoAtivacao;
	private ArrayList<double []> entradasSupervisionadas = new ArrayList<double []>();
	private ArrayList<double []> saidasDesejadas = new ArrayList<double []>();
	private ArrayList<INeuronioBackPropagation[]> camadasRN = new ArrayList<INeuronioBackPropagation[]>();
	
	public RNPerceptron(ArrayList<Integer> camadas, ITreinador treinador, ITestador testador, IFuncaoAtivacao funcaoAtivacao, double taxaDeAprendizado){
		this.treinador = treinador;
		treinador.setRN(this);
		this.testador = testador;
		testador.setRN(this);
		this.taxaDeAprendizado = taxaDeAprendizado;
		this.funcaoAtivacao = funcaoAtivacao;
		instancia(camadas);
	}
	
	@Override
	public void instancia(ArrayList<Integer> camadas) {
	    for(Integer camada : camadas){
	    	INeuronioBackPropagation[] neuronios = new INeuronioBackPropagation[camada];
	    	for(int i = 0; i < camada; i++){
	    		if(i == 0){
	    		    neuronios[i] =  new NeuronioBackPropagation(camadas.get(i), taxaDeAprendizado, funcaoAtivacao);
	    		}
	    		else{
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
}
