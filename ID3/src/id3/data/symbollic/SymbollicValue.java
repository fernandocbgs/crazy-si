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
import id3.data.ValueClass;
import id3.data.ValueInstance;

/**
 * Valor do tipo string (simbólico). Por conveniência, é também uma classe de valores.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class SymbollicValue implements ValueClass, ValueInstance {
	private String value;
	private SymbollicAttribute attribute;

	public SymbollicValue(String value, SymbollicAttribute attribute) {
		this.value = value;
		this.attribute = attribute;
	}
	
	@Override public Attribute getAttribute() {
		return attribute;
	}

	@Override public String getName() {
		return value;
	}

	@Override public Object getObject() {
		return value;
	}

	@Override public boolean isClassOf(ValueInstance value) {
		return getObject().equals(value.getObject());
	}

	@Override public boolean equals(Object obj) {
		if (obj instanceof ValueClass) {
			ValueClass valueClass = (ValueClass) obj;
			return getAttribute().equals(valueClass.getAttribute()) && getName().equals(valueClass.getName());
		}
		if (obj instanceof ValueInstance) {
			ValueInstance value = (ValueInstance) obj;
			return getAttribute().equals(value.getAttribute()) && getObject().equals(value.getObject());
		}
		return super.equals(obj);
	}
}
