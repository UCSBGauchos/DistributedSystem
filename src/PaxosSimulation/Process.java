package PaxosSimulation;

public class Process {
	int PID;
	boolean isLeader;
	BallotNum bal;
	int value;
	
	public Process(int _PID, int _value){
		this.isLeader = false;
		this.PID = _PID;
		this.bal = new BallotNum(0);
		this.value = _value;
	}
}
