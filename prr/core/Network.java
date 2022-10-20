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
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Terminal> _terminals;
	private Map<String, Client> _clients;

	public Network() {
		_terminals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		_clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}

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

	public Terminal getTerminal(String terminalID) throws UnknownKeyException {
		if (!_terminals.containsKey(terminalID)) {
			throw new UnknownKeyException(terminalID);
		}
		return _terminals.get(terminalID);
	}

	public List<Terminal> getTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			terminals.add(terminal);
		}
		return terminals;
	}

	public List<Terminal> getFreeTerminals() {
		List<Terminal> terminals = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.canStartCommunication()) {
				terminals.add(terminal);
			}
		}
		return terminals;
	}

	public boolean hasTerminal(String terminalID) {
		return _terminals.containsKey(terminalID);
	}

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(terminalID).addFriend(friendID);
	}

	public boolean isValidTerminalType(String terminalType) {
		boolean isValid = false;
		switch (terminalType) {
			case "BASIC", "FANCY" -> isValid = true;
		}

		return isValid;
	}

	public void registerClient(String key, String name, int taxNumber) throws KeyAlreadyExistsException {
		if (_clients.containsKey(key)) {
			throw new KeyAlreadyExistsException(key);
		}
		Client client = new Client(name, taxNumber, key);
		_clients.put(key, client);
	}

	public Client getClient(String id) throws UnknownIdentifierException, UnknownKeyException {
		Client client = _clients.get(id);
		if (client == null) {
			throw new UnknownKeyException(id);
		}
		return client;
	}

	public List<Client> getClients() {
		List<Client> listed = new ArrayList<>(_clients.values());
		return listed;
	}

	public boolean hasClient(String clientID) {
		return _clients.containsKey(clientID);
	}

	public List<Notification> getNotifications(String clientID) {
		Client client = _clients.get(clientID);
		return client.getNotifications();

	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO error while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, FileOpenFailedException,
			UnavailableFileException {
		Parser networkParser = new Parser(this);
		networkParser.parseFile(filename);
	}

}
