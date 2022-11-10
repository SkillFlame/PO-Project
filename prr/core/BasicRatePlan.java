package prr.core;

import java.io.Serializable;

/**
 * Specialization of a RatePlan into a BasicPlan
 */
public class BasicRatePlan implements RatePlan, Serializable {

	private static RatePlan _nextPlan = new GoldRatePlan();

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
			return 10.0;
		} else if (communication.getSize() >= 50 && communication.getSize() < 100) {
			return 16.0;
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
		return duration * 20.0;
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
		return duration * 30.0;
	}

	/**
	 * Promotes a Client from this RatePlan to a Gold RatePlan
	 * 
	 * @param client the client that has this RatePlan
	 */
	@Override
	public void promote(Client client) {
		if (client.getBalance() > 500.0) {
			client.setRatePlan(_nextPlan);
		}
	}

	/**
	 * This action is not possible
	 */
	@Override
	public void demote(Client client) {
	}

	/**
	 * Conversion of this RatePlan into String
	 */
	@Override
	public String toString() {
		return "NORMAL";
	}
}