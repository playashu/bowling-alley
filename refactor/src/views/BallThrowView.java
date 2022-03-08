package views;

import events.BallThrowEvent;
import observers.BallThrowObserver;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

        private List<List<Point>> points;

        public TestPane() {
            points = new ArrayList<>(25);

            MouseAdapter ma = new MouseAdapter() {

                private List<Point> currentPath;

                @Override
                public void mousePressed(MouseEvent e) {

                    currentPath = new ArrayList<>(25);
                    currentPath.add(e.getPoint());

                    points.add(currentPath);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point dragPoint = e.getPoint();
                    currentPath.add(dragPoint);
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    BallThrowEvent ballThrowEvent = new BallThrowEvent(currentPath);
                    publish(ballThrowEvent);
                    currentPath = null;
                }

            };

            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g.create();
//            for (List<Point> path : points) {
//                Point from = null;
//                for (Point p : path) {
//                    if (from != null) {
//                        g2d.drawLine(from.x, from.y, p.x, p.y);
//                    }
//                    from = p;
//                }
//            }
//            g2d.dispose();
//        }

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