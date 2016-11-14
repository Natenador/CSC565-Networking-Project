package server_side;

import base.HouseHubViewCreation;
import data.CommandListItem;
import views.CommandList;
import views.DataList;

/**
 * This class will be used to generate views for the Grocery List App
 * Not accurate at the moment
 * Will be different for every app.
 * @author natha
 *
 */
public class GroceryListViewCreation implements HouseHubViewCreation{

	@Override
	public CommandList createCommandList() {
		CommandList commands = new CommandList();
		commands.addCommand("View List", 0);
		return commands;
	}

	@Override
	public DataList createDataList() {
		// TODO Auto-generated method stub
		return null;
	}

}
