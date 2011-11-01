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

import java.security.InvalidParameterException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classificador com base numa lista de classes de valores.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class ClassListClassifier implements Classifier {
	private ValueClass[] valueClasses;
	private Attribute attribute;

	/**
	 * Cria um classificador com base numa lista de classes de valores.
	 * @param attribute Atributo-base.
	 * @param valueClasses Lista de classes na qual se basear.
	 */
	public ClassListClassifier(Attribute attribute, ValueClass... valueClasses) {
		this.attribute = attribute;
		this.valueClasses = valueClasses;

		// faz uma validação dos dados.
		for (ValueClass valueClass : valueClasses)
			if (! valueClass.getAttribute().equals(attribute))
				throw new InvalidParameterException("A classe de valores fornecida não é compatível com o classificador.");
	}

	@Override public ValueClass classify(ValueInstance value) {
		for (ValueClass valueClass : valueClasses)
			if (valueClass.isClassOf(value))
				return valueClass;
		throw new InvalidParameterException("O valor " + value.getObject() + " não é classificável como " + attribute);
	}

	@Override public List<ValueClass> listClasses() {
		return Collections.unmodifiableList(Arrays.asList(valueClasses));
	}

	@Override public int countClasses() {
		return valueClasses.length;
	}

	@Override public Attribute getAttribute() {
		return attribute;
	}

}
