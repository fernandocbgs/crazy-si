package servidor_tcp.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import servidor_tcp.TCPServer;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameServidorTCP extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea _jta;
	private JButton btnIniciarTcp;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameServidorTCP frame = new FrameServidorTCP();
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
	public FrameServidorTCP() {
		setResizable(false);
		setTitle("TCP Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollJTA = new JScrollPane();
		scrollJTA.setBounds(10, 36, 414, 203);
		contentPane.add(scrollJTA);
		
		_jta = new JTextArea();
		scrollJTA.setViewportView(_jta);
		
		btnIniciarTcp = new JButton("iniciar TCP");
		btnIniciarTcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarServidor();
			}
		});
		btnIniciarTcp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciarTcp.setBounds(302, 10, 122, 23);
		contentPane.add(btnIniciarTcp);
	}
	
	private void iniciarServidor(){
		btnIniciarTcp.setEnabled(false);
		new TCPServer(_jta).start();
	}
	
}