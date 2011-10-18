package auxiliarRN;

import java.util.ArrayList;

import geral.NeuronioBackPropagationCamadaIntermediaria;
import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITreinador;

public class TreinadorBPRN2Camadas implements ITreinador{
	
	private IRN redeNeural;
	private int ciclosTreinamento;
	
	public TreinadorBPRN2Camadas(int ciclosTreinamento){
		this.ciclosTreinamento = ciclosTreinamento;
	}

	@Override
	public void treinaRede() {
	    //para cada neuronio da camada de saida
		INeuronioBackPropagation [] ultimaCamada = redeNeural.getNeuronios().get(1);
		INeuronioBackPropagation [] camadaIntermediaria = redeNeural.getNeuronios().get(0);
		
		for(int cicloAtual = 0; cicloAtual < ciclosTreinamento; cicloAtual++){
			for(int k = 0; k < ultimaCamada.length; k++){
				INeuronioBackPropagation neuronio = ultimaCamada[k];
				neuronio.calculaAjustes(getEntradasUltimaCamada(redeNeural.getEntradasSupervisionadas(), camadaIntermediaria), redeNeural.getSaidasDesejadas(), k);
			}
			//calculados os ajustes vamos a cada neur�nio da camada de entrada
			
			for(int j = 0; j < camadaIntermediaria.length; j++){
				System.out.println("Neuronio " + j);
				INeuronioBackPropagation neuronio = camadaIntermediaria[j];
				NeuronioBackPropagationCamadaIntermediaria neuron = (NeuronioBackPropagationCamadaIntermediaria) neuronio;
				neuron.setEntradasPropagadas(getEntradasUltimaCamada(redeNeural.getEntradasSupervisionadas(), camadaIntermediaria));
				neuronio.calculaAjustes(redeNeural.getEntradasSupervisionadas(), redeNeural.getSaidasDesejadas(), j);
			}
			
			//manda todo mundo ajustar
	
			for(int k = 0; k < ultimaCamada.length; k++){
				INeuronioBackPropagation neuronio = ultimaCamada[k];
				neuronio.ajustaPesos();
			}
			//calculados os ajustes vamos a cada neur�nio da camada de entrada
			
			for(int j = 0; j < camadaIntermediaria.length; j++){
				INeuronioBackPropagation neuronio = camadaIntermediaria[j];
				neuronio.ajustaPesos();
			}
		}		
	}
	
	public ArrayList<double []> getEntradasUltimaCamada(ArrayList<double []> entradas, INeuronioBackPropagation [] camadaIntermediaria){
		ArrayList<double []> entradasUltimacamada = new ArrayList<double []>();
		for(double [] entrada : entradas){
			double [] entradaUltimaCamada = new double[camadaIntermediaria.length];
			for(int i  = 0; i < camadaIntermediaria.length; i++){
				entradaUltimaCamada[i] = camadaIntermediaria[i].getSaida(entrada);
			}
			entradasUltimacamada.add(entradaUltimaCamada);
		}
		return entradasUltimacamada;
	}

	@Override
	public void setRN(IRN redeNeural) {
	    this.redeNeural = redeNeural;			
	}
}
