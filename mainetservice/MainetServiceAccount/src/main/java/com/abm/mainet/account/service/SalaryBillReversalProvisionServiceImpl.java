package com.abm.mainet.account.service;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.propertytax.soap.jaxws.salarybill.client.ACBillMasServer;

@Service
public class SalaryBillReversalProvisionServiceImpl implements SalaryBillReversalProvisionService {

    private static final Logger LOGGER = Logger.getLogger(SalaryBillReversalProvisionServiceImpl.class);

    private static final QName SERVICE_NAME = new QName("urn:orangescape", "AC_Bill_MasServer");
    private static final String AUTHORIZATION = "Authorization";
    private static final String EMPLOYEE_SERVER_PREPARE_EXCEPTION = "Exception while preparing Salary Bill Reversal Server with WSDL WRL, WSDL file location and Authentication : ";
    private static final String GRP_EMP_POSTING_EXCEPTION = "Exception while posting data to Property Tax :";

    public SalaryBillReversalProvisionServiceImpl() {
    }

    /**
     * this service is used to update salary bill reversal Flag 'Y'
     * 
     * @param receiptReversalBean.
     */
    @Override
    public void updateSalaryBillReversalDelFlag(Long salBillRefId) {

        /**
         * it is used to push data to Propert tax environment if salary bill reversal posting Mainet to Property tax. all
         * configuration data is configured in serviceConfiguration.properties file.
         */
        LOGGER.info("Update Property tax Salary bill reversal deletion flag is 'Y' to posting...");

        try {
        } catch (Exception e) {
            throw new FrameworkException(GRP_EMP_POSTING_EXCEPTION + e);
        }
    }

    /**
     * this common method is used to get salary bill reversal server with authentication,WSDL URL and WSDL file location.
     * 
     * @return ACBillMasServer.
     */
    private ACBillMasServer getACBillMasServer() {
        ACBillMasServer aCBillMasServer = null;
        try {
        } catch (Exception ex) {
            throw new FrameworkException(EMPLOYEE_SERVER_PREPARE_EXCEPTION + ex);
        }
        return aCBillMasServer;
    }

}
