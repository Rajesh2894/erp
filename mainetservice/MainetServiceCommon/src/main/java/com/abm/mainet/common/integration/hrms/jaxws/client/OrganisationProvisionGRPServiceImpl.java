package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.IOrganisationProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 17-Aug-2018
 */
@Service
public class OrganisationProvisionGRPServiceImpl implements IOrganisationProvisionService {

    private static final Logger LOGGER = Logger.getLogger(LocationMasProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "Org_MasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String ORGANISATION_SERVER_PREPARE_EXCEPTION = "Exception while preparing Organisation Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_ORGANISATION_POSTING_EXCEPTION = "Exception while posting data to GRP :";

    private OrganisationProvisionGRPServiceImpl() {
    }

    @Override
    public void createOrganisation(TbOrganisation createdOrgDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start create GRP Organisation posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputOrganisation = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster grpOrgDto = new com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster();
                grpOrgDto.setONLSORGNAME(createdOrgDto.getONlsOrgname());
                grpOrgDto.setORGANISATIONID(createdOrgDto.getOrgid().toString());
                grpOrgDto.setSheetName(createdOrgDto.getOrgid().toString());
                grpOrgDto.setORGSTATUS(createdOrgDto.getOrgStatus());
                grpOrgDto.setDEFAULTSTATUS(createdOrgDto.getDefaultStatus());
                grpOrgDto.setOrgShortName(createdOrgDto.getOrgShortNm());
                grpOrgDto.setONLSORGNAMEMAR(createdOrgDto.getONlsOrgname());
                grpOrgDto.setORGADDRESS(createdOrgDto.getOrgAddress());
               // grpOrgDto.setOrgShortName(createdOrgDto.getOrgShortNm());
                grpOrgDto.setEmpSeqNo(1d);
                final ObjectMapper mapper = new ObjectMapper();
                inputOrganisation = mapper.writeValueAsString(grpOrgDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster> grpParamHolder = new javax.xml.ws.Holder<>(
                        grpOrgDto);
                OrgMasterServer orgServer = getOrgServer();
                orgServer.submit(grpParamHolder);

            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Organisation create data" + inputOrganisation);
                throw new FrameworkException(GRP_ORGANISATION_POSTING_EXCEPTION, ex.getCause());
            }
            LOGGER.info("GRP Organisation posting created Succesfully..!!");
        }

    }

    @Override
    public void updateOrganisation(TbOrganisation updatedOrgDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start update GRP Organisation posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputOrganisation = null;

            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster grpOrgDto = new com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster();
                grpOrgDto.setONLSORGNAME(updatedOrgDto.getONlsOrgname());
                grpOrgDto.setORGANISATIONID(updatedOrgDto.getOrgid().toString());
                grpOrgDto.setSheetName(updatedOrgDto.getOrgid().toString());
                grpOrgDto.setORGSTATUS(updatedOrgDto.getOrgStatus());
                grpOrgDto.setDEFAULTSTATUS(updatedOrgDto.getDefaultStatus());
               // grpOrgDto.setOrgShortNm(updatedOrgDto.getOrgShortNm());
                // grpOrgDto.setONLSORGNAMEMAR(updatedOrgDto.getONlsOrgname());
                grpOrgDto.setORGADDRESS(updatedOrgDto.getOrgAddress());
                grpOrgDto.setOrgShortName(updatedOrgDto.getOrgShortNm());
                final ObjectMapper mapper = new ObjectMapper();
                inputOrganisation = mapper.writeValueAsString(grpOrgDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.OrgMaster> grpParamHolder = new javax.xml.ws.Holder<>(
                        grpOrgDto);
                OrgMasterServer orgServer = getOrgServer();
                orgServer.update(grpParamHolder);

            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Organisation update data" + inputOrganisation);
                throw new FrameworkException(GRP_ORGANISATION_POSTING_EXCEPTION, ex.getCause());
            }
            LOGGER.info("GRP Organisation posting updated Succesfully..!!");
        }

    }

    /**
     * this common method is used to get Location server with authentication,WSDL URL and WSDL file location.
     * @return OrgMasterServer.
     */
    private OrgMasterServer getOrgServer() {
        OrgMasterServer orgServer = null;
        try {
            OrgMasterServer_Service orgServerService = new OrgMasterServer_Service(
                    OrgMasterServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            orgServer = orgServerService.getOrgMasterServer();
            Map<String, Object> requestctx = ((BindingProvider) orgServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_ORG_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(ORGANISATION_SERVER_PREPARE_EXCEPTION + ex);
        }
        return orgServer;
    }

}
