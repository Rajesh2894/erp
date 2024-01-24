package com.abm.mainet.cms.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
@Scope("session")
public class EntitlementModel extends AbstractEntryFormModel<GroupMaster> {

    private static final long serialVersionUID = 1L;

    private Set<RoleEntitlement> listSystems = new LinkedHashSet<>(
            0);
    private Set<RoleEntitlement> listModules = new LinkedHashSet<>(
            0);
    private Set<SystemModuleFunction> entitlements = null;
    private Set<RoleEntitlement> roleEntitlements = null;
    private SystemModuleFunction entitle;
    private Boolean successMsg = false;
    private String addOrEdit = null;
    private List<GroupMaster> groupMaster = null;
    private List<RoleEntitlement> parentRoleList = new ArrayList<>(0);
    private List<RoleEntitlement> childRoleList = new ArrayList<>(0);
    private Long editedRoleId;
    private List<String> roleIds = new ArrayList<>(0);
    private List<String> roleIdsCompare = new ArrayList<>(0);

    @Autowired
    private IEntitlementService iEntitlementService;

    @Autowired
    private IEmployeeService employeeService;

    private String viewType = null;

    public String getViewType() {
        return viewType;
    }

    public void setViewType(final String viewType) {
        this.viewType = viewType;
    }

    public EntitlementModel() {
    }

    public void getParentList() throws RuntimeException {

        setViewType(MainetConstants.MENU.ALL);
        String orderByClause = MainetConstants.MENU.NAMEENG;
        if (UserSession.getCurrent().getLanguageId() == 2) {
            orderByClause = MainetConstants.MENU.NAMEREG;
        }

        final Set<SystemModuleFunction> entitlementSet = iEntitlementService
                .getMenuMasterList(orderByClause);
        // For lazy fetching
        fetchChild(entitlementSet);
        setEntitlements(entitlementSet);

    }

    public Set<SystemModuleFunction> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(final Set<SystemModuleFunction> entitlements) {
        this.entitlements = entitlements;
    }

    public Boolean saveGrpMenuForm(final String roleName, final String menuIds,
            final String grDescEng, final String grDescReg) throws RuntimeException {
        if ((roleName == null) || roleName.trim().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.roleCode.validation"));
        } else {
            final boolean result = iEntitlementService
                    .getRoleCodeFromGrpMst(UserSession.getCurrent().getOrganisation().getOrgid(), roleName.trim());
            if (result) {
                addValidationError(getAppSession().getMessage(
                        "menu.create.role.exist"));
            }
        }

        if ((menuIds == null) || menuIds.trim().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.select.validation"));
        }

        if (hasValidationErrors()) {
            return false;
        }

        final GroupMaster groupMaster = new GroupMaster();
        groupMaster.setGrCode(roleName);
        groupMaster.setGrDescEng(grDescEng);
        groupMaster.setGrDescReg(grDescReg);
        groupMaster.setGrStatus(MainetConstants.MENU.A);
        groupMaster.setOrgId(UserSession.getCurrent().getOrganisation());
        final Set<RoleEntitlement> roleEntitlements = new LinkedHashSet<>();
        RoleEntitlement roleEntitlement = null;
        SystemModuleFunction entitlement = null;
        final String[] ids = menuIds.split(MainetConstants.operator.COMA);
        for (final String id : ids) {

            final String[] subIds = id.split(MainetConstants.operator.FORWARD_SLACE);
            final Long childId = new Long(subIds[0]);
            entitlement = new SystemModuleFunction();
            entitlement.setSmfid(childId);
            final Long parentId = new Long(subIds[1]);
            roleEntitlement = new RoleEntitlement();

            roleEntitlement.setUpdatedDate(new Date());
            roleEntitlement.setUpdatedBy(UserSession.getCurrent().getEmployee()
                    .getEmpId());
            roleEntitlement.setOrganisation(UserSession.getCurrent()
                    .getOrganisation());
            roleEntitlement.setEntitle(entitlement);
            roleEntitlement.setParentId(parentId);
            roleEntitlement.setIsActive(MainetConstants.MENU._0);
            roleEntitlement.setAdd(MainetConstants.MENU.N);
            roleEntitlement.setDelete(MainetConstants.MENU.N);
            roleEntitlement.setUpdate(MainetConstants.MENU.N);
            roleEntitlements.add(roleEntitlement);
        }
        groupMaster.setEntitlements(roleEntitlements);
        iEntitlementService.save(groupMaster);

        return true;
    }

    public void getData(final Long roleId) throws RuntimeException {

        String orderByClause = MainetConstants.MENU.NAMEENG;
        if (UserSession.getCurrent().getLanguageId() == 2) {
            orderByClause = MainetConstants.MENU.NAMEREG;
        }
        final List<String> flaList = Arrays
                .asList(MainetConstants.MENU.ROLEMENU);

        final Set<RoleEntitlement> parentList = iEntitlementService
                .getExistTemplateParent(roleId, flaList, UserSession
                        .getCurrent().getOrganisation().getOrgid(), orderByClause);
        if (UserSession.getCurrent().getLanguageId() == 2) {
            for (final RoleEntitlement roleEntitlement : parentList) {
                roleEntitlement.getEntitle().setSmfname_mar(
                        roleEntitlement.getEntitle().getSmfname_mar());
            }
        }

        final Set<RoleEntitlement> childList = iEntitlementService
                .getExistTemplateChild(roleId, flaList, UserSession
                        .getCurrent().getOrganisation().getOrgid(), orderByClause);
        if (UserSession.getCurrent().getLanguageId() == 2) {
            for (final RoleEntitlement roleEntitlement : childList) {
                roleEntitlement.getEntitle().setSmfname_mar(
                        roleEntitlement.getEntitle().getSmfname_mar());
            }
        }

        setListSystems(parentList);
        setListModules(childList);

    }

    @Override
    public void addForm() {
        super.addForm();
    }

    public Set<RoleEntitlement> getRoleEntitlements() {
        return roleEntitlements;
    }

    public void setRoleEntitlements(final Set<RoleEntitlement> roleEntitlements) {
        this.roleEntitlements = roleEntitlements;
    }

    public Set<RoleEntitlement> getListSystems() {
        return listSystems;
    }

    public void setListSystems(final Set<RoleEntitlement> listSystems) {
        this.listSystems = listSystems;
    }

    public Set<RoleEntitlement> getListModules() {
        return listModules;
    }

    public void setListModules(final Set<RoleEntitlement> listModules) {
        this.listModules = listModules;
    }

    public SystemModuleFunction getEntitle() {
        return entitle;
    }

    public void setEntitle(final SystemModuleFunction entitle) {
        this.entitle = entitle;
    }

    public int getGroupCount(final Long roleId) {
        return employeeService.getCountOfGroup(roleId, UserSession.getCurrent()
                .getOrganisation().getOrgid(), MainetConstants.MENU._0);
    }

