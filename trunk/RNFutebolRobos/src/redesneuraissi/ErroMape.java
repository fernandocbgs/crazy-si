package redesneuraissi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public class ErroMape {
	
	private JTextArea _jta;
	private double erroMape = 0.0;
	private double numeroExemplos = 0.0;
	private String caminhoSalvarErroMape =  "erroMape.txt";
	
	public ErroMape(String comentario){init(comentario);}
	
	public ErroMape(String comentario, JTextArea jta)
	{
		_jta = jta;
		init(comentario);
	}
	
	private void setTextJta(String tx){
		String txAntigo = _jta.getText();
		_jta.setText(txAntigo + "\n" + tx);
	}
	
	private void init(String comentario){
		Date todaysDate = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
		String formattedDate = formatter.format(todaysDate);
		//inicia o log
		salvar("-----------");
		salvar(comentario);
		salvar("Início do teste: " + formattedDate);
		salvar("-----------");
		
		setTextJta("-----------");
		//setTextJta(comentario);
		setTextJta("Início do teste: " + formattedDate);
		setTextJta("-----------");
		
	}
	
	public void adicionarErro(double[] valoresEntrada, double encontrado, double esperado){
		double erro = Math.abs(encontrado - esperado)/encontrado;
		numeroExemplos++;
		erroMape += erro;
		salvarErro(valoresEntrada, erro, encontrado, esperado);
	}
	
	public void salvarErro(double [] valoresEntrada, double erro, double encontrado, double esperado){
		String erroSalvar = "";
		for(double valor : valoresEntrada)
			erroSalvar += valor + " ";
		erroSalvar += " Encontrado: " + encontrado;
		erroSalvar += " Esperado: " + esperado;
		erroSalvar += " Erro: " + erro;
		salvar(erroSalvar);
		
		setTextJta(erroSalvar);
	}
	
	public void salvarErroMape(){
		//calcula o erro total para depois salvar
		erroMape /= numeroExemplos;
		salvar(Double.toString(erroMape));
		
		setTextJta(Double.toString(erroMape));
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

	public double getErroMAPE(){return erroMape;}
	
}
