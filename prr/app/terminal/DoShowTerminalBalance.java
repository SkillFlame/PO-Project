package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

	DoShowTerminalBalance(Network context, Terminal terminal) {
		super(Label.SHOW_BALANCE, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(Message.terminalPaymentsAndDebts(_network.getTerminalId(_receiver), 
			_network.getTerminalPayments(_receiver), _network.getTerminalDebt(_receiver)));
	}
}
