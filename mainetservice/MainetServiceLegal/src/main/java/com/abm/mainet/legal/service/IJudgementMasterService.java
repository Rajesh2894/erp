package com.abm.mainet.legal.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.legal.dto.JudgementMasterDto;

public interface IJudgementMasterService {

    List<JudgementMasterDto> fetchJudgementDataByIds(Long cseId, Long crtId, Long orgId);

    void saveJudgementData(List<JudgementMasterDto> judgementMasterList, List<DocumentDetailsVO> attachments);

    void updateJudgementData(List<JudgementMasterDto> judgementMasterList, List<DocumentDetailsVO> attachments,
            List<AttachDocs> attachDocs, List<Long> removeIds);

    Date getJudgementMasterdate(Long orgId, Long cseId);
    
    Date getJudgementMasterdateById(Long cseId);
    
}
