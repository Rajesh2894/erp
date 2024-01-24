package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
public class EIPAnnouncementValidator extends BaseEntityValidator<EIPAnnouncement> {

    @Override
    protected void performValidations(final EIPAnnouncement entity,
            final EntityValidationContext<EIPAnnouncement> validationContext) {

        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);
        if (MainetConstants.NON.equals(check)) {
            if ((entity.getAnnounceDescEng() == null) || entity.getAnnounceDescEng().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getAnnounceDescEng()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.detail1en"));
                } else if ((entity.getAnnounceDescEng().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail1"));
                }
            }

            if ((entity.getAnnounceDescReg() == null) || entity.getAnnounceDescReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getAnnounceDescReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.detail2mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getAnnounceDescEng(), "EngAnnounce");
            validationContext.rejectIfEmpty(entity.getAnnounceDescReg(), "RegAnnounce");
        }
       //#129040
       if(entity.getLinkType()!=null) {
    	   if (!entity.getLinkType().equalsIgnoreCase("E")  && !entity.getLinkType().equalsIgnoreCase("L") && (entity.getAttach() == null || entity.getAttach().isEmpty()) ) {  
                   validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.upload"));
           }
        }else {
        	validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.linktype"));
        }
       /* if(entity.getLinkType()!=null) {
        if(entity.getLinkType().equalsIgnoreCase("E")) {
        	if (entity.getAttachImage() == null) {
                if (FileUploadUtility.getCurrent().getFileMap().size() < 1) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.image"));
                }
            }
        }else if (entity.getLinkType().equalsIgnoreCase("L")) {
        	if (entity.getAttachImage() == null) {
                if (FileUploadUtility.getCurrent().getFileMap().size() < 1) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.image"));
                }
            }
        }else {
        	if (entity.getAttach() == null && entity.getAttachImage() == null) {
                if (FileUploadUtility.getCurrent().getFileMap().size() != 2) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.upload"));
                }
            }
            
        }}else {
        	validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.linktype"));
        }
        
        */
        
        if (entity.getValidityDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.validitydate"));
        }
       
       if ((entity.getNewsDate() != null) && (entity.getValidityDate() != null) && (entity.getHighlightedDate() != null)) {
           if (entity.getHighlightedDate().before(entity.getNewsDate())) {
               validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.announcement.highlighted.date1"));
           }
           if (entity.getHighlightedDate().after(entity.getValidityDate())) {
               validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.announcement.highlighted.date2"));
           }

       }

    }

}
