package websearchengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI implements ActionListener {

	private static JPanel panel;
	private static JFrame frame;
	private static JLabel label, label1, display;
	private static JTextField userText;
	private static JButton btn;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		panel = new JPanel();
		frame = new JFrame();

		panel.setLayout(null);
		panel.setBackground(Color.white);// sets bg color
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));// sets border

		frame.setSize(700, 600);// sets size of GUI
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		label = new JLabel("Welcome To Web Search Engine", SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		label.setBounds(100, 20, 500, 25);
		panel.add(label);

		// Search Label
		label1 = new JLabel("search:");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label1.setBounds(220, 70, 80, 25);
		panel.add(label1);

		// Text Field
		userText = new JTextField(20);
		userText.setBounds(270, 70, 165, 25);
		panel.add(userText);
		frame.setVisible(true);

		// Search Button
		btn = new JButton("Search");
		btn.setBounds(300, 100, 100, 25);
		btn.addActionListener(new GUI());
		panel.add(btn);

		// message after button clicked
		display = new JLabel();
		display.setBounds(10, 120, 500, 25);
		panel.add(display);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String[] testDate = { "Deepanshu", "Shah", "kshitij", "halankar" };
		String userEnteredString = userText.getText();
		for (int i = 0; i < testDate.length; i++) {
			if (userEnteredString == testDate[i]) {
				display.setText("String Matched");
			} else {
				System.out.println("Did you mean: " + testDate[i]);
			}
		}

	}

}
