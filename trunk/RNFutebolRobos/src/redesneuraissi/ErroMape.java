package redesneuraissi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ErroMape {
	
	private double erroMape = 0.0;
	private int numeroExemplos = 0;
	private String caminhoSalvarErroMape =  "erroMape.txt";
	
	public void adicionarErro(double[] valoresEntrada, double erro){
		numeroExemplos++;
		erroMape += erro;
	}
	
	public void salvar(){
		File file = new File(caminhoSalvarErroMape);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			BufferedWriter writerBuffer = new BufferedWriter(writer);
			writerBuffer.write("Erro mape: " + Double.toString(erroMape));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
