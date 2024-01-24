package com.abm.mainet.common.dao;

import java.util.List;

public interface ISupplementaryPayBillEntryDAO {

	Long checkIsValidEmployee(String empId, String ulbCode);

	List<Object> getEmployeeData(String empId, String payMonth, String payYear, String ulbCode, String suppType);

	List<Object> getEmployeePayDetails(Object[] ipValues, int[] sqlTypes);

}
