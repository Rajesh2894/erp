package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FundReleaseRequestDetailEntity;
import com.abm.mainet.sfac.domain.FundReleaseRequestMasterEntity;

@Repository
public interface FundReleaseRequestDetailRepository extends JpaRepository<FundReleaseRequestDetailEntity, Long> {

	List<FundReleaseRequestDetailEntity> findByFundReleaseRequestMasterEntity(FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity);

	@Modifying
	@Query("UPDATE FundReleaseRequestDetailEntity d SET d.status ='D', d.updatedBy =:updatedBy, d.updatedDate = CURRENT_DATE "
			+ "WHERE d.frrdId in (:removeIds) ")
	void deActiveBPInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

}
