package stationGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class KeypadWithDisplay extends JPanel {
	
	public JLabel display;
	public Keypad keypad;
	
	public KeypadWithDisplay() {
		
		setLayout(new BorderLayout());
		
		display = new JLabel(" ");
		display.setBackground(Color.WHITE);
		display.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 72));
		display.setHorizontalAlignment(SwingConstants.CENTER);
		display.setVerticalTextPosition(SwingConstants.CENTER);
		display.setHorizontalTextPosition(SwingConstants.CENTER);
		add(display, BorderLayout.NORTH);
		
		keypad = new Keypad();
		add(keypad);
	}
}