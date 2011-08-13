import java.text.DecimalFormat;
import java.text.NumberFormat;

public class main {

	private static Runtime runtime;
	private static NumberFormat formatter;
	private static long usedMemory;
	
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
		
	
		runtime = Runtime.getRuntime();
		formatter = new DecimalFormat("###,###,###,###");
		usedMemory = 0;
		System.gc();
		
		
		long timeInMillis = System.currentTimeMillis();
		
		showMemoryInfo();
		calculateUsedMemoryBefore();
		
		AnaliseBeam anB = new AnaliseBeam(50,2);
		Estado melhorEncontrado = anB.Busca();
		if (melhorEncontrado!=null)
			melhorEncontrado.print();
		anB = null;
		System.out.println("--end--");
	
		calculateUsedMemoryAfter();
		showMemoryInfo();
		
		System.out.println("Tempo usado: " + (System.currentTimeMillis() - timeInMillis) + " ms");
		
	}

	private static void showMemoryInfo() {
		System.out.println("Amount of storage space available to the program  (in bytes): "
				+ formatter.format(runtime.totalMemory()));
		System.out.println("Amount of free storage space                      (in bytes): "
				+ formatter.format(runtime.freeMemory()));
		System.out.println("Total amount of storage space used by the program (in bytes): "
				+ formatter.format(runtime.totalMemory() - runtime.freeMemory()));
		System.out.println("Memory used 			                          (in bytes): "
				+ formatter.format((usedMemory)));
	}

	private static void calculateUsedMemoryAfter() {
		usedMemory = (runtime.totalMemory() - runtime.freeMemory()) - usedMemory;
	}

	private static void calculateUsedMemoryBefore() {
		usedMemory = runtime.totalMemory() - runtime.freeMemory();
	}
	
}
