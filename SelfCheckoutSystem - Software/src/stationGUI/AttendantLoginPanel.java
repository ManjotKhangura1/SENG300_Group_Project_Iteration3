package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttendantLoginPanel extends JPanel {
	
	//Creates field for a password for attendant
	private JPasswordField passwordField = new JPasswordField();
	
	//For username of attendant
	JTextField textField = new JTextField();
	
	private MainFrame mainFrame;
	
	//login button
	public JButton loginButton;
	
	/**
	 * Constructor for this panel
	 * @param mainFrame - Frame which shows all panels
	 */
	public AttendantLoginPanel(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		initComponents();
	}

	/**
	 * Initializes the components of the panel
	 */
	private void initComponents(){
		
		//Sets layout
		setBounds(mainFrame.frame.getBounds());
		setLayout(new MigLayout("", "[390.00][368.00]", "[215.00][grow][][grow]"));
		setVisible(false);
		
		//Makes username label
		JLabel username = new JLabel("Username: ");
		username.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(username, "cell 0 0,alignx trailing");
		
		add(textField, "cell 1 0,growx");
		
		//Makes password label
		JLabel password = new JLabel("Password: ");
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(password, "cell 0 1,alignx trailing");
		
		add(passwordField, "cell 1 1,growx");
		
		//Makes show password label
		JCheckBox showPassword = new JCheckBox("Show Password");
		showPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(showPassword, "cell 1 2");
		
		//If show password is clicked, it will show password or hide it again
		showPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == showPassword)
				{
					if (showPassword.isSelected()) {
		                passwordField.setEchoChar((char) 0);
		            } else {
		                passwordField.setEchoChar('*');
		            }
		 
				}
				
			}
			
		});
		
		//Login button
		loginButton = new JButton("Login");
		
		//Sets actions for when attendant clicks login button to log in
		loginButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loginButton)
				{
					//Preset usernames and passwords for attendant login
					String userText;
		            String pwdText;
		            userText = textField.getText();
		            pwdText = passwordField.getText();
		            if ((userText.equalsIgnoreCase("attendant1") && pwdText.equalsIgnoreCase("123"))
		            		|| (userText.equalsIgnoreCase("attendant2") && pwdText.equalsIgnoreCase("456"))
		            		|| (userText.equalsIgnoreCase("attendant3") && pwdText.equalsIgnoreCase("789"))) {
		                JOptionPane.showMessageDialog(null, "Login Successful");
		                
		                mainFrame.attendantLoginPanel.setVisible(false);
		                mainFrame.attendantPanel.setVisible(true);
		                
		            } else {
		                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
		                mainFrame.attendantLoginPanel.setVisible(false);
		                mainFrame.scanningPanel.setVisible(true);
		            }
				}
			}
			
		});
		
		loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(loginButton, "cell 1 3");
	}
}
