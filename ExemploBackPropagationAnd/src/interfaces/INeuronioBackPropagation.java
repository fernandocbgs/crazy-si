package interfaces;

import java.util.ArrayList;

public interface INeuronioBackPropagation {
	public void iniciaPesos(int numeroPesos);
	public double[] getPesos();
	public void ajustaPesos();
	public void calculaAjustes(ArrayList<double[]> entradas, ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio);
	public double getSaida(double uW);
	public double getSaida(double [] entrada);
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao);
	public void setTaxaDeAprendizado(double taxaDeAprendizado);
}
