package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

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
			_display.popup(Message.clientPaymentsAndDebts("clientKey", 
			_receiver.getClientPayments("clientKey"), _receiver.getClientDebt("clientKey")));
		}
		catch(UnknownKeyException uie){
			throw new UnknownClientKeyException("clientKey");
		}
	}
}
