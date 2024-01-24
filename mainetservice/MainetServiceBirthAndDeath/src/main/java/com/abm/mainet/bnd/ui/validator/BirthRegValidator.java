package com.abm.mainet.bnd.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class BirthRegValidator extends BaseEntityValidator<BirthRegistrationDTO> {

	@Override
	protected void performValidations(BirthRegistrationDTO birthRegDTO,
			EntityValidationContext<BirthRegistrationDTO> entityValidationContext) {

		// Birth Registration validation start
		if (birthRegDTO.getBrDob() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrDob(), "Date Of Birth");
		}

		if (birthRegDTO.getBrSex() != null && !(birthRegDTO.getBrSex().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrSex(), "Birth Sex");
		}

		if (birthRegDTO.getBrBirthWt() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrBirthWt(), "Birth Weight");
		}
		// Birth Registration validation end

		// Child Birth validation start
		if (birthRegDTO.getBrChildName() != null && !(birthRegDTO.getBrChildName().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrChildName(), "Child Name (in english)");
		}

		if (birthRegDTO.getBrChildNameMar() != null && !(birthRegDTO.getBrChildNameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrChildNameMar(), "Child Name (in marathi)");
		}

		/*
		 * if (birthRegDTO.getBrBirthPlaceType() != null &&
		 * !(birthRegDTO.getBrBirthPlaceType().isEmpty())) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getBrBirthPlaceType()
		 * , "Birth Place Type"); }
		 */

		if (birthRegDTO.getBrHospital() != null && !(birthRegDTO.getBrHospital().isEmpty())) {
			entityValidationContext.rejectIfNotSelected(birthRegDTO.getBrHospital(), "Hospital Name");
		}

		if (birthRegDTO.getBrBirthPlace() != null && !(birthRegDTO.getBrBirthPlace().isEmpty())) {
			entityValidationContext.rejectIfNotSelected(birthRegDTO.getBrBirthPlace(), "Birth Place (in english)");
		}

		if (birthRegDTO.getBrBirthPlaceMar() != null && !(birthRegDTO.getBrBirthPlaceMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrBirthPlaceMar(), "Birth Place (in marathi)");
		}

		if (birthRegDTO.getBrBirthAddr() != null && !(birthRegDTO.getBrBirthAddr().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrBirthAddr(), "Birth Address (in english)");
		}

		if (birthRegDTO.getBrBirthAddrMar() != null && !(birthRegDTO.getBrBirthAddrMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrBirthAddrMar(), "Birth Address (in marathi)");
		}

		if (birthRegDTO.getBrInformantName() != null && !(birthRegDTO.getBrInformantName().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrInformantName(), "Informant Name (in english)");
		}

		if (birthRegDTO.getBrInformantNameMar() != null && !(birthRegDTO.getBrInformantNameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrInformantNameMar(), "Informant Name (in marathi)");
		}

		if (birthRegDTO.getBrInformantAddr() != null && !(birthRegDTO.getBrInformantAddr().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrInformantAddr(), "Informant Address (in english)");
		}

		if (birthRegDTO.getBrInformantAddrMar() != null && !(birthRegDTO.getBrInformantAddrMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrInformantAddrMar(),
					"Informant Address (in marathi)");
		}

		/*
		 * if (birthRegDTO.getCpdAttntypeId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getCpdAttntypeId(),
		 * "Attention Type"); }
		 * 
		 * if (birthRegDTO.getCpdDelMethId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getCpdDelMethId(),
		 * "Delivery Method"); }
		 */

		if (birthRegDTO.getBrPregDuratn() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrPregDuratn(), "Pregnency Duration");
		}

		if (birthRegDTO.getBrBirthMark() != null && !(birthRegDTO.getBrBirthMark().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getBrBirthMark(), "Birth Mark");
		}
		// Child Birth validation end

		// Parent Detail validation start
		if (birthRegDTO.getParentDetailDTO().getPdFathername() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdFathername().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdFathername(),
					"Father Name (in english)");
		}

		if (birthRegDTO.getParentDetailDTO().getPdFathernameMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdFathernameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdFathernameMar(),
					"Father Name (in marathi)");
		}

		/*
		 * if (birthRegDTO.getParentDetailDTO().getCpdFEducnId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdFEducnId(), "Father Education"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdFOccuId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdFOccuId(), "Father Occupation"); }
		 */

		if (birthRegDTO.getParentDetailDTO().getPdMothername() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdMothername().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdMothername(),
					"Mother Name (in english)");
		}

		if (birthRegDTO.getParentDetailDTO().getPdMothernameMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdMothernameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdMothernameMar(),
					"Mother Name (in Hindi)");
		}

		/*
		 * if (birthRegDTO.getParentDetailDTO().getCpdMEducnId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdMEducnId(), "Mother Education"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdMOccuId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdMOccuId(), "Mother Occupation"); }
		 */

		if (birthRegDTO.getParentDetailDTO().getPdAgeAtMarry() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAgeAtMarry(),
					"Mother's age at the time of marriage");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAgeAtBirth() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAgeAtBirth(),
					"Mother's age at the time of child birth");
		}

		if (birthRegDTO.getParentDetailDTO().getPdLiveChildn() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdLiveChildn(),
					"Number of live children");
		}
		// Parent Detail validation end

		// Parent Address validation start
		if (birthRegDTO.getParentDetailDTO().getMotheraddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getMotheraddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getMotheraddress(),
					"Mother's Address(in English)");
		}

		if (birthRegDTO.getParentDetailDTO().getMotheraddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getMotheraddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getMotheraddress(),
					"Mother's Address(in Marathi)");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdAddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAddress(),
					"Parent's Address At time of child birth(in English)");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAddressMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdAddressMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAddressMar(),
					"Parent's Address At time of child birth(in Marathi)");
		}

		/*
		 * if (birthRegDTO.getParentDetailDTO().getCpdId1() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdId1(), "Country"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdId2() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdId2(), "State"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdId3() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdId3(), "District"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdId4() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdId4(), "Taluka"); }
		 * 
		 * if (birthRegDTO.getParentDetailDTO().getCpdReligionId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getCpdReligionId(), "Religion"); }
		 */

		/*
		 * if (birthRegDTO.getParentDetailDTO().getPdRegUnitId() != null) {
		 * entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().
		 * getPdRegUnitId(), "Registration unit"); }
		 */
		// Parent Address validation end

	}

}