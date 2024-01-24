package com.abm.mainet.rts.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

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

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.datamodel.BndRateMaster;
import com.abm.mainet.rts.service.IDeathCertificateApprovalService;
import com.abm.mainet.rts.service.IDeathCertificateService;
import com.abm.mainet.rts.service.IRtsService;
import com.abm.mainet.rts.ui.model.DeathCertificateModel;

@Controller
@RequestMapping(value = "/applyForDeathCertificate.html")
public class DeathCertificateController extends AbstractFormController<DeathCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(DeathCertificateController.class);

	@Resource
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private IDeathCertificateService iDeathCertificateService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	private IDeathCertificateApprovalService iDeathCertificateApprovalService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	 @Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	 
	@Autowired
	private IRtsService rtsService;

	@SuppressWarnings("deprecation")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setRequestDTO(requestDTO);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(RtsConstants.RDC, orgId);
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());

		if (serviceMas.getSmFeesSchedule() != 0) {
			this.getModel().getDeathCertificateDTO().setChargeStatus(RtsConstants.CHARGESAPPLICABLE);
		}
		if (lookup.getLookUpCode().equals(RtsConstants.CHECKLISTAPPLICABLE)) {
			getCheckListAndCharges(this.getModel(), httpServletRequest, httpServletResponse);
		}

		if (requestDTO.getApplicationId() != null) {
			this.getModel().setSaveMode(RtsConstants.VIEWMODE);
			this.getModel().setDeathCertificateDTO(
					iDeathCertificateApprovalService.getDeathCertificateDetail(requestDTO.getApplicationId(), orgId));
			// fetch uploaded document
			List<DocumentDetailsVO> dvo = rtsService.getRtsUploadedCheckListDocuments(
   				 requestDTO.getApplicationId(), orgId);
			this.getModel().setCheckList(dvo);
		}
		return new ModelAndView(RtsConstants.DEATH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());

	}

	@SuppressWarnings("unchecked")
	private void getCheckListAndCharges(DeathCertificateModel deathModel, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		deathModel.setCommonHelpDocs("applyForDeathCertificate.html");

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(RtsConstants.ChecklistModel);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);

			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(RtsConstants.RDC);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(RtsConstants.ChecklistModel);
			checklistReqDto.setDataModel(checkListModel);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

					if ((checkListList != null) && !checkListList.isEmpty()) {
						deathModel.setCheckList(checkListList);

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

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody String getBndCharges(@RequestParam("demandedCopies") int demandedCopies,
			@RequestParam("issuedCopies") int issuedCopies) {
		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		DeathCertificateModel bndmodel = this.getModel();
		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName(RtsConstants.BNDRateMaster);
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(RtsConstants.RDC);
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
			// iDeathCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = iDeathCertificateService.getApplicableTaxes(taxRequestDto);
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
						masterrate = populateChargeModel(bndmodel, masterrate);
						masterrate.setIssuedCopy(issuedCopies);
						masterrate.setNoOfCopies(demandedCopies);
						requiredCharges.add(masterrate);
					}
					final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
					bndChagesRequestDto.setDataModel(requiredCharges);
					bndChagesRequestDto.setModelName(RtsConstants.BNDRateMaster);
					certificateCharges = iDeathCertificateService.getBndCharge(bndChagesRequestDto);
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
				 if(certificateCharges != null) {
				chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxMasterByTaxCode(orgIds,
						serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode")).getTaxId()); // taxID
				 }
				chargeDetailDTO
						.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerRegName"));
				chargeDetailDTO
						.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
				chargesInfo.add(chargeDetailDTO);
				if(chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
				bndmodel.setChargesInfo(chargesInfo);
				bndmodel.setChargesAmount(chargesAmount);
				}
			}
		}
		return chargesAmount;
	}

	private BndRateMaster populateChargeModel(DeathCertificateModel model, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(RtsConstants.RDC);
		bndRateMaster.setDeptCode(RtsConstants.RTS);
		return bndRateMaster;
	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		DeathCertificateModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
					.getBean(IChecklistVerificationService.class)
					.getAttachDocumentByDocumentStatus(model.getDeathCertificateDTO().getApplicationNo(), docStatus,
							UserSession.getCurrent().getOrganisation().getOrgid());
			if (CollectionUtils.isNotEmpty(documentUploaded)) {
				model.setDocumentList(documentUploaded);
			}
		}
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
				model.getDeathCertificateDTO().getApplicationNo(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setCfcEntity(cfcEntity);
		model.setApplicationId(model.getDeathCertificateDTO().getApplicationNo());
		String name = " ";
		if (model.getCfcEntity().getApmFname() != null) {
			name = model.getCfcEntity().getApmFname() + " ";
		}
		if (model.getCfcEntity().getApmMname()!= null) {
			name += model.getCfcEntity().getApmMname() + " ";
		}
		if (model.getCfcEntity().getApmLname() != null) {
			name += model.getCfcEntity().getApmLname();
		}
		int langId = UserSession.getCurrent().getLanguageId();
		if (langId == 1) {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		} else {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getoNlsOrgnameMar());
		}
        model.setApplicantName(name);
		model.setServiceName(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
		model.setFormName(getApplicationSession().getMessage("TbDeathregDTO.drDeathSerEngName"));
		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME,
				this.getModel());

	}
	
	@RequestMapping(params = "resetDeathForm", method = RequestMethod.POST)
	public ModelAndView resetDeathForm(final Model model, final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		return new ModelAndView(RtsConstants.DEATH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
	}


}
