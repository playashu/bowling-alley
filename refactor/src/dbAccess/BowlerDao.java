package dbAccess;

import models.Bowler;

import java.util.Vector;

public interface BowlerDao {
    Bowler getBowler(String nickName);
    Vector<String> getAllBowlerNames();
    boolean insertBowler(Bowler bowler);
}
