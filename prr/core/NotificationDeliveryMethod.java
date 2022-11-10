package prr.core;

import java.io.Serializable;

/* Notification Delivery Method Class that implements 
	Notification Delivery Strategy Interface */
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

	@Override
	public void update(String type) {
		_type = type;

	}

	@Override
	public String toString() {
		return _type + "|" + _terminalId;
	}

}
