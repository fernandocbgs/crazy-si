package funcoes;

import java.io.File;
import javax.swing.JComboBox;

/**
 * Esta classe possui funcoes genericas, comuns a diversas classes
 * @author Emerson Shigueo Sugimoto
 * @version 1.0
 * */
public class FuncoesGerais {

	public static int recuperaCodigoCombo(JComboBox cbo) {
		return Integer.parseInt(cbo.getSelectedItem().toString().substring(0, cbo.getSelectedItem().toString().indexOf(" ")));
	}

	/**
	 * Retorna apenas numeros de uma String
	 * */
	public static String retornaNumero(String valor) {
		return retornaNumero(valor, false);
	}

	/**
	 * Retorna apenas numeros de uma String
	 * */
	public static String retornaNumero(String valor, boolean valorDecimal) {
		String rt = "";
		int cont_virgula = 0;
		char caractere;
		for (int i = 0; i < valor.length(); i++) {
			caractere = valor.charAt(i);
			if (!valorDecimal) {
				if (Character.isDigit(caractere)) {
					rt += String.valueOf(caractere);
				}
			} else {
				// tem a questao da virgula
				if (Character.isDigit(caractere)) {
					rt += String.valueOf(caractere);
				} else {
					if ((cont_virgula == 0) && (caractere == ',')) {
						rt += String.valueOf(caractere);
						cont_virgula++;
					}
				}
			}

		}
		return rt;
	}

	/**
	 * Se virgula = true, retorna um no do tipo #,## <br />
	 * Caso contrario #.##
	 * */
	public static String getValor(String v) {
		return getValor(v, true);
	}
	public static String getValor(String v, boolean virgula) {
		String posVirgula = "";

		if (v.contains(".")) {
			v = (v.replace('.', ','));
		}

		if (v.contains(",")) {
			posVirgula = v.substring(v.indexOf(","), v.indexOf(",")
					+ (v.length() - v.indexOf(",")));
			v = v.substring(0, v.indexOf(","));
			while (posVirgula.length() < 3) {
				posVirgula += "0";
			}
		} else {
			posVirgula = ",00";
		}
		v += posVirgula;

		// vai salvar com ponto, no lugar da virgula
		if (!virgula) {
			v = v.replace(',', '.');
		}

		return v;
	}
	
	/**
	 * Retorna 0 se nao houverem arquivos no diretorio selecionado
	 * */
	public static int numeroArquivosDiretorio(String dir) {
		try {
			return new File(dir).listFiles().length;	
		} catch (Exception ex) {
			return 0;
		}
	}
	public static String[] ListarArquivos(String dir){
		return ListarArquivos(dir, false);
	}
	public static String[] ListarArquivos(String dir, boolean addData){
 	    File diretorio = new File(dir);
		File fList[] = diretorio.listFiles();
		String[] arquivos = null;
		if (fList != null) {
			arquivos = new String[fList.length];
			for ( int i = 0; i < fList.length; i++ ){
				arquivos[i] = fList[i].getName() + (addData ?  " - " + new java.util.Date(fList[i].lastModified()) : "");
			}
		}
		return arquivos;
	}
	
	public static String getUserDir(){
		return System.getProperty("user.dir");
	}
	
	public static void VerificaECriaDiretorio(String dir){
		File diretorio = new File(dir);  
		if (!diretorio.exists()) {  
			diretorio.mkdirs();
		}
	}
}