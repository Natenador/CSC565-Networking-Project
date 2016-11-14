package data;

public class CommandListItem extends HouseHubData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1217905918477452666L;
	
	private String commandName;
	
	public CommandListItem(String name, int command) {
		super(command);
		this.commandName = name;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	@Override
	public String toString() {
		return "CommandListItem [commandName=" + commandName + "]";
	}

}
