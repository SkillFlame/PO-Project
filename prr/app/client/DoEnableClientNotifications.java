package prr.app.client;

import prr.core.Network;
import prr.core.exception.NotificationsAlreadyEnabledException;
import prr.core.exception.UnknownKeyException;

import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("clientKey", Message.key());
	}
	
	@Override
	protected final void execute() throws CommandException, UnknownClientKeyException {
		try{
			_receiver.activateClientNotifications(stringField("clientKey"));
		}
		catch(NotificationsAlreadyEnabledException naee){
			_display.popup(Message.clientNotificationsAlreadyEnabled());
		}
		catch(UnknownKeyException uke){
			throw new UnknownClientKeyException(uke.getKey());
		}
	}
}
