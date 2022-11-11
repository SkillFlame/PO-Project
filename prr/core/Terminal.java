package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import prr.core.exception.DuplicateNotificationException;
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
	private Client _client;
	private Communication _lastInteractiveCommunication;
	private Communication _lastCommunicationMade;
	private TerminalMode _lastTerminalMode;

	private Collection<String> _friendsId = new TreeSet<String>();
	private Map<Integer, Communication> _communicationsMade = new TreeMap<>();
	private Map<Integer, Communication> _communicationsReceived = new TreeMap<>();
	private List<Notification> _notifications = new ArrayList<>();

	Terminal(String id, Client client) throws InvalidKeyException {
		setId(id);
		_client = client;
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
		return _client;
	}

	Collection<Communication> getCommunicationsMade() {
		return _communicationsMade.values();
	}

	Collection<Communication> getCommunicationsReceived() {
		return _communicationsReceived.values();
	}

	TerminalMode getLastTerminalMode() {
		return _lastTerminalMode;
	}

	void setLastTerminalMode(TerminalMode lastTerminalMode) {
		_lastTerminalMode = lastTerminalMode;
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
	 * Adds a Communication that was received to the Terminal's received
	 * Communication Map
	 * 
	 * @param communication the communication that was received
	 */
	void addReceivedCommunication(Communication communication) {
		_communicationsReceived.put(communication.getId(), communication);
	}

	void setLastCommunicationMade(Communication communication) {
		_lastCommunicationMade = communication;
	}

	Communication getLastCommunicationMade() {
		return _lastCommunicationMade;
	}

	void setLastInteractiveCommunication(Communication communication) {
		_lastInteractiveCommunication = communication;
	}

	/**
	 * Adds a Friend Terminal to the Friend List
	 * 
	 * @param friendId id of the friend terminal
	 */
	void addFriend(String friendId) {
		if (friendId.compareTo(_id) != 0) {
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
	 * @throws TerminalStateAlreadySetException if the terminal is already in Off
	 *                                          state
	 */
	public void turnOff() throws TerminalStateAlreadySetException {
		getMode().turnOff(this);
	}

	/**
	 * Turns the Terminal to the Silent state
	 * 
	 * @throws TerminalStateAlreadySetException if the terminal is already in Silent
	 *                                          state
	 */
	public void setOnSilent() throws TerminalStateAlreadySetException {
		getMode().setOnSilent(this);
	}

	/**
	 * Turns the Terminal to the Idle state
	 * 
	 * @throws TerminalStateAlreadySetException if the terminal is already in Idle
	 *                                          state
	 */
	public void setOnIdle() throws TerminalStateAlreadySetException {
		getMode().setOnIdle(this);
	}

	/**
	 * Creates a Text Communication and adds it to the Map of made Communications
	 * 
	 * @param receiver the terminal that receives the communication
	 * @param Message  text content of the sent message
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 */
	void makeSMS(Terminal receiver, String Message) throws ReceiverIsOffException {
		Communication communication = getMode().makeSMS(this, receiver, Message);

		setLastCommunicationMade(communication);

		try {
			receiver.acceptSMS(this);
		} catch (ReceiverIsOffException rioe) {
			handleFailedCommunication(receiver);
			throw new ReceiverIsOffException();
		}
		communication.computeCost(getOwner().getRatePlan());
		addMadeCommunication(communication);
		_debt += communication.getPrice();
		getOwner().addDebt(communication.getPrice());
		getOwner().resetVideoCommunicationCounter();
		getOwner().increaseTextCommunicationCounter();
		getOwner().demote();
	}

	/**
	 * Accepts a received Text Communication adding it to the Map of received
	 * Communications
	 * 
	 * @param sender the terminal that sends the communication
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 */
	void acceptSMS(Terminal sender) throws ReceiverIsOffException {
		Communication communication = getMode().acceptSMS(sender);
		addReceivedCommunication(communication);
	}

	/**
	 * Creates a Voice Communication and adds it to the Map of made Communications
	 * 
	 * @param receiver the terminal that receives the communication
	 * 
	 * @throws ReceiverIsBusyException   if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException    if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state
	 */
	void makeVoiceCall(Terminal receiver)
			throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {

		Communication communication = getMode().makeVoiceCall(this, receiver);

		setLastCommunicationMade(communication);
		setLastInteractiveCommunication(communication);
		try {
			receiver.acceptVoiceCall(this);
		} catch (ReceiverIsOffException rioe) {
			handleFailedCommunication(receiver);
			throw new ReceiverIsOffException();
		} catch (ReceiverIsBusyException ribe) {
			handleFailedCommunication(receiver);
			throw new ReceiverIsBusyException();
		} catch (ReceiverIsSilentException rise) {
			handleFailedCommunication(receiver);
			throw new ReceiverIsSilentException();
		}
		addMadeCommunication(communication);
		getOwner().resetVideoCommunicationCounter();
		getOwner().resetTextCommunicationCounter();
	}

	/**
	 * Accepts a received Voice Communication adding it to the Map of received
	 * Communications
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
	 * @throws SenderTerminalDoesNotSupportCommunicationException   if the sender
	 *                                                              terminal does
	 *                                                              not support a
	 *                                                              Video
	 *                                                              Communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver
	 *                                                              terminal does
	 *                                                              not support
	 *                                                              a Video
	 *                                                              Communication
	 * 
	 * @throws ReceiverIsBusyException                              if the receiver
	 *                                                              terminal is in
	 *                                                              Busy state
	 * 
	 * @throws ReceiverIsOffException                               if the receiver
	 *                                                              terminal is in
	 *                                                              Off state
	 * 
	 * @throws ReceiverIsSilentException                            if the receiver
	 *                                                              terminal is in
	 *                                                              Silent state
	 */
	void makeVideoCall(Terminal receiver) throws SenderTerminalDoesNotSupportCommunicationException,
			ReceiverTerminalDoesNotSupportCommunicationException, ReceiverIsBusyException, ReceiverIsOffException,
			ReceiverIsSilentException {
		throw new SenderTerminalDoesNotSupportCommunicationException(getId(), "VIDEO");
	}

	/**
	 * Prepares the process of accepting a received Video Communication on a Fancy
	 * Terminal
	 * 
	 * @param sender the terminal that sends the communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver
	 *                                                              terminal does
	 *                                                              not support
	 *                                                              a Video
	 *                                                              Communication
	 * 
	 * @throws ReceiverIsBusyException                              if the receiver
	 *                                                              terminal is in
	 *                                                              Busy state
	 * 
	 * @throws ReceiverIsOffException                               if the receiver
	 *                                                              terminal is in
	 *                                                              Off state
	 * 
	 * @throws ReceiverIsSilentException                            if the receiver
	 *                                                              terminal is in
	 *                                                              Silent state
	 */
	void acceptVideoCall(Terminal sender) throws ReceiverTerminalDoesNotSupportCommunicationException,
			ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		throw new ReceiverTerminalDoesNotSupportCommunicationException(getId(), "VIDEO");
	}

	/**
	 * Ends the current ongoing Interactive Communication
	 * 
	 * @param duration minutes of duration of the made interactive communication
	 * 
	 * @throws NoOngoingCommunicationException if there is no current ongoing
	 *                                         communication
	 */
	void endOngoingCommunication(int duration) throws NoOngoingCommunicationException {
		Communication communication = getOngoingCommunication();
		communication.setIsOngoing(false);
		communication.setSize(duration);
		communication.computeCost(getOwner().getRatePlan());
		_debt += getOngoingCommunication().getPrice();
		getOwner().addDebt(getOngoingCommunication().getPrice());
		getMode().endOngoingCommunication(this);

		Terminal receiver = communication.getTerminalReceiver();
		receiver.getMode().endOngoingCommunication(receiver);
		getOwner().demote();
		getOwner().promote();
	}

	/**
	 * Handles a failed Communication from the Terminal and adds a Notification
	 * to the terminal that couldn't receive the Communication
	 * 
	 * @param receiver the terminal that receives the communication
	 */
	void handleFailedCommunication(Terminal receiver) {
		receiver.addNotification(new NotificationDeliveryMethod(getOwner(), receiver.getId()), this);
		getMode().handleFailedCommunication(this);
		getLastCommunicationMade().setIsOngoing(false);
		getLastCommunicationMade().decreaseIdCounter();
	}

	/**
	 * Adds a Notification to the Terminal
	 * 
	 * @param notification the notification received
	 * @param sender       the terminal that sent the notification
	 */
	void addNotification(Notification notification, Terminal sender) {
		if (sender.getOwner().isAcceptingNotifications()) {
			_notifications.add(notification);
		}
	}

	/**
	 * Updates the Notifications of the Terminal by sending each one to
	 * the Notification List of the Client that owns the Terminal
	 * then clears the Terminal's Notifications
	 * 
	 * @param type the type of the notification received
	 */
	void updateNotifications(String type) {
		for (Notification notification : _notifications) {
			notification.update(type);
			try {
				notification.getClient().addNotification(notification);
			} catch (DuplicateNotificationException dne) {
				System.out.print("");
			}
		}
		_notifications.clear();
	}

	/**
	 * Performs the payment of a Communication by its id
	 * promotes the Terminal's owner if it meets the conditions
	 * 
	 * @param communicationId id of the desired communication
	 * 
	 * @throws UnknownIdentifierException if the given communicationId is not
	 *                                    recognized
	 */
	void pay(int communicationId) throws UnknownIdentifierException {
		if (!_communicationsMade.keySet().contains(communicationId)) {
			throw new UnknownIdentifierException(communicationId);
		}

		Communication communication = _communicationsMade.get(communicationId);
		communication.pay();

		double cost = communication.getPrice();
		_payments += cost;
		_debt -= cost;
		getOwner().addPayment(cost);
		getOwner().addDebt(-cost);
		getOwner().promote();
	}

	double getPayments() {
		return _payments;
	}

	double getDebt() {
		return _debt;
	}

	String getClientId() {
		return _client.getKey();
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
		if (canStartCommunication()) {
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

		String output = "|";
		Iterator<String> friendId = _friendsId.iterator();
		while (friendId.hasNext()) {
			output += friendId.next();
			if (friendId.hasNext()) {
				output += ",";
			}
		}
		return output;
	}

	/**
	 * toString implementation of a Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paId|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {

		String output = _id + "|" + _client.getKey() + "|" + _mode + "|" + (int) _payments + "|" + (int) _debt
				+ friendsToString();
		return output;
	}

}
