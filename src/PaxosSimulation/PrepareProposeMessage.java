package PaxosSimulation;

public class PrepareProposeMessage {
	String phase;
	BallotNum bal;
	int sPID;
	int dPID;
	
	public PrepareProposeMessage(String _phase, BallotNum _bal, int _sPID, int _dPID){
		this.phase = _phase;
		this.bal = _bal;
		this.sPID = _sPID;
		this.dPID = _dPID;
	}
}
