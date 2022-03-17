package models;

public class Query {
    int sqlId;
    String sqlName;
    String sql;
    String type;
    String message;
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Query(int sqlId, String sqlName, String sql,String type,String message) {
        this.sqlId = sqlId;
        this.sqlName = sqlName;
        this.sql = sql;
        this.type=type;
        this.message=message;
    }

    public int getSqlId() {
        return sqlId;
    }

    public void setSqlId(int sqlId) {
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
