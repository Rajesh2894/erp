package com.abm.mainet.buildingplan.ui.validator;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.dto.DevLicenseHDRUDTO;
import com.abm.mainet.buildingplan.dto.DeveloperAuthorizedUserDTO;
import com.abm.mainet.buildingplan.dto.DeveloperDirectorInfoDTO;
import com.abm.mainet.buildingplan.dto.DeveloperStakeholderDTO;
import com.abm.mainet.buildingplan.ui.model.DeveloperRegistrationFormModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
public class developerRegFormValidator extends BaseEntityValidator<DeveloperRegistrationFormModel> {


	@Override
	protected void performValidations(final DeveloperRegistrationFormModel model,
			final EntityValidationContext<DeveloperRegistrationFormModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if (model.getDeveloperRegistrationDTO().getDevType() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("select.developer.type"));
		} 
		if (model.getDeveloperRegistrationDTO().getDevType() != null) {

			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					model.getDeveloperRegistrationDTO().getDevType(), UserSession.getCurrent().getOrganisation());
			if (lookUp.getLookUpCode() == "COM" || lookUp.getLookUpCode() == "PAF") {
				if (model.getDeveloperRegistrationDTO().getCinNo() == null
						|| model.getDeveloperRegistrationDTO().getCinNo() == "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.cin.no"));
				}
			}
			if (lookUp.getLookUpCode() == "COM" || lookUp.getLookUpCode() == "PAF" || lookUp.getLookUpCode() == "LLP") {
				if (model.getDeveloperRegistrationDTO().getCompanyName() == null
						|| model.getDeveloperRegistrationDTO().getCompanyName() == "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.company.name"));
				}
				if (model.getDeveloperRegistrationDTO().getDateOfIncorporation() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.date.of.incorporation"));
				}
				if (model.getDeveloperRegistrationDTO().getRegisteredAddress() == null
						|| model.getDeveloperRegistrationDTO().getRegisteredAddress() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.registered.address"));
				}
				if (model.getDeveloperRegistrationDTO().getEmail() == null
						|| model.getDeveloperRegistrationDTO().getEmail() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.email"));
				}
				if (model.getDeveloperRegistrationDTO().getMobileNo() == null
						) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.mobile.no"));
				}
				if (model.getDeveloperRegistrationDTO().getGstNo() == null
						|| model.getDeveloperRegistrationDTO().getGstNo() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.GST.no"));
				}
			}
			if (lookUp.getLookUpCode() == "IND" || lookUp.getLookUpCode() == "PRF" || lookUp.getLookUpCode() == "HUF") {
				if (model.getDeveloperRegistrationDTO().getName() == null
						|| model.getDeveloperRegistrationDTO().getName() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.name"));
				}
				if (model.getDeveloperRegistrationDTO().getGender() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.gender"));
				}
				if (model.getDeveloperRegistrationDTO().getDateOfBirth() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.dob"));
				}
				if (model.getDeveloperRegistrationDTO().getRegisteredAddress() == null
						|| model.getDeveloperRegistrationDTO().getRegisteredAddress() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.registered.address"));
				}
				if (model.getDeveloperRegistrationDTO().getEmail() == null
						|| model.getDeveloperRegistrationDTO().getEmail() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.email"));
				}
				if (model.getDeveloperRegistrationDTO().getMobileNo() == null
						) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.mobile.no"));
				}
				if (model.getDeveloperRegistrationDTO().getGstNo() == null
						|| model.getDeveloperRegistrationDTO().getGstNo() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.GST.no"));
				}
				if (model.getDeveloperRegistrationDTO().getPanNo() == null
						|| model.getDeveloperRegistrationDTO().getPanNo() != "") {
					entityValidationContext.addOptionConstraint(session.getMessage("please enter PAN No"));
				}
			}
			if (lookUp.getLookUpCode() == "LLP") {
				if (model.getDeveloperRegistrationDTO().getLlpNo() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.LLP.no"));
				}
			}

			if (lookUp.getLookUpCode() == "COM" || lookUp.getLookUpCode() == "PAF") {
				if(!model.getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().isEmpty()){
					for (int i = 0; i < model.getDeveloperRegistrationDTO().getDeveloperStakeholderDTOList().size(); i++) {
						DeveloperStakeholderDTO developerStakeholderDTOList = model.getDeveloperRegistrationDTO()
								.getDeveloperStakeholderDTOList().get(i);
						if (developerStakeholderDTOList.getStakeholderName() == null
								|| developerStakeholderDTOList.getStakeholderName() != "") {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.devloper.name"));
						}
						if (developerStakeholderDTOList.getStakeholderDesignation() == null
								|| developerStakeholderDTOList.getStakeholderDesignation() != "") {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.devloper.designation"));
						}
						if (developerStakeholderDTOList.getStakeholderPercentage() == null
								) {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.devloper.percentage"));
						}
					}
				}				
			}
		}
		
		if (model.getDeveloperRegistrationDTO().getDirectorInfoFlag() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("select.Yes.No"));
		}
		
		if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag()!=null){
			if(model.getDeveloperRegistrationDTO().getDirectorInfoFlag().equals("Y")){		
				if(!model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().isEmpty()){
					for(int i=0; i<model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().size(); i++){
						DeveloperDirectorInfoDTO developerDirectorDTOList = model.getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().get(i);
						if (developerDirectorDTOList.getDinNumber()== null || developerDirectorDTOList.getDinNumber()!="") {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.din.number"));
						}
						if (developerDirectorDTOList.getDirectorName() == null || developerDirectorDTOList.getDirectorName() !="") {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.director.name"));
						}
					}
				}
			}
		
		}
		
		if (model.getDeveloperRegistrationDTO().getLicenseInfoFlag() == null || model.getDeveloperRegistrationDTO().getLicenseInfoFlag() !="") {
			entityValidationContext.addOptionConstraint(session.getMessage("select.Yes.No"));
		}
		
		if(model.getDeveloperRegistrationDTO().getLicenseInfoFlag()!=null){
			if(model.getDeveloperRegistrationDTO().getLicenseInfoFlag().equals("Y")){
				if (model.getDeveloperRegistrationDTO().getLicenseNo() == null || model.getDeveloperRegistrationDTO().getLicenseNo() !="") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.license.No"));
				}
				if (model.getDeveloperRegistrationDTO().getDevName() == null || model.getDeveloperRegistrationDTO().getDevName() !="") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.developer.name"));
				}
				if (model.getDeveloperRegistrationDTO().getDevLicenseDate() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.dateOfGrantLicense.date"));
				}
			}
		}
		
		
		if(!model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().isEmpty()){
			for(int i=0; i<model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().size(); i++){
				DeveloperAuthorizedUserDTO developerAuthorizedUserDTOList = model.getDeveloperRegistrationDTO().getDeveloperAuthorizedUserDTOList().get(i);
				if (developerAuthorizedUserDTOList.getAuthUserName()== null || developerAuthorizedUserDTOList.getAuthUserName()!="") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.auth.user.name"));
				}
				if (developerAuthorizedUserDTOList.getAuthMobileNo() == null ) {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.auth.mobile.no"));
				}
				if (developerAuthorizedUserDTOList.getAuthEmail() == null || developerAuthorizedUserDTOList.getAuthEmail() !="") {
					entityValidationContext.addOptionConstraint(session.getMessage("enter.auth.email"));
				}
				if (developerAuthorizedUserDTOList.getAuthGender() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("select.auth.gender"));
				}
				if (developerAuthorizedUserDTOList.getAuthDOB() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("select.auth.DOB"));
				}
				if (developerAuthorizedUserDTOList.getAuthPanNumber() == null || developerAuthorizedUserDTOList.getAuthPanNumber() !="") {
					entityValidationContext.addOptionConstraint(session.getMessage("select.auth.pan.number"));
				}
			}
		}
		
		
		if (model.getDeveloperRegistrationDTO().getLicenseHDRUFlag() == null) {
			entityValidationContext.addOptionConstraint(session.getMessage("select.Yes.No"));
		}
		if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag()!= null){
			if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals("Y")){
				if(!model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().isEmpty()){
					for(int i=0; i<model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().size(); i++){
						DevLicenseHDRUDTO devLicenseHDRUDTOList = model.getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().get(i);
						if (devLicenseHDRUDTOList.getLicenseNo()== null || devLicenseHDRUDTOList.getLicenseNo()!="") {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.license.no"));
						}
						if (devLicenseHDRUDTOList.getDateOfGrantLicense() == null) {
							entityValidationContext.addOptionConstraint(session.getMessage("enter.date.of.grant.license"));
						}
						if (devLicenseHDRUDTOList.getPurposeOfColony() == null) {
							entityValidationContext.addOptionConstraint(session.getMessage("select.purpose.of.colony"));
						}
						if (devLicenseHDRUDTOList.getDateOfValidityLicense() == null) {
							entityValidationContext.addOptionConstraint(session.getMessage("selecta.dte.of.validity.license"));
						}
					}
				}
				
			}
		}
		
		
		if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag()!= null){
			if(model.getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals("N")){
				if (model.getDeveloperRegistrationDTO().getProjectsFlag() == null || model.getDeveloperRegistrationDTO().getProjectsFlag() !="") {
					entityValidationContext.addOptionConstraint(session.getMessage("select.Yes.No"));
				}
				if (model.getDeveloperRegistrationDTO().getProjectsFlag().equals("Y")) {
					if (model.getDeveloperRegistrationDTO().getProjectName() == null || model.getDeveloperRegistrationDTO().getProjectName() !="") {
						entityValidationContext.addOptionConstraint(session.getMessage("enter.project.name"));
					}
					if (model.getDeveloperRegistrationDTO().getAthorityName() == null || model.getDeveloperRegistrationDTO().getAthorityName() !="") {
						entityValidationContext.addOptionConstraint(session.getMessage("enter.authority.name"));
					}
					if (model.getDeveloperRegistrationDTO().getDevStatus() == null || model.getDeveloperRegistrationDTO().getDevStatus() !="") {
						entityValidationContext.addOptionConstraint(session.getMessage("enter.status.of.development"));
					}
				}
			}
		}	
	}
}
