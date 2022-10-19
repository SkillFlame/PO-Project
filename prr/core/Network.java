package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

	private List<Terminal> _terminals;
	private List<Client> _clients;
	private List<Terminal> _friends;

	// FIXME define contructor(s)
	public Network(){
		_terminals = new ArrayList<>();
		_clients = new ArrayList<>();
		_friends = new ArrayList<>();
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

		_terminals.add(Integer.parseInt(terminalID), terminal);
		return terminal;
	}

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(Integer.parseInt(terminalID)).addFriend(friendID);
	}

	public void registerClient(String name, int taxNumber, String key){
		Client client = new Client(key,taxNumber, name);
		try{
			_clients.add(Integer.parseInt(key),client);
		}
		catch(Exception e){
			
		}	
	}

	public List<String> getSaveContents() {
		List<String> output = new ArrayList<> ();
		for(Client client : _clients) {
			output.add(client.saveToString());
		}
		for(Terminal terminal : _terminals) {
			output.add(terminal.saveToString());
		}
		for(Terminal terminal : _terminals) {
			output.add("FRIENDS|" + terminal.getID() + "|" + terminal.friendsToString());
		}
		return output;
	}

	public void sendTextCommunication(Terminal terminalFrom, String keyTerminalTo, String message){

	}

	public void startInteractiveCommunication(Terminal terminalFrom, String keyTerminalTo, String communicationType){
		
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
