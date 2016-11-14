package base;

import java.io.IOException;

public class HouseHubConnection extends Thread{
	
	public static int CURRENT_ID = 0;
	public static final int CONTROLLER_CONNECTION = 0;
	public static final int MULTI_CLIENT_CONNECTION = 1;
	public static final int SINGLE_CLIENT_CONNECTION = 2;
	public static final int CONTOLLER_PORT_NUMBER = 13000;
	
	protected int socketNumber;
	protected int id;
	protected boolean connected;

	public int getSocketNumber() {
		return socketNumber;
	}

	public void setSocketNumber(int socketNumber) {
		this.socketNumber = socketNumber;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isConnected() {
		return connected;
	}
	public void disconnect() throws IOException {
		connected = false;
	}
}
