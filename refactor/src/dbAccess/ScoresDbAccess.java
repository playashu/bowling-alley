package dbAccess;

import models.Score;
import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ScoresDbAccess {
    public static Vector<Score> getScores(String nickName)
    {
        String query="select * from scores JOIN bowlers ON scores.nickname = bowlers.nickname WHERE scores.nickname=?";
        Vector<Score>scores=new Vector<Score>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,nickName);
            ResultSet rs=statement.executeQuery();
            while(rs.next())
            {
                scores.add(new Score(
                        rs.getString("nickname"),
                        rs.getString("date"),
                        rs.getString("score")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
    public static boolean putScores(String nick, String date, String score)
    {
        String query="insert into scores(nickname,data,score) values(?,?,?)";
        Vector<String>bowlers=new Vector<String>();
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,nick);
            statement.setString(2,date);
            statement.setString(3,score);
            if(!statement.execute());
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
