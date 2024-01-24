package com.abm.mainet.rnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.abm.mainet.rnl.domain.TenantEntity;

/**
 * @author ritesh.patil
 *
 */
public interface TenantRepository extends CrudRepository<TenantEntity, Long> {

    @Query("select t.tntId,t.code,t.type,t.fName,t.lName,t.mobileNumber from TenantEntity t where t.orgId =?1 and t.isActive = ?2")
    List<Object[]> findAllTenantRecords(Long orgId, Character activeFlag);

    @Modifying
    @Query("update TenantEntity e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.tntId = ?3")
    void deleteRecord(Character flag, Long empId, Long id);

    @Modifying
    @Query("update TenantOwnerEntity e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.tenantEntity.tntId = ?3")
    void deleteRecordOwner(Character flag, Long empId, Long id);

    @Modifying
    @Query("update TenantOwnerEntity e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.tntOwnerId in ?3")
    void deleteRecordDetails(Character flag, Long empId, List<Long> id);

}
