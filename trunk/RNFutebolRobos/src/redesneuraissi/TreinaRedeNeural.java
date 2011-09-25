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
 * @author Emerson
 * @author Lucas
 */
public class TreinaRedeNeural {

	/**
	 * Número de entradas para a rede neural
	 */
	int numeroDeEntradas = 2;
	/**
	 * Número de saídas para a rede neural
	 */
	int numeroDeSaidas = 1;
	/**
	 * Arquivo correspondente a rede neural
	 */
    String MLP_FILE = "MLP.nnet";
    /**
     * Nome do arquivo de testes
     */
    String TS_FILE = "Set.set";
    /**
     * Conjunto de treinamento com 5 campos de entrada (vide problema) e 2 campos de saida
     * 
     * 5 Entradas: 1 para a bola, 3 para os jogadores e 1 para saída
     */
    TrainingSet training = new TrainingSet(numeroDeEntradas,numeroDeSaidas);
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
        learning.setLearningRate(0.7);
        //Número de camadas
        ArrayList<Integer> camadas = new ArrayList<Integer>();
        //camada 1 : 2 neuronios
        camadas.add(numeroDeEntradas);
        //Camada 2 : 12 neuronios
        camadas.add(12);
        //Camada 3 : 2 neuronios
        camadas.add(numeroDeSaidas);
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
        training.addElement(new SupervisedTrainingElement(new double[] {0.0, 1.0}, new double[] {0}));
        training.addElement(new SupervisedTrainingElement(new double[] {0.0, 0.0}, new double[] {0}));
    	
    	//aqui são adicionadas algumas entradas aleatórias e calculadas algumas saídas
    }
    
    /**
     * Treina a rede e salva no arquivo do conjunto de treinamento
     */
    public void treinarRede(){
    	//Treina a rede!
    	mlp.learnInSameThread(training, learning);
    }    
}
