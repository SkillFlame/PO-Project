package prr.core;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.KeyAlreadyExistsException;
import prr.core.exception.NotificationsAlreadyDisabledException;
import prr.core.exception.NotificationsAlreadyEnabledException;
import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.NoOngoingCommunicationException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.ReceiverTerminalDoesNotSupportCommunicationException;
import prr.core.exception.SenderTerminalDoesNotSupportCommunicationException;
import prr.core.exception.TerminalStateAlreadySetException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnknownIdentifierException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnrecognizedTypeException;

/**
 * Network class implements a Terminal Network
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/**
	 * The Map of Terminals of one Network
	 * and the Map of Clients of one Network
	 */
	private Map<String, Terminal> _terminals;
	private Map<String, Client> _clients;

	/** Constructor of a Network */
	public Network() {
		_terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		_clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	}

	// TERMINALS

	/**
	 * Registers a Terminal on the current Network
	 * 
	 * @param terminalType either "FANCY" or "BASIC" Terminal type
	 * @param terminalId   id of a terminal
	 * @param clientId     id of a client
	 * 
	 * @throws UnrecognizedTypeException if the given entry type is not recognized
	 * 
	 * @throws InvalIdKeyException       if the given clientId is not valId
	 * 
	 * @throws KeyAlreadyExistsException if the given clientId is a duplicate of an
	 *                                   existing one
	 * 
	 * @throws UnknownKeyException       if the given clientId is not recognized
	 */
	public Terminal registerTerminal(String terminalType, String terminalId, String clientId)
			throws UnrecognizedTypeException, InvalidKeyException, KeyAlreadyExistsException,
			UnknownKeyException {
		Terminal terminal;

		if (hasTerminal(terminalId)) {
			throw new KeyAlreadyExistsException(terminalId);
		}

		if (!hasClient(clientId)) {
			throw new UnknownKeyException(clientId);
		}

		switch (terminalType) {
			case "BASIC" -> terminal = new BasicTerminal(terminalId, _clients.get(clientId));
			case "FANCY" -> terminal = new FancyTerminal(terminalId, _clients.get(clientId));
			default -> throw new UnrecognizedTypeException();
		}

		_clients.get(clientId).addTerminal(terminalId);
		_terminals.put(terminalId, terminal);
		return terminal;
	}

	/**
	 * Checks if a Terminal with a certain id is contained in the Terminal Map
	 * 
	 * @param terminalID id of a terminal
	 * @return true if the terminal with the desired id exists
	 */
	public boolean hasTerminal(String terminalId) {
		return _terminals.containsKey(terminalId);
	}

	/**
	 * Gets the Terminal with the desired id from the Terminal Map
	 * 
	 * @param terminalID id of the desired terminal
	 * 
	 * @throws UnknownKeyException if the given clientId is not recognized
	 */
	public Terminal getTerminal(String terminalId) throws UnknownKeyException {
		if (!_terminals.containsKey(terminalId)) {
			throw new UnknownKeyException(terminalId);
		}
		return _terminals.get(terminalId);
	}

	/**
	 * Gets the List of Terminals from the Terminal Map by its ids
	 */
	public List<Terminal> getTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			terminals.add(terminal);
		}
		return terminals;
	}

	/**
	 * Gets the unused Terminals from the Terminal Map by its ids
	 */
	public List<Terminal> getFreeTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.canStartCommunication()) {
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	/**
	 * Adds a friend to the chosen Terminal's friendlist
	 * 
	 * @param terminalId id of desired terminal
	 * @param friendId   id of desired friend terminal
	 */
	public void addFriend(String terminalId, String friendId) {
		_terminals.get(terminalId).addFriend(friendId);
	}

	/**
	 * Adds friend to the chosen Terminal
	 * 
	 * @param terminal chosen terminal
	 * @param friendId id of desired friend terminal
	 */
	public void addFriend(Terminal terminal, String friendId) {
		terminal.addFriend(friendId);
	}

	/**
	 * Removes a friend from the chosen Terminal's friendlist
	 * 
	 * @param terminalId id of desired terminal
	 * @param friendId   id of desired friend terminal
	 */
	public void removeFriend(String terminalId, String friendId) {
		_terminals.get(terminalId).removeFriend(friendId);
	}

	/**
	 * Removes friend from chosen Terminal
	 * 
	 * @param terminal chosen terminal
	 * @param friendId id of desired friend terminal
	 */
	public void removeFriend(Terminal terminal, String friendId) {
		_terminals.get(terminal.getId()).removeFriend(friendId);
	}

	/**
	 * Gets all Terminals with a positive balance from the Terminal Map
	 */
	public List<Terminal> getPositiveBalanceTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getPayments() - terminal.getDebt() > 0) {
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	/**
	 * Sets a terminal on Idle state
	 * 
	 * @param terminal chosen terminal
	 * 
	 * @throws TerminalStateAlreadySetException if the Idle state is already set
	 */
	public void setOnIdle(Terminal terminal) throws TerminalStateAlreadySetException {
		terminal.setOnIdle();
	}

	/**
	 * Sets a terminal to the Off state
	 * 
	 * @param terminal chosen terminal
	 * 
	 * @throws TerminalStateAlreadySetException if the Off state is already set
	 */
	public void turnOff(Terminal terminal) throws TerminalStateAlreadySetException {
		terminal.turnOff();
	}

	/**
	 * Sets a terminal to the Silent state
	 * 
	 * @param terminal chosen terminal
	 * 
	 * @throws TerminalStateAlreadySetException if the Silent state is already set
	 */
	public void setOnSilent(Terminal terminal) throws TerminalStateAlreadySetException {
		terminal.setOnSilent();
	}

	/**
	 * Gets a Terminal's id
	 * 
	 * @param terminal chosen terminal
	 */
	public String getTerminalId(Terminal terminal) {
		return terminal.getId();
	}

	// CLIENTS

	/**
	 * Registers a Client on the current Network
	 * 
	 * @param clientId   clientId of a client
	 * @param clientName name of a client
	 * @param taxNumber  tax number of a client
	 * 
	 * @throws KeyAlreadyExistsException if the given clientId is a duplicate of an
	 *                                   existing one
	 */
	public void registerClient(String clientId, String name, int taxNumber) throws KeyAlreadyExistsException {
		if (hasClient(clientId)) {
			throw new KeyAlreadyExistsException(clientId);
		}
		Client client = new Client(name, taxNumber, clientId);
		_clients.put(clientId, client);
	}

	/**
	 * Checks if a Client with a certain id is contained in the Client Map
	 * 
	 * @param clientId id of a Client
	 * @return true if the client with the desired id exists
	 */
	public boolean hasClient(String clientId) {
		return _clients.containsKey(clientId);
	}

	/**
	 * Gets the Client from the Client Map by its id
	 * 
	 * @param clientId Id of a client
	 * 
	 * @throws UnknownIdentifierException if the given id is not contained in the
	 *                                    Collection
	 * 
	 * @throws UnknownKeyException        if the given clientId is not recognized
	 */
	public Client getClient(String clientId) throws UnknownIdentifierException, UnknownKeyException {
		Client client = _clients.get(clientId);
		if (client == null) {
			throw new UnknownKeyException(clientId);
		}
		return client;
	}

	/**
	 * Gets the List of Clients from the Client Map by its ids
	 */
	public List<Client> getClients() {
		List<Client> listed = new ArrayList<>(_clients.values());
		return listed;
	}

	// CLIENT NOTIFICATIONS

	/**
	 * Checks if the desired Client in the Client Map has its Notifications active
	 * 
	 * @param clientId id of the desired client
	 * @return true if the client's notifications are active
	 */
	public boolean isClientNotificationsActive(String clientId) {
		return _clients.get(clientId).getNotificationActivity();
	}

	/**
	 * Activates the Client's Notification activity
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws NotificationsAlreadyEnabledException if the client's notifications
	 *                                              are already
	 *                                              enabled
	 * 
	 * @throws UnknownKeyException                  if the given clientId is not
	 *                                              recognized
	 */
	public void activateClientNotifications(String clientId)
			throws NotificationsAlreadyEnabledException, UnknownKeyException {
		if (isClientNotificationsActive(clientId)) {
			throw new NotificationsAlreadyEnabledException();
		}
		_clients.get(clientId).activateNotifications();
	}

	/**
	 * Deactivates the Client's Notifications activity
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws NotificationsAlreadyDisabledException if the client's notifications
	 *                                               are already
	 *                                               disabled
	 * 
	 * @throws UnknownKeyException                   if the given clientId is not
	 *                                               recognized
	 */
	public void deactivateClientNotifications(String clientId)
			throws NotificationsAlreadyDisabledException, UnknownKeyException {
		if (!isClientNotificationsActive(clientId)) {
			throw new NotificationsAlreadyDisabledException();
		}
		_clients.get(clientId).deactivateNotifications();
	}

	/**
	 * Gets the Notifications of a Client from the Client Map by its id
	 * 
	 * @param clientId id of a client
	 */
	public List<Notification> getNotifications(String clientId) {
		Client client = _clients.get(clientId);
		return client.getNotifications();
	}

	/**
	 * 
	 * @return
	 */
	public void clearNotifications(String clientId) {
		_clients.get(clientId).removeNotifications();
	}

	// PAYMENTS AND DEBTS

	/**
	 * Gets the global payments of a Network
	 */
	public long getGlobalPayments() {
		long payments = 0;
		for (Client client : _clients.values()) {
			payments += client.getClientPayments();
		}
		return payments;
	}

	/**
	 * Gets the global debts of a Network
	 */
	public long getGlobalDebts() {
		long debts = 0;
		for (Client client : _clients.values()) {
			debts += client.getClientDebt();
		}
		return debts;
	}

	/**
	 * Gets the chosen Terminal's payments
	 * 
	 * @param terminal the chosen terminal
	 */
	public long getTerminalPayments(Terminal terminal) {
		return (long) terminal.getPayments();
	}

	/**
	 * Gets the chosen Terminal's debt
	 * 
	 * @param terminal the chosen terminal
	 */
	public long getTerminalDebt(Terminal terminal) {
		return (long) terminal.getDebt();
	}

	/**
	 * Gets the desired Client's payments
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws UnknownKeyException if the given clientId is not recognized
	 */
	public long getClientPayments(String clientId) throws UnknownKeyException {
		if (!hasClient(clientId)) {
			throw new UnknownKeyException(clientId);
		}
		return (long) _clients.get(clientId).getClientPayments();
	}

	/**
	 * Gets the desired Client's debt
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws UnknownKeyException if the given clientId is not recognized
	 */
	public long getClientDebt(String clientId) throws UnknownKeyException {
		if (!hasClient(clientId)) {
			throw new UnknownKeyException(clientId);
		}
		return (long) _clients.get(clientId).getClientDebt();
	}

	/**
	 * Gets the balance (payments - debt) of the desired client
	 * 
	 * @param clientId id of the desired client
	 */
	public double getClientBalance(String clientId) {
		Client client = _clients.get(clientId);
		return client.getBalance();

	}

	/**
	 * Gets all the Clients with debt from the Client Map
	 */
	public List<Client> getClientsWithDebt() {
		List<Client> clientsWithDebt = new ArrayList<>(_clients.values());
		for (Client client : clientsWithDebt) {
			if (client.getClientDebt() > 0) {
				clientsWithDebt.remove(client);
			}
		}
		return clientsWithDebt;
	}

	/**
	 * Gets all the Clients without debt from the Client Map
	 */
	public List<Client> getClientsWithoutDebt() {
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for (Client client : clientsWithoutDebt) {
			if (client.getClientDebt() == 0) {
				clientsWithoutDebt.remove(client);
			}
		}
		return clientsWithoutDebt;
	}

	// COMMUNICATIONS

	/**
	 * Gets all the Communications from the Network
	 */
	public List<Communication> getCommunications() {
		List<Communication> communications = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			communications.addAll(terminal.getCommunicationsMade());
			communications.addAll(terminal.getCommunicationsReceived());
		}
		return communications;
	}

	/**
	 * Starts an Interactive Communication from a Terminal
	 * 
	 * @param terminal   the chosen terminal
	 * @param receiverId id of the terminal that recieves the communication
	 * @param type       either "VIDEO" or "VOICE" interactive communication type
	 * 
	 * @throws UnknownKeyException                                  if the given
	 *                                                              receiverId is
	 *                                                              not recognized
	 * 
	 * @throws SenderTerminalDoesNotSupportCommunicationException   if the sender
	 *                                                              terminal does
	 *                                                              not support an
	 *                                                              interactive
	 *                                                              communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver
	 *                                                              terminal
	 *                                                              does not support
	 *                                                              an interactive
	 *                                                              communication
	 * 
	 * @throws ReceiverIsBusyException                              if the receiver
	 *                                                              terminal is on
	 *                                                              Busy mode
	 * 
	 * @throws ReceiverIsOffException                               if the receiver
	 *                                                              terminal is on
	 *                                                              Off mode
	 * 
	 * @throws ReceiverIsSilentException                            if the receiver
	 *                                                              terminal is on
	 *                                                              Silent mode
	 */
	public void startInteractiveCommunication(Terminal terminal, String receiverId, String type)
			throws UnknownKeyException, SenderTerminalDoesNotSupportCommunicationException,
			ReceiverTerminalDoesNotSupportCommunicationException, ReceiverIsBusyException, ReceiverIsOffException,
			ReceiverIsSilentException {

		switch (type) {
			case "VIDEO" -> terminal.makeVideoCall(getTerminal(receiverId));

			case "VOICE" -> terminal.makeVoiceCall(getTerminal(receiverId));
		}
	}

	/**
	 * Ends the current Interactive Communication of a Terminal
	 * 
	 * @param terminal the chosen terminal
	 * @param duration the duration of the interactive communication
	 */
	public void endInteractiveCommunication(Terminal terminal, int duration) throws NoOngoingCommunicationException {
		terminal.endOngoingCommunication(duration);
	}

	/**
	 * Sends a Text Communication to another Terminal
	 * 
	 * @param terminal   the sender terminal
	 * @param receiverId id of the receiver terminal
	 * @param message    content of the sent message
	 * 
	 * @throws UnknownKeyException    if the given receiverId is not recognized
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is on Off mode
	 */
	public void sendTextCommunication(Terminal terminal, String receiverId, String message)
			throws UnknownKeyException, ReceiverIsOffException {
		if (!hasTerminal(receiverId)) {
			throw new UnknownKeyException(receiverId);
		}
		terminal.makeSMS(_terminals.get(receiverId), message);
	}

	/**
	 * Shows the current ongoing InteractiveCommunication of a Terminal
	 * 
	 * @param terminal the chosen terminal
	 * 
	 * @throws NoOngoingCommunicationException if there no ongoing communication on
	 *                                         the chosen terminal
	 */
	public Communication showOngoingCommunication(Terminal terminal) throws NoOngoingCommunicationException {
		return terminal.getOngoingCommunication();
	}

	/**
	 * Gets the cost of a Communication
	 */
	public long getCommunicationCost(Terminal terminal) {
		return (long) terminal.getLastInteractiveCommunicationCost();
	}

	/**
	 * Gets the List of Communications made by a Client
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws UnknownKeyException if if the given clientId is not recognized
	 */
	public List<Communication> getCommunicationsMadeByClient(String clientId) throws UnknownKeyException {
		List<Communication> madeCommunications = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			madeCommunications.addAll(terminal.getCommunicationsMade());
		}
		return madeCommunications;
	}

	/**
	 * Gets the List of Communications received by a Client
	 * 
	 * @param clientId id of the desired client
	 * 
	 * @throws UnknownKeyException if the given clientId is not recognized
	 */
	public List<Communication> getCommunicationsRecievedByClient(String clientId) throws UnknownKeyException {
		List<Communication> receivedCommunications = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			receivedCommunications.addAll(terminal.getCommunicationsReceived());

		}
		return receivedCommunications;
	}

	/**
	 * Performs the payment of a Communication made by a Terminal
	 * 
	 * @param terminal        chosen terminal
	 * @param communicationId id of the communication made
	 * 
	 * @throws UnknownIdentifierException if the given communicationId is not
	 *                                    recognized
	 */
	public void performPayment(Terminal terminal, int communicationId) throws UnknownIdentifierException {
		terminal.pay(communicationId);
	}

	// IMPORT FILE

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * 
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * 
	 * @throws IOException                if there is an IO error while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, FileOpenFailedException,
			UnavailableFileException {
		Parser networkParser = new Parser(this);
		networkParser.parseFile(filename);
	}

}
