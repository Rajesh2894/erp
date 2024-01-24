package com.abm.mainet.rti.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiApplicationUserDetailFormModel;


@Controller
@RequestMapping("/RtiApplicationUserDetailForm.html")
public class RtiApplicationUserDetailFormController extends AbstractFormController<RtiApplicationUserDetailFormModel> {

	private static final Logger LOGGER = Logger.getLogger(RtiApplicationUserDetailFormController.class);

	@Autowired
	IPortalServiceMasterService serviceMasterService;

	@Resource
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private IOrganisationService iOrganisationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final RtiApplicationUserDetailFormModel model = getModel();
		final Employee emp = UserSession.getCurrent().getEmployee();
		if (emp != null) {
			setApplicantDetail(emp, model);

		}

		/* changes for time restriction start */

		java.time.LocalTime time = java.time.LocalTime.now();

		String times = time.toString();

		String[] timess = times.split("\\.");

		times = timess[0].replaceAll(":", "");

		long cur_timeL = Long.parseLong(times.toString());
		long first_time = Long.valueOf((ApplicationSession.getInstance().getMessage("rti.HH.MM.SS.firsttime")));
		long second_time = Long.valueOf((ApplicationSession.getInstance().getMessage("rti.HH.MM.SS.secondtime")));
		ModelAndView mv = null;
		if (cur_timeL >= first_time && cur_timeL <= second_time) {
			mv = new ModelAndView("RtiGuidelinesPage", MainetConstants.FORM_NAME, getModel());
			return mv;
		} else {
			mv = new ModelAndView("RtiApplicationTimeForm", MainetConstants.FORM_NAME, getModel());
			return mv;
		}

	}

	@RequestMapping(params = "accept", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView search(HttpServletRequest request) {
		this.sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		Employee emp = UserSession.getCurrent().getEmployee();
		RtiApplicationUserDetailFormModel model = getModel();
		setApplicantDetail(emp, model);
		// For filtering deprtment RTI from lookup
		if (isEnvPresent(MainetConstants.APP_NAME.DSCL) || isEnvPresent(MainetConstants.APP_NAME.SUDA)) {
			Organisation org = iOrganisationService.getOrganisationById(Utility.getOrgId());
			this.getModel().setRelated_dept(model.getDepartments());
			this.getModel()
					.setDepartments(model.getDepartments().stream()
							.filter(l -> l.getLookUpCode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.RTI))
							.collect(Collectors.toSet()));
			
			
			
				
				for (Iterator<LookUp> it = model.getDepartments().iterator(); it.hasNext();) {
					LookUp f = it.next();
					if (f.getLookUpCode().equals(MainetConstants.DEPT_SHORT_NAME.RTI)) {
						model.getReqDTO().setRtiDeptId((int) f.getLookUpId());
						// D#124048
					if (isEnvPresent(MainetConstants.APP_NAME.SUDA)) {
						if (UserSession.getCurrent().getLanguageId() == 1) {
							model.getReqDTO().setDepartmentName(f.getLookUpDesc());
						} else {
							model.getReqDTO().setDepartmentName(f.getDescLangSecond());
						}
					}
				}
			}
			if (isEnvPresent(MainetConstants.APP_NAME.DSCL)) {
				this.getModel().setRefrnceList(Arrays.asList(CommonMasterUtility.getValueFromPrefixLookUp("O", "RIT", org)));
				this.getModel().setDeptId((long) model.getReqDTO().getRtiDeptId());
				this.getModel().getReqDTO().setApplReferenceMode((int)CommonMasterUtility.getValueFromPrefixLookUp("D", "RRM", org).getLookUpId());
				this.getModel().getReqDTO().setInwardType((int)CommonMasterUtility.getValueFromPrefixLookUp("O", "RIT", org).getLookUpId());
				this.getModel().getReqDTO().setApplicationType(CommonMasterUtility.getValueFromPrefixLookUp("I", "ATP", org).getLookUpId());
				if(this.getModel().getReqDTO().getRtiDeptId()!=0) {
					Set<LookUp> lookup=	rtiApplicationDetailService.getDeptLocation(org.getOrgid(), (long) this.getModel().getReqDTO().getRtiDeptId());
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
		this.getModel().setShowBtn("Y");
		return new ModelAndView("RtiApplicationUserDetailForm", MainetConstants.FORM_NAME, this.getModel());
	}

	private void setApplicantDetail(Employee emp, RtiApplicationUserDetailFormModel model) {

		model.getReqDTO().setfName(emp.getEmpname());
		model.getReqDTO().setmName(emp.getEmpMName());
		model.getReqDTO().setlName(emp.getEmpLName());
		model.getReqDTO().setMobileNo((emp.getEmpmobno()));
		model.getReqDTO().setEmail(emp.getEmpemail());
		model.getReqDTO().setTitleId(emp.getTitle());
		// Defect#118623
		// for handling Number Format Exception
		if (!StringUtils.isEmpty(emp.getPincode())) {
			model.getReqDTO().setPincodeNo(Long.valueOf(emp.getPincode()));
		}
		model.getReqDTO().setAreaName(emp.getEmpAddress());

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		List<LookUp> lookUpsTitle = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.TITLE,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((emp.getEmpGender() != null) && !emp.getEmpGender().isEmpty()
					&& lookUp.getLookUpCode().equals(emp.getEmpGender())) {
				{
					model.getReqDTO().setGender(String.valueOf(lookUp.getLookUpId()));
					break;
				}
			}
		}
		for (LookUp lookUp : lookUpsTitle) {
			if (emp.getTitle() != null) {
				if (lookUp.getLookUpId() == emp.getTitle()) {
					model.getReqDTO().setTitleId(lookUp.getLookUpId());
					/* model.setTitle(lookUp.getLookUpDesc()); */
					break;
				}
			}
		}

	}

	/* Method for Getting Checklist and service charge from BRMS */
	@RequestMapping(params = "getRtiCheckListAndCharge", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getChecklistAndServiceCharge(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		RtiApplicationUserDetailFormModel model = this.getModel();
		RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		Organisation org = new Organisation();
		if (reqDTO.getOrgId() == null || reqDTO.getOrgId() == 0L) {
			reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			org = UserSession.getCurrent().getOrganisation();
		} else {
			org = iOrganisationService.getOrganisationById(reqDTO.getOrgId());
		}
		String isBPLFlag = "N";
		Long applicationType = reqDTO.getApplicationType();
		String applicantCode = CommonMasterUtility.findLookUpCode("ATP", reqDTO.getOrgId(),
				reqDTO.getApplicationType());
		if (applicantCode != null && !applicantCode.equals("O")) {
			isBPLFlag = CommonMasterUtility.findLookUpCode("YNC", reqDTO.getOrgId(), Long.valueOf(reqDTO.getIsBPL()));
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps("ATP", org);
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
				List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
				List<Object> rtiratemasterlist = JersyCall.castResponse(response, RtiRateMaster.class, 1);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				final RtiRateMaster rtiRateMaster = (RtiRateMaster) rtiratemasterlist.get(0);

				checkListModel.setOrgId(reqDTO.getOrgId());
				checkListModel.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
				checkListModel.setIsBPL(isBPLFlag);
				checkListModel.setApplicantType(reqDTO.getApplicant());
				if (isBPLFlag.equals("Y")) {
					final WSRequestDTO checkRequestDto = new WSRequestDTO();
					checkRequestDto.setDataModel(checkListModel);
					docs = brmsCommonService.getChecklist(checkListModel);
					if (response.getWsStatus() != null
							&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
						if (docs != null && !docs.isEmpty()) {
							Long fileSerialNo = 1L;
							for (final DocumentDetailsVO docSr : docs) {
								docSr.setDocumentSerialNo(fileSerialNo);
								fileSerialNo++;
							}
						}
					} else {
						mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
					}
				}

				model.setCheckList(docs);
				/* end of checklist document fetch code */
				rtiRateMaster.setOrgId(reqDTO.getOrgId());
				rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
				rtiRateMaster.setChargeApplicableAt(Long
						.toString(CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.RoadCuttingConstant.APL,
								MainetConstants.NewWaterServiceConstants.CAA).getLookUpId()));
				final WSRequestDTO taxRequestDto = new WSRequestDTO();
				taxRequestDto.setDataModel(rtiRateMaster);

				/* Method of getting Service Charge from BRMS rule */
				WSResponseDTO res = rtiApplicationDetailService.getApplicableTaxes(rtiRateMaster, taxRequestDto,
						PrefixConstants.Prefix.APL);

				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
					if (!res.isFree() && !isBPLFlag.equals("Y")) {
						final List<?> rates = JersyCall.castResponse(res, RtiRateMaster.class);
						final List<RtiRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							RtiRateMaster master1 = (RtiRateMaster) rate;
							master1 = populateChargeModel(model, master1, isBPLFlag);
							requiredCHarges.add(master1);
						}

						WSRequestDTO chargeReqDto = new WSRequestDTO();
						chargeReqDto.setDataModel(requiredCHarges);
						List<MediaChargeAmountDTO> chargesResDTO = rtiApplicationDetailService
								.getApplicableCharges(requiredCHarges);
						model.setIsFree("N");
						model.getReqDTO().setFree(false);

						model.setCharges(chargesToPay(chargesResDTO));
						setChargeMap(model, chargesResDTO);
						model.getOfflineDTO().setAmountToShow(model.getCharges());
					} else {
						model.setIsFree(MainetConstants.Common_Constant.YES);
						model.getReqDTO().setFree(true);
						model.setCharges(0.0d);
					}
					if ((isBPLFlag.equals("N") && applicantCode.equals("I")) || applicantCode.equals("O")) {
						/* end of code for getting Service Charge */
						mv = new ModelAndView("RtiApplicationUserDetailFormValidn", MainetConstants.FORM_NAME,
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

	private void setChargeMap(RtiApplicationUserDetailFormModel model, List<MediaChargeAmountDTO> chargeDetailDTO) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : chargeDetailDTO) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	private Double chargesToPay(List<MediaChargeAmountDTO> chargeDetailDTO) {
		double amountSum = 0.0;
		for (MediaChargeAmountDTO charge : chargeDetailDTO) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private RtiRateMaster populateChargeModel(RtiApplicationUserDetailFormModel model, RtiRateMaster rtiRateMaster,
			String bplFlag) {
		rtiRateMaster.setOrgId(model.getReqDTO().getOrgId());
		rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
		rtiRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		rtiRateMaster.setRateStartDate(new Date().getTime());
		rtiRateMaster.setIsBPL(bplFlag);
		return rtiRateMaster;
	}

	@RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
	@ResponseBody
	public Set<LookUp> getLocationByDepartment(@RequestParam(value = "deptId", required = true) Long deptId,
			@RequestParam(value = "orgId", required = true) Long orgId1) {
		final RtiApplicationFormDetailsReqDTO dto = this.getModel().getReqDTO();
		Long orgId = null;
		if (orgId1 == null || orgId1 == 0) {
			orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		} else {
			orgId = orgId1;
		}
		dto.setOrgId(orgId);
		dto.setDeptId(deptId);
		Set<LookUp> locList = rtiApplicationDetailService.getDeptLocation(orgId, deptId);
		this.getModel().setLocations(locList);
		return locList;

	}

	/* end */

	/* Method for RTI Acknowledgement page */
	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final RtiApplicationUserDetailFormModel model = this.getModel();
		RtiApplicationFormDetailsReqDTO dto = model.getReqDTO();
		String name = " ";
		if (dto.getfName() != null) {
			name = dto.getfName() + " ";
		}
		if (dto.getmName() != null) {
			name += dto.getmName() + " ";
		}
		if (dto.getlName() != null) {
			name += dto.getlName();
		}
		model.getReqDTO().setApplicantName(name);
		model.getReqDTO().setDateDesc(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(model.getReqDTO().getApmApplicationDate()));
		dto.setDepartmentName(dto.getDepartmentName());
		model.getReqDTO().setStatus("Pending");
		return new ModelAndView("RtiApplicationUserStatusForm", MainetConstants.FORM_NAME, model);

	}
	/* end */

	/* method to validate and Save RTI Application */
	@RequestMapping(params = "saveRti", method = RequestMethod.POST)
	public ModelAndView saveRtiApplication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		RtiApplicationUserDetailFormModel model = this.getModel();
		final RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		ModelAndView mv = null;
        if(reqDTO.getDeptId()==null)
        	reqDTO.setDeptId((long)reqDTO.getRtiDeptId());
		List<DocumentDetailsVO> docs = model.getCheckList();
		List<DocumentDetailsVO> docList = model.getUploadFileList();

		List<DocumentDetailsVO> stampList = model.getFetchStampDoc();
		if (docs != null) {
			docs = setFileUploadMethod(docs);
		}
		if (docList != null) {
			docList = prepareFileUpload(docList);
            model.setUploadFileList(docList);
		}
//added regarding US#111612
		if (stampList != null && (model.getReqDTO().getStampNo() != null && model.getReqDTO().getStampAmt() != null)) {
			stampList = prepareFileUploadStampDoc(stampList, "S");
			model.getReqDTO().setStampDoc(stampList);
		}
		if (stampList != null
				&& (model.getReqDTO().getPostalCardNo() != null && model.getReqDTO().getPostalAmt() != null)) {
			stampList = prepareFileUploadStampDoc(stampList, "P");
			model.getReqDTO().setPostalDoc(stampList);
		}if (stampList != null
				&& (model.getReqDTO().getNonJudclNo() != null )) {
			stampList = prepareFileUploadStampDoc(stampList, "N");
			model.getReqDTO().setChlNonJudDoc(stampList);
		}
		if (stampList != null
				&& (model.getReqDTO().getChallanNo() != null )) {
			stampList = prepareFileUploadStampDoc(stampList, "C");
			model.getReqDTO().setChlNonJudDoc(stampList);
		}
		
		model.getReqDTO().setDocumentList(docs);
		model.getReqDTO().setFetchDocs(docList);
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}

		mv = new ModelAndView("RtiApplicationUserDetailFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}
	/* end */

	private List<DocumentDetailsVO> prepareFileUpload(List<DocumentDetailsVO> document) {
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
						if (entry.getKey() == 200) {
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

	private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();

		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			Base64 base64 = null;
			List<File> list = null;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						throw new FrameworkException("Exception has been occurred in file byte to string conversions",
								e);
					}
				}
			}
		}
		if (!docs.isEmpty() && !listOfString.isEmpty()) {
			for (final DocumentDetailsVO d : docs) {
				final long count = d.getDocumentSerialNo() - 1;
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
			}
		}
		return docs;
	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		RtiApplicationUserDetailFormModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;

		LOGGER.info("Inside dashboardView method appId" + appId + "   orgId" + orgId);
		RtiApplicationFormDetailsReqDTO rtiDto = rtiApplicationDetailService.fetchRtiApplicationInformationById(appId,
				orgId);
		LOGGER.info("Inside dashboardView method rtiApplicationDetailService method return value "
				+ rtiDto.getApplicantName() + "    " + rtiDto.getMobileNo());
		//D#129188
		Organisation org = iOrganisationService.getOrganisationById(rtiDto.getOrgId());
		//D#133280
		if(rtiDto.getDistrict() ==null || rtiDto.getDistrict()==0) {
			rtiDto.setDistrict(org.getOrgCpdIdDis());
		}
		if(UserSession.getCurrent().getLanguageId()==1) {
			rtiDto.setApmOrgnName(org.getONlsOrgname());
		}
		else {
			rtiDto.setApmOrgnName(org.getONlsOrgnameMar());
		}
		// D#120769
		List<DocumentDetailsVO> checkList = brmsCommonService
				.getChecklistDocument(rtiDto.getApmApplicationId().toString(), rtiDto.getOrgId(), "Y");
		List<DocumentDetailsVO> checkList1 = brmsCommonService.getChecklistDocument(rtiDto.getRtiNo(),
				rtiDto.getOrgId(), "Y");
		List<DocumentDetailsVO> checkList3 = brmsCommonService.getChecklistDocument(MainetConstants.Nine_Zero_Nine.concat(rtiDto.getRtiNo()),
				rtiDto.getOrgId(), "Y");
		checkList.addAll(checkList1);
		model.setCheckList(checkList);
		model.setUploadFileList(checkList3);
		LOGGER.info("rtiDto ====>> " + rtiDto);
		model.setReqDTO(rtiDto);
		model.setBackBtn("Y");
		model.setShowBtn("N");

		/* #29380,#29855 by Priti */

		mv = new ModelAndView("RtiApplicationViewForm", MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, params = "rtiOrganisations")
	public @ResponseBody List<LookUp> grievanceOrganisations(@RequestParam Long districtId,
			HttpServletRequest httpServletRequest) {
		this.getModel().getOrgLookups().clear();
		this.getModel().setOrg(iOrganisationService.getAllMunicipalOrganisation(districtId));
		if (getModel().getOrg() != null) {
			for (Organisation org : getModel().getOrg()) {
				LookUp lookup = new LookUp();
				lookup.setLookUpId(org.getOrgid().longValue());
				lookup.setLookUpCode(org.getOrgShortNm());
				lookup.setDescLangFirst(org.getONlsOrgname());
				lookup.setDescLangSecond(org.getONlsOrgnameMar());
				lookup.setLookUpDesc(lookup.getLookUpDesc());
				this.getModel().getOrgLookups().add(lookup);
			}
		}
		/*
		 * return new ModelAndView(MainetConstants.GrievanceConstants.ORGANISATION,
		 * MainetConstants.FORM_NAME, getModel());
		 */
		return this.getModel().getOrgLookups();
	}

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetailRti", MainetConstants.FORM_NAME, getModel());
	}

	public boolean isEnvPresent(String codeVal) {
		Organisation org = iOrganisationService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		if (CollectionUtils.isNotEmpty(envLookUpList)) {
			return envLookUpList.stream().anyMatch(env -> env != null && env.getLookUpCode().equals(codeVal)
					&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		}
		return false;
	}

	private List<DocumentDetailsVO> prepareFileUploadStampDoc(List<DocumentDetailsVO> document, String docType) {

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
						throw new FrameworkException("Exception has been occurred in file byte to string conversions",
								e);
					}
				}
			}
		}
		if (docType != null && docType.equals("S")) {
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
		if (docType != null && docType.equals("P")) {
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
		}else if (docType != null && docType.equals(MainetConstants.FlagN)) {
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
