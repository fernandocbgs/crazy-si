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

import DadosRobos.DadosRobos;
import Matematica.CalculoVetores;
import Matematica.XY;
import tcp.TCPClient;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/*
 * OBS: Referenciar o projeto do Jasonum e descomentar as linhas
 * sobre o TCPClient
 * 
 * por algum motivo, o eclipse não permite que um projeto referenciado por ele mesmo
 * seja referenciado nas properties, acho que para evitar redundancia
 */

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
		
		JButton btnAngulo = new JButton("Angulo");
		btnAngulo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAngulo.setBackground(Color.WHITE);
		btnAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TCPClient cli1 = new TCPClient(7891);
				TCPClient cli2 = new TCPClient(7892);
				
				DadosRobos r1 = new DadosRobos(cli1.pedirDados());
				DadosRobos r2 = new DadosRobos(cli2.pedirDados());
				//dados dos robos 1 e robos 2
				
				double angulo1 =
					AnguloTeste(
						new XY(r1.getX(), r1.getY()), 
						new XY(r2.getX(), r2.getY())
					);
				double angulo2 = 					
					AnguloTeste(
						new XY(r2.getX(), r2.getY()),
						new XY(r2.getX(), r1.getY())
					);
				//ajuste do robocode
				//angulo1 -= 90;
				//angulo2 -= 90;
				
				System.out.println("angulo1: " + angulo1);
				System.out.println("angulo2: " + angulo2);
				
				if (r1.getHeading() != angulo1) {
					List<String> ordens1 = new ArrayList<String>();
					ordens1.add("4");
					ordens1.add(""+getAnaliseValor(angulo1, r1.getHeading()));
					cli1.enviarOrdem(ordens1);
				}
				
				if (r2.getHeading() != angulo2) {
					List<String> ordens2 = new ArrayList<String>();
					ordens2.add("4");
					ordens2.add(""+getAnaliseValor(angulo2, r2.getHeading()));
					cli2.enviarOrdem(ordens2);
				}
				
			}
		});
		btnAngulo.setBounds(318, 11, 89, 23);
		contentPane.add(btnAngulo);
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
	
	//---------------------------------------------
	private double getAnaliseValor(double angulo, double heading){
		double vlr;
		vlr = angulo - heading;
		//informa o lado à ser rotacionado
		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
		return vlr;
	}
	
	//#####################################
	//enviar este método para dentro da classe CalculoVetores
    public static double AnguloTeste(XY p1, XY p2){
    	XY v1 = CalculoVetores.getVetor(p1, p2);
    	XY v2 = CalculoVetores.getVetor(
    				new XY(p1.X(),p2.Y())
    				,
    				new XY(p1.X(),p1.Y())
    			); //vetor criado, base de calculo do angulo
    	
    	//System.out.println("v1: " + v1 + ", v2: " + v2);
    	
    	//o = arccos( u * v / |v|*|u| )
    	double o = Math.acos(CalculoVetores.multiplicao(v1, v2) / (CalculoVetores.norma(v1) * CalculoVetores.norma(v2)));
    	o = Math.toDegrees(o); //(float)
    	//System.out.println("o: " + o);
    	return o;
    }
	
}