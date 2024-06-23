package logic;

import java.io.Serializable;

public class sqlmessage implements Serializable {
	 // Choose one from {save, delete, edit, get,check}
    private String whatToDO;
    // Write the SQL query
    private String query;
    // Parameter to set in the SQL query
    private Object[] parameter;

    public sqlmessage(String whatToDO, String query, Object[] parameter) {
        this.whatToDO = whatToDO;
        this.query = query;
        this.parameter = parameter;
    }

    public String getWhatToDO() {
        return whatToDO;
    }

    public void setWhatToDO(String whatToDO) {
        this.whatToDO = whatToDO;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Object[] getParameter() {
        return parameter;
    }
    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }
}
