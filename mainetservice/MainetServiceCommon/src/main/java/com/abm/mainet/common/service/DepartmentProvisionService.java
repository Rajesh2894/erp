package com.abm.mainet.common.service;

import com.abm.mainet.common.master.dto.TbDeporgMap;

/**
 * common web service interface to push data on client application
 * @author hiren.poriya
 * @Since 10-May-2018
 */
public interface DepartmentProvisionService {

    /**
     * used to post created department details on client environment using web service.
     * @param Department Entity
     */
    void createDepartment(TbDeporgMap mapDTO);

    /**
     * used to post update department details on client environment using web service.
     * @param Department Entity
     */
    void updateDepartment(TbDeporgMap mapDTO);

}
