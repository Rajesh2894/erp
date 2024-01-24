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
import com.abm.mainet.common.master.dto.TbDeporgMap;
import com.abm.mainet.common.service.DepartmentProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 11-May-2018
 */
@Service
public class DepartmentProvisionGRPServiceImpl implements DepartmentProvisionService {
    private static final Logger LOGGER = Logger.getLogger(DepartmentProvisionGRPServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "Dept_MasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String DEPARTMENT_SERVER_PREPARE_EXCEPTION = "Exception while preparing Department Server with WSDL WRL, WSDL file location and Authentication : ";

    private static final String GRP_DEPT_POSTING_EXCEPTION = "Exception while posting data to GRP :";

    /**
     * it is used to post created department details on GRP Environment if department posting flag is enable. this flag is
     * configured in serviceConfiguration.properties file
     */
    @Override
    public void createDepartment(TbDeporgMap departmentMapDTO) {
        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");
        if (grpPostingFlag.equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputDepartment = null;
            try {
                LOGGER.info("create Department master data to GRP start..");

                com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster grpDept = new com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster();
                grpDept.setDPDEPTID(departmentMapDTO.getDpDeptid().toString());
                grpDept.setSheetName(departmentMapDTO.getMapId().toString());
                grpDept.setDPStatus(departmentMapDTO.getDeptStatus());
                grpDept.setDPDEPTDESC(departmentMapDTO.getDpDeptdesc());
                // grpDept.setDPNAMEMAR(departmentMapDTO.getDpDeptdesc());
                grpDept.setDPDEPTCODE(departmentMapDTO.getDpDeptcode());

                // new added fields
                grpDept.setMapId(departmentMapDTO.getMapId());
                grpDept.setMapStatus(departmentMapDTO.getMapStatus());
                grpDept.setUlbCode(departmentMapDTO.getOrgid().toString());

                final ObjectMapper mapper = new ObjectMapper();
                inputDepartment = mapper.writeValueAsString(grpDept);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster> grpdeptClient = new javax.xml.ws.Holder<>(
                        grpDept);
                // get department server with authentication, WSDL URL and WSDL file location for create department data.
                DeptMasterServer deptServer = getDepartmentServer();
                deptServer.submit(grpdeptClient);
            } catch (Exception e) {
                LOGGER.error("Error Occur while GRP Department create data..." + inputDepartment);
                throw new FrameworkException(GRP_DEPT_POSTING_EXCEPTION, e.getCause());
            }
            LOGGER.info("GRP Department posting created Successfully..!!");

        }

    }

    /**
     * it is used to post update department details on GRP Environment if department posting flag is enable. this flag is
     * configured in serviceConfiguration.properties file
     */
    @Override
    public void updateDepartment(TbDeporgMap departmentMapDTO) {

        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");

        if (grpPostingFlag.equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {

            String inputDepartment = null;
            try {
                LOGGER.info("update Department master data to GRP start..");

                com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster grpDept = new com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster();
                grpDept.setDPDEPTID(departmentMapDTO.getDpDeptid().toString());
                grpDept.setSheetName(departmentMapDTO.getMapId().toString());
                grpDept.setDPStatus(departmentMapDTO.getDeptStatus());
                grpDept.setDPDEPTDESC(departmentMapDTO.getDpDeptdesc());
                // grpDept.setDPNAMEMAR(departmentMapDTO.getDpDeptdesc());
                grpDept.setDPDEPTCODE(departmentMapDTO.getDpDeptcode());

                // new added fields
                grpDept.setMapId(departmentMapDTO.getMapId());
                grpDept.setMapStatus(departmentMapDTO.getMapStatus());
                grpDept.setUlbCode(departmentMapDTO.getOrgid().toString());

                final ObjectMapper mapper = new ObjectMapper();
                inputDepartment = mapper.writeValueAsString(grpDept);

                javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DeptMaster> grpdeptClient = new javax.xml.ws.Holder<>(
                        grpDept);
                // get department server with authentication, WSDL URL and WSDL file location for create department data.
                DeptMasterServer deptServer = getDepartmentServer();
                deptServer.update(grpdeptClient);
            } catch (Exception e) {
                LOGGER.error("Error Occur while GRP Department update data..." + inputDepartment);
                throw new FrameworkException(GRP_DEPT_POSTING_EXCEPTION, e.getCause());
            }
            LOGGER.info("GRP Department posting updated Successfully..!!");

        }

    }

    /**
     * this common method is used to get Department server with authentication,WSDL URL and WSDL file location.
     * @return DeptMasterServer.
     */
    private DeptMasterServer getDepartmentServer() {

        DeptMasterServer deptServer = null;
        try {
            DeptMasterServer_Service departmentServer = new DeptMasterServer_Service(DeptMasterServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            deptServer = departmentServer.getDeptMasterServer();
            Map<String, Object> requestctx = ((BindingProvider) deptServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_DEPT_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(DEPARTMENT_SERVER_PREPARE_EXCEPTION + ex);
        }
        return deptServer;
    }

}
