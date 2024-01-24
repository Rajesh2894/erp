package com.abm.mainet.common.service;

import java.util.List;

public interface ISupplementaryPayBillEntryService {

	boolean checkIsValidEmployee(String empId, String ulbCode);

	List<Object> getEmployeeData(String empId, String payMonth, String payYear, String ulbCode, String suppType);

	List<Object> getEmployeePayDetails(String empId, String payMonth, String payYear, String string, String suppType,
			String suppMonth, String suppYear, String eLeave, String mLeave, String hpLeave, String workDays);

}
