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
import id3.data.ValueInstance;

/**
 * Valor do tipo double (numérico).
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class NumericalValue implements ValueInstance {
	private Double value;
	private NumericalAttribute attribute;
	
	public NumericalValue(double value, NumericalAttribute attribute) {
		this.value = value;
	}

	@Override public Object getObject() {
		return value;
	}

	@Override public Attribute getAttribute() {
		return attribute;
	}

	@Override public boolean equals(Object object) {
		if (object instanceof ValueInstance) {
			ValueInstance value = (ValueInstance) object;
			return getObject().equals(value.getObject());
		}
		return false;
	}
}
