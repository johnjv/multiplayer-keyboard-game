import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.text.*;

public class TypingConsole implements KeyListener {
	private ArrayList<Character> userInputtedWord; 
	private WordToType challengeWord;
	private Controller controller;

	public TypingConsole() {
		userInputtedWord = new ArrayList<Character>();

		challengeWord = new WordToType();
		challengeWord.getJTextPane().addKeyListener(this);
	}

	public WordToType getWordToType() {
		return challengeWord;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void printUserInputtedWord() {
		for (int i = 0; i < userInputtedWord.size(); i++) {
			System.out.print(userInputtedWord.get(i));
		}
		System.out.print("\n");
	}

	private void setGreenStyle() { //TODO rename
		StyledDocument document = challengeWord.getStyledDocument();
		Style correctStyle = challengeWord.getJTextPane().addStyle("Green", null);

		StyleConstants.setForeground(correctStyle, Color.green);
		StyleConstants.setBold(correctStyle, true);
		document.setCharacterAttributes(0, userInputtedWord.size(), challengeWord.getJTextPane().getStyle("Green"), true);
		
		/*
		StyleConstants.setBackground(correctStyle, green);
		try {
			document.insertString(document.getLength(), "Some Text", correctStyle);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}
	public void checkIfCorrect() {
		String correctWord = challengeWord.getWord();
		int index = userInputtedWord.size() - 1;
		
		StringBuilder wordToCheck = new StringBuilder(); //TODO could build this into method
		for (int i = 0; i < userInputtedWord.size(); i++) {
			wordToCheck.append(userInputtedWord.get(i));
		}

		if (userInputtedWord.get(index) == challengeWord.getCharacter(index)) {
			setGreenStyle();
			
			
		} else if (userInputtedWord.get(index) != challengeWord.getCharacter(index)) {
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
				controller.establishConnectionWithMessage(controller.getUsername());
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
	
	public static void main(String[] args0) {
		JFrame gui = new JFrame();
		TypingConsole typingConsole = new TypingConsole();
		WordToType challengeWord = typingConsole.getWordToType();
		gui.getContentPane().setLayout(new BorderLayout());
		//gui.getContentPane().add(typingConsole.getJTextField(), BorderLayout.SOUTH);
		gui.getContentPane().add(challengeWord.getJTextPane(), BorderLayout.CENTER);
		gui.setSize(500,100);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
}