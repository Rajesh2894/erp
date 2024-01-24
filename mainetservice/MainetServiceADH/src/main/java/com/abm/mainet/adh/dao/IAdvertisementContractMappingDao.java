package com.abm.mainet.adh.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.master.dto.TbDepartment;

/**
 * @author cherupelli.srikanth
 * @since 15 November 2019
 */
public interface IAdvertisementContractMappingDao {

    List<ContractMastEntity> searchContractMappingData(Long orgId, String contNo, Date contDate, TbDepartment contDept);
}
