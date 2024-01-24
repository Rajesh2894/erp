package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.InclusionOfChildNameService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.InclusionOfChildNameModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
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
@RequestMapping("/InclusionOfChildName.html")
public class InclusionOfChildNameController extends AbstractFormController<InclusionOfChildNameModel> {

	private static final Logger LOGGER = Logger.getLogger(InclusionOfChildNameController.class);
	
	@Autowired
	private InclusionOfChildNameService inclusionOfChildNameService;

	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		InclusionOfChildNameModel model = this.getModel();
		model.setCommonHelpDocs("InclusionOfChildName.html");
		// code update for signature on certificate based in registration unit
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		else
			this.getModel().setKdmcEnv(MainetConstants.FlagN);
		return new ModelAndView("InclusionOfChildNameCorr", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCertificate(@RequestParam("brCertNo") String brCertNo,
			@RequestParam("applicationId") String applicationId, @RequestParam("year") String year,@RequestParam("brDob") Date brDob,
			@RequestParam("brRegNo") String brRegNo) {

		InclusionOfChildNameModel appModel = this.getModel();
		appModel.setCommonHelpDocs("InclusionOfChildName.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegistrationDTO> registrationDetail = inclusionOfChildNameService.getBirthRegisteredAppliDetail(brCertNo,
				brRegNo, year,brDob, null, orgId);
		appModel.setBirthRegistrationDTOList(registrationDetail);
		//appModel.getBirthRegDto().setBrId(registrationDetail.getBrId());
		//appModel.getBirthRegDto().setBrRegDate(registrationDetail.getBrRegDate());
		//appModel.getBirthRegDto().setOrgId(orgId);
		return getBirth(registrationDetail);
	}
	
	
	
	@RequestMapping(params = "searchBirthDetails", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDetails(HttpServletRequest request,Model model) {
		getModel().bind(request);
		InclusionOfChildNameModel appModel = this.getModel();
		appModel.setCommonHelpDocs("InclusionOfChildName.html");
		BirthRegistrationDTO birthRegDto = appModel.getBirthRegDto();
		birthRegDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegiDetailForCorr(birthRegDto);
		appModel.setBirthRegistrationDTOList(registrationDetail);
		return getBirth(registrationDetail);
	}
	private List<BirthRegistrationDTO> getBirth(List<BirthRegistrationDTO> births) {
		births.forEach(registrationDetail -> {
			if(registrationDetail.getParentDetailDTO()!=null && registrationDetail.getParentDetailDTO().getPdRegUnitId()!=null) {
			registrationDetail.setCpdRegUnit(CommonMasterUtility.getCPDDescription(registrationDetail.getParentDetailDTO().getPdRegUnitId(),  MainetConstants.BLANK));
			}
			registrationDetail.setBrSex(CommonMasterUtility.getCPDDescription(Long.parseLong(registrationDetail.getBrSex()),  MainetConstants.BLANK));
			if(registrationDetail.getHiId()!=null && registrationDetail.getHiId()!=0L){
				
			HospitalMasterDTO hospital = iHospitalMasterService.getHospitalById(registrationDetail.getHiId());
			registrationDetail.setBrHospital(hospital.getHiName());
			}
		});
		return births;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final Model model) {
		getModel().bind(httpServletRequest);
		InclusionOfChildNameModel appModel = this.getModel();
		fileUpload.sessionCleanUpForFileUpload();
		ModelAndView mv = null;
		final InclusionOfChildNameModel inclusionOfChildModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		inclusionOfChildModel.setCommonHelpDocs("InclusionOfChildName.html");
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.INC,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		if(serviceMas.getSmFeesSchedule()!=0) {
			this.getModel().getBirthRegDto().setChargesStatus("CA");
		}
		
		
		if(this.getModel().getOfflineDTO().getAmountToShow()!=null) {	
			if(this.getModel().getOfflineDTO().getAmountToShow()!=0 || !(this.getModel().getOfflineDTO().getAmountToShow()==0.0)) {
				this.getModel().getBirthRegDto().setAmount(this.getModel().getOfflineDTO().getAmountToShow());		
			} else {
				this.getModel().getBirthRegDto().setAmount(0.0);
			}
		} else {
			this.getModel().getBirthRegDto().setAmount(0.0);
		}
		
		if (lookup.getLookUpCode().equals("A")) {
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);

			long noOfDays = inclusionOfChildNameService.CalculateNoOfDays(inclusionOfChildModel.getBirthRegDto());
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("INC");
			checkListModel.setNoOfDays(noOfDays);
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName("ChecklistModel");
			checklistReqDto.setDataModel(checkListModel);

			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;
					
					if ((checkListList != null) && !checkListList.isEmpty()) {
						inclusionOfChildModel.setCheckList(checkListList);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, getModel());
				} else {
					mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, getModel());
				}
			}
		}
		}else {
			mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, getModel());
		}

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
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
	@RequestMapping(params = "getBNDChargeForInclusion", method = RequestMethod.POST, produces = "Application/JSON")
     public @ResponseBody Map<String, Object>  getBndCharges(@RequestParam("noOfCopies") int noOfCopies,@RequestParam("issuedCopy") int issuedCopy){
		 BndRateMaster ratemaster=new BndRateMaster();
		 Map<String, Object> object = new LinkedHashMap<String, Object>();
    	 String chargesAmount=BndConstants.CHARGES_AMOUNT;
    	 InclusionOfChildNameModel bndmodel = this.getModel();
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
           rateMasterModel.setServiceCode(BndConstants.INC);
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
        		responsefortax =issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
            }catch(Exception ex) {
            	chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
            	 object.put("chargesAmount",chargesAmount);
            	return object;
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
                   Organisation org = UserSession.getCurrent().getOrganisation();
                   List<LookUp> lookUps = CommonMasterUtility.getLookUps("TXN",org);
                   lookUps = lookUps.stream()
                           .filter(look -> look.getLookUpCode() != null && (look.getLookUpCode().equals("CRS")
                                   || look.getLookUpCode().equals("CF") || look.getLookUpCode().equals("COPY")))
                           .collect(Collectors.toList());
                   certificateCharges=issuenceOfBirthCertificateService.getBndCharge(bndChagesRequestDto) ; 
                   if(certificateCharges != null) {
                   detailDTOs = (List<Object>) certificateCharges.getResponseObj();
                   BigDecimal totalAmount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
                   for (final Object rate : detailDTOs) {
                	    charges =(LinkedHashMap<String,String>)rate;
                	    ChargeDetailDTO chargeDTO = new ChargeDetailDTO();
 					TbTaxMas taxMaster = tbTaxMasService.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
 					chargeDTO.setChargeCode(taxMaster.getTaxId());
 					chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthRegDto.brBirthinclSerRegName"));
 					 chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthRegDto.brBirthinclSerEngName"));
 					chargeDTO.setChargeAmount(Double.valueOf(String.valueOf(charges.get(BndConstants.BNDCHARGES))));
 					 					if (CollectionUtils.isNotEmpty(lookUps)) {
									for(LookUp lookUp:lookUps) {
									if (lookUp.getLookUpCode().equals("CRS")) {
										if(lookUp.getDescLangFirst().equals(taxMaster.getTaxDesc())) {
										String serviceCharge = String.valueOf(chargeDTO.getChargeAmount());
										object.put("serviceCharge", serviceCharge);
										}
									} else if (lookUp.getLookUpCode().equals("COPY")||lookUp.getLookUpCode().equals("CF")) {
										if(lookUp.getDescLangFirst().equals(taxMaster.getTaxDesc())) {
										String certificateFee = String.valueOf(chargeDTO.getChargeAmount());
										object.put("certificateFee", certificateFee);
									}
									}
								   }
							   }
 					
 					totalAmount = totalAmount.add(new BigDecimal(chargeDTO.getChargeAmount()));
 					chargesInfo.add(chargeDTO);
                      }
                   LOGGER.info("total amount in InclusionOfChildNameController:-"+totalAmount);
                     chargesAmount=String.valueOf(totalAmount);
                   }else {
                	   chargesAmount=BndConstants.CHARGES_AMOUNT_FLG; 
                   }
         	  }
         	  else {
         		 chargesAmount=BndConstants.CHARGES_AMOUNT;
         	  }
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
         object.put("chargesAmount",chargesAmount);

     	return object;
  }
	 
	 /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(InclusionOfChildNameModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.INC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	    /* end */

	    
	    @SuppressWarnings("deprecation")
		@ResponseBody
		@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView editDeathreg(Model model,@RequestParam String mode,
				@RequestParam(MainetConstants.Common_Constant.ID) Long brID, final HttpServletRequest httpServletRequest) {
		 this.getModel().setSaveMode(mode);
		 this.getModel().setBirthRegDto(inclusionOfChildNameService.getBirthByID(brID));
		 Date date = this.getModel().getBirthRegDto().getBrDob();
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			 int day = calendar.get(Calendar.DAY_OF_MONTH);
		   LocalDate l = LocalDate.of(year, month, day); 
		   LocalDate now = LocalDate.now(); 
		   Period diff = Period.between(l, now);
		   int age = diff.getYears();
		   int days = diff.getDays();
		   int months = diff.getMonths();
		 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 getModel().bind(httpServletRequest);
		 InclusionOfChildNameModel appModel = this.getModel();
		 appModel.setCommonHelpDocs("InclusionOfChildName.html");
		 ModelAndView mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, this.getModel());
		 ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.INC, orgId);
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
			if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
			{
			this.getModel().getBirthRegDto().setStatusCheck("A");
			}
			else {
				this.getModel().getBirthRegDto().setStatusCheck("NA");
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
				this.getModel().setKdmcEnv(MainetConstants.FlagY);
			else
				this.getModel().setKdmcEnv(MainetConstants.FlagN);
			//lookUp.getLookUpCode().equals("A") && 
			 if(serviceMas.getSmFeesSchedule()!=0) {
					this.getModel().getBirthRegDto().setChargesStatus("CC");
				}

		 if(this.getModel().getSaveMode().equals("E"))
			{
		 if(this.getModel().getBirthRegDto().getBirthWfStatus().equals("OPEN")){
				this.getModel().setSaveMode("V");
				this.getModel().addValidationError(getApplicationSession().getMessage(
						  "BirthRegistrationDTO.call.norecord"));
				final BindingResult bindingResult = this.getModel().getBindingResult();
				return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
			}else if(age>=15){
				if(age==15) {
				if(days!=0 || months!=0) {
				this.getModel().setSaveMode("V");
				this.getModel().addValidationError(getApplicationSession().getMessage(
						  "bnd.child.valid.age"));
				return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
				}
			}else {
				this.getModel().setSaveMode("V");
				this.getModel().addValidationError(getApplicationSession().getMessage(
						  "bnd.child.valid.age"));
				return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
			}
			}
		 return mv;
		}
	
		 return mv ;
		 
		
		 
	 }
	    
	    @RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final InclusionOfChildNameModel birthModel = this.getModel();
	        ModelAndView mv = null;
	        if(birthModel.getBirthRegDto().getApmApplicationId()!=null) {
	        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.INC, UserSession.getCurrent().getOrganisation().getOrgid());
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
			/*
			 * if(title == 1) { ackDto.setApplicantTitle(MainetConstants.MR); } else
			 * if(title == 2) { ackDto.setApplicantTitle(MainetConstants.MRS); } else
			 * if(title == 3) { ackDto.setApplicantTitle(MainetConstants.MS); }
			 */
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
	        ackDto.setAppDate(new Date());
	        ackDto.setAppTime(new SimpleDateFormat("HH:mm").format(new Date()));
	        ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
	        ackDto.setHelpLine(getApplicationSession().getMessage("bnd.acknowledgement.helplineNo"));
	        birthModel.setAckDto(ackDto);
	        
	        // runtime print acknowledge or certificate
	        String viewName = "bndRegAcknow";
	       
	        // fetch checklist result if not fetch already
	        if (birthModel.getCheckList().isEmpty()) {
	            // call for fetch checklist based on Marriage Status (STA)
	            fetchCheckListForAck(birthModel,serviceMas);
	        }
	         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
	        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	    }
	        return mv;

	    }
	    
	    @SuppressWarnings({ "deprecation", "unchecked" })
		private void fetchCheckListForAck(InclusionOfChildNameModel birthRegModel,ServiceMaster serviceMas) {
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final WSRequestDTO requestDTO = new WSRequestDTO();
			requestDTO.setModelName(BndConstants.ChecklistModel);
			LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
			if (lookup.getLookUpCode().equals(BndConstants.CHECKLISTAPPLICABLE)) {
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
				final CheckListModel checkListModel = (CheckListModel) models.get(0);
				long noOfDays = iBirthRegSevice.CalculateNoOfDays(birthRegModel.getBirthRegDto());
				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode(BndConstants.INC);
				checkListModel.setNoOfDays(noOfDays);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(BndConstants.ChecklistModel);
				checklistReqDto.setDataModel(checkListModel);

				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
						|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
						List<DocumentDetailsVO> checkListList = Collections.emptyList();
						checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
						
						if ((checkListList != null) && !checkListList.isEmpty()) {
							birthRegModel.setCheckList(checkListList);
						} else {
							// Message For Checklist
							this.getModel().addValidationError("No CheckList Found");
						}
					}
				}
		  }
		 }
		}
	    
	   
}
