/**
 * 
 */
package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.GroupMaster;

/**
 * @author Harsha.Ramachandran
 *
 */
public interface IGroupMasterService {

    public GroupMaster create(GroupMaster groupMaster);

    GroupMaster findByGmId(Long gmId, Long orgId);

    GroupMaster getAdminGroupByOrg(Long orgId);

    void update(Long gmId, String roleDescriptionEng, String roleDescriptionReg);

    GroupMaster getGroupMasterByGroupCode(Long orgId, String groupCode);

    List<GroupMaster> getGroupMasByGroupCode(String groupCode);
    
    List<GroupMaster> getAllGroupMast(Long orgId);
}
