/**
 * 
 */
package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.rnl.dto.LeaseDemandNoticeGenerationDTO;

/**
 * @author divya.marshettiwar
 *
 */
public interface LeaseDemandNoticeGenerationService {

	String generateNoticeNumber(LookUp noticeTypeByLookup, Organisation org);

	LeaseDemandNoticeGenerationDTO saveNoticeForm(LeaseDemandNoticeGenerationDTO demandNoticeDto,Organisation orgId);

	List<LeaseDemandNoticeGenerationDTO> findPropertyNo(Long orgId, String refNo);

	List<LeaseDemandNoticeGenerationDTO> findByLocationId(Long orgId, Long locationId);

	void saveNoticeFormList(List<LeaseDemandNoticeGenerationDTO> demandNoticeDtoList, Long notTyp,Organisation orgId);

	List<LeaseDemandNoticeGenerationDTO> findSecondReminderNotice(Long notTyp, Long orgId);

	LeaseDemandNoticeGenerationDTO findFirstReminderNotice(Long notTyp, String refNo, Long orgId);

	List<LeaseDemandNoticeGenerationDTO> getfirstReminderNoticeByNoticeType(Long notTyp, Long orgId);
}
