package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.IHolidayProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 24-Jul-2018
 */
@Service
public class HolidayProvisionGRPServiceImpl implements IHolidayProvisionService {
    private static final Logger LOGGER = Logger.getLogger(HolidayProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "HolidayMasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String HOLIDAY_SERVER_PREPARE_EXCEPTION = "Exception while preparing Holiday Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_HOLIDAY_POSTING_EXCEPTION = "Exception while posting data to GRP :";
    private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

    private HolidayProvisionGRPServiceImpl() {
    }

    @Override
    public void createHoliday(HolidayMasterDto holidayMasterCreate) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start create GRP Holiday master posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputHoliday = null;

            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster grpHolidayDto = new com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster();
                if (holidayMasterCreate.getHoDate() != null) {
                    grpHolidayDto
                            .setHolidayDate(getXMLGregorianCalendar(holidayMasterCreate.getHoDate()));
                }
                if (holidayMasterCreate.getHoYearStartDate() != null) {
                    grpHolidayDto.setFromDate(getXMLGregorianCalendar(holidayMasterCreate.getHoYearStartDate()));
                }

                if (holidayMasterCreate.getHoYearEndDate() != null) {
                    grpHolidayDto
                            .setToDate(getXMLGregorianCalendar(holidayMasterCreate.getHoYearEndDate()));
                }
                // Holiday Id
                grpHolidayDto.setHOID(holidayMasterCreate.getHoId());
                grpHolidayDto.setSheetName(holidayMasterCreate.getHoId().toString());
                grpHolidayDto.setULBCode(holidayMasterCreate.getOrgId().toString());
                grpHolidayDto.setHoActive(holidayMasterCreate.getHoActive());
                grpHolidayDto.setHolidayName(holidayMasterCreate.getHoDescription());

                final ObjectMapper mapper = new ObjectMapper();
                inputHoliday = mapper.writeValueAsString(grpHolidayDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster> grpHoliDto = new javax.xml.ws.Holder<>(
                        grpHolidayDto);
                HolidayMasterServer holidayPostionServer = getHolidayServer();
                holidayPostionServer.submit(grpHoliDto);

            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Holiday Master create data..." + inputHoliday);
                throw new FrameworkException(GRP_HOLIDAY_POSTING_EXCEPTION, ex.getCause());
            }
            LOGGER.info("GRP Holiday Master posting created Successfully..!!");
        }
    }

    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            GregorianCalendar gregCal = new GregorianCalendar();
            gregCal.setTime(date);
            newXMLGregorianCalendar = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCal);
        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION, ex.getCause());
        }
        return newXMLGregorianCalendar;
    }

    @Override
    public void updateHoliday(HolidayMasterDto holidayMasterUpdate) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start update GRP Holiday Master posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputHoliday = null;
            try {

                com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster grpUpdateParam = new com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster();
                // Holiday Date
                if (holidayMasterUpdate.getHoDate() != null) {
                    grpUpdateParam.setHolidayDate(getXMLGregorianCalendar(holidayMasterUpdate.getHoDate()));
                }
                // Holiday Start Date
                if (holidayMasterUpdate.getHoYearStartDate() != null) {
                    grpUpdateParam.setFromDate(getXMLGregorianCalendar(holidayMasterUpdate.getHoYearStartDate()));
                }
                // Holiday End Date
                if (holidayMasterUpdate.getHoYearEndDate() != null) {
                    grpUpdateParam.setToDate(getXMLGregorianCalendar(holidayMasterUpdate.getHoYearEndDate()));
                }
                // Holiday Id
                grpUpdateParam.setHOID(holidayMasterUpdate.getHoId());
                grpUpdateParam.setSheetName(holidayMasterUpdate.getHoId().toString());
                grpUpdateParam.setULBCode(holidayMasterUpdate.getOrgId().toString());
                grpUpdateParam.setHoActive(holidayMasterUpdate.getHoActive());
                grpUpdateParam.setHolidayName(holidayMasterUpdate.getHoDescription());

                final ObjectMapper mapper = new ObjectMapper();
                inputHoliday = mapper.writeValueAsString(grpUpdateParam);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.HolidayMaster> grpClientHolDto = new javax.xml.ws.Holder<>(
                        grpUpdateParam);
                HolidayMasterServer holidayPostionServer = getHolidayServer();
                holidayPostionServer.update(grpClientHolDto);
            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP Holiday Master update data..." + inputHoliday);
                throw new FrameworkException(GRP_HOLIDAY_POSTING_EXCEPTION, ex.getCause());

            }
            LOGGER.info("GRP Holiday Master posting updated Successfully..!!");
        }
    }

    /**
     * this common method is used to get Holiday server with authentication,WSDL URL and WSDL file location.
     * @return HolidayMasterServer.
     */

    private HolidayMasterServer getHolidayServer() {
        HolidayMasterServer holidayServer = null;
        try {
            HolidayMasterServer_Service holidayServerService = new HolidayMasterServer_Service(
                    HolidayMasterServer_Service.WSDL_LOCATION, SERVICE_NAME);
            holidayServer = holidayServerService.getHolidayMasterServer();
            Map<String, Object> requestctx = ((BindingProvider) holidayServer).getRequestContext();
            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_HOLIDAY_WSDL_URL);
            Map<String, List<String>> headers = new HashMap<>();
            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(HOLIDAY_SERVER_PREPARE_EXCEPTION + ex.getCause());
        }
        return holidayServer;
    }

}
