package models;/*
 * Pinsetter.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: Pinsetter.java,v $
 *   Revision 1.21  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.20  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.19  2003/02/06 22:28:51  ???
 *   added delay
 *
 *   Revision 1.18  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.17  2003/02/05 23:56:07  ???
 *   *** empty log message ***
 *
 *   Revision 1.16  2003/02/05 23:51:09  ???
 *   Better random numbers.
 *
 *   Revision 1.15  2003/02/02 23:49:48  ???
 *   Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.14  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.13  2003/02/02 23:21:30  ???
 *   pinsetter should give better results
 *
 *   Revision 1.12  2003/02/02 23:20:28  ???
 *   pinsetter should give better results
 *
 *   Revision 1.11  2003/02/02 23:11:41  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.10  2003/02/01 19:14:42  ???
 *   Will now set the pins back up at times other than the 10th frame.
 *
 *   Revision 1.9  2003/01/30 21:44:25  ???
 *   Fixed speling of received in may places.
 *
 *   Revision 1.8  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added Lane/Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.7  2003/01/19 21:55:24  ???
 *   updated pinsetter to new spec
 *
 *   Revision 1.6  2003/01/16 04:59:59  ???
 *   misc fixes across the board
 *
 *   Revision 1.5  2003/01/13 22:35:21  ???
 *   Scoring works.
 *
 *   Revision 1.3  2003/01/12 22:37:20  ???
 *   Wrote a better algorythm for knocking down pins
 *
 *
 */

/**
 * Class to represent the pinsetter
 *
 */

import events.BallThrowEvent;
import events.PinsetterEvent;
import managers.PinsetterManager;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Pinsetter {

	private Random rnd;
	private boolean penalty;
	private PinsetterManager manager;

	private boolean[] pins;
	/* 0-9 of state of pine, true for standing,
    false for knocked down

    6   7   8   9
      3   4   5
        2   1
          0

    */
	private boolean foul;
	private int throwNumber;
	private boolean ballThrown;
	private float degree;
	int pinsDown;

	/** Pinsetter()
	 *
	 * Constructs a new pinsetter
	 *
	 * @pre none
	 * @post a new pinsetter is created
	 * @return Pinsetter object
	 */
	public Pinsetter() {
		pins = new boolean[10];
		pinsDown = 0;
		rnd = new Random();
		manager = new PinsetterManager();
		foul = false;
		ballThrown = false;
		penalty = false;
		reset();
	}


	/** ballThrown()
	 *
	 * Called to simulate a ball thrown comming in contact with the pinsetter
	 *
	 * @pre none
	 * @post pins may have been knocked down and the thrownumber has been incremented
	 */
	public void ballThrown(BallThrowEvent ballThrowEvent, int pervScore) {	// simulated event of ball hits sensor
		int count = 0;
		foul = false;
		pinsDown = 0;
		degree = ballThrowEvent.getDegree();



		double skill = rnd.nextDouble();
		if(throwNumber==3 || pervScore + degree <=10) {
			while (count < degree) {
				int randomInt = rnd.nextInt(10);
				if (pins[randomInt]) {
					pins[randomInt] = false;
					count++;
				}
			}
		}else{
			count = 0;
		}

		if(count == 0 &&  pervScore == 0){
			penalty = true;
		}
		try {
			sleep(500);				// pinsetter is where delay will be in a real game
		} catch (Exception e) {}

		manager.sendEvent(new PinsetterEvent(pins, foul, throwNumber, count, penalty));

		throwNumber++;
	}

	/** reset()
	 *
	 * Reset the pinsetter to its complete state
	 *
	 * @pre none
	 * @post pinsetters state is reset
	 */
	public void reset() {
		foul = false;
		throwNumber = 1;
		ballThrown = false;
		resetPins();
		penalty = false;
		try {
			sleep(1000);
		} catch (Exception e) {}

		manager.sendEvent(new PinsetterEvent(pins, foul, throwNumber, -1,false));
	}

	/** resetPins()
	 *
	 * Reset the pins on the pinsetter
	 *
	 * @pre none
	 * @post pins array is reset to all pins up
	 */
	public void resetPins() {
		pinsDown = 0;
		for (int i=0; i <= 9; i++) {
			pins[i] = true;
		}
	}

	public PinsetterManager getManager(){
		return manager;
	}

};

