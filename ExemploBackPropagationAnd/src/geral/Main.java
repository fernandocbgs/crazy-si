package geral;

import interfaces.IFuncaoAtivacao;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;

import java.util.ArrayList;

import auxiliarRN.FuncaoSigmoide;
import auxiliarRN.TestadorBPRN1Camada;
import auxiliarRN.TreinadorBPRN1Camada;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//1 neuronio
		camadas.add(2);
		ITreinador treinador = new TreinadorBPRN1Camada(500);
		ITestador testador = new TestadorBPRN1Camada();
		IFuncaoAtivacao sigmoide = new FuncaoSigmoide();
		IRN perceptron = new RNPerceptron(camadas, treinador, testador, sigmoide, 0.7);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.LINEAR, perceptron);
		executor.executa();
	}
}
