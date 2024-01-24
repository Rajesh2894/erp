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

import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionYearDetDto;

/**
 * @author Ajay Kumar
 *
 */

@WebService
public interface IPurchaseRequistionService {

	
	/**
	 * @param purchaseRequistionDto
	 * @return
	 */
	//PurchaseRequistionDto purchaseReqSave(PurchaseRequistionDto purchaseRequistionDto,List<Long> removeDetailsIds,List<Long> removeYearIds);
	void purchaseReqSave(PurchaseRequistionDto purchaseRequistionDto, Long levelCheck, List<Long> removeDetailsIds, List<Long> removeYearIds,
			WorkflowTaskAction workflowTaskAction);
		
	/**
	 * @param prid
	 * @param orgId
	 */
	void purchaseReqFordelete(Long prid,Long orgId);
	
	
	List<PurchaseRequistionDto> purchaseReqSearchForSummaryData(Long storeid,String prno,Date fromDate,Date toDate,Long orgId);
	
	/**
	 * @param prId
	 * @param orgId
	 * @return
	 */
	PurchaseRequistionDto purchaseReqSearchForEditAndView(Long prId,Long orgId);
	
	
	Long  getUomByUsingItemCode(Long orgId,Long itemId);
	
	@POST
	@Path("/fetchPurchaseRequistion/{deptId}/{orgId}")
	@Produces(MediaType.APPLICATION_JSON)
	List<PurchaseRequistionDto> fetchPurchaseRequistionList(@Valid @PathParam("deptId") Long deptId,@Valid @PathParam("orgId") Long orgId);
	
	Long getPrIdById(String appNo, Long orgId);


	void updatePurchaseStatus(long orgid, String referenceId, String flagr);


	void updateWorkFlowPurchaseService(WorkflowTaskAction workflowActionDto);
	
	@POST
	@Path("/UpdatePurchaseReqStatus")
	@Produces(MediaType.APPLICATION_JSON)
	void updateStatusPurchaseReq(@RequestBody PurchaseRequistionDto dto);
	

	@POST
	@Path("/fetchPurchaseReq")
	@Produces(MediaType.APPLICATION_JSON)
	Map<String,String> fetchPurchaseRequistionCode(@RequestBody PurchaseRequistionDto dto);

	
	List<PurchaseRequistionYearDetDto> getAllYearDetEntity(Long orgId,Long prId);
	
	VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO);
	
	List<Object[]> purchaseReqForTender(Long orgId);


	List<PurchaseRequistionDto> getAllDataListPrId(Long orgId, List<Long> prId);

	Date getPrDateByPrId(Long orgId, String prNo);

	PurchaseRequistionDto getPurchaseReuisitonByPONumber(String prNo, Long orgId);

	@POST
	@Path("/fetchPurchaseReqDetails")
	@Produces(MediaType.APPLICATION_JSON)
	List<PurchaseRequistionDetDto> fetchPurchaseReqDetailList(@RequestBody PurchaseRequistionDto dto);

}
