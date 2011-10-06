package geral;
import java.util.ArrayList;
import interfaces.IFuncaoAtivacao;
import interfaces.IRN;

public class RN implements IRN {
	
	private IFuncaoAtivacao funcaoAtivacao;
	private ArrayList<double []> entradasSupervisionadas = new ArrayList<double []>();
	
	@Override
	public void instancia(int numeroEntradas, int numeroCamadas,int numeroSaidas) {
	   			
	}

	@Override
	public void calculaBackPropagation() {
				
	}

	@Override
	public void insereEntradaSupervisionada(double[] entrada, double[] saida) {
				
	}

	@Override
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao) {
		this.funcaoAtivacao = funcaoAtivacao;		
	}	
}
