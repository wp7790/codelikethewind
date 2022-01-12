package org.codelikethewind.dataoperation;

public class DataOperationSpec {

	static final long serialVersionUID = 1L;

    private String deploymentId;
    private Long processInstanceId;


    public DataOperationSpec() {
    }

    /**
     * Creates a data operation definition to be used to perform a data op on a single process instance
     * @param deploymentId source deployment id - one that process instance belongs to
     * @param processInstanceId actual process instance id -  must be active process instance
     */
    public DataOperationSpec(String deploymentId, Long processInstanceId) {
        this.deploymentId = deploymentId;
        this.processInstanceId = processInstanceId;

    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Long getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "ProcessData [deploymentId=" + deploymentId + ", processInstanceId=" + processInstanceId;
    }
}
