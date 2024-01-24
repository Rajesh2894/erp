/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Controller file for RTI Application Form.
 * @method  : getChecklistAndServiceCharge - for getting applicable checklist document list and charges
 *            getPincodeByLocationId - for getting pincode by location name.            
 */

package com.abm.mainet.rti.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.repository.RtiRepository;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiApplicationFormModel;
import com.abm.mainet.rti.utility.RtiUtility;

@Controller
@RequestMapping("/RtiApplicationDetailForm.html")
public class RtiApplicationDetailController extends AbstractFormController<RtiApplicationFormModel> {

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Resource
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Resource
	private BRMSCommonService brmsCommonService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private IWorkflowRequestService requestService;

	@Resource
	RtiRepository rtiRepository;
	@Autowired
	private RtiUtility rtiUtility;

	@Autowired
	private DepartmentService departmentService;

	private static final Logger LOGGER = Logger.getLogger(RtiApplicationDetailController.class);

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final RtiApplicationFormModel model = getModel();
		// String time = rtiRepository.getCurrentTime();

		java.time.LocalTime time = java.time.LocalTime.now();

		String times = time.toString();

		String[] timess = times.split("\\.");

		times = timess[0].replaceAll(":", "");

		/*
		 * String time = rtiApplicationDetailService.getCurentTime(); time =
		 * time.replaceAll(":", "");
		 */
//For filtering deprtment RTI from lookup
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)
				|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			Organisation org = UserSession.getCurrent().getOrganisation();
			getModel().setRelated_dept(model.getDepartments());
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
			model.setDepartments(
					model.getDepartments().stream().filter(l -> l.getLookUpId() == deptId).collect(Collectors.toSet()));
			getModel().getReqDTO().setRtiDeptId(deptId.intValue());
			// D#124084
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {

				if (UserSession.getCurrent().getLanguageId() == 1) {
					getModel().getReqDTO()
							.setDepartmentName(model.getDepartments().stream()
									.filter(dept -> dept != null && dept.getLookUpId() == deptId)
									.collect(Collectors.toList()).get(0).getLookUpDesc());
				} else {
					getModel().getReqDTO()
							.setDepartmentName(model.getDepartments().stream()
									.filter(dept -> dept != null && dept.getLookUpId() == deptId)
									.collect(Collectors.toList()).get(0).getDescLangSecond());
				}
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)) {
				LookUp rfmLookup = CommonMasterUtility.getValueFromPrefixLookUp("H", "RIT",
						UserSession.getCurrent().getOrganisation());
				getModel().setRefList(Arrays.asList(rfmLookup));
				getModel().getReqDTO().setDeptId(deptId);
				getModel().getReqDTO().setApplReferenceMode(
						(int) CommonMasterUtility.getValueFromPrefixLookUp("D", "RRM", org).getLookUpId());
				getModel().getReqDTO().setInwardType(
						(int) CommonMasterUtility.getValueFromPrefixLookUp("O", "RIT", org).getLookUpId());
				if (this.getModel().getReqDTO().getRtiDeptId() != 0) {
					Set<LookUp> lookup = rtiApplicationDetailService.getDeptLocation(org.getOrgid(),
							(long) getModel().getReqDTO().getRtiDeptId());
					if (CollectionUtils.isNotEmpty(lookup)) {
						for (LookUp lkp : lookup) {
							if (lkp.getDescLangFirst().equalsIgnoreCase("Head Office")) {
								this.getModel().getReqDTO().setRtiLocationId((int) lkp.getLookUpId());
							}
						}
					}
				}
			}

		}
		long cur_timeL = Long.parseLong(times.toString());
		long first_time = Long.valueOf((ApplicationSession.getInstance().getMessage("rti.HH.MM.SS.firsttime")));
		long second_time = Long.valueOf((ApplicationSession.getInstance().getMessage("rti.HH.MM.SS.secondtime")));
		setCommonFields(model);
		ModelAndView mv = null;
		model.setCommonHelpDocs("RtiApplicationDetailForm.html");
		if (cur_timeL >= first_time && cur_timeL <= second_time) {
			mv = new ModelAndView("RtiApplicationDetailForm", MainetConstants.FORM_NAME, getModel());
			return mv;
		} else {
			mv = new ModelAndView("RtiApplicationTimeForm", MainetConstants.FORM_NAME, getModel());
			return mv;
		}
	}

	/*
	 * @RequestMapping(method = { RequestMethod.POST }) public ModelAndView
	 * index(final HttpServletRequest httpServletRequest) {
	 * sessionCleanup(httpServletRequest); fileUpload.sessionCleanUpForFileUpload();
	 * getModel().bind(httpServletRequest); final RtiApplicationFormModel model =
	 * getModel(); setCommonFields(model); ModelAndView mv = null;
	 * model.setCommonHelpDocs("RtiApplicationDetailForm.html"); mv = new
	 * ModelAndView("RtiApplicationDetailForm", MainetConstants.FORM_NAME,
	 * getModel()); return mv; }
	 */

	private void setCommonFields(RtiApplicationFormModel model) {
		final RtiApplicationFormDetailsReqDTO dto = model.getReqDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		final ServiceMaster service = serviceMasterService
				.getServiceByShortName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getSmServiceId());
		model.getReqDTO().setServiceId(service.getSmServiceId());
		dto.setSmServiceId(service.getSmServiceId());

	}

	/* Method for Getting Checklist and service charge from BRMS */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = MainetConstants.RTISERVICE.GET_RTI_CHECKLIST_AND_CHARGE, method = RequestMethod.POST)
	public @ResponseBody ModelAndView getChecklistAndServiceCharge(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		RtiApplicationFormModel model = this.getModel();
		RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		reqDTO.setOrgId(model.getOrgId());
		String isBPLFlag = "N";
		Long applicationType = reqDTO.getApplicationType();
		String applicantCode = CommonMasterUtility.findLookUpCode(PrefixConstants.APPLICATION_TYPE_PREFIX,
				UserSession.getCurrent().getOrganisation().getOrgid(), reqDTO.getApplicationType());
		if (applicantCode != null && !applicantCode.equals("O")) {
			isBPLFlag = CommonMasterUtility.findLookUpCode("YNC", UserSession.getCurrent().getOrganisation().getOrgid(),
					Long.valueOf(reqDTO.getIsBPL()));
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.APPLICATION_TYPE_PREFIX,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((applicationType != null) && lookUp.getLookUpId() == applicationType) {
				reqDTO.setApplicant(lookUp.getLookUpDesc());
				break;
			}
		}

		reqDTO.setApplicationType(applicationType);
		ModelAndView mv = null;

		/* Method for getting Checklist Document for BPL from rules */

		try {

			List<DocumentDetailsVO> docs = null;
			final WSRequestDTO initRequestDto = new WSRequestDTO();
			initRequestDto.setModelName(MainetConstants.RTISERVICE.CHECKLIST_RTIRATE_MASTER);
			WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
				List<Object> rtiratemasterlist = RestClient.castResponse(response, RtiRateMaster.class, 1);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				final RtiRateMaster rtiRateMaster = (RtiRateMaster) rtiratemasterlist.get(0);

				checkListModel.setOrgId(reqDTO.getOrgId());
				checkListModel.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
				checkListModel.setIsBPL(isBPLFlag);
				checkListModel.setApplicantType(reqDTO.getApplicant());
				if (isBPLFlag.equals("Y")) {
					final WSRequestDTO checkRequestDto = new WSRequestDTO();
					try {
						checkRequestDto.setDataModel(checkListModel);
						WSResponseDTO checklistResp = brmsCommonService.getChecklist(checkRequestDto);
						if (response.getWsStatus() != null
								&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
							docs = (List<DocumentDetailsVO>) checklistResp.getResponseObj();
							if (docs != null && !docs.isEmpty()) {
								long cnt = 1;
								for (final DocumentDetailsVO doc : docs) {
									doc.setDocumentSerialNo(cnt);
									cnt++;
								}
							}
						} else {
							mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
						}
					} catch (FrameworkException e) {
						this.getModel().addValidationError(
								ApplicationSession.getInstance().getMessage("rti.no.checkList.found"));
						mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_VALIDATION_VIEW, MainetConstants.FORM_NAME,
								getModel());
						mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return mv;
					}
				}

				model.setCheckList(docs);
				/* end of checklist document fetch code */
				rtiRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
				rtiRateMaster.setChargeApplicableAt(
						Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
								MainetConstants.NewWaterServiceConstants.CAA,
								UserSession.getCurrent().getOrganisation()).getLookUpId()));
				final WSRequestDTO taxRequestDto = new WSRequestDTO();
				taxRequestDto.setDataModel(rtiRateMaster);

				/* Method of getting Service Charge from BRMS rule */
				WSResponseDTO responseDTO = null;
				try {
					responseDTO = rtiApplicationDetailService.getApplicableTaxes(taxRequestDto);
				} catch (Exception e) {
					this.getModel()
							.addValidationError(ApplicationSession.getInstance().getMessage("rti.no.charge.found"));
					mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_VALIDATION_VIEW, MainetConstants.FORM_NAME,
							getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
				if (responseDTO.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
					if (!responseDTO.isFree()) {
						final List<Object> rates = (List<Object>) responseDTO.getResponseObj();
						final List<RtiRateMaster> requiredCharges = new ArrayList<>();
						for (final Object rate : rates) {
							RtiRateMaster rtiMaster = (RtiRateMaster) rate;
							rtiMaster = populateChargeModel(model, rtiMaster, isBPLFlag);
							requiredCharges.add(rtiMaster);
						}
						WSRequestDTO chargeReqDto = new WSRequestDTO();
						chargeReqDto.setDataModel(requiredCharges);
						WSResponseDTO chargesResDTO = rtiApplicationDetailService.getApplicableCharges(chargeReqDto);

						final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
								.getResponseObj();
						// code added for production issue Defect #92885 start
						if (chargeDetailDTO == null) {
							this.getModel().addValidationError(
									ApplicationSession.getInstance().getMessage("rti.no.charge.found"));
							mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_VALIDATION_VIEW,
									MainetConstants.FORM_NAME, getModel());
							mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
									getModel().getBindingResult());
							return mv;
						}
						// end
						model.setIsFree(MainetConstants.N_FLAG);
						model.getReqDTO().setFree(false);

						model.setCharges(chargesToPay(chargeDetailDTO));
						setChargeMap(model, chargeDetailDTO);
						model.getOfflineDTO().setAmountToShow(model.getCharges());
					} else {
						model.setIsFree(MainetConstants.Common_Constant.YES);
						model.getReqDTO().setFree(true);
						model.setCharges(0.0d);
					}
					if ((isBPLFlag.equals("N") && applicantCode.equals("I")) || applicantCode.equals("O")) {
						/* end of code for getting Service Charge */
						mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_VALIDATION_VIEW, MainetConstants.FORM_NAME,
								getModel());
					} else {
						mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_CHECKLIST_CHARGE_VIEW,
								MainetConstants.FORM_NAME, getModel());
					}
				} else {
					mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
				}

			} else {
				mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
			}

		} catch (FrameworkException e) {
			LOGGER.info(e.getErrMsg());
			// model.setCheckList(null);
			mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
		}

		return mv;
	}
	/* end */

	private void setChargeMap(RtiApplicationFormModel model, List<MediaChargeAmountDTO> chargeDetailDTO) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : chargeDetailDTO) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	private double chargesToPay(List<MediaChargeAmountDTO> chargeDetailDTO) {
		double amountSum = 0.0;
		for (final MediaChargeAmountDTO charge : chargeDetailDTO) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	/* For populating RtiRateMaster Model for BRMS call */
	private RtiRateMaster populateChargeModel(RtiApplicationFormModel model, RtiRateMaster rtiRateMaster,
			String bplFlag) {
		rtiRateMaster.setOrgId(model.getOrgId());
		rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
		rtiRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		rtiRateMaster.setRateStartDate(new Date().getTime());
		rtiRateMaster.setIsBPL(bplFlag);
		return rtiRateMaster;
	}
	/* end */

	/* For Getting Location by Department Name */
	/*
	 * @RequestMapping(params =
	 * MainetConstants.RTISERVICE.GET_LOCATION_BY_DEPARTMENT, method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public List<LookUp> getLocationByDepartment(
	 * 
	 * @RequestParam(value = "deptId", required = true) Long deptId) { Long orgid =
	 * UserSession.getCurrent().getOrganisation().getOrgid(); return
	 * rtiApplicationDetailService.getDeptLocation(orgid, deptId); }
	 */

	@RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
	@ResponseBody
	public Set<LookUp> getLocationByDepartment(@RequestParam(value = "deptId", required = true) Long deptId) {
		final RtiApplicationFormDetailsReqDTO dto = this.getModel().getReqDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		dto.setDeptId(deptId);
		Set<LookUp> locList = rtiApplicationDetailService.getDeptLocation(orgId, deptId);
		this.getModel().setLocations(locList);
		return locList;
	}
	/* end */

	/* For showing acknowledgment page in case of free service */
	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().bind(httpServletRequest);
		final RtiApplicationFormModel model = getModel();
		Long applicationId = model.getReqDTO().getApmApplicationId();
		RtiApplicationFormDetailsReqDTO rtiDto = model.getReqDTO();
		WorkflowRequest workflowRequest = requestService.findByApplicationId(applicationId);
		model.setReqDTO(
				rtiApplicationDetailService.fetchRtiApplicationInformationById(rtiDto.getApmApplicationId(), orgId));
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(rtiDto.getApmApplicationId()));
		model.setCfcEntity(cfcService.getCFCApplicationByApplicationId(rtiDto.getApmApplicationId(), orgId));
		// model.getReqDTO().setApplicantName(model.getCfcEntity().getApmFname());
		model.getReqDTO()
				.setApplicantName(model.getCfcEntity().getApmFname().concat(MainetConstants.WHITE_SPACE)
						.concat(model.getCfcEntity().getApmMname().concat(MainetConstants.WHITE_SPACE))
						.concat(model.getCfcEntity().getApmLname()));

		model.getReqDTO().setDateDesc(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(model.getReqDTO().getApmApplicationDate()));
		model.getReqDTO().setDepartmentName(
				rtiApplicationDetailService.getdepartmentNameById(Long.valueOf(model.getReqDTO().getRtiDeptId())));
		model.getReqDTO().setStatus(workflowRequest.getStatus());
		this.getModel().setReqDTO(model.getReqDTO());
		return new ModelAndView(MainetConstants.RTISERVICE.RTI_SUCCESS_PAGE, MainetConstants.FORM_NAME, model);

	}
	/* end */

	/* method to validate and Save RTI Application */
	@RequestMapping(params = "saveRti", method = RequestMethod.POST)
	public ModelAndView saveRtiApplication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
        String refcode="";
		RtiApplicationFormModel model = this.getModel();
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA) && model.getReqDTO().getApplReferenceMode()!=0) {
			 refcode=	rtiUtility.getPrefixCode(PrefixConstants.LookUpPrefix.RRM,(long)model.getReqDTO().getApplReferenceMode());
			if(StringUtils.containsIgnoreCase("ENCSP", refcode)) {
				model.getOfflineDTO().setAmountToPay(null);
				model.getOfflineDTO().setAmountToShow(null);
				model.getOfflineDTO().setOnlineOfflineCheck(null);
				model.getReqDTO().setFree(true);
			}
			}
		// prepareContractDocumentsData(model.getReqDTO());
		List<DocumentDetailsVO> docs = model.getCheckList();
		List<DocumentDetailsVO> docList = model.getUploadFileList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
       //US#90795
		if (docList != null) {
			docList=prepareFileUploadForImg(docList);
			model.getReqDTO().setFetchDocs(docList);
			model.setUploadFileList(docList);
		}
		// for uploading stamp document
		List<DocumentDetailsVO> stampDoc = model.getUploadStamoDoc();
