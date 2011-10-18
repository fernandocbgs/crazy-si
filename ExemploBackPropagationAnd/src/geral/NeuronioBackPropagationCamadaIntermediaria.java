package geral;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;

public class NeuronioBackPropagationCamadaIntermediaria extends NeuronioBackPropagation {

    private INeuronioBackPropagation [] neuroniosUltimaCamada;
    private ArrayList<double []> entradasPropagadas;

	public NeuronioBackPropagationCamadaIntermediaria(int numeroDeEntradas,
			double taxaDeAprendizado, IFuncaoAtivacao funcaoAtivacao, INeuronioBackPropagation [] neuroniosUltimaCamada) {
		super(numeroDeEntradas, taxaDeAprendizado, funcaoAtivacao);
		this.neuroniosUltimaCamada = neuroniosUltimaCamada;
	}
	
	/**
	 * j = indice deste neuronio
	 */
	public void calculaAjustes(ArrayList<double[]> entradas, ArrayList<double[]> saidasRede, int j){
		//para cada peso i
		for(int i = 0; i < this.pesos.length; i++){
	 		double djDw = 0.0;
			for(int p = 0; p < entradas.size(); p++){
				double somatorioSigmaW = 0.0;
				for(int k = 0; k < neuroniosUltimaCamada.length; k++){
					NeuronioBackPropagationUltimaCamada neuron = (NeuronioBackPropagationUltimaCamada) neuroniosUltimaCamada[k];
					somatorioSigmaW += neuron.getSigmaW(j, entradasPropagadas.get(p), saidasRede.get(p), k);
				}
				double uW = calculaUW(entradas.get(p));
				djDw += somatorioSigmaW*funcaoAtivacao.derivada(uW)*entradas.get(p)[i];
			    somatorioSigmaW = 0;
			}
			ajustes[i] = taxaDeAprendizado*djDw;
			System.out.println("Ajustes " + ajustes[i]);
		}
	}
	
	public void setEntradasPropagadas(ArrayList<double []> entradasPropagadas){
		this.entradasPropagadas = entradasPropagadas;
	}
}
