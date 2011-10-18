package geral;

import geral.RNPerceptron.TIPO_FUNCAO;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;
import java.util.ArrayList;
import auxiliarRN.TestadorBPRN2Camadas;
import auxiliarRN.TreinadorBPRN2Camadas;

public class Main {
	
	public static void main(String[] args) {
		//testeLinear();
		testeFutebolRobos();
	}
	
	/**
	 * testa a rede neural
	 * */
	@SuppressWarnings("unused")
	private static void testeLinear() {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//2 neuronios
		camadas.add(2);
		camadas.add(2);
		//tira as camadas em branco
		camadas.trimToSize();
		ITreinador treinador = new TreinadorBPRN2Camadas(50);
		ITestador testador = new TestadorBPRN2Camadas();
		IRN perceptron = RNPerceptron.getInstancia(RNPerceptron.TIPO_RN.DUAS_CAMADAS, camadas, treinador, testador, TIPO_FUNCAO.sigmodal, 0.1, 2);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.LINEAR, perceptron);
		executor.executa();
	}
	
	/**
	 * Teste Futebol de Robos
	 * */
	private static void testeFutebolRobos(){
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//2 neuronios
		camadas.add(2);
		camadas.add(2);
		//tira as camadas em branco
		camadas.trimToSize();
		ITreinador treinador = new TreinadorBPRN2Camadas(50);
		ITestador testador = new TestadorBPRN2Camadas();
		IRN perceptron = RNPerceptron.getInstancia(RNPerceptron.TIPO_RN.DUAS_CAMADAS, camadas, treinador, testador, TIPO_FUNCAO.sigmodal, 0.1, 5);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.FUTEBOL_ROBOS, perceptron);
		executor.executa();
	}
	
	/**
	 * Teste não linear
	 * */
	@SuppressWarnings("unused")
	private static void testeNaoLinear() {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//2 neuronios
		camadas.add(2);
		camadas.add(2);
		//tira as camadas em branco
		camadas.trimToSize();
		ITreinador treinador = new TreinadorBPRN2Camadas(50);
		ITestador testador = new TestadorBPRN2Camadas();
		IRN perceptron = RNPerceptron.getInstancia(RNPerceptron.TIPO_RN.DUAS_CAMADAS, camadas, treinador, testador, TIPO_FUNCAO.sigmodal, 0.1,2);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.NAO_LINEAR, perceptron);
		executor.executa();
	}
	
	
}