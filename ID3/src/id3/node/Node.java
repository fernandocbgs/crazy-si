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
 * Nó de uma árvore de decisão.
 * @author Felipe
 * @author Thayse
 */
public abstract class Node {
	/**
	 * Obtém a decisão para certa entrada.
	 * @param input Entrada.
	 * @return Saída.
	 */
	public abstract ValueClass[] getOutput(Input input);

	public String print() {
		return print(0);
	}

	protected abstract String print(int depth);
	
	protected String pipes(int length) {
		String s = "";
		for (int i  = 0; i < length; i ++) s += "|  ";
		return s;
	}

	protected String combine(ValueClass[] outputs, String separator) {
		if (outputs == null) return "?";
		if (outputs.length == 0) return "WTF";
		if (outputs.length == 1) return outputs[0].getName();
		String result = outputs[0].getName();
		for (int i = 1; i < outputs.length; i ++)
			result += separator + outputs[i].getName();
		return result;
	}
}
