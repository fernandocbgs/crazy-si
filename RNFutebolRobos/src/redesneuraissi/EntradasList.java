package redesneuraissi;

import java.util.ArrayList;
import java.util.Arrays;

public class EntradasList {

	private ArrayList<double[]> _entradas = new ArrayList<double[]>();

	public void adicionarEntrada(double[] entrada) {
		_entradas.add(entrada);
	}

	public boolean contem(double[] entrada) {
		Arrays.sort(entrada, 1, entrada.length-1);
		// ordena os dois vetores
		for (int i = 0; i < _entradas.size(); i++) {
			double[] valorJaAdicionado = _entradas.get(i);
			if (entrada.length == valorJaAdicionado.length) {
				// ordena o valor já utilizado
				Arrays.sort(valorJaAdicionado, 1, valorJaAdicionado.length-1);
				for (int j = 0; j < _entradas.get(i).length; j++) {
					if (entrada[j] != valorJaAdicionado[j]) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		if (_entradas.size() != 0)
			return true;
		else
			return false;
	}

}
