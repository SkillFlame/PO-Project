package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.TerminalStateAlreadySetException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
	}


	@Override
	protected final void execute() throws CommandException {
		try{
			_network.turnOff(_receiver);
		}
		catch(TerminalStateAlreadySetException tsae){
			_display.popup(Message.alreadyOff());
		}
	}

}
