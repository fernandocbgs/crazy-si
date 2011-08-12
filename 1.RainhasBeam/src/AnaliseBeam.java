import java.util.ArrayList;
import java.util.List;


public class AnaliseBeam {
	public int MaxProfundidade;
	private List<Estado> _beam = new ArrayList<Estado>();
	
	public AnaliseBeam(int maxProfundidade){
		MaxProfundidade = maxProfundidade;
	}
	
	public Estado Busca(){
		Estado rt = null;
		Estado e = new Estado(); //inicia o estado
		e.CalculoColisoesEstado();
		
		EstadosFilhos es;
		while (MaxProfundidade > 0 || (rt!=null && rt.ColisoesEstado == 0 /*ideal*/)) {
			if (rt == null) {
				es = new EstadosFilhos(e);
			} else {
				es = new EstadosFilhos(rt);
			}
			
			es.CriarListaFilhos();
			rt = es.MelhorEscolha();

			_beam.add(rt); //lista beam
		}

		
		return rt;
	}
	
}
