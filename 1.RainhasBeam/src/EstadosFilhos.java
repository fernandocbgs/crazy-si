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
		Estado estadoTemporario = copiar(this._estado);
		if (!movido.equals(rainha)) { //moveu
			estadoTemporario.AtualizaRainha(rainha, movido);
			ListaEstadosFilhos.add(estadoTemporario); //novo sub estado add
		}
	}
	//como java referencia objetos, é necessário se copiar item à item
	private Estado copiar(Estado original){
		Estado rt = new Estado();
		String estado = "";
		List<String> rainhas = original.getRainhas();
		for (String r:rainhas){
			estado += r;
		}
		rt.setQueens(estado);
		return rt;
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
	
	public String moveRainhaCima(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		if (ix > 0) {//não esta na borda
			ix--; //cima
			String b = Estado.getBinario(ix);
			while(b.length() < 3) {b = "0" + b;}
			rainha = b + y;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
	}
	public String moveRainhaBaixo(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int ix = Estado.getDecimal(x);
		if (ix < 7) {//não esta na borda
			ix++; //baixo
			String b = Estado.getBinario(ix);
			while(b.length() < 3) {b = "0" + b;}
			rainha = b + y;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
	}
	public String moveRainhaEsquerda(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int iy = Estado.getDecimal(y);
		if (iy > 0) {//não esta na borda
			iy--; //esquerda
			String b = Estado.getBinario(iy);
			while(b.length() < 3) {b = "0" + b;}
			rainha = x + b;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
	}
	public String moveRainhaDireita(String rainha){
		String x, y;
		x = rainha.substring(0, 3);
		y = rainha.substring(3, 6);
		
		int iy = Estado.getDecimal(y);
		if (iy < 7) {//não esta na borda
			iy++; //direita
			String b = Estado.getBinario(iy);
			while(b.length() < 3) {b = "0" + b;}
			rainha = x + b;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
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
			String b1 = Estado.getBinario(ix);
			while(b1.length() < 3) {b1 = "0" + b1;}
			String b2 = Estado.getBinario(iy);
			while(b2.length() < 3) {b2 = "0" + b2;}
			rainha = b1 + b2;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
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
			String b1 = Estado.getBinario(ix);
			while(b1.length() < 3) {b1 = "0" + b1;}
			String b2 = Estado.getBinario(iy);
			while(b2.length() < 3) {b2 = "0" + b2;}
			rainha = b1 + b2;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
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
			String b1 = Estado.getBinario(ix);
			while(b1.length() < 3) {b1 = "0" + b1;}
			String b2 = Estado.getBinario(iy);
			while(b2.length() < 3) {b2 = "0" + b2;}
			rainha = b1 + b2;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
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
			String b1 = Estado.getBinario(ix);
			while(b1.length() < 3) {b1 = "0" + b1;}
			String b2 = Estado.getBinario(iy);
			while(b2.length() < 3) {b2 = "0" + b2;}
			rainha = b1 + b2;
		}
		return rainha; //se não permitiu, retorna a mesma pos rainha
	}
}
