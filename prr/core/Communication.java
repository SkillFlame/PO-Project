package prr.core;

import java.io.Serializable;

/**
 * Communication implementation
 */
public abstract class Communication implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private static int _id;
	boolean _isPaid;
	private double _price;
	boolean _isOngoing;
	Terminal terminalFrom;
	Terminal terminalTo;

	abstract double computeCost(RatePlan ratePlan);
	abstract int getSize();
	

	String getStatus(){
		if(_isOngoing){
			return "ONGOING";
		}
		return "FINISHED";
	}

	int getId(){
		return _id;
	}

	void setPriceAtZero(){
		if(_isOngoing){
			_price = 0;
		}
	}


	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = _id + "|" + terminalFrom.getId() + "|" + terminalTo.getId() + "|" + getSize() + "|" + _price + "|" + getStatus();
		return output;
	}
}