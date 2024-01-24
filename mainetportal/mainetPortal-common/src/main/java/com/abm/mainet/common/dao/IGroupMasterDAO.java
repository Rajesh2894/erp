package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.GroupMaster;

public interface IGroupMasterDAO {

    /**
     * To save or update {@link GroupMaster}
     * @param GroupMaster
     * @return true if success else false.
     */
    public abstract GroupMaster save(GroupMaster groupmaster);

    GroupMaster findByGmId(Long gmId, Long orgId);

    GroupMaster getAdminGroup(Long orgId);

    void update(Long gmId, String roleDescriptionEng, String roleDescriptionReg);

    GroupMaster getGroupMasterByGroupCode(Long orgId, String groupCode);

	List<GroupMaster> getGroupMasByGroupCode(String groupCode);
	
	List<GroupMaster> getAllGroupMast(Long orgId);

}