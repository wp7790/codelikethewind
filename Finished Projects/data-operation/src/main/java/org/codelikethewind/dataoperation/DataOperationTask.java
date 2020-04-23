package org.codelikethewind.dataoperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataOperationTask implements Runnable {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataOperationTask.class);
	
	private DataOperationSpec spec;
	
	public DataOperationTask(DataOperationSpec spec) {
		this.spec = spec;
	}
	
	@Override
	public void run() {
		DataOperationManager manager = new DataOperationManager(spec);
		try { 
			DataOperationReport report = manager.operation();
			LOG.info("[SUCCESS] Performed data operation on {} in {} ms", spec.getProcessInstanceId(), duration(report));

		} catch (Exception e) {
			LOG.error("[FAILURE] Error while performing data operation " + spec.getProcessInstanceId(), e);
		}
	}
	
	private static long duration(DataOperationReport report) {
		return report.getEndDate().getTime() - report.getStartDate().getTime();
	}
}
