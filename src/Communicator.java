import java.io.*;
import java.net.Socket;

public class Communicator extends Thread {

	private Socket socket;
	private String message;
	private Controller controller;

	Communicator(Socket socket, Controller controller) {
		super("Communicator");
		this.socket = socket;
		this.controller = controller;
	}

	Communicator(Socket socket, String message) {
		super("Communicator");
		this.socket = socket;
		this.message = message;
	}

	public void run() {
		try {
			communicate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicate() throws IOException {
		// write to the output stream
		// read from the input stream
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		String inputLine, outputLine;

		outputLine = message;
		out.println(outputLine);

		inputLine = in.readLine();
		if (inputLine.equals("null")) {
			inputLine = null;
		}
//		while (inputLine != null) {
//			if (!updateNetwork(inputLine)) {
//				updatePlayer(inputLine);
//			}
//			out.println(inputLine);
//			inputLine = in.readLine();
//		}

		out.close();
		in.close();
		socket.close();
		if (inputLine != null) {		
			if (inputLine.startsWith("TR")) {
				inputLine = inputLine.replace("TR", " ").trim();
				handleMessage(inputLine);
				System.out.println("'" + inputLine + "'");
			}
		}
	}
	
	private void handleMessage(String message) {
		if (message.startsWith("connect")) {
			message = message.replace("connect", " ").trim();
			updateNetwork(message);
		}
		else if (message.startsWith("update")) {
			message = message.replace("update", " ").trim();
			updatePlayer(message);
		}
	}

	private boolean updateNetwork(String string) {
		String[] words = string.split(" ");

		if (words.length == 2) {
			return controller.updateNetwork(words[0], words[1]);
		} else if (words.length == 1) {
			return controller.updateNetwork(words[0], getSocketName());
		} else {
			System.out.println("This should not be happening!");
			System.out.println("words.length: " + words.length);
			System.out.println("words: " + words);
		}
		return false;
	}

	private void updatePlayer(String player) {
		controller.movePlayer(player);
	}

	public String getSocketName() {
		return socket.getInetAddress().getHostName();
	}
}
