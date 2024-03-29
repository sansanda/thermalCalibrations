package controller;

import java.io.IOException;

public class ReqUtility {
	ActionRequest request;
	
	public ReqUtility(ActionRequest _req) throws IOException
	{
		request = _req;
	}
	public IAction getAction()throws Exception
	{
		/* Use factory to create action based on request parms */
		String action = request.getAction();
		return ActionFactory.createAction(action);
	}
}