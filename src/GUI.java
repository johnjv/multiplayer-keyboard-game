import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel playerPanel;
	private JPanel finishPanel;
	private JPanel wordPanel;
	private TypingConsole typingConsole;
	private Controller controller;
	private HashMap<String, Player> players;
	private boolean gameIsRunning = true;

	private int initPlayerY = 50;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		GUI test = new GUI();
	}

	public GUI() {
		typingConsole = new TypingConsole();
		controller = new Controller();
		controller.setGUI(this);
		typingConsole.setController(this.controller);
		players = new HashMap<String, Player>();
		set();
		playerToPanel();
		finishPanel();
		wordPanel();
		requirements();
	}

	private void set() {
		this.setSize(1200, 800);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void playerToPanel() {

		setBackground("background.jpg");
		System.out.println("In playerToPanel");
		this.add(playerPanel, BorderLayout.CENTER);
	}

	public void setBackground(String filename) {
		final Image image = new ImageIcon(filename).getImage();
		System.out.println("In setBackground");

		playerPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("Creating Background");
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
				for (String key : players.keySet()) {
					players.get(key).draw(g);
				}
			}
		};

		this.add(playerPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public void setPlayers() {

	}

	private void finishPanel() {
		setFinishline("finishline.jpg");
	}

	public void setFinishline(String filename) {
		final Image image = new ImageIcon(filename).getImage();

		System.out.println("In setFinishline");
		finishPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("Creating finish line");

				g.drawImage(image, 0, 0, 100, getHeight(), null);
			}
		};
		this.add(finishPanel, BorderLayout.EAST);

	}

	private void wordPanel() {
		wordPanel = new JPanel(new GridLayout(1, 1));
		wordPanel.add(typingConsole.getWordToType().getJTextPane(),
				BorderLayout.EAST);
		this.add(wordPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private void requirements() {
		final JDialog startInput = new JDialog(this);
		startInput.setSize(250, 100);
		startInput.setLayout(new GridLayout(3, 1));
		startInput.setLocation(350, 300);
		startInput.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		Container startInputPane = startInput.getContentPane();

		final JTextField playerName = new JTextField("Username");
		playerName.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				JTextField pntf = (JTextField) e.getSource();
				pntf.setText(null);
			}

			public void focusLost(FocusEvent arg0) { // Intentionally left blank
			}
		});

		final JTextField computer = new JTextField("Connect to...");
		computer.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				JTextField ctf = (JTextField) e.getSource();
				ctf.setText(null);
			}

			public void focusLost(FocusEvent arg0) { // Intentionally left blank
			}
		});

		JButton connect = new JButton("Connect and play!");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = playerName.getText();
				String connectTo = computer.getText();

				System.out.println("Name: " + username + " is connecting to: "
						+ connectTo);

				if (GUI.this.checkUsername(username)) {

					controller.setUsername(username);
					controller.establishConnection(connectTo);
				}
				startInput.setVisible(false);
			}
		});

		startInputPane.add(playerName);
		startInputPane.add(computer);
		startInputPane.add(connect);
		startInput.setVisible(true);
	}

	@SuppressWarnings("static-access")
	private boolean checkUsername(String username) {
		JOptionPane error = new JOptionPane();
		try {
			if (username.trim().isEmpty()) {
				error.showMessageDialog(null, "Cannot leave name empty",
						"Error", JOptionPane.ERROR_MESSAGE);
				requirements();
				return false;
			}
		} catch (java.lang.NullPointerException e) {
			System.out.println(e.getMessage());
			error.showMessageDialog(null, "Cannot have name as \"null\" ",
					"Error", JOptionPane.ERROR_MESSAGE);
			requirements();
		}
		return true;
	}

	public void addNewPlayer(String username) {
		players.put(username, new Player(username, initPlayerY));
		initPlayerY += 100;
		playerPanel.repaint();
	}

	public void movePlayer(String player) {
		if (gameIsRunning) {
			if (players.containsKey(player)) {
				players.get(player).move(40);
				checkForWin();
			}
			playerPanel.repaint();
		}
	}

	private void checkForWin() {
		for (String key : players.keySet()) {
			if (players.get(key).getX() > 1200) {
				finishGame(key);
				gameIsRunning = false;
			}
		}
	}

	private void finishGame(String player) {

		JLabel endMessage = new JLabel();
		endMessage.setFont(new Font("Serif", Font.BOLD, 46));
		endMessage.setText("");

		if (player.equals(controller.getUsername())) {
			endMessage.setText("You Win!");
		} else {
			endMessage.setText("You Lose!");
		}

		playerPanel.add(endMessage, BorderLayout.CENTER);
		playerPanel.setVisible(true);
	}
}