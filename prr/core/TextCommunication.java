package prr.core;

import org.w3c.dom.Text;

/**
 * Specialization of a Communication by Text
 */
public class TextCommunication extends Communication {
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private String _message;

	public TextCommunication(String message){
		_message = message;
	}
	
	@Override
	int getSize(){ 
		if(_isOngoing){
			return 0;
		}
		return _message.length(); 
	}

	@Override
	double computeCost(RatePlan ratePlan) {
		_price = ratePlan.computeCost(getTerminalSender().getOwner(), this);
		return _price;
	}

	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = "TEXT" + "|" + super.toString();
		return output;
	}

	
}
