package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IBirthCertificateService;
import com.abm.mainet.bnd.service.IRtsService;
import com.abm.mainet.bnd.ui.model.BirthCertificateModel;
import com.abm.mainet.bnd.ui.model.NacForBirthRegModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller("bndBirthCertificate")
@RequestMapping(value = "/ApplyforBirthCertificates.html")
public class BirthCertificateController extends AbstractFormController<BirthCertificateModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IBirthCertificateService birthCertificateService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ICFCApplicationMasterService cfcApplicationMasterService;
    
    @Autowired
	private IChecklistVerificationService iChecklistVerificationService;
    
    @Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
    
    @Autowired
    @Qualifier("RtsService")
	private IRtsService rtsService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
        sessionCleanup(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.RBC, orgId);
        LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
       this.getModel().setRequestDTO(requestDTO);
        if (serviceMas.getSmFeesSchedule() != 0) {
            this.getModel().getBirthCertificateDto().setChargesStatus(BndConstants.CHARGESAPPLICABLE);
        }
        if (lookup.getLookUpCode().equals(BndConstants.CHECKLISTAPPLICABLE)) {
            getCheckListAndCharges(this.getModel(), httpServletRequest, httpServletResponse);
        }
        if(requestDTO.getApplicationId()!=null)
        {
        	this.getModel().setSaveMode(BndConstants.VIEWMODE);
        	this.getModel().setBirthCertificateDto(birthCertificateService.getBirthRegisteredAppliDetail(requestDTO.getApplicationId(), orgId));
        	//fetch uploaded document
    		 List<DocumentDetailsVO> dvo = rtsService.getRtsUploadedCheckListDocuments(
    				 requestDTO.getApplicationId(), orgId);
    		 this.getModel().setCheckList(dvo);
        	//this.getModel().setFetchDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(requestDTO.getApplicationId().toString(), orgId));
        }
        return new ModelAndView(BndConstants.BIRTH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
    }

    private void getCheckListAndCharges(BirthCertificateModel birthRegModel,
            final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {

        getModel().bind(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        birthRegModel.setCommonHelpDocs(BndConstants.APPLY_FOR_BIRTH_CERTIFICATE);

        final WSRequestDTO requestDTO = new WSRequestDTO();
        requestDTO.setModelName(BndConstants.ChecklistModel);
        WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
            final CheckListModel checkListModel = (CheckListModel) models.get(0);
            // long noOfDays =
            // iBirthRegSevice.CalculateNoOfDays(birthRegModel.getBirthRegDto());
            checkListModel.setOrgId(orgId);
            checkListModel.setServiceCode(BndConstants.RBC);
            // checkListModel.setNoOfDays(365);
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
                        birthRegModel.setCheckList(checkListList);
                    }
                }
            }

        }
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
    public @ResponseBody String getBndCharges(@RequestParam("noOfCopies") int noOfCopies,
            @RequestParam("issuedCopy") int issuedCopy) {
        BndRateMaster ratemaster = new BndRateMaster();
        String chargesAmount = null;
        BirthCertificateModel bndmodel = this.getModel();
        WSResponseDTO certificateCharges = null;
        final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
        WSRequestDTO chargeReqDto = new WSRequestDTO();
        chargeReqDto.setModelName(BndConstants.BNDRateMaster);
        chargeReqDto.setDataModel(ratemaster);
        WSResponseDTO response = brmsCommonService.initializeModel(chargeReqDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
            List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
            List<Object> rateMaster = RestClient.castResponse(response, BndRateMaster.class, 0);
            BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
            rateMasterModel.setOrgId(orgIds);
            rateMasterModel.setServiceCode(BndConstants.RBC);
            rateMasterModel.setChargeApplicableAt(Long.toString(CommonMasterUtility
                    .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                            MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
                    .getLookUpId()));
            rateMasterModel.setOrganisationType(CommonMasterUtility
                    .getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
                    .getDescLangFirst());
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(rateMasterModel);
			// WSResponseDTO responsefortax =
			// birthCertificateService.getApplicableTaxes(taxRequestDto);
			WSResponseDTO responsefortax = null;
			try {
				responsefortax = birthCertificateService.getApplicableTaxes(taxRequestDto);
			} catch (Exception ex) {
				chargesAmount = MainetConstants.FlagN;
				return chargesAmount;
			}
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
					orgIds);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
                List<Object> detailDTOs = null;
                LinkedHashMap<String, String> charges = null;
                if (!responsefortax.isFree()) {
                    final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
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
                    bndChagesRequestDto.setModelName(BndConstants.BNDRateMaster);
                    certificateCharges = birthCertificateService.getBndCharge(bndChagesRequestDto);
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
                if(certificateCharges != null) {
                chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxMasterByTaxCode(orgIds,
                        serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode")).getTaxId());
                }
                chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthCertificateDTO.serName"));
                chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthCertificateDTO.regserName"));
                chargesInfo.add(chargeDetailDTO);
                bndmodel.setChargesInfo(chargesInfo);
                if(chargesAmount != null && !chargesAmount.equals(MainetConstants.FlagN)) {
                bndmodel.setChargesAmount(chargesAmount);
                this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
                }
            }
        }
        return chargesAmount;
    }

    private BndRateMaster populateChargeModel(BirthCertificateModel model, BndRateMaster bndRateMaster) {
        bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        bndRateMaster.setServiceCode(BndConstants.RBC);
        bndRateMaster.setDeptCode(BndConstants.RTS);
        return bndRateMaster;
    }

    @RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
    public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
        BirthCertificateModel model = this.getModel();
        String docStatus = new String();

        if (CollectionUtils.isNotEmpty(model.getCheckList())) {
            List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
                    .getBean(IChecklistVerificationService.class)
                    .getAttachDocumentByDocumentStatus(Long.valueOf(model.getBirthCertificateDto().getApplicationId()),
                            docStatus, UserSession.getCurrent().getOrganisation().getOrgid());
            if (CollectionUtils.isNotEmpty(documentUploaded)) {
                model.setDocumentList(documentUploaded);
            }
        }
        TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
                Long.valueOf(model.getBirthCertificateDto().getApplicationId()),
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.setCfcEntity(cfcEntity);
        model.setApplicationId(Long.valueOf(model.getBirthCertificateDto().getApplicationId()));
        String name = " ";
		if (model.getCfcEntity().getApmFname() != null) {
			name = model.getCfcEntity().getApmFname() + " ";
		}
		if (model.getCfcEntity().getApmMname()!= null) {
			name += model.getCfcEntity().getApmMname() + " ";
		}
		if (model.getCfcEntity().getApmLname() != null) {
			name += model.getCfcEntity().getApmLname();
		}
        model.setApplicantName(name);
        model.setServiceName(getApplicationSession().getMessage("BirthCertificateDTO.serName"));
        model.setFormName(getApplicationSession().getMessage("BirthCertificateDTO.serviceName"));
        return new ModelAndView(BndConstants.DRN_ACK_PAGE, MainetConstants.FORM_NAME,
                this.getModel());

    }
    
    @RequestMapping(params = "resetBirthForm", method = RequestMethod.POST)
	public ModelAndView resetBirthForm(final Model model, final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		return new ModelAndView(BndConstants.BIRTH_CERTIFICATE, MainetConstants.FORM_NAME, getModel());
	}

    @RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
        bindModel(request);
        final BirthCertificateModel birthModel = this.getModel();
        ModelAndView mv = null;
        if(birthModel.getBirthCertificateDto().getApmApplicationId()!=null) {
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.RBC, UserSession.getCurrent().getOrganisation().getOrgid());
        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
        Long title = birthModel.getBirthCertificateDto().getRequestDTO().getTitleId();
        LookUp lokkup = null;
		if (birthModel.getBirthCertificateDto().getRequestDTO().getTitleId() != null) {
			lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(birthModel.getBirthCertificateDto().getRequestDTO().getTitleId(),
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
        ackDto.setApplicationId(birthModel.getBirthCertificateDto().getApmApplicationId());
        ackDto.setApplicantName(String.join(" ", Arrays.asList(birthModel.getRequestDTO().getfName(),
        		birthModel.getRequestDTO().getmName(), birthModel.getRequestDTO().getlName())));
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
        	getCheckListAndCharges(birthModel,request,null);
        }
         mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return mv;

    }

}
