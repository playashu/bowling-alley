package dbAccess;

import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfigDbAccess {
    public boolean putConfig(String key,String value) {
        String query="update configuration set value=? where key=?";
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,value);
            statement.setString(2,key);
            if(!statement.execute());
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getConfig(String key) {
        return null;
    }
}
