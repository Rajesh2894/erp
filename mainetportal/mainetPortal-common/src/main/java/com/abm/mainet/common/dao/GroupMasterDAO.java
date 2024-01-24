/**
 * 
 */
package com.abm.mainet.common.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.GroupMaster;

@Repository
public class GroupMasterDAO extends AbstractDAO<GroupMaster> implements Serializable, IGroupMasterDAO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(GroupMasterDAO.class);

    @Override
    public GroupMaster save(GroupMaster groupmaster) {
        GroupMaster groupMasterSavd = null;
        groupMasterSavd = this.entityManager.merge(groupmaster);
        return groupMasterSavd;
    }

    @Override
    public GroupMaster findByGmId(final Long gmId, final Long orgId) {
        GroupMaster groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.gmId=:gmId AND gm.orgId.orgid=:orgId");
        query.setParameter("gmId", gmId);
        query.setParameter("orgId", orgId);
        groupMaster = (GroupMaster) query.getSingleResult();
        return groupMaster;
    }

    @Override
    public GroupMaster getAdminGroup(Long orgId) {
        return getGroupMasterByGroupCode(orgId, "SUPER_ADMIN");
    }

    @Override
    public void update(Long gmId, String roleDescriptionEng, String roleDescriptionReg) {
        final Query query = createQuery(
                "UPDATE GroupMaster gm SET gm.grDescEng=?1,gm.grDescReg=?2 WHERE "
                        + "gm.gmId=?3");
        query.setParameter(1, roleDescriptionEng);
        query.setParameter(2, roleDescriptionReg);
        query.setParameter(3, gmId);
        query.executeUpdate();
    }

    @Override
    public GroupMaster getGroupMasterByGroupCode(Long orgId, String groupCode) {
        GroupMaster groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.grCode=:groupCode AND gm.orgId.orgid=:orgId");
        query.setParameter("orgId", orgId);
        query.setParameter("groupCode", groupCode);
        try {
            groupMaster = (GroupMaster) query.getSingleResult();
        } catch (Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return groupMaster;
        }
        return groupMaster;
    }
    
    @Override
    public List<GroupMaster> getGroupMasByGroupCode(String groupCode) {
        List<GroupMaster> groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.grCode=:groupCode ");
        query.setParameter("groupCode", groupCode);
        try {
            groupMaster = query.getResultList();
        } catch (Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return groupMaster;
        }
        return groupMaster;
    }

	@Override
	public List<GroupMaster> getAllGroupMast(Long orgId) {
		List<GroupMaster> groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.orgId.orgid=:orgId and gm.grStatus=:grStatus ");
        query.setParameter("orgId", orgId);
        query.setParameter("grStatus", MainetConstants.FlagA);
        
        try {
            groupMaster = query.getResultList();
        } catch (Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return groupMaster;
        }
        return groupMaster;
	}
}
