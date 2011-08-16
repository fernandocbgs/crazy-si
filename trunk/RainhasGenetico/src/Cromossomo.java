import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Define um cromossomo
 * 
 * @author lucas
 * @author emerson
 *
 */
public class Cromossomo implements Comparable<Cromossomo>{
	
	//fitness do cromossomo
	private double fitness;
	//numero de colisões para o cromossomo
	private double numeroDeColisoes = 0;
	
	/**
	 * Um cromossomo representa a posiï¿½ï¿½o de todas as rainhas no tabuleiro
	 * Assim, como o tabuleiro Ã© bidimensional temos para cada rainha 3 bits para uma posiÃ§Ã£o e 3 para outra
	 * ou 6 bits por rainha
	 * como temos 8 rainhas = 48 bits
	 */
	private int [] valorCromossomo = new int[8];
	
	public Cromossomo()
	{
		//inicia o valor do cromossomo
		for(int i = 0; i < valorCromossomo.length; i++)
		{
			valorCromossomo[i] = 0;
		}
		
		valorCromossomo = gerarCromossomoAleatorio();
	}
	
	public Cromossomo(int [] valorCromossomo)
	{
		this.valorCromossomo = valorCromossomo;
	}	
	
	public Cromossomo mutacao()
	{
		int [] valorCromossomoAux = valorCromossomo.clone();
		//sorteia uma rainha aleatoria de 0 a 7
		int rainha = (int)(8*Math.random());
		//depois um numero aleatorio de 0 a 7
		int posicaoAleatoria = (int)(8*Math.random());
		//e troca
		//antes de trocar vamos guardar o valor
		int antigaPosicao = valorCromossomoAux[rainha];
		valorCromossomoAux[rainha] = posicaoAleatoria;	
		//agora a posicao aleatoria provavelmente está duplicada
		for(int i = 0; i < valorCromossomoAux.length; i++)
		{
			if(valorCromossomoAux[i] == posicaoAleatoria && i != rainha)
			{
				valorCromossomoAux[i] = antigaPosicao;
			}
		}
		return new Cromossomo(valorCromossomoAux);
	}

	@Override
	public int compareTo(Cromossomo cromossomo) {
		
		if(this.fitness > cromossomo.getFitness())
		{
			return 1;
		}
		else if(this.fitness < cromossomo.getFitness())
		{
			return -1;
		}
		else{
		    return 0;
		}
	}	
	
	/**
	 * Calcula o fitness baseado no nÃºmero de colisÃµes
	 */
	public void calcularFitness(){
		double colisoes = 0.0;
		//para cada rainha, verifica se ela estï¿½ na mesma linha
		//compara o x com o y
		for(int i = 0; i < valorCromossomo.length; i++)
		{
			for(int j = 0; j < valorCromossomo.length; j++)
			{
				if(naMesmaCoordenada(valorCromossomo[i], valorCromossomo[j]))
				{
					colisoes++;
				} else if(naMesmaDiagonal(i, valorCromossomo[i], j, valorCromossomo[j]))
				{
					colisoes++;
				}
			}
		}
		//seta o fitness
		numeroDeColisoes = colisoes;
	    fitness = 1/(colisoes+1);	    
	}
	
	public double getNumeroDeColisoes() {
		return numeroDeColisoes;
	}

	public int getPosicaoRainha(BitSet coordenadaRainha)
	{
		String posRainha = "";
		//converte para string
		for(int i = 0; i < coordenadaRainha.size(); i++)
		{
			posRainha += coordenadaRainha.get(i) ? "1" : "0";
		}
		return getDecimal(posRainha);
	}
	
