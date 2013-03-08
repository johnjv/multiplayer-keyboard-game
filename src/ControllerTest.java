import static org.junit.Assert.*;
import org.junit.Test;

public class ControllerTest {

	@Test
	public void test() {
		// Set username
		Controller controller = new Controller();
		controller.setUsername("Generic username");
		assertTrue(controller.getUsername() == "Generic username");

		// updating network
		try {
			assertTrue(controller.network.size() == 0);
			controller.updateNetwork("merry", "localhost");
			System.out.println(controller.network.size());
			assertTrue(controller.network.size() == 1);
		} catch (NullPointerException e) {

		}
	}

}
