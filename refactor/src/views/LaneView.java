package views;/*
 *  constructs a prototype Lane View
 *
 */

import models.Bowler;
import events.LaneEvent;
import models.frameContext;
import observers.LaneObserver;
import models.Lane;
import models.Party;
import utils.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

public class LaneView implements LaneObserver, ActionListener {
	private int frames;
	private int n_balls;
	private boolean initDone = true;

	JFrame frame;
	Container cpanel;
	Vector bowlers;
	//int cur;
	//Iterator bowlIt;

	JPanel[][] balls;
	JLabel[][] ballLabel;
	JPanel[][] scores;
	JLabel[][] scoreLabel;
	JPanel[][] ballGrid;
	JPanel[] pins;

	JButton maintenance;
	Lane lane;
	frameContext frameC;

	public LaneView(Lane lane, int laneNum, frameContext frameC) {
		this.lane = lane;
		initDone = true;
		this.frames = frameC.getFrames();
		this.n_balls = frameC.numberOfBalls();
		this.frameC = frameC;
		frame = new JFrame("Lane " + laneNum + ":");
		cpanel = frame.getContentPane();
		cpanel.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.hide();
			}
		});
		cpanel.add(new JPanel());
	}
	public void show() {
		frame.setVisible(true);
	}
	public void hide() {
		frame.dispose();
	}
	private JPanel makeFrame(Party party) {
		initDone = false;
		bowlers = party.getMembers();
		int numBowlers = bowlers.size();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		balls = new JPanel[numBowlers][n_balls];
		ballLabel = new JLabel[numBowlers][n_balls];
		scores = new JPanel[numBowlers][frames];
		scoreLabel = new JLabel[numBowlers][frames];
		ballGrid = new JPanel[numBowlers][frames];
		pins = new JPanel[numBowlers];
		for (int i = 0; i != numBowlers; i++) {
			LaneViewHelper.createBallLabels(this,i,frameC);
			LaneViewHelper.createBallGrid(this,i,frameC);
			LaneViewHelper.createPinsGrid(this,i,frameC);
			panel.add(pins[i]);
		}
		initDone = true;
		return panel;
	}

	private int getValue(LaneEvent le, int k, int i)
	{
		return ((int[]) le.getScore().get(bowlers.get(k)))[i];
	}
	public void setScoreLabelDisplay(LaneEvent le, int[][] lescores , int k)
	{
		for (int i = 0; i <= le.getFrameNum() - 1; i++) {
			if (lescores[k][i] != 0)
				scoreLabel[k][i].setText((new Integer(lescores[k][i])).toString());
		}
	}
	public void setBallDisplay(LaneEvent le, int k, int i)
	{
			if (getValue(le,k,i) == 10 && (i % 2 == 0 || i == 2*frames -1))
				ballLabel[k][i].setText("X");
			else if (i > 0 && getValue(le,k,i) + getValue(le,k,i-1) == 10 && i % 2 == 1)
				ballLabel[k][i].setText("/");
			else if ( getValue(le,k,i) == -20 ){
				ballLabel[k][i].setText("F");
			} else
				ballLabel[k][i].setText((new Integer(getValue(le,k,i))).toString());
	}
	public void displayScores(LaneEvent le)
	{
		Party party=le.getParty();
		int numBowlers=party.getSize();
		int[][] lescores = le.getCumulScore();
		for (int k = 0; k < numBowlers; k++) {
			setScoreLabelDisplay(le,lescores,k);
			for (int i = 0; i <= 2*frames; i++) {
				if (getValue(le,k,i) == -10)
					continue;
				else
					setBallDisplay(le,k,i);
			}
		}
	}
	public void receiveLaneEvent(LaneEvent le) {
		if (lane.isPartyAssigned()) {
//			int numBowlers = le.getParty().getSize();
			while (!initDone) {
				//System.out.println("chillin' here.");
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace(System.out);}
			}
			System.out.println("le.getFrameNum()");
			System.out.println(le.getFrameNum());
			System.out.println(le.getIndex());
			System.out.println(le.getBall());
			if (le.isCheck()) {
				System.out.println("Making the frame.");
				cpanel.removeAll();
				cpanel.add(makeFrame(le.getParty()), "Center");
				JPanel buttonPanel = UiComponents.createFlowPanel();
				Insets buttonMargin = new Insets(4, 4, 4, 4);
				maintenance = UiComponents.createFlowButton("Maintenance Call",buttonPanel,this);
				cpanel.add(buttonPanel, "South");
				frame.pack();
			}
			displayScores(le);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(maintenance)) {
			lane.pauseGame();
		}
	}

}
