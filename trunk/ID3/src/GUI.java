import id3.learning.ID3Learning;
import id3.learning.TrainingData;

import id3.node.Node;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import java.io.StringReader;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Interface gráfica (duas caixas de texto e um botão). A maior parte do código não foi feita
 * manualmente, mas sim com a ferramenta Window Builder Pro.
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {
	private JTextArea leftArea;
	private JTextArea rightArea;

	private void eventButton() {
		try {
			TrainingData trainingData = TrainingData.readReader(new StringReader(leftArea.getText()));
			Node rootNode = ID3Learning.generateTree(trainingData.attributes, trainingData.inputs);
			rightArea.setText(rootNode.print());
		} catch (Exception e) {
			rightArea.setText("");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
				/* mensagem: */ e.toString(),
				/* título:   */ "Exceção!",
				/* tipo:     */ JOptionPane.ERROR_MESSAGE
			);
		}
	}

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Algoritmo ID3");

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel button = new JPanel();
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		contentPane.add(button, gbc_button);
		button.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel leftLabel = new JLabel("Exemplos (c\u00F3digo WEKA):");
		button.add(leftLabel);
		
		JLabel rightLabel = new JLabel("Sa\u00EDda (\u00E1rvore):");
		button.add(rightLabel);
		
		JPanel textPanel = new JPanel();
		GridBagConstraints gbc_textPanel = new GridBagConstraints();
		gbc_textPanel.insets = new Insets(0, 0, 5, 0);
		gbc_textPanel.fill = GridBagConstraints.BOTH;
		gbc_textPanel.gridx = 0;
		gbc_textPanel.gridy = 1;
		contentPane.add(textPanel, gbc_textPanel);
		textPanel.setLayout(new GridLayout(1, 0, 5, 0));
		
		JScrollPane leftScroll = new JScrollPane();
		textPanel.add(leftScroll);
		
		leftArea = new JTextArea();
		leftArea.setText("@relation risco-de-credito\r\n\r\n@attribute historico {semInfo, ruim, bom}\r\n@attribute dividas {baixas, altas}\r\n@attribute ganhos-extras {nenhum, adequado }\r\n@attribute ganhos {0a15k, 15a35k, MQ35k}\r\n@attribute risco {baixo, medio, alto}\r\n\r\n@data\r\nsemInfo, altas, nenhum, 15a35k, alto\r\nsemInfo, baixas, nenhum, 15a35k, medio\r\nsemInfo, baixas, nenhum, 0a15k, alto\r\nsemInfo, baixas, nenhum, MQ35k, baixo\r\nsemInfo, baixas, adequado, MQ35k, baixo\r\nruim, baixas, nenhum, 0a15k, alto\r\nruim, baixas, adequado, MQ35k, medio\r\nbom, baixas, nenhum, MQ35k, baixo\r\nbom, altas, adequado, MQ35k, baixo\r\nbom, altas, nenhum, 0a15k, alto\r\nbom, altas, nenhum, 15a35k, medio\r\nbom, altas, nenhum, MQ35k, baixo\r\nruim, altas, nenhum, 15a35k, alto");
		leftScroll.setViewportView(leftArea);
		
		JScrollPane rightScroll = new JScrollPane();
		textPanel.add(rightScroll);
		
		rightArea = new JTextArea();
		rightArea.setEditable(false);
		rightScroll.setViewportView(rightArea);
		
		JButton btnTree = new JButton("Criar \u00E1rvore");
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventButton();
			}
		});
		GridBagConstraints gbc_btnTree = new GridBagConstraints();
		gbc_btnTree.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTree.gridx = 0;
		gbc_btnTree.gridy = 2;
		contentPane.add(btnTree, gbc_btnTree);

		setSize(800, 600);
	}
}
