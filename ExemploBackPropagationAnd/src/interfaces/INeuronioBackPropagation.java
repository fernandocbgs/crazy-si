package interfaces;

import java.util.ArrayList;

public interface INeuronioBackPropagation {
	public void iniciaPesos(int numeroPesos);
	public double[] getPesos();
	public void ajustaPesos(ArrayList<double[]> entradas, ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio);
	public double getSaida(double [] entrada);
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao);
	public void setTaxaDeAprendizado(double taxaDeAprendizado);
}
