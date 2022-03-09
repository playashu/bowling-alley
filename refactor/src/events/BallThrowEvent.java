package events;

import java.awt.*;
import java.util.List;

public class BallThrowEvent {
    private List<Point> currentPath;
    public BallThrowEvent(List<Point> currentPath) {
        this.currentPath = currentPath;
    }
    public List<Point> getPath(){
        return currentPath;
    }
}
