import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

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
	
	public void CalculoColisoesEstado(){
		ColisoesEstado = 0;
		String rainha = "";
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				ColisoesEstado += colisoesRainha(rainha);
				rainha="";
			}
			rainha+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		ColisoesEstado += colisoesRainha(rainha);
	}
	
	public void AtualizaRainha(String rainhaAntiga, String rainhaNova){
		if (rainhaAntiga.equals(rainhaNova)) {return;}
		String rainha = "";
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				if (rainha.equals(rainhaAntiga)){ //substituir
					String[] pos = rainhaNova.split("");  //o split não pega a 1ª posição
					int c = i-6;
					for (int j = 1; j < pos.length; j++){
						estado.set(c+j-1, c+j, pos[j].equals("1") );
					}
					return;
				}
				
				//rainha
				rainha="";
			}
			rainha+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		if (rainha.equals(rainhaAntiga)){ //substituir
			String[] pos = rainhaNova.split("");  //o split não pega a 1ª posição
			int c = 47-6;
			for (int j = 1; j < pos.length; j++){
				estado.set(c+j-1, c+j, pos[j].equals("1") );
			}
		}
	}
	
	/**
	 * seta a população
	 * */
	public void setQueens(String queens){
		String[] pos = queens.split(""); //o split não pega a 1ª posição
		for (int j = 1; j < 48; j++){
			estado.set(j-1, pos[j].equals("1"));
		}
	}
	
	//retorna uma lista de Rainhas
	public List<String> getRainhas(){
		List<String> rt = new ArrayList<String>();
		String rainha = "";
		for(int i = 0; i < 48; i++) {
			if (i > 0 && i%6==0) {
				rt.add(rainha);
				rainha="";
			}
			rainha+=(estado.get(i) ? "1" : "0");
		}
		//last queen
		rt.add(rainha);
		return rt;
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
		if ( (x == xC && y!=yC) || 
				(y == yC && x != xC) || 
				(Math.abs(x-xC) == Math.abs(y-yC) /*diagonal*/ )){
			return true;
		}
		return false;
	}
	
	public Estado copiar(){
		return copiar(this);
	}
	
	//como java referencia objetos, é necessário se copiar item à item
	public Estado copiar(Estado original){
		Estado rt = new Estado();
		String estado = "";
		List<String> rainhas = original.getRainhas();
		for (String r:rainhas){
			estado += r;
		}
		rt.setQueens(estado);
		return rt;
	}
	
	public boolean equals(Estado e){
		//compara as rainhas
		boolean achou = false;
		for (String r : getRainhas()){
			achou = false;
			for (String rComp : e.getRainhas()){
				if (r.equals(rComp)) {achou = true; continue;}
			}
			if (!achou) {return false;} //já é diferente não achou uma rainha
		}
		return true;
	}
	
	public void print(){
		if (ColisoesEstado == 0){CalculoColisoesEstado();}
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

	public static String getBinario(int decimal, int tam){
		String rt = Integer.toString(decimal, 2);
		while (rt.length() < tam) {rt = "0" + rt; }
		return rt;
	}
	
	public static String getBinario(int decimal){
		return Integer.toString(decimal, 2);
	}
	
}
