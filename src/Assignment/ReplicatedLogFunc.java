package Assignment;

import java.util.ArrayList;

public class ReplicatedLogFunc{
	
	//unique ID;
	int tramissionID = 0;
	//three replicas
	ArrayList<Node> replicaList = new ArrayList<Node>();
	//the list of the message
	ArrayList<Message> msgList = new ArrayList<Message>();
	//initialize the local time table 
	public void init(int [][] timeTable){
		for(int i=0; i<timeTable.length; i++){
			for(int j=0; j<timeTable[0].length; j++){
				timeTable[i][j] = 0;
			}
		}
	}
	
	//hasrec function
	public boolean hasrec(int Ti, Event eR, int k){
		Node replica = replicaList.get(Ti-1);
		int [][] timeTable = replica.twoDimensionalTimeTable;
		return (timeTable[k-1][eR.NodeID-1]>=eR.time);
	}
	
	public boolean shouldRemove(Event eR, Node localNode, int nodeID){
		for(int i=1; i<=localNode.twoDimensionalTimeTable.length; i++){
			if(!hasrec(nodeID, eR, i)){
				return false;
			}
		}
		return true;
	}
	
	public void increment(int replicaID, String key){
		Node replica = replicaList.get(replicaID-1);
		if(replica == null){
			return;
		}
		int newTime = replica.localClock+1;
		Event newEvent = new Event("increnemt("+key+")", newTime, replicaID); //event contains op, ti, Ni
		replica.twoDimensionalTimeTable[replicaID-1][replicaID-1] = newTime;
		replica.log.add(newEvent);
		if(!replica.hashmap.containsKey(key)){
			replica.hashmap.put(key, 1);
		}else{
			int oldValue = replica.hashmap.get(key);
			int newValue = oldValue+1;
			replica.hashmap.put(key, newValue);
		}
	}
	
	public void decrement(int replicaID, String key){
		Node replica = replicaList.get(replicaID-1);
		if(replica == null){
			return;
		}
		int newTime = replica.localClock+1;
		Event newEvent = new Event("decrement("+key+")", newTime, replicaID);
		replica.twoDimensionalTimeTable[replicaID-1][replicaID-1] = newTime;
		replica.log.add(newEvent);
		if(!replica.hashmap.containsKey(key)){
			return;
		}else{
			int oldValue = replica.hashmap.get(key);
			if(oldValue>=1){
				int newValue = oldValue-1;
				replica.hashmap.put(key, newValue);
			}else{
				return;
			}
		}
	}
	
