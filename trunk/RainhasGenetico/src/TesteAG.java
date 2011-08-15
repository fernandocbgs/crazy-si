import java.text.DecimalFormat;
import java.text.NumberFormat;


public class TesteAG {
	
	private static Runtime runtime;
	private static NumberFormat formatter;
	private static long usedMemory;
	
	public static void main(String[] args) {
		runtime = Runtime.getRuntime();
		formatter = new DecimalFormat("###,###,###,###");
		usedMemory = 0;
		System.gc();
		
		
		long timeInMillis = System.currentTimeMillis();
		
		showMemoryInfo();
		calculateUsedMemoryBefore();
		
		AlgoritmoGenetico Ag = new AlgoritmoGenetico(30, 0.3f, 0.1f, 10000);
		Ag.executar();	
		
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
