package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.Collections;
import java.util.GregorianCalendar;
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
import com.abm.mainet.common.master.dto.TbFincialyearorgMap;
import com.abm.mainet.common.master.service.IFinancialYearProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 24-Jul-2018
 */
@Service
public class FinancialYearProvisionGRPServiceImpl implements IFinancialYearProvisionService {
    private static final Logger LOGGER = Logger.getLogger(FinancialYearProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "CalendarYearServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String FINANCIAL_SERVER_PREPARE_EXCEPTION = "Exception while preparing Financial year Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_FINANCIAL_POSTING_EXCEPTION = "Exception while posting data to GRP :";

    private FinancialYearProvisionGRPServiceImpl() {
    }

    /**
     * this service is used to create Financial year-Organization to GRP environment if GRP posting flag is 'Y'. if yes than only
     * Location data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbFincialyearorgMap DTO
     */
    @Override
    public void createFinYearMapping(TbFincialyearorgMap savedMappingDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start create GRP Financial year-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputFinYearMap = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear grpFinYearDto = new com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear();
                if (savedMappingDto.getFaFromDate() != null) {
                    GregorianCalendar calFromDate = new GregorianCalendar();
                    calFromDate.setTime(savedMappingDto.getFaFromDate());
                    grpFinYearDto.setFromDate(
                            javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(calFromDate));
                }
                grpFinYearDto.setSheetName(savedMappingDto.getMapId().toString());
                grpFinYearDto.setFaYearstatus(savedMappingDto.getFaYearstatusDesc());
                grpFinYearDto.setULBCode(savedMappingDto.getOrgid().toString());
                if (savedMappingDto.getFaToDate() != null) {
                    GregorianCalendar calToDate = new GregorianCalendar();
                    calToDate.setTime(savedMappingDto.getFaToDate());
                    grpFinYearDto.setToDate(
                            javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(calToDate));
                }

                grpFinYearDto.setFaYearId(savedMappingDto.getFaYearid());
                grpFinYearDto.setMapId(savedMappingDto.getMapId());

                final ObjectMapper mapper = new ObjectMapper();
                inputFinYearMap = mapper.writeValueAsString(grpFinYearDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear> finSubmitParam = new javax.xml.ws.Holder<>(
                        grpFinYearDto);
                CalendarYearServer finServer = getFinancialServer();
                finServer.submit(finSubmitParam);
            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP FinYearMap create data..." + inputFinYearMap);
                throw new FrameworkException(GRP_FINANCIAL_POSTING_EXCEPTION, ex.getCause());
            }
            LOGGER.info("GRP Financial year posting created Successfully..!!");
        }

    }

    /**
     * this service is used to update Financial year-Organization to GRP environment if GRP posting flag is 'Y'. if yes than only
     * Location data push from MAINet to GRP.flag is configured in serviceConfiguration.properties file.
     * @param TbFincialyearorgMap DTO
     */
    @Override
    public void updateFinYearMapping(TbFincialyearorgMap updatedMappingDto) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info(
                "start update GRP Financial year-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
                        + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputFinYearMap = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear grpFinUpdateDto = new com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear();
                if (updatedMappingDto.getFaFromDate() != null) {
                    GregorianCalendar calFromDate = new GregorianCalendar();
                    calFromDate.setTime(updatedMappingDto.getFaFromDate());
                    grpFinUpdateDto.setFromDate(
                            javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(calFromDate));
                }
                grpFinUpdateDto.setSheetName(updatedMappingDto.getMapId().toString());
                grpFinUpdateDto.setFaYearstatus(updatedMappingDto.getFaYearstatusDesc());
                grpFinUpdateDto.setULBCode(updatedMappingDto.getOrgid().toString());

                if (updatedMappingDto.getFaToDate() != null) {
                    GregorianCalendar calToDate = new GregorianCalendar();
                    calToDate.setTime(updatedMappingDto.getFaToDate());
                    grpFinUpdateDto.setToDate(
                            javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(calToDate));
                }

                grpFinUpdateDto.setFaYearId(updatedMappingDto.getFaYearid());
                grpFinUpdateDto.setMapId(updatedMappingDto.getMapId());

                final ObjectMapper mapper = new ObjectMapper();
                inputFinYearMap = mapper.writeValueAsString(grpFinUpdateDto);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.CalendarYear> finUpdateParam = new javax.xml.ws.Holder<>(
                        grpFinUpdateDto);
                CalendarYearServer finServer = getFinancialServer();
                finServer.update(finUpdateParam);
            } catch (Exception ex) {
                LOGGER.error("Error Occur while GRP FinYearMap update data..." + inputFinYearMap);
                throw new FrameworkException(GRP_FINANCIAL_POSTING_EXCEPTION, ex.getCause());

            }
            LOGGER.info("GRP Financial year posting updated Successfully..!!");
        }
    }

    /**
     * this common method is used to get Financial server with authentication,WSDL URL and WSDL file location.
     * @return CalendarYearServer.
     */
    private CalendarYearServer getFinancialServer() {
        CalendarYearServer finServer = null;
        try {
            CalendarYearServer_Service finServerService = new CalendarYearServer_Service(
                    CalendarYearServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            finServer = finServerService.getCalendarYearServer();
            Map<String, Object> requestctx = ((BindingProvider) finServer).getRequestContext();

            // this WSDL URL is configured in serviceEndpoint.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_FIN_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(FINANCIAL_SERVER_PREPARE_EXCEPTION + ex);
        }
        return finServer;
    }
}
