package prr.core;

import java.io.Serializable;

public class PlatinumRatePlan implements RatePlan, Serializable {

	private static RatePlan _previousPlan = new GoldRatePlan();
	private static RatePlan _firstPlan = new BasicRatePlan();

	@Override
	public double computeCost(Client client, TextCommunication communication) {
		if (communication.getSize() < 50) {
			return 0.0;
		}
		return 4.0;
	}

	@Override
	public double computeCost(Client client, VoiceCommunication communication, int duration) {
		return duration * 10.0;
	}

	@Override
	public double computeCost(Client client, VideoCommunication communication, int duration) {
		return duration * 10.0;
	}

	@Override
	public String toStringRatePlan() {
		return "PLATINUM";
	}

	@Override
	public void promote(Client client) {
		// nao e possível
	}

	@Override
	public void demote(Client client) {
		if (client.getBalance() < 0 && true) { // true = saldo do cliente apos realizar uma comunicacao
			client.setRatePlan(_firstPlan);
		} else if (client.getBalance() > 0 && true) { // true = cliente realizou 2 textcommunication consecutivas, a 2a
														// conta como platinum
			client.setRatePlan(_previousPlan);
		}
	}

}
