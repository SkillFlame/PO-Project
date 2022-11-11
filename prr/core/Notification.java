package prr.core;

/**
 * Implementation of a Notification and its possible types
 *  by the interface of the Observer pattern
 */
public interface Notification {

	Client getClient();

	void update(String type);

}
