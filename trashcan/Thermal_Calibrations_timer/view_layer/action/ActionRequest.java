package action;


import javax.swing.JFrame;

public class ActionRequest {
	private String action;
	private JFrame frameWhoHasTheRequest;
	
	public ActionRequest(String _action, JFrame _frameWhoHasTheRequest){
		action = _action;
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public String getAction(){return action;}
	//public void setAction(String _action){action = _action;}
	public JFrame getFrameWhoHasTheRequest(){return frameWhoHasTheRequest;}
}
