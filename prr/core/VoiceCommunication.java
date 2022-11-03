package prr.core;

/**
 * Specialization of an Interactive Communication by Voice
 */
public class VoiceCommunication extends InteractiveCommunication {
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;


	@Override
	double computeCost(RatePlan ratePlan) {
		double price = getPrice();
		price = ratePlan.computeCost(getTerminalSender().getOwner(), this);
		if(getTerminalSender().getFriends().contains(getTerminalReciever().getId())){
			price *= 0.5;
		}
		return price;
	}

	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = "VOICE" + "|" + super.toString();
		return output;
	}
}
