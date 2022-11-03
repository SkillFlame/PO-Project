package prr.core;

import java.io.Serializable;

public class NotificationDeliveryMethod  implements NotificationDelivery, Serializable{

    @Override
    public void notifyTerminalO2S(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "O2S"));
        
    }

    @Override
    public void notifyTerminalO2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "O2I"));
        
    }

    @Override
    public void notifyTerminalB2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "B2I"));
        
    }

    @Override
    public void notifyTerminalS2I(Terminal target) {
        target.getOwner().addNotification(new Notification(target, this, "S2I"));
        
    }
}
