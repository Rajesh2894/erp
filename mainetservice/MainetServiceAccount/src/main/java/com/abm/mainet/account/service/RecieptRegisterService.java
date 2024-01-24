package com.abm.mainet.account.service;

import java.util.List;
import java.util.Map;

public interface RecieptRegisterService {

    Map<Long, String> getRecieptRegisterEmployeeDetails(Long OrgId);

    List<Object[]> getRecieptChallanDeatiles(Long employeeListid, String fromDate, String toDate, Long orgid);

    Long findByRmRcptidTbSrcptModesDetEntity(Long rmRcptid);

    List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntity(String fromDate, String toDate, Long orgid,
            Long EmployeeListid);

    List<Object[]> getRecieptChallanDeatilesAll(String fromDate, String toDate, Long orgid);

    List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntityAll(String fromDate, String toDate, Long orgid);

    Map<Long, String> getRecieptRegisterEmployeeAllDetails(Long orgId);

}
