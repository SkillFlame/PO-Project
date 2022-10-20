package prr.app.client;


import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

	DoShowAllClients(Network receiver) {
		super(Label.SHOW_ALL_CLIENTS, receiver);
	}

	@Override
	protected final void execute(){
		// FIXME implement command
		_display.addAll(_receiver.getClients());
		_display.display();
	}
}
