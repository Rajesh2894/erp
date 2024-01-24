package com.abm.mainet.property.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.AdjustmentEntryService;
import com.abm.mainet.cfc.challan.dto.AdjustmentDetailDTO;
import com.abm.mainet.cfc.challan.dto.AdjustmentMasterDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.PropertyDuesCheckModel;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author anwarul.hassan
 * @since 23-Feb-2022
 */
@Controller
@RequestMapping("/PropertyDuesCheck.html")
public class PropertyDuesCheckController  extends AbstractFormController<PropertyDuesCheckModel>{

	@Autowired
	private ViewPropertyDetailsService viewPropertyDetailsService;
	
	@Autowired
    private PropertyBillPaymentService propertyBillPaymentService;
	
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;
    
    @Autowired
    private AdjustmentEntryService adjustmentEntryService;
    
    @Autowired
    private ReceiptRepository receiptRepository;
    
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setShowForm(MainetConstants.FlagN);
        return defaultResult();
    }
	
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request) {
		bindModel(request);
		PropertyDuesCheckModel model = this.getModel();
		model.setSearchDtoResult(new ArrayList<>(0));
		List<ProperySearchDto> properySearchDtoList = new ArrayList<>();
		Organisation org = UserSession.getCurrent().getOrganisation();
		this.getModel().setShowForm(MainetConstants.FlagY);
		model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ProperySearchDto dto = model.getSearchDto();
		if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty())
				&& (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
				&& (dto.getOccupierName() == null || dto.getOccupierName().isEmpty())
				&& (dto.getAddress() == null || dto.getAddress().isEmpty())
				&& (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
				&& (dto.getAssWard2() == null || dto.getAssWard2() <= 0)) {
			model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
		}
		
		List<ProperySearchDto> result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(),
				null, null,null, UserSession.getCurrent().getLoggedLocId());
		if (CollectionUtils.isNotEmpty(result)) {
		Iterator<ProperySearchDto> itr = result.iterator();
		while(itr.hasNext()) {
			ProperySearchDto search = itr.next();
			if (StringUtils.isNotEmpty(search.getProertyNo())) {
			BillPaymentDetailDto billPayDto = propertyBillPaymentService.getBillPaymentDetail(null, search.getProertyNo(),
					org.getOrgid(),	UserSession.getCurrent().getEmployee().getEmpId(), null,
					getBillMethod(search.getProertyNo()), search.getFlatNo());
					
			if(billPayDto != null && billPayDto.getTotalPayableAmt() > 0) {
				search.setOutstandingAmt(String.valueOf(billPayDto.getTotalPayableAmt()));
			}else {
				search.setOutstandingAmt("0.00");
			}
			properySearchDtoList.add(search);
		 }
		 }
		}
		model.setSearchDtoResult(properySearchDtoList);
		return customResult("PropertyDuesCheckTemplate");
	}

	@RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
			HttpServletRequest httpServletRequest) {
		List<ProperySearchDto> result = null;
		int count = 0;
		PropertyDuesCheckModel model = this.getModel();
		ProperySearchDto dto = model.getSearchDto();
		Organisation org = UserSession.getCurrent().getOrganisation();
		final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
		final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);
		if(MainetConstants.FlagY.equals(model.getShowForm())) {
			if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty())
					&& (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
					&& (dto.getOccupierName() == null || dto.getOccupierName().isEmpty())
					&& (dto.getAddress() == null || dto.getAddress().isEmpty())
					&& (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
					&& (dto.getAssWard2() == null || dto.getAssWard2() <= 0)) {
				model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
			}
			
			if (!model.hasValidationErrors()) {
				result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(),
						getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest),
						null, UserSession.getCurrent().getLoggedLocId());
				if (result != null && !result.isEmpty()) {
					count = viewPropertyDetailsService.getTotalSearchCountForAll(model.getSearchDto(),
							getModel().createPagingDTO(httpServletRequest),
							getModel().createGridSearchDTO(httpServletRequest), null);
				}
				
				Iterator<ProperySearchDto> itr = result.iterator();
				while(itr.hasNext()) {
					ProperySearchDto search = itr.next();
					BillPaymentDetailDto billPayDto = propertyBillPaymentService.getBillPaymentDetail(null, search.getProertyNo(),
							org.getOrgid(),	UserSession.getCurrent().getEmployee().getEmpId(), null,
							getBillMethod(search.getProertyNo()), search.getFlatNo());
					if(billPayDto != null && billPayDto.getTotalPayableAmt() > 0) {
						search.setOutstandingAmt(String.valueOf(billPayDto.getTotalPayableAmt()));
					}else {
						search.setOutstandingAmt("0.00");
					}
				}
			}
		}
		return this.getModel().paginate(httpServletRequest, page, rows, count, result);
	}
	
	@RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView searchPropertyBillDetails(@RequestParam String rowId,final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        PropertyDuesCheckModel model = this.getModel();
        ProperySearchDto dto = model.getSearchDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        BillPaymentDetailDto billPayDto = null;
        String flatNo=null;       
        if(rowId.contains(MainetConstants.operator.UNDER_SCORE)) {
        	String[] splitStr = rowId.split(MainetConstants.operator.UNDER_SCORE);
        	rowId=splitStr[0];
        	if (splitStr != null && splitStr.length>1)
        	flatNo=splitStr[1];        	
        }
        model.getSearchDto().setProertyNo(rowId);
        model.getSearchDto().setFlatNo(flatNo);
        if(model.getSearchDto().getFlatNo() != null) {
        	model.setShowFlag(MainetConstants.FlagY);
        }
        model.getSearchDto().setOrgId(org.getOrgid());
			billPayDto = propertyBillPaymentService.getBillPaymentDetail(null, dto.getProertyNo(),
					UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee().getEmpId(), null,
					getBillMethod(dto.getProertyNo()), flatNo);
			AtomicDouble rebateAmount = new AtomicDouble(0);
			if(billPayDto != null) {
				if(billPayDto.getTotalCurrentAmt() != null)
				model.setCurrentBalAmt(billPayDto.getTotalCurrentAmt().doubleValue());
				if(billPayDto.getTotalArrearAmt() != null)
				model.setTotalArrearsAmt(billPayDto.getTotalArrearAmt().doubleValue());
				if(billPayDto.getTotalPenalty() != null)
				model.setTotalPenalty(billPayDto.getTotalPenalty().doubleValue());
				if(billPayDto.getTotalRebate() != null)
				rebateAmount.set(billPayDto.getTotalRebate().doubleValue());
				model.setTotalPaybale(billPayDto.getTotalPayableAmt());
				if(StringUtils.isNotBlank(flatNo)) {
					BillDisplayDto advanceDto =	asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(rowId, org.getOrgid(), flatNo);
					if(advanceDto != null && advanceDto.getTotalTaxAmt() != null) {
						model.setAdvanceAmount(advanceDto.getTotalTaxAmt().doubleValue());
					}
				}else {
					model.setAdvanceAmount(asExecssAmtService.getAdvanceAmount(rowId, org.getOrgid()));
				}
				if(model.getAdvanceAmount() > 0 && billPayDto.getTotalPayableAmt() > 0) {
					model.setTotalPaybale(billPayDto.getTotalPayableAmt() - model.getAdvanceAmount());
				}
				Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
				List<AdjustmentMasterDTO> adjustmentMasterDTOList = adjustmentEntryService.fetchHistory(rowId, deptId);
				if(CollectionUtils.isNotEmpty(adjustmentMasterDTOList)) {
					AdjustmentMasterDTO adjustmentMasterDTO = adjustmentMasterDTOList.get(adjustmentMasterDTOList.size() - 1);
					if(StringUtils.isNotBlank(adjustmentMasterDTO.getAdjType())) {
						model.setPositiveAajustment(adjustmentMasterDTO.getAdjType());
					}
					
					if(adjustmentMasterDTO != null && CollectionUtils.isNotEmpty(adjustmentMasterDTO.getAdjDetailDto())) {
						model.setAdjustmentAmount(adjustmentMasterDTO.getAdjDetailDto().stream().map(AdjustmentDetailDTO::getAdjAmount)
								.mapToDouble(e->e).sum());
					}
				}
				else {
					model.setPositiveAajustment(MainetConstants.Y_FLAG);
				}
				List<TbServiceReceiptMasEntity> rebateList = receiptRepository
						.getRebateByAdditionalRefNo(org.getOrgid(), rowId);
				if(CollectionUtils.isNotEmpty(rebateList)) {
					rebateList.forEach(rebate ->{
						rebateAmount.addAndGet(rebate.getRmAmount().doubleValue());
					});
				}
				model.setRebateAmount(rebateAmount.doubleValue());
		}
        this.getModel().setShowForm(MainetConstants.FlagY);
        return new ModelAndView("PropertyDuesCheckDetails", MainetConstants.FORM_NAME,model);
        
    }
	
	@ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
    	this.getModel().bind(request);
    	PropertyDuesCheckModel model = this.getModel();
    	List<String> flatNoList = null;
    	String billingMethod = null;
    	Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	LookUp billingMethodLookUp  = null;
    	try {
    		 billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation());
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	if(billingMethodLookUp != null) {
    		billingMethod = billingMethodLookUp.getLookUpCode();
    	}
    	this.getModel().setBillingMethod(billingMethod);
    	if(StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
    		flatNoList = new ArrayList<String>();
    		flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    	}
    	model.setFlatNoList(flatNoList);
    	return flatNoList;
    }
	
	private String getBillMethod(String rowId) {
		String billingMethod = null;
		LookUp billMethod = null;
		 Organisation org = UserSession.getCurrent().getOrganisation();
		try {
			billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV", org);
		} catch (Exception e) {
		}
		if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
				&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
			Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(rowId, org.getOrgid());
            if (billingMethodId != null ) {
			LookUp billingMethodLookUp = CommonMasterUtility
					.getNonHierarchicalLookUpObject(billingMethodId, org);
			if (billingMethodLookUp != null) {
				billingMethod = billingMethodLookUp.getLookUpCode();
			}
            }
		}
		return billingMethod;
	}
}
