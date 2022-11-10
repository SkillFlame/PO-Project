package prr.core;

import java.io.Serializable;
import java.util.List;

import prr.core.exception.DuplicateNotificationException;
import prr.core.exception.NotificationsAlreadyDisabledException;
import prr.core.exception.NotificationsAlreadyEnabledException;

import java.util.ArrayList;

/**
 * Client Implementation
 */
public class Client implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private String _key;
	private String _name;
	private int _taxNumber;
	private int _clientPayments;
	private int _clientDebt;
	private boolean _acceptNotifications;
	private RatePlan _ratePlan;

	private List<Notification> _notifications;
	private List<String> _terminals;

	Client(String name, int taxNumber, String key) {
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_ratePlan = new BasicRatePlan();
		_notifications = new ArrayList<>();
		_terminals = new ArrayList<>();
		_acceptNotifications = true;
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
	 * Adds a notification to the Client's Notification List
	 * 
	 * @param notification the chosen notification
	 * @throws DuplicateNotificationException
	 */
	void addNotification(Notification notification) throws DuplicateNotificationException {
		for(Notification not: _notifications) {
			if(not.toString().compareTo(notification.toString()) == 0) {
				throw new DuplicateNotificationException();
			}
		}
		_notifications.add(notification);
	}

	/**
	 * Activates the Client's ability of receiving notifications
	 * 
	 * @throws NotificationsAlreadyEnabledException
	 */
	void activateNotifications() throws NotificationsAlreadyEnabledException {
		if (_acceptNotifications) {
			throw new NotificationsAlreadyEnabledException();
		}
		_acceptNotifications = true;
	}

	/**
	 * Deactivates the Client's ability of receiving notifications
	 * 
	 * @throws NotificationsAlreadyDisabledException
	 */
	void deactivateNotifications() throws NotificationsAlreadyDisabledException {
		if (!_acceptNotifications) {
			throw new NotificationsAlreadyDisabledException();
		}
		_acceptNotifications = false;
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
	 * Gets the activity of the Client's Notifications
	 * 
	 * @return YES if the client can receive notifications, NO otherwise
	 */
	String getNotificationActivity() {
		if (_acceptNotifications) {
			return "YES";
		}
		return "NO";
	}

	/**
	 * toString implementation of a Client
	 * CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
	 */
	@Override
	public String toString() {
		String output = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _ratePlan.toStringRatePlan() + "|"
				+ getNotificationActivity() + "|"
				+ _terminals.size() + "|" + _clientPayments + "|" + _clientDebt;
		return output;
	}

}
