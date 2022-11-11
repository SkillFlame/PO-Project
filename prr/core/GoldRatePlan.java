package prr.core;

import java.io.Serializable;

public class GoldRatePlan implements RatePlan, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private static RatePlan _previousPlan = new BasicRatePlan();
	private static RatePlan _nextPlan = new PlatinumRatePlan();


	/**
	 * Computes the cost of a Text Communication made by a Client
	 * with this RatePlan
	 * 
	 * @param client        the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, TextCommunication communication) {
		if (communication.getSize() >= 0 && communication.getSize() < 100) {
			return 10.0;
		}
		return 2 * communication.getSize();
	}


	/**
	 * Computes the cost of a Voice Communication made by a Client
	 * with this RatePlan
	 * 
	 * @param client        the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, VoiceCommunication communication, int duration) {
		return duration * 10.0;
	}


	/**
	 * Computes the cost of a Video Communication made by a Client
	 * with this RatePlan
	 * 
	 * @param client        the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, VideoCommunication communication, int duration) {
		return duration * 20.0;
	}


	/**
	 * Promotes a Client from this RatePlan to a Platinum RatePlan
	 * 
	 * @param client the client that has this RatePlan
	 */
	@Override
	public void promote(Client client) {
		if (client.getBalance() > 0 && client.getVideoCommunicationCounter() >= 5) {
			client.setRatePlan(_nextPlan);
		}

	}


	/**
	 * Demotes a Client from this RatePlan to a Basic RatePlan
	 * 
	 * @param client the client that has this RatePlan
	 */
	@Override
	public void demote(Client client) {
		if (client.getBalance() < 0) {
			client.setRatePlan(_previousPlan);
		}
	}


	/**
	 * toString implementation of the Gold RatePlan
	 */
	@Override
	public String toString() {
		return "GOLD";
	}

}
