package com.abm.mainet.additionalservices.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.BndRateMaster;
import com.abm.mainet.additionalservices.service.CFCNursingHomeService;
import com.abm.mainet.additionalservices.ui.model.DuplicatePaymentReceiptModel;
import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ReceiptDetailService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/DuplicatePaymentReceipt.html")
public class DuplicatePaymentReceiptController extends AbstractFormController<DuplicatePaymentReceiptModel> {

	private static final Logger LOGGER = Logger.getLogger(DuplicatePaymentReceiptController.class);
	@Autowired
	TbDepartmentService departmentService;

	@Autowired
	private ReceiptDetailService receiptDetailService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private CFCNursingHomeService cfcNursingHomeService;

	@Autowired
	private TbCfcApplicationMstService tbCfcservice;

	@Autowired
	private IEmployeeService employeeServcie;

	@Autowired
	private IReceiptEntryService receiptEntryService;

	@Autowired
	private IDuplicateReceiptService duplicateReceiptService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		//#150172
		this.getModel().setDepartmentList(departmentService.findActualDept(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setLangId(UserSession.getCurrent().getLanguageId());
		//Defect #145245
        this.getModel().setFinYearList(Utility.getNoOfFinancialYearIncludingCurrent(20));
		LOGGER.info("Duplicate Receipt print index method()");
		return new ModelAndView("DuplicatePaymentReceipt", MainetConstants.FORM_NAME, this.getModel());
	}

	

	@RequestMapping(method = RequestMethod.POST, params = "getCharges")
	public ModelAndView getCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		DuplicatePaymentReceiptModel bndmodel = getModel();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("DRP",
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (sm.getSmFeesSchedule().longValue() != 0) {
			if (sm.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
				this.getModel().setPaymentCheck(MainetConstants.FlagY);
			}
		}
		if (sm.getSmFeesSchedule().longValue() == 0) {
			this.getModel().setPaymentCheck(MainetConstants.FlagN);
		}
		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName("BNDRateMaster");
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(sm.getSmShortdesc());
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			rateMasterModel.setOrganisationType(CommonMasterUtility
					.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
							UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
					.getDescLangFirst());
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);

			WSResponseDTO responsefortax = null;
			try {
				responsefortax = cfcNursingHomeService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				this.getModel().setApplicationCharge(chargesAmount);
			}
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
					orgIds);
			this.getModel().setServiceMaster(serviceMas);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
				if (true) {
					final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate = populateChargeModel(bndmodel, masterrate, sm.getSmShortdesc());
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName("BNDRateMaster");
					certificateCharges = cfcNursingHomeService.getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							break;
						}
						if (sm.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg))
							chargesAmount = String.valueOf(charges.get("slab4"));
						else
							chargesAmount = String.valueOf(charges.get("flatRate"));
					} else {
						chargesAmount = MainetConstants.FlagN;
						bndmodel.setChargesAmount(chargesAmount);
					}
				} else {
					chargesAmount = "0.0";
				}
				if (chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
					chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
				}
				if (certificateCharges != null) {
					chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxMasterByTaxCode(orgIds,
							serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode")).getTaxId());
				}
                //#148693
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("cfc.dup.receipt.fees"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("cfc.dup.receipt.fees"));

				chargesInfo.add(chargeDetailDTO);
				bndmodel.setChargesInfo(chargesInfo);
				if (chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
					bndmodel.setChargesAmount(chargesAmount);
					this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
				}
			}
		}
		return new ModelAndView("DuplicatePaymentReceiptValidn", MainetConstants.FORM_NAME, this.getModel());

	}

	private BndRateMaster populateChargeModel(DuplicatePaymentReceiptModel model, BndRateMaster bndRateMaster,
			String smShortCode) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(smShortCode);
		bndRateMaster.setDeptCode("CFC");
		return bndRateMaster;
	}

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetail", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@ResponseBody
	@RequestMapping(params = "printReceipt")
	public ModelAndView printReceiptData(final HttpServletRequest request,
			@RequestParam("rmRcptid") final Long rmRcptid) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		try {
			printDTO = receiptDetailService.setValuesAndPrintReport(rmRcptid, orgId, langId);
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation());

			CFCSchedulingCounterDet counterDet = new CFCSchedulingCounterDet();
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
				if (StringUtils.isNotEmpty(printDTO.getNarration()) && null != printDTO.getServiceCodeflag() && printDTO.getServiceCodeflag().equals(MainetConstants.FlagY)) {
					printDTO.setSubject(printDTO.getNarration());
				}
			}
            //to get counter details with new format
			 String receiptNo = null;
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				printDTO.setEnvFlag(MainetConstants.FlagD);
				receiptNo = printDTO.getReceiptNo();
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCounterNo())) {
					if (null != counterDet)
						counterDet.setCounterno(printDTO.getCfcColCounterNo());
					else
						this.getModel().getCfcSchedulingCounterDet().setCounterno(printDTO.getCfcColCounterNo());
				}
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCenterNo())) {
					if (null != counterDet)
						counterDet.setCollcntrno(printDTO.getCfcColCenterNo());
					else
						this.getModel().getCfcSchedulingCounterDet().setCollcntrno(printDTO.getCfcColCenterNo());
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
				//#148057- to get counter no. and center no. details with count
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCounterNo()))
					printDTO.setCfcCounterNo(printDTO.getCfcColCounterNo());
				if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCenterNo()))
					printDTO.setCfcCenter(printDTO.getCfcColCenterNo());
                 //to get counter details with new format
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)) {
					if (receiptNo != null && printDTO.getReceiptNo() != null)
						printDTO.setReceiptNo(receiptNo);
					    printDTO.setEnvFlag(MainetConstants.FlagD);
				}
			}
			if (null != counterDet)
			this.getModel().setCfcSchedulingCounterDet(counterDet);
			this.getModel().setReceiptDTO(printDTO);
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details in printReceipt():" + e);
		}
		if (printDTO.getServiceCode() != null && printDTO.getDeptShortCode() != null && (MainetConstants.Property.PROP_DEPT_SHORT_CODE.equals(printDTO.getDeptShortCode()) || printDTO.getServiceCode().equals("PBP") || printDTO.getServiceCode().equals("BPW"))) {
			return new ModelAndView("revenueReceiptPrint", MainetConstants.FORM_NAME, this.getModel());
		} else {
			return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
					this.getModel());
		}
	}

	@ResponseBody
	@RequestMapping(params = "printNewReceipt")
	public ModelAndView printNewReceiptData(final HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		try {
			ChallanReceiptPrintDTO printDTO = receiptDetailService
					.setValuesAndPrintReport(this.getModel().getReceiptDTO().getReceiptId(), orgId, langId);
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation());
			CFCSchedulingCounterDet counterDet = null;
			if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null && counterDet.getEmpId() != null) {
					Employee empDto = employeeServcie.findEmployeeById(counterDet.getEmpId());
					if (empDto != null) {
						String empName = empDto.getEmpname().concat(" ").concat(empDto.getEmpmname()).concat(" ")
								.concat(empDto.getEmplname());
						printDTO.setUserName(empName);
					}
				}
				//#148057- to get counter no. and center no. details with count
					if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCounterNo()))
						counterDet.setCounterno(printDTO.getCfcColCounterNo());
					if (printDTO != null && StringUtils.isNotEmpty(printDTO.getCfcColCenterNo()))
						counterDet.setCollcntrno(printDTO.getCfcColCenterNo());
				
			}
			this.getModel().setCfcSchedulingCounterDet(counterDet);
			this.getModel().setReceiptDTO(printDTO);
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details in printNewReceiptData:" + e);
		}
		return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		DuplicatePaymentReceiptModel model = this.getModel();
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
			} else
				return defaultMyResult();
		}
		return defaultMyResult();
	}

	@ResponseBody
	@RequestMapping(params = "checkReceiptExists", method = RequestMethod.POST)
	public Boolean checkReceiptExists(@RequestParam("rmRcptno") String rmRcptno, @RequestParam("deptId") Long deptId,@RequestParam("loiNo") String loiNo ,@RequestParam("rmAmount")  String rmAmount,@RequestParam("appNo")  Long appNo ,@RequestParam("refNo") Long refNo,@RequestParam("rmReceivedfrom") String rmReceivedfrom,@RequestParam("finYearId") String finYearId) {
		//Defect #145245
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date[] date =null;
		String formAndToDt=null;
		if(StringUtils.isNotBlank(finYearId)) {
			String[] year=	finYearId.split("-");
			if(year!=null&&year.length>0) {
				formAndToDt=("'"+year[0]+"-04-01"+"'"+"  and  "+"'"+year[1]+"-03-31"+"'");
			}
		}
		TbServiceReceiptMasEntity entity = receiptEntryService
				.getReceiptDetailByIds(Utility.getReceiptIdFromCustomRcptNO(rmRcptno), orgId, deptId,loiNo,null,rmAmount,appNo,refNo,rmReceivedfrom,formAndToDt);
		if (entity.getRmRcptid() > 0) {
			this.getModel().setReceiptFlag(MainetConstants.FlagY);
			this.getModel().setServiceId(entity.getSmServiceId());
			this.getModel().setReceiptMasEntity(entity);
			return true;
		}
		LOGGER.info("checkReceiptExists method called");
		return false;
	}

	@ResponseBody
	@RequestMapping(params = "getReceiptId", method = RequestMethod.POST)
	public Long getReceiptId(final HttpServletRequest request, @RequestParam("rmRcptno") String rmRcptno,
			@RequestParam("deptId") Long deptId,@RequestParam("loiNo") String loiNo ,@RequestParam("rmAmount")  String rmAmount,@RequestParam("appNo")  Long appNo ,
			@RequestParam("refNo") Long refNo,@RequestParam("rmReceivedfrom") String rmReceivedfrom,@RequestParam("finYearId") String finYearId) {
		Long Rcptno = null;
       //Defect #145245
		String formAndToDt=null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(StringUtils.isNotBlank(finYearId)) {
		String[] year=	finYearId.split("-");
		if(year!=null&&year.length>0) {
			formAndToDt=("'"+year[0]+"-04-01"+"'"+"  and  "+"'"+year[1]+"-03-31"+"'");
		}
		}
		TbServiceReceiptMasEntity entity = receiptEntryService
				.getReceiptDetailByIds(Utility.getReceiptIdFromCustomRcptNO(rmRcptno), orgId, deptId,loiNo,null,rmAmount,appNo,refNo,rmReceivedfrom,formAndToDt);
		if (entity != null)
			Rcptno = entity.getRmRcptid();
		LOGGER.info("getReceiptId() method called");
		return Rcptno;
	}
	
	
	@RequestMapping(params = "printCFCAckRcpt", method = { RequestMethod.POST })
	public ModelAndView printAcknowledgement(HttpServletRequest request) {
		DuplicatePaymentReceiptModel model = this.getModel();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.DUPULICATE_RECEIPT_PRINTING, UserSession.getCurrent().getOrganisation().getOrgid());

		if (model.getCfcApplicationMst() != null) {
		String applicantName = model.getCfcApplicationMst().getApmFname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmMname() == null ? MainetConstants.BLANK
				: model.getCfcApplicationMst().getApmMname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmLname();
		this.getModel().getAckDto().setApplicationId(model.getCfcApplicationMst().getApmApplicationId());
		this.getModel().getAckDto().setApplicantName(applicantName);
		this.getModel().getAckDto().setServiceName(model.getServiceMaster().getSmServiceName());
		model.getAckDto().setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
		model.getAckDto().setAppDate(new Date());
		model.getAckDto().setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
		model.getAckDto().setDueDate(Utility.getAddedDateBy2(model.getAckDto().getAppDate(), serviceMas.getSmServiceDuration().intValue()));
		}
		return new ModelAndView("CommonAcknowledgement",MainetConstants.FORM_NAME, this.getModel());

	}
}
