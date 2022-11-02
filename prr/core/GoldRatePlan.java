package prr.core;

import java.io.Serializable;

public class GoldRatePlan implements RatePlan, Serializable {

    @Override
    public double computeCost(Client client, TextCommunication communication) {
        if(communication.getSize() >= 0 && communication.getSize() < 100){
			return 10.0;
		}
		return 2 * communication.getSize();
    }

    @Override
    public double computeCost(Client client, VoiceCommunication communication) {
        return 10.0;
    }

    @Override
    public double computeCost(Client client, VideoCommunication communication) {
        return 20.0;
    }

    @Override
    public String toStringRatePlan() {
        return "GOLD";
    }

    @Override
    public void promote(Client client) {
        if(client.getClientBalance() > 0 && true){ // true = cliente realizou 5 comunicacoes de video consecutivas, a 5a conta como gold
            client._ratePlan = new PlatinumRatePlan();
        }
        
    }

    @Override
    public void demote(Client client) {
        if(client.getClientBalance() < 0 && true){ // true = saldo do cliente apos realizar uma chamada
            client._ratePlan = new BasicRatePlan();
        }
    }

    
    
}
