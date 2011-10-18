package auxiliarRN;

import java.util.ArrayList;

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
				neuronio.calculaAjustes(redeNeural.getEntradasSupervisionadas(), redeNeural.getSaidasDesejadas(), k);
			}
			//calculados os ajustes vamos a cada neur�nio da camada de entrada
			
			for(int j = 0; j < ultimaCamada.length; j++){
				INeuronioBackPropagation neuronio = camadaIntermediaria[j];
				neuronio.calculaAjustes(redeNeural.getEntradasSupervisionadas(), redeNeural.getSaidasDesejadas(), j);
			}
			
			//manda todo mundo ajustar
	
			for(int k = 0; k < ultimaCamada.length; k++){
				INeuronioBackPropagation neuronio = ultimaCamada[k];
				neuronio.ajustaPesos();
			}
			//calculados os ajustes vamos a cada neur�nio da camada de entrada
			
			for(int j = 0; j < ultimaCamada.length; j++){
				INeuronioBackPropagation neuronio = camadaIntermediaria[j];
				neuronio.ajustaPesos();
			}
		}		
	}

	@Override
	public void setRN(IRN redeNeural) {
	    this.redeNeural = redeNeural;			
	}
}
