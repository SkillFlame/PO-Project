package prr.app.client;

import prr.core.Network;
import prr.core.exception.KeyAlreadyExistsException;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("key", Message.key());
		addStringField("name", Message.name());
		addIntegerField("taxId", Message.taxId());
	}

	@Override
	protected final void execute() throws DuplicateClientKeyException {
		try {
			_receiver.registerClient(stringField("key"), stringField("name"), integerField("taxId"));
		} catch (KeyAlreadyExistsException e) {
			throw new DuplicateClientKeyException(stringField("key"));
		}
	}
}
