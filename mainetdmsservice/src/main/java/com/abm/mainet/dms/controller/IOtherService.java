package com.abm.mainet.dms.controller;

import java.io.IOException;
import java.util.List;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;

/**
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA
 * API's
 * 
 * @author Shyam.Ghodasra
 */

@WebService
@Api("Other Service")
@Path("/dms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)	
public interface IOtherService {


    /**
     * 
     * Getting multiple documents content by using docId
     * @param docIds docIds is Document IDs which are stored in DB.
     * @return List of document content
     */
    @POST
    @Path("/getMultipleDocumentContentByIds/")
    public List<String> getMultipleDocumentContentByIds(@Valid @QueryParam(value = "docIds") List<String> docIds);
  
    /**
     * Increase version number of document
     * @param documentId DocumentID of document
     * @param versionNumber Version Number
     * @param content Content which need to updated in document
     */
    @POST
    @Path("/increaseVersionNumber/")
    public void increaseVersionNumber(@Valid @QueryParam(value = "docId") String docId,
            @Valid @QueryParam(value = "versionNumber") int versionNumber, @Valid @QueryParam(value = "content") byte[] content);

    /**
     * Get the workflow status of document by passing document referenceID
     * @param workflowId WorkflowID which is appended on document need to pass
     * @return workflow status of document in String format.
     * @throws IOException - if the stream could not be created IllegalStateException - if content stream cannot be created.
     */
    @GET
    @Path("/getWfStatus/{workflowId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getWfStatus(@Valid @PathParam(value = "workflowId") String workflowId)
            throws Exception;
}
