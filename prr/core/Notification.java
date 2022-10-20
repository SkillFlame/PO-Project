package prr.core;

import java.io.Serializable;

public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private NotificationType _type;
	private String _terminalId;

	enum NotificationType {
		O2S, O2I, B2S, B2I
	};

	public Notification(String id) {
		_type = null;
		_terminalId = id;
	}

	public String toString() {
		// notificationType | idTerminal
		return _type + "|" + _terminalId;
	}
}
