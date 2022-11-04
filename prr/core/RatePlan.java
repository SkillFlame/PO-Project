package prr.core;
/* Interface of the RatePlan State pattern */
public interface RatePlan {
	double computeCost(Client client, TextCommunication communication);
	double computeCost(Client client, VoiceCommunication communication);
	double computeCost(Client client, VideoCommunication communication);
	String toStringRatePlan();
	void promote(Client client);
	void demote(Client client);
	
}
