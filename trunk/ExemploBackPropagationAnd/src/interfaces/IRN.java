package interfaces;

import java.util.ArrayList;

public interface IRN {
	public void instancia(ArrayList<Integer> camadas);
	public void insereEntradaSupervisionada(double [] entrada, double [] saida);
	public void treina();
	public void testa(ArrayList<double []> entradasTeste, ArrayList<double[]> saidasTeste);
	public void setTreinador(ITreinador treinador);
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao);
	public ArrayList<double []> getEntradasSupervisionadas();
	public ArrayList<double []> getSaidasDesejadas();
	public ArrayList<INeuronioBackPropagation[]> getNeuronios();
} 
