package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.EmployeeProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 16-Apr-2018
 */
@Service
public class EmployeeProvisionGRPServiceImpl implements EmployeeProvisionService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "EmployeeServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Employee Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";
    private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to GRP :";

    private EmployeeProvisionGRPServiceImpl() {
    }

    @Override
    public void createEmployee(EmployeeBean empBean) {

        /**
         * it is used to push data to GRP environment if employee posting flag is 'Y'. if yes than only Employee data push from
         * Mainet to GRP.flag is configured in serviceConfiguration.properties file.
         */
        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info("start GRP Employee posting if posting flag is 'Y'. configured value of posting flag is : " + grpPostingFlag);
        if (grpPostingFlag
                .equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputEmployee = null;

            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.Employee grpEmp = new com.abm.mainet.common.integration.hrms.jaxws.client.Employee();
                grpEmp.setLgipmac(empBean.getLgIpMac());
                grpEmp.setDpdeptid(empBean.getDpDeptid());
                grpEmp.setEmpmobno(Double.valueOf(empBean.getEmpmobno()));
                grpEmp.setEmpname(empBean.getEmpname());
                grpEmp.setEmplname(empBean.getEmplname());
                grpEmp.setDsgid(empBean.getDsgid());
                grpEmp.setOrgid(empBean.getOrgid());
                grpEmp.setCreatedBy(empBean.getUserId().toString());
                grpEmp.setEmploginname(empBean.getEmploginname());
                grpEmp.setLocid(empBean.getDepid());
                grpEmp.setEmpemail(empBean.getEmpemail());
                grpEmp.setEmpid(empBean.getEmpId());
                grpEmp.setSheetName(empBean.getEmpId().toString());

                grpEmp.setDeptName(empBean.getDeptName());
                grpEmp.setDsgName(empBean.getDesignName());
                grpEmp.setLocName(empBean.getLocation());
                grpEmp.setOrgName(empBean.getOrgName());
                grpEmp.setGrpCode(empBean.getGrCode());

                if (empBean.getLmoddate() != null) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(empBean.getLmoddate());
                    grpEmp.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                }

                final ObjectMapper mapper = new ObjectMapper();
                inputEmployee = mapper.writeValueAsString(grpEmp);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.Employee> grpClientEmp = new javax.xml.ws.Holder<>(
                        grpEmp);

                // get employee server with authentication, WSDL URL and WSDL file location for create employee data.
                EmployeeServer empServer = getEmployeeServer();
                empServer.submit(grpClientEmp);
            } catch (DatatypeConfigurationException ex) {
                LOGGER.error("Error Occur while pusing employee data" + inputEmployee);
                throw new FrameworkException(DATE_COVERT_EXCEPTION, ex.getCause());
            } catch (Exception e) {
                LOGGER.error("Error Occur while pusing employee data" + inputEmployee);
                throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION, e.getCause());
            }
            LOGGER.info("GRP Employee posting created Successfully..!!");
        }

    }

    /**
     * this service is used to update existing employee details on GRP Environment if GRP employee posting flag is 'Y'. this flag
     * is configured in serviceConfiguration.properties file.
     * @param employeeBean.
     */
    @Override
    public void updateEmployee(EmployeeBean employeeBean) {
        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        LOGGER.info("start GRP update Employee posting if posting flag is 'Y'. configured value of posting flag is : "
                + grpPostingFlag);

        if (grpPostingFlag.equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
            String inputEmployee = null;
            try {
                com.abm.mainet.common.integration.hrms.jaxws.client.Employee grpEmp = new com.abm.mainet.common.integration.hrms.jaxws.client.Employee();
                grpEmp.setLgipmac(employeeBean.getLgIpMac());
                grpEmp.setDpdeptid(employeeBean.getDpDeptid());
                grpEmp.setEmpmobno(Double.valueOf(employeeBean.getEmpmobno()));
                grpEmp.setEmpname(employeeBean.getEmpname());
                grpEmp.setEmplname(employeeBean.getEmplname());
                grpEmp.setDsgid(employeeBean.getDsgid());
                grpEmp.setOrgid(employeeBean.getOrgid());
                if (employeeBean.getUpdatedBy() != null) {
                    grpEmp.setModifiedBy(employeeBean.getUpdatedBy().toString());
                }
                grpEmp.setEmploginname(employeeBean.getEmploginname());
                grpEmp.setLocid(employeeBean.getDepid());
                grpEmp.setEmpemail(employeeBean.getEmpemail());
                grpEmp.setEmpid(employeeBean.getEmpId());
                grpEmp.setSheetName(employeeBean.getEmpId().toString());

                grpEmp.setDeptName(employeeBean.getDeptName());
                grpEmp.setDsgName(employeeBean.getDesignName());
                grpEmp.setLocName(employeeBean.getLocation());
                grpEmp.setOrgName(employeeBean.getOrgName());
                grpEmp.setGrpCode(employeeBean.getGrCode());

                if (employeeBean.getUpdatedDate() != null) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(employeeBean.getUpdatedDate());
                    grpEmp.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
                }

                final ObjectMapper mapper = new ObjectMapper();
                inputEmployee = mapper.writeValueAsString(grpEmp);
                // get employee server with authentication, WSDL URL and WSDL file location for update employee data.

                EmployeeServer empServer = getEmployeeServer();
                empServer.update(grpEmp);
            } catch (DatatypeConfigurationException ex) {
                LOGGER.error("Error Occur while updating employee data" + inputEmployee);
                throw new FrameworkException(DATE_COVERT_EXCEPTION + ex.getCause());
            } catch (Exception e) {
                throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e.getCause());
            }
            LOGGER.info("GRP Employee posting updated Successfully..!!");
        }
    }

    /**
     * this common method is used to get Employee server with authentication,WSDL URL and WSDL file location.
     * @return EmployeeServer.
     */
    private EmployeeServer getEmployeeServer() {
        EmployeeServer empServer = null;
        try {
            EmployeeServer_Service empServerService = new EmployeeServer_Service(EmployeeServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            empServer = empServerService.getEmployeeServer();
            Map<String, Object> requestctx = ((BindingProvider) empServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_EMP_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(EMPLOYEE_SERVER_PREPARE_EXCEPTION + ex);
        }
        return empServer;
    }

}
