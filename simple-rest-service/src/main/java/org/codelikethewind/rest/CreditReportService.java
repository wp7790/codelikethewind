package org.codelikethewind.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codelikethewind.data.CreditReport;

@Path("/creditReport")
public class CreditReportService {

	@GET
	@Path("/get")
	@Produces("application/xml")
	public CreditReport getCreditReport(
			@QueryParam("fullname") String fullname, 
			@QueryParam("ssn") String ssn){
		
		CreditReport creditReport = new CreditReport();
		creditReport.setFullname("TiOluwa Olarewaju");
		creditReport.setCreditScore(new Integer(500));
		creditReport.setSsn("609223403");
		
		return creditReport;
	}
	
}
