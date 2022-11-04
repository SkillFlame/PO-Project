package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
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

	private String _Id;
	private double _debt;
	private double _payments;
	private TerminalMode _mode;

	private String _clientId;
	private Client _owner;
	private Collection<String> _friendsId = new HashSet<String>();
	private Map<Integer, Communication> _communications = new TreeMap<>();
	private Communication _lastInteractiveCommunication; //FIXME add implementation

	Terminal(String Id, String clientId) throws InvalidKeyException {
		setId(Id);
		_clientId = clientId;
		_mode = new IdleMode();
	}

	/**
	 * @param Id of a Terminal
	 * @throws InvalIdKeyException if the given clientId is not valId
	 */
	private void setId(String Id) throws InvalidKeyException {
		if (Id.length() != 6) {
			throw new InvalidKeyException(Id);
		}
		try {
			Integer.parseInt(Id);
			_Id = Id;
		} catch (NumberFormatException nfe) {
			throw new InvalidKeyException(Id);
		}
	}

	String getId() {
		return _Id;
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

	Collection<Communication> getCommunications() {
		return _communications.values();
	}

	/**
	 * Adds a Friend to the Friend List
	 */
	void addFriend(String friendId) {
		if (friendId != _Id && !_friendsId.contains(friendId)) {
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

	public void turnOff() {
		getMode().turnOff(this);
	}

	public void setOnSilent() {
		getMode().setOnSilent(this);
	}

	public void setOnIdle() {
		getMode().setOnIdle(this);
	}

	void makeSMS(Terminal receiver, String Message) {
		addCommunication(getMode().makeSMS(this, receiver, Message));
	}

	void acceptSMS(Terminal sender) {
		getMode().acceptSMS(sender);
	}

	void makeVoiceCall(Terminal receiver) {
		addCommunication(getMode().makeVoiceCall(this, receiver));
	}

	void acceptVoiceCall(Terminal sender) {
		getMode().acceptVoiceCall(sender);
	}

	void makeVideoCall(Terminal receiver) {
		throw new 
	}

	void endOngoingCommunication(int duration) {
		getMode().endOngoingCommunication(duration);
	}

	void addCommunication(Communication communication) {
		_communications.put(communication.getId(), communication);
	}

	void pay(int communicationId) throws UnknownIdentifierException {
		if (_communications.keySet().contains(communicationId)) {
			Communication communication = _communications.get(communicationId);
			if (!communication.getPaymentState()) {
				double cost = communication.computeCost(_owner.getRatePlan());
				_payments += cost;
				_debt -= cost;

			}
		}
		// throw new InvalIdCommunicationException;
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

		String output = _Id + "|" + _clientId + "|" + _mode + "|" + (int) _payments + "|" + (int) _debt
				+ friendsToString();
		return output;
	}

}
