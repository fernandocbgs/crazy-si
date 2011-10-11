package geral;

import static java.lang.Math.random;
import static utils.Utils.erro;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;

public class NeuronioBackPropagationUltimaCamada extends NeuronioBackPropagation {
	
	/**
	 * Erro do backpropagation ou sigma*xpkj p = indice entrada treinamento, k = indice deste neuronio, j = peso relacionado ao ebp
	 */

	public NeuronioBackPropagationUltimaCamada(int numeroDeEntradas,
			double taxaDeAprendizado, IFuncaoAtivacao funcaoAtivacao) {
		super(numeroDeEntradas, taxaDeAprendizado, funcaoAtivacao);
	}
	
	public double getSigmaW(int j, double [] entrada, double [] saidaEsperada, int indiceDesteNeuronio){
		double uW = calculaUW(entrada);
	    return pesos[j]*(erro(getSaida(uW), saidaEsperada[indiceDesteNeuronio]))*funcaoAtivacao.derivada(uW);	
	}
}
