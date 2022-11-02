package prr.core;

import prr.core.exception.InvalidKeyException;

/**
 * Specialization of a Terminal by its type being "FANCY"
 */
public class FancyTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	FancyTerminal(String terminalID, String clientID) throws InvalidKeyException {
		super(terminalID, clientID);
	}

	/**
	 * toString implementation of a Fancy Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

	
	public void makeVideoCall(Terminal terminalTo) {
		if( terminalTo.getMode() != OffMode.getMode() || terminalTo.getMode() != BusyMode.getMode()){

		}
		
	}

	void acceptVideoCall(Terminal terminalFrom) {
		// TODO Auto-generated method stub
		
	}

}
