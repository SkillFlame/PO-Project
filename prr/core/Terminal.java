package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import prr.core.exception.InvalidKeyException;

/**
 * Terminal implementation
 */
abstract public class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	TerminalMode _mode;
	
	/** Possible Terminal Modes */
	enum TerminalMode {
		BUSY, IDLE, SILENCE, OFF
	};

	private String _clientId;
	Collection<String> _friendsId = new HashSet<String>();

	Terminal(String id, String clientId) throws InvalidKeyException {
		setID(id);
		_clientId = clientId;
		_mode = TerminalMode.IDLE;
	}

	/**
	 * @param id of a Terminal
	 * @throws InvalidKeyException if the given clientId is not valid
	 */
	private void setID(String id) throws InvalidKeyException {
		if (id.length() != 6) {
			throw new InvalidKeyException(id);
		}
		try {
			Integer.parseInt(id);
			_id = id;
		} catch (NumberFormatException nfe) {
			throw new InvalidKeyException(id);
		}
	}

	String getId() {
		return _id;
	}

	/**
	 * Adds a Friend to the Friend List
	 */
	void addFriend(String friendId) {
		if(friendId != _id){
			_friendsId.add(friendId);
		}
	}

	void removeFriend(String friendID){
		if(_friendsId.contains(friendID)){
			_friendsId.remove(friendID);
		}
	}

	/**
	 * Checks if this terminal can end the current interactive communication.
	 *
	 * @return true if this terminal is busy (i.e., it has an active interactive
	 *         communication) and
	 *         it was the originator of this communication.
	 **/
	public boolean canEndCurrentCommunication() {
		// FIXME add implementation code
		return false;
	}

	/**
	 * Checks if this terminal can start a new communication.
	 *
	 * @return true if this terminal is neither off nor busy, false otherwise.
	 **/
	public boolean canStartCommunication() {
		return isOn() && _mode != TerminalMode.BUSY;
	}

	public void turnOff() {
		// FIXME Finish Method
		_mode = TerminalMode.OFF;
	}

	public void setOnSilent() {
		// FIXME Finish Method
		_mode = TerminalMode.SILENCE;
	}

	public void setOnIdle() {
		// FIXME Finish Method
		_mode = TerminalMode.IDLE;
	}

	/**
	 * Checks if the Terminal is On
	 * 
	 * @return true if Terminal is ON
	 */
	boolean isOn() {
		return _mode != TerminalMode.OFF;
	}

	/**
	 * Conversion of the Terminal's Friends into String
	 */
	public String friendsToString() {
		if (_friendsId.isEmpty()) {
			return "";
		}

		String output = "";
		for (String friendId : _friendsId) {
			output += friendId + ", ";
		}
		return output;
	}


	double getPayments(){
		return _payments;
	}

	double getDebt(){
		return _debt;
	}

	String getClientId(){
		return _clientId;
	}

	abstract public void makeSMS(Terminal terminalTo, String message);
	abstract void acceptSMS(Terminal terminalFrom);
	abstract public void makeVoiceCall(Terminal terminalTo);
	abstract void acceptVoiceCall(Terminal terminalFrom);


	/**
	 * toString implementation of a Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {

		String output = _id + "|" + _clientId + "|" + _mode + "|" + (int) _payments + "|" + (int) _debt
				+ friendsToString();
		return output;
	}
}
