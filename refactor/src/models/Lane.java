package models;
/* $Id$
 *
 * Revisions:
 *   $Log: Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added Lane/Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 *
 */

import dbAccess.ConfigCache;
import events.BallThrowEvent;
import events.LaneEvent;
import events.PinsetterEvent;
import managers.LaneManager;
import observers.BallThrowObserver;
import observers.PinsetterObserver;
//import views.BallThrowView;
import views.EndGamePromptView;

import java.util.HashMap;
import java.util.Iterator;

public class Lane extends Thread implements PinsetterObserver, BallThrowObserver {
    int prevScoreTotal;
    private Pinsetter setter;
    //private Vector subscribers;
    private LaneManager laneManager;
    //private boolean gameIsHalted;
    boolean flag;
    private boolean gameFinished;
    private Iterator bowlerIterator;
    LaneState state;
    //private BallThrowView ballThrowView;
    private int ball;
    private int bowlIndex;
    private int frameNumber;
    private boolean tenthFrameStrike;
    private boolean canThrowAgain;

    private int[][] finalScores;
    private int gameNumber;
    private Bowler currentThrower;            // = the thrower who just took a throw

    private boolean partyAssigned;
    private Party party;
    int frames;
    private HashMap scores;
    private int[] curScores;
    //private int[][] cumulScores;
    private ScoreBoard scoreBoard;
    private LaneConfiguration frameC;
    private boolean ballThrown;
    private int prevScore;
    int totalPinsDown;
    private HashMap<Bowler, Integer> highScores;
    private boolean penalty;
    private HashMap<Bowler, Boolean> penaltie;
    int anotherRun;
    /**
     * Lane()
     * <p>
     * Constructs a new lane and starts its thread
     *
     * @pre none
     * @post a new lane has been created and its thered is executing
     */

