package prr.core;

/**
 * Specialization of a Communication by Text
 */
public class TextCommunication extends Communication {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _message;

	public TextCommunication(Terminal sender, Terminal receiver, String message) {
		super(sender, receiver);
		_message = message;
	}

	@Override
	void setSize(int size) {
	}

	/**
	 * Gets the length of the Text Communication made
	 */
	@Override
	int getSize() {
		return _message.length();
	}

	/**
	 * Computes the cost of the Text Communication made using the Terminal owner's
	 * Rateplan
	 * 
	 * @param ratePlan the owner's rateplan
	 */
	@Override
	double computeCost(RatePlan ratePlan) {
		double price = ratePlan.computeCost(getTerminalSender().getOwner(), this);
		setPrice(price);
		return price;
	}

	/**
	 * toString implementation of a Communication
	 * type|IdCommunication|IdSender|IdReceiver|units|price|status
	 */
	@Override
	public String toString() {
		String output = "TEXT" + "|" + super.toString();
		return output;
	}

}
