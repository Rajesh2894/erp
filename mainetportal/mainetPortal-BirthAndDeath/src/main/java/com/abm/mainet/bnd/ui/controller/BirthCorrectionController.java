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
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.ui.model.BirthCorrectionModel;
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
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller
@RequestMapping("BirthCorrectionForm.html")
public class BirthCorrectionController extends AbstractFormController<BirthCorrectionModel> {

	@Autowired
	private IBirthRegService iBirthRegSevice;

	@Autowired
	private ICommonBRMSService brmsCommonService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("BirthCorrectionForm.html");
		return new ModelAndView("BirthRegCorrection", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCertificate(
			@RequestParam("brCertNo") String brCertNo, @RequestParam("applnId") String applnId,
			@RequestParam("year") String year, @RequestParam("brRegNo") String brRegNo,
			@RequestParam("brDob") Date brDob, @RequestParam("brChildName") String brChildName, final Model model) {

		BirthCorrectionModel appModel = this.getModel();
		appModel.setCommonHelpDocs("BirthCorrectionForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegisteredAppliDetail(brCertNo, brRegNo,
				year, brDob, brChildName, applnId, orgId);
		appModel.setBirthRegistrationDTOList(registrationDetail);
		model.addAttribute("birthList", registrationDetail);

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
		});
		return births;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model, @RequestParam("mode") String mode, @RequestParam("id") Long brID,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(mode);
		this.getModel().setCommonHelpDocs("BirthCorrectionForm.html");
		this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByID(brID));
		this.getModel().getBirthRegDto().setBrDateOfBirth(Utility.dateToString(this.getModel().getBirthRegDto().getBrDob()));
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, this.getModel());

		if (this.getModel().getSaveMode().equals("E")) {
			if (this.getModel().getBirthRegDto().getBirthWfStatus().equals("OPEN")) {
				this.getModel().setSaveMode("V");
				this.getModel()
						.addValidationError(getApplicationSession().getMessage("BirthRegistrationDTO.call.norecord"));
				final BindingResult bindingResult = this.getModel().getBindingResult();
				return mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
			}
			return mv;
		}
		return mv;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getBNDCharge", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody String getBndCharges(@RequestParam("noOfCopies") int noOfCopies,
			@RequestParam("issuedCopy") int issuedCopy) {
		BndRateMaster ratemaster = new BndRateMaster();
		String chargesAmount = null;
		BirthCorrectionModel bndmodel = this.getModel();
		bndmodel.getBirthRegDto().setBrDob(Utility.stringToDate(bndmodel.getBirthRegDto().getBrDateOfBirth()));
		bndmodel.setCommonHelpDocs("BirthCorrectionForm.html");
		WSResponseDTO certificateCharges = null;
		final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
		WSRequestDTO chargeReqDto = new WSRequestDTO();
		chargeReqDto.setModelName(BndConstants.BND_RATE_MASTER);
		chargeReqDto.setDataModel(ratemaster);
		WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			List<Object> rateMaster = JersyCall.castResponse(response, BndRateMaster.class, 0);
			BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
			rateMasterModel.setOrgId(orgIds);
			rateMasterModel.setServiceCode(BndConstants.BRC);
			rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.RoadCuttingConstant.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = iBirthRegSevice.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = BndConstants.CHARGES_AMOUNT_FLG;
				return chargesAmount;
			}
		
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
				List<Object> detailDTOs = null;
				LinkedHashMap<String, String> charges = null;
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
					certificateCharges = iBirthRegSevice.getBndCharge(bndChagesRequestDto);
					if (certificateCharges != null) {
						detailDTOs = (List<Object>) certificateCharges.getResponseObj();
						for (final Object rate : detailDTOs) {
							charges = (LinkedHashMap<String, String>) rate;
							break;
						}
						String certCharge= String.valueOf(charges.get(BndConstants.BNDCHARGES));
		                  String appCharge = String.valueOf(charges.get(BndConstants.FLAT_RATE));
		                  Double totalAmount= Double.valueOf(certCharge)+Double.valueOf(appCharge);
						chargesAmount = String.valueOf(totalAmount);
					} else {
						chargesAmount = BndConstants.CHARGES_AMOUNT_FLG;
					}
				} else {
					chargesAmount = BndConstants.CHARGES_AMOUNT;
				}

				if (chargesAmount != null && !chargesAmount.equals(BndConstants.CHARGES_AMOUNT_FLG)) {
					chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
				}
				
				chargeDetailDTO
						.setChargeDescReg(getApplicationSession().getMessage("BirthRegDto.brBirthCorrSerRegName"));
				chargeDetailDTO
						.setChargeDescEng(getApplicationSession().getMessage("BirthRegDto.brBirthCorrSerEngName"));
				chargesInfo.add(chargeDetailDTO);
				bndmodel.setChargesInfo(chargesInfo);
				if (chargesAmount != null && !chargesAmount.equals(BndConstants.CHARGES_AMOUNT_FLG)) {
					bndmodel.setChargesAmount(chargesAmount);
					this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
				}

			}
		} else {
			// when BRMS server off
			chargesAmount = BndConstants.CHARGES_AMOUNT_FLG;
		}
		return chargesAmount;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final Model model) {
		getModel().bind(httpServletRequest);
		BirthCorrectionModel appModel = this.getModel();
		List<DocumentDetailsVO> finalCheckListList = new ArrayList<>();
		
		// fileUpload.sessionCleanUpForFileUpload();
		ModelAndView mv = null;
		final BirthCorrectionModel birthRegModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		birthRegModel.setCommonHelpDocs("BirthCorrectionForm.html");
		birthRegModel.getBirthRegDto().setBrDob(Utility.stringToDate(birthRegModel.getBirthRegDto().getBrDateOfBirth()));
		List<DocumentDetailsVO> docs = null;
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		
		LinkedHashMap<String, Object> map = iBirthRegSevice
				.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.BRC);
		if (map.get("ChargesStatus") != null) {
			if (map.get("ChargesStatus").equals("CA")) {
				this.getModel().getBirthRegDto().setChargesStatus(map.get("ChargesStatus").toString());
			}
		}
		if (this.getModel().getOfflineDTO().getAmountToShow() != null) {
			if (this.getModel().getOfflineDTO().getAmountToShow() != 0
					|| !(this.getModel().getOfflineDTO().getAmountToShow() == 0.0)) {
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
			Map<String, List<DocumentDetailsVO>> responseMap = new LinkedHashMap<String, List<DocumentDetailsVO>>();
			long noOfDays = iBirthRegSevice.CalculateNoOfDays(birthRegModel.getBirthRegDto());

			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("BRC");
			checkListModel.setNoOfDays(noOfDays);
			
				if (birthRegModel.getBirthRegDto().getCorrCategory() != null
						&& !birthRegModel.getBirthRegDto().getCorrCategory().isEmpty()) {
					birthRegModel.getBirthRegDto().getCorrCategory().forEach(category -> {
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
						birthRegModel.setCheckList(checklist);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}
					mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
				} else {
			
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
			this.getModel().setCheckList(docs);
			mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME, getModel());
		  }
		 }
		}
		else {
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
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
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

	private BndRateMaster populateChargeModel(BirthCorrectionModel model, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(BndConstants.BRC);
		bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
		// bndRateMaster.setStartDate(new Date().getTime());
		return bndRateMaster;
	}
	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		this.getModel().setCommonHelpDocs("BirthCorrectionForm.html");
		ModelAndView mv = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByApplId(appId,orgId));
		this.getModel().getBirthRegDto().setBrDateOfBirth(Utility.dateToString(this.getModel().getBirthRegDto().getBrDob()));
		this.getModel().setSaveMode("V");
		this.getModel().setViewMode("V");
		List<DocumentDetailsVO>list=brmsCommonService.getChecklistDocument(String.valueOf(appId), orgId, "Y");
		this.getModel().setViewCheckList(list);
		mv = new ModelAndView("BirthCorrectionValidn", MainetConstants.FORM_NAME,  getModel());

		return mv;
	}
	
	@RequestMapping(params = "printBndAcknowledgement", method = { RequestMethod.POST })
	public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		final BirthCorrectionModel birthModel = this.getModel();
		LinkedHashMap<String, Object> map = iBirthRegSevice
				.serviceInformation(UserSession.getCurrent().getOrganisation().getOrgid(), BndConstants.BRC);
		BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
		ackDto.setApplicationId(Long.valueOf(birthModel.getBirthRegDto().getApmApplicationId()));
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
			fetchCheckListForAck(birthModel, map);
		}
		ModelAndView mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}
	
	private void fetchCheckListForAck(BirthCorrectionModel birthRegModel, LinkedHashMap<String, Object> map) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(BndConstants.CHECKLISTMODEL);
		if (map.get("lookup").equals(BndConstants.CHECKLISTAPPLICABLE)) {
			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
			List<DocumentDetailsVO> docs = null;

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> models = JersyCall.castResponse(response, CheckListModel.class, 0);
				final CheckListModel checkListModel = (CheckListModel) models.get(0);
				long noOfDays = iBirthRegSevice.CalculateNoOfDays(birthRegModel.getBirthRegDto());
				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode(BndConstants.BRC);
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
				} else {
					// Message For Checklist
					this.getModel().addValidationError("No CheckList Found");
				}
				this.getModel().setCheckList(docs);
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
	 @RequestMapping(params = "getChildNameById", method = RequestMethod.POST, produces = "Application/JSON")
		public @ResponseBody BirthRegistrationDTO getChildNameById(@RequestParam("brId") Long brId,final HttpServletRequest request) {
			getModel().bind(request);
			BirthRegistrationDTO birthDTO = iBirthRegSevice.getBirthByID(brId);
			birthDTO.setBrDateOfBirth(Utility.dateToString(birthDTO.getBrDob()));
			return birthDTO;
		}
}


