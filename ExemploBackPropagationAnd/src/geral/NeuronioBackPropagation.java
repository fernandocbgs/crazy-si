package geral;

import static java.lang.Math.*;
import static utils.Utils.*;

import java.util.ArrayList;

import interfaces.IFuncaoAtivacao;
import interfaces.INeuronioBackPropagation;

public class NeuronioBackPropagation implements INeuronioBackPropagation {
	
	private double taxaDeAprendizado;
	private double [] pesos;
	private double [] ajustes;
	private double uW = 0.0;
	private IFuncaoAtivacao funcaoAtivacao;
	
	public NeuronioBackPropagation(int numeroDePesos, double taxaDeAprendizado, IFuncaoAtivacao funcaoAtivacao){
		iniciaPesos(numeroDePesos);
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
	public void ajustaPesos(ArrayList<double[]> entradas, ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio) {
		calculaAjustes(entradas, saidasDesejadas, indiceDesteNeuronio);
		//faz os ajustes
		for(int i = 0; i < pesos.length; i++){
			pesos[i] = pesos[i]-ajustes[i];
		}
	}
	
	private void calculaAjustes(ArrayList<double[]> entradas, ArrayList<double[]> saidasDesejadas, int indiceDesteNeuronio){
	//para cada peso
		for(int i = 0; i < pesos.length; i++){
	        double djDW = 0.0;
		    //para cada par de treinamento
			for(int p = 0; p < entradas.size(); p++){
				djDW += erro(getSaida(entradas.get(p)),saidasDesejadas.get(p)[indiceDesteNeuronio])
						*funcaoAtivacao.derivada(uW)*entradas.get(p)[i];
			}			
			ajustes[i] = taxaDeAprendizado*djDW;
		}
	}
	
	public double getSaida(double [] entrada){
		//calcula o somatorio de wi*entrada_i
		double somatorioEntradas = 0.0;
		for(int i = 0; i < entrada.length; i++){
			somatorioEntradas += pesos[i]*entrada[i];
		}
		uW = somatorioEntradas;
		return funcaoAtivacao.funcao(somatorioEntradas);
	}
}
