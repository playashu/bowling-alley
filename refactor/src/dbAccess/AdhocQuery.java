package dbAccess;

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
//        while(resultSet.next())
//            rows++;
//        resultSet.beforeFirst();
        Vector<Object[]>r=new Vector<Object[]>();
        while (resultSet.next()) {
            Object[]colResult=new Object[columns];
            for (int i = 0; i < columns; i++) {
                colResult[i] = resultSet.getObject(i+1);
            }
            rows++;
            r.add(colResult);
        }
        Object[][] result = new Object[rows][columns];
        for(int i=0;i<rows;i++)
        {
            Object colResult[]=r.get(i);
            for(int j=0;j<columns;j++)
            {
                result[i][j]=colResult[j];
            }
        }
        return result;
    }
    public static Vector<Query> fetchQueriesbyFilter(String filter)
    {
        StringBuilder query=new StringBuilder("select * from adhoc_scripts");
        if(!filter.isEmpty())
        {
            query.append(" where sql_name=?");
        }
        Vector<Query>queries=new Vector<Query>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query.toString());
            if(!filter.isEmpty())
                statement.setString(1,filter);
            ResultSet rs=statement.executeQuery();
            while(rs.next())
                queries.add(new Query(
                        rs.getInt("sql_id"),
                        rs.getString("sql_name"),
                        rs.getString("sql"),
                        rs.getString("type"),
                        rs.getString("message")
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
    public static String[] fetchQueryNames()
    {
        Vector<Query>queries=fetchQueries();
        String names[]=new String[queries.size()];
        for(int i=0;i< queries.size();i++)
            names[i]=queries.get(i).getSqlName();
        return names;
    }

    public static boolean insertScript(Query queryObj){
        String query="insert into adhoc_scripts(sql_name,sql) values(?,?)";
        Vector<String>bowlers=new Vector<String>();
        try {
            Connection conn=ConnectionFactory.getConnection();
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1,queryObj.getSqlName());
            statement.setString(2,queryObj.getSql());
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
            ResultSet rs=statement.executeQuery();
            return get2DArrayfromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] fetchTables() {
        String query = "select * from sqlite_master where type = \"table\" and tbl_name not in (\"adhoc_scripts\",\"sqlite_sequence\")";
        List<String> list = new ArrayList<>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("tbl_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]);
    }
    public static Object[] fetchColumnsByScript(String query,String value) {
        Vector<String> vector = new Vector<>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,value);
            ResultSet resultSet = statement.executeQuery();
            int colcount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= colcount; i++) {
                vector.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vector.toArray();
    }
    public static Object[] fetchColumnsByScript(String query) {
        Vector<String> vector = new Vector<>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            int colcount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= colcount; i++) {
                vector.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vector.toArray();
    }
    public static Object[] fetchColumnsBySqlName(String sqlName)
    {
        Query queryObj=fetchQueriesbyFilter(sqlName).firstElement();
        return fetchColumnsByScript(queryObj.getSql());
    }
    public static String[] fetchColumnsByTableName(String table)
    {
        StringBuilder query=new StringBuilder("select * from ");
        query.append(table);
        Object[] arr=fetchColumnsByScript(query.toString());
        return Arrays.copyOf(arr, arr.length, String[].class);
    }
}
