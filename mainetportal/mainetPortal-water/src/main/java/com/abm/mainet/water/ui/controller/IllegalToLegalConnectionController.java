/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.ui.model.IllegalToLegalConnectionModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Controller
@RequestMapping("/IllegalToLegalConnection.html")
public class IllegalToLegalConnectionController extends AbstractFormController<IllegalToLegalConnectionModel> {

	@Autowired
	IPortalServiceMasterService iPortalService;

	@Autowired
	private IWaterBRMSService checklistService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	private INewWaterConnectionService iNewWaterConnectionService;

	@Autowired
	private IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setCommonHelpDocs("IllegalToLegalConnection.html");
		final IllegalToLegalConnectionModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		setCommonFields(model, reqDTO);
		ModelAndView mv = null;
		mv = new ModelAndView("IllegalToLegalConnection", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	private void setCommonFields(final IllegalToLegalConnectionModel model, final NewWaterConnectionReqDTO reqDTO) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		reqDTO.setOrgId(orgId);
		reqDTO.getCsmrInfo().setOrgId(orgId);
		model.setOrgId(orgId);
		final Long serviceId = iPortalService.getServiceId("WIL", orgId);
		PortalService service = iPortalService.getPortalService(serviceId);
		model.setServiceMaster(service);
		model.setServiceName(service.getServiceName());
		reqDTO.setServiceId(serviceId);
		model.setServiceId(serviceId);
		model.setDeptId(service.getPsmDpDeptid());
		reqDTO.setDeptId(service.getPsmDpDeptid());
		model.setPlumberList(iNewWaterConnectionService.getListofplumber(orgId));
		final Long langId = (long) UserSession.getCurrent().getLanguageId();
		model.setLangId(langId);
	}

	@RequestMapping(params = "fetchConnectionByIllegalNoticeNo", method = RequestMethod.POST)
	public ModelAndView fetchConnectionByIllegalNoticeNo(HttpServletRequest request) {
		bindModel(request);
		IllegalToLegalConnectionModel model = this.getModel();
		NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
		infoDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		TbCsmrInfoDTO propInfoDTO = iNewWaterConnectionService.fetchConnectionByIllegalNoticeNo(infoDTO);
		String respMsg = "";
		if (propInfoDTO.getCsIdn() == 0) {
			respMsg = ApplicationSession.getInstance().getMessage("No Records Found ");
			return new ModelAndView(new MappingJackson2JsonView(), "errMsg", respMsg);
		} else {
			model.setCsmrInfo(propInfoDTO);
			reqDTO.setPropertyNo(propInfoDTO.getPropertyNo());
		}
		return defaultMyResult();
	}

