package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.exception.UnknownIdentifierException;
import prr.core.exception.UnknownKeyException;

import pt.tecnico.uilib.menus.Command;

//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		addStringField("key", Message.key());
		//FIXME add command fields
	}
	
	@Override
	protected final void execute() throws UnknownClientKeyException{
		//FIXME implement command
		String key = stringField("key");
		try {
			_display.addLine(_receiver.getClient(key));
			for(Object notification : _receiver.getNotifications(key)){
				_display.addLine(notification);
			}
			
		} catch (UnknownIdentifierException | UnknownKeyException e) {
			throw new UnknownClientKeyException(key);
		}
		_display.display();
	}
}
