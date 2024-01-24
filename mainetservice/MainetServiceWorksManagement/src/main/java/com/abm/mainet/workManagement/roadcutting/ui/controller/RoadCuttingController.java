package com.abm.mainet.workManagement.roadcutting.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.workManagement.roadcutting.service.IRoadCuttingService;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.model.RoadCuttingModel;

/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(MainetConstants.RoadCuttingConstant.RAOD_CUTTING_URL)
public class RoadCuttingController extends AbstractFormController<RoadCuttingModel> {
	
	private static final String JSP_LIST = "LOARemark";
	
	private static final String JSP_FORM = "addRemark";
	
    @Autowired
    private BRMSCommonService brmsCommonService;
    @Autowired
    private IRoadCuttingService roadCuttingService;
    @Autowired
    private IFileUploadService fileUpload;
    
    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private ServiceMasterService serviceMasterService;
    
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IWorkflowRequestService requestService;
    
    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;
    
    @Autowired    
    private ICFCApplicationAddressService iCFCApplicationAddressService;
    
    @Resource
    private TbApprejMasService tbApprejMasService;
    
    @Resource
    private TbServicesMstService tbServicesMstService;
    
    @Resource
    private TbDepartmentService tbDepartmentService;
    
    @Resource
    private ICFCApplicationMasterService icfcApplicationMasterService;
    
    private List<TbApprejMas> apprejMasList = new ArrayList<>();
    
    protected static final String MODE = "mode";
    protected static final String MODE_CREATE = "create";
    protected static final String MODE_UPDATE = "update";
    protected static final String MODE_EDIT = "edit";
    protected static final String MODE_VIEW = "view";

    protected static final String SAVE_ACTION = "saveAction";
    
    private static final String SAVE_ACTION_CREATE = "CommonRemarkMaster.html?create";

    private static final Logger LOGGER = Logger.getLogger(RoadCuttingController.class);
	
    
    
