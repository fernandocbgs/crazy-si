package servidor_tcp.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import servidor_tcp.TCPClient;
import servidor_tcp.TCPServer;
import servidor_tcp.pacotes.CriadorPacotes;
import servidor_tcp.pacotes.CriadorPacotes.TipoPacotes;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class FrameServidor extends JFrame implements IAcoesTCP{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea JTextArea;
	private JButton btnIniciarServidor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameServidor frame = new FrameServidor();
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
	public FrameServidor() {
		setResizable(false);
		setTitle("TCP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 315);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnIniciarServidor = new JButton("Servidor");
		btnIniciarServidor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciaServidorTCP();
			}
		});
		btnIniciarServidor.setBounds(10, 11, 89, 23);
		contentPane.add(btnIniciarServidor);
		
		JButton btnIniciarCliente = new JButton("Cliente");
		btnIniciarCliente.setVisible(false);
		btnIniciarCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciaClienteTCP();
			}
		});
		btnIniciarCliente.setBounds(109, 11, 89, 23);
		contentPane.add(btnIniciarCliente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 449, 230);
		contentPane.add(scrollPane);
		
		JTextArea = new JTextArea();
		JTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(JTextArea);
		
		//já inicia o servidor
		iniciaServidorTCP();
	}

	private void iniciaServidorTCP(){
		btnIniciarServidor.setEnabled(false);
		new ThreadIniciaServidor(7890, this).start();
	}
	private void iniciaClienteTCP(){
		new ThreadIniciaCliente("localhost", 7890, this).start();
	}
	
	@Override
	public void print(String msg) {
		//System.out.println(msg);
		JTextArea.setText(msg + "\n" + JTextArea.getText());
	}

	@Override
	public void Trata(List<String> l) {
		if (l==null)return;
		TipoPacotes tipo = new CriadorPacotes().getTipoPacote(Integer.valueOf(l.get(0)));
		print("tipo: " + tipo.toString());
		for(int i = 0; i < l.size();i++){
			print("["+i+"] - " + l.get(i));
		}
//		for (String s:l){
//			print(s);
//		}
	}
}

class ThreadIniciaServidor extends Thread {
	private int _porta = 7890;
	private IAcoesTCP _iatcp;
	public ThreadIniciaServidor(int porta, IAcoesTCP iatcp){
		_porta = porta;
		_iatcp = iatcp;
	}
	public void run(){
		new TCPServer(_porta, _iatcp).iniciarServidor();
	}
}
class ThreadIniciaCliente extends Thread {
	private int _porta = 7890;
	private String _ip;
	private IAcoesTCP _iatcp;
	public ThreadIniciaCliente(String ip, int porta, IAcoesTCP iatcp){
		_ip = ip;
		_porta = porta;
		_iatcp = iatcp;
	}
	public void run(){
		List<String> l = new ArrayList<String>();
		l.add("7");
		l.add("1.5");
		new TCPClient(_porta, _ip, _iatcp).iniciarCliente(TipoPacotes.respostaAcao, l);
	}
}