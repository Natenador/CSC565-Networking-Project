package messages;

import java.io.Serializable;

public class LocalCommandResponse implements Serializable{

	private int viewType;

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}
	
}
