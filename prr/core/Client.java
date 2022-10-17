package prr.core;

import java.io.Serializable;

public class Client implements Serializable{
	private String _key;
	private String _name;
	private int _taxNumber;
	private boolean _receiveNotifications;
	private ClientLevel _clientLevel;
	enum ClientLevel{NORMAL,  GOLD, PLATINUM}

	public Client(String name, int taxNumber, String key){
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_receiveNotifications = false;
		_clientLevel = ClientLevel.NORMAL;

	}

	public String get_key() {
		return _key;
	}
	public void set_key(String _key) {
		this._key = _key;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public int get_taxNumber() {
		return _taxNumber;
	}
	public void set_taxNumber(int _taxNumber) {
		this._taxNumber = _taxNumber;
	}
	public boolean is_receiveNotifications() {
		return _receiveNotifications;
	}
	public void set_receiveNotifications(boolean _receiveNotifications) {
		this._receiveNotifications = _receiveNotifications;
	}
	public ClientLevel get_clientLevel() {
		return _clientLevel;
	}
	public void set_clientLevel(ClientLevel _clientLevel) {
		this._clientLevel = _clientLevel;
	}


	
}
