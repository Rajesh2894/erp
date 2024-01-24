package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FireCallRegister;
import com.abm.mainet.firemanagement.domain.TbFmComplainClosure;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;

/**
 * Spring Data repository for the ComplainRegister entity.
 */
@Repository
public interface IFireCallRegisterDAO {

	List<FireCallRegister> searchFireCallRegister(String cmplntNo, String complaintStatus, String fireStation,
			Long orgid);

	List<FireCallRegister> searchFireCallRegisterwithDate(Date fromDate, Date toDate, String fireStation, Long orgid);

	List<FireCallRegister> searchFireData(Long orgId, String status);

	List<FireCallRegister> searchFireCallRegisterReg(Date fromDate,Date toDate,String fireStation,String cmplntNo,Long orgid,String status);

	List<TbFmComplainClosure> searchFireCallCloser(String complainNo, String complaintStatus, String fireStation, Long orgid);

	
	
}
