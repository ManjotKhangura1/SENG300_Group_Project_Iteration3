package stationGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class KeypadWithDisplay extends JPanel {
	
	public JLabel display;
	public Keypad keypad;
	public String data = "";
	
	/**
	 * Constructor for displaying keypad values
	 */
	public KeypadWithDisplay() {
		
		setLayout(new BorderLayout());
		
		//Sets keypad to display numbers being clicked
		display = new JLabel(" ");
		display.setBackground(Color.WHITE);
		display.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 72));
		display.setHorizontalAlignment(SwingConstants.CENTER);
		display.setVerticalTextPosition(SwingConstants.CENTER);
		display.setHorizontalTextPosition(SwingConstants.CENTER);
		add(display, BorderLayout.NORTH);
		
		keypad = new Keypad();
		add(keypad);
		
		//Adds listeners to key[ad to show numbers as they are clicked to display it
		keypad.one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "1");
				data += "1";
			}
		});
		
		keypad.two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "2");
				data += "2";
			}
		});
		
		keypad.three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "3");
				data += "3";
			}
		});
		
		keypad.four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "4");
				data += "4";
			}
		});
		
		keypad.five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "5");
				data += "5";
			}
		});
		
		keypad.six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "6");
				data += "6";
			}
		});
		
		keypad.seven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "7");
				data += "7";
			}
		});
		
		keypad.eight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "8");
				data += "8";
			}
		});
		
		keypad.nine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "9");
				data += "9";
			}
		});
		
		keypad.clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(" ");
				data = "";
			}
		});
		
		keypad.zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.setText(display.getText() + "0");
				data += "0";
			}
		});
	}
}