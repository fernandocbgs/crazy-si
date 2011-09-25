/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redesneuraissi;

import java.util.ArrayList;
import java.util.Vector;

import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.DynamicBackPropagation;

/**
 * Cria uma rede neural e um conjunto de treinamento
 * @author Lucas
 */
public class TreinaRedeNeural {

    String MLP_FILE = "MLP.nnet";
    String TS_FILE = "Set.set";
    /**
     * Conjunto de treinamento com 5 campos de entrada (vide problema) e 2 campos de saida
     */
    TrainingSet training = new TrainingSet(2,1);
    /**
     * Classe necessária para realizar o backpropagation
     */
    DynamicBackPropagation learning;
    /**
     * MultilayerPerceptron ou rede neural
     */
    MultiLayerPerceptron mlp;

   /**
    * Cria uma rede neural para testes
    * O conteúdo dela é salvo em um arquivo
   */
    public TreinaRedeNeural()
    {
    	//necessário para realizar o backPropagation
    	learning = new DynamicBackPropagation();
        learning.setMaxIterations(500);
        learning.setLearningRate(0.5);
        //Número de camadas
        ArrayList<Integer> camadas = new ArrayList<Integer>();
        //camada 1 : 2 neuronios
        camadas.add(2);
        //Camada 2 : 12 neuronios
        camadas.add(12);
        //Camada 3 : 1 neuronio
        camadas.add(1);
        //Cria um perceptron
        mlp = new MultiLayerPerceptron (camadas);
        //adiciona as entradas de treinamento
        adicionaEntradasDeTreinamento();
        //treina a rede
        treinarRede();
        //salvando arquivos
        mlp.save(MLP_FILE);
        training.save(TS_FILE);
    }
    
    /**
     * Adiciona entradas para fazer o treinamento
     */
    public void adicionaEntradasDeTreinamento(){
        training.addElement(new SupervisedTrainingElement(new double[] {1.0, 1.0}, new double[] {1.0}));
        training.addElement(new SupervisedTrainingElement(new double[] {1.0, 0.0}, new double[] {0}));
        training.addElement(new SupervisedTrainingElement(new double[] {1.0, 0.0}, new double[] {0}));
        training.addElement(new SupervisedTrainingElement(new double[] {0.0, 0.0}, new double[] {0}));
    }
    
    /**
     * Treina a rede e salva no arquivo do conjunto de treinamento
     */
    public void treinarRede(){
    	//Treina a rede!
    	mlp.learnInSameThread(training, learning);
    }    
}
