package com.abm.mainet.dms.controller;

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

import io.swagger.annotations.Api;

/**
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA
 * API's
 * 
 * @author Shyam.Ghodasra
 */

@WebService
@Api("Folder Service")
@Path("/dms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)	
public interface IFolderService {


    @POST
    @Path("/folder")
    public String createFolder(@Valid @QueryParam(value = "folderPath") String folderPath)
            throws Exception;


    @GET
    @Path("/folder/{path}")
    public String getFolder(@Valid @PathParam("path") String path);

    
    @DELETE
    @Path("/folder/{folderId}")
    public void deleteFolder(@Valid @PathParam("folderId") String folderId);


}
