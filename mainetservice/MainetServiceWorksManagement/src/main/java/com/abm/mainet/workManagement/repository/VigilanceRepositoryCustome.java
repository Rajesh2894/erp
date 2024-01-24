package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.Vigilance;

public interface VigilanceRepositoryCustome {

	/**
	 * get filter vigilance list by refType and/or refNumber and/or memoDate and/or
	 * inspectionDate and/or orgId
	 * 
	 * @param refType
	 * @param refNumber
	 * @param memoDate
	 * @param inspectionDate
	 * @param orgId
	 * @return
	 */
	List<Vigilance> getFilterVigilanceList(String refType, String refNumber, Date memoDate, Date inspectionDate,
			Long orgId);

}
