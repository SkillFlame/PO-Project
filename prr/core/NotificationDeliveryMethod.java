package prr.core;

import java.io.Serializable;
/* Notification Delivery Method Class that implements 
    Notification Delivery Strategy Interface */
public class NotificationDeliveryMethod  implements NotificationDelivery, Serializable{
    
    /** 
     * Sends an Off2Silence Notification to a Client by its terminal
     * 
     * @param target terminal that will recieve the Notification
     */
    @Override
    public void notifyTerminalO2S(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "O2S"));
    }

    
    /**
     * Sends an Off2Idle Notification to a Client by its terminal
     * 
     * @param target terminal that will recieve the Notification
     */
    @Override
    public void notifyTerminalO2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "O2I"));
    }

    
    /** 
     * Sends a Busy2Idle Notification to a Client by its terminal
     * 
     * @param target terminal that will recieve the Notification
     */
    @Override
    public void notifyTerminalB2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "B2I"));
    }

    
    /** 
     * Sends a Silent2Idle Notification to a Client by its terminal
     * 
     * @param target terminal that will recieve the Notification
     */
    @Override
    public void notifyTerminalS2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "S2I")); 
    }
}
