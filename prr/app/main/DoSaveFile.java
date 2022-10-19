package prr.app.main;

import prr.core.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
		try {
			if (_receiver.getFilename().equals("")) {
				_receiver.saveAs(Form.requestString(Message.newSaveAs()));
			} else {
				_receiver.save();
			}
		} catch () {

		}
	}
}
