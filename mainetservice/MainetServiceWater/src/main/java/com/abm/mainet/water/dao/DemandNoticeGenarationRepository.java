package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.water.domain.DemandNotice;
import com.abm.mainet.water.dto.DemandNoticeRequestDTO;
import com.abm.mainet.water.dto.DemandNoticeResponseDTO;

/**
 * @author Lalit.Mohan
 *
 */

public interface DemandNoticeGenarationRepository {

    public List<DemandNoticeResponseDTO> searchAllDefaulter(
            DemandNoticeRequestDTO request, long demandId);

    /**
     * @param notices
     */
    void generateDemandNotice(
            Map<Long, DemandNotice> notices);

    /**
     * @param request
     * @return
     */
    List<DemandNoticeResponseDTO> searchAllDemand(
            DemandNoticeRequestDTO request);

    /**
     * @param orgId
     */
    public Map<Long, DemandNotice> findAllPreviousDemand(
            long orgId);

    /**
     * @param csIdn
     * @param orgId
     * @return
     */
    List<DemandNotice> getTaxCodeByConnectionId(List<Long> csIdn,
            long orgId);

    /**
     * @param connectionId
     * @param orgId
     */
    void deleteDemandNotice(long connectionId, long orgId);

    public void updateNoticeDueDate(Date dueDate, Date distDate, long orgid, long dnId);

	List<DemandNoticeResponseDTO> searchAllDefaulterForAscl(DemandNoticeRequestDTO request, long demandId, Long csIdn);

}
