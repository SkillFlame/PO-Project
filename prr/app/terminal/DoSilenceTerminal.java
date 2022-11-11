package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.TerminalStateAlreadySetException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}


	@Override
	protected final void execute() throws CommandException {
		try{
			_network.setOnSilent(_receiver);
		}
		catch(TerminalStateAlreadySetException tsae){
			_display.popup(Message.alreadySilent());
		}
	}

}
