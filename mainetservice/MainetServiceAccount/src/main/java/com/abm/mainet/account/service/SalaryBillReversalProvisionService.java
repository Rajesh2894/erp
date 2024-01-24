package com.abm.mainet.account.service;

public interface SalaryBillReversalProvisionService {

    /**
     * this service is used to updating salary bill deletion flag in property tax receipt entry table through SOAP WS.
     * @param ACBillMas
     */
    void updateSalaryBillReversalDelFlag(Long salBillRefId);
}
