package dbAccess;

import java.util.Vector;

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
            for(String col:filterColumns)
            {
                clause.append(col);
            }
        }
        return clause.toString();
    }
}
