package com.abm.mainet.water.ui.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.service.IWaterReconnectionFormService;
import com.abm.mainet.water.ui.model.ChangeOfUsageModel;
import com.abm.mainet.water.ui.model.WaterReconnectionFormModel;

@Controller
@RequestMapping("/WaterReconnectionForm.html")
public class WaterReconnectionFormController extends AbstractFormController<WaterReconnectionFormModel> {

	private static Logger log = Logger.getLogger(WaterReconnectionFormController.class);

	@Autowired
	private transient IServiceMasterService serviceMaster;

	@Autowired
	private IWaterReconnectionFormService iWaterReconnectionFormService;

	@Autowired
	IPortalServiceMasterService iPortalService;

	@Autowired
	private INewWaterConnectionService iNewWaterConnectionService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		bindModel(httpServletRequest);
		try {
			sessionCleanup(httpServletRequest);
			FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
			final WaterReconnectionFormModel model = getModel();
			getModel().initializeApplicantDetail();
			final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final Long serviceId = serviceMaster.getServiceId(MainetConstants.ServiceShortCode.WATER_RECONN, orgId);
			final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
			getModel().setServiceId(serviceId);
			getModel().setDeptId(deptId);
			model.setPlumberList(iNewWaterConnectionService.getListofplumber(orgId));
		} catch (final Exception ex) {
			log.error("Error Occurred while rendering Form:", ex);
			return defaultExceptionView();
		}
		ModelAndView mv = null;
		mv = new ModelAndView("WaterReconnectionForm", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getReconnectionDetails")
	public @ResponseBody WaterReconnectionResponseDTO getConnectionRecords(
			@RequestParam("connectionNo") final String connectionNo, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();
		final WaterReconnectionResponseDTO responseDto = new WaterReconnectionResponseDTO();
		try {
			final WaterReconnectionRequestDTO requestDTO = model.getReconnRequestDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			requestDTO.setConnectionNo(connectionNo);
			requestDTO.getResponseDTOs().clear();

			final WaterReconnectionRequestDTO outPutObject = iWaterReconnectionFormService
					.searchConnectionDetails(requestDTO);

			model.setReconnRequestDTO(outPutObject);
			String connSize = MainetConstants.BLANK;
			String disType = MainetConstants.BLANK;
			String disMethod = MainetConstants.BLANK;
			String tarrifCate = null;
			String preType = null;
			String applicantType = null;
			String meterType = null;
			if (model.getReconnRequestDTO() != null) {
				if (!model.getReconnRequestDTO().getResponseDTOs().isEmpty()) {
					getModel().getReconnRequestDTO()
							.setDiscMethodId(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId());
					getModel().getReconnRequestDTO()
							.setDiscType(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType());
					getModel().getReconnRequestDTO()
							.setConsumerIdNo(model.getReconnRequestDTO().getResponseDTOs().get(0).getConsumerIdNo());
					getModel().getReconnRequestDTO()
							.setDiscAppDate(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscAppDate());
					final int langId = UserSession.getCurrent().getLanguageId();

					if (model.getReconnRequestDTO().getResponseDTOs().get(0).getApplicantTypeId() != null) {
						applicantType = CommonMasterUtility
								.getNonHierarchicalLookUpObject(
										model.getReconnRequestDTO().getResponseDTOs().get(0).getApplicantTypeId())
								.getLookUpDesc();
					}
					if (model.getReconnRequestDTO().getResponseDTOs().get(0).getMeterTypeId() != null) {
						meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getMeterTypeId(),
								UserSession.getCurrent().getOrganisation()).getLookUpDesc();
					}
					final List<LookUp> categorylist = CommonMasterUtility.getLevelData(
							MainetConstants.NewWaterServiceConstants.TRF, MainetConstants.ENGLISH,
							UserSession.getCurrent().getOrganisation());
					for (final LookUp lookup : categorylist) {
						if (lookup.getLookUpId() == model.getReconnRequestDTO().getResponseDTOs().get(0)
								.getTarrifCategoryId()) {
							tarrifCate = lookup.getLookUpDesc();
						}
					}
					final List<LookUp> premiselist = getSubAlphanumericSortInfo(MainetConstants.BLANK,
							model.getReconnRequestDTO().getResponseDTOs().get(0).getTarrifCategoryId(),
							MainetConstants.NewWaterServiceConstants.YES);
					for (final LookUp lookup : premiselist) {
						if (lookup.getLookUpId() == model.getReconnRequestDTO().getResponseDTOs().get(0)
								.getPremiseTypeId()) {
							preType = lookup.getLookUpDesc();
						}
					}
					if (langId == MainetConstants.DEFAULT_LANGUAGE_ID) {
						connSize = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize().longValue(),
								MainetConstants.D2KFUNCTION.ENGLISH_DESC);
						disType = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType(),
								MainetConstants.D2KFUNCTION.ENGLISH_DESC);
						disMethod = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId(),
								MainetConstants.D2KFUNCTION.ENGLISH_DESC);

					} else {
						connSize = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize().longValue(),
								MainetConstants.D2KFUNCTION.REG_DESC);
						disType = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscType(),
								MainetConstants.D2KFUNCTION.REG_DESC);
						disMethod = CommonMasterUtility.getCPDDescription(
								model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscMethodId(),
								MainetConstants.D2KFUNCTION.REG_DESC);
					}
				}
			}
			responseDto.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			responseDto.setTarrifCategory(tarrifCate);
			responseDto.setPremiseType(preType);
			responseDto.setDisconnectionType(disType);
			responseDto.setDiscMethod(disMethod);
			responseDto.setApplicantType(applicantType);
			responseDto.setConnectionNo(connectionNo);
			responseDto.setMeterType(meterType);
			requestDTO.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			// responseDto.setAlreadyApplied(model.getReconnRequestDTO().getResponseDTOs().get(0).isAlreadyApplied());
			responseDto.setConsumerName(model.getReconnRequestDTO().getResponseDTOs().get(0).getConsumerName());
			model.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			model.getReconnRequestDTO().setTarrifCategory(tarrifCate);
			model.getReconnRequestDTO().setPremiseType(preType);
			model.getReconnRequestDTO().setDisconnectionType(disType);
			model.getReconnRequestDTO().setDiscMethod(disMethod);
			model.getReconnResponseDTO().setApplicantType(applicantType);
			model.getReconnResponseDTO().setMeterType(meterType);
			model.getReconnResponseDTO()
					.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			model.getReconnResponseDTO().setTarrifCategory(tarrifCate);
			model.getReconnResponseDTO().setPremiseType(preType);
			model.getReconnResponseDTO().setDisconnectionType(disType);
			model.getReconnResponseDTO().setDiscMethod(disMethod);
			responseDto.setDiscDate(
					Utility.dateToString(model.getReconnRequestDTO().getResponseDTOs().get(0).getDiscAppDate()));

		}

		catch (final Exception e) {
			log.error("Exception while fetching the old connection Record for Water Reconnection", e);
		}

		return responseDto;

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getSelectedData")
	public @ResponseBody WaterReconnectionResponseDTO getSelectedData(final HttpServletRequest httpServletRequest,
			@RequestParam("id") final int index) {
		bindModel(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();
		WaterReconnectionResponseDTO responseDTO = null;
		final List<WaterReconnectionResponseDTO> list = model.getReconnRequestDTO().getResponseDTOs();
		if ((model.getReconnRequestDTO() != null) && (list != null) && !list.isEmpty()) {

			responseDTO = list.get(index);
			getModel().getReconnRequestDTO().setDiscMethodId(responseDTO.getDiscMethodId());
			getModel().getReconnRequestDTO().setDiscType(responseDTO.getDiscType());
			getModel().getReconnRequestDTO().setConsumerIdNo(responseDTO.getConsumerIdNo());
			getModel().getReconnRequestDTO().setDiscAppDate(responseDTO.getDiscAppDate());
			final int langId = UserSession.getCurrent().getLanguageId();
			String connSize = MainetConstants.BLANK;
			String disType = MainetConstants.BLANK;
			String disMethod = MainetConstants.BLANK;
			String tarrifCate = null;
			String preType = null;
			final String applicantType = CommonMasterUtility
					.getNonHierarchicalLookUpObject(responseDTO.getApplicantTypeId()).getLookUpDesc();
			final String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(responseDTO.getMeterTypeId(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc();
			final List<LookUp> categorylist = CommonMasterUtility.getLevelData(
					MainetConstants.NewWaterServiceConstants.TRF, MainetConstants.ENGLISH,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp lookup : categorylist) {
				if (lookup.getLookUpId() == responseDTO.getTarrifCategoryId()) {
					tarrifCate = lookup.getLookUpDesc();
				}
			}
			final List<LookUp> premiselist = getSubAlphanumericSortInfo(MainetConstants.BLANK,
					responseDTO.getTarrifCategoryId(), MainetConstants.NewWaterServiceConstants.YES);
			for (final LookUp lookup : premiselist) {
				if (lookup.getLookUpId() == responseDTO.getPremiseTypeId()) {
					preType = lookup.getLookUpDesc();
				}
			}
			if (langId == MainetConstants.DEFAULT_LANGUAGE_ID) {
				connSize = CommonMasterUtility.getCPDDescription(responseDTO.getConnectionSize().longValue(),
						MainetConstants.D2KFUNCTION.ENGLISH_DESC);
				disType = CommonMasterUtility.getCPDDescription(responseDTO.getDiscType(),
						MainetConstants.D2KFUNCTION.ENGLISH_DESC);
				disMethod = CommonMasterUtility.getCPDDescription(responseDTO.getDiscMethodId(),
						MainetConstants.D2KFUNCTION.ENGLISH_DESC);

			} else {
				connSize = CommonMasterUtility.getCPDDescription(responseDTO.getConnectionSize().longValue(),
						MainetConstants.D2KFUNCTION.REG_DESC);
				disType = CommonMasterUtility.getCPDDescription(responseDTO.getDiscType(),
						MainetConstants.D2KFUNCTION.REG_DESC);
				disMethod = CommonMasterUtility.getCPDDescription(responseDTO.getDiscMethodId(),
						MainetConstants.D2KFUNCTION.REG_DESC);
			}

			responseDTO.setConnectionSize(model.getReconnRequestDTO().getResponseDTOs().get(0).getConnectionSize());
			responseDTO.setTarrifCategory(tarrifCate);
			responseDTO.setPremiseType(preType);
			responseDTO.setDisconnectionType(disType);
			responseDTO.setDiscMethod(disMethod);
			responseDTO.setApplicantType(applicantType);
			responseDTO.setMeterType(meterType);
			model.setReconnResponseDTO(responseDTO);
		}
		return responseDTO;

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "checkPlumberLicNo")
	public @ResponseBody String checkRegisteredPlumber(final HttpServletRequest httpServletRequest,
			@RequestParam("plumberLicNo") final String plumberLicNo) {

		String isRegisteredPlumberNo = MainetConstants.NO;
		final WaterReconnectionRequestDTO requestDTO = new WaterReconnectionRequestDTO();
		requestDTO.setPlumberLicNo(plumberLicNo);
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		try {

			final WaterReconnectionResponseDTO outPutObject = iWaterReconnectionFormService
					.serachPlumerLicense(requestDTO);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(outPutObject.getStatus())) {
				isRegisteredPlumberNo = MainetConstants.YES;
				getModel().getReconnRequestDTO().setPlumberId(outPutObject.getPlumberId());
			}

		} catch (final IOException e) {
			log.error("Exception while fetching the old connection Record for Water Reconnection", e);
		}
		return isRegisteredPlumberNo;
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterReconnectionForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {

		getModel().bind(httpServletRequest);
		final WaterReconnectionFormModel model = getModel();
		ModelAndView mv = null;
		if (model.saveForm()) {
			final CommonChallanDTO offline = model.getOfflineDTO();
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
				return jsonResult(
						JsonViewObject.successResult(model.getSuccessMessage()));
			}
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
				return jsonResult(
						JsonViewObject.successResult(getApplicationSession().getMessage("continue.forchallan")));
			} else {
				return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("Your Application No. " + model.getReconnRequestDTO().getApplicationId()
						+ " for Reconnection has been saved successfully.")));
			}
		}
		mv = new ModelAndView("WaterReconnectionFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		try {
			getModel().findApplicableCheckListAndCharges(getModel().getServiceId(), orgId);
			modelAndView = new ModelAndView("WaterReconnectionFormValidn", MainetConstants.REQUIRED_PG_PARAM.COMMAND,
					getModel());
			if (getModel().getBindingResult() != null) {
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
			}
		} catch (final Exception ex) {
			log.error("Error Occurred while getCheckListAndCharges:", ex);
			modelAndView = defaultExceptionFormView();
		}
		return modelAndView;
	}
	
	 @RequestMapping(params = "showDetails", method = RequestMethod.POST)
	    public ModelAndView defaultLoad(@RequestParam("appId") final long appId, final HttpServletRequest httpServletRequest) throws Exception {  
	        getModel().bind(httpServletRequest);
	        
	        WaterReconnectionFormModel model = this.getModel();
	        try {
	        	 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        	 model.setPlumberList(iNewWaterConnectionService.getListofplumber(orgId));
	        	 WaterReconnectionRequestDTO requestDto = new WaterReconnectionRequestDTO();
	             final WaterReconnectionRequestDTO response = iWaterReconnectionFormService.getAppicationDetails(appId,orgId);
	            if(response != null) {
	            	model.setApplicantDetailDto(response.getApplicant());
	            	model.setReconnRequestDTO(response);
	            	requestDto.setConnectionSize(response.getConnectionSize());
	            	String tarrifCate = null;
	            	String discMethod = null;
	            	final List<LookUp> categorylist = CommonMasterUtility.getLevelData(
	    					MainetConstants.NewWaterServiceConstants.TRF, MainetConstants.ENGLISH,
	    					UserSession.getCurrent().getOrganisation());
	    			for (final LookUp lookup : categorylist) {
	    				if (lookup.getLookUpId() == response.getTarrifCategoryId()) {
	    					tarrifCate = lookup.getLookUpDesc();
	    				}
	    				
	    				if (lookup.getLookUpId() == response.getDiscMethodId()) {
	    					discMethod = lookup.getLookUpDesc();
	    				}
	    			}
	    			response.setTarrifCategory(tarrifCate);
	            }
	           
	        }
	       catch(Exception exception) {
	    	   throw new FrameworkException(exception);
	       }
	        return new ModelAndView("waterReconnectionViewApplication", MainetConstants.FORM_NAME, getModel());
	    }
}
