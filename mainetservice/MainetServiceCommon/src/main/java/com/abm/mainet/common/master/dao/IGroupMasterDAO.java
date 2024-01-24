package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Harsha.Ramachandran
 *
 */
public interface IGroupMasterDAO {

    public abstract GroupMaster createGroupMaster(GroupMaster groupMaster, Organisation organisation);

    public abstract GroupMaster findByGmId(Long gmId, Long orgId);
    
    public abstract GroupMaster getAdminGroup(Long orgId);
    
    public abstract void update(Long gmId,String roleDescriptionEng,String roleDescriptionReg);
    
    public List<GroupMaster> getGmIdyGrCode(String gmCode, Long orgId);

	public abstract List<GroupMaster> getRoles(long orgid, Long deptId);

}
