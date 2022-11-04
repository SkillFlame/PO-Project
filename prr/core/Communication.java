package prr.core;

import java.io.Serializable;

/**
 * Communication implementation
 */
public abstract class Communication implements Serializable{
	
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private static int _Id;
	private boolean _isPaId;
	private double _price;
	private boolean _isOngoing;
	private Terminal _sender;
	private Terminal _receiver;

	abstract double computeCost(RatePlan ratePlan);
	abstract int getSize();
	
	public Communication(Terminal sender, Terminal receiver){
		_Id += 1;
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
		return _Id;
	}

	double getPrice(){
		return _price;
	}

	boolean getPaymentState(){
		return _isPaId;
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
	 * type|IdCommunication|IdSender|IdReceiver|units|price|status
	 */
	@Override 
	public String toString() {
		String output = _Id + "|" + _sender.getId() + "|" + _receiver.getId() + "|" + getSize() + "|" + _price + "|" + getStatus();
		return output;
	}
}