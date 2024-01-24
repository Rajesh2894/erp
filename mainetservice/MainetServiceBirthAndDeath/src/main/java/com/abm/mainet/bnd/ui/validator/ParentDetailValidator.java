package com.abm.mainet.bnd.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class ParentDetailValidator extends BaseEntityValidator<BirthRegistrationDTO> {

	@Override
	protected void performValidations(BirthRegistrationDTO birthRegDTO,
			EntityValidationContext<BirthRegistrationDTO> entityValidationContext) {

		if (birthRegDTO.getParentDetailDTO().getPdFathername() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdFathername().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdFathername(), "Father Name");
		}

		if (birthRegDTO.getParentDetailDTO().getPdFathernameMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdFathernameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdFathernameMar(),
					"Father Name Mar");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdFEducnId() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdMEducnId(),
					"Father Education");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdFOccuId() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdFOccuId(),
					"Father Occupation");
		}

		if (birthRegDTO.getParentDetailDTO().getPdMothername() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdMothername().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdMothername(), "Mother Name");
		}

		if (birthRegDTO.getParentDetailDTO().getPdMothernameMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdMothernameMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdMothernameMar(),
					"Mother Name Mar");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdMEducnId() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdMEducnId(),
					"Mother Education");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdMOccuId() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdMOccuId(),
					"Mother Occupation");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAgeAtMarry() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAgeAtMarry(),
					"Parent Age At Marriage");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAgeAtBirth() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAgeAtBirth(),
					"Parent Age At Birth");
		}

		if (birthRegDTO.getParentDetailDTO().getPdLiveChildn() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdLiveChildn(),
					"Live No. Of Children");
		}

		if (birthRegDTO.getParentDetailDTO().getMotheraddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getMotheraddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getMotheraddress(),
					"Mother Address");
		}

		if (birthRegDTO.getParentDetailDTO().getMotheraddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getMotheraddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getMotheraddress(),
					"Mother Address Mar");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAddress() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdAddress().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAddress(), "Parent Address");
		}

		if (birthRegDTO.getParentDetailDTO().getPdAddressMar() != null
				&& !(birthRegDTO.getParentDetailDTO().getPdAddressMar().isEmpty())) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getPdAddressMar(),
					"Parent Address Mar");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdId1() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdId1(), "Country");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdId2() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdId2(), "State");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdId3() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdId3(), "District");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdId4() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdId4(), "Block");
		}

		if (birthRegDTO.getParentDetailDTO().getCpdId5() != null) {
			entityValidationContext.rejectIfEmpty(birthRegDTO.getParentDetailDTO().getCpdId5(), "City/Town/Village");
		}

		if (birthRegDTO.getParentDetailDTO().getPdRegUnitId() != null) {
			entityValidationContext.rejectIfNotSelected(birthRegDTO.getParentDetailDTO().getPdRegUnitId(),
					"Registration Unit");
		}

	}

}
