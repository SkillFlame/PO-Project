package prr.core;

import java.io.Serializable;

public class Client implements Serializable{
	private String _key;
	private String _name;
	private int _taxNumber;
	private boolean _receiveNotifications;
	private ClientLevel _clientLevel;
	enum ClientLevel{NORMAL, GOLD, PLATINUM}

	public Client(String name, int taxNumber, String key){
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_receiveNotifications = false;
		_clientLevel = ClientLevel.NORMAL;

	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public int getTaxNumber() {
		return _taxNumber;
	}

	public boolean canReceiveNotifications() {
		return _receiveNotifications;
	}

	public ClientLevel get_clientLevel() {
		return _clientLevel;
	}
	public void setClientLevel(ClientLevel _clientLevel) {
		this._clientLevel = _clientLevel;
	}


	
}
