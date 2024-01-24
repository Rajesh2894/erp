package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryEntity;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;

public interface IInvoiceEntryService {

	void saveInvoiceEntryData(InvoiceEntryDTO invoiceEntryDTO);
	
	List<PurchaseOrderDto> getPONumbersByStore(Long storeId, Long orgId);

	List<GoodsReceivedNotesDto> getGRNNumberListByPoId(Long storeId, Long poId, long orgid);

	InvoiceEntryDTO fetchInvoiceGRNAndItemDataByGRNId(InvoiceEntryDTO invoiceEntryDTO, Long orgId);
	
	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster service);

	void updateWorkFlowStatus(String invoiceNo, Long orgId, Character invoiceStatus, String wfFlag);

	InvoiceEntryDTO invoiceEntryEditAndView(Long invoiceId, Long orgId);

	List<InvoiceEntryDTO> fetchInvoiceEntrySummaryData(Long invoiceId, Long poId, Date fromDate, Date toDate, Long storeId, 
					Long vendorId, Long orgId);

	InvoiceEntryDTO getInvoiceEntityToInvoiceDto(InvoiceEntryEntity invoiceEntryEntity, Long orgId);

	InvoiceEntryDTO getInvoiceApprovalData(String invoiceNo, Long orgId);

	List<PurchaseOrderDto> getPONumbersForInvoiceSummary(Long orgId);

	void updateInvoicePaymentDet(String invoiceNo, BigDecimal invoiceAmt, Long orgId);

	void initializeWorkFlowForFreeService(InvoiceEntryDTO invoiceEntryDTO, ServiceMaster service);

}
