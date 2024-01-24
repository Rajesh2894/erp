package com.abm.mainet.common.master.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractTermsDetailDTO;
import com.abm.mainet.common.master.ui.model.ContractAgreementModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author apurva.salgaonkar
 *
 */
@Component
public class ContractAgreementFormValidator extends BaseEntityValidator<ContractAgreementModel> {
	@Override
	protected void performValidations(final ContractAgreementModel model,
			final EntityValidationContext<ContractAgreementModel> entityValidationContext) {

		final ApplicationSession session = ApplicationSession.getInstance();

		if (model.getContractMastDTO().getContDate() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.date"));
		}
		if ((model.getContractMastDTO().getContType() == null) || (model.getContractMastDTO().getContType() == 0)) {
			entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.type"));
		}
		
		if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
		    if (model.getContractMastDTO().getContRsoDate() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.resolution.date"));
                    }
		    if (model.getContractMastDTO().getContTndDate() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.tender.date="));
                    }
		    if ((model.getContractMastDTO().getContTndNo() == null)
                            || model.getContractMastDTO().getContTndNo().isEmpty()) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.tender.no"));
                    }
	        }
		
		
		if ((model.getContractMastDTO().getContRsoNo() == null)
				|| model.getContractMastDTO().getContRsoNo().isEmpty()) {
			/*
			 * entityValidationContext.addOptionConstraint(session.getMessage(
			 * "rnl.resolution.no"));
			 */
		}
		
		
		if (model.getContractMastDTO().getContractDetailList().get(0).getContFromDate() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.from.date"));
		}
		if (model.getContractMastDTO().getContractDetailList().get(0).getContToDate() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.to.date"));
		}

		if (model.getContractMastDTO().getContMode().equals(MainetConstants.FlagC)) {
			if (model.getContractMastDTO().getContractDetailList().get(0).getContAmount() == null)
				entityValidationContext.addOptionConstraint(session.getMessage("agreement.enterContractAmount"));
		}
		/*
		 * if ((model.getContractMastDTO().getContRenewal() == null) ||
		 * model.getContractMastDTO().getContRenewal().contains("0")) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.renewal")); }
		 */
		/*
		 * if ((model.getContractMastDTO().getContractPart1List().get(0).getDpDeptid()
		 * == null) ||
		 * (model.getContractMastDTO().getContractPart1List().get(0).getDpDeptid() ==
		 * 0)) { entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.ulb.dept")); } if
		 * ((model.getContractMastDTO().getContractPart1List().get(0).getDsgid() ==
		 * null) || (model.getContractMastDTO().getContractPart1List().get(0).getDsgid()
		 * == 0)) { entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.ulb.desgn")); } if
		 * ((model.getContractMastDTO().getContractPart1List().get(0).getEmpid() ==
		 * null) || (model.getContractMastDTO().getContractPart1List().get(0).getEmpid()
		 * == 0)) { entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.ulb.repst.by")); } int k = 1; for (final ContractPart1DetailDTO
		 * contractPart1DetailDTO : model.getContractMastDTO().getContractPart1List()) {
		 * 
		 * if ((contractPart1DetailDTO.getContp1Type() != null) &&
		 * contractPart1DetailDTO.getContp1Type().equals("W")) { if
		 * ((contractPart1DetailDTO.getContp1Name() == null) ||
		 * contractPart1DetailDTO.getContp1Name().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.ulb.witness.name" + k)); } if
		 * ((contractPart1DetailDTO.getContp1Address() == null) ||
		 * contractPart1DetailDTO.getContp1Address().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.ulb.witness.add" + k)); } if
		 * ((contractPart1DetailDTO.getContp1ProofIdNo() == null) ||
		 * contractPart1DetailDTO.getContp1ProofIdNo().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contractulb.witness.proof" + k)); } k++;
		 * 
		 * } } int i = 1; int j = 1; for (final ContractPart2DetailDTO
		 * contractPart2DetailDTO : model.getContractMastDTO().getContractPart2List()) {
		 * if ((contractPart2DetailDTO.getContp2Type() != null) &&
		 * contractPart2DetailDTO.getContp2Type().equals("V")) { if
		 * ((contractPart2DetailDTO.getContp2vType() == null) ||
		 * (contractPart2DetailDTO.getContp2vType() == 0)) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.vender.type" + i)); } if
		 * ((contractPart2DetailDTO.getVmVendorid() == null) ||
		 * (contractPart2DetailDTO.getVmVendorid() == 0)) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.vender.name" + i)); }
		 * 
		 * if ((contractPart2DetailDTO.getContp2Name() == null) ||
		 * contractPart2DetailDTO.getContp2Name().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.vender.repst.by" + i)); } i++; } else if
		 * ((contractPart2DetailDTO.getContp2Type() != null) &&
		 * contractPart2DetailDTO.getContp2Type().equals("W")) {
		 * 
		 * if ((contractPart2DetailDTO.getContp2Name() == null) ||
		 * contractPart2DetailDTO.getContp2Name().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contractvender.witness.name" + j)); }
		 * 
		 * if ((contractPart2DetailDTO.getContp2Address() == null) ||
		 * contractPart2DetailDTO.getContp2Address().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.vender.witness.add" + j)); } if
		 * ((contractPart2DetailDTO.getContp2ProofIdNo() == null) ||
		 * contractPart2DetailDTO.getContp2ProofIdNo().isEmpty()) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "rnl.contract.vender.proof" + j)); } j++;
		 * 
		 * } }
		 */
		int k = 1;
		for (final ContractTermsDetailDTO contractTermsDetailDTO : model.getContractMastDTO()
				.getContractTermsDetailList()) {
			if ((contractTermsDetailDTO.getConttDescription() == null)
					|| contractTermsDetailDTO.getConttDescription().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.vender.term.condtn" + k));
			}
			k++;
		}
		if (model.getContractMastDTO().getContMode() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("rnl.contract.mode"));
		}

	}

}
