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
import com.abm.mainet.bnd.dto.BirthReceiptDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.BirthRegistrationCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
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
@Controller
@RequestMapping("/IssuanceBirthCertificate.html")
public class IssuanceBirthCertificateController extends AbstractFormController<BirthRegistrationCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(IssuanceBirthCertificateController.class);
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;

	@Autowired
    private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		BirthRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceBirthCertificate.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
			try {
				model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
			} catch (Exception e) {
				throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
			 }
			}else {
				model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
		return new ModelAndView("IssuenceOfBirthCert", MainetConstants.FORM_NAME, model);
	}
	//#150016
	@RequestMapping(params = "birthRegSearch", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView birthRegistrationSearchForCfc(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		BirthRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceBirthCertificate.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
			try {
				model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
			} catch (Exception e) {
				throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
			 }
			}else {
				model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
		this.getModel().setActionViewFlag(MainetConstants.FlagY);
		return new ModelAndView("IssuenceOfBirthCert", MainetConstants.FORM_NAME, model);
	}
	
	@RequestMapping(params = "birthReceiptService", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView birthReceiptService(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		BirthRegistrationCertificateModel model = this.getModel();
		model.setCommonHelpDocs("IssuanceBirthCertificate.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
			try {
				//model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
			} catch (Exception e) {
				throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
			 }
			}else {
				//model.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
		return new ModelAndView("birthReceiptService", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody BirthRegistrationDTO searchBirthDataForCertificate(@RequestParam("brCertNo") String brCertNo,
			@RequestParam("applicationId") String applicationId, @RequestParam("year") String year,
			@RequestParam("brRegNo") String brRegNo) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		BirthRegistrationDTO registrationDetail = issuenceOfBirthCertificateService
				.getBirthRegisteredAppliDetail(brCertNo, brRegNo, year, applicationId, orgId);
		return registrationDetail;
	}
	
        @RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model,@RequestParam("mode") String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long brID, final HttpServletRequest httpServletRequest) {
	this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByID(brID));
	 getModel().bind(httpServletRequest);
	 this.getModel().setCommonHelpDocs("IssuanceBirthCertificate.html");
	  ModelAndView mv = new ModelAndView("IssuanceOfBirthCertificateFormValidn", MainetConstants.FORM_NAME, this.getModel());
	  final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	  ServiceMaster serviceMas = serviceMasterService.getServiceMasterByShortCode(BndConstants.IBC, orgId);
	  if(serviceMas.getSmFeesSchedule()!=0) {
			this.getModel().getBirthRegDto().setChargesStatus("CA");
		}
			if(this.getModel().getBirthRegDto().getBirthWfStatus().equals("OPEN")){
				String status = iBirthRegSevice.getBirthAppn(this.getModel().getBirthRegDto().getBrId(), orgId);
                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {	
				this.getModel().setSaveMode("V");
				this.getModel().addValidationError(getApplicationSession().getMessage(
						  "BirthRegistrationDTO.call.norecord"));
				final BindingResult bindingResult = this.getModel().getBindingResult();
					return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							this.getModel().getBindingResult());
                }
			}
			return mv;
		
 }	

	 @SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
     public @ResponseBody String  getBndCharges(@RequestParam("noOfCopies") int noOfCopies,@RequestParam("issuedCopy") int issuedCopy){
    	 BndRateMaster ratemaster=new BndRateMaster();
    	 String chargesAmount=BndConstants.CHARGES_AMOUNT;
    	 BirthRegistrationCertificateModel bndmodel = this.getModel();
    	 bndmodel.setCommonHelpDocs("IssuanceBirthCertificate.html");
    	 WSResponseDTO certificateCharges=null;
         final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		 WSRequestDTO chargeReqDto = new WSRequestDTO();
         chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
         chargeReqDto.setDataModel(ratemaster);
         WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
         ChargeDetailDTO chargeDetailDTO=new ChargeDetailDTO();
  		  List<ChargeDetailDTO> chargesInfo=new ArrayList<>();
         if (response.getWsStatus() != null
                 && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
           List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
           BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
           rateMasterModel.setOrgId(orgIds);
           rateMasterModel.setServiceCode(BndConstants.IBC);
           rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                   BndConstants.CAA,
                   UserSession.getCurrent().getOrganisation()).getLookUpId()));
           rateMasterModel.setOrganisationType(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                   UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                   UserSession.getCurrent().getOrganisation().getOrgid(),
                   MainetConstants.CommonMasterUi.OTY).getDescLangFirst());
           final WSRequestDTO taxRequestDto = new WSRequestDTO();
           taxRequestDto.setDataModel(rateMasterModel);
           WSResponseDTO responsefortax = null;
           try {
           responsefortax = issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
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
                   detailDTOs = (List<Object>) certificateCharges.getResponseObj();
                   for (final Object rate : detailDTOs) {
                	    charges =(LinkedHashMap<String,String>)rate;
                 	    break;
                      }
                   String certCharge= String.valueOf(charges.get(BndConstants.BNDCHARGES));
                   String appCharge = String.valueOf(charges.get(BndConstants.FLAT_RATE));
                   Double totalAmount= Double.valueOf(certCharge)+Double.valueOf(appCharge);
                   LOGGER.info("certificateCharge in IssuanceBirthCertificateController:-"+certCharge);
                   LOGGER.info("application charge in IssuanceBirthCertificateController:-"+appCharge);
                   LOGGER.info("total amount in IssuanceBirthCertificateController:-"+totalAmount);  
                   chargesAmount=String.valueOf(totalAmount);
                   }else {
                	   chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
                   }
         	  }
         	  else {
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
			// chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode")).getTaxId());
			 chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthRegDto.brBirthIssSerRegName"));
			 chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthRegDto.brBirthIssSerEngName"));
			 chargesInfo.add(chargeDetailDTO);
			 bndmodel.setChargesInfo(chargesInfo);
			 if(chargesAmount != null && !chargesAmount.equals(BndConstants.CHARGES_AMOUNT_FLG)) {
			 bndmodel.setChargesAmount(chargesAmount);
			 this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
			 }
           }
	 }else {
		//when BRMS server off
		 chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
	 }
		return chargesAmount; 
  }
	
	  /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(BirthRegistrationCertificateModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.IBC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	    /* end */
	    
	@RequestMapping(params = "applyForBirthCert", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "Application/JSON")
	public ModelAndView applyForBirthCert(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		return new ModelAndView("IssuenceOfBirthCertValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        final BirthRegistrationCertificateModel birthModel = this.getModel();
        ModelAndView mv = null;
        if(birthModel.getBirthRegDto().getApmApplicationId()!=null) {
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.IBC, UserSession.getCurrent().getOrganisation().getOrgid());
        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
        Long title = birthModel.getBirthRegDto().getRequestDTO().getTitleId();
        LookUp lokkup = null;
        if (birthModel.getBirthRegDto().getRequestDTO().getTitleId() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(birthModel.getBirthRegDto().getRequestDTO().getTitleId(),
					UserSession.getCurrent().getOrganisation().getOrgid(), "TTL");
		}
		if(lokkup!=null) {
			ackDto.setApplicantTitle(lokkup.getLookUpDesc());
		}
        ackDto.setApplicationId(birthModel.getBirthRegDto().getApmApplicationId());
        ackDto.setApplicantName(String.join(" ", Arrays.asList(birthModel.getBirthRegDto().getRequestDTO().getfName(),
        		birthModel.getBirthRegDto().getRequestDTO().getmName(), birthModel.getBirthRegDto().getRequestDTO().getlName())));
        if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
        	ackDto.setServiceShortCode(serviceMas.getSmServiceName());
        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpDeptdesc());
        }else {
        	ackDto.setServiceShortCode(serviceMas.getSmServiceNameMar());
        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpNameMar());
        }
        /*
		 * if(title == 1) { ackDto.setApplicantTitle(MainetConstants.MR); } else
		 * if(title == 2) { ackDto.setApplicantTitle(MainetConstants.MRS); } else
		 * if(title == 3) { ackDto.setApplicantTitle(MainetConstants.MS); }
		 */
        ackDto.setAppDate(new Date());
        ackDto.setAppTime(new SimpleDateFormat("HH:mm").format(new Date()));
        ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
        ackDto.setHelpLine(getApplicationSession().getMessage("bnd.acknowledgement.helplineNo"));
        birthModel.setAckDto(ackDto);
        
        // runtime print acknowledge or certificate
        String viewName = "bndRegAcknow";
       
         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;

    }
	
	@RequestMapping(params = "searchBirthReceiptData", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthReceiptDTO> searchBirthReceiptData(HttpServletRequest request,Model model) {
      
		getModel().bind(request);
		BirthRegistrationCertificateModel appModel = this.getModel();
		BirthReceiptDTO birthReceiptData = appModel.getBirthReceiptData();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<BirthReceiptDTO> registrationDetail = iBirthRegSevice.getBirethReceiptData(birthReceiptData);
		
		return registrationDetail;
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
