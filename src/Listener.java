import java.io.IOException;
import java.net.ServerSocket;

public class Listener extends Thread {

	private ServerSocket serverSocket;
	private boolean listening = true;
	private Controller controller;

	Listener(Controller controller) {
		try {
			serverSocket = new ServerSocket(8888);
			this.controller = controller;
		} catch (IOException e) {
			System.out.println("Can't listen on port 8888");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run() {
		while (listening) {
			accept();
		}

		close();
	}

	private void accept() {
		try {
			Communicator communicator = new Communicator(serverSocket.accept(),
					controller);
			communicator.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
