package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
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
	protected final void execute() throws CommandException {
		_network.addFriend(_receiver, stringField("terminalID"));
	}
}
