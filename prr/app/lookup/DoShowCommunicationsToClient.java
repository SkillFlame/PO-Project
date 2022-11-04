package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

	DoShowCommunicationsToClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
		addStringField("clientkey", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException, UnknownClientKeyException{
		try{
			_display.addAll(_receiver.getCommunicationsRecievedByClient(stringField("clientKey")));
			_display.display();
		}
		catch(UnknownKeyException uke){
			throw new UnknownClientKeyException(uke.getKey());
		}
	}
}
