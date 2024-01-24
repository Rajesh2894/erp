package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.ui.model.PropertyDemandNoticeGenerationModel;

public interface PropertyNoticeDeletionService {

    boolean validateNoticeDeletion(long noticeNo, LookUp typelookUp, long noticeType, long orgId);

    void deleteNoticByNoticeNO(long noticeNo, Long orgId);

    List<NoticeGenSearchDto> fetchPropertyForNoticeDeletion(PropertyDemandNoticeGenerationModel model, long orgId,
            long noticeType);

    boolean validateNoticeForExistOrNot(long noticeNo, LookUp typelookUp, long noticeType, long orgId);

}
