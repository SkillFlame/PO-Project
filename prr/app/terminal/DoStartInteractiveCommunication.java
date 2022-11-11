package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.ReceiverTerminalDoesNotSupportCommunicationException;
import prr.core.exception.SenderTerminalDoesNotSupportCommunicationException;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("receiverID", Message.terminalKey());
		addOptionField("communicationType", Message.commType(), "VIDEO", "VOICE");
	}


	@Override
	protected final void execute() throws CommandException, UnknownTerminalKeyException {
		try {
			_network.startInteractiveCommunication(_receiver, stringField("receiverID"), optionField("communicationType"));
		} catch (UnknownKeyException uke) {
			throw new UnknownTerminalKeyException(uke.getKey());
		} catch (SenderTerminalDoesNotSupportCommunicationException stdnsce) {
			_display.popup(Message.unsupportedAtOrigin(stdnsce.getId(), stdnsce.getType()));
		} catch (ReceiverTerminalDoesNotSupportCommunicationException rtdnsce) {
			_display.popup(Message.unsupportedAtDestination(rtdnsce.getId(), rtdnsce.getType()));
		} catch (ReceiverIsBusyException ribe) {
			_display.popup(Message.destinationIsBusy(stringField("receiverID")));
		} catch (ReceiverIsOffException rioe) {
			_display.popup(Message.destinationIsOff(stringField("receiverID")));
		} catch (ReceiverIsSilentException rise) {
			_display.popup(Message.destinationIsSilent(stringField("receiverID")));
		}
	}

}
