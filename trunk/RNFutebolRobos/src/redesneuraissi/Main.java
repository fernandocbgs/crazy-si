/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redesneuraissi;

import java.util.Scanner;

/**
 * 
 * @author Emerson
 * @author Lucas
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int entrada = 0;
		do {
			System.out.println("Digite:");
			System.out.println("1 - Treinar rede neural");
			System.out.println("2 - Testar rede neural (Só funciona com redes previamente treinadas)");
			System.out.println("3 - Sair");
			entrada = sc.nextInt();
			if (entrada == 1) {
				TreinaRedeNeural treinador = new TreinaRedeNeural();
			} else if (entrada == 2) {
				TestaRedeNeural teste = new TestaRedeNeural();
				// nunca faça testes de treinamento depois, já que os vetores
				// sao ordenados!
				teste.testarRedeNeuralTreinamento();
			}
		} while (entrada != 3);

		System.out.println("Fim da execução...");
	}
}