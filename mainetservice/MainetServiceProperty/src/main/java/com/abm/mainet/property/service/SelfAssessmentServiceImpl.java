package com.abm.mainet.property.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.property.dao.IProvisionalAssessmentMstDao;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailHistEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.PropertyDetEntity;
import com.abm.mainet.property.domain.PropertyFactorEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.domain.PropertyRoomDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentFactorDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.ProvisionalAssessmentRoomDetailEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.PropertyCommonInfoDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.PropertyMastRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.property.repository.SelfAssessmentMasJpaRepository;
import com.abm.mainet.property.repository.TbAsTryRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.google.common.util.concurrent.AtomicDouble;

@Service

public class SelfAssessmentServiceImpl implements SelfAssessmentService {

    private final Logger LOGGER = Logger.getLogger(SelfAssessmentServiceImpl.class);

    @Resource
    private LocationMasJpaRepository locationMasJpaRepository;

    @Resource
    private SelfAssessmentMasJpaRepository selfAssessmentJpaRepository;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private BRMSCommonService brmsCommonService;
    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private TbAsTryRepository tbAsTryRepository;

    @Autowired
    private IProvisionalAssessmentMstDao iProvisionalAssessmentMstDao;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;
    
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;
	
	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;
	
	@Autowired
	private IWorkflowExecutionService workflowExecutionService;
	
    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;
    
    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;
    
    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
    private IChallanService challanService;
    
    @Autowired
    private IFinancialYearService iFinancialYearService;
    
    @Autowired
    private PropertyBillPaymentService PropertyBillPaymentService;
     
    @Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;
    
    @Autowired
	private  AssesmentMstRepository assesmentMstRepository;

    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private PropertyMastRepository propertyMastRepository; 
    
    @Autowired
    private IEmployeeService employeeService;
       
	@Autowired
	private PrimaryPropertyService primaryPropertyService;
    
