package prr.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

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
	private int _clientDebt;
	private NotificationsAreAcceptable _acceptance;
	private RatePlan _ratePlan;

	/** Notification acceptability */
	enum NotificationsAreAcceptable {
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
		_acceptance = NotificationsAreAcceptable.YES;
	}

	List<Notification> getNotifications() {
		return _notifications;
	}

	/**
	 * Clears the Notification List of the Client
	 */
	void removeNotifications() {
		_notifications.clear();
	}

	/**
	 * Gets the activity of the Client's Notifications
	 * 
	 * @return true if the client can receive notifications, false otherwise
	 */
	boolean getNotificationActivity() {
		if (_acceptance == NotificationsAreAcceptable.YES) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a notification to the Client's Notification List
	 * 
	 * @param notification the chosen notification
	 */
	void addNotification(Notification notification) {
		_notifications.add(notification);
	}

	/**
	 * Activates the Client's ability of receiving notifications
	 */
	void activateNotifications() {
		this._acceptance = NotificationsAreAcceptable.YES;
	}

	/**
	 * Deactivates the Client's ability of receiving notifications
	 */
	void deactivateNotifications() {
		this._acceptance = NotificationsAreAcceptable.NO;
	}

	/**
	 * Adds a Terminal to the Client's Terminal List
	 */
	void addTerminal(String terminalId) {
		_terminals.add(terminalId);
	}

	/**
	 * Gets the terminal ids of the Client's Terminals
	 */
	List<String> getClientTerminals() {
		return _terminals;
	}

	/**
	 * Updates Client's Balance by its Terminal
	 * 
	 * @param clientTerminal a terminal of the client
	 */
	void updateClientBalance(Terminal clientTerminal) {
		for (String Id : _terminals) {
			if (Id == clientTerminal.getId()) {
				_clientPayments += clientTerminal.getPayments();
				_clientDebt += clientTerminal.getDebt();
			}
		}
	}

	double getBalance() {
		return _clientPayments - _clientDebt;
	}

	double getClientPayments() {
		return _clientPayments;
	}

	double getClientDebt() {
		return _clientDebt;
	}

	RatePlan getRatePlan() {
		return _ratePlan;
	}

	void setRatePlan(RatePlan ratePlan) {
		_ratePlan = ratePlan;
	}

	String getKey() {
		return _key;
	}

	/**
	 * toString implementation of a Client
	 * CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
	 */
	@Override
	public String toString() {
		String output = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _ratePlan.toStringRatePlan() + "|"
				+ _acceptance + "|"
				+ _terminals.size() + "|" + _clientPayments + "|" + _clientDebt;
		return output;
	}

}
