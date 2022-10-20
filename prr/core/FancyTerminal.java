package prr.core;

import prr.core.exception.InvalidKeyException;

public class FancyTerminal extends Terminal {

	public FancyTerminal(String terminalID, String clientID) throws InvalidKeyException {
		super(terminalID, clientID);
	}
	
	public void makeVideoCall(Terminal terminalTo){

	}

	void acceptVideoCall(Terminal terminalFrom){
		
	}
	
	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

}	
