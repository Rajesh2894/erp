package com.abm.mainet.common.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbOrganisationRest;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.client.FileNetApplicationClient;

public interface TbOrganisationService {

    TbOrganisationRest findById(Long orgid);

    /**
     * Loads all entities.
     * @return all entities
     */
    Set<TbOrganisationRest> findAll();

    boolean defaultexist(String flag);

    /**
     * @param mode
     * @param orgId
     * @param orgName
     * @param orgNameMar
     * @return
     */
    List<String> exist(String mode, Long orgId, Long ulbOrgId, String orgName, String orgNameMar, ApplicationSession appSession);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    TbOrganisationRest create(TbOrganisationRest TbOrganisationRest, ApplicationSession appSession, UserSession userSession,
            String directory,
            FileNetApplicationClient fileNetApplicationClient) throws JsonGenerationException, JsonMappingException,
            URISyntaxException, IOException, IllegalAccessException, InvocationTargetException;

    public void createDefaults(Organisation orgMasterSavedEntity, UserSession userSession, ApplicationSession appSession)
            throws IllegalAccessException, InvocationTargetException;

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */

    TbOrganisationRest update(TbOrganisationRest entity, String directry, FileNetApplicationClient filenetClient);

    /**
     * Deletes an entity using its Primary Key
     * @param orgid
     */
    void delete(Long orgid);

    public void createDefaultPrefixes(Organisation orgSaved, UserSession userSession);

    public Organisation findByShortCode(String orgShortCode);

}
