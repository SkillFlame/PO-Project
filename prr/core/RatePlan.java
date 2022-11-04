package prr.core;
/**
 * RatePlan interface
 */
public interface RatePlan {
	double computeCost(Client client, TextCommunication communication);
	double computeCost(Client client, VoiceCommunication communication, int duration);
	double computeCost(Client client, VideoCommunication communication, int duration);
	String toStringRatePlan();
	void promote(Client client);
	void demote(Client client);
	
}
