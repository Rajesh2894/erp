
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.abm.mainet.common.integration.hrms.jaxws.client;

import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 3.1.14
 * 2018-08-17T19:53:02.381+05:30
 * Generated source version: 3.1.14
 * 
 */

@javax.jws.WebService(serviceName = "Org_MasterServer", portName = "Org_MasterServer", targetNamespace = "urn:orangescape", wsdlLocation = "file:/D:/Dev_Service/MainetServiceWeb/src/main/resources/organisation.wsdl", endpointInterface = "com.abm.mainet.common.integration.hrms.jaxws.client.OrgMasterServer")

public class Org_MasterServerImpl implements OrgMasterServer {

    private static final Logger LOG = Logger.getLogger(Org_MasterServerImpl.class.getName());

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.common.integration.hrms.jaxws.client.OrgMasterServer#update(com.abm.mainet.common.integration.hrms.jaxws.
     * client.OrgMasterUp parameters)*
     */
    public void update(javax.xml.ws.Holder<OrgMaster> parameters) {
        LOG.info("Executing operation update");
        System.out.println(parameters.value);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.common.integration.hrms.jaxws.client.OrgMasterServer#submit(com.abm.mainet.common.integration.hrms.jaxws.
     * client.OrgMaster parameters)*
     */
    public void submit(javax.xml.ws.Holder<OrgMaster> parameters) {
        LOG.info("Executing operation submit");
        System.out.println(parameters.value);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
