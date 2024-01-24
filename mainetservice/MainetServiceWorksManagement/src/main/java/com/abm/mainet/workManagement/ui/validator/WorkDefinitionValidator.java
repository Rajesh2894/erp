package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;

/**
 * @author hiren.poriya
 * @Since 07-Dec-2017
 */
@Component
public class WorkDefinitionValidator extends BaseEntityValidator<WorkDefinitionDto> {

	@Override
	protected void performValidations(WorkDefinitionDto dto,
			EntityValidationContext<WorkDefinitionDto> validationContext) {

		if (dto.getWorkName() == null || dto.getWorkName().isEmpty()) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.enter.workname"));
		}

		if (dto.getProjId() == null) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.projName"));
		}

		/* REMOVE AS PER SUDA UAT */

		/*
		 * if (dto.getWorkStartDate() == null) {
		 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
		 * "work.Def.valid.select.work.startdate")); } if (dto.getWorkEndDate() == null)
		 * { validationContext.addOptionConstraint(getApplicationSession().getMessage(
		 * "work.Def.valid.select.work.enddate")); } if (dto.getProjId() != null &&
		 * dto.getWorkStartDate() != null && dto.getWorkEndDate() != null) {
		 * WmsProjectMasterDto projectMasterDto =
		 * iProjectMasterService.getProjectMasterByProjId(dto.getProjId()); if
		 * (UtilityService.compareDateField(dto.getWorkStartDate(),
		 * projectMasterDto.getProjStartDate()) ||
		 * UtilityService.compareDateField(projectMasterDto.getProjEndDate(),
		 * dto.getWorkEndDate())) { validationContext
		 * .addOptionConstraint(getApplicationSession().getMessage(
		 * "work.Def.valid.stardate.enddate.with.project")); } }
		 */
		if (dto.getWorkType() == null || dto.getWorkType() == 0) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.worktype"));
		}

		if (dto.getDeptId() == null) {
			validationContext
					.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.execut.dept"));
		}

		/*
		 * if (dto.getWorkProjPhase() == null || dto.getWorkProjPhase() == 0) {
		 * validationContext.addOptionConstraint(getApplicationSession().getMessage(
		 * "work.Def.valid.select.proj.phase")); }
		 */

		if (dto.getWorkCategory() == null || dto.getWorkCategory() == 0) {
			validationContext
					.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.workCategory"));
		}

		if (dto.getLocIdSt() == null) {
			validationContext
					.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.start.loc"));
		}

		/*if (dto.getLocIdEn() == null) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("work.Def.valid.select.end.loc"));
		}*/

	}

}
