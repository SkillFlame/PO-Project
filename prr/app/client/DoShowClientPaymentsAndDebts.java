package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("clientKey", Message.key());
	}


	@Override
	protected final void execute() throws CommandException, UnknownClientKeyException {
		try{
			_display.popup(Message.clientPaymentsAndDebts(stringField("clientKey"), 
			_receiver.getClientPayments(stringField("clientKey")), _receiver.getClientDebt(stringField("clientKey"))));
		}
		catch(UnknownKeyException uie){
			throw new UnknownClientKeyException(uie.getKey());
		}
	}

}
