package PaxosSimulation;

import java.util.ArrayList;

// the initial value 

public class Paxos {
	
	ArrayList<PrepareProposeMessage> ppMsgList = new ArrayList<PrepareProposeMessage>();
	ArrayList<PrepareRespondMessage> prMsgList = new ArrayList<PrepareRespondMessage>();
	ArrayList<AcceptSendMessage> accSendMsgList = new ArrayList<AcceptSendMessage>();
	ArrayList<AcceptRecvMessage> accRecvMsgList = new ArrayList<AcceptRecvMessage>();
	
	
	
	Process [] procList = new Process[100];
	
	public PrepareProposeMessage createPrepareProposeMsg(int sPID, int dPID, BallotNum bal){
		BallotNum newBal = new BallotNum(sPID);
		PrepareProposeMessage msg = new PrepareProposeMessage("prepare", newBal, sPID, dPID);
		return msg;
	}
	
	public PrepareRespondMessage createPrepareRespondMsg(int sPID, int dPID, BallotNum bal){
		AcceptNum acc = new AcceptNum(bal.num,sPID);
		PrepareRespondMessage msg = new PrepareRespondMessage("ack", acc, sPID, dPID);
		return msg;
	}
	
	public AcceptSendMessage createACSendMsg(int sPID, int dPID, AcceptNum acc, int value){
		BallotNum bal = new BallotNum(sPID);
		bal.num = acc.num;
		if(value == -1){
			AcceptSendMessage msg = new AcceptSendMessage("accept", bal, sPID, dPID, procList[sPID].value);
			return msg;
		}else{
			AcceptSendMessage msg = new AcceptSendMessage("accept", bal, sPID, dPID, value);
			return msg;
		}
	}
	
	public AcceptRecvMessage createACCRecvMsg(int sPID, int dPID, BallotNum bal, int value){
		AcceptRecvMessage msg = new AcceptRecvMessage("accept", bal, sPID, dPID, value);
		return msg;
	}
	
	// when call send function, we will create send msg and put them in the send cache
	public void sendPropose (int sPID, ArrayList<Integer> dPIDs){
		if(procList[sPID].isLeader==true){
			for(int dPID: dPIDs){
				ppMsgList.add(createPrepareProposeMsg(sPID, dPID, procList[sPID].bal));
			}
		}
	}
	
	public void recvPromise(ArrayList<PrepareProposeMessage> msgList, int myPID){
		for(PrepareProposeMessage msg: msgList){
			if(msg.dPID == myPID){
				int sPID = msg.sPID;
				// only if the send bal's num is bigger than local bal, local will send back respond
				if(msg.bal.num>=procList[myPID].bal.num){
					procList[myPID].bal.num = msg.bal.num;
					prMsgList.add(createPrepareRespondMsg(myPID, sPID, msg.bal));
				}
			}
		}
	}
	
	public void sendAccept(ArrayList<PrepareRespondMessage> prMsgList){
		PrepareRespondMessage msgWithBiggestBal = prMsgList.get(prMsgList.size()-1);
		int sPID = msgWithBiggestBal.dPID;
		int dPID = msgWithBiggestBal.sPID;
		accSendMsgList.add(createACSendMsg(sPID, dPID, msgWithBiggestBal.acc, msgWithBiggestBal.value));
	}
	
	public void recvAccept(ArrayList<AcceptSendMessage> accSendMsgList){
		for(AcceptSendMessage msg: accSendMsgList){
			int sPID = msg.dPID;
			int dPID = msg.sPID;
			if(msg.bal.num>=procList[sPID].bal.num){
				accRecvMsgList.add(createACCRecvMsg(sPID, dPID, msg.bal, msg.value));
			}
		}
	}
	
	
	
	
	
	public void getppMsgInfo(ArrayList<PrepareProposeMessage> ppMsgList){
		System.out.println("************Prepare request****************");
		for(PrepareProposeMessage ppMsg: ppMsgList){
			System.out.println("phase is "+ppMsg.phase);
			System.out.println("souce is "+ppMsg.sPID);
			System.out.println("dest is "+ppMsg.dPID);
			System.out.println("ballot is "+ppMsg.bal.num);
		}
	}
	
	public void getMsgWithBiggestBallot(ArrayList<PrepareRespondMessage> prMsgList){
		System.out.println("************Respond request****************");
		System.out.println("souce is "+prMsgList.get(prMsgList.size()-1).sPID);
		System.out.println("dest is "+prMsgList.get(prMsgList.size()-1).dPID);
		System.out.println("phase is "+prMsgList.get(prMsgList.size()-1).phase);
		System.out.println("value is "+prMsgList.get(prMsgList.size()-1).value);
		System.out.println("acc is "+prMsgList.get(prMsgList.size()-1).acc.num);
	}
	
	public void getAccSendMsg(ArrayList<AcceptSendMessage> accSendMsgList){
		System.out.println("************Send accept****************");
		for(AcceptSendMessage assMsg: accSendMsgList){
			System.out.println("phase is "+assMsg.phase);
			System.out.println("souce is "+assMsg.sPID);
			System.out.println("dest is "+assMsg.dPID);
			System.out.println("ballot is "+assMsg.bal.num);
			System.out.println("value is "+assMsg.value);
		}
	}
	
	public void getAccRecvMsg(ArrayList<AcceptRecvMessage> accRecvMsgList){
		System.out.println("************Recv accept****************");
		for(AcceptRecvMessage recvMsg: accRecvMsgList){
			System.out.println("phase is "+recvMsg.phase);
			System.out.println("souce is "+recvMsg.sPID);
			System.out.println("dest is "+recvMsg.dPID);
			System.out.println("ballot is "+recvMsg.bal.num);
			System.out.println("value is "+recvMsg.value);
		}
	}
	
	
	
	public static void main(String [] args){
		Paxos p = new Paxos();
		
		Process p1 = new Process(1, 100);
		Process p2 = new Process(2, 200);
		Process Acceptor = new Process(0, 0);
		
		//procList[PID] = process
		p.procList[1] = p1;
		p.procList[2] = p2;
		p.procList[0] = Acceptor;
		
		//add all acceptor to the list
		ArrayList<Integer> dPIDs = new ArrayList<Integer>();
		dPIDs.add(0);
		
		
		p1.isLeader = true;
		p2.isLeader = true;
		
		//p1 send prepare propose to acceptor;
		p.sendPropose(1, dPIDs);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.sendPropose(2, dPIDs);
		p.getppMsgInfo(p.ppMsgList);
		
		
		p.recvPromise(p.ppMsgList, 0);
		p.getMsgWithBiggestBallot(p.prMsgList);
		
		p.sendAccept(p.prMsgList);
		p.getAccSendMsg(p.accSendMsgList);
		
		p.recvAccept(p.accSendMsgList);
		p.getAccRecvMsg(p.accRecvMsgList);
		
	}
}
