import java.util.BitSet;


public class TesteCromossomo {
	
	public static void main(String [] args)
	{
//		BitSet bitset1 = new BitSet(6);
//		
//		bitset1.set(0);
//		
//		for(int i =0; i < 6; i++)
//			System.out.print(bitset1.get(i) ? "1" : "0");
//		System.out.println();
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(50, 0.1f, 0.5f, 60);
		
		Cromossomo cromossomo = new Cromossomo();
		System.out.print(1);
		cromossomo.print();
		//
		Cromossomo cromossomo2 = new Cromossomo();	
		System.out.print(2);
		cromossomo2.print();
		//
//		cromossomo2.crossOver(cromossomo);
		//
		//faz crossover
		ag.crossOver(cromossomo, cromossomo2);
		
		System.out.print(1);
		cromossomo.print();
		System.out.print(2);
		cromossomo2.print();
		
		
		cromossomo.calcularFitness();
		System.out.println(cromossomo.getFitness());
	}

}
