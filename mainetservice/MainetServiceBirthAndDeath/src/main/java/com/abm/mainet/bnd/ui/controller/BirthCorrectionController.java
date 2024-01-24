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
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.BirthCorrectionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
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
@RequestMapping("/BirthCorrectionForm.html")
public class BirthCorrectionController extends AbstractFormController<BirthCorrectionModel> {
	private static Logger LOGGER = Logger.getLogger(BirthCorrectionController.class);
	

	@Autowired
	private IBirthRegService iBirthRegSevice;

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
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;

	private static final Logger logger = Logger.getLogger(BirthCorrectionController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		BirthCorrectionModel appModel = this.getModel();
		appModel.setCommonHelpDocs("BirthCorrectionForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		try {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
				List<HospitalMasterDTO> hospitalList = appModel
						.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
				model.addAttribute("hospitalList", hospitalList);
			} else {
				appModel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
           //code update for signature on certificate based in registration unit
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
				this.getModel().setKdmcEnv(MainetConstants.FlagY);
			else 
				this.getModel().setKdmcEnv(MainetConstants.FlagN);
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		 }
		
		return new ModelAndView("BirthRegCorrection", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCertificate(@RequestParam("brCertNo") String brCertNo,
			@RequestParam("applnId") String applnId, @RequestParam("year") String year,
			@RequestParam("brRegNo") String brRegNo,@RequestParam("brDob") Date brDob,@RequestParam("brChildName") String brChildName ,final Model model) {
      
		
		BirthCorrectionModel appModel = this.getModel();
		appModel.setCommonHelpDocs("BirthCorrectionForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegisteredAppliDetail(brCertNo, brRegNo, year,brDob,brChildName,
				applnId, orgId);
               appModel.setBirthRegistrationDTOList(registrationDetail);
		model.addAttribute("birthList", registrationDetail);
		//appModel.getBirthRegDto().setBrId(registrationDetail.getBrId());
		return getBirth(registrationDetail);
	}
	
	
	private List<BirthRegistrationDTO> getBirth(List<BirthRegistrationDTO> births) {
		births.forEach(registrationDetail -> {
			if(registrationDetail.getParentDetailDTO()!=null && registrationDetail.getParentDetailDTO().getPdRegUnitId()!=null) {
			registrationDetail.setCpdRegUnit(CommonMasterUtility.getCPDDescription(registrationDetail.getParentDetailDTO().getPdRegUnitId(),  MainetConstants.BLANK));
			}
			registrationDetail.setBrSex(CommonMasterUtility.getCPDDescription(Long.parseLong(registrationDetail.getBrSex()),  MainetConstants.BLANK));
		});
		return births;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final Model model) {
		getModel().bind(httpServletRequest);
		List<DocumentDetailsVO> finalCheckListList = new ArrayList<>();
		//BirthCorrectionModel appModel = this.getModel();
		fileUpload.sessionCleanUpForFileUpload();
		ModelAndView mv = null;
		final BirthCorrectionModel birthRegModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		birthRegModel.setCommonHelpDocs("BirthCorrectionForm.html");

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC,orgId);
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

			long noOfDays = iBirthRegSevice.CalculateNoOfDays(birthRegModel.getBirthRegDto());
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("BRC");
			checkListModel.setNoOfDays(noOfDays);
			Map<String, List<DocumentDetailsVO>> responseMap = new LinkedHashMap<String, List<DocumentDetailsVO>>();
			
				if (birthRegModel.getBirthRegDto().getCorrCategory() != null
						&& !birthRegModel.getBirthRegDto().getCorrCategory().isEmpty()) {
					birthRegModel.getBirthRegDto().getCorrCategory().forEach(category -> {
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
						birthRegModel.setCheckList(finalCheckListList);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
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
						birthRegModel.setCheckList(checkListList);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
				} else {
					mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
				}
			}
		}
				}
		
		}else {
			mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

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
   	 BirthCorrectionModel bndmodel = this.getModel();
   	 bndmodel.setCommonHelpDocs("BirthCorrectionForm.html");
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
          rateMasterModel.setServiceCode(BndConstants.BRC);
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
          }catch(Exception ex){
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
                  
                  BigDecimal totalAmount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
                  for (final Object rate : detailDTOs) {
               	    charges =(LinkedHashMap<String,String>)rate;
               	    ChargeDetailDTO chargeDTO = new ChargeDetailDTO();
					TbTaxMas taxMaster = tbTaxMasService.getTaxMasterByTaxCode(orgIds, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
					chargeDTO.setChargeCode(taxMaster.getTaxId());
					chargeDTO.setChargeDescReg(getApplicationSession().getMessage("BirthRegDto.brBirthCorrSerRegName"));
					chargeDTO.setChargeDescEng(getApplicationSession().getMessage("BirthRegDto.brBirthCorrSerEngName"));
					chargeDTO.setChargeAmount(Double.valueOf(String.valueOf(charges.get(BndConstants.BNDCHARGES))));
					totalAmount = totalAmount.add(new BigDecimal(chargeDTO.getChargeAmount()));
					chargesInfo.add(chargeDTO);
                     }
                 
                  LOGGER.info("total amount in BirthCorrectionController:-"+totalAmount);
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
	 }
		return chargesAmount; 
 }
	
