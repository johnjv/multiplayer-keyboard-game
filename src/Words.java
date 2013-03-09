import java.io.*;
import java.util.*;

public class Words {
	private Scanner scanner;
	private ArrayList<String> wordsList;
	private static final String FILE_PATH = "fiveletterwords.txt";

	public Words() {
		wordsList = new ArrayList<String>();

		try {
			scanner = new Scanner(new File(FILE_PATH));
		} catch (FileNotFoundException e) {
			System.out.println("file not found. Check if file path is correct");
		}

		scanner.useDelimiter("\\s");
		while (scanner.hasNext()) {
			wordsList.add(scanner.next());
		}
	}

	public int getSize() {
		return wordsList.size();
	}

	public String getWord(int index) {
		return wordsList.get(index);
	}
	
	public String getRandomWord() {
		Random randomGenerator = new Random();
		int randomInteger = randomGenerator.nextInt(wordsList.size());
		return getWord(randomInteger);
	}

}