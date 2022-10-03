package prr.app.main;

import prr.core.Network;
import prr.core.NetworkManager;
import pt.tecnico.uilib.menus.Command;

/**
 * Command for opening the menu for terminal management.
 */
class DoOpenMenuTerminal extends Command<NetworkManager> {

  DoOpenMenuTerminal(NetworkManager receiver) {
    super(Label.OPEN_MENU_TERMINALS, receiver);
  }

  @Override
  protected final void execute() {
    (new prr.app.terminals.Menu(_receiver.getNetwork())).open();
  }
}
