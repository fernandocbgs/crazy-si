package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
*
* @author Emerson
* @author Lucas
*/
public class EntradasRN {
	
	Random random = new Random();
	private int[] valoresDistintos = new int[9];
	private int[] camposMeioDefesa = new int[6];
	private int[] camposDefesa = new int[3];
	private int[] camposMeio = new int[3];
	private int[] camposAtaque = new int[3];
	
	public EntradasRN(){
		for(int i = 0; i < valoresDistintos.length; i++){
    		valoresDistintos[i] = i;
    	}
		//seta camposMeioDefesa
		camposMeioDefesa[0] = 0;
		camposMeioDefesa[1] = 3;
		camposMeioDefesa[2] = 6;
		camposMeioDefesa[3] = 1;
		camposMeioDefesa[4] = 4;
		camposMeioDefesa[5] = 7;
	    //setando cada campo
		camposDefesa[0] = 0;
		camposDefesa[1] = 3;
		camposDefesa[2] = 6;
		//meio
		camposMeio[0] = 1;
		camposMeio[1] = 4;
		camposMeio[2] = 7;
		//ataque
		camposAtaque[0] = 2;
		camposAtaque[1] = 5;
		camposAtaque[2] = 8;
		
	}
	
    public double geraErroPercentualErro(int total, int erros)
    {
        return 100.0*((double)erros)/((double)total);
    }
    
    /**
     * A ideia é permitir que um valor entre 0 e 8, garantindo um campo válido
     * @param valor
     * @return valor entre 0 e 8
     */
    public int[] gerar4ValoresDistintos0a8(){
        shuffleArray(valoresDistintos);
        return valoresDistintos;
    }
    
    /**
     * A ideia é permitir que um valor entre os campos de defesa e meio
     * @param valor
     * @return valor entre 0 e 8
     */
    public int[] gerar6ValoresDistintosCamposDefesaMeio(){
        shuffleArray(camposMeioDefesa);
        return camposMeioDefesa;
    }
    
    /**
     * Retorna se a posição é da defesa
     */
    public boolean naDefesa(int valor)
    {
    	if(valor == 0 || valor == 3 || valor == 6)
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Retorna se a posição é do ataque
     */
    public boolean noMeio(int valor)
    {
    	if(valor == 1 || valor == 4 || valor == 7)
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Retorna se a posição é do ataque
     */
    public boolean noAtaque(int valor)
    {
    	if(valor == 2 || valor == 5 || valor == 8)
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Retorna um valor da defesa aleatorio
     */
    public int getValorDefesa()
    {
    	Collections.shuffle(Arrays.asList(camposDefesa));
    	return camposDefesa[0];
    }
    
    /**
     * Retorna um valor do ataque aleatorio
     */
    public int getValorAtaque()
    {
    	Collections.shuffle(Arrays.asList(camposAtaque));
    	return camposAtaque[0];
    }
    
    /**
     * Retorna um valor do meio aleatorio
     */
    public int getValorMeio()
    {
    	Collections.shuffle(Arrays.asList(camposMeio));
    	return camposMeio[0];
    }
    
    /**
     * Gera uma posição aleatória para o goleiro
     * @return
     */
    public int gerarPosicaoGoleiro()
    {
    	return random.nextInt(2);
    }
    
    /**
     * Embaralha um vetor
     * @param a
     */
    public static void shuffleArray(int[] a) {
		int n = a.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(a, i, change);
		}
	}

	private static void swap(int[] a, int i, int change) {
		int helper = a[i];
		a[i] = a[change];
		a[change] = helper;
	}
}
