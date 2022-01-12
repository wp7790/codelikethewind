package org.codelikethewind.dataoperation;

public class DataOperationException extends RuntimeException {
	private static final long serialVersionUID = 5712543628038416507L;

    private DataOperationReport report;
    
    public DataOperationException(String message, Throwable cause, DataOperationReport report) {
        super(message, cause);
        this.report = report;
    }

    public DataOperationException(String message, DataOperationReport report) {
        super(message);
        this.report = report;
    }
    
    public DataOperationReport getReport() {
        return this.report;
    }
}
