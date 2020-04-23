package org.codelikethewind.dataoperation;

import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.codelikethewind.dataoperation.DataOperationEntry.Type;
import org.drools.persistence.SessionNotFoundException;
import org.drools.persistence.TransactionManager;
import org.drools.persistence.TransactionManagerFactory;
import org.jbpm.runtime.manager.impl.jpa.EntityManagerFactoryManager;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.jbpm.workflow.instance.node.WorkItemNodeInstance;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.manager.InternalRuntimeManager;
import org.kie.internal.runtime.manager.RuntimeManagerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataOperationManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataOperationManager.class);
	
	private DataOperationReport report;
	private DataOperationSpec dataOperationSpec;
	
	/**
	 * Creates new instance of DataOperationManager with given operation specification.
	 * @param dataOperationSpec definition of operation to be performed
	 */
	public DataOperationManager(DataOperationSpec dataOperationSpec) {
        this.report = new DataOperationReport(dataOperationSpec);
        this.dataOperationSpec = dataOperationSpec;
    }

	/**
	 * Performs migration with node mapping (if non null).
	 * @param nodeMapping node instance mapping that is composed of unique ids of source node mapped to target node
	 * @return returns migration report describing complete migration process.
	 */
	public DataOperationReport operation() {		
	    	    	    
	    KieSession ksession = null;
	    TransactionManager txm = null;
	    boolean transactionOwner = false;
	    
	    try {		    
			InternalRuntimeManager currentManager = (InternalRuntimeManager) RuntimeManagerRegistry.get().getManager(dataOperationSpec.getDeploymentId());		
						
			txm = TransactionManagerFactory.get().newTransactionManager(currentManager.getEnvironment().getEnvironment());
			transactionOwner = txm.begin();
						
			String auditPu = currentManager.getDeploymentDescriptor().getAuditPersistenceUnit();
			
			EntityManagerFactory emf = EntityManagerFactoryManager.get().getOrCreate(auditPu);
			EntityManager em = emf.createEntityManager();			
            
			try {
    	        
    			ksession = JPAKnowledgeService.newStatefulKnowledgeSession(currentManager.getEnvironment().getKieBase(), null, currentManager.getEnvironment().getEnvironment());
    			
    			performDataOperation(ksession, dataOperationSpec.getProcessInstanceId(), em);
    			
    			em.flush();
			} finally {
    			em.clear();
    			em.close();    		
			}
			
			txm.commit(transactionOwner);
			report.addEntry(Type.INFO, "Data operation on process instance (" + dataOperationSpec.getProcessInstanceId() + ") completed successfully ");
			report.setSuccessful(true);
			report.setEndDate(new Date());
		} catch (Throwable e) {
			txm.rollback(transactionOwner);
		    LOG.error("Unexpected error during data operation", e);
		    report.addEntry(Type.ERROR, "Data operation on process instance (" + dataOperationSpec.getProcessInstanceId() 
		    	+ " # "+ "failed due to " + e.getMessage());			
		} finally {
            if (ksession != null) {
                try {
                    ksession.destroy();
                } catch (SessionNotFoundException e) {
                    // in case of rollback session might not exist
                }
            }

		}
		
		return report;
	}
	

    private void performDataOperation(KieRuntime kruntime, long processInstanceId, EntityManager em) {

        WorkflowProcessInstanceImpl processInstance = (WorkflowProcessInstanceImpl) kruntime.getProcessInstance(processInstanceId);
        
        // perform operation on process instance
        
        Collection<NodeInstance> instances = processInstance.getNodeInstances(); 
        for (NodeInstance ni : instances) {
			// Perform Operations on nodes
        	
        	if (ni instanceof WorkItemNodeInstance) {
				WorkItemNodeInstance wi = (WorkItemNodeInstance)ni;
				// Detect corrupt processes
				if (wi.getWorkItemId() == -1){
					System.out.println(">>>>>> Corrupt_processInstanceId: " + processInstanceId);
				} 
    		}
    	}
        
        return;
        
        
    }
    
    
}
