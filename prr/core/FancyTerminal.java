package prr.core;

import prr.core.exception.InvalidKeyException;

/**
 * Specialization of a Terminal by its type being "FANCY"
 */
public class FancyTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	FancyTerminal(String terminalId, String clientId) throws InvalidKeyException {
		super(terminalId, clientId);
	}

	/**
	 * toString implementation of a Fancy Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paId|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

	@Override
	public void makeVideoCall(Terminal receiver) {
		addMadeCommunication(getMode().makeVideoCall(this, receiver));
	}
	@Override
	void acceptVideoCall(Terminal terminalFrom) {
		addReceivedCommunication(getMode().acceptVideoCall(terminalFrom));
	}

}
