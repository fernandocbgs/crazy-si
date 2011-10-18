package geral;

import static java.lang.Math.*;
import static utils.Utils.*;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;

public class NeuronioBackPropagation implements INeuronioBackPropagation {
	
	protected double taxaDeAprendizado;
	protected double [] pesos;
	protected double [] ajustes;
//	private double uW = 0.0;
	protected IFuncaoAtivacao funcaoAtivacao;
	protected double [] ultimaAlteracao;
	
	public NeuronioBackPropagation(int numeroDeEntradas, double taxaDeAprendizado, IFuncaoAtivacao funcaoAtivacao){
		iniciaPesos(numeroDeEntradas);
		this.taxaDeAprendizado = taxaDeAprendizado;
		this.funcaoAtivacao = funcaoAtivacao;
	}

	@Override
	public void iniciaPesos(int numeroPesos) {
        //sorteia numeros aleatorios para criar os pesos
		pesos = new double[numeroPesos];
		for(int i = 0; i < numeroPesos; i++){
			//feitos de -1 a 1 como solicitado
			pesos[i] = (2*random()-1.0);
		}
		//ajustes também
		ajustes = new double[numeroPesos];
		ultimaAlteracao = new double[numeroPesos];
	}

	@Override
	public double[] getPesos() {
	    return pesos;
	}


	@Override
	public void setFuncaoAtivacao(IFuncaoAtivacao funcaoAtivacao) {
		this.funcaoAtivacao = funcaoAtivacao;		
	}

	@Override
	public void setTaxaDeAprendizado(double taxaDeAprendizado) {
		this.taxaDeAprendizado = taxaDeAprendizado;		
	}

	@Override
	public void ajustaPesos() {
		//faz os ajustes
		for(int i = 0; i < pesos.length; i++){
			pesos[i] = pesos[i]-ajustes[i]+0.8*ultimaAlteracao[i];
			ultimaAlteracao[i]=ajustes[i];
		}
	}
	
	public void calculaAjustes(ArrayList<double[]> entradas, ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio){
	//para cada peso
		for(int i = 0; i < pesos.length; i++){
	        double djDW = 0.0;
		    //para cada par de treinamento
	        // p = par de entradas (xp,yp)
			for(int p = 0; p < entradas.size(); p++){
				double uW = calculaUW(entradas.get(p));
				double saidaNeuronio = getSaida(uW);
				djDW += erro(saidaNeuronio,saidasDesejadas.get(p)[indiceDesteNeuronio])
						*funcaoAtivacao.derivada(uW)*entradas.get(p)[i];
			}			
			//uW = somatório de wixi de i=1 até n entradas
			ajustes[i] = taxaDeAprendizado*djDW;
		}
	}
	
	protected double calculaUW(double [] entrada){
	   //calcula o somatorio de wi*entrada_i
		double uW = 0.0;
		for(int i = 0; i < entrada.length; i++){
		       uW += pesos[i]*entrada[i];
		}
		return uW;
	}
	
	public double getSaida(double uW){
		return funcaoAtivacao.funcao(uW);
	}
	
	public double getSaida(double [] entrada){
		double uW = calculaUW(entrada);
		return funcaoAtivacao.funcao(uW);
	}
}