    @Override
    @Transactional(readOnly = true)
    public List<LookUp> getAllBillscheduleWithoutCurrentScheduleByOrgid(Organisation orgid) {
        final Map<Integer, String> monthPrefix = new HashMap<>(0);
        final List<LookUp> monthLookup = CommonMasterUtility
                .getLookUps(MainetConstants.Property.propPref.MONTH, orgid);
        for (final LookUp mon : monthLookup) {
            monthPrefix.put(
                    Integer.valueOf(mon.getLookUpCode()),
                    mon.getDescLangFirst());
        }

        List<Object[]> result = billingScheduleService.getBillscheduleByOrgid(orgid.getOrgid());
        BillingScheduleDetailEntity billSchDet = billingScheduleService
                .getSchedulebySchFromDate(orgid.getOrgid(), new Date());
        List<LookUp> list = new ArrayList<>(0);
        LookUp lookup = null;
        if (result != null && !result.isEmpty()) {
            for (Object[] data : result) {
                if (!data[0].toString().equals(billSchDet.getSchDetId().toString())) {
                    lookup = new LookUp();
                    lookup.setLookUpId(Double.valueOf(data[0].toString()).longValue());
                    Date from = (Date) data[2];
                    Date to = (Date) data[3];
                    Date startFrom = (Date) data[4];
                    Date startTo = (Date) data[5];
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(from);
                    int fromyear = cal.get(Calendar.YEAR);
                    cal.setTime(to);
                    int toYear = cal.get(Calendar.YEAR);
                    cal.setTime(startFrom);
                    int monthFrom = cal.get(Calendar.MONTH);
                    cal.setTime(startTo);
                    int monthTo = cal.get(Calendar.MONTH);
                    String fromMonth = monthPrefix.get(monthFrom + 1);// cal return 0 to 11 adding 1 for Month
                    String toMonth = monthPrefix.get(monthTo + 1);
                    lookup.setLookUpCode(fromyear + "-" + toYear + "->" + fromMonth + "-" + toMonth);
                    list.add(lookup);
                }
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> getAllBillscheduleByOrgid(Organisation orgid) {
        final Map<Integer, String> monthPrefix = new HashMap<>(0);
        final List<LookUp> monthLookup = CommonMasterUtility
                .getListLookup(MainetConstants.Property.propPref.MONTH, orgid);
        for (final LookUp mon : monthLookup) {
            monthPrefix.put(
                    Integer.valueOf(mon.getLookUpCode()),
                    mon.getDescLangFirst());
        }

        List<Object[]> result = billingScheduleService.getBillscheduleByOrgid(orgid.getOrgid());

        List<LookUp> list = new ArrayList<>(0);
        LookUp lookup = null;
        if (result != null && !result.isEmpty()) {
            for (Object[] data : result) {

                lookup = new LookUp();
                lookup.setLookUpId(Double.valueOf(data[0].toString()).longValue());
                Date from = (Date) data[2];
                Date to = (Date) data[3];
                Date startFrom = (Date) data[4];
                Date startTo = (Date) data[5];
                Calendar cal = Calendar.getInstance();
                cal.setTime(from);
                int fromyear = cal.get(Calendar.YEAR);
                cal.setTime(to);
                int toYear = cal.get(Calendar.YEAR);
                cal.setTime(startFrom);
                int monthFrom = cal.get(Calendar.MONTH);
                cal.setTime(startTo);
                int monthTo = cal.get(Calendar.MONTH);
                String fromMonth = monthPrefix.get(monthFrom + 1);// cal return 0 to 11 adding 1 for Month
                String toMonth = monthPrefix.get(monthTo + 1);
                lookup.setLookUpCode(fromyear + "-" + toYear + "->" + fromMonth + "-" + toMonth);
                list.add(lookup);

            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> getAllBillschedulByOrgid(Organisation orgid) {
        final Map<Integer, String> monthPrefix = new HashMap<>(0);
        final List<LookUp> monthLookup = CommonMasterUtility
                .getListLookup(MainetConstants.Property.propPref.MONTH, orgid);
        for (final LookUp mon : monthLookup) {
            monthPrefix.put(
                    Integer.valueOf(mon.getLookUpCode()),
                    mon.getDescLangFirst());
        }

        List<Object[]> result = billingScheduleService.getBillscheduleByOrgid(orgid.getOrgid());
        List<LookUp> list = new ArrayList<>(0);
        LookUp lookup = null;
        if (result != null && !result.isEmpty()) {
            for (Object[] data : result) {
                lookup = new LookUp();
                lookup.setLookUpId(Double.valueOf(data[0].toString()).longValue());
                Date from = (Date) data[2];
                Date to = (Date) data[3];
                Date startFrom = (Date) data[4];
                Date startTo = (Date) data[5];
                Calendar cal = Calendar.getInstance();
                cal.setTime(from);
                int fromyear = cal.get(Calendar.YEAR);
                cal.setTime(to);
                int toYear = cal.get(Calendar.YEAR);
                cal.setTime(startFrom);
                int monthFrom = cal.get(Calendar.MONTH);
                cal.setTime(startTo);
                int monthTo = cal.get(Calendar.MONTH);
                String fromMonth = monthPrefix.get(monthFrom + 1);// cal return 0 to 11 adding 1 for Month
                String toMonth = monthPrefix.get(monthTo + 1);
                lookup.setLookUpCode(fromyear + "-" + toYear + "->" + fromMonth + "-" + toMonth);
                list.add(lookup);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<DocumentDetailsVO> fetchCheckList(ProvisionalAssesmentMstDto dto,
            List<ProvisionalAssesmentFactorDtlDto> factDto) {
        final WSRequestDTO wsdto = new WSRequestDTO();
        List<DocumentDetailsVO> docs = null;
        wsdto.setModelName(MainetConstants.Property.CHECK_LIST_MODEL);
        WSResponseDTO response = brmsCommonService.initializeModel(wsdto);
        // RestClient.callBRMS(wsdto, ServiceEndpoints.Property.INIT_MODEL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
            CheckListModel checkListModel = (CheckListModel) checklist.get(0);
            docs = populateCheckListModel(dto, checkListModel, wsdto, factDto);
        }
        return docs;
    }

    private List<DocumentDetailsVO> populateCheckListModel(ProvisionalAssesmentMstDto dto,
            CheckListModel checklistModel, final WSRequestDTO wsdto, List<ProvisionalAssesmentFactorDtlDto> factDto) {
        Map<Long, DocumentDetailsVO> docMap = new HashMap<>(0);
        List<DocumentDetailsVO> checklist = new ArrayList<>(0);
        List<ProvisionalAssesmentDetailDto> detDto = dto.getProvisionalAssesmentDetailDtoList();
        checklistModel.setOrgId(dto.getOrgId());

        Organisation org = new Organisation();
        org.setOrgid(dto.getOrgId());
        checklistModel.setServiceCode(serviceMasterRepository.getServiceShortCode(dto.getSmServiceId(), dto.getOrgId()));
        if (null != dto.getAssOwnerType()) {
            checklistModel.setApplicantType(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getAssOwnerType(),
                    org).getDescLangFirst());
        }
        if (dto.getAssLandType() != null && dto.getAssLandType() > 0) {
            String lookUpCode = CommonMasterUtility.findLookUpCode(MainetConstants.Property.propPref.LDT, dto.getOrgId(),
                    dto.getAssLandType());
            if (lookUpCode.equals(MainetConstants.Property.LandType.ENT)) {
                checklistModel.setFactor2(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getAssLandType(),
                        org).getDescLangFirst());
            } else {
                checklistModel.setFactor2(MainetConstants.CommonConstants.NA);
            }
        }

        if (CollectionUtils.isNotEmpty(factDto)) {
            setFactorDetails(checklistModel, factDto, dto.getOrgId());
        }

        if (Utility.isEnvPrefixAvailable(org, "EXM") && MapUtils.isNotEmpty(checklistModel.getFactor())) {
            checklistModel.setFactor3(MainetConstants.FlagY);
        } else {
            checklistModel.setFactor3(MainetConstants.FlagN);
        }
        if (MainetConstants.Property.MANUAL.equals(dto.getBillPayment())) {
            checklistModel.setFactor3(MainetConstants.Property.MANUAL);
        }
        checklistModel.setIsBPL(MainetConstants.CommonConstants.NA);
        if (detDto != null && !detDto.isEmpty()) {
            setDetails(checklistModel, detDto, org, wsdto, docMap);
        }
        if (!docMap.isEmpty()) {
            docMap.forEach((key, value) -> {
                checklist.add(value);
            });
            checklist.sort(Comparator.comparing(DocumentDetailsVO::getDocumentSerialNo));// Sorting List by collection sequence
        }
        return checklist;
    }

    private void setFactorDetails(CheckListModel checklistModel, List<ProvisionalAssesmentFactorDtlDto> factDto,
            Long orgId) {
        // SET Factorvalue in Excemption defect no 34203
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        Map<String, String> unitFactors = new HashMap<>(0);
        factDto.forEach(fact -> {
            LookUp factorLookup = null;
            LookUp lookup = null;
            if (fact.getAssfFactorId() != null) {
                factorLookup = CommonMasterUtility.lookUpByLookUpIdAndPrefix(fact.getAssfFactorId(),
                        MainetConstants.Property.propPref.FCT, orgId);
            }
            if (fact.getAssfFactorValueId() != null) {
                lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(), organisation);
            }
			if (factorLookup != null && lookup != null) {
				unitFactors.put(factorLookup.getDescLangFirst(), lookup.getDescLangFirst());
			}

        });
        checklistModel.setFactor(unitFactors);
    }

    @Override
    public void setFactorMappingToAssDto(List<ProvisionalAssesmentFactorDtlDto> provFactList, ProvisionalAssesmentMstDto dto) {
        // Factor and Unit Binding
        provFactList.forEach(factDtlDto -> {
            if (factDtlDto.getAssfFactorValueId() != null) {
                if (factDtlDto.getUnitNoFact().equals(MainetConstants.Property.ALL)) {
                    dto.getProvisionalAssesmentDetailDtoList().forEach(detDto -> {
                        addFactorToDto(factDtlDto, detDto);
                    });
                } else {
                    dto.getProvisionalAssesmentDetailDtoList().forEach(detDto -> {
                        if (detDto.getAssdUnitNo().toString().equals(factDtlDto.getUnitNoFact())) {
                            addFactorToDto(factDtlDto, detDto);
                        }
                    });
                }
            }
        });
    }

    private void addFactorToDto(ProvisionalAssesmentFactorDtlDto factDtlDto, ProvisionalAssesmentDetailDto detDto) {
        /* if (factDtlDto.getProAssfId() == 0) { */
        // New factor added
        boolean result = detDto.getProvisionalAssesmentFactorDtlDtoList().stream()
                .anyMatch(fact -> fact.getAssfFactorValueId().equals(factDtlDto.getAssfFactorValueId()));
        if (!result) {
            detDto.getProvisionalAssesmentFactorDtlDtoList().add(factDtlDto);
        } else {
            detDto.getProvisionalAssesmentFactorDtlDtoList().stream()
                    .filter(fact -> fact.getAssfFactorValueId().equals(factDtlDto.getAssfFactorValueId()))
                    .forEach(fact -> {
                        fact.setAssfFactorId(factDtlDto.getAssfFactorId());
                        fact.setAssfFactorValueId(factDtlDto.getAssfFactorValueId());
                    });
        }
        /*
         * } else { // Existing factor if value change detDto.getProvisionalAssesmentFactorDtlDtoList().stream() .filter(fact ->
         * fact.getProAssfId() == factDtlDto.getProAssfId()) .forEach(fact -> {
         * fact.setAssfFactorId(factDtlDto.getAssfFactorId()); fact.setAssfFactorValueId(factDtlDto.getAssfFactorValueId()); }); }
         */
    }

    @SuppressWarnings("unchecked")
    private void setDetails(CheckListModel checklistModel, List<ProvisionalAssesmentDetailDto> detDto, Organisation org,
            WSRequestDTO wsdto, Map<Long, DocumentDetailsVO> docMap) {
        detDto.forEach(dto -> {
            if (dto.getAssdUsagetype1() != null) {
                checklistModel
                        .setUsageSubtype1(CommonMasterUtility.getHierarchicalLookUp(dto.getAssdUsagetype1(), org)
                                .getDescLangFirst());
            }
            if (dto.getAssdUsagetype2() != null) {
                checklistModel
                        .setUsageSubtype2(CommonMasterUtility.getHierarchicalLookUp(dto.getAssdUsagetype2(), org)
                                .getDescLangFirst());
            }
            if (dto.getAssdUsagetype3() != null) {
                checklistModel
                        .setUsageSubtype3(CommonMasterUtility.getHierarchicalLookUp(dto.getAssdUsagetype3(), org)
                                .getDescLangFirst());
            }
            if (dto.getAssdUsagetype4() != null) {
                checklistModel
                        .setUsageSubtype4(CommonMasterUtility.getHierarchicalLookUp(dto.getAssdUsagetype4(), org)
                                .getDescLangFirst());
            }
            if (dto.getAssdUsagetype5() != null) {
                checklistModel
                        .setUsageSubtype5(CommonMasterUtility.getHierarchicalLookUp(dto.getAssdUsagetype5(), org)
                                .getDescLangFirst());
            }
            if (dto.getAssdOccupancyType() != null) {
                checklistModel.setFactor1(CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getAssdOccupancyType(), org)
                        .getDescLangFirst());
            }
            wsdto.setDataModel(checklistModel);
            // WSResponseDTO response = RestClient.callBRMS(wsdto, ServiceEndpoints.Property.PROP_CHECK_LIST);
            WSResponseDTO response = brmsCommonService.getChecklist(wsdto);
            // RestClient.callBRMS(wsdto, ServiceEndpoints.Property.PROP_CHECK_LIST);
            if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                // final List<?> docs = RestClient.castResponse(response, DocumentDetailsVO.class);
                // (List<DocumentDetailsVO>) response.getResponseObj();
                List<DocumentDetailsVO> checklistData = (List<DocumentDetailsVO>) response.getResponseObj();
                long count = 1;
                for (DocumentDetailsVO doc : checklistData) {
                    doc.setDocumentSerialNo(count);
                    count++;
                    docMap.put(doc.getDocumentId(), doc);
                }
            }
        });
    }

    @Override
    public Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, final Organisation organisation,
            Long deptId, List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge) {
        if (!billMasList.isEmpty()) {
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas, organisation, deptId);
            if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                billDisDtoList.addAll(rebateDtoList);
            }
            if (surCharge != null) {
                billDisDtoList.add(surCharge);
            }
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {
                taxWiseRebate.entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
                });
            }
            return propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList, organisation, deptId);
        }
        return null;
    }

    @Override
    public Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, final Organisation organisation,
            Long deptId, Map<Long, BillDisplayDto> taxWiseRebate, List<BillDisplayDto> otherTaxesDisDtoList) {
        if (!billMasList.isEmpty()) {
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas, organisation, deptId);
            billDisDtoList.addAll(otherTaxesDisDtoList);
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {
                taxWiseRebate.entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
					if (Utility.isEnvPrefixAvailable(organisation, "ASCL")
							&& Utility.isEnvPrefixAvailable(organisation, "DBA")) {
						BillDisplayDto value = entry.getValue();
						billDisDtoList.forEach(bill ->{
							if(bill.getTaxId().equals(value.getParentTaxId())) {
								bill.setCurrentYearTaxAmt(BigDecimal.valueOf(bill.getCurrentYearTaxAmt().doubleValue() + value.getCurrentYearTaxAmt().doubleValue()));
								bill.setTotalTaxAmt(BigDecimal.valueOf(bill.getTotalTaxAmt().doubleValue() + value.getCurrentYearTaxAmt().doubleValue()));
							}
						});
					}
                });
            }
            return propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList, organisation, deptId);
        }
        return null;
    }

    /*
     * @Override public Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, final Organisation
     * organisation, Long deptId, BillDisplayDto rebateDto, Map<Long, BillDisplayDto> taxWiseRebate, String propNo) { if
     * (!billMasList.isEmpty()) { TbBillMas billMas = billMasList.get(billMasList.size() - 1); List<BillDisplayDto> billDisDtoList
     * = propertyService.getTaxListForDisplay(billMas, organisation, deptId); if (rebateDto != null) {
     * billDisDtoList.add(rebateDto); } BillDisplayDto advanceDto = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(propNo,
     * organisation.getOrgid()); // get advance amt if (advanceDto != null) { billDisDtoList.add(advanceDto); } if (taxWiseRebate
     * != null && !taxWiseRebate.isEmpty()) { taxWiseRebate.entrySet().forEach(entry -> { billDisDtoList.add(entry.getValue());
     * }); } return propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList, organisation, deptId); } return null; }
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> saveSelfAssessment(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
            List<Long> finYearList, List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto,
            BillDisplayDto advanceAmt) {
        Long orgId = offline.getOrgId();
        Organisation org=organisationService.getOrganisationById(orgId);
        Long deptId = offline.getDeptId();
        Long empId = offline.getUserId();
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        setRequestApplicantDetails(reqDto, provAsseMstDto, orgId, deptId, offline.getLangId(), empId,
                provAsseMstDto.getSmServiceId());
        /*final Long applicationNo = applicationService.createApplication(reqDto);
        provAsseMstDto.setApmApplicationId(applicationNo);
        offline.setApplNo(applicationNo);*/
        provAsseMstDto.setEditableDate(new Date());
        LookUp noOfDaysLookUp = null;
		try {
			 noOfDaysLookUp = CommonMasterUtility.getValueFromPrefixLookUp("D", "NOD",organisation);
		}catch (Exception exception) {
		
		}
		if(noOfDaysLookUp != null && StringUtils.isNotBlank(noOfDaysLookUp.getOtherField())) {
			LocalDate currentDate = LocalDate.now();
			Date afterAddingDaysTocurrentdate = Date.from(
					currentDate.plusDays(Long.valueOf(noOfDaysLookUp.getOtherField())).atStartOfDay(ZoneId.systemDefault()).toInstant());
			 provAsseMstDto.setEditableDate(afterAddingDaysTocurrentdate);
		}
		
		//100510 - Unique property ID
		if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.UPI)) {
			provAsseMstDto.setUniquePropertyId(uniquePropertyId(provAsseMstDto, org));
		}
		
		//120052 - In case of individual billing multiple entries inside master table in combination with flat no
		Map<Long, Long> assIdMap = null;
		Long applicationNo=null;
		Map<String, Long> flatWiseAppIdmap = new LinkedHashMap<>();
		if (provAsseMstDto.getBillMethod() != null
				&& MainetConstants.FlagI.equals(CommonMasterUtility.getNonHierarchicalLookUpObject(provAsseMstDto.getBillMethod(), organisation)
						.getLookUpCode())) {
			
			//To generate flat wise application no			
			for (ProvisionalAssesmentDetailDto det : provAsseMstDto.getProvisionalAssesmentDetailDtoList()) {
				if (!flatWiseAppIdmap.containsKey(det.getFlatNo())) {
					Long applNo = applicationService.createApplication(reqDto);
					flatWiseAppIdmap.put(det.getFlatNo(), applNo);
				}
			}
			assIdMap = iProvisionalAssesmentMstService.saveProvisionalAssessmentWithFlatNo(provAsseMstDto, orgId, empId,
					finYearList, flatWiseAppIdmap);
		} else {
			applicationNo = applicationService.createApplication(reqDto);
	        provAsseMstDto.setApmApplicationId(applicationNo);
	        offline.setApplNo(applicationNo);
			assIdMap = iProvisionalAssesmentMstService.saveProvisionalAssessment(provAsseMstDto, orgId, empId,
					finYearList, applicationNo);
		}      
        List<BillReceiptPostingDTO> billRecePstingDto = null;
        double ajustedAmt = 0;
        if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
        	billMasList.get(0).setPropNo(provAsseMstDto.getAssNo());
            billRecePstingDto = knowkOffAdvanceAmt(billMasList, advanceAmt, organisation,offline.getManualReeiptDate(),provAsseMstDto.getAssNo());
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
            	
            	//In case of individual property to set documents against each application no
				if (MapUtils.isNotEmpty(flatWiseAppIdmap)) {
					for (Map.Entry<String, Long> map : flatWiseAppIdmap.entrySet()) {
						reqDto.setApplicationId(map.getValue());
						fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
					}
				} else {
					reqDto.setApplicationId(applicationNo);
					fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
				}
            }
            // CHALLAN case will skip
            if (offline.getOnlineOfflineCheck() == null
                    || MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER.equals(offline.getOnlineOfflineCheck())
                    || MainetConstants.PAYMENT_TYPE.ONLINE.equals(offline.getOnlineOfflineCheck())) {
            	
            	//97675 - Workflow based on Amount           	
    			if (Utility.isEnvPrefixAvailable(org,MainetConstants.ABW)) {
            		initiateWorkFlowBasedOnAmount(provAsseMstDto, offline,orgId,flatWiseAppIdmap);
            	}else {
            		workflowRequestService.initiateAndUpdateWorkFlowProcess(offline, null);
            	}              
            }
            // D#34197 below inside method (PropetyServiceImpl->getWordZoneBlockByApplicationId)getting data from persistent state
            // as per Discussion With RAJESH Sir
            saveDemandLevelRebate(provAsseMstDto, empId, deptId, billMasList, rebateRecDto, organisation, provBillList);
            saveAdvanceAmt(provAsseMstDto, empId, deptId, advanceAmt, organisation, billMasList, provAsseMstDto.getLgIpMac(),
                    provBillList, billRecePstingDto, ajustedAmt,null,0);
            copyToHistoryAndDeleteMainBill(provAsseMstDto, orgId,empId,billMasList,finYearList,provAsseMstDto.getLgIpMac());
            return bmIds;
        }
        return null;
    }

	private void initiateWorkFlowBasedOnAmount(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline,
			Long orgid, Map<String, Long> flatWiseAppIdmap) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgid);
		ServiceMaster service = null;
		if(provAsseMstDto.getSmServiceId() != null) {
			service = serviceMasterService.getServiceMaster(provAsseMstDto.getSmServiceId(), orgid);
		}
		if(service == null) {
			service = serviceMasterService.getServiceByShortName(MainetConstants.Property.SAS, orgid);
		}
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
					if (StringUtils.isNotBlank(detail.getFlatNo())
							&& StringUtils.isNotBlank(provAsseMstDto.getAssNo())) {
						flatWiseMap.put(detail.getFlatNo(), provAsseMstDto.getAssNo());
						flatWiseAlvMap.put(detail.getFlatNo(),
								flatWiseAlvMap.get(detail.getFlatNo()) != null
										? (flatWiseAlvMap.get(detail.getFlatNo()) + detail.getAssdRv())
										: detail.getAssdRv());
					}
				}
			}
			if (provAsseMstDto.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
					.getNonHierarchicalLookUpObject(provAsseMstDto.getBillMethod(), organisation).getLookUpCode())) {

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
			} else {
				workflowMas = getWorkflowMaster(worKFlowList, totalRv, service, dwzDto);
				if (workflowMas != null) {
					try {
						offline.setReferenceNo(provAsseMstDto.getAssNo());
						initiateWorkflow(offline, workflowMas, service);
					} catch (Exception e) {
						LOGGER.error("Unsuccessful initiation/updation of task for application : " + e);
						throw new FrameworkException("Exception while calling work flow", e);
					}
				}
			}
		}
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

	private void copyToHistoryAndDeleteMainBill(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId, Long empId,List<TbBillMas> billMasList,List<Long> finYearList,String lgIpAddress) {
		Organisation org = new Organisation();
		org.setOrgid(orgId);
        List<TbBillMas> arrearBillMasList = propertyMainBillService.fetchNotPaidBillForAssessment(provAsseMstDto.getAssNo(),
                orgId);
        if(Utility.isEnvPrefixAvailable(org, "DBA")) {
        	arrearBillMasList = new ArrayList<TbBillMas>();
        	List<TbBillMas> existingBillList = propertyMainBillService.fetchBillSForViewProperty(provAsseMstDto.getAssNo());
        	Long finYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(new Date());
        	List<ProvisionalAssesmentMstDto> allYearAssesment = assesmentMastService.getAllYearAssesment(finYearId, provAsseMstDto.getAssNo());
    		if(CollectionUtils.isNotEmpty(allYearAssesment)) {
    			Employee employee = employeeService.findEmployeeById(empId);
    			allYearAssesment.forEach(currentYearAssesmentDto ->{
    				currentYearAssesmentDto.setApmApplicationId(provAsseMstDto.getApmApplicationId());
    	    		maintainHistory(currentYearAssesmentDto, employee);
    			});
    		}
        	List<AssesmentMastEntity> currentYearAssesmentList = assesmentMstRepository.getCurrentYearAssesment(finYearList, provAsseMstDto.getAssNo());
        	if(CollectionUtils.isNotEmpty(currentYearAssesmentList)) {
        		currentYearAssesmentList.forEach(currentYearAssesment ->{
        			assesmentMstRepository.delete(currentYearAssesment.getProAssId());
        		});
        	}
        	for (TbBillMas billMas : existingBillList) {
        		Optional<TbBillMas> findAny = billMasList.stream().filter(currGenBill -> currGenBill.getBmYear().equals(billMas.getBmYear())).findAny();
        		if(billMas.getBmYear().equals(finYearId)) {
        			arrearBillMasList.add(billMas);
        		}else if(findAny.isPresent()){
        			arrearBillMasList.add(billMas);
        		}
			}
        	    		
        	Optional<TbBillMas> findFirst = arrearBillMasList.stream().filter(billMas -> billMas.getBmYear().equals(finYearId)).findFirst();
        	if(!findFirst.isPresent()) {
        		List<TbBillMas> currBillList = propertyMainBillService.fetchAllBillByPropNoAndCurrentFinId(provAsseMstDto.getAssNo(), orgId, finYearId);
        		arrearBillMasList.addAll(currBillList);
        	}
        }
        if (arrearBillMasList != null && !arrearBillMasList.isEmpty()) {
        	arrearBillMasList.forEach(bill ->{
        		bill.setApmApplicationId(provAsseMstDto.getApmApplicationId());
        	});
            propertyMainBillService.copyDataFromMainBillDetailToHistory(arrearBillMasList,null,empId,lgIpAddress);
            propertyMainBillService.deleteMainBillWithDtoById(arrearBillMasList);
        }
    }

    @Transactional
    @Override
    public List<Long> saveSelfAssessmentWithEdit(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId, Long empId, Long deptId,
            int langId, List<Long> finYearList, List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto) {

        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(provAsseMstDto.getApmApplicationId(), orgId);
        List<TbBillMas> billMasListOld = iProvisionalBillService.getProvisionalBillMasByPropertyNo(provAsseMstDto.getAssNo(),
                orgId);

        iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,empId,provAsseMstDto);
        iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(billMasListOld,MainetConstants.FlagW);
        iReceiptEntryService.inActiveAllRebetReceiptByAppNo(orgId, provAsseMstDto.getApmApplicationId(), deptId,
                MainetConstants.Property.REBATE_DELETE_REMARK,
                empId);
        propertyAuthorizationService.setUniqueIdentiFromOldBillToNewBill(billMasList, billMasListOld);
        setRequestApplicantDetails(reqDto, provAsseMstDto, orgId, deptId, langId, empId, provAsseMstDto.getSmServiceId());
        // ChallanMaster challnMaster = iChallanService.getchallanOfflineType(provAsseMstDto.getApmApplicationId(), orgId);
        Map<Long, Long> assIdMap = iProvisionalAssesmentMstService.saveProvisionalAssessment(provAsseMstDto, orgId, empId,
                finYearList, provAsseMstDto.getApmApplicationId());
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
        List<Long> bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, empId,
                provAsseMstDto.getAssNo(), assIdMap, provBillList, provAsseMstDto.getLgIpMac());
        saveDemandLevelRebate(provAsseMstDto, empId, deptId, billMasList, rebateRecDto, organisation, provBillList);
        if ((provAsseMstDto.getDocs() != null) && !provAsseMstDto.getDocs().isEmpty()) {
            reqDto.setApplicationId(provAsseMstDto.getApmApplicationId());
            fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
        }
        return bmIds;
    }

    @Transactional
    @Override
    public void sendMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId, Long userId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
        dto.setMobnumber(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
        dto.setUserId(userId);
        ServiceMaster service = serviceMasterRepository.getServiceMaster(provAsseMstDto.getSmServiceId(),
                provAsseMstDto.getOrgId());
        if (langId == MainetConstants.MARATHI) {
            dto.setServName(service.getSmServiceNameMar());
        } else {
            dto.setServName(service.getSmServiceName());
        }
        dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        dto.setPropertyNo(provAsseMstDto.getAssNo());
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                MainetConstants.URLBasedOnShortCode.valueOf(service.getSmShortdesc()).getUrl(),
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, langId);
    }

    @Override
    public void saveDemandLevelRebate(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId,
            List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto, final Organisation organisation,
            List<ProvisionalBillMasEntity> provBillList) {
        if (rebateRecDto != null && !rebateRecDto.isEmpty()) {
            mappingDummyKeyToActualKey(provBillList, rebateRecDto);
            CommonChallanDTO challanDto = new CommonChallanDTO();
            setChallanDtoForRebatePosting(challanDto, provAsseMstDto, organisation.getOrgid(), deptId, empId, null,0);
            iReceiptEntryService.saveDemandLevelRebateAndAccountPosting(challanDto, organisation, rebateRecDto);
        }
    }

    /*
     * Adjusting Advance amount in bill 1)update Advance table 2)insert receipt with flag AR(ADVANCE_RECEIV)3)Account Posting
     */

    @Override
    public void saveAdvanceAmt(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId, BillDisplayDto advanceAmt,
            Organisation organisation, List<TbBillMas> billMasList, String ipAddress, List<ProvisionalBillMasEntity> provBillList,
            List<BillReceiptPostingDTO> billRecePstingDto, double ajustedAmt,String flatNo,int langId) {
        if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
            CommonChallanDTO challanDto = new CommonChallanDTO();
            mappingDummyKeyToActualKey(provBillList, billRecePstingDto);
            final LookUp advanceReceipt = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.RVPrefix.ADVANCE_RECEIV,
                    MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, organisation);
            asExecssAmtService.updateExecssAmtByAdjustedAmt(provAsseMstDto.getAssNo(), organisation.getOrgid(), ajustedAmt, empId,
                    ipAddress,flatNo);
            AtomicDouble totAmt = new AtomicDouble(0);
            billRecePstingDto.forEach(rebate -> {
                totAmt.addAndGet(rebate.getTaxAmount());
            });
            challanDto.setAmountToPay(totAmt.toString());
            challanDto.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp("T",
                    PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, organisation).getLookUpId());
            setChallanDtoForRebatePosting(challanDto, provAsseMstDto, organisation.getOrgid(), deptId, empId,
                    advanceReceipt.getLookUpCode(),langId);
            try {
            	TbServiceReceiptMasEntity insertInReceiptMaster = iReceiptEntryService.insertInReceiptMaster(challanDto, billRecePstingDto);
            	// challanService.setReceiptDtoAndSaveDuplicateService(insertInReceiptMaster, challanDto);
            }catch (Exception exception) {
            	LOGGER.error("Exception occured while saving duplicate receipt for advance payment" + exception);
			}
            
        }
    }

    /*
     * pure Advance amount entry in receipt master with flag A and account posting(come from property authorization bill decrease)
     */
    @Override
    public void savePureAdvancePayment(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId,
            Organisation org, String ipAddress, Double advAmt) {
        CommonChallanDTO challanDto = new CommonChallanDTO();
        List<BillReceiptPostingDTO> billRecePstingDto = null;
        LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(PrefixConstants.TAX_SUBCATEGORY.ADVANCE_PAYMENT,
                PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                org);
        List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
        if (CollectionUtils.isNotEmpty(taxList)) {
            billRecePstingDto = new ArrayList<>();
            BillReceiptPostingDTO dto = new BillReceiptPostingDTO();
            TbTaxMasEntity taxMast = taxList.get(0);
            dto.setPayableAmount(advAmt);
            dto.setTaxId(taxMast.getTaxId());
            dto.setTaxCategory(taxMast.getTaxCategory1());
            billRecePstingDto.add(dto);
        }
        challanDto.setAmountToPay(advAmt.toString());
        challanDto.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp("T",
                PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, org).getLookUpId());
        setChallanDtoForRebatePosting(challanDto, provAsseMstDto, org.getOrgid(), deptId, empId,
                PrefixConstants.RVPrefix.ADVANCE_RECEIV,0);
        iReceiptEntryService.insertInReceiptMaster(challanDto, billRecePstingDto);
    }

    private void setChallanDtoForRebatePosting(CommonChallanDTO challanDto, ProvisionalAssesmentMstDto asseMstDto, Long orgId,
            Long deptId, Long empId, String receiptType,int langId) {
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        challanDto.setApplicantName(ownDtlDto.getAssoOwnerName());
        challanDto.setUniquePrimaryId(asseMstDto.getAssNo());
        challanDto.setLgIpMac(asseMstDto.getLgIpMac());
        challanDto.setApplNo(asseMstDto.getApmApplicationId());
        challanDto.setDeptId(deptId);
        challanDto.setOrgId(orgId);
        challanDto.setServiceId(asseMstDto.getSmServiceId());
        challanDto.setUserId(empId);
        challanDto.setLgIpMac(asseMstDto.getLgIpMac());
        challanDto.setLangId(langId);
        challanDto.setPaymentCategory(receiptType);
        challanDto.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);

    }

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
        if (asseMstDto.getBillTotalAmt() <= 0) {
            reqDto.setPayStatus(MainetConstants.FlagY);
        }
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(empId);
        if (asseMstDto.getApmDraftMode() != null) {
            reqDto.setApmMode(asseMstDto.getApmDraftMode());
        }
    }

    /*
     * knowking off rebate and exemption from Bill. if one rebate is applicable and balamt is zero then second rebate or exemption
     * should not be applied
     */

    @Override
    public List<BillReceiptPostingDTO> knowkOffDemandLevelRebateAndExemption(List<TbBillMas> billMasList,
            Map<Date, List<BillPresentAndCalculationDto>> demandLevelRebateMap,
            Organisation org, Map<Long, BillDisplayDto> taxWiseRebate) {
    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + "knowkOffDemandLevelRebateAndExemption() method");
        List<TbBillMas> billMasTempList = new ArrayList<>();
        boolean reachLevel = false;
        List<BillReceiptPostingDTO> rebteReciptDtoList = new ArrayList<>();
        
       
        for (Map.Entry<Date, List<BillPresentAndCalculationDto>> entry : demandLevelRebateMap.entrySet()) {
        	
        	
        	 /**
             * Defect No : 140826
             * This logic for SKDCL, here we are calculating tax wise exemption and after adding all taxes ,
             *  then ceil that amount, after before ceil amount difference is added to first tax
             */
        	
        	 AtomicDouble totalRebate = new AtomicDouble(0);
             AtomicBoolean diffAddedFlag = new AtomicBoolean(true);
             double diffAmount = 0;
             if(Utility.isEnvPrefixAvailable(org, "SKDCL")){
            	 for (BillPresentAndCalculationDto dto : entry.getValue()) {
                 	final String taxCode = CommonMasterUtility.getHierarchicalLookUp(dto.getTaxCategoryId(), org)
                             .getLookUpCode();
                     if (taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE)
                             || taxCode.equals(PrefixConstants.TAX_CATEGORY.EXEMPTION)) {
                     	totalRebate.addAndGet(dto.getChargeAmount());
                     }
                 }
             double roundedRebate = Math.ceil(totalRebate.get());
             diffAmount = roundedRebate - totalRebate.get();
             }
             
            for (BillPresentAndCalculationDto dto : entry.getValue()) {
                final String taxCode = CommonMasterUtility.getHierarchicalLookUp(dto.getTaxCategoryId(), org)
                        .getLookUpCode();
                if (taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE)
                        || taxCode.equals(PrefixConstants.TAX_CATEGORY.EXEMPTION)) {

                    reachLevel = false;
                    billMasTempList.clear();
                    for (TbBillMas billMas : billMasList) {// each year rebate should knock off from each year only
                        if (reachLevel) {
                            billMasTempList.add(billMas);
                        }
                        if (Utility.comapreDates(entry.getKey(), billMas.getBmFromdt())) {
                            billMasTempList.add(billMas);
                            billMas.setDemandRebate(dto.getChargeAmount());
                            reachLevel = true;
                        }
                    }
                    double balAmt = billMasTempList.get(0).getBmTotalBalAmount() + billMasTempList.get(0).getTotalPenalty();
                    LookUp taxType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            Long.valueOf(dto.getTaxMethod()), org);
                    BillDisplayDto taxDto = taxWiseRebate.get(dto.getTaxId());
                    if (balAmt > 0) {
                        // if in case of rebate or exemption if parent tax is null then the percentage on complete demand
                        if (taxType.getLookUpCode().equals(PrefixConstants.TAX_TYPE.PERCENTAGE) && dto.getParentCode() == null) {
                            dto.setChargeAmount(
                                    Math.round(((billMasTempList.get(0).getBmTotalBalAmount()
                                            + billMasTempList.get(0).getTotalPenalty())
                                            * dto.getChargeAmount()) / 100));
                        }
                        final Map<Long, Double> details = new HashMap<>(0);
                        final Map<Long, Long> billDetails = new HashMap<>(0);
                        
                        if(Utility.isEnvPrefixAvailable(org, "EKT") && dto.getParentCode() != null) {
                        	 billMasTempList.forEach(mas ->{
                             	AtomicLong count = new AtomicLong(1);
                             	mas.getTbWtBillDet().forEach(det ->{
                             		if(det.getTaxId().equals(dto.getParentCode())) {
                             			det.setCollSeq(1L);
                             		}else {
                             			count.addAndGet(1);
                             			det.setCollSeq(count.longValue());
                             		}
                             		
                             	});
                             });
                             billMasTempList.forEach(billMas -> {
             					billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
             				});
                        }
                        if(Utility.isEnvPrefixAvailable(org, "SKDCL") && diffAddedFlag.get()) {
                        	dto.setChargeAmount(dto.getChargeAmount() + diffAmount);
                        	diffAddedFlag.getAndSet(false);
                        }
                        List<BillReceiptPostingDTO> eachRebateList = billMasterCommonService.updateBillData(billMasTempList,
                                dto.getChargeAmount(), details, billDetails, org, null,null);
                        double knowkOffAmt = 0d;
                        if (billMasTempList.get(billMasTempList.size() - 1).getExcessAmount() > 0) {
                            knowkOffAmt = dto.getChargeAmount()
                                    - billMasTempList.get(billMasTempList.size() - 1).getExcessAmount();
                        } else {
                            knowkOffAmt = dto.getChargeAmount();
                        }
                        taxDto.setTaxAmt(taxDto.getTaxAmt() + knowkOffAmt);
                        taxDto.setCurrentYearTaxAmt(BigDecimal.valueOf(taxDto.getTaxAmt()));
                        taxDto.setTotalTaxAmt(BigDecimal.valueOf(taxDto.getTaxAmt()));
                        eachRebateList.forEach(rebate -> {
                            if (rebate.getTaxAmount() > 0) {
                                rebate.setTaxId(dto.getTaxId());
                                rebate.setRmNarration(dto.getChargeDescEng());
                                rebteReciptDtoList.add(rebate);
                            }
                        });
                    } else {
                        taxDto.setTaxAmt(0);
                        taxDto.setCurrentYearTaxAmt(BigDecimal.valueOf(taxDto.getTaxAmt()));
                        taxDto.setTotalTaxAmt(BigDecimal.valueOf(taxDto.getTaxAmt()));
                    }
                }
            }
        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + "knowkOffDemandLevelRebateAndExemption() method");
        return rebteReciptDtoList;
    }

    @Override
    public List<BillReceiptPostingDTO> knowkOffAdvanceAmt(List<TbBillMas> billMasList,
            BillDisplayDto billDisplayDto,
            Organisation org,Date manualReceiptDate,String propNo) {
    	String flatNo = "";
    	 final Map<Long, Double> details = new HashMap<>(0);
         final Map<Long, Long> billDetails = new HashMap<>(0);
    	 Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                 MainetConstants.STATUS.ACTIVE);
    	double amount = billDisplayDto.getCurrentYearTaxAmt().doubleValue();
    	if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
    		propertyService.createBillDetWhereFirstBillHaveArrearAmount(billMasList);
    	}
    	final TbBillMas last = billMasList.get(billMasList.size() - 1);
        List<BillReceiptPostingDTO> rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
        List<BillDisplayDto> rebateDtoList = null;
		if (manualReceiptDate != null) {
			double receiptAmount = 0.0;
			String paymentMethodType = null;
			Long payFinYearId = iFinancialYearService.getFinanceYearId(manualReceiptDate);
			FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
					.getBean(IFinancialYearService.class).getFinincialYearsById(payFinYearId, org.getOrgid());
			if(Utility.isEnvPrefixAvailable(org, "RBT")) {
				paymentMethodType = billMasterCommonService.getFullPaymentOrHalfPayRebate(billMasList,org);
				last.setBmToatlRebate(0.0);
			}
            rebateDtoList = ApplicationContextProvider.getApplicationContext().getBean(PropertyBRMSService.class).fetchEarlyPayRebateRate(billMasList, org, deptId,
            		manualReceiptDate,paymentMethodType);
            double rebateAmount = 0.0;
            for (BillDisplayDto billDisplay : rebateDtoList) {
                rebateAmount = rebateAmount + billDisplay.getTotalTaxAmt().doubleValue();
            }

            if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                double roundedRebateAmt = Math.round(rebateAmount);
                if(last.getBmToatlRebate() > 0) {
                	roundedRebateAmt = last.getBmToatlRebate();
                }
                double bmTotalOutStandingAmt = 0.0;
                if (Utility.isEnvPrefixAvailable(org, "RBT")) {
					bmTotalOutStandingAmt = billMasterCommonService.getFullPayableOutstanding(billMasList, org,null);
				}else {
					bmTotalOutStandingAmt = last.getBmTotalOutstanding();
				}
                if ((amount >= (bmTotalOutStandingAmt - roundedRebateAmt))) {
                    Long iterator = 1l;
                    double checkAmt = 0d;
                    for (BillDisplayDto rebateDto : rebateDtoList) {
                        BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
                        // details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

                        billDetails.put(rebateDto.getTaxId(), null);
                        rebateTax.setBillMasId(last.getBmIdno());
                        double correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
                        double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
                        checkAmt += roundedTax;
                        if (rebateDtoList.size() == iterator) {
                            // if equal than check rebateAmt is equal to checkAmt
                            if (roundedRebateAmt != checkAmt) {
                                double testAmt = roundedRebateAmt - checkAmt;
                                if (testAmt > 0) {
                                    // + testAmt
                                    correctTax += testAmt;
                                } else {
                                    // -testAmt
                                    correctTax += testAmt;
                                }
                            }
                        }
                        iterator++;
                        rebateTax.setTaxAmount(correctTax);
                        details.put(rebateDto.getTaxId(), correctTax);
                        // rebateTax.setTaxAmount(rebateDto.getTotalTaxAmt().doubleValue());
                        rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
                        rebateTax.setTaxId(rebateDto.getTaxId());
                        rebateTax.setYearId(last.getBmYear());
                        last.setBmToatlRebate(Math.round(rebateAmount));
                        if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
                            ArrayList<Double> taxValueIdList = new ArrayList<Double>();
                            // taxValueIdList.add(rebateDto.getTotalTaxAmt().doubleValue());
                            taxValueIdList.add(correctTax);
                            taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
                            last.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
                        }
                        rebateTaxList.add(rebateTax);
                    }

                }else if(Utility.isEnvPrefixAvailable(org, "RBT")){

					Map<String, Object> checkHalfPaymentRebateAppl = propertyService.checkHalfPaymentRebateAppl(billMasList,
							org, deptId, manualReceiptDate, paymentMethodType, amount,receiptAmount);
					Date secondSemStartDate = PropertyBillPaymentService.getSecondSemStartDate(propNo, flatNo, manualReceiptDate, currentFinYearDto, org,deptId);
					if((boolean) checkHalfPaymentRebateAppl.get("halfPayApplicable") && Utility.compareDate(manualReceiptDate,secondSemStartDate)) {
						checkHalfPaymentRebateAppl.get("rebateList");
						@SuppressWarnings("unchecked")
						List<BillDisplayDto> halfRebateDtoList = (List<BillDisplayDto>) checkHalfPaymentRebateAppl.put("rebateList", rebateDtoList);
						double totalRebateAmount = (double) checkHalfPaymentRebateAppl.get("totalRebate");
						roundedRebateAmt = totalRebateAmount;
						Long iterator = 1l;
						double checkAmt = 0d;
						rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
						for (BillDisplayDto rebateDto : halfRebateDtoList) {
							BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
							// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

							billDetails.put(rebateDto.getTaxId(), null);
							rebateTax.setBillMasId(last.getBmIdno());
							double correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
							double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
							checkAmt += roundedTax;
							if (rebateDtoList.size() == iterator) {
								if (roundedRebateAmt != checkAmt) {
									double testAmt = roundedRebateAmt - checkAmt;
									if (testAmt > 0) {
										correctTax += testAmt;
									} else {
										correctTax += testAmt;
									}
								}
							}
							iterator++;
							rebateTax.setTaxAmount(correctTax);
							details.put(rebateDto.getTaxId(), correctTax);
							rebateTax.setTaxCategory(rebateDto.getTaxCategoryId());
							rebateTax.setTaxId(rebateDto.getTaxId());
							rebateTax.setYearId(last.getBmYear());
							last.setBmToatlRebate(Math.round(totalRebateAmount));
							if (StringUtils.isNotBlank(rebateDto.getParentTaxCode())) {
								ArrayList<Double> taxValueIdList = new ArrayList<Double>();
								taxValueIdList.add(correctTax);
								taxValueIdList.add(Double.valueOf(rebateDto.getTaxId()));
								last.getTaxWiseRebate().put(rebateDto.getParentTaxCode(), taxValueIdList);
							}
							rebateTaxList.add(rebateTax);
						}
					}
				
                }
                
                else {
                	last.setBmToatlRebate(0.0);
                }
            }

        }
		
		billMasList.forEach(billMas -> {
			billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
		});
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		List<BillReceiptPostingDTO> eachRebateList = new ArrayList<>();
		if(propertyService.checkBifurcationMethodApplicable(org)) {
			eachRebateList = billMasterCommonService.updateBifurcationMethodBillData(billMasList,
	                billDisplayDto.getCurrentYearTaxAmt().doubleValue(), details, billDetails, org, rebateDetails,manualReceiptDate);
		}else {
			eachRebateList = billMasterCommonService.updateBillData(billMasList,
	                billDisplayDto.getCurrentYearTaxAmt().doubleValue(), details, billDetails, org, rebateDetails,manualReceiptDate);
		}
		
		if(Utility.isEnvPrefixAvailable(org, "PSCL")) {
			propertyService.reArrangeTheDataWhereFirstBillHaveArrearAmount(billMasList);
		}
                    
        if (CollectionUtils.isNotEmpty(rebateTaxList)) {
            for (BillReceiptPostingDTO rebateTax : rebateTaxList) {
                rebateTax.setRebateDetails(rebateDetails);
                eachRebateList.add(rebateTax);
            }

        }
        if (last.getExcessAmount() > 0) {// Excess Payment
			final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(org.getOrgid(), null, deptId);
			details.put(advanceTaxId, last.getExcessAmount());// to add advance amt into receipt det
			propertyService.setAdnavcePayDetail(eachRebateList, org, last.getExcessAmount(), advanceTaxId);
		}
        return eachRebateList;
    }

    /*
     * public List<BillReceiptPostingDTO> knowkOffDemandLevelRebate1(List<TbBillMas> billMasList, Map<Long, BillDisplayDto>
     * taxWiseRebate, Organisation org) { if (!taxWiseRebate.isEmpty()) { List<BillReceiptPostingDTO> rebteReciptDtoList = new
     * ArrayList<>(); final Map<Long, Double> details = new HashMap<>(0); final Map<Long, Long> billDetails = new HashMap<>(0);
     * taxWiseRebate.entrySet().forEach(entry -> { List<BillReceiptPostingDTO> eachRebateList =
     * billMasterCommonService.updateBillData(billMasList, entry.getValue().getTotalTaxAmt().doubleValue(), details, billDetails,
     * org, null); eachRebateList.forEach(rebate -> { rebate.setTaxId(entry.getValue().getTaxId());
     * rebate.setRmNarration(entry.getValue().getTaxDesc()); rebteReciptDtoList.add(rebate); }); }); return rebteReciptDtoList; }
     */

    @Override
    public void mappingDummyKeyToActualKey(List<ProvisionalBillMasEntity> billMasList,
            List<BillReceiptPostingDTO> rebateRecDto) {
        rebateRecDto.forEach(rebate -> {
            billMasList.parallelStream()
                    .filter(billMas -> (rebate.getBillMasId() != null) && (rebate.getBillMasId() == billMas.getDummyMasId()))// enter in loop if tax cat is
                    .forEach(billMas -> {
                        rebate.setBillMasId(billMas.getBmIdno());

                        billMas.getProvisionalBillDetEntityList().parallelStream()
                                .filter(billDet -> rebate.getBillDetId() == billDet.getDummyDetId())// enter in loop if tax cat is
                                .forEach(billDet -> {
                                    rebate.setBillDetId(billDet.getBdBilldetid());

                                });
                    });
        });

    }

    @Override
    public List<CFCAttachment> preparePreviewOfFileUpload(final List<CFCAttachment> downloadDocs, List<DocumentDetailsVO> docs) {
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            long count = 1;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        CFCAttachment c = new CFCAttachment();
                        String path = StringUtility.staticStringBeforeChar(
                                MainetConstants.FILE_PATH_SEPARATOR, file.getPath());
                        c.setAttPath(path);
                        c.setAttFname(file.getName());
                        c.setClmSrNo(count);
                        docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1)).forEach(doc -> {
                            c.setClmDescEngl(doc.getDoc_DESC_ENGL());
                            c.setClmId(doc.getDocumentId());
                            c.setClmDesc(doc.getDoc_DESC_Mar());
                            c.setClmDescEngl(doc.getDoc_DESC_ENGL());

                        });
                        count++;
                        downloadDocs.add(c);
                    } catch (final Exception e) {
                        LOGGER.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }

        return downloadDocs;
    }

    @Override
    public List<AttachDocs> preparePreviewOfFileUpload(List<DocumentDetailsVO> docs) {
        final List<AttachDocs> downloadDocs = new ArrayList<>();

        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        AttachDocs c = new AttachDocs();
                        c.setAttPath(file.getPath());
                        c.setAttFname(file.getName());
                        // c.setClmSrNo(count);
                        docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey())).forEach(doc -> {
                            c.setDmsDocName(doc.getDoc_DESC_ENGL());
                            // c.setClmId(doc.getDocumentId());
                            // c.setClmDesc(doc.getDoc_DESC_Mar());
                            // c.setClmDescEngl(doc.getDoc_DESC_ENGL());

                        });
                        downloadDocs.add(c);
                    } catch (final Exception e) {
                        LOGGER.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }

        return downloadDocs;
    }

    @Override
    public boolean CheckForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId) {
        boolean result = iProvisionalAssesmentMstService.CheckForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status,
                finYearId, serviceId);
        if (!result) {
            return assesmentMastService.checkForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status, finYearId,
                    serviceId);
        }
        return result;
    }

    @WebMethod(exclude = true)
    public List<LookUp> findDistrictByLandType(String landType) {
        List<LookUp> districtList = new ArrayList<>();
        List<Object[]> district = tbAsTryRepository.findDistrictByLandType(landType);

        for (final Object[] listObj : district) {
            LookUp lookup = new LookUp();
            if (!listObj.equals(null)) {
                if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null")) {
                    lookup.setDescLangFirst(listObj[0].toString());
                    lookup.setDescLangSecond(listObj[0].toString());
                    lookup.setLookUpId(listObj[1] == null ? null : Long.valueOf(listObj[1].toString()));
                }
            }
            districtList.add(lookup);
        }
        return districtList;
    }

    @Override
    @WebMethod(exclude = true)
    public List<LookUp> getTehsilListByDistrict(String districtId, String landType) {
        List<LookUp> tehsilList = new ArrayList<>();
        List<Object[]> tehsil = tbAsTryRepository.findTehsilListByDistrict(districtId, landType);
        for (final Object[] listObj : tehsil) {
            LookUp lookup = new LookUp();
            if (!listObj.equals(null)) {
                if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null")) {
                    lookup.setDescLangFirst(listObj[0].toString());
                    lookup.setDescLangSecond(listObj[0].toString());
                    lookup.setLookUpCode(listObj[1].toString());
                }
            }
            tehsilList.add(lookup);
        }

        return tehsilList;

    }

    @Override
    @WebMethod(exclude = true)
    public List<LookUp> getVillageListByTehsil(String tehsilId, String districtId, String landType) {
        List<LookUp> villageList = new ArrayList<>();
        List<Object[]> village = tbAsTryRepository.findVillageListByTehsil(tehsilId, districtId, landType);
        for (final Object[] listObj : village) {
            LookUp lookup = new LookUp();
            if (!listObj.equals(null)) {
                if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null")) {
                    lookup.setDescLangFirst(listObj[0].toString());
                    lookup.setDescLangSecond(listObj[0].toString());
                    lookup.setLookUpCode(listObj[1].toString());
                    lookup.setOtherField(listObj[2].toString());
                }

            }
            villageList.add(lookup);
        }
        return villageList;
    }

    @Override
    public List<LookUp> getMohallaListByVillageId(String villageId, String tehsilId, String districtId, String landType) {
        List<LookUp> mohallaList = new ArrayList<>();
        List<Object[]> mohalla = tbAsTryRepository.findMohallaListByVillageId(villageId, tehsilId, districtId, landType);
        for (final Object[] listObj : mohalla) {
            LookUp lookup = new LookUp();
            if (!listObj.equals(null)) {
                if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null") && listObj[0] != null
                        && listObj[1] != null) {
                    lookup.setDescLangFirst(listObj[0].toString());
                    lookup.setDescLangSecond(listObj[0].toString());
                    lookup.setLookUpCode(listObj[1].toString());

                }

            }
            mohallaList.add(lookup);
        }
        return mohallaList;

    }

    @Override
    public List<LookUp> getStreetListByMohallaId(String mohallaId, String villageId, String tehsilId, String districtId,
            String landType) {
        List<LookUp> streetList = new ArrayList<>();
        List<Object[]> street = tbAsTryRepository.findStreetListByMohallaId(mohallaId, villageId, tehsilId, districtId, landType);
        for (final Object[] listObj : street) {
            LookUp lookup = new LookUp();
            if (!listObj.equals(null)) {
                if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null") && listObj[0] != null
                        && listObj[1] != null) {
                    lookup.setDescLangFirst(listObj[0].toString());
                    lookup.setDescLangSecond(listObj[0].toString());
                    lookup.setLookUpCode(listObj[1].toString());
                }
            }
            streetList.add(lookup);
        }
        return streetList;

    }

    @Override
    public String getRiDetailsByDistrictTehsilVillageID(String landType, String districtId, String tehsilId, String villageId) {
        return tbAsTryRepository.findRiDetailsByDistrictTehsilVillageID(landType, districtId, tehsilId, villageId);
    }

    @Override
    public String getRecordDetailsByDistrictTehsilVillageMohallaStreetID(String districtId, String tehsilId,
            String villageId, String mohallaId, String streetNo) {
        return tbAsTryRepository.findRecordDetailsByDistrictTehsilVillageMohallaStreetID(districtId, tehsilId, villageId,
                mohallaId, streetNo);

    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyCommonInfoDto> searchPropertyDetails(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, List<LookUp> location) {
        List<Object[]> entity = iProvisionalAssessmentMstDao.searchPropetyForView(searchDto, pagingDTO,
                gridSearchDTO, serviceId);
        List<PropertyCommonInfoDto> resultList = new ArrayList<>();
        Organisation org = new Organisation();
        org.setOrgid(searchDto.getOrgId());
        if (entity != null && !entity.isEmpty()) {
            for (Object[] assMst : entity) {
                PropertyCommonInfoDto mst = new PropertyCommonInfoDto();
                mst.setProertyNo(assMst[0].toString());
                if (assMst[1] != null) {
                    mst.setOldPid(assMst[1].toString());
                }
                if (assMst[2] != null) {
                    mst.setOwnerName(assMst[2].toString());
                }
                if (assMst[6] != null) {
                    mst.setAssWard1(
                            CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(assMst[6].toString()), org)
                                    .getDescLangFirst());
                }
                if (assMst[7] != null) {
                    mst.setAssWard2(
                            CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(assMst[7].toString()), org)
                                    .getDescLangFirst());
                }
                /*
                 * if (assMst[6] != null) { mst.setAssWard3(
                 * CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(assMst[6].toString()), org) .getDescLangFirst()); } if
                 * (assMst[7] != null) { mst.setAssWard4(
                 * CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(assMst[7].toString()), org) .getDescLangFirst()); } if
                 * (assMst[8] != null) { mst.setAssWard5(
                 * CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(assMst[8].toString()), org) .getDescLangFirst()); }
                 */

                if (assMst[8] != null && location != null && !location.isEmpty()) {
                    location.stream().filter(loc -> loc.getLookUpId() == Long.valueOf(assMst[8].toString())).findFirst()
                            .ifPresent(loc -> mst.setLoction(loc.getDescLangFirst()));
                }

                resultList.add(mst);
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public BillDisplayDto getAjustedAdvanceAmt(Organisation org, Long applicationNo, Long deptId) {
        BillDisplayDto displayDto = null;

        BigDecimal totalAdvAmt = iReceiptEntryService.getAjustedAdvancedAmountByAppNo(org.getOrgid(), applicationNo, deptId);

        if (totalAdvAmt != null && totalAdvAmt.doubleValue() > 0) {
            LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(PrefixConstants.TAX_SUBCATEGORY.ADVANCE_PAYMENT,
                    PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
            final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                    org);

            List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
                    taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());

            if (taxList != null) {
                TbTaxMasEntity tax = taxList.get(0);
                displayDto = new BillDisplayDto();
                displayDto.setTaxId(tax.getTaxId());
                displayDto.setTaxDesc(tax.getTaxDesc());
                displayDto.setDisplaySeq(tax.getTaxDisplaySeq());
                displayDto.setTaxCategoryId(tax.getTaxCategory1());
                displayDto.setCurrentYearTaxAmt(totalAdvAmt);
                displayDto.setTotalTaxAmt(totalAdvAmt);
            }
        }
        return displayDto;
    }

    @Transactional
    @Override
    public List<Long> saveAndUpdateDraftAssessmentAfterEdit(SelfAssessmentSaveDTO saveDto, Organisation org) {
        List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
        List<BillReceiptPostingDTO> billRecePstingDto = null;
        final TbCfcApplicationMstEntity cfcEntity = cfcService.getCFCApplicationByApplicationId(
                saveDto.getProvisionalMas().getApmApplicationId(),
                saveDto.getProvisionalMas().getOrgId());
        cfcEntity.setApmMode(MainetConstants.FlagS);
        cfcEntity.setUpdatedBy(saveDto.getEmpId());
        cfcEntity.setUpdatedDate(new Date());
        cfcEntity.setLgIpMacUpd(saveDto.getProvisionalMas().getLgIpMac());
        applicationService.updateApplication(cfcEntity);
        final List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(saveDto.getProvisionalMas().getApmApplicationId(),
                        saveDto.getProvisionalMas().getOrgId());
        Map<Long, Long> assIdMap = null;
        assIdMap = iProvisionalAssesmentMstService.updateProvisionalAssessment(saveDto.getProvisionalMas(), provAssDtoList,
                saveDto.getProvisionalMas().getOrgId(), saveDto.getEmpId(), MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG,
                saveDto.getFinYearList(), provAssEntList);
        List<Long> bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(saveDto.getBillMasList(),
                saveDto.getProvisionalMas().getOrgId(), saveDto.getEmpId(), saveDto.getProvisionalMas().getAssNo(),
                assIdMap, null, saveDto.getProvisionalMas().getLgIpMac());

        List<BillReceiptPostingDTO> demandreabate = new ArrayList<>(0);
        if (saveDto.getDemandLevelRebateList() != null && !saveDto.getDemandLevelRebateList().isEmpty()) {
            saveDto.getDemandLevelRebateList().forEach(rebate -> {
                BillReceiptPostingDTO dmdRebate = new BillReceiptPostingDTO();
                BeanUtils.copyProperties(rebate, dmdRebate);
                demandreabate.add(dmdRebate);
            });
        }
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
        BillDisplayDto advanceAmt = saveDto.getAdvanceAmt();
        double ajustedAmt = 0;
        if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
            billRecePstingDto = knowkOffAdvanceAmt(saveDto.getBillMasList(), advanceAmt, org,null,null);
            if (saveDto.getBillMasList().get(saveDto.getBillMasList().size() - 1).getExcessAmount() > 0) {
                ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue()
                        - saveDto.getBillMasList().get(saveDto.getBillMasList().size() - 1).getExcessAmount();
            } else {
                ajustedAmt = advanceAmt.getCurrentYearTaxAmt().doubleValue();
            }
        }
        saveDemandLevelRebate(saveDto.getProvisionalMas(), saveDto.getEmpId(), saveDto.getDeptId(), saveDto.getBillMasList(),
                demandreabate, org, provBillList);
        saveAdvanceAmt(saveDto.getProvisionalMas(), saveDto.getEmpId(), saveDto.getDeptId(), advanceAmt, org,
                saveDto.getBillMasList(), saveDto.getProvisionalMas().getLgIpMac(),
                provBillList, billRecePstingDto, ajustedAmt,null,0);
        if (saveDto.getOffline().getOnlineOfflineCheck() == null
                || MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER.equals(saveDto.getOffline().getOnlineOfflineCheck())
                || MainetConstants.PAYMENT_TYPE.ONLINE.equals(saveDto.getOffline().getOnlineOfflineCheck())) {
            workflowRequestService.initiateAndUpdateWorkFlowProcess(saveDto.getOffline(), null);
        }
        return bmIds;

    }

    @Override
    public boolean CheckForAssesmentFiledForCurrYear(Long orgId, String assNo, String assOldpropno, String status,
            Long finYearId) {
        boolean result = iProvisionalAssesmentMstService.CheckForAssesmentFiledForCurrYear(orgId, assNo, assOldpropno, status,
                finYearId);
        if (!result) {
            return assesmentMastService.checkForAssesmentFieldForCurrYear(orgId, assNo, assOldpropno, status, finYearId);
        }
        return result;
    }

    @Override
    public Long findTaxDescIdByDeptIdAndOrgId(Long orgId, Long deptId, Long taxDescId) {
        return selfAssessmentJpaRepository.findTaxDescIdByDeptIdAndOrgId(orgId, deptId, taxDescId);
    }
    
	// 100510 - Unique property ID for ASCL
    @Override
    public String uniquePropertyId(ProvisionalAssesmentMstDto dto, Organisation org) {
		StringBuilder uniquePropertyId = new StringBuilder();
		uniquePropertyId.append(MainetConstants.Property.ASCL_STATE_CODE + MainetConstants.Property.ASCL_ULB_CODE);
		String zone = "";
		String ward = "";
		if (dto.getAssWard1() != null && dto.getAssWard2() != null) {
			zone = CommonMasterUtility.getHierarchicalLookUp(dto.getAssWard1(), org).getLookUpCode();
			StringBuilder zoneBuilder = new StringBuilder();
			for (int i = 0; i < zone.length(); i++) {
				char ch = zone.charAt(i);
				if (Character.isDigit(ch)) {
					zoneBuilder.append(ch);
				}
			}
			if (StringUtils.isNotBlank(zoneBuilder.toString())) {
				String format = "%0" + MainetConstants.NUMBERS.TWO + "d";
				String zoneCode = String.format(format, Integer.parseInt(zoneBuilder.toString()));
				uniquePropertyId.append(zoneCode);
			}

			ward = CommonMasterUtility.getHierarchicalLookUp(dto.getAssWard2(), org).getLookUpCode();
			StringBuilder wardBuilder = new StringBuilder();
			for (int i = 0; i < ward.length(); i++) {
				char ch = ward.charAt(i);
				if (Character.isDigit(ch)) {
					wardBuilder.append(ch);
				}
			}
			if (StringUtils.isNotBlank(wardBuilder.toString())) {
				String format = "%0" + MainetConstants.NUMBERS.THREE + "d";
				String wardCode = String.format(format, Integer.parseInt(wardBuilder.toString()));
				uniquePropertyId.append(wardCode);
			}
		} else {
			throw new FrameworkException("Exception occurred due to absence of zone ward");
		}
		StringBuilder lastnumber = new StringBuilder();
		for (int i = 0; i < MainetConstants.NUMBERS.SIX; i++) {
			lastnumber.append("9");
		}
		Long lastNum = Long.parseLong(lastnumber.toString());
		String deptId = String.valueOf(departmentService
				.findDeptByCode(org.getOrgid(), MainetConstants.FlagA, MainetConstants.DEPT_SHORT_NAME.PROPERTY)
				.getDpDeptid());
		Long sequence = seqGenFunctionUtility.getNumericSeqNo(String.valueOf(MainetConstants.NUMBERS.ZERO),
				MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST, MainetConstants.Property.UNIQUE_PROPERTY_ID,
				org.getOrgid(), null, deptId, 1L, lastNum);
		String format = "%0" + MainetConstants.NUMBERS.SIX + "d";
		String sequenceNo = String.format(format, sequence.intValue());
		uniquePropertyId.append(String.valueOf(sequenceNo));
		
		List<String> usageType = new ArrayList<>();
		dto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
			usageType.add(CommonMasterUtility.getHierarchicalLookUp(detail.getAssdUsagetype1(), org).getLookUpCode());
		});
		if (usageType.contains(MainetConstants.FlagR) && usageType.contains(MainetConstants.FlagN)
				&& usageType.contains(MainetConstants.OPL)) {
			uniquePropertyId.append(MainetConstants.FlagM);
		} else if (usageType.contains(MainetConstants.FlagR) && usageType.contains(MainetConstants.OPL)) {
			uniquePropertyId.append(MainetConstants.FlagR);
		} else if (usageType.contains(MainetConstants.FlagN) && usageType.contains(MainetConstants.OPL)) {
			uniquePropertyId.append(MainetConstants.FlagN);
		} else if (usageType.contains(MainetConstants.FlagR) && usageType.contains(MainetConstants.FlagN)) {
			uniquePropertyId.append(MainetConstants.FlagM);
		} else if (usageType.contains(MainetConstants.FlagR) || usageType.contains(MainetConstants.OPL)) {
			uniquePropertyId.append(MainetConstants.FlagR);
		} else if (usageType.contains(MainetConstants.FlagN)) {
			uniquePropertyId.append(MainetConstants.FlagN);
		}		
		
		return uniquePropertyId.toString();
	}
    

	@Override
	@Transactional
	public void saveChangedBillingMethod(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long applicationNo,
			Long empId, String ipAddress, Long orgId) {
		final ProvisionalAssesmentMstEntity provAssetMst = new ProvisionalAssesmentMstEntity();
		provisionalAssesmentMstDto.setApmApplicationId(applicationNo);
		provisionalAssesmentMstDto.setCreatedBy(empId);
		provisionalAssesmentMstDto.setLgIpMac(ipAddress);
		provisionalAssesmentMstDto.setCreatedDate(new Date());
		BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);
		// Assessment Detail
		final List<ProvisionalAssesmentDetailEntity> provAsseDetList = new ArrayList<>();
		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {

			ProvisionalAssesmentDetailEntity provAsseDet = new ProvisionalAssesmentDetailEntity();
			BeanUtils.copyProperties(provDet, provAsseDet);
			provAsseDet.setTbAsAssesmentMast(provAssetMst);
			provAsseDet.setOrgId(orgId);
			provAsseDet.setCreatedBy(empId);
			provAsseDet.setLgIpMac(ipAddress);
			provAsseDet.setCreatedDate(new Date());
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
				proAssessmentRoomDetailEntity.add(roomEntity);
			});
			provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);

			provAsseDetList.add(provAsseDet);
		});
		provAssetMst.setProvisionalAssesmentDetailList(provAsseDetList);

		final List<ProvisionalAssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
		provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
			ProvisionalAssesmentOwnerDtlEntity provAsseOwnerEnt = new ProvisionalAssesmentOwnerDtlEntity();
			BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
			provAsseOwnerEnt.setTbAsAssesmentOwnerMast(provAssetMst);
			provAsseOwnerEnt.setOrgId(orgId);
			provAsseOwnerEnt.setCreatedBy(empId);
			provAsseOwnerEnt.setLgIpMac(ipAddress);
			provAsseOwnerEnt.setCreatedDate(new Date());
			provAsseOwnerEnt.setApmApplicationId(applicationNo);
			provAsseOwnerEnt.setSmServiceId(provisionalAssesmentMstDto.getSmServiceId()); 
			provAsseOwnEntList.add(provAsseOwnerEnt);
		});
		provAssetMst.setProvisionalAssesmentOwnerDtlList(provAsseOwnEntList);
		provisionalAssesmentMstRepository.save(provAssetMst);
	}
	
	@Override
	@Transactional
	public void saveBillMethodChangeAuthorization(ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
			Employee employee, Long orgId) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveBillMethodChangeAuthorization() method");		
		List<ProvisionalAssesmentMstDto> propList = assesmentMastService.getPropDetailFromAssByPropNo(orgId,
				provisionalAssesmentMstDto.getAssNo());
		ProvisionalAssesmentMstDto propDto = propList.get(propList.size()-1);
		PropertyMastEntity propertyMaster = primaryPropertyService
				.getPropertyDetailsByPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
		if (propDto != null) {
			saveInAssessmentMaster(provisionalAssesmentMstDto, employee, orgId);
			// To maintain History of previous billing method
			maintainHistory(propDto, employee);
			// Delete previous entry from master
			assesmentMstRepository.delete(propDto.getProAssId());
		}
		if (propertyMaster != null) {
			saveInPropertyMaster(provisionalAssesmentMstDto, employee, orgId);
			propertyMastRepository.delete(propertyMaster.getPmPropid());
		}
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " saveBillMethodChangeAuthorization() method");
	}

	@Transactional
	private void saveInPropertyMaster(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Employee employee,
			Long orgId) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveInPropertyMaster() method");
		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
			PropertyMastEntity provAssetMst = new PropertyMastEntity();
			Long applicationNo = provisionalAssesmentMstDto.getApmApplicationId();
			provisionalAssesmentMstDto.setFlatNo(provDet.getFlatNo());
			if (StringUtils.isNotBlank(provDet.getFlatNo()))
				provisionalAssesmentMstDto.setLogicalPropNo(provisionalAssesmentMstDto.getAssNo()
						+ MainetConstants.operator.UNDER_SCORE + provDet.getFlatNo());
			provisionalAssesmentMstDto.setFaYearId(provDet.getFaYearId());
			BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);
			provAssetMst.setUpdatedBy(employee.getEmpId());
			provAssetMst.setLgIpMacUpd(employee.getLgIpMac());
			provAssetMst.setUpdatedDate(new Date());

			List<PropertyDetEntity> provAsseDetList = new ArrayList<>();
			PropertyDetEntity provAsseDet = new PropertyDetEntity();
			provDet.setApmApplicationId(applicationNo);
			BeanUtils.copyProperties(provDet, provAsseDet);
			provAsseDet.setTbAsPrimaryMast(provAssetMst);
			provAsseDet.setUpdatedBy(employee.getEmpId());
			provAsseDet.setLgIpMacUpd(employee.getLgIpMac());
			provAsseDet.setUpdatedDate(new Date());

			final List<PropertyFactorEntity> provAsseFactList = new ArrayList<>();
			provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
				PropertyFactorEntity provAssFact = new PropertyFactorEntity();
				BeanUtils.copyProperties(provfact, provAssFact);
				provAssFact.setTbAsPrimaryDet(provAsseDet);
				provAssFact.setTbAsPrimaryMast(provAssetMst);
				provAssFact.setUpdatedBy(employee.getEmpId());
				provAssFact.setUpdatedDate(new Date());
				provAssFact.setLgIpMacUpd(employee.getLgIpMac());
				provAsseFactList.add(provAssFact);
			});
			provAsseDet.setPropfactorDtlList(provAsseFactList);

			final List<PropertyRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
			provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
				PropertyRoomDetailEntity roomEntity = new PropertyRoomDetailEntity();
				BeanUtils.copyProperties(roomDetails, roomEntity);
				roomEntity.setPropDetEntity(provAsseDet);
				proAssessmentRoomDetailEntity.add(roomEntity);
			});
			provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);
			provAsseDetList.add(provAsseDet);
			provAssetMst.setProDetList(provAsseDetList);
			propertyMastRepository.save(provAssetMst);
		});
		LOGGER.info("End --> " + this.getClass().getSimpleName() + " saveInPropertyMaster() method");
	}

	@Transactional
	private void saveInAssessmentMaster(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Employee employee,
			Long orgId) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveInAssessmentMaster() method");
		provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(provDet -> {
			AssesmentMastEntity provAssetMst = new AssesmentMastEntity();
			Long applicationNo = provisionalAssesmentMstDto.getApmApplicationId();
			provisionalAssesmentMstDto.setFlatNo(provDet.getFlatNo());
			if (StringUtils.isNotBlank(provDet.getFlatNo()))
				provisionalAssesmentMstDto.setLogicalPropNo(provisionalAssesmentMstDto.getAssNo()
						+ MainetConstants.operator.UNDER_SCORE + provDet.getFlatNo());
			provisionalAssesmentMstDto.setFaYearId(provDet.getFaYearId());
			BeanUtils.copyProperties(provisionalAssesmentMstDto, provAssetMst);
			provAssetMst.setUpdatedBy(employee.getEmpId());
			provAssetMst.setLgIpMacUpd(employee.getLgIpMac());
			provAssetMst.setUpdatedDate(new Date());

			final long proAssId = seqGenFunctionUtility.generateJavaSequenceNo(
					MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_MAST,
					MainetConstants.Property.primColumn.PRO_ASS_ID, null, null);
			provAssetMst.setProAssId(proAssId);

			List<AssesmentDetailEntity> provAsseDetList = new ArrayList<>();
			AssesmentDetailEntity provAsseDet = new AssesmentDetailEntity();
			provDet.setApmApplicationId(applicationNo);
			BeanUtils.copyProperties(provDet, provAsseDet);
			provAsseDet.setMnAssId(provAssetMst);
			provAsseDet.setUpdatedBy(employee.getEmpId());
			provAsseDet.setLgIpMacUpd(employee.getLgIpMac());
			provAsseDet.setUpdatedDate(new Date());

			final long proAssdId = seqGenFunctionUtility.generateJavaSequenceNo(
					MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_DETAIL,
					MainetConstants.Property.primColumn.PRO_ASSD_ID, null, null);
			provAsseDet.setProAssdId(proAssdId);

			final List<AssesmentFactorDetailEntity> provAsseFactList = new ArrayList<>();
			provDet.getProvisionalAssesmentFactorDtlDtoList().forEach(provfact -> {
				AssesmentFactorDetailEntity provAssFact = new AssesmentFactorDetailEntity();
				BeanUtils.copyProperties(provfact, provAssFact);

				final long proAssfId = seqGenFunctionUtility.generateJavaSequenceNo(
						MainetConstants.Property.PROP_DEPT_SHORT_CODE,
						MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_FACTOR_DTL,
						MainetConstants.Property.primColumn.PRO_ASSF_ID, null, null);
				provAssFact.setProAssfId(proAssfId);

				provAssFact.setMnAssdId(provAsseDet);
				provAssFact.setMnAssId(provAssetMst);
				provAssFact.setUpdatedBy(employee.getEmpId());
				provAssFact.setUpdatedDate(new Date());
				provAssFact.setLgIpMacUpd(employee.getLgIpMac());
				provAsseFactList.add(provAssFact);
			});
			provAsseDet.setAssesmentFactorDetailEntityList(provAsseFactList);

			final List<AssessmentRoomDetailEntity> proAssessmentRoomDetailEntity = new ArrayList<>();
			provDet.getRoomDetailsDtoList().forEach(roomDetails -> {
				AssessmentRoomDetailEntity roomEntity = new AssessmentRoomDetailEntity();
				BeanUtils.copyProperties(roomDetails, roomEntity);
				roomEntity.setAssPropRoomDet(provAsseDet);
				proAssessmentRoomDetailEntity.add(roomEntity);
			});
			provAsseDet.setRoomDetailEntityList(proAssessmentRoomDetailEntity);// Room end
			provAsseDetList.add(provAsseDet);
			provAssetMst.setAssesmentDetailEntityList(provAsseDetList);

			final List<AssesmentOwnerDtlEntity> provAsseOwnEntList = new ArrayList<>();
			provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwndto -> {
				AssesmentOwnerDtlEntity provAsseOwnerEnt = new AssesmentOwnerDtlEntity();
				BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
				final long proAssoId = seqGenFunctionUtility.generateJavaSequenceNo(
						MainetConstants.Property.PROP_DEPT_SHORT_CODE,
						MainetConstants.Property.table.TB_AS_PRO_ASSESMENT_OWNER_DTL,
						MainetConstants.Property.primColumn.PRO_ASSO_ID, null, null);
				provAsseOwnerEnt.setProAssoId(proAssoId);
				provAsseOwnerEnt.setMnAssId(provAssetMst);
				provAsseOwnerEnt.setOrgId(orgId);
				provAsseOwnerEnt.setUpdatedBy(employee.getEmpId());
				provAsseOwnerEnt.setLgIpMacUpd(employee.getLgIpMac());
				provAsseOwnerEnt.setUpdatedDate(new Date());
				provAsseOwnEntList.add(provAsseOwnerEnt);
			});
			provAssetMst.setAssesmentOwnerDetailEntityList(provAsseOwnEntList);
			assesmentMstRepository.save(provAssetMst);
		});
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveInAssessmentMaster() method");
	}

	@Transactional
	private void maintainHistory(ProvisionalAssesmentMstDto assesmentMastDto, Employee emp) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " maintainHistory() method");
		AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
		mastHistEntity.setMnAssId(assesmentMastDto.getProAssId());
		mastHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
		assesmentMastDto.setCreatedBy(emp.getUserId());
		assesmentMastDto.setCreatedDate(new Date());
		assesmentMastDto.setLgIpMac(emp.getLgIpMac());
		auditService.createHistory(assesmentMastDto, mastHistEntity);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ProvisionalAssesmentDetailDto detailEntity : assesmentMastDto.getProvisionalAssesmentDetailDtoList()) {
			AssesmentDetailHistEntity detailHisEntity = new AssesmentDetailHistEntity();
			detailHisEntity.setMnAssId(assesmentMastDto.getProAssId());
			detailHisEntity.setProAssMastHisId(mastHistEntity.getMnAssHisId());
			detailHisEntity.setProAssdId(detailEntity.getProAssdId());
			detailHisEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
			detailEntity.setCreatedDate(new Date());
			detailEntity.setCreatedBy(emp.getUserId());
			detailEntity.setLgIpMac(emp.getLgIpMac());
			auditService.createHistory(detailEntity, detailHisEntity);

			detailEntity.getProvisionalAssesmentFactorDtlDtoList().forEach(factor -> {
				AssesmentFactorDetailHistEntity assesmentFactorDetailHistEntity = new AssesmentFactorDetailHistEntity();
				assesmentFactorDetailHistEntity.setMnAssdId(detailEntity.getProAssdId());
				assesmentFactorDetailHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
				assesmentFactorDetailHistEntity.setCreatedDate(new Date());
				assesmentFactorDetailHistEntity.setCreatedBy(emp.getUserId());
				assesmentFactorDetailHistEntity.setLgIpMac(emp.getLgIpMac());
				auditService.createHistory(factor, assesmentFactorDetailHistEntity);
			});

			detailEntity.getRoomDetailsDtoList().forEach(room -> {
				AssessmentRoomDetailHistEntity assessmentRoomDetailHistEntity = new AssessmentRoomDetailHistEntity();
				assessmentRoomDetailHistEntity.setMnAssdId(detailEntity.getProAssdId());
				assessmentRoomDetailHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
				assessmentRoomDetailHistEntity.setCreatedDate(new Date());
				assessmentRoomDetailHistEntity.setCreatedBy(emp.getUserId());
				auditService.createHistory(room, assessmentRoomDetailHistEntity);
			});
		}
		for (ProvisionalAssesmentOwnerDtlDto ownerEntity : assesmentMastDto.getProvisionalAssesmentOwnerDtlDtoList()) {
			AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
			ownerDtlHistEntity.setMnAssId(assesmentMastDto.getProAssId());
			ownerDtlHistEntity.setProAssoId(ownerEntity.getProAssoId());
			ownerDtlHistEntity.setProAssMastHisId(mastHistEntity.getMnAssHisId());
			ownerDtlHistEntity.sethStatus(MainetConstants.Transaction.Mode.UPDATE);
			ownerEntity.setCreatedDate(new Date());
			ownerEntity.setCreatedBy(emp.getUserId());
			ownerEntity.setLgIpMac(emp.getLgIpMac());
			auditService.createHistory(ownerEntity, ownerDtlHistEntity);
		}
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " maintainHistory() method");
	}
	
	@Override
	@Transactional
	public List<String> fetchAssessmentByGroupPropName(Long orgId) {
		List<String> groupPropNameList = assesmentMstRepository.fetchAssessmentByGroupPropNo(orgId);
		List<String> result = groupPropNameList.stream().filter(group -> group!=null)
				.collect(Collectors.toList());
		return result;
	}

	@Override
	@Transactional
	public List<String> fetchAssessmentByParentPropName(Long orgId) {
		List<String> parentPropNameList = assesmentMstRepository.fetchAssessmentByParentPropNo(orgId);
		List<String> result = parentPropNameList.stream().filter(parent -> parent!=null)
				.collect(Collectors.toList());
		return result;
	}
	

    @Override
    public Map<String, List<BillDisplayDto>> getDisplayMapOfTaxesForRevision(List<TbBillMas> billMasList, final Organisation organisation,
            Long deptId, List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge, String interstWaiveOff) {
        if (!billMasList.isEmpty()) {
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplayForRevision(billMas, organisation, deptId,interstWaiveOff);
            if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                billDisDtoList.addAll(rebateDtoList);
            }
            if (surCharge != null) {
                billDisDtoList.add(surCharge);
            }
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {
                taxWiseRebate.entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
                });
            }
            return propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList, organisation, deptId);
        }
        return null;
    }
    
	@Override
	@Transactional
	public void saveBillMethodChangeData(String appId, Long serviceId, String workflowDecision, Long orgId, Long empId,
			String lgIpMac, Long level) {
		List<TbBillMas> completeBillList = new ArrayList<>();
		List<TbBillMas> proBillList;
		Employee emp = new Employee();
		emp.setLgIpMac(lgIpMac);
		emp.setUserId(empId);
		List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
				.getAssesmentMstDtoListByAppId(Long.valueOf(appId), orgId);
		ProvisionalAssesmentMstDto assMst = provAssDtoList.get(provAssDtoList.size() - 1);
		String propNo = assMst.getAssNo();
		Map<String, String> propAndFlatMap = new LinkedHashMap<>();
		assMst.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
			propAndFlatMap.put(detail.getFlatNo(), propNo);
		});
		for (Map.Entry<String, String> map : propAndFlatMap.entrySet()) {
			proBillList = new ArrayList<>();
			proBillList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(
					map.getValue() + MainetConstants.operator.UNDER_SCORE + map.getKey(), orgId);
			if (CollectionUtils.isNotEmpty(proBillList)) {
				proBillList.forEach(proBill -> {
					proBill.setFlatNo(map.getKey());
				});
				completeBillList.addAll(proBillList);
			}
		}

		if (workflowDecision.equals(MainetConstants.WorkFlow.Decision.APPROVED) && level == 2) {
			List<TbBillMas> billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(propNo, orgId);
			// Move data from provisional tables to main table
			saveBillMethodChangeAuthorization(assMst, emp, orgId);
			if (CollectionUtils.isNotEmpty(completeBillList)) {
				propertyMainBillService.saveAndUpdateMainBillWithKeyGen(completeBillList, orgId, empId, propNo, null,
						lgIpMac);
				// Delete provisional table data
				iProvisionalBillService.deleteProvisionalBillsById(completeBillList);
			}
			iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);

			// Delete main bill previous and save in history - need to ask
			propertyMainBillService.copyDataFromMainBillDetailToHistory(billMasList, null,empId,lgIpMac);
			propertyMainBillService.deleteMainBillWithDtoById(billMasList);
		} else if (workflowDecision.equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			// Delete provisional tables
			iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
			iProvisionalBillService.deleteProvisionalBillsById(completeBillList);
		}
	}
	
}