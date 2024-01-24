package com.abm.mainet.additionalservices.service;

import com.abm.mainet.additionalservices.ui.model.DuplicatePaymentReceiptModel;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;

public interface DuplicatePaymentReceiptService {

	TbCfcApplicationMst saveData(TbCfcApplicationMst applicationMst, CFCApplicationAddressEntity addressEntity,
			TbReceiptDuplicateDTO receiptDuplicateDto, DuplicatePaymentReceiptModel duplicatePaymentReceiptModel);

	void setChallanDToandSaveChallanData(CommonChallanDTO offline, TbCfcApplicationMst applicationMst,
			CFCApplicationAddressEntity addressEntity, DuplicatePaymentReceiptModel model);

}
