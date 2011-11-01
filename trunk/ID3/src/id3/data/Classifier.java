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
 * Classificador de valores de certo atributo.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public interface Classifier {
	/**
	 * Classifica um certo valor.
	 * @param value Valor a classificar.
	 * @return Classificação do valor.
	 */
	public ValueClass classify(ValueInstance value);

	/**
	 * Obtém uma listagem de classificações.
	 * @return Listagem de classificações possíveis.
	 */
	public List<ValueClass> listClasses();

	/**
	 * Obtém o total de classificações possíveis.
	 * @return Contagem de classificações possíveis.
	 */
	public int countClasses();

	/**
	 * Obtém o atributo com relação ao qual foi feita essa classificação.
	 * @return Atributo.
	 */
	public Attribute getAttribute();
}
