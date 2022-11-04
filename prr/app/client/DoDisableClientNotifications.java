package prr.app.client;

import prr.core.Network;
import prr.core.exception.NotificationsAlreadyDisabledException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("clientKey", Message.key());
		
	}
	
	@Override
	protected final void execute() throws CommandException, UnknownClientKeyException {
		try{
			_receiver.deactivateClientNotifications("clientKey");
		}
		catch(NotificationsAlreadyDisabledException nade){
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		}
	}
}
