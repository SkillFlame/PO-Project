package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.KeyAlreadyExistsException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedTypeException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalID", Message.terminalKey());
		addOptionField("terminalType", Message.terminalType(), "BASIC", "FANCY");
		addStringField("clientID", Message.clientKey());
	}


	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.registerTerminal(optionField("terminalType"), stringField("terminalID"), stringField("clientID"));
		} catch (UnrecognizedTypeException e) {
			System.out.print("");
		} catch (InvalidKeyException ike) {
			throw new InvalidTerminalKeyException(ike.getKey());
		} catch (KeyAlreadyExistsException kaee) {
			throw new DuplicateTerminalKeyException(kaee.getKey());
		} catch (UnknownKeyException e) {
			throw new UnknownClientKeyException(stringField("clientID"));
		}
	}

}
