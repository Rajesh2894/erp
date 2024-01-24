package com.abm.mainet.additional.ui.controller;

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

import com.abm.mainet.additional.dto.EChallanMasterDto;
import com.abm.mainet.additional.service.EChallanEntryService;
import com.abm.mainet.additional.ui.model.EChallanEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.domain.CFCAttachment;

/**
 * @author cherupelli.srikanth
 * @since 13 Jan 2023
 */
@Controller
@RequestMapping("/SeizedItemChallanEntry.html")
public class SeizedItemChallanEntryController extends AbstractFormController<EChallanEntryModel>{



	@Autowired
	private EChallanEntryService challanService;
	
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
		ModelAndView mv = null;
		this.getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<EChallanMasterDto> searchList=challanService.searchRaidDetailsList(raidNo, offenderName, challanFromDate, challanToDate, offenderMobNo, orgid);
		
		this.getModel().setChallanMasterDtoList(searchList);
				
		mv = new ModelAndView("EChallanSeizedItem", MainetConstants.FORM_NAME, this.getModel());
		return mv;
	}
	
	@RequestMapping(params = "ViewForm", method = {RequestMethod.POST })
	public ModelAndView ViewForm(@RequestParam("challanId") String challanId, 
								 @RequestParam("saveMode") String saveMode, HttpServletRequest request){
		
		this.getModel().bind(request);
		this.getModel().setSaveMode(saveMode);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		/*
		 * ServiceMaster serviceMas =
		 * seviceMasterService.getServiceByShortName(MainetConstants.EncroachmentChallan
		 * .CHALLAN_SHORT_CODE,orgId); Long deptId =
		 * serviceMas.getTbDepartment().getDpDeptid();
		 */
		
		EChallanMasterDto masterDto = challanService
				.getEChallanMasterByOrgidAndChallanId(orgId, Long.parseLong(challanId));
		if (masterDto != null) {
			 this.getModel().setChallanMasterDto(masterDto);
		}
		
		// fetch uploaded document		
		List<DocumentDetailsVO> checklist = new ArrayList<>();
		
		checklist = challanService
				.getDocumentUploadedByRefNoAndDeptId(masterDto.getRaidNo(), orgId);
		 
		this.getModel().setDocumentList(checklist);
		 	
		return new ModelAndView("RaidDetailsForm", MainetConstants.FORM_NAME,
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
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("No payment done"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}
		return mv;
	}
	
	@RequestMapping(params = "printAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printAcknowledgement(HttpServletRequest request,
    		 @RequestParam(required = false) String offenderName) {
        bindModel(request);
        final EChallanEntryModel model = this.getModel();
        ModelAndView mv = null;
        
        // runtime print acknowledge or certificate
        String viewName = "printChallanAcknowledgement";
           
	       mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        return mv;
	}

}
