package com.abm.mainet.property.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.PropertyMainBillRepository;

@Service
public class ChangeInAssessmentServiceImpl implements ChangeInAssessmentService {

    /**
     * 
     */
    private static final long serialVersionUID = -228746465371104057L;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Override
    @Transactional
    public void setLastPaymentDetails(ProvisionalAssesmentMstDto assMst, Long orgId) throws Exception {

        TbServiceReceiptMasEntity recMaster = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(orgId, assMst.getAssNo());
        Date fromDate = propertyMainBillRepository.fetchLastPaidBillFromDate(assMst.getAssNo(), orgId);
        /*
         * BillingScheduleDetailEntity billSchDet = billingScheduleService .getSchedulebySchFromDate(orgId, fromDate);
         */
        if (recMaster != null) {
            assMst.setProAssBillPayment("C");// Computerized
            assMst.setAssLpReceiptNo(recMaster.getRmRcptno().toString());
            assMst.setAssLpReceiptDate(recMaster.getRmDate());
            assMst.setAssLpReceiptAmt(recMaster.getRmAmount());
            if (fromDate != null) {
                assMst.setProAssLpYearDesc(Utility.getFinancialYearFromDate(fromDate));
            }

        }
    }

}
