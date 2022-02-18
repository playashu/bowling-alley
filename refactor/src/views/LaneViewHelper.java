package views;

import models.Bowler;
import utils.UiComponents;

import javax.swing.*;
import java.awt.*;

public class LaneViewHelper {
    public static void createBallLabels(LaneView view,int i) {
        for (int j = 0; j != 23; j++) {
            view.ballLabel[i][j] = new JLabel(" ");
            view.balls[i][j] = new JPanel();
            view.balls[i][j].setBorder(
                    BorderFactory.createLineBorder(Color.BLACK));
            view.balls[i][j].add(view.ballLabel[i][j]);
        }
    }
    public static void createBallGrid(LaneView view,int i)
    {
        for (int j = 0; j != 9; j++) {
            view.ballGrid[i][j] = UiComponents.createGridPanel(0,3);
            view.ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
            view.ballGrid[i][j].add(view.balls[i][2 * j], BorderLayout.EAST);
            view.ballGrid[i][j].add(view.balls[i][2 * j + 1], BorderLayout.EAST);
        }
        int j = 9;
        view.ballGrid[i][j] = UiComponents.createGridPanel(0,3);
//        view.ballGrid[i][j].setLayout(new GridLayout(0, 3));
        view.ballGrid[i][j].add(view.balls[i][2 * j]);
        view.ballGrid[i][j].add(view.balls[i][2 * j + 1]);
        view.ballGrid[i][j].add(view.balls[i][2 * j + 2]);
    }
    public static void createPinsGrid(LaneView view,int i)
    {
        view.pins[i] = new JPanel();
        view.pins[i].setBorder(
                BorderFactory.createTitledBorder(
                        ((Bowler) view.bowlers.get(i)).getNickName()));
        view.pins[i].setLayout(new GridLayout(0, 10));
        for (int k = 0; k != 10; k++) {
            view.scores[i][k] = new JPanel();
            view.scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
            view.scores[i][k].setBorder(
                    BorderFactory.createLineBorder(Color.BLACK));
            view.scores[i][k].setLayout(new GridLayout(0, 1));
            view.scores[i][k].add(view.ballGrid[i][k], BorderLayout.EAST);
            view.scores[i][k].add(view.scoreLabel[i][k], BorderLayout.SOUTH);
            view.pins[i].add(view.scores[i][k], BorderLayout.EAST);
        }

    }
}
