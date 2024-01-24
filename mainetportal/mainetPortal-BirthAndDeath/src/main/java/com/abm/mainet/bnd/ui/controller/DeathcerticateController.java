package com.abm.mainet.bnd.ui.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
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

import com.abm.mainet.bnd.constant.RtsConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathCertificateService;
import com.abm.mainet.bnd.service.IRtsService;
import com.abm.mainet.bnd.ui.model.BirthCertificateModel;
import com.abm.mainet.bnd.ui.model.DeathCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller("bndDeathcerticateController")
@RequestMapping(value = "/bndApplyForDeathCertificate.html")
public class DeathcerticateController extends AbstractFormController<DeathCertificateModel> {

	@Autowired
	private ICommonBRMSService brmsCommonService;

	@Autowired
	private IDeathCertificateService iDeathCertificateService;
	
	@Autowired
	private IRtsService rtsService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;

	private static final Logger LOGGER = Logger.getLogger(DeathcerticateController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse,@ModelAttribute("requestDTO") RequestDTO requestDTO) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setRequestDTO(requestDTO);
		this.getModel().setCommonHelpDocs("bndApplyForDeathCertificate.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LinkedHashMap<String, Object> map = rtsService.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(),
				RtsConstants.RDC);
		if (map.get("checkListApplFlag") != null) {
			this.getModel().setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		if (map.get("applicationchargeApplFlag") != null) {
			this.getModel().setApplicationChargeFlag(map.get("applicationchargeApplFlag").toString());
		}
		if (this.getModel().getCheckListApplFlag().equals("Y")) {
		getCheckListAndCharges(this.getModel(), httpServletRequest, httpServletResponse);
		}
		if (requestDTO.getApplicationId() != null) {
			this.getModel().setSaveMode(MainetConstants.D2KFUNCTION.CPD_VALUE);
			this.getModel().setDeathCertificateDTO(
					iDeathCertificateService.getDeathCertificateDetail(requestDTO.getApplicationId(), orgId));
			this.getModel().getDeathCertificateDTO().setDateOfDeath(Utility.dateToString(this.getModel().getDeathCertificateDTO().getDrDod()));
			List<DocumentDetailsVO> checkListList1 = rtsService
					.fetchDocumentDetailsByAppNo(requestDTO.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setCheckList(checkListList1);
		}
		
		return new ModelAndView(RtsConstants.DEATH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());

	}

	private void getCheckListAndCharges(DeathCertificateModel deathModel, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		deathModel.setCommonHelpDocs(RtsConstants.APPLY_FOR_DEATH_CERTIFICATE);

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(RtsConstants.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = JersyCall.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);

			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(RtsConstants.RDC);
			final WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName(RtsConstants.CHECKLIST_MODEL);
			checklistReqDto.setDataModel(checkListModel);
			List<DocumentDetailsVO> docs = brmsCommonService.getChecklist(checkListModel);

			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

				if (docs != null && !docs.isEmpty()) {
					long cnt = 1;
					for (final DocumentDetailsVO doc : docs) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
				}

				deathModel.setCheckList(docs);
			}
		}
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
			rateMasterModel.setServiceCode(RtsConstants.RDC);
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			this.getModel().getDeathCertificateDTO().setChargeApplicableAt(Long.parseLong(rateMasterModel.getChargeApplicableAt()));
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);
			//WSResponseDTO responsefortax = iDeathCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = iDeathCertificateService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargesAmount;
			}
			
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;

				if (!responsefortax.isFree()) {
					 final List<?> rates =  JersyCall.castResponse(responsefortax, BndRateMaster.class);
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
					bndChagesRequestDto.setModelName(RtsConstants.BND_RATE_MASTER);
					certificateCharges = iDeathCertificateService.getDeathCertificateCharges(bndChagesRequestDto);
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
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerRegName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
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
	
	private BndRateMaster populateChargeModel(DeathCertificateModel model, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(RtsConstants.RDC);
		bndRateMaster.setDeptCode(RtsConstants.RTS);
		return bndRateMaster;
	}
	
	@RequestMapping(params = "resetDeathForm", method = RequestMethod.POST)
	public ModelAndView resetDeathForm(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		return new ModelAndView(RtsConstants.DEATH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		DeathCertificateModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<DocumentDetailsVO> checkListList1 = rtsService.fetchDrainageConnectionDocsByAppNo(
					model.getDeathCertificateDTO().getApplicationNo(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (checkListList1 != null && !checkListList1.isEmpty()) {
				model.setDocumentList(checkListList1);
			}
		}
		String name = " ";
		if (model.getRequestDTO().getfName() != null) {
			name = model.getRequestDTO().getfName() + " ";
		}
		if (model.getRequestDTO().getmName() != null) {
			name += model.getRequestDTO().getmName() + " ";
		}
		if (model.getRequestDTO().getlName() != null) {
			name += model.getRequestDTO().getlName();
		}
		model.getApplicantDetailDto().setApplicantFirstName(name);
		model.setApmApplicationId(model.getDeathCertificateDTO().getApplicationNo());
		model.setServiceName(RtsConstants.APPLY_FOR_DEATH_CERTIFICATE_SERVICE);
		model.setFormName(RtsConstants.DEATH_CERTIFICATE_FORMNAME);
		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);
	}
	
	
	@RequestMapping(params = "printBndAcknowledgement", method = { RequestMethod.POST })
	public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		final DeathCertificateModel birthModel = this.getModel();
		LinkedHashMap<String, Object> map = iBirthRegSevice
				.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), RtsConstants.RDC);
		BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
		ackDto.setApplicationId(Long.valueOf(birthModel.getDeathCertificateDTO().getApplicationNo()));
		ackDto.setApplicantName(String.join(" ", Arrays.asList(UserSession.getCurrent().getEmployee().getEmpname(),
        		UserSession.getCurrent().getEmployee().getEmpMName(), UserSession.getCurrent().getEmployee().getEmpLName())));
		if(MainetConstants.DEFAULT_LANGUAGE_ID == UserSession.getCurrent().getLanguageId()) {
		ackDto.setServiceShortCode(String.valueOf(map.get("serviceNameEng")));
		ackDto.setDepartmentName(String.valueOf(map.get("deptNameEng")));
		}else{
        	ackDto.setServiceShortCode(String.valueOf(map.get("serviceNameReg")));
	        ackDto.setDepartmentName(String.valueOf(map.get("deptNameReg")));
        }
		ackDto.setAppDate(new Date());
		ackDto.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (map.get("smServiceDuration") != null)
			ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),
					Long.valueOf((String) map.get("smServiceDuration")).intValue()));
		ackDto.setHelpLine(getApplicationSession().getMessage("bnd.acknowledgement.helplineNo"));
		birthModel.setAckDto(ackDto);

		// runtime print acknowledge or certificate
		String viewName = "bndRegAcknow";

		// fetch checklist result if not fetch already
		if (birthModel.getCheckList().isEmpty()) {
			// call for fetch checklist based on Marriage Status (STA)
			getCheckListAndCharges(birthModel, request,null);
		}
		ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}
	
	@RequestMapping(params = "deathCertificateView", method = { RequestMethod.POST })
	public ModelAndView deathCertificateView(final Model model, HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		ModelAndView mv = null;
		String viewName = "ViewDeathCertificateDetails";
		mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());

		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(params = "searchDeathDetails", method = RequestMethod.POST)
	public List<DeathCertificateDTO> searchDeathDetails(HttpServletRequest request) {
		DeathCertificateModel model = this.getModel();
		model.bind(request);
		model.getDeathCertificateDTO().setOrgId(Utility.getOrgId());
		List<DeathCertificateDTO> searchConnectionDetails = iDeathCertificateService
				.searchDeathCertiDetails(model.getDeathCertificateDTO());
		if(null!=searchConnectionDetails)
			this.getModel().setSearchDeathCertiDtoList(searchConnectionDetails);
		this.getModel().getSearchDeathCertiDtoList().forEach(tr->{
			tr.getUploadDocument().forEach(doc->{
				if(tr .getDownloadPath()==null)
				 tr.setDownloadPath(Utility.getImageDetails((doc.getUploadedDocumentPath()+"\\"+doc.getDoc_DESC_ENGL()).replace("\\", "\\\\")));				 
			});
		});
		
		return searchConnectionDetails;
	}
	
	
	@RequestMapping(params = "viewDeathCertificate", method = { RequestMethod.POST })
	public ModelAndView viewDeathCertificate(@RequestParam("applicationNo") final Long applicationNo,
			final HttpServletRequest request) {
		bindModel(request);
		
		if(this.getModel().getSearchDeathCertiDtoList()!=null) {
			List<DeathCertificateDTO> deathDtoList = this.getModel().getSearchDeathCertiDtoList().stream().filter(dtoList -> (dtoList.getApplicationNo()!=null 
					&& dtoList.getApplicationNo().equals(applicationNo))).collect(Collectors.toList());
				
			if(CollectionUtils.isNotEmpty(deathDtoList) && deathDtoList.get(0)!=null)
				this.getModel().setDeathCertificateDTO(deathDtoList.get(0));
		}
		
		return new ModelAndView("ViewDeathCertificateDetForm", "command", getModel());
	}



}
