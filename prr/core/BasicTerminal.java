package prr.core;

import prr.core.exception.InvalidKeyException;

/**
 * Specialization of a Terminal by its type being "BASIC"
 */
public class BasicTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public BasicTerminal(String terminalID, String clientID) throws InvalidKeyException {
		super(terminalID, clientID);
	}

	/**
	 * toString implementation of a Basic Terminal
	 */
	@Override
	public String toString() {
		String output = "BASIC" + "|" + super.toString();
		return output;
	}

}
