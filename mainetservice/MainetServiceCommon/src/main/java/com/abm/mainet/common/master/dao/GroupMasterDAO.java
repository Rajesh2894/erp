package com.abm.mainet.common.master.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Harsha.Ramachandran
 *
 */

@Repository
public class GroupMasterDAO extends AbstractDAO<GroupMaster> implements Serializable, IGroupMasterDAO {

    private static final long serialVersionUID = 1L;

    @Override
    public GroupMaster createGroupMaster(final GroupMaster groupMaster,
            final Organisation organisation) {

        try {
            entityManager.persist(groupMaster);
            // return groupMaster;
        } catch (final Exception ex) {
            throw new FrameworkException("Exception while creating group master :", ex);
        }
        return groupMaster;

    }

    @Override
    public GroupMaster findByGmId(final Long gmId, final Long orgId) {
        GroupMaster groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.gmId=:gmId AND gm.orgId.orgid=:orgId");
        query.setParameter("gmId", gmId);
        query.setParameter("orgId", orgId);
        try {
        groupMaster = (GroupMaster) query.getSingleResult();
        }
        catch (Exception e) {
		}
        return groupMaster;
    }

    @Override
    public GroupMaster getAdminGroup(Long orgId) {
        GroupMaster groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.grCode='SUPER_ADMIN' AND gm.orgId.orgid=:orgId");
        query.setParameter("orgId", orgId);
        groupMaster = (GroupMaster) query.getSingleResult();
        return groupMaster;
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
    public List<GroupMaster> getRoles(long orgId, Long deptId) {
    	List<GroupMaster> groupMaster = null;
        final Query query = entityManager
                .createQuery("select gm from GroupMaster gm where gm.orgId.orgid=:orgId and gm.dpDeptId=:deptId and gm.grStatus='A'");
        query.setParameter("orgId", orgId);
        query.setParameter("deptId", deptId);
        groupMaster = (List<GroupMaster>) query.getResultList();
        return groupMaster;
    }

    @Override
	public List<GroupMaster> getGmIdyGrCode(String gmCode, Long orgId) {

    	List<GroupMaster> groupMaster = null;
        final Query query = entityManager
                .createQuery("SELECT gm FROM GroupMaster gm WHERE gm.grCode=:grCode and  gm.orgId.orgid=:orgId");
        query.setParameter("grCode", gmCode);
        query.setParameter("orgId", orgId);
        try {
            groupMaster = (List<GroupMaster>) query.getResultList();
            }
            catch (Exception e) {
    		}
        return groupMaster;
    
	}
}
