import java.util.List;


public class AnaliseBeam {
	public int MaxProfundidade;
	public int kEstados;
	//private List<Estado> _beam = new ArrayList<Estado>();
	
	
	public AnaliseBeam(int maxProfundidade, int kEstados){
		MaxProfundidade = maxProfundidade;
		this.kEstados = kEstados;
	}
	
	public Estado Busca(){
		Estado rt = null;
		Estado e = new Estado(); //inicia o estado
		e.CalculoColisoesEstado(); //aleatorio
		
		int maxBusca = MaxProfundidade;
		
		EstadosFilhos es;
		while (maxBusca > 0 
				|| (rt!=null && rt.ColisoesEstado == 0 /*ideal*/)
			) {
			if (rt == null) {
				es = new EstadosFilhos(e);
			} else {
				es = new EstadosFilhos(rt);
			}
			es.CriarListaFilhos();
			List<Estado> ePes = es.MelhorEscolha(kEstados);
			for (Estado eK : ePes) {
				rt = buscaEstadosBeam(eK);
				try {Thread.sleep(10);} catch (InterruptedException e1) {}
			}
			
			if (rt!=null && rt.ColisoesEstado == 0) {return rt;}
			
			maxBusca--;
		}
		
		return rt;
	}
	
	private Estado buscaEstadosBeam(Estado e){
		Estado rt = null;
		EstadosFilhos es;
		
		int maxBusca = MaxProfundidade;
		
		while (maxBusca > 0 
				|| (rt!=null && rt.ColisoesEstado == 0 /*ideal*/)
			) {
			
			if (rt == null) {
				es = new EstadosFilhos(e);
			} else {
				es = new EstadosFilhos(rt);
			}
			
			es.CriarListaFilhos();
			Estado ePes = es.MelhorEscolha();
			
			if (rt == null) {
				rt = ePes;
			} else {
				if (ePes.ColisoesEstado < rt.ColisoesEstado) {
					rt = ePes;
				}
			}
			
			if (rt!=null && rt.ColisoesEstado == 0) {return rt;}
			
			try {Thread.sleep(10);} catch (InterruptedException e1) {}
			
			maxBusca--;
		}
		return rt;
	}
	
}
