import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;

public class TypingConsole implements KeyListener {
	private JTextField typingConsole;
	private ArrayList<Character> userInputtedWord; 
	private WordToType challengeWord;
	private Controller controller;

	public TypingConsole() {
		typingConsole = new JTextField();
		typingConsole.addKeyListener(this);
		typingConsole.setFont(new Font("Serif", 0, 20));

		userInputtedWord = new ArrayList<Character>();

		challengeWord = new WordToType();
		challengeWord.getJTextPane().addKeyListener(this);
	}

	public JTextField getJTextField() {
		return typingConsole;
	}

	public WordToType getWordToType() {
		return challengeWord;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void printuserInputtedWord() {
		for (int i = 0; i < userInputtedWord.size(); i++) {
			System.out.print(userInputtedWord.get(i));
		}
		System.out.print("\n");
	}

	public void checkIfCorrect() {
		String correctWord = challengeWord.getWord();

		int index = userInputtedWord.size() - 1;
		StyledDocument document = challengeWord.getStyledDocument();

		StringBuilder wordToCheck = new StringBuilder();
		for (int i = 0; i < userInputtedWord.size(); i++) {
			wordToCheck.append(userInputtedWord.get(i));
		}

		if (userInputtedWord.get(index) == challengeWord.getCharacter(index)) {
			Style correctStyle = challengeWord.getJTextPane().addStyle("Green",
					null);
			StyleConstants.setForeground(correctStyle, Color.green);
			StyleConstants.setBold(correctStyle, true);
			document.setCharacterAttributes(0, userInputtedWord.size(),
					challengeWord.getJTextPane().getStyle("Green"), true);
		} else if (userInputtedWord.get(index) != challengeWord
				.getCharacter(index)) {
			System.out.println("INCORRECT");
			userInputtedWord.clear(); 
			challengeWord.setNewWord();
		}

		if (userInputtedWord.size() == challengeWord.getLength()) {
			System.out.println("wordToCheck: " + wordToCheck);
			System.out.println("correctWord: " + correctWord);
			if (wordToCheck.toString().trim()
					.equalsIgnoreCase(correctWord.trim())) {
				System.out.println("CORRECT");
				controller.establishConnectionWithMessage(controller
						.getUsername());
				userInputtedWord.clear(); // clears array of chars that user
											// just typed
				challengeWord.setNewWord();
			} else {
				System.out.println("INCORRECT");
				userInputtedWord.clear(); // clears array of chars that user
											// just typed
				challengeWord.setNewWord();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
		}

		else if (userInputtedWord.size() == challengeWord.getLength()) {
			checkIfCorrect();
			userInputtedWord.clear();
			challengeWord.setNewWord();
		}

		else if (userInputtedWord.size() < challengeWord.getLength()) {
			userInputtedWord.add(arg0.getKeyChar());
			checkIfCorrect();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}