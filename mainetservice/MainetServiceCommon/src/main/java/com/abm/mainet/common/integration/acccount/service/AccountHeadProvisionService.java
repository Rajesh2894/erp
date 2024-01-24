package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.soap.jaxws.client.AccountHead;

public interface AccountHeadProvisionService {

	/**
     * this service is used to create account head details.
     * @param account_head
     */
    void createAccountHead(AccountHead accountHead);

    /**
     * this service is used to update account head details.
     * @param account_head
     */
    void updateAccountHead(AccountHead accountHead);
    
}
