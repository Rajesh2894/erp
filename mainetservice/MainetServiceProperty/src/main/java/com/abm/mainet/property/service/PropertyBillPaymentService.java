package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;

public interface PropertyBillPaymentService {

    BillPaymentDetailDto getBillPaymentDetail(String oldPropNo, String propNo, Long orgId, Long userId, Date manualRcptDate,String billingMethod,String flatNo);

    // Long updateBillMasterAmountPaidMobilePayment(Long propNo, Double amount, Long orgId, CommonChallanDTO offline);

    void setTaxDetailInBillDetail(TbBillMas billMas, Organisation org, Long deptId, String flag);

    Long getRebateReceivedCount(String propNo, String flatNo,Date paymentDate,FinancialYear currentYear, Organisation org,Long deptId);
    
	Date getSecondSemStartDate(String propNo, String flatNo, Date paymentDate, FinancialYear currentYear,
			Organisation org, Long deptId);

	BillPaymentDetailDto getBillPaymentDetailForGrp(PropertyBillPaymentDto dto, List<String> propertyNoList, Date manualReeiptDate, Long empId,
			Organisation organisation);

}
