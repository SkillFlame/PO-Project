package prr.core;

import java.io.Serializable;

public class PlatinumRatePlan implements RatePlan, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private static RatePlan _previousPlan = new GoldRatePlan();
	private static RatePlan _firstPlan = new BasicRatePlan();


	/**
	 * Computes the cost of a Text Communication made by a Client
	 * with this RatePlan
	 * 
	 * @param client        the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, TextCommunication communication) {
		if (communication.getSize() < 50) {
			return 0.0;
		}
		return 4.0;
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
		return duration * 10.0;
	}


	/**
	 * This action is not possible
	 */
	@Override
	public void promote(Client client) {
	}


	/**
	 * Demotes a Client from this RatePlan to a Gold RatePlan if the conditions are
	 * met or
	 * Demotes a Client from this RatePlan to a Basic RatePlan if the conditions
	 * are met
	 * 
	 * @param client the client that has this RatePlan
	 */
	@Override
	public void demote(Client client) {
		if (client.getBalance() < 0) {
			client.setRatePlan(_firstPlan);
		} else if (client.getBalance() > 0 && client.getTextCommunicationCounter() >= 2) {
			client.setRatePlan(_previousPlan);
		}
	}


	/**
	 * toString implementation of the Platinum RatePlan
	 */
	@Override
	public String toString() {
		return "PLATINUM";
	}

}
