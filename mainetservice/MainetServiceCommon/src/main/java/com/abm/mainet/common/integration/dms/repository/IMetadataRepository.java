package com.abm.mainet.common.integration.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;

@Repository
public interface IMetadataRepository extends JpaRepository<DmsDocsMetadata, Long> {

	@Query("select max(md.dmsId)+1 from DmsDocsMetadata md where md.orgId.orgid=:orgid")
	public Long getMaxCount(@Param("orgid") Long orgid);

	@Modifying
	@Query("update DmsDocsMetadata ds set ds.wfStatus=:wfStatus , ds.remark=:remark where ds.seqNo=:seqNo and  ds.orgId.orgid=:orgid")
	public void updateWfStatus(@Param("seqNo") String seqNo, @Param("remark") String remark,
			@Param("wfStatus") String wfStatus, @Param("orgid") Long orgid);
	
	@Modifying
    @Query(value = "delete from DmsDocsMetadataDet ds where ds.dmsDocsMetadata.dmsId=:dmsId")
    void deleteByDmsId(@Param("dmsId") Long dmsId);

}
