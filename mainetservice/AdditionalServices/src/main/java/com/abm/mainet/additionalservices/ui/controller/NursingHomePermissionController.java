
package com.abm.mainet.additionalservices.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.dto.BndRateMaster;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.service.CFCNursingHomeService;
import com.abm.mainet.additionalservices.ui.model.NursingHomePermisssionModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
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
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("NursingHomePermission.html")
public class NursingHomePermissionController extends AbstractFormController<NursingHomePermisssionModel> {

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private CFCNursingHomeService cfcNursingHomeService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	private TbCfcApplicationMstService tbCFCApplicationMst;

	@Autowired
	private TbCfcApplicationAddressJpaRepository cfcAddressRepo;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {

		sessionCleanup(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		Organisation organisation = UserSession.getCurrent().getOrganisation();

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		loadWardZone(httpServletRequest);
		//#150170-Service names should be shown for related services in drop down field
		 List<TbServicesMst> serviceMstList = new ArrayList<>();
			final List<TbServicesMst> serviceMastList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
			if (CollectionUtils.isNotEmpty(serviceMastList)) {
			for (TbServicesMst tbServicesMst : serviceMastList) {
	     	   if (tbServicesMst.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg) || tbServicesMst.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center))
	     		  serviceMstList.add(tbServicesMst);
	          }
			}
	    this.getModel().setTbServicesMsts(serviceMstList);
		this.getModel().setRefIds(cfcNursingHomeService.findAllApplicationNo());
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);

		return index();

	}

	private void loadWardZone(HttpServletRequest httpServletRequest) {
		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();
		wardList = locationMasterService.findlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		this.getModel().setWardList(wardMapList);

	}

	@ResponseBody
	@RequestMapping(params = "resetSummary", method = RequestMethod.POST)
	public ModelAndView resetSummary(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);

		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid());
		this.getModel().setTbServicesMsts(serviceMstList);
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);
		this.getModel().setRefIds(cfcNursingHomeService.findAllApplicationNo());
		return new ModelAndView("NursingHomePermission/summary", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest, Model model) {
		populateModel();
		final int langId = UserSession.getCurrent().getLanguageId();
		model.addAttribute(MainetConstants.CommonMasterUi.LANGUAGE_ID, langId);	
		return new ModelAndView("NursingHomePermission/create", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "searchData", method = RequestMethod.POST)
	public ModelAndView searchDate(final HttpServletRequest httpServletRequest, @RequestParam Long serviceId,
			@RequestParam String refId) {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		this.getModel().setFlag(MainetConstants.FlagN);
		// this.getModel().setAppIds(cfcNursingHomeService.findAllApplicationNo());

		this.getModel().setHomeSummaryDtos(
				cfcNursingHomeService.getAllByServiceIdAndAppId(serviceId, refId, organisation.getOrgid()));
		if (this.getModel().getHomeSummaryDtos().isEmpty())
			this.getModel().setFlag(MainetConstants.FlagE);

		this.getModel().setRefId(refId);
		this.getModel().setServiceId(serviceId);
		return new ModelAndView("NursingHomePermission/search", MainetConstants.FORM_NAME, this.getModel());

	}

	private void populateModel() {
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);
           //Defect #145348
		final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, organisation.getOrgid())
				.stream()
				.filter(ser -> ser != null
						&& (ser.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg) || ser
								.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)))
				.collect(Collectors.toList());
		this.getModel().setTbServicesMsts(serviceMstList);
	}

	@ResponseBody
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public ModelAndView viewSequenceMaster(final HttpServletRequest request) {
		sessionCleanup(request);

		populateModel();

		return new ModelAndView("NursingHomePermission/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "hospitalInfo", method = RequestMethod.POST)
	public ModelAndView hospitalInfo(final HttpServletRequest request) {
		this.bindModel(request);
		this.getModel().setTbDepartments(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findActualDept(UserSession.getCurrent().getOrganisation().getOrgid()));

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
				this.getModel().getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());

		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());

		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
        // #129863
		if(serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			this.getModel().setServiceShortCode(serviceMaster.getSmShortdesc());
			return new ModelAndView("NursingHomePermission/sonographyForm", MainetConstants.FORM_NAME, this.getModel());
		}else
		return new ModelAndView("NursingHomePermission/hospitalInfo", MainetConstants.FORM_NAME, this.getModel());
	}
	@ResponseBody
	@RequestMapping(params = "proceedToCheckList", method = RequestMethod.POST)
	public ModelAndView proceedToCheckList(final HttpServletRequest request) {
		this.bindModel(request);
		/* this.getModel().getCheckListFromBrms(); */
	
		NursingHomePermisssionModel model = this.getModel();
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
				this.getModel().getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (model.validateInputs()) {
        if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getCheckListFromBrms(serviceMaster.getSmShortdesc());
		}

		
		/*
		 * ServiceMaster serviceMaster =
		 * serviceMasterService.getServiceByShortName("NHR",
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {

			try {
				this.getModel().setPaymentCheck(MainetConstants.FlagY);
				String charges = getCharges(this.getModel(), serviceMaster.getSmShortdesc());
				if (!charges.equals(MainetConstants.FlagN))
					this.getModel().getOfflineDTO().setAmountToShow(Double.valueOf(Double.valueOf(charges)));
			} catch (FrameworkException e) {
				ModelAndView mv = defaultResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));

				return mv;
			}

		}

		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		this.getModel().setDownloadMode(MainetConstants.FlagM);
		this.getModel().setHideshowAddBtn(MainetConstants.FlagY);
		this.getModel().setHideshowDeleteBtn(MainetConstants.FlagY);
		this.getModel().setTemporaryDateHide(MainetConstants.FlagD);
		this.getModel().getdataOfUploadedImage();
		}else {
			ModelAndView mv = null;
			if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
			mv =  new ModelAndView("NursingHomePermission/sonographyForm", MainetConstants.FORM_NAME, this.getModel());
			}else{
				mv =  new ModelAndView("NursingHomePermission/hospitalInfo", MainetConstants.FORM_NAME, this.getModel());	
			}
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		return new ModelAndView("NursingHomePermission/checklist", MainetConstants.FORM_NAME, this.getModel());
		// return defaultResult();
	}

	public String getCharges(NursingHomePermisssionModel model, String serviceCode) {

		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		NursingHomePermisssionModel bndmodel = model;
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
			rateMasterModel.setServiceCode(serviceCode);
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
				responsefortax = cfcNursingHomeService.getApplicableTaxes(taxRequestDto);
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
						masterrate = populateChargeModel(bndmodel, masterrate, serviceCode);
						if (serviceCode.equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg))
							masterrate.setSlab4(bndmodel.getCfcHospitalInfoDTO().getTotalBedCount());
						else
							masterrate.setSlab4(0);
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
						if (serviceCode.equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg))
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
				//120072   Showing the application charges info
				if(serviceMas.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Nursing_Home_Reg)) {
					chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("NHP.regestration.fees"));
					chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("NHP.regestration.fees"));
				}else {
					chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("HST.regestration.fees"));
					chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("HST.regestration.fees"));
				}
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

	private BndRateMaster populateChargeModel(NursingHomePermisssionModel model, BndRateMaster bndRateMaster,
			String smShortCode) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(smShortCode);
		bndRateMaster.setDeptCode("CFC");
		return bndRateMaster;
	}

	@ResponseBody
	@RequestMapping(params = "generateChallanAndPayement", method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
		NursingHomePermisssionModel model = this.getModel();
		//Map<String, Object> object = new LinkedHashMap<String, Object>();
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
				this.getModel().getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setServiceMaster(serviceMaster);
		List<DocumentDetailsVO> ownerDocs;
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)){
			ownerDocs = model.getcFCSonographyMastDtos().getAttachments();
		}else
		ownerDocs = model.getCfcHospitalInfoDTO().getAttachments();

		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (ownerDocs != null) {
			ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
		}
		if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)){
			model.getcFCSonographyMastDtos().setDocumentList(docs);
			model.getcFCSonographyMastDtos().setAttachments(ownerDocs);
		}else {
		model.getCfcHospitalInfoDTO().setDocumentList(docs);
		model.getCfcHospitalInfoDTO().setAttachments(ownerDocs);
		}
		fileUpload.validateUpload(model.getBindingResult());

		try {
			if (model.validateInputs()) {
				if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)){
					if (model.saveSonographyForm()) {
						return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
					}
				}			
				else if (model.saveForm()) {
					//object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());
					return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
				} else
					return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
					//object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
			}		
		} catch (FrameworkException e) {
			
		}
		
		  ModelAndView mv = new ModelAndView("NursingHomePermission/checklist",
		  MainetConstants.FORM_NAME, this.getModel());
		  mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
		  getModel().getBindingResult());
		 
		return mv;

	}
	
	
	
	
	@ResponseBody
	@RequestMapping(params = "saveApplication", method = RequestMethod.POST)
	public Map<String, Object> saveApplication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
		NursingHomePermisssionModel model = this.getModel();
		List<DocumentDetailsVO> ownerDocs = null ;
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		//Map<String, Object> object = new LinkedHashMap<String, Object>();
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(
				this.getModel().getCfcApplicationMst().getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setServiceMaster(serviceMaster);
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)){
			ownerDocs = model.getcFCSonographyMastDtos().getAttachments();
		}
		else
			ownerDocs = model.getCfcHospitalInfoDTO().getAttachments();
	

		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (ownerDocs != null) {
			ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
		}
		if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)){
			model.getcFCSonographyMastDtos().setDocumentList(docs);
			model.getcFCSonographyMastDtos().setAttachments(ownerDocs);
		}
		else {
		model.getCfcHospitalInfoDTO().setDocumentList(docs);
		model.getCfcHospitalInfoDTO().setAttachments(ownerDocs);
		}
		fileUpload.validateUpload(model.getBindingResult());

		try {
			if (model.validateInputs()) {
				if (serviceMaster.getSmShortdesc().equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
					if (model.saveSonographyForm()) {
						object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());
					}						
				}
				else if (model.saveForm()) {
					object.put(MainetConstants.SUCCESS_MSG, getModel().getSuccessMessage());

				}
				else {
					object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
				}
			}else {
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

	@RequestMapping(params = "addProgemDtl", method = RequestMethod.POST)
	public @ResponseBody String addProgemDtl(HttpServletRequest httpServletRequest,
			@RequestParam("department") final String programId) {

		bindModel(httpServletRequest);
		List<Long> vendorIdList = null;
		if (programId != null && !programId.isEmpty()) {
			vendorIdList = new ArrayList<>();
			String fileArray[] = programId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				vendorIdList.add(Long.valueOf(fields));
			}
		}
		this.getModel().setProgramIds(vendorIdList);

		return MainetConstants.FlagY;
	}

	@ResponseBody
	@RequestMapping(params = "viewNursingHomeData", method = RequestMethod.POST)
	public ModelAndView viewNursingHomeData(final HttpServletRequest request, @RequestParam String appId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		populateModel();
		Long applicationId = Long.parseLong(appId);
		TbCfcApplicationMst cfcApplicationMst = tbCFCApplicationMst.findById(applicationId);
		String shortCode = tbServicesMstService.getServiceShortDescByServiceId(cfcApplicationMst.getSmServiceId());
		this.getModel().setCfcApplicationMst(cfcApplicationMst);
		CFCApplicationAddressEntity cfcApplicationAddressEntity = cfcAddressRepo.findOne(applicationId);
		this.getModel().setCfcApplicationAddressEntity(cfcApplicationAddressEntity);
        if(shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
        	this.getModel().setcFCSonographyMastDtos(cfcNursingHomeService.findDetByApplicationId(applicationId));        	
        	this.getModel().setCfcHospitalInfoDTO(new CFCNursingHomeInfoDTO());
           if(cfcApplicationAddressEntity.getApaZoneNo() != null && cfcApplicationAddressEntity.getApaWardNo() != null) {
        	this.getModel().getCfcHospitalInfoDTO().setCfcWard1(cfcApplicationAddressEntity.getApaZoneNo());
    		this.getModel().getCfcHospitalInfoDTO().setCfcWard2(cfcApplicationAddressEntity.getApaWardNo());
           }
        }else {
        	this.getModel().setCfcHospitalInfoDTO(cfcNursingHomeService.findByApplicationId(applicationId));
        	this.getModel().getCfcHospitalInfoDTO().setCfcWard1(cfcApplicationAddressEntity.getApaZoneNo());
    		this.getModel().getCfcHospitalInfoDTO().setCfcWard2(cfcApplicationAddressEntity.getApaWardNo());
        }
 
       
		this.getModel().setTbDepartments(
				ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());

		this.getModel().setDocumentList(checklistVerificationService.getDocumentUploaded(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setSaveMode(MainetConstants.FlagV);
	
		return new ModelAndView("NursingHomePermission/view", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewHospitalInfo", method = RequestMethod.POST)
	public ModelAndView viewHospitalInfo(final HttpServletRequest request) {
         // #129863
		bindModel(request);	
		String shortCode = null ;
       if (this.getModel().getCfcApplicationMst().getSmServiceId() != null) {
    	   shortCode = tbServicesMstService.getServiceShortDescByServiceId(this.getModel().getCfcApplicationMst().getSmServiceId());
       }
		
		if(shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {
		List<LookUp> list1 = CommonMasterUtility.getListLookup("FAT",UserSession.getCurrent().getOrganisation());
		for (final LookUp l1 : list1) {
			l1.setOtherField(MainetConstants.FlagN);			
		}
	   
		this.getModel().setList(list1);
		List<LookUp> list2 = CommonMasterUtility.getListLookup("FCC",UserSession.getCurrent().getOrganisation());
			for (final LookUp l2 : list2) {
				l2.setOtherField(MainetConstants.FlagN);
			}
	  	this.getModel().setList1(list2);
		
		this.getModel().getList().forEach(lookup ->{
			this.getModel().getcFCSonographyMastDtos().getCfcSonoDetDtoList().forEach(dto->{			
				if(dto.getFacilityTest() != null && (dto.getFacilityTest() == lookup.getLookUpId())) {
					lookup.setOtherField(MainetConstants.FlagY);
				}
			});			
		});		
		this.getModel().getList1().forEach(lookup ->{
			this.getModel().getcFCSonographyMastDtos().getCfcSonoDetDtoList().forEach(dto ->{	
				if (dto.getFacilityCenter() != null && (dto.getFacilityCenter() == lookup.getLookUpId())) {
					lookup.setOtherField(MainetConstants.FlagY);
				}
			});	
		 });
		}else{
			List<LookUp> listLookup = CommonMasterUtility.getListLookup("NPL",
					UserSession.getCurrent().getOrganisation());
			listLookup.forEach(look -> {
				look.setOtherField(MainetConstants.FlagN);
			});
			this.getModel().setList(listLookup);
			this.getModel().getList().forEach(lookup -> {
				this.getModel().getCfcHospitalInfoDTO().getCfcHospitalInfoDetailDTOs().forEach(dto -> {
					if (dto.getProgramId() == lookup.getLookUpId()) {
						lookup.setOtherField(MainetConstants.FlagY);
					}
				});
			});
		}
		if (StringUtils.isNotEmpty(shortCode)  && shortCode.equals(MainetConstants.CFCServiceCode.Hospital_Sonography_center)) {			
			return new ModelAndView("NursingHomePermission/sonographyForm", MainetConstants.FORM_NAME, this.getModel());
		}else
		return new ModelAndView("NursingHomePermission/hospitalInfo", MainetConstants.FORM_NAME, this.getModel());
	}
	
	/*122597
		To generate the acknowledgement receipt after application punch
	*/
	@RequestMapping(params = "printCFCAckRcpt", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		NursingHomePermisssionModel model = this.getModel();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.Nursing_Home_Reg, UserSession.getCurrent().getOrganisation().getOrgid());
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
		return new ModelAndView("CommonAcknowledgement",MainetConstants.FORM_NAME, this.getModel());

	}
	 // #129863
	@SuppressWarnings("unused")
	@RequestMapping(params = "addFacilities", method = RequestMethod.POST)
	public @ResponseBody String addFacilities(HttpServletRequest httpServletRequest,
			 @RequestParam("facilityTest") final String facilityTest, @RequestParam("facilityCenter") final String facilityCenter) {

		bindModel(httpServletRequest);
		List<Long> facilityTestList = null;
		List<Long> facilityCenterList = null;
		if (facilityTest != null && !facilityTest.isEmpty()) {
			facilityTestList = new ArrayList<>();
			String fileArray[] = facilityTest.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				facilityTestList.add(Long.valueOf(fields));
			}
		}
		if (facilityCenter!= null && !facilityCenter.isEmpty()) {
			facilityCenterList = new ArrayList<>();
			String fileArraylist[] = facilityCenter.split(MainetConstants.operator.COMMA);
			for (String fields : fileArraylist) {
				facilityCenterList.add(Long.valueOf(fields));
			}
		}
		this.getModel().setFacilityTest(facilityTestList);
        this.getModel().setFacilityCenter(facilityCenterList);
		return MainetConstants.FlagY;
	}
	
	@ResponseBody
	@RequestMapping (params = "getFacilityCenter", method = RequestMethod.POST)
	List<Long> facilityCenter(HttpServletRequest request){
		bindModel(request);
		return this.getModel().getFacilityCenter();
	}
	@ResponseBody
	@RequestMapping (params = "getFacilityTest", method = RequestMethod.POST)
	List<Long> facilityTest(HttpServletRequest request){
		bindModel(request);
		return this.getModel().getFacilityTest();
	}
	
}
