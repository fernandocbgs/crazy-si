import java.util.BitSet;


public class TesteCromossomo {
	
	public static void main(String [] args)
	{
		AlgoritmoGenetico Ag = new AlgoritmoGenetico(30, 0.3f, 0.1f, 10000);
		//
		Cromossomo cromossomo = new Cromossomo();
		Cromossomo cromossomo2 = new Cromossomo();
		cromossomo.printRainhas();
		System.out.println();
		cromossomo2.printRainhas();
		System.out.println();
        Ag.crossOver(cromossomo, cromossomo2);
				
	}
}
