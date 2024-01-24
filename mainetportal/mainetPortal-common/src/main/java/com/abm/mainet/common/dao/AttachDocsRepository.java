/**
 *
 */
package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.AttachDocs;

/**
 * @author ritesh.patil
 *
 */

public interface AttachDocsRepository extends JpaRepository<AttachDocs, Long> {

    @Query("FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId = ?2 and a.status= ?3")
    List<AttachDocs> findByCode(Long orgId, String estateCode, String status);

    @Modifying
    @Query("update AttachDocs a set a.status = ?1,a.updatedBy = ?2,a.updatedDate = CURRENT_DATE where a.attId = ?3")
    void deleteRecord(String flag, Long empId, Long id);

    @Modifying
    @Query("update AttachDocs a set a.status = ?2,a.updatedDate = CURRENT_DATE where a.idfId = ?1")
    int updateMasterDocumentStatus(String uniqueId, String status);

    @Modifying
    @Query(" UPDATE AttachDocs a SET a.status = :flag , a.updatedBy = :updatedBy , a.updatedDate = CURRENT_DATE where a.attId in :attList ")
    int updateRecord(@Param("attList") final List<Long> ids, @Param("updatedBy") final Long empId,
            @Param("flag") final String flag);

}
