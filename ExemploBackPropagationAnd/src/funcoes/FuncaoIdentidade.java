package funcoes;

import interfaces.IFuncaoAtivacao;

public class FuncaoIdentidade implements IFuncaoAtivacao {

	@Override
	public double funcao(double x) {
		return x;
	}

	@Override
	public double derivada(double entrada) {
		return 0;
	}

}
