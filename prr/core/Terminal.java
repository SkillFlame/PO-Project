package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import prr.core.exception.InvalidKeyException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	private TerminalMode _mode;
	enum TerminalMode{BUSY, IDLE, SILENCE, OFF};

	private String _clientID;
	private Collection<String> _friendsID = new HashSet<String>();

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	public Terminal(String id, String clientID) throws InvalidKeyException{
		_id = id;
		setID(id);
		_mode = TerminalMode.IDLE;
	}

	private void setID(String id) throws InvalidKeyException{
		if(id.length() != 6) {
			throw new InvalidKeyException(id);
		}
		try {
			Integer.parseInt(id);
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
		// FIXME add implementation code
		return true;
	}

	public void turnOff(){
		// FIXME Finish Metod
		_mode = TerminalMode.OFF;
	}
	public void setOnSilent(){
		// FIXME Finish Metod
		_mode = TerminalMode.SILENCE;
	}

	public void setOnIdle(){
		_mode = TerminalMode.IDLE;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void makeVoiceCall(Terminal terminalTo){

	}

	void acceptVoiceCall(Terminal terminalFrom){

	}

	public void makeSMS(Terminal terminalTo, String message){

	}
	
	void acceptSMS(Terminal terminalFrom){
		
	}

	public void endOngoingCommunication(int size){

	}


	public boolean checkIdLenght(String id){
		if(id.length() != 6){
			return false;
		}
		return true;
	}

	public boolean isOn() {
		return _mode != TerminalMode.OFF;
	}

	public boolean isFree() {
		return isOn() && _mode != TerminalMode.BUSY;
	}

	public String friendsToString() {
		String output = "";
		for(String friendID : _friendsID) {
			output += friendID + ", ";
		}
		return output;
	}

	public String toString() {
		//terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
		String output = _id + "|" + _clientID + "|" + _mode + "|" + _payments + "|" + _debt + "|" + friendsToString();
		return output;
	}
}

