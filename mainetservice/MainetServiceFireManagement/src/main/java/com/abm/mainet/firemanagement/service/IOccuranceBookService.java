package com.abm.mainet.firemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;

public interface IOccuranceBookService {

	/**
	 * Save a OccuranceBookModel.
	 *
	 * @param OccuranceBookDTO the entity to save
	 * @return the persisted entity
	 */
	String getRecordByDate(Long orgid,Date inputDate,String cmplntId);
	
	
	OccuranceBookDTO save(OccuranceBookDTO occuranceBookDTO);

	/**
	 * Get the OccuranceBookModel.
	 * @param status 
	 *
	 * @param complainNo the complain no of the entity
	 * @param orgid      organization id
	 * @return the entity
	 */
	public List<FireCallRegisterDTO> getAllOccLogBook(Long orgId, String status);

	FireCallRegisterDTO getBookOccId(Long cmplntId);
	
	List<FireCallRegisterDTO> searchFireCallRegisterwithDate(Date toDate, Date fromDate, String fireStation,
			Long orgid);

	String getRecordByTime(Long orgid, String cmplntId);
	
}
