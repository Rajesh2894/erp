package com.abm.mainet.bnd.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

@Component
public class DeathRegValidator extends BaseEntityValidator<TbDeathregDTO> {

	@Override
	protected void performValidations(TbDeathregDTO tbDeathregDTO, EntityValidationContext<TbDeathregDTO> validation) {

		validation.compareWithCurrentDate(tbDeathregDTO.getDrDod(), "Deceassed date");
		validation.rejectIfNotSelected(tbDeathregDTO.getDrSex(), "Sex");
		validation.rejectIfNull(tbDeathregDTO.getDrDeceasedage(), "Deceased Age");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdAgeperiodId(), "age period");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDeceasedname(), "Deceased Name(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarDeceasedname(), "Deceased Name(in  Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMotherName(), "Mother's Name(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarMotherName(), "Mother's Name(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDeceasedaddr(), "Permanent Address(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarDeceasedaddr(), "Permanent Address(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarDeceasedaddr(), "Address at the time of death(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDcaddrAtdeathMar(), "Address at the time of death(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDeathaddr(), "Death Address(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarDeathaddr(), "Death Address(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDeceasedname(), "Deceased Name(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrDeathplace(), "Death place(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarDeathplace(), "Death place(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrInformantName(), "Informant Name(in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarInformantName(), "Informant Name(in Marathi)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrInformantAddr(), "Informant Address (in english)");
		validation.rejectIfEmpty(tbDeathregDTO.getDrMarInformantAddr(), "Informant Address(in  Marathi)");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdReligionId(), "Religion");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdEducationId(), "Education");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdMaritalStatId(), "Marrital Status");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdOccupationId(), "Occupation");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdRegUnit(), "Reg. Unit");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdAttntypeId(), "Attention Type");
		validation.rejectIfNotSelected(tbDeathregDTO.getCpdDeathcauseId(), "Death Cause");

	}

}
