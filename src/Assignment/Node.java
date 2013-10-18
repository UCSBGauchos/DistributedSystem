package Assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
	int localClock;
	int [][] twoDimensionalTimeTable;
	ArrayList<Event> log;
	//the hash will contain all the key of one node, like a dictionary of the node
	Map<String, Integer> hashmap; 
	
	public Node(int _localClock, int [][] _twoDimensionalTimeTable, ArrayList<Event> _log){
		this.localClock = _localClock;
		this.twoDimensionalTimeTable = _twoDimensionalTimeTable;
		this.log = _log;
		this.hashmap = new HashMap<String, Integer>();
	}
	
}