    @Override
    public boolean saveOrUpdateForm() throws RuntimeException {

        final SystemModuleFunction entitlement = getEntitle();
        if ((entitlement.getSmfdescription() == null)
                || entitlement.getSmfdescription().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.description.valid"));
        }

        if ((entitlement.getSmfname_mar() == null)
                || entitlement.getSmfname_mar().trim().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.name.valid")
                    + "  "
                    + getAppSession().getMessage("menu.langType.english.label"));
        }
        if ((entitlement.getSmfname() == null)
                || entitlement.getSmfname().trim().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.name.valid")
                    + "  "
                    + getAppSession().getMessage("menu.langType.reg.label"));
        }
        if ((entitlement.getSmfaction() == null)
                || entitlement.getSmfaction().trim().equals(MainetConstants.BLANK)) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.action.valid"));
        }
        if (!MainetConstants.MENU.E.equals(getAddOrEdit())) {
            if (entitlement.getSmfflag().equalsIgnoreCase(MainetConstants.MENU.SF)) {
                addValidationError(getAppSession().getMessage(
                        "menu.add.child.valid"));
            }
        }

        if (hasValidationErrors()) {
            return false;
        }
        SystemModuleFunction data = null;
        if (entitlement.getHiddenEtId() != null) {
            data = iEntitlementService.findById(entitlement.getHiddenEtId());
        }
        if (MainetConstants.MENU.E.equals(getAddOrEdit())) {
            data.setSmfname(entitlement.getSmfname());
            data.setSmfname_mar(entitlement.getSmfname_mar());
            data.setSmfdescription(entitlement.getSmfdescription());
            data.setSmfaction(entitlement.getSmfaction());
            data.setSmParam1(entitlement.getSmParam1());
            data.setSmParam2(entitlement.getSmParam2());
            data.setLg_ip_mac_upd(Utility.getMacAddress());
            data.setUpdated_by(UserSession.getCurrent().getEmployee().getEmpId());
            data.setUpdated_date(new Date());
            iEntitlementService.updateNode(data);
        } else {
            entitle.setSmfid(null);
            String flagType = null;
            if (data != null) {
                switch (data.getSmfflag()) {
                case MainetConstants.MENU.S:
                    flagType = MainetConstants.MENU.M;
                    break;
                case MainetConstants.MENU.M:
                    flagType = MainetConstants.MENU.F;
                    break;
                case MainetConstants.MENU.F:
                    flagType = MainetConstants.MENU.F;
                    break;
                case MainetConstants.MENU.P:
                    flagType = MainetConstants.MENU.F;
                }
            } else {
                flagType = MainetConstants.MENU.S;
            }
            final Date date = new Date();
            final DateFormat dateFormat = new SimpleDateFormat(MainetConstants.COMMOM_TIME_FORMAT);
            entitlement.setUser_id(UserSession.getCurrent().getEmployee()
                    .getEmpId());
            entitlement.setOntime(dateFormat.format(date));
            entitlement.setOndate(date);
            entitlement.setSmfflag(flagType);
            entitlement.setLang_id(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            entitlement.setLg_ip_mac(Utility.getMacAddress());
            SystemModuleFunction entitlement2 = null;
            if (entitlement.getHiddenEtId() != null) {
                entitlement2 = new SystemModuleFunction();
                entitlement2.setSmfid(entitlement.getHiddenEtId());
            }
            entitlement.setModuleFunction(entitlement2);
            entitlement.setIsdeleted(MainetConstants.MENU._0);
            iEntitlementService.saveNode(entitlement);
        }

        return true;
    }

    // Recursive method for fething child for language
    public void fetchChild(final Set<SystemModuleFunction> hierarchicals) {

        Set<SystemModuleFunction> childSet = null;
        if ((hierarchicals != null) && !hierarchicals.isEmpty()) {
            if (UserSession.getCurrent().getLanguageId() == 2) {
                for (final SystemModuleFunction mas : hierarchicals) {
                    mas.setSmfname(mas.getSmfname_mar());
                    childSet = mas.getMenuHierarchicalList();
                    fetchChild(childSet);
                }
            }

        }

    }

    public Boolean getUpdateNode(final Long roleId, final String nodeList)
            throws RuntimeException {

        if ((roleId == null) || (nodeList == null) || nodeList.equals(MainetConstants.BLANK)) {
            return false;
        }

        final String[] ids = nodeList.split(MainetConstants.operator.COMA);
        RoleEntitlement roleEntitlement = null;
        for (final String id : ids) {

            final String[] subIds = id.split(MainetConstants.operator.FORWARD_SLACE);
            roleEntitlement = iEntitlementService.findByRoleAndEntitleId(
                    roleId, Long.valueOf(subIds[0]));
            roleEntitlement.setIsActive(MainetConstants.MENU._1);
            iEntitlementService.saveRoleEntitlement(roleEntitlement);
        }
        return true;
    }

    public Boolean getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(final Boolean successMsg) {
        this.successMsg = successMsg;
    }

    public String getAddOrEdit() {
        return addOrEdit;
    }

    public void setAddOrEdit(final String addOrEdit) {
        this.addOrEdit = addOrEdit;
    }

    public void setGroupList() {
        groupMaster = new ArrayList<>(0);
        groupMaster = iEntitlementService.getGroupListForNewActiveNode(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public List<GroupMaster> getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(final List<GroupMaster> groupMaster) {
        this.groupMaster = groupMaster;
    }

    public Map<Long, String> getRoleEntitleIds(final Long roleId) {

        return iEntitlementService.getRoleEntitleIds(roleId, UserSession
                .getCurrent().getOrganisation().getOrgid());
    }

    public Long getEditedRoleId() {
        return editedRoleId;
    }

    public void setEditedRoleId(final Long editedRoleId) {
        this.editedRoleId = editedRoleId;
    }

    public boolean saveEditActiveNode(final Long editedRoleId, final String menuIds) {

        if ((editedRoleId == null) || (editedRoleId.longValue() == 0)) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.edit.code"));
        }
        if (hasValidationErrors()) {
            return false;
        }
        List<Long> parentids = null;
        final List<RoleEntitlement> relist = iEntitlementService.getExistTemplateChild(editedRoleId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if ((relist != null) && !relist.isEmpty()) {
            parentids = new ArrayList<>(0);
            for (final RoleEntitlement re : relist) {
                parentids.add(re.getEntitle().getSmfid());
            }
        }

        final Set<RoleEntitlement> roleEntitlements = new LinkedHashSet<>();
        RoleEntitlement roleEntitlement = null;
        SystemModuleFunction entitlement = null;
        GroupMaster groupMaster = null;
        if ((menuIds != null) && !menuIds.isEmpty()) {
            final String[] ids = menuIds.split(MainetConstants.operator.COMA);
            for (final String id : ids) {

                final String[] subIds = id.split(MainetConstants.operator.FORWARD_SLACE);
                final Long etId = Long.valueOf(subIds[0]);
                if ((parentids != null) && !parentids.isEmpty() && parentids.contains(etId)) {
                    parentids.remove(etId);
                } else {
                    final Long childId = new Long(subIds[0]);
                    entitlement = new SystemModuleFunction();
                    entitlement.setSmfid(childId);
                    final Long parentId = new Long(subIds[1]);
                    roleEntitlement = new RoleEntitlement();
                    groupMaster = new GroupMaster();
                    groupMaster.setGmId(editedRoleId);
                    roleEntitlement.setGroupMaster(groupMaster);
                    roleEntitlement.setUpdatedDate(new Date());
                    roleEntitlement.setUpdatedBy(UserSession.getCurrent().getEmployee()
                            .getEmpId());
                    roleEntitlement.setOrganisation(UserSession.getCurrent()
                            .getOrganisation());
                    roleEntitlement.setEntitle(entitlement);
                    roleEntitlement.setParentId(parentId);
                    roleEntitlement.setIsActive(MainetConstants.MENU._0);

                    roleEntitlements.add(roleEntitlement);
                }
            }
            if ((parentids != null) && !parentids.isEmpty()) {
                iEntitlementService.updateInactiveMenu(parentids, UserSession.getCurrent().getOrganisation().getOrgid(),editedRoleId);
            }
            if ((roleEntitlements != null) && !roleEntitlements.isEmpty()) {
                iEntitlementService.activeNewNode(roleEntitlements);
            }
        } else {
            if ((parentids != null) && !parentids.isEmpty()) {
                iEntitlementService.updateInactiveMenu(parentids, UserSession.getCurrent().getOrganisation().getOrgid(),editedRoleId);
            }
        }

        return true;
    }

    public List<RoleEntitlement> getParentRoleList() {
        return parentRoleList;
    }

    public void setParentRoleList(
            final List<RoleEntitlement> parentRoleList) {
        this.parentRoleList = parentRoleList;
    }

    public List<RoleEntitlement> getChildRoleList() {
        return childRoleList;
    }

    public void setChildRoleList(
            final List<RoleEntitlement> childRoleList) {
        this.childRoleList = childRoleList;
    }

    public boolean saveDataEntitlement(final String array) throws RuntimeException {

        if ((array.length() == 0) || array.isEmpty()) {
            addValidationError(getAppSession().getMessage(
                    "menu.node.select.trans.entitle"));
        }

        if (hasValidationErrors()) {
            return false;
        }

        final List<String> addElement = new ArrayList<>();
        final List<String> deleteElement = new ArrayList<>();
        final List<String> passedList = new ArrayList<>();
        final StringTokenizer st = new StringTokenizer(array, MainetConstants.operator.COMA);
        while (st.hasMoreElements()) {
            passedList.add((String) st.nextElement());
        }
        for (final String string : passedList) {
            if (!roleIdsCompare.contains(string)) {
                addElement.add(string);
            }
        }
        for (final String string : roleIdsCompare) {
            if (!passedList.contains(string)) {
                deleteElement.add(string);
            }
        }

        return iEntitlementService.updateRecord(addElement, deleteElement);
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(final List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleIdsCompare() {
        return roleIdsCompare;
    }

    public void setRoleIdsCompare(final List<String> roleIdsCompare) {
        this.roleIdsCompare = roleIdsCompare;
    }

}
