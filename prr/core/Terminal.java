package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import prr.core.exception.InvalidKeyException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	private TerminalMode _mode;

	enum TerminalMode {
		BUSY, IDLE, SILENCE, OFF
	};

	private String _clientID;
	private Collection<String> _friendsID = new HashSet<String>();

	public Terminal(String id, String clientID) throws InvalidKeyException {
		setID(id);
		_clientID = clientID;
		_mode = TerminalMode.IDLE;
	}

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

	public String getID() {
		return _id;
	}

	public void addFriend(String friendID) {
		_friendsID.add(friendID);
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
	 * @return true if this terminal is neither off neither busy, false otherwise.
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

	public boolean isOn() {
		return _mode != TerminalMode.OFF;
	}

	public String friendsToString() {
		if (_friendsID.isEmpty()) {
			return "";
		}

		String output = "";
		for (String friendID : _friendsID) {
			output += friendID + ", ";
		}
		return output;
	}

	public String toString() {
		// terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
		String output = _id + "|" + _clientID + "|" + _mode + "|" + (int) _payments + "|" + (int) _debt
				+ friendsToString();
		return output;
	}
}
