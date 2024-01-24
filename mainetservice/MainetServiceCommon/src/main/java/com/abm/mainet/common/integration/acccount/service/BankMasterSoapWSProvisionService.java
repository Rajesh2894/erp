package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.bankmaster.soap.jaxws.client.WSBankMaster;

public interface BankMasterSoapWSProvisionService {

	/**
     * this service is used to create bank master details.
     * @param WSBankMaster
     */
    void createBankMasterHead(WSBankMaster wSBankMaster);

    /**
     * this service is used to update bank master details.
     * @param WSBankMaster
     */
    void updateBankMasterHead(WSBankMaster wSBankMaster);
}
