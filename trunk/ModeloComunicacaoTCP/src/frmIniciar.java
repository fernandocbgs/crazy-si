import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import servidor_tcp.TCPClient;
import servidor_tcp.TCPServer;
import servidor_tcp.interfaces.IClienteTCP;
import servidor_tcp.interfaces.IServidorTCP;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class frmIniciar extends JFrame implements IServidorTCP, IClienteTCP {
	private static final long serialVersionUID = 8646011828941723155L;
	private JPanel contentPane;
	private JTextArea JTA;
	private JButton btnIniciarTCP;
	
	private TCPClient _clienteTCP;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmIniciar frame = new frmIniciar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmIniciar() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 375);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 724, 230);
		contentPane.add(scrollPane);
		
		JTA = new JTextArea();
		scrollPane.setViewportView(JTA);
		
		final frmIniciar isto = this;

		btnIniciarTCP = new JButton("Iniciar Servidor TCP");
		btnIniciarTCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnIniciarTCP.setEnabled(false);
				new IniciarServidorTCP(isto).start();
			}
		});
		btnIniciarTCP.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciarTCP.setBounds(10, 11, 204, 23);
		contentPane.add(btnIniciarTCP);
		
		JButton btnClienteDados = new JButton("Cliente Dados");
		btnClienteDados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClienteDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Jason pedindo dados ao Servidor TCP do Robo
				List<String> retornoServidor = getClienteTCP().pedirDados();
				isto.print("#Retorno do Servidor TCP Robo");
				for(String dados : retornoServidor) {
					isto.print(dados);
				}
				//new IniciarClienteTCP(isto, 1).start();
			}
		});
		btnClienteDados.setBounds(10, 42, 204, 23);
		contentPane.add(btnClienteDados);
		
		JButton btnClienteEnviarOrdem = new JButton("Cliente Enviar Ordem");
		btnClienteEnviarOrdem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClienteEnviarOrdem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Jason enviando uma ordem ao Servidor TCP do robo no robocode
				List<String> ordens = new ArrayList<String>();
				ordens.add("Faça isto " + r(800));
				ordens.add("Vire a Esquerda " + r(360));
				ordens.add("Atires, intensidade " + r(4));
				getClienteTCP().enviarOrdem(ordens);
			}
		});
		btnClienteEnviarOrdem.setBounds(10, 72, 204, 23);
		contentPane.add(btnClienteEnviarOrdem);
	}

	@Override
	public void print(String msg) {
		JTA.setText(JTA.getText() + "\n" + msg);		
	}

	@Override
	public List<String> getDadosRobo() {
		List<String> meuDados = new ArrayList<String>();
		meuDados.add(r(7) + "");
		meuDados.add(r(100) + ""); //energia
		meuDados.add(r(700) + "");
		meuDados.add(r(452) + "");
		return meuDados;
	}

	@Override
	public void ExecutarAcoes(List<String> l) {
		for (String a : l) {
			print("#ação: " + a);
		}
	}

	private int r(int vlr){return new java.util.Random().nextInt(vlr);}
	
	private TCPClient getClienteTCP(){
		if (_clienteTCP == null)
			_clienteTCP = new TCPClient(this);
		return _clienteTCP;
	}
	
}

class IniciarServidorTCP extends Thread {
	private IServidorTCP _istcp;
	public IniciarServidorTCP(IServidorTCP istcp){_istcp=istcp;}
	public void run(){ iniciar(); }
	private void iniciar(){
		new TCPServer(_istcp).iniciarServidor();
	}
}
//class IniciarClienteTCP extends Thread {
//	private IClienteTCP _ictcp;
//	private int _tipo;
//	public IniciarClienteTCP(IClienteTCP ictcp, int tipo){_ictcp = ictcp; _tipo = tipo;}
//	public void run(){ 
//		switch (_tipo){
//		case 1: pedirDadosRobo(); break;
//		case 2: enviarOrdemsaoRobo(); break;
//		}
//	}
//	private void pedirDadosRobo(){
//		List<String> retornoServidor = new TCPClient(_ictcp).pedirDados();
//		_ictcp.print("#Retorno do Servidor TCP Robo");
//		for(String dados : retornoServidor) {
//			_ictcp.print(dados);
//		}
//	}
//	private void enviarOrdemsaoRobo(){
//		List<String> ordens = new ArrayList<String>();
//		ordens.add("Faça isto " + r(800));
//		ordens.add("Vire a Esquerda " + r(360));
//		ordens.add("Atires, intensidade " + r(4));
//		new TCPClient(_ictcp).enviarOrdem(ordens);
//	}
//	private int r(int vlr){return new java.util.Random().nextInt(vlr);}
//}