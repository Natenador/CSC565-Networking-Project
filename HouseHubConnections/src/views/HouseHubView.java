package views;

import java.io.Serializable;

public class HouseHubView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7520888287654731553L;
	
	//view types
	public static final int COMMAND_LIST = 0;
	public static final int DATA_LIST = 1;
	
	private int type;
	private String name;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
