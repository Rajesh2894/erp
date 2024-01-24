/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.CommitteeMemberMasterDto;

/**
 * @author pooja.maske
 *
 */
public interface CommitteeMemberService {

	/**
	 * @param mastDto
	 * @return
	 */
	CommitteeMemberMasterDto saveCommitteeMemDetails(CommitteeMemberMasterDto mastDto);

	/**
	 * @param mastDto
	 * @return
	 */
	CommitteeMemberMasterDto updateCommitteeMemDetails(CommitteeMemberMasterDto mastDto);

	/**
	 * @return
	 */
	List<CommitteeMemberMasterDto> findAllCommitteeDet(Long orgId);

	/**
	 * @param committeeTypeId
	 * @param comMemberId
	 * @param orgid
	 * @return
	 */
	List<CommitteeMemberMasterDto> getDetByCommiteeIdAndName(Long committeeTypeId, Long comMemberId, Long orgId);

	/**
	 * @param comMemberId
	 * @param orgId
	 * @return
	 */
	CommitteeMemberMasterDto findById(Long comMemberId);

}
