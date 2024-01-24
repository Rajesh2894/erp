package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.ui.validator.AdminContactUsFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Component
@Scope(value = "session")
public class AdminContactUsModel extends AbstractEntryFormModel<EIPContactUs> {

    private static final long serialVersionUID = -7518560985254982161L;

    private static final Logger LOG = Logger.getLogger(AdminContactUsModel.class);

    @Autowired
    private IEIPContactUsService iEIPContactUsService;

    private String mode;

    private Double previosSeq;

    private String isChecker;

    @Autowired
    private IEntitlementService iEntitlementService;

    @Override
    public boolean saveOrUpdateForm() {

        validateBean(getEntity(), AdminContactUsFormValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        if (!getEntity().getSequenceNo().equals(previosSeq)) {
            final boolean checksequnce = iEIPContactUsService.hasSequenceExist(getEntity().getFlag(), getEntity().getSequenceNo(),
                    UserSession.getCurrent().getOrganisation());
            if (checksequnce) {
                addValidationError(getAppSession().getMessage("eip.admin.contactUs.seqmsg"));
                return false;
            }
        }
        // Designation drop Down changes starts -->
  		if(getEntity().getDesignationEn() != null && !getEntity().getDesignationEn().isEmpty()) {
  			final List<LookUp> portalDesgList = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.PDL,
      				UserSession.getCurrent().getOrganisation());
  			if (portalDesgList != null) {
					portalDesgList.forEach(i -> {
						long desigNationId = 0;
						try {
							desigNationId = new Long(getEntity().getDesignationEn()).longValue();
						} catch (Exception e) {
							logger.error("cannot convert String to long ");
						}
						if (desigNationId == i.getLookUpId()) {
							getEntity().setDesignationEn(i.getDescLangFirst());
							getEntity().setDesignationReg(i.getDescLangSecond());
						}
					});
				}
  		}
  		
  		// Designation drop Down changes ends -->
  		if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.APP_NAME.DSCL)) {
  		if(getEntity().getDesignationEn() == null || getEntity().getDesignationEn().isEmpty() || getEntity().getDesignationReg() == null || getEntity().getDesignationReg().isEmpty() ) {
  			 addValidationError(getAppSession().getMessage("eipcontactus.designation1"));
             return false;
  		}
  		}
  		
        try {        	
        	 long id=getEntity().getContactUsId(); 
        	 if(getEntity().getChekkerflag() !=null) {
        		 getEntity().setChekkerflag(iEntitlementService.isTrustedChecker(getEntity().getChekkerflag(), this.getIsChecker()));
             }
        	 
             iEIPContactUsService.saveContactUs(getEntity());
             Utility.sendSmsAndEmail(getAppSession().getMessage("dashboard.contact")+" "+(UserSession.getCurrent().getLanguageId()==1?getEntity().getContactNameEn():getEntity().getContactNameReg()),getEntity().getChekkerflag(),id,getEntity().getUpdatedBy());
        	 return true;
        } catch (final Exception exception) {

            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, exception);

            return false;
        }

    }

    public Double getPreviosSeq() {
        return previosSeq;
    }

    public void setPreviosSeq(final Double previosSeq) {
        this.previosSeq = previosSeq;
    }

    @Override
    public void addForm() {
        setEntity(new EIPContactUs());
        setMode(MainetConstants.Transaction.Mode.ADD);
    }

    @Override
    public void editForm(final long rowId) {
        setMode("Edit");
        long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            this.setIsChecker("Y");
        } else {
            this.setIsChecker("N");
        }
        final EIPContactUs contactUs = iEIPContactUsService.editContactInfoDetails(rowId,
                UserSession.getCurrent().getOrganisation());
        
        // Designation drop Down changes starts -->
  			final List<LookUp> portalDesgList = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.PDL,
      				UserSession.getCurrent().getOrganisation());
  			
  			if(contactUs != null ) {
  			
  			if (portalDesgList != null) {
					portalDesgList.forEach(i -> {
						if(contactUs.getDesignationEn() != null && contactUs.getDesignationEn().equalsIgnoreCase(i.getDescLangFirst())) {
							contactUs.setDesignationEn(new Long(i.getLookUpId()).toString());
						}
						
					});
				}
  		}
  		
  		// Designation drop Down changes ends -->
  			
        setPreviosSeq(contactUs.getSequenceNo());
        setEntity(contactUs);
    }

    @Override
    public void delete(final long rowId) {

        iEIPContactUsService.delete(rowId, UserSession.getCurrent().getOrganisation());
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(String isChecker) {
        this.isChecker = isChecker;
    }

}
