package com.abm.mainet.common.integration.acccount.service;

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
import com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster;
import com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMasterServer;
import com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMasterServer_Service;
import com.abm.mainet.common.utility.ApplicationSession;

@Service
public class VendorMasterSoapWSProvisionServiceImpl implements VendorMasterSoapWSProvisionService {

    private static final Logger LOGGER = Logger.getLogger(AccountHeadProvisionServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "VendorMasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Vendor Master Head Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";

    /*
     * @Value("${grp.posting.enable}")
     * private String grpPostingFlag;
     * @Value("${grp.server.url}")
     * private String grpServerUrl;
     * @Value("${grp.authentication.code}")
     * private String grpPostingAuthCode;
     * @Value("${soap.vendor.master.wsdl.url}")
     * private String grpPostingEmpWsdlUrl;
     */

    public VendorMasterSoapWSProvisionServiceImpl() {
    }

    @Override
    public void createVendorMaster(VendorMaster vendorMaster) {

        /**
         * it is used to push data to GRP environment if vendor master Head posting
         * Mainet to GRP. all configuration data is configured in
         * serviceConfiguration.properties file.
         */
        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");

        LOGGER.info("start GRP Vendor Master if posting flag is 'Y'. configured value of posting flag is : "
                + grpPostingFlag);
        if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
            try {
                // System.out.println("Invoking submit...");
                javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster> _submit_parameters = new javax.xml.ws.Holder<com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster>(
                        vendorMaster);
                VendorMasterServer port = getVendorMasterServer();
                port.submit(_submit_parameters);

                // System.out.println("submit._submit_parameters=" + _submit_parameters.value);
            } catch (Exception e) {
                throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
            }
        }
    }

    @Override
    public void updateVendormaster(VendorMaster vendorMaster) {
        String grpPostingFlag = ApplicationSession.getInstance().getMessage("grp.posting.enable");

        LOGGER.info("start GRP update Vendor Master posting flag is 'Y'. configured value of posting flag is : "
                + grpPostingFlag);

        if (grpPostingFlag.equals(MainetConstants.Common_Constant.YES)) {
            try {
                // System.out.println("Invoking update...");
                VendorMasterServer port = getVendorMasterServer();
                com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster _update__return = port
                        .update(vendorMaster);
                // System.out.println("update.result=" + _update__return);
            } catch (Exception e) {
                throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
            }
        }
    }

    /**
     * this common method is used to get vendor master server with
     * authentication,WSDL URL and WSDL file location.
     * 
     * @return VendorMasterServer.
     */
    private VendorMasterServer getVendorMasterServer() {
        VendorMasterServer vendorMasterServer = null;
        try {
            VendorMasterServer_Service vendorMasterServer_Service = new VendorMasterServer_Service(
                    VendorMasterServer_Service.WSDL_LOCATION, SERVICE_NAME);
            vendorMasterServer = vendorMasterServer_Service.getVendorMasterServer();
            Map<String, Object> requestctx = ((BindingProvider) vendorMasterServer).getRequestContext();

            // this WSDL URL is configured in serviceConfiguration.properties file.
            requestctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ServiceEndpoints.GRP_VENDOR_MASTER_WSDL_URL);

            Map<String, List<String>> headers = new HashMap<>();

            // this Authentication code is configured in serviceConfiguration.properties
            // file.
            headers.put(AUTHORIZATION,
                    Collections.singletonList(ApplicationSession.getInstance().getMessage("grp.authentication.code")));
            requestctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        } catch (Exception ex) {
            throw new FrameworkException(EMPLOYEE_SERVER_PREPARE_EXCEPTION + ex);
        }
        return vendorMasterServer;
    }

}
