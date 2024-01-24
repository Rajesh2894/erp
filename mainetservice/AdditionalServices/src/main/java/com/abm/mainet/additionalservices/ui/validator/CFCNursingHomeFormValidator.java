package com.abm.mainet.additionalservices.ui.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

@Component
public class CFCNursingHomeFormValidator extends BaseEntityValidator<NursingHomePermisssionModel>{

	@Resource
	private TbServicesMstService tbServicesMstService;

	@Override
	protected void performValidations(NursingHomePermisssionModel model,
			EntityValidationContext<NursingHomePermisssionModel> entityValidationContext) {
		
		final ApplicationSession session = ApplicationSession.getInstance();
         // #129863
		String shortCode = tbServicesMstService.getServiceShortDescByServiceId(model.getCfcApplicationMst().getSmServiceId());
		if ((MainetConstants.CFCServiceCode.Hospital_Sonography_center).equals(shortCode)) {
			if (model.getcFCSonographyMastDtos() != null ) {
			if ((model.getcFCSonographyMastDtos().getDocumentList() != null) && !model.getcFCSonographyMastDtos().getDocumentList().isEmpty()) {
				for (final DocumentDetailsVO doc : model.getcFCSonographyMastDtos().getDocumentList()) {
					if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("cfc.upload.mand.doc"));
						break;
					}
				}
			}
			if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getCenterAddress())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.centreAddress"));
			}if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getCenterName())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.centreName"));
			}if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getApplicantName())){
				entityValidationContext.addOptionConstraint(session.getMessage("TCP.validation.app.name"));
			}if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getContactNo())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.conNumber"));
			}if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getDiagProcedure())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.diagTest"));
			}if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getWorkArea())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.workarea"));
			}if (model.getcFCSonographyMastDtos().getInstitutionType() == null ){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.instituteType"));
			}
			//EmailId non mandatory 
			/*if (StringUtils.isEmpty(model.getcFCSonographyMastDtos().getEmailId())){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.emailId"));
			}*/
			if (model.getcFCSonographyMastDtos().getCenterType() == null){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.center.type"));
			}
			if (model.getcFCSonographyMastDtos().getApplyCapacity() == null){
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.validation.capacity"));
			}
			if(!model.getcFCSonographyMastDtos().getCfcSonoDetDtoList().isEmpty()) {
			   model.getcFCSonographyMastDtos().getCfcSonoDetDtoList().forEach(dto ->{
				if(dto.getFacilityCenter() == null && dto.getFacilityTest() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("NHP.validate.facility"));
				}
			});
		  }	
			}else {
				entityValidationContext.addOptionConstraint(session.getMessage("NHP.sonography.submit"));
			}
			
		}else {
		if ((model.getCfcHospitalInfoDTO().getDocumentList() != null) && !model.getCfcHospitalInfoDTO().getDocumentList().isEmpty()) {
			for (final DocumentDetailsVO doc : model.getCfcHospitalInfoDTO().getDocumentList()) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("cfc.upload.mand.doc"));
					break;
				}
			}
		}
	}
	}

}
