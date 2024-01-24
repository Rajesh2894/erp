package com.abm.mainet.sfac.service;

import com.abm.mainet.sfac.dto.AuditBalanceSheetMasterDto;
import com.abm.mainet.sfac.ui.model.ABSEntryFormModel;

public interface AuditBalanceSheetEntryService {

	AuditBalanceSheetMasterDto findFPODetails(Long masId);

	AuditBalanceSheetMasterDto saveDetails(AuditBalanceSheetMasterDto dto, ABSEntryFormModel absEntryFormModel);

	AuditBalanceSheetMasterDto fetchABSDetails(Long valueOf);

	void updateApprovalStatusAndRemark(AuditBalanceSheetMasterDto oldMasDto, 
			String lastDecision, String status);

	AuditBalanceSheetMasterDto fetchABSEntryReqDetailbyAppId(Long valueOf);

}
