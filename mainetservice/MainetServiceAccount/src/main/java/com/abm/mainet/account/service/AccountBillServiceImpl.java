package com.abm.mainet.account.service;

import java.util.Date;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountBillServiceImpl implements AccountBillRegisterService {

    @Resource
    private BillEntryRepository billEntryServiceJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Object[]> getBillRegisterDetails(Long orgId, String fromDate, String toDate,Long billTyp) {

        Date fromDates = Utility.stringToDate(fromDate);

        Date toDates = Utility.stringToDate(toDate);

        return billEntryServiceJpaRepository.getBillRegisterDetails(orgId, fromDates, toDates,billTyp);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Object[]> getOutStandingBillRegisterDetails(Long orgId, String fromdate, Long dpDeptid,Long accountHeadId,String allHeads) {
        // TODO Auto-generated method stub
        //billEntryServiceJpaRepository.getOutStandingBillRegisterDetails(orgId, Utility.stringToDate(fromdate), dpDeptid);
         if(allHeads.equalsIgnoreCase(MainetConstants.ALL))
    	    return billEntryServiceJpaRepository.getOutStandingBillRegisterDetailsnNew(orgId, Utility.stringToDate(fromdate), dpDeptid);
         else
        	 return billEntryServiceJpaRepository.getOutStandingBillRegisterDetailsnNewWithAccountHead(orgId, Utility.stringToDate(fromdate), dpDeptid,accountHeadId); 	 
    }

}
