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
	private TerminalMode _mode;

	private String _clientId;
	private Collection<String> _friendsId = new HashSet<String>();

	Terminal(String id, String clientId) throws InvalidKeyException {
		setID(id);
		_clientId = clientId;
		_mode = new IdleMode();
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

	void setMode(TerminalMode mode) {
		_mode = mode;
	}

	/**
	 * Adds a Friend to the Friend List
	 */
	void addFriend(String friendId) {
		_friendsId.add(friendId);
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

	void makeSMS(Terminal receiver, String Message) {

	}

	void acceptSMS(Terminal sender) {

	}

	void MakeVoiceCall(Terminal receiver) {

	}

	void acceptVoiceCall(Terminal sender) {

	}

	void endOngoingCommunication(int size) {
		
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
