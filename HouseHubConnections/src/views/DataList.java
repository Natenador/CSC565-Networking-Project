package views;

import java.util.ArrayList;
import java.util.List;

import data.DataListItem;

public class DataList extends HouseHubView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6205391688593759222L;
	
	private List<DataListItem> dataItems = new ArrayList<DataListItem>();
	private int icon;
	private String iconTitle;
	private int iconCommand;
	
	public DataList() {
		setType(DATA_LIST);
	}

	public List<DataListItem> getDataItems() {
		return dataItems;
	}

	public void setDataItems(List<DataListItem> dataItems) {
		this.dataItems = dataItems;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getIconTitle() {
		return iconTitle;
	}

	public void setIconTitle(String iconTitle) {
		this.iconTitle = iconTitle;
	}

	public int getIconCommand() {
		return iconCommand;
	}

	public void setIconCommand(int iconCommand) {
		this.iconCommand = iconCommand;
	}

}
