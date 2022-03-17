package dbAccess;

import java.util.List;

public interface AbstractQueryBuilder {
    String selectClause(String tableName, List<String> filterColumns);
    String whereClause(String key);
}
