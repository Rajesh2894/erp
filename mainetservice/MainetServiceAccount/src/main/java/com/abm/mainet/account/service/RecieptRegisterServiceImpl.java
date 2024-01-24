package com.abm.mainet.account.service;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;

@Service
public class RecieptRegisterServiceImpl implements RecieptRegisterService {

    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntrRrepository;

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getRecieptRegisterEmployeeDetails(final Long orgId) {

        final Map<Long, String> empListMap = new LinkedHashMap<>();
        empListMap.put(0L, "All");
        if ((orgId != null) && (orgId != 0L)) {
            final List<Object[]> EmployeeList = accountReceiptEntrRrepository.getRecieptRegisterEmployeeDetails(orgId);
            if (!EmployeeList.isEmpty()) {
                for (final Object[] objects : EmployeeList) {
                    if ((objects[0] != null)) {
                        if (objects[1] != null && objects[2] != null && objects[3] != null) {
                            empListMap.put((Long) objects[0], objects[1].toString() + MainetConstants.WHITE_SPACE
                                    + objects[2].toString() + MainetConstants.WHITE_SPACE + objects[3].toString());
                        } else if (objects[1] != null && objects[2] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.WHITE_SPACE + objects[2].toString());
                        } else if (objects[1] != null && objects[3] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.WHITE_SPACE + objects[3].toString());
                        } else if (objects[1] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString());
                        }
                    }
                }
            }
        }
        return empListMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getRecieptChallanDeatiles(final Long employeeListid, final String fromDate, final String toDate,
            final Long orgid) {
        Date fromDates = null;
        Date toDates = null;
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            fromDates = Utility.stringToDate(fromDate);
            toDates = Utility.stringToDate(toDate);
        }
        List<Object[]> receiptsAcList = accountReceiptEntrRrepository.getRecieptChallanDeatiles(employeeListid, fromDates,
                toDates,
                orgid);

        if (!receiptsAcList.isEmpty()) {
            return receiptsAcList;
        } else {
            return null;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Long findByRmRcptidTbSrcptModesDetEntity(Long rmRcptid) {
        //D#133658
        List<Long> idList= accountReceiptEntrRrepository.findByRmRcptidFromTbSrcptModesDetEntity(rmRcptid);
        if(CollectionUtils.isNotEmpty(idList))
        	return idList.get(0);
        else
        	return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntity(String fromDate, String toDate,
            Long orgid, Long EmployeeListid) {
        Date fromDates = null;
        Date toDates = null;
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            fromDates = Utility.stringToDate(fromDate);
            toDates = Utility.stringToDate(toDate);
        }

        return accountReceiptEntrRrepository.findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntity(fromDates, toDates, orgid,
                EmployeeListid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getRecieptChallanDeatilesAll(String fromDate, String toDate, Long orgid) {
        Date fromDates = null;
        Date toDates = null;
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            fromDates = Utility.stringToDate(fromDate);
            toDates = Utility.stringToDate(toDate);
        }
        List<Object[]> receiptsAcList = accountReceiptEntrRrepository.getRecieptChallanDeatilesAll(fromDates,
                toDates,
                orgid);

        if (!receiptsAcList.isEmpty()) {
            return receiptsAcList;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntityAll(String fromDate, String toDate, Long orgid) {
        Date fromDates = null;
        Date toDates = null;
        if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            fromDates = Utility.stringToDate(fromDate);
            toDates = Utility.stringToDate(toDate);
        }

        return accountReceiptEntrRrepository.findByOrgIdAndDateAndCpdVlaueFromTbSrcptFeesDetEntityAll(fromDates, toDates, orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getRecieptRegisterEmployeeAllDetails(Long orgId) {

        final Map<Long, String> empListMap = new LinkedHashMap<>();
        // empListMap.put(0L, "All");
        if ((orgId != null) && (orgId != 0L)) {
            final List<Object[]> EmployeeList = accountReceiptEntrRrepository.getRecieptRegisterEmployeeDetails(orgId);
            if (!EmployeeList.isEmpty()) {
                for (final Object[] objects : EmployeeList) {
                    if ((objects[0] != null)) {
                        if (objects[1] != null && objects[2] != null && objects[3] != null) {
                            empListMap.put((Long) objects[0], objects[1].toString() + MainetConstants.WHITE_SPACE
                                    + objects[2].toString() + MainetConstants.WHITE_SPACE + objects[3].toString());
                        } else if (objects[1] != null && objects[2] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.WHITE_SPACE + objects[2].toString());
                        } else if (objects[1] != null && objects[3] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.WHITE_SPACE + objects[3].toString());
                        } else if (objects[1] != null) {
                            empListMap.put((Long) objects[0],
                                    objects[1].toString());
                        }
                    }
                }
            }
        }
        return empListMap;
    }

}
