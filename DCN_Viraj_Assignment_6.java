package dcn_lab6_2;
import java.io.*;
import java.util.Scanner;
import Fsm.Action;
import Fsm.FSM;
import Fsm.Transition;


public class DCN_Viraj_Assignment_6 {
	
	public DCN_Viraj_Assignment_6 (String string) {
		
	}
	
	
	public static void main(String args[]) {
		Ext_Event_Class FsmEvent = null;
		
		int RDataCount = 0;
		int SDataCount = 0;
		
	
		// Creating the state objects to be considered:
		
		Ext_State_Class Closed_State = new Ext_State_Class("CLOSED");
		Ext_State_Class SynSent_State = new Ext_State_Class("SYN_SENT");
		Ext_State_Class Listen_State = new Ext_State_Class("LISTEN");
		Ext_State_Class SynRecd_State = new Ext_State_Class("SYN_RCVD");
		Ext_State_Class Finwait1_State = new Ext_State_Class("FIN_WAIT_1");
		Ext_State_Class Finwait2_State = new Ext_State_Class("FIN_WAIT_2");
		Ext_State_Class Estblshd_State = new Ext_State_Class("ESTABLISHED");
		Ext_State_Class Closing_State = new Ext_State_Class("CLOSING");
		Ext_State_Class Timewait_State = new Ext_State_Class("TIME_WAIT");
		Ext_State_Class CloseWait_State = new Ext_State_Class("CLOSE_WAIT");
		Ext_State_Class LastAck_State = new Ext_State_Class("LAST_ACK");
		
		//Defining various actions objects for the actions taking place
		Ext_Action_Class synAckAction = new Ext_Action_Class("synack");
		Ext_Action_Class synAction = new Ext_Action_Class("syn");
		Ext_Action_Class finAction = new Ext_Action_Class("fin");
		Ext_Action_Class ackAction = new Ext_Action_Class("ack");
		Ext_Action_Class nAction = new Ext_Action_Class("n");
		Ext_Action_Class nullAction = new Ext_Action_Class("null");
		
		// Defining Event objects for the events
		Ext_Event_Class Active_Event = new Ext_Event_Class("ACTIVE");
		Ext_Event_Class Passive_Event = new Ext_Event_Class("PASSIVE");
		Ext_Event_Class SynAck_Event = new Ext_Event_Class("SYNACK");
		Ext_Event_Class Syn_Event = new Ext_Event_Class("SYN");
		Ext_Event_Class Ack_Event = new Ext_Event_Class("ACK");
		Ext_Event_Class rData_Event = new Ext_Event_Class("RDATA");
		Ext_Event_Class sData_Event = new Ext_Event_Class("SDATA");
		Ext_Event_Class Fin_Event = new Ext_Event_Class("FIN");
		Ext_Event_Class Close_Event = new Ext_Event_Class("CLOSE");
		Ext_Event_Class Timeout_Event = new Ext_Event_Class("TIMEOUT");
		
		// Defining FSM transitions as follows:
		
		Transition trans_1 = new Transition(Closed_State, Passive_Event, Listen_State, nullAction);
		
		Transition trans_2 = new Transition(Listen_State, Syn_Event, SynRecd_State, synAckAction);
		
		Transition trans_3 = new Transition(SynRecd_State, Close_Event, Finwait1_State, finAction);
		
		Transition trans_4 = new Transition(Finwait1_State, Ack_Event, Finwait2_State, nullAction);
		
		Transition trans_5 = new Transition(Finwait2_State, Fin_Event, Timewait_State, ackAction);
		
		Transition trans_6 = new Transition(Timewait_State, Timeout_Event, Closed_State, nullAction);
		
		Transition trans_7 = new Transition(Finwait1_State, Fin_Event, Closing_State, ackAction);
		
		Transition trans_8 = new Transition(Closing_State, Ack_Event, Timewait_State, nullAction);
		
		Transition trans_9 = new Transition(LastAck_State, Ack_Event, Closed_State, nullAction);
		
		Transition trans_10 = new Transition(CloseWait_State, Close_Event, LastAck_State, finAction);
		
		Transition trans_11 = new Transition(Estblshd_State, Fin_Event, CloseWait_State, ackAction);
		
		Transition trans_12 = new Transition(Estblshd_State, Fin_Event, Finwait1_State, finAction);
		
		Transition trans_13 = new Transition(Estblshd_State, rData_Event, Estblshd_State, nAction);
		
		Transition trans_14 = new Transition(Estblshd_State, sData_Event, Estblshd_State, nAction);
		
		Transition trans_15 = new Transition(SynRecd_State, Ack_Event, Estblshd_State, nullAction);
		
		Transition trans_16 = new Transition(SynSent_State, SynAck_Event, Estblshd_State, nullAction);
		
		Transition trans_17 = new Transition(SynSent_State, Syn_Event, SynRecd_State, synAckAction);
		
		Transition trans_18 = new Transition(Listen_State, Close_Event, Closed_State, nullAction);
		
		Transition trans_19 = new Transition(SynSent_State, Close_Event, Closed_State, nullAction);
		
		Transition trans_20 = new Transition(Closed_State, Active_Event, SynSent_State, synAction);
		
		
		// Initializing FSM object with closed state
		FSM f_sm =  new FSM("TCPFsm", Closed_State);
		
		// appending transitions to FSM
		Transition [] transitions = new Transition [] {trans_1,trans_2,trans_3,trans_4,trans_5,trans_6
				,trans_7,trans_8,trans_9,trans_10,trans_11,trans_12,trans_13,trans_14,trans_15,
				trans_16,trans_17,trans_18,trans_19,trans_20};
		try {
			
			for (int  i=0;i<=transitions.length;i++) {
			f_sm.addTransition(transitions[i]);
			}
		}
		catch(Exception Ex){
			System.out.println("Exception: " + Ex.getMessage());
		}
		
		// To read events from the standard command line
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			String a = scanner.nextLine();
			
			try {
				FsmEvent = new Ext_Event_Class(a);
				
				//If event RData, we will increment the count and display it
				
				if (FsmEvent.getName().equals("SDATA")) {
					SDataCount = SDataCount + 1;
					System.out.println("Data Sent: " + SDataCount );
					
				}
				if (FsmEvent.getName().equals("RDATA")) {
					RDataCount = RDataCount + 1;
					System.out.println("Data Received: " + RDataCount);
					}
				f_sm.doEvent(FsmEvent);
				
			}
			catch(Exception Ex) {
				System.out.println("Unknown event " + a);
			}
		
		
		
		}
	
		
		
	}

}
