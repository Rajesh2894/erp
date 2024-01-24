package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.bnd.service.InclusionOfChildNameService;
import com.abm.mainet.bnd.ui.model.InclusionOfChildNameModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller
@RequestMapping("/InclusionOfChildName.html")
public class InclusionOfChildNameController extends AbstractFormController<InclusionOfChildNameModel> {

	@Autowired
	private IBirthRegService iBirthRegSevice;

	@Autowired
	private InclusionOfChildNameService inclusionOfChildNameService;
	
	@Autowired
	private ICommonBRMSService brmsCommonService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		InclusionOfChildNameModel model = this.getModel();
		this.getModel().setCommonHelpDocs("InclusionOfChildName.html");
		return new ModelAndView("InclusionOfChildNameCorr", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCertificate(
			@RequestParam("brCertNo") String brCertNo, @RequestParam("applicationId") String applicationId,
			@RequestParam("year") String year, @RequestParam("brDob") Date brDob,
			@RequestParam("brRegNo") String brRegNo) {
		InclusionOfChildNameModel appModel = this.getModel();
		appModel.setCommonHelpDocs("InclusionOfChildName.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegisteredAppliDetail(brCertNo, brRegNo,
				year, brDob, null, applicationId, orgId);
		appModel.setBirthRegistrationDTOList(registrationDetail);
		// model.addAttribute("birthList", registrationDetail);

		return getBirth(registrationDetail);
	}
	
	@RequestMapping(params = "searchBirthDetails", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDetails(HttpServletRequest request,Model model) {
		getModel().bind(request);
		getModel().setCommonHelpDocs("InclusionOfChildName.html");
		InclusionOfChildNameModel appModel = this.getModel();
		BirthRegistrationDTO birthRegDto = appModel.getBirthRegDto();
		birthRegDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegiDetailForCorr(birthRegDto);
		appModel.setBirthRegistrationDTOList(registrationDetail);
		return getBirth(registrationDetail);
	}

	private List<BirthRegistrationDTO> getBirth(List<BirthRegistrationDTO> births) {
		births.forEach(registrationDetail -> {
			if (registrationDetail.getParentDetailDTO() != null
					&& registrationDetail.getParentDetailDTO().getPdRegUnitId() != null) {
				registrationDetail.setCpdRegUnit(CommonMasterUtility.getCPDDescription(
						registrationDetail.getParentDetailDTO().getPdRegUnitId(), MainetConstants.BLANK));
			}
			registrationDetail.setBrSex(CommonMasterUtility
					.getCPDDescription(Long.parseLong(registrationDetail.getBrSex()), MainetConstants.BLANK));
			HospitalMasterDTO hospital = iBirthRegSevice.getHospitalById(registrationDetail.getHiId());
			if(hospital != null)
			registrationDetail.setBrHospital(hospital.getHiName());
		});
		return births;
	}

	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model, @RequestParam String mode, @RequestParam("id") Long brID,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(mode);
		getModel().setCommonHelpDocs("InclusionOfChildName.html");
		this.getModel().setBirthRegDto(inclusionOfChildNameService.getBirthByID(brID));
		this.getModel().getBirthRegDto().setBrDateOfBirth(Utility.dateToString(this.getModel().getBirthRegDto().getBrDob()));
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
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, this.getModel());
		
		if (this.getModel().getSaveMode().equals("E")) {
			if (this.getModel().getBirthRegDto().getBirthWfStatus().equals("OPEN")) {
				this.getModel().setSaveMode("V");
				this.getModel()
						.addValidationError(getApplicationSession().getMessage("BirthRegistrationDTO.call.norecord"));
				final BindingResult bindingResult = this.getModel().getBindingResult();
				return mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
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

		return mv;

	}
	

	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final Model model) {
		getModel().bind(httpServletRequest);
		List<DocumentDetailsVO> docs = null;
		ModelAndView mv = null;
		final InclusionOfChildNameModel inclusionOfChildModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		inclusionOfChildModel.setCommonHelpDocs("InclusionOfChildName.html");
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");

		LinkedHashMap<String, Object> map = iBirthRegSevice
				.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.INC);
		if (map.get("ChargesStatus") != null) {
			if (map.get("ChargesStatus").equals("CA")) {
				this.getModel().getBirthRegDto().setChargesStatus(map.get("ChargesStatus").toString());
			}
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
		
		if (map.get("lookup").equals("A")) {
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
			docs = brmsCommonService.getChecklist(checkListModel);
			if (docs != null && !docs.isEmpty()) {
				long cnt = 1;
				for (final DocumentDetailsVO doc : docs) {
					doc.setDocumentSerialNo(cnt);
					cnt++;
				}
			} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, getModel());
		} 
		}
		else {
			mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME, getModel());
		}

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	public  List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for(Object object:list) {
                    responseMap = (LinkedHashMap<Long, Object>) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
            }

        } catch (final IOException e) {
           
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
	        	 List<Object> rateMaster = JersyCall.castResponse(response, BndRateMaster.class, 0);
	           BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
	           rateMasterModel.setOrgId(orgIds);
	           rateMasterModel.setServiceCode(BndConstants.INC);
	           rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.RoadCuttingConstant.APL,
								MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
						.getLookUpId()));
			
	           final WSRequestDTO taxRequestDto = new WSRequestDTO();
	           taxRequestDto.setDataModel(rateMasterModel);
	           WSResponseDTO responsefortax = null;
	           try {
	        		responsefortax =iBirthRegSevice.getApplicableTaxes(taxRequestDto);
	            }catch(Exception ex) {
	            	chargesAmount=BndConstants.CHARGES_AMOUNT_FLG;
	            	 object.put("chargesAmount",chargesAmount);
	             	return object;	            }
	           if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
	        	   List<Object> detailDTOs=null;
	        	   LinkedHashMap<String,String> charges=null;
	         	  if (!responsefortax.isFree()) {
	         		 List<Object> rates = this.castResponse(responsefortax, BndRateMaster.class, 0);
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
	                   certificateCharges=iBirthRegSevice.getBndCharge(bndChagesRequestDto) ; 
	                   BigDecimal totalAmount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
	                   if(certificateCharges != null) {
	                   detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							ChargeDetailDTO chargeDTO = new ChargeDetailDTO();
							 LinkedHashMap<String, Object> taxMap = iBirthRegSevice.getTaxDescByTaxCode(orgIds,charges.get("taxCode"));
							chargeDetailDTO.setChargeDescReg(
									getApplicationSession().getMessage("BirthRegDto.brBirthinclSerRegName"));
							chargeDetailDTO.setChargeDescEng(
									getApplicationSession().getMessage("BirthRegDto.brBirthinclSerEngName"));
							chargeDTO.setChargeAmount(
									Double.valueOf(String.valueOf(charges.get(BndConstants.BNDCHARGES))));
							//#157676 set taxId
                            String taxid = String.valueOf(taxMap.get("taxId"));
							chargeDTO.setChargeCode(Long.valueOf(taxid));
							if (CollectionUtils.isNotEmpty(lookUps)) {
								for(LookUp lookUp:lookUps) {
									if (lookUp.getLookUpCode().equals("CRS")) {
										if(lookUp.getLookUpDesc().equals(String.valueOf(taxMap.get("taxName")))) {
										String serviceCharge = String.valueOf(chargeDTO.getChargeAmount());
										object.put("serviceCharge", serviceCharge);
										}
									} else if (lookUp.getLookUpCode().equals("COPY")||lookUp.getLookUpCode().equals("CF")) {
										if(lookUp.getLookUpDesc().equals(String.valueOf(taxMap.get("taxName")))) {
										String certificateFee = String.valueOf(chargeDTO.getChargeAmount());
										object.put("certificateFee", certificateFee);
										}
									}
								}
							}

							totalAmount = totalAmount.add(new BigDecimal(chargeDTO.getChargeAmount()));
							chargesInfo.add(chargeDTO);
						}
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
		 
	    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
		public ModelAndView dashboardView(@RequestParam("appId") final long appId,
				@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
			this.sessionCleanup(httpServletRequest);
			this.getModel().bind(httpServletRequest);
			this.getModel().getBirthRegDto().setBrDateOfBirth(Utility.dateToString(this.getModel().getBirthRegDto().getBrDob()));
			this.getModel().setCommonHelpDocs("InclusionOfChildName.html");
			ModelAndView mv = null;
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByApplId(appId,orgId));
			this.getModel().setSaveMode("V");
			this.getModel().setViewMode("V");
			List<DocumentDetailsVO>list=brmsCommonService.getChecklistDocument(String.valueOf(appId), orgId, "Y");
			this.getModel().setViewCheckList(list);
			mv = new ModelAndView("InclusionOfChildNameValidn", MainetConstants.FORM_NAME,  getModel());

			return mv;
		}
	    
		@RequestMapping(params = "printBndAcknowledgement", method = { RequestMethod.POST })
		public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
			bindModel(request);
			final InclusionOfChildNameModel birthModel = this.getModel();
			LinkedHashMap<String, Object> map = iBirthRegSevice
					.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.INC);
			BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
			ackDto.setApplicationId(birthModel.getBirthRegDto().getApmApplicationId());
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
				fetchCheckListForAck(birthModel, map);
			}
			ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;

		}

	private void fetchCheckListForAck(InclusionOfChildNameModel birthModel, LinkedHashMap<String, Object> map) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<DocumentDetailsVO> docs = null;
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(BndConstants.CHECKLISTMODEL);
		if (map.get("lookup").equals(BndConstants.CHECKLISTAPPLICABLE)) {
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> models = JersyCall.castResponse(response, CheckListModel.class, 0);
				final CheckListModel checkListModel = (CheckListModel) models.get(0);

				long noOfDays = inclusionOfChildNameService.CalculateNoOfDays(birthModel.getBirthRegDto());
				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode(BndConstants.INC);
				checkListModel.setNoOfDays(noOfDays);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(BndConstants.CHECKLISTMODEL);
				checklistReqDto.setDataModel(checkListModel);
				docs = brmsCommonService.getChecklist(checkListModel);
				if (docs != null && !docs.isEmpty()) {
					long cnt = 1;
					for (final DocumentDetailsVO doc : docs) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					this.getModel().setCheckList(docs);
				}else {
					// Message For Checklist
					this.getModel().addValidationError("No CheckList Found");
				}
			}
		}
	}
	
	
}