    public Lane(LaneConfiguration frameC) {
        state = new LaneUnpaused();
        setter = new Pinsetter();
        prevScore = -1;
        scores = new HashMap();
        laneManager = new LaneManager();

        partyAssigned = false;
        ballThrown = false;
        gameNumber = 0;
        flag = false;
        this.frameC = frameC;
        anotherRun = 0;
        scoreBoard = new ScoreBoard(bowlIndex,frameC);

        setter.getManager().subscribe(this);

//        scoreBoard = new ScoreBoard(bowlIndex,party.getSize());
        this.start();
    }
    private void throwBall(){
        currentThrower = (Bowler) bowlerIterator.next();
        ball = 0;
        canThrowAgain = true;
        tenthFrameStrike = false;
        while (canThrowAgain) {
            while(!ballThrown){
                sleep();
            }
            //setter.ballThrown();
            // simulate the thrower's ball hiting
            ballThrown = false;
            ball++;
        }
    }
    private void finishGame()
    {   EndGamePromptView egp;
        if(party.getSize()>=2) {

            egp = new EndGamePromptView(((Bowler) party.getHighestScorers().get(0)).getNickName() + " Won!!");
        }else{
            egp = new EndGamePromptView(((Bowler) party.getMembers().get(0)).getNickName() + " Won!!");
        }

        int result = egp.getResult();
        egp.distroy();
        for( Object i : party.getMembers()){
            highScores.put((Bowler)i,-9999);
            penaltie.put((Bowler)i,false);
        }
        egp = null;
        System.out.println("result was: " + result);
        flag = false;
        anotherRun  = 0;
        this.frameC = new LaneConfiguration(Integer.parseInt(ConfigCache.getConfig("NUMBER_OF_FRAMES")),true);

        resetBowlerIterator(0);
        resetScores();
        laneManager.publish(lanePublish(anotherRun));

        if (result == 2) {// no, dont want to play another game

            partyAssigned = false;
            party = null;
            laneManager.publish(lanePublish(anotherRun));

        }

    }
    /**
     * run()
     * <p>
     * entry point for execution of this lane
     */
    public void run() {

        while (true) {
            if (partyAssigned && !gameFinished) {    // we have a party on this lane,
                // so next bower can take a throw

                waitWhileGameHalted();

                if (bowlerIterator.hasNext()) {

                    throwBall();

                    if (frameNumber == frameC.getFrames() - 1) {
                        finalScores[bowlIndex][gameNumber] = scoreBoard.getFinalScore();
                        scoreBoard.saveToFile(currentThrower.getNickName());
                    }
                    setter.reset();
                    bowlIndex++;
                    scoreBoard.setBowlerIndex(bowlIndex);
                } else {
                    frameNumber++;
                    resetBowlerIterator(0);

                    scoreBoard.setBowlerIndex(bowlIndex);
                    if (frameNumber > frameC.getFrames() - 1) {
                        if (party.getSize() >= 2 && anotherRun == 0) {
                            int b = get_2ndHighScorer();
                            if (party.getDiff()!=0 && flag == false) {
                                anotherRun = 1;
                                flag = true;
                                resetBowlerIterator(b);
                                currentThrower = (Bowler) bowlerIterator.next();
                                this.frameC = new LaneConfiguration(1, false);
                                resetScores();
                                scoreBoard.setBowlerIndex(b);
                                laneManager.publish(lanePublish(anotherRun));
                                resetBowlerIterator(-1);
                                sleep();
                                continue;
                            } else if (party.getDiff()==0) {
                                flag = true;
                                intiitalize_SuperFrame();
                                sleep();
                                continue;
                            }
                        } else if (flag = true && party.getDiff() + prevScoreTotal >=0){
                            System.out.println("++++++++++++++++++++++++++++++++++++++");
                            System.out.println(party.getDiff());
                            System.out.println(prevScoreTotal);
                            System.out.println("++++++++++++++++++++++++++++++++++++++");
                            intiitalize_SuperFrame();
                            sleep();
                            continue;
                        }
                        System.out.println("++++++++++++++++++++++++++++++++++++++");
                        System.out.println(party.getDiff());
                        System.out.println(prevScoreTotal);
                        System.out.println("++++++++++++++++++++++++++++++++++++++");
                        gameFinished = true;
                        gameNumber++;

                    }
                }
            }else if (partyAssigned && gameFinished) {
                    finishGame();
                }
                sleep();
            }
        }


    void intiitalize_SuperFrame(){
        party.initialize_SuperFrame();
        prevScore = -1;
        anotherRun  = 0;
        this.frameC = new LaneConfiguration(3, true);
        resetBowlerIterator(0);
        resetScores();
        laneManager.publish(lanePublish(anotherRun));
    }

    int get_2ndHighScorer(){
        int [][] a = scoreBoard.getCumulScores();
        int b=-100, s=-100;
        for(int i =0 ; i < party.getSize(); i++ ){
            int j = frameC.getFrames() -1;
            if(a[i][j]>=s){
                s = a[i][j];
                b = i;
            }
        }
        int b2=-100,s2=-100;
        for(int i =0 ; i < party.getSize(); i++ ){
            int j = frameC.getFrames() -1;
            if(a[i][j]>=s2 && i!=b){
                s2 = a[i][j];
                b2 = i;
            }
        }
        party.addHighScorer(b);
        party.addHighScorer(b2);
        party.setDiff(s2-s);
        return b2;
    }
    private void waitWhileGameHalted(){
        state.handle();
    }

