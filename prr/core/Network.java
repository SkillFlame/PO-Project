package prr.core;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import prr.core.exception.UnavailableFileException;
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
	public Network(){
		_terminals = new TreeMap<>();
		_clients = new TreeMap<>();
		_friends = new TreeMap<>();
	}

	// FIXME define methods

	public Terminal registerTerminal(String type, String terminalID, String clientID) throws UnrecognizedTypeException {
		Terminal terminal;
		switch (type) {
			case "BASIC" -> terminal = new BasicTerminal(terminalID, clientID);
			case "FANCY" -> terminal = new FancyTerminal(terminalID, clientID);
			default -> throw new UnrecognizedTypeException();
			// FIXME finish exception
		}

		_terminals.put(terminalID, terminal);
		return terminal;
	}

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(terminalID).addFriend(friendID);
	}

	public void registerClient(String name, int taxNumber, String key){
		Client client = new Client(key,taxNumber, name);
		try{
			_clients.put(key,client);
		}
		catch(Exception e){
			
		}	
	}

	public void sendTextCommunication(Terminal terminalFrom, String keyTerminalTo, String message){

	}

	public void startInteractiveCommunication(Terminal terminalFrom, String keyTerminalTo, String communicationType){
		
	}

	public boolean hasClient(String clientId){
		return _clients.containsKey(clientId);
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
