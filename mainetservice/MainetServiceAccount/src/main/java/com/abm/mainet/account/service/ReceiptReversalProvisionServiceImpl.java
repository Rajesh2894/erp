package com.abm.mainet.account.service;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.propertytax.soap.jaxws.client.ReceiptMasterServer;

@Service
public class ReceiptReversalProvisionServiceImpl implements ReceiptReversalProvisionService {

    private static final Logger LOGGER = Logger.getLogger(ReceiptReversalProvisionServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "ReceiptMasterServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Receipt Reversal Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";

    public ReceiptReversalProvisionServiceImpl() {
    }

    /**
     * this service is used to update receipt reversal Flag 'Y'
     * 
     * @param receiptReversalBean.
     */
    @Override
    public void updateReceiptReversalDelFlag(Long recRefId) {

        /**
         * it is used to push data to Propert tax environment if receipt reversal posting Mainet to Property tax. all
         * configuration data is configured in serviceConfiguration.properties file.
         */
        LOGGER.info("Update Property tax Receipt reversal deletion flag is 'Y' to posting...");

        try {
        } catch (Exception e) {
            throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
        }
    }

    /**
     * this common method is used to get receipt reversal server with authentication,WSDL URL and WSDL file location.
     * @return ReceiptMasterServer.
     */
    private ReceiptMasterServer getReceiptMasterServer() {
        ReceiptMasterServer recRevServer = null;
        try {
        } catch (Exception ex) {
            throw new FrameworkException(EMPLOYEE_SERVER_PREPARE_EXCEPTION + ex);
        }
        return recRevServer;
    }

}
