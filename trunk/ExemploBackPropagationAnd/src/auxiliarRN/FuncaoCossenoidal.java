package auxiliarRN;
import static java.lang.Math.*;
import interfaces.IFuncaoAtivacao;

public class FuncaoCossenoidal implements IFuncaoAtivacao {

	@Override
	public double funcao(double entrada) {
		return cos(entrada);
	}

	@Override
	public double derivada(double entrada) {
		return -sin(entrada);
	}

}
