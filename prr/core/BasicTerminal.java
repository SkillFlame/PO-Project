package prr.core;

import prr.core.exception.InvalidKeyException;

/**
 * Specialization of a Terminal by its type being "BASIC"
 */
public class BasicTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;


	BasicTerminal(String terminalId, Client client) throws InvalidKeyException {
		super(terminalId, client);
	}

	
	/**
	 * toString implementation of a Basic Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paId|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {
		String output = "BASIC" + "|" + super.toString();
		return output;
	}

}
