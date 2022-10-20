package prr.core;

import prr.core.exception.InvalidKeyException;

public class FancyTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public FancyTerminal(String terminalID, String clientID) throws InvalidKeyException {
		super(terminalID, clientID);
	}

	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

}
