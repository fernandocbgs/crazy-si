/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redesneuraissi;

import java.util.Scanner;

/**
 *
 * @author Lucas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite:");
        System.out.println("1 - Treinar rede neural");
        System.out.println("2 - Testar rede neural (Só funciona com redes previamente treinadas)");
        int opcao = sc.nextInt();
        if (opcao == 1) {
            TreinaRedeNeural treinador = new TreinaRedeNeural();
        } else if (opcao == 2) {
            TestaRedeNeural teste = new TestaRedeNeural();
            teste.testarRedeNeuralTreinamento();
            //nunca faça testes de treinamento depois, já que os vetores sao ordenados!
            teste.testarRedeNeuralTreinamento();
        }
        System.out.println("Fim da execução...");
    }
}
