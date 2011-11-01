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

package id3.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;

import id3.data.numerical.NumericalAttribute;
import id3.data.numerical.NumericalValue;

import id3.data.symbollic.SymbollicAttribute;
import id3.data.symbollic.SymbollicValue;

import id3.data.Attribute;
import id3.data.Input;
import id3.data.ValueInstance;

/**
 * Estrutura de dados lida de um leitor para armazenar uma lista de atributos e outra de entradas.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class TrainingData {
	public Input[] inputs;
	public Attribute[] attributes;
	public String relationName;

	public TrainingData() {}

	public static TrainingData readReader(Reader reader) throws IOException {
		BufferedReader buffer = new BufferedReader(reader);
		List<Attribute> attributeList = new ArrayList<Attribute>();
		Attribute[] attributes = null;
		List<Input> inputs = null;
		String relationName = "";
		for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
			line = line.trim();
			if (line.length() == 0) continue;

			if (inputs == null) {
				// l� a tag @relation
				try {
					if (line.substring(0, 9).equals("@relation")) {
						relationName = line.substring(9).trim();
					}
				} catch (Exception e) {}

				// l� a tag @attribute
				try {
					if (line.substring(0, 10).equals("@attribute")) {
						line = line.substring(10).trim() + ' ';
						String name = line.substring(0, line.indexOf(' '));
						line += ' ';
						line = line.substring(line.indexOf(' ')).trim();
						if (line.length() > 2) {
							line = line.substring(1, line.length() - 1);
						}
						if (line.length() == 0) {
							attributeList.add(new NumericalAttribute(name));
						} else {
							String[] values = line.split(",");
							for (int i = 0; i < values.length; i ++)
								values[i] = values[i].trim();
							attributeList.add(new SymbollicAttribute(name, values));
						}
					}
				} catch (Exception e) {}

				// l� a tag @data
				try {
					if (line.substring(0, 5).equals("@data")) {
						inputs = new ArrayList<Input>();
						attributes = attributeList.toArray(new Attribute[attributeList.size()]);
					}
				} catch (Exception e) {}
			} else {
				// l� um exemplo.
				try {
					String[] stringSplit = line.split(",");
					ValueInstance[] values = new ValueInstance[stringSplit.length];
					for (int i = 0; i < stringSplit.length; i ++) {
						stringSplit[i] = stringSplit[i].trim();
						if (attributes[i] instanceof SymbollicAttribute) {
							// valor simb�lico
							values[i] = new SymbollicValue(stringSplit[i], (SymbollicAttribute) attributes[i]);
						} else {
							// valor num�rico
							double doubleValue = 0;
							try {
								doubleValue = Double.parseDouble(stringSplit[i]);
							} catch (NumberFormatException e) {};
							values[i] = new NumericalValue(doubleValue, (NumericalAttribute) attributes[i]);
						}
					}
					inputs.add(new Input(attributes, values));
				} catch (Exception e) {}
			}
		}

		// cria o conjunto de dados.
		TrainingData training = new TrainingData();
		training.attributes = attributes;
		training.inputs = inputs.toArray(new Input[inputs.size()]);
		training.relationName = relationName;
		return training;
	}
}
