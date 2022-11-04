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
import prr.core.exception.ReceiverIsNotIdleException;
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

	/**
	 * Registers a Terminal on the current Network
	 * 
	 * @param terminalType either "FANCY" or "BASIC" terminal type
	 * @param terminalId   Id of a terminal
	 * @param clientId     Id of a client
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
			throws UnrecognizedTypeException, InvalidKeyException, KeyAlreadyExistsException, UnknownKeyException {
		Terminal terminal;

		if (hasTerminal(terminalId)) {
			throw new KeyAlreadyExistsException(terminalId);
		}

		if (!hasClient(clientId)) {
			throw new UnknownKeyException(clientId);
		}

		switch (terminalType) {
			case "BASIC" -> terminal = new BasicTerminal(terminalId, clientId);
			case "FANCY" -> terminal = new FancyTerminal(terminalId, clientId);
			default -> throw new UnrecognizedTypeException();
		}

		_clients.get(clientId).addTerminal(terminalId);
		_terminals.put(terminalId, terminal);
		return terminal;
	}

	/**
	 * Gets the Terminal with the desired Id from the terminal Map
	 * 
	 * @param terminalId Id of the desired terminal
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
	 * Gets the List of Terminals from the Terminal Map by its Ids
	 */
	public List<Terminal> getTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			terminals.add(terminal);
		}
		return terminals;
	}

	/**
	 * Gets the UnusedTerminals from the Terminal Map by its Ids
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

	public boolean hasFriend(String friendId) {
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getFriends().contains(friendId)) {
				return true;
			}
		}
		return false;
	}

	public void addFriend(String terminalId, String friendId) {
		if (!hasFriend(friendId)) {
			_terminals.get(terminalId).addFriend(friendId);
		}
	}

	public void addFriend(Terminal terminal, String friendId) {
		if (!hasFriend(friendId)) {
			_terminals.get(terminal.getId()).addFriend(friendId);
		}
	}

	public void removeFriend(String terminalId, String friendId) {
		_terminals.get(terminalId).removeFriend(friendId);
	}

	public void removeFriend(Terminal terminal, String friendId) {
		_terminals.get(terminal.getId()).removeFriend(friendId);
	}

	/**
	 * Checks if a Terminal with a certain Id is contained in the Terminal Map
	 * 
	 * @param terminalId Id of a terminal
	 * @return true if the terminal with the desired Id exists
	 */
	public boolean hasTerminal(String terminalId) {
		return _terminals.containsKey(terminalId);
	}

	/**
	 * Checks if a Terminal Type is valId ("BASIC" or "FANCY")
	 * 
	 * @param terminalType type of a terminal
	 * @return true if the given type is a valId terminal type
	 */
	public boolean isValIdTerminalType(String terminalType) {
		boolean isValId = false;
		switch (terminalType) {
			case "BASIC", "FANCY" -> isValId = true;
		}

		return isValId;
	}

	public void startInteractiveCommunication(Terminal terminal, String receiverId, String type) throws UnknownKeyException {
		switch (type) {
			case "VIDEO" -> terminal.makeVideoCall(getTerminal(receiverId));
				
			case "VOICE" -> terminal.makeVoiceCall(getTerminal(receiverId));
		}
	}

	public void endInteractiveCommunication(Terminal terminal, int duration) {
		terminal.endOngoingCommunication(duration);
	}

	public void sendTextCommunication(Terminal terminal, String receiverId, String message) throws UnknownKeyException {
		if(!hasTerminal(receiverId)) {
			throw new UnknownKeyException(receiverId);
		}
		terminal.makeSMS(_terminals.get(receiverId), message);
	}

	public Communication showOngoingCommunication(Terminal terminal) throws ReceiverIsNotIdleException {
		return terminal.getOngoingCommunication();
	}

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
		if (_clients.containsKey(clientId)) {
			throw new KeyAlreadyExistsException(clientId);
		}
		Client client = new Client(name, taxNumber, clientId);
		_clients.put(clientId, client);
	}

	/**
	 * Gets the Client from the Client Map by its Id
	 * 
	 * @param clientId Id of a client
	 * 
	 * @throws UnknownIdentifierException if the given Id is not contained in the
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
	 * Gets the List of Clients from the Client Map by its Ids
	 */
	public List<Client> getClients() {
		List<Client> listed = new ArrayList<>(_clients.values());
		return listed;
	}

	/**
	 * Checks if a Client with a certain Id is contained in the Client Map
	 * 
	 * @param clientId Id of a Client
	 * @return true if the client with the desired Id exists
	 */
	public boolean hasClient(String clientId) {
		return _clients.containsKey(clientId);
	}

	/**
	 * Gets the Notifications of a Client from the Client Map by its Id
	 * 
	 * @param clientId Id of a client
	 */
	public List<Notification> getNotifications(String clientId) {
		Client client = _clients.get(clientId);
		return client.getNotifications();

	}

	public int getGlobalPaymentsAndDebts() {
		int balance = 0;
		for (Client client : _clients.values()) {
			balance += client.getClientBalance();
		}
		return balance;
	}

	public double getClientBalance(String clientId) {
		Client client = _clients.get(clientId);
		return client.getClientBalance();

	}

	public void activateNotificationReception(String clientId) throws NotificationsAlreadyEnabledException {
		// FIXME add implementation code
		Client client = _clients.get(clientId);
		if (client.getNotificationActivity()) {
			throw new NotificationsAlreadyEnabledException();
		}
		client.activateNotifications();
	}

	public void deactivateNotificationReception(String clientId) throws NotificationsAlreadyDisabledException {
		// FIXME add implementation code
		Client client = _clients.get(clientId);
		if (!client.getNotificationActivity()) {
			throw new NotificationsAlreadyDisabledException();
		}
		client.deactivateNotifications();
	}

	public List<Communication> getCommunications() {
		List<Communication> communications = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			communications.addAll(terminal.getCommunications());
		}
		return communications;
	}

	public long getCommunicationCost(Terminal terminal) {
		return (long) terminal.getLastInteractiveCommunicationCost();
	}

	public List<Communication> getCommunicationsMadeByClient(String clientId) {
		List<Communication> madeCommunications = new ArrayList<>();
		// FIXME add implementation code
		return madeCommunications;
	}

	public List<Communication> getCommunicationsRecievedByClient(String clientId) {
		List<Communication> recievedCommunications = new ArrayList<>();
		// FIXME add implementation code
		return recievedCommunications;
	}

	public List<Client> getClientsWithoutDebt() {
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for (Client client : clientsWithoutDebt) {
			if (client.getClientDebts() != 0) {
				clientsWithoutDebt.remove(client);
			}
		}
		return clientsWithoutDebt;
	}

	public List<Client> getClientsWithDebt() {
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for (Client client : clientsWithoutDebt) {
			if (client.getClientDebts() == 0) {
				clientsWithoutDebt.remove(client);
			}
			// FIXME add implementation code
		}
		return clientsWithoutDebt;
	}

	public List<Terminal> getPositiveBalanceTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getPayments() - terminal.getDebt() > 0) {
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	public void performPayment(Terminal terminal, int communicationId) throws UnknownIdentifierException {
		terminal.pay(communicationId);
	}

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
