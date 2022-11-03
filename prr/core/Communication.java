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
	private double _price;
	private boolean _isOngoing;
	private Terminal _sender;
	private Terminal _receiver;

	abstract double computeCost(RatePlan ratePlan);
	abstract int getSize();
	
	public Communication(Terminal sender, Terminal receiver){
		_id += 1;
		_sender = sender;
		_receiver = receiver;
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

	double getPrice(){
		return _price;
	}

	boolean getPaymentState(){
		return _isPaid;
	}

	void setPrice(double price) {
		_price = price;
	}

	boolean getIsOngoing() {
		return _isOngoing;
	}

	Terminal getTerminalSender(){
		return _sender;
	}

	Terminal getTerminalReceiver(){
		return _receiver;
	}


	/**
	 * toString implementation of a Communication
	 * type|idCommunication|idSender|idReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = _id + "|" + _sender.getId() + "|" + _receiver.getId() + "|" + getSize() + "|" + _price + "|" + getStatus();
		return output;
	}
}