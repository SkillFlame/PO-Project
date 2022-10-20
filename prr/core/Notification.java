package prr.core;

public class Notification {
	private NotificationType _type;
	private String _terminalId;
	enum NotificationType{O2S, O2I, B2S, B2I};

	public Notification(String id){
		_type = null;
		_terminalId = id;
	}


	public String toString(){
		//notificationType | idTerminal
		return _type + "|" + _terminalId;
	}
}
