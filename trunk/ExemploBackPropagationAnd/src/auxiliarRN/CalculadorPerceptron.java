package auxiliarRN;

import interfaces.ICalculadorSaidaRN;
import interfaces.IRN;

public class CalculadorPerceptron implements ICalculadorSaidaRN {
	private IRN redeNeural;
	
	public IRN getRedeNeural() { return redeNeural; }
	public void setRedeNeural(IRN redeNeural) { this.redeNeural = redeNeural; }
	
	@Override
	public void setRN(IRN redeNeural) {
        this.setRedeNeural(redeNeural);
	}

	@Override
	public double[] calculaSaida() {
		return null;
	}
}
