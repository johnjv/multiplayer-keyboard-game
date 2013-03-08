import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class WordToType { // TODO could def use better variable name
	private JTextPane wordToType;
	private Words wordsList;

	private StyleContext context = new StyleContext();
	private StyledDocument document = new DefaultStyledDocument(context);
	private Style style = context.getStyle(StyleContext.DEFAULT_STYLE);

	public WordToType() {
		wordsList = new Words();
		wordToType = new JTextPane(document);
		
		wordToType.setEditable(false);
		wordToType.setSize(100, 100);
	
		setDefaultStyle();
		
		setNewWord();
	}
	
	private void setDefaultStyle() {
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(style, 30);
		StyleConstants.setForeground(style, Color.black);
	}
	

	public void setNewWord() {
		Random randomGenerator = new Random();
		int randomInteger = randomGenerator.nextInt(8000);
		wordToType.setText(wordsList.getWord(randomInteger));
	}

	public JTextPane getJTextPane() {
		return wordToType;
	}

	public String getWord() {
		return wordToType.getText();
	}
	
	public char getCharacter(int index) {
		return getWord().charAt(index);
	}
	
	public int getLength() {
		return getWord().length();
	}

	public StyledDocument getStyledDocument() {
		return document;
	}

	public Style getStyle() {
		return style;
	}

}