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

	/** The Map of Terminals of one Network
	 *  and the Map of Clients of one Network
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
	 *  Registers a Terminal on the current Network
	 * @param terminalType either "FANCY" or "BASIC" terminal type
	 * @param terminalID id of a terminal
	 * @param clientID id of a client
	 * 
	 * @throws UnrecognizedTypeException if the given entry type is not recognized
	 * 
	 * @throws InvalidKeyException 	     if the given clientID is not valid
	 * 
	 * @throws KeyAlreadyExistsException if the given clientID is a duplicate of an existing one
	 * 
	 * @throws UnknownKeyException       if the given clientID is not recognized
	 */
	public Terminal registerTerminal(String terminalType, String terminalID, String clientID)
			throws UnrecognizedTypeException, InvalidKeyException, KeyAlreadyExistsException, UnknownKeyException {
		Terminal terminal;

		if (hasTerminal(terminalID)) {
			throw new KeyAlreadyExistsException(terminalID);
		}

		if (!hasClient(clientID)) {
			throw new UnknownKeyException(clientID);
		}

		switch (terminalType) {
			case "BASIC" -> terminal = new BasicTerminal(terminalID, clientID);
			case "FANCY" -> terminal = new FancyTerminal(terminalID, clientID);
			default -> throw new UnrecognizedTypeException();
		}

		_clients.get(clientID).addTerminal(terminalID);
		_terminals.put(terminalID, terminal);
		return terminal;
	}

	/** 
	 *	Checks if a Terminal with a certain ID is contained in the Terminal Map
	 * @param terminalID id of a terminal
	 * @return true if the terminal with the desired id exists
	 */
	public boolean hasTerminal(String terminalID) {
		return _terminals.containsKey(terminalID);
	}

	/** 
	 * 	Checks if a Terminal Type is valid ("BASIC" or "FANCY")
	 * @param terminalType type of a terminal
	 * @return true if the given type is a valid terminal type
	 */
	public boolean isValidTerminalType(String terminalType) {
		boolean isValid = false;
		switch (terminalType) {
			case "BASIC", "FANCY" -> isValid = true;
		}

		return isValid;
	}

	/** 
	 *	Gets the Terminal with the desired ID from the terminal Map
	 * @param terminalID id of the desired terminal
	 * 
	 * @throws UnknownKeyException if the given clientID is not recognized
	 */
	public Terminal getTerminal(String terminalID) throws UnknownKeyException {
		if (!_terminals.containsKey(terminalID)) {
			throw new UnknownKeyException(terminalID);
		}
		return _terminals.get(terminalID);
	}

	/** 
	 *	Gets the List of Terminals from the Terminal Map by its IDs
	 */
	public List<Terminal> getTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			terminals.add(terminal);
		}
		return terminals;
	}

	/** 
	 * Gets the UnusedTerminals from the Terminal Map by its IDs
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

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(terminalID).addFriend(friendID);
	}

	public void removeFriend(String terminalID, String friendID){
		_terminals.get(terminalID).removeFriend(friendID);
	}

	public List<Terminal> getPositiveBalanceTerminals(){
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()){
			if (terminal.getPayments() - terminal.getDebt() > 0){
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	public void setOnIdle(Terminal terminal) throws TerminalStateAlreadySetException{
		terminal.setOnIdle();
	}
	
	public void turnOff(Terminal terminal) throws TerminalStateAlreadySetException{
		terminal.turnOff();
	}

	public void setOnSilent(Terminal terminal) throws TerminalStateAlreadySetException{
		terminal.setOnSilent();
	}

	public String getTerminalId(Terminal terminal){
		return terminal.getId();
	}

	// CLIENTS
	
	/** 
	 * 	Registers a Client on the current Network
	 * @param clientID clientID of a client
	 * @param clientName name of a client
	 * @param taxNumber tax number of a client
	 * 
	 * @throws KeyAlreadyExistsException if the given clientID is a duplicate of an existing one
	 */
	public void registerClient(String clientID, String name, int taxNumber) throws KeyAlreadyExistsException {
		if (hasClient(clientID)) {
			throw new KeyAlreadyExistsException(clientID);
		}
		Client client = new Client(name, taxNumber, clientID);
		_clients.put(clientID, client);
	}

	/** 
	 * 	Checks if a Client with a certain ID is contained in the Client Map
	 * @param clientID id of a Client
	 * @return true if the client with the desired id exists
	 */
	public boolean hasClient(String clientID) {
		return _clients.containsKey(clientID);
	}
	
	/** 
	 * 	Gets the Client from the Client Map by its ID
	 * @param clientID id of a client
	 * 
	 * @throws UnknownIdentifierException if the given id is not contained in the Collection
	 * 
	 * @throws UnknownKeyException 		  if the given clientID is not recognized
	 */
	public Client getClient(String clientID) throws UnknownIdentifierException, UnknownKeyException {
		Client client = _clients.get(clientID);
		if (client == null) {
			throw new UnknownKeyException(clientID);
		}
		return client;
	}
	
	/** 
	 *	Gets the List of Clients from the Client Map by its IDs
	 */
	public List<Client> getClients() {
		List<Client> listed = new ArrayList<>(_clients.values());
		return listed;
	}


	// CLIENT NOTIFICATIONS

	public boolean isClientNotificationsActive(String clientId){
		return _clients.get(clientId).getNotificationActivity();
	}

	public void activateClientNotifications(String clientId) throws NotificationsAlreadyEnabledException{
		if(isClientNotificationsActive(clientId)){
			throw new NotificationsAlreadyEnabledException();
		}
		_clients.get(clientId).activateNotifications();
	}

	public void deactivateClientNotifications(String clientId) throws NotificationsAlreadyDisabledException{
		if(!isClientNotificationsActive(clientId)){
			throw new NotificationsAlreadyDisabledException();
		}
		_clients.get(clientId).deactivateNotifications();
	}

	/** 
	 * 	Gets the Notifications of a Client from the Client Map by its ID
	 * @param clientID id of a client
	 */
	public List<Notification> getNotifications(String clientID) {
		Client client = _clients.get(clientID);
		return client.getNotifications();
	}


	// PAYMENTS AND DEBTS

	public long getGlobalPayments(){
		long payments = 0;
		for(Client client : _clients.values()){
			payments += client.getClientPayments();
		}
		return payments;
	}

	public long getGlobalDebts(){
		long debts = 0;
		for(Client client : _clients.values()){
			debts += client.getClientDebt();
		}
		return debts;
	}

	public long getClientPayments(String clientId) throws UnknownKeyException{
		if(!hasClient(clientId)){
			throw new UnknownKeyException(clientId);
		}
		return (long) _clients.get(clientId).getClientPayments();
	}

	public long getClientDebt(String clientId) throws UnknownKeyException{
		if(!hasClient(clientId)){
			throw new UnknownKeyException(clientId);
		}
		return (long) _clients.get(clientId).getClientDebt();
	}


	public double getClientBalance(String clientId){
		Client client = _clients.get(clientId);
		return client.getBalance();
		
	}

	public List<Client> getClientsWithoutDebt(){
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for(Client client : clientsWithoutDebt){
			if(client.getClientDebt() == 0){
				clientsWithoutDebt.remove(client);
			}
		}
		return clientsWithoutDebt;
	}

	public List<Client> getClientsWithDebt(){
		List<Client> clientsWithDebt = new ArrayList<>(_clients.values());
		for(Client client : clientsWithDebt){
			if(client.getClientDebt() > 0){
				clientsWithDebt.remove(client);
			}
		}
		return clientsWithDebt;
	}

	public long getTerminalPayments(Terminal terminal){
		return (long) terminal.getPayments();
	}

	public long getTerminalDebt(Terminal terminal){
		return (long) terminal.getDebt();
	}


	// COMMUNICATIONS

	public List<Communication> getCommunications(){
		List<Communication> communications = new ArrayList<>();
		for(Terminal terminal : _terminals.values()){
			communications.addAll(terminal.getCommunicationsMade());
			communications.addAll(terminal.getCommunicationsReceived());
		}
		return communications;
	}

	public List<Communication> getCommunicationsMadeByClient(String clientId){
		List<Communication> madeCommunications = new ArrayList<>();
		for(Terminal terminal : _terminals.values()){
			madeCommunications.addAll(terminal.getCommunicationsMade());
		}
		return madeCommunications;
	}

	public List<Communication> getCommunicationsRecievedByClient(String clientId){
		List<Communication> receivedCommunications = new ArrayList<>();
		for(Terminal terminal : _terminals.values()){
			receivedCommunications.addAll(terminal.getCommunicationsReceived());
		}
		return receivedCommunications;
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
