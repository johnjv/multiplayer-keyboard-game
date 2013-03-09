import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class WordToType { // TODO could use better variable name
	private JTextPane wordToType;
	private Words wordsList;

	private StyleContext context;
	private StyledDocument document;
	private Style style;

	public WordToType() {
		wordsList = new Words();
		
		context = new StyleContext();
		document = new DefaultStyledDocument(context);
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		
		wordToType = new JTextPane(document);
		wordToType.setEditable(false);
		
		wordToType.setSize(100, 100); //TODO should this class set size, or should this fall upon GUI class?
		
		setDefaultStyle();
		
		setNewWord();
	}
	
	private void setDefaultStyle() {
		StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(style, 30);
		StyleConstants.setForeground(style, Color.black);
		StyleConstants.setBackground(style, Color.green);
	}
	
	public void setNewWord() {
		wordToType.setText(wordsList.getRandomWord());
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