package geral;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;

public class NeuronioBackPropagationCamadaIntermediaria extends NeuronioBackPropagation {

    private NeuronioBackPropagationUltimaCamada [] neuroniosUltimaCamada;

	public NeuronioBackPropagationCamadaIntermediaria(int numeroDeEntradas,
			double taxaDeAprendizado, IFuncaoAtivacao funcaoAtivacao, NeuronioBackPropagationUltimaCamada [] neuroniosUltimaCamada) {
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
					somatorioSigmaW += neuroniosUltimaCamada[k].getSigmaW(j, entradas.get(p), saidasRede.get(p), k);
				}
				double uW = calculaUW(entradas.get(p));
				djDw += somatorioSigmaW*funcaoAtivacao.derivada(uW)*entradas.get(p)[j];					
			    somatorioSigmaW = 0;
			}
			ajustes[i] = taxaDeAprendizado*djDw;
		}
	}
}
