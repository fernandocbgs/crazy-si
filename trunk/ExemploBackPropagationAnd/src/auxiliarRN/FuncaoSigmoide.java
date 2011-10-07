package auxiliarRN;

import static java.lang.Math.*;
import interfaces.IFuncaoAtivacao;

public class FuncaoSigmoide implements IFuncaoAtivacao{

	@Override
	public double funcao(double entrada) {
		return 1/(1+exp(-1*entrada));
	}

	@Override
	public double derivada(double entrada) {
		return funcao(entrada)*(1-funcao(entrada));
	}

}
