package com.abm.mainet.disastermanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.dto.DisasterOccuranceBookDTO;

public interface IDisasterOccuranceBookService {

	DisasterOccuranceBookDTO save(DisasterOccuranceBookDTO occuranceBookDTO);

	List<ComplainRegisterDTO> searchFireCallRegisterwithDate(Date toDate, Date fromDate, Long orgid, Long callType, Long callSubType);

	List<ComplainRegisterDTO> disasterSummaryDate(Long orgId);


	String getRecordByTime(Long orgid, String cmplntId);

}