//added regarding US#111612
		if (stampDoc != null && model.getReqDTO().getStampAmt() != null) {
			stampDoc = prepareFileUploadForStampImg(stampDoc, MainetConstants.FlagS);
			model.getReqDTO().setStampDoc(stampDoc);

		}
		if (stampDoc != null
				&& (model.getReqDTO().getPostalAmt() != null && model.getReqDTO().getPostalCardNo() != null)) {
			stampDoc = prepareFileUploadForStampImg(stampDoc, MainetConstants.FlagP);
			model.getReqDTO().setPostalDoc(stampDoc);

		}
		if(stampDoc!=null&&(model.getReqDTO().getChallanNo()!=null || model.getReqDTO().getNonJudclNo()!=null) ) {
			stampDoc = prepareFileUploadForStampImg(stampDoc, refcode);
			model.getReqDTO().setChlNonJudDoc(stampDoc);
		}
		model.getReqDTO().setDocumentList(docs);
		
		ModelAndView mv = null;
		fileUpload.validateUpload(model.getBindingResult());
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}

		mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_VALIDATION_VIEW, MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> document) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
             
       //US#90795
				int i = 0;
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						if(entry.getKey()==200) {
						fileName.put(entry.getKey() + i, file.getName());
						listOfString.put(entry.getKey() + i, bytestring);
						i++;
						}
						
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
					}
				}
			}
		}
		List<DocumentDetailsVO> docVo = new ArrayList<DocumentDetailsVO>();
		if (!listOfString.isEmpty()) {
			long count = 200;
			for (final Map.Entry<Long, String> entry : listOfString.entrySet()) {
				
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					DocumentDetailsVO d = new DocumentDetailsVO();
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
					docVo.add(d);
					count++;
				}
			}

		}

		return docVo;
	}

	@RequestMapping(params = "getApplicationDetails", method = RequestMethod.POST)
	@ResponseBody
	public RequestDTO getApplicationDetails(@RequestParam(value = "mobileNumber", required = true) String mobileNumber)
			throws Exception {
		return rtiApplicationDetailService.getApplicationDetailsByMobile(mobileNumber);
	}

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetailRti", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	public List<DocumentDetailsVO> prepareFileUploadForStampImg(List<DocumentDetailsVO> document, String docType)
			throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
					}
				}
			}
		}
		if (docType != null && docType.equals(MainetConstants.FlagS)) {
			if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
				long count = 100;
				for (final DocumentDetailsVO d : document) {
					if (d.getDocumentSerialNo() != null) {
						count = d.getDocumentSerialNo() - 1;

					}
					if (listOfString.containsKey(count) && fileName.containsKey(count)) {
						d.setDocumentByteCode(listOfString.get(count));
						d.setDocumentName(fileName.get(count));
					}
					count++;
				}
			}

		}
