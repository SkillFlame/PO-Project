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

	
	/** 
	 *	Checks if a Terminal with a certain ID is contained in the Terminal Map
	 * @param terminalID id of a terminal
	 * @return true if the terminal with the desired id exists
	 */
	public boolean hasTerminal(String terminalID) {
		return _terminals.containsKey(terminalID);
	}

	public boolean hasFriend(String friendID) {
		for(Terminal terminal : _terminals.values()){
			if(terminal.getFriends().contains(friendID)){
				return true;
			}
		}
		return false;
	}

	/** 
	 * 	Adds a Friend Terminal to the selected Terminal
	 * @param terminalID id of a terminal
	 * @param friendID id of the friend terminal to be added
	 */
	public void addFriend(String terminalID, String friendID) {
		if(!hasFriend(friendID)){
			_terminals.get(terminalID).addFriend(friendID);
		}
	}

	public void removeFriend(String terminalID, String friendID){
		_terminals.get(terminalID).removeFriend(friendID);
	}

	public void pay(int communicationId){
		//FIXME finish method
	}

	public double getTerminalBalance(Terminal terminal){
		return terminal.getPayments() - terminal.getDebt();
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
	 * 	Registers a Client on the current Network
	 * @param clientID clientID of a client
	 * @param clientName name of a client
	 * @param taxNumber tax number of a client
	 * 
	 * @throws KeyAlreadyExistsException if the given clientID is a duplicate of an existing one
	 */
	public void registerClient(String clientID, String name, int taxNumber) throws KeyAlreadyExistsException {
		if (_clients.containsKey(clientID)) {
			throw new KeyAlreadyExistsException(clientID);
		}
		Client client = new Client(name, taxNumber, clientID);
		_clients.put(clientID, client);
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

	
	/** 
	 * 	Checks if a Client with a certain ID is contained in the Client Map
	 * @param clientID id of a Client
	 * @return true if the client with the desired id exists
	 */
	public boolean hasClient(String clientID) {
		return _clients.containsKey(clientID);
	}

	
	/** 
	 * 	Gets the Notifications of a Client from the Client Map by its ID
	 * @param clientID id of a client
	 */
	public List<Notification> getNotifications(String clientID) {
		Client client = _clients.get(clientID);
		return client.getNotifications();

	}


	public int getGlobalPaymentsAndDebts(){
		int balance = 0;
		for(Client client : _clients.values()){
			balance += client.getClientBalance();
		}
		return balance;
	}

	public void activateFailedContactReception(String clientId){
		// FIXME add implementation code
	}

	public void deactivateFailedContactReception(String clientId){
		// FIXME add implementation code
	}

	// public List<Communication> getCommunications(){
		// FIXME add implementation code
	// }

	public List<Communication> getCommunicationsMadeByClient(String clientId){
		List<Communication> madeCommunications = new ArrayList<>();
		// FIXME add implementation code
		return madeCommunications;
	}

	public List<Communication> getCommunicationsRecievedByClient(String clientId){
		List<Communication> recievedCommunications = new ArrayList<>();
		// FIXME add implementation code
		return recievedCommunications;
	}

	public List<Client> getClientsWithoutDebt(){
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for(Client client: clientsWithoutDebt){
			if(client.getClientDebts() != 0){
				clientsWithoutDebt.remove(client);
			}
		}
		return clientsWithoutDebt;
	}

	public List<Client> getClientsWithDebt(){
		List<Client> clientsWithoutDebt = new ArrayList<>(_clients.values());
		for(Client client: clientsWithoutDebt){
			if(client.getClientDebts() == 0){
				clientsWithoutDebt.remove(client);
			}
			// FIXME add implementation code
		}
		return clientsWithoutDebt;
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

	public void setTerminalOnIdle(Terminal terminal){
		terminal.setOnIdle();
	}

	public void turnTerminalOff(Terminal terminal){
		terminal.turnOff();
	}

	public void silenceTerminal(Terminal terminal){
		terminal.setOnSilent();
	}

	public void sendTextCommunication(Terminal terminalTo, String message){
		// FIXME add implementation code

	}

	public void startInteractiveCommunication(Terminal selectedTerminal, String communicationType){
		// FIXME add implementation code
	}


	public void endInteractiveCommunication(Terminal selectedTerminal){
		// FIXME add implementation code
	}

	public void getOngoingCommunication(Terminal selectedTerminal){
		// FIXME add implementation code
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
