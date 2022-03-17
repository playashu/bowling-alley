package views;

import javax.swing.*;
import java.awt.*;

public class BallThrowThread implements Runnable{
    BallThrowView ballThrowView;
    JFrame throw_frame;
    BallThrowFrame frame;
    public BallThrowThread(BallThrowView ballThrowView){
        this.ballThrowView = ballThrowView;
    }
    public void setVisibilty(boolean flag){
        while(throw_frame==null) {

        }
        if(flag==false) {
            frame.reset();
        }
        throw_frame.setVisible(flag);
    }
    public void status(boolean flag){
        throw_frame.setEnabled(flag);
    }
    @Override
    public void run() {
        throw_frame = new JFrame("Ball Throw View");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //throw_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame = new BallThrowFrame(ballThrowView);
        throw_frame.add(frame);
        throw_frame.pack();
        throw_frame.setLocation(((screenSize.width)) - ((throw_frame.getSize().width)),
                ((screenSize.height) / 2) - ((throw_frame.getSize().height) / 2));
        //throw_frame.setVisible(true);
    }
}