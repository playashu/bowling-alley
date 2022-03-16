package dbAccess;

import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QueryBuilder {
    /**
     * creates the select clause, put column selections in array
     * @param tableName
     * @param filterColumns
     * @return
     */
    public static String selectClause(String tableName, Vector<String> filterColumns)
    {
        StringBuilder clause=new StringBuilder("select ");
        if(filterColumns.isEmpty())
            clause.append("*");
        else
        {
            int i=0;
            for(;i<filterColumns.size()-1;i++)
            {
                clause.append(filterColumns.get(i));
                clause.append(",");
            }
            clause.append(filterColumns.get(i));
        }
        clause.append(" from ");
        clause.append(tableName);
        return clause.toString();
    }
    public static String whereClause(String key)
    {
        StringBuilder clause=new StringBuilder(" where ");
        clause.append(String.format("%s = ?",key));
        return clause.toString();
    }

    public static Object[] fetchTables() {
        String query = "select * from sqlite_master where type = \"table\" and tbl_name not in (\"adhoc_scripts\",\"sqlite_sequence\")";
        List<String> list = new ArrayList<>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                list.add(resultSet.getString("tbl_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray();
    }

    public static Object[] fetchColumnsByScript(String query) {
        Vector<String> vector = new Vector<>();
        try {
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            int colcount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= colcount; i++) {
                vector.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vector.toArray();
    }
}
