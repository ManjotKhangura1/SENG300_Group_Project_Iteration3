package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AttendantLoginPanel extends JPanel {
	private JPasswordField passwordField;
	public AttendantLoginPanel() {
		initComponents();
	}

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("", "[390.00][368.00]", "[215.00][grow][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Username: ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		add(formattedTextField, "cell 1 0,growx");
		
		JLabel lblNewLabel_1 = new JLabel("Password: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		passwordField = new JPasswordField();
		add(passwordField, "cell 1 1,growx");
		
		JCheckBox showPassword = new JCheckBox("Show Password");
		showPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(showPassword, "cell 1 2");
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
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loginButton)
				{
					String userText;
		            String pwdText;
		            userText = formattedTextField.getText();
		            pwdText = passwordField.getText();
		            if ((userText.equalsIgnoreCase("attendant1") && pwdText.equalsIgnoreCase("123"))
		            		|| (userText.equalsIgnoreCase("attendant2") && pwdText.equalsIgnoreCase("456"))
		            		|| (userText.equalsIgnoreCase("attendant3") && pwdText.equalsIgnoreCase("789"))) {
		                JOptionPane.showMessageDialog(null, "Login Successful");
		            } else {
		                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
		            }
				}
			}
			
		});
		loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(loginButton, "cell 1 3");
		setVisible(true);
	}
}
