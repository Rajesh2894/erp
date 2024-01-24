package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbOrgDesignation;
import com.abm.mainet.common.service.DesignationOrgMapProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 20-Jul-2018
 */
@Service
public class DesigOrgMapProvisionGRPServiceImpl implements DesignationOrgMapProvisionService {
    private static final Logger LOGGER = Logger.getLogger(DesigOrgMapProvisionGRPServiceImpl.class);
    private static final QName SERVICE_NAME = new QName("urn:orangescape", "DesigOrg_MapServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String DESIG_ORG_MAP_SERVER_PREPARE_EXCEPTION = "Exception while preparing Designation-Organization Mapping Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_DESIG_ORG_MAP_POSTING_EXCEPTION = "Exception while posting Designation-Organization Mapping data to GRP :";

    private DesigOrgMapProvisionGRPServiceImpl() {
    }

    /**
     * this service is used to create Designation Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Designation-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param createdDesigOrgMapDto
     */
    @Override
    public void createDesigOrgMapping(TbOrgDesignation createdDesigOrgMapDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start GRP create Designation-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap grpDesigOrgMapCreate = new com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap();
                grpDesigOrgMapCreate.setSheetName(createdDesigOrgMapDto.getMapId().toString());
                grpDesigOrgMapCreate.setMapId(createdDesigOrgMapDto.getMapId());
                grpDesigOrgMapCreate.setDSGID(createdDesigOrgMapDto.getDsgid());
                grpDesigOrgMapCreate.setULBCode(createdDesigOrgMapDto.getOrgid().toString());
                grpDesigOrgMapCreate.setMapStatus(createdDesigOrgMapDto.getMapStatus());

                grpDesigOrgMapCreate.setDesigDesc(createdDesigOrgMapDto.getDesigDesc());
                grpDesigOrgMapCreate.setDesigName(createdDesigOrgMapDto.getDesgName());
                // grpDesigOrgMapCreate.setDesigNamereg(createdDesigOrgMapDto.getDesgName());
                grpDesigOrgMapCreate.setDesigShortName(createdDesigOrgMapDto.getDesgShortName());

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap> desigOrgMapClientCreate = new javax.xml.ws.Holder<>(
                        grpDesigOrgMapCreate);
                DesigOrgMapServer desigOrgMapServer = getDesigOrgServer();

                desigOrgMapServer.submit(desigOrgMapClientCreate);
            } catch (Exception ex) {
                throw new FrameworkException(GRP_DESIG_ORG_MAP_POSTING_EXCEPTION, ex);
            }
            LOGGER.info("GRP Designation-Organization posting created Successfully..!!");
        }

    }

    /**
     * this service is used to create Designation Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Designation-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param createdDesigOrgMapDto
     */
    @Override
    public void updateDesigOrgMapping(TbOrgDesignation updatedDesigOrgMapDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start update GRP Designation-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap grpDesigOrgMap = new com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap();
                grpDesigOrgMap.setSheetName(updatedDesigOrgMapDto.getMapId().toString());
                grpDesigOrgMap.setMapId(updatedDesigOrgMapDto.getMapId());
                grpDesigOrgMap.setDSGID(updatedDesigOrgMapDto.getDsgid());
                grpDesigOrgMap.setULBCode(updatedDesigOrgMapDto.getOrgid().toString());
                grpDesigOrgMap.setMapStatus(updatedDesigOrgMapDto.getMapStatus());

                grpDesigOrgMap.setDesigDesc(updatedDesigOrgMapDto.getDesigDesc());
                grpDesigOrgMap.setDesigName(updatedDesigOrgMapDto.getDesgName());
                // grpDesigOrgMap.setDesigNamereg(updatedDesigOrgMapDto.getDesgName());
                grpDesigOrgMap.setDesigShortName(updatedDesigOrgMapDto.getDesgShortName());

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DesigOrgMap> desigOrgMapClient = new javax.xml.ws.Holder<>(
                        grpDesigOrgMap);
                DesigOrgMapServer desigOrgMapServer = getDesigOrgServer();
                desigOrgMapServer.update(desigOrgMapClient);
            } catch (Exception ex) {
                throw new FrameworkException(GRP_DESIG_ORG_MAP_POSTING_EXCEPTION, ex);

            }
            LOGGER.info("GRP Designation-Organization posting updated Successfully..!!");
        }

    }

    /**
     * this common method is used to get DepartOrgMapServer server with authentication,WSDL URL and WSDL file location.
     * @return DepartOrgMapServer.
     */
    private DesigOrgMapServer getDesigOrgServer() {
        DesigOrgMapServer desigOrgServer = null;
        try {
            DesigOrgMapServer_Service desigOrgServerService = new DesigOrgMapServer_Service(
                    DesigOrgMapServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            desigOrgServer = desigOrgServerService.getDesigOrgMapServer();
            Map<String, Object> requestctx = ((BindingProvider) desigOrgServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_DESIGORGMAP_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(DESIG_ORG_MAP_SERVER_PREPARE_EXCEPTION + ex);
        }
        return desigOrgServer;
    }
}