//added regarding US#111612
		else if (docType != null && docType.equals(MainetConstants.FlagP)) {
			if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
				long count = 230;
				for (final DocumentDetailsVO d : document) {
					if (d.getDocumentSerialNo() != null) {
						count = d.getDocumentSerialNo() - 1;

					}
					if (listOfString.containsKey(count) && fileName.containsKey(count)) {
						d.setDocumentByteCode(listOfString.get(count));
						d.setDocumentName(fileName.get(count));
					}
					count++;
				}
			}
		}
		else if (docType != null && docType.equals(MainetConstants.FlagN)) {
			if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
				long count = 150;
				for (final DocumentDetailsVO d : document) {
					if (d.getDocumentSerialNo() != null) {
						count = d.getDocumentSerialNo() - 1;

					}
					if (listOfString.containsKey(count) && fileName.containsKey(count)) {
						d.setDocumentByteCode(listOfString.get(count));
						d.setDocumentName(fileName.get(count));
					}
					count++;
				}
			}
		}
		else if (docType != null && docType.equals(MainetConstants.FlagC)) {
			if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
				long count = 170;
				for (final DocumentDetailsVO d : document) {
					if (d.getDocumentSerialNo() != null) {
						count = d.getDocumentSerialNo() - 1;

					}
					if (listOfString.containsKey(count) && fileName.containsKey(count)) {
						d.setDocumentByteCode(listOfString.get(count));
						d.setDocumentName(fileName.get(count));
					}
					count++;
				}
			}
		}

		return document;
	}

}
