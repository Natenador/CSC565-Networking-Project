package client_side;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import base.HouseHubConnection;
import messages.RequestMessage;
import test.Device;
import views.CommandList;
import views.HouseHubView;

public class ControllerConnection extends HouseHubConnection{
	
	
	private String hostName;
	//Socket controllerSocket;
	DataOutputStream toServer;
	DataInputStream fromServer;
	Queue<String> messagesFromServer = new LinkedBlockingQueue<String>();
	
	public ControllerConnection() {
		connected = false;
	}

	public int connectTo(Device device) {
		setHostName(device.getIpAddress());
		setSocketNumber(device.getPortNumber());
		setId(CURRENT_ID++);
		connected = true;
		try {
			String messageToServer = "";
			while(!messageToServer.equals("quit") && connected) {  //Send and receive message until transaction is complete
				Socket client = new Socket(hostName, socketNumber);  //create connection with server
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());  
				/*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter a message:");
				messageToServer = reader.readLine();
				out.writeUTF(messageToServer);*/  
				RequestMessage message = new RequestMessage();
				message.setId(0);
				message.setCommand(RequestMessage.REQUEST_COMMAND_LIST);
				message.setViewRequested(HouseHubView.COMMAND_LIST);
				out.writeObject(message);
				ObjectInputStream in = new ObjectInputStream(client.getInputStream()); //listen for server input
				CommandList commandList = (CommandList) in.readObject();
				System.out.println(commandList);
				client.close();
			}
			disconnect();

		}
		catch(IOException | ClassNotFoundException e) {
			System.out.println("Could not connect to " + hostName);
			e.printStackTrace();
		}
		return id;
	}
	
	public String getRecentMessage() {
		String message = "There are no recent messages from the client.";
		if(!messagesFromServer.isEmpty()) {
			message =  messagesFromServer.remove();
		}
		return message;
	}
	
	public boolean hasMessage() {
		return !messagesFromServer.isEmpty();
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public static void main(String[] args) throws IOException {
		Device device = new Device("Test Device");
		device.setIpAddress("localhost");
		device.setPortNumber(13000);
		
		ControllerConnection controller = new ControllerConnection();
		controller.connectTo(device);
	}
}
