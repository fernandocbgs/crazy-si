import java.util.BitSet;

/**
 * Define um estado
 *
 * @author emerson
 * @author lucas
 *
 */
public class Estado {
	
	public int ColisoesEstado; /*total de colisões deste estado*/
	
	/**
	 * Um estado representa a posição de todas as rainhas no tabuleiro
	 * Assim, como o tabuleiro é bidimensional temos para cada rainha 3 bits para uma posição e 3 para outra
	 * ou 6 bits por rainha
	 * como temos 8 rainhas = 48 bits
	 */
	private BitSet estado; // = new BitSet(48);
	
	public Estado(){
		estado = gerarEstadoAleatorio();
		CalculoColisoesEstado();
	}
	/**
	 * Retorna um valor aleatório de cromossomo para ser criado
	 * É usado quando o cromossomo é iniciado e também na mutação para gerar um cromossomo de mutação
	 * @return
	 */
	private BitSet gerarEstadoAleatorio() {
		BitSet estadoAleatorio = new BitSet(48);
		//inicia o cromossomo setando alguns valores de forma aleatória
		for(int i = 0; i < 48; i++) {
			//sorteia um valor de 0 a 1, se for maior que 0,5 seta o bit do cromossomo
			//50% de chance de ser 0 e 50% de ser 1
			double valorAleatorio = Math.random();
			if(valorAleatorio > 0.5){
				estadoAleatorio.set(i);
			}
		}
		return estadoAleatorio;
	}
	
	private void CalculoColisoesEstado(){
		ColisoesEstado = 0;
		String rainha = "", x, y;
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				x = rainha.substring(0, 3);
				y = rainha.substring(3, 6);
				ColisoesEstado += colisoesRainha(rainha);
				rainha="";
			}
			rainha+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		ColisoesEstado += colisoesRainha(rainha);
	}
	
	private int colisoesRainha(String rainha){
		int ncolisoes = 0;
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		//compara com os valores de x,y das outras rainhas
		String rainhaComparar = "", xC, yC;
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				if (!rainhaComparar.equals(rainha)) {
					xC = rainhaComparar.substring(0, 3);
					yC = rainhaComparar.substring(3, 6);
					if (colidiu(x, y, xC, yC)) ncolisoes++;
				}
				rainhaComparar="";
			}
			rainhaComparar+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		xC = rainhaComparar.substring(0, 3);
		yC = rainhaComparar.substring(3, 6);
		if (colidiu(x, y, xC, yC)) ncolisoes++;
		return ncolisoes;
	}
	
	private boolean colidiu(String x, String y, String xC, String yC){
		return colidiu(getDecimal(x), getDecimal(y), getDecimal(xC), getDecimal(yC));
	}
	private boolean colidiu(int x, int y, int xC, int yC){
		if (x == xC || y == yC || ( Math.abs(x-xC) == Math.abs(y-yC) /*diagonal*/ )){
			return true;
		}
		return false;
	}
	
	public void print(){
		String rainha = "", x, y;
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				x = rainha.substring(0, 3);
				y = rainha.substring(3, 6);
				System.out.println(rainha + " ["+x+","+y+"]=["+getDecimal(x)+","+getDecimal(y)+"], coli: " + colisoesRainha(rainha));
				rainha="";
			}
			rainha+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		System.out.println(rainha + " ["+x+","+y+"]=["+getDecimal(x)+","+getDecimal(y)+"], coli: " + colisoesRainha(rainha));
		System.out.println("Total Colisoes: " + ColisoesEstado);
		System.out.println();
	}
	
	public static int getDecimal(String binario){
		return Integer.parseInt(binario, 2);
	}
	public static String getBinario(int decimal){
		return Integer.toString(decimal, 2);
	}
	
}
