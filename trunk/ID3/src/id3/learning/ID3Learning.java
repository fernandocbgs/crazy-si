/*
 * A decision trees implementation for the Java(tm) platform
 *
 * Copyright (c) 2011, Felipe Michels Fontoura and Thayse Marques
 * Solis
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package id3.learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import id3.data.Attribute;
import id3.data.Classifier;
import id3.data.Input;
import id3.data.InputComparator;
import id3.data.ValueClass;
import id3.data.symbollic.SymbollicAttribute;

import id3.node.InnerNode;
import id3.node.LeafNode;
import id3.node.Node;

/**
 * Classe com mï¿½todos referentes ao algoritmo de aprendizado ID3.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class ID3Learning {
	private static final double log2 = Math.log(2);

	/**
	 * Construtor privado para impedir instanciaï¿½ï¿½o.
	 */
	private ID3Learning() {};

	/**
	 * Gera uma ï¿½rvore de decisï¿½o para um certo parï¿½metro de saï¿½da a partir de
	 * um vetor de atributos e de outro de entradas. Retorna o nï¿½-raiz da ï¿½rvore
	 * gerada.
	 * @param attributes Vetor de atributos.
	 * @param inputs Vetor de entradas.
	 * @param outputAttribute Atributo de saï¿½da.
	 * @return Nï¿½-raiz da ï¿½rvore de decisï¿½o gerada.
	 */
	public static Node generateTree(Attribute[] attributes, Input[] inputs, Attribute outputAttribute) {
		// se nï¿½o definiu qual o atributo de saï¿½da, escolhe o ï¿½ltimo.
		if (outputAttribute == null)
			outputAttribute = attributes[attributes.length - 1];

		// passa todos os atributos para uma lista.
		List<Attribute> attributeList = new ArrayList<Attribute>(attributes.length - 1);
		for (int i = 0; i < attributes.length; i ++) 
			if (! outputAttribute.equals(attributes[i]))
				attributeList.add(attributes[i]);

		// passa todos os exemplos para uma lista.
		List<Input> exampleList = Arrays.asList(inputs);

		// executa o algoritmo ID3.
		Node root = ID3(
			/* lista de atributos:      */ attributeList,
			/* lista de exemplos:       */ exampleList,
			/* classificador de saï¿½das: */ outputAttribute.getClassifier(exampleList)
		);

		// retorna o nï¿½ gerado.
		return root;
	}

	/**
	 *Gera uma ï¿½rvore de decisï¿½o a partir de um vetor de atributos e de outro
	 * de entradas. Assume o que o ï¿½ltimo atributo ï¿½ o atributo de saï¿½da.
	 * Retorna o nï¿½-raiz da ï¿½rvore gerada.
	 * @param attributes Vetor de atributos.
	 * @param inputs Vetor de entradas.
	 * @return Nï¿½-raiz da ï¿½rvore de decisï¿½o gerada.
	 */
	public static Node generateTree(Attribute[] attributes, Input[] inputs) {
		return generateTree(attributes, inputs, null);
	}

	/**
	 * Chamada recursiva do algoritmo ID3.
	 * @param attributes Vetor de atributos.
	 * @param inputs Vetor de entradas.
	 * @param outputClassifier Classificador de saï¿½das.
	 * @return Nï¿½-raiz da ï¿½rvore gerada.
	 */
	private static Node ID3(List<Attribute> attributes, List<Input> inputs, Classifier outputClassifier) {
		// verifica se a lista de exemplos ï¿½ vazia.
		// se for, nï¿½o tem como saber o que fazer.
		if (inputs.size() == 0) {
			System.out.println("Achou um nï¿½ para o qual nï¿½o sabe o que fazer.");
			return new LeafNode();
		}

		// verifica se todas as saï¿½das pertencem ï¿½ mesma classe.
		// se estiverem, retorna um nï¿½-folha com essa classe.
		ValueClass result = allHaveSameOutput(inputs, outputClassifier);
		if (result != null) return new LeafNode(result);

		// verifica se tem atributos restando para testar.
		// se nï¿½o tem, cria um nï¿½-folha com a disjunï¿½ï¿½o das classes dos exemplos.
		if (attributes.isEmpty()) {
			List<ValueClass> allClasses = outputClassifier.listClasses();
			boolean[] disjuct = new boolean[allClasses.size()];
			for (int i = 0; i < allClasses.size(); i ++) {
				disjuct[i] = false;
				for (Input input : inputs)
					if (input.isClassifiedAs(allClasses.get(i)))
						disjuct[i] = true;
			}
			List<ValueClass> classes = new ArrayList<ValueClass>();
			for (int i = 0; i < allClasses.size(); i ++) {
				if (disjuct[i])
					classes.add(allClasses.get(i));
			}
			return new LeafNode(classes.toArray(new ValueClass[classes.size()]));
		}

		// se chegou atï¿½ aqui, realiza o algoritmo ID3.

		// obtï¿½m o classificador do atributo com a menor entropia.
		Classifier chosenClassifier = chooseAttribute(attributes, inputs, outputClassifier);
		
		// cria um conjunto de atributos sem o atributo com menor entropia.
		Attribute swap = attributes.get(attributes.size() - 1);
		for (int i = 0; i < attributes.size() && swap != null; i ++) {
			Attribute attribute = attributes.get(i);
			if (attribute.equals(chosenClassifier.getAttribute())) {
				attributes.set(i, swap);
				attributes.set(attributes.size() - 1, attribute);
				swap = null;
			}
		}
		List<Attribute> childAttributes = attributes.subList(0, attributes.size() - 1);

		// para cada valor possï¿½vel dessa classificaï¿½ï¿½o, separa o subconjunto de entradas
		// e faz a recursï¿½o.
		Node[] children = new Node[chosenClassifier.countClasses()];
		List<ValueClass> possibleClasses = chosenClassifier.listClasses();

		// para nï¿½o ter de criar listas adoidadamente, ordena a lista de acordo com o
		// atributo desse classificador.
		Collections.sort(inputs, new InputComparator(chosenClassifier.getAttribute()));

		for (int index = 0; index < children.length; index ++) {
			ValueClass value = possibleClasses.get(index);

			// como a lista estï¿½ ordenada com relaï¿½ï¿½o a esse parï¿½metro, ï¿½ sï¿½ achar o
			// primeiro e o ï¿½ltimo que tï¿½m ele, e entï¿½o criar uma sub-lista.
			// gasta menos memï¿½ria do que ficar criando uma nova lista toda vez.
			List<Input> childInputs;
			int beginIndex = -1;
			int endIndex = -1;
			for (int inputIndex = 0; inputIndex < inputs.size() && (beginIndex == -1 || endIndex == -1); inputIndex ++) {
				Input input = inputs.get(inputIndex);
				if (input.isClassifiedAs(value)) {
					// primeiro valor.
					if (beginIndex == -1)
						beginIndex = inputIndex;
				} else {
					// ï¿½ltimo valor.
					if (beginIndex != -1)
						endIndex = inputIndex;
				}
			}
			// cria a sub-lista.
			if (beginIndex == -1) {
				// nï¿½o achou nenhum elemento correspondente.
				// a lista de valores ï¿½ vazia.
				childInputs = Collections.emptyList();
			} else {
				if (endIndex == -1)
					endIndex = inputs.size();
				childInputs = inputs.subList(beginIndex, endIndex);
			}

			//
			children[index] = ID3(childAttributes, childInputs, outputClassifier);
		}
		return new InnerNode(chosenClassifier, children);
	}

	/**
	 * Verifica se uma listagem de entradas tem suas saï¿½das pertencentes ï¿½
	 * mesma classe. 
	 * @param inputs Lista de entradas.
	 * @param outputClassifier Classificador de saï¿½das.
	 * @return Se todas as saï¿½das pertencem ï¿½ mesma classe.
	 */
	private static ValueClass allHaveSameOutput(List<Input> inputs, Classifier outputClassifier) {
		Input first = inputs.get(0);
		ValueClass firstValueClass = first.classify((SymbollicAttribute)outputClassifier);
		for (int i = 1; i < inputs.size(); i ++) {
			Input currentInput = inputs.get(i);
			ValueClass currentValueClass = currentInput.classify((SymbollicAttribute)outputClassifier);
			if (! firstValueClass.equals(currentValueClass)) return null;
		}
		return firstValueClass;
	}

	/**
	 * Escolhe o classificador do melhor atributo com relaï¿½ï¿½o ï¿½ entropia.
	 * @param inputs Lista de entradas.
	 * @param attributes Lista de atributos.
	 * @param outputClassifier Classificador de saï¿½das.
	 * @return Classificador do melhor atributo
	 */
	private static Classifier chooseAttribute(List<Attribute> attributes, List<Input> inputs, Classifier outputClassifier) {
		Classifier classifier = attributes.get(0).getClassifier(inputs);
		double bestEnthropy = calculateEntropy(classifier, outputClassifier, inputs);

		for (int a = 1; a < attributes.size(); a ++) {
			Classifier currentClassifier = attributes.get(a).getClassifier(inputs);
			double currentEnthropy = calculateEntropy(currentClassifier, outputClassifier, inputs);
			if (currentEnthropy < bestEnthropy) {
				bestEnthropy = currentEnthropy;
				classifier = currentClassifier;
			}
		}

		return classifier;
	}
	
	public static ValueClass getIndiceValorMaisProvavel(int indiceInput, Classifier classifier,
			Classifier  outputClassifier,
			List<ValueClass> classList,
			List<ValueClass> outputClassList,List<Input> inputs)
	{
        double maiorProbabilidade = 0.0;
        double propabilidadeAux = 0.0;
        ValueClass valorSaida = (ValueClass) inputs.get(indiceInput).getValue(outputClassifier.getAttribute());
        
        int somaSaida = 0;
        ValueClass valorProvavel = null;
        
        for (int i = 0; i < inputs.size(); i++) {
        	Input input = inputs.get(i);
        	Input input2 = inputs.get(indiceInput);
        	
			if(input.getValue(outputClassifier.getAttribute()).equals(input2.getValue(outputClassifier.getAttribute())))
			{
			    somaSaida++;
			}
		}
        
		//para cada atributo de entrada dentre os recuperados
		for(ValueClass valorEntrada : classList){
			//para cada atributo de saida
				int contagemTotalAtributo = 0;
				for(Input input : inputs){
					//recupera o atributo quando a saida for de dado atributo e a entrada também
					if(valorSaida.equals(input.getValue(outputClassifier.getAttribute()))
							&& valorEntrada.equals(input.getValue(classifier.getAttribute())))
					{
						contagemTotalAtributo++;
					}
				}
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println("O atributo " + valorEntrada.getName() + " totalizou " + contagemTotalAtributo + " para a saida " + valorSaida.getName() );
				propabilidadeAux = (1.0*contagemTotalAtributo)/(1.0*somaSaida);
				if(propabilidadeAux > maiorProbabilidade){
					maiorProbabilidade = propabilidadeAux;
					System.out.println("Valor provável: " + valorEntrada.getName() + " Probabilidade: " + maiorProbabilidade);
					
					valorProvavel = valorEntrada;
				}
		}
		
		return valorProvavel;
	}

	/**
	 * Computa a entropia de uma lista de exemplos com base na classificaï¿½ï¿½o em relaï¿½ï¿½o a certo
	 * atributo.
	 * @param classifier Classificador do atributo.
	 * @param outputClassifier Classificador de saï¿½das.
	 * @param inputs Lista de entradas.
	 * @return Entropia dessa lista de exemplos.
	 */
	private static double calculateEntropy(Classifier classifier, Classifier outputClassifier, List<Input> inputs) {
		int inputCount = inputs.size();
		List<ValueClass> classList = classifier.listClasses();
		List<ValueClass> outputClassList = outputClassifier.listClasses();

		// conta quantas vezes cada classe aparece na lista de exemplos.
		int[] classOcurrences = new int[classifier.countClasses()+1];
		for (int i = 0; i < inputs.size(); i++) {
			Input input = inputs.get(i);
			ValueClass valueClass = input.classify((SymbollicAttribute)classifier, outputClassList);
			int classNumber = classList.indexOf(valueClass);
			if(classNumber == -1){
				//atributo desconhecido
				valueClass = getIndiceValorMaisProvavel(i,classifier, outputClassifier, classList,outputClassList,inputs);
				classNumber = classList.indexOf(valueClass);
			}
			classOcurrences[classNumber]++;
		}

		// computa a entropia para cada classe, e faz o somatï¿½rio.
		double totalEntropy = 0;
		for (int v = 0; v < classOcurrences.length; v ++) if (classOcurrences[v] != 0) {
			ValueClass value = classList.get(v);

			// conta quantas vezes cada classe de saï¿½da aparece nos valores dessa classe.
			int outputClassOcurrences[] = new int[outputClassifier.countClasses()];
			for (Input input : inputs) if (input.isClassifiedAs(value)) {
				int outputClassNumber = outputClassList.indexOf(input.classify((SymbollicAttribute)outputClassifier, outputClassList));
				outputClassOcurrences[outputClassNumber] ++;
			}

			// computa a entropia para cada saï¿½da.
			double classEntropy = 0;
			for (int c = 0; c < outputClassOcurrences.length; c ++) {
				double p = outputClassOcurrences[c] / (double)classOcurrences[v];
				if (p != 0)
					classEntropy -= p * Math.log(p) / log2;
			}

			// adiciona a entropia ï¿½ entropia total (de forma ponderada).
			totalEntropy += (classOcurrences[v] * classEntropy) / inputCount;
		}
		return totalEntropy;
	}
}
