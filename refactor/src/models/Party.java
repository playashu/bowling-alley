package models;/*
 * Party.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: Party.java,v $
 *   Revision 1.3  2003/02/09 21:21:31  ???
 *   Added lots of comments
 *
 *   Revision 1.2  2003/01/12 22:23:32  ???
 *   *** empty log message ***
 *
 *   Revision 1.1  2003/01/12 19:09:12  ???
 *   Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Container that holds bowlers
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Party {

	/** Vector of bowlers in this party */	
    private Vector myBowlers;
	
	/**
	 * Constructor for a Party
	 * 
	 * @param bowlers	Vector of bowlers that are in this party
	 */
	private Vector highestScorers;
	private int diff_high;
	boolean superFrame;

    public Party( Vector bowlers ) {

		myBowlers = new Vector(bowlers);
		highestScorers = new Vector();
		superFrame = false;

    }

	/**
	 * Accessor for members in this party
	 * 
	 * @return 	A vector of the bowlers in this party
	 */
	public int getSize() {
		return myBowlers.size();
	}
    public Vector getMembers() {
		//System.out.println(((Bowler)myBowlers.get(0)).getNickName());
		return myBowlers;
    }

	public void initialize_SuperFrame(){

		myBowlers.clear();
		for(Object i : highestScorers) {
			myBowlers.add(i);
		}
		superFrame = true;
	}

	public void addHighScorer(int b){
		if(highestScorers.size()==2){
			highestScorers.clear();
		}
		highestScorers.add(myBowlers.get(b));
	}
	public void setDiff(int b){
		diff_high = b;
	}
	public void resetHighScorers(){
		highestScorers.clear();
	}
	public Vector getHighestScorers(){
		return highestScorers;
	}

	public int getDiff(){
		return diff_high ;
	}

}
