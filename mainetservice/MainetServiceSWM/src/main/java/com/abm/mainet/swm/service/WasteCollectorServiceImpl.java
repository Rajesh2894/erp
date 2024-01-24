/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.swm.datamodel.SWMRateMaster;
import com.abm.mainet.swm.domain.WasteCollector;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.repository.WasteCollectorRepository;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.swm.service.IWasteCollectorService")
@Path(value = "/wasteCollectorService")
public class WasteCollectorServiceImpl implements IWasteCollectorService {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private IChallanService iChallanService;
    @Resource
    private IFileUploadService fileUploadService;
    @Autowired
    private WasteCollectorRepository wasteCollectorRepository;
    @Autowired
    private BRMSCommonService brmsCommonService;
    @Autowired
    private IBRMSSWMService bRMSSWMService;
    @Autowired
    private ICFCApplicationMasterService cFCApplicationMasterService;
    @Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Resource
    private TbDepartmentService iTbDepartmentService;
    @Resource
    private ServiceMasterService iServiceMasterService;
    @Autowired
    private ISWMWorkFlowService swmWorkflowService;
    @Autowired
    private ISMSAndEmailService smsAndEmailService;
    @Resource
    private CommonService commonService;


    private static Logger logger = Logger.getLogger(WasteCollectorServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#saveCnDApplicantForm(com.abm.mainet.swm.dto.CollectorReqDTO,
     * com.abm.mainet.common.dto.CommonChallanDTO, java.lang.String, java.lang.Long,
     * com.abm.mainet.common.workflow.dto.WorkflowTaskAction)
     */
    @Override
    @Transactional
    public CollectorResDTO saveCnDApplicantForm(CollectorReqDTO collectorReqDTO, CommonChallanDTO offline, String serviceName, Long taskId, WorkflowTaskAction wrkflwTskAction) {
        logger.info("Enter into save CnDApplicant Form");
        String decision = "";
        setRequestApplicantDetails(collectorReqDTO);
        if (wrkflwTskAction == null) {
            wrkflwTskAction = new WorkflowTaskAction();
        }
        final WasteCollectorDTO collectorDTO = collectorReqDTO.getCollectorDTO();
        if (collectorDTO.getPayFlag() == null) {
            collectorDTO.setPayFlag(false);
        }
        if (collectorDTO.getLangId() != null) {
            collectorReqDTO.setLangId(collectorDTO.getLangId());
        } else {
            collectorReqDTO.setLangId(new Long(MainetConstants.NUMBERS.ONE));
        }
        collectorReqDTO.setOrgId(collectorDTO.getOrgId());
        collectorReqDTO.setUserId(collectorDTO.getCreatedBy());

        final Long applicationId = applicationService.createApplication(collectorReqDTO);

        ChallanReceiptPrintDTO printDto = null;
        if (offline != null) {
            offline.setApplNo(applicationId);
            collectorDTO.setPayFlag(true);
            collectorDTO.setCollectionStatus(MainetConstants.PAY_STATUS.PAID);
            printDto = iChallanService.savePayAtUlbCounter(offline, serviceName);
            logger.info("challan service called successfully in saveWaste Collector");
        }

        saveWasteCollector(collectorReqDTO, applicationId, collectorDTO);
       
        //Initiate for free service
        if (collectorReqDTO.getPayAmount() != null &&  collectorReqDTO.getPayAmount() == 0D) {
            initiateWorkFlow(collectorReqDTO.getOrgId(), String.valueOf(applicationId), decision, MainetConstants.FlagA, taskId, wrkflwTskAction, collectorReqDTO);
        }
        
        updateWasteCollector(applicationId);

        final CollectorResDTO collectorResDTO = new CollectorResDTO();
        if (offline != null) {
            collectorResDTO.setChallanReceiptPrintDTO(printDto);
        }
        collectorResDTO.setApplicationNo(applicationId);
        collectorReqDTO.setApplicationId(applicationId);

        if ((collectorReqDTO.getDocumentList() != null) && !collectorReqDTO.getDocumentList().isEmpty()) {
            fileUploadService.doFileUpload(collectorReqDTO.getDocumentList(), collectorReqDTO);
        }
        return collectorResDTO;

    }

