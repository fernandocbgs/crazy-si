package auxiliarRN;

import interfaces.ICalculadorSaidaRN;
import interfaces.IRN;

public class CalculadorPerceptron implements ICalculadorSaidaRN {
	private IRN redeNeural;
	
	@Override
	public void setRN(IRN redeNeural) {
        this.redeNeural = redeNeural;
	}

	@Override
	public double[] calculaSaida() {
		return null;
	}
}
