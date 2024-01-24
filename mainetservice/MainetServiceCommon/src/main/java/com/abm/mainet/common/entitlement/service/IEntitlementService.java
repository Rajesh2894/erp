package com.abm.mainet.common.entitlement.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
import com.abm.mainet.common.utility.LookUp;

public interface IEntitlementService {

    Set<SystemModuleFunction> getMenuMasterList(String orderByClause);

    SystemModuleFunction findById(Long id);

    boolean save(GroupMaster groupMaster);

    Set<RoleEntitlement> getExistTemplateParent(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    Set<RoleEntitlement> getExistTemplateChild(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    boolean saveEntitlement(SystemModuleFunction entitlement);

    boolean saveRoleEntitlement(RoleEntitlement entitlement);

    RoleEntitlement findByRoleAndEntitleId(Long roleId, Long entitleId);

    List<LookUp> getLinks(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    List<SystemModuleFunction> getSearchdata(Long gmId, String text, Long orgId);

    public Map<Long, String> getGroupList(Long orgId);

    public Long getGroupIdByName(String groupCode, Long orgId);

    SystemModuleFunction updateEntitlement(SystemModuleFunction data);

    boolean getRoleCodeFromGrpMst(long orgid, String roleCode);

    List<GroupMaster> getGroupListForNewActiveNode(long orgid);

    Map<Long, String> getRoleEntitleIds(Long roleId, long orgid);

    List<RoleEntitlement> getExistTemplateChild(Long editedRoleId, long orgid);

    void updateInactiveMenu(List<Long> parentids, long orgid, long roleId);

    void activeNewNode(Set<RoleEntitlement> roleEntitlements);

    boolean updateRecord(List<String> addList, List<String> removeList);

    GroupMaster findByRoleId(Long gmId, long orgId);

    List<Object[]> findAllRolesByOrg(Long orgId);
    //119534
	Set<RoleEntitlement> getExistTemplateChildByFlag(Long roleId, List<String> flagList, Long orgId,
			String orderByClause) throws RuntimeException;
}
