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
		adicionaEntradasTreinamento(perceptron);
		//treina
		perceptron.treina();
		//testa
		ArrayList<double []> entradasTeste = new ArrayList<double []>();
		ArrayList<double []> saidasTeste = new ArrayList<double []>();
		adicionaEntradasTeste(entradasTeste, saidasTeste);
		perceptron.testa(entradasTeste, saidasTeste);
	}
	
	
	public void adicionaEntradasTreinamento(IRN rn){
		for(double i = -200.0; i <= -100.0; i++){
			rn.insereEntradaSupervisionada(new double[]{i},new double[]{1});
		}
		for(double i = 100.0; i <= 200.0; i++){
			rn.insereEntradaSupervisionada(new double[]{i},new double[]{0});
		}
	}
	
	public void adicionaEntradasTeste(ArrayList<double []> entradasTeste, ArrayList<double []> saidasTeste){
		for(double i = -30.0; i <= -20.0; i++){
			entradasTeste.add(new double[]{i});
			saidasTeste.add(new double[]{1});
		}
		for(double i = 20.0; i <= 30.0; i++){
			entradasTeste.add(new double[]{i});
			saidasTeste.add(new double[]{0});
		}
	}

}
