package com.abm.mainet.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.util.LookUp;

public interface IEntitlementDao {
    Set<SystemModuleFunction> getMenuMasterList(String orderByClause);

    SystemModuleFunction findByTmId(Long tmId);

    boolean save(GroupMaster groupMaster);

    Set<RoleEntitlement> getExistTemplateParent(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    Set<RoleEntitlement> getExistTemplateChild(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    boolean saveNode(SystemModuleFunction entitlement);

    boolean saveRoleEntitlement(RoleEntitlement entitlement);

    RoleEntitlement findByRoleAndEntitleId(Long roleId, Long entitleId);

    List<LookUp> getLinks(Long roleId, List<String> flagList, Long orgId, String orderByClause);

    List<SystemModuleFunction> getSearchdata(Long gmId, String text, Long orgId);

    Map<Long, String> getGroupList(Long orgId);

    Long getGroupId(String groupCode, Long orgId);

    SystemModuleFunction updateNode(SystemModuleFunction data);

    boolean getRoleCodeFromGrpMst(long orgid, String roleCode);

    List<GroupMaster> getGroupListForNewActiveNode(long orgid);

    Map<Long, String> getRoleEntitleIds(Long roleId, long orgid);

    List<RoleEntitlement> getExistTemplateChild(Long editedRoleId, long orgid);

    void getExistTemplateChild(List<Long> parentids, long orgid,long roleId);

    void getExistTemplateChild(Set<RoleEntitlement> roleEntitlements);

    boolean updateRecord(List<String> addList, List<String> removeList);
	List<SystemModuleFunction> findBySmfaction(String smfaction,String smfname);

	String getGroupCodeById(Long gmid, Long orgid);
}
