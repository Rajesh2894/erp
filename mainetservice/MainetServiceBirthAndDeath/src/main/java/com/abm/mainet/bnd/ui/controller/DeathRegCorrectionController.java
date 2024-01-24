package com.abm.mainet.bnd.ui.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.DeathRegCorrectionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
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
@RequestMapping("/DeathRegistrationCorrection.html")
public class DeathRegCorrectionController extends AbstractFormController<DeathRegCorrectionModel>{
	
	private static final Logger LOGGER = Logger.getLogger(DeathRegCorrectionController.class);
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		DeathRegCorrectionModel deathRegCorrectionModel=this.getModel();
		deathRegCorrectionModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
        //code update for signature on certificate based in registration unit
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		else 
			this.getModel().setKdmcEnv(MainetConstants.FlagN);
		 return new ModelAndView("DeathRegCorrection",MainetConstants.FORM_NAME, getModel());
		
	}

	
	@RequestMapping(params = "searchDeathCorrection", method = RequestMethod.POST,  produces = "Application/JSON")
	public @ResponseBody List<TbDeathregDTO> SearchDeathCorrectionData(@RequestParam("drCertNo") final String drCertNo, @RequestParam("applnId") 
			final Long applnId,@RequestParam("year") String year, @RequestParam("drRegno") final String drRegno,@RequestParam("drDod") final Date drDod,
			@RequestParam("drDeceasedname") final String drDeceasedname,
			final HttpServletRequest request,final Model model ){
		
		DeathRegCorrectionModel deathRegCorrectionModel=this.getModel();
		deathRegCorrectionModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathRegDataByStatus(drCertNo, applnId, year, drRegno,drDod, drDeceasedname,orgId);
		
		deathRegCorrectionModel.setTbDeathregDTOList(tbDeathRegDtoList);
		model.addAttribute("tbDeathRegDtoList", tbDeathRegDtoList);
		return getDeath(tbDeathRegDtoList);

	}
	
	private List<TbDeathregDTO> getDeath(List<TbDeathregDTO> deaths) {
		deaths.forEach(tbDeathRegDTO -> {
			if(tbDeathRegDTO.getCpdRegUnit()!=null) {
			tbDeathRegDTO.setCpdDesc(CommonMasterUtility.getCPDDescription(tbDeathRegDTO.getCpdRegUnit(), MainetConstants.BLANK));
			}
		});
		return deaths;
	}
	
	@ResponseBody
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model,@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long drID, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathById(drID));
		this.getModel().setSaveMode(mode);
		DeathRegCorrectionModel deathRegCorrectionModel = this.getModel();
		deathRegCorrectionModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		if(lookup.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
		{
			this.getModel().getTbDeathregDTO().setCheckStatus("A");
		}else {
			this.getModel().getTbDeathregDTO().setCheckStatus("NA");
		}
		if(lookup.getLookUpCode().equals("A"))
		{
			this.getModel().setChecklistStatus("A");
		}
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		else 
			this.getModel().setKdmcEnv(MainetConstants.FlagN);
		/*
		 * String occ = this.getModel().getTbDeathregDTO().getOccupation(); if(occ ==
		 * null || occ == "") {
		 * this.getModel().addValidationError(getApplicationSession().getMessage(
		 * "BirthRegistrationDTO.occupation")); }
		 */
		//serviceMas.getSmFeesSchedule()!=0 &&
		 if(serviceMas.getSmFeesSchedule()!=0) {
				this.getModel().getTbDeathregDTO().setChargeStatus("CC");
			}
		ModelAndView mv = new ModelAndView("DeathRegCorrectionValidn", MainetConstants.FORM_NAME, this.getModel());
		
		if(this.getModel().getSaveMode().equals("E"))
		{
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
		return mv;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		List<DocumentDetailsVO> finalCheckListList = new ArrayList<>();
		ModelAndView mv = null;
		final DeathRegCorrectionModel deathModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		deathModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
		
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		if(serviceMas.getSmFeesSchedule()!=0) {
			this.getModel().getTbDeathregDTO().setChargeStatus("CA");
		}
		
		if(this.getModel().getOfflineDTO().getAmountToShow()!=null) {
			if(this.getModel().getOfflineDTO().getAmountToShow()!=0 || !(this.getModel().getOfflineDTO().getAmountToShow()==0.0)) {
				this.getModel().getTbDeathregDTO().setAmount(this.getModel().getOfflineDTO().getAmountToShow());		
			} else {
				this.getModel().getTbDeathregDTO().setAmount(0.0);
			}
		} else {
			this.getModel().getTbDeathregDTO().setAmount(0.0);
		}
		
		if (lookup.getLookUpCode().equals("A")) {

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);

			long noOfDays = ideathregCorrectionService.CalculateNoOfDays(deathModel.getTbDeathregDTO());

			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("DRC");
			checkListModel.setNoOfDays(noOfDays);
			Map<String, List<DocumentDetailsVO>> responseMap = new LinkedHashMap<String, List<DocumentDetailsVO>>();
			if (deathModel.getTbDeathregDTO().getCorrCategory() != null
					&& !deathModel.getTbDeathregDTO().getCorrCategory().isEmpty()) {
				deathModel.getTbDeathregDTO().getCorrCategory().forEach(category -> {
					checkListModel.setUsageSubtype1(category);
					WSRequestDTO checklistReqDto1 = new WSRequestDTO();
					checklistReqDto1.setModelName("ChecklistModel");
					checklistReqDto1.setDataModel(checkListModel);

					WSResponseDTO checklistRespDto1 = brmsCommonService.getChecklist(checklistReqDto1);
					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto1.getWsStatus())) {
						responseMap.put(checklistRespDto1.getDocumentGroup(), (List<DocumentDetailsVO>)checklistRespDto1.getResponseObj());
					}

				});
				Collection<List<DocumentDetailsVO>> resp = responseMap.values();
				Iterator iterator = resp.iterator();
				while (iterator.hasNext()) {
					finalCheckListList.addAll((Collection<? extends DocumentDetailsVO>) iterator.next());
				}
				if ((finalCheckListList != null) && !finalCheckListList.isEmpty()) {
					long cnt = 1;
					for (final DocumentDetailsVO doc : finalCheckListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					deathModel.setCheckList(finalCheckListList);
				} else {
					// Message For Checklist
					this.getModel().addValidationError("No CheckList Found");
				}
				mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
			}
		else {
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
						 deathModel.setCheckList(checkListList);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
				} else {
					mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
				}

			}
		 }
		}
		}else {
			mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
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
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
    public @ResponseBody String  getBndCharges(@RequestParam("noOfCopies") int noOfCopies,@RequestParam("issuedCopy") int issuedCopy){
   	 BndRateMaster ratemaster=new BndRateMaster();
   	 String chargesAmount=null;
   	DeathRegCorrectionModel bndmodel = this.getModel();
   	bndmodel.setCommonHelpDocs("DeathRegistrationCorrection.html");
   	 WSResponseDTO certificateCharges=null;
        final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		 WSRequestDTO chargeReqDto = new WSRequestDTO();
        chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
        chargeReqDto.setDataModel(ratemaster);
        WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
          //ChargeDetailDTO chargeDetailDTO=new ChargeDetailDTO();
		  List<ChargeDetailDTO> chargesInfo=new ArrayList<>();
          List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
          BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
          rateMasterModel.setOrgId(orgIds);
          rateMasterModel.setServiceCode(BndConstants.DRC);
          rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                  MainetConstants.NewWaterServiceConstants.CAA,
                  UserSession.getCurrent().getOrganisation()).getLookUpId()));
          rateMasterModel.setOrganisationType(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                  UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                  UserSession.getCurrent().getOrganisation().getOrgid(),
                  MainetConstants.CommonMasterUi.OTY).getDescLangFirst());
          final WSRequestDTO taxRequestDto = new WSRequestDTO();
          taxRequestDto.setDataModel(rateMasterModel);
          WSResponseDTO responsefortax =null;
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
                  if(certificateCharges!=null) {
                  detailDTOs = (List<Object>) certificateCharges
                          .getResponseObj();
                  
                  BigDecimal totalAmount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
                 
                  for (final Object rate : detailDTOs) {
  					charges =(LinkedHashMap<String,String>)rate;
  					ChargeDetailDTO chargeDTO = new ChargeDetailDTO();
  					TbTaxMas taxMaster = tbTaxMasService.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
  					chargeDTO.setChargeCode(taxMaster.getTaxId());
  					chargeDTO.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathCorrSerRegName"));
  					chargeDTO.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathCorrSerEngName"));
  					chargeDTO.setChargeAmount(Double.valueOf(String.valueOf(charges.get(BndConstants.BNDCHARGES))));
  					totalAmount = totalAmount.add(new BigDecimal(chargeDTO.getChargeAmount()));
  					chargesInfo.add(chargeDTO); 
  				}
                  LOGGER.info("total amount in DeathRegCorrectionController:-"+totalAmount);
                  chargesAmount=String.valueOf(totalAmount);
                  }else {
                	  chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
                  }
        	  }else {
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
   	     return chargesAmount;
	 }
		return chargesAmount; 
 }
	
	  /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(DeathRegCorrectionModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.DRC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	    /* end */	
	    
	    @RequestMapping(params = "searchDeathDataForCorr", method = RequestMethod.POST,  produces = "Application/JSON")
		public @ResponseBody List<TbDeathregDTO> searchDeathDataForCorr(HttpServletRequest request,Model model) {
			
			DeathRegCorrectionModel deathRegCorrectionModel=this.getModel();
			getModel().bind(request);
			deathRegCorrectionModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
			TbDeathregDTO tbDeathregDTO = deathRegCorrectionModel.getTbDeathregDTO();
			tbDeathregDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid()); 
			List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathDataForCorr(deathRegCorrectionModel.getTbDeathregDTO());
			
			deathRegCorrectionModel.setTbDeathregDTOList(tbDeathRegDtoList);
			model.addAttribute("tbDeathRegDtoList", tbDeathRegDtoList);
			return getDeath(tbDeathRegDtoList);

		}
	    
	    @RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final DeathRegCorrectionModel deathModel = this.getModel();
	        ModelAndView mv = null;
	        if(deathModel.getTbDeathregDTO().getApmApplicationId()!=null) {
	        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC, UserSession.getCurrent().getOrganisation().getOrgid());
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
	        ackDto.setApplicationId(deathModel.getTbDeathregDTO().getApmApplicationId());
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
	       
	        // fetch checklist result if not fetch already
	        if (deathModel.getCheckList().isEmpty()) {
	            // call for fetch checklist based on Marriage Status (STA)
	            fetchCheckListForAck(deathModel,serviceMas);
	        }
	         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
	        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	    }
	        return mv;

	    }

		@SuppressWarnings({ "deprecation", "unchecked" })
		private void fetchCheckListForAck(DeathRegCorrectionModel deathModel, ServiceMaster serviceMas) {
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final WSRequestDTO requestDTO = new WSRequestDTO();
			requestDTO.setModelName(BndConstants.ChecklistModel);
			
			LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
			if (lookup.getLookUpCode().equals(BndConstants.CHECKLISTAPPLICABLE)) {
				WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
					final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
					final CheckListModel checkListModel = (CheckListModel) models.get(0);

					long noOfDays = ideathregCorrectionService.CalculateNoOfDays(deathModel.getTbDeathregDTO());

					checkListModel.setOrgId(orgId);
					checkListModel.setServiceCode(BndConstants.DRC);
					checkListModel.setNoOfDays(noOfDays);
					WSRequestDTO checklistReqDto = new WSRequestDTO();
					checklistReqDto.setModelName(BndConstants.ChecklistModel);
					checklistReqDto.setDataModel(checkListModel);
					WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

					if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
							|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

						if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
							List<DocumentDetailsVO> checkListList = Collections.emptyList();
							checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;
							
							if ((checkListList != null) && !checkListList.isEmpty()) {
								 deathModel.setCheckList(checkListList);
							} else {
								// Message For Checklist
								this.getModel().addValidationError("No CheckList Found");
							}

					}
				}
				}
			
		}
    }
		
	@RequestMapping(params = "getDeathDataById", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody TbDeathregDTO getDeathDataById(@RequestParam("drId") Long drId,
			final HttpServletRequest request) {
		getModel().bind(request);
		TbDeathregDTO deathDTO = ideathregCorrectionService.getDeathById(drId);
		return deathDTO;
	}
}