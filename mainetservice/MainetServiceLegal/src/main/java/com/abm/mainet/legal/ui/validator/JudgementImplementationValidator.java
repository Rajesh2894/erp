package com.abm.mainet.legal.ui.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.domain.JudgementMaster;
import com.abm.mainet.legal.dto.JudgementDetailDTO;
import com.abm.mainet.legal.repository.JudgementMasterRepository;

@Component
public class JudgementImplementationValidator extends BaseEntityValidator<JudgementDetailDTO> {

	@Autowired
	JudgementMasterRepository judgementMasterRepository;

	@Override
	protected void performValidations(JudgementDetailDTO entity,
			EntityValidationContext<JudgementDetailDTO> validation) {

		int pageNumber = 0;
		int pageSize = 1;
		Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "judDate");
		List<JudgementMaster> latestJudge = judgementMasterRepository.getLatestJudge(entity.getCseId(),
				entity.getOrgid(), pageable);
		if (CollectionUtils.isNotEmpty(latestJudge)) 
		{
			JudgementMaster judgementMaster = latestJudge.get(0);
			//Defect #18243 Judgement implementation dates less than Judgement date
			if (Utility.compareDate(entity.getImplementationStartDate(),judgementMaster.getJudDate())) {
				validation.addOptionConstraint(ApplicationSession.getInstance().getMessage("legal.case.Judgement.validation.judgement.date"));
			}
		} 
		else
		{
			validation.addOptionConstraint(ApplicationSession.getInstance().getMessage("legal.case.Judgement.validation.judgement.date"));
		}

	}
}