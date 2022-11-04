package prr.core;

import java.io.Serializable;

/**
 * Specialization of a RatePlan into a BasicPlan
 */
public class BasicRatePlan implements RatePlan, Serializable {

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

	@Override
	public double computeCost(Client client, VoiceCommunication communication) {
		return 20.0;
	}

	@Override
	public double computeCost(Client client, VideoCommunication communication) {
		return 30.0;
	}

	@Override
	public String toStringRatePlan() {
		return "NORMAL";
	}

	@Override
	public void promote(Client client) {
		if(client.getBalance() > 500.0){
			RatePlan ratePlan = client.getRatePlan();
			ratePlan = new GoldRatePlan();
		}
	}

	@Override
	public void demote(Client client) {
		// Not possible
	}	
}