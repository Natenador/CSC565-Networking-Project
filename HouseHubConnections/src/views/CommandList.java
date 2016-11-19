package views;

import java.util.ArrayList;
import java.util.List;

import data.CommandListItem;

public class CommandList extends HouseHubView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3556277046405156405L;
	
	private List<CommandListItem> commands;
	
	public CommandList() {
		setType(COMMAND_LIST);
		commands = new ArrayList<CommandListItem>();
	}
	public List<CommandListItem> getCommands() {
		return commands;
	}
	public void setCommands(List<CommandListItem> commands) {
		this.commands = commands;
	}
	
	public void addCommand(CommandListItem command) {
		this.commands.add(command);
	}
	public void addCommand(String name, int type) {
		commands.add(new CommandListItem(name, type));
	}
}
