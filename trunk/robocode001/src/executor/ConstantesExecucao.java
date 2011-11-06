package executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConstantesExecucao {
	
	public String caminhoProjetoJason;
	public String caminhoProjetoRobocode;
	public String caminhoRobocode;
	
	public ConstantesExecucao(){
		File arquivo = new File("caminhos.properties");
		FileInputStream input;
		
		try {
			input = new FileInputStream(arquivo);
			Properties props = new Properties();
			props.load(input);
			
			caminhoProjetoJason = props.getProperty("projetoJason");
			caminhoProjetoRobocode = props.getProperty("projetoRobocode");
			caminhoRobocode = props.getProperty("robocode");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
