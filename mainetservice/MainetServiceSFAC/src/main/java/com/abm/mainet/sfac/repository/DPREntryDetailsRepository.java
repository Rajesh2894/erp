package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.DPREntryDetailsEntity;
import com.abm.mainet.sfac.domain.DPREntryMasterEntity;

@Repository
public interface DPREntryDetailsRepository extends JpaRepository<DPREntryDetailsEntity, Long>{

	List<DPREntryDetailsEntity> findByDprEntryMasterEntity(DPREntryMasterEntity dprEntryMasterEntity);

	@Modifying
	@Query("UPDATE DPREntryDetailsEntity d SET d.docStatus ='D', d.updatedBy =:updatedBy, d.updatedDate = CURRENT_DATE "
			+ "WHERE d.dprdId in (:removeIds) ")
	void deActiveBPInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

}
