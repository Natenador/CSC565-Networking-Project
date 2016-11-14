package data;

import java.io.Serializable;

public class HouseHubData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4154824329542918714L;
	
	private int command;
	public HouseHubData(int command) {
		this.command = command;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

}
