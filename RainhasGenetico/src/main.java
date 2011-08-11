import java.util.Random;


public class main {

	private static int _tabuleiro[][] = new int[8][8]; //8x8
	private static int _numeroRainhas = 8;
	
	public static void main(String[] args) {
		inicializaTabuleiro();
		print();
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
	
}
