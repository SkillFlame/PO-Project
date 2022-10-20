package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.KeyAlreadyExistsException;
import prr.core.exception.UnrecognizedTypeException;
import prr.app.exception.InvalidTerminalKeyException;
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
		boolean done = false;
		while(!done) {
			try {
				done = true;
				_receiver.registerTerminal(stringField("terminalType"), stringField("terminalID"), stringField("clientID"));
			} catch (UnrecognizedTypeException ute) {
				done = false;
			}catch (InvalidKeyException ike) {
				throw new InvalidTerminalKeyException(ike.getKey());
			} catch (KeyAlreadyExistsException kaee) {
				throw new InvalidTerminalKeyException(kaee.getKey());
			}

		}
		
	}
}
