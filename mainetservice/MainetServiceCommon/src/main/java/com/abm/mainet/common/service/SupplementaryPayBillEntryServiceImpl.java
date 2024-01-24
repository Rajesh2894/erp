package com.abm.mainet.common.service;

import java.sql.Types;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.dao.ISupplementaryPayBillEntryDAO;

@Service
public class SupplementaryPayBillEntryServiceImpl implements ISupplementaryPayBillEntryService {

	@Resource
	private ISupplementaryPayBillEntryDAO payBillEntryDAO;

	@Override
	public boolean checkIsValidEmployee(String empId, String ulbCode) {
		return payBillEntryDAO.checkIsValidEmployee(empId, ulbCode) >= 1L ? true : false;
	}
	
	@Override
	public List<Object> getEmployeeData(String empId, String payMonth, String payYear, String ulbCode, String suppType) {
		return payBillEntryDAO.getEmployeeData(empId, payMonth, payYear, ulbCode, suppType);
	}

	@Override
	public List<Object> getEmployeePayDetails(String empId, String payMonth, String payYear, String ulbCode,
			String suppType, String suppMonth, String suppYear, String eLeave, String mLeave, String hpLeave,
			String workDays) {
        final Object[] ipValues = new Object[] { empId, payMonth, payYear, ulbCode, eLeave, mLeave, hpLeave, workDays, suppMonth, suppYear };
        final int[] opTypes = new int[] { Types.DECIMAL, Types.DECIMAL, Types.DECIMAL };
        final List<Object> payDetails = payBillEntryDAO.getEmployeePayDetails(ipValues, opTypes);
		return payDetails;
	}

}
