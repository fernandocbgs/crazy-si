import java.util.List;

public class main {

	public static void main(String[] args) {
		/*Estado e = new Estado();
		e.CalculoColisoesEstado();
		e.print();
		
		String rTeste = "";
		List<String> rainhas = e.getRainhas();
		for (String r : rainhas){
			rTeste = r;
			System.out.println(r);
		}
		
		e=null;*/
/*		EstadosFilhos es = new EstadosFilhos();
		es.CriarListaFilhos();
		
		for (Estado e : es.ListaEstadosFilhos) {
			e.print();
		}
		
		//MelhorEscolha
		System.out.println("----Escolha do melhor estado: ---");
		Estado melhorEstado = es.MelhorEscolha();
		melhorEstado.print();
		
		es=null;*/
		
		AnaliseBeam anB = new AnaliseBeam(500);
		Estado melhorEncontrado = anB.Busca();
		melhorEncontrado.print();
		anB = null;
		
	}

}