package dbAccess;

import models.Bowler;
import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class BowlerDbAccess {
    private static HashMap<String,Bowler> cache=new HashMap<String,Bowler>();
    public  static Bowler getBowler(String nickName) {
        if(cache.containsKey(nickName))
            return cache.get(nickName);
        String query="select * from bowlers where nickname=?";
        Bowler bowler=null;
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,nickName);
            ResultSet rs=statement.executeQuery();
            bowler=new Bowler(
                    rs.getString("nickname"),
                    rs.getString("fullname"),
                    rs.getString("email")
            );
            cache.put(nickName,bowler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bowler;
    }

    public static Vector<String> getAllBowlerNames() {
        String query="SELECT * FROM bowlers";
        Vector<String>bowlers=new Vector<String>();
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            ResultSet rs=statement.executeQuery();
            while(rs.next())
                bowlers.add(rs.getString("nickname"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bowlers;
    }

    public static boolean insertBowler(Bowler bowler) {
        String query="insert into bowlers(nickname,fullname,email) values(?,?,?)";
        Vector<String>bowlers=new Vector<String>();
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,bowler.getNickName());
            statement.setString(2,bowler.getFullName());
            statement.setString(3,bowler.getEmail());
            if(!statement.execute());
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
