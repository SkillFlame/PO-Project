package prr.core;

import java.io.Serializable;

public class GoldRatePlan implements RatePlan, Serializable {

	private static RatePlan _previousPlan = new BasicRatePlan();
	private static RatePlan _nextPlan = new PlatinumRatePlan();

	
	/** 
	 * Computes the cost of a Text Communication made by a Client
	 * 												with this Rateplan
	 * 
	 * @param client the desired client
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
	 * 												with this Rateplan
	 * 
	 * @param client the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, VoiceCommunication communication) {
		return 10.0;
	}

	
	/** 
	 * Computes the cost of a Video Communication made by a Client
	 * 												with this Rateplan
	 * 
	 * @param client the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, VideoCommunication communication) {
		return 20.0;
	}

	
	/** 
	 * Conversion of this RatePlan into String
	 */
	@Override
	public String toStringRatePlan() {
		return "GOLD";
	}

	
	/** 
	 * Promotes a Client from this Rateplan to a Platinum Rateplan
	 * @param client the client that has this Rateplan
	 */
	@Override
	public void promote(Client client) {
		if (client.getBalance() > 0 && true) { // true = cliente realizou 5 comunicacoes de video consecutivas, a 5a conta como gold
			client.setRatePlan(_nextPlan);
		}

	}

	
	/** 
	 * Demotes a Client from this Rateplan to a Basic Rateplan
	 * @param client the client that has this Rateplan
	 */
	@Override
	public void demote(Client client) {
		if (client.getBalance() < 0) {
			client.setRatePlan(_previousPlan);
		}
	}

}
