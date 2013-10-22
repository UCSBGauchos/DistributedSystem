package Assignment;

import java.util.ArrayList;

public class Message {
	ArrayList<Event> log = new ArrayList<Event>();
	int [][] twoDimensionalTimeTable = new int [3][3];
	int sourceID;
	int destID;
	int transID;
	
	public Message(ArrayList<Event> _log, int [][] _2DTT, int _sourceID, int _destID, int _transID){
		for(Event e: _log){
			this.log.add(e);
		}
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				twoDimensionalTimeTable[i][j] = _2DTT[i][j];
			}
		}
		this.sourceID = _sourceID;
		this.destID = _destID;
		this.transID = _transID;
	}
}
