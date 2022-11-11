package prr.app.lookup;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

	DoShowClientsWithDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
	}


	@Override
	protected final void execute() throws CommandException {
		_display.addAll(_receiver.getClientsWithDebt());
		_display.display();
	}

}
