import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() {
		Player player = new Player("Test Player", 50);

		assertEquals(player.getX(), 50);

		player.move(20);

		assertEquals(player.getX(), 70);
	}

}
