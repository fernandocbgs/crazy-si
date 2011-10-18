package auxiliarRN;

import java.util.ArrayList;
import static java.lang.Math.*;
import interfaces.INeuronioBackPropagation;
import interfaces.IRN;
import interfaces.ITestador;

public class TestadorBPRN2Camadas implements ITestador {
	
	private IRN redeNeural;
	double[] errosMape;

	@Override
	public void setRN(IRN redeNeural) {
		this.redeNeural = redeNeural;
	}

	@Override
	public void testa(ArrayList<double[]> entradasTreinamento,ArrayList<double[]> saidasTreinamento) {
	    //vamos calcular o erro mape de treinamento
		ArrayList<INeuronioBackPropagation[]> camadasRN = redeNeural.getNeuronios();
		//instancia os erros mape
		errosMape = new double[camadasRN.get(camadasRN.size()-1).length];
		//foreach
		System.out.println("-----------------------------------------");
		for(int i = 0; i < errosMape.length; i++){
			errosMape[i] = 0;
			System.out.println("Neuronio " + i);
			System.out.println("-----------------------------------------");
			for(int j = 0; j < entradasTreinamento.size(); j++){
			   double saidaEncontrada = Math.round(redeNeural.getSaida(entradasTreinamento.get(j))[i]);
			   double saidaEsperada = saidasTreinamento.get(j)[i];
			   errosMape[i] += abs(saidaEncontrada-saidaEsperada);
			   
			   if(round(saidaEncontrada-saidaEsperada) != 0){
				   
				   for(double entrada : entradasTreinamento.get(j))
					      System.err.println("Entrada: " + entrada);
				   
				  if(i == 0)
				  {
	   			       System.err.println("Saida: " + saidaEncontrada);
				       System.err.println("Saida: " + saidasTreinamento.get(j)[1]);
				  }
				  else if(i == 1)
				  {
				       System.err.println("Saida: " + saidasTreinamento.get(j)[0]);
		   			   System.err.println("Saida: " + saidaEncontrada);
				  }
			   }
			   else{
			   
				   for(double entrada : entradasTreinamento.get(j))
				      System.out.println("Entrada: " + entrada);
				   
				   for(double saida : saidasTreinamento.get(j))
					      System.out.println("Saida: " + saida);
			   }
			}
			System.out.println("-----------------------------------------");
			errosMape[i] /= entradasTreinamento.size();
		}
		System.out.println("-----------------------------------------");
		//Imprime os erros mape
		for(int i = 0; i < errosMape.length; i++){
			System.out.println("Erro mape: " + errosMape[i]);
		}
	}

	@Override
	public double [] getErrosMape() {
		return errosMape;
	}
}
