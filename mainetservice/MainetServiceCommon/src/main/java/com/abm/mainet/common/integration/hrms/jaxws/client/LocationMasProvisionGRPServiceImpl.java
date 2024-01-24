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
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 21-Jul-2018
 */
@Service
public class LocationMasProvisionGRPServiceImpl implements ILocationMasProvisionService {

    private static final Logger LOGGER = Logger.getLogger(LocationMasProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "LocationMasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String LOCATION_SERVER_PREPARE_EXCEPTION = "Exception while preparing Location Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_LOCATION_POSTING_EXCEPTION = "Exception while posting data to GRP :";

    private LocationMasProvisionGRPServiceImpl() {
    }

    /**
     * this service is used to create Location to GRP environment if GRP posting flag is 'Y'. if yes than only Location data push
     * from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbLocationMas DTO
     */
    @Override
    public void createLocation(TbLocationMas createdLocationMasDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start create GRP Location posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputLocation = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster grpLocationDto = new com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster();
                grpLocationDto.setSheetName(createdLocationMasDto.getLocId().toString());
                grpLocationDto.setLocArea(createdLocationMasDto.getLocArea());
                grpLocationDto.setLocationName(createdLocationMasDto.getLocNameEng());
                grpLocationDto.setLocLandmark(createdLocationMasDto.getLandmark());
                grpLocationDto.setLocAddress(createdLocationMasDto.getLocAddress());

                if (createdLocationMasDto.getPincode() != null) {
                    grpLocationDto.setPincode(createdLocationMasDto.getPincode());
                }

                grpLocationDto.setLocationCode(createdLocationMasDto.getLocId().toString());
                grpLocationDto.setLocCategory(createdLocationMasDto.getLocCategoryDesc());
                // grpLocationDto.setLocAddressReg(createdLocationMasDto.getLocAddress());
                // grpLocationDto.setLocNameReg(createdLocationMasDto.getLocNameEng());
                grpLocationDto.setULBCode(createdLocationMasDto.getOrgId().toString());
                // grpLocationDto.setLocAreaReg(createdLocationMasDto.getLocArea());
                grpLocationDto.setActiveStatus(createdLocationMasDto.getLocActive());

                final ObjectMapper mapper = new ObjectMapper();
                inputLocation = mapper.writeValueAsString(grpLocationDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster> grpLocDto = new javax.xml.ws.Holder<>(
                        grpLocationDto);
                LocationMasterServer locServer = getLocationServer();
                locServer.submit(grpLocDto);
            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Location create data" + inputLocation);
                throw new FrameworkException(GRP_LOCATION_POSTING_EXCEPTION, ex.getCause());
            }
            LOGGER.info("GRP Location posting created Succesfully..!!");
        }

    }

    /**
     * this service is used to update Location with organization to GRP environment if GRP posting flag is 'Y'. if yes than only
     * Location data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbLocationMas DTO
     */
    @Override
    public void updateLocation(TbLocationMas updatedLocatioDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start create GRP Location posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputLocation = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster grpLocationUpdateDto = new com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster();
                grpLocationUpdateDto.setSheetName(updatedLocatioDto.getLocId().toString());
                grpLocationUpdateDto.setLocArea(updatedLocatioDto.getLocArea());
                grpLocationUpdateDto.setLocationName(updatedLocatioDto.getLocNameEng());
                grpLocationUpdateDto.setLocLandmark(updatedLocatioDto.getLandmark());
                grpLocationUpdateDto.setLocAddress(updatedLocatioDto.getLocAddress());
                if (updatedLocatioDto.getPincode() != null) {
                    grpLocationUpdateDto.setPincode(updatedLocatioDto.getPincode());
                }
                grpLocationUpdateDto.setLocationCode(updatedLocatioDto.getLocId().toString());
                grpLocationUpdateDto.setLocCategory(updatedLocatioDto.getLocCategoryDesc());
                // grpLocationUpdateDto.setLocAddressReg(updatedLocatioDto.getLocAddress());
                // grpLocationUpdateDto.setLocNameReg(updatedLocatioDto.getLocNameEng());
                grpLocationUpdateDto.setULBCode(updatedLocatioDto.getOrgId().toString());
                // grpLocationUpdateDto.setLocAreaReg(updatedLocatioDto.getLocArea());
                grpLocationUpdateDto.setActiveStatus(updatedLocatioDto.getLocActive());

                final ObjectMapper mapper = new ObjectMapper();
                inputLocation = mapper.writeValueAsString(grpLocationUpdateDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.LocationMaster> grpLocDto = new javax.xml.ws.Holder<>(
                        grpLocationUpdateDto);
                LocationMasterServer locServer = getLocationServer();
                locServer.update(grpLocDto);
            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Location update data..." + inputLocation);
                throw new FrameworkException(GRP_LOCATION_POSTING_EXCEPTION, ex.getCause());

            }
            LOGGER.info("GRP Location posting updated Succesfully..!!");
        }
    }

    /**
     * this common method is used to get Location server with authentication,WSDL URL and WSDL file location.
     * @return LocationMasterServer.
     */
    private LocationMasterServer getLocationServer() {
        LocationMasterServer locServer = null;
        try {
            LocationMasterServer_Service locServerService = new LocationMasterServer_Service(
                    LocationMasterServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            locServer = locServerService.getLocationMasterServer();
            Map<String, Object> requestctx = ((BindingProvider) locServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_LOC_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(LOCATION_SERVER_PREPARE_EXCEPTION + ex.getCause());
        }
        return locServer;
    }

}
