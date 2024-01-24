/*
 * Created on 27 Jul 2015 ( Time 15:58:02 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.abm.mainet.common.master.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Business Service Interface for entity TbOrganisation.
 */

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
@Path("/orgservice")
@Api("/orgservice")
public interface TbOrganisationService {

    /**
     * get organization master details by orgId
     * @param orgId
     * @return TbOrganisation DTO
     */

    @GET
    @Path("/orgId/{orgId}")
    @ApiOperation(value = "Fetch orgnisation details by Organisation Id", notes = "get Organisation details by Organisation Id", response = TbOrganisation.class)
    TbOrganisation findById(@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId);

    /**
     * this service is used to fetch all organization
     * @return List<TbOrganisation>
     */
    @GET
    @Path("/getAllOrg")
    @ApiOperation(value = "Fetch All Active organisation details", notes = "Fetch All Active organisation details", response = TbOrganisation.class)
    List<TbOrganisation> findAll();

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbOrganisation update(TbOrganisation entity, String directry, FileNetApplicationClient filenetClient);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    TbOrganisation create(TbOrganisation entity, ApplicationSession appSession, UserSession userSession, String directory,
            FileNetApplicationClient fileNetApplicationClient) throws Exception;

    /**
     * Deletes an entity using its Primary Key
     * @param orgid
     */
    void delete(Long orgid);

    boolean defaultexist(String flag);

    /**
     * @param mode
     * @param orgId
     * @param orgName
     * @param orgNameMar
     * @return
     */
    List<String> exist(String mode, Long orgId, Long ulbOrgId, String orgName, String orgNameMar, ApplicationSession appSession);

    public List<Object[]> findAllOrganization(String orgStatus);

    /**
     * this service is used to fetch default organization details
     * @return Organisation
     */
    @GET
    @Path("/getDefaultOrg")
    @ApiOperation(value = "Fetch Default organisation details", notes = "Fetch Default organisation details", response = Organisation.class)
    Organisation findDefaultOrganisation();

    /*
     * void createPortalOrganisation(Organisation serviceOrg, ApplicationSession appSession, UserSession userSession,
     * Employee portalEmp)
     * throws JsonGenerationException, JsonMappingException, URISyntaxException, IOException;
     */

    public Designation createDefaultDesignation(String dsgName, String shortCode)
            throws IllegalAccessException, InvocationTargetException;

    /*
     * public Employee createDefaults(Organisation orgSaved, UserSession userSession, ApplicationSession appSession,
     * TbOrganisation tbOrganisation)
     * throws Exception;
     */

    public Organisation findByShortCode(String orgShortCode);

   
	List<OrganisationDTO> getOrganizationActiveWithWorkflow(String deptCode);

	/**
	 * @return
	 */
	List<TbOrganisation> fetchOrgListBasedOnLoginOrg();

}
