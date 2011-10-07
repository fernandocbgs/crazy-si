package geral;

import java.util.ArrayList;

import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITreinador;

public class TreinadorBPRN1Camada implements ITreinador{

	private IRN redeNeural;
	
	public TreinadorBPRN1Camada(IRN redeNeural){
		this.redeNeural = redeNeural;
	}
	
	@Override
	public void treinaRede() {
	    ArrayList<INeuronioBackPropagation []> camadasNeuronios = redeNeural.getNeuronios();			
	    //Aqui só estamos considerando uma camada
	    INeuronioBackPropagation[] camadaNeuronios = camadasNeuronios.get(0);
	    for(INeuronioBackPropagation neuronio : camadaNeuronios){
	    	neuronio.ajustaPesos(redeNeural.getEntradasSupervisionadas(), redeNeural.getSaidasDesejadas());
	    }
	}

	@Override
	public void setRN(IRN redeNeural) {
		this.redeNeural = redeNeural;		
	}
}
