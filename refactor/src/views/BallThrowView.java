package views;

import events.BallThrowEvent;
import observers.BallThrowObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.*;

public class BallThrowView{
    private Vector subscribers;

   // public static void main(String[] args) {
//        new BallThrowView();
//    }

    public BallThrowView() {
        subscribers = new Vector();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });


    }

    public class TestPane extends JPanel {

        private JLabel label;
        private float degrees = -180;
        private float cost = 1;
        int score = 0;
        boolean flag = false;

        public TestPane() {
            label = new JLabel();
            this.add(label);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            Timer timer = new Timer(40, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(degrees <= -360-180){
                        degrees +=360;
                        flag = true;
                    }
                    else if(!flag){
                        cost *= 1.09;
                        degrees -= cost;
                    }else{
                        degrees -= cost;
                    }
                    score = -1 * (int) degrees -180;
                    score /=36;
                    label.setText(Integer.toString(score));
                    repaint();
                }
            });
            timer.start();

            MouseAdapter ma = new MouseAdapter() {

                private List<Point> currentPath;

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1){
                        timer.stop();
                        BallThrowEvent ballThrowEvent = new BallThrowEvent(score);
                        publish(ballThrowEvent);
                    } else if (e.getButton() == MouseEvent.BUTTON3){
                        degrees = -180;
                        cost = 1;
                        flag = false;
                        timer.start();
                    }

                }
            };

            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int diameter = Math.min(getWidth(), getHeight());
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            g2d.setColor(Color.GREEN);
            g2d.drawOval(x, y, diameter, diameter);

            g2d.setColor(Color.RED);
            float innerDiameter = 40;

            Point p = getPointOnCircle(degrees, (diameter / 2f) - (innerDiameter / 2));
            g2d.drawOval(x + p.x - (int) (innerDiameter / 2), y + p.y - (int) (innerDiameter / 2), (int) innerDiameter, (int) innerDiameter);

            g2d.dispose();
        }

        protected Point getPointOnCircle(float degress, float radius) {

            int x = Math.round(getWidth() / 2);
            int y = Math.round(getHeight() / 2);

            double rads = Math.toRadians(degress - 90); // 0 becomes the top

            // Calculate the outter point of the line
            int xPosy = Math.round((float) (x + Math.cos(rads) * radius));
            int yPosy = Math.round((float) (y + Math.sin(rads) * radius));

            return new Point(xPosy, yPosy);

        }

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