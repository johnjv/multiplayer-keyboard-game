import java.io.*;
import java.util.*;

public class Words {
	private Scanner scanner;
	private ArrayList<String> wordList;
	private static final String FILE_PATH = "fiveletterwords.txt";

	public Words() {
		wordList = new ArrayList<String>();

		try {
			scanner = new Scanner(new File(FILE_PATH));
		} catch (FileNotFoundException e) {
			System.out.println("file not found. Check if file path is correct");
		}

		scanner.useDelimiter("\\s");
		while (scanner.hasNext()) {
			wordList.add(scanner.next());
		}
	}

	public int getSize() {
		return wordList.size();
	}

	public String getWord(int index) {
		return wordList.get(index);
	}

	public static void main(String[] args) {
		Words wordTest = new Words();
		for (int i = 0; i < wordTest.getSize(); i++) {
			System.out.println(wordTest.getWord(i));
		}
	}
}