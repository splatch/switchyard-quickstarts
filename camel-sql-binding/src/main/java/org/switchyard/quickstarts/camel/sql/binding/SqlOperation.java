package org.switchyard.quickstarts.camel.sql.binding;

public class SqlOperation {

    private String query;
    private Object data;

    protected SqlOperation(String query, Object data) {
        this.query = query;
        this.data = data;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

}
