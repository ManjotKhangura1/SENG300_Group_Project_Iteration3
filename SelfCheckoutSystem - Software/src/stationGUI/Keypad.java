package stationGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Keypad extends JPanel{
	
	public JButton one;
	public JButton two;
	public JButton three;
	public JButton four;
	public JButton five;
	public JButton six;
	public JButton seven;
	public JButton eight;
	public JButton nine;
	public JButton clear;
	public JButton zero;
	public JButton enter;
	
	/**
	 * Constructor for keyad. Makes buttons for the keypad
	 */
	public Keypad() {
		setLayout(new GridLayout(4, 3));
		
		one = new JButton("1"); 
		one.setBackground(Color.WHITE);
		one.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		one.setVerticalTextPosition(SwingConstants.CENTER);
		one.setHorizontalTextPosition(SwingConstants.CENTER);
		one.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(one);
		
		two = new JButton("2"); 
		two.setBackground(Color.WHITE);
		two.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		two.setVerticalTextPosition(SwingConstants.CENTER);
		two.setHorizontalTextPosition(SwingConstants.CENTER);
		two.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(two);
		
		three = new JButton("3"); 
		three.setBackground(Color.WHITE);
		three.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		three.setVerticalTextPosition(SwingConstants.CENTER);
		three.setHorizontalTextPosition(SwingConstants.CENTER);
		three.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(three);
		
		four = new JButton("4"); 
		four.setBackground(Color.WHITE);
		four.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		four.setVerticalTextPosition(SwingConstants.CENTER);
		four.setHorizontalTextPosition(SwingConstants.CENTER);
		four.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(four);
		
		five = new JButton("5"); 
		five.setBackground(Color.WHITE);
		five.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		five.setVerticalTextPosition(SwingConstants.CENTER);
		five.setHorizontalTextPosition(SwingConstants.CENTER);
		five.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(five);
		
		six = new JButton("6"); 
		six.setBackground(Color.WHITE);
		six.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		six.setVerticalTextPosition(SwingConstants.CENTER);
		six.setHorizontalTextPosition(SwingConstants.CENTER);
		six.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(six);
		
		seven = new JButton("7"); 
		seven.setBackground(Color.WHITE);
		seven.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		seven.setVerticalTextPosition(SwingConstants.CENTER);
		seven.setHorizontalTextPosition(SwingConstants.CENTER);
		seven.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(seven);
		
		eight = new JButton("8"); 
		eight.setBackground(Color.WHITE);
		eight.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		eight.setVerticalTextPosition(SwingConstants.CENTER);
		eight.setHorizontalTextPosition(SwingConstants.CENTER);
		eight.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(eight);
		
		nine = new JButton("9"); 
		nine.setBackground(Color.WHITE);
		nine.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		nine.setVerticalTextPosition(SwingConstants.CENTER);
		nine.setHorizontalTextPosition(SwingConstants.CENTER);
		nine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(nine);
		
		clear = new JButton("CLEAR"); 
		clear.setBackground(Color.WHITE);
		clear.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		clear.setVerticalTextPosition(SwingConstants.CENTER);
		clear.setHorizontalTextPosition(SwingConstants.CENTER);
		clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(clear);
		
		zero = new JButton("0"); 
		zero.setBackground(Color.WHITE);
		zero.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		zero.setVerticalTextPosition(SwingConstants.CENTER);
		zero.setHorizontalTextPosition(SwingConstants.CENTER);
		zero.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(zero);
		
		enter = new JButton("ENTER"); 
		enter.setBackground(Color.WHITE);
		enter.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		enter.setVerticalTextPosition(SwingConstants.CENTER);
		enter.setHorizontalTextPosition(SwingConstants.CENTER);
		enter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(enter);
	}
}