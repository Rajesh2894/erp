package com.abm.mainet.account.service;

public interface ReceiptReversalProvisionService {

    /**
     * this service is used to updating receipt deletion flag in property tax receipt entry table through SOAP WS.
     * @param ReceiptMaster
     */
    void updateReceiptReversalDelFlag(Long recRefId);

}
