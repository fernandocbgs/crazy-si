package visual;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

import redesneuraissi.ErroMape;
import redesneuraissi.TestaRedeNeural;
import redesneuraissi.TreinaRedeNeural;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualRedeNeural extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNumeroCamadas;
	private JTextField txtNumeroNeuronios;
	private JTextField txtPorcentagem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualRedeNeural frame = new VisualRedeNeural();
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
	public VisualRedeNeural() {
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("Rede Neural");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 282);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNmeroDeNeurnios = new JLabel("N\u00FAmero de Neur\u00F4nios");
		lblNmeroDeNeurnios.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNmeroDeNeurnios.setBounds(10, 36, 114, 14);
		contentPane.add(lblNmeroDeNeurnios);
		
		JLabel lblNmeroDeCamadas = new JLabel("N\u00FAmero de Camadas:");
		lblNmeroDeCamadas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNmeroDeCamadas.setBounds(10, 11, 114, 14);
		contentPane.add(lblNmeroDeCamadas);
		
		JLabel lblValoresTreinamento = new JLabel("% valores treinamento:");
		lblValoresTreinamento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValoresTreinamento.setBounds(10, 63, 121, 14);
		contentPane.add(lblValoresTreinamento);
		
		JLabel lblErroMapeCalculado = new JLabel("Erro MAPE calculado:");
		lblErroMapeCalculado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErroMapeCalculado.setBounds(10, 88, 114, 14);
		contentPane.add(lblErroMapeCalculado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 120, 449, 124);
		contentPane.add(scrollPane);
		
		JTextArea txtErroMAPE = new JTextArea();
		scrollPane.setViewportView(txtErroMAPE);
		
		txtNumeroCamadas = new JTextField();
		txtNumeroCamadas.setText("1");
		txtNumeroCamadas.setBounds(134, 8, 140, 20);
		contentPane.add(txtNumeroCamadas);
		txtNumeroCamadas.setColumns(10);
		
		txtNumeroNeuronios = new JTextField();
		txtNumeroNeuronios.setText("12");
		txtNumeroNeuronios.setBounds(134, 33, 140, 20);
		contentPane.add(txtNumeroNeuronios);
		txtNumeroNeuronios.setColumns(10);
		
		txtPorcentagem = new JTextField();
		txtPorcentagem.setText("0.1");
		txtPorcentagem.setBounds(134, 60, 140, 20);
		contentPane.add(txtPorcentagem);
		txtPorcentagem.setColumns(10);
		
		final ErroMape mape = new ErroMape("", txtErroMAPE);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreinaRedeNeural treinador = new TreinaRedeNeural(
							Integer.valueOf(txtNumeroCamadas.getText()),
							Integer.valueOf(txtNumeroNeuronios.getText()),
							Double.parseDouble(txtPorcentagem.getText())
						);
				
				
				TestaRedeNeural teste = new TestaRedeNeural(treinador.getListaDeValores(), mape);
				// nunca faça testes de treinamento depois, já que os vetores
				// sao ordenados!
				teste.testarRedeNeural();
				//salva o erro
				mape.salvarErroMape();
				
				
			}
		});
		btnIniciar.setBounds(289, 7, 170, 73);
		contentPane.add(btnIniciar);
	}
}
