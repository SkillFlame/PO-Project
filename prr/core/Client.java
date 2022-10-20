package prr.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Client implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _key;
	private String _name;
	private int _taxNumber;
	private int _payments;
	private int _debts;
	private ClientLevel _level;
	private Notifications _activity;

	enum ClientLevel {
		NORMAL, GOLD, PLATINUM
	};

	enum Notifications {
		YES, NO
	};

	private List<Notification> _notifications;
	private List<String> _terminals;

	public Client(String name, int taxNumber, String key) {
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_level = ClientLevel.NORMAL;
		_notifications = new ArrayList<>();
		_terminals = new ArrayList<>();
		_activity = Notifications.YES;
	}

	public List<Notification> getNotifications() {
		return Collections.unmodifiableList(_notifications);
	}

	public void removeNotifications() {
		_notifications.clear();
	}

	public Notifications getNotificationActivity() {
		return _activity;
	}

	public void activateNotifications() {
		this._activity = Notifications.YES;
	}

	public void deactivateNotifications() {
		this._activity = Notifications.NO;
	}

	public ClientLevel getClientLevel() {
		return _level;
	}

	public void setClientLevel(ClientLevel clientLevel) {
		this._level = clientLevel;
	}

	public void addTerminal(String terminalID) {
		_terminals.add(terminalID);
	}

	public String toString() {
		// CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
		String output = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _level + "|" + _activity + "|"
				+ _terminals.size() + "|" + _payments + "|" + _debts;
		return output;
	}

}
