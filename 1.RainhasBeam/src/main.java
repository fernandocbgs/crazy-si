
public class main {

	public static void main(String[] args) {
		Estado e = new Estado();
		e.print();

		System.out.println(getDecimal("10000001001010100"));
		System.out.println(getBinario(66132));
	}
	
	private static int getDecimal(String binario){
		return Integer.parseInt(binario, 2);
	}
	private static String getBinario(int decimal){
		return Integer.toString(decimal, 2);
	}

}
