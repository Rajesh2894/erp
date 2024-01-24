package com.abm.mainet.common.entitlement.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
import com.abm.mainet.common.entitlement.dto.ActiveExistTemplateDTO;
import com.abm.mainet.common.entitlement.service.IEntitlementService;
import com.abm.mainet.common.entitlement.service.MenuRoleEntitlement;
import com.abm.mainet.common.entitlement.ui.model.EntitlementModel;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author ritesh.patil
 *
 */

@Controller
@RequestMapping(value = "/entitlement.html")
public class EntitlementController extends AbstractEntryFormController<EntitlementModel> {

    private static Logger logger = Logger.getLogger(EntitlementController.class);

    @Autowired
    IEntitlementService entitlementService;

    @Autowired
    TbDepartmentService tbDepartmentService;

    @Autowired
    GroupMasterService groupMasterService;

    String isSuperAdmin = MainetConstants.Common_Constant.NO;

    @RequestMapping()
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        ModelAndView modelAndView = null;
        try {

            getModel().setSuccessMsg(false);
            if (getModel().getEntitlements() == null) {
                sessionCleanup(httpServletRequest);
                getModel().getParentList();
            }
            final List<TbDepartment> departmentList = tbDepartmentService
                    .findAllMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
            getModel().setDepartmentList(departmentList);
            final Organisation sessionOrg = UserSession.getCurrent().getOrganisation();
            final Long loggedInGmId = UserSession.getCurrent().getEmployee().getGmid();
            final GroupMaster groupMaster = groupMasterService.findByGmId(loggedInGmId, sessionOrg.getOrgid());
            final ApplicationSession appSession = ApplicationSession.getInstance();
            if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
                isSuperAdmin = MainetConstants.Common_Constant.YES;
            }
            getModel().setIsOrgDefault(sessionOrg.getDefaultStatus());
            getModel().setIsSuperAdmin(isSuperAdmin);
            this.getModel().setCommonHelpDocs("entitlement.html");
            modelAndView = new ModelAndView("entitlement", MainetConstants.CommonConstants.COMMAND, getModel());

        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
            modelAndView = new ModelAndView("defaultExceptionView", MainetConstants.CommonConstants.COMMAND,
                    getModel());
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveForm")
    public ModelAndView saveData(@RequestParam(value = "roleName", required = true) final String roleName,
            @RequestParam(value = "menuIds", required = true) final String menuIds,
            @RequestParam(value = "groupDescE", required = false) final String groupDescE,
            @RequestParam(value = "groupDescR", required = false) final String groupDescR,
            @RequestParam(value = "dpDeptId", required = false) final Long dpDeptId,
            final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final HttpSession session = httpServletRequest.getSession(true);
        try {

            final boolean flag = getModel().saveGrpMenuForm(roleName, menuIds, groupDescE, groupDescR, dpDeptId);
            session.setAttribute("checkErrorEntitlement", MainetConstants.MENU.N);

            if (!flag) {
                getModel().setSuccessMsg(false);
                session.setAttribute("checkErrorEntitlement", MainetConstants.MENU.Y);

            }
        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
            session.setAttribute("checkErrorEntitlement", "EX");
        }

        return getEntitleForm(httpServletRequest);

    }

