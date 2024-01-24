package com.abm.mainet.cms.ui.controller;

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

import com.abm.mainet.cms.ui.model.EntitlementModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;

/**
 * @author ritesh.patil
 *
 */

@Controller
@RequestMapping(value = "/entitlement.html")
public class EntitlementController extends
        AbstractEntryFormController<EntitlementModel> {

    private static Logger logger = Logger
            .getLogger(EntitlementController.class);

    @Autowired
    IEntitlementService entitlementService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        ModelAndView modelAndView = null;
        try {
            getModel().setSuccessMsg(false);
            if (getModel().getEntitlements() == null) {
                sessionCleanup(httpServletRequest);
                getModel().getParentList();
            }
            modelAndView = new ModelAndView("entitlement", MainetConstants.FORM_NAME,
                    getModel());

        } catch (final RuntimeException e) {
            throw e;
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveForm")
    public ModelAndView saveData(
            @RequestParam(value = "roleName", required = true) final String roleName,
            @RequestParam(value = "menuIds", required = true) final String menuIds,
            @RequestParam(value = "groupDescE", required = false) final String groupDescE,
            @RequestParam(value = "groupDescR", required = false) final String groupDescR,
            final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final HttpSession session = httpServletRequest.getSession(true);
        try {

            final boolean flag = getModel().saveGrpMenuForm(roleName, menuIds,
                    groupDescE, groupDescR);
            session.setAttribute("checkErrorEntitlement",
                    MainetConstants.MENU.N);

            if (!flag) {
                getModel().setSuccessMsg(false);
                session.setAttribute("checkErrorEntitlement",
                        MainetConstants.MENU.Y);

            }
        } catch (final RuntimeException e) {
            logger.error(MainetConstants.EXCEPTION_OCCURED, e);
            session.setAttribute("checkErrorEntitlement", "EX");
        }

        return getEntitleForm(httpServletRequest);

    }

    @RequestMapping(method = RequestMethod.POST, params = "existTemplate")
    public ModelAndView getData(
            @RequestParam(value = "roleId", required = true) final Long roleId) {

        try {
            getModel().getData(roleId);
            return new ModelAndView("existEntitle", MainetConstants.FORM_NAME, getModel());
        } catch (final RuntimeException e) {
            throw e;
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "activeExistTemplate")
    @ResponseBody
    public Map<Long, String> getActiveExistData(
            @RequestParam(value = "roleId", required = true) final Long roleId) {
        try {
            getModel().setViewType(MainetConstants.MENU.A);
            return getModel().getRoleEntitleIds(roleId);
        } catch (final RuntimeException e) {
            logger.error(MainetConstants.EXCEPTION_OCCURED, e);

        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, params = "saveEditActiveNode")
    public ModelAndView editActiveNode(
            @RequestParam(value = "editedRoleId", required = true) final Long editedRoleId,
            @RequestParam(value = "menuIds", required = true) final String menuIds,
            final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final HttpSession session = httpServletRequest.getSession(true);
        try {
            final boolean flag = getModel().saveEditActiveNode(editedRoleId, menuIds);
            session.setAttribute("checkErrorEntitlement",
                    MainetConstants.MENU.N);

            if (!flag) {
                getModel().setSuccessMsg(false);
                session.setAttribute("checkErrorEntitlement",
                        MainetConstants.MENU.Y);

            }
        } catch (final RuntimeException e) {
            logger.error(MainetConstants.EXCEPTION_OCCURED, e);
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
        return new ModelAndView("ActiveNewNode", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "addNode")
    public ModelAndView addNodeInTree(final HttpServletRequest httpServletRequest) {
        getModel().setEntitle(new SystemModuleFunction());
        getModel().setAddOrEdit(MainetConstants.MENU.A);
        return new ModelAndView("addNode", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "editNode")
    public ModelAndView editNodeInTree(final HttpServletRequest httpServletRequest) {
        getModel().setEntitle(new SystemModuleFunction());
        getModel().setAddOrEdit(MainetConstants.MENU.E);
        return new ModelAndView("addNode", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "dataEntitle")
    public ModelAndView assignEntitlement(final HttpServletRequest httpServletRequest) {
        getModel().setGroupList();
        return new ModelAndView("assignDataEntitle", MainetConstants.FORM_NAME, getModel());
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
                .addAll(entitlementService.getExistTemplateChild(groupId, Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD),
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
        return new ModelAndView("assignSubPage", MainetConstants.FORM_NAME, getModel());
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
        final ModelAndView mv = new ModelAndView("addNode", MainetConstants.FORM_NAME,
                getModel());
        final EntitlementModel model = getModel();

        try {
            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            }
        } catch (final Exception ex) {

            logger.error(MainetConstants.EXCEPTION_OCCURED, ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        if (getModel().getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX
                    + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;
    }

    @RequestMapping(params = "updateNode", method = RequestMethod.POST)
    @ResponseBody
    public String UpdateNodes(@RequestParam("roleId") final Long roleId,
            @RequestParam("nodesList") final String nodeList) {

        final EntitlementModel model = getModel();
        String returnType = null;
        try {
            if (model.getUpdateNode(roleId, nodeList)) {
                returnType = MainetConstants.MENU.TRUE;
            } else {
                returnType = MainetConstants.MENU.FALSE;
            }
        } catch (final Exception ex) {
            logger.error(MainetConstants.EXCEPTION_OCCURED, ex);
            returnType = "exce";
        }

        return returnType;
    }

    public ModelAndView getEntitleForm(final HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = null;
        HttpSession session = null;
        getModel().setEditedRoleId(null);
        try {

            session = httpServletRequest.getSession();
            final String checker = (String) session
                    .getAttribute("checkErrorEntitlement");

            if ((checker == null) || checker.equals("N")) {
                sessionCleanup(httpServletRequest);
                getModel().getParentList();
            }

            if ((checker == null) || !(checker.equals(MainetConstants.MENU.EX))) {

                if (getModel().getSuccessMsg()) {
                    getModel().setSuccessMsg(false);
                }

                modelAndView = new ModelAndView("entitlement", MainetConstants.FORM_NAME,
                        getModel());
            } else {
                modelAndView = new ModelAndView("defaultExceptionView",
                        MainetConstants.FORM_NAME, getModel());
            }
            if ((checker == null) || !(checker.equals(MainetConstants.MENU.Y))) {
                if ((checker != null) && checker.equals(MainetConstants.MENU.N)) {
                    getModel().setSuccessMsg(true);
                }
            } else {
                modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX
                        + MainetConstants.FORM_NAME,
                        getModel()
                                .getBindingResult());
            }

        } catch (final RuntimeException e) {
            logger.error(MainetConstants.EXCEPTION_OCCURED, e);
            modelAndView = new ModelAndView("defaultExceptionView", MainetConstants.FORM_NAME,
                    getModel());
        }
        session.removeAttribute("checkErrorEntitlement");
        return modelAndView;

    }

    @RequestMapping(params = "saveDataEntitle", method = RequestMethod.POST)
    public ModelAndView saveDataEntitleForm(@RequestParam(value = "array", required = true) final String array) {
        final ModelAndView mv = new ModelAndView("assignDataEntitle", MainetConstants.FORM_NAME,
                getModel());
        final EntitlementModel model = getModel();

        try {
            if (model.saveDataEntitlement(array)) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            }
        } catch (final Exception ex) {

            logger.error(MainetConstants.EXCEPTION_OCCURED, ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }

        if (getModel().getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX
                    + MainetConstants.FORM_NAME, getModel().getBindingResult());
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

}
