package org.codelikethewind.workitemhandler;

import java.util.Random;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class RandomWorkItemHandler implements WorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
		
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Random rand = new Random();
		int  n = rand.nextInt(50) + 1;
		
		Object name = workItem.getParameter("Message");
		System.out.println("Hi " + name + ", your random number is: " + n);
		
		manager.completeWorkItem(workItem.getId(), null);
	}

}
