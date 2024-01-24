package com.abm.mainet.authentication.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface AuthenticationService {
	
	
	    /**
	     * this service is used for get applicable tax for Road Cutting service
	     * @param emploginname
	     * @param emppassword
	     * @param orgId
	     * @return String with model applicable tax details
	     */

		String authenticateEmployee(String emploginname, String emppassword, long orgId);
}
