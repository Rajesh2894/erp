/**
 * 
 */
package com.abm.mainet.sfac.ui.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.sfac.dto.FarmerMasterDto;

/**
 * @author pooja.maske
 *
 */
@Component
public class FarmerMasterValidator extends BaseEntityValidator<FarmerMasterDto>{

	/* (non-Javadoc)
	 * @see com.abm.mainet.common.ui.validator.BaseEntityValidator#performValidations(java.lang.Object, com.abm.mainet.common.ui.validator.EntityValidationContext)
	 */
	@Override
	protected void performValidations(FarmerMasterDto dto,
			EntityValidationContext<FarmerMasterDto> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();
		
		if (StringUtils.isEmpty(dto.getFrmFPORegNo())){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.FPORegNo"));
		}
		if (StringUtils.isEmpty(dto.getFrmName())){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.farmerName"));
		}
		if (StringUtils.isEmpty(dto.getFrmFatherName())){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.farmerType"));
		}
		if (dto.getFrmReservation() == null || dto.getFrmReservation() == 0){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.reservation"));
		}
		if (dto.getFrmGender() == null || dto.getFrmGender() == 0){
		 entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.frmGender"));
	    }
		/*if (dto.getFrmSDB1() == null || dto.getFrmSDB1() == 0){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.SDB1"));
		}
		if (dto.getFrmSDB2() == null || dto.getFrmSDB2() == 0){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.SDB2"));
		}
		if (dto.getFrmSDB3() == null || dto.getFrmSDB3() == 0){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.SDB3"));
		}*/
		/*if (null == dto.getFrmLandDet()){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.landDetails"));
		}
		if (null == dto.getFrmLandUnit() || dto.getFrmLandUnit() == 0){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.landUnit"));
		}*/
		if (null == dto.getFrmEquityShare()){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.equityShare"));
		}
		/*if (null != dto.getFrmAadharNo() && dto.getFrmAadharNo() <= 12){
			entityValidationContext.addOptionConstraint(session.getMessage("sfac.validation.AdharNo"));
		}*/
	}

}
