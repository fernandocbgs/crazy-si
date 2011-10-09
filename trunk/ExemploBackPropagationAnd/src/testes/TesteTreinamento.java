package testes;
import static org.junit.Assert.*;
import static java.lang.Math.*;
import java.util.ArrayList;

import geral.ARedeNeuralExecutor;
import geral.RNPerceptron;
import interfaces.IFuncaoAtivacao;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;
import org.junit.Test;
import auxiliarRN.FuncaoSigmoide;
import auxiliarRN.TestadorBPRN1Camada;
import auxiliarRN.TreinadorBPRN1Camada;

public class TesteTreinamento {
	@Test
	public void test() {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//1 neuronio
		camadas.add(2);
		ITreinador treinador = new TreinadorBPRN1Camada(50);
		ITestador testador = new TestadorBPRN1Camada();
		IFuncaoAtivacao sigmoide = new FuncaoSigmoide();
		IRN perceptron = new RNPerceptron(camadas, treinador, testador, sigmoide, 0.1);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.LINEAR, perceptron);
		executor.executa();
	}
}