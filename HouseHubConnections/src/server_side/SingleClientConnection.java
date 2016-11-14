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
import data.CommandListItem;
import messages.RequestMessage;
import views.CommandList;

public class SingleClientConnection extends HouseHubConnection{

	private ServerSocket serverSocket;
	Socket server;
	
	public SingleClientConnection(int socketNum) throws IOException {
		serverSocket = new ServerSocket(socketNum);
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
				ObjectOutputStream toClient = new ObjectOutputStream(server.getOutputStream());
				
				//toClient.writeObject(commands);
				server.close();
			}
			catch(Exception e) {
				System.out.println("Something Happened.");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		SingleClientConnection app = new SingleClientConnection(13000);
		app.start();
	}
}
