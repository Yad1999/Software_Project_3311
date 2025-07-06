package uitestprofilemodule;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainFrame {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	
	private JTextField appTitle;
	private JTextField signInTitle;
	private JTextField signUpTitle;
	
	private JPasswordField pField;
	private JButton button;
	
	
	
	public MainFrame() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("NutriSci_3311");
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		panel = new JPanel();
		label = new JLabel("NutriSci");
		appTitle = new JTextField(28);
		pField = new JPasswordField(28);
		button = new JButton("Login");
		
		panel.add(label);
		panel.add(appTitle);
		panel.add(pField);
		panel.add(button);
		
		frame.add(panel);
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
