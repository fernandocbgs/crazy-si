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

package id3.data.numerical;

import id3.data.Attribute;
import id3.data.ValueClass;
import id3.data.ValueInstance;

import java.security.InvalidParameterException;

/**
 * Classe de valores do tipo double (numérico).
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public abstract class NumericalClass implements ValueClass {
	private String name;
	private NumericalAttribute attribute;

	public NumericalClass(String name, NumericalAttribute attribute) {
		this.name = name;
		this.attribute = attribute;
	}

	@Override public String getName() {
		return name;
	}

	@Override public Attribute getAttribute() {
		return attribute;
	}

	@Override public boolean equals(Object obj) {
		if (obj instanceof ValueClass) {
			ValueClass valueClass = (ValueClass) obj;
			return getAttribute().equals(valueClass.getAttribute()) && getName().equals(valueClass.getName());
		}
		return false;
	}

	/**
	 * Classe de valores do tipo double (numéricos) maiores (ou maiores ou iguais) que certo valor.
	 * @author Felipe Michels Fontoura
	 * @author Thayse Marques Solis
	 */
	public static class GreaterThan extends NumericalClass {
		private double reference;
		private boolean equals;

		public GreaterThan(NumericalAttribute attribute, double value, boolean equals) {
			super((equals ? ">=" : ">") + ' ' + value, attribute);
			this.reference = value;
			this.equals = equals;
		}

		@Override public boolean isClassOf(ValueInstance value) {
			Object object = value.getObject();
			if (object instanceof Double) {
				double doubleValue = (Double) object;
				if (equals) return doubleValue >= reference;
				else return doubleValue > reference;
			}
			throw new InvalidParameterException("O valor " + value.getObject() + " não é numérico.");
		}
	}

	/**
	 * Classe de valores do tipo double (numéricos) menores (ou menores ou iguais) que certo valor.
	 * @author Felipe Michels Fontoura
	 * @author Thayse Marques Solis
	 */
	public static class LesserThan extends NumericalClass {
		private double reference;
		private boolean equals;

		public LesserThan(NumericalAttribute attribute, double value, boolean equals) {
			super((equals ? "<=" : "<") + ' ' + value, attribute);
			this.reference = value;
			this.equals = equals;
		}

		@Override public boolean isClassOf(ValueInstance value) {
			Object object = value.getObject();
			if (object instanceof Double) {
				double doubleValue = (Double) object;
				if (equals) return doubleValue <= reference;
				else return doubleValue < reference;
			}
			throw new InvalidParameterException("O valor " + value.getObject() + " não é numérico.");
		}
	}
}
