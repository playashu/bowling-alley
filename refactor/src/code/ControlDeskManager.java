package code;

import java.util.Iterator;
import java.util.Vector;

public class ControlDeskManager {
    /** The collection of subscribers */
    private Vector subscribers;

    public ControlDeskManager() {
        this.subscribers = new Vector();
    }

    /**
     * Allows objects to subscribe as observers
     *
     * @param adding	the ControlDeskObserver that will be subscribed
     *
     */

    public void subscribe(ControlDeskObserver adding) {
        subscribers.add(adding);
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param event	the ControlDeskEvent to broadcast
     *
     */

    public void publish(ControlDeskEvent event) {
        Iterator eventIterator = subscribers.iterator();
        while (eventIterator.hasNext()) {
            (
                    (ControlDeskObserver) eventIterator
                            .next())
                    .receiveControlDeskEvent(
                            event);
        }
    }
}
