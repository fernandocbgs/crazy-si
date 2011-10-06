package interfaces;
public interface IRN {
	public void instancia(int numeroEntradas, int numeroCamadas, int numeroSaidas);
	public void insereEntradaSupervisionada(double [] entrada, double [] saida);
	public void calculaBackPropagation();
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao);
}
