/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redesneuraissi;
import java.util.ArrayList;
import org.neuroph.core.learning.*;
import org.neuroph.core.*;
import org.neuroph.nnet.learning.*;

/**
 * Classe usada para treinar e testar a rede neural
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
//            neuralN.setInput(i);
//            neuralN.calculate();
//            double[] valorSaidaTeste = neuralN.getOutput();
    	neuralN.setInput(new double[]{1.0, 1.0});
    	neuralN.calculate();
    	double[] valorSaidaTeste = neuralN.getOutput();
    	for(double saidaTeste : valorSaidaTeste)
    	    System.out.println(saidaTeste);
    }
}
