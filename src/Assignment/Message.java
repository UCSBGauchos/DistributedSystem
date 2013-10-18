package Assignment;

import java.util.ArrayList;

public class Message {
	ArrayList<Event> log;
	int [][] twoDimensionalTimeTable;
	int sourceID;
	int destID;
	int transID;
	
	public Message(ArrayList<Event> _log, int [][] _2DTT, int _sourceID, int _destID, int _transID){
		this.log = _log;
		this.twoDimensionalTimeTable = _2DTT;
		this.sourceID = _sourceID;
		this.destID = _destID;
		this.transID = _transID;
	}
}
