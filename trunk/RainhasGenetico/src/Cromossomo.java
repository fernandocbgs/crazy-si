import java.util.BitSet;

/**
 * Define um cromossomo
 * 
 * @author lucas
 * @author emerson
 *
 */
public class Cromossomo implements Comparable{
	
	//fitness do cromossomo
	private double fitness;
	
	/**
	 * Um cromossomo representa a posição de todas as rainhas no tabuleiro
	 * Assim, como o tabuleiro é bidimensional temos para cada rainha 3 bits para uma posição e 3 para outra
	 * ou 6 bits por rainha
	 * como temos 8 rainhas = 48 bits
	 */
	private BitSet valorCromossomo = new BitSet(48);
	
	public Cromossomo()
	{
		valorCromossomo = gerarCromossomoAleatorio();
	}
	
	
	public int calculaColisoes()
	{
	    return 0;	
	}
	
	public void mutacao()
	{
		valorCromossomo.or(gerarCromossomoAleatorio());
	}


	@Override
	public int compareTo(Object cromos) {
		Cromossomo cromossomo = (Cromossomo) cromos;
		
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
	 * Calcula o fitness baseado no número de colisões
	 */
	public void calcularFitness()
	{
		
	}
	
	/**
	 * Retorna um valor aleatório de cromossomo para ser criado
	 * É usado quando o cromossomo é iniciado e também na mutação para gerar um cromossomo de mutação
	 * @return
	 */
	private BitSet gerarCromossomoAleatorio()
	{
		BitSet cromossomoAleatorio = new BitSet(48);
		//inicia o cromossomo setando alguns valores de forma aleatória
		for(int i = 0; i < 64; i++)
		{
			//sorteia um valor de 0 a 1, se for maior que 0,5 seta o bit do cromossomo
			//50% de chance de ser 0 e 50% de ser 1
			double valorAleatorio = Math.random();
			if(valorAleatorio > 0.5){
				cromossomoAleatorio.set(i);
			}
		}
		return cromossomoAleatorio;
	}
	
	public double getFitness()
	{
		return this.fitness;
	}
}
