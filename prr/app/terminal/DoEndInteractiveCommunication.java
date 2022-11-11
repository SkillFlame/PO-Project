package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.NoOngoingCommunicationException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addIntegerField("duration", Message.duration());
	}
	

	@Override
	protected final void execute() throws CommandException {
		try {
			_network.endInteractiveCommunication(_receiver, integerField("duration"));
			_display.popup(Message.communicationCost(_network.getCommunicationCost(_receiver)));
		} catch (NoOngoingCommunicationException noce) {
			_display.popup(Message.noOngoingCommunication());
		}
		
	}

}
