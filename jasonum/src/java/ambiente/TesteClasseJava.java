package ambiente;
import java.util.logging.Logger;

public class TesteClasseJava {
	private Logger _logger;

	
	public TesteClasseJava(Logger logger){
		_logger = logger;
	}
	
	public void getXY(int x, int y){
		String tx = "["+x+"," + y + "]";
		System.out.println(tx);
		_logger.info(tx);
		
		//em teoria deve 'falar' com o simulador do robocode
		//e enviar as informações para que o robo se movimente
		//sinistro
		
	}
	
}
