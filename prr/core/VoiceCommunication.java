package prr.core;

/**
 * Specialization of an Interactive Communication by Voice
 */
public class VoiceCommunication extends InteractiveCommunication {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;


	public VoiceCommunication(Terminal sender, Terminal receiver) {
		super(sender, receiver);
	}


	/** 
	 * Computes the cost of the Voice Communication made using the Terminal owner's Rateplan
	 * 
	 * @param ratePlan the owner's rateplan
	 */
	@Override
	double computeCost(RatePlan ratePlan) {
		double price = ratePlan.computeCost(getTerminalSender().getOwner(), this, getSize());
		if (getTerminalSender().getFriends().contains(getTerminalReceiver().getId())) {
			price *= 0.5;
		}

		setPrice(price);
		
		return price;
	}


	/**
	 * toString implementation of a Communication
	 * type|IdCommunication|IdSender|IdReceiver|units|price|status
	 */
	@Override
	public String toString() {
		String output = "VOICE" + "|" + super.toString();
		return output;
	}

}
