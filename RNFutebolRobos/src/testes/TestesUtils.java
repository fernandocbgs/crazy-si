package testes;

import junit.framework.TestCase;

import org.junit.Test;

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
}
