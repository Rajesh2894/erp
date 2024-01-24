/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.ui.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadRouteDetailsDto;

/**
 * @author satish.rathore
 *
 */
@Component
public class RoadCuttingValidator extends BaseEntityValidator<RoadCuttingDto> {

	@Override
	protected void performValidations(RoadCuttingDto entity,
			EntityValidationContext<RoadCuttingDto> entityValidationContext) {
		
		List<RoadRouteDetailsDto> roadRouteDetailsDto =entity.getRoadList(); 
		/*applicant Information*/
		entityValidationContext.rejectIfEmpty(entity.getApplicantCompName1(), "applicantCompName1");
        entityValidationContext.rejectIfEmpty(entity.getCompanyAddress1(), "companyAddress1");
        entityValidationContext.rejectIfEmpty(entity.getPersonName1(), "personName1");
        entityValidationContext.rejectIfEmpty(entity.getPersonAddress1(), "personAddress1");
        entityValidationContext.rejectIfEmpty(entity.getFaxNumber1(), "faxNumber1");
        entityValidationContext.rejectIfEmpty(entity.getTelephoneNo1(), "telephoneNo1");
        entityValidationContext.rejectIfEmpty(entity.getPersonMobileNo1(), "personMobileNo1");
        entityValidationContext.rejectIfEmpty(entity.getTotalCostOfproject(), "totalCostOfproject");
        entityValidationContext.rejectIfEmpty(entity.getEstimteForRoadDamgCharge(), "estimteForRoadDamgCharge");       
        /*local office detail*/
        entityValidationContext.rejectIfEmpty(entity.getCompanyName2(), "companyName2");
        entityValidationContext.rejectIfEmpty(entity.getCompanyAddress2(), "companyAddress2");
        entityValidationContext.rejectIfEmpty(entity.getPersonName2(), "personName2");
        entityValidationContext.rejectIfEmpty(entity.getPersonAddress2(), "personAddress2");
        entityValidationContext.rejectIfEmpty(entity.getFaxNumber2(), "faxNumber2");
        entityValidationContext.rejectIfEmpty(entity.getTelephoneNo2(), "telephoneNo2");
        entityValidationContext.rejectIfEmpty(entity.getPersonMobileNo2(), "personMobileNo2");
        /*contractor datails*/
        entityValidationContext.rejectIfEmpty(entity.getContractorName(), "contractorName");
        entityValidationContext.rejectIfEmpty(entity.getContractorAddress(), "contractorAddress");
        entityValidationContext.rejectIfEmpty(entity.getContractorContactPerName(), "contractorContactPerName");
        entityValidationContext.rejectIfEmpty(entity.getContracterContactPerMobileNo(), "contracterContactPerMobileNo");
         
        roadRouteDetailsDto.stream().forEachOrdered(dto->{
        	
        	 entityValidationContext.rejectIfEmpty(dto.getTypeOfTechnology(), "typeOfTechnology");
        	 entityValidationContext.rejectIfEmpty(dto.getRoadRouteDesc(), "roadRouteDesc");
        	 entityValidationContext.rejectIfEmpty(dto.getRoadType(), "roadType");
        	 entityValidationContext.rejectIfEmpty(dto.getLength(), "length");
        	 /* entityValidationContext.rejectIfEmpty(dto.getHeight(), "height");
        	 entityValidationContext.rejectIfEmpty(dto.getBreadth(), "breadth");*/
        	 entityValidationContext.rejectIfEmpty(dto.getNumbers(), "numbers");
        	 entityValidationContext.rejectIfEmpty(dto.getQuantity(), "quantity");
        	/* entityValidationContext.rejectIfEmpty(dto.getDiameter(), "diameter"); */    	
        });
		
		
	}
	

}
