package org.codelikethewind.dataoperation;

import java.io.Serializable;
import java.util.Date;

public class DataOperationEntry implements Serializable {
	private static final long serialVersionUID = 5382825398221178666L;

    public enum Type {
        INFO,
        WARN,
        ERROR;
    }
    
    private Date timestamp;
    private String message;
    private Type type;
    
    public DataOperationEntry(Type type, String message) {
        this.timestamp = new Date();
        this.type = type;
        this.message = message;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataOperationEntry [timestamp=" + timestamp + ", message=" + message + "]";
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
