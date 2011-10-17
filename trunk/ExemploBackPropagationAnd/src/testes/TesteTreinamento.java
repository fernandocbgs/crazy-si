package testes;
import java.util.ArrayList;

import funcoes.FuncaoSigmoide;
import geral.ARedeNeuralExecutor;
import geral.RNPerceptron;
import geral.RNPerceptron.TIPO_FUNCAO;
import interfaces.IFuncaoAtivacao;
import interfaces.IRN;
import interfaces.ITestador;
import interfaces.ITreinador;
import org.junit.Test;
import auxiliarRN.TestadorBPRN1Camada;
import auxiliarRN.TreinadorBPRN1Camada;

/**
 * classe de teste treinamento
 * */
public class TesteTreinamento {
	@Test
	public void test() {
		ArrayList<Integer> camadas = new ArrayList<Integer>();
		//1 neuronio
		camadas.add(2);
		ITreinador treinador = new TreinadorBPRN1Camada(50);
		ITestador testador = new TestadorBPRN1Camada();
		IRN perceptron = RNPerceptron.getInstancia(RNPerceptron.TIPO_RN.DUAS_CAMADAS,camadas, treinador, testador, TIPO_FUNCAO.sigmodal, 0.1);
		ARedeNeuralExecutor executor = ARedeNeuralExecutor.instancia(ARedeNeuralExecutor.TIPO_EXECUTOR.LINEAR, perceptron);
		executor.executa();
	}
}