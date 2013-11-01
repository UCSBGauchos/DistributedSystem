package PaxosSimulation;

public class BallotNum {
	int num;
	int PID;
	
	public BallotNum(int _PID){
		this.num = new java.util.Date().getSeconds();
		this.PID = _PID;
	}
}
