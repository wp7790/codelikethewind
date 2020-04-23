package org.codelikethewind.dataoperation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataOperationReport implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(DataOperationReport.class);
    private static final long serialVersionUID = -5992169359542031146L;

    private DataOperationSpec dataOperationSpec;
    
    private boolean successful;
    
    private Date startDate;
    private Date endDate;
    
    private List<DataOperationEntry> entries = new ArrayList<DataOperationEntry>();

    public DataOperationReport(DataOperationSpec dataOperationSpec) {
        this.dataOperationSpec = dataOperationSpec;
        this.startDate = new Date();
    }
    
    public DataOperationSpec getDataOperationSpec() {
        return dataOperationSpec;
    }
    
    public void setMigrationSpec(DataOperationSpec processData) {
        this.dataOperationSpec = processData;
    }
    
    public boolean isSuccessful() {
        return successful;
    }
    
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public List<DataOperationEntry> getEntries() {
        return entries;
    }
    
    public void setEntries(List<DataOperationEntry> entries) {
        this.entries = entries;
    }
    
    public void addEntry(DataOperationEntry.Type type, String message) throws DataOperationException {
        this.entries.add(new DataOperationEntry(type, message));
        switch (type) {
            case INFO:
                logger.debug(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                this.setSuccessful(false);
                setEndDate(new Date());
                throw new DataOperationException(message, this);
            default:
                break;
        }
    }
}
