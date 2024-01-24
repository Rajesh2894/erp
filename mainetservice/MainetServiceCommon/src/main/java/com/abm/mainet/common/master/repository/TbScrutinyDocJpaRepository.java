/**
 *
 */
package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public interface TbScrutinyDocJpaRepository extends CrudRepository<CFCAttachment, Long> {
	
	@Query("SELECT ac FROM CFCAttachment ac WHERE ac.clmId IN (SELECT g.dgId FROM TbDocumentGroupEntity g WHERE g.groupCpdId = (SELECT cpd.cpdId\r\n" + 
			"FROM TbComparamDetEntity cpd WHERE cpd.cpdValue =:groupCode AND cpd.tbComparamMas.cpmId IN (SELECT cpm.cpmId FROM TbComparamMasEntity cpm WHERE cpm.cpmPrefix = 'CLG'))) AND applicationId=:applicationId")
    public List<CFCAttachment> getDocumentUploadedListByGroupId( @Param("groupCode") String groupCode,@Param("applicationId")Long applicationId);
	
	

}
