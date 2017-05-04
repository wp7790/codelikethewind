package org.codelikethewind.rest;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codelikethewind.data.CreditReport;

@Path("/creditReport")
public class CreditReportService {

	@GET
	@Path("/get")
	@Produces("application/json")
	public CreditReport getCreditReport(
			@QueryParam("fullname") String fullname, 
			@QueryParam("ssn") String ssn){
		
		CreditReport creditReport = new CreditReport();
		creditReport.setFullname(fullname);
		creditReport.setSsn(ssn);
		
		Random rand = new Random();
	    int randomCreditScore = rand.nextInt((700 - 400) + 1) + 400;
		creditReport.setCreditScore(new Integer(randomCreditScore));
		
		return creditReport;
	}
	
}
