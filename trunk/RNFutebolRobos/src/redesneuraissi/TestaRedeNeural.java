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
    	neuralN.setInput(new double[]{0.0, 1.0});
    	neuralN.calculate();
    	double[] valorSaidaTeste = neuralN.getOutput();
    	System.out.println("Saída:");
    	for(double saidaTeste : valorSaidaTeste)
    	    System.out.println(Math.round(saidaTeste));
    }
}
