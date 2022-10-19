package prr.app.client;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.exception.UnrecognizedTypeException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

	DoShowAllClients(Network receiver) {
		super(Label.SHOW_ALL_CLIENTS, receiver);
	}


	static String ClientToString(Network network, String client) throws UnrecognizedTypeException{
		return network.
	}

	@Override
	protected final void execute() throws CommandException {
		// FIXME implement command
	}
}
