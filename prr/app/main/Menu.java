package prr.app.main;

import prr.core.NetworkManager;
import pt.tecnico.uilib.menus.DoOpenMenu;

/**
 * Main menu.
 */
public final class Menu extends pt.tecnico.uilib.menus.Menu {
  public Menu(NetworkManager receiver) {
    super(Label.TITLE, //
          new DoOpenFile(receiver),
          new DoSaveFile(receiver),
          new DoOpenMenuClient(receiver),
          new DoOpenMenuTerminal(receiver),
          new DoOpenMenuLookups(receiver),
          new DoShowGlobalBalance(receiver.getNetwork())//
          );
  }
}
