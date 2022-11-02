package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

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
	private Map<Integer, Communication> _communications = new TreeMap<>();

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

	TerminalMode getMode(){
		return _mode;
	}

	Collection<String> getFriends(){
		return _friendsId;
	}

	Collection<Communication> getCommunications(){
		return _communications.values();
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
		return _mode != OffMode.getMode() && _mode != BusyMode.getMode();
	}

	public void turnOff() {
		// FIXME Finish Method
		_mode = OffMode.getMode();
	}

	public void setOnSilent() {
		// FIXME Finish Method
		_mode = SilenceMode.getMode();
	}

	public void setOnIdle() {
		// FIXME Finish Method
		_mode = IdleMode.getMode();
	}

	void makeSMS(Terminal receiver, String Message) {
		// FIXME Finish Method
	}

	void acceptSMS(Terminal sender) {
		// FIXME Finish Method
	}

	void MakeVoiceCall(Terminal receiver) {
		// FIXME Finish Method
	}

	void acceptVoiceCall(Terminal sender) {
		// FIXME Finish Method
	}

	void endOngoingCommunication(int size) {
		// FIXME Finish Method
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


	void pay(int communicationId){
		if(_communications.keySet().contains(communicationId)){
			
		}
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

	public double getTerminalBalance(){
		return getPayments() - getDebt();
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
