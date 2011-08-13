import java.util.ArrayList;
import java.util.List;

/**
 * Cria uma lista de Estados Filhos de um estado especifico
 * um estado filho é um estado no qual uma rainha se movimentou
 * cada rainha pode se movimentar em 8 posições diferentes (exceto quando estiver na borda) 
 * */
public class EstadosFilhos {
	private Estado _estado;
	public List<Estado> ListaEstadosFilhos = new ArrayList<Estado>();
	
	public EstadosFilhos(){
		this._estado = new Estado();
		this._estado.CalculoColisoesEstado();
	}
	public EstadosFilhos(Estado e /*estado especifico*/){
		this._estado = e;
		this._estado.CalculoColisoesEstado();
	}
	
	public void CriarListaFilhos(){
		List<String> rainhas = this._estado.getRainhas();
		for (String r:rainhas){ //para cada rainha, movimenta e cria um novo subestado
			//8 movimentos
			AddListaEstado(moveRainhaCima(r), r);
			AddListaEstado(moveRainhaBaixo(r), r);
			AddListaEstado(moveRainhaEsquerda(r), r);
			AddListaEstado(moveRainhaDireita(r), r);
			AddListaEstado(moveRainhaEsqCima(r), r);
			AddListaEstado(moveRainhaEsqBaixo(r), r);
			AddListaEstado(moveRainhaDirCima(r), r);
			AddListaEstado(moveRainhaDirBaixo(r), r);
		}
	}
	
	private void AddListaEstado(String movido, String rainha){
		Estado estadoTemporario = this._estado.copiar();
		if (movido.equals("")) {return;}
		if (!movido.equals(rainha)) { //moveu
			estadoTemporario.AtualizaRainha(rainha, movido);
			if (!EstadoAdicionado(estadoTemporario))
				ListaEstadosFilhos.add(estadoTemporario); //novo sub estado add
		}
	}
	
	private boolean EstadoAdicionado(Estado eAdd){
		for (Estado e : ListaEstadosFilhos) {
			if (e.equals(eAdd)) return true;
		}
		return false;
	}
	
	public Estado MelhorEscolha(){
		Estado rt = null;
		for (Estado e : this.ListaEstadosFilhos) {
			if (e.ColisoesEstado == 0) e.CalculoColisoesEstado();
			if (rt == null) { rt = e;} else{
				if (rt.ColisoesEstado > e.ColisoesEstado) {
					rt = e;
				}
			}
		}
		return rt;
	} 
	
	/*
	 * Movimentação de Rainhas
	 * */
	
	//verifica se não tem uma rainha na posição à ser movida
	private boolean podeMover(String rainhaNovaPosicao){
		for (Estado e : ListaEstadosFilhos) {
			for (String r : e.getRainhas()) {
				if (RainhaIgual(r, rainhaNovaPosicao)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean RainhaIgual(String rainha, String rainhaComparar){
		String x1, y1, x2, y2;
		x1 = rainha.substring(0, 3);
		y1 = rainha.substring(3, 6);
		x2 = rainhaComparar.substring(0, 3);
		y2 = rainhaComparar.substring(3, 6);

		boolean Comparacao = (x1.equals(x2) && y1.equals(y2)) || 
		   					 (x1.equals(y2) && y1.equals(x2));
/*		if (Comparacao) {
		System.out.println("rainhaComparar: " + rainhaComparar + ", rainha: " + rainha);
		System.out.println("x1: " + x1 + ", x2: " + x2);
		System.out.println("y1: " + y1 + ", y2: " + y2);
		}*/
		return Comparacao;
	}
	
	public String moveRainhaCima(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		if (ix > 0) {//não esta na borda
			ix--; //cima
			
			String newRainha = Estado.getBinario(ix, 3) + y;
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaBaixo(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		if (ix < 7) {//não esta na borda
			ix++; //baixo
			
			String newRainha = Estado.getBinario(ix, 3) + y;
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaEsquerda(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int iy = Estado.getDecimal(y);
		if (iy > 0) {//não esta na borda
			iy--; //esquerda
			
			String newRainha = x + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaDireita(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int iy = Estado.getDecimal(y);
		if (iy < 7) {//não esta na borda
			iy++; //direita
			
			String newRainha = x + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaEsqCima(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		int iy = Estado.getDecimal(y);
		if (ix > 0 && iy > 0) {//não esta na borda
			ix--; //cima
			iy--; //esquerda
			
			String newRainha = Estado.getBinario(ix, 3) + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaEsqBaixo(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		int iy = Estado.getDecimal(y);
		if (ix < 7 && iy > 0) {//não esta na borda
			ix++; //baixo
			iy--; //esquerda
			
			String newRainha = Estado.getBinario(ix, 3) + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaDirCima(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		int iy = Estado.getDecimal(y);
		if (ix > 0 && iy < 7) {//não esta na borda
			ix--; //cima
			iy++; //direita
			
			String newRainha = Estado.getBinario(ix, 3) + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
	public String moveRainhaDirBaixo(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		int iy = Estado.getDecimal(y);
		if (ix < 7 && iy < 7) {//não esta na borda
			ix++; //baixo
			iy++; //direita
			
			String newRainha = Estado.getBinario(ix, 3) + Estado.getBinario(iy, 3);
			if (!podeMover(newRainha)) {
				return "";
			} else {
				return newRainha;
			}
		}
		return "";
	}
}