    @SuppressWarnings("unchecked")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final RoadCuttingModel roadModel = this.getModel();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	return new ModelAndView("RoadCuttingBase", MainetConstants.FORM_NAME, this.getModel());
        }else {
	       roadModel.setCommonHelpDocs("RoadCutting.html");
	        roadModel.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
	        roadModel.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
	        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());
	        roadModel.setServiceId(service.getSmServiceId());
	        roadModel.setServiceName(service.getSmServiceName());
	        roadModel.setRoadCuttingLevelState("I");
	        if (service.getSmFeesSchedule().equals(1l)) {
	        	roadModel.setAppliChargeFlag(service.getSmAppliChargeFlag());
	        } else {
	        	roadModel.setAppliChargeFlag(MainetConstants.N_FLAG);
	        	roadModel.getRoadCuttingDto().setFree(true);
	        }
	        roadModel.setCharges(null);
	        roadModel.setCheckList(null);
	        LookUp checkListApplLookUp = null;
			if (service != null) {
				checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmChklstVerify(),
						ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
								.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid()));
				if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
					roadModel.setCheckListFlag("Y");
				}else {
					roadModel.setCheckListFlag("N");
				}
			 }
			LOGGER.info("Check List applicable : "+roadModel.getCheckListFlag()+"  Charges Application time : "+roadModel.getAppliChargeFlag());
			if(roadModel.getCheckListFlag().equals("N") && roadModel.getAppliChargeFlag().equals("N")) {
				roadModel.setSaveButFlag("Y");
			}
			return new ModelAndView("RoadCuttingCommon", MainetConstants.FORM_NAME, this.getModel());
        }
    } 
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getForm", method = RequestMethod.POST)
    public ModelAndView findForm(final Model model, final HttpServletRequest httpServletRequest,
    		@RequestParam("purposeType") String purposeType,@RequestParam("purposeId") Long purposeId) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final RoadCuttingModel roadModel = this.getModel();
        final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
        roadModel.setCommonHelpDocs("RoadCutting.html");
        roadModel.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
        roadModel.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
        roadModel.setPurposeValue(purposeType);
        String serviceShortCode=null;
        if ("N".equals(roadModel.getPurposeValue())){
        	serviceShortCode=MainetConstants.RoadCuttingConstant.RCP;
        }
        else{
        	serviceShortCode=MainetConstants.RoadCuttingConstant.RCW;
        }
        ServiceMaster service = serviceMasterService.getServiceByShortName(serviceShortCode,
				UserSession.getCurrent().getOrganisation().getOrgid());
        roadModel.setServiceId(service.getSmServiceId());
        roadModel.setServiceName(service.getSmServiceName());
        roadModel.setRoadCuttingLevelState("I");
        LOGGER.info("serviceShortCode : "+serviceShortCode+"  service.getSmFeesSchedule() : "+service.getSmFeesSchedule()+" Service Id  "+
        		service.getSmServiceId());
        if (service.getSmFeesSchedule().equals(1l)) {
        	roadModel.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
        	roadModel.setAppliChargeFlag(MainetConstants.N_FLAG);
        	roadModel.getRoadCuttingDto().setFree(true);
        }
        roadModel.setCharges(null);
        roadModel.setCheckList(null);
        LookUp checkListApplLookUp = null;
		if (service != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid()));
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				roadModel.setCheckListFlag("Y");
			}else {
				roadModel.setCheckListFlag("N");
			}
		 }
		LOGGER.info("Check List applicable : "+roadModel.getCheckListFlag()+"  Charges Application time : "+roadModel.getAppliChargeFlag());
		if(roadModel.getCheckListFlag().equals("N") && roadModel.getAppliChargeFlag().equals("N")) {
			roadModel.setSaveButFlag("Y");
		}
		
		roadModel.getRoadCuttingDto().setPurposeValue(purposeType);
		roadModel.getRoadCuttingDto().setPurpose(purposeId);
		return index();	
		//return new ModelAndView("RoadCuttingBase", MainetConstants.FORM_NAME, this.getModel());
    } 
			
		
    
    

   /* @SuppressWarnings("unchecked")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final RoadCuttingModel roadModel = this.getModel();
        final Long orgIds = UserSession.getCurrent().getOrganisation().getOrgid();
        roadModel.setCommonHelpDocs("RoadCutting.html");
        roadModel.setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
        roadModel.setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());
        roadModel.setServiceId(service.getSmServiceId());
        roadModel.setServiceName(service.getSmServiceName());
        roadModel.setRoadCuttingLevelState("I");
        if (service.getSmFeesSchedule().equals(1l)) {
        	roadModel.setAppliChargeFlag(service.getSmAppliChargeFlag());
        } else {
        	roadModel.setAppliChargeFlag(MainetConstants.N_FLAG);
        	roadModel.getRoadCuttingDto().setFree(true);
        }
        roadModel.setCharges(null);
        roadModel.setCheckList(null);
        LookUp checkListApplLookUp = null;
		if (service != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid()));
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				roadModel.setCheckListFlag("Y");
			}else {
				roadModel.setCheckListFlag("N");
			}
		 }
		LOGGER.info("Check List applicable : "+roadModel.getCheckListFlag()+"  Charges Application time : "+roadModel.getAppliChargeFlag());
		if(roadModel.getCheckListFlag().equals("N") && roadModel.getAppliChargeFlag().equals("N")) {
			roadModel.setSaveButFlag("Y");
		}
        
        
        
        try {
              List<DocumentDetailsVO> docs = null;
              final WSRequestDTO initRequestDto = new WSRequestDTO();
              initRequestDto.setModelName(MainetConstants.RoadCuttingConstant.CHECKLIST_ROADCUTTING_RATE_MASTER);
              WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
            if (response.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                 this is for charges and payment 
                final List<Object> roadCuttingRateMasterList = RestClient.castResponse(response,
                        RoadCuttingRateMaster.class, 1);
                final RoadCuttingRateMaster roadCuttingRateMaster = (RoadCuttingRateMaster) roadCuttingRateMasterList
                        .get(0);
                checkListModel.setOrgId(orgIds);
                checkListModel.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
                final WSRequestDTO checkRequestDto = new WSRequestDTO();
                checkRequestDto.setDataModel(checkListModel);
                WSResponseDTO checklistResp = brmsCommonService.getChecklist(checkRequestDto);
                if (response.getWsStatus() != null
                        && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                    docs = (List<DocumentDetailsVO>) checklistResp.getResponseObj();
                    if (docs != null && !docs.isEmpty()) {
                        long cnt = 1;
                        for (final DocumentDetailsVO doc : docs) {
                            doc.setDocumentSerialNo(cnt);
                            cnt++;
                        }
                    }
                    roadModel.setCheckList(docs);
                     payment and charges 
                    
                    
                    roadCuttingRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                    roadCuttingRateMaster.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
                    roadCuttingRateMaster.setChargeApplicableAt(
                            Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                                    MainetConstants.NewWaterServiceConstants.CAA,
                                    UserSession.getCurrent().getOrganisation()).getLookUpId()));

                    roadCuttingRateMaster
                            .setOrganisationType(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                                    UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                                    UserSession.getCurrent().getOrganisation().getOrgid(),
                                    MainetConstants.CommonMasterUi.OTY).getDescLangFirst());

                    final WSRequestDTO taxRequestDto = new WSRequestDTO();
                    taxRequestDto.setDataModel(roadCuttingRateMaster);
                    WSResponseDTO res = roadCuttingService.getApplicableTaxes(taxRequestDto);
                    if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
                        if (!res.isFree()) {
                            final List<Object> rates = (List<Object>) res.getResponseObj();
                            final List<RoadCuttingRateMaster> requiredCHarges = new ArrayList<>();
                            for (final Object rate : rates) {
                                RoadCuttingRateMaster master1 = (RoadCuttingRateMaster) rate;
                                master1 = populateChargeModel(roadModel, master1);
                                requiredCHarges.add(master1);
                            }
                            WSRequestDTO chargeReqDto = new WSRequestDTO();
                            chargeReqDto.setDataModel(requiredCHarges);
                            WSResponseDTO applicableCharges = roadCuttingService.getApplicableCharges(chargeReqDto);
                            List<ChargeDetailDTO> detailDTOsN = (List<ChargeDetailDTO>) applicableCharges
                                    .getResponseObj();
                            roadModel.setFree(PrefixConstants.NewWaterServiceConstants.NO);
                            roadModel.getRoadCuttingDto().setFree(false);
                            roadModel.setChargesInfo(detailDTOs);
                            roadModel.setCharges((chargesToPay(detailDTOs)));
                            setChargeMap(roadModel, detailDTOs);
                            roadModel.getOfflineDTO().setAmountToShow(roadModel.getCharges());
                            roadModel.setAmountToPaid(new BigDecimal(roadModel.getCharges()).setScale(2));
                        }
                    }
                } else {
                    return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
                }
            } else {
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
            }
        } catch (FrameworkException e) {
            LOGGER.info(e.getErrMsg());
            roadModel.setCheckList(null);
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
        }
        return index();
    }*/
    
    @RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
    public ModelAndView getCheckListAndCharges(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        RoadCuttingModel roadModel = this.getModel();
        RoadCuttingDto cuttingDto=new RoadCuttingDto();
        cuttingDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        cuttingDto.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
        roadModel.setSaveButFlag(MainetConstants.Y_FLAG);
        LookUp checkListApplLookUp = null;
        List<DocumentDetailsVO> docs = null;
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.RoadCuttingConstant.RCP, UserSession.getCurrent().getOrganisation().getOrgid());
		if (serviceMas != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid()));
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				docs =  roadCuttingService.fetchCheckList(cuttingDto);
			}
		}
		roadModel.setCheckList(docs);
        if (MainetConstants.Y_FLAG.equals(roadModel.getAppliChargeFlag())) {
            try {
                final WSRequestDTO initRequestDto = new WSRequestDTO();
                initRequestDto.setModelName(MainetConstants.RoadCuttingConstant.CHECKLIST_ROADCUTTING_RATE_MASTER);
                WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
              if (response.getWsStatus() != null
                      && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                  final List<Object> roadCuttingRateMasterList = RestClient.castResponse(response,
                          RoadCuttingRateMaster.class, 1);
                  final RoadCuttingRateMaster roadCuttingRateMaster = (RoadCuttingRateMaster) roadCuttingRateMasterList
                          .get(0);
                  roadCuttingRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                  roadCuttingRateMaster.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
                  roadCuttingRateMaster.setChargeApplicableAt(
                              Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                                      MainetConstants.NewWaterServiceConstants.CAA,
                                      UserSession.getCurrent().getOrganisation()).getLookUpId()));

                      roadCuttingRateMaster
                              .setOrganisationType(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                                      UserSession.getCurrent().getOrganisation().getOrgCpdId(),
                                      UserSession.getCurrent().getOrganisation().getOrgid(),
                                      MainetConstants.CommonMasterUi.OTY).getDescLangFirst());

                      final WSRequestDTO taxRequestDto = new WSRequestDTO();
                      taxRequestDto.setDataModel(roadCuttingRateMaster);
                      WSResponseDTO res = roadCuttingService.getApplicableTaxes(taxRequestDto);
                      if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
                          if (!res.isFree()) {
                              final List<Object> rates = (List<Object>) res.getResponseObj();
                              final List<RoadCuttingRateMaster> requiredCHarges = new ArrayList<>();
                              for (final Object rate : rates) {
                                  RoadCuttingRateMaster master1 = (RoadCuttingRateMaster) rate;
                                  master1 = populateChargeModel(roadModel, master1);
                                  requiredCHarges.add(master1);
                              }
                              WSRequestDTO chargeReqDto = new WSRequestDTO();
                              chargeReqDto.setDataModel(requiredCHarges);
                              WSResponseDTO applicableCharges = roadCuttingService.getApplicableCharges(chargeReqDto);
                              List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges
                                      .getResponseObj();
                              roadModel.setFree(PrefixConstants.NewWaterServiceConstants.NO);
                              roadModel.getRoadCuttingDto().setFree(false);
                              roadModel.setChargesInfo(detailDTOs);
                              roadModel.setCharges((chargesToPay(detailDTOs)));
                              setChargeMap(roadModel, detailDTOs);
                              roadModel.getOfflineDTO().setAmountToShow(roadModel.getCharges());
                              roadModel.setAmountToPaid(new BigDecimal(roadModel.getCharges()).setScale(2));
                          }else {
                        	  LOGGER.info("Rule Sheet Fetch free service false inspite service master configure Application time charges");
                        	  roadModel.getRoadCuttingDto().setFree(true);

                          }
                      }
                  } else {
                      return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
                  }
              
          } catch (FrameworkException e) {
              LOGGER.info(e.getErrMsg());
              roadModel.setCheckList(null);
              return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
          }
        }
       return defaultMyResult();

    }
    
    
    
    

    /* For populating RoadCuttingRateMaster Model for BRMS call */
    private RoadCuttingRateMaster populateChargeModel(RoadCuttingModel model,
            RoadCuttingRateMaster roadCuttingRateMaster) {
        roadCuttingRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        roadCuttingRateMaster.setServiceCode(MainetConstants.RoadCuttingConstant.RCP);
        roadCuttingRateMaster.setDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        roadCuttingRateMaster.setStartDate(new Date().getTime());
        return roadCuttingRateMaster;
    }
    /* end */

    private void setChargeMap(final RoadCuttingModel model, final List<ChargeDetailDTO> charges) {
        final Map<Long, Double> chargesMap = new HashMap<>();
        for (final ChargeDetailDTO dto : charges) {
            chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
        }
        model.setChargesMap(chargesMap);
    }

    private double chargesToPay(List<ChargeDetailDTO> chargeDetailDTO) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : chargeDetailDTO) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    /* method to validate and Save Road Cutting Application */
    @RequestMapping(params = "saveRoadCutting", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveRoadCutting(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        RoadCuttingModel model = this.getModel();
        if("Y".equals(model.getCheckListFlag())){
	        List<DocumentDetailsVO> docs = model.getCheckList();
	        if (docs != null) {
	            docs = fileUpload.prepareFileUpload(docs);
	        }
	        final RoadCuttingDto reqDTO = model.getRoadCuttingDto();
	        reqDTO.setDocumentList(docs);
	        fileUpload.validateUpload(model.getBindingResult());
        }
        if (model.validateInputs()) {
            if (model.saveRoadCuttingForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

            } else
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }

        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }
    /* end */
    
    @RequestMapping(params = "saveRoadCuttingFree", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveRoadCuttingFree(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	getModel().bind(httpServletRequest);
        RoadCuttingModel model = this.getModel();
        if (model.validateInputWithNoConfig()) {
        	if (model.saveRoadCuttingForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

            } else {
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
             }
        }
        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    	
    }


    @RequestMapping(params = "printWorkOrder", method = RequestMethod.GET)
    public String printWorkOrder(Model model, final HttpServletRequest request) {
        try {
            bindModel(request);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            Long appid = (Long) inputFlashMap.get("ApplicationID");

            RoadCuttingDto roadData = roadCuttingService.getRoadCuttingApplicationData(appid,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            model.addAttribute("data", roadData);
        } catch (final Exception e) {
            logger.error("Problem occurred generatWorkOrder service:", e);
        }
        return "RoadCutting/workorder";
    }

    @RequestMapping(params = "assignEngineer", method = RequestMethod.POST)
    public @ResponseBody ModelAndView assignEngineer(@RequestParam("applId") final long applId,
            @RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
            @RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
            @RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
            final HttpServletRequest httpServletRequest){

        DepartmentService departmentService = (DepartmentService) ApplicationContextProvider.getApplicationContext()
                .getBean("departmentServiceImpl");
        IEmployeeService employeeService = (IEmployeeService) ApplicationContextProvider.getApplicationContext()
                .getBean("employeeService");
        long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        List<EmployeeBean> employee = employeeService.getEmployeeData(deptId, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(),null);
        this.getModel().populateApplicationData(applId);
        ModelAndView mv = new ModelAndView("AssignEngineer", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("mode", mode);
        mv.addObject("employee", employee);
        mv.addObject("assignEng", this.getModel().getRoadCuttingDto().getRcAssignEng());
        mv.addObject("appId", applId);
        mv.addObject("labelId", level);
        mv.addObject("serviceId", serviceId);
        return mv;

    }

    @RequestMapping(params = "uploadEndPoints", method = RequestMethod.POST)
    public ModelAndView uploadEndPoints(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam Long index, @RequestParam String mode) {
        getModel().bind(httpServletRequest);
        ModelAndView mv = new ModelAndView("RoadCuttingUpload", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        mv.addObject("index", index);
        mv.addObject("mode", mode);
        return mv;

    }

    @RequestMapping(params = "deleteEndPoints", method = RequestMethod.POST)
    public ModelAndView deleteEndPoints(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam Long index) {

        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
                && !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {

            long lKey = 100 + index;
            long rKey = 200 + index;
            if (FileUploadUtility.getCurrent().getFileMap().containsKey(lKey)) {
                FileUploadUtility.getCurrent().getFileMap().remove(lKey);
            }

            if (FileUploadUtility.getCurrent().getFileMap().containsKey(rKey)) {
                FileUploadUtility.getCurrent().getFileMap().remove(rKey);
            }
            getModel().getRoadCuttingDto().getRoadList().clear();
            getModel().bind(httpServletRequest);
        }

        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        mv.addObject("index", index);
        return mv;

    }

    @RequestMapping(params = "bindData", method = RequestMethod.POST)
    public ModelAndView bindData(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    @RequestMapping(params = "back", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    @RequestMapping(params = "scrBack", method = RequestMethod.POST)
    public ModelAndView scrBack(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        ModelAndView mv = new ModelAndView("RoadCuttingView", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    @RequestMapping(params = "getCoordinates", method = RequestMethod.POST)
    public @ResponseBody double[] fetchCoordinate(HttpServletRequest httpRequest, @RequestParam Long fileIndex) {
        double[] coordinates = new double[2];
        if ((FileUploadUtility.getCurrent().getFileMap().entrySet() != null)
                && !FileUploadUtility.getCurrent().getFileMap().entrySet().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final Iterator<File> setFilesItr = entry.getValue().iterator();
                while (setFilesItr.hasNext()) {
                    final File file = setFilesItr.next();
                    javaxt.io.Image image = new javaxt.io.Image(file.getPath());
                    double[] coord = image.getGPSCoordinate();
                    if (coord != null) {
                        coordinates[0] = coord[1];
                        coordinates[1] = coord[0];
                    }
                }
            }
        }
        return coordinates;
    }

    @RequestMapping(params = "getWorkFlow", method = RequestMethod.POST)
    public @ResponseBody List<RoadCuttingDto> getWorkFlow(final HttpServletRequest request,
            @RequestParam(name = "serviceId") final Long serviceId) {
        RoadCuttingModel model = this.getModel();
        getModel().bind(request);

        final ApplicationSession session = ApplicationSession.getInstance();
        RoadCuttingDto dto = model.getRoadCuttingDto();
        String successMessage = StringUtils.EMPTY;
        ServiceMaster smm = serviceMasterService.getServiceMaster(serviceId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        ApplicationMetadata applicationData = new ApplicationMetadata();
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicationData.setApplicationId(dto.getApplicationId());
        applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        applicationData.setIsCheckListApplicable(false);
        applicantDetailDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        applicantDetailDto.setServiceId(serviceId);
        applicantDetailDto.setDepartmentId(smm.getTbDepartment().getDpDeptid());

        WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                .resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
                        smm.getTbDepartment().getDpDeptid(), smm.getSmServiceId(), null, null, null, null, null);

        if (workFlowMas != null) {
            ApplicationContextProvider.getApplicationContext().getBean(CommonService.class).initiateWorkflowfreeService(
                    applicationData,
                    applicantDetailDto);
            successMessage = session.getMessage("road.msg");
        }

        WorkflowRequest workflowRequest = requestService.findByApplicationId(dto.getApplicationId(),
                workFlowMas.getWfId());

        for (RoadCuttingDto masterEntity : model.getRoadCuttingDtoList()) {
            if ((masterEntity.getDeptId().equals(workFlowMas.getDepartment().getDpDeptid()))
                    && (masterEntity.getServiceId().equals(workFlowMas.getService().getSmServiceId()))) {

                masterEntity.setStatus(workflowRequest.getStatus());
            }

        }
        return model.getRoadCuttingDtoList();
    }
    
    /* method to validate and Save Road Cutting Application */
    @RequestMapping(params = "saveZoneWiseRoadCutting", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveZoneWiseScrutinyDetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(name = "roadZone") final long roadZone,
            @RequestParam(name = "purpose") final long purpose, @RequestParam(name = "purposeValue") final String purposeValue) {
        getModel().bind(httpServletRequest);
        RoadCuttingModel model = this.getModel();
        if (model.validateInputs()) {
            if (model.saveRoadCuttingZoneWise(roadZone)) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

            } else
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }

        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }
    
    @RequestMapping(params = "saveZoneWiseform", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveZoneWiseformDetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        RoadCuttingModel model = this.getModel();
        if (model.validateInputs()) {
            if (model.editSrutinyForm()) {
                return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

            } else
                return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }

        ModelAndView mv = new ModelAndView("RoadCuttingValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

    }

    /* end */

    
    @RequestMapping(params = "viewApplicationAndDoScrutiny", method = RequestMethod.GET)
    @Override
    public ModelAndView viewApplicationAndDoScrutiny(final HttpServletRequest request) {
        
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
    		final String taskId;
    		try {
	        	bindModel(request);
	            final String applicationId = UserSession.getCurrent().getScrutinyCommonParamMap()
	                    .get(MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
	            taskId = UserSession.getCurrent().getScrutinyCommonParamMap()
	                    .get(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID);
	            getModel().setTaskId(Long.parseLong(taskId));
	            getModel().populateApplicationData(Long.parseLong(applicationId));
	            getModel().setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
                getModel().setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
	        } catch (final Exception e) {
	            logger.error("Problem occurred during fetching application related info from respective service:", e);
	            return defaultExceptionFormView();
	        }

    		if ("N".equals(getModel().getPurposeValue())) {
                UserSession.getCurrent().getScrutinyCommonParamMap().put("SERVICE_CODE",MainetConstants.RoadCuttingConstant.RCP);
	        	
	        	UserTaskDTO userTaskdto= iWorkflowTaskService.findByTaskId(Long.parseLong(taskId));
	        	if(userTaskdto.getReferenceId().startsWith("Z")){
	        		return new ModelAndView("RoadCuttingZoneWise", MainetConstants.CommonConstants.COMMAND, this.getModel());
	        	}else {
	        		return this.getScrutinyView();
	        	}

                    /*if(!getModel().getRoadCuttingDto().getScrutinyPerformList().isEmpty() && null != getModel().getRoadCuttingDto().getScrutinyPerformList()
	        			.get(0) && null != getModel().getRoadCuttingDto().getScrutinyPerformList()
	    	        			.get(0).getRefId()) {
	        		return new ModelAndView("RoadCuttingZoneWise", MainetConstants.CommonConstants.COMMAND, this.getModel());
	        	}else {*/
	        		//return this.getScrutinyView();
	        	//}
	        	
	        }else {
	        	UserSession.getCurrent().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.SM_SERVICE_ID,
	        			serviceMasterService.getServiceByShortName(MainetConstants.RoadCuttingConstant.RCW,
	        					UserSession.getCurrent().getOrganisation().getOrgid()).getSmServiceId().toString());
	        	return new ModelAndView("WaterSupplySewageView", MainetConstants.CommonConstants.COMMAND, this.getModel());
	        }
    	}else {
    		getModel().setListTEC(CommonMasterUtility.getLookUps("TEC", UserSession.getCurrent().getOrganisation()));
            getModel().setListROT(CommonMasterUtility.getLookUps("ROT", UserSession.getCurrent().getOrganisation()));
    		super.viewApplicationAndDoScrutiny(request);
    	}
    	
    	return this.getScrutinyView();
    }  
    	

	
	@RequestMapping(params = "LOA", method = RequestMethod.POST)
	public String LOA(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest,final Model model) {
		
		long sId = Long.parseLong(serviceId);
		final String serviceName = tbServicesMstService.getServiceNameByServiceId(sId);

        final RoadCuttingDto roadCuttingdto = new RoadCuttingDto();
        long workorderid = 0;
        final long deparmentid = tbServicesMstService.findDepartmentIdByserviceid(sId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        TbDepartment department = tbDepartmentService.findById(deparmentid);
        String dept = MainetConstants.BLANK;
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dept = department.getDpDeptdesc();
        } else {
            dept = department.getDpNameMar();
        }

        final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(
                Long.valueOf(applId + MainetConstants.BLANK),
                UserSession.getCurrent().getOrganisation().getOrgid());
        String mobileNo = address.getApaMobilno() != null ? address.getApaMobilno() : MainetConstants.BLANK;
        final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applId, UserSession.getCurrent().getOrganisation().getOrgid());
        final String ApplicantFullName = tbCfcApplicationMstEntity.getApmFname() + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmMname() != null ? tbCfcApplicationMstEntity.getApmMname()
                        : MainetConstants.BLANK)
                + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmLname() != null ? tbCfcApplicationMstEntity.getApmLname()
                        : MainetConstants.BLANK);
        final Date ApplicarionDate = tbCfcApplicationMstEntity.getApmApplicationDate();
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpCode().equalsIgnoreCase(MainetConstants.RoadCuttingConstant.WOR)) {
                workorderid = Lookup1.getLookUpId();
            }
        }
        apprejMasList = tbApprejMasService.findByRemarkType(sId, workorderid);
        if(tbCfcApplicationMstEntity.getRefNo() != null && !tbCfcApplicationMstEntity.getRefNo().isEmpty()){
            String ref_No = tbCfcApplicationMstEntity.getRefNo();
            model.addAttribute("ref_No", ref_No);
        }else{
        	model.addAttribute("ref_No", "");
        }
        
        roadCuttingdto.setWoServiceId(sId);
        roadCuttingdto.setWoDeptId(deparmentid);
        roadCuttingdto.setWoApplicationDateS(ApplicarionDate + MainetConstants.BLANK);
        roadCuttingdto.setWoApplicationId(applId);
        roadCuttingdto.setAppId(applId);
        roadCuttingdto.setServiceId(sId);
        roadCuttingdto.setLabelId(labelId);
        
        model.addAttribute("appId", applId);
        model.addAttribute("ApplicantFullName", ApplicantFullName);
        model.addAttribute("ApplicarionDate", ApplicarionDate);
        model.addAttribute("apprejMasList", apprejMasList);
        model.addAttribute("roadCuttingdto", roadCuttingdto);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("deptName", dept);
        model.addAttribute("mobileNo", mobileNo);
		model.addAttribute("labelId", labelId);
		model.addAttribute("serviceId", serviceId);
        
        httpServletRequest.getSession().removeAttribute("applicationId");
        httpServletRequest.getSession().removeAttribute("conncetionNo");
        httpServletRequest.getSession().removeAttribute("serviceId");
        httpServletRequest.getSession().removeAttribute("taskId");
        return JSP_LIST;
	}
	
	@RequestMapping(params = "create", method = RequestMethod.POST) // GET or POST
    public String create(@Valid final RoadCuttingDto roadCuttingdto, final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest) {
        LOGGER.info("Action 'create'");
        try {

            if (!bindingResult.hasErrors()) {
                final String woApplicationDate = roadCuttingdto.getWoApplicationDateS();
                final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                final Date date = formatter.parse(woApplicationDate);
                roadCuttingdto.setWoApplicationDate(date);
               
                final RoadCuttingDto roadCuttingCreated = roadCuttingService.create(roadCuttingdto);
                model.addAttribute("roadCuttingdto", roadCuttingCreated);
               
                model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL, "AdminHome.html");
                return MainetConstants.CommonConstants.SUCCESS_PAGE;
            } else {
                return new String("redirect:/AdminHome.html?");
            }
        } catch (final Exception e) {
            LOGGER.info("Action 'create' : Exception - " + e.getMessage());
            //messageHelper.addException(model, "tbWorkOrder.error.create", e);
            return new String("redirect:/AdminHome.html?");
        }
    }
	
/*	@RequestMapping(params = "printLoaReport", method = {RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String printLoaReport(final HttpServletRequest request) {
        this.getModel().bind(request);
        ViewWaterDetailsModel model = this.getModel();
        String connNo = model.getViewConnectionDto().getCsCcn();

        long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

        return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
                + "=LicenseRegister_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

    }*/
	
	@RequestMapping(params = "addRemark", method = RequestMethod.POST)
	public String addRemark(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest,final Model model) {
		
		long sId = Long.parseLong(serviceId);
		final String serviceName = tbServicesMstService.getServiceNameByServiceId(sId);
		final long deparmentid = tbServicesMstService.findDepartmentIdByserviceid(sId,
                UserSession.getCurrent().getOrganisation().getOrgid());
		final RoadCuttingDto roadCuttingdto = new RoadCuttingDto();

        TbDepartment department = tbDepartmentService.findById(deparmentid);
        String dept = MainetConstants.BLANK;
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dept = department.getDpDeptdesc();
        } else {
            dept = department.getDpNameMar();
        }
        
        final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(
                Long.valueOf(applId + MainetConstants.BLANK),
                UserSession.getCurrent().getOrganisation().getOrgid());
        String mobileNo = address.getApaMobilno() != null ? address.getApaMobilno() : MainetConstants.BLANK;
        final TbCfcApplicationMstEntity tbCfcApplicationMstEntity = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(applId, UserSession.getCurrent().getOrganisation().getOrgid());
        final String ApplicantFullName = tbCfcApplicationMstEntity.getApmFname() + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmMname() != null ? tbCfcApplicationMstEntity.getApmMname()
                        : MainetConstants.BLANK)
                + MainetConstants.WHITE_SPACE
                + (tbCfcApplicationMstEntity.getApmLname() != null ? tbCfcApplicationMstEntity.getApmLname()
                        : MainetConstants.BLANK);
        final Date ApplicarionDate = tbCfcApplicationMstEntity.getApmApplicationDate();
        
        long artType = 0;
        final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpCode().equalsIgnoreCase(MainetConstants.RoadCuttingConstant.WOR)) {
            	artType = Lookup1.getLookUpId();
            }
        }
        roadCuttingdto.setLabelId(labelId);
		LOGGER.info("Action 'formForCreate'");
        final List<TbApprejMas> list = new ArrayList<>();
        model.addAttribute("list", list);
        final TbApprejMas tbApprejMas = new TbApprejMas();
        tbApprejMas.setArtServiceId(sId);
        tbApprejMas.setArtType(artType);
        model.addAttribute(MainetConstants.TB_APPREJ_MAS, tbApprejMas);
        model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
        model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        model.addAttribute("appId", applId);
        model.addAttribute("ApplicantFullName", ApplicantFullName);
        model.addAttribute("ApplicarionDate", ApplicarionDate);
        model.addAttribute("serviceName", serviceName);
        model.addAttribute("deptName", dept);
        model.addAttribute("mobileNo", mobileNo);
		model.addAttribute("labelId", labelId);
		model.addAttribute("artServiceId", serviceId);
		model.addAttribute("deptId", deparmentid);
		model.addAttribute("artType", artType);
		model.addAttribute("roadCuttingdto", roadCuttingdto);
        return JSP_FORM;
	}
	
}
