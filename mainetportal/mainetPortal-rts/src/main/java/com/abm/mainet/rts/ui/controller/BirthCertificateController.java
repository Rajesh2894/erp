package com.abm.mainet.rts.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.datamodel.BndRateMaster;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.service.IBirthCertificateService;
import com.abm.mainet.rts.service.IRtsService;
import com.abm.mainet.rts.ui.model.BirthCertificateModel;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;

@Controller
@RequestMapping("ApplyforBirthCertificate.html")
public class BirthCertificateController extends AbstractFormController<BirthCertificateModel> {

	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private IBirthCertificateService birthCertificateService;
	
	@Autowired
	DrainageConnectionService drainageConnectionService;
	
	@Autowired
	private IRtsService rtsService;

	private static final Logger LOGGER = Logger.getLogger(BirthCertificateController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
		sessionCleanup(httpServletRequest);
		ModelAndView mv = null;
		this.getModel().setRequestDTO(requestDTO);
		LinkedHashMap<String, Object> map = rtsService.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(),
				RtsConstants.RBC);
		if (map.get("checkListApplFlag") != null) {
			this.getModel().setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		if (map.get("applicationchargeApplFlag") != null) {
			this.getModel().setApplicationChargeFlag(map.get("applicationchargeApplFlag").toString());
		}
		try {
			if (this.getModel().getCheckListApplFlag().equals("Y")) {
			getCheckListAndCharges(this.getModel(), httpServletRequest, httpServletResponse);
			}
			mv = new ModelAndView(RtsConstants.BIRTH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
		} catch (FrameworkException e) {
			LOGGER.info(e.getErrMsg());

			mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
		}
		if(requestDTO.getApplicationId()!=null)
        {
        	this.getModel().setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);
        	this.getModel().setBirthCertificateDto(birthCertificateService.getBirthCertificateInfo(requestDTO.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid()));
        	List<DocumentDetailsVO> checkListList1 = rtsService
					.fetchDocumentDetailsByAppNo(requestDTO.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setCheckList(checkListList1);
        }
		return mv;
	}

	private void getCheckListAndCharges(BirthCertificateModel birthRegModel,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {

		getModel().bind(httpServletRequest);
		List<DocumentDetailsVO> docs = null;
		// fileUpload.sessionCleanUpForFileUpload();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		birthRegModel.setCommonHelpDocs(RtsConstants.APPLY_FOR_BIRTH_CERTIFICATE);

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(RtsConstants.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(RtsConstants.RBC);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel);

			docs = brmsCommonService.getChecklist(checkListModel);

			if (docs != null && !docs.isEmpty()) {
				long cnt = 1;
				for (final DocumentDetailsVO doc : docs) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
			}
			birthRegModel.setCheckList(docs);

		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody String getBndCharges(@RequestParam("noOfCopies") int noOfCopies,
			@RequestParam("issuedCopy") int issuedCopy) {

		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		BirthCertificateModel bndmodel = this.getModel();
		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName(RtsConstants.BND_RATE_MASTER);
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = JersyCall.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(RtsConstants.RBC);
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.RoadCuttingConstant.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			this.getModel().getBirthCertificateDto().setChargeApplicableAt(Long.parseLong(rateMasterModel.getChargeApplicableAt()));
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);

			//WSResponseDTO responsefortax = birthCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = birthCertificateService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargesAmount;
			}
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {

				LinkedHashMap<String, String> charges = null;
				if (!responsefortax.isFree()) {
					List<Object> rates = JersyCall.castResponse(responsefortax, BndRateMaster.class, 0);
					List<Object> detailDTOs = null;
					// final List<Object> rates =(List<Object>)responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate = populateChargeModel(bndmodel, masterrate);
						masterrate.setIssuedCopy(issuedCopy);
						masterrate.setNoOfCopies(noOfCopies);
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName(RtsConstants.BND_RATE_MASTER);
					certificateCharges = birthCertificateService.getBndCharge(bndChagesRequestDto);
					if(certificateCharges != null) {
					detailDTOs = (List<Object>) certificateCharges.getResponseObj();
					for (final Object rate : detailDTOs) {
						charges = (LinkedHashMap<String, String>) rate;
						break;
					}
					chargesAmount = String.valueOf(charges.get("certificateCharge"));
					}else {
						chargesAmount = MainetConstants.FlagN;
						bndmodel.setChargesAmount(chargesAmount);
					}
				} else {
					chargesAmount = "0.0";
				}
				 if(chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
				chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
				 }
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthCertificateDTO.serName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthCertificateDTO.regserName"));
				chargesInfo.add(chargeDetailDTO);
				bndmodel.setChargesInfo(chargesInfo);
				 if(chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
				bndmodel.setChargesAmount(chargesAmount);
				 }
			}
			else {
				chargesAmount = MainetConstants.FlagN;
			}

		}
		return chargesAmount;
	}

	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {
		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
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

	private BndRateMaster populateChargeModel(BirthCertificateModel model, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(RtsConstants.RBC);
		bndRateMaster.setDeptCode(RtsConstants.RTS);

		return bndRateMaster;
	}

	@RequestMapping(params = "resetBirthForm", method = RequestMethod.POST)
	public ModelAndView resetBirthForm(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		return new ModelAndView(RtsConstants.BIRTH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		BirthCertificateModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<DocumentDetailsVO> checkListList1 = drainageConnectionService.fetchDrainageConnectionDocsByAppNo(
					model.getBirthCertificateDto().getApmApplicationId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (checkListList1 != null && !checkListList1.isEmpty()) {
				model.setDocumentList(checkListList1);
			}
		}
		String name = " ";
		if (model.getRequestDTO().getfName() != null) {
			name = model.getRequestDTO().getfName() + " ";
		}
		if (model.getRequestDTO().getmName()!= null) {
			name += model.getRequestDTO().getmName() + " ";
		}
		if (model.getRequestDTO().getlName() != null) {
			name += model.getRequestDTO().getlName();
		}
		model.getApplicantDetailDto().setApplicantFirstName(name);
		model.setApmApplicationId(model.getBirthCertificateDto().getApmApplicationId());
		model.setServiceName(RtsConstants.APPLY_FOR_BIRTH_CERTIFICATE_SERVICE);
		model.setFormName(RtsConstants.BIRTH_CERTIFICATE_FORMNAME);
		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);
	}

}