	public void getValue(int replicaID, String key){
		Node replica = replicaList.get(replicaID-1);
		if(replica == null){
			return;
		}else{
			System.out.println(replica.hashmap.get(key));
		}
	}
	public void printState(int replicaID){
		Node replica = replicaList.get(replicaID-1);
		if(replica == null){
			return;
		}else{
			System.out.println("Time table is");
			for(int i=0;i<replica.twoDimensionalTimeTable.length; i++){
				for(int j=0; j<replica.twoDimensionalTimeTable[0].length; j++){
					System.out.print(replica.twoDimensionalTimeTable[i][j]+" ");
				}
				System.out.println();
			}
			if(replica.log.size()==0){
				System.out.println("nothing in the log");
			}else{
				for(Event e: replica.log){
					System.out.print("log is "+e.operationType+" ");
				}
				System.out.println();
			}
		}
	}
	public int sendLog(int sourceReplicaID, int destReplicaID){
		Node replica = replicaList.get(sourceReplicaID-1);
		ArrayList<Event> NP = new ArrayList<Event>();
		for(Event e: replica.log){
			if(!hasrec(sourceReplicaID, e, destReplicaID)){
				NP.add(e);
			}
		}
		tramissionID++;
		Message newMsg = new Message(NP, replica.twoDimensionalTimeTable, sourceReplicaID, destReplicaID, tramissionID);
//		System.out.println("The msg information is ");
		System.out.println("TransID is "+newMsg.transID);
//		System.out.println("sourceID is "+newMsg.transID);
//		System.out.println("destID is "+newMsg.destID);
		msgList.add(newMsg);
		System.out.println("add this message to the list.......");
		return tramissionID;	
	}
	public void recvLog(int transmitID){
		int index = 0;
		for(Message msg: msgList){
			if(msg.transID == transmitID){
				break;
			}
			index++;
		}
		Message messageExpected = msgList.get(index);
		ArrayList<Event> NP = messageExpected.log;
		Node localNode = replicaList.get(messageExpected.destID-1);
		Node remoteNode = replicaList.get(messageExpected.sourceID-1);
		ArrayList<Event> NE = new ArrayList<Event>();
		for(Event f: NP){
			if(!hasrec(messageExpected.destID, f, messageExpected.destID)){
				NE.add(f);
			}
		}
		for(Event f:NE){
			String key = f.operationType.substring(f.operationType.length()-2, f.operationType.length()-1);
			if(f.operationType.substring(0,2).equals("in")){
				if(!localNode.hashmap.containsKey(key)){
					localNode.hashmap.put(key, 1);
				}else{
					int oldValue = localNode.hashmap.get(key);
					int newValue = oldValue+1;
					localNode.hashmap.put(key, newValue);
				}
			}else if(f.operationType.substring(0,2).equals("de")){
				if(localNode.hashmap.containsKey(key)){
					if(localNode.hashmap.get(key)>=1){
						int oldValue = localNode.hashmap.get(key);
						int newValue = oldValue-1;
						localNode.hashmap.put(key, newValue);
					}
				}
			}
		}
		int [][] localTimeTable = localNode.twoDimensionalTimeTable;
		int [][] remoteTimeTable = remoteNode.twoDimensionalTimeTable;
		
		for(int i=0; i<localTimeTable.length; i++){
			localTimeTable[messageExpected.destID-1][i] = Math.max(localTimeTable[messageExpected.destID-1][i], remoteTimeTable[messageExpected.sourceID-1][i]);
		}
		for(int i=0; i<localTimeTable.length; i++){
			for(int j=0; j<localTimeTable[0].length; j++){
				localTimeTable[i][j] = Math.max(localTimeTable[i][j], remoteTimeTable[i][j]);
			}
		}
		for(Event f:NE){
			localNode.log.add(f);
		}
		ArrayList<Event> tmpLog = new ArrayList<Event>();
		tmpLog.addAll(localNode.log);
		for(Event eR: tmpLog){
			if(shouldRemove(eR, localNode, messageExpected.destID)){
				localNode.log.remove(eR);
			}
		}
	}
	
	public static void main(String [] args){
		ReplicatedLogFunc rl = new ReplicatedLogFunc();
		
		//system clock
		int initialClock1=0;
		int initialClock2=0;
		int initialClock3=0;
		
		int replicaNum = 3;
		
		int [][] timeTable1 = new int [replicaNum][replicaNum];
		int [][] timeTable2 = new int [replicaNum][replicaNum];
		int [][] timeTable3 = new int [replicaNum][replicaNum];
		rl.init(timeTable1);
		rl.init(timeTable2);
		rl.init(timeTable3);
		
		//initial part of the algorithm, init the log and timetable
		ArrayList<Event> log1 = new ArrayList<Event>();
		ArrayList<Event> log2 = new ArrayList<Event>();
		ArrayList<Event> log3 = new ArrayList<Event>();
		
		//Now create 3 replicas
		Node n1 = new Node(initialClock1, timeTable1, log1);
		Node n2 = new Node(initialClock2, timeTable2, log2);
		Node n3 = new Node(initialClock3, timeTable3, log3);
		
		//add these three replicas into the replicaList
		rl.replicaList.add(n1);
		rl.replicaList.add(n2);
		rl.replicaList.add(n3);
		
		rl.increment(1, "x");
		rl.getValue(1, "x");
		rl.getValue(2, "x");
		rl.printState(1);
		rl.sendLog(1, 2);
		rl.increment(1, "Y");
		rl.printState(2);
		rl.recvLog(1);
		rl.printState(2);
		rl.getValue(2, "x");
	}
}
