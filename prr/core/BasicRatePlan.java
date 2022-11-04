package prr.core;

import java.io.Serializable;

/**
 * Specialization of a RatePlan into a BasicPlan
 */
public class BasicRatePlan implements RatePlan, Serializable {

	private static RatePlan _nextPlan = new GoldRatePlan();

	
	/** 
	 * Computes the cost of a Text Communication made by a Client
	 * 												with this Rateplan
	 * 
	 * @param client the desired client
	 * @param communication the communication made
	 */
	@Override
	public double computeCost(Client client, TextCommunication communication) {
		if(communication.getSize() < 50){
			return 10.0;
		}
		else if(communication.getSize() >= 50 && communication.getSize() < 100){
			return 16.0;
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
		return 20.0;
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
		return 30.0;
	}

	
	/** 
	 * Conversion of this RatePlan into String
	 */
	@Override
	public String toStringRatePlan() {
		return "NORMAL";
	}

	
	/** 
	 * Promotes a Client from this Rateplan to a Gold Rateplan
	 * @param client the client that has this Rateplan
	 */
	@Override
	public void promote(Client client) {
		if(client.getBalance() > 500.0){
			client.setRatePlan(_nextPlan);
		}
	}

	
	/** 
	 * This action is not possible
	 */
	@Override
	public void demote(Client client) {
	}	
}