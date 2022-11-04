package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import prr.core.exception.InvalidCommunicationException;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.ReceiverIsNotIdleException;
import prr.core.exception.ReceiverIsNotIdleException;
import prr.core.exception.TerminalStateAlreadySetException;
import prr.core.exception.UnknownIdentifierException;

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
	private Client _owner;
	private Collection<String> _friendsId = new HashSet<String>();
	private Map<Integer, Communication> _communicationsMade = new TreeMap<>();
	private Map<Integer, Communication> _communicationsReceived = new TreeMap<>();
	private Communication _lastInteractiveCommunication; //FIXME add implementation

	Terminal(String id, String clientId) throws InvalidKeyException {
		setId(id);
		_clientId = clientId;
		_mode = new IdleMode();
	}

	/**
	 * @param Id of a Terminal
	 * @throws InvalIdKeyException if the given clientId is not valId
	 */
	private void setId(String id) throws InvalidKeyException {
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

	TerminalMode getMode() {
		return _mode;
	}

	Collection<String> getFriends() {
		return _friendsId;
	}

	Client getOwner() {
		return _owner;
	}

	Collection<Communication> getCommunicationsMade(){
		return _communicationsMade.values();
	}

	Collection<Communication> getCommunicationsReceived(){
		return _communicationsReceived.values();
	}

	void addMadeCommunication(Communication communication) {
        _communicationsMade.put(communication.getId(), communication);
    }

	void addReceivedCommunication(Communication communication) {
        _communicationsReceived.put(communication.getId(), communication);
    }

	/**
	 * Adds a Friend to the Friend List
	 */
	void addFriend(String friendId) {
		if (friendId != _id && !_friendsId.contains(friendId)) {
			_friendsId.add(friendId);
		}
	}

	void removeFriend(String friendId) {
		if (_friendsId.contains(friendId)) {
			_friendsId.remove(friendId);
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
		return getMode().canEndCurrentCommunication(this);
	}

	/**
	 * Checks if this terminal can start a new communication.
	 *
	 * @return true if this terminal is neither off nor busy, false otherwise.
	 **/
	public boolean canStartCommunication() {
		return getMode().canStartCommunication();
	}

	public void turnOff() throws TerminalStateAlreadySetException{
		getMode().turnOff(this);
	}

	public void setOnSilent() throws TerminalStateAlreadySetException{
		getMode().setOnSilent(this);
	}

	public void setOnIdle() throws TerminalStateAlreadySetException{
		getMode().setOnIdle(this);
	}

	void makeSMS(Terminal receiver, String Message) {
		addMadeCommunication(getMode().makeSMS(this, receiver, Message));
	}

	void acceptSMS(Terminal sender) {
		addReceivedCommunication(getMode().acceptSMS(sender));
	}

	void makeVoiceCall(Terminal receiver) {
		addMadeCommunication(getMode().makeVoiceCall(this, receiver));
	}

	void acceptVoiceCall(Terminal sender) {
		addReceivedCommunication(getMode().acceptVoiceCall(sender));
	}

	void makeVideoCall(Terminal receiver) {
		addMadeCommunication(getMode().makeVideoCall(this, receiver));
	}

	void acceptVideoCall(Terminal sender) {
	}

	void endOngoingCommunication(int duration) {
		getMode().endOngoingCommunication(duration);
	}


	void pay(int communicationId) throws UnknownIdentifierException{
		if(_communicationsMade.keySet().contains(communicationId)){
			Communication communication = _communicationsMade.get(communicationId);
			if(!communication.getPaymentState()){
				double cost = communication.computeCost(_owner.getRatePlan());
				_payments += cost;
				_debt -= cost;

			}
		}
		// throw new InvalidCommunicationException();
	}

	double getPayments() {
		return _payments;
	}

	double getDebt() {
		return _debt;
	}

	String getClientId() {
		return _clientId;
	}

	public double getTerminalBalance() {
		return getPayments() - getDebt();
	}

	public double getLastInteractiveCommunicationCost() {
		return _lastInteractiveCommunication.getPrice();
	}

	public Communication getOngoingCommunication() throws ReceiverIsNotIdleException {
		if(!canEndCurrentCommunication()) {
			throw new ReceiverIsNotIdleException();
		}
		return _lastInteractiveCommunication;
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
	 * terminalType|terminalId|clientId|terminalStatus|balance-paId|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {

		String output = _id + "|" + _clientId + "|" + _mode + "|" + (int) _payments + "|" + (int) _debt
				+ friendsToString();
		return output;
	}

}
