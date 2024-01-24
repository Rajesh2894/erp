package com.abm.mainet.dms.controller;

import java.io.IOException;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import io.swagger.annotations.Api;

/**
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA
 * API's
 * 
 * @author Shyam.Ghodasra
 */

@WebService
@Api("Share Service")
@Path("/dms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IShareService {

	@GET
	@Path("/shareDocumentId/{documentId}")
	public String getShareId(@Valid @PathParam("documentId") String documentId) throws ClientProtocolException, IOException;

	@DELETE
	@Path("/shareDocumentId/{documentId}")
	public void deleteShareId(@Valid @PathParam("documentId") String documentId) throws ClientProtocolException, IOException;

}
