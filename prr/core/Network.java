package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private List<Terminal> _terminals = new ArrayList<>();

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	public Terminal registerTerminal(String type, String terminalID, String clientID) {
		Terminal terminal = new type(terminalID, clientID);
		
		_terminals.add(Integer.parseInt(terminalID), terminal);
		return terminal;
	}

	public void addFriend(String terminalID, String friendID) {
		_terminals.get(Integer.parseInt(terminalID)).addFriend(friendID);
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
		// FIXME implement method
	}

}
