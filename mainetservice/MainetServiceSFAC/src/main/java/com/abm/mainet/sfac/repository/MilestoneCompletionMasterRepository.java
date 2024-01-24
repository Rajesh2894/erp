package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MilestoneCompletionMasterEntity;

@Repository
public interface MilestoneCompletionMasterRepository extends JpaRepository<MilestoneCompletionMasterEntity, Long> {

	@Query("select ms.msId from MilestoneCompletionMasterEntity ms where ms.cbboId =:cbboId")
	List<Long> findByCbboId(@Param("cbboId")Long cbboId);

	MilestoneCompletionMasterEntity findByApplicationNumber(Long appNumber);

	List<MilestoneCompletionMasterEntity> findByCbboIdAndIaId(Long cbboId, Long iaId);

	List<MilestoneCompletionMasterEntity> findByCbboIdAndIaIdAndStatus(Long cbboId, Long iaId, String status);

}
