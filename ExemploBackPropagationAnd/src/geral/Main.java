package geral;

import geral.RNPerceptron.TIPO_FUNCAO;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;
import java.util.ArrayList;
import auxiliarRN.TestadorBPRN1Camada;
import auxiliarRN.TreinadorBPRN1Camada;

public class Main {
	
	public static void main(String[] args) {
		test();
	}
	
	/**
	 * testa a rede neural
	 * */
	public static void test() {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//2 neuronios
		camadas.add(2);
		ITreinador treinador = new TreinadorBPRN1Camada(50);
		ITestador testador = new TestadorBPRN1Camada();
		IRN perceptron = new RNPerceptron(camadas, treinador, testador, TIPO_FUNCAO.sigmodal, 0.1);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.LINEAR, perceptron);
		executor.executa();
	}
	
}
