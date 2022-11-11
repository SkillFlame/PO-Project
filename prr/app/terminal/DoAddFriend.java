package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
		addStringField("terminalID", Message.terminalKey());
	}


	@Override
	protected final void execute() throws CommandException, UnknownTerminalKeyException {
		try {
			_network.addFriend(_receiver, stringField("terminalID"));
		} catch (UnknownKeyException uke) {
			throw new UnknownTerminalKeyException(uke.getKey());
		}
	}

}
