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

import id3.data.symbollic.SymbollicAttribute;
import id3.data.symbollic.SymbollicValue;

import java.security.InvalidParameterException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Um dado (exemplo) para geração da árvore.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class Input {
	/**
	 * Mapeamento de atributos aos respectivos valores.
	 */
	Map<Attribute, ValueInstance> map;

	/**
	 * Gera uma entrada (exemplo).
	 * @param attributes Lista de atributos.
	 * @param values Lista de valores.
	 */
	public Input(Attribute[] attributes, ValueInstance[] values) {
		if (attributes.length != values.length)
			throw new InvalidParameterException("O número de atributos e valores é diferente.");
		map = new TreeMap<Attribute, ValueInstance>();
		for (int i = 0; i < attributes.length; i ++)
			map.put(attributes[i], values[i]);
	}

	/**
	 * Gera uma entrada (exemplo) em que todos os atributos são do tipo string.
	 * @param attributes Lista de atributos.
	 * @param strings Lista de valores.
	 */
	public Input(SymbollicAttribute[] attributes, String[] strings) {
		if (attributes.length != strings.length)
			throw new InvalidParameterException("O número de atributos e valores é diferente.");
		map = new TreeMap<Attribute, ValueInstance>();
		for (int i = 0; i < attributes.length; i ++)
			map.put(attributes[i], new SymbollicValue(strings[i], attributes[i]));
	}

	/**
	 * Obtém o valor associado a certo atributo
	 * @param attribute Atributo.
	 * @return Valor associado a esse atributo.
	 */
	public ValueInstance getValue(Attribute attribute) {
		if (map.containsKey(attribute))
			return map.get(attribute);
		throw new InvalidParameterException("Atributo " + attribute.getName() + " não existe na entrada " + toString() + ".");
	}

	/**
	 * Método conveniente para obte a classe de valores ao qual pertence o valor associado a certo atributo.
	 * @param classifier Classificador.
	 * @return Classe de valores ao qual pertence o valor associado a esse atributo.
	 */
	public ValueClass classify(SymbollicAttribute classifier, List<ValueClass> outputClassList) {
		ValueClass value;
		try{
		        value = classifier.classify(getValue(classifier.getAttribute()),outputClassList);
		}
		catch(InvalidParameterException i)
		{
			System.out.println("Entrada desconhecida");
			value = new SymbollicValue("unknown", (SymbollicAttribute)classifier.getAttribute());
		}
		return value;
	}
	
	public ValueClass classify(SymbollicAttribute classifier) {
		return classifier.classify(getValue(classifier.getAttribute()));
	}

	/**
	 * Método conveniente para verificar se o valor associado a certo atributo pertence a certa classe.
	 * @param valueClass Classe de valores.
	 * @return Se o valor associado a certo atributo pertence a essa classe.
	 */
	public boolean isClassifiedAs(ValueClass valueClass) {
		return valueClass.isClassOf(getValue(valueClass.getAttribute()));
	}
}