    @RequestMapping(method = RequestMethod.POST, params = "existTemplate")
    public ModelAndView getData(@RequestParam(value = "roleId", required = true) final Long roleId) {

        try {
            getModel().getData(roleId);
            return new ModelAndView("existEntitle", MainetConstants.CommonConstants.COMMAND, getModel());
        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
            return new ModelAndView("defaultExceptionFormView", MainetConstants.CommonConstants.COMMAND, getModel());
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "activeExistTemplate")
    @ResponseBody
    public ActiveExistTemplateDTO getActiveExistData(@RequestParam(value = "roleId", required = true) final Long roleId) {
        try {
            getModel().setViewType(MainetConstants.MENU.A);
            long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            GroupMaster groupMaster = null;
            ActiveExistTemplateDTO activeExistDto = new ActiveExistTemplateDTO();
            activeExistDto.setRoleEntitleIds(getModel().getRoleEntitleIds(roleId));
            groupMaster = entitlementService.findByRoleId(roleId, orgId);
            activeExistDto.setDpDeptId(groupMaster.getDpDeptId());
            activeExistDto.setRoleDescriptionEng(groupMaster.getGrDescEng());
            activeExistDto.setRoleDescriptionReg(groupMaster.getGrDescReg());
            return activeExistDto;
        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveEditActiveNode")
    public ModelAndView editActiveNode(@RequestParam(value = "editedRoleId", required = true) final Long editedRoleId,
            @RequestParam(value = "roleDescriptionEng") String roleDescriptionEng,
            @RequestParam(value = "roleDescriptionReg") String roleDescriptionReg,
            @RequestParam(value = "menuIds", required = true) final String menuIds, final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final HttpSession session = httpServletRequest.getSession(true);
        try {
            final boolean flag = getModel().saveEditActiveNode(editedRoleId, menuIds, roleDescriptionEng, roleDescriptionReg);
            session.setAttribute("checkErrorEntitlement", MainetConstants.MENU.N);
          //31111 : Added logic to load menu on load event of home page
        	final Organisation organisation = UserSession.getCurrent().getOrganisation();
            final Employee employee = UserSession.getCurrent().getEmployee();
    		if (employee.getGmid() != null) {
    			MenuRoleEntitlement.getCurrentMenuRoleManager().getMenuList(employee.getGmid(),
    					organisation.getOrgid());
    		} else {
				final Long groupId = entitlementService.getGroupIdByName(MainetConstants.MENU.PORTAL_LOGIN,
    					organisation.getOrgid());
    			MenuRoleEntitlement.getCurrentMenuRoleManager().getSpecificMenuList(groupId, organisation.getOrgid());
    		}
            if (!flag) {
                getModel().setSuccessMsg(false);
                session.setAttribute("checkErrorEntitlement", MainetConstants.MENU.Y);

            }
        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
            session.setAttribute("checkErrorEntitlement", "EX");
        }

        return getEntitleForm(httpServletRequest);

    }

    @RequestMapping(method = RequestMethod.POST, params = "grpList")
    @ResponseBody
    public Map<Long, String> getGroupList() {
        return entitlementService.getGroupList(UserSession.getCurrent().getOrganisation().getOrgid());
    }

    @RequestMapping(method = RequestMethod.POST, params = "activeNewNode")
    public ModelAndView getActineNodeList() {
        getModel().setGroupList();
        isSuperAdmin = MainetConstants.Common_Constant.NO;
        GroupMaster adminGroup = null;
        final Organisation sessionOrg = UserSession.getCurrent().getOrganisation();
        final Long loggedInGmId = UserSession.getCurrent().getEmployee().getGmid();
        final GroupMaster groupMaster = groupMasterService.findByGmId(loggedInGmId, sessionOrg.getOrgid());
        final ApplicationSession appSession = ApplicationSession.getInstance();
        if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
            isSuperAdmin = MainetConstants.Common_Constant.YES;
        }
        getModel().setIsSuperAdmin(isSuperAdmin);

        adminGroup = groupMasterService.getAdminGroupByOrg(sessionOrg.getOrgid());
        getModel().setAdminGmId(adminGroup.getGmId());
        return new ModelAndView("ActiveNewNode", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "addNode")
    public ModelAndView addNodeInTree(final HttpServletRequest httpServletRequest) {
        getModel().setEntitle(new SystemModuleFunction());
        getModel().setAddOrEdit(MainetConstants.MENU.A);
        return new ModelAndView("addNode", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "editNode")
    public ModelAndView editNodeInTree(final HttpServletRequest httpServletRequest) {
        getModel().setEntitle(new SystemModuleFunction());
        getModel().setAddOrEdit(MainetConstants.MENU.E);
        return new ModelAndView("addNode", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "dataEntitle")
    public ModelAndView assignEntitlement(final HttpServletRequest httpServletRequest) {
        getModel().setGroupList();
        return new ModelAndView("assignDataEntitle", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "addRole")
    public ModelAndView addRole(final HttpServletRequest httpServletRequest) {
        return new ModelAndView("addRole", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "getDataEntitle")
    public ModelAndView filterByRole(final HttpServletRequest httpServletRequest,
            @RequestParam(value = "groupId", required = true) final Long groupId) {
        String orderByClause = MainetConstants.MENU.NAMEENG;
        if (UserSession.getCurrent().getLanguageId() == 2) {
            orderByClause = MainetConstants.MENU.NAMEREG;
        }
        getModel().getParentRoleList().clear();
        getModel().getChildRoleList().clear();
        getModel().getParentRoleList()
                .addAll(entitlementService.getExistTemplateParent(groupId,
                        Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_PARENT),
                        UserSession.getCurrent().getOrganisation().getOrgid(), orderByClause));
        if (UserSession.getCurrent().getLanguageId() == 2) {
            for (final RoleEntitlement roleEntitlement : getModel().getParentRoleList()) {
                roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
            }
        }

        getModel().getChildRoleList()
                .addAll(entitlementService.getExistTemplateChild(groupId,
                        Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD),
                        UserSession.getCurrent().getOrganisation().getOrgid(), orderByClause));
        final List<String> ids = new ArrayList<>();
        final List<String> compareiIds = new ArrayList<>();
        if (getModel().getChildRoleList() != null) {
            for (final RoleEntitlement obj1 : getModel().getChildRoleList()) {

                if ((obj1.getAdd() != null) && obj1.getAdd().equals(MainetConstants.MENU.Y)) {
                    ids.add(MainetConstants.MENU.A_ID + obj1.getRoleEtId());
                    compareiIds.add(MainetConstants.MENU.A + MainetConstants.WINDOWS_SLASH + obj1.getRoleEtId());
                }
                if ((obj1.getUpdate() != null) && obj1.getUpdate().equals(MainetConstants.MENU.Y)) {
                    ids.add(MainetConstants.MENU.E_ID + obj1.getRoleEtId());
                    compareiIds.add(MainetConstants.MENU.E + MainetConstants.WINDOWS_SLASH + obj1.getRoleEtId());
                }
                if ((obj1.getDelete() != null) && obj1.getDelete().equals(MainetConstants.MENU.Y)) {
                    ids.add(MainetConstants.MENU.D_ID + obj1.getRoleEtId());
                    compareiIds.add(MainetConstants.MENU.D + MainetConstants.WINDOWS_SLASH + obj1.getRoleEtId());
                }

                Boolean isLastNode = true;
                for (final RoleEntitlement obj2 : getModel().getChildRoleList()) {
                    if (obj1.getEntitle().getSmfid().equals(obj2.getParentId())) {
                        isLastNode = false;
                        break;
                    }
                }
                obj1.setLastNode(isLastNode);
            }
        }

        if (UserSession.getCurrent().getLanguageId() == 2) {
            for (final RoleEntitlement roleEntitlement : getModel().getChildRoleList()) {
                roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
            }
        }
        getModel().setChildRoleList(getModel().getChildRoleList());
        getModel().setRoleIds(ids);
        getModel().setRoleIdsCompare(compareiIds);
        return new ModelAndView("assignSubPage", MainetConstants.CommonConstants.COMMAND, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "hasEmpGrp")
    @ResponseBody
    public int isGrpExist(@RequestParam("roleId") final Long roleId) {
        return getModel().getGroupCount(roleId);
    }

    @Override
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ModelAndView mv = new ModelAndView("addNode", MainetConstants.CommonConstants.COMMAND, getModel());
        final EntitlementModel model = getModel();

        try {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            }
        } catch (final Exception ex) {

            logger.error("**Exception occurred**", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        if (getModel().getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;
    }

    @RequestMapping(params = "updateNode", method = RequestMethod.POST)
    @ResponseBody
    public String UpdateNodes(@RequestParam("roleId") final Long roleId, @RequestParam("nodesList") final String nodeList) {

        final EntitlementModel model = getModel();
        String returnType = null;
        try {
            if (model.getUpdateNode(roleId, nodeList)) {
                returnType = MainetConstants.MENU.TRUE;
            } else {
                returnType = MainetConstants.MENU.FALSE;
            }
        } catch (final Exception ex) {
            logger.error("**Exception occurred**", ex);
            returnType = "exce";
        }

        return returnType;
    }

    @RequestMapping(params = "getDepartment", method = RequestMethod.POST)
    public @ResponseBody Long getDepartment(@RequestParam("roleId") final Long roleId) {

        Long returnType = 0l;
        List<RoleEntitlement> entitlementList = new ArrayList<>();
        entitlementList = entitlementService.getExistTemplateChild(roleId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if ((entitlementList.size() > 0) && (entitlementList.get(0).getDepartment() != null)) {
            returnType = entitlementList.get(0).getDepartment().getDpDeptid();
        }

        return returnType;
    }

    public ModelAndView getEntitleForm(final HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = null;
        HttpSession session = null;
        getModel().setEditedRoleId(null);

        try {

            session = httpServletRequest.getSession();
            final String checker = (String) session.getAttribute("checkErrorEntitlement");

            if ((checker == null) || checker.equals(MainetConstants.Common_Constant.NO)) {
                sessionCleanup(httpServletRequest);
                getModel().getParentList();
            }

            if ((checker == null) || !(checker.equals(MainetConstants.MENU.EX))) {

                if (getModel().getSuccessMsg()) {
                    getModel().setSuccessMsg(false);
                }
                if (getModel().getDepartmentList().isEmpty()) {
                    final List<TbDepartment> departmentList = tbDepartmentService
                            .findAllMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
                    getModel().setDepartmentList(departmentList);
                }
                final Organisation sessionOrg = UserSession.getCurrent().getOrganisation();
                final Long loggedInGmId = UserSession.getCurrent().getEmployee().getGmid();
                final GroupMaster groupMaster = groupMasterService.findByGmId(loggedInGmId, sessionOrg.getOrgid());
                final ApplicationSession appSession = ApplicationSession.getInstance();
                if (groupMaster.getGrCode().equals(appSession.getMessage("groupmaster.GrCode"))) {
                    isSuperAdmin = MainetConstants.Common_Constant.YES;
                }
                getModel().setIsOrgDefault(sessionOrg.getDefaultStatus());
                getModel().setIsSuperAdmin(isSuperAdmin);
                modelAndView = new ModelAndView("entitlement", MainetConstants.CommonConstants.COMMAND,
                        getModel());
            } else {
                modelAndView = new ModelAndView("defaultExceptionView", MainetConstants.CommonConstants.COMMAND,
                        getModel());
            }
            if ((checker == null) || !(checker.equals(MainetConstants.MENU.Y))) {
                if ((checker != null) && checker.equals(MainetConstants.MENU.N)) {
                    getModel().setSuccessMsg(true);
                }
            } else {
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());
            }

        } catch (final RuntimeException e) {
            logger.error("**Exception occurred**", e);
            modelAndView = new ModelAndView("defaultExceptionView", MainetConstants.CommonConstants.COMMAND,
                    getModel());
        }
        session.removeAttribute("checkErrorEntitlement");
        return modelAndView;

    }

    @RequestMapping(params = "saveDataEntitle", method = RequestMethod.POST)
    public ModelAndView saveDataEntitleForm(@RequestParam(value = "array", required = true) final String array) {
        final ModelAndView mv = new ModelAndView("assignDataEntitle", MainetConstants.CommonConstants.COMMAND,
                getModel());
        final EntitlementModel model = getModel();

        try {
            if (model.saveDataEntitlement(array)) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
            }
        } catch (final Exception ex) {

            logger.error("**Exception occurred**", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        if (getModel().getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;
    }

    @RequestMapping(params = "getRoleIds", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getIds() {
        return getModel().getRoleIds();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "getSessionData")
    public ModelAndView getSessionData(final HttpServletRequest httpServletRequest) {

        return getEntitleForm(httpServletRequest);

    }

    @RequestMapping(method = { RequestMethod.POST }, params = "checkDuplicateRoleCode")
    public @ResponseBody String checkDuplicateRoleCodeExists(@RequestParam("roleName") final String roleName) {

        String error = null;
        final boolean result = entitlementService.getRoleCodeFromGrpMst(UserSession.getCurrent().getOrganisation().getOrgid(),
                roleName.trim());
        if (result) {
            error = ApplicationSession.getInstance().getMessage("menu.create.role.exist");
        }
        return error;
    }
}
