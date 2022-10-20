package prr.core;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Collections;

import prr.app.exception.FileOpenFailedException;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.KeyAlreadyExistsException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnknownIdentifierException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnrecognizedTypeException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Terminal> _terminals;
	private Map<String, Client> _clients;
	private Map<Network, Terminal> _friends;

	// FIXME define contructor(s)
	public Network() {
		_terminals = new TreeMap<>();
		_clients = new TreeMap<>();
		_friends = new TreeMap<>();
	}

	// FIXME define methods

	public Terminal registerTerminal(String terminalType, String terminalID, String clientID)
			throws UnrecognizedTypeException, InvalidKeyException, KeyAlreadyExistsException, UnknownKeyException {
		Terminal terminal;

		if (hasTerminal(terminalID)) {
			throw new KeyAlreadyExistsException(terminalID);
		}

		if(!hasClient(clientID)) {
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

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(terminalID).addFriend(friendID);
	}

	public void registerClient(String key, String name, int taxNumber) throws KeyAlreadyExistsException {
		if(_clients.containsKey(key)) {
			throw new KeyAlreadyExistsException(key);
		}
		Client client = new Client(name, taxNumber, key);
		_clients.put(key, client);
	}

	public boolean isValidTerminalType(String terminalType) {
		boolean isValid = false;
		switch (terminalType) {
			case "BASIC", "FACY" -> isValid = true;
		}
		
		return isValid;
	}

	public void sendTextCommunication(Terminal terminalFrom, String keyTerminalTo, String message) {

	}

	public void startInteractiveCommunication(Terminal terminalFrom, String keyTerminalTo, String communicationType) {

	}

	public boolean hasClient(String clientID) {
		return _clients.containsKey(clientID);
	}

	public Terminal getTerminal(String terminalID) throws UnknownKeyException {
		if (!_terminals.containsKey(terminalID)) {
			throw new UnknownKeyException(terminalID);
		}
		return _terminals.get(terminalID);
	}

	public List<Notification> getNotifications(String clientID) {
		Client client = _clients.get(clientID);
		return client.getNotifications();

	}

	public boolean hasTerminal(String terminalID) {
		return _terminals.containsKey(terminalID);
	}

	public List<Terminal> showAllTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			terminals.add(terminal);
		}
		return terminals;
	}

	private static class IdentifierComparator implements Comparator<Client> {
		public int compare(Client client1, Client client2) {
			String id1 = client1.getKey();
			String id2 = client2.getKey();
			return id1.compareToIgnoreCase(id2);
		}
	}

	public List<Client> getClients() {
		List<Client> listed = new ArrayList<>(_clients.values());
		Collections.sort(listed, new IdentifierComparator());
		return listed;

	}

	public Client getClient(String id) throws UnknownIdentifierException, UnknownKeyException {
		Client client = _clients.get(id);
		if (client == null) {
			throw new UnknownKeyException(id);
		}
		return client;
	}


	public List<Terminal> showFreeTerminals() {
		List<Terminal> terminals = new ArrayList<> ();
		for(Terminal terminal : _terminals.values()) {
			if(terminal.isFree()){
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, FileOpenFailedException,
			UnavailableFileException /* FIXME maybe other exceptions */ {
		// FIXME implement method
		// FIXME throw errors
		Parser networkParser = new Parser(this);
		networkParser.parseFile(filename);
	}

}
