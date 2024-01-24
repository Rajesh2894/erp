package com.abm.mainet.bnd.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.bnd.ui.model.DeathRegistrationCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author vishwanath.s
 *
 */
@Controller
@RequestMapping("/IssuanceDeathCertificate.html")
public class IssuanceDeathCertificateController extends AbstractFormController<DeathRegistrationCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(IssuanceDeathCertificateController.class);
	
	@Autowired
	private IssuenceOfDeathCertificateService issanceDeathSertiService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
        @Autowired
	private IdeathregCorrectionService ideathregCorrectionService;

	@Autowired
    private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Resource
	private ServiceMasterService serviceMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		DeathRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceDeathCertificate.html");
		return new ModelAndView("IssuenceOfDeathcertificate", MainetConstants.FORM_NAME, model);
	}
    //#150016
	@RequestMapping(params = "deathRegSearch",method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deathRegistrationSearchForCfc(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		DeathRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceDeathCertificate.html");
		this.getModel().setActionViewFlag(MainetConstants.FlagY);
		return new ModelAndView("IssuenceOfDeathcertificate", MainetConstants.FORM_NAME, model);
	}
	
	@RequestMapping(params = "searchDeathDetail", method = RequestMethod.POST)
	public ModelAndView searchDeathDataForCertificate(@RequestParam("drCertNo") String drCertNo,
			@RequestParam("drRegno") String drRegno, @RequestParam("year") String year,
			@RequestParam("applicationId") String applicationId) {
		DeathRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceDeathCertificate.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbDeathregDTO deathregDetail =issanceDeathSertiService.getDeathRegisteredAppliDetail(drCertNo, drRegno,year, applicationId,orgId);
		
		if(deathregDetail != null) {
	     model.setTbDeathregDTO(deathregDetail);
		}else {
			model=null;
		} 
		ModelAndView mv=new ModelAndView("IssuanceDeathCertificateFormValidn", MainetConstants.FORM_NAME, model);
		if(deathregDetail != null) {
        if((deathregDetail.getDrStatus().equals("Y")) && (deathregDetail.getDeathWFStatus().equals("OPEN"))) {
        	this.getModel().addValidationError("This application is in progress");
			final BindingResult bindingResult = this.getModel().getBindingResult();

				return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						"This application is in progress");

		 }
		}
		return mv;
	}
	

	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model,@RequestParam("mode") String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long drID, final HttpServletRequest httpServletRequest) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathById(drID));
		this.getModel().setSaveMode(mode);
		 getModel().bind(httpServletRequest);
		 this.getModel().setCommonHelpDocs("IssuanceDeathCertificate.html");
		 ServiceMaster serviceMas = serviceMasterService.getServiceMasterByShortCode(BndConstants.IDC, orgId);
		  if(serviceMas.getSmFeesSchedule()!=0) {
				this.getModel().getTbDeathregDTO().setChargeStatus("CA");
			}
		//DeathRegistrationCertificateModel deathRegistrationCertificateModel = this.getModel();
		//Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

	  ModelAndView mv = new ModelAndView("IssuanceDeathCertificateFormValidn", MainetConstants.FORM_NAME, this.getModel());
	  

		if(this.getModel().getTbDeathregDTO().getDeathWFStatus().equals("OPEN")){
			this.getModel().setSaveMode("V");
			this.getModel().addValidationError(getApplicationSession().getMessage(
					  "BirthRegistrationDTO.call.norecord"));
			//final BindingResult bindingResult = this.getModel().getBindingResult();
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
   	 bndmodel.setCommonHelpDocs("IssuanceDeathCertificate.html");
   	 WSResponseDTO certificateCharges=null;
        final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		 WSRequestDTO chargeReqDto = new WSRequestDTO();
        chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
        chargeReqDto.setDataModel(ratemaster);
        WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
          ChargeDetailDTO chargeDetailDTO=new ChargeDetailDTO();
   		  List<ChargeDetailDTO> chargesInfo=new ArrayList<>();
          List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
          BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
          rateMasterModel.setOrgId(orgIds);
          rateMasterModel.setServiceCode(BndConstants.IDC);
          rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                  MainetConstants.NewWaterServiceConstants.CAA,
                  UserSession.getCurrent().getOrganisation()).getLookUpId()));
          rateMasterModel.setOrganisationType(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                  UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                  UserSession.getCurrent().getOrganisation().getOrgid(),
                  MainetConstants.CommonMasterUi.OTY).getDescLangFirst());
          final WSRequestDTO taxRequestDto = new WSRequestDTO();
          taxRequestDto.setDataModel(rateMasterModel);
          WSResponseDTO responsefortax = null;
          try {
        		  responsefortax =issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
           }catch(Exception ex) {
        	   chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
        	   return chargesAmount;
           }
          ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
        		  orgIds);
          if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
        	  List<Object> detailDTOs=null;
        	  LinkedHashMap<String,String> charges=null;
        	  if (!responsefortax.isFree()) {
        		  final List<Object> rates =(List<Object>)responsefortax.getResponseObj();
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
                  certificateCharges=issuenceOfBirthCertificateService.getBndCharge(bndChagesRequestDto) ; 
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
        	    TbTaxMas taxMas=null;
    			if(certificateCharges != null) {
    				 taxMas=tbTaxMasService
    						.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
    			chargeDetailDTO.setChargeCode(taxMas.getTaxId());
    			}
        	   // if(certificateCharges != null)
				//chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode")).getTaxId()); // taxID
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
	
	  /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(DeathRegistrationCertificateModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.IDC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	    /* end */
	    
	@RequestMapping(params = "applyForDeathCert", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "Application/JSON")
	public ModelAndView applyForBirthCert(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		return new ModelAndView("IssuenceOfDeathcertificateValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        ModelAndView mv = null;
        final DeathRegistrationCertificateModel deathModel = this.getModel();
        if(StringUtils.isNotBlank(deathModel.getTbDeathregDTO().getApplicationNo())) {
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.IDC, UserSession.getCurrent().getOrganisation().getOrgid());
        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
        Long title = deathModel.getTbDeathregDTO().getRequestDTO().getTitleId();
        LookUp lokkup = null;
		if (deathModel.getTbDeathregDTO().getRequestDTO().getTitleId() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(deathModel.getTbDeathregDTO().getRequestDTO().getTitleId(),
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
        ackDto.setApplicationId(Long.valueOf(deathModel.getTbDeathregDTO().getApplicationNo()));
        ackDto.setApplicantName(String.join(" ", Arrays.asList(deathModel.getTbDeathregDTO().getRequestDTO().getfName(),
        		deathModel.getTbDeathregDTO().getRequestDTO().getmName(), deathModel.getTbDeathregDTO().getRequestDTO().getlName())));
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
         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;

    }	
	@SuppressWarnings("unused")
    @RequestMapping(params = "checkFileUpload", method = RequestMethod.POST)
    public @ResponseBody Boolean checkFileUpload(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        boolean flag = false;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            flag = true;
            break;
        }
        return flag;
    }
}
