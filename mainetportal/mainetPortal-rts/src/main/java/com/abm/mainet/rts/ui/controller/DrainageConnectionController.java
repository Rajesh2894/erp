
package com.abm.mainet.rts.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.service.IRtsService;
import com.abm.mainet.rts.service.RtsBrmsService;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;
import com.abm.mainet.rts.ui.model.WaterRateMaster;
//import com.abm.mainet.rts.validator.DrainageConnectionFormValidator;

@Controller
@RequestMapping("/drainageConnection.html")
@ComponentScan(basePackages = "com.abm.mainet")
public class DrainageConnectionController extends AbstractFormController<DrainageConnectionModel> {

	@Autowired
	IPortalServiceMasterService serviceMasterService;

	@Autowired
	ICommonBRMSService brmsCommonService, iCommonBRMSService;

	@Autowired
	RtsBrmsService checklistService;

	@Autowired
	DrainageConnectionService drainageConnectionService;

	@Autowired
	IRtsService rtsService;

	@Autowired
	IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setRequestDTO(requestDTO);
		this.getModel().setCommonHelpDocs("rtsService.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LoadWardZone(httpServletRequest);

		Map<String, String> checklistAndCharges = new LinkedHashMap<String, String>();
		checklistAndCharges = rtsService.serviceInfo(UserSession.getCurrent().getOrganisation().getOrgid());

		if (MapUtils.isNotEmpty(checklistAndCharges)) {
			for (Map.Entry<String, String> map : checklistAndCharges.entrySet()) {
				if (StringUtils.equals(map.getKey(), "checkListApplFlag")) {
					this.getModel().setCheckListApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), "applicationchargeApplFlag")) {
					this.getModel().setApplicationchargeApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), "serviceId")) {
					this.getModel().setServiceId(Long.valueOf(map.getValue()));
				}
				if (StringUtils.equals(map.getKey(), "serviceName")) {
					this.getModel().setServiceName(map.getValue());
				}
				if (StringUtils.equals(map.getKey(), "deptId")) {
					this.getModel().setDeptId(Long.valueOf(map.getValue()));
				}
			}

			if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagY)) {
				this.getModel().setFree(false);
				// add for defect#100746
				this.getModel().getReqDTO().setPayStatus(MainetConstants.FlagY);
			}
			if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagN)) {
				this.getModel().setFree(true);
				// add for defect#100746
				this.getModel().getReqDTO().setPayStatus(MainetConstants.FlagF);
			}
			this.getModel().setDrainageConnectionDto(null);
			/*
			 * // for setting prepopulated value by application no at view time Defect
			 * #81731
			 */ this.getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.FLAGC);

			if (requestDTO.getApplicationId() != null) {
				try {

					RequestDTO reqDto = rtsService.fetchRtsApplicationInformationById(requestDTO.getApplicationId(),
							orgId);
					this.getModel().setReqDTO(reqDto);
					DrainageConnectionDto dto = drainageConnectionService
							.fetchDrainageConnectionInfo(requestDTO.getApplicationId(), orgId);
					this.getModel().setDrainageConnectionDto(dto);
					this.getModel().getReqDTO().setServiceShortCode(MainetConstants.RightToService.DCS);
					this.getModel().setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);
					List<DocumentDetailsVO>list=iCommonBRMSService.getChecklistDocument(requestDTO.getApplicationId().toString(), orgId, "Y");

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

		}
		return index();
	}

	private void LoadWardZone(HttpServletRequest httpServletRequest) {
		Map<Long, String> wardMap = new LinkedHashMap<Long, String>();
		wardMap = rtsService.fetchWardZone(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWardList(wardMap);
	}

	@RequestMapping(params = "resetdraiangeForm", method = RequestMethod.POST)
	public ModelAndView resetdraiangeForm(final Model model, final HttpServletRequest httpServletRequest) {

		this.getModel().bind(httpServletRequest);
		// this.getModel().setRequestDTO(requestDTO);
		return new ModelAndView("DrainageConnection", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "previousPage", method = RequestMethod.POST)
	public ModelAndView previousPage(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		LoadWardZone(httpServletRequest);

		this.getModel().getReqDTO().setServiceShortCode(MainetConstants.RightToService.DCS);
		return new ModelAndView("DrainageConnection", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		ModelAndView mv = null;
		getModel().bind(httpServletRequest);
		final DrainageConnectionModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final Employee emp = UserSession.getCurrent().getEmployee();
		setApplicantDetail(emp, model);
		// iNewWaterConnectionService.findNoOfDaysCalculation(model.getCsmrInfo(), org);
		// if (model.validateInputs()) {
		// [START] BRMS call initialize model
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.MODEL_NAME);
		final WSResponseDTO response = iCommonBRMSService.initializeModel(dto);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
			final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			populateCheckListModel(model, checkListModel2);
			checkListList = iCommonBRMSService.getChecklist(checkListModel2);
			if (checkListList != null && !checkListList.isEmpty()) {
				Long fileSerialNo = 1L;
				for (final DocumentDetailsVO docSr : checkListList) {
					docSr.setDocumentSerialNo(fileSerialNo);
					fileSerialNo++;
				}
				model.setCheckList(checkListList);
				mv = new ModelAndView("rtsCheckListForm", MainetConstants.FORM_NAME, getModel());
			} else {

				getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
				mv = new ModelAndView("rtsCheckListForm", MainetConstants.FORM_NAME, getModel());

			}

			// for setting pre populated values of DCS Information Defect #81731
			if (model.getReqDTO().getApplicationId() != null && org != null) {
				List<DocumentDetailsVO> checkListList1 = drainageConnectionService
						.fetchDrainageConnectionDocsByAppNo(model.getReqDTO().getApplicationId(), org.getOrgid());
				this.getModel().setCheckList(checkListList1);
				this.getModel().setCheckListApplFlag(MainetConstants.FlagF);
				this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagF);

			}
			// checklist done
			// Charges Start
			if(model.getApplicationchargeApplFlag().equals("Y"))
			{
			final WSResponseDTO res = checklistService.getApplicableTaxes(WaterRateMaster,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.RightToService.DCS);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
				if (!res.isFree()) {
					final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
					final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						WaterRateMaster master1 = (WaterRateMaster) rate;
						master1 = populateChargeModel(model, master1);
							if (this.getModel().getDrainageConnectionDto().getNoOfFlat() != null) {
								master1.setNoOfCopies(
										this.getModel().getDrainageConnectionDto().getNoOfFlat().intValue());
							}
						requiredCHarges.add(master1);
					}
					// final List<ChargeDetailDTO> detailDTOs =
					// checklistService.getApplicableCharges(requiredCHarges);
					final List<MediaChargeAmountDTO> chargeDetailDTO = (checklistService
							.getApplicableCharges(requiredCHarges));
					// code adding for throwing validation when charge not found
					if (!CollectionUtils.isNotEmpty(chargeDetailDTO)) {
						this.getModel().addValidationError(
								ApplicationSession.getInstance().getMessage("rts.validation.payment"));
						mv = new ModelAndView("rtsCheckListFormValdin", MainetConstants.FORM_NAME, getModel());
						mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return mv;
					}
					model.setIsFree(MainetConstants.NewWaterServiceConstants.NO);
					model.getReqDTO().setFree(false);
					// model.setChargesInfo(chargeDetailDTO);
					model.setCharges((chargesToPay(chargeDetailDTO)));
					setChargeMap(model, chargeDetailDTO);
					model.getOfflineDTO().setAmountToShow(model.getCharges());
					RtsServiceFormModel rtsModel = ApplicationContextProvider.getApplicationContext().getBean(RtsServiceFormModel.class);
					 rtsModel.getOfflineDTO().setAmountToShow(model.getCharges());
				} else {
					// model.setFree(MainetConstants.NewWaterServiceConstants.YES);
					model.getReqDTO().setFree(true);
					// model.getReqDTO().setCharges(0.0d);
				}
			} else {
				this.getModel()
						.addValidationError(ApplicationSession.getInstance().getMessage("rts.validation.payment"));
				mv = new ModelAndView("rtsCheckListFormValdin", MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}
		}
		// [END]
		else {
			logger.error("Exception found in initializing Charges and Checklist: ");
			mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
		}
		// } else {mv = new ModelAndView("NewWaterConnectionFormValidn",
		// MainetConstants.FORM_NAME, getModel());}
		

		mv = new ModelAndView("rtsCheckListForm", MainetConstants.FORM_NAME, getModel());

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private WaterRateMaster populateChargeModel(WaterRateMaster waterRateMaster) {
		waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		waterRateMaster.setServiceCode(MainetConstants.RightToService.DCS);
		waterRateMaster.setDeptCode(MainetConstants.RightToService.RTS_DEPT_CODE);
		return waterRateMaster;
	}

	private double chargesToPay(final List<MediaChargeAmountDTO> charges) {
		double amountSum = 0.0;
		for (final MediaChargeAmountDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setChargeMap(final DrainageConnectionModel model, final List<MediaChargeAmountDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : charges) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	private void setApplicantDetail(final Employee emp, final DrainageConnectionModel model) {
		model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
		model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpMName());
		model.getApplicantDetailDto().setApplicantLastName(emp.getEmpLName());
		model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
		model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
		model.getApplicantDetailDto().setApplicantTitle(emp.getTitle());
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((emp.getEmpGender() != null) && !emp.getEmpGender().isEmpty()) {
				if (lookUp.getLookUpCode().equals(emp.getEmpGender())) {
					model.getApplicantDetailDto().setGender(String.valueOf(lookUp.getLookUpId()));
					break;
				}
			}
		}
		// model.setGender(model.getApplicantDetailDto().getGender());
		if (emp.getPincode() != null) {
			model.getApplicantDetailDto().setPinCode(emp.getPincode());
		}
	}

	private void populateCheckListModel(final DrainageConnectionModel model, final CheckListModel checklistModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		checklistModel.setOrgId(org.getOrgid());
		checklistModel.setServiceCode(MainetConstants.RightToService.DCS);
	}

	private WaterRateMaster populateChargeModel(final DrainageConnectionModel model,
			final WaterRateMaster chargeModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		chargeModel.setOrgId(org.getOrgid());
		chargeModel.setServiceCode(MainetConstants.RightToService.DCS);
		chargeModel.setDeptCode(MainetConstants.RightToService.RTS_DEPT_CODE);
		chargeModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		chargeModel.setRateStartDate(new Date().getTime());

		return chargeModel;
	}

	private List<DocumentDetailsVO> prepareFileUpload(List<DocumentDetailsVO> document) {

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
		if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
			long count = 200;
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

		return document;
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

	@RequestMapping(params = "saveRts", method = RequestMethod.POST)
	public ModelAndView saveCheckListAppdetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		JsonViewObject respObj;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long langId = (long) UserSession.getCurrent().getLanguageId();
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		DrainageConnectionModel appModel = this.getModel();
		DrainageConnectionDto dto = appModel.getDrainageConnectionDto();
//	
		// this call is for checklist validation
		List<DocumentDetailsVO> docs = appModel.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		dto.setDocumentList(docs);
		fileUpload.validateUpload(appModel.getBindingResult());

		// appModel.validateBean(appModel, DrainageConnectionFormValidator.class);

		// this call is for checklist validation end
		if (appModel.hasValidationErrors()) {
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, appModel.getBindingResult());
			return new ModelAndView("rtsCheckListForm", MainetConstants.FORM_NAME, appModel); // change
																								// here
		}
		/*
		 * List<Object[]> list = appModel.getServiceList().parallelStream().filter(s ->
		 * s[0].equals(dto.getSelectSchemeName())) .collect(Collectors.toList());
		 * list.parallelStream().forEach(l -> { dto.setServiceCode(l[2].toString()); });
		 * appModel.saveForm()
		 */
		dto.setOrgId(orgId);
		dto.setCreatedBy(empId);
		dto.setCreatedDate(new Date());
		dto.setLgIpMac(ipMacAddress);
		dto.setLangId(langId);
		// dto.setUlbName(ulbName);
		if (appModel.saveForm()) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage(appModel.getSuccessMessage()));

		} else {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success"));

		}
		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		getModel().bind(httpServletRequest);
		RtsServiceFormModel model = new RtsServiceFormModel();
		model.setDrainageConnectionDto(this.getModel().getDrainageConnectionDto());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		try {
			Map<String, String> serviceMap = rtsService
					.fetchRtsService(UserSession.getCurrent().getOrganisation().getOrgid());
			model.setServiceMap(serviceMap);
			Map<Long, String> wardMap = wardMap = rtsService
					.fetchWardZone(UserSession.getCurrent().getOrganisation().getOrgid());
			model.setWardList(wardMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);

		RequestDTO reqDto = rtsService.fetchRtsApplicationInformationById(appId, orgId);
		model.setReqDTO(reqDto);
		model.getReqDTO().setServiceShortCode(MainetConstants.RightToService.DCS);

		mv = new ModelAndView("rtsApplicantForm", MainetConstants.FORM_NAME, model);

		return mv;
	}
	/* For showing acknowledgment page in case of free service */

	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		DrainageConnectionModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {

			List<DocumentDetailsVO> checkListList1 = drainageConnectionService.fetchDrainageConnectionDocsByAppNo(
					model.getDrainageConnectionDto().getApmApplicationId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (checkListList1 != null && !checkListList1.isEmpty()) {
				model.setDocumentList(checkListList1);
			}

		}

		String name = " ";
		if (model.getApplicantDetailDto().getApplicantFirstName() != null) {
			name = model.getApplicantDetailDto().getApplicantFirstName() + " ";
		}
		if (model.getApplicantDetailDto().getApplicantMiddleName() != null) {
			name += model.getApplicantDetailDto().getApplicantMiddleName() + " ";
		}
		if (model.getApplicantDetailDto().getApplicantLastName() != null) {
			name += model.getApplicantDetailDto().getApplicantLastName();
		}
		model.getApplicantDetailDto().setApplicantFirstName(name);
		model.setApmApplicationId(model.getDrainageConnectionDto().getApmApplicationId());

		// model.setApplicantName(model.getDrainageConnectionDto().getReqDTO().getfName());
		model.setServiceName(MainetConstants.RightToService.APPLY_DRAINAGE_CONNECTION);
		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);

	}

}
