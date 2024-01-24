package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.RoleEntitlement;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Component("menuRoleEntitlement")
@Scope(value = "session"/* , proxyMode = ScopedProxyMode.TARGET_CLASS */)
public class MenuRoleEntitlement implements Serializable {

    private static final long serialVersionUID = -7872415063500455622L;

    private Set<RoleEntitlement> parentList = new LinkedHashSet<>(0);
    private Set<RoleEntitlement> childList = new LinkedHashSet<>(0);
    private String userType = null, serviceType = null;

    @Autowired
    private IEntitlementService iEntitlementService;

    public Set<RoleEntitlement> getParentList() {
        return parentList;
    }

    public void setParentList(final Set<RoleEntitlement> parentList) {
        this.parentList = parentList;
    }

    public Set<RoleEntitlement> getChildList() {
        return childList;
    }

    public void setChildList(final Set<RoleEntitlement> childList) {
        this.childList = childList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public static MenuRoleEntitlement getCurrentMenuRoleManager() {
        return ApplicationContextProvider.getApplicationContext().getBean(MenuRoleEntitlement.class);
    }

    public void getMenuList(final Long roleId, final Long orgId) {

        if (roleId != null) {
            final List<String> menuChildList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD);
            final List<String> menuParentList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_PARENT);
            String orderByClause = MainetConstants.MENU.NAMEENG;
        //Df #117806 changes done for making consistent ordering  of menus for both the languages --->starts
           /* if (UserSession.getCurrent().getLanguageId() == 2) {
                orderByClause = MainetConstants.MENU.NAMEREG;
            } */
        //Df #117806 changes done for ordering of menus --->ends
            final Set<RoleEntitlement> parentList = iEntitlementService.getExistTemplateParent(roleId, menuParentList, orgId,
                    orderByClause);
            for (final RoleEntitlement roleEntitlement : parentList) {
                roleEntitlement.getEntitle().setSmfname_eng(roleEntitlement.getEntitle().getSmfname());
            }
            if (UserSession.getCurrent().getLanguageId() == 2) {
                for (final RoleEntitlement roleEntitlement : parentList) {
                    roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
                }
            }
           
            final Set<RoleEntitlement> childList = iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
                    orderByClause);
            for (final RoleEntitlement roleEntitlement : childList) {
                roleEntitlement.getEntitle().setSmfname_eng(roleEntitlement.getEntitle().getSmfname());
            }
            if (UserSession.getCurrent().getLanguageId() == 2) {
                for (final RoleEntitlement roleEntitlement : childList) {
                    roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
                }
            }
            
            setParentList(parentList);
            setChildList(childList);
        }
    }

    public void getSpecificMenuList(final Long roleId, final Long orgId) {
        final List<String> menuChildList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD);
        final String orderByClause = MainetConstants.MENU.NAMEENG;
        final Set<RoleEntitlement> childList = iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
                orderByClause);
        setChildList(childList);
    }

}
