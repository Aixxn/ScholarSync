package test;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class login extends JFrame{
	JFrame frame = new JFrame();
	JLabel label = new JLabel("Login");
	
	 login() {
	 label.setBounds(0,0,100,50);
     frame.setTitle("ScholarSync Login");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(1920, 1080);
     frame.setLocationRelativeTo(null);
     frame.setLayout(null);

     // Background Panel
     JPanel background = new JPanel();	
     Color customColor = new Color(0xF8FAFD); 
     background.setBackground(customColor);
     background.setBounds(0, 0, 1920, 1080);
     background.setLayout(null);
     frame.add(background);
     frame.add(label);
     
     frame.setVisible(true);
}
}
