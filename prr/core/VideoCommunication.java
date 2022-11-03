package prr.core;

/**
 * Specialization of an Interactive Communication by Video
 */
public class VideoCommunication extends InteractiveCommunication {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	public VideoCommunication(Terminal sender, Terminal receiver) {
		super(sender, receiver);
	}

	@Override
	double computeCost(RatePlan ratePlan) {
		double price = ratePlan.computeCost(getTerminalSender().getOwner(), this);
		if (getTerminalSender().getFriends().contains(getTerminalReceiver().getId())) {
			price *= 0.5;
		}

		setPrice(price);

		return price;
	}

	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */

	@Override
	public String toString() {
		String output = "VIDEO" + "|" + super.toString();
		return output;
	}
}
