package com.abm.mainet.bnd.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.constant.RtsConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IRtsService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.ui.model.DeathRegistrationCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

/**
 * 
 * @author bhagyashri.dongardive
 *
 */
@Controller
@RequestMapping("/IssuanceDeathCertificate.html")
public class IssuanceDeathCertificateController extends AbstractFormController<DeathRegistrationCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(IssuanceDeathCertificateController.class);
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;
	
	@Autowired
	private ICommonBRMSService brmsCommonService;
	
	@Autowired
	private IRtsService rtsService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("IssuanceDeathCertificate.html");
		return new ModelAndView("IssuenceOfDeathcertificate", MainetConstants.FORM_NAME, getModel());
		
	}
	
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model,@RequestParam("mode") String mode,
			@RequestParam("id") Long drID, final HttpServletRequest httpServletRequest) {
		
		this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathById(drID));
		this.getModel().getTbDeathregDTO().setDateOfDeath(Utility.dateToString(this.getModel().getTbDeathregDTO().getDrDod()));
		this.getModel().setSaveMode(mode);
		 getModel().bind(httpServletRequest);
		 this.getModel().setCommonHelpDocs("IssuanceDeathCertificate.html");
		 LinkedHashMap<String, Object> map = iBirthRegSevice
					.serviceInformation(Utility.getOrgId(), BndConstants.IDC);
			if (map.get("ChargesStatus") != null) {
				if (map.get("ChargesStatus").equals("CA")) {
					this.getModel().getTbDeathregDTO().setChargeStatus(map.get("ChargesStatus").toString());
				}
			}
	
	  ModelAndView mv = new ModelAndView("IssuanceDeathCertificateFormValidn", MainetConstants.FORM_NAME, this.getModel());
	  

		if(this.getModel().getTbDeathregDTO().getDeathWFStatus().equals("OPEN")){
			this.getModel().setSaveMode("V");
			this.getModel().addValidationError(getApplicationSession().getMessage(
					  "BirthRegistrationDTO.call.norecord"));
			 final BindingResult bindingResult = this.getModel().getBindingResult();
				return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
		}
			return mv;
		
 }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
    public @ResponseBody String  getBndCharges(@RequestParam("noOfCopies") int noOfCopies,@RequestParam("issuedCopy") int issuedCopy){
   	 BndRateMaster ratemaster=new BndRateMaster();
   	 String chargesAmount=BndConstants.CHARGES_AMOUNT;
   	  DeathRegistrationCertificateModel bndmodel = this.getModel();
   	 WSResponseDTO certificateCharges=null;
        final Long orgIds = Utility.getOrgId();
		 WSRequestDTO chargeReqDto = new WSRequestDTO();
        chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
        chargeReqDto.setDataModel(ratemaster);
        WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
          ChargeDetailDTO chargeDetailDTO=new ChargeDetailDTO();
   		  List<ChargeDetailDTO> chargesInfo=new ArrayList<>();
          List<Object> rateMaster = JersyCall.castResponse(response, BndRateMaster.class, 0);
          BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
          rateMasterModel.setOrgId(orgIds);
          rateMasterModel.setServiceCode(BndConstants.IDC);
          rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
          final WSRequestDTO taxRequestDto = new WSRequestDTO();
          taxRequestDto.setDataModel(rateMasterModel);
          WSResponseDTO responsefortax = null;
          try {
        		  responsefortax =ideathregCorrectionService.getApplicableTaxes(taxRequestDto);
           }catch(Exception ex) {
        	   chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
        	   return chargesAmount;
           }
          if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
        	  List<Object> detailDTOs=null;
        	  LinkedHashMap<String,String> charges=null;
        	  if (!responsefortax.isFree()) {
        		  List<Object> rates = JersyCall.castResponse(responsefortax, BndRateMaster.class, 0);
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
                  bndChagesRequestDto.setModelName(BndConstants.BND_RATE_MASTER);
                  certificateCharges=ideathregCorrectionService.getBndCharge(bndChagesRequestDto) ; 
                  if(certificateCharges != null) {
                  detailDTOs = (List<Object>) certificateCharges
                          .getResponseObj();
                  for (final Object rate : detailDTOs) {
               	    charges =(LinkedHashMap<String,String>)rate;
                	    break;
                     }
                  String certCharge= String.valueOf(charges.get(BndConstants.BNDCHARGES));
                  String appCharge = String.valueOf(charges.get(BndConstants.FLAT_RATE));
                  Double totalAmount= Double.valueOf(certCharge)+Double.valueOf(appCharge);
                  LOGGER.info("certificateCharge in IssuanceDeathCertificateController:-"+certCharge);
                  LOGGER.info("application charge in IssuanceDeathCertificateController:-"+appCharge);
                  LOGGER.info("total amount in IssuanceDeathCertificateController:-"+totalAmount);  
                  chargesAmount=String.valueOf(totalAmount);
                  }else {
                	  chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
                  }
        	  }else {
        		  chargesAmount=BndConstants.CHARGES_AMOUNT;
        	  }
        	    if(chargesAmount != null && !chargesAmount.equals(BndConstants.CHARGES_AMOUNT_FLG)) {
				chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
        	    }
        	    if(certificateCharges != null)
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerRegName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
				chargesInfo.add(chargeDetailDTO);
				bndmodel.setChargesInfo(chargesInfo);
				if(chargesAmount != null && !chargesAmount.equals(BndConstants.CHARGES_AMOUNT_FLG)) {
				bndmodel.setChargesAmount(chargesAmount);
				}
          }
	 }else {
		//when BRMS server off
		 chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
	 }
		return chargesAmount; 
 }
	
	 /* For populating BNDRateMaster Model for BRMS call */
	private BndRateMaster populateChargeModel(DeathRegistrationCertificateModel model,
    		BndRateMaster bndRateMaster) {
    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	bndRateMaster.setServiceCode(BndConstants.IDC);
    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
    	//bndRateMaster.setStartDate(new Date().getTime());
        return bndRateMaster;
    }
	
	@RequestMapping(params = "applyForDeathCert", method = { RequestMethod.GET, RequestMethod.POST },  produces = "Application/JSON")
	public ModelAndView applyForBirthCert(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("IssuanceDeathCertificate.html");
		return new ModelAndView("IssuenceOfDeathcertificateValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	 @RequestMapping(params = "proceed", method = RequestMethod.POST)
		public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
				final RedirectAttributes redirectAttributes) {
			this.getModel().bind(httpServletRequest);
			DeathRegistrationCertificateModel model = this.getModel();
			model.setCommonHelpDocs("IssuanceDeathCertificate.html");
			String shortCode = this.getModel().getRequestDTO().getServiceShortCode().replaceAll("[^a-zA-Z0-9\\s+]", "");
			this.getModel().getRequestDTO().setServiceShortCode(shortCode);
			
			if (CollectionUtils.isNotEmpty(model.getCheckList())) {
				List<DocumentDetailsVO> checkListList1 = rtsService.fetchDrainageConnectionDocsByAppNo(
						model.getApmApplicationId(),
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
			model.setApmApplicationId(model.getApmApplicationId());
			switch (shortCode) {
			case RtsConstants.RBC:
				model.setServiceName(RtsConstants.APPLY_FOR_BIRTH_CERTIFICATE_SERVICE);
				model.setFormName(RtsConstants.BIRTH_CERTIFICATE_FORMNAME);
				break;
			case RtsConstants.RDC:
				model.setServiceName(RtsConstants.APPLY_FOR_DEATH_CERTIFICATE_SERVICE);
				model.setFormName(RtsConstants.DEATH_CERTIFICATE_FORMNAME);
				break;
			}

			return new ModelAndView(BndConstants.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);

		}
	 
	 @RequestMapping(params = "showDetails", method = RequestMethod.POST)
		public ModelAndView dashboardView(@RequestParam("appId") final long appId,
				@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
			this.sessionCleanup(httpServletRequest);
			this.getModel().bind(httpServletRequest);
			this.getModel().getTbDeathregDTO().setDateOfDeath(Utility.dateToString(this.getModel().getTbDeathregDTO().getDrDod()));
			this.getModel().setCommonHelpDocs("IssuanceDeathCertificate.html");
			ModelAndView mv = null;
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathIssuanceApplId(appId, orgId));
			this.getModel().setSaveMode("V");
			this.getModel().setViewMode("V");
			mv = new ModelAndView("IssuanceDeathCertificateFormValidn", MainetConstants.FORM_NAME,  getModel());

			return mv;
		}
	 
	 @RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final DeathRegistrationCertificateModel birthModel = this.getModel();
	        LinkedHashMap<String, Object> map = iBirthRegSevice
					.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.IDC);
	        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	        ackDto.setApplicationId(birthModel.getTbDeathregDTO().getApmApplicationId());
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
	        if(map.get("smServiceDuration")!=null)
	        ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),Long.valueOf((String) map.get("smServiceDuration")).intValue()));
	        ackDto.setHelpLine(getApplicationSession().getMessage(
					  "bnd.acknowledgement.helplineNo"));
	        birthModel.setAckDto(ackDto);
	        
	        // runtime print acknowledge or certificate
	        String viewName = "bndRegAcknow";
	        ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
	        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	        return mv;

	    }
}
