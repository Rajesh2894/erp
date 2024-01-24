/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CommitteeMemberMasterEntity;
import com.abm.mainet.sfac.dto.CommitteeMemberMasterDto;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface CommitteeMemberRepository extends JpaRepository<CommitteeMemberMasterEntity, Long> {

	/**
	 * @param orgId
	 * @return
	 */
	@Query("Select c from CommitteeMemberMasterEntity c where c.orgId=:orgId")
	List<CommitteeMemberMasterEntity> findAllCommitteeDet(@Param("orgId") Long orgId);

	/**
	 * @param comMemberId
	 * @return
	 */
	@Query("Select c from CommitteeMemberMasterEntity c where c.comMemberId=:comMemberId")
	CommitteeMemberMasterEntity findById(@Param("comMemberId") Long comMemberId);

	/**
	 * @param meetingTypeId
	 * @return
	 */
	@Query("Select c from CommitteeMemberMasterEntity c where c.committeeTypeId=:meetingTypeId")
	List<CommitteeMemberMasterEntity> getCommitteeMemDetByComTypeId(@Param("meetingTypeId") Long meetingTypeId);

}
