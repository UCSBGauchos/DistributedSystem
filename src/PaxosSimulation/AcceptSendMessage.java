package PaxosSimulation;

public class AcceptSendMessage {
	String phase;
	BallotNum bal;
	int value;
	int sPID;
	int dPID;
	
	public AcceptSendMessage(String _phase, BallotNum _bal, int _sPID, int _dPID, int _value){
		this.phase = _phase;
		this.bal = _bal;
		this.sPID = _sPID;
		this.dPID = _dPID;
		this.value = _value;
	}
}
