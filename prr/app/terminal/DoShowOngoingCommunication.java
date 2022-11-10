package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.NoOngoingCommunicationException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

	DoShowOngoingCommunication(Network context, Terminal terminal) {
		super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(_network.showOngoingCommunication(_receiver));
		} catch (NoOngoingCommunicationException noce) {
			_display.popup(Message.noOngoingCommunication());
		}
	}
}
