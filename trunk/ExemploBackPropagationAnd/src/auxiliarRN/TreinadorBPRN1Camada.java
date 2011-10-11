package auxiliarRN;

import java.util.ArrayList;

import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITreinador;

public class TreinadorBPRN1Camada implements ITreinador{

	/**
	 * Representa o ciclo atual do treinamento
	 */
	private int numeroCiclos = 0;
	private IRN redeNeural;
	
	public TreinadorBPRN1Camada(int numeroCiclos){
		this.numeroCiclos = numeroCiclos;
	}
	
	@Override
	public void treinaRede() {
		for(int cicloTreinamento = 0; cicloTreinamento < numeroCiclos; cicloTreinamento++){
		    ArrayList<INeuronioBackPropagation []> camadasNeuronios = redeNeural.getNeuronios();			
		    //Aqui só estamos considerando uma camada
		    INeuronioBackPropagation[] camadaNeuronios = camadasNeuronios.get(0);
		    for(int i = 0; i < camadaNeuronios.length; i++){
		    	INeuronioBackPropagation neuronio = camadaNeuronios[i];
		    	neuronio.calculaAjustes(redeNeural.getEntradasSupervisionadas(), redeNeural.getSaidasDesejadas(), i);
		    	neuronio.ajustaPesos();
		    }
	    }
	}

	@Override
	public void setRN(IRN redeNeural) {
		this.redeNeural = redeNeural;		
	}
}
