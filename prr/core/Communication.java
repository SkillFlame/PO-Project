package prr.core;

import java.io.Serializable;

import prr.core.exception.UnknownIdentifierException;

/**
 * Communication implementation
 */
public abstract class Communication implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private static int _idCounter;
	private int _id;
	private boolean _isPaid;
	private boolean _isOngoing;
	private double _price;
	private Terminal _sender;
	private Terminal _receiver;

	abstract double computeCost(RatePlan ratePlan);

	abstract int getSize();

	abstract void setSize(int size);


	public Communication(Terminal sender, Terminal receiver) {
		_idCounter += 1;
		_id = _idCounter;
		_sender = sender;
		_receiver = receiver;
	}


	void decreaseIdCounter() {
		_idCounter -= 1;
	}


	static void setIdCounter(int value) {
		_idCounter = value;
	}


	void setIsOngoing(boolean ongoing) {
		_isOngoing = ongoing;
	}


	/**
	 * Gets the current Communication status
	 */
	String getStatus() {
		if (_isOngoing) {
			return "ONGOING";
		}
		return "FINISHED";
	}


	int getId() {
		return _id;
	}


	double getPrice() {
		return _price;
	}


	boolean getPaymentState() {
		return _isPaid;
	}


	void setPrice(double price) {
		_price = price;
	}


	boolean getIsOngoing() {
		return _isOngoing;
	}


	Terminal getTerminalSender() {
		return _sender;
	}


	Terminal getTerminalReceiver() {
		return _receiver;
	}


	/**
	 * Verifies if a communication payment can be done
	 * 
	 * @throws UnknownIdentifierException if the given id is
	 * 					not a valid communication identifier
	 */
	void pay() throws UnknownIdentifierException {
		if (getPaymentState() || getIsOngoing()) {
			throw new UnknownIdentifierException(getId());
		}
		_isPaid = true;
	}


	/**
	 * toString implementation of a Communication
	 * type|IdCommunication|IdSender|IdReceiver|units|price|status
	 */
	@Override
	public String toString() {
		String output = _id + "|" + _sender.getId() + "|" + _receiver.getId() + "|" + getSize() + "|" + (long) _price
				+ "|" + getStatus();
		return output;
	}

}