package auxiliarRN;

import java.util.ArrayList;

import interfaces.IRN;
import interfaces.ITestador;

public class TestadorBPRN1Camada implements ITestador {
	
	private IRN redeNeural;
	double erroMape = 0;

	@Override
	public void setRN(IRN redeNeural) {
		this.redeNeural = redeNeural;
	}

	@Override
	public void testa(ArrayList<double[]> entradasTreinamento,ArrayList<double[]> saidasTreinamento) {
	    //vamos calcular o erro mape de treinamento
		
	}

	@Override
	public double getErroMape() {
		return erroMape;
	}
}
