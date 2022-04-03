package websearchengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
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
	private static JLabel label, label1, display, display1, display2;
	private static JTextField userText, yes;
	private static JButton btn, btn1;
	WebSearchEngine wb = new WebSearchEngine();
	InvertedIndex invertedIndex = new InvertedIndex();
	int countMore = 0;

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

		// Search Button
		btn = new JButton("Search");
		btn.setBounds(300, 100, 100, 25);
		btn.addActionListener(new GUI());
		panel.add(btn);

		// message after button clicked
		display = new JLabel();
		display.setBounds(10, 110, 500, 200);
		panel.add(display);

		display1 = new JLabel();
		display1.setBounds(10, 350, 500, 25);
		panel.add(display1);

		frame.setVisible(true);
		yes = new JTextField(2);
		yes.setBounds(200, 350, 50, 25);
		panel.add(yes);

		btn1 = new JButton("moreResults");
		btn1.setBounds(260, 350, 20, 25);
		btn1.addActionListener(new GUI());
		panel.add(btn1);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (((AbstractButton) e.getSource()).getText().equals("Search")) {
			String searchWord = userText.getText();

			ArrayList<LinkIndex> outputLinks = wb.search(invertedIndex, searchWord);

			String op = "<html>";
			for (LinkIndex link : outputLinks) {
				op = op + "<br><br>" + link.url + "-----" + link.frequency;
				System.out.println("maal:" + link.url);
				// display1.setText(link.url);
			}
			op += "</html>";
			System.out.println(op);

			display.setText(op);
			display1.setText("Do you want more results? (y/n)");
		}
		if (((AbstractButton) e.getSource()).getText().equals("moreResults")) {
			// while (true) {

			// System.out.println("Do you want more results? (y/n)");

			String more = yes.getText();
			String searchWord = userText.getText();

			if (more.equals("y")) {
				countMore += 5;
				System.out.println(searchWord);
				String op = "<html>";
				ArrayList<LinkIndex> outputLinks = wb.searchInCache(invertedIndex, searchWord, true, countMore);
				if (outputLinks == null) {
					display.setText("No more results");
				} else {
					for (LinkIndex link : outputLinks) {
						op = op + "<br><br>" + link.url + "-----" + link.frequency;
						System.out.println("maal:" + link.url);
						// display1.setText(link.url);
					}
					op += "</html>";
					System.out.println(op);

					display.setText(op);
				}

				// }
			}

		}
	}

}
