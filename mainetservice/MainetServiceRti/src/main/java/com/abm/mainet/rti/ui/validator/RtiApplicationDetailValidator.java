/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Validation file for RTI Application Form for Server Side Validation.
 * @method  : performValidations - check the mandatory validation of the field
 *              
 */
package com.abm.mainet.rti.ui.validator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiApplicationFormModel;

@Component
public class RtiApplicationDetailValidator extends BaseEntityValidator<RtiApplicationFormModel> {
	@Resource
	private IWorkflowTyepResolverService iWorkflowTyepResolverService;
	@Autowired
	private IRtiApplicationDetailService iRtiService;

	@Override
	protected void performValidations(RtiApplicationFormModel model,
			EntityValidationContext<RtiApplicationFormModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if (model.getReqDTO().getApplReferenceMode() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicantType"));
		} else if (model.getReqDTO().getApplReferenceMode() == 1
				&& (model.getReqDTO().getApplicantDTO().getOrganizationName() == null
						|| model.getReqDTO().getApplicantDTO().getOrganizationName().equals(MainetConstants.BLANK))) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.OrganisationName"));
		}
        //D#142047
		if ((model.getReqDTO().getTitleId() == null) || (model.getReqDTO().getTitleId() == 0L)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicantTitle"));
		}
 
		if ((model.getReqDTO().getfName() == null) || (model.getReqDTO().getfName().isEmpty())) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicantFirstName"));
		}
		if ((model.getReqDTO().getlName() == null) || (model.getReqDTO().getlName().isEmpty())) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicantLastName"));
		}
		if ((!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL))&&(model.getReqDTO().getGender() == null) || (model.getReqDTO().getGender().isEmpty())) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.Gender"));
		}
		if ((model.getReqDTO().getMobileNo() == null) || (model.getReqDTO().getMobileNo().isEmpty())) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicantMobileNo"));
		}
		if ((!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL))&&(model.getReqDTO().getRtiLocationId() == 0)) {
			
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.RtiLocationId"));
		}
		if ((model.getReqDTO().getPincodeNo() == null) || (model.getReqDTO().getPincodeNo() == 0L)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.PinCode"));
		}
		if ((model.getReqDTO().getAreaName() == null) || (model.getReqDTO().getAreaName().isEmpty())) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.address"));
		}
		if ((model.getReqDTO().getApplicationType() == null) || (model.getReqDTO().getApplicationType() == 0L)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.ApplicationType"));
		}
		if ((model.getReqDTO().getIsBPL() == null) || (model.getReqDTO().getIsBPL().equals(MainetConstants.BLANK))) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.IsBPL"));
		} else if (model.getReqDTO().getIsBPL() != null && "Y".equalsIgnoreCase(model.getReqDTO().getIsBPL())) {
			if (model.getReqDTO().getBplNo() == null || (model.getReqDTO().getBplNo().equals(MainetConstants.BLANK))) {
				entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.BplNo"));
			} else if (model.getReqDTO().getYearOfIssue() == null || (model.getReqDTO().getYearOfIssue() == 0L)) {
				entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.YearOfIssueBpl"));
			} else if (model.getReqDTO().getBplIssuingAuthority() == null
					|| (model.getReqDTO().getBplIssuingAuthority().equals(MainetConstants.BLANK))) {
				entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.IssuingAuthority"));
			}
		}
		if ((model.getReqDTO().getSubject() == null)
				|| (model.getReqDTO().getSubject().equals(MainetConstants.BLANK))) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.Subject"));
		}
         //D#142047
		/*
		 * if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
		 * MainetConstants.ENV_SUDA)) { if ((model.getReqDTO().getTrdWard1() == null) ||
		 * (model.getReqDTO().getTrdWard1()==0)) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rti.validation.TrdWard1")); } if ((model.getReqDTO().getTrdWard2() == null)
		 * || (model.getReqDTO().getTrdWard2()==0)) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rti.validation.TrdWard2")); } }
		 */
		/* MOM POINTS */
		/*
		 * if ((model.getReqDTO().getRtiDetails() == null) ||
		 * (model.getReqDTO().getRtiDetails().equals(MainetConstants.BLANK))) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rti.validation.RtiDetails")); }
		 */
		/* Check workflow is configured for department */
		// Defect#117838
		boolean mas = iRtiService.checkWoflowDefinedOrNot(model.getReqDTO());
		if (mas == false) {
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.workflow"));
		}
		/* end */

		/* checking manadatory documents */
		if ((model.getCheckList() != null) && !model.getCheckList().isEmpty()) {
			for (final DocumentDetailsVO doc : model.getCheckList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
					break;
				}
			}
		}
		/*
		 * D#127746 for validating Stamp and postal documents
		 */ if (model.getReqDTO().getApplReferenceMode() != 0) {
			LookUp look = CommonMasterUtility.lookUpByLookUpIdAndPrefix(
					Long.valueOf(model.getReqDTO().getApplReferenceMode()), "RRM",
					model.getReqDTO().getOrgId().longValue());
			if (look != null && !StringUtils.isEmpty(look.getLookUpCode())
					&& (look.getLookUpCode().equals(MainetConstants.FlagP)
							|| look.getLookUpCode().equals(MainetConstants.FlagS))) {
				if (!CollectionUtils.isEmpty(model.getUploadStamoDoc())) {
					for (final DocumentDetailsVO doc : model.getUploadStamoDoc()) {
						if (doc.getDocumentByteCode() == null) {
							entityValidationContext
									.addOptionConstraint(session.getMessage("rti.validate." + look.getLookUpCode()));
							break;
						}
					}
				}
			}
		}
		/* end */
		if (((model.getUploadFileList() != null) && !model.getUploadFileList().isEmpty())&& !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)) {
			for (final DocumentDetailsVO doc : model.getUploadFileList()) {
				if (doc.getDocumentByteCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("rti.validate.uploadFiles"));
					break;
				}
			}
		}//D13009 for validation when document is empty or null
		else {
			if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL))
			entityValidationContext.addOptionConstraint(session.getMessage("rti.validate.uploadFiles"));
		}
	}

}
