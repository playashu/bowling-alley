package views;

import events.BallThrowEvent;
import observers.BallThrowObserver;

import java.awt.*;
import java.util.Vector;

public class BallThrowView {
    private Vector subscribers;
    Container cpanel;
    BallThrowThread ballThrowThread;

    public BallThrowView() {
        subscribers = new Vector();
        ballThrowThread = new BallThrowThread(this);
        EventQueue.invokeLater(ballThrowThread);

    }

    public void setVisibilty(boolean flag){
        ballThrowThread.setVisibilty(flag);
    }
    public void status(boolean flag){
        ballThrowThread.status(flag);
    }

    public void subscribe(BallThrowObserver subscriber) {
        subscribers.add(subscriber);
    }

    public void publish(BallThrowEvent event) {	// send events when our state is changd
        for (int i=0; i < subscribers.size(); i++) {
            ((BallThrowObserver)subscribers.get(i)).receiveBallThrowEvent(event);
        }
    }
}