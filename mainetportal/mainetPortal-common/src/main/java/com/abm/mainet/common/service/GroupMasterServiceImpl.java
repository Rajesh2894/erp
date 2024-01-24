/**
 * 
 */
package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.IGroupMasterDAO;
import com.abm.mainet.common.domain.GroupMaster;

/**
 * @author Harsha.Ramachandran
 *
 */
@Service
public class GroupMasterServiceImpl implements IGroupMasterService {

    @Autowired
    private IGroupMasterDAO igroupMasterDao;

    @Override
    @Transactional
    public GroupMaster create(GroupMaster groupMaster) {
        GroupMaster groupMasSaved = null;
        groupMasSaved = igroupMasterDao.save(groupMaster);
        return groupMasSaved;
    }

    @Override
    public GroupMaster findByGmId(final Long gmId, final Long orgId) {
        return igroupMasterDao.findByGmId(gmId, orgId);
    }

    @Override
    public GroupMaster getAdminGroupByOrg(Long orgId) {
        return igroupMasterDao.getAdminGroup(orgId);
    }

    @Override
    @Transactional
    public void update(Long gmId, String roleDescriptionEng, String roleDescriptionReg) {
        igroupMasterDao.update(gmId, roleDescriptionEng, roleDescriptionReg);
    }

    @Override
    public GroupMaster getGroupMasterByGroupCode(Long orgId, String groupCode) {
        return igroupMasterDao.getGroupMasterByGroupCode(orgId, groupCode);
    }

	@Override
	public List<GroupMaster> getGroupMasByGroupCode(String groupCode) {
		 return igroupMasterDao.getGroupMasByGroupCode(groupCode);
	}

	@Override
	public List<GroupMaster> getAllGroupMast(Long orgId) {
		 return igroupMasterDao.getAllGroupMast(orgId);
	}

}
