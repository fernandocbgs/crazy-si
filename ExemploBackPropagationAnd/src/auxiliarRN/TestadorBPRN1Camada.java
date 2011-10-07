package auxiliarRN;

import java.util.ArrayList;
import static java.lang.Math.*;
import geral.NeuronioBackPropagation;
import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITestador;

public class TestadorBPRN1Camada implements ITestador {
	
	private IRN redeNeural;
	double[] errosMape;

	@Override
	public void setRN(IRN redeNeural) {
		this.redeNeural = redeNeural;
	}

	@Override
	public void testa(ArrayList<double[]> entradasTreinamento,ArrayList<double[]> saidasTreinamento) {
	    //vamos calcular o erro mape de treinamento
		ArrayList<INeuronioBackPropagation[]> camadasRN = redeNeural.getNeuronios();
		//truncando em uma camada para facilitar
		INeuronioBackPropagation [] camada = camadasRN.get(0);
		//instancia os erros mape
		errosMape = new double[camada.length];
		//foreach
		for(int i = 0; i < camada.length; i++){
			INeuronioBackPropagation neuronio = camada[i];
			errosMape[i] = 0;
			for(int j = 0; j < entradasTreinamento.size(); j++){
			   double saidaEncontrada = neuronio.getSaida(entradasTreinamento.get(j));
			   double saidaEsperada = saidasTreinamento.get(j)[i];
			   errosMape[i] += abs(saidaEncontrada-saidaEsperada);
			}
			errosMape[i] /= entradasTreinamento.size();
		}
		//Imprime os erros mape
		for(int i = 0; i < errosMape.length; i++){
			System.out.println("Erro mape: " + errosMape[i]);
		}
	}

	@Override
	public double [] getErrosMape() {
		return errosMape;
	}
}
