package prr.core;

import java.io.Serializable;
/* Notification Delivery Method Class, subject of the
	Notification Observer Interface */
public class NotificationDeliveryMethod implements Notification, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Client _client;
	private String _terminalId;
	private String _type;


	NotificationDeliveryMethod(Client client, String terminalId) {
		_client = client;
		_terminalId = terminalId;
	}


	@Override
	public Client getClient() {
		return _client;
	}


	/**
	 * Updates the notification type
	 */
	@Override
	public void update(String type) {
		_type = type;

	}


	/**
	 * toString implementation of a Notification
	 * notification-type|terminalId
	 */
	@Override
	public String toString() {
		return _type + "|" + _terminalId;
	}

}
