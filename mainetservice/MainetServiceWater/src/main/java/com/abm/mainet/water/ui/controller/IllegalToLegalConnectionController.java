/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbWorkOrderService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.IllegalToLegalConnectionModel;
import com.abm.mainet.water.utility.WaterCommonUtility;

/**
 * @author Saiprasad.Vengurekar
 *
 */
@Controller
@RequestMapping("/IllegalToLegalConnection.html")
public class IllegalToLegalConnectionController extends AbstractFormController<IllegalToLegalConnectionModel> {

	@Autowired
	NewWaterConnectionService waterService;

	@Autowired
	ServiceMasterService serviceMaster;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	WaterCommonService waterCommonService;

	@Resource
	private NewWaterConnectionService waterConnectionService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private BRMSWaterService brmsWaterService;

	@Autowired
	TbWorkOrderService workOrderService;

	@Autowired
	DesignationService designationService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	BillMasterService billMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setCommonHelpDocs("IllegalToLegalConnection.html");
		final IllegalToLegalConnectionModel model = getModel();
		setCommonFields(model);
		ModelAndView mv = null;
		mv = new ModelAndView("IllegalToLegalConnection", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	/**
	 * @param model
	 */
	private void setCommonFields(final IllegalToLegalConnectionModel model) {
		final TbCsmrInfoDTO dto = model.getCsmrInfo();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		dto.setFromIllegal(MainetConstants.FlagY);
		final ServiceMaster service = serviceMaster.getServiceByShortName("WIL", orgId);
		model.setServiceMaster(service);
		final Long deptId = service.getTbDepartment().getDpDeptid();
		model.setDeptId(deptId);
		if (service != null) {
			model.setServiceId(service.getSmServiceId());
			model.getReqDTO().setServiceId(service.getSmServiceId());
		}
		model.setServiceName(service.getSmServiceName());
		model.setPlumberList(waterCommonService.listofplumber(orgId));
		Designation dsg = designationService.findByShortname("PLM");
		List<PlumberMaster> plumberList = new ArrayList<>();
		PlumberMaster master = null;
		if (dsg != null) {

			List<Object[]> empList = employeeService.getAllEmpByDesignation(dsg.getDsgid(), orgId);
			if (!empList.isEmpty()) {
				for (final Object empObj[] : empList) {
					master = new PlumberMaster();
					master.setPlumId(Long.valueOf(empObj[0].toString()));
					master.setPlumFname(empObj[1].toString());
					master.setPlumMname(empObj[2].toString());
					master.setPlumLname(empObj[3].toString());
					plumberList.add(master);
				}

			}
		}
		model.setUlbPlumberList(plumberList);
	}

	@Override
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		if (getModel().getCsmrInfo().getOwnerList() != null) {
			getModel().getCsmrInfo().getOwnerList().clear();
		}
		if (getModel().getCsmrInfo().getLinkDetails() != null) {
			getModel().getCsmrInfo().getLinkDetails().clear();
		}
		bindModel(httpServletRequest);
		if (getModel().saveForm()) {
			return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("water.save")));
		}
		return defaultMyResult();
	}

	@RequestMapping(params = "ShowViewForm", method = RequestMethod.POST)
	public ModelAndView ShowViewForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();
		ModelAndView mv = null;
		if (!MainetConstants.FlagY.equals(model.getScrutinyFlag())) {
			List<DocumentDetailsVO> docs = model.getCheckList();
			docs = fileUpload.prepareFileUpload(docs);
			model.setCheckListForPreview(fileUpload.getUploadedDocForPreview(docs));
			final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
			reqDTO.setDocumentList(docs);
			reqDTO.setCsmrInfo(model.getCsmrInfo());
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());
			setRequestApplicantDetails(model);
			setUpdateFields(model.getCsmrInfo());
			getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
			setCsmrInfoApplicantDetails(reqDTO);
		}
		if (model.validateInputs()) {
			if (MainetConstants.FlagY.equals(model.getScrutinyFlag())) {
				model.saveForm();
			}
			mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());
		} else {
			mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = "deletedLinkCCnRow", method = RequestMethod.POST)
	public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRow") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		TbKLinkCcnDTO detDto = this.getModel().getCsmrInfo().getLinkDetails().get(deletedRowCount);
		if (detDto != null) {
			detDto.setIsDeleted("Y");
		}
	}

	@RequestMapping(params = "EditApplicationForm", method = RequestMethod.POST)
	public ModelAndView editApplicationForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		IllegalToLegalConnectionModel model = this.getModel();
		model.bind(httpServletRequest);
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		model.getReqDTO().setPropertyNo(model.getCsmrInfo().getPropertyNo());
		model.setPlumberList(waterCommonService.listofplumber(UserSession.getCurrent().getOrganisation().getOrgid()));
		return defaultMyResult();
	}

	@RequestMapping(params = "SaveAndViewApplication", method = RequestMethod.POST)
	public ModelAndView saveViewApplication(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());
		final IllegalToLegalConnectionModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		Organisation org = UserSession.getCurrent().getOrganisation();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();

			outPutObject = waterService.saveIllegalToLegalConnectionApplication(reqDTO);
			if (outPutObject != null && (outPutObject.getStatus() != null)
					&& outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {

				if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(outPutObject.getStatus())) { // free
					waterService.initiateWorkFlowForFreeService(reqDTO);
				}
				model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
				reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
				WaterCommonUtility.sendSMSandEMail(reqDTO.getApplicantDTO(), reqDTO.getApplicationId(),
						reqDTO.getPayAmount(), MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION,
						UserSession.getCurrent().getOrganisation());
				if ((model.getFree() != null) && model.getFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
					model.save();

				}
				long duration = 0;
				if (model.getServiceMaster().getSmServiceDuration() != null) {
					duration = model.getServiceMaster().getSmServiceDuration();
				}
				model.setServiceDuration(
						LocalDate.now().plusDays(duration).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				model.setDistrictDesc(
						CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdDis(), org).getLookUpDesc());
				model.setStateDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdState(), org)
						.getLookUpDesc());
				model.setTalukaDesc(
						CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdId(), org).getLookUpDesc());
				mv = new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME, getModel());
			} else {
				if (!outPutObject.getErrorList().isEmpty()) {
					for (final String msg : outPutObject.getErrorList()) {
						model.addValidationError(msg);
					}
				} else {
					return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
				}
			}
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setDocumentList(docs);
		reqDTO.setCsmrInfo(model.getCsmrInfo());
		reqDTO.setApplicantDTO(model.getApplicantDetailDto());
		setRequestApplicantDetails(model);
		setUpdateFields(model.getCsmrInfo());
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		setCsmrInfoApplicantDetails(reqDTO);
		if (model.validateInputs()) {

			NewWaterConnectionResponseDTO outPutObject = new NewWaterConnectionResponseDTO();

			// Display to console

			outPutObject = waterService.saveWaterApplication(reqDTO);

			try {
				if (outPutObject != null) {
					if ((outPutObject.getStatus() != null)
							&& outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {
						model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
						reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
						WaterCommonUtility.sendSMSandEMail(reqDTO.getApplicantDTO(), reqDTO.getApplicationId(),
								reqDTO.getPayAmount(), MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION,
								UserSession.getCurrent().getOrganisation());
						if ((model.getFree() != null)
								&& model.getFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
							if (model.save()) {
								final CommonChallanDTO offline = model.getOfflineDTO();
								if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck()
										.equals(PrefixConstants.NewWaterServiceConstants.NO)) {
									return jsonResult(JsonViewObject
											.successResult(getApplicationSession().getMessage("continue.forchallan")));
								} else {
									return jsonResult(JsonViewObject
											.successResult(getApplicationSession().getMessage("continue.forpayment")));
								}
							}
						} else {
							return jsonResult(
									JsonViewObject.successResult(getApplicationSession().getMessage("water.success"),
											new Object[] { outPutObject.getApplicationNo() }));
						}
					} else {
						if (!outPutObject.getErrorList().isEmpty()) {
							for (final String msg : outPutObject.getErrorList()) {
								model.addValidationError(msg);
							}
						} else {
							return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
						}
					}
				}

			} catch (final Exception exception) {
				logger.error("Exception found in save method: ", exception);
				return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

			}
		}
		mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	/**
	 * @param model
	 */
	private void setRequestApplicantDetails(final IllegalToLegalConnectionModel model) {
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final ApplicantDetailDTO appDTO = model.getApplicantDetailDto();
		reqDTO.setApplicationType(model.getCsmrInfo().getApplicantType());
		reqDTO.setmName(appDTO.getApplicantMiddleName());
		reqDTO.setfName(appDTO.getApplicantFirstName());
		reqDTO.setlName(appDTO.getApplicantLastName());
		reqDTO.setEmail(appDTO.getEmailId());
		reqDTO.setMobileNo(appDTO.getMobileNo());
		reqDTO.setBldgName(appDTO.getBuildingName());
		reqDTO.setRoadName(appDTO.getRoadName());
		reqDTO.setAreaName(appDTO.getAreaName());
		if ((appDTO.getPinCode() != null) && !appDTO.getPinCode().isEmpty()) {
			reqDTO.setPincodeNo(Long.valueOf(appDTO.getPinCode()));
		}
		reqDTO.setWing(appDTO.getWing());
		reqDTO.setBplNo(appDTO.getBplNo());
		reqDTO.setDeptId(model.getDeptId());
		reqDTO.setFloor(appDTO.getFloorNo());
		reqDTO.setWardNo(appDTO.getDwzid2());
		reqDTO.setZoneNo(appDTO.getDwzid1());
		reqDTO.setCityName(appDTO.getVillageTownSub());
		reqDTO.setBlockName(appDTO.getBlockName());
		reqDTO.setHouseComplexName(appDTO.getHousingComplexName());
		reqDTO.setFlatBuildingNo(appDTO.getFlatBuildingNo());
		if ((appDTO.getAadharNo() != null) && !appDTO.getAadharNo().equals(MainetConstants.BLANK)) {
			reqDTO.setUid(Long.valueOf(appDTO.getAadharNo()));
		}
		reqDTO.setGender(appDTO.getGender());
	}

	/**
	 * @param csmrInfo
	 */
	/**
	 * @param csmrInfo
	 */
	private void setUpdateFields(final TbCsmrInfoDTO csmrInfo) {
		final IllegalToLegalConnectionModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		final UserSession session = UserSession.getCurrent();
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
		final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
		reqDTO.setUserId(session.getEmployee().getEmpId());
		reqDTO.setLangId((long) session.getLanguageId());
		reqDTO.setOrgId(session.getOrganisation().getOrgid());
		reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		dto.setOrgId(reqDTO.getOrgId());
		dto.setLmodDate(new Date());
		model.getApplicantDetailDto().setOrgId(session.getOrganisation().getOrgid());
		dto.setUserId(session.getEmployee().getEmpId());
		dto.setLangId(session.getLanguageId());
		dto.setLgIpMac(reqDTO.getLgIpMac());
		if ((dto.getLinkDetails() != null) && !dto.getLinkDetails().isEmpty()) {
			for (final TbKLinkCcnDTO link : dto.getLinkDetails()) {
				link.setOrgIds(reqDTO.getOrgId());
				link.setUserIds(reqDTO.getUserId());
				link.setLangId(dto.getLangId());
				link.setLgIpMac(reqDTO.getLgIpMac());
				link.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
				tempLinkList.add(link);
			}
			dto.setLinkDetails(tempLinkList);
		} else {
			dto.setLinkDetails(tempLinkList);
		}
		if ((dto.getOwnerList() != null) && !dto.getOwnerList().isEmpty()) {
			for (final AdditionalOwnerInfoDTO owner : dto.getOwnerList()) {
				owner.setOrgid(reqDTO.getOrgId());
				owner.setUserId(reqDTO.getUserId());
				owner.setLangId(reqDTO.getLangId());
				owner.setLgIpMac(reqDTO.getLgIpMac());
				owner.setLmoddate(new Date());
				owner.setIsDeleted(PrefixConstants.NewWaterServiceConstants.NO);
				tempOwnerList.add(owner);
			}
			dto.setOwnerList(tempOwnerList);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		final IllegalToLegalConnectionModel model = getModel();
		setApplicantDetails();

		if (model.validateInputs()) {
			// [START] BRMS call initialize model
			final WSRequestDTO requestDTO = new WSRequestDTO();
			requestDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
				final List<Object> waterRateMasterList = this.castResponse(response, WaterRateMaster.class, 1);
				final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
				final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
				populateCheckListModel(model, checkListModel2);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(MainetConstants.SolidWasteManagement.CHECK_LIST_MODEL);
				checklistReqDto.setDataModel(checkListModel2);
				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
						|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
						List<DocumentDetailsVO> checkListList = Collections.emptyList();
						checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

						long cnt = 1;
						for (final DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
						if ((checkListList != null) && !checkListList.isEmpty()) {
							model.setCheckList(checkListList);
						}
						mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
					} else {
						final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
						reqDTO.setCsmrInfo(model.getCsmrInfo());
						reqDTO.setApplicantDTO(model.getApplicantDetailDto());
						setRequestApplicantDetails(model);
						setUpdateFields(model.getCsmrInfo());
						getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
						setCsmrInfoApplicantDetails(reqDTO);
						mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());
					}

					// checklist done
					final ServiceMaster service = serviceMaster.getServiceByShortName("WIL",
							UserSession.getCurrent().getOrganisation().getOrgid());
					if (StringUtils.contains(service.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
						final WSRequestDTO taxRequestDto = new WSRequestDTO();
						waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						waterRateMaster.setServiceCode("WIL");
						waterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
								.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
										MainetConstants.NewWaterServiceConstants.CAA,
										UserSession.getCurrent().getOrganisation())
								.getLookUpId()));
						taxRequestDto.setDataModel(waterRateMaster);
						WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);

						if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
							if (!res.isFree()) {
								final List<Object> rates = (List<Object>) res.getResponseObj();
								final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
								for (final Object rate : rates) {
									WaterRateMaster master1 = (WaterRateMaster) rate;
									master1 = populateChargeModel(model, master1);
									requiredCHarges.add(master1);
								}
								WSRequestDTO chargeReqDto = new WSRequestDTO();
								chargeReqDto.setModelName("WaterRateMaster");
								chargeReqDto.setDataModel(requiredCHarges);
								WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
								List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges
										.getResponseObj();

								model.setFree(PrefixConstants.NewWaterServiceConstants.NO);
								model.getReqDTO().setFree(false);
								model.setChargesInfo(detailDTOs);
								model.setCharges((chargesToPay(detailDTOs)));
								setChargeMap(model, detailDTOs);
								model.getOfflineDTO().setAmountToShow(model.getCharges());
							} else {
								model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
								model.getReqDTO().setFree(true);
								model.getReqDTO().setCharges(0.0d);
							}
						} else {
							mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
						}
					} else {
						model.setFree(PrefixConstants.NewWaterServiceConstants.YES);
						model.getReqDTO().setFree(true);
						model.getReqDTO().setCharges(0.0d);
					}
				} else {
					mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
				}
			}

			// [END]
			else {
				mv = new ModelAndView(PrefixConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
			// [END]
		} else {
			mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void populateCheckListModel(final IllegalToLegalConnectionModel model,
			final CheckListModel checklistModel) {
		checklistModel.setOrgId(model.getOrgId());
		checklistModel.setServiceCode("WIL");
		checklistModel.setIsBPL(model.getCsmrInfo().getBplFlag());

		final TbCsmrInfoDTO data = model.getCsmrInfo();
		final Organisation org = UserSession.getCurrent().getOrganisation();

		if (data.getTrmGroup1() != null) {
			checklistModel.setUsageSubtype1(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1(), org).getDescLangFirst());
		}
		if (data.getTrmGroup2() != null) {
			checklistModel.setUsageSubtype2(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2(), org).getDescLangFirst());
		}
		if (data.getTrmGroup3() != null) {
			checklistModel.setUsageSubtype3(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3(), org).getDescLangFirst());
		}
		if (data.getTrmGroup4() != null) {
			checklistModel.setUsageSubtype4(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4(), org).getDescLangFirst());
		}
		if (data.getTrmGroup5() != null) {
			checklistModel.setUsageSubtype5(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5(), org).getDescLangFirst());
		}
		if ((model.getReqDTO() != null) && (model.getReqDTO().getExistingConsumerNumber() == null)) {
			checklistModel.setIsExistingConnectionOrConsumerNo(PrefixConstants.NewWaterServiceConstants.NO);

		} else {
			checklistModel.setIsExistingConnectionOrConsumerNo(PrefixConstants.NewWaterServiceConstants.YES);

		}
		if ((model.getReqDTO() != null) && (model.getReqDTO().getPropertyNo() == null)) {
			checklistModel.setIsExistingProperty(PrefixConstants.NewWaterServiceConstants.NO);
		} else {
			checklistModel.setIsExistingProperty(PrefixConstants.NewWaterServiceConstants.YES);
		}

	}

	private WaterRateMaster populateChargeModel(final IllegalToLegalConnectionModel model,
			final WaterRateMaster chargeModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		chargeModel.setOrgId(model.getOrgId());
		chargeModel.setServiceCode("WIL");
		chargeModel.setIsBPL(model.getCsmrInfo().getBplFlag());
		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		final TbCsmrInfoDTO data = model.getCsmrInfo();

		if (data.getTrmGroup1() != null) {
			chargeModel.setUsageSubtype1(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1(), org).getDescLangFirst());
		}
		if (data.getTrmGroup2() != null) {
			chargeModel.setUsageSubtype2(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2(), org).getDescLangFirst());
		}
		if (data.getTrmGroup3() != null) {
			chargeModel.setUsageSubtype3(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3(), org).getDescLangFirst());
		}
		if (data.getTrmGroup4() != null) {
			chargeModel.setUsageSubtype4(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4(), org).getDescLangFirst());
		}
		if (data.getTrmGroup5() != null) {
			chargeModel.setUsageSubtype5(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5(), org).getDescLangFirst());
		}

		if (data.getCsCcnsize() != null) {
			chargeModel.setConnectionSize(Double.valueOf(
					CommonMasterUtility.getNonHierarchicalLookUpObject(data.getCsCcnsize(), org).getDescLangFirst()));
		}
		if (data.getNoOfFamilies() != null) {
			chargeModel.setNoOfFamilies(data.getNoOfFamilies().intValue());
		}
		if (data.getCsTaxPayerFlag() == null || data.getCsTaxPayerFlag().isEmpty()) {
			chargeModel.setTaxPayer(MainetConstants.FlagN);
		} else {
			chargeModel.setTaxPayer(data.getCsTaxPayerFlag());
		}

		return chargeModel;

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setCsmrInfoApplicantDetails(final NewWaterConnectionReqDTO reqDTO) {
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final ApplicantDetailDTO applicantDTO = reqDTO.getApplicantDTO();
		reqDTO.setfName(applicantDTO.getApplicantFirstName());

		if (reqDTO.getIsConsumer() != null && !reqDTO.getIsConsumer().isEmpty()) {
			setConsumerDetails(applicantDTO, dto);
		}
		if (reqDTO.getIsBillingAddressSame() != null && !reqDTO.getIsBillingAddressSame().isEmpty()) {
			setBillingDetails(dto);
		}

		reqDTO.setTitleId(applicantDTO.getApplicantTitle());
		if ((applicantDTO.getAadharNo() != null) && !applicantDTO.getAadharNo().isEmpty()) {
			dto.setCsUid(Long.valueOf(applicantDTO.getAadharNo()));
		}
		dto.setCsApldate(new Date());
		if (dto.getBplFlag().equals(MainetConstants.FlagY))
			applicantDTO.setBplNo(dto.getBplNo());

		if (!CollectionUtils.isEmpty(reqDTO.getCsmrInfo().getOwnerList())) {
			if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(MainetConstants.ZERO)) {
				reqDTO.getCsmrInfo().setOwnerList(null);
			}
		}
		if (!CollectionUtils.isEmpty(reqDTO.getCsmrInfo().getLinkDetails())) {
			if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
				reqDTO.getCsmrInfo().setLinkDetails(null);
			}
		}
	}

	private void setConsumerDetails(ApplicantDetailDTO applicantDTO, TbCsmrInfoDTO tbCsmrInfoDTO) {
		tbCsmrInfoDTO.setCsName(applicantDTO.getApplicantFirstName());
		tbCsmrInfoDTO.setCsMname(applicantDTO.getApplicantMiddleName());
		tbCsmrInfoDTO.setCsLname(applicantDTO.getApplicantLastName());
		tbCsmrInfoDTO.setCsAdd(applicantDTO.getAreaName());
		tbCsmrInfoDTO.setCsBldplt(applicantDTO.getBuildingName());
		tbCsmrInfoDTO.setCsLanear(applicantDTO.getFlatBuildingNo());
		tbCsmrInfoDTO.setCsRdcross(applicantDTO.getRoadName());
		tbCsmrInfoDTO.setCsCcityName(applicantDTO.getVillageTownSub());
		if (applicantDTO.getPinCode() != null && !applicantDTO.getPinCode().isEmpty())
			tbCsmrInfoDTO.setCsCpinCode(Long.valueOf(applicantDTO.getPinCode()));
	}

	private void setBillingDetails(final TbCsmrInfoDTO tbCsmrInfoDTO) {
		tbCsmrInfoDTO.setCsBadd(tbCsmrInfoDTO.getCsAdd());
		tbCsmrInfoDTO.setCsBbldplt(tbCsmrInfoDTO.getCsBldplt());
		tbCsmrInfoDTO.setCsBlanear(tbCsmrInfoDTO.getCsLanear());
		tbCsmrInfoDTO.setCsBrdcross(tbCsmrInfoDTO.getCsRdcross());
		tbCsmrInfoDTO.setCsBcityName(tbCsmrInfoDTO.getCsCcityName());
		tbCsmrInfoDTO.setBpincode(tbCsmrInfoDTO.getCsCpinCode().toString());
	}

	private void setChargeMap(final IllegalToLegalConnectionModel model, final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				for (final Object object : list) {
					responseMap = (LinkedHashMap<Long, Object>) object;
					final String jsonString = new JSONObject(responseMap).toString();
					dataModel = new ObjectMapper().readValue(jsonString, clazz);
					dataModelList.add(dataModel);
				}
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@RequestMapping(params = "clearCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView clearCheckListAndChrages(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();
		model.setFree(PrefixConstants.SMS_EMAIL.OTP_MSG);
		model.setChargesInfo(null);
		model.setCharges(0.0d);
		model.getChargesMap().clear();
		model.getOfflineDTO().setAmountToShow(0.0d);
		model.getReqDTO().setFree(true);
		model.getReqDTO().setCharges(0.0d);
		model.getCheckList().clear();
		model.getReqDTO().getDocumentList().clear();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		fileUpload.sessionCleanUpForFileUpload();
		final ModelAndView mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME,
				getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getPropertyDetails(HttpServletRequest request) {
		bindModel(request);
		IllegalToLegalConnectionModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		TbCsmrInfoDTO propInfoDTO = model.getPropertyDetailsByPropertyNumber(reqDTO);
		String respMsg = "";
		if (propInfoDTO != null) {
			infoDTO.setCsOname(propInfoDTO.getCsOname());
			infoDTO.setCsOcontactno(propInfoDTO.getCsOcontactno());
			infoDTO.setCsOEmail(propInfoDTO.getCsOEmail());
			infoDTO.setOpincode(propInfoDTO.getOpincode());
			infoDTO.setCsOadd(propInfoDTO.getCsOadd());
			infoDTO.setPropertyUsageType(propInfoDTO.getPropertyUsageType());
			if (propInfoDTO.getCsOGender() != null && propInfoDTO.getCsOGender() != 0l) {
				infoDTO.setCsOGender(propInfoDTO.getCsOGender());
			}
			reqDTO.setApplicantDTO(new ApplicantDetailDTO());
			infoDTO.setPropertyNo(propInfoDTO.getPropertyNo());
			infoDTO.setTotalOutsatandingAmt(propInfoDTO.getTotalOutsatandingAmt());
			if (infoDTO.getTotalOutsatandingAmt() > 0) {
				model.setPropOutStanding(MainetConstants.FlagY);
			} else {
				model.setPropOutStanding(MainetConstants.FlagN);
			}
		} else {
			respMsg = ApplicationSession.getInstance().getMessage("water.dataentry.validation.property.not.found");
			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		}
		return defaultMyResult();
	}

	/**
	 * Get Connection Details
	 * 
	 * @param connectionNo
	 * @return TbCsmrInfoDTO
	 */
	@ResponseBody
	@RequestMapping(params = "getConnectionDetails", method = RequestMethod.POST)
	public TbCsmrInfoDTO getConnectionDetails(@RequestParam("ccnNo") String ccnNo) {
		try {
			TbCsmrInfoDTO infoDto = waterCommonService.fetchConnectionDetailsByConnNo(ccnNo,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
			List<TbBillMas> bill = billMasterService.getBillMasterListByUniqueIdentifier(infoDto.getCsIdn(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (!bill.isEmpty()) {
				infoDto.setCcnOutStandingAmt(Double.toString(bill.get(bill.size() - 1).getBmTotalOutstanding()));
			} else {
				infoDto.setCcnOutStandingAmt("0");
			}
			return infoDto;
		} catch (Exception e) {
			return new TbCsmrInfoDTO();
		}

	}

	private void setApplicantDetails() {
		IllegalToLegalConnectionModel model = this.getModel();
		UserSession session = UserSession.getCurrent();
		NewWaterConnectionReqDTO connectionDTO = model.getReqDTO();
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		ApplicantDetailDTO appDTO = connectionDTO.getApplicantDTO();
		Organisation organisation = new Organisation();
		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		connectionDTO.setUserId(session.getEmployee().getEmpId());
		connectionDTO.setOrgId(session.getOrganisation().getOrgid());
		connectionDTO.setLangId((long) session.getLanguageId());
		connectionDTO.setLgIpMac(session.getEmployee().getEmppiservername());

		if (model.getIsConsumerSame().equalsIgnoreCase(MainetConstants.FlagY)) {
			connectionDTO.setIsConsumer(MainetConstants.FlagY);
			infoDTO.setCsName(infoDTO.getCsOname());
			if (infoDTO.getCsOGender() != null && infoDTO.getCsOGender() != 0l) {
				infoDTO.setCsGender(infoDTO.getCsOGender());
			}
			infoDTO.setCsContactno(infoDTO.getCsOcontactno());
			infoDTO.setCsEmail(infoDTO.getCsOEmail());
			infoDTO.setCsAdd(infoDTO.getCsOadd());
			if (infoDTO.getOpincode() != null) {
				infoDTO.setCsCpinCode(Long.valueOf(infoDTO.getOpincode()));
			}
		}
		if (model.getIsBillingSame().equalsIgnoreCase(MainetConstants.FlagY)) {
			connectionDTO.setIsBillingAddressSame(MainetConstants.FlagY);
			infoDTO.setCsBadd(infoDTO.getCsAdd());
			if (infoDTO.getCsCpinCode() != null) {
				infoDTO.setBpincode(infoDTO.getCsCpinCode().toString());
			}
		}

		connectionDTO.setCityName(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		connectionDTO.setRoadName(infoDTO.getCsAdd());
		if (infoDTO.getCsCpinCode() != null) {
			connectionDTO.setPinCode(infoDTO.getCsCpinCode());
			connectionDTO.setPincodeNo(infoDTO.getCsCpinCode());
		}
		connectionDTO.setAreaName(infoDTO.getCsAdd());
		connectionDTO.setBldgName(infoDTO.getCsAdd());
		connectionDTO.setBlockName(infoDTO.getCsAdd());
		//connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsAdd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		appDTO.setIsBPL(infoDTO.getBplFlag());
		if (infoDTO.getBplFlag().equals(MainetConstants.FlagY))
			appDTO.setBplNo(infoDTO.getBplNo());
		// Need To Check
		appDTO.setAadharNo(connectionDTO.getApplicantDTO().getAadharNo());
		if (infoDTO.getCsCpinCode() != null) {
			appDTO.setPinCode(infoDTO.getCsCpinCode().toString());
		}
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
		for (final LookUp lookUp : lookUps) {
			if ((infoDTO.getCsOGender() != null) && infoDTO.getCsOGender() != 0l) {
				if (lookUp.getLookUpId() == infoDTO.getCsOGender()) {
					appDTO.setGender(lookUp.getLookUpCode());
					break;
				}
			}

		}
		appDTO.setAreaName(infoDTO.getCsAdd());
		model.setApplicantDetailDto(appDTO);
		connectionDTO.setCsmrInfo(infoDTO);

	}

	@RequestMapping(params = "fetchConnectionByIllegalNoticeNo", method = RequestMethod.POST)
	public ModelAndView fetchConnectionByIllegalNoticeNo(HttpServletRequest request) {
		bindModel(request);
		IllegalToLegalConnectionModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		infoDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		TbCsmrInfoDTO propInfoDTO = waterService.fetchConnectionByIllegalNoticeNo(infoDTO);
		String respMsg = "";
		if (propInfoDTO.getCsIdn() == 0) {
			respMsg = ApplicationSession.getInstance().getMessage("No Records Found ");
			return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);
		} else {
			model.setCsmrInfo(propInfoDTO);
			reqDTO.setPropertyNo(propInfoDTO.getPropertyNo());
		}
		return defaultMyResult();
	}

}
