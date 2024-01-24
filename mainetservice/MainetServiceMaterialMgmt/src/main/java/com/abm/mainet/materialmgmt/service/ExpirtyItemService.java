
package com.abm.mainet.materialmgmt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.ExpiryItemDetailsDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;

@WebService
public interface ExpirtyItemService {

	List<ExpiryItemsDto> findByAllGridSearchData(Long storeId, String movementNo, Date fromDate, Date toDate, Long orgId);
	
    List<ExpiryItemsDto> findExpiryItemDetailsByFinId(Long orgId);

	List<ExpiryItemDetailsDto> getEpiryItem(Date fromDate, Date toDate, long orgid);
	
	ExpiryItemsDto saveExpirtyItem(ExpiryItemsDto expiryItemsDto, CommonChallanDTO offline, long levelcheck, WorkflowTaskAction workflowTaskAction, ApplicantDetailDTO applicantDetailDTO);

	List<ExpiryItemsDto> expiryItemSearchForSummaryData(Long storeNameId, String movementNo, Date fromDate, Date toDate, long orgid);
	
	ExpiryItemsDto getExpiryItemDataById(Long expiryId, Long orgId);
	
	ExpiryItemsDto getExpiredItemsDataforExpiry(Long storeId, Date expiryDate, Long orgId);

	String updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ApplicantDetailDTO applicantDetailDTO, ExpiryItemsDto expiryItemsDto);

	void updateDispoalStatus(Long orgid, Long referenceId, String flagr);

	ExpiryItemsDto getExpiryItemDataByApplicationNo(Long appNo, Long orgId);

	ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long expiryId, Long orgId);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fecthExpiryDisposalListForTender/{deptId}/{orgId}")
	List<ExpiryItemsDto> fecthExpiryDisposalListForTender(@Valid @PathParam("deptId") Long deptId, @Valid @PathParam("orgId") Long orgId);

	@POST
	@Path("/updateExpiryDisposalStatus")
	@Produces(MediaType.APPLICATION_JSON)
	void updateExpiryDisposalStatus(@RequestBody ExpiryItemsDto expiryItemsDto);

	@POST
	@Path("/fetchExpiryDisposalCodes")
	@Produces(MediaType.APPLICATION_JSON)
	Map<String,String> fetchExpiryDisposalCodes(@RequestBody ExpiryItemsDto expiryItemsDto);

	
}
