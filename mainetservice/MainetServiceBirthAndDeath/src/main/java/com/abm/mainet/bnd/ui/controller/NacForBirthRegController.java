package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.NacForBirthRegModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Arun Shinde
 *
 */

@Controller
@RequestMapping(value = "/NacForBirthReg.html")
public class NacForBirthRegController extends AbstractFormController<NacForBirthRegModel> {

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	
	@Autowired
	private ILocationMasService locationMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.NBR, orgId);
		loadWardZone(httpServletRequest);
		this.getModel().getRequestDTO().setServiceShortCode(BndConstants.NBR);
		this.getModel().getRequestDTO().setServiceId(serviceMas.getSmServiceId());
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
			this.getModel().setServiceName(serviceMas.getSmServiceName());
		}else {
			this.getModel().setServiceName(serviceMas.getSmServiceNameMar());
		}
		
		return new ModelAndView("bndApplicantDetForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@SuppressWarnings("unchecked")
	private void getCheckListAndCharges(NacForBirthRegModel model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// birthRegModel.setCommonHelpDocs(RtsConstants.APPLY_FOR_BIRTH_CERTIFICATE);
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(BndConstants.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(BndConstants.NBR);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(BndConstants.CHECKLIST_MODEL);
			checklistReqDto.setDataModel(checkListModel);

			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

					if ((checkListList != null) && !checkListList.isEmpty()) {
						model.setCheckList(checkListList);
					}
				}
			}

		}

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

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody String getBndCharges(@RequestParam("noOfCopies") int noOfCopies,
			@RequestParam("issuedCopy") int issuedCopy,@RequestParam("brDob") Date brDob) {
		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		NacForBirthRegModel bndmodel = this.getModel();
		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(BndConstants.NBR);
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
				responsefortax = issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargesAmount;
			}
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
					orgIds);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
				if (!responsefortax.isFree()) {
					final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
					final List<BndRateMaster> requiredCharges = new ArrayList<>();
					for (final Object rate : rates) {
						BndRateMaster masterrate = (BndRateMaster) rate;
						masterrate = populateChargeModel(masterrate);
						masterrate.setIssuedCopy(issuedCopy);
						masterrate.setNoOfCopies(noOfCopies);
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName(BndConstants.BND_RATE_MASTER);
					certificateCharges = issuenceOfBirthCertificateService.getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							break;
						}
						String certCharge = String.valueOf(charges.get(BndConstants.BNDCHARGES));
						String appCharge = String.valueOf(charges.get(BndConstants.FLAT_RATE));
						int birthYear = brDob.getYear();
						int currYear  = new Date().getYear();
						Double searchCharges= (currYear-birthYear)*Double.valueOf(appCharge);
						
						Double totalAmount= Double.valueOf(certCharge)+Double.valueOf(searchCharges);
						chargesAmount=String.valueOf(totalAmount);
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
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("NacBirthRegDTO.serName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("NacBirthRegDTO.regserName"));
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

	private BndRateMaster populateChargeModel(BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(BndConstants.NBR);
		bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
		return bndRateMaster;
	}

	@RequestMapping(params = "resetBirthForm", method = RequestMethod.POST)
	public ModelAndView resetBirthForm(final Model model, final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		return new ModelAndView("NacForBirthRegValidn", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		NacForBirthRegModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
					.getBean(IChecklistVerificationService.class)
					.getAttachDocumentByDocumentStatus(Long.valueOf(model.getNacForBirthRegDTO().getApplicationId()),
							docStatus, UserSession.getCurrent().getOrganisation().getOrgid());
			if (CollectionUtils.isNotEmpty(documentUploaded)) {
				model.setDocumentList(documentUploaded);
			}
		}
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
				Long.valueOf(model.getNacForBirthRegDTO().getApplicationId()),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setCfcEntity(cfcEntity);
		model.setApplicationId(Long.valueOf(model.getNacForBirthRegDTO().getApplicationId()));
		String name = " ";
		if (model.getCfcEntity().getApmFname() != null) {
			name = model.getCfcEntity().getApmFname() + " ";
		}
		if (model.getCfcEntity().getApmMname() != null) {
			name += model.getCfcEntity().getApmMname() + " ";
		}
		if (model.getCfcEntity().getApmLname() != null) {
			name += model.getCfcEntity().getApmLname();
		}
		model.setApplicantName(name);
		model.setServiceName(BndConstants.NBR_SERVICE);
		model.setFormName(BndConstants.NON_AVAIL_CERT);
		return new ModelAndView("rtsAcknowldgeMentStatusForm", MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@RequestMapping(params = "nacBirthRedirect", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView nacBirthRedirect(final Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.NBR, orgId);
		Organisation org = orgService.getOrganisationById(orgId);
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), org);
		this.getModel().setRequestDTO(requestDTO);
		if (serviceMas.getSmFeesSchedule() != 0) {
			this.getModel().getNacForBirthRegDTO().setChargesStatus(BndConstants.CHARGESAPPLICABLE);
		}
		if (lookup.getLookUpCode().equals(BndConstants.CHECKLISTAPPLICABLE)) {
			getCheckListAndCharges(this.getModel(), httpServletRequest, httpServletResponse);
		}
		return new ModelAndView("NacForBirthReg", MainetConstants.FORM_NAME, this.getModel());
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
	
	@RequestMapping(params = "backOnApplicantForm", method = RequestMethod.POST)
	private ModelAndView applicantForm(final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceMas = ApplicationContextProvider.getApplicationContext()
		.getBean(ServiceMasterService.class).getServiceByShortName(BndConstants.NBR, orgId);
		this.getModel().setWardList(this.getModel().getWardList());
		this.getModel().getRequestDTO().setServiceShortCode(BndConstants.NBR);
		this.getModel().getRequestDTO().setServiceId(serviceMas.getSmServiceId());
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
			this.getModel().setServiceName(serviceMas.getSmServiceName());
		}else {
			this.getModel().setServiceName(serviceMas.getSmServiceNameMar());
		}
		
		return new ModelAndView("bndApplicantForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        final NacForBirthRegModel deathModel = this.getModel();
        ModelAndView mv = null;
        if(deathModel.getNacForBirthRegDTO().getApmApplicationId()!=null) {
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.NBR, UserSession.getCurrent().getOrganisation().getOrgid());
        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
        Long title = deathModel.getNacForBirthRegDTO().getRequestDTO().getTitleId();
        LookUp lokkup = null;
		if (deathModel.getNacForBirthRegDTO().getRequestDTO().getTitleId() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(deathModel.getNacForBirthRegDTO().getRequestDTO().getTitleId(),
					UserSession.getCurrent().getOrganisation().getOrgid(), "TTL");
		}
		if(lokkup!=null) {
			ackDto.setApplicantTitle(lokkup.getLookUpDesc());
		}
		/*
		 * if(title == 1) { ackDto.setApplicantTitle(MainetConstants.MR); } else
		 * if(title == 2) { ackDto.setApplicantTitle(MainetConstants.MRS); } else
		 * if(title == 3) { ackDto.setApplicantTitle(MainetConstants.MS); }
		 */
        ackDto.setApplicationId(deathModel.getNacForBirthRegDTO().getApmApplicationId());
        ackDto.setApplicantName(String.join(" ", Arrays.asList(deathModel.getRequestDTO().getfName(),
        		deathModel.getRequestDTO().getmName(), deathModel.getRequestDTO().getlName())));
        if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
        	ackDto.setServiceShortCode(serviceMas.getSmServiceName());
        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpDeptdesc());
        }else {
        	ackDto.setServiceShortCode(serviceMas.getSmServiceNameMar());
        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpNameMar());
        }
        ackDto.setAppDate(new Date());
        ackDto.setAppTime(new SimpleDateFormat("HH:mm").format(new Date()));
        ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
        ackDto.setHelpLine(getApplicationSession().getMessage("bnd.acknowledgement.helplineNo"));
        deathModel.setAckDto(ackDto);
        
        // runtime print acknowledge or certificate
        String viewName = "bndRegAcknow";
       
        // fetch checklist result if not fetch already
        if (deathModel.getCheckList().isEmpty()) {
            // call for fetch checklist based on Marriage Status (STA)
        	getCheckListAndCharges(deathModel,request,null);
        }
         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	}
        return mv;

    }

}
