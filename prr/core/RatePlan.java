package prr.core;

/* Interface of the RatePlan State pattern */
public interface RatePlan {

	double computeCost(Client client, TextCommunication communication);

	double computeCost(Client client, VoiceCommunication communication, int duration);

	double computeCost(Client client, VideoCommunication communication, int duration);

	void promote(Client client);

	void demote(Client client);

	String toString();

}
