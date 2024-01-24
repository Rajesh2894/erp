package com.abm.mainet.common.entitlement.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.entitlement.dao.IEntitlementDao;
import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
import com.abm.mainet.common.utility.LookUp;

@Service
public class EntitlementService implements IEntitlementService {

    @Autowired
    IEntitlementDao iEntitlementDao;

    @Override
    @Transactional(readOnly = true)
    public Set<SystemModuleFunction> getMenuMasterList(final String orderByClause) throws RuntimeException {

        return iEntitlementDao.getMenuMasterList(orderByClause);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemModuleFunction findById(final Long id) throws RuntimeException {

        return iEntitlementDao.findByTmId(id);
    }

    @Override
    @Transactional
    public boolean save(final GroupMaster groupMaster) throws RuntimeException {

        return iEntitlementDao.save(groupMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RoleEntitlement> getExistTemplateParent(final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        return iEntitlementDao.getExistTemplateParent(roleId, flagList, orgId, orderByClause);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RoleEntitlement> getExistTemplateChild(final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        return iEntitlementDao.getExistTemplateChild(roleId, flagList, orgId, orderByClause);
    }
    //119534 calling the method which is returning the data base on smParam flag
    @Override
    @Transactional(readOnly = true)
    public Set<RoleEntitlement> getExistTemplateChildByFlag(final Long roleId, final List<String> flagList, final Long orgId,
            final String orderByClause) throws RuntimeException {

        return iEntitlementDao.getExistTemplateChildByFlag(roleId, flagList, orgId, orderByClause);
    }

    @Override
    @Transactional
    public boolean saveEntitlement(final SystemModuleFunction entitlement) throws RuntimeException {

        return iEntitlementDao.saveNode(entitlement);
    }

    @Override
    @Transactional
    public boolean saveRoleEntitlement(final RoleEntitlement entitlement) throws RuntimeException {
        return iEntitlementDao.saveRoleEntitlement(entitlement);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleEntitlement findByRoleAndEntitleId(final Long roleId, final Long entitleId) throws RuntimeException {
        return iEntitlementDao.findByRoleAndEntitleId(roleId, entitleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> getLinks(final Long roleId, final List<String> flagList, final Long orgId, final String orderByClause) {
        return iEntitlementDao.getLinks(roleId, flagList, orgId, orderByClause);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemModuleFunction> getSearchdata(final Long gmId, final String text, final Long orgId) {
        return iEntitlementDao.getSearchdata(gmId, text, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getGroupList(final Long orgId) {

        return iEntitlementDao.getGroupList(orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getGroupIdByName(final String groupCode, final Long orgId) {
        return iEntitlementDao.getGroupId(groupCode, orgId);
    }

    @Override
    @Transactional
    public SystemModuleFunction updateEntitlement(final SystemModuleFunction data) {
        return iEntitlementDao.updateNode(data);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getRoleCodeFromGrpMst(final long orgid, final String roleCode) {
        return iEntitlementDao.getRoleCodeFromGrpMst(orgid, roleCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMaster> getGroupListForNewActiveNode(final long orgid) {
        return iEntitlementDao.getGroupListForNewActiveNode(orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getRoleEntitleIds(final Long roleId, final long orgid) {
        return iEntitlementDao.getRoleEntitleIds(roleId, orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntitlement> getExistTemplateChild(final Long editedRoleId, final long orgid) {
        return iEntitlementDao.getExistTemplateChild(editedRoleId, orgid);
    }

    @Override
    @Transactional
    public void updateInactiveMenu(final List<Long> parentids, final long orgid,long roleId) {
        iEntitlementDao.getExistTemplateChild(parentids, orgid,roleId);
    }

    @Override
    @Transactional
    public void activeNewNode(final Set<RoleEntitlement> roleEntitlements) {
        iEntitlementDao.getExistTemplateChild(roleEntitlements);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.service.IEntitlementService# updateRecord(java.util.List,
     * java.util.List)
     */
    @Override
    @Transactional
    public boolean updateRecord(final List<String> addList, final List<String> removeList) {

        return iEntitlementDao.updateRecord(addList, removeList);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.service.IEntitlementService# findByRoleId(java.lang.Long)
     */
    @Override
    public GroupMaster findByRoleId(final Long gmId, final long orgId) {
        return iEntitlementDao.findByRoleId(gmId, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.service.IEntitlementService# findAllRolesByDept(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public List<Object[]> findAllRolesByOrg(final Long orgId) {
        return iEntitlementDao.findAllRolesByOrg(orgId);
    }

}
