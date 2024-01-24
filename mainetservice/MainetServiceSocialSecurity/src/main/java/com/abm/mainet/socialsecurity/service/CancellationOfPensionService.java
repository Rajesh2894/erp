package com.abm.mainet.socialsecurity.service;


import java.util.Date;
import java.util.List;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.ui.dto.CancelPensionDto;

public interface CancellationOfPensionService {
    
    public List<CancelPensionDto> getAllData(Long orgId);
    
    public CancelPensionDto saveCancelPension(String pensionCancelReason ,Date pensionCancelDate,String beneficiaryno);
    
    public SocialSecurityApplicationForm findPensionDetails(CancelPensionDto dto);

    
    
    
    

}
