import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Controller {

	private String username;
	private String computerName;
	private Listener listener;
	ConcurrentHashMap<String, String> network = new ConcurrentHashMap<String, String>();
	private GUI gui;

	Controller() {
		this.username = "";
		initComputerName();
		this.listener = new Listener(this);
		listener.start();
	}

	public void setGUI(GUI gui) {this.gui = gui;}

	public String getUsername() {return username;}

	public void setUsername(String name) {this.username = name;}

	public String getComputerName() {return this.computerName;}

	public ConcurrentHashMap<String, String> getNetwork() {return this.network;}

	public void clearNetwork() {network.clear();}

	public void establishConnection(String connectTo) {
		try {
			String message = "TR connect " + username;
			new Communicator(new Socket(connectTo, 8888), message).start();
		} 
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}

	public void establishConnectionWithMessage(String message) {
		try {
			for (String address : network.values()) {
				new Communicator(new Socket(address, 8888), message).start();
			}
		} 
		catch (UnknownHostException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}

	public boolean updateNetwork(String name, String address) {

		if (!network.containsValue(address)) {
			establishConnectionWithMessage(name);
			network.put(name, address);
			sendKnownAddresses(address);
			gui.addNewPlayer(name);

			return true;
		}
		return false;
	}

	private void sendKnownAddresses(String address) {
		for (String key : network.keySet()) {
			try {
				String networkInfo = key + " " + network.get(key);
				new Communicator(new Socket(address, 8888), networkInfo)
						.start();
			} 
			catch (UnknownHostException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void sendCorrectMessage(String username) {
		String message = "TR update " + username;
		establishConnectionWithMessage(message);
	}

	public void initComputerName() {
		try {
			String name = InetAddress.getLocalHost().getHostName();
			this.computerName = InetAddress.getByName(name)
					.getCanonicalHostName();
		} 
		catch (UnknownHostException e) {e.printStackTrace();}
	}

	public void movePlayer(String player) {
		gui.movePlayer(player);
	}
}