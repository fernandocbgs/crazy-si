import javax.swing.UIManager;

/**
 * @author Felipe Michels Fontoura
 * @author Thayse Marques Solis
 */
public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}
