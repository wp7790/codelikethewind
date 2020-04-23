package org.codelikethewind.workitemhandler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class AccountWorkItemHandler implements WorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
		
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		String accountNumber = (String)workItem.getParameter("accountNumber");
		
		if (accountNumber == null) {
			throw new RuntimeException("No account number found for transfer");
		}
		else {
			// start the retrieval
		}
		
	}

}
