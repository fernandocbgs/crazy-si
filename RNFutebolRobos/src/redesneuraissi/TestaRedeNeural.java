/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redesneuraissi;
import org.neuroph.core.*;

/**
 * Classe usada para treinar e testar a rede neural
 * @author Emerson
 * @author Lucas
 */
public class TestaRedeNeural {
	//ErroMape
	double erroMape = 0.0;
    NeuralNetwork neuralN;
    /**
     * Cria um teste de rede neural
     */
    public TestaRedeNeural() {
        neuralN = NeuralNetwork.load("MLP.nnet");
    }

     /**
     * Testa a rede neural na etapa de treinamento
     */
    public void testarRedeNeuralTreinamento(){
    	neuralN.setInput(new double[]{0.0, 0.0, 1.0, 2.0, 2.0});
    	neuralN.calculate();
    	double[] valorSaidaTeste = neuralN.getOutput();
    	System.out.println("Saída:");
        for(double saidaTeste : valorSaidaTeste)
    	{
    	    System.out.print(saidaTeste + " ");
    	}
        System.out.println("Valor decimal: "  + getDecimal(valorSaidaTeste));
    	System.out.println();
    }
    
    public static double getDecimal(double [] valoresBinarios){
    	  int potencia = 1;
    	  double decimal = 0.0;
    	  for(int i = valoresBinarios.length - 1; i >= 0; i--){
    		  decimal += potencia*valoresBinarios[i];
    		  potencia *= 2;
    	  }
    	  return decimal;
    }
}
