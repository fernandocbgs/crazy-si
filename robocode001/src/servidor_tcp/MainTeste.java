package servidor_tcp;

import servidor_tcp.gui.FrameServidorTCP;

public class MainTeste {


	public static void main(String[] args) {
		new FrameServidorTCP().setVisible(true);
		//new TCPServer(null).start();
		
		//try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		
		//new TCPClient().iniciarCliente();
	}

}
