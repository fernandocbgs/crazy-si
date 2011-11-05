package tcp.enviarmsg;

import interfaces.IMetodosJason;
import java.util.List;
import robocode.DadosRobos.DadosRobos;
import tcp.TCPClient;

public class EnviarMsgTCP extends Thread {
	public enum TipoEnvio {pedirDados, enviarOrdens, atualizarModoAvisarJason}
	private TipoEnvio _tipo;
	private int _porta; private String _ip;
	private List<String> _ordens = null;
	private IMetodosJason _imj;
	private int _indice;

	public void setOrdens(List<String> ordens) { this._ordens = ordens; }
	public List<String> getOrdens() { return _ordens; }
	
	public EnviarMsgTCP(TipoEnvio tp, int porta, String ip, IMetodosJason imj, int indice){
		_tipo = tp;
		_porta = porta;
		_ip = ip;
		_imj = imj;
		_indice = indice;
	}
	
	public void run(){
		switch(_tipo){
			case enviarOrdens: EnviarOrdens(); break;
			case pedirDados: pedirDados(); break;
		}
	}
	
	private void pedirDados(){
		DadosRobos _dados = null;
		TCPClient cli = new TCPClient(_porta, _ip);
		_dados = cli.pedirDados();
		cli = null;
		if (_dados!=null && _imj!=null) _imj.atualizarDadosRobos(_dados, _indice);
	}
	
	private void EnviarOrdens(){
		TCPClient cli = new TCPClient(_porta, _ip);
		cli.enviarOrdem(getOrdens());
		cli = null;
	}
	
}
