/**
 *
 */
package com.abm.mainet.common.integration.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;

/**
 * @author ritesh.patil
 *
 */

public interface AttachDocsRepository extends JpaRepository<AttachDocs, Long> {

    @Query("FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId = ?2 and a.status= ?3")
    List<AttachDocs> findByCode(Long orgId, String estateCode, String status);

    @Query("FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId in (?2) and a.status= ?3")
    List<AttachDocs> findByIdfInQuery(Long orgId, List<String> IDFID, String status);

    @Modifying
    @Query("update AttachDocs a set a.status = ?1,a.updatedBy = ?2,a.updatedDate = CURRENT_DATE where a.attId = ?3")
    void deleteRecord(String flag, Long empId, Long id);

    @Modifying
    @Query("update AttachDocs a set a.status = ?2,a.updatedDate = CURRENT_DATE where a.idfId = ?1")
    int updateMasterDocumentStatus(String uniqueId, String status);

    @Query("FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId like ?2 and a.status= ?3")
    List<AttachDocs> findAllDocLikeReferenceId(Long orgId, String referenceId, String status);

    @Query("FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId like ?2 and a.status= ?3")
	AttachDocs findDocLikeReferenceId(Long orgId, String identifier, String flaga);
    
    @Modifying
	@Transactional
    @Query("delete FROM AttachDocs a WHERE a.orgid= ?1 and a.idfId like ?2 and a.status= ?3")
    void deleteDocLikeReferenceId(Long orgId, String identifier, String flaga);

}
