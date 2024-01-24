/**
 * 
 */
package com.abm.mainet.additionalservices.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.additionalservices.service.EChallanEntryService;
import com.abm.mainet.additionalservices.ui.model.EChallanEntryModel;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/SeizedItemChallanEntry.html")
public class SeizedItemChallanEntryController extends AbstractFormController<EChallanEntryModel>{
	
	@Autowired
	private EChallanEntryService challanService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private ServiceMasterService seviceMasterService;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index( HttpServletRequest request) {
		sessionCleanup(request);
		return index();
	}
	
	@ResponseBody
    @RequestMapping(params = "searchRaidDetails", method = RequestMethod.POST)
	public ModelAndView searchRaidDetails(final HttpServletRequest request,
            @RequestParam(required = false) String raidNo,
            @RequestParam(required = false) String offenderName,
            @RequestParam(required = false) Date challanFromDate,
            @RequestParam(required = false) Date challanToDate,
            @RequestParam(required = false) String offenderMobNo) { 
		sessionCleanup(request);
		getModel().bind(request);
		ModelAndView mv = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		String challanType = MainetConstants.EncroachmentChallan.AGAINST_SEIZED_ITEMS;
		List<EChallanMasterDto> searchList=challanService.searchRaidDetailsList(raidNo, offenderName, 
				challanFromDate, challanToDate, offenderMobNo,challanType, orgid);
		
		if(searchList.isEmpty()) {
			mv = new ModelAndView("EChallanSeizedItem", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("EChallan.noRecordFound"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}else {			
			this.getModel().setChallanMasterDtoList(searchList);			
			mv = new ModelAndView("EChallanSeizedItem", MainetConstants.FORM_NAME, this.getModel());
		}	
		return mv;
	}
	
	@RequestMapping(params = "ViewForm", method = {RequestMethod.POST })
	public ModelAndView ViewForm(@RequestParam("challanId") String challanId, 
								 @RequestParam("saveMode") String saveMode, HttpServletRequest request){
		this.getModel().setSaveMode(saveMode);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE,orgId);
		Long deptId = serviceMas.getTbDepartment().getDpDeptid();
		
		EChallanMasterDto masterDto = challanService
				.getEChallanMasterByOrgidAndChallanId(orgId, Long.parseLong(challanId));
		if (masterDto != null) {
			 this.getModel().setChallanMasterDto(masterDto);
		}
		
		// fetch uploaded document		
		List<CFCAttachment> checklist = new ArrayList<>();
		checklist = iChecklistVerificationService.getDocumentUploadedByRefNoAndDeptId(this.getModel().getChallanMasterDto().getRaidNo(),
				deptId, orgId);
		this.getModel().setDocumentList(checklist);
		 	
		return new ModelAndView(MainetConstants.EncroachmentChallan.RAID_DETAILS_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("challanId") Long challanId,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		ChallanReceiptPrintDTO receiptDto = challanService.getDuplicateReceiptDetail(challanId, orgId);
		getModel().setReceiptDTO(receiptDto);
		if (receiptDto != null) {
			mv = new ModelAndView("ChallanAtULBReceiptPrint", MainetConstants.FORM_NAME, getModel());
		}else {
			
			mv = new ModelAndView("SeizedItemChallanEntryValidn", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("EChallan.paymentNotDone"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}
		return mv;
	}
	
	@RequestMapping(params = "printAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printAcknowledgement(HttpServletRequest request,
    		 @RequestParam(required = false) String offenderName) {
        bindModel(request);
        ModelAndView mv = null;
        EChallanEntryModel model = this.getModel();
   
        // runtime print acknowledge or certificate
        String viewName = "printChallanAcknowledgement";
           
	    mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, model);
        return mv;
	}

}
