package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 *
 */
@Component
public class AdminPublicNoticesLinkValidator extends BaseEntityValidator<PublicNotices> {

    @Override
    protected void performValidations(final PublicNotices entity,
            final EntityValidationContext<PublicNotices> validationContext) {
        validationContext.rejectIfNotSelected(entity.getDepartment().getDpDeptid(), "department");

        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);
        if (MainetConstants.NON.equals(check)) {
            if ((entity.getNoticeSubEn() == null) || entity.getNoticeSubEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.noticesub1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getNoticeSubEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.noticesub1en"));
                } else if ((entity.getNoticeSubEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.noticesub1"));
                }
            }

            if ((entity.getNoticeSubReg() == null) || entity.getNoticeSubReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.noticesub2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getNoticeSubReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.noticesub2mar"));
                }
            }

            if ((entity.getDetailEn() == null) || entity.getDetailEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getDetailEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.detail1en"));
                } else if ((entity.getDetailEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail1"));
                }
            }

            if ((entity.getDetailReg() == null) || entity.getDetailReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.detail2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getDetailReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eippublicnotice.detail2mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getNoticeSubEn(), "noticeSubEn");
            validationContext.rejectIfEmpty(entity.getNoticeSubReg(), "noticeSubReg");
            /*
             * validationContext.rejectIfEmpty(entity.getDetailEn(), "detailEn");
             * validationContext.rejectIfEmpty(entity.getDetailReg(), "detailReg");
             */
        }
        validationContext.rejectIfNull(entity.getIssueDate(), "issueDate");

        validationContext.rejectIfNull(entity.getValidityDate(), "validityDate");

        if ((entity.getIssueDate() != null) && (entity.getValidityDate() != null)) {
            if (entity.getValidityDate().before(entity.getIssueDate())
                    && (!Utility.comapreDates(entity.getIssueDate(), entity.getValidityDate()))) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("PublicNotices.date.validate"));
            }

        }

        validationContext.rejectIfNotSelected(entity.getPublishFlag(), "publishFlag");
        
        if (entity.isCategoryFlag() && "0".equalsIgnoreCase(entity.getNewOrImpLink())) {
        	
        	 validationContext.addOptionConstraint(getApplicationSession().getMessage("PublicNotices.category.selection"));
        }
        

        // check either download link or external link

        /*
         * if ((FileUploadUtility.getCurrent().getFileMap().size() == 0) && (entity.getProfileImgPath() == null)) {
         * validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.upload")); }
         */
        	//Defect #129997
		        if(entity.getLinkType()!= null) {
		         
        	
        	
	        	if (entity.getLinkType().equalsIgnoreCase("E")) {  
	
					String external = entity.getLink();
	
					if (external.isEmpty() || external == null) 
					{
						validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.externaltype"));
					}
					  
		
				}
				else if (entity.getLinkType().equalsIgnoreCase("L")) {  
	
					String local = entity.getLink();
	
					if (local.isEmpty() || local == null) 
					{
						validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.localtype"));
					}
				}
		        }
		        else
		        {
		        	validationContext.addOptionConstraint(getApplicationSession().getMessage("eip.dept.linktype"));
		         }
		        
      		        	
    
        
    }

}
