package PaxosSimulation;

public class PrepareRespondMessage {
	String phase;
	AcceptNum acc;
	int sPID;
	int dPID;
	int value;
	
	public PrepareRespondMessage(String _phase, AcceptNum _acc, int _sPID, int _dPID){
		this.phase = _phase;
		this.acc = _acc;
		this.sPID = _sPID;
		this.dPID = _dPID;
		this.value = -1;
	}
}