	  /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(BirthCorrectionModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.BRC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	    /* end */	


         //@ResponseBody
		@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView editDeathreg(Model model,@RequestParam("mode") String mode,
				@RequestParam(MainetConstants.Common_Constant.ID) Long brID, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		 this.getModel().setSaveMode(mode);
		this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByID(brID));
		// BirthRegistrationDTO birthRegDto=iBirthRegSevice.getBirthByID(brID);
		 
		 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 getModel().bind(httpServletRequest);

		 BirthCorrectionModel birthCorrectionModel = this.getModel();
		 birthCorrectionModel.setCommonHelpDocs("BirthCorrectionForm.html");
		  ModelAndView mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, this.getModel());
		  ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC, orgId);
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
			if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
			{
			this.getModel().getBirthRegDto().setStatusCheck("A");
			}
			else {
				this.getModel().getBirthRegDto().setStatusCheck("NA");
			}
            //code update for signature on certificate based in registration unit
			if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
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
					String status = iBirthRegSevice.getBirthAppn(this.getModel().getBirthRegDto().getBrId(), orgId);
	                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {		
					this.getModel().setSaveMode("V");
					this.getModel().addValidationError(getApplicationSession().getMessage(
							  "BirthRegistrationDTO.call.norecord"));
					final BindingResult bindingResult = this.getModel().getBindingResult();
						return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								this.getModel().getBindingResult());
	                }
	                return mv;
				}
				return mv;
			}
			return mv;
		 
	 }
		
		@RequestMapping(params = "searchBirthDetailForCorr", method = RequestMethod.POST, produces = "Application/JSON")
		public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCorr(HttpServletRequest request,Model model) {
	      
			getModel().bind(request);
			BirthCorrectionModel appModel = this.getModel();
			BirthRegistrationDTO birthRegDto = appModel.getBirthRegDto();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			birthRegDto.setOrgId(orgId);
			List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegiDetailForCorr(birthRegDto);
			   appModel.setBirthRegistrationDTOList(registrationDetail);
			model.addAttribute("birthList", registrationDetail);
			return getBirth(registrationDetail);
		}
		
		
		@RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final BirthCorrectionModel birthModel = this.getModel();
	        ModelAndView mv = null;
	        if(birthModel.getBirthRegDto().getApmApplicationId()!=null) {
	        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC, UserSession.getCurrent().getOrganisation().getOrgid());
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
		private void fetchCheckListForAck(BirthCorrectionModel birthRegModel,ServiceMaster serviceMas) {
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
				checkListModel.setServiceCode(BndConstants.BRC);
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
		
		@RequestMapping(params = "getChildNameById", method = RequestMethod.POST, produces = "Application/JSON")
		public @ResponseBody BirthRegistrationDTO getChildNameById(@RequestParam("brId") Long brId,final HttpServletRequest request) {
			getModel().bind(request);
			BirthRegistrationDTO birthDTO = iBirthRegSevice.getBirthByID(brId);
			return birthDTO;
		}
		

}
