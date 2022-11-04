package prr.app.client;

import prr.core.Network;
import prr.core.exception.NotificationsAlreadyEnabledException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

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
			_receiver.activateClientNotifications("clientKey");
		}
		catch(NotificationsAlreadyEnabledException naee){
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		}
	}
}
