package com.abm.mainet.water.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.water.repository.BillMasterJpaRepository;

@Service
public class BillDistributionServiceImpl implements BillDistributionService {

    @Resource
    private BillMasterJpaRepository billMasterJpaRepository;

    @Resource
    private TbWtBillMasService tbWtBillMasService;

    @Resource
    private DemandNoticeGenarationService demandNoticeGenarationService;

    @Override
    @Transactional
    public boolean updateBillDueDate(final List<TbBillMas> billMas, final Organisation organisation,
            final Long distributionType) {
        final String lookupCode = CommonMasterUtility.getNonHierarchicalLookUpObject(distributionType, organisation)
                .getLookUpCode();
        billMas.stream()
                .filter(bill -> MainetConstants.NewWaterServiceConstants.YES.equals(bill.getBmEntryFlag()))
                .forEach(bill -> {
                    LookUp demandNotice = null;
                    if (MainetConstants.BILL_DISTRIBUTION.equals(lookupCode)) {
                        final Calendar cal = tbWtBillMasService.getBillingScheduleDueDate(bill.getBmMeteredccn(), organisation,
                                bill.getDistDate());
                        billMasterJpaRepository.updateBillDueDate(cal.getTime(), bill.getDistDate(), organisation.getOrgid(),
                                bill.getBmIdno());
                    } else {
                        if (MainetConstants.DEMAND_DISTRIBUTION.equals(lookupCode)) {
                            demandNotice = demandNoticeGenarationService
                                    .getDemandType(MainetConstants.DemandNotice.DEMAND_NOTICE, organisation);
                        } else if (MainetConstants.FINAL_DEMAND_DISTRIBUTION.equals(lookupCode)) {
                            demandNotice = demandNoticeGenarationService
                                    .getDemandType(MainetConstants.DemandNotice.FINAL_DEMAND_NOTICE, organisation);
                        }
                        final Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, Integer.parseInt(demandNotice.getOtherField()));
                        demandNoticeGenarationService.updateNoticeDueDate(cal.getTime(), bill.getDistDate(),
                                organisation.getOrgid(), bill.getBmIdno());
                    }
                });
        return true;
    }
}
