package com.abm.mainet.cms.ui.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.service.IEIPAboutUsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
@Scope(value = "session")
public class AdminAboutUsModel extends AbstractFormModel implements Serializable {
    private static final long serialVersionUID = 7826092254142861404L;
    private static final Logger LOG = Logger.getLogger(AdminAboutUsModel.class);

    private EIPAboutUs adminAboutUs;

    private String chekkerflag;
    
    @Autowired
    private IEIPAboutUsService iEIPAboutUsService;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    public void setModel() {
        setAdminAboutUs(iEIPAboutUsService.getAboutUs(getUserSession().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE));
    }

    public boolean saveAboutUs() {
        try {
        	
        	long id=getAdminAboutUs().getAboutUsId();
        	if(MainetConstants.FLAGY.equalsIgnoreCase(getAdminAboutUs().getChekkerflag())){
        		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
    	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
                 	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER, UserSession.getCurrent().getOrganisation().getOrgid());
                     if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                     	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                     	getAdminAboutUs().setChekkerflag(MainetConstants.FLAGN);
                     }
    	    	}
             }
            iEIPAboutUsService.saveAboutUs(getAdminAboutUs(),this.getChekkerflag());
            if(this.getChekkerflag() !=null && this.getChekkerflag().equals(MainetConstants.FlagN)) {
            this.setSuccessMessage(getAppSession().getMessage("admin.update.successmsg"));//admin.save.successmsg
            }else {
            	if(getAdminAboutUs().getChekkerflag() !=null && getAdminAboutUs().getChekkerflag().equals(MainetConstants.FlagY)) {
            		  this.setSuccessMessage(getAppSession().getMessage("admin.approve.successmsg"));
            	}else {
            		  this.setSuccessMessage(getAppSession().getMessage("admin.reject.successmsg"));
            	}
            }
            Utility.sendSmsAndEmail(getAppSession().getMessage("eip.admin.aboutUs.title"),getAdminAboutUs().getChekkerflag(),id,getAdminAboutUs().getUpdatedBy());
            return true;
        } catch (final Exception exception) {
            LOG.error(MainetConstants.ERROR_OCCURED, exception);
            return false;
        }
    }

    /**
     * @return the adminAboutUs
     */
    public EIPAboutUs getAdminAboutUs() {
        return adminAboutUs;
    }

    /**
     * @param adminAboutUs the adminAboutUs to set
     */
    public void setAdminAboutUs(final EIPAboutUs adminAboutUs) {
        this.adminAboutUs = adminAboutUs;
    }

	public String getChekkerflag() {
		return chekkerflag;
	}

	public void setChekkerflag(String chekkerflag) {
		this.chekkerflag = chekkerflag;
	}

    
}
