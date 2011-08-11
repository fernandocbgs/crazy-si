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
		
		numeroColisoes(3);
	}
	
	private static void inicializaTabuleiro(){
		for (int i = 0; i < _tabuleiro[0].length; i++) {
			for (int j = 0; j < _tabuleiro[1].length; j++) {
				_tabuleiro[i][j] = 0;
			}
		}
		int rainhas = _numeroRainhas;
		int posX, posY;
		while(rainhas > 0){
			//sorteia a posição inicial
			posX = randomizar(0, _tabuleiro[0].length);
			posY = randomizar(0, _tabuleiro[1].length);
			if (_tabuleiro[posX][posY] == 0){ //posição desocupada
				_tabuleiro[posX][posY] = 1; //rainha
				rainhas--;
			}
		}
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
	
	//randomiza um int entre um valor máximo e mínimo
	private static int randomizar(int min, int max){
		if (max <= 0) max = 1;
		if (min > max) min = max-1;
		if (min <= 0) min = 0;
		return min + new Random().nextInt(max);
	}
	
	
	private static int numeroColisoes(int linha/*cromossomo*/){
		List<Point> rainhas = rainhasLinha(linha);
		for (Point r : rainhas){
			System.out.println("linha: " + linha + " - " + r.toString());
			verificaColisoes(r);
		}
		return 0;
	}
	
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
		for (int x = 0; x < _tabuleiro[0].length; x++){ //up to down
			if (x==rainha.x) continue; //mesma posição da rainha
			if (_tabuleiro[x][rainha.y] == 1) { //colisão
				adicionarColisao(rainha, x, rainha.y);
			}
		}
		//verifica linha
		for (int y = 0; y < _tabuleiro[1].length; y++){ //up to down
			if (y==rainha.y) continue; //mesma posição da rainha
			if (_tabuleiro[rainha.x][y] == 1) { //colisão
				adicionarColisao(rainha, rainha.x, y);
			}
		}
		//verifica diagonal esquerda
		int x = rainha.x, y = rainha.y;
		
		
	}
	private static void adicionarColisao(Point p, int x2, int y2){
		adicionarColisao(p.x, p.y, x2, y2);
	}
	private static void adicionarColisao(int x1, int y1, int x2, int y2){
		Colisoes col = new Colisoes(
				new Point(x1, y1), 
				new Point(x2, y2));
		if (ColisaoNaoAdicionada(col)) _colisoes.add(col); //add a colisão
	}
	
	private static boolean ColisaoNaoAdicionada(Colisoes col){
		for (Colisoes c : _colisoes){
			if (c.equals(col)){return true;} 
		}
		return true;
	}
	
}

class Colisoes {
	Point p1, p2;
	
	public Colisoes(){}
	public Colisoes(Point ponto1, Point ponto2){this.p1 = ponto1; this.p2 = ponto2;}
	
	public boolean equals(Point ponto1, Point ponto2){
		return ((verificaPonto(this.p1, ponto1) && verificaPonto(this.p2, ponto2)) || (verificaPonto(this.p2, ponto1) && verificaPonto(this.p1,ponto2)));
	}
	public boolean equals(Colisoes c){
		return equals(c.p1, c.p2);
	}
	
	private boolean verificaPonto(Point pontoA, Point pontoB){
		if (
				(pontoA.getX() == pontoB.getX() || pontoA.getY() == pontoB.getY()) &&
				(pontoA.getY() == pontoB.getX() || pontoA.getX() == pontoB.getY())
			) return true;
		return (pontoA.equals(pontoB));
	}
}
