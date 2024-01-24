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

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.DeptOrgMapProvisionService;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author hiren.poriya
 * @Since 19-Jul-2018
 */
@Service
public class DeptOrgMapProvisionGRPServiceImpl implements DeptOrgMapProvisionService {

    private static final Logger LOGGER = Logger.getLogger(DeptOrgMapProvisionGRPServiceImpl.class);
    private static final QName SERVICE_NAME = new QName("urn:orangescape", "DepartOrg_MapServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String DEPT_ORG_MAP_SERVER_PREPARE_EXCEPTION = "Exception while preparing Dept-Org Mapping Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_DEPT_ORG_MAP_POSTING_EXCEPTION = "Exception while posting Department-Organization Mapping data to GRP :";

    private DeptOrgMapProvisionGRPServiceImpl() {
    }

    /**
     * this service is used to create Department Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Department-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param savedDeptOrgMapDTO
     */
    /*
     * @Override
     * public void createDeptOrgMapping(TbDeporgMap savedDeptOrgMapDTO) {
     * LOGGER.info(
     * "start create GRP Department-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
     * + grpPostingFlag);
     * if (grpPostingFlag
     * .equals(MainetConstants.Common_Constant.YES)) {
     * try {
     * com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap grpDeptOrgMap = new
     * com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap();
     * grpDeptOrgMap.setMapId(savedDeptOrgMapDTO.getMapId());
     * // sheet name should be same as primary key MapId
     * grpDeptOrgMap.setSheetName(savedDeptOrgMapDTO.getMapId().toString());
     * grpDeptOrgMap.setDpDeptId(savedDeptOrgMapDTO.getDpDeptid());
     * grpDeptOrgMap.setMapStatus(savedDeptOrgMapDTO.getMapStatus());
     * grpDeptOrgMap.setULBCode(savedDeptOrgMapDTO.getOrgid().toString());
     * grpDeptOrgMap.setDpDeptCode(savedDeptOrgMapDTO.getDpDeptcode());
     * grpDeptOrgMap.setDpDeptDesc(savedDeptOrgMapDTO.getDpDeptdesc());
     * grpDeptOrgMap.setDpDeptNameMar(savedDeptOrgMapDTO.getDpDeptdescReg());
     * javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap> createdMap = new
     * javax.xml.ws.Holder<>(
     * grpDeptOrgMap);
     * DepartOrgMapServer deptOrgMapServer = getDeptOrgServer();
     * deptOrgMapServer.submit(createdMap);
     * } catch (Exception ex) {
     * throw new FrameworkException(GRP_DEPT_ORG_MAP_POSTING_EXCEPTION, ex);
     * }
     * LOGGER.info("GRP Department-Organization posting created successfully..!!");
     * }
     * }
     */

    /**
     * this service is used to update Department Mapping with organization to GRP environment if GRP posting flag is 'Y'. if yes
     * than only Department-Organization Mapping data push from MAINet to GRP.flag is configured in
     * serviceConfiguration.properties file.
     * @param updatedMapDTO
     */
    /*
     * @Override
     * public void updateDeptOrgMapping(TbDeporgMap updatedMapDTO) {
     * LOGGER.info(
     * "start GRP update Department-Organization posting if posting flag is 'Y'. configured value of posting flag is : "
     * + grpPostingFlag);
     * if (grpPostingFlag
     * .equals(MainetConstants.Common_Constant.YES)) {
     * try {
     * com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap grpDeptOrgMap = new
     * com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap();
     * grpDeptOrgMap.setMapId(updatedMapDTO.getMapId());
     * // sheet name should be same as primary key MapId
     * grpDeptOrgMap.setSheetName(updatedMapDTO.getMapId().toString());
     * grpDeptOrgMap.setDpDeptId(updatedMapDTO.getDpDeptid());
     * grpDeptOrgMap.setMapStatus(updatedMapDTO.getMapStatus());
     * grpDeptOrgMap.setULBCode(updatedMapDTO.getOrgid().toString());
     * grpDeptOrgMap.setDpDeptCode(updatedMapDTO.getDpDeptcode());
     * grpDeptOrgMap.setDpDeptDesc(updatedMapDTO.getDpDeptdesc());
     * grpDeptOrgMap.setDpDeptNameMar(updatedMapDTO.getDpDeptdescReg());
     * javax.xml.ws.Holder<com.abm.mainet.common.integration.hrms.jaxws.client.DepartOrgMap> updatedMapping = new
     * javax.xml.ws.Holder<>(
     * grpDeptOrgMap);
     * DepartOrgMapServer deptOrgMapServer = getDeptOrgServer();
     * deptOrgMapServer.update(updatedMapping);
     * } catch (Exception ex) {
     * throw new FrameworkException(GRP_DEPT_ORG_MAP_POSTING_EXCEPTION, ex);
     * }
     * LOGGER.info("GRP Department-Organization posting updated successfully..!!");
     * }
     * }
     */
    /**
     * this common method is used to get DepartOrgMapServer server with authentication,WSDL URL and WSDL file location.
     * @return DepartOrgMapServer.
     */
    private DepartOrgMapServer getDeptOrgServer() {
        DepartOrgMapServer deptOrgServer = null;
        try {
            DepartOrgMapServer_Service deptOrgServerService = new DepartOrgMapServer_Service(
                    DepartOrgMapServer_Service.WSDL_LOCATION,
                    SERVICE_NAME);
            deptOrgServer = deptOrgServerService.getDepartOrgMapServer();
            Map<String, Object> requestctx = ((BindingProvider) deptOrgServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_DEPTORGMAP_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(DEPT_ORG_MAP_SERVER_PREPARE_EXCEPTION + ex);
        }
        return deptOrgServer;
    }

}
