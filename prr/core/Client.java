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
	private int _payments;
	private int _debt;
	private boolean _acceptNotifications;
	private RatePlan _ratePlan;

	private List<Notification> _notifications;
	private List<String> _terminals;

	private int _videoCommunicationCounter;
	private int _textCommunicationCounter;

	Client(String name, int taxNumber, String key) {
		_key = key;
		_name = name;
		_taxNumber = taxNumber;
		_ratePlan = new BasicRatePlan();
		_notifications = new ArrayList<>();
		_terminals = new ArrayList<>();
		_acceptNotifications = true;
		_videoCommunicationCounter = 0;
		_textCommunicationCounter = 0;
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
			for (Notification not : _notifications) {
				if (not.toString().compareTo(notification.toString()) == 0) {
					throw new DuplicateNotificationException();
				}
			}
			_notifications.add(notification);
	}

	boolean isAcceptingNotifications() {
		return _acceptNotifications;
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

	void addPayment(double value) {
		_payments += value;
	}

	void addDebt(double value) {
		_debt += value;
	}

	double getBalance() {
		return _payments - _debt;
	}

	double getPayments() {
		return _payments;
	}

	double getDebt() {
		return _debt;
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

	void promote() {
		getRatePlan().promote(this);
	}
	
	void demote() {
		getRatePlan().demote(this);
	}

	int getVideoCommunicationCounter() {
		return _videoCommunicationCounter;
	}

	void increaseVideoCommunicationCounter() {
		_videoCommunicationCounter += 1;
	}

	void resetVideoCommunicationCounter() {
		_videoCommunicationCounter = 0;
	}

	int getTextCommunicationCounter() {
		return _textCommunicationCounter;
	}

	void increaseTextCommunicationCounter() {
		_textCommunicationCounter += 1;
	}

	void resetTextCommunicationCounter() {
		_textCommunicationCounter = 0;
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
		String output = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _ratePlan.toString() + "|"
				+ getNotificationActivity() + "|"
				+ _terminals.size() + "|" + _payments + "|" + _debt;
		return output;
	}

}
