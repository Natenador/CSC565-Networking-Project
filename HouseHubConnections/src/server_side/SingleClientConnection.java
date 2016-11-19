package server_side;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import base.HouseHubConnection;
import base.HouseHubViewCreation;
import data.CommandListItem;
import messages.LocalCommandResponse;
import messages.RequestMessage;
import views.CommandList;
import views.HouseHubView;

public class SingleClientConnection extends HouseHubConnection{

	private ServerSocket serverSocket;
	Socket server;
	HouseHubViewCreation creator;
	
	public SingleClientConnection(int socketNum, HouseHubViewCreation creator) throws IOException {
		serverSocket = new ServerSocket(socketNum);
		this.creator = creator;
		setSocketNumber(socketNum);
	}
	
	public void run() {
		connected = true;
		while(connected) {
			try {
				System.out.println("Waiting for client on port " + socketNumber);
				server = serverSocket.accept();
				System.out.println("connected.");
				ObjectInputStream fromClient = new ObjectInputStream(server.getInputStream());
				RequestMessage messageFromClient = (RequestMessage) fromClient.readObject();
				//ObjectOutputStream toClient = new ObjectOutputStream(server.getOutputStream());
				if(messageFromClient.getCommand() == RequestMessage.REQUEST_VIEW)
				{
					sendView(messageFromClient.getViewRequested());
				}
				else
				{
					sendLocalCommandResponse(messageFromClient.getCommand());
				}
				server.close();
			}
			catch(Exception e) {
				System.out.println("Something Happened.");
				e.printStackTrace();
			}
		}
	}
	
	private void sendView(int view) throws IOException {
		ObjectOutputStream toClient = new ObjectOutputStream(server.getOutputStream());
		switch(view) {
		case HouseHubView.COMMAND_LIST:
			toClient.writeObject(creator.createCommandList());
			break;
		case HouseHubView.DATA_LIST:
			toClient.writeObject(creator.createDataList());
		}
	}
	
	private void sendLocalCommandResponse(int command) throws IOException {
		ObjectOutputStream toClient = new ObjectOutputStream(server.getOutputStream());
		//switch statement here executes command and sends back view id required to display result
		//controller then sends a request for that view
		LocalCommandResponse response = creator.createLocalCommandResponse(command);
		toClient.writeObject(response);
	}
}
