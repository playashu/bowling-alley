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
    public static String selectClause(String tableName, List<String> filterColumns)
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


}
