package models;

public class frameContext {
    int frames;
    boolean last_3strike;

    public frameContext(int frames,boolean last_3strike) {
        this.frames = frames;
        this.last_3strike = last_3strike;
    }

    public int numberOfBalls(){
        int res;
        if(last_3strike){
            res = 2*frames+3;
        }else{
            res = 2*frames+2;
        }
        return  res;
    }
    public int getFrames(){
        return  frames;
    }

    public boolean is_3Strike(){
        return last_3strike;
    }
}
