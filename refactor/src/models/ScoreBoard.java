package models;

import utils.ScoreHistoryFile;

import java.util.Date;

public class ScoreBoard  {

    private int[][] cumulScores;
    private int bowlerIndex;
    private int teamSize;
    public ScoreBoard(int bowlerIndex) {
        this.bowlerIndex = bowlerIndex;

    }

    public void saveToFile(String nickName) {
        try {
            Date date = new Date();
            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
            ScoreHistoryFile.addScore(nickName, dateString, new Integer(cumulScores[bowlerIndex][9]).toString());
        } catch (Exception e) {
            System.err.println("Exception in addScore. " + e);
        }
    }

    public void setBowlerIndex(int val) {
        bowlerIndex = val;
    }


    public void reset(int partySize) {
        this.teamSize = partySize;
        cumulScores = new int[partySize][10];
    }

    public int getFinalScore() {
        return cumulScores[bowlerIndex][9];
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
        for (int i = 0; i != 10; i++){
            cumulScores[bowlerIndex][i] = 0;
        }
        int current = 2*(frame - 1)+ball-1;
        for (int i = 0; i != current+2; i++){
            if(i%2 == 1 && checkIfTrue( curScore[i - 1] + curScore[i] == 10 , i < current - 1 , i < 19)){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+1] + curScore[i];
            } else if(checkIfTrue( i < current && i%2 == 0 , curScore[i] == 10  , i < 18)){
                if (checkTwoStrike(curScore, i)) break;
            } else {
                normalThrow(curScore, i);
            }
        }
        return totalScore;
    }

    private boolean checkTwoStrike(int[] curScore, int i) {
        if (checkIfTrue(curScore[i +2] != -1 , (curScore[i +3] != -1 || curScore[i +4] != -1),true)) {
            twoStrikeBalls(curScore, i);
        } else {
            return true;
        }
        return false;
    }


    private void normalThrow(int[] curScore, int i) {
        if(i < 18){
            if(i % 2 ==0 ) {
                if (checkIfTrue(i == 0 , curScore[i] != -2,true)) {
                    cumulScores[bowlerIndex][i / 2] += curScore[i];
                } else {
                    int add = (curScore[i] != -2) ? curScore[i] : 0;
                    cumulScores[bowlerIndex][i / 2] += cumulScores[bowlerIndex][i / 2 - 1] + add;
                }
            } else if(checkIfTrue(curScore[i] != -1 , i > 2 , curScore[i] != -2))
                cumulScores[bowlerIndex][i/2] += curScore[i];
        }
        else if (checkIfTrue((i/2 == 9 || i/2 == 10), curScore[i] != -2,true)){
            cumulScores[bowlerIndex][9] += curScore[i];
        }
        if (i == 18){
            cumulScores[bowlerIndex][9] += cumulScores[bowlerIndex][8];
        }
    }

    private void twoStrikeBalls(int[] curScore, int i) {
        cumulScores[bowlerIndex][i/2] += 10;
        if(curScore[i+1] != -1) {
            cumulScores[bowlerIndex][i/2] += curScore[i+1] + cumulScores[bowlerIndex][(i/2)-1];
            if (checkIfTrue(curScore[i+2] != -1 , curScore[i+2] != -2, true)){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+2];
            } else if(curScore[i+3] != -2){
                cumulScores[bowlerIndex][(i/2)] += curScore[i+3];
            }
        } else {
            int add = (i/2 > 0) ? cumulScores[bowlerIndex][(i/2)-1] : 0;
            cumulScores[bowlerIndex][i/2] += curScore[i+2] + add;
            int id = 4;
            if (curScore[i+3] != -1 &&  curScore[i+3] != -2){
                id = 3;
            }
            cumulScores[bowlerIndex][(i/2)] += curScore[i+id];
        }
    }

}