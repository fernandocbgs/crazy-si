package auxiliarRN;

import java.util.ArrayList;

import interfaces.IRN;
import interfaces.ITestador;

public class TestadorBPRN2Camadas implements ITestador {

	private IRN redeNeural;
	
	@Override
	public void setRN(IRN redeNeural) {
        this.redeNeural = redeNeural;		
	}

	@Override
	public void testa(ArrayList<double[]> entradasTreinamento,ArrayList<double[]> saidasTreinamento) {
		
	}

	@Override
	public double[] getErrosMape() {
		return null;
	}

}
