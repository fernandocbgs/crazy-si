package geral;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;

public class NeuronioBackPropagationPrimeiraCamada implements INeuronioBackPropagation {
	
	@Override
	public void iniciaPesos(int numeroPesos) {
	}

	@Override
	public double[] getPesos() {
        return null;
	}

	@Override
	public void ajustaPesos() {
	}

	@Override
	public void calculaAjustes(ArrayList<double[]> entradas,
			ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio) {		
	}

	@Override
	public double getSaida(double uW) {
		return 0;
	}

	@Override
	public double getSaida(double[] entrada) {
		double uW = 0.0;
		for(int i = 0; i < entrada.length; i++)
		{
			uW += entrada[i];
		}
		return uW;
	}

	@Override
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao) {
		
	}

	@Override
	public void setTaxaDeAprendizado(double taxaDeAprendizado) {
		
	}

}
