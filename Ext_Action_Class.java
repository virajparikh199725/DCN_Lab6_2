package dcn_lab6_2;

import Fsm.Action;
import Fsm.Event;
import Fsm.FSM;

public class Ext_Action_Class extends Action{
	
	private String actionName;
	
	public Ext_Action_Class(String action) {
		this.actionName = action;
	}
	

	@Override
	public void execute(FSM FSM, Event Event) {
		// TODO Auto-generated method stub
		if (!(Event.getName().equals("RDATA")|| Event.getName().equals("SDATA"))) {
			System.out.println("Event " + Event.getName());
		}
		
	}

}
