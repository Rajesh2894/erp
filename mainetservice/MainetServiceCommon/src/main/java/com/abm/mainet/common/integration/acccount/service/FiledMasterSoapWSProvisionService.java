package com.abm.mainet.common.integration.acccount.service;

import com.abm.mainet.common.integration.acccount.hrms.fieldmaster.soap.jaxws.client.FieldMaster;

public interface FiledMasterSoapWSProvisionService {

	/**
     * this service is used to create field master details.
     * @param FieldMaster
     */
    void createFiledMaster(FieldMaster fieldMaster);

    /**
     * this service is used to update field master details.
     * @param FieldMaster
     */
    void updateFiledMaster(FieldMaster fieldMaster);
}
