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

import id3.data.Input;
import id3.data.ValueClass;

/**
 * Nó-folha de uma árvore de decisão.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class LeafNode extends Node {
	/**
	 * Classificações das saídas para esse nó.
	 */
	protected ValueClass[] outputs;

	/**
	 * Cria um nó-folha a partir de uma lista de classificações de saídas.
	 * @param outputs Classificações das saídas.
	 */
	public LeafNode(ValueClass... outputs) {
		this.outputs = outputs;
	}

	@Override public ValueClass[] getOutput(Input input) {
		return outputs;
	}

	@Override protected String print(int depth) {
		return pipes(depth) + combine(outputs, ", ") + "\n";
	}
}
