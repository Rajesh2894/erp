package com.abm.mainet.water.ui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.NewWaterConnectionFormModel;

@Component
public class NewWaterConnectionFormValidator extends BaseEntityValidator<NewWaterConnectionFormModel> {

	/*
	 * @Autowired private WaterCommonService waterCommonService;
	 */

	@Autowired
	NewWaterConnectionService waterService;

	@Override
	protected void performValidations(final NewWaterConnectionFormModel model,
			final EntityValidationContext<NewWaterConnectionFormModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if ((model.getCsmrInfo().getTypeOfApplication() != null)
				&& model.getCsmrInfo().getTypeOfApplication().equals(MainetConstants.WHITE_SPACE)) {
			entityValidationContext.addOptionConstraint(session.getMessage("water.validation.typeApplication"));
		} else {
			if ((model.getCsmrInfo().getTypeOfApplication() != null) && model.getCsmrInfo().getTypeOfApplication()
					.equals(MainetConstants.NewWaterServiceConstants.APPLICATION_TYPE)) {

				if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
					entityValidationContext.rejectCompareDate(model.getCsmrInfo().getFromDate(), "fromDate",
							model.getCsmrInfo().getToDate(), "toDate");
				} else {
					if (model.getCsmrInfo().getFromDate() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("Please Select From Period"));
					}
					if (model.getCsmrInfo().getToDate() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("Please Select To Period"));
					}
				}
				try {
					if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
						waterService.findNoOfDaysCalculation(model.getCsmrInfo(),
								UserSession.getCurrent().getOrganisation());
						if (checkDate(model.getCsmrInfo().getFromDate(), model.getCsmrInfo().getToDate(),
								model.getCsmrInfo().getMaxNumberOfDay()))
							entityValidationContext.addOptionConstraint(
									session.getMessage("Temporary connection period should not be greater than "
											+ model.getCsmrInfo().getMaxNumberOfDay() + " Days"));
					}
				} catch (FrameworkException exp) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("water.validation.prefix.not.found.noOfDays"));
				}

			}
		}

		if (model.getReqDTO().getIsConsumer() == null) {
			// entityValidationContext.rejectIfNotSelected(model.getCsmrInfo().getCsTitle(),
			// "csTitle");
			entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsName(), "csName");
			// entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsLname(),
			// "csLname");

			if (model.getCsmrInfo().getCsAdd() == null || model.getCsmrInfo().getCsAdd().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ConsumerArea"));
			}
			if (model.getCsmrInfo().getCsCpinCode() == null) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ConsumerPincode"));
			}
		}

		if (model.getReqDTO().getIsBillingAddressSame() == null) {

			if ((model.getCsmrInfo().getCsBadd() == null) || model.getCsmrInfo().getCsBadd().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.billingArea"));
			}
			if (model.getCsmrInfo().getBpincode() == null || model.getCsmrInfo().getBpincode().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.billingPincode"));
			}

		}
		// entityValidationContext.rejectIfNotSelected(model.getCsmrInfo().getCsOtitle(),
		// "csOtitle");
		if((model.getIsWithoutProp() != null && StringUtils.equals(model.getIsWithoutProp(), MainetConstants.FlagN)) && (StringUtils.isBlank(model.getPropNoOptionalFlag()) || 
				StringUtils.equals(model.getPropNoOptionalFlag(), MainetConstants.FlagN))) {
		entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsOname(), "csOname");
		}
		// entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsOlname(),
		// "csOlname");
		if (model.getCsmrInfo().getBplFlag() == null || model.getCsmrInfo().getBplFlag().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("water.validation.isabovepovertyline"));
		} else {
			if (model.getCsmrInfo().getBplFlag().equals(MainetConstants.FlagY)
					&& (model.getCsmrInfo().getBplNo() == null || model.getCsmrInfo().getBplNo().isEmpty()))
				entityValidationContext.addOptionConstraint(session.getMessage("Please Enter BPL No."));
		}
		if((model.getIsWithoutProp() != null && StringUtils.equals(model.getIsWithoutProp(), MainetConstants.FlagN)) && (StringUtils.isBlank(model.getPropNoOptionalFlag()) || StringUtils.equals(model.getPropNoOptionalFlag(), MainetConstants.FlagN))) {
			if (model.getCsmrInfo().getCsOadd() == null || model.getCsmrInfo().getCsOadd().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerArea"));
			}
			if (model.getCsmrInfo().getOpincode() == null || model.getCsmrInfo().getOpincode().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerPincode"));
			}
		}

		if (model.getCsmrInfo().getCsCcnsize() == null || model.getCsmrInfo().getCsCcnsize() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("Please Select Connection Size"));
		}

		performGroupValidation("csmrInfo.trmGroup");
		performGroupValidation("csmrInfo.csCcncategory");
		performGroupValidation("csmrInfo.codDwzid");
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)){
			performGroupValidation("csmrInfo.coBDwzid");
		}
		
		/*if ((model.getCsmrInfo().getCsPtype() != null)) {
			if (model.getCsmrInfo().getPlumId() == null) {
				entityValidationContext.addOptionConstraint(session.getMessage("Please Select Plumber Name"));
			}
		}*/
		String panCheck = model.getCsmrInfo().getCsTaxPayerFlag();

		if ("Y".equalsIgnoreCase(panCheck)) {
		    if(model.getReqDTO().getApplicantDTO().getPanNo() == null || model.getReqDTO().getApplicantDTO().getPanNo().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("Please Enter Pan Number"));
		    }
		    else {
			Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
			    String panNumber = model.getReqDTO().getApplicantDTO().getPanNo();
			    
			    String code_chk = panNumber.substring(3, 4);
			   String[] validatePan = {"C","H","F","A","T","B","L","J","G","P"};
			   boolean check = false;
			   for(String validatePanNo :validatePan) {
			       if(code_chk.equals(validatePanNo)) {
				   check=true;
				   break;
			       }
			   }
			    Matcher matcher = pattern.matcher(panNumber);
			    if (!matcher.matches() || !check) {
				entityValidationContext.addOptionConstraint(session.getMessage("Please Enter Valid Pan Number"));
			    }
		    }

		}

		if ((model.getReqDTO().getDocumentList() != null) && !model.getReqDTO().getDocumentList().isEmpty()) {
			for (final DocumentDetailsVO doc : model.getReqDTO().getDocumentList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if (doc.getDocumentByteCode() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("water.fileuplaod.validtnMsg"));
						break;
					}
				}
			}
		}
		if (model.getReqDTO().getExistingConsumerNumber() != null) {
			if ((model.getCsmrInfo().getLinkDetails() != null) && !model.getCsmrInfo().getLinkDetails().isEmpty()) {
				for (final TbKLinkCcnDTO link : model.getCsmrInfo().getLinkDetails()) {
					if ((link.getLcOldccn() != null) && link.getLcOldccn().equals(MainetConstants.BLANK)) {
						entityValidationContext.addOptionConstraint(session.getMessage("old.conNo"));
					}
				}
			}
		}
		if (model.getCsmrInfo().getApplicantType() != null
				&& PrefixConstants.NEC.ENGINEER.equals(CommonMasterUtility
						.getNonHierarchicalLookUpObject(model.getCsmrInfo().getApplicantType()).getLookUpCode())
				&& model.getCsmrInfo().getOwnerList() != null && !model.getCsmrInfo().getOwnerList().isEmpty()) {
			for (final AdditionalOwnerInfoDTO owner : model.getCsmrInfo().getOwnerList()) {
				if ((owner.getOwnerFirstName() != null) && owner.getOwnerFirstName().equals(MainetConstants.BLANK)) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ownerfirstname"));
				}
				if ((owner.getOwnerLastName() != null) && owner.getOwnerLastName().equals(MainetConstants.BLANK)) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ownerlastname"));
				}
				if ((owner.getOwnerTitle() != null)
						&& owner.getOwnerTitle().equals(MainetConstants.Common_Constant.ZERO_SEC)) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ctitle"));
				}
				if ((owner.getGender() != null) && (owner.getGender() == 0L)) {
					entityValidationContext.addOptionConstraint(session.getMessage("owner.gender"));
				}
			}
		}

	}

	public boolean checkDate(Date fromDate, Date toDate, Long noOfDays) {
		boolean flag = false;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fromD = df.format(fromDate);
		String toD = df.format(toDate);
		long diff;
		try {
			diff = df.parse(toD).getTime() - df.parse(fromD).getTime();
			Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			long range = TimeUnit.DAYS.toDays(days);
			if (range > noOfDays)
				flag = true;
		} catch (ParseException e) {
			throw new FrameworkException("Exception While parsing Dates in checkDate()", e);
		}

		return flag;
	}

}
