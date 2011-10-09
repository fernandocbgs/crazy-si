package geral;

import static java.lang.Math.pow;
import static java.lang.Math.random;

import java.util.ArrayList;

import interfaces.IRN;

public abstract class ARedeNeuralExecutor {
	
	protected IRN rn;
	public enum TIPO_EXECUTOR {
		LINEAR, NAO_LINEAR
	}	
	public ARedeNeuralExecutor(IRN redeNeural){
		this.rn = redeNeural;
	}
	
	public void executa(){
		adicionaEntradasTreinamento();
		rn.treina();
		ArrayList<double []> entradasTeste = new ArrayList<double []>();
		ArrayList<double []> saidasTeste = new ArrayList<double []>();
		adicionaEntradasTeste(entradasTeste, saidasTeste);
		rn.testa(entradasTeste, saidasTeste);
	}
	public abstract void adicionaEntradasTreinamento();
	public abstract void adicionaEntradasTeste(ArrayList<double []> entradasTeste, ArrayList<double []> saidasTeste);
	
	public static ARedeNeuralExecutor instancia(TIPO_EXECUTOR executor, IRN rn){
		switch(executor){
			case LINEAR: return new RedeNeuralExecutorLinear(rn); 
		}
		return null;
	}
}

class RedeNeuralExecutorLinear extends ARedeNeuralExecutor{

	public RedeNeuralExecutorLinear(IRN redeNeural) {
		super(redeNeural);
	}

	@Override
	public void adicionaEntradasTreinamento() {
		for(double i = -200.0; i <= -100.0; i++){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{1, 0});
		}
		for(double i = 100.0; i <= 200.0; i++){
			rn.insereEntradaSupervisionada(new double[]{i, i},new double[]{0, 1});
		}		
	}

	@Override
	public void adicionaEntradasTeste(ArrayList<double []> entradasTeste, ArrayList<double []> saidasTeste) {

		for(double i = -30.0; i <= -20.0; i++){
			entradasTeste.add(new double[]{i, i});
			saidasTeste.add(new double[]{1, 0});
		}
		for(double i = 20.0; i <= 30.0; i++){
			entradasTeste.add(new double[]{i, i});
			saidasTeste.add(new double[]{0, 1});
		}		
	}
}

class RedeNeuralExecutorNaoLinear extends ARedeNeuralExecutor{

	public RedeNeuralExecutorNaoLinear(IRN redeNeural) {
		super(redeNeural);
	}

	@Override
	public void adicionaEntradasTreinamento() {
		for(double i = -200.0; i <= 100.0; i++){
			for(double j = -200.0; j <= 100.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2+3*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) < pow(j,valorAleatorio)){
					rn.insereEntradaSupervisionada(new double[]{i, j},new double[]{1, 1});
				}
			}
		}
		for(double i = -200.0; i <= 100.0; i++){
			for(double j = -200.0; j <= 100.0; j++){
				//sorteia um número aleatório entre 0 e 2 para sair acima da curva
				double b = 2*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) > pow(j,valorAleatorio)){
					rn.insereEntradaSupervisionada(new double[]{i, j},new double[]{1, 1});
				}
			}
		}			
	}
	@Override
	public void adicionaEntradasTeste(ArrayList<double[]> entradasTeste,ArrayList<double[]> saidasTeste) {
		for(double i = 300.0; i <= 320.0; i++){
			for(double j = 300.0; j <= 320.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2+3*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) > pow(j,valorAleatorio)){
					entradasTeste.add(new double[]{i, i});
					saidasTeste.add(new double[]{1, 1});
				}
			}
		}
		for(double i = 300.0; i <= 320.0; i++){
			for(double j = 300.0; j <= 320.0; j++){
				//sorteia um número aleatório entre 2 e 5 para sair acima da curva
				double b = 2*random();
				double valorAleatorio = pow(j, b);
				if(pow(i,2) < pow(j,valorAleatorio)){
					entradasTeste.add(new double[]{i, i});
					saidasTeste.add(new double[]{0, 0});
				}
			}
		}
	}	
}
