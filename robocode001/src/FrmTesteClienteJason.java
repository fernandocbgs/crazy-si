import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import tcp.TCPClient;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Este é um formulario de controle dos Robos Via TCP<br />
 * Ele simula o Jason, de forma simplificada.
 * @author Emerson
 * */
public class FrmTesteClienteJason extends JFrame {
	private static final long serialVersionUID = -5997286175039906312L;
	private JPanel contentPane;
	private JComboBox cboPortaServidorTCPRobo;
	private JTextArea JTA;
	private JComboBox cboAcao;
	private TCPClient _tcpcli;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmTesteClienteJason frame = new FrmTesteClienteJason();
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
	public FrmTesteClienteJason() {
		setTitle("Envia Dados ao Servidor TCP do Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 325);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGetDadosRobo = new JButton("Dados Robot");
		btnGetDadosRobo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//recupera os dados do robo
				List<String> dados = getTcpClient().pedirDados();
				print("------------Dados do robo da porta: " + getPortaCliente() + "----------------");
				for (String d : dados) {
					print(d);
				}
			}
		});
		btnGetDadosRobo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGetDadosRobo.setBackground(Color.WHITE);
		btnGetDadosRobo.setBounds(10, 11, 171, 23);
		contentPane.add(btnGetDadosRobo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 487, 225);
		contentPane.add(scrollPane);
		
		JTA = new JTextArea();
		scrollPane.setViewportView(JTA);
		
		JButton btnEnviarAcoes = new JButton("Enviar Acoes");
		btnEnviarAcoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//envia ações à serem executadas pelo robo
				
				/**
				 * 0 - parar
				 * 1 - reiniciar robo
				 * 2 - atirar
				 * 3 - virar esquerda
				 * 4 - virar direita
				 * 5 - andar
				 **/
				List<String> ordens = new ArrayList<String>();
				ordens.add(getAcao()+"");
				if (getAcao() == 2) {
					ordens.add(r(3)+"");
				} else {
					ordens.add(r(360)+"");
				}
				getTcpClient().enviarOrdem(ordens);
				
			}
		});
		btnEnviarAcoes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEnviarAcoes.setBackground(Color.WHITE);
		btnEnviarAcoes.setBounds(10, 34, 171, 23);
		contentPane.add(btnEnviarAcoes);
		
		cboPortaServidorTCPRobo = new JComboBox();
		cboPortaServidorTCPRobo.setBackground(Color.WHITE);
		cboPortaServidorTCPRobo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboPortaServidorTCPRobo.setModel(new DefaultComboBoxModel(new String[] {"7891", "7892"}));
		cboPortaServidorTCPRobo.setBounds(191, 12, 117, 20);
		contentPane.add(cboPortaServidorTCPRobo);
		
		cboAcao = new JComboBox();
		cboAcao.setBackground(Color.WHITE);
		cboAcao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboAcao.setModel(new DefaultComboBoxModel(new String[] {"0 - parar", "1 - reiniciar robo", "2 - atirar", "3 - virar esquerda", "4 - virar direita", "5 - andar"}));
		cboAcao.setSelectedIndex(0);
		cboAcao.setBounds(191, 34, 117, 20);
		contentPane.add(cboAcao);
	}
	private int getPortaCliente(){
		return Integer.valueOf(cboPortaServidorTCPRobo.getSelectedItem().toString());
	}
	private int getAcao(){
		return cboAcao.getSelectedIndex();
	}
	private int r(int max){return new java.util.Random().nextInt(max);}
	
	private void print(String msg) {
		JTA.setText(JTA.getText() + "\n" + msg);
	}
	
	private TCPClient getTcpClient(){
		if (_tcpcli!=null) {_tcpcli=null;}
		_tcpcli = new TCPClient(getPortaCliente());
		return _tcpcli;
	}
	
}