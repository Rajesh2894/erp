package com.abm.mainet.common.entitlement.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.ibm.icu.text.SimpleDateFormat;

@Component("menuRoleEntitlement")
@Scope(value = "session")
public class MenuRoleEntitlement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7872415063500455622L;
    /**
     *
     */
    private Set<RoleEntitlement> parentList = new LinkedHashSet<>(0);
    private Set<RoleEntitlement> childList = new LinkedHashSet<>(0);
    private String userType = null, serviceType = null;

    @Autowired
    private IEntitlementService iEntitlementService;
    
    @Autowired
    private TbCfcApplicationMstService tbCfcservice;

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

    @SuppressWarnings("deprecation")
	public void getMenuList(final Long roleId, final Long orgId) {

        if (roleId != null) {
            final List<String> menuChildList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD);
            final List<String> menuParentList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_PARENT);
            String orderByClause = MainetConstants.MENU.NAMEENG;
			/*Defect #127794
			 * Commenting that code as per discussion with Samadhan sir in marathi need node
			 * sequence same as English
			 */
			/*
			 * if (UserSession.getCurrent().getLanguageId() == 2) { orderByClause =
			 * MainetConstants.MENU.NAMEREG; }
			 */

			//119534  This is change for SKDCL env Which executes for Scheduling for TRX
            final Set<RoleEntitlement> parentList;
            Set<RoleEntitlement> childList;
            LookUp lookUp=null;
            CFCSchedulingCounterDet counterDet =null;
    	    try {
    	    	lookUp =CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.SchedulingForTrx.CSH, MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
    		} catch (Exception e) {
    		        
    		}
    	    if(lookUp != null && StringUtils.equals(MainetConstants.FlagY, lookUp.getOtherField())) {
    	    	counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(), 
        				UserSession.getCurrent().getOrganisation().getOrgid());
    	    }
            
    	    parentList = iEntitlementService.getExistTemplateParent(roleId, menuParentList, orgId,
                    orderByClause);
			
    	    if(counterDet == null || (counterDet != null && counterDet.getScheduleSts().equals(MainetConstants.FlagI))) {
				
    	    	childList = iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
	                    orderByClause);
    	    	UserSession.getCurrent().setCountersts(MainetConstants.FlagA);
    		}else {
    			Date date = new Date();
    			
    			if(counterDet.getFrequencySts().equals("Y") && counterDet.getToTime().after(date) && counterDet.getFromTime().before(date)) {
    				SimpleDateFormat localDateHour = new SimpleDateFormat("HH");
        			SimpleDateFormat localDateMin = new SimpleDateFormat("mm");
        		    String endTimeH = localDateHour.format(counterDet.getToTime());
        		    String startTimeH=localDateHour.format(counterDet.getFromTime());
        		    String startTimeM= localDateHour.format(counterDet.getFromTime());
        		    String currentTimeH = localDateHour.format(date);
        		    String endTimeM = localDateMin.format(counterDet.getToTime());
        		    String currentTimeM = localDateMin.format(date);
        		    int endTimeHour = Integer.parseInt(endTimeH);
        		    int startTimeHour = Integer.parseInt(startTimeH);
        		    int currentTimeHour = Integer.parseInt(currentTimeH);
        		    int startTimeMin=Integer.parseInt(startTimeM);
        		    int endTimeMin = Integer.parseInt(endTimeM);
        		    int currentTimeMin = Integer.parseInt(currentTimeM);
        		    if(endTimeHour > currentTimeHour && startTimeHour < currentTimeHour) {
        		    	childList = setValues(roleId, orgId, menuChildList, orderByClause, date, endTimeHour,
								endTimeMin);
        		    	
        		    }else if((endTimeHour == currentTimeHour && endTimeMin > currentTimeMin)
        		    		||(startTimeHour == currentTimeHour && startTimeMin < currentTimeMin)) {
        		    	childList = setValues(roleId, orgId, menuChildList, orderByClause, date, endTimeHour,
								endTimeMin);
        		    }else {
        		    	 childList=iEntitlementService.getExistTemplateChildByFlag(roleId,
        						 menuChildList, orgId, orderByClause);
        				  UserSession.getCurrent().setCountersts(MainetConstants.FlagI);
        		    }
    			}else {
    				if(counterDet.getToTime().after(date) && counterDet.getFromTime().before(date)) { 
    					childList =iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
    								orderByClause);
    					UserSession.getCurrent().setCountersts(MainetConstants.FlagA);
    					UserSession.getCurrent().setValidEmpFlag(MainetConstants.FlagY);
    					UserSession.getCurrent().setScheduleDate(counterDet.getToTime());
    						  
    				}else {
    					childList=iEntitlementService.getExistTemplateChildByFlag(roleId,
       						 menuChildList, orgId, orderByClause);
    					UserSession.getCurrent().setCountersts(MainetConstants.FlagI);
    					
        		    }
    				
    			}
    				
    		}
            if (UserSession.getCurrent().getLanguageId() == 2) {
                for (final RoleEntitlement roleEntitlement : parentList) {
                    roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
                }
            }

           

            for (final RoleEntitlement roleEntitlement : childList) {
                if ("STORE".equals(roleEntitlement.getEntitle().getSmParam1())) {
                    roleEntitlement.getEntitle().setSmfaction(roleEntitlement.getEntitle().getSmfaction()
                            + UserSession.getCurrent().getEmployee().getEmpisecuritykey());
                }

                if (UserSession.getCurrent().getLanguageId() == 2) {
                    roleEntitlement.getEntitle().setSmfname(roleEntitlement.getEntitle().getSmfname_mar());
                }

            }
            setParentList(parentList);
            setChildList(childList);
        }
    }
	//119534 Method added to reduce boiler plate logic
	private Set<RoleEntitlement> setValues(final Long roleId, final Long orgId, final List<String> menuChildList,
			String orderByClause, Date date, int endTimeHour, int endTimeMin) {
		Set<RoleEntitlement> childList;
		date.setHours(endTimeHour);
		date.setMinutes(endTimeMin);
		childList = iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
		        orderByClause);
		UserSession.getCurrent().setCountersts(MainetConstants.FlagA);
		UserSession.getCurrent().setValidEmpFlag(MainetConstants.FlagY);
		UserSession.getCurrent().setScheduleDate(date);
		return childList;
	}

    public void getSpecificMenuList(final Long roleId, final Long orgId) {
        final List<String> menuChildList = Arrays.asList(MainetConstants.MENU.DISPLAY_MENU_CHILD);
        final String orderByClause = MainetConstants.MENU.NAMEENG;
        final Set<RoleEntitlement> childList = iEntitlementService.getExistTemplateChild(roleId, menuChildList, orgId,
                orderByClause);
        setChildList(childList);
    }

}
