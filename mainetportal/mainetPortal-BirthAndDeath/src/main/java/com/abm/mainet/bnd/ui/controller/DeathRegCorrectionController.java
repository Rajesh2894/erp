package com.abm.mainet.bnd.ui.controller;
import java.io.IOException;
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
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.ui.model.DeathRegCorrectionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller
@RequestMapping("/DeathRegistrationCorrection.html")
public class DeathRegCorrectionController extends AbstractFormController<DeathRegCorrectionModel>{
	
	private static final Logger LOGGER = Logger.getLogger(DeathRegCorrectionController.class);
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private ICommonBRMSService brmsCommonService;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("DeathRegistrationCorrection.html");
		return new ModelAndView("DeathRegCorrection", MainetConstants.FORM_NAME, getModel());
		
	}

	
	@RequestMapping(params = "searchDeathCorrection", method = RequestMethod.POST,  produces = "Application/JSON")
	public @ResponseBody List<TbDeathregDTO> SearchDeathCorrectionData(@RequestParam("drCertNo") final String drCertNo, @RequestParam("applnId") 
			final Long applnId,@RequestParam("year") String year, @RequestParam("drRegno") final String drRegno,@RequestParam("drDod") final Date drDod,
			@RequestParam("drDeceasedname") final String drDeceasedname,
			final HttpServletRequest request,final Model model ){
		
		TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
		DeathRegCorrectionModel deathRegCorrectionModel=this.getModel();
		getModel().bind(request);
		tbDeathregDTO.setOrgId(Utility.getOrgId());
		tbDeathregDTO.setDrCertNo(drCertNo);
		tbDeathregDTO.setApmApplicationId(applnId);
		tbDeathregDTO.setYear(year);
		tbDeathregDTO.setDrRegno(drRegno);
		tbDeathregDTO.setDrDod(drDod);
		tbDeathregDTO.setDrDeceasedname(drDeceasedname);
		 List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathRegDataByStatus(tbDeathregDTO);
		
		deathRegCorrectionModel.setTbDeathregDTOList(tbDeathRegDtoList);
		model.addAttribute("tbDeathRegDtoList", tbDeathRegDtoList);
		return getDeath(tbDeathRegDtoList);//getDeath(tbDeathRegDtoList);

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
			@RequestParam("id") Long drID, final HttpServletRequest httpServletRequest) {
		
		this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathById(drID));
		this.getModel().getTbDeathregDTO().setDateOfDeath(Utility.dateToString(this.getModel().getTbDeathregDTO().getDrDod()));
		this.getModel().getTbDeathregDTO().setRegDate(Utility.dateToString(this.getModel().getTbDeathregDTO().getDrRegdate()));
		this.getModel().setSaveMode(mode);
		this.getModel().setCommonHelpDocs("DeathRegistrationCorrection.html");
		ModelAndView mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, this.getModel());
		if (this.getModel().getSaveMode().equals("E")) {
		if(this.getModel().getTbDeathregDTO().getDeathWFStatus().equals("OPEN")){
			this.getModel().setSaveMode("V");
			this.getModel()
					.addValidationError(getApplicationSession().getMessage("BirthRegistrationDTO.call.norecord"));
			return mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
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
		this.getModel().setCommonHelpDocs("DeathRegistrationCorrection.html");
		ModelAndView mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
		final DeathRegCorrectionModel deathModel = this.getModel();
		List<DocumentDetailsVO> finalCheckListList = new ArrayList<>();
		List<DocumentDetailsVO> finalCheckListList1 = new ArrayList<>();
		deathModel.getTbDeathregDTO().setDrDod(Utility.stringToDate(deathModel.getTbDeathregDTO().getDateOfDeath()));
		final Long orgId = Utility.getOrgId();
		deathModel.setCommonHelpDocs("DeathRegistrationCorrection.html");
		LinkedHashMap<String, Object> map = iBirthRegSevice
				.serviceInformation(Utility.getOrgId(), BndConstants.DRC);
		if (map.get("ChargesStatus") != null) {
			if (map.get("ChargesStatus").equals("CA")) {
				this.getModel().getTbDeathregDTO().setChargeStatus(map.get("ChargesStatus").toString());
			}
		}
	
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		
		
		//this.getModel().getTbDeathregDTO().setChargeStatus("CA");
		if(this.getModel().getOfflineDTO().getAmountToShow()!=null) {
			if(this.getModel().getOfflineDTO().getAmountToShow()!=0 || !(this.getModel().getOfflineDTO().getAmountToShow()==0.0)) {
				this.getModel().getTbDeathregDTO().setAmount(this.getModel().getOfflineDTO().getAmountToShow());		
			} else {
				this.getModel().getTbDeathregDTO().setAmount(0.0);
			}
		} else {
			this.getModel().getTbDeathregDTO().setAmount(0.0);
		}
		if (map.get("lookup").equals("A")) {
			
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = JersyCall.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			long noOfDays = ideathregCorrectionService.CalculateNoOfDays(deathModel.getTbDeathregDTO());
			Map<String, List<DocumentDetailsVO>> responseMap = new LinkedHashMap<String, List<DocumentDetailsVO>>();
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("DRC");
			checkListModel.setNoOfDays(noOfDays);
			
			if (deathModel.getTbDeathregDTO().getCorrCategory() != null
					&& !deathModel.getTbDeathregDTO().getCorrCategory().isEmpty()) {
				deathModel.getTbDeathregDTO().getCorrCategory().forEach(category -> {
					checkListModel.setUsageSubtype1(category);
					WSRequestDTO checklistReqDto1 = new WSRequestDTO();
					checklistReqDto1.setModelName("ChecklistModel");
					checklistReqDto1.setDataModel(checkListModel);
					final WSRequestDTO dto = new WSRequestDTO();
					dto.setDataModel(checkListModel);
				 WSResponseDTO response1 = JersyCall.callServiceBRMSRestClient(dto,
							ServiceEndpoints.BRMS_URL.CHECKLIST_URL);
					 //document = brmsCommonService.getChecklist(checkListModel);
				 if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response1.getWsStatus())
							|| MainetConstants.NewWaterServiceConstants.NA.equalsIgnoreCase(response1.getWsStatus())) {
						responseMap.put(response1.getDocumentGroup(), (List<DocumentDetailsVO>)response1.getResponseObj());
					}

				});
					Collection<List<DocumentDetailsVO>> resp = responseMap.values();
					Iterator iterator = resp.iterator();
					while (iterator.hasNext()) {
						finalCheckListList.addAll((Collection<? extends DocumentDetailsVO>) iterator.next());
					}
					 
				if ((finalCheckListList != null) && !finalCheckListList.isEmpty()) {
					List<DocumentDetailsVO> checklist = Collections.emptyList();
					final List<?> docs1 = this.castDocumentResponse(finalCheckListList, DocumentDetailsVO.class);
					checklist = (List<DocumentDetailsVO>) docs1;
					long count = 1;
					for (final DocumentDetailsVO doc : checklist) {
						doc.setDocumentSerialNo(count);
						count++;
					}
					deathModel.setCheckList(checklist);
				} else {
					// Message For Checklist
					this.getModel().addValidationError("No CheckList Found");
				}
				mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
			} else {
			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setModelName("ChecklistModel");
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
					deathModel.setCheckList(docs);
				}else {
					// Message For Checklist
					this.getModel().addValidationError("No CheckList Found");
				}
				mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
			}
			}
		}else {
				mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME, getModel());
			}
		}
		
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
    public @ResponseBody String  getBndCharges(@RequestParam("noOfCopies") int noOfCopies,@RequestParam("issuedCopy") int issuedCopy){
   	 BndRateMaster ratemaster=new BndRateMaster();
   	 String chargesAmount=null;
   	DeathRegCorrectionModel bndmodel = this.getModel();
   	bndmodel.getTbDeathregDTO().setDrDod(Utility.stringToDate(bndmodel.getTbDeathregDTO().getDateOfDeath()));
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
          rateMasterModel.setServiceCode(BndConstants.DRC);
          rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
          final WSRequestDTO taxRequestDto = new WSRequestDTO();
          taxRequestDto.setDataModel(rateMasterModel);
          WSResponseDTO responsefortax =null;
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
                  if(certificateCharges!=null) {
                  detailDTOs = (List<Object>) certificateCharges
                          .getResponseObj();
       
                  for (final Object rate : detailDTOs) {
               	    charges =(LinkedHashMap<String,String>)rate;
                	    break;
                     }
                  String certCharge= String.valueOf(charges.get(BndConstants.BNDCHARGES));
                  String appCharge = String.valueOf(charges.get(BndConstants.FLAT_RATE));
                  Double totalAmount= Double.valueOf(certCharge)+Double.valueOf(appCharge);
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
				if(certificateCharges!=null)
				chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathCorrSerRegName"));
				chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathCorrSerEngName"));
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
   	     return chargesAmount;
	 }
		return chargesAmount; 
 }
	
	  /* For populating RoadCuttingRateMaster Model for BRMS call */
	    private BndRateMaster populateChargeModel(DeathRegCorrectionModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(Utility.getOrgId());
	    	bndRateMaster.setServiceCode(BndConstants.DRC);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	        return bndRateMaster;
	    }
	    
	    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
		public ModelAndView dashboardView(@RequestParam("appId") final long appId,
				@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
			this.sessionCleanup(httpServletRequest);
			this.getModel().bind(httpServletRequest);
			this.getModel().getTbDeathregDTO().setDateOfDeath(Utility.dateToString(this.getModel().getTbDeathregDTO().getDrDod()));
			this.getModel().setCommonHelpDocs("DeathRegistrationCorrection.html");
			ModelAndView mv = null;
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathByApplId(appId, orgId));
			this.getModel().setSaveMode("V");
			this.getModel().setViewMode("V");
			List<DocumentDetailsVO>list=brmsCommonService.getChecklistDocument(String.valueOf(appId), orgId, "Y");
			this.getModel().setViewCheckList(list);
			mv = new ModelAndView("DeathRegCorrectionEditValidn", MainetConstants.FORM_NAME,  getModel());

			return mv;
		}
	    
	    @RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final DeathRegCorrectionModel birthModel = this.getModel();
	        LinkedHashMap<String, Object> map = iBirthRegSevice
					.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.DRC);
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
	       
	        // fetch checklist result if not fetch already
	        if (birthModel.getCheckList().isEmpty()) {
	            // call for fetch checklist based on Marriage Status (STA)
	            fetchCheckListForAck(birthModel,map);
	        }
	        ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
	        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	        return mv;

	    }


	private void fetchCheckListForAck(DeathRegCorrectionModel deathModel, LinkedHashMap<String, Object> map) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(BndConstants.CHECKLISTMODEL);
		if (map.get("lookup").equals(BndConstants.CHECKLISTAPPLICABLE)) {

			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> models = JersyCall.castResponse(response, CheckListModel.class, 0);
				final CheckListModel checkListModel = (CheckListModel) models.get(0);
				long noOfDays = ideathregCorrectionService.CalculateNoOfDays(deathModel.getTbDeathregDTO());

				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode(BndConstants.DRC);
				checkListModel.setNoOfDays(noOfDays);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(BndConstants.CHECKLISTMODEL);
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
						deathModel.setCheckList(docs);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}

				}
			}
		}
	}
	@SuppressWarnings("unchecked")
    public  List<Object> castDocumentResponse(final List<DocumentDetailsVO> finalCheckListList, final Class<?> clazz) {

        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
                final List<?> list = (List<?>) finalCheckListList;
                for (final Object object : list) {
                    responseMap = (LinkedHashMap<Long, Object>) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
        } catch (final IOException e) {
        	logger.error("Error Occurred during cast response to DocumentDetailsVO!", e);
        }

        return dataModelList;

    }
	
	@RequestMapping(params = "getDeathDataById", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody TbDeathregDTO getDeathDataById(@RequestParam("drId") Long drId,
			final HttpServletRequest request) {
		getModel().bind(request);
		TbDeathregDTO deathDTO = ideathregCorrectionService.getDeathById(drId);
		deathDTO.setDateOfDeath(Utility.dateToString(deathDTO.getDrDod()));
		return deathDTO;
	}
}
