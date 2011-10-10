package auxiliarRN;

import static java.lang.Math.*;
import interfaces.IFuncaoAtivacao;

public class FuncaoSigmoide implements IFuncaoAtivacao{
	@Override
	public double funcao(double x) {
		return 1/(1+exp(-1*x));
	}

	@Override
	public double derivada(double x) {
		return funcao(x)*(1-funcao(x));
	}
}
