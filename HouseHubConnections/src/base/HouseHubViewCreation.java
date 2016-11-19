package base;

import messages.LocalCommandResponse;
import views.CommandList;
import views.DataList;

/**
 * This interface will be used for each app that is created
 * The developer will implement this interface to define what each view will consist of.
 * The class they create will be used in a factory to generate views.
 * @author natha
 *
 */
public interface HouseHubViewCreation {

	public LocalCommandResponse createLocalCommandResponse(int command);
	public CommandList createCommandList();
	public DataList createDataList();
}
