package com.abm.mainet.additionalservices.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.BndRateMaster;
import com.abm.mainet.additionalservices.dto.NOCforOtherGovtDeptDto;
import com.abm.mainet.additionalservices.service.NOCForOtherGovtDeptService;
import com.abm.mainet.additionalservices.ui.model.NOCForOtherGovtDeptModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("NOCForOtherGovtDept.html")
public class NOCForOtherGovtDeptController extends AbstractFormController<NOCForOtherGovtDeptModel> {

	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMstService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private TbCfcApplicationAddressJpaRepository cfcAddressRepo;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private NOCForOtherGovtDeptService nocForOtherGovtDeptService;

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		this.getModel().setTbServicesMsts(serviceMstList);

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel()
				.setRefIds(tbCFCApplicationMstService.getApplicationIdsByServiceId(serviceMaster.getSmServiceId()));

		//this.getModel().setRefIds(tbCFCApplicationMstService.getRefIdsByServiceId(serviceMaster.getSmServiceId()));
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		return index();

	}

	@ResponseBody
	@RequestMapping(params = "summary", method = RequestMethod.POST)
	public ModelAndView resetSummaryForm(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		this.getModel().setTbServicesMsts(serviceMstList);

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel()
				.setRefIds(tbCFCApplicationMstService.getApplicationIdsByServiceId(serviceMaster.getSmServiceId()));
		///this.getModel().setRefIds(tbCFCApplicationMstService.getRefIdsByServiceId(serviceMaster.getSmServiceId()));
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		
		return new ModelAndView("NOCForOtherGovtDept/summary", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest,Model model) {
		populateModel();

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());

		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);

		return new ModelAndView("NOCForOtherGovtDept/create", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView searchDate(final HttpServletRequest httpServletRequest, @RequestParam Long serviceId,
			@RequestParam String refId) {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		this.getModel().setFlag(MainetConstants.FlagN);
		// this.getModel().setAppIds(cfcNursingHomeService.findAllApplicationNo());

		this.getModel().setHomeSummaryDtos(
				nocForOtherGovtDeptService.getAllByServiceIdAndAppId(serviceId, refId, organisation.getOrgid()));
		if (this.getModel().getHomeSummaryDtos().isEmpty())
			this.getModel().setFlag(MainetConstants.FlagE);
		this.getModel().setRefId(refId);
		this.getModel().setServiceId(serviceId);
		return new ModelAndView("NOCForOtherGovtDept/search", MainetConstants.FORM_NAME, this.getModel());

	}
	    @ResponseBody
		@RequestMapping(params = "applicantForm", method = RequestMethod.POST)
		public ModelAndView backToApplicantForm(final HttpServletRequest httpServletRequest,Model model) {
			bindModel(httpServletRequest);
			
			Organisation organisation = UserSession.getCurrent().getOrganisation();
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
					PrefixConstants.STATUS_ACTIVE_PREFIX);
			
			final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
			this.getModel().setTbServicesMsts(serviceMstList);
			
			this.getModel().getCfcApplicationMst().setSmServiceId(tbServicesMstService
					.findShortCodeByOrgId(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept, organisation.getOrgid())
					.getSmServiceId());
			ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
					UserSession.getCurrent().getOrganisation().getOrgid());
			LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());

			if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
			} else {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
			}
			final int langId = UserSession.getCurrent().getLanguageId();
			model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
			return new ModelAndView("NOCForOtherGovtDept/create", MainetConstants.FORM_NAME, this.getModel());

		}
	private void populateModel() {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		this.getModel().setTbServicesMsts(serviceMstList);
        //Defect #145348
		this.getModel().setCfcApplicationMst(new TbCfcApplicationMst());
		this.getModel().getCfcApplicationMst().setSmServiceId(tbServicesMstService
				.findShortCodeByOrgId(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept, organisation.getOrgid())
				.getSmServiceId());
		
	}

	@ResponseBody
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public ModelAndView viewSequenceMaster(final HttpServletRequest request) {
		sessionCleanup(request);

		populateModel();

		return new ModelAndView("NOCForOtherGovtDept/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "proceedToCheckList", method = RequestMethod.POST)
	public ModelAndView proceedToCheckList(final HttpServletRequest request) {
		this.bindModel(request);
		//this.getModel().getCheckListFromBrms();

		if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getCheckListFromBrms(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept);
		}

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
				UserSession.getCurrent().getOrganisation().getOrgid());

		/*
		 * if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
		 * 
		 * try { String charges = getCharges(this.getModel());
		 * if(!charges.equals(MainetConstants.FlagN)) this.getModel().getOfflineDTO()
		 * .setAmountToShow(Double.valueOf(Double.valueOf(getCharges(this.getModel()))))
		 * ; } catch (FrameworkException e) { ModelAndView mv = defaultResult();
		 * mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
		 * getModel().getBindingResult());
		 * this.getModel().addValidationError(ApplicationSession.getInstance().
		 * getMessage("renewal.brms.msg"));
		 * 
		 * return mv; }
		 * 
		 * }
		 */

		/// this.getModel().setPaymentCheck(MainetConstants.FlagY);
		this.getModel().setServiceMaster(serviceMaster);
		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		this.getModel().setDownloadMode(MainetConstants.FlagM);
		this.getModel().setHideshowAddBtn(MainetConstants.FlagY);
		this.getModel().setHideshowDeleteBtn(MainetConstants.FlagY);
		this.getModel().setTemporaryDateHide(MainetConstants.FlagD);
		this.getModel().getdataOfUploadedImage();

		return new ModelAndView("NOCForOtherGovtDept/checklist", MainetConstants.FORM_NAME, this.getModel());
		// return defaultResult();
	}

	public String getCharges(NOCForOtherGovtDeptModel model) {

		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		NOCForOtherGovtDeptModel bndmodel = model;
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
			rateMasterModel.setServiceCode(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept);
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
			// WSResponseDTO responsefortax =
			// birthCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = nocForOtherGovtDeptService.getApplicableTaxes(taxRequestDto);
				/* birthCertificateService.getApplicableTaxes(taxRequestDto); */
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargesAmount;
			}
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
					orgIds);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
				if (true/* !responsefortax.isFree() */) {
					final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate = populateChargeModel(bndmodel, masterrate);
						// masterrate.setSlab4(bndmodel.getCfcHospitalInfoDTO().getTotalBedCount());
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName("BNDRateMaster");
					certificateCharges = nocForOtherGovtDeptService.getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							break;
						}
						chargesAmount = String.valueOf(charges.get("slab4"));
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
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthCertificateDTO.serName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthCertificateDTO.regserName"));
				chargesInfo.add(chargeDetailDTO);
				bndmodel.setChargesInfo(chargesInfo);
				if (chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
					bndmodel.setChargesAmount(chargesAmount);
					this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
				}
			}
		}
		return chargesAmount;

	}

	private BndRateMaster populateChargeModel(NOCForOtherGovtDeptModel model, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept);
		bndRateMaster.setDeptCode("CFC");
		return bndRateMaster;
	}

	@ResponseBody
	@RequestMapping(params = "generateChallanAndPayement", method = RequestMethod.POST)
	public Map<String, Object> generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		NOCForOtherGovtDeptModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		List<DocumentDetailsVO> ownerDocs = model.getNoCforOtherGovtDeptDto().getAttachments();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (ownerDocs != null) {
			ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
		}

		model.getNoCforOtherGovtDeptDto().setDocumentList(docs);
		model.getNoCforOtherGovtDeptDto().setAttachments(ownerDocs);
		fileUpload.validateUpload(model.getBindingResult());
		String msg=new String();
		try {
			if (model.validateInputs()) {
				if (model.saveForm()) {
					object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());

				} else
				{
					object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
				}
			}else{
				object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
			}
		} catch (FrameworkException e) {

		}
		return object;
		/*
		 * ModelAndView mv = new ModelAndView("NOCForOtherGovtDept/checklist",
		 * MainetConstants.FORM_NAME, this.getModel());
		 * mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
		 * getModel().getBindingResult()); return mv;
		 */

	}

	@ResponseBody
	@RequestMapping(params = "viewNOCFormData", method = RequestMethod.POST)
	public ModelAndView viewNOCFormData(final HttpServletRequest request, @RequestParam String appId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		populateModel();
		Long applicationId = Long.parseLong(appId);
		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMstService.findById(applicationId);

		this.getModel().setCfcApplicationMst(cfcApplicationMst);
		CFCApplicationAddressEntity cfcApplicationAddressEntity = cfcAddressRepo.findOne(applicationId);
		this.getModel().setCfcApplicationAddressEntity(cfcApplicationAddressEntity);

		NOCforOtherGovtDeptDto cforOtherGovtDeptDto = new NOCforOtherGovtDeptDto();
		cforOtherGovtDeptDto.setCfcWard1(cfcApplicationAddressEntity.getApaZoneNo());
		cforOtherGovtDeptDto.setCfcWard2(cfcApplicationAddressEntity.getApaWardNo());
		this.getModel().setNoCforOtherGovtDeptDto(cforOtherGovtDeptDto);

		this.getModel().setDocumentList(checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setSaveMode(MainetConstants.FlagV);

		return new ModelAndView("NOCForOtherGovtDept/view", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "printNocAckRcpt", method = {
		    RequestMethod.POST })
	    public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		NOCForOtherGovtDeptModel model = this.getModel();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept, UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(this.getModel().getCheckList())) {
		    List<CFCAttachment> downloadDocs = new ArrayList<>();
		    List<CFCAttachment> preparePreviewOfFileUpload = model.preparePreviewOfFileUpload(downloadDocs,
			    this.getModel().getCheckList());

		    if (CollectionUtils.isNotEmpty(preparePreviewOfFileUpload)) {
			this.getModel().setDocumentList(preparePreviewOfFileUpload);
		    }

		}

		String applicantName = model.getCfcApplicationMst().getApmFname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmMname() == null ? MainetConstants.BLANK
			: model.getCfcApplicationMst().getApmMname() + MainetConstants.WHITE_SPACE;
		applicantName += model.getCfcApplicationMst().getApmLname();
		this.getModel().getAckDto().setApplicationId(model.getCfcApplicationAddressEntity().getApmApplicationId());
		this.getModel().getAckDto().setApplicantName(applicantName);
		this.getModel().getAckDto().setServiceName(model.getServiceMaster().getSmServiceName());
		model.getAckDto().setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
		model.getAckDto().setAppDate(new Date());
		model.getAckDto().setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
		model.getAckDto().setDueDate(Utility.getAddedDateBy2(model.getAckDto().getAppDate(), serviceMas.getSmServiceDuration().intValue()));
		return new ModelAndView(MainetConstants.COMMON.COMMON_ACKNOWLEDGEMENT,
			MainetConstants.FORM_NAME, this.getModel());

	    }

	
}
