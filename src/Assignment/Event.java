package Assignment;

public class Event {
	String operationType;
	int time;
	int NodeID;
	
	public Event(String _operationType, int _time, int _NodeID){
		this.operationType = _operationType;
		this.time = _time;
		this.NodeID = _NodeID;
	}
	
}