    private WasteCollector saveWasteCollector(CollectorReqDTO collectorReqDTO, Long applicationId, WasteCollectorDTO collectorDTO) {
        WasteCollector collector = new WasteCollector();
        BeanUtils.copyProperties(collectorDTO, collector);
        collector.setApplicationId(applicationId);
        collector.setCollectionAmount(collectorReqDTO.getPayAmount());
        collector.setTermAccepted(true);
        collector.setCollectionDate(new Date());
        collector = wasteCollectorRepository.save(collector);
        logger.info("Entry save successfully in saveWaste Collector");
        return collector;

    }

    private int updateWasteCollector(Long applicationId) {
        int i = wasteCollectorRepository.updateAppliactionMst(applicationId);
        return i;
    }

    private void setRequestApplicantDetails(final CollectorReqDTO reqDTO) {
        final ApplicantDetailDTO appDTO = reqDTO.getApplicantDetailDto();
        final Organisation organisation = new Organisation();
        final Long orgId = reqDTO.getCollectorDTO().getOrgId();
        organisation.setOrgid(orgId);
        reqDTO.setmName(appDTO.getApplicantMiddleName());
        reqDTO.setfName(appDTO.getApplicantFirstName());
        reqDTO.setlName(appDTO.getApplicantLastName());
        reqDTO.setEmail(appDTO.getEmailId());
        reqDTO.setMobileNo(appDTO.getMobileNo());
        reqDTO.setTitleId(appDTO.getApplicantTitle());
        reqDTO.setBldgName(appDTO.getBuildingName());
        reqDTO.setRoadName(appDTO.getRoadName());
        reqDTO.setAreaName(appDTO.getAreaName());
        if ((appDTO.getPinCode() != null) && !appDTO.getPinCode().isEmpty()) {
            reqDTO.setPincodeNo(Long.valueOf(appDTO.getPinCode()));
        }
        reqDTO.setWing(appDTO.getWing());
        reqDTO.setBplNo(appDTO.getBplNo());
        reqDTO.setDeptId(reqDTO.getDeptId());
        reqDTO.setFloor(appDTO.getFloorNo());
        reqDTO.setWardNo(appDTO.getDwzid2());
        reqDTO.setZoneNo(appDTO.getDwzid1());
        reqDTO.setCityName(appDTO.getVillageTownSub());
        reqDTO.setBlockName(appDTO.getBlockName());
        reqDTO.setHouseComplexName(appDTO.getHousingComplexName());
        reqDTO.setFlatBuildingNo(appDTO.getFlatBuildingNo());
        if ((appDTO.getAadharNo() != null) && !appDTO.getAadharNo().equals("")) {
            reqDTO.setUid(Long.valueOf(appDTO.getAadharNo().replaceAll("\\s", "")));
        }
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
        for (final LookUp lookUp : lookUps) {
            if ((appDTO.getGender() != null) && !appDTO.getGender().isEmpty()
                    && (lookUp.getLookUpId() == Long.parseLong(appDTO.getGender()))) {
                appDTO.setGender(lookUp.getLookUpCode());
                break;
            }

        }
        reqDTO.setGender(appDTO.getGender());
    }

