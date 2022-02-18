package managers;

import events.PinsetterEvent;
import observers.PinsetterObserver;

import java.util.Vector;

public class PinsetterManager {
    private Vector subscribers;

    public PinsetterManager(){
        subscribers = new Vector();
    }

    /** sendEvent()
     *
     * Sends pinsetter events to all subscribers
     *
     * @pre none
     * @post all subscribers have recieved pinsetter event with updated state
     * */
    public void sendEvent(PinsetterEvent event) {	// send events when our state is changd
        for (int i=0; i < subscribers.size(); i++) {
            ((PinsetterObserver)subscribers.get(i)).receivePinsetterEvent(event);
        }
    }

    /** subscribe()
     *
     * subscribe objects to send events to
     *
     * @pre none
     * @post the subscriber object will recieve events when their generated
     */
    public void subscribe(PinsetterObserver subscriber) {
        subscribers.add(subscriber);
    }
}
