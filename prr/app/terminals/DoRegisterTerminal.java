package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.UnrecognizedTypeException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalID", Message.terminalKey());
		addStringField("terminalType", Message.terminalType());
		addStringField("clientID", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.registerTerminal(stringField("terminalType"), stringField("terminalID"), stringField("clientID"));
		} catch (UnrecognizedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
