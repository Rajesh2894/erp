package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Harsha.Ramachandran
 */

public interface GroupMasterService {

    public GroupMaster createGroupMaster(GroupMaster groupMaster, Organisation organisation);

    public GroupMaster findByGmId(Long gmId, Long orgId);
    
    public GroupMaster getAdminGroupByOrg(Long orgId);
    
    public void update(Long gmId,String roleDescriptionEng,String roleDescriptionReg);
    
    public List<GroupMaster> getGmIdyGrCode(String gmCode, Long orgId);

	public List<GroupMaster> getRoles(long orgid, Long deptId);

}
