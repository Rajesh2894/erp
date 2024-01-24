package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.checklist.domain.TbDocumentGroupEntity;

/**
 * Repository : TbDocumentGroup.
 */
public interface TbDocumentGroupRepository extends PagingAndSortingRepository<TbDocumentGroupEntity, Long> {

    @Query("SELECT group  FROM TbDocumentGroupEntity AS group WHERE group.tbOrganisation.orgid=:orgId AND group.groupCpdId=:groupCpdId and group.docStatus='A' order by group.docSrNo ASC")
    List<TbDocumentGroupEntity> findAllByGroupIdIdOrgId(@Param("orgId") Long orgId, @Param("groupCpdId") Long groupId);

    @Query("SELECT cl FROM TbDocumentGroupEntity cl,TbComparamDetEntity cp WHERE  cl.docStatus='A' AND cl.tbOrganisation.orgid=:orgId "
            + " and cl.groupCpdId=cp.cpdId and  cp.cpdValue in (:docGroup)"
            + " and cp.cpdStatus='A' ORDER BY cl.docSrNo ASC ")
    List<TbDocumentGroupEntity> fetchCheckListByDocumentGroup(@Param("docGroup") List<String> docGroupList,
            @Param("orgId") long orgId);

    @Query("SELECT TbDocumentGroup.docName,TbDocumentGroup.docNameReg,TbDocumentGroup.docType,TbDocumentGroup.docTypeReg,TbDocumentGroup.docSize,"
            + "TbDocumentGroup.ccmValueset,TbDocumentGroup.docSrNo,TbDocumentGroup.dgId,TbDocumentGroup.docPrefixRequired,TbDocumentGroup.prefixName  FROM TbDocumentGroupEntity TbDocumentGroup WHERE TbDocumentGroup.groupCpdId=:groupId"
            + " AND TbDocumentGroup.docStatus='A' " + "AND TbDocumentGroup.tbOrganisation.orgid=:orgId"
            + " order by TbDocumentGroup.docSrNo ASC")
    List<Object> getDocumentDetails(@Param("orgId") Long orgId, @Param("groupId") Long groupId);
}
