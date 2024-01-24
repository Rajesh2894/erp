package com.abm.mainet.tradeLicense.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.tradeLicense.dto.InspectionDetailDto;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.ui.model.InspectionDetailFormModel;

@WebService
public interface InspectionDetailService {
	
	public InspectionDetailDto saveInspection(InspectionDetailDto inspectionDto,InspectionDetailFormModel model);

	public void saveNoticeDetails(List<NoticeDetailDto> noticeDetDtoList,InspectionDetailFormModel model);

	public List<NoticeDetailDto> getNoticeDataById(String licNo,Long noticeNo, Long orgId);

	public NoticeDetailDto getDetailsToPrintLetter(String licNo, Long inspNo, Long noticeNo, Long orgId);
	
	public Long generateInspectionNo(Long orgId);

	public void saveRenewalReminderNotice(NoticeDetailDto noticeDetailDto);

	public void updateRenewalReminderNotice(NoticeDetailDto noticeDetailDto);

	public Long generateNoticeNo(Long orgId);

	public NoticeDetailDto getNoticeDetailsByTrdIdAndOrgId(TradeMasterDetailDTO trdMastDto, Long orgId);


}
