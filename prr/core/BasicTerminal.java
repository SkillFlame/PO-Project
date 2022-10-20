package prr.core;

import prr.core.exception.InvalidKeyException;

public class BasicTerminal extends Terminal {

	public BasicTerminal(String terminalID, String clientID) throws InvalidKeyException {
		super(terminalID, clientID);
	}

	@Override
	public String toString() {
		String output = "BASIC" + "|" + super.toString();
		return output;
	}
	
}