	/**
	 * Retorna se a rainha estï¿½ na mesma linha que a outra
	 * @return
	 */
	public boolean naMesmaCoordenada(int colRainha1, int colRainha2)
	{
		if(colRainha1 == colRainha2){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Retorna se a rainha estï¿½ na mesma diagonal que a outra
	 * @return
	 */
	public boolean naMesmaDiagonal(int posXRainha1, int posYRainha1, int posXRainha2, int posYRainha2)
	{
		int distanciaX = Math.abs(posXRainha1 - posXRainha2);
		int distanciaY = Math.abs(posYRainha1 - posYRainha2);
		if(distanciaX == distanciaY)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Retorna um valor aleatÃ³rio de cromossomo para ser criado
	 * Ã‰ usado quando o cromossomo Ã© iniciado e tambÃ©m na mutaÃ§Ã£o para gerar um cromossomo de mutaÃ§Ã£o
	 * @return
	 */
	private int [] gerarCromossomoAleatorio()
	{
		List<Integer> cromossomoAleatorio = new ArrayList<Integer>();
		//inicia o cromossomo setando alguns valores de forma aleatï¿½ria
	    for(int i = 0; i < 8; i++)
	    {
	    	cromossomoAleatorio.add(i);
	    }
	    //agora vamos embaralharar o cromossomo
	    Collections.shuffle(cromossomoAleatorio);
	    
	    int [] listaValores = new int[cromossomoAleatorio.size()];
	    for(int i = 0; i < listaValores.length; i++){
	    	listaValores[i] = cromossomoAleatorio.get(i);
	    }
	   	
	    return listaValores;
	}
	
	/**
	 * retorna verdadeiro se o cromossomo contem o dado valor
	 * @param valor
	 * @return
	 */
	private boolean contemValor(int valor){
		for(int i = 0; i < valorCromossomo.length; i++)
		{
			if(valorCromossomo[i] == valor)
			{
				return true;
			}
		}
		return false;
	}
	
	public void printRainhas()
	{
		for(int i = 0; i < valorCromossomo.length; i++)
		{
			System.out.print("{"+valorCromossomo[i]+"} ");
		}
	}
	
	public void print()
	{
		
		for(int i =0; i < valorCromossomo.length; i++)
		{
			for(int j = 0; j < valorCromossomo.length; j++)
			{
				if(j == valorCromossomo[i]){
					System.out.print("1    ");
				}
				else{
					System.out.print("0    ");
				}
			}
			System.out.println("");
		}
	}
	
	
	public static int getDecimal(String binario){
//		System.out.println(binario);
		return Integer.parseInt(binario, 2);
	}	
	
	static String getBinario(int decimal){
		return Integer.toString(decimal, 2);
	}
	
	public static String getBinario(int decimal, int tam){
		String rt = Integer.toString(decimal, 2);
		while (rt.length() < tam) {rt = "0" + rt; }
		return rt;
	} 
	
	public double getFitness()
	{
		return this.fitness;
	}
	
	public int [] getValorParaCrossOver(){
		//sorteia uma rainha aleatoria
		int rainhaAleatoria = (int)(8*Math.random());
		//e uma posição aleatória
		int posicaoRainhaAleatoria = valorCromossomo[rainhaAleatoria];
		//agora declaramos um array e enviamos
		int [] rainhaPosicao = new int[2];
		//na sequencia: Rainha depois posição
		rainhaPosicao[0] = rainhaAleatoria;
		rainhaPosicao[1] = posicaoRainhaAleatoria;
		return rainhaPosicao;
	}
	
	public int getPosicaoRainha(int index){
		return valorCromossomo[index];
	}
	
	public void setPosicaoRainha(int rainha, int valor)
	{
		valorCromossomo[rainha] = valor;
	}
	
	public int getRainhaPorPosicao(int posicao){
		for(int i = 0; i < valorCromossomo.length; i++)
		{
			if(valorCromossomo[i] == posicao){
				return i;
			}
		}
		//só pra indicar erro
		return Integer.MAX_VALUE;
	}
	
	public Cromossomo clone()
	{
		return new Cromossomo(this.valorCromossomo);
	}
}
