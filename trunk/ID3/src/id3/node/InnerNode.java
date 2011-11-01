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

package id3.node;

import java.util.ArrayList;
import java.util.List;

import id3.data.Classifier;
import id3.data.Input;
import id3.data.ValueClass;
import id3.data.symbollic.SymbollicAttribute;
import id3.data.symbollic.SymbollicValue;

/**
 * Nó interno (não-folha) de uma árvore de decisão.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class InnerNode extends Node {
	/**
	 * Classificador de valores.
	 */
	private Classifier classifier;

	/**
	 * Nós-filhos desse nó.
	 */
	private Node[] children;

	/**
	 * Cria um nó interno a partir de seu classificador e de seus nós-filhos.
	 * @param classifier Classificador de valores.
	 * @param children Nós-filhos.
	 */
	public InnerNode(Classifier classifier, Node[] children) {
		this.classifier = classifier;
		this.children = children;
	}

	@Override public ValueClass[] getOutput(Input input) {
		ValueClass value = input.classify((SymbollicAttribute)classifier, new ArrayList<ValueClass>());
		int i = classifier.listClasses().indexOf(value);
		return children[i].getOutput(input);
	}

	@Override protected String print(int depth) {
		String result = "";
		for (int i = 0; i < children.length; i ++) {
			ValueClass valueClass = classifier.listClasses().get(i);
			result += pipes(depth) + classifier.getAttribute().getName() + ' ' + ((valueClass instanceof SymbollicValue) ? ("= " + valueClass.getName()) : valueClass.getName());
			if (children[i] instanceof LeafNode) {
				LeafNode node = (LeafNode) children[i];
				result += ": " + combine(node.outputs, ", ") + "\n";
			} else result += "\n" + children[i].print(depth + 1);
		}
		return result;
	}
}
