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
import messages.LocalCommandResponse;
import messages.RequestMessage;
import domain.Device;
import views.CommandList;
import views.DataList;
import views.HouseHubView;

public class ControllerConnection extends HouseHubConnection{
	
	
	private String hostName;
	//Socket controllerSocket;
	DataOutputStream toServer;
	DataInputStream fromServer;
	Queue<String> messagesFromServer = new LinkedBlockingQueue<String>();
	
	public ControllerConnection(Device device) {
		setHostName(device.getIpAddress());
		setSocketNumber(device.getSocketNumber());
		connected = false;
	}

	public CommandList requestCommandList() {
		CommandList commandList= null;
		try {
			Socket client = new Socket(hostName, socketNumber);  //create connection with server
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			RequestMessage message = new RequestMessage();
			message.setId(0);
			message.setCommand(RequestMessage.REQUEST_VIEW);
			message.setViewRequested(HouseHubView.COMMAND_LIST);
			out.writeObject(message);
			ObjectInputStream in = new ObjectInputStream(client.getInputStream()); //listen for server input
			commandList = (CommandList) in.readObject();
			client.close();

		}
		catch(IOException | ClassNotFoundException e) {
			System.out.println("Could not connect to " + hostName);
			e.printStackTrace();
		}
		return commandList;
	}
	
	public DataList requestDataList() {
		DataList dataList= null;
		try {
			Socket client = new Socket(hostName, socketNumber);  //create connection with server
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			RequestMessage message = new RequestMessage();
			message.setId(0);
			message.setCommand(RequestMessage.REQUEST_VIEW);
			message.setViewRequested(HouseHubView.DATA_LIST);
			out.writeObject(message);
			ObjectInputStream in = new ObjectInputStream(client.getInputStream()); //listen for server input
			dataList = (DataList) in.readObject();
			System.out.println(dataList);
			client.close();

		}
		catch(IOException | ClassNotFoundException e) {
			System.out.println("Could not connect to " + hostName);
			e.printStackTrace();
		}
		return dataList;
	}
	
	public LocalCommandResponse sendLocalCommand(int command) {
		LocalCommandResponse response = null;
		try {
			Socket client = new Socket(hostName, socketNumber);  //create connection with server
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			RequestMessage message = new RequestMessage();
			message.setId(0);
			message.setCommand(command);
			out.writeObject(message);
			ObjectInputStream in = new ObjectInputStream(client.getInputStream()); //listen for server input
			response = (LocalCommandResponse) in.readObject();
			client.close();

		}
		catch(IOException | ClassNotFoundException e) {
			System.out.println("Could not connect to " + hostName);
			e.printStackTrace();
		}
		return response;
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
}
