package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;

public interface DemandNoticeGenarationService {

    List<DemandNoticeResponseDTO> searchAllDefaulter(
            DemandNoticeRequestDTO request);

    boolean generateDemandNotice(
            List<DemandNoticeResponseDTO> demands,
            long userId, long orgId, long langId);

    /**
     * @param request
     * @return
     */
    List<DemandNoticeResponseDTO> searchAllDemand(
            DemandNoticeRequestDTO request);

    /**
     * @param demandNoticeType
     * @return
     */
    LookUp getDemandType(String demandNoticeType, final Organisation organisation);

    void updateNoticeDueDate(Date dueDAte, Date distDate, long orgid, long dnId);

	List<DemandNoticeResponseDTO> searchAllDefaulterForAscl(DemandNoticeRequestDTO request, Long csIdn);

}
