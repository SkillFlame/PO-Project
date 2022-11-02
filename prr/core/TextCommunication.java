package prr.core;

/**
 * Specialization of a Communication by Text
 */
public class TextCommunication extends Communication {
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private String _message;

	@Override
	double computeCost(RatePlan ratePlan) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	int getSize(){ 
		if(_isOngoing){
			return 0;
		}
		return _message.length(); 
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
