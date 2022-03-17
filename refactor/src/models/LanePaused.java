package models;

import static java.lang.Thread.sleep;

public class LanePaused implements LaneState{


    public void handle(){
        try {
            sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean getState(){
        return true;
    }
}
