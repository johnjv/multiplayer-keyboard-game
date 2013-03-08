import static org.junit.Assert.*;
import org.junit.Test;

public class WordsTest {

	@Test
	public void getSize() {
		Words tempList = new Words();

		assertTrue(tempList.getSize() != 0);

		for (int index = 0; index <= 8917; index++) {
			assertTrue(tempList.getWord(index) != null);
		}
	}

}
