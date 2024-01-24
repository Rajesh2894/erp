package com.abm.mainet.dms.controller;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.dms.dto.DocumentDetails;
import com.abm.mainet.dms.dto.DocumentDetailsResponse;

import io.swagger.annotations.Api;

/**
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA
 * API's
 * 
 * @author Shyam.Ghodasra
 */

@WebService
@Api("Document Service")
@Path("/dms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDocumentService {

	@POST
	@Path("/document/")
	public @ResponseBody String createDocument(@RequestBody final @Valid DocumentDetails documentListReq);

	@GET
	@Path("/documents")
	public @ResponseBody  DocumentDetailsResponse getDocuments(@Valid @QueryParam(value = "applicationId") String applicationId,
			@Valid @QueryParam(value = "referenceId") String referenceId) throws Exception;

	@GET
	@Path("/documentByDocumentId/{documentId}")
	public String getDocumentByDocumentId(@Valid @PathParam("documentId") String documentId);

	@GET
	@Path("/documentByPath/{path}")
	public String getDocumentByPath(@Valid @PathParam("path") String path);

	@DELETE
	@Path("/documentByDocumentId/{documentId}")
	public void deleteDocumentByDocumentId(@Valid @PathParam("documentId") String documentId);

	@DELETE
	@Path("/documentByPath/{path}")
	public void deleteDocumentByPah(@Valid @PathParam("path") String path);

}
