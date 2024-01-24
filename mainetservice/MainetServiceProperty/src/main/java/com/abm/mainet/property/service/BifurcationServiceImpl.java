package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.BarcodeGenerator;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentFactorDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.MainAssessmentDetailRepository;
import com.abm.mainet.property.repository.MainAssessmentFactorRepository;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;

@Service
public class BifurcationServiceImpl implements BifurcationService {

    private final Logger LOGGER = Logger.getLogger(BifurcationServiceImpl.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;

    @Autowired
    private MainAssessmentDetailRepository mainAssessmentDetailRepository;

    @Autowired
    private MainAssessmentOwnerRepository assessmentOwnerRepository;

    @Autowired
    private MainAssessmentFactorRepository assessmentFactorRepository;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private PropertyService propertyService;

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    @Autowired
    private IOrganisationService organisationService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IWorkflowTypeDAO iWorkflowTypeDAO;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;

    private void setRequestApplicantDetails(final RequestDTO reqDto, ProvisionalAssesmentMstDto asseMstDto, Long orgId,
            Long deptId, int langId, Long empId, Long serviceId) {
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        reqDto.setfName(ownDtlDto.getAssoOwnerName());
        reqDto.setMobileNo(ownDtlDto.getAssoMobileno());
        reqDto.setEmail(asseMstDto.getAssEmail());
        reqDto.setPincodeNo(asseMstDto.getAssPincode());
        reqDto.setDeptId(deptId);
        if (reqDto.getGender() != null) {
            reqDto.setGender(ownDtlDto.getGenderId().toString());
        }
        reqDto.setPayStatus(MainetConstants.PAYMENT.FREE);
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(empId);
    }

    @Override
    @Transactional
    public List<Long> saveBifurcation(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline, List<Long> finYearList,
            List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto, BillDisplayDto advanceAmt) {
        Long orgId = offline.getOrgId();
        Long deptId = offline.getDeptId();
        Long empId = offline.getUserId();
        int languageId = offline.getLangId();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        RequestDTO reqDto = new RequestDTO();
        List<ProvisionalAssesmentDetailDto> detailDtoList = provAsseMstDto.getProvisionalAssesmentDetailDtoList();
        Map<String, List<ProvisionalAssesmentDetailDto>> bifurcateGrouping = detailDtoList.stream()
                .collect(Collectors.groupingBy(ProvisionalAssesmentDetailDto::getAssdBifurcateNo));
        for (Entry<String, List<ProvisionalAssesmentDetailDto>> entry : bifurcateGrouping.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equals(MainetConstants.Y_FLAG)) {
            	
            	String bifurcatedPropNo = null;
            	//get max property number
            	if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_ASCL) 
            		||Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_PSCL)){
            		bifurcatedPropNo = propertyService.getPropertyNo(orgId, provAsseMstDto);
            	}else {
            		bifurcatedPropNo = getBifurcatedPropertyNo(provAsseMstDto.getParentProp());
            	}
                List<ProvisionalAssesmentDetailDto> assesmentDetailDtos = new ArrayList<>();
                provAsseMstDto.getProvisionalAssesmentDetailDtoList().clear();
                for (ProvisionalAssesmentDetailDto bif : entry.getValue()) {
                    if (bif.getAssdBifurcateNo() != null && bif.getAssdBifurcateNo().equals(MainetConstants.Y_FLAG)) {
                        ProvisionalAssesmentDetailDto newDTO = new ProvisionalAssesmentDetailDto();
                        BeanUtils.copyProperties(bif, newDTO);
                        assesmentDetailDtos.add(newDTO);
                        provAsseMstDto.setAssNo(bifurcatedPropNo);
                        provAsseMstDto.setProvisionalAssesmentDetailDtoList(assesmentDetailDtos);
                        setRequestApplicantDetails(reqDto, provAsseMstDto, orgId, deptId, languageId, empId,
                                provAsseMstDto.getSmServiceId());
                        // To flush data from detail table 
                        List<Long> proAssIdList = assesmentMastService.getAssdIdListbyPropNo(provAsseMstDto.getParentProp(), orgId);
                        if(CollectionUtils.isNotEmpty(proAssIdList)) {
                        	Long assId = proAssIdList.get(proAssIdList.size()-1);
                        	//Long assId = assesmentMastService.getAssMasterIdbyPropNo(provAsseMstDto.getParentProp(), orgId);
                        	Long assdId = assesmentMastService.getAssDetailIdByAssIdAndUnitNo(assId, bif.getAssdUnitNo());
                        	if (assdId != null) {
                        		AssesmentDetailEntity detailEntity = mainAssessmentDetailRepository.findOne(assdId);
                        		// List<Long> assfIdList = assessmentFactorRepository.fetchAssFactorIdListByAssdId(detailEntity);
                        		assessmentFactorRepository.deleteAssFactor(detailEntity);
                        		mainAssessmentDetailRepository.delete(assdId);
                        		// for (Long assfId : assfIdList) {
                        		// assessmentFactorRepository.delete(assfId);
                        		// }
                        	}
                        }
                        AssesmentMastEntity assesmentMastEntity = assesmentMstRepository
                                .fetchPropertyByPropNo(provAsseMstDto.getParentProp());
                        List<AssesmentDetailEntity> detailEntityList = mainAssessmentDetailRepository
                                .fetchAssdIdByAssId(assesmentMastEntity);
                        if (detailEntityList.size() == 0) {
                            // Long assoId = assesmentMastService.getAssOwnerIdByAssId(assId);
                            assesmentMstRepository.updateAssessFlagOfProperty(orgId, assesmentMastEntity.getParentProp(),
                                    MainetConstants.STATUS.INACTIVE);
                            List<AssesmentOwnerDtlEntity> ownerDtlEntitiyList = assessmentOwnerRepository
                                    .fetchOwnerDetailListByProAssId(assesmentMastEntity, orgId);
                            for (AssesmentOwnerDtlEntity ownerEntity : ownerDtlEntitiyList) {
                                Long assoId = ownerEntity.getProAssoId();
                                if (assoId != null) {
                                    assessmentOwnerRepository.delete(assoId);
                                }
                            }
                            // Long assfId = assesmentMastService.getAssFactorIdByAssId(assId);
                            // if (assfId != null) {
                            // assessmentFactorRepository.delete(assfId);
                            // }
                        }
                    }
                }
            }
        }
        final Long applicationNo = applicationService.createApplication(reqDto);
        Map<Long, Long> assIdMap = iProvisionalAssesmentMstService.saveProvisionalAssessment(provAsseMstDto, orgId, empId,
                finYearList, applicationNo);
        List<BillReceiptPostingDTO> billRecePstingDto = null;
        double ajustedAmt = 0;
        if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
            billRecePstingDto = selfAssessmentService.knowkOffAdvanceAmt(billMasList, advanceAmt, organisation,
                    offline.getManualReeiptDate(),
                    provAsseMstDto.getAssNo());
            if (billMasList.get(billMasList.size() - 1).getExcessAmount() > 0) {
                ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue()
                        - billMasList.get(billMasList.size() - 1).getExcessAmount();
            } else {
                ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue();
            }
        }
        if (provAsseMstDto.getApmDraftMode() == null) {
            List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
            List<Long> bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, empId,
                    provAsseMstDto.getAssNo(), assIdMap, provBillList, provAsseMstDto.getLgIpMac());
            if ((provAsseMstDto.getDocs() != null) && !provAsseMstDto.getDocs().isEmpty()) {
                reqDto.setApplicationId(applicationNo);
                fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
            }
            // CHALLAN case will skip
            // if (offline.getOnlineOfflineCheck() == null
            // || MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER.equals(offline.getOnlineOfflineCheck())
            // || MainetConstants.PAYMENT_TYPE.ONLINE.equals(offline.getOnlineOfflineCheck())) {
            // workflowRequestService.initiateAndUpdateWorkFlowProcess(offline, null);
            // }
            callWorkflow(provAsseMstDto, deptId);
            selfAssessmentService.saveDemandLevelRebate(provAsseMstDto, empId, deptId, billMasList, rebateRecDto,
                    organisation, provBillList);
            selfAssessmentService.saveAdvanceAmt(provAsseMstDto, empId, deptId, advanceAmt, organisation, billMasList,
                    provAsseMstDto.getLgIpMac(),
                    provBillList, billRecePstingDto, ajustedAmt, null, 0);
            copyToHistoryAndDeleteMainBill(provAsseMstDto, orgId,empId,provAsseMstDto.getLgIpMac());
            return bmIds;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> saveBifurcationForSkdcl(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
            List<Long> finYearList, List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto,
            BillDisplayDto advanceAmt) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " saveBifurcationForSkdcl() method");
        Long orgId = offline.getOrgId();
        Long deptId = offline.getDeptId();
        Long empId = offline.getUserId();
        int languageId = offline.getLangId();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        RequestDTO reqDto = new RequestDTO();
        Map<Long, Long> assIdMap = null;
        Long applicationNo = null;
        List<ProvisionalAssesmentDetailDto> detailDtoList = new ArrayList<>();

        Map<String, Long> flatWiseAppIdmap = new LinkedHashMap<>();
        List<Long> bmIds = new ArrayList<>();
        Long currFinYear = iFinancialYear.getFinanceYearId(new Date());
        // START To save gap year assessment
        List<ProvisionalAssesmentMstDto> assMstList = assesmentMastService.fetchAssessmentMasterForAmalgamation(
                orgId,
                provAsseMstDto.getParentProp(),
                new ArrayList<>(), provAsseMstDto.getAssOldpropno(), new ArrayList<>());
        if (CollectionUtils.isNotEmpty(assMstList)) {
            ProvisionalAssesmentMstDto assesmentMstDto = assMstList.get(assMstList.size() - 1);

            List<FinancialYear> finYearIdList = new ArrayList<>();
            if (assesmentMstDto != null) {
                finYearIdList = iFinancialYear.getFinanceYearListFromGivenDate(orgId, assesmentMstDto.getFaYearId(),
                        new Date());
                if (finYearIdList.size() >= 3) {
                    finYearIdList.remove(0);
                    finYearIdList.remove(finYearIdList.size() - 1);

                    List<Long> gapYearList = new ArrayList<>();
                    if (finYearIdList.size() > 0) {
                        for (FinancialYear yearId : finYearIdList) {
                            gapYearList.add(yearId.getFaYear());
                        }
                        saveMainAssessmentBifurcation(assesmentMstDto, orgId, empId, gapYearList, applicationNo);
                    }
                }
            }
        }// END To save gap year assessment

        int count = 0;
        for (ProvisionalAssesmentDetailDto detailDto : provAsseMstDto.getProvisionalAssesmentDetailDtoList()) {
            detailDtoList.clear();
            if (count == 0) {
                provAsseMstDto.setAssNo(provAsseMstDto.getParentProp());
            } else {
                // String bifurcatedPropNo = getBifurcatedPropertyNo(provAsseMstDto.getParentProp());
                // bifurcated PropNo reference
                String unitNo = provAsseMstDto.getParentProp().substring(provAsseMstDto.getParentProp().length() - 3);
                String uniquePropNo = provAsseMstDto.getParentProp().substring(0, (provAsseMstDto.getParentProp().length() - 3));
                String paddingUnitNoInc = String.format(MainetConstants.CommonMasterUi.PADDING_THREE,
                        (Integer.valueOf(unitNo) + count));
                String bifurcatedPropNo = uniquePropNo.concat((paddingUnitNoInc));
                provAsseMstDto.setAssNo(bifurcatedPropNo);
            }
            provAsseMstDto.setFlatNo(detailDto.getFlatNo());

            detailDtoList.add(detailDto);
            provAsseMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList);
            provAsseMstDto
                    .setLogicalPropNo(provAsseMstDto.getAssNo() + MainetConstants.operator.UNDER_SCORE + detailDto.getFlatNo());
            setRequestApplicantDetails(reqDto, provAsseMstDto, orgId, deptId, languageId, empId,
                    provAsseMstDto.getSmServiceId());
            if (!flatWiseAppIdmap.containsKey(detailDto.getFlatNo())) {
                applicationNo = applicationService.createApplication(reqDto);
                flatWiseAppIdmap.put(detailDto.getFlatNo(), applicationNo);
            }
            // applicationNo = applicationService.createApplication(reqDto);

            assIdMap = saveProvisionalAssessmentBifurcation(provAsseMstDto, orgId, empId, finYearList, applicationNo);
            count++;

            // if current financial year bill is present then skip
            List<TbBillMas> tbBillMas = propertyMainBillService
                    .fetchAllBillByPropNo(provAsseMstDto.getParentProp(), orgId);
            if (CollectionUtils.isNotEmpty(tbBillMas)) {
                TbBillMas billMas = tbBillMas.get(tbBillMas.size() - 1);
                if (!currFinYear.equals(billMas.getBmYear())) {
                    List<TbBillMas> tbBillMasList = new ArrayList<>();
                    tbBillMasList.clear();
                    for (TbBillMas billMass : billMasList) {
                        if (detailDto.getFlatNo().equals(billMass.getFlatNo())) {
                            tbBillMasList.add(billMass);
                        }
                    }
                    List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
                    bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(tbBillMasList, orgId, empId,
                            provAsseMstDto.getAssNo(), assIdMap, provBillList, provAsseMstDto.getLgIpMac());

                }
            }

            if ((provAsseMstDto.getDocs() != null) && !provAsseMstDto.getDocs().isEmpty()) {
                // In case of bifurcation property to set documents against each application no
                if (MapUtils.isNotEmpty(flatWiseAppIdmap)
                        && Utility.isEnvPrefixAvailable(organisation, MainetConstants.APP_NAME.SKDCL)) {
                    for (Map.Entry<String, Long> map : flatWiseAppIdmap.entrySet()) {
                        reqDto.setApplicationId(map.getValue());
                        fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
                    }
                } else {
                    reqDto.setApplicationId(applicationNo);
                    fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
                }
            }
            if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ABW)) {
                initiateWorkFlowBasedOnAmount(provAsseMstDto, offline, orgId, flatWiseAppIdmap);
            }
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " saveBifurcationForSkdcl() method");
        return bmIds;

    }

    private void copyToHistoryAndDeleteMainBill(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId,Long empId,String lgIpAddress) {
        List<TbBillMas> arrearBillMasList = propertyMainBillService.fetchNotPaidBillForAssessment(provAsseMstDto.getAssNo(),
                orgId);
        if (arrearBillMasList != null && !arrearBillMasList.isEmpty()) {
            propertyMainBillService.copyDataFromMainBillDetailToHistory(arrearBillMasList, "B",empId,lgIpAddress);
            propertyMainBillService.deleteMainBillWithDtoById(arrearBillMasList);
        }
    }

    @Override
    @Transactional
    public void saveBifurcationAssessment(ProvisionalAssesmentMstDto provAsseMstDto, long orgid, Long empId,
            Long deptId, int languageId, List<Long> finYearList) {
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        List<ProvisionalAssesmentDetailDto> detailDtoList = provAsseMstDto.getProvisionalAssesmentDetailDtoList();
        // int size = detailDtoList.size();
        // if (size == 0) {
        // assesmentMstRepository.updateActiveFlagOfProperty(provAsseMstDto.getOrgId(), provAsseMstDto.getParentProp(),
        // MainetConstants.STATUS.INACTIVE);
        // }
        // for increment of bifurcatedPropNo (ex: CGMNCR0004207001, CGMNCR0004207002)
        Map<String, List<ProvisionalAssesmentDetailDto>> bifurcateGrouping = detailDtoList.stream()
                .collect(Collectors.groupingBy(ProvisionalAssesmentDetailDto::getAssdBifurcateNo));
        for (Entry<String, List<ProvisionalAssesmentDetailDto>> entry : bifurcateGrouping.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equals(MainetConstants.Y_FLAG)) {

                String bifurcatedPropNo = getBifurcatedPropertyNo(provAsseMstDto.getParentProp());
                List<ProvisionalAssesmentDetailDto> assesmentDetailDtos = new ArrayList<>();
                provAsseMstDto.getProvisionalAssesmentDetailDtoList().clear();
                for (ProvisionalAssesmentDetailDto bif : entry.getValue()) {
                    if (bif.getAssdBifurcateNo() != null && bif.getAssdBifurcateNo().equals(MainetConstants.Y_FLAG)) {
                        ProvisionalAssesmentDetailDto newDTO = new ProvisionalAssesmentDetailDto();
                        // bif.setAssdActive("I");
                        BeanUtils.copyProperties(bif, newDTO);
                        assesmentDetailDtos.add(newDTO);
                        provAsseMstDto.setAssNo(bifurcatedPropNo);
                        provAsseMstDto.setProvisionalAssesmentDetailDtoList(assesmentDetailDtos);
                        setRequestApplicantDetails(reqDto, provAsseMstDto, orgid, deptId, languageId, empId,
                                provAsseMstDto.getSmServiceId());
                        // To flush data from detail table
                        Long assId = assesmentMastService.getAssMasterIdbyPropNo(provAsseMstDto.getParentProp(), orgid);
                        Long assdId = assesmentMastService.getAssDetailIdByAssIdAndUnitNo(assId, bif.getAssdUnitNo());
                        if (assdId != null) {
                            // mainAssessmentDetailRepository.delete(assdId);
                        }
                        Long assoId = assesmentMastService.getAssOwnerIdByAssId(assId);
                        if (assoId != null) {
                            // assessmentOwnerRepository.delete(assoId);
                        }
                        Long assfId = assesmentMastService.getAssFactorIdByAssId(assId);
                        if (assfId != null) {
                            // assessmentFactorRepository.delete(assfId);
                        }
                    }
                }
            }
            final Long applicationNo = applicationService.createApplication(reqDto);

            iProvisionalAssesmentMstService.saveProvisionalAssessment(provAsseMstDto, orgid, empId,
                    finYearList, applicationNo);
            if ((provAsseMstDto.getDocs() != null) && !provAsseMstDto.getDocs().isEmpty()) {
                reqDto.setApplicationId(applicationNo);
                fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
            }
            callWorkflow(provAsseMstDto, deptId);
        }
    }

    String getBifurcatedPropertyNo(String propNo) {
        String unitNo = propNo.substring(propNo.length() - 3);
        String uniquePropNo = propNo.substring(0, (propNo.length() - 3));
        String paddingUnitNoInc = String.format(MainetConstants.CommonMasterUi.PADDING_THREE, (Integer.valueOf(unitNo) + 1));
        return uniquePropNo.concat((paddingUnitNoInc));
    }

    private void callWorkflow(ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            Long deptId) {
        ApplicationMetadata applicationData = new ApplicationMetadata();
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicantDetailDto.setOrgId(provisionalAssesmentMstDto.getOrgId());
        applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
        applicationData.setIsCheckListApplicable(
                (provisionalAssesmentMstDto.getDocs() != null && !provisionalAssesmentMstDto.getDocs().isEmpty()) ? true
                        : false);
        applicationData.setOrgId(provisionalAssesmentMstDto.getOrgId());
        applicantDetailDto.setServiceId(provisionalAssesmentMstDto.getSmServiceId());
        applicantDetailDto.setDepartmentId(deptId);
        applicantDetailDto.setUserId(provisionalAssesmentMstDto.getCreatedBy());
        Organisation organisation = new Organisation();
        organisation.setOrgid(provisionalAssesmentMstDto.getOrgId());
        if(Utility.isEnvPrefixAvailable(organisation, "PWZ")) {
          	 if (provisionalAssesmentMstDto.getAssParshadWard1() != null) {
          		applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssParshadWard1());
               }
          	 if (provisionalAssesmentMstDto.getAssParshadWard2() != null) {
          		applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssParshadWard2());
               }
          	 if (provisionalAssesmentMstDto.getAssParshadWard3() != null) {
          		applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssParshadWard3());
               }
          	 if (provisionalAssesmentMstDto.getAssParshadWard4() != null) {
          		applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssParshadWard4());
               }
          	 if (provisionalAssesmentMstDto.getAssParshadWard5() != null) {
          		applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssParshadWard5());
               }
   		}else{
   			applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
   	        applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
   	        applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
   	        applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
   	        applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
   		}
        
        commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
    }

    @Transactional
    @Override
    public void SaveAuthorizationForSkdcl(List<ProvisionalAssesmentMstDto> provAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, WorkflowTaskAction workFlowActionDto, long orgId, Employee emp,
            Long deptId, int languageId, Long serviceId, String authEditFlag) {
        List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        Map<Long, Long> assIdMap = null;
        if ("Y".equals(authEditFlag)) {
            authStatus = MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG;
        }
        List<TbBillMas> billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNoAndFlatNo(
                provisionalAssesmentMstDto.getAssNo(), provisionalAssesmentMstDto.getFlatNo(), orgId);

        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
                && iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {

            assIdMap = assesmentMastService.saveAndUpdateAssessmentMast(provisionalAssesmentMstDto, provAssMstDtoList,
                    orgId, emp.getEmpId(), authStatus);

            primaryPropertyService.savePropertyMaster(provisionalAssesmentMstDto, orgId, emp.getEmpId());

            iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssMstDtoList,emp.getEmpId(),provisionalAssesmentMstDto);
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssMstDtoList);

            if (CollectionUtils.isNotEmpty(billMasList)) {
                propertyMainBillService.saveAndUpdateMainBillWithKeyGen(billMasList, orgId,
                        emp.getEmpId(), provisionalAssesmentMstDto.getAssNo(),
                        assIdMap, provisionalAssesmentMstDto.getLgIpMac());
                iProvisionalBillService.deleteProvisionalBillsById(billMasList);
            }
        }
        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssMstDtoList);
            if (CollectionUtils.isNotEmpty(billMasList)) {
                iProvisionalBillService.deleteProvisionalBillsById(billMasList);
            }
        }

        iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId,
                serviceId);

    }

    @Override
    @Transactional
    public void SaveAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, WorkflowTaskAction workFlowActionDto, long orgId, Employee emp,
            Long deptId, int languageId, Long serviceId, String authEditFlag,List<TbBillMas> billMasList) {
        List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        if ("Y".equals(authEditFlag)) {
            authStatus = MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG;
        }
        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
        		&& iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
            assesmentMastService.saveAndUpdateAssessmentMast(provisionalAssesmentMstDto, provAssMstDtoList,
                    orgId, emp.getEmpId(), authStatus);
            List<MainBillMasEntity> mainBillList = propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId,
                    emp.getEmpId(),
                    authStatus, provisionalAssesmentMstDto.getLgIpMac());
            List<MainBillMasEntity> mainBillListForDupBill = new ArrayList<MainBillMasEntity>();
            provAssMstDtoList.forEach(assMastDto ->{
            	mainBillList.forEach(maainBill ->{
            		if(assMastDto.getFaYearId().equals(maainBill.getBmYear())) {
            			mainBillListForDupBill.add(maainBill);
                	}
            	});
            	
            });
            new Thread(() -> propertyBillGenerationService.saveDuplicateBill(mainBillListForDupBill, provisionalAssesmentMstDto, languageId, orgId)).start();


            primaryPropertyService.savePropertyMaster(provisionalAssesmentMstDto, orgId, emp.getEmpId());
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssMstDtoList);
            iProvisionalBillService.deleteProvisionalBillsById(billMasList);
            
        } else {
            iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssMstDtoList,emp.getEmpId(),provisionalAssesmentMstDto);
            iProvisionalAssesmentMstService.updateProvisionalAssessment(provisionalAssesmentMstDto, provAssMstDtoList,
                    orgId, emp.getEmpId(), authStatus, null, provAssEntList);
        }
        /*
         * List<Long> attachementId = iChecklistVerificationService
         * .fetchAttachmentIdByAppid(provisionalAssesmentMstDto.getApmApplicationId(), orgId);
         * workFlowActionDto.setAttachementId(attachementId);
         */
        iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId,
                serviceId);

    }

    @Override
    @Transactional
    public Map<Long, Long> saveProvisionalAssessmentBifurcation(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId,
            Long empId, List<Long> finYearList, Long applicationNo) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " saveProvisionalAssessmentBifurcation() method");
        Map<Long, Long> assIdMap = new HashMap<>();
        Organisation org = organisationService.getOrganisationById(orgId);
        boolean skdclCheck = Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SKDCL);
        final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
        Long currFinYear = iFinancialYear.getFinanceYearId(new Date());
        String propNoExist = provisionalAssesmentMstDto.getAssNo();
        if (StringUtils.isEmpty(propNoExist)) {
            propNoExist = propertyService.getPropertyNo(orgId, provisionalAssesmentMstDto);
            getBarcode(provisionalAssesmentMstDto, propNoExist);
        }
        List<ProvisionalAssesmentMstEntity> proEntList = new ArrayList<>();
        final String propNo = propNoExist;

        final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
        provisionalAssesmentMstDto.setAssNo(propNo);
        provisionalAssesmentMstDto.setOrgId(orgId);
        provisionalAssesmentMstDto.setCreatedBy(empId);
        provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
        provisionalAssesmentMstDto.setLgIpMac(ipAddress);
        provisionalAssesmentMstDto.setCreatedDate(new Date());
        provisionalAssesmentMstDto.setAssActive(MainetConstants.STATUS.ACTIVE);
        if (provisionalAssesmentMstDto.getApmDraftMode() != null
                && provisionalAssesmentMstDto.getApmDraftMode().equals(MainetConstants.FlagD)) {
            provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.SAVE_AS_DRAFT);
        } else {
            provisionalAssesmentMstDto.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
        }
        provisionalAssesmentMstDto.setAssAutStatus(MainetConstants.Property.AuthStatus.NON_AUTH);

        BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);

        // Assessment Detail
        final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {

            provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
            provisionalAssesmentMstDto.setFlatNo(provDet.getFlatNo());
            provisionalAssesmentMstDto.setFaYearId(currFinYear);
            provisionalAssesmentMstDto.setFaYearId(currFinYear);

            ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
            BeanUtils.copyProperties(provDet, provAsseDet);
            if (skdclCheck && StringUtils.isEmpty(provDet.getLegal())) {
                provAsseDet.setLegal(MainetConstants.FlagN);
            }
            provAsseDet.setTbAsAssesmentMast(provAssetMst);
            provAsseDet.setOrgId(orgId);
            provAsseDet.setCreatedBy(empId);
            provAsseDet.setLgIpMac(ipAddress);
            provAsseDet.setCreatedDate(new Date());
            provAsseDet.setAssdAssesmentDate(new Date());
            provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
            // Factor
            final List<ProvisionalAssesmentFactorDtlEntity> provAsseFactList = new ArrayList<>();
            provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                ProvisionalAssesmentFactorDtlEntity provAssFact = new ProvisionalAssesmentFactorDtlEntity();
                BeanUtils.copyProperties(provfact, provAssFact);
                provAssFact.setTbAsAssesmentFactorDetail(provAsseDet);
                provAssFact.setTbAsAssesmentMast(provAssetMst);
                provAssFact.setCreatedBy(empId);
                provAssFact.setCreatedDate(new Date());
                provAssFact.setLgIpMac(ipAddress);
                provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                provAssFact.setOrgId(orgId);
                provAsseFactList.add(provAssFact);
            });
            provAsseDet.setProvisionalAssesmentFactorDtlList(provAsseFactList);

            final List<ProvisionalAssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
            provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
                ProvisionalAssessmentRoomDetailEntity roomEntity = new ProvisionalAssessmentRoomDetailEntity();
                BeanUtils.copyProperties(roomDetails, roomEntity);
                roomEntity.setProAssDetEntity(provAsseDet);
                roomEntity.setOrgId(orgId);
                roomEntity.setCreatedBy(empId);
                roomEntity.setCreatedDate(new Date());
                roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                proAssessmentRoomDetailEntity.add(roomEntity);
            });
            provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);

            provAsseDetList.add(provAsseDet);
        });
        provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

        // Assessment owner

        final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
        provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
            ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            if (StringUtils.isEmpty(provOwndto.getAssoGuardianName())) {
                provAsseOwnerEnt.setAssoGuardianName(MainetConstants.BLANK);
            }
            provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
            provAsseOwnerEnt.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
            provAsseOwnerEnt.setOrgId(orgId);
            provAsseOwnerEnt.setCreatedBy(empId);
            provAsseOwnerEnt.setLgIpMac(ipAddress);
            provAsseOwnerEnt.setCreatedDate(new Date());
            provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
            provAsseOwnerEnt.setApmApplicationId(applicationNo);
            provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
            provAsseOwnerEnt.setAssNo(provisionalAssesmentMstDto.getAssNo());
            provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
            provAsseOwnEntList.add(provAsseOwnerEnt);

        });
        provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);

        proEntList.add(provAssetMst);

        provisionalAssesmentMstRepository.save(provAssetMst);
        proEntList.forEach(proEnt -> {
            assIdMap.put(proEnt.getFaYearId(), proEnt.getProAssId());
        });
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " saveProvisionalAssessmentBifurcation() method");
        return assIdMap;
    }

    private void getBarcode(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, String propNoExist) {
        if (propNoExist != null) {
            try {
                provisionalAssesmentMstDto.setBarCode(barcodeGenerator.getBarcodeInByteArray(propNoExist));
            } catch (Exception e) {
                throw new FrameworkException("error occurs while generating barcode for PropertyNum", e);
            }
        }
    }

    // in case of SKDCL Bifurcation assessment done for current finYear, so this is used for save previous year assessment
    @Override
    @Transactional
    public void saveMainAssessmentBifurcation(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId,
            List<Long> finYearList, Long applicationNo) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " saveMainAssessmentBifurcation() method");
        Organisation org = organisationService.getOrganisationById(orgId);
        boolean skdclCheck = Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SKDCL);
        final String ipAddress = provisionalAssesmentMstDto.getLgIpMac();
        Long currFinYaer = iFinancialYear.getFinanceYearId(new Date());
        String propNoExist = provisionalAssesmentMstDto.getAssNo();
        if (StringUtils.isEmpty(propNoExist)) {
            propNoExist = propertyService.getPropertyNo(orgId, provisionalAssesmentMstDto);
            getBarcode(provisionalAssesmentMstDto, propNoExist);
        }
        List<AssesmentMastEntity> proEntList = new ArrayList<>();
        final String propNo = propNoExist;
        finYearList.forEach(finYear -> {
            final AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
            final long proAssId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                    MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
                    MainetConstants.Property.primColumn.PRO_ASS_ID,
                    null,
                    null);
            provisionalAssesmentMstDto.setProAssId(proAssId);
            provisionalAssesmentMstDto.setAssNo(propNo);
            provisionalAssesmentMstDto.setOrgId(orgId);
            provisionalAssesmentMstDto.setCreatedBy(empId);
            provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
            provisionalAssesmentMstDto.setLgIpMac(ipAddress);
            provisionalAssesmentMstDto.setCreatedDate(new Date());
            provisionalAssesmentMstDto.setFaYearId(finYear);//
            provisionalAssesmentMstDto.setAssActive(MainetConstants.STATUS.ACTIVE);
            provisionalAssesmentMstDto.setAssStatus(MainetConstants.FlagN);
            provisionalAssesmentMstDto.setAssAutStatus(MainetConstants.Property.AuthStatus.AUTH);
            BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);

            // Assessment Detail
            final List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {

                AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
                BeanUtils.copyProperties(provDet, provAsseDet);
                final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
                        MainetConstants.Property.primColumn.PRO_ASSD_ID,
                        null,
                        null);
                provAsseDet.setProAssdId(proAssdId);
                if (skdclCheck && StringUtils.isEmpty(provDet.getLegal())) {
                    provAsseDet.setLegal(MainetConstants.FlagN);
                }
                provAsseDet.setMnAssId(provAssetMst);
                provAsseDet.setOrgId(orgId);
                provAsseDet.setCreatedBy(empId);
                provAsseDet.setLgIpMac(ipAddress);
                provAsseDet.setCreatedDate(new Date());
                provAsseDet.setAssdAssesmentDate(new Date());
                provAsseDet.setAssdActive(MainetConstants.STATUS.ACTIVE);
                // Factor
                final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
                provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
                    AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
                    BeanUtils.copyProperties(provfact, provAssFact);
                    final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
                            MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                            MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
                            MainetConstants.Property.primColumn.PRO_ASSF_ID,
                            null,
                            null);
                    provAssFact.setProAssfId(proAssfId);
                    provAssFact.setMnAssdId(provAsseDet);
                    provAssFact.setMnAssId(provAssetMst);
                    // provAssFact.setAssfFactor(144L);// PENDING FCT prefix Value
                    provAssFact.setCreatedBy(empId);
                    provAssFact.setCreatedDate(new Date());
                    provAssFact.setLgIpMac(ipAddress);
                    provAssFact.setAssfActive(MainetConstants.STATUS.ACTIVE);
                    provAssFact.setOrgId(orgId);
                    provAsseFactList.add(provAssFact);
                });
                provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

                final List<AssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
                provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
                    AssessmentRoomDetailEntity roomEntity = new AssessmentRoomDetailEntity();
                    BeanUtils.copyProperties(roomDetails, roomEntity);
                    roomEntity.setAssPropRoomDet(provAsseDet);
                    roomEntity.setOrgId(orgId);
                    roomEntity.setCreatedBy(empId);
                    roomEntity.setCreatedDate(new Date());
                    roomEntity.setIsActive(MainetConstants.STATUS.ACTIVE);
                    proAssessmentRoomDetailEntity.add(roomEntity);
                });
                provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);

                provAsseDetList.add(provAsseDet);

            });
            provAssetMst.setAssesmentDetailEntityList(provAsseDetList);

            // Assessment owner

            final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
            provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
                AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
                BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
                final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                        MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
                        MainetConstants.Property.primColumn.PRO_ASSO_ID,
                        null,
                        null);
                provAsseOwnerEnt.setProAssoId(proAssoId);
                if (StringUtils.isEmpty(provOwndto.getAssoGuardianName())) {
                    provAsseOwnerEnt.setAssoGuardianName(MainetConstants.BLANK);
                }
                provAsseOwnerEnt.setMnAssId(provAssetMst);
                provAsseOwnerEnt.setAssoStartDate(provisionalAssesmentMstDto.getAssAcqDate());
                provAsseOwnerEnt.setOrgId(orgId);
                provAsseOwnerEnt.setCreatedBy(empId);
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setAssoType(MainetConstants.Property.OWNER);// Owner Type
                provAsseOwnerEnt.setApmApplicationId(applicationNo);
                provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId());
                provAsseOwnerEnt.setAssNo(provisionalAssesmentMstDto.getAssNo());
                provAsseOwnerEnt.setAssoActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnEntList.add(provAsseOwnerEnt);

            });
            provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);

            proEntList.add(provAssetMst);
        });
        assesmentMstRepository.save(proEntList);
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " saveMainAssessmentBifurcation() method");
    }

    private void initiateWorkFlowBasedOnAmount(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
            Long orgid, Map<String, Long> flatWiseAppIdmap) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " initiateWorkFlowBasedOnAmount() method");
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.Property.Bifurcation, orgid);
        if (service != null) {
            List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
                    service.getTbDepartment().getDpDeptid(), service.getSmServiceId());

            WorkflowMas workflowMas = null;
            WardZoneBlockDTO dwzDto = null;
            if (dwzDto == null) {
                dwzDto = offline.getDwzDTO();
            }
            Double totalRv = 0.0;
            Map<String, String> flatWiseMap = new LinkedHashMap<>(); // Flat No and Property No
            Map<String, Double> flatWiseAlvMap = new LinkedHashMap<>(); // Flat No and Total ALV
            if (CollectionUtils.isNotEmpty(provAsseMstDto.getProvisionalAssesmentDetailDtoList())) {
                for (ProvisionalAssesmentDetailDto detail : provAsseMstDto.getProvisionalAssesmentDetailDtoList()) {
                    totalRv += detail.getAssdRv();
                    if (org.apache.commons.lang.StringUtils.isNotBlank(detail.getFlatNo())
                            && org.apache.commons.lang.StringUtils.isNotBlank(provAsseMstDto.getAssNo())) {
                        flatWiseMap.put(detail.getFlatNo(), provAsseMstDto.getParentProp());
                        flatWiseAlvMap.put(detail.getFlatNo(),
                                flatWiseAlvMap.get(detail.getFlatNo()) != null
                                        ? (flatWiseAlvMap.get(detail.getFlatNo()) + detail.getAssdRv())
                                        : detail.getAssdRv());
                    }
                }
            }

            for (Map.Entry<String, String> map : flatWiseMap.entrySet()) {
                workflowMas = getWorkflowMaster(worKFlowList, flatWiseAlvMap.get(map.getKey()), service, dwzDto);
                if (workflowMas != null) {
                    try {
                        offline.setReferenceNo(
                                map.getValue() + MainetConstants.operator.UNDER_SCORE + map.getKey());
                        offline.setApplNo(flatWiseAppIdmap.get(map.getKey()));
                        initiateWorkflow(offline, workflowMas, service);
                    } catch (Exception e) {
                        LOGGER.error("Unsuccessful initiation/updation of task for application : " + e);
                        throw new FrameworkException("Exception while calling work flow", e);
                    }
                }
            }
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " initiateWorkFlowBasedOnAmount() method");
    }

    private void initiateWorkflow(CommonChallanDTO offline, WorkflowMas workflowMas,
            ServiceMaster service) throws Exception {
        WorkflowProcessParameter processParameter = prepareInitProcessParameter(offline, workflowMas,
                service);
        TaskAssignment assignment = setRequesterTask(offline);
        if (offline.getLoiNo() == null) {
            processParameter.setRequesterTaskAssignment(assignment);
            workflowExecutionService.initiateWorkflow(processParameter);
        } else {
            workflowExecutionService.updateWorkflow(processParameter);
        }
    }

    WorkflowProcessParameter prepareInitProcessParameter(CommonChallanDTO offline, WorkflowMas workflowMas,
            ServiceMaster serviceMaster) {
        Organisation org = new Organisation();
        org.setOrgid(offline.getOrgId());
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction workflowAction = new WorkflowTaskAction();
        workflowAction.setReferenceId(offline.getReferenceNo());
        workflowAction.setApplicationId(offline.getApplNo());
        workflowAction.setDateOfAction(new Date());
        workflowAction.setOrgId(offline.getOrgId());
        workflowAction.setEmpId(offline.getUserId());
        workflowAction.setEmpType(offline.getEmpType());
        workflowAction.setEmpName(offline.getApplicantName());
        workflowAction.setCreatedBy(offline.getUserId());
        workflowAction.setCreatedDate(new Date());
        if (StringUtils.isEmpty(offline.getChallanNo())) {
            workflowAction.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
        } else {
            workflowAction.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
        }
        if (offline.getLoiNo() != null && !offline.getLoiNo().isEmpty()) {
            workflowAction.setIsLoiGenerated(true);
            workflowAction.setTaskId(offline.getTaskId());
            workflowAction.setModifiedBy(offline.getUserId());
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        } else {
            if (offline.isDocumentUploaded()) {
                List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(offline.getApplNo(),
                        offline.getOrgId());
                workflowAction.setAttachementId(attachmentId);
            }
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
        }

        String processName = serviceMasterService.getProcessName(offline.getServiceId(), offline.getOrgId());
        processParameter.setProcessName(processName);
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setOrgId(offline.getOrgId());
        if (StringUtils.isEmpty(offline.getChallanNo())) {
            applicationMetadata.setPaymentMode(MainetConstants.PAYMENT.ONLINE);
        } else {
            applicationMetadata.setPaymentMode(MainetConstants.PAYMENT.OFFLINE);
        }
        applicationMetadata.setIsCheckListApplicable(false);
        if (offline.isDocumentUploaded()) {
            String checklistFlag = null;
            final List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", org);
            for (final LookUp lookUp : lookUps) {
                if (serviceMaster.getSmChklstVerify() != null
                        && lookUp.getLookUpId() == serviceMaster.getSmChklstVerify().longValue()) {
                    checklistFlag = lookUp.getLookUpCode();
                    break;
                }
            }
            if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagN)
                    && MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
                applicationMetadata.setIsCheckListApplicable(false);
            }
            if (serviceMaster != null && serviceMaster.getSmCheckListReq().equals(MainetConstants.FlagY)
                    && MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)) {
                applicationMetadata.setIsCheckListApplicable(true);
            }
        }
        applicationMetadata.setReferenceId(offline.getReferenceNo());
        applicationMetadata.setApplicationId(offline.getApplNo());
        applicationMetadata.setIsFreeService(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(workflowAction);
        return processParameter;
    }

    TaskAssignment setRequesterTask(final CommonChallanDTO offline) {
        TaskAssignment assignment = new TaskAssignment();
        Set<String> actorIds = new HashSet<>();
        CFCApplicationAddressEntity addressEntity = iCFCApplicationAddressService
                .getApplicationAddressByAppId(offline.getApplNo(), offline.getOrgId());
        assignment.setActorId(addressEntity.getUserId() + MainetConstants.operator.COMMA + offline.getMobileNumber());
        actorIds.add(addressEntity.getUserId().toString());
        actorIds.add(offline.getMobileNumber());
        assignment.setActorIds(actorIds);
        assignment.setOrgId(addressEntity.getOrgId().getOrgid());
        return assignment;
    }

    private WorkflowMas getWorkflowMaster(List<WorkflowMas> worKFlowList, Double amount, ServiceMaster service,
            WardZoneBlockDTO dwzDto) {
        WorkflowMas workflowMas = null;
        if (CollectionUtils.isNotEmpty(worKFlowList)) {
            for (WorkflowMas mas : worKFlowList) {
                if (mas.getStatus().equalsIgnoreCase("Y")) {
                    if (mas.getToAmount() != null) {
                        workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
                                service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
                                new BigDecimal(amount), null, dwzDto.getAreaDivision1(), dwzDto.getAreaDivision2(),
                                dwzDto.getAreaDivision3(), dwzDto.getAreaDivision4(), dwzDto.getAreaDivision5());
                        break;
                    } else {
                        workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
                                service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
                                dwzDto.getAreaDivision1(), dwzDto.getAreaDivision2(), dwzDto.getAreaDivision3(),
                                dwzDto.getAreaDivision4(), dwzDto.getAreaDivision5());
                        break;
                    }
                }
            }
        } else {
            throw new FrameworkException("Workflow Not Found");
        }
        return workflowMas;
    }
}
