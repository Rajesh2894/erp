package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.workManagement.domain.ApprovalTermsCondition;
import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;
import com.abm.mainet.workManagement.repository.ApprovalTermsConditionRepository;

@Service
public class ApprovalTermsConditionServiceImpl implements ApprovalTermsConditionService {

    @Autowired
    private ApprovalTermsConditionRepository termRepository;

    @Override
    @Transactional
    public void saveTermsCondition(List<ApprovalTermsConditionDto> approvalTermsConditionDto, List<Long> deletedIds) {

        ApprovalTermsCondition entity = null;
        for (ApprovalTermsConditionDto dto : approvalTermsConditionDto) {
            entity = new ApprovalTermsCondition();
            BeanUtils.copyProperties(dto, entity);
            entity.setTermActive(MainetConstants.FlagA);
            inactivedata(entity, deletedIds);
            termRepository.save(entity);

        }

    }

    private void inactivedata(ApprovalTermsCondition entity, List<Long> deletedIds) {
        if (deletedIds != null && !deletedIds.isEmpty()) {
            entity.setUpdatedBy(entity.getCreatedBy());
            termRepository.inActiveIds(deletedIds, entity.getUpdatedBy());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprovalTermsConditionDto> getTermsList(String referenceId, Long orgId) {
        List<ApprovalTermsConditionDto> approvalListDto = new ArrayList<ApprovalTermsConditionDto>();
        List<ApprovalTermsCondition> approvalList = termRepository.getTermsList(referenceId, orgId);
        if (approvalList != null) {
            for (ApprovalTermsCondition entity : approvalList) {
                ApprovalTermsConditionDto dto = new ApprovalTermsConditionDto();
                BeanUtils.copyProperties(entity, dto);
                approvalListDto.add(dto);
            }
        }

        return approvalListDto;
    }

    @Override
    @Transactional
    public void updateSancNoInTermsAndCodition(String sanctionNumber, Long workId, String workEstimateNo) {
        termRepository.updateSanctionNumber(sanctionNumber, workId, workEstimateNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprovalTermsConditionDto> findAllTermsAndCodition(Long workId, String sanctionNumber) {
        List<ApprovalTermsConditionDto> conditionDtosList = new ArrayList<>();

        List<ApprovalTermsCondition> termsConditions = termRepository.findAllTermsAndCondition(workId, sanctionNumber);
        if (termsConditions != null) {

            for (ApprovalTermsCondition condition : termsConditions) {
                ApprovalTermsConditionDto conditionDto = new ApprovalTermsConditionDto();
                BeanUtils.copyProperties(condition, conditionDto);
                conditionDtosList.add(conditionDto);
            }
        }

        return conditionDtosList;
    }

}
