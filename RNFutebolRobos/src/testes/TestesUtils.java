package testes;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import redesneuraissi.EntradasList;
import redesneuraissi.Utils;


public class TestesUtils extends TestCase {
	@Test
	public void testarEmbaralhamento(){
		Utils metodos = new Utils();
		int [] vetorValores = metodos.gerar4ValoresDistintos0a8();
		for(int valor : vetorValores)
			System.out.print(valor + " ");
		System.out.println();
		vetorValores = metodos.gerar4ValoresDistintos0a8();
		for(int valor : vetorValores)
			System.out.print(valor + " ");
	}
	
	
	@Test
	public void testarEntradasList(){
		EntradasList lista = new EntradasList();
		double [] valores = new double[] {1.0, 2.0, 3.0, 4.0, 5.0};
		lista.adicionarEntrada(valores);
		assertEquals(true,lista.contem(new double[] {1.0, 3.0, 4.0, 2.0, 5.0}));
	}
}
