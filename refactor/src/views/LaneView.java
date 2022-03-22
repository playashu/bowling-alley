package views;/*
 *  constructs a prototype Lane View
 *
 */

import events.LaneEvent;
import models.LaneConfiguration;
import observers.LaneObserver;
import models.Lane;
import models.Party;
import utils.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Vector;

public class LaneView implements LaneObserver, ActionListener {
	private int frames;
	private int n_balls;
	private boolean initDone = true;
	boolean second_view;
	boolean status;
	JFrame frame;
	Container cpanel;
	JPanel gifPanel;
	int prev;
	Vector bowlers;
	//int cur;
	//Iterator bowlIt;
	Party party;
	int cumm;
	JPanel[][] balls;
	JLabel[][] ballLabel;
	JPanel[][] scores;
	JLabel[][] scoreLabel;
	JPanel[][] ballGrid;
	JPanel[] pins;
	int currentBowlerIndex;
	JButton maintenance,info;
	Lane lane;
	LaneConfiguration frameC;
	int laneNum;
	int numBowlers;
	String[] img;
	Dimension screenSize;
	BallThrowView ballThrowView;
	public LaneView(Lane lane, int laneNum, LaneConfiguration frameC) {
		this.lane = lane;
		this.laneNum = laneNum;
		status = true;
		initDone = true;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frames = frameC.getFrames();
		this.n_balls = frameC.numberOfBalls();
		this.frameC = frameC;
		second_view = false;
		img = new String[]{"./images/0.gif", "./images/1.gif", "./images/2.gif","./images/3.gif", "./images/4.gif", "./images/5.gif","./images/6.gif", "./images/7.gif", "./images/8.gif","./images/9.gif", "./images/10.gif","./images/11.gif", "./images/12.gif"};
		cumm = 12;
		prev = 0;
		makeJFrame();
	}
	private void makeJFrame(){
		frame = new JFrame("Lane " + laneNum + ":");
		cpanel = frame.getContentPane();
		cpanel.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.hide();
				ballThrowView.setVisibilty(false);
			}
		});

		cpanel.add(new JPanel());
		ballThrowView = new BallThrowView();
		ballThrowView.subscribe(lane);

	}
	public void show() {
		ballThrowView.setVisibilty(true);
		frame.setVisible(true);
	}
	public void hide() {
		ballThrowView.setVisibilty(false);
		frame.dispose();
	}
	public void setVisibility(boolean flag){
		if(flag == false){
			hide();
		}
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if(!is_2chance)
			panel.setPreferredSize(new Dimension(frameC.getFrames()*screenSize.width/20, party.getSize() * screenSize.height/8));
		else
			panel.setPreferredSize(new Dimension(screenSize.width/7, party.getSize() * screenSize.height/8));
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

	private int getValue(LaneEvent le, int k, int i)
	{
		return ((int[]) le.getScore().get(bowlers.get(k)))[i];
	}
	public void setScoreLabelDisplay(LaneEvent le, int[][] lescores, int k)
	{	int b = k;
		if(le.isAnotherRun()){
			b = 0;
		}
		for (int i = 0; i <= le.getFrameNum() - 1; i++) {
			if (lescores[k][i] != 0)
				scoreLabel[b][i].setText((new Integer(lescores[k][i])).toString());

		}
	}
	public void setBallDisplay(LaneEvent le, int k, int i) {
			int b = k;
			if(le.isAnotherRun()){
				b = 0;
			}

			if (getValue(le,k,i) == 10 && (i % 2 == 0 || i == 2*frames -1)){
				ballLabel[b][i].setText("X");
			} else if (i > 0 && getValue(le,k,i) + getValue(le,k,i-1) == 10 && i % 2 == 1) {
				ballLabel[b][i].setText("/");
			}
			else if ( getValue(le,k,i) == -20 ){
				ballLabel[b][i].setText("F");
			} else {
				ballLabel[b][i].setText((new Integer(getValue(le, k, i))).toString());
			}

	}
	public void displayScores(LaneEvent le)
	{
		Party party=le.getParty();
		int numBowlers=party.getSize();
		int b = 0,c=0;
		if(le.isAnotherRun()){
			numBowlers = 1;
			c=party.getSize()-1;
			b = le.getIndex();
		}
		int[][] lescores = le.getCumulScore();
		for (int k = 0; k < numBowlers; k++) {
			setScoreLabelDisplay(le,lescores,k+b);
			for (int i = 0; i <= 2*frames; i++) {
				if (getValue(le, k+c, i) == -10) {
					continue;
				}else{
					setBallDisplay(le, k + c, i);
				}
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

				//show();
			}

			displayScores(le);
			cumm = le.getTotalPinsDown();
			if(cumm<0){
				cumm = 11;
			}
			System.out.println("============================");
			System.out.println(cumm);
			System.out.println("============================");
			URL url = this.getClass().getResource(img[cumm]);
			Icon icon = new ImageIcon(url);
			JLabel label = new JLabel(icon);
			cpanel.add(label, "West");
			//ballThrowView.setVisibilty(true);
			frame.pack();
		}
	}

	private void makeView(boolean anotherRun){

		System.out.println("Making the frame.");
		if(!second_view && anotherRun){
			cpanel.remove(2);
			cpanel.add(makeFrame(party, anotherRun), "East");
			second_view = true;
		}else {

			cpanel.removeAll();
			cpanel.add(makeFrame(party, anotherRun), "Center");
			JPanel buttonPanel = UiComponents.createFlowPanel();
			Insets buttonMargin = new Insets(4, 4, 4, 4);
			maintenance = UiComponents.createFlowButton("Pause/Resume", buttonPanel, this);
			info=UiComponents.createFlowButton("How to play",buttonPanel,this);
			cpanel.add(buttonPanel, "South");
		}

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(maintenance)) {
			if( status == true){
				lane.pauseGame();
				status = false;
				ballThrowView.status(false);
			}else{
				status = true;
				lane.unPauseGame();
				ballThrowView.status(true);
			}
		}
		if(e.getSource().equals(info)){
			InfoView infoView=new InfoView();
		}
	}
	public void ballThrowViewEnable(){
		ballThrowView.status(true);
	}









}
