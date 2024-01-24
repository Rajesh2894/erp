/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.CommitteeMemberMasterEntity;

/**
 * @author pooja.maske
 *
 */

public interface CommitteeMemberDao {

	/**
	 * @param committeeTypeId
	 * @param comMemberId
	 * @param orgId
	 * @return
	 */
	List<CommitteeMemberMasterEntity> getDetByCommiteeIdAndName(Long committeeTypeId, Long comMemberId, Long orgId);

}
