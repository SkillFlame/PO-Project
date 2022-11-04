package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

	DoShowCommunicationsFromClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
		addStringField("clientKey", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException, UnknownClientKeyException{
		try{
			_display.addAll(_receiver.getCommunicationsMadeByClient(stringField("clientKey")));
			_display.display();
		}
		catch(UnknownKeyException uke){
			throw new UnknownClientKeyException(uke.getKey());
		}

	}
}
