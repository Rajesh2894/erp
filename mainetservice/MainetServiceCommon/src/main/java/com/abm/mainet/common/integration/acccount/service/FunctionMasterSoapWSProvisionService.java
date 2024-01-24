package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.functionmaster.soap.jaxws.client.Functionmaster;

public interface FunctionMasterSoapWSProvisionService {

	/**
     * this service is used to create function master details.
     * @param Functionmaster
     */
    void createFunctionMaster(Functionmaster functionmaster);

    /**
     * this service is used to update function master details.
     * @param Functionmaster
     */
    void updateFunctionMaster(Functionmaster functionmaster);
}
