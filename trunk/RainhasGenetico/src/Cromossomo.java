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
	
	/**
	 * Um cromossomo representa a posição de todas as rainhas no tabuleiro
	 * Assim, como o tabuleiro Ã© bidimensional temos para cada rainha 3 bits para uma posiÃ§Ã£o e 3 para outra
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
		//para cada rainha, verifica se ela está na mesma linha
		//compara o x com o y
		for(int i = 0; i < 7; i++)
		{
			 int posXRainha1 = getPosicaoRainha(valorCromossomo.get(i*6,i*6+3));
			 int posXRainha2 = getPosicaoRainha(valorCromossomo.get(i*6+6,i*6+6+3));
			 int posYRainha1 = getPosicaoRainha(valorCromossomo.get(i*6+3,i*6+6));
			 int posYRainha2 = getPosicaoRainha(valorCromossomo.get(i*6+6+3,i*6+6+6));
			 //afora vamos verificar
			 if(naMesmaCoordenada(posXRainha1, posXRainha2))
			 {
				 fitness++;
			 }
			 if(naMesmaCoordenada(posYRainha1, posYRainha2)){
				 fitness++;
			 }
			 if(naMesmaDiagonal(posXRainha1, posYRainha1, posXRainha2, posYRainha2))
			 {
				 fitness++;
			 }
		}
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
	 * Retorna se a rainha está na mesma linha que a outra
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
	 * Retorna se a rainha está na mesma diagonal que a outra
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
	private BitSet gerarCromossomoAleatorio()
	{
		BitSet cromossomoAleatorio = new BitSet(48);
		//inicia o cromossomo setando alguns valores de forma aleatória
		for(int i = 0; i < 8; i++){		
			//a coluna é setada, a linha não
			String coluna = getBinario(i, 3);
			for(int k = 0; k < 3; k++){
				if((coluna.charAt(k)) == '1'){
					cromossomoAleatorio.set(i*6+k, true);
				}
				else{
					cromossomoAleatorio.set(i*6+k, false);
				}
			}
			
			for(int j = 3; j < 6; j++){
				//sorteia um valor de 0 a 1, se for maior que 0,5 seta o bit do cromossomo
				//50% de chance de ser 0 e 50% de ser 1
				double valorAleatorio = Math.random();
				if(valorAleatorio > 0.5){
					cromossomoAleatorio.set(i*6+j);
				}
			}
			
			//imprime a posição da rainha
//			for(int l = 0; l < 6; l++)
//			{
//				System.out.print(cromossomoAleatorio.get(i*6+l) ? "1" : "0");
//			}
//			System.out.println("");
			
		}

//		for(int i = 0; i < 48; i++){
//			System.out.print(cromossomoAleatorio.get(i) ? "1" : "0");
//		}
//		System.out.println();
		return cromossomoAleatorio;
	}
	
	public void print()
	{
		for(int i =0; i < 48; i++)
		{
			if(i%6 == 0){
				System.out.print(":");
			}
			System.out.print(valorCromossomo.get(i) ? "1" : "0");

		}
		System.out.println("");
	}
	
	public static int getDecimal(String binario){
		System.out.println(binario);
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

	public BitSet getBitsMutacao(int inicio)
	{
		return valorCromossomo.get(inicio, inicio+6);
	}
	
	public void trocarBits(int inicio, BitSet bits)
	{
		for(int i = 0; i < 6; i++)
		{
			valorCromossomo.set(i+inicio, bits.get(i));
		}
	}
}
