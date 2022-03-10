package views;/*
 *  constructs a prototype Lane View
 *
 */

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
import java.util.Vector;

public class LaneView implements LaneObserver, ActionListener {
	private int frames;
	private int n_balls;
	private boolean initDone = true;
	boolean second_view;
	JFrame frame;
	Container cpanel;
	Vector bowlers;
	//int cur;
	//Iterator bowlIt;
	Party party;
	JPanel[][] balls;
	JLabel[][] ballLabel;
	JPanel[][] scores;
	JLabel[][] scoreLabel;
	JPanel[][] ballGrid;
	JPanel[] pins;
	int currentBowlerIndex;
	JButton maintenance;
	Lane lane;
	frameContext frameC;
	int laneNum;
	int numBowlers;
	public LaneView(Lane lane, int laneNum, frameContext frameC) {
		this.lane = lane;
		this.laneNum = laneNum;
		initDone = true;
		this.frames = frameC.getFrames();
		this.n_balls = frameC.numberOfBalls();
		this.frameC = frameC;
		second_view = false;
		makeJFrame();
	}
	private void makeJFrame(){
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

	private JPanel makeFrame(Party party, boolean is_2chance) {
		initDone = false;
		int b=0;
		int frames = this.frames;
		bowlers = party.getMembers();
		if(!is_2chance) {
			numBowlers = bowlers.size();

		}else{
			numBowlers = 1;
			frames = 1;
			b = currentBowlerIndex;
		}
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		balls = new JPanel[numBowlers][n_balls];
		ballLabel = new JLabel[numBowlers][n_balls];
		scores = new JPanel[numBowlers][frames];
		scoreLabel = new JLabel[numBowlers][frames];
		ballGrid = new JPanel[numBowlers][frames];
		pins= new JPanel[numBowlers];
		for (int i = 0; i != numBowlers; i++) {
			LaneViewHelper.createBallLabels(this,i,frameC);
			LaneViewHelper.createBallGrid(this,i,frameC);
			LaneViewHelper.createPinsGrid(this,i,frameC,b);
			panel.add(pins[i]);
		}

		initDone = true;
		return panel;
	}

	private int getValue(LaneEvent le, int k, int i,int b)
	{
		return ((int[]) le.getScore().get(bowlers.get(k+b)))[i];
	}
	public void setScoreLabelDisplay(LaneEvent le, int[][] lescores, int k)
	{
		for (int i = 0; i <= le.getFrameNum() - 1; i++) {
			if (lescores[k][i] != 0)
				scoreLabel[k][i].setText((new Integer(lescores[k][i])).toString());
		}
	}
	public void setBallDisplay(LaneEvent le, int k, int i,int b)
	{
			if (getValue(le,k,i,b) == 10 && (i % 2 == 0 || i == 2*frames -1))
				ballLabel[k][i].setText("X");
			else if (i > 0 && getValue(le,k,i,b) + getValue(le,k,i-1,b) == 10 && i % 2 == 1)
				ballLabel[k][i].setText("/");
			else if ( getValue(le,k,i,b) == -20 ){
				ballLabel[k][i].setText("F");
			} else
				ballLabel[k][i].setText((new Integer(getValue(le,k,i,b))).toString());
	}
	public void displayScores(LaneEvent le)
	{
		Party party=le.getParty();
		int numBowlers=party.getSize();
		int b = 0;
		if(le.isAnotherRun()){
			numBowlers = 1;
			b = le.getIndex();
		}
		int[][] lescores = le.getCumulScore();
		for (int k = 0; k < numBowlers; k++) {
			setScoreLabelDisplay(le,lescores,k);
			for (int i = 0; i <= 2*frames; i++) {
				System.out.println("getValue(le,k,i)");
				System.out.println(getValue(le,k,i,b));
				System.out.println("getValue(le,k,i)");
				if (getValue(le,k,i,b) == -10)
					continue;
				else
					setBallDisplay(le,k,i,b);
			}
		}
	}
	public void receiveLaneEvent(LaneEvent le) {
		if (lane.isPartyAssigned()) {
			this.party = le.getParty();
//			int numBowlers = le.getParty().getSize();
			while (!initDone) {
				//System.out.println("chillin' here.");
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace(System.out);}
			}
			System.out.println("le.getFrameNum()");

			currentBowlerIndex = le.getIndex();
			if (le.isCheck() & !le.isAnotherRun() || !second_view) {
				if(le.isAnotherRun()){
					System.out.println("qqqqqqq-=-=-=-=-=-=-=-=-=-=-=");
					this.frameC = le.getFrameC();
					this.party = le.getParty();
					this.frames = frameC.getFrames();
					this.n_balls = frameC.numberOfBalls();
					this.numBowlers = 1;
				}else{
					second_view = false;
					this.frameC = le.getFrameC();
					this.party = le.getParty();
					this.frames = frameC.getFrames();
					this.n_balls = frameC.numberOfBalls();
					this.numBowlers = le.getParty().getSize();
				}
				makeView(le.isAnotherRun());
				show();
			}
			displayScores(le);
		}
	}

	private void makeView(boolean anotherRun){
		System.out.println("Making the frame.");
		if(!second_view && anotherRun){
			cpanel.add(makeFrame(party, anotherRun), "East");
			second_view = true;
		}else {
			cpanel.removeAll();
			cpanel.add(makeFrame(party, anotherRun), "Center");
			JPanel buttonPanel = UiComponents.createFlowPanel();
			Insets buttonMargin = new Insets(4, 4, 4, 4);
			maintenance = UiComponents.createFlowButton("Maintenance Call", buttonPanel, this);
			cpanel.add(buttonPanel, "South");
		}
		frame.pack();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(maintenance)) {
			lane.pauseGame();
		}
	}


}
