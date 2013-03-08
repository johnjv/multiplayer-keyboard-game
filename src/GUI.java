import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel playerPanel;
	private JPanel finishPanel;
	private JPanel wordPanel;
	private TypingConsole typingConsole;
	private Controller controller;
	private HashMap<String, Player> players;

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
		this.setSize(900, 600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void playerToPanel() {
		playerPanel = new JPanel() { 
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("Repaint");
				playerPanel.setBackground(Color.cyan);

				for (String key : players.keySet()) {
					players.get(key).draw(g);
				}
			}
		};
		playerPanel.setBackground(Color.cyan);
		this.add(playerPanel, BorderLayout.CENTER);
	}

	private void finishPanel() {
		finishPanel = new JPanel(new BorderLayout());
		JLabel finish = new JLabel("FINISH");
		finishPanel.add(finish, BorderLayout.EAST);
		this.add(finishPanel, BorderLayout.EAST);
	}

	private void wordPanel() {
		wordPanel = new JPanel(new GridLayout(1, 1));
		wordPanel.add(typingConsole.getWordToType().getJTextPane(),
				BorderLayout.EAST);
		this.add(wordPanel, BorderLayout.SOUTH);
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

		if (players.containsKey(player)) {
			players.get(player).move(20);
			checkForWin();
		}
		playerPanel.repaint();
	}

	private void checkForWin() {
		for (String key : players.keySet()) {
			if (players.get(key).getX() > 860) {
				finishGame(key);
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