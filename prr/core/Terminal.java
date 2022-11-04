package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import prr.core.exception.InvalidKeyException;
import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.NoOngoingCommunicationException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.ReceiverTerminalDoesNotSupportCommunicationException;
import prr.core.exception.SenderTerminalDoesNotSupportCommunicationException;
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
	private Communication _lastInteractiveCommunication; // FIXME add implementation

	Terminal(String id, String clientId) throws InvalidKeyException {
		setId(id);
		_clientId = clientId;
		_mode = new IdleMode();
	}

	/** 
	 * Sets the Terminal id while checking if it is valid
	 * 
	 * @param Id of a Terminal
	 * 
	 * @throws InvalIdKeyException if the given id is not valId
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

	Collection<Communication> getCommunicationsMade() {
		return _communicationsMade.values();
	}

	Collection<Communication> getCommunicationsReceived() {
		return _communicationsReceived.values();
	}

	/**
	 * Adds a Communication that was made to the Terminal's made Communication Map
	 * 
	 * @param communication the communication that was made
	 */
	void addMadeCommunication(Communication communication) {
		_communicationsMade.put(communication.getId(), communication);
	}

	/**
	 * Adds a Communication that was received to the Terminal's received Communication Map
	 * 
	 * @param communication the communication that was received
	 */
	void addReceivedCommunication(Communication communication) {
		_communicationsReceived.put(communication.getId(), communication);
	}

	/**
	 * Adds a Friend Terminal to the Friend List
	 * 
	 * @param friendId id of the friend terminal
	 */
	void addFriend(String friendId) {
		if (friendId != _id && !_friendsId.contains(friendId)) {
			_friendsId.add(friendId);
		}
	}

	/**
	 * Removes a Friend Terminal to the Friend List
	 * 
	 * @param friendId id of the friend terminal
	 */
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

	/**
	 * Turns the Terminal to the Off state
	 * 
	 * @throws TerminalStateAlreadySetException if the terminal is already in Off state
	 */
	public void turnOff() throws TerminalStateAlreadySetException {
		getMode().turnOff(this);
	}

	/**
	 * Turns the Terminal to the Silent state
	 * 
	 * @throws TerminalStateAlreadySetException if the terminal is already in Silent state
	 */
	public void setOnSilent() throws TerminalStateAlreadySetException {
		getMode().setOnSilent(this);
	}

	/**
	 * Turns the Terminal to the Idle state
	 * 
	 * @throws TerminalStateAlreadySetException if the terminal is already in Idle state
	 */
	public void setOnIdle() throws TerminalStateAlreadySetException {
		getMode().setOnIdle(this);
	}

	/**
	 * Creates a Text Communication and adds it to the Map of made Communications
	 * 
	 * @param receiver the terminal that receives the communication
	 * @param Message text content of the sent message
	 */
	void makeSMS(Terminal receiver, String Message) {
		addMadeCommunication(getMode().makeSMS(this, receiver, Message));
		receiver.acceptSMS(this);
	}

	/**
	 * Accepts a received Text Communication adding it to the Map of received Communications
	 * 
	 * @param sender the terminal that sends the communication
	 */
	void acceptSMS(Terminal sender) {
		addReceivedCommunication(getMode().acceptSMS(sender));
	}

	/**
	 * Creates a Voice Communication and adds it to the Map of made Communications
	 * 
	 * @param receiver the terminal that receives the communication
	 * 
	 * @throws ReceiverIsBusyException if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state 
	 */
	void makeVoiceCall(Terminal receiver)
			throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		addMadeCommunication(getMode().makeVoiceCall(this, receiver));
		receiver.acceptVoiceCall(this);
	}

	/**
	 * Accepts a received Voice Communication adding it to the Map of received Communications
	 * 
	 * @param sender the terminal that sends the communication
	 */
	void acceptVoiceCall(Terminal sender)
			throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		addReceivedCommunication(getMode().acceptVoiceCall(sender));
	}

	/**
	 * Prepares the process of creating a Video Communication on a Fancy Terminal
	 * 
	 * @param receiver the terminal that receives the communication
	 * 
	 * @throws SenderTerminalDoesNotSupportCommunicationException if the sender terminal does not support a
	 * 															Video Communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver terminal does not support
	 * 															a Video Communication
	 * 
	 * @throws ReceiverIsBusyException if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state
	 */
	void makeVideoCall(Terminal receiver) throws SenderTerminalDoesNotSupportCommunicationException,
			ReceiverTerminalDoesNotSupportCommunicationException, ReceiverIsBusyException, ReceiverIsOffException,
			ReceiverIsSilentException {
		throw new SenderTerminalDoesNotSupportCommunicationException(getId(), "VIDEO");
	}

	/**
	 * Prepares the process of accepting a received Video Communication on a Fancy Terminal
	 * 
	 * @param sender the terminal that sends the communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver terminal does not support
	 * 																a Video Communication
	 * 
	 * @throws ReceiverIsBusyException if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state
	 */
	void acceptVideoCall(Terminal sender) throws ReceiverTerminalDoesNotSupportCommunicationException,
			ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		throw new ReceiverTerminalDoesNotSupportCommunicationException(getId(), "VIDEO");
	}

	/**
	 * Ends the current ongoing Interactive Communication
	 * 
	 * @param duration minutes of duration of the made interactive communication
	 */
	void endOngoingCommunication(int duration) {
		getMode().endOngoingCommunication(duration);
		if(getOwner().getRatePlan().toStringRatePlan() == "GOLD" || 
			getOwner().getRatePlan().toStringRatePlan() == "PLATINUM"){
			getOwner().getRatePlan().demote(_owner);
		}
	}

	/**
	 * Performs the payment of a Communication by its id
	 * 		promotes the Terminal's owner if it meets the conditions
	 * 
	 * @param communicationId id of the desired communication
	 * 
	 * @throws UnknownIdentifierException if the given communicationId is not recognized
	 */
	void pay(int communicationId) throws UnknownIdentifierException {
		if (_communicationsMade.keySet().contains(communicationId)) {
			Communication communication = _communicationsMade.get(communicationId);
			if (!communication.getPaymentState()) {
				double cost = communication.computeCost(_owner.getRatePlan());
				_payments += cost;
				_debt -= cost;
			}
		}
		if(getOwner().getRatePlan().toStringRatePlan() == "NORMAL"){
			getOwner().getRatePlan().promote(_owner);
		}
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

	/**
	 * Gets the cost of the last Interactive Communication made
	 */
	public double getLastInteractiveCommunicationCost() {
		return _lastInteractiveCommunication.getPrice();
	}

	/**
	 * Gets the current ongoing Communication
	 * 
	 * @throws NoOngoingCommunicationException if there is no ongoing communication
	 */
	public Communication getOngoingCommunication() throws NoOngoingCommunicationException {
		if (!canEndCurrentCommunication()) {
			throw new NoOngoingCommunicationException();
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
