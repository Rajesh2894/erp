package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client.BankAccount;

public interface BankAccountSoapWSProvisionService {

	/**
     * this service is used to create bank account head details.
     * @param BankAccount
     */
    void createBankAccountHead(BankAccount bankAccount);

    /**
     * this service is used to update bank account details.
     * @param BankAccount
     */
    void updateBankAccountHead(BankAccount bankAccount);
    
}