    private void sleep(){
        try {
            sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * recievePinsetterEvent()
     * <p>
     * recieves the thrown event from the pinsetter
     *
     * @param pe The pinsetter event that has been received.
     * @pre none
     * @post the event has been acted upon if desiered
     */
    public void receivePinsetterEvent(PinsetterEvent pe) {

        int currScore = pe.pinsDownOnThisThrow();

        if (currScore >= 0) {            // this is a real throw


            // next logic handles the ?: what conditions dont allow them another throw?
            // handle the case of 10th frame first
            if (frameC.is_3Strike() && frameNumber == frameC.getFrames()-1) {
                tenthframeStrike(pe.totalPinsDown(),pe.getThrowNumber());
            } else { // its not the 10th frame

                normalStrike(currScore,pe.getThrowNumber(),pe.totalPinsDown());
            }

            if(canThrowAgain){
                prevScore = pe.pinsDownOnThisThrow();
            }
            totalPinsDown = pe.totalPinsDown();
            markScore(currentThrower, frameNumber + 1, pe.getThrowNumber(), currScore, pe.isPenalty());
        }
    }

    public void receiveBallThrowEvent(BallThrowEvent ballThrowEvent) {
        setter.ballThrown(ballThrowEvent,prevScore);
        ballThrown = true;
    }

    void tenthframeStrike(int totalPinsDown,int throwNumber){
        System.out.println("====================================");
        System.out.println(totalPinsDown);
        if (totalPinsDown == 10) {
            System.out.println("=******************************=");
            setter.resetPins();
            if (throwNumber == 1) {
                System.out.println("==()()()()()()()()()()()=======");
                tenthFrameStrike = true;
            }
        }

        if ((totalPinsDown != 10) && (throwNumber == 2 && tenthFrameStrike == false)) {
            canThrowAgain = false;
        }

        if (throwNumber == 3) {
            canThrowAgain = false;
        }
    }
    void normalStrike(int pinsDownOnThisThrow, int throwNumber, int totalPinsDown){
        if (pinsDownOnThisThrow == 10) {        // threw a strike
            canThrowAgain = false;
        } else if (throwNumber == 2) {
            canThrowAgain = false;
        }
    }
    /**
     * resetBowlerIterator()
     * <p>
     * sets the current bower iterator back to the first bowler
     *
     * @pre the party as been assigned
     * @post the iterator points to the first bowler in the party
     */
    private void resetBowlerIterator(int a) {
        if(a==-1){
            bowlerIterator = (party.getMembers()).iterator();
            int q = party.getSize();
            while(q>1) {
                bowlerIterator.next();
                q--;
            }
        }else{
            bowlIndex = a;
            scoreBoard.setBowlerIndex(bowlIndex);
            bowlerIterator = (party.getMembers()).iterator();
            int i = 0;
            while(i!=a) {
                bowlerIterator.next();
                i++;
            }
            //currentThrower = (Bowler) bowlerIterator;
        }
    }

    /**
     * resetScores()
     * <p>
     * resets the scoring mechanism, must be called before scoring starts
     *
     * @pre the party has been assigned
     * @post scoring system is initialized
     */
    private void resetScores() {
        Iterator bowlIt = (party.getMembers()).iterator();
        scoreBoard.reset(party.getSize(),frameC);
        while (bowlIt.hasNext()) {
            int[] toPut = new int[2* frameC.getFrames()+5];
            for (int i = 0; i != 2* frameC.getFrames()+5; i++) {
                toPut[i] = -10;
            }
            scores.put(bowlIt.next(), toPut);
        }
        for( Object i : party.getMembers()){
            highScores.put((Bowler)i,-9999);
            penaltie.put((Bowler)i,false);
        }

        gameFinished = false;
        frameNumber = 0;
        ball = 0;
    }

    /**
     * assignParty()
     * <p>
     * assigns a party to this lane
     *
     * @param theParty Party to be assigned
     * @pre none
     * @post the party has been assigned to the lane
     */
    public void assignParty(Party theParty) {
        party = theParty;
        resetBowlerIterator(0);
        curScores = new int[party.getSize()];
        currentThrower = (Bowler) theParty.getMembers().get(0);
        //cumulScores = new int[party.getMembers().size()][10];
        //scoreBoard.reset(party.getSize());
        finalScores = new int[party.getSize()][128]; //Hardcoding a max of 128 games, bite me.
        highScores = new HashMap();
        penaltie = new HashMap();
        for( Object i : party.getMembers()){
            highScores.put((Bowler)i,-9999);
            penaltie.put((Bowler)i,false);
        }
        gameNumber = 0;
        resetScores();

//        setter.getManager().subscribe(emoji);
        partyAssigned = true;
        laneManager.publish(lanePublish(anotherRun));
        //ballThrowView = new BallThrowView();
        //ballThrowView.subscribe(this);
    }

    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param Cur   The current bowler
     * @param frame The frame that bowler is on
     * @param ball  The ball the bowler is on
     * @param score The bowler's score
     */
    private void markScore(Bowler Cur, int frame, int ball, int score, boolean penalty) {
        int[] curScore;

        int index = ((frame - 1) * 2 + ball);

        curScore = (int[]) scores.get(Cur);

        curScore[index - 1] = score;
        scores.put(Cur, curScore);
        System.out.println("scorescorescorescorescore");
        System.out.println(score);
        System.out.println("scorescorescorescorescore");
        int cumScore = scoreBoard.getScore(Cur, frame, ball, (int[]) scores.get(Cur));

        if(highScores.get(Cur)<cumScore){
            highScores.put(Cur,cumScore);
        }

        if (!canThrowAgain){
            if(anotherRun==1){
                if(score != 10) {
                    prevScoreTotal = score + prevScore;
                }else{
                    prevScoreTotal = 10;
                }
            }
            if (penalty == true) {
                if (highScores.get(Cur) > 0) {
                    score -= highScores.get(Cur) / 2;
                } else {
                    penaltie.put(Cur, true);
                }
            } else if (penaltie.get(Cur) == true) {
                int cost;
                if(prevScore!=-1) {
                    cost = (score + prevScore) / 2;
                    curScore[index - 3] = -1 * cost;
                }
                else{
                    cost = (score) / 2;
                    curScore[index - 2] = -1 * cost;
                }
                penaltie.put(Cur, false);
            }
            prevScore = -1;
        }
        curScore[index - 1] = score;

        scores.put(Cur, curScore);
        scoreBoard.getScore(Cur, frame, ball, (int[]) scores.get(Cur));
        laneManager.publish(lanePublish(anotherRun));
    }

    /**
     * lanePublish()
     * <p>
     * Method that creates and returns a newly created laneEvent
     *
     * @return The new lane event
     */
    private LaneEvent lanePublish(int anotherRun) {
        //LaneEvent laneEvent = new LaneEvent(party, bowlIndex, currentThrower, scoreBoard.getCumulScores(), scores, frameNumber + 1, curScores, ball, gameIsHalted);
        LaneEvent laneEvent =new LaneEvent();

        laneEvent.setParty(party);
        laneEvent.setIndex(bowlIndex);
        laneEvent.setBowler(currentThrower);
        laneEvent.setCumulScore(scoreBoard.getCumulScores());
        laneEvent.setScore(scores);
        laneEvent.setFrameNum(frameNumber+1);
        laneEvent.setCurScores(curScores);
        laneEvent.setBall(ball);
        laneEvent.setTotalPinsDown(totalPinsDown);
        laneEvent.setMechProb(state.getState());
        laneEvent.setFrameC(frameC);
        if(anotherRun == 1){
            laneEvent.setAnotherRun(true);
        }else {
            laneEvent.setAnotherRun(false);
        }
        return laneEvent;
    }

    /**
     * isPartyAssigned()
     * <p>
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned() {
        return partyAssigned;
    }

    /**
     * isGameFinished
     *
     * @return true if the game is done, false otherwise
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    public LaneManager getLaneManager() {
        return this.laneManager;
    }

    /**
     * Accessor to get this Lane's pinsetter
     *
     * @return A reference to this lane's pinsetter
     */

    public Pinsetter getPinsetter() {
        return setter;
    }

    /**
     * Pause the execution of this game
     */
    public void pauseGame() {

        state = new LanePaused();
        laneManager.publish(lanePublish(anotherRun));
    }

    /**
     * Resume the execution of this game
     */
    public void unPauseGame() {
        state = new LaneUnpaused();
        laneManager.publish(lanePublish(anotherRun));
    }

}
