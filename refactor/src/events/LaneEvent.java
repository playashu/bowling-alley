package events;/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */

import models.Bowler;
import models.Party;
import models.LaneConfiguration;

import java.util.HashMap;

public class LaneEvent {

	private Party party;
	//private int frame;
	private int ball;
	private Bowler bowler;
	int[][] cumulScore;

	int totalPinsDown;

	private HashMap score;
	private int index;
	private int frameNum;
	private int[] curScores;
	private boolean mechProb;
	private boolean anotherRun;
	private LaneConfiguration frameC;

	public boolean isAnotherRun() {
		return anotherRun;
	}
	public void setTotalPinsDown(int a) {
		this.totalPinsDown= a;
	}
	public int getTotalPinsDown() {
		return totalPinsDown;
	}
	public LaneConfiguration getFrameC() {
		return frameC;
	}

	public boolean isCheck() {
		return (frameNum == 1 && ball == 0 && index == 0);
	}

	private boolean check;
	
	public LaneEvent( ) {
		anotherRun = false;
	}
	public void setAnotherRun(boolean anotherRun) {
		this.anotherRun = anotherRun;
	}

	public void setFrameC(LaneConfiguration frameC) {
		this.frameC = frameC;
	}
	public boolean isMechanicalProblem() {
		return mechProb;
	}
	
	public int getFrameNum() {
		return frameNum;
	}
	
	public HashMap getScore( ) {
		return score;
	}

	public int[] getCurScores(){ 
		return curScores;
	}
	
	public int getIndex() {
		return index;
	}

	/*public int getFrame( ) {
		return frame;
	}*/

	public int getBall( ) {
		return ball;
	}
	
	public int[][] getCumulScore(){
		return cumulScore;
	}

	public Party getParty() {
		return party;
	}
	
	public Bowler getBowler() {
		return bowler;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void setBall(int ball) {
		this.ball = ball;
	}

	public void setBowler(Bowler bowler) {
		this.bowler = bowler;
	}

	public void setCumulScore(int[][] cumulScore) {
		this.cumulScore = cumulScore;
	}

	public void setScore(HashMap score) {
		this.score = score;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setFrameNum(int frameNum) {
		this.frameNum = frameNum;
	}

	public void setCurScores(int[] curScores) {
		this.curScores = curScores;
	}

	public void setMechProb(boolean mechProb) {
		this.mechProb = mechProb;
	}
};
 
