package com.abm.mainet.socialsecurity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.ui.dto.CancelPensionDto;

@Service
public class CancellationOfPensionServiceImpl implements CancellationOfPensionService {

    @Autowired
    private SchemeApplicationFormRepository schemeApplicationFormRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CancelPensionDto> getAllData(Long orgId) {
        List<CancelPensionDto> cancelPensionlist = new ArrayList<CancelPensionDto>();
        try {
            List<SocialSecurityApplicationForm> entity = schemeApplicationFormRepository.fetchAlldata(orgId);

            if (!entity.isEmpty()) {

                entity.forEach(ent -> {
                    // if one's pension is cancelled that won't be seen in the dropdown
                    if (!ent.getSapiStatus().equals(MainetConstants.FlagC)) {
                        CancelPensionDto dto = new CancelPensionDto();
                        BeanUtils.copyProperties(ent, dto);
                        dto.setBeneficiaryno(ent.getBeneficiarynumber());
                        cancelPensionlist.add(dto);
                    }
                });
            }

        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occurs while finding data from pension cancellation form please check inputs", ex);
        }
        return cancelPensionlist;
    }

    // get data from dropdown of name and beneficiary number
    @Override
    @Transactional(readOnly = true)
    public SocialSecurityApplicationForm findPensionDetails(CancelPensionDto cancelPensionDto) {

        return schemeApplicationFormRepository.findPensionDetails(cancelPensionDto.getBeneficiaryno(),
                cancelPensionDto.getOrgId());
    }

    // save cancellation of method
    @Override
    @Transactional
    public CancelPensionDto saveCancelPension(String pensionCancelReason,
            Date pensionCancelDate, String beneficiaryno) {
        CancelPensionDto dto = new CancelPensionDto();
        try {
            SocialSecurityApplicationForm entity = new SocialSecurityApplicationForm();
            schemeApplicationFormRepository.updateClosePension(pensionCancelReason, pensionCancelDate, beneficiaryno);
            BeanUtils.copyProperties(entity, dto);

        } catch (Exception ex) {
            throw new FrameworkException(
                    "Exception occured while saving the Cancellation Form Form Details so please check all mandatory fields", ex);
        }
        return dto;

    }

}
