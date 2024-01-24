package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.repository.AccountPermanentAdvancesRegisterRepository;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountPermanentAdvancesRegisterServiceImpl implements AccountPermanentAdvancesRegisterService {

    private static final Logger LOGGER = Logger.getLogger(RegisterOfAdvanceServiceImpl.class);

    @Resource
    AccountPermanentAdvancesRegisterRepository accountPermanentAdvancesRegisterRepository;
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Override
    @Transactional(readOnly = true)
    public AdvanceEntryDTO findPermanentAdvanceLadger(String asOnDate, Long currentOrgId) {
        AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
        if (StringUtils.isNotEmpty(asOnDate) && currentOrgId != null) {
            List<AdvanceEntryDTO> advanceEntryEntityListfinal = new ArrayList<>();
            Date asOnDates = Utility.stringToDate(asOnDate);
            List<AdvanceEntryEntity> advanceEntryEntityList = accountPermanentAdvancesRegisterRepository
                    .findPermanentAdvanceLadger(asOnDates, currentOrgId);
            if (!advanceEntryEntityList.isEmpty() && advanceEntryEntityList != null) {
                for (AdvanceEntryEntity AdvanceEntryEntitys : advanceEntryEntityList) {
                    AdvanceEntryDTO AdvanceEntryDTO1 = new AdvanceEntryDTO();

                    BeanUtils.copyProperties(AdvanceEntryEntitys, AdvanceEntryDTO1);

                    List<AccountBillEntryMasterEnitity> accountBillEntryMasterEnitityList = accountPermanentAdvancesRegisterRepository
                            .findAdvanceBill(AdvanceEntryEntitys.getPrAdvEntryId(), currentOrgId);
                    if (!accountBillEntryMasterEnitityList.isEmpty() && accountBillEntryMasterEnitityList != null) {
                        for (AccountBillEntryMasterEnitity accountBillEntryMasterEnitity : accountBillEntryMasterEnitityList) {

                            AdvanceEntryDTO1.setBillDate(Utility.dateToString(accountBillEntryMasterEnitity.getBillEntryDate()));
                            AdvanceEntryDTO1.setBillPaidAmount(accountBillEntryMasterEnitity.getInvoiceValue());
                            AdvanceEntryDTO1.setNatureOfbill(accountBillEntryMasterEnitity.getNarration());
                        }
                    }

                    if (AdvanceEntryEntitys.getAdvanceAmount() != null) {
                        AdvanceEntryDTO1.setAdvanceAmount(AdvanceEntryEntitys.getAdvanceAmount().toString());
                    }
                    if (AdvanceEntryEntitys.getBalanceAmount() != null) {
                        AdvanceEntryDTO1.setBalanceAmount(AdvanceEntryEntitys.getBalanceAmount().toString());
                    }
                    if (AdvanceEntryEntitys.getPrAdvEntryDate() != null) {
                        AdvanceEntryDTO1.setAdvanceDate(Utility.dateToString(AdvanceEntryEntitys.getPrAdvEntryDate()));
                    }
                    if (AdvanceEntryEntitys.getPaymentNumber() != null) {
                        AdvanceEntryDTO1.setPaymentNumber(AdvanceEntryEntitys.getPaymentNumber().toString());
                    }
                    if (AdvanceEntryEntitys.getVendorId() != null) {
                        final TbAcVendormaster vendorList = tbAcVendormasterService.findById(AdvanceEntryEntitys.getVendorId(),
                                currentOrgId);
                        if ((vendorList != null)
                                && ((vendorList.getVmVendorname() != null) && !vendorList.getVmVendorname().isEmpty())) {
                            AdvanceEntryDTO1.setVendorName(vendorList.getVmVendorname());
                        }
                    }

                    advanceEntryEntityListfinal.add(AdvanceEntryDTO1);

                }
                advanceEntryDTO.setAdvanceLedgerList(advanceEntryEntityListfinal);
            }
            return advanceEntryDTO;
        } else {
            LOGGER.error("As On Date Or OrgId is empty asOnDate" + asOnDate + "and orgId" + currentOrgId);
        }
        return advanceEntryDTO;
    }

}
