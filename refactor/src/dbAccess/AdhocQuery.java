package dbAccess;

import com.sun.tools.javac.util.Pair;
import models.Query;
import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdhocQuery {
    private static Object[][] get2DArrayfromResultSet(ResultSet resultSet) throws SQLException {
        int columns=resultSet.getMetaData().getColumnCount();
        int rows=0;
        while(resultSet.next())
            rows++;
        resultSet.beforeFirst();
        Object[][] result = new Object[rows][columns];
        int row = 0;
        while (resultSet.next()) {
            for (int i = 0; i < columns; i++) {
                result[row][i] = resultSet.getObject(i+1);
            }
            row++;
        }
        return result;
    }
    public static Vector<Query> fetchQueriesbyFilter(String filter)
    {
        StringBuilder query=new StringBuilder("select * from adhoc_scripts");
        if(!filter.isEmpty())
        {
            query.append(" where sql_name=?");
            query.append(filter);
        }
        Vector<Query>queries=new Vector<Query>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query.toString());
            statement.setString(1,filter);
            ResultSet rs=statement.executeQuery();
            while(rs.next())
                queries.add(new Query(
                        rs.getString("sql_id"),
                        rs.getString("sql_name"),
                        rs.getString("sql")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queries;
    }
    public static Vector<Query> fetchQueries()
    {
        return fetchQueriesbyFilter("");
    }

    public static boolean insertScript(Query queryObj){
        String query="insert into adhoc_scripts(sql_id,sql_name,sql) values(?,?,?)";
        Vector<String>bowlers=new Vector<String>();
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,queryObj.getSqlId());
            statement.setString(2,queryObj.getSqlName());
            statement.setString(3,queryObj.getSql());
            if(!statement.execute());
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static Object[][] executeAdHocScriptbySql(String query,String value)
    {
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,value);
            ResultSet rs=statement.executeQuery();
            return get2DArrayfromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[][] executeAdHocScriptbyName(String sqlName)
    {
        Query queryObj=fetchQueriesbyFilter(sqlName).firstElement();
        Vector<Query>queries=new Vector<Query>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(queryObj.getSql());
            statement.setString(1,filter);
            ResultSet rs=statement.executeQuery();
            while(rs.next())
                queries.add(new Query(
                        rs.getString("sql_id"),
                        rs.getString("sql_name"),
                        rs.getString("sql")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queries;
    }
}
