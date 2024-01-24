package com.abm.mainet.common.master.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.ui.model.ReceiptFormModel;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ReceiptDetailService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;



@Controller
@RequestMapping("/ReceiptForm.html")
public class ReceiptFormController extends AbstractFormController<ReceiptFormModel>{

	private static final Logger LOGGER = Logger.getLogger(ReceiptFormController.class);
    @Autowired
	TbDepartmentService departmentService;
    
    @Autowired
    IReceiptEntryService recService;
    
    
    @Autowired
    private TbCfcApplicationMstService tbCfcservice;
    
    @Autowired
    private  IEmployeeService employeeServcie;
    
    @Autowired
    private ReceiptDetailService receiptDetailService;
    
    @Autowired
    private DepartmentService departmentservice;
    
    @Autowired
	private IDuplicateReceiptService duplicateReceiptService;
    
   
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request){
		sessionCleanup(request);
		this.getModel().setDepartmentList(departmentService.findAll());
		this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
		return index();
	}
	
	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView searchData(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		    this.getModel().bind(httpServletRequest);
		    ReceiptFormModel model = this.getModel();		
		    Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
		    this.getModel().getReceiptMasBean().setOrgId(orgId);
		    this.getModel().getReceiptMasBean().setLangId(UserSession.getCurrent().getLanguageId());
		    List<TbServiceReceiptMasBean> tbServiceReceiptMasBean = new ArrayList<>();
		     String rmdate = Utility.dateToString(this.getModel().getReceiptMasBean().getRmDate(), MainetConstants.DATE_FORMATS);
		    if (model.validateInputs()) {
		     this.getModel().getReceiptMasBean().setRmDatetemp(rmdate);
		     tbServiceReceiptMasBean = receiptDetailService.findReceiptDet(this.getModel().getReceiptMasBean());
		     if (CollectionUtils.isNotEmpty(tbServiceReceiptMasBean)) {
		     this.getModel().setReceiptMasBeanList(tbServiceReceiptMasBean);
		     }else {
					model.addValidationError(ApplicationSession.getInstance().getMessage("receipt.noRecords"));
		     }
		    }
		ModelAndView mv = null;
		mv = new ModelAndView("ReceiptFormValidn", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	

	
	@ResponseBody
	@RequestMapping(params = "viewReceipt", method = RequestMethod.POST)
	public ModelAndView viewReceiptDetails(final HttpServletRequest request, @RequestParam("rmRcptid") final Long rmRcptid) {
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		Map<Long, ChallanReportDTO> taxdto = new HashMap<>();
		TbServiceReceiptMasBean  serviceReceiptMasBean = receiptDetailService.findReceiptById(rmRcptid, orgId,langId);	
		if(serviceReceiptMasBean != null)
		  this.getModel().setReceiptMasBean(serviceReceiptMasBean);
		return new ModelAndView("ViewReceiptForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "printReceipt")
	public  ModelAndView printReceiptData(final HttpServletRequest request, @RequestParam("rmRcptid") final Long rmRcptid) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		try {
			printDTO = receiptDetailService.setValuesAndPrintReport(rmRcptid, orgId, langId);
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation());
			
			CFCSchedulingCounterDet counterDet = new CFCSchedulingCounterDet();
			String departmentShortCode = departmentservice.getDeptCode(printDTO.getDeptId());
			printDTO.setDeptShortCode(departmentShortCode);
			if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				counterDet = tbCfcservice.getCounterDetByEmpId(printDTO.getRecptCreatedBy(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (printDTO.getRecptCreatedBy() != null) {
				Employee empDto = employeeServcie.findEmployeeById(printDTO.getRecptCreatedBy());
				if (empDto != null) {
					String empName = empDto.getEmpname().concat(" ").concat(empDto.getEmpmname()).concat(" ")
							.concat(empDto.getEmplname());
					printDTO.setUserName(empName);
				}
			}
				//D#147490
				final SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
				String cfcDate = sd.format(printDTO.getRecptCreatedDate());
				printDTO.setCfcDate(cfcDate);
				final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				final String time = localDateFormat.format(printDTO.getRecptCreatedDate());
				printDTO.setReceiptTime(time);
               //D#147476
				if (StringUtils.isNotEmpty(printDTO.getNarration()) && null != printDTO.getServiceCodeflag()  && printDTO.getServiceCodeflag().equals(MainetConstants.FlagY)) {
					printDTO.setSubject(printDTO.getNarration());
				}
			}
			//to get counter details with new format
			 String receiptNo = null;
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
					receiptNo = printDTO.getReceiptNo();
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCenterNo())) {
					if (null != counterDet)
						counterDet.setCollcntrno(printDTO.getCfcColCenterNo());
					else
						this.getModel().getCfcSchedulingCounterDet().setCollcntrno(printDTO.getCfcColCenterNo());
				}
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCounterNo())) {
					if (null != counterDet)
						counterDet.setCounterno(printDTO.getCfcColCounterNo());
					else
						this.getModel().getCfcSchedulingCounterDet().setCounterno(printDTO.getCfcColCounterNo());
				}
			}
			
			 // #148057 and #147528 for water and property payment receipt 
			if (printDTO.getServiceCode() != null && (printDTO.getServiceCode().equals("PBP") || printDTO.getServiceCode().equals("BPW"))) {
				printDTO = duplicateReceiptService.getRevenueReceiptDetails(rmRcptid, Utility.getReceiptIdFromCustomRcptNO(printDTO.getReceiptNo()),printDTO.getReferenceNo(),UserSession.getCurrent().getOrganisation().getOrgid(),UserSession.getCurrent().getLanguageId());
				Boolean displaySeq = true;
				if (printDTO != null) {
					for (ChallanReportDTO dto : printDTO.getPaymentList()) {
						if (dto.getDisplaySeq() == null) {
							displaySeq = false;
							break;
						}
					}
					if (displaySeq)
						printDTO.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDisplaySeq));
				}
				if (printDTO != null && printDTO.getCfcColCounterNo() != null)
					printDTO.setCfcCounterNo(printDTO.getCfcColCounterNo());
				if (printDTO != null && printDTO.getCfcColCenterNo() != null)
					printDTO.setCfcCenter(printDTO.getCfcColCenterNo());
                //to get counter details with new format
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)) {
					if (receiptNo != null && printDTO.getReceiptNo() != null)
						printDTO.setReceiptNo(receiptNo);
				}
			}
            if (null != counterDet)
			this.getModel().setCfcSchedulingCounterDet(counterDet);
			this.getModel().setReceiptDTO(printDTO);
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details:" + e);
		}
		if (printDTO.getServiceCode() != null && (printDTO.getServiceCode().equals("PBP") || printDTO.getServiceCode().equals("BPW"))) {
			return new ModelAndView("revenueReceiptPrint", MainetConstants.FORM_NAME, this.getModel());
		} else {
		return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
		}
	}
}
