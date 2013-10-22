package Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	public static void main(String [] args){
		ReplicatedLogFunc rl = new ReplicatedLogFunc();
		Scanner in = new Scanner(System.in);
		
		//initial all three clocks 
		int initialClock1=0;
		int initialClock2=0;
		int initialClock3=0;
		
		//there are three replicas in the system.
		int replicaNum = 3;
		
		//each replica has its own timetable
		int [][] timeTable1 = new int [replicaNum][replicaNum];
		int [][] timeTable2 = new int [replicaNum][replicaNum];
		int [][] timeTable3 = new int [replicaNum][replicaNum];
		
		//init the time table
		rl.init(timeTable1);
		rl.init(timeTable2);
		rl.init(timeTable3);
		
		//create three logs for each machine
		ArrayList<Event> log1 = new ArrayList<Event>();
		ArrayList<Event> log2 = new ArrayList<Event>();
		ArrayList<Event> log3 = new ArrayList<Event>();
		
		//create three nodes based on the exist clock, timetable and log
		Node n1 = new Node(initialClock1, timeTable1, log1);
		Node n2 = new Node(initialClock2, timeTable2, log2);
		Node n3 = new Node(initialClock3, timeTable3, log3);
		
		//add these nodes to the node list
		rl.replicaList.add(n1);
		rl.replicaList.add(n2);
		rl.replicaList.add(n3);
		
		while(true){
			System.out.println("**************************************************");
			System.out.println("Please Note: DONT INPUT SPACE IN YOUR INPUT COMMAND");
			System.out.println("**************************************************");
			System.out.println("Please input the command you woudl like to input");
			String command = in.nextLine();
			if(command.substring(0, 9).equals("Increment")){
				String replicaID = command.substring(10, 11);
				String key = command.substring(13, 14);
				if(replicaID.equals("1")){
					rl.increment(1, key);
				}else if(replicaID.equals("2")){
					rl.increment(2, key);
				}else if(replicaID.equals("3")){
					rl.increment(3, key);
				}else{
					System.out.println("The machine must be 1-3");
				}
			}
			else if(command.substring(0,8).equals("getValue")){
				String replicaID = command.substring(9,10);
				String key = command.substring(12,13);
				if(replicaID.equals("1")){
					rl.getValue(1, key);
				}else if(replicaID.equals("2")){
					rl.getValue(2, key);
				}else if(replicaID.equals("3")){
					rl.getValue(3, key);
				}else{
					System.out.println("The machine must be 1-3");
				}
			}else if(command.substring(0,10).equals("PrintState")){
				String replicaID = command.substring(11,12);
				if(replicaID.equals("1")){
					rl.printState(1);
				}else if(replicaID.equals("2")){
					rl.printState(2);
				}else if(replicaID.equals("3")){
					rl.printState(3);
				}else{
					System.out.println("The machine must be 1-3");
				}
			}else if(command.substring(0,7).equals("SendLog")){
				String source = command.substring(8, 9);
				String dest = command.substring(10,11);
				int sourceID = 0;
				int destID = 0;
				if(source.equals("1")){
					sourceID = 1;
				}else if(source.equals("2")){
					sourceID = 2;
				}else if(source.equals("3")){
					sourceID = 3;
				}
				if(dest.equals("1")){
					destID = 1;
				}else if(dest.equals("2")){
					destID = 2;
				}else if(dest.equals("3")){
					destID = 3;
				}
				rl.sendLog(sourceID, destID);
			}else if(command.substring(0, 10).equals("ReceiveLog")){
				String trans = command.substring(11,12);
				int transID = trans.charAt(0)-48;
				rl.recvLog(transID);
			}
		}
	}
}
