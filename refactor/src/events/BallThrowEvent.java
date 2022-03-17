package events;

import java.awt.*;
import java.util.List;

public class BallThrowEvent {
    private float degree;
    public BallThrowEvent(float degree) {
        this.degree = degree;
    }
    public float getDegree(){
        return degree;
    }
}
