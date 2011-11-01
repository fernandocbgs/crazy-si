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
import id3.data.ClassListClassifier;
import id3.data.Classifier;
import id3.data.Input;
import id3.data.InputComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Atributo de tipo double (numérico).
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class NumericalAttribute implements Attribute {
	private String name;
	private Comparator<Input> comparator;

	public NumericalAttribute(String name) {
		this.name = name;
		this.comparator = new InputComparator(this);
	}

	@Override public Classifier getClassifier(List<Input> data) {
		Collections.sort(data, comparator);
		double meio = (Double) (data.get(data.size() / 2).getValue(this).getObject());
		return new ClassListClassifier(this, new NumericalClass.LesserThan(this, meio, false), new NumericalClass.GreaterThan(this, meio, true));
	}

	@Override public String getName() {
		return name;
	}

	@Override public boolean equals(Object object) {
		if (object instanceof NumericalAttribute) {
			NumericalAttribute attribute = (NumericalAttribute) object;
			if (getName().equals(attribute.getName())) return true;
			else return false;
		}
		return false;
	}

	@Override public int compareTo(Attribute attribute) {
		return getName().compareTo(attribute.getName());
	}
}
