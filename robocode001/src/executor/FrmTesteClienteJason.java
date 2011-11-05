package executor;

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
import robocode.DadosRobos.DadosRobos;
import Matematica.CalculoVetores;
import tcp.TCPClient;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/*
 * OBS: Referenciar o projeto do Jasonum e descomentar as linhas
 * sobre o TCPClient
 * 
 * por algum motivo, o eclipse nao permite que um projeto referenciado por ele mesmo
 * seja referenciado nas properties, acho que para evitar redundancia
 */

/**
 * Este e um formulario de controle dos Robos Via TCP<br />
 * Ele simula o Jason, de forma simplificada.
 * 
 * @author Emerson
 * */
public class FrmTesteClienteJason extends JFrame {
	private static final long serialVersionUID = -5997286175039906312L;
	private JPanel contentPane;
	private JComboBox cboPortaServidorTCPRobo;
	private JTextArea JTA;
	private JComboBox cboAcao;
	private TCPClient _tcpcli;
	private JTextField txtVirar;
	private JComboBox cboNumeroPlano;

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
				// recupera os dados do robo
				DadosRobos dados = getTcpClient().pedirDados();
				print("------------Dados do robo da porta: " + getPortaCliente() + "----------------");
				print(dados.toString());
			}
		});
		btnGetDadosRobo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGetDadosRobo.setBackground(Color.WHITE);
		btnGetDadosRobo.setBounds(10, 11, 171, 23);
		contentPane.add(btnGetDadosRobo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 487, 194);
		contentPane.add(scrollPane);

		JTA = new JTextArea();
		scrollPane.setViewportView(JTA);

		JButton btnEnviarAcoes = new JButton("Enviar Acoes");
		btnEnviarAcoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// envia as acoes a serem executadas pelo robo

				/**
				 * 0 - parar 1 - reiniciar robo 2 - atirar 3 - virar esquerda 4
				 * - virar direita 5 - andar
				 **/
				List<String> ordens = new ArrayList<String>();
				ordens.add(getAcao() + "");
				if (getAcao() == 2) {
					ordens.add(r(3) + "");
				} else {
					ordens.add(r(360) + "");
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
		cboPortaServidorTCPRobo.setModel(new DefaultComboBoxModel(new String[] {
				"7891", "7892" }));
		cboPortaServidorTCPRobo.setBounds(191, 12, 117, 20);
		contentPane.add(cboPortaServidorTCPRobo);

		cboAcao = new JComboBox();
		cboAcao.setBackground(Color.WHITE);
		cboAcao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboAcao.setModel(new DefaultComboBoxModel(new String[] { "0 - parar",
				"1 - reiniciar robo", "2 - atirar", "3 - virar esquerda",
				"4 - virar direita", "5 - andar" }));
		cboAcao.setSelectedIndex(5);
		cboAcao.setBounds(191, 34, 117, 20);
		contentPane.add(cboAcao);

		JButton btnAngulo = new JButton("Angulo");
		btnAngulo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAngulo.setBackground(Color.WHITE);
		btnAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TCPClient cli1 = new TCPClient(7891);
				TCPClient cli2 = new TCPClient(7892);
				DadosRobos r1 = cli1.pedirDados();
				DadosRobos r2 = cli2.pedirDados();
				String tx = "r1 ["+ (int)r1.getX()+","+ (int)r1.getY()+"]" + "\n";
				tx += "r2 ["+ (int)r2.getX()+","+ (int)r2.getY()+"]" + "\n";
			
				//tenta rodar os robos:
				List<String> ordem = new ArrayList<String>();
				ordem.add("3");
				//ordem.add("" + (r1.getHeading()- (angulo+180.0) )); //caso 1
				//ordem.add("" + (r1.getHeading()- (angulo+90.0) )); //caso 2
				//ordem.add("" + (r1.getHeading()- (angulo) )); //caso 3
				//ordem.add("" + (r1.getHeading()- (angulo+270.0) )); //caso 4
				ordem.add("" + CalculoVetores.getQuantidadeVirar(r1, r2));
				
				cli1.enviarOrdem(ordem);
				ordem.clear();
				ordem.add("3");
				//ordem.add("" + (r2.getHeading()-(angulo) )); //caso 1
				//ordem.add("" + (r2.getHeading()-(angulo+270.0) )); //caso 2
				//ordem.add("" + (r2.getHeading()-(angulo+180.0) )); //caso 3
				//ordem.add("" + (r2.getHeading()-(angulo+90.0) )); //caso 4
				ordem.add("" + CalculoVetores.getQuantidadeVirar(r2, r1, r2.getHeading()));
				cli2.enviarOrdem(ordem);
				
				//System.out.println(tx);
				JTA.setText(tx);

			}
		});
		btnAngulo.setBounds(318, 11, 89, 23);
		contentPane.add(btnAngulo);

		JButton btnVirar = new JButton("Virar");
		btnVirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				double v = Double.parseDouble(txtVirar.getText());
				
				TCPClient cli1 = new TCPClient(7891);
				TCPClient cli2 = new TCPClient(7892);
				DadosRobos r1 = cli1.pedirDados();
				DadosRobos r2 = cli2.pedirDados();
				List<String> ordem = new ArrayList<String>();
				ordem.add("3");
				ordem.add("" + (r1.getHeading()-v));
				cli1.enviarOrdem(ordem);
				ordem.clear();
				ordem.add("3");
				ordem.add("" + (r2.getHeading()-v));
				cli2.enviarOrdem(ordem);

			}
		});
		btnVirar.setFont(new Font("Dialog", Font.PLAIN, 11));
		btnVirar.setBackground(Color.WHITE);
		btnVirar.setBounds(318, 33, 89, 23);
		contentPane.add(btnVirar);

		txtVirar = new JTextField();
		txtVirar.setText("90");
		txtVirar.setBounds(412, 33, 85, 23);
		contentPane.add(txtVirar);
		txtVirar.setColumns(10);
		
		JButton btnExecutarPlano = new JButton("Executar Plano");
		btnExecutarPlano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TCPClient cli1 = new TCPClient(7891);
				TCPClient cli2 = new TCPClient(7892);
				DadosRobos r1 = cli1.pedirDados();
				DadosRobos r2 = cli2.pedirDados();
				List<String> ordem = new ArrayList<String>();
				
				int p = Integer.valueOf(cboNumeroPlano.getSelectedItem().toString());
				if (p == 1) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600 - r1.getY() - 50)); //envia ele para cima
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800 - r1.getX() - 50)); //canto superior direito
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 2) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600 - r1.getY() - 50)); //envia ele para cima
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (-r1.getX() + 50)); //canto superior esquerdo
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 3) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (- r1.getY() + 50)); //envia ele para baixo
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (-r1.getX() + 50)); //canto inferior esquerdo
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 4) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (- r1.getY() + 50)); //envia ele para baixo
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800 - r1.getX() - 50)); //canto inferior direito
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 5) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600 - r1.getY() - 50)); //envia ele para cima
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800/2 - r1.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 6) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600/2 - r1.getY() - 50)); //envia ele para cima
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (- r1.getX() + 50));
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 7) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (- r1.getY() + 50)); //envia ele para baixo
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800/2 - r1.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 8) {
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600/2 - r1.getY() - 50)); //envia ele para o meio
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800 - r1.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-90.0));
					ordem.add("5");
					ordem.add("" + (800/2 - r2.getX() - 50));
					ordem.add("3");
					ordem.add("" + 90.0); //agora o heading é de 90º
					ordem.add("5");
					ordem.add("" + (600/2 - r2.getY() - 50));
					cli2.enviarOrdem(ordem);
				} else if (p == 9) {
					//teste de resgate
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r1.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (600 - r1.getY() - 50)); //envia ele para cima
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (800 - r1.getX() - 50)); //canto superior direito
					ordem.add("3");
					ordem.add("" + 90);
					cli1.enviarOrdem(ordem);
					
					ordem.clear();
					ordem.add("3");
					ordem.add("" + (r2.getHeading()-0.0));
					ordem.add("5");
					ordem.add("" + (- r2.getY() + 50)); //envia ele para baixo
					ordem.add("4");
					ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
					ordem.add("5");
					ordem.add("" + (-r2.getX() + 50)); //canto inferior esquerdo
					ordem.add("3");
					ordem.add("" + 90);
					cli2.enviarOrdem(ordem);
				} else if (p == 10) {
					ordem10(r1, r2, cli1, cli2);
				}
			}
		});
		btnExecutarPlano.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExecutarPlano.setBackground(Color.WHITE);
		btnExecutarPlano.setBounds(10, 60, 171, 23);
		contentPane.add(btnExecutarPlano);
		
		cboNumeroPlano = new JComboBox();
		cboNumeroPlano.setBackground(Color.WHITE);
		cboNumeroPlano.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cboNumeroPlano.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"}));
		cboNumeroPlano.setSelectedIndex(9);
		cboNumeroPlano.setBounds(191, 60, 117, 20);
		contentPane.add(cboNumeroPlano);
	}
	//------------------------------------------------------
	private int getPortaCliente() {
		return Integer.valueOf(cboPortaServidorTCPRobo.getSelectedItem().toString());
	}

	private int getAcao() {
		return cboAcao.getSelectedIndex();
	}

	private int r(int max) {
		return new java.util.Random().nextInt(max);
	}

	private void print(String msg) {
		JTA.setText(JTA.getText() + "\n" + msg);
	}

	private TCPClient getTcpClient() {
		if (_tcpcli != null) {
			_tcpcli = null;
		}
		_tcpcli = new TCPClient(getPortaCliente());
		return _tcpcli;
	}
	//------------------------------------------------------
	
	private void ordem10(DadosRobos r1, DadosRobos r2, TCPClient cli1, TCPClient cli2){
		List<String> ordem = new ArrayList<String>();
		ordem.add("3");
		ordem.add("" + (r1.getHeading()-0.0));
		ordem.add("5");
		ordem.add("" + (- r1.getY() + 50)); //envia ele para baixo
		ordem.add("4");
		ordem.add("" + 90); //tenho que lembrar que agora o heading é 0º
		ordem.add("5");
		ordem.add("" + (800 - r1.getX() - 100));
		ordem.add("3");
		ordem.add("" + 90);
		cli1.enviarOrdem(ordem);
		
		ordem.clear();
		ordem.add("3");
		ordem.add("" + (r2.getHeading()-90.0));
		ordem.add("5");
		ordem.add("" + (800 - r2.getX() - 100));
		ordem.add("3");
		ordem.add("" + 90.0); //agora o heading é de 90º
		ordem.add("5");
		ordem.add("" + (600/2 - r2.getY() - 50));
		cli2.enviarOrdem(ordem);
	}
}
//http://www.it.uc3m.es/jvillena/irc/practicas/05-06/robocode/Definitivo.pdf

/**
 * 0 - parar
 * 1 - reiniciar robo
 * 2 - atirar
 * 3 - virar esquerda
 * 4 - virar direita
 * 5 - andar
 **/