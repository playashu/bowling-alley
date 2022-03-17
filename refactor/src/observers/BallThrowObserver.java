package observers;

import events.BallThrowEvent;

public interface BallThrowObserver {
    public void receiveBallThrowEvent(BallThrowEvent be);
}
