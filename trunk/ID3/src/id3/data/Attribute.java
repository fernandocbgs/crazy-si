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

package id3.data;

import java.util.List;

/**
 * Atributo.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public interface Attribute extends Comparable<Attribute> {
	/**
	 * Obtém um classificador para um conjunto de dados.
	 * @param data Conjunto de dados.
	 * @return Classificador para esse conjunto de dados, com relação a
	 * esse atributo.
	 */
	public Classifier getClassifier(List<Input> data);

	/**
	 * Obtém o nome desse atributo.
	 * @return Nome do atributo.
	 */
	public String getName();
}
