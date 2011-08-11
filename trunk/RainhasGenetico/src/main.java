import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class main {
	
	private static List<Colisoes> _colisoes = new ArrayList<Colisoes>();
	private static int _tabuleiro[][] = new int[8][8]; //8x8
	private static int _numeroRainhas = 8;
	
	public static void main(String[] args) {
		inicializaTabuleiro();
		print();
		
		AnaliseColisoes();
		printColisoes();
	}
	
	private static void inicializaTabuleiro(){
		for (int i = 0; i < _tabuleiro[0].length; i++) {
			for (int j = 0; j < _tabuleiro[1].length; j++) {
				_tabuleiro[i][j] = 0;
			}
		}
		
		TabuleiroTeste(); return;
		
		//comentado para testes
		/*int rainhas = _numeroRainhas;
		int posX, posY;
		while(rainhas > 0){
			//sorteia a posição inicial
			posX = randomizar(0, _tabuleiro[0].length);
			posY = randomizar(0, _tabuleiro[1].length);
			if (_tabuleiro[posX][posY] == 0){ //posição desocupada
				_tabuleiro[posX][posY] = 1; //rainha
				rainhas--;
			}
		}*/
	}
	
	//cria um tabuleiro para análise inicial
	private static void TabuleiroTeste(){
		/*
			00010000
			00000000
			00000000
			01011001
			00100010
			00100000
			00000000
			00000000
		 */
		_tabuleiro[0][3] = 1;
		_tabuleiro[3][1] = 1;
		_tabuleiro[3][3] = 1;
		_tabuleiro[3][4] = 1;
		_tabuleiro[3][7] = 1;
		_tabuleiro[4][2] = 1;
		_tabuleiro[4][6] = 1;
		_tabuleiro[5][2] = 1;
	}
	
	
	private static String ToString(){
		String rt = "";
		for (int i = 0; i < _tabuleiro[0].length; i++) {
			for (int j = 0; j < _tabuleiro[1].length; j++) {
				if (j==0 && i > 0) rt += "\n";
				rt +=_tabuleiro[i][j]; 
			}
		}
		return rt+"\n";
	}
	private static void print(){System.out.print(ToString());}
	private static void printColisoes(){
		for (Colisoes c : _colisoes){
			System.out.println("Colisao: " + c.toString() + ", linha "+c.p1.x+": " + getLinha(c.p1.x) + ", linha "+c.p2.x+": " + getLinha(c.p2.x));
		}
	}
	private static String getLinha(int linha){
		String rt = "";
		for (int j = 0; j < _tabuleiro[1].length; j++) { //coluna
			rt += _tabuleiro[linha][j];
		}
		return rt;
	}
	
	//randomiza um int entre um valor máximo e mínimo
	private static int randomizar(int min, int max){
		if (max <= 0) max = 1;
		if (min > max) min = max-1;
		if (min <= 0) min = 0;
		return min + new Random().nextInt(max);
	}
	
	
	private static void AnaliseColisoes(){
		for (int i = 0; i < _tabuleiro[0].length; i++) { //linhas
			numeroColisoes(i);
		}
	} 
	
	private static int numeroColisoes(int linha/*cromossomo*/){
		List<Point> rainhas = rainhasLinha(linha);
		for (Point r : rainhas){
			//System.out.println("linha: " + linha + " - Rainha: " + r.toString());
			verificaColisoes(r);
		}

		return _colisoes.size();
	}
	
	//retorna as rainhas de uma linha
	private static List<Point> rainhasLinha(int linha){
		ArrayList<Point> rt = new ArrayList<Point>();
		for (int j = 0; j < _tabuleiro[1].length; j++) {
			if (_tabuleiro[linha][j] == 1) rt.add(new Point(linha, j));
		}
		return rt;
	}
	
	/**
	 * verifica as colisoes de uma rainha
	 * */
	private static void verificaColisoes(Point rainha){
		//verifica coluna
		//parte de onde esta a rainha e sobe
		int x = rainha.x-1;
		int h = _tabuleiro[1].length;
		int l = _tabuleiro[0].length;
		while (x >= 0) {
			//System.out.println("verificando1: ["+x+","+rainha.y+"]");
			if (_tabuleiro[x][rainha.y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+rainha.y+"]");
				adicionarColisao(rainha, x, rainha.y);
				break;
			}
			x--; //sobe
		}
		x = rainha.x+1; //parte de onde esta a rainha e desce
		while (x < h) {
			//System.out.println("verificando2: ["+x+","+rainha.y+"]");
			if (_tabuleiro[x][rainha.y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+rainha.y+"]");
				adicionarColisao(rainha, x, rainha.y);
				break;
			}
			x++; //desce
		}
		
		//verifica linha
		//parte onde esta a rainha e esquerda
		int y = rainha.y-1;
		while (y >= 0){
			//System.out.println("verificando1: ["+rainha.x+","+y+"]");
			if (_tabuleiro[rainha.x][y] == 1) { //colisão
				//System.out.println("colisao ["+rainha.x+","+y+"]");
				adicionarColisao(rainha, rainha.x, y);
				break;
			}
			y--; //esquerda
		}
		//parte onde esta a rainha e direita
		y = rainha.y+1;
		while (y < l){
			//System.out.println("verificando2: ["+rainha.x+","+y+"]");
			if (_tabuleiro[rainha.x][y] == 1) { //colisão
				//System.out.println("colisao ["+rainha.x+","+y+"]");
				adicionarColisao(rainha, rainha.x, y);
				break;
			}
			y++; //direita
		}
		//---------------------------------------------------------
		//verifica diagonal esquerda
		//parte onde esta a rainha e desce + esquerda
		x = rainha.x+1;
		y = rainha.y-1;
		
		//x--; //sobe
		//x++; //desce
		//y--; //esquerda
		//y++; //direita
		while (x < h && y >= 0) { //condições de saída
			//System.out.println("verificando1: ["+x+","+y+"]");
			if (_tabuleiro[x][y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+y+"]");
				adicionarColisao(rainha, x, y);
				break;
			}
			x++; //desce
			y--; //esquerda
		}
		//parte onde esta a rainha e desce + direita
		x = rainha.x+1;
		y = rainha.y+1;
		while (x < h && y < l){
			//System.out.println("verificando2: ["+x+","+y+"]");
			if (_tabuleiro[x][y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+y+"]");
				adicionarColisao(rainha, x, y);
				break;
			}
			x++; //desce
			y++; //direita
		}
		//parte onde esta a rainha e sobe + direita
		x = rainha.x-1;
		y = rainha.y+1;
		while (x >= 0 && y < l){
			//System.out.println("verificando1: ["+x+","+y+"]");
			if (_tabuleiro[x][y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+y+"]");
				adicionarColisao(rainha, x, y);
				break;
			}
			x--; //sobe
			y++; //direita
		}
		//parte onde esta a rainha e sobe + esquerda
		x = rainha.x-1;
		y = rainha.y-1;
		while (x >= 0 && y >= 0){
			//System.out.println("verificando2: ["+x+","+y+"]");
			if (_tabuleiro[x][y] == 1) { //colisão
				//System.out.println("colisao ["+x+","+y+"]");
				adicionarColisao(rainha, x, y);
				break;
			}
			x--; //sobe
			y--; //esquerda
		}
	}
	private static void adicionarColisao(Point p, int x2, int y2){
		adicionarColisao(p.x, p.y, x2, y2);
	}
	private static void adicionarColisao(int x1, int y1, int x2, int y2){
		Colisoes col = new Colisoes(
				new Point(x1, y1), 
				new Point(x2, y2));
		if (!ColisaoAdicionada(col)) _colisoes.add(col); //add a colisão
	}
	private static boolean ColisaoAdicionada(Colisoes col){
		for (Colisoes c : _colisoes){
			if (c.equals(col)){return true;} 
		}
		return false;
	}
}

class Colisoes {
	Point p1, p2;
	
	public Colisoes(){}
	public Colisoes(Point ponto1, Point ponto2){this.p1 = ponto1; this.p2 = ponto2;}
	
	public boolean equals(Point ponto1, Point ponto2){
		return 
			(verificaPonto(this.p1, ponto1) && 
			verificaPonto(this.p2, ponto2)) || 
			(verificaPonto(this.p2, ponto1) && 
			 verificaPonto(this.p1,ponto2));
	}
	public boolean equals(Colisoes c){
		return equals(c.p1, c.p2);
	}
	
	private boolean verificaPonto(Point pontoA, Point pontoB){
		if (
				(pontoA.getX() == pontoB.getX() && pontoA.getY() == pontoB.getY()) 
				||
				(pontoA.getY() == pontoB.getX() && pontoA.getX() == pontoB.getY())
			) return true;
		return (pontoA.equals(pontoB));
	}
	
	public String toString(){
		return "["+p1.x+","+p1.y+"]["+p2.x+","+p2.y+"]";
	} 
}
