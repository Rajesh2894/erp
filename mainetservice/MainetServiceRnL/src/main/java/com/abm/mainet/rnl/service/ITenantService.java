package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.rnl.dto.TenantMaster;
import com.abm.mainet.rnl.dto.TenantMasterGrid;

/**
 * @author ritesh.patil
 *
 */
public interface ITenantService {

    List<TenantMasterGrid> findAllOrgTenantRecords(Long orgId);

    TenantMaster findById(Long esId);

    String saveTenantMaster(TenantMaster tenantMaster, List<AttachDocs> attachDocs);

    boolean deleteRecord(Long id, Long empId);

    boolean saveEdit(TenantMaster tenantMaster, List<AttachDocs> attachDocs, List<Long> ids, List<Long> removeChildIds);

}