    private void initiateWorkFlow(Long orgId, String applicationId, String decision, String sendBackflag, Long getTaskId, WorkflowTaskAction wrkflwTskAction, CollectorReqDTO collectorReqDTO) {
        TbDepartment deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA, MainetConstants.SolidWasteManagement.SHORT_CODE);
        ServiceMaster sm = iServiceMasterService.getServiceMasterByShortCode(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE, orgId);
        // Code related to work flow
        WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId, deptObj.getDpDeptid(),
                sm.getSmServiceId(), null, null, null, null, null);

        WorkflowTaskAction prepareWorkFlowTaskAction = null;
        Long taskId = null;
        if (sendBackflag.equals(MainetConstants.FlagU)) {
            prepareWorkFlowTaskAction = prepareWorkFlowTaskActionUpdate(wrkflwTskAction, getTaskId, collectorReqDTO);
            prepareWorkFlowTaskAction.setEmpId(wrkflwTskAction.getEmpId());
            prepareWorkFlowTaskAction.setEmpName(wrkflwTskAction.getEmpName());
            prepareWorkFlowTaskAction.setOrgId(orgId);
            prepareWorkFlowTaskAction.setDecision(decision);
            taskId = getTaskId;
        } else {
            prepareWorkFlowTaskAction = prepareWorkFlowTaskActionCreate(Long.valueOf(applicationId), collectorReqDTO);
            taskId = workFlowMas.getWfId();
            prepareWorkFlowTaskAction.setTaskId(taskId);
        }

        WorkflowTaskActionResponse response = swmWorkflowService.initiateWorkFlowSWMService(prepareWorkFlowTaskAction, taskId, "WasteCollector.html", sendBackflag,
                MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE);

        if (response != null) {
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setOrganizationName(org.getONlsOrgname());
            dto.setAppNo(applicationId);
            dto.setServName(sm.getSmServiceName());
            dto.setMobnumber(collectorReqDTO.getApplicantDetailDto().getMobileNo());
            dto.setUserName(collectorReqDTO.getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE
                    + collectorReqDTO.getApplicantDetailDto().getApplicantLastName());
            if (!sendBackflag.equals(MainetConstants.FlagU)) {
                dto.setAmount(collectorReqDTO.getPayAmount().doubleValue());
                dto.setChallanAmt(collectorReqDTO.getPayAmount().toString());
                dto.setAppAmount(collectorReqDTO.getPayAmount().toString());
            }
            dto.setCurrentDate(Utility.dateToString(new Date()));

            smsAndEmailService.sendEmailSMS(MainetConstants.SolidWasteManagement.SHORT_CODE, "WasteCollector.html",
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, 1);
        }

    }

    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(WorkflowTaskAction workflowActionDto, Long getTaskId, CollectorReqDTO collectorReqDTO) {

        workflowActionDto.setOrgId(collectorReqDTO.getOrgId());
        workflowActionDto.setEmpType(collectorReqDTO.getCollectorDTO().getEmpType());
        workflowActionDto.setEmpName(collectorReqDTO.getfName() + MainetConstants.WHITE_SPACE + collectorReqDTO.getlName());
        workflowActionDto.setEmpEmail(collectorReqDTO.getEmail());

        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(collectorReqDTO.getCollectorDTO().getCreatedBy());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        workflowActionDto.setTaskId(getTaskId);
        return workflowActionDto;

    }

    public WorkflowTaskAction prepareWorkFlowTaskActionCreate(Long serialNo, CollectorReqDTO collectorReqDTO) {
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(collectorReqDTO.getOrgId());
        taskAction.setEmpId(collectorReqDTO.getCollectorDTO().getCreatedBy());
        taskAction.setEmpType(collectorReqDTO.getCollectorDTO().getEmpType());
        taskAction.setEmpName(collectorReqDTO.getfName() + MainetConstants.WHITE_SPACE + collectorReqDTO.getlName());
        taskAction.setEmpEmail(collectorReqDTO.getEmail());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(collectorReqDTO.getCollectorDTO().getCreatedBy());
        taskAction.setEmpMobile(collectorReqDTO.getMobileNo());
        taskAction.setApplicationId(serialNo);
        taskAction.setPaymentMode(MainetConstants.FlagF);
        return taskAction;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#getComParamDetById(java.lang.Long, java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public String getComParamDetById(Long cpdId, Long orgId) {
        return wasteCollectorRepository.getComParamDetById(cpdId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#initializeModel(com.abm.mainet.common.integration.dto.WSRequestDTO,
     * java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public WSResponseDTO initializeModel(@RequestBody WSRequestDTO requestDTO, @PathParam("orgId") Long orgId) {
        return brmsCommonService.initializeModel(requestDTO);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#getChecklist(com.abm.mainet.common.integration.dto.WSRequestDTO,
     * java.lang.Long)
     */
    @Override
    @POST
    @Path(value = "/getChecklist/{orgId}")
    @Transactional(readOnly = true)
    public WSResponseDTO getChecklist(@RequestBody WSRequestDTO requestDTO, @PathParam("orgId") Long orgId) {
        WSResponseDTO resposeDTO = initializeModel(requestDTO, orgId);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(resposeDTO.getWsStatus())) {
            final List<Object> checklistModel = RestClient.castResponse(resposeDTO, CheckListModel.class, 0);
            final CheckListModel checkListModel = (CheckListModel) checklistModel.get(0);
            populateCheckListModel(checkListModel, orgId);
            final WSRequestDTO checklistReqDTO = new WSRequestDTO();
            checklistReqDTO.setDataModel(checkListModel);
            return brmsCommonService.getChecklist(checklistReqDTO);
        } else {
            throw new FrameworkException("Problem while fetching checklist data for SWM rate .");
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.IWasteCollectorService#getApplicableTaxes(com.abm.mainet.common.integration.dto.WSRequestDTO,
     * java.lang.Long)
     */
    @Override
    @WebMethod(exclude = true)
    @Transactional(readOnly = true)
    public WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO, Long orgId) {
        WSResponseDTO resposeDTO = initializeModel(requestDTO, orgId);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(resposeDTO.getWsStatus())) {
            final List<Object> rnlRateMasterList = RestClient.castResponse(resposeDTO, SWMRateMaster.class, 0);
            final SWMRateMaster rnlRateMaster = (SWMRateMaster) rnlRateMasterList.get(0);
            final Organisation organisation = ApplicationContextProvider.getApplicationContext()
                    .getBean(IOrganisationService.class).getOrganisationById(orgId);
            rnlRateMaster.setOrgId(orgId);
            rnlRateMaster.setServiceCode(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE);
            rnlRateMaster.setChargeApplicableAt(
                    Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                            PrefixConstants.LookUpPrefix.CAA, organisation).getLookUpId()));
            requestDTO.setDataModel(rnlRateMaster);
            return bRMSSWMService.getApplicableTaxes(requestDTO);
        } else {
            throw new FrameworkException("Problem while fetching applicable taxes for SWM rate .");
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainet.swm.service.IWasteCollectorService#getApplicableCharges(com.abm.mainet.common.integration.dto.WSRequestDTO,
     * java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    @POST
    @Path(value = "/getApplicableCharges/{orgId}/{vehicleId}")
    @Transactional(readOnly = true)
    public List<ChargeDetailDTO> getApplicableCharges(@RequestBody WSRequestDTO requestDTO,
            @PathParam("vehicleId") Long vehicleId, @PathParam("orgId") Long orgId) {
        WSResponseDTO resposeDTO = getApplicableTaxes(requestDTO, orgId);
        List<ChargeDetailDTO> output = null;
        final Organisation organisation = ApplicationContextProvider.getApplicationContext()
                .getBean(IOrganisationService.class).getOrganisationById(orgId);
        if (resposeDTO.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(resposeDTO.getWsStatus())) {
            if (!resposeDTO.isFree()) {
                final List<Object> rates = (List<Object>) resposeDTO.getResponseObj();
                final List<SWMRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    final SWMRateMaster master1 = (SWMRateMaster) rate;
                    SWMRateMaster rateMaster = new SWMRateMaster();
                    rateMaster.setOrgId(orgId);
                    rateMaster.setServiceCode(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE);
                    rateMaster.setDeptCode(MainetConstants.SOLID_WASTE_MGMT);
                    rateMaster.setTaxType(master1.getTaxType());
                    rateMaster.setTaxCode(master1.getTaxCode());
                    rateMaster.setTaxId(master1.getTaxId());
                    LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vehicleId, orgId,
                            PrefixConstants.SOLID_WASTE_MGMT.VCH);
                    rateMaster.setUsageSubtype1(lookup.getDescLangFirst());
                    String orgName = getComParamDetById(organisation.getOrgCpdId(), organisation.getOrgid());
                    rateMaster.setUsageSubtype2(orgName);
                    rateMaster.setUsageSubtype3(MainetConstants.CommonConstants.NA);
                    rateMaster.setUsageSubtype4(MainetConstants.CommonConstants.NA);
                    rateMaster.setUsageSubtype5(MainetConstants.CommonConstants.NA);
                    rateMaster.setFactor4(MainetConstants.CommonConstants.NA);
                    rateMaster.setChargeApplicableAt(master1.getChargeApplicableAt());
                    rateMaster.setTaxCategory(master1.getTaxCategory());
                    rateMaster.setTaxSubCategory(master1.getTaxSubCategory());
                    requiredCHarges.add(rateMaster);
                }
                WSRequestDTO chargeReqDTO = new WSRequestDTO();
                chargeReqDTO.setDataModel(requiredCHarges);
                WSResponseDTO applicableCharges = bRMSSWMService.getApplicableCharges(chargeReqDTO);
                output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
            } else {
                throw new FrameworkException("Application tax is not free for SWM rate .");
            }
        } else {
            throw new FrameworkException("Problem while checking dependent param for SWM rate .");
        }
        return output;
    }

    private CheckListModel populateCheckListModel(final CheckListModel checklistModel, final Long orgId) {
        checklistModel.setOrgId(orgId);
        checklistModel.setServiceCode(MainetConstants.SolidWasteManagement.DEPT_WASTE_COLLECTOR_SHORT_CODE);
        checklistModel.setApplicantType(MainetConstants.CommonConstants.NA);
        checklistModel.setFactor1(MainetConstants.CommonConstants.NA);
        checklistModel.setFactor2(MainetConstants.CommonConstants.NA);
        checklistModel.setFactor3(MainetConstants.CommonConstants.NA);
        checklistModel.setFactor4(MainetConstants.CommonConstants.NA);
        checklistModel.setNoOfDays(0.0d);
        checklistModel.setIsBPL(MainetConstants.CommonConstants.NA);
        checklistModel.setUsageSubtype1(MainetConstants.CommonConstants.NA);
        checklistModel.setUsageSubtype2(MainetConstants.CommonConstants.NA);
        checklistModel.setUsageSubtype3(MainetConstants.CommonConstants.NA);
        checklistModel.setUsageSubtype4(MainetConstants.CommonConstants.NA);
        checklistModel.setUsageSubtype5(MainetConstants.CommonConstants.NA);
        checklistModel.setIsExistingConnectionOrConsumerNo(MainetConstants.CommonConstants.NA);
        checklistModel.setIsExistingProperty(MainetConstants.CommonConstants.NA);
        checklistModel.setDisConnectionType(MainetConstants.CommonConstants.NA);
        checklistModel.setApplicantType(MainetConstants.CommonConstants.NA);
        checklistModel.setIsOutStandingPending(MainetConstants.CommonConstants.NA);
        checklistModel.setIsOutStandingPending(MainetConstants.CommonConstants.NA);
        return checklistModel;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#getApplicationDetailsByApplicationId(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    @WebMethod(exclude = true)
    public CollectorReqDTO getApplicationDetailsByApplicationId(Long applicationId, Long orgId) {
        CollectorReqDTO creqDto = new CollectorReqDTO();
        final TbCfcApplicationMstEntity cfcMaster = cFCApplicationMasterService.getCFCApplicationByApplicationId(applicationId,
                orgId);
        final CFCApplicationAddressEntity cfcAddress = cFCApplicationMasterService
                .getApplicantsDetails(cfcMaster.getApmApplicationId());
        creqDto.getApplicantDetailDto().setApplicantFirstName(cfcMaster.getApmFname());
        creqDto.getApplicantDetailDto().setApplicantMiddleName(cfcMaster.getApmMname());
        creqDto.getApplicantDetailDto().setApplicantLastName(cfcMaster.getApmLname());
        creqDto.getApplicantDetailDto().setApplicantTitle(cfcMaster.getApmTitle());
        creqDto.getApplicantDetailDto().setGender(cfcMaster.getApmSex());
        creqDto.getApplicantDetailDto().setAreaName(cfcAddress.getApaAreanm());
        creqDto.getApplicantDetailDto().setPinCode(cfcAddress.getApaPincode().toString());
        creqDto.getApplicantDetailDto().setMobileNo(cfcAddress.getApaMobilno());
        creqDto.getApplicantDetailDto().setEmailId(cfcAddress.getApaEmail());

        final WasteCollector wasteCollect = wasteCollectorRepository.getWasteDetails(applicationId, orgId);
        if(null != wasteCollect) {
            BeanUtils.copyProperties(wasteCollect, creqDto.getCollectorDTO());
        }
        return creqDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IWasteCollectorService#updateProcess(com.abm.mainet.common.workflow.dto.WorkflowTaskAction,
     * java.lang.Long, java.lang.String, java.lang.String, java.lang.String, com.abm.mainet.swm.dto.CollectorReqDTO,
     * java.lang.String)
     */
    @Override
    @Transactional
    @WebMethod(exclude = true)
    public String updateProcess(WorkflowTaskAction departmentAction, Long taskId, String string, String flagu, String deptWasteCollectorShortCode, CollectorReqDTO collectorReqDTO, String decision) {
        final WasteCollectorDTO collectorDTO = collectorReqDTO.getCollectorDTO();
        WasteCollector collector = new WasteCollector();
        BeanUtils.copyProperties(collectorDTO, collector);
        int i = 0;
        String success = "fail";
        if (decision.equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            i = wasteCollectorRepository.updateWasteCollector(collectorDTO.getMrfId(), collectorDTO.getVehicleNo(),
                    collectorDTO.getEmpName(), collectorDTO.getPickUpDate(),
                    collectorDTO.getApplicationId(), departmentAction.getOrgId(),
                    departmentAction.getEmpId(), collectorDTO.getLgIpMacUpd());
            if (i != 0)
                success = "pass";
        }
        initiateWorkFlow(collectorReqDTO.getCollectorDTO().getOrgId(), String.valueOf(departmentAction.getApplicationId()), decision, flagu, taskId, departmentAction, collectorReqDTO);
        return success;
    }

}
