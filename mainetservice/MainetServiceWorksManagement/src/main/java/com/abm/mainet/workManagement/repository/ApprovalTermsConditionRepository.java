package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.ApprovalTermsCondition;

@Repository
public interface ApprovalTermsConditionRepository extends CrudRepository<ApprovalTermsCondition, Long> {

	/**
	 * used to get all terms by reference number and orgId
	 * 
	 * @param referenceId
	 * @param orgId
	 * @return
	 */
	@Query("select tc from ApprovalTermsCondition tc where tc.refId=:refId and tc.orgId=:orgId")
	List<ApprovalTermsCondition> getTermsList(@Param("refId") String referenceId, @Param("orgId") Long orgId);

	/**
	 * In active terms And Condition With Id
	 * 
	 * @param deletedIds
	 * @param updatedBy
	 */

	@Modifying
	@Query("UPDATE ApprovalTermsCondition atc SET atc.termActive ='I', atc.updatedBy =:updatedBy, atc.updatedDate = CURRENT_DATE "
			+ "WHERE atc.teId in (:removeId) ")
	void inActiveIds(@Param("removeId") List<Long> deletedIds, @Param("updatedBy") Long updatedBy);

	/**
	 * update Sanction Number with given identifier
	 * 
	 * @param sanctionNumber
	 * @param workId
	 * @param workEstimateNo
	 */
	@Modifying
	@Query("UPDATE ApprovalTermsCondition atc SET atc.workSancNo =:sanctionNumber where atc.workId =:workId "
			+ "and atc.refId=:workEstimateNo and atc.workSancNo is null ")
	void updateSanctionNumber(@Param("sanctionNumber") String sanctionNumber, @Param("workId") Long workId,
			@Param("workEstimateNo") String workEstimateNo);

	/**
	 * find All Terms And Condition with the Help of WorkId And Sanction Number
	 * 
	 * @param workId
	 * @param sanctionNumber
	 * @return
	 */
	@Query("Select atc from ApprovalTermsCondition atc where atc.workId =:workId and atc.workSancNo =:sanctionNumber ")
	List<ApprovalTermsCondition> findAllTermsAndCondition(@Param("workId") Long workId,
			@Param("sanctionNumber") String sanctionNumber);

}
