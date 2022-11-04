package prr.core;

import java.io.Serializable;

public class GoldRatePlan implements RatePlan, Serializable {

	private static RatePlan _previousPlan = new BasicRatePlan();
	private static RatePlan _nextPlan = new PlatinumRatePlan();

	@Override
	public double computeCost(Client client, TextCommunication communication) {
		if (communication.getSize() >= 0 && communication.getSize() < 100) {
			return 10.0;
		}
		return 2 * communication.getSize();
	}

	@Override
	public double computeCost(Client client, VoiceCommunication communication, int duration) {
		return duration * 10.0;
	}

	@Override
	public double computeCost(Client client, VideoCommunication communication, int duration) {
		return duration * 20.0;
	}

	@Override
	public String toStringRatePlan() {
		return "GOLD";
	}

	@Override
	public void promote(Client client) {
		if (client.getBalance() > 0 && true) { // true = cliente realizou 5 comunicacoes de video consecutivas, a 5a
												// conta como gold
			client.setRatePlan(_nextPlan);
		}

	}

	@Override
	public void demote(Client client) {
		if (client.getBalance() < 0 && true) { // true = saldo do cliente apos realizar 1a chamada
			client.setRatePlan(_previousPlan);
		}
	}

}
