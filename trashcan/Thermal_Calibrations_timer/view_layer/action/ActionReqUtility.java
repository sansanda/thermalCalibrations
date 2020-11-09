package action;


import java.io.IOException;


public class ActionReqUtility {
	ActionRequest request;

	public ActionReqUtility(ActionRequest _req) throws IOException
	{
		request = _req;
	}
	public Action getAction()throws Exception
	{
		/* Use factory to create action based on request parms */
		String action = request.getAction();
		return ActionFactory.createAction(action);
	}
}