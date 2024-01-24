package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client.VendorMaster;

public interface VendorMasterSoapWSProvisionService {

	/**
     * this service is used to create vendor master head details.
     * @param VendorMaster
     */
    void createVendorMaster(VendorMaster vendorMaster);

    /**
     * this service is used to update vendor master details.
     * @param VendorMaster
     */
    void updateVendormaster(VendorMaster vendorMaster);
    
}
