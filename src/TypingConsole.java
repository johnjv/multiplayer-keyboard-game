import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.text.*;

public class TypingConsole implements KeyListener, Runnable {
	private ArrayList<Character> userInputtedWord; 
	private WordToType challengeWord;
	private Controller controller;

	public TypingConsole() {
		userInputtedWord = new ArrayList<Character>();

		challengeWord = new WordToType();
		challengeWord.getJTextPane().addKeyListener(this);
		challengeWord.getJTextPane().setOpaque(true);
		
		
		
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
	}
	
	private void setRedStyle() { //TODO rename
		StyledDocument document = challengeWord.getStyledDocument();
		Style correctStyle = challengeWord.getJTextPane().addStyle("Red", null);
		StyleConstants.setForeground(correctStyle, Color.red);
		StyleConstants.setBold(correctStyle, true);
		document.setCharacterAttributes(0, challengeWord.getLength(), challengeWord.getJTextPane().getStyle("Red"), true);
	}
	
	private String buildString() {
		StringBuilder wordToBuild = new StringBuilder();
		for (int i = 0; i < userInputtedWord.size(); i++) {
			wordToBuild.append(userInputtedWord.get(i));
		}
		return wordToBuild.toString();
	}
	
	private void resetTypingConsole() {
		userInputtedWord.clear(); 
		challengeWord.setNewWord();
	}
	
	private void playCorrectSound() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("correct.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void playIncorrectSound() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("incorrect.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void checkIfCorrect() throws Throwable { //TODO read up on exceptions for threads
		String correctWord = challengeWord.getWord();
		int index = userInputtedWord.size() - 1;
		final int SLEEP_TIME = 500;
		
		if (userInputtedWord.get(index) == challengeWord.getCharacter(index)) {
			setGreenStyle();			
		} 
		
		else if (userInputtedWord.get(index) != challengeWord.getCharacter(index)) { //start thread in this method
			System.out.println("INCORRECT");
			playIncorrectSound();
			setRedStyle();
			challengeWord.getJTextPane().setBackground(Color.red);
			Thread.sleep(SLEEP_TIME);
			challengeWord.getJTextPane().setBackground(Color.white);
			resetTypingConsole();
		}

		if (userInputtedWord.size() == challengeWord.getLength()) {
			String wordToCheck = buildString();
			
			System.out.println("wordToCheck: " + wordToCheck);
			System.out.println("correctWord: " + correctWord);
			
			if (wordToCheck.toString().trim().equalsIgnoreCase(correctWord.trim())) {
				System.out.println("CORRECT");
				playCorrectSound();
				challengeWord.getJTextPane().setBackground(Color.green);
				Thread.sleep(SLEEP_TIME);
				challengeWord.getJTextPane().setBackground(Color.white);
				controller.establishConnectionWithMessage(controller.getUsername());
				resetTypingConsole();
			} else {
				System.out.println("INCORRECT");
				playIncorrectSound();
				Thread.sleep(SLEEP_TIME);
				resetTypingConsole();
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

//		else if (userInputtedWord.size() == challengeWord.getLength()) {
//			checkIfCorrect();
//			//userInputtedWord.clear();
//			//challengeWord.setNewWord();
//		}

		else if (userInputtedWord.size() < challengeWord.getLength()) {
			//challengeWord.getJTextPane().setBackground(Color.white);
			userInputtedWord.add(arg0.getKeyChar());
			
		}
		//checkIfCorrect();
		Thread thread = new Thread(this);
		thread.start();
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

	@Override
	public void run() {
		System.out.print("thread initiated");
		try {
			checkIfCorrect();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}