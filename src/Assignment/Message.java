package Assignment;

import java.util.ArrayList;

public class Message {
	ArrayList<Event> log;
	int [][] twoDimensionalTimeTable;
	
	public Message(ArrayList<Event> _log, int [][] _2DTT){
		this.log = _log;
		this.twoDimensionalTimeTable = _2DTT;
	}
}
