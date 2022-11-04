package prr.core;

/**
 * Specialization of a Communication by Text
 */
public class TextCommunication extends Communication {
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private String _message;

	public TextCommunication(Terminal sender, Terminal receiver, String message){
		super(sender, receiver);
		_message = message;
	}
	
	@Override
	void setSize(int size) {
	}

	@Override
	int getSize(){ 
		if(!getIsOngoing()){
			return 0;
		}
		return _message.length(); 
	}

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
