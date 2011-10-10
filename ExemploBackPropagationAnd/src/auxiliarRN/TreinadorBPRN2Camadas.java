package auxiliarRN;

import interfaces.IRN;
import interfaces.ITreinador;

public class TreinadorBPRN2Camadas implements ITreinador{
	
	private IRN redeNeural;

	@Override
	public void treinaRede() {
	    			
	}

	@Override
	public void setRN(IRN redeNeural) {
	    this.redeNeural = redeNeural;			
	}

}
