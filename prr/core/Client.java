package prr.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Client Implementaion
 */
public class Client implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _key;
	private String _name;
	private int _taxNumber;
	private int _clientPayments;
	private int _clientDebts;
	private Notifications _activity;
	private RatePlan _ratePlan;


	/** Notification availability */
	enum Notifications {
		YES, NO
	};

	private List<Notification> _notifications;
	private List<String> _terminals;

	Client(String name, int taxNumber, String key) {
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_ratePlan = new BasicRatePlan();
		_notifications = new ArrayList<>();
		_terminals = new ArrayList<>();
		_activity = Notifications.YES;
	}

	
	/** 
	 * Puts all the Client's Notifications in a Notification List
	 */
	List<Notification> getNotifications() {
		return Collections.unmodifiableList(_notifications);
	}

	void removeNotifications() {
		_notifications.clear();
	}

	
	Notifications getNotificationActivity() {
		return _activity;
	}

	void activateNotifications() {
		this._activity = Notifications.YES;
	}

	void deactivateNotifications() {
		this._activity = Notifications.NO;
	}
	
	/** 
	 * Adds a Terminal to the Client's Terminal List
	 */
	void addTerminal(String terminalID) {
		_terminals.add(terminalID);
	}

	
	void updateClientBalance(Terminal clientTerminal){
		for(String id : _terminals){
			if(id == clientTerminal.getId()){
				_clientPayments += clientTerminal.getPayments();
				_clientDebts += clientTerminal.getDebt();
			}
		}
	}

	double getClientBalance(){
		return _clientPayments - _clientDebts;
	}

	double getClientDebts(){
		return _clientDebts;
	}

	RatePlan getRatePlan(){
		return _ratePlan;
	}

	/** 
	 * toString implementation of a Client
	 * CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
	 */
	@Override //A TRATAR: O LEVEL DO CLIENT MIGHT NOT WORK (_ratePlan.toStringRatePlan())
	public String toString() {
		String output = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _ratePlan.toStringRatePlan() + "|" + _activity + "|"
				+ _terminals.size() + "|" + _clientPayments + "|" + _clientDebts;
		return output;
	}

}
