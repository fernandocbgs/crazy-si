package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		camadas.add(1);
		ITreinador treinador = new TreinadorBPRN1Camada(50);
		ITestador testador = new TestadorBPRN1Camada();
		IFuncaoAtivacao sigmoide = new FuncaoSigmoide();
		IRN perceptron = new RNPerceptron(camadas, treinador, testador, sigmoide, 0.1);
		//alguns valores de treinamento
		perceptron.insereEntradaSupervisionada(new double[]{-1}, new double[]{0.0});
		perceptron.insereEntradaSupervisionada(new double[]{+1}, new double[]{1});
		//treina
		perceptron.treina();
	}

}
