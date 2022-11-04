package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownIdentifierException;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("communicationID", Message.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_network.performPayment(_receiver, integerField("communicationID"));
		} catch (UnknownIdentifierException uie) {
			_display.popup(Message.invalidCommunication());
		}
	}
}
