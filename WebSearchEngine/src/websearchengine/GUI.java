package websearchengine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.net.URI;
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
	private static JLabel[] links;
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

		frame.setSize(1400, 800);// sets size of GUI
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		label = new JLabel("ACC Web Search Engine", SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 45));
		label.setBounds(430, 20, 500, 55);
		panel.add(label);

		// Search Label
		label1 = new JLabel("search:");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		label1.setBounds(450, 120, 100, 25);
		panel.add(label1);

		// Text Field
		userText = new JTextField(20);
		userText.setBounds(550, 120, 365, 25);
		panel.add(userText);

		// Search Button
		btn = new JButton("Search");
		btn.setBounds(650, 190, 100, 25);
		btn.addActionListener(new GUI());
		panel.add(btn);

		// message after button clicked
		display = new JLabel();
		display.setBounds(500, 210, 800, 200);
//		panel.add(display);

		links = new JLabel[5];
		int y = 250;
		for (int i = 0; i < links.length; i++) {
			links[i] = new JLabel();
			links[i].setBounds(500, y, 800, 25);
			links[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			y += 50;
			panel.add(links[i]);

		}
		display1 = new JLabel();
		display1.setBounds(500, 550, 800, 25);
		panel.add(display1);

		frame.setVisible(true);
		btn1 = new JButton("moreResults");
		btn1.setBounds(650, 650, 160, 25);
		btn1.addActionListener(new GUI());
		btn1.setText("Click for More Results");
		btn1.setEnabled(false);
		btn1.setVisible(false);
		panel.add(btn1);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (((AbstractButton) e.getSource()).getText().equals("Search")) {
			countMore = 0;
			String searchWord = userText.getText();
			ArrayList<LinkIndex> outputLinks = wb.search(invertedIndex, searchWord);
			String op = "<html>";
			if (outputLinks != null) {
				int i = 0;
				for (LinkIndex link : outputLinks) {
//					String tempOp = "<a href=\"" + link.url + "\">" + link.url + "</a>-----" + link.frequency;
					String tempOp = "<html><a href=" + link.url + ">" + link.url + "</a>-----" + link.frequency;
					op = op + tempOp;
					System.out.println("" + link.url);
					links[i].setText(tempOp);
					links[i].setVisible(true);
					links[i].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								Desktop.getDesktop().browse(new URI(link.url));
							} catch (Exception ex) {
								System.out.println(ex);
							}
						}
					});
					i++;
					// display1.setText(link.url);
				}
				if (i < 5) {
					for (int j = i; j <= 4; j++) {
						links[j].setVisible(false);
						links[j].setText("");
					}
				}
				op += "</html>";
				System.out.println(op);
				display.setText(op);
				display1.setText("Do you want more results?");
				btn1.setEnabled(true);
				btn1.setVisible(true);
			} else {
				System.out.println(invertedIndex.getDictionary().size());
				ArrayList<String> suggestions = wb.checkSpelling(invertedIndex.getDictionary(), searchWord);
				if (suggestions.size() > 0) {
					display1.setText("No results found for the word entered. some suggested words are: " + suggestions);
				} else {
					display1.setText("No results found for the word entered.");
				}
				for (int i = 0; i < 5; i++) {
					links[i].setVisible(false);
				}
				btn1.setVisible(false);
			}
		}

		if (((AbstractButton) e.getSource()).getText().equals("Click for More Results")) {
			String searchWord = userText.getText();
			countMore += 5;
			System.out.println(searchWord);
			String op = "<html>";
			ArrayList<LinkIndex> outputLinks = wb.searchInCache(invertedIndex, searchWord, true, countMore);
			if (outputLinks == null) {
				display1.setText("No more results");
				display1.setVisible(true);
//				btn1.setEnabled(true);
				btn1.setVisible(false);
			} else {
				int i = 0;
				for (LinkIndex link : outputLinks) {
					op = op + "<br><br><a href=\"" + link.url + "\">" + link.url + "</a>-----" + link.frequency;
					String tempOp = "<html><a href=" + link.url + ">" + link.url + "</a>-----" + link.frequency;

					System.out.println("" + link.url);
					links[i].setText(tempOp);
					links[i].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								Desktop.getDesktop().browse(new URI(link.url));
							} catch (Exception ex) {
								System.out.println(ex);
							}
						}
					});
					i++;
				}
				if (i < 5) {
					for (int j = i; j <= 4; j++) {
						System.out.println(j);
						links[j].setText("");
					}
				}
				op += "</html>";
				System.out.println(op);
				display.setText(op);
			}

		}
	}

}
