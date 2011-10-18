package ambiente;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

/**
* A classe exibe caixas de diálogos, muito util para se exibir e capturar informações do usuário.
* @author Emerson Shigueo Sugimoto
* @version 1.1 29/08/2009
*/
public class Mensagens {
	private JFileChooser fileChooser = new JFileChooser();
	public String getOpen(){
		int result = fileChooser.showOpenDialog(null);
		if(result == JFileChooser.CANCEL_OPTION){   
			return "";
		}    
		else{   
			return fileChooser.getSelectedFile().getPath();
		} 
	}
	public String getSave(){
		int result = fileChooser.showDialog(null, "Salvar");
		if(result == JFileChooser.CANCEL_OPTION){   
			return "";
		}    
		else{   
			return fileChooser.getSelectedFile().getPath();
		}
	}
	public static void mensagemAlerta(String mensagem, String titulo){
		javax.swing.JOptionPane.showMessageDialog(null, mensagem, titulo, javax.swing.JOptionPane.WARNING_MESSAGE);
	}
	public static void mensagemInfo(String mensagem, String titulo){
		javax.swing.JOptionPane.showMessageDialog(null,mensagem,titulo,javax.swing.JOptionPane.INFORMATION_MESSAGE);   
	}
	public static void mensagemErro(String mensagem, String titulo){
		javax.swing.JOptionPane.showMessageDialog(null, mensagem,titulo, JOptionPane.ERROR_MESSAGE);
	}
	public static boolean question(String pergunta, String titulo){
		int n = javax.swing.JOptionPane.showConfirmDialog(null, pergunta, titulo, javax.swing.JOptionPane.YES_NO_OPTION);
		if (n == 0){
			return true;
		} 
		return false;
	}
	public static String input(String msg){
		return(String.valueOf(javax.swing.JOptionPane.showInputDialog(msg))); 
	}
	
	public String getSavePoint(boolean questaoConfirmacao){
		String arqSalvar = "";
		boolean continuar = true;
		do {
			mensagemInfo("Escolha um local para salvar e um nome de arquivo", "Salvar");
			arqSalvar = getSave();
			if ((arqSalvar.equals(""))||(arqSalvar.equals(" "))) {
				if (question("Local inválido, deseja cancelar ?", "Local inválido salvar")){
					continuar = false;
				} else {
					continuar = true;
				}
			} else { //OK
				if (questaoConfirmacao) {
					if (question("Deseja salvar em:\n" + arqSalvar, "Salvar")){
						continuar = false; //OK
					} else {
						continuar = true; //Not OK
					}
				} else {
					continuar = false;
				}
			}
		} while (continuar);
		return arqSalvar;
	}
	
	public String getOpenPoint(boolean questaoConfirmacao){
		String arqAbrir = "";
		boolean continuar = true;
		do {
			mensagemInfo("Escolha um arquivo para abrir", "Abrir");
			arqAbrir = getOpen();
			if ((arqAbrir.equals(""))||(arqAbrir.equals(" "))) {
				if (question("Local inválido, deseja cancelar ?", "Local inválido abrir")){
					continuar = false;
				} else {
					continuar = true;
				}
			} else { //OK
				if (questaoConfirmacao) {
					if (question("Deseja abrir o arquivo:\n" + arqAbrir, "Abrir")){
						continuar = false; //OK
					} else {
						continuar = true; //Not OK
					}
				} else {
					continuar = false;
				}
			}
		} while (continuar);
		return arqAbrir;
	}
}
