package redesneuraissi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErroMape {
	
	private double erroMape = 0.0;
	private double numeroExemplos = 0.0;
	private String caminhoSalvarErroMape =  "erroMape.txt";
	
	public ErroMape()
	{
		Date todaysDate = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
		String formattedDate = formatter.format(todaysDate);
		//inicia o log
		salvar("Início do teste: " + formattedDate);
	}
	
	public void adicionarErro(double[] valoresEntrada, double encontrado, double esperado){
		double erro = (encontrado - esperado)/encontrado;
		numeroExemplos++;
		erroMape += erro;
		salvarErro(valoresEntrada, erro);
	}
	
	public void salvarErro(double [] valoresEntrada, double erro){
		String erroSalvar = "";
		for(double valor : valoresEntrada)
			erroSalvar += valor + " ";
		erroSalvar += " Erro: " + erro;
		salvar(erroSalvar);
	}
	
	public void salvarErroMape(){
		//calcula o erro total para depois salvar
		erroMape += 1/numeroExemplos*erroMape;
		salvar(Double.toString(erroMape));
	}
	
	public void salvar(String mensagem){
		File file = new File(caminhoSalvarErroMape);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			BufferedWriter writerBuffer = new BufferedWriter(writer);
			writerBuffer.write(mensagem + "\n");
			writerBuffer.flush();
			writerBuffer.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