	@RequestMapping(params = "ShowViewForm", method = RequestMethod.POST)
	public ModelAndView ShowViewForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.convertFileToByteString(docs);
		model.setCheckListForPreview(fileUpload.getUploadedDocForPreview(docs));
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setDocumentList(docs);
		reqDTO.setCsmrInfo(model.getCsmrInfo());
		reqDTO.setApplicantDTO(model.getApplicantDetailDto());
		setRequestApplicantDetails(model);
		setUpdateFields(reqDTO);
		getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		setCsmrInfoApplicantDetails(reqDTO);
		if (model.validateInputs()) {
			mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());
		} else {
			mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = "EditApplicationForm", method = RequestMethod.POST)
	public ModelAndView editApplicationForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		return defaultMyResult();
	}

	@RequestMapping(params = "SaveAndViewApplication", method = RequestMethod.POST)
	public ModelAndView SaveAndViewApplication(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());
		final IllegalToLegalConnectionModel model = getModel();
		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());
			reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
			final NewWaterConnectionResponseDTO outPutObject = iNewWaterConnectionService
					.saveIllegalToLegalConnectionApplication(reqDTO);
			if (outPutObject != null) {
				if ((outPutObject.getStatus() != null)
						&& outPutObject.getStatus().equals(MainetConstants.NewWaterServiceConstants.SUCCESS)) {
					model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
					final ApplicationPortalMaster applicationMaster = saveApplcationMaster(reqDTO.getServiceId(),
							outPutObject.getApplicationNo(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					iPortalService.saveApplicationMaster(applicationMaster, model.getCharges(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					if ((model.getFree() != null)
							&& model.getFree().equals(MainetConstants.NewWaterServiceConstants.NO)) {
						model.saveForm();
					}
					long duration = 0;
					if (model.getServiceMaster().getSlaDays() != null) {
						duration = model.getServiceMaster().getSlaDays();
					}
					model.setServiceDuration(
							LocalDate.now().plusDays(duration).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					model.setDistrictDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdDis(), org)
							.getLookUpDesc());
					model.setStateDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdIdState(), org)
							.getLookUpDesc());
					model.setTalukaDesc(
							CommonMasterUtility.getNonHierarchicalLookUpObject(org.getOrgCpdId(), org).getLookUpDesc());
					mv = new ModelAndView("NewWaterConnectionApplicationFormPrint", MainetConstants.FORM_NAME,
							getModel());
				} else {
					if (!outPutObject.getErrorList().isEmpty()) {
						for (final String msg : outPutObject.getErrorList()) {
							model.addValidationError(msg);
						}
					} else {
						return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
					}
				}
			} else {
				return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		ModelAndView mv = null;
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();
		setApplicantDetails();
		if (model.validateInputs()) {
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
					mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
				} else {

					final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
					reqDTO.setCsmrInfo(model.getCsmrInfo());
					reqDTO.setApplicantDTO(model.getApplicantDetailDto());
					setRequestApplicantDetails(model);
					setUpdateFields(reqDTO);
					getModel().setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
					setCsmrInfoApplicantDetails(reqDTO);
					mv = new ModelAndView("IllegalToLegalFormView", MainetConstants.FORM_NAME, getModel());

				}
				// checklist done
				// Charges Start
				final WSResponseDTO res = checklistService.getApplicableTaxes(WaterRateMaster,
						UserSession.getCurrent().getOrganisation().getOrgid(), "WIL");
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
					if (!res.isFree()) {
						final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							WaterRateMaster master1 = (WaterRateMaster) rate;
							master1 = populateChargeModel(model, master1);
							requiredCHarges.add(master1);
						}
						final List<ChargeDetailDTO> detailDTOs = checklistService.getApplicableCharges(requiredCHarges);
						model.setFree(MainetConstants.NewWaterServiceConstants.NO);
						model.getReqDTO().setFree(false);
						model.setChargesInfo(detailDTOs);
						model.setCharges((chargesToPay(detailDTOs)));
						setChargeMap(model, detailDTOs);
						model.getOfflineDTO().setAmountToShow(model.getCharges());
					} else {
						model.setFree(MainetConstants.NewWaterServiceConstants.YES);
						model.getReqDTO().setFree(true);
						model.getReqDTO().setCharges(0.0d);
					}
				} else {
					mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
				}
			}
			// [END]
			else {
				logger.error("Exception found in initializing Charges and Checklist: ");
				mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);
			}
		} else {
			mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void setChargeMap(final IllegalToLegalConnectionModel model, final List<ChargeDetailDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final ChargeDetailDTO dto : charges) {
			chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
		}
		model.setChargesMap(chargesMap);
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();

		final NewWaterConnectionReqDTO reqDTO = model.getReqDTO();
		reqDTO.setPayMode(model.getOfflineDTO().getOnlineOfflineCheck());
		reqDTO.setCharges(model.getCharges());
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = fileUpload.convertFileToByteString(docs);
		reqDTO.setDocumentList(docs);

		model.setSaveMode(MainetConstants.NewWaterServiceConstants.YES);
		if (model.validateInputs()) {
			setRequestApplicantDetails(model);
			reqDTO.setCsmrInfo(model.getCsmrInfo());
			reqDTO.getCsmrInfo().setLinkDetails(reqDTO.getLinkDetails());
			setCsmrInfoApplicantDetails(reqDTO);
			if (reqDTO.getCsmrInfo().getOwnerList() != null) {
				if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(MainetConstants.BLANK)) {
					reqDTO.getCsmrInfo().setOwnerList(null);
				}
			}
			if (reqDTO.getCsmrInfo().getLinkDetails() != null) {
				if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
					reqDTO.getCsmrInfo().setLinkDetails(null);
				}
			}
			setUpdateFields(reqDTO);
			reqDTO.setApplicantDTO(model.getApplicantDetailDto());

			reqDTO.getApplicantDTO().setLangId(UserSession.getCurrent().getLanguageId());
			final NewWaterConnectionResponseDTO outPutObject = iNewWaterConnectionService
					.saveOrUpdateNewConnection(reqDTO);
			if (outPutObject != null) {
				if ((outPutObject.getStatus() != null)
						&& outPutObject.getStatus().equals(MainetConstants.NewWaterServiceConstants.SUCCESS)) {
					model.getResponseDTO().setApplicationNo(outPutObject.getApplicationNo());
					final ApplicationPortalMaster applicationMaster = saveApplcationMaster(reqDTO.getServiceId(),
							outPutObject.getApplicationNo(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					iPortalService.saveApplicationMaster(applicationMaster, model.getCharges(),
							FileUploadUtility.getCurrent().getFileMap().entrySet().size());
					if ((model.getFree() != null)
							&& model.getFree().equals(MainetConstants.NewWaterServiceConstants.NO)) {
						if (model.saveForm()) {
							final CommonChallanDTO offline = model.getOfflineDTO();
							if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck()
									.equals(MainetConstants.NewWaterServiceConstants.NO)) {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("continue.forchallan")));
							} else {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("continue.forpayment")));
							}
						}
					} else {

						return jsonResult(
								JsonViewObject.successResult(getApplicationSession().getMessage("water.free.msg1") + " "
										+ outPutObject.getApplicationNo() + MainetConstants.WHITE_SPACE
										+ getApplicationSession().getMessage("water.free.msg2")));
					}
				} else {
					if (!outPutObject.getErrorList().isEmpty()) {
						for (final String msg : outPutObject.getErrorList()) {
							model.addValidationError(msg);
						}
					} else {
						return mv = new ModelAndView(MainetConstants.NewWaterServiceConstants.DEFAULT_EXCEPTION_VIEW);

					}

				}
			}
		}
		mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

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
		if (reqDTO.getCsmrInfo().getOwnerList() != null) {
			if (reqDTO.getCsmrInfo().getOwnerList().get(0).getOwnerTitle().equals(0)) {
				reqDTO.getCsmrInfo().setOwnerList(null);
			}
		}
		if (reqDTO.getCsmrInfo().getLinkDetails() != null) {
			if (reqDTO.getCsmrInfo().getLinkDetails().get(0).getLcOldccn().equals(MainetConstants.BLANK)) {
				reqDTO.getCsmrInfo().setLinkDetails(null);
			}
		}

	}

	private void setUpdateFields(final NewWaterConnectionReqDTO reqDTO) {
		final UserSession session = UserSession.getCurrent();
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final List<TbKLinkCcnDTO> tempLinkList = new ArrayList<>();
		final List<AdditionalOwnerInfoDTO> tempOwnerList = new ArrayList<>();
		reqDTO.setUserId(session.getEmployee().getEmpId());
		reqDTO.setLangId((long) session.getLanguageId());
		reqDTO.setUpdatedBy(session.getEmployee().getEmpId());
		reqDTO.setOrgId(session.getOrganisation().getOrgid());
		reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		dto.setOrgId(reqDTO.getOrgId());
		dto.setUserId(session.getEmployee().getEmpId());
		dto.setLangId(session.getLanguageId());
		dto.setLgIpMac(reqDTO.getLgIpMac());
		dto.setUpdatedDate(new Date());
		if ((dto.getLinkDetails() != null) && !dto.getLinkDetails().isEmpty()) {
			for (final TbKLinkCcnDTO link : dto.getLinkDetails()) {
				link.setOrgIds(reqDTO.getOrgId());
				link.setUserIds(reqDTO.getUserId());
				link.setLangId(dto.getLangId());
				link.setLgIpMac(reqDTO.getLgIpMac());
				link.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
				tempLinkList.add(link);
			}
			dto.setLinkDetails(tempLinkList);
		}
		if ((dto.getOwnerList() != null) && !dto.getOwnerList().isEmpty()) {
			for (final AdditionalOwnerInfoDTO owner : dto.getOwnerList()) {
				owner.setOrgid(reqDTO.getOrgId());
				owner.setUserId(reqDTO.getUserId());
				owner.setLangId(reqDTO.getLangId());
				owner.setLgIpMac(reqDTO.getLgIpMac());
				owner.setLmoddate(new Date());
				owner.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
				tempOwnerList.add(owner);
			}
			dto.setOwnerList(tempOwnerList);
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

	private void setCsmrInfoApplicantDetails(final NewWaterConnectionReqDTO reqDTO) {
		final TbCsmrInfoDTO dto = reqDTO.getCsmrInfo();
		final ApplicantDetailDTO applicantDTO = reqDTO.getApplicantDTO();

		if (reqDTO.getIsConsumer() != null && !reqDTO.getIsConsumer().isEmpty()) {
			setConsumerDetails(applicantDTO, dto);
		}
		if (reqDTO.getIsBillingAddressSame() != null && !reqDTO.getIsBillingAddressSame().isEmpty()) {
			setBillingDetails(dto);
		}
		dto.setCsName(applicantDTO.getApplicantFirstName());
		reqDTO.setfName(applicantDTO.getApplicantFirstName());
		dto.setCsLname(applicantDTO.getApplicantLastName());
		dto.setCsMname(applicantDTO.getApplicantMiddleName());
		dto.setCsAdd(applicantDTO.getAreaName());
		dto.setCsBldplt((applicantDTO.getBuildingName()));
		dto.setCsFlatno(applicantDTO.getFloorNo());
		dto.setCsRdcross(applicantDTO.getRoadName());
		dto.setCsContactno(applicantDTO.getMobileNo());
		dto.setCsLanear(applicantDTO.getAreaName());
		if (dto.getBplFlag().equals("Y"))
			applicantDTO.setBplNo(dto.getBplNo());
		dto.setCsApldate(new Date());
		dto.setBplFlag(applicantDTO.getIsBPL());
	}

	private void populateCheckListModel(final IllegalToLegalConnectionModel model,
			final CheckListModel checklistModel) {
		checklistModel.setOrgId(model.getOrgId());
		checklistModel.setServiceCode("WIL");
		final TbCsmrInfoDTO data = model.getCsmrInfo();
		checklistModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		if (data.getTrmGroup1() != null) {
			checklistModel.setUsageSubtype1(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1()).getDescLangFirst());
		}
		if (data.getTrmGroup2() != null) {
			checklistModel.setUsageSubtype2(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2()).getDescLangFirst());
		}
		if (data.getTrmGroup3() != null) {
			checklistModel.setUsageSubtype3(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3()).getDescLangFirst());
		}
		if (data.getTrmGroup4() != null) {
			checklistModel.setUsageSubtype4(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4()).getDescLangFirst());
		}
		if (data.getTrmGroup5() != null) {
			checklistModel.setUsageSubtype5(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5()).getDescLangFirst());
		}

		checklistModel.setDeptCode(MainetConstants.DeptCode.WATER);
		if ((model.getReqDTO() != null) && (model.getReqDTO().getExistingConsumerNumber() == null)) {
			checklistModel.setIsExistingConnectionOrConsumerNo(MainetConstants.NewWaterServiceConstants.NO);

		} else {
			checklistModel.setIsExistingConnectionOrConsumerNo(MainetConstants.NewWaterServiceConstants.YES);

		}
		if ((model.getReqDTO() != null) && (model.getReqDTO().getPropertyNo() == null)) {
			checklistModel.setIsExistingProperty(MainetConstants.NewWaterServiceConstants.NO);
		} else {
			checklistModel.setIsExistingProperty(MainetConstants.NewWaterServiceConstants.YES);
		}
	}

	private WaterRateMaster populateChargeModel(final IllegalToLegalConnectionModel model,
			final WaterRateMaster chargeModel) {
		final TbCsmrInfoDTO data = model.getCsmrInfo();
		chargeModel.setOrgId(model.getOrgId());
		chargeModel.setServiceCode("WIL");
		chargeModel.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		chargeModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		chargeModel.setRateStartDate(new Date().getTime());
		if (data.getCsCcnsize() != null) {
			chargeModel.setConnectionSize(Double.valueOf(
					CommonMasterUtility.getNonHierarchicalLookUpObject(data.getCsCcnsize()).getDescLangFirst()));
		}
		if (data.getTrmGroup1() != null) {
			chargeModel.setUsageSubtype1(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup1()).getDescLangFirst());
		}
		if (data.getTrmGroup2() != null) {
			chargeModel.setUsageSubtype2(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup2()).getDescLangFirst());
		}
		if (data.getTrmGroup3() != null) {
			chargeModel.setUsageSubtype3(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup3()).getDescLangFirst());
		}
		if (data.getTrmGroup4() != null) {
			chargeModel.setUsageSubtype4(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup4()).getDescLangFirst());
		}
		if (data.getTrmGroup5() != null) {
			chargeModel.setUsageSubtype5(
					CommonMasterUtility.getHierarchicalLookUp(data.getTrmGroup5()).getDescLangFirst());
		}
		if (data.getNoOfFamilies() != null) {
			chargeModel.setNoOfFamilies(data.getNoOfFamilies().intValue());
		}
		if (data.getCsTaxPayerFlag() == null || data.getCsTaxPayerFlag().isEmpty()) {
			chargeModel.setTaxPayer(MainetConstants.IsDeleted.NOT_DELETE);
		} else {
			chargeModel.setTaxPayer(data.getCsTaxPayerFlag());
		}
		return chargeModel;
	}

	public ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {
		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	@RequestMapping(params = "clearCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView clearCheckListAndChrages(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final IllegalToLegalConnectionModel model = getModel();

		model.setFree(MainetConstants.NewWaterServiceConstants.OPEN);
		model.setChargesInfo(null);
		model.setCharges(0.0d);
		model.getChargesMap().clear();
		model.getOfflineDTO().setAmountToShow(0.0d);
		model.getReqDTO().setFree(true);
		model.getReqDTO().setCharges(0.0d);
		model.getCheckList().clear();
		model.getReqDTO().getDocumentList().clear();
		model.setSaveMode(MainetConstants.NewWaterServiceConstants.NO);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final ModelAndView mv = new ModelAndView("IllegalToLegalConnectionValidn", MainetConstants.FORM_NAME,
				getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
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

		if (model.getIsConsumerSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsConsumer("Y");
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
		if (model.getIsBillingSame().equalsIgnoreCase("Y")) {
			connectionDTO.setIsBillingAddressSame("Y");
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
		connectionDTO.setBlockNo(infoDTO.getCsAdd());
		connectionDTO.setFlatBuildingNo(infoDTO.getCsAdd());
		if (infoDTO.getCsGender() != null && infoDTO.getCsGender() != 0l) {
			connectionDTO.setGender(String.valueOf(infoDTO.getCsGender()));
		}
		connectionDTO.setMobileNo(infoDTO.getCsContactno());
		connectionDTO.setEmail(infoDTO.getCsEmail());
		// connectionDTO.setAadhaarNo(connectionDTO.getApplicantDTO().getAadharNo());

		appDTO.setApplicantFirstName(infoDTO.getCsName());
		appDTO.setAreaName(infoDTO.getCsAdd());
		appDTO.setRoadName(infoDTO.getCsAdd());
		appDTO.setMobileNo(infoDTO.getCsContactno());
		appDTO.setEmailId(infoDTO.getCsEmail());
		appDTO.setIsBPL(infoDTO.getBplFlag());
		if (infoDTO.getBplFlag().equals("Y"))
			appDTO.setBplNo(infoDTO.getBplNo());
		// Need To Check
		// appDTO.setAadharNo(connectionDTO.getApplicantDTO().getAadharNo());
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
			TbCsmrInfoDTO infoDto = iNewWaterConnectionService.fetchConnectionDetailsByConnNo(ccnNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
			return infoDto;
		} catch (Exception e) {
			return new TbCsmrInfoDTO();
		}

	}

}
