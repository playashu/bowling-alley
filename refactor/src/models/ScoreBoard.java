package models;

import dbAccess.ScoresDbAccess;

import java.util.Date;

public class ScoreBoard{

    private int[][] cumulScores;
    private int bowlerIndex;
    private int teamSize;
    frameContext frameC;
    boolean is_3Strike;
    int frames;
    int n_balls;

    public ScoreBoard(int bowlerIndex, frameContext frameC) {
        this.frameC = frameC;
        this.frames = frameC.getFrames();
        this.n_balls = frameC.numberOfBalls();
        this.is_3Strike = frameC.is_3Strike();
        this.bowlerIndex = bowlerIndex;
    }

    public void reset(int partySize, frameContext frameC){
        this.frameC = frameC;
        this.frames = frameC.getFrames();
        this.n_balls = frameC.numberOfBalls();
        this.is_3Strike = frameC.is_3Strike();
        this.bowlerIndex = 0;
        this.teamSize = partySize;
        cumulScores = new int[partySize][frames];
    }

    public void saveToFile(String nickName) {
        try {
            Date date = new Date();
            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
            ScoresDbAccess.putScores(nickName, dateString, new Integer(cumulScores[bowlerIndex][frames-1]).toString());
        } catch (Exception e) {
            System.err.println("Exception in addScore. " + e);
        }
    }

    public void setBowlerIndex(int val) {
        bowlerIndex = val;
    }


    public int getFinalScore() {
        return cumulScores[bowlerIndex][frames-1];
    }

    public int [][] getCumulScores() {
        return cumulScores;
    }

    private boolean checkIfTrue(boolean a, boolean b, boolean c){
        if(a && b && c) {
            return true;
        }else{
            return  false;
        }
    }

    public int getScore( Bowler Cur, int frame, int ball, int[] curScore) {
        int totalScore = 0;
        for (int i = 0; i != frames; i++){
            cumulScores[bowlerIndex][i] = 0;
        }
        int current = 2*(frame - 1)+ball-1;
        for (int i = 0; i != current+1; i++){
            if(i%2 == 1 && checkIfTrue( curScore[i - 1] + curScore[i] == 10 , i < current - 1 , i < n_balls - 4)){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+1] + curScore[i];
            } else if(checkIfTrue( i < current && i%2 == 0 , curScore[i] == 10  , i < n_balls-5)){
                if (checkTwoStrike(curScore, i)){
                    break;
                }
            } else {
                normalThrow(curScore, i);
            }
        }
        return cumulScores[bowlerIndex][frame-1];
    }

    private boolean checkTwoStrike(int[] curScore, int i) {
        if (checkIfTrue(curScore[i +2] != -10 , (curScore[i +3] != -10 || curScore[i +4] != -10),true)) {
            twoStrikeBalls(curScore, i);
        } else {
            return true;
        }
        return false;
    }


    private void normalThrow(int[] curScore, int i) {
        int n=0;
        if(is_3Strike){
            n = (frames-1)*2;
            if (i == n){
                cumulScores[bowlerIndex][frames-1] += cumulScores[bowlerIndex][frames-2];
            }
        }else{
            n = (frames)*2;
        }
        if(i < n){
            if(i % 2 ==0 ) {
                if (checkIfTrue(i == 0 , curScore[i] != -20,true)) {
                    cumulScores[bowlerIndex][i / 2] += curScore[i];
                } else {
                    int add = (curScore[i] != -20) ? curScore[i] : 0;
                    cumulScores[bowlerIndex][i / 2] += cumulScores[bowlerIndex][i / 2 - 1] + add;
                }
            } else if(checkIfTrue(curScore[i] != -10 , i >= 1 , curScore[i] != -20))
                cumulScores[bowlerIndex][i/2] += curScore[i];
        }
        else if (checkIfTrue((i/2 == frames-1 || i/2 == frames), curScore[i] != -20,true)){
            cumulScores[bowlerIndex][frames-1] += curScore[i];
        }

    }

    private void twoStrikeBalls(int[] curScore, int i) {
        cumulScores[bowlerIndex][i/2] += 10;
        if(curScore[i+1] != -10) {
            cumulScores[bowlerIndex][i/2] += curScore[i+1] + cumulScores[bowlerIndex][(i/2)-1];
            if (checkIfTrue(curScore[i+2] != -10 , curScore[i+2] != -20, true)){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+2];
            } else if(curScore[i+3] != -20){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+3];
            }
        } else {
            int add = (i/2 > 0) ? cumulScores[bowlerIndex][(i/2)-1] : 0;
            cumulScores[bowlerIndex][i/2] += curScore[i+2] + add;
            int id = 4;
            if (curScore[i+3] != -10 &&  curScore[i+3] != -20){
                id = 3;
            }
            cumulScores[bowlerIndex][(i/2)] += curScore[i+id];
        }
    }



}