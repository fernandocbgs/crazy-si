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

package id3.data.symbollic;

import id3.data.Attribute;
import id3.data.Classifier;
import id3.data.Input;
import id3.data.ValueClass;
import id3.data.ValueInstance;

import java.security.InvalidParameterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Atributo de tipo string (simbólico). Por conveniência, é também um classificador.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class SymbollicAttribute implements Attribute, Classifier {
	/**
	 * Nome do atributo.
	 */
	public String name;

	/**
	 * Classes de valores possíveis.
	 */
	private List<ValueClass> valueClasses;

	/**
	 * Cria um atributo de tipo string.
	 * @param name Nome do atributo.
	 * @param values Valores válidos para o atributo.
	 */
	public SymbollicAttribute(String name, String... values) {
		this.name = name;
		valueClasses = new ArrayList<ValueClass>();
		for (int i = 0; i < values.length; i ++)
			valueClasses.add(new SymbollicValue(values[i], this));
	}

	@Override public String getName() {
		return name;
	}

	@Override public Classifier getClassifier(List<Input> data) {
		return this;
	}

	@Override public ValueClass classify(ValueInstance value) throws InvalidParameterException {
		int index = valueClasses.indexOf(value);
		if (index < 0) throw new InvalidParameterException("O valor " + value.getObject() + " não é classificável como " + getName());
		return valueClasses.get(index);
	}
	
	public ValueClass classify(ValueInstance value, List<ValueClass> outputClassList) throws InvalidParameterException {
		int index = valueClasses.indexOf(value);
		if (index < 0) throw new InvalidParameterException("O valor " + value.getObject() + " não é classificável como " + getName());
		return valueClasses.get(index);
	}

	@Override public List<ValueClass> listClasses() {
		return Collections.unmodifiableList(valueClasses);
	}

	@Override public int countClasses() {
		return valueClasses.size();
	}

	@Override public Attribute getAttribute() {
		return this;
	}

	@Override public int compareTo(Attribute attribute) {
		return getName().compareTo(attribute.getName());
	}

	@Override public boolean equals(Object object) {
		if (object instanceof SymbollicAttribute) {
			SymbollicAttribute attribute = (SymbollicAttribute) object;
			if (getName().equals(attribute.getName())) return true;
			else return false;
		}
		return false;
	}
}
