package data;

public class DataListItem extends HouseHubData {

	public DataListItem(int command) {
		super(command);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5599337574387388274L;

	private String dataName;
	private String description;
	private int icon;
	private String iconTitle;
	private int iconCommand;
	
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
