package prr.core;

import java.io.Serializable;

/**
 * Communication implementation
 */
public abstract class Communication implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private static int _id;
	private boolean _isPaid;
	double _price;
	boolean _isOngoing;
	private Terminal _sender;
	private Terminal _reciever;

	abstract double computeCost(RatePlan ratePlan);
	abstract int getSize();
	
	public Communication(){
		_id += 1;
	}

	String getStatus(){
		if(_isOngoing){
			return "ONGOING";
		}
		return "FINISHED";
	}

	int getId(){
		return _id;
	}

	boolean getPaymentState(){
		return _isPaid;
	}

	void setPriceAtZero(){
		if(_isOngoing){
			_price = 0;
		}
	}

	Terminal getTerminalSender(){
		return _sender;
	}

	Terminal getTerminalReciever(){
		return _reciever;
	}


	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = _id + "|" + _sender.getId() + "|" + _reciever.getId() + "|" + getSize() + "|" + _price + "|" + getStatus();
		return output;
	}
}