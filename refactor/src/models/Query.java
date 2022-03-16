package models;

public class Query {
    String sqlId;
    String sqlName;
    String sql;

    public Query(String sqlId, String sqlName, String sql) {
        this.sqlId = sqlId;
        this.sqlName = sqlName;
        this.sql = sql;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
