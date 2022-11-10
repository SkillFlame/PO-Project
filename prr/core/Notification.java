package prr.core;

/**
 * Implementation of a Notification and its possible types
 */
public interface Notification {

	Client getClient();

	void update(String type);

}
