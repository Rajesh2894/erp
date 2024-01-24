package com.abm.mainet.property.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonBillResponseDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailForMPOSDto;
import com.abm.mainet.common.dto.ReversalPaymentForMPOSDto;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.CommonAppResponseDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dao.IEmployeeDAO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.dto.TrasactionReversalDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.ICommonReversalEntry;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentFactorDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.AssessmentRoomDetailHistEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.MainBillDetEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProAssDetailHisEntity;
import com.abm.mainet.property.domain.ProAssFactlHisEntity;
import com.abm.mainet.property.domain.ProAssMstHisEntity;
import com.abm.mainet.property.domain.ProAssOwnerHisEntity;
import com.abm.mainet.property.domain.ProBillDetHisEntity;
import com.abm.mainet.property.domain.ProBillMstHisEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.DemandLevelRebateDTO;
import com.abm.mainet.property.dto.ExcelDownloadDTO;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.PropertyDetailRequestDTO;
import com.abm.mainet.property.dto.PropertyDetailResponseDTO;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;
import com.abm.mainet.property.repository.PropertyMainBillDetRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.ProvisionalAssesmentMstRepository;
import com.abm.mainet.property.repository.ProvisionalBillRepository;
import com.google.common.util.concurrent.AtomicDouble;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.IPropertyTaxSelfAssessmentService")
@Api(value = "/selfAssessment")
@Path("/selfAssessment")
public class PropertyTaxSelfAssessmentServiceImpl implements IPropertyTaxSelfAssessmentService {

    private static final Logger LOGGER = Logger.getLogger(PropertyTaxSelfAssessmentServiceImpl.class);

    @Resource
    private ILocationMasService iLocationMasService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ChangeInAssessmentService changeInAssessmentService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private PropertyBillPaymentService propertyBillPaymentService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private IWorkflowRequestService workflowRequestService;

    @Autowired
    private MainAssessmentOwnerRepository mainAssessmentOwnerRepository;
    
    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;
    
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private PropertyPenltyService propertyPenltyService;
    
    @Autowired
    private  ReceiptRepository receiptRepository;
  
    @Autowired
    private AssesmentMstRepository assesmentMstRepository;
    
    @Autowired
    private ProvisionalAssesmentMstRepository provisionalAssesmentMstRepository;
    
    @Autowired
    private ProvisionalBillRepository provisionalBillRepository;
    
    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;
    
    @Autowired
    private PropertyMainBillDetRepository propertyMainBillDetRepository;
    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
	private IChallanService iChallanService;
    
    @Autowired
    private ICommonReversalEntry commonReversalEntry;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
	ICommonReversalEntry iCommonReversalEntry;
    
	@Autowired
	IFinancialYearService iFinancialYearService;
	
	@Autowired
	ViewPropertyDetailsService viewPropertyDetailsService;

    @Override
    @POST
    @Path("/defaultData")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentDeaultValueDTO setDefaultFeildsOnLoad(@RequestBody SelfAssessmentDeaultValueDTO request)
            throws Exception {
        Organisation org = new Organisation();
        org.setOrgid(request.getOrgId());
        List<LookUp> locList = new ArrayList<>(0);
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(request.getOrgId(),
                request.getDeptId());
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
            request.setLocation(locList);
        }
        request.setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        List<Long> finanYearList = new ArrayList<>();
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            request.getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
            request.getFinancialYearDate().put(finYearTemp.getFaFromDate(), finYearTemp.getFaYear());
            Long finYear = iFinancialYear.getFinanceYearId(finYearTemp.getFaFromDate());
            finanYearList.add(finYear);
        }
        request.setFinYearList(finanYearList);
        request.setLeastFinYear(iFinancialYear.getMinFinanceYear(request.getOrgId()));
        return request;
    }

    @POST
    @Path("/fetchChecklistAndChargesForChangeAndNoChange")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public AssessmentChargeCalDTO fetchChecklistAndChargesForChangeAndNoChange(
            @RequestBody SelfAssessmentDeaultValueDTO assessmentDto) throws Exception {
        AssessmentChargeCalDTO checklistChargeDto = new AssessmentChargeCalDTO();
        ProvisionalAssesmentMstDto dto = assessmentDto.getProvisionalMas();
        List<ProvisionalAssesmentFactorDtlDto> factDto = assessmentDto.getProvAsseFactDtlDto();
        Organisation organisation = new Organisation();
        organisation.setOrgid(dto.getOrgId());

        if (assessmentDto.getProvAsseFactDtlDto() != null) {
            selfAssessmentService.setFactorMappingToAssDto(assessmentDto.getProvAsseFactDtlDto(), dto);
        }
        List<DocumentDetailsVO> docs = selfAssessmentService.fetchCheckList(dto, factDto);
        checklistChargeDto.setCheckList(docs);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto,
                assessmentDto.getDeptId(), taxWiseRebate, assessmentDto.getFinYearList(), null, null, null, null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                organisation, assessmentDto.getDeptId(), null, null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                organisation, taxWiseRebate);
        List<DemandLevelRebateDTO> demandreabate = new ArrayList<>(0);
        if (reabteRecDtoList != null && !reabteRecDtoList.isEmpty()) {
            reabteRecDtoList.forEach(rebate -> {
                DemandLevelRebateDTO dmdRebate = new DemandLevelRebateDTO();
                BeanUtils.copyProperties(rebate, dmdRebate);
                demandreabate.add(dmdRebate);
            });
            assessmentDto.setDemandLevelRebateList(demandreabate);
            checklistChargeDto.setDemandLevelRebateList(demandreabate);
        }
        assessmentDto.setTaxWiseRebate(taxWiseRebate);
        checklistChargeDto.setTaxWiseRebate(taxWiseRebate);
        propertyService.interestCalculationWithoutBRMSCall(organisation, assessmentDto.getDeptId(), billMasList,
                MainetConstants.Property.INT_RECAL_NO);

        List<Long> taxAppList = new ArrayList<>();
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        final LookUp taxAppAtRecp = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                organisation);
        taxAppList.add(taxAppAtBill.getLookUpId());
        taxAppList.add(taxAppAtRecp.getLookUpId());
        List<Long> taxCatList = tbTaxMasService.getDistinctTaxCatByDept(organisation.getOrgid(), assessmentDto.getDeptId(),
                taxAppList);
        checklistChargeDto.setTaxCatList(taxCatList);
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                organisation, assessmentDto.getDeptId(), null, null);
        // Later we will change checklistChargeDto.setEarlyPayRebate to list.
        if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
            assessmentDto.setEarlyPayRebate(earlyPayRebate.get(0));
            checklistChargeDto.setEarlyPayRebate(earlyPayRebate.get(0));
        }
        checklistChargeDto.setBillMasList(billMasList);
        checklistChargeDto.setProvisionalMas(dto);
        checklistChargeDto.setDeptId(assessmentDto.getDeptId());
        calculateArrearsAndTaxForward(checklistChargeDto);
        return checklistChargeDto;

    }

    @Override
    @POST
    @Path("/calculateArrearsAndTaxForward")
    @Produces(MediaType.APPLICATION_JSON)
    public AssessmentChargeCalDTO calculateArrearsAndTaxForward(@RequestBody AssessmentChargeCalDTO request)
            throws Exception {
        Organisation org = new Organisation();
        org.setOrgid(request.getProvisionalMas().getOrgId());
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
        List<TbBillMas> billMasArrears = propertyMainBillService.fetchNotPaidBillForAssessment(
                request.getProvisionalMas().getAssNo(),
                request.getProvisionalMas().getOrgId());

        List<TbBillMas> billList = request.getBillMasList();
        if (billMasArrears != null && !billMasArrears.isEmpty()) {
            propertyService.interestCalculation(org, deptId, billMasArrears,
                    MainetConstants.Property.INT_RECAL_YES);
            billList = propertyService.getBillListWithMergingOfOldAndNewBill(billMasArrears, request.getBillMasList());
            propertyService.updateArrearInCurrentBills(billList);
            billList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
        }
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        BillDisplayDto surCharge = propertyService.calculateSurcharge(org,
                request.getDeptId(), billList, request.getProvisionalMas().getAssNo(),
                MainetConstants.Property.SURCHARGE, finYearId, null);
        if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            request.getProvisionalMas().setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
        }

        BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(request.getProvisionalMas().getAssNo(),
                request.getProvisionalMas().getOrgId(), null);
        request.setAdvanceAmt(advanceAmt);
        propertyService.taxCarryForward(billList, request.getProvisionalMas().getOrgId());

        if (billList != null && !billList.isEmpty()) {
            TbBillMas billMas = billList.get(billList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas, org,
                    deptId);
            if (request.getEarlyPayRebate() != null) {
                billDisDtoList.add(request.getEarlyPayRebate());
            }
            if (surCharge != null) {
                billDisDtoList.add(surCharge);
            }
            if (advanceAmt != null) {
                billDisDtoList.add(advanceAmt);
            }
            if (request.getTaxWiseRebate() != null && !request.getTaxWiseRebate().isEmpty()) {
                request.getTaxWiseRebate().entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
                });
            }
            request.setDisplayDto(billDisDtoList);
        }
        List<BillDisplayDto> rebatedtoList = new ArrayList<BillDisplayDto>();
        rebatedtoList.add(request.getEarlyPayRebate());
        request.getProvisionalMas().setBillTotalAmt(propertyService
                .getTotalActualAmount(billList, rebatedtoList,
                        request.getTaxWiseRebate(), surCharge, advanceAmt));
        request.setBillMasList(billList);
        return request;
    }

    @Override
    @POST
    @Path("/checklistAndCharges")
    @Produces(MediaType.APPLICATION_JSON)
    public AssessmentChargeCalDTO fetchChecklistAndCharges(@RequestBody SelfAssessmentDeaultValueDTO assessmentDto) {

        AssessmentChargeCalDTO checklistChargeDto = new AssessmentChargeCalDTO();
        List<ProvisionalAssesmentFactorDtlDto> factDto = assessmentDto.getProvAsseFactDtlDto();
        ProvisionalAssesmentMstDto dto = assessmentDto.getProvisionalMas();
        Organisation organisation = new Organisation();
        organisation.setOrgid(dto.getOrgId());
        // propertyService.setWardZoneDetailByLocId(dto,
        // assessmentDto.getDeptId(), dto.getLocId());
        if (assessmentDto.getProvAsseFactDtlDto() != null) {
            selfAssessmentService.setFactorMappingToAssDto(assessmentDto.getProvAsseFactDtlDto(), dto);
        }
        List<DocumentDetailsVO> docs = selfAssessmentService.fetchCheckList(dto, factDto);
        checklistChargeDto.setCheckList(docs);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto,
                assessmentDto.getDeptId(), taxWiseRebate, assessmentDto.getFinYearList(), null, null, null, null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                organisation, assessmentDto.getDeptId(), null, null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                organisation, taxWiseRebate);
        propertyService.updateARVRVInBill(dto, billMasList);
        List<DemandLevelRebateDTO> demandreabate = new ArrayList<>(0);
        if (reabteRecDtoList != null && !reabteRecDtoList.isEmpty()) {
            reabteRecDtoList.forEach(rebate -> {
                DemandLevelRebateDTO dmdRebate = new DemandLevelRebateDTO();
                BeanUtils.copyProperties(rebate, dmdRebate);
                demandreabate.add(dmdRebate);
            });
            assessmentDto.setDemandLevelRebateList(demandreabate);
            checklistChargeDto.setDemandLevelRebateList(demandreabate);
        }
        assessmentDto.setTaxWiseRebate(taxWiseRebate);
        checklistChargeDto.setTaxWiseRebate(taxWiseRebate);
        propertyService.interestCalculationWithoutBRMSCall(organisation, assessmentDto.getDeptId(), billMasList,
                MainetConstants.Property.INT_RECAL_NO);
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        BillDisplayDto surCharge = propertyService.calculateSurcharge(organisation,
                assessmentDto.getDeptId(), billMasList, dto.getAssNo(),
                MainetConstants.Property.SURCHARGE, finYearId, null);
        if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            dto.setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
        }
        List<Long> taxAppList = new ArrayList<>();
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        final LookUp taxAppAtRecp = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                organisation);
        taxAppList.add(taxAppAtBill.getLookUpId());
        taxAppList.add(taxAppAtRecp.getLookUpId());
        List<Long> taxCatList = tbTaxMasService.getDistinctTaxCatByDept(organisation.getOrgid(), assessmentDto.getDeptId(),
                taxAppList);
        checklistChargeDto.setTaxCatList(taxCatList);
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                organisation, assessmentDto.getDeptId(), null, null);
        if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
            assessmentDto.setEarlyPayRebate(earlyPayRebate.get(0));
            checklistChargeDto.setEarlyPayRebate(earlyPayRebate.get(0));
        }
        if (billMasList != null && !billMasList.isEmpty()) {
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas, organisation,
                    assessmentDto.getDeptId());
            if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
                billDisDtoList.addAll(earlyPayRebate);
            }
            if (surCharge != null) {
                billDisDtoList.add(surCharge);
            }
            if (!taxWiseRebate.isEmpty()) {
                taxWiseRebate.entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
                });
            }
            checklistChargeDto.setDisplayDto(billDisDtoList);
        }
        checklistChargeDto
                .setBillTotalAmt(
                        propertyService.getTotalActualAmount(billMasList, earlyPayRebate, taxWiseRebate, surCharge));
        checklistChargeDto.setBillMasList(billMasList);
        checklistChargeDto.setProvisionalMas(dto);
        return checklistChargeDto;

    }

    @Override
    @POST
    @Path("/saveSelfAssessment")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentSaveDTO saveSelfAssessment(@RequestBody SelfAssessmentSaveDTO saveData) {
        if (saveData.getDeptId() == null) {
            Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
            saveData.setDeptId(deptId);
        }
        List<BillReceiptPostingDTO> demandreabate = new ArrayList<>(0);
        if (saveData.getDemandLevelRebateList() != null && !saveData.getDemandLevelRebateList().isEmpty()) {
            saveData.getDemandLevelRebateList().forEach(rebate -> {
                BillReceiptPostingDTO dmdRebate = new BillReceiptPostingDTO();
                BeanUtils.copyProperties(rebate, dmdRebate);
                demandreabate.add(dmdRebate);
            });
        }
        Organisation org = new Organisation();
        org.setOrgid(saveData.getProvisionalMas().getOrgId());
        List<Long> bmIds = selfAssessmentService.saveSelfAssessment(saveData.getProvisionalMas(), saveData.getOffline(),
                saveData.getFinYearList(), saveData.getBillMasList(),
                demandreabate, saveData.getAdvanceAmt());
        billMasterCommonService.accountVoucherPosting(bmIds, org,
                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                saveData.getProvisionalMas().getCreatedBy(), null);
        return saveData;
    }

    @Override
    @POST
    @Path("/getFinYearId")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ "application/json", "application/xml" })
    public SelfAssessmentFinancialDTO getFinYearId(@RequestBody PropertyInputDto request) {
        // change request dto as extra nework data was used with old dto
        SelfAssessmentFinancialDTO response = new SelfAssessmentFinancialDTO();
        Long yearId = iFinancialYear.getFinanceYearId(request.getAcquisitionDate());
        response.setFinYearId(yearId);
        return response;
    }

    @Override
    @POST
    @Path("/fetchFromGivenDate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ "application/json", "application/xml" })
    public SelfAssessmentFinancialDTO fetchFromGivenDate(@RequestBody PropertyInputDto request) {
        List<Long> finYearList = null;
        SelfAssessmentFinancialDTO response = new SelfAssessmentFinancialDTO();
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(request.getOrgId(), request.getFinYearId(), new Date());
        if (!financialYearList.isEmpty()) {
            finYearList = new ArrayList<>();
            for (FinancialYear financialYearEach : financialYearList) {
                Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                finYearList.add(finYear);
            }
        }
        response.setFinYearList(finYearList);
        return response;

    }

    @Override
    @POST
    @Path("/fetchNextSchedule")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ "application/json", "application/xml" })
    public SelfAssessmentFinancialDTO fetchNextSchedule(@RequestBody PropertyInputDto request) {
        SelfAssessmentFinancialDTO response = new SelfAssessmentFinancialDTO();
        List<Object> acqDateDetail = new ArrayList<>();
        List<BillingScheduleDetailEntity> nextSchedule = billingScheduleService
                .getNextScheduleFromLastPayDet(request.getOrgId(), request.getScheduleId());
        BillingScheduleDetailEntity billingScheduleDetailEntity = nextSchedule.get(0);
        BillingScheduleDetailEntity selectedSch = billingScheduleService.getSchDetailByScheduleId(request.getScheduleId(),
                request.getOrgId());
        acqDateDetail.add(selectedSch.getBillToDate());
        acqDateDetail.add(iFinancialYear.getFinanceYearId(billingScheduleDetailEntity.getBillFromDate()));
        response.setAcqDateDetail(acqDateDetail);
        return response;
    }

    @Override
    @POST
    @Path("/displayYearList")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ "application/json", "application/xml" })
    public SelfAssessmentFinancialDTO displayYearList(@RequestBody PropertyInputDto request)
            throws Exception {
        SelfAssessmentFinancialDTO response = new SelfAssessmentFinancialDTO();
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(request.getOrgId(), request.getFinYearId(), new Date());
        String financialYear = null;
        Map<Long, String> yearMap = new HashMap<>(0);
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            yearMap.put(finYearTemp.getFaYear(), financialYear);

        }
        response.setYearMap(yearMap);
        return response;
    }

    // User Story #132423: Suda mobile new self assessment service
    @Override
    @POST
    @Path("/displayYearListForMobile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ "application/json", "application/xml" })
    public List<SelfAssessmentFinancialDTO> displayYearListForMobile(@RequestBody PropertyInputDto request) throws Exception {
        List<SelfAssessmentFinancialDTO> responseList = new ArrayList<>();
        List<Long> finYearIdList = new ArrayList<>();
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(request.getOrgId(), request.getFinYearId(), new Date());
        String financialYear = null;
        Map<Long, String> yearMap = new HashMap<>(0);
        for (final FinancialYear finYearTemp : financialYearList) {
            SelfAssessmentFinancialDTO response = new SelfAssessmentFinancialDTO();
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            yearMap.put(finYearTemp.getFaYear(), financialYear);
            response.setFinYearId(finYearTemp.getFaYear());
            response.setFinYear(financialYear);
            finYearIdList.add(finYearTemp.getFaYear());
            response.setFinYearList(finYearIdList);
            response.setYearMap(yearMap);
            responseList.add(response);
        }
        return responseList;

    }

    @Override
    @POST
    @Path("/checkValidProperty")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentDeaultValueDTO checkForValidProperty(@RequestBody SelfAssessmentDeaultValueDTO request)
            throws Exception {
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.NPR, request.getOrgId());
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);

        boolean assessment = selfAssessmentService.CheckForAssesmentFieldForCurrYear(request.getOrgId(),
                request.getProvisionalMas().getAssNo(), request.getProvisionalMas().getAssOldpropno(), "A", finYearId,
                service.getSmServiceId());

        if (!assessment) {

            ProvisionalAssesmentMstDto assMst = null;
            // 128185 - To check any application in progress
            List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
            if (!StringUtils.isEmpty(request.getProvisionalMas().getAssNo())) {
                provAssesMastList = provisionalAssesmentMstService
                        .getPropDetailByPropNoOnly(request.getProvisionalMas().getAssNo());
            } else {
                provAssesMastList = provisionalAssesmentMstService.getPropDetailByOldPropNoAndOrgId(
                        request.getProvisionalMas().getAssOldpropno(), request.getOrgId());
            }
            if (CollectionUtils.isNotEmpty(provAssesMastList)) {
                assMst = provAssesMastList.get(provAssesMastList.size() - 1);
                if (assMst != null && assMst.getApmApplicationId() != null) {
                    WorkflowRequest workflowRequest = workflowRequestService
                            .getWorkflowRequestByAppIdOrRefId(assMst.getApmApplicationId(), null, request.getOrgId());
                    if (workflowRequest != null
                            && StringUtils.equals(MainetConstants.WorkFlow.Status.PENDING, workflowRequest.getStatus())) {
                        request.setProvisionalMas(null);
                        request.setDisplayValidMsg(
                                ApplicationSession.getInstance().getMessage("property.propApplicationInProgress"));
                        return request;
                    }
                }
            }
            //
            assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(request.getOrgId(),
                    request.getProvisionalMas().getAssNo(), request.getProvisionalMas().getAssOldpropno(),
                    "A", null);

            if (assMst != null) {

                // D#122016, D#117828
                Organisation organisation = new Organisation();
                organisation.setOrgid(assMst.getOrgId());
                StringBuilder ownerFullName = new StringBuilder();
                int ownerSize = 1;
                List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMst.getProvisionalAssesmentOwnerDtlDtoList();
                for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

                        if (StringUtils.isEmpty(ownerFullName.toString())) {
                            ownerFullName.append(owner.getAssoOwnerName());
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                                LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                        organisation);
                                ownerFullName.append(reltaionLookUp.getDescLangFirst());
                            } else {
                                ownerFullName.append("Contact person - ");
                            }
                            if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
                                ownerFullName.append(MainetConstants.WHITE_SPACE);
                                ownerFullName.append(owner.getAssoGuardianName());
                            }
                        } else {
                            ownerFullName.append(owner.getAssoOwnerName());
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                                LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                        organisation);
                                ownerFullName.append(reltaionLookUp.getDescLangFirst());
                            } else {
                                ownerFullName.append("Contact person - ");
                            }
                            ownerFullName.append(MainetConstants.WHITE_SPACE);
                            ownerFullName.append(owner.getAssoGuardianName());
                        }
                        if (ownerSize < ownerList.size()) {
                            ownerFullName.append("," + " ");
                        }
                        ownerSize = ownerSize + 1;
                    } else {
                        ownerFullName.append(owner.getAssoOwnerName());
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                            LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    organisation);
                            ownerFullName.append(reltaionLookUp.getDescLangFirst());
                        } else {
                            ownerFullName.append("Contact person - ");
                        }
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        ownerFullName.append(owner.getAssoGuardianName());
                        if (ownerSize < ownerList.size()) {
                            ownerFullName.append("," + " ");
                        }
                    }
                }
                request.setOwnerFullName(ownerFullName.toString());
                // End D#122016, D#117828
                request.setProvisionalMas(assMst);
                request.setDeptId(deptId);
                assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), request.getOrgId()));
                List<Long> finYearList = new ArrayList<>();
                List<FinancialYear> financialYearList = null;
                if (service != null && service.getSmServiceId().equals(assMst.getSmServiceId())) {
                    financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(request.getOrgId(), assMst.getFaYearId(),
                            new Date());
                } else {
                    financialYearList = iFinancialYear
                            .getFinanceYearListAfterGivenDate(request.getOrgId(),
                                    assMst.getFaYearId(),
                                    new Date());
                }
                if (!financialYearList.isEmpty()) {
                    String financialYear = null;
                    for (final FinancialYear finYearTemp : financialYearList) {
                        financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                        request.getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
                        request.getFinancialYearDate().put(finYearTemp.getFaFromDate(), finYearTemp.getFaYear());
                    }
                    propertyService.setUnitDetailsForNextYears(assMst, finYearList, financialYearList);
                }
                // propertyService.setWardZoneDetailByLocId(assMst, request.getDeptId(), assMst.getLocId());
                assMst.setFinancialYearList(finYearList);
                request.setFinYearList(finYearList);
                // Defect#130731
                setDecriptionValues(organisation, assMst);
            } else {
                request.setProvisionalMas(null);
                request.setDisplayValidMsg(ApplicationSession.getInstance().getMessage("property.propValidCheckMsg"));
            }
        } else {
            request.setProvisionalMas(null);
            request.setDisplayValidMsg(ApplicationSession.getInstance().getMessage("property.assessmentDoneValidMsg"));
        }
        return request;
    }

    @Override
    public void setDecriptionValues(Organisation org, ProvisionalAssesmentMstDto assMst) throws Exception {
        List<FinancialYear> financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(org.getOrgid(),
                assMst.getFaYearId(), new Date());
        Map<Long, String> financialYearMap = new LinkedHashMap<>();

        if (!financialYearList.isEmpty()) {
            String financialYear = null;
            for (final FinancialYear finYearTemp : financialYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                financialYearMap.put(finYearTemp.getFaYear(), financialYear);
            }
        }

		assMst.setProAssOwnerTypeName(CommonMasterUtility
				.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), org)
				.getDescLangFirst());

		if (assMst.getPropLvlRoadType() != null) {
			assMst.setProAssdRoadfactorDesc(CommonMasterUtility
					.getNonHierarchicalLookUpObject(assMst.getPropLvlRoadType(), org).getDescLangFirst());
		}

		assMst.setAssWardDesc1(CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard1(), org).getDescLangFirst());

		if (assMst.getAssWard2() != null) {
			assMst.setAssWardDesc2(
					CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard2(), org).getDescLangFirst());
		}

		if (assMst.getAssWard3() != null) {
			assMst.setAssWardDesc3(
					CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard3(), org).getDescLangFirst());
		}

		if (assMst.getAssWard4() != null) {
			assMst.setAssWardDesc4(
					CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard4(), org).getDescLangFirst());
		}

		if (assMst.getAssWard5() != null) {
			assMst.setAssWardDesc5(
					CommonMasterUtility.getHierarchicalLookUp(assMst.getAssWard5(), org).getDescLangFirst());
		}
        for (ProvisionalAssesmentDetailDto detaildto : assMst.getProvisionalAssesmentDetailDtoList())
            if (detaildto.getAssdBuildupArea() != null) {
                for (Map.Entry<Long, String> entry : financialYearMap.entrySet()) {
                    if (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                        detaildto.setProFaYearIdDesc(entry.getValue());
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                    detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                    detaildto.setProAssdUsagetypeDesc(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getDescLangFirst());

                    if (detaildto.getAssdUsagetype2() != null) {
                        detaildto.setProAssdUsagetypeDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype3() != null) {
                        detaildto.setProAssdUsagetypeDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype4() != null) {
                        detaildto.setProAssdUsagetypeDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdUsagetype5() != null) {
                        detaildto.setProAssdUsagetypeDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
                    }

                    detaildto.setAssdNatureOfpropertyDesc1(CommonMasterUtility
                            .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty1(), org).getDescLangFirst());

                    if (detaildto.getAssdNatureOfproperty2() != null) {
                        detaildto.setAssdNatureOfpropertyDesc2(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty2(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty3() != null) {
                        detaildto.setAssdNatureOfpropertyDesc3(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty3(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty4() != null) {
                        detaildto.setAssdNatureOfpropertyDesc4(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty4(), org).getDescLangFirst());
                    }
                    if (detaildto.getAssdNatureOfproperty5() != null) {
                        detaildto.setAssdNatureOfpropertyDesc5(CommonMasterUtility
                                .getHierarchicalLookUp(detaildto.getAssdNatureOfproperty5(), org).getDescLangFirst());
                    }
                    detaildto.setProFloorNo(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org).getDescLangFirst());
                    detaildto.setProAssdConstruTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org).getDescLangFirst());

                    detaildto.setProAssdOccupancyTypeDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org).getDescLangFirst());

                    for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : detaildto
                            .getProvisionalAssesmentFactorDtlDtoList()) {
                        if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                            provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(
                                            provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                    .getDescLangFirst());
                            provisionalAssesmentFactorDtlDto
                                    .setProAssfFactorIdDesc(CommonMasterUtility
                                            .getNonHierarchicalLookUpObject(
                                                    provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                            .getDescLangFirst());
                        }
                    }
                }

            }
    }

    @Override
    @POST
    @Path("/fetchPropertyByApplicationId")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentDeaultValueDTO fetchPropertyByApplicationId(@RequestBody SelfAssessmentDeaultValueDTO request)
            throws Exception {
        List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(request.getProvisionalMas().getApmApplicationId(), request.getOrgId());
        // D#93173
        if (provAssDtoList.isEmpty()) {
            provAssDtoList = assesmentMastService
                    .getAssesmentMstDtoListByAppId(request.getProvisionalMas().getApmApplicationId(), request.getOrgId());
        }
        final ProvisionalAssesmentMstDto assMst = propertyAuthorizationService.getAssesmentMstDtoForDisplay(provAssDtoList);
        if (assMst != null) {
            request.setProvisionalMas(assMst);
            assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), request.getOrgId()));
            List<Long> finYearList = new ArrayList<>();
            Long finYearId = iFinancialYear.getFinanceYearId(assMst.getAssAcqDate());
            List<FinancialYear> financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(request.getOrgId(),
                    finYearId,
                    new Date());
            if (!financialYearList.isEmpty()) {
                String financialYear = null;
                for (final FinancialYear finYearTemp : financialYearList) {
                    financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                    request.getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
                }

            }
            // propertyService.setWardZoneDetailByLocId(assMst, request.getDeptId(), assMst.getLocId());
            assMst.setFinancialYearList(finYearList);
        } else {
            request.setProvisionalMas(null);
        }
        return request;
    }

    @Override
    @POST
    @Path("/checklistAndChargesNoChnage")
    @Produces(MediaType.APPLICATION_JSON)
    public AssessmentChargeCalDTO checklistAndChargesNoChnage(@RequestBody SelfAssessmentDeaultValueDTO request)
            throws Exception {
        Organisation organisation = new Organisation();
        organisation.setOrgid(request.getOrgId());
        if (request.getProvisionalMas().getAssCorrAddress() == null) {
            request.getProvisionalMas().setProAsscheck(MainetConstants.YES);
        } else {
            request.getProvisionalMas().setProAsscheck(MainetConstants.NO);
        }
        if (request.getProvisionalMas().getAssLpYear() == null) {
            request.getProvisionalMas().setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            request.getProvisionalMas().setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.NCA,
                request.getOrgId());
        request.setDeptId(service.getTbDepartment().getDpDeptid());
        request.getProvisionalMas().setSmServiceId(service.getSmServiceId());
        request.setProvAsseFactDtlDto(factorMappingForView(request.getProvisionalMas(), organisation));
        AssessmentChargeCalDTO taxDto = fetchChecklistAndCharges(request);

        return calculateArrearsAndTaxForward(taxDto);

    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst,
            Organisation organisation) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                fact.setUnitNoFact(propDet.getAssdUnitNo().toString());
                fact.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), organisation)
                        .getDescLangFirst());
                fact.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                organisation)
                        .getDescLangFirst());
                fact.setFactorValueCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), organisation)
                        .getLookUpCode());
                factorMap.add(fact);
                assMst.getProAssfactor().add(MainetConstants.YES);
            });

        });
        return factorMap;
    }

    @Override
    @POST
    @Path("/fetchAllLastPaymentDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentDeaultValueDTO fetchAllLastPaymentDetails(@RequestBody SelfAssessmentDeaultValueDTO request)
            throws Exception {
        List<TbBillMas> billMasList = propertyMainBillService.fetchAllBillByPropNo(request.getProvisionalMas().getAssNo(),
                request.getOrgId());
        List<BillDisplayDto> rebatedtoList = new ArrayList<BillDisplayDto>();
        rebatedtoList.add(request.getEarlyPayRebate());
        request.getProvisionalMas().setBillAmount(propertyService.getTotalActualAmount(billMasList,
                rebatedtoList, request.getTaxWiseRebate(), null));
        BigDecimal outstandingAmount = iReceiptEntryService.getPaidAmountByAdditionalRefNo(request.getOrgId(),
                request.getProvisionalMas().getAssNo(),
                request.getDeptId());
        if (outstandingAmount != null) {
            request.getProvisionalMas().setOutstandingAmount(outstandingAmount.doubleValue());
        }
        changeInAssessmentService.setLastPaymentDetails(request.getProvisionalMas(), request.getOrgId());
        return request;
    }

    @Override
    @POST
    @Path("/billScheduleLookup")
    @Produces(MediaType.APPLICATION_JSON)
    public ExcelDownloadDTO billScheduleLookup(@RequestBody ExcelDownloadDTO request)
            throws Exception {
        List<String> taxAmountList = new LinkedList<>();
        Organisation org = new Organisation();
        org.setOrgid(request.getOrgId());
        List<LookUp> billSchList = selfAssessmentService.getAllBillschedulByOrgid(org);
        request.getBillMasList().forEach(mas -> {
            BillingScheduleDetailEntity billSchDet = billingScheduleService
                    .getSchedulebySchFromDate(request.getOrgId(), mas.getBmFromdt());
            billSchList.parallelStream()
                    .filter(lookup -> billSchDet.getSchDetId().equals(lookup.getLookUpId()))// enter in loop if tax cat is
                    .forEach(lookup -> {
                        taxAmountList.add(lookup.getLookUpCode());
                    });
        });
        request.setTaxAmountList(taxAmountList);
        return request;

    }

    @Override
    @POST
    @Path("/getPropertyPaymentDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public BillPaymentDetailDto getPropertyPaymentDetails(@RequestBody PropertyBillPaymentDto request) throws Exception {
        Organisation org = new Organisation();
        org.setOrgid(request.getOrgId()); 
    	//PayTM fetch Property Block
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
        	return null;
        }else {
        	 BillPaymentDetailDto billPaymentDetailDto = propertyBillPaymentService.getBillPaymentDetail(request.getAssOldpropno(),
                    request.getAssNo(), request.getOrgId(), request.getUserId(), request.getManualReeiptDate(), request.getBillMethod(), request.getFlatNo());
        	ServiceMaster service = serviceMaster.getServiceByShortName("PBP", request.getOrgId());
            if(service != null) {
                billPaymentDetailDto.setServiceId(service.getSmServiceId());
                billPaymentDetailDto.setServiceShortCode(service.getSmShortdesc());
            }
            
            return billPaymentDetailDto;
        }
     }
    
    @Override
    @POST
    @Path("/propertyPaymentDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public BillPaymentDetailDto fetchPropertyPaymentDetails(@RequestBody PropertyBillPaymentDto request) throws Exception {
         BillPaymentDetailDto billPaymentDetailDto = propertyBillPaymentService.getBillPaymentDetail(request.getAssOldpropno(),
                request.getAssNo(), request.getOrgId(), request.getUserId(), request.getManualReeiptDate(), request.getBillMethod(), request.getFlatNo());
         Organisation org = new Organisation();
         org.setOrgid(request.getOrgId()); 
         if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL)) {
        	 ServiceMaster service = serviceMaster.getServiceByShortName("PBP", request.getOrgId());
        	 if(service != null) {
        		 billPaymentDetailDto.setServiceId(service.getSmServiceId());
        		 billPaymentDetailDto.setServiceShortCode(service.getSmShortdesc());
        	 }        	 
         }
        return billPaymentDetailDto;
    }

    @Override
    @POST
    @Path("/viewUploadedDocuments")
    @Produces(MediaType.APPLICATION_JSON)

    public List<DocumentDetailsVO> fetchAppDocuments(ProperySearchDto searchDto) {
        List<DocumentDetailsVO> document = new ArrayList<>();
        List<CFCAttachment> att = iChecklistVerificationService.getDocumentUploaded(searchDto.getApplicationId(),
                searchDto.getOrgId());
        if (att != null && !att.isEmpty()) {
            att.forEach(doc -> {
                DocumentDetailsVO d = new DocumentDetailsVO();
                d.setDocumentName(doc.getClmDescEngl());
                d.setUploadedDocumentPath(doc.getAttPath());
                d.setDocumentType(doc.getDmsDocId());
                d.setDescriptionType(doc.getAttFname());
                document.add(d);
            });
        }
        return document;
    }

    @Override
    @POST
    @Path("/fetchTaxListForDisplay")
    @Produces(MediaType.APPLICATION_JSON)
    public AssessmentChargeCalDTO fetchTaxListForDisplay(@RequestBody AssessmentChargeCalDTO dto) throws Exception {

        List<TbBillMas> billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(
                dto.getProvisionalMas().getAssNo(),
                dto.getProvisionalMas().getOrgId());
        if (billMasList.isEmpty()) {
            billMasList = propertyMainBillService.fetchAllBillByPropNo(dto.getProvisionalMas().getAssNo(),
                    dto.getProvisionalMas().getOrgId());
        }
        if (!billMasList.isEmpty()) {
            dto.setBillMasList(billMasList);
            Organisation organisation = new Organisation();
            organisation.setOrgid(dto.getProvisionalMas().getOrgId());
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<Long> taxAppList = new ArrayList<>();
            final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                    organisation);
            final LookUp taxAppAtRecp = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                    organisation);
            taxAppList.add(taxAppAtBill.getLookUpId());
            taxAppList.add(taxAppAtRecp.getLookUpId());
            List<Long> taxCatList = tbTaxMasService.getDistinctTaxCatByDept(dto.getProvisionalMas().getOrgId(),
                    dto.getDeptId(),
                    taxAppList);
            propertyBillPaymentService.setTaxDetailInBillDetail(billMas, organisation,
                    dto.getDeptId(),null);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas,
                    organisation, dto.getDeptId());
            Map<Long, BillDisplayDto> rebateMap = propertyAuthorizationService.getRebete(
                    dto.getProvisionalMas().getApmApplicationId(),
                    dto.getProvisionalMas().getOrgId(),
                    dto.getDeptId());
            rebateMap.entrySet().forEach(rebate -> {
                billDisDtoList.add(rebate.getValue());
            });
            dto.getProvisionalMas().setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, null, rebateMap, null));
            dto.setDisplayDto(billDisDtoList);
            dto.setTaxCatList(taxCatList);

        }
        return dto;
    }

    @Override
    @POST
    @Path("/saveAssessmentWithEdit")
    @Produces(MediaType.APPLICATION_JSON)
    public SelfAssessmentSaveDTO saveAssessmentWithEdit(@RequestBody SelfAssessmentSaveDTO saveData) throws Exception {
        selfAssessmentService.saveSelfAssessmentWithEdit(saveData.getProvisionalMas(),
                saveData.getProvisionalMas().getOrgId(),
                saveData.getProvisionalMas().getCreatedBy(), saveData.getDeptId(), saveData.getLanguageId(),
                saveData.getFinYearList(), saveData.getBillMasList(),
                null);
        return saveData;
    }

    @POST
    @Path("/getCheckList")
    @Produces(MediaType.APPLICATION_JSON)

    public List<DocumentDetailsVO> getCheckList(ProvisionalAssesmentMstDto assDto,
            List<ProvisionalAssesmentFactorDtlDto> factDto) {
        return selfAssessmentService.fetchCheckList(assDto, factDto);
        // return null;
    }

    @Override
    @POST
    @Path("/fetchCharges")
    @Produces(MediaType.APPLICATION_JSON)
    public AssessmentChargeCalDTO fetchCharges(@RequestBody SelfAssessmentDeaultValueDTO assessmentDto) {

        AssessmentChargeCalDTO checklistChargeDto = new AssessmentChargeCalDTO();
        ProvisionalAssesmentMstDto dto = assessmentDto.getProvisionalMas();
        Organisation organisation = new Organisation();
        organisation.setOrgid(dto.getOrgId());
        // propertyService.setWardZoneDetailByLocId(dto,
        // assessmentDto.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(assessmentDto.getProvAsseFactDtlDto(), dto);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto,
                assessmentDto.getDeptId(), taxWiseRebate, assessmentDto.getFinYearList(), null, null, null, null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                organisation, assessmentDto.getDeptId(), null, null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                organisation, taxWiseRebate);
        List<DemandLevelRebateDTO> demandreabate = new ArrayList<>(0);
        if (reabteRecDtoList != null && !reabteRecDtoList.isEmpty()) {
            reabteRecDtoList.forEach(rebate -> {
                DemandLevelRebateDTO dmdRebate = new DemandLevelRebateDTO();
                BeanUtils.copyProperties(rebate, dmdRebate);
                demandreabate.add(dmdRebate);
            });
            assessmentDto.setDemandLevelRebateList(demandreabate);
            checklistChargeDto.setDemandLevelRebateList(demandreabate);
        }
        assessmentDto.setTaxWiseRebate(taxWiseRebate);
        checklistChargeDto.setTaxWiseRebate(taxWiseRebate);
        propertyService.interestCalculationWithoutBRMSCall(organisation, assessmentDto.getDeptId(), billMasList,
                MainetConstants.Property.INT_RECAL_NO);
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        BillDisplayDto surCharge = propertyService.calculateSurcharge(organisation,
                assessmentDto.getDeptId(), billMasList, dto.getAssNo(),
                MainetConstants.Property.SURCHARGE, finYearId, null);
        if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            dto.setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
        }
        List<Long> taxAppList = new ArrayList<>();
        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                organisation);
        final LookUp taxAppAtRecp = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA,
                organisation);
        taxAppList.add(taxAppAtBill.getLookUpId());
        taxAppList.add(taxAppAtRecp.getLookUpId());
        List<Long> taxCatList = tbTaxMasService.getDistinctTaxCatByDept(organisation.getOrgid(), assessmentDto.getDeptId(),
                taxAppList);
        checklistChargeDto.setTaxCatList(taxCatList);
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                organisation, assessmentDto.getDeptId(), null, null);
        if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
            assessmentDto.setEarlyPayRebate(earlyPayRebate.get(0));
            checklistChargeDto.setEarlyPayRebate(earlyPayRebate.get(0));
        }
        if (billMasList != null && !billMasList.isEmpty()) {
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas, organisation,
                    assessmentDto.getDeptId());
            if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
                billDisDtoList.addAll(earlyPayRebate);
            }
            if (taxWiseRebate != null && !taxWiseRebate.isEmpty()) {
                taxWiseRebate.entrySet().forEach(entry -> {
                    billDisDtoList.add(entry.getValue());
                });
            }
            checklistChargeDto.setDisplayDto(billDisDtoList);
        }
        checklistChargeDto
                .setBillTotalAmt(
                        propertyService.getTotalActualAmount(billMasList, earlyPayRebate, taxWiseRebate, surCharge));
        checklistChargeDto.setBillMasList(billMasList);
        checklistChargeDto.setProvisionalMas(dto);
        return checklistChargeDto;

    }

    @Transactional
    @Override
    @POST
    @Path("/getServiceIdByShortName/{orgId}/{serviceCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getServiceIdByShortName(@PathParam(value = "orgId") Long orgId,
            @PathParam(value = "serviceCode") String serviceCode) {
        ServiceMaster smMaster = serviceMaster.getServiceByShortName(serviceCode, orgId);
        if (smMaster != null) {
            return smMaster.getSmServiceId();
        }
        return null;
    }

    @Path("/callWorkflowInCaseOfZeroBillPayment")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Override
    public void callWorkflowInCaseOfZeroBillPaymentByRequest(@RequestBody SelfAssessmentDeaultValueDTO assDefaultDto) {
        callWorkflowInCaseOfZeroBillPayment(assDefaultDto.getCommonChallanDTO(), assDefaultDto.getProvisionalMas());
    }

    @Override
    @Transactional
    public void callWorkflowInCaseOfZeroBillPayment(CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDt) {
        WardZoneBlockDTO dwzDto = new WardZoneBlockDTO();
        if (asseMstDt.getAssWard1() != null) {
            dwzDto.setAreaDivision1(asseMstDt.getAssWard1());
        }
        if (asseMstDt.getAssWard2() != null) {
            dwzDto.setAreaDivision1(asseMstDt.getAssWard2());
        }
        if (asseMstDt.getAssWard3() != null) {
            dwzDto.setAreaDivision1(asseMstDt.getAssWard3());
        }
        if (asseMstDt.getAssWard4() != null) {
            dwzDto.setAreaDivision1(asseMstDt.getAssWard4());
        }
        if (asseMstDt.getAssWard5() != null) {
            dwzDto.setAreaDivision1(asseMstDt.getAssWard5());
        }
        workflowRequestService.initiateAndUpdateWorkFlowProcess(offline, dwzDto);
    }

    @Path("/saveAndUpdateDraftApplicationAfterEdit")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Override
    public SelfAssessmentSaveDTO saveAndUpdateDraftApplicationAfterEdit(@RequestBody SelfAssessmentSaveDTO saveDto) {
        Organisation org = new Organisation();
        org.setOrgid(saveDto.getProvisionalMas().getOrgId());
        List<Long> bmIds = selfAssessmentService.saveAndUpdateDraftAssessmentAfterEdit(saveDto, org);
        billMasterCommonService.accountVoucherPosting(bmIds, org,
                MainetConstants.Property.PROP_DEPT_SHORT_CODE,
                saveDto.getEmpId(), null);
        return saveDto;
    }

    @Override
    @POST
    @Path("/updateOTPServiceForAssessment/mobileNo/{mobileNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyDetailDto updateOTPServiceForAssessment(@PathParam(value = "mobileNo") String mobileNo) {
        LOGGER.info("REST call to updateOTPServiceForAssessment");
        PropertyDetailDto detailDto = new PropertyDetailDto();
        ProvisionalAssesmentOwnerDtlDto ownerDtlDto = assesmentMastService.fetchPropertyOwnerByMobileNo(mobileNo);
        if (ownerDtlDto != null) {
            detailDto.setStatus(MainetConstants.Req_Status.SUCCESS);
            detailDto.setPrimaryOwnerMobNo(ownerDtlDto.getAssoMobileno());
            detailDto.setPrimaryOwnerName(ownerDtlDto.getAssoOwnerName());
            if (ownerDtlDto.getAssoMobileNoOtp() != null) {
                detailDto.setOtp(ownerDtlDto.getAssoMobileNoOtp().toString());
            }
            if (ownerDtlDto.geteMail() != null) {
                detailDto.setOwnerEmail(ownerDtlDto.geteMail());
            }
        } else {
            detailDto.setStatus(MainetConstants.Req_Status.FAIL);
            detailDto.setErrorMsg(
                    ApplicationSession.getInstance().getMessage("mobie.not.registered.or.invalid"));
        }
        return detailDto;
    }

    @Override
    @POST
    @Path("/getOTPServiceForAssessment/mobileNo/{mobileNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public PropertyDetailDto getOTPServiceForAssessment(@PathParam(value = "mobileNo") String mobileNo) {
        LOGGER.info("REST call to getOTPServiceForAssessment");
        PropertyDetailDto detailDto = new PropertyDetailDto();
        ProvisionalAssesmentOwnerDtlDto ownerDtlDto = assesmentMastService.getOTPServiceForAssessment(mobileNo);
        if (ownerDtlDto != null && ownerDtlDto.getAssoMobileNoOtp() != null) {
            detailDto.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
            detailDto.setOtp(ownerDtlDto.getAssoMobileNoOtp().toString());
            if (ownerDtlDto.getUpdatedDate() != null) {
                detailDto.setUpdatedDate(ownerDtlDto.getUpdatedDate());
            }
        } else {
            detailDto.setStatus(MainetConstants.WebServiceStatus.FAIL);
        }
        return detailDto;
    }

    // Defect#130681 there should be facility that citizen can update mobile no and email id at the time of payment
    @Override
    @POST
    @Transactional
    @Path("/updateMobileAndEmailForMobile")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonAppResponseDTO updateMobileAndEmailForMobile(@RequestBody ProperySearchDto searchDto) {
        LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " updateMobileAndEmailForMobile method");
        CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
        //AssesmentOwnerDtlEntity assesmentOwnerDtlEntity = null;
        int count = 0;

        List<AssesmentOwnerDtlEntity> mainOwnerDtlEntityList = assesmentMastService
                .getPrimaryOwnerDetailByPropertyNo(searchDto.getOrgId(), searchDto.getProertyNo());
        if (CollectionUtils.isNotEmpty(mainOwnerDtlEntityList)) {
        	for(AssesmentOwnerDtlEntity assesmentOwnerDtlEntity : mainOwnerDtlEntityList) {
        		//assesmentOwnerDtlEntity = mainOwnerDtlEntityList.get(0);
        		if(StringUtils.isBlank(searchDto.geteMail()) && StringUtils.isNotBlank(assesmentOwnerDtlEntity.geteMail())) {
        			searchDto.seteMail(assesmentOwnerDtlEntity.geteMail());
        		}
        		try {
        			// update Mobile And Email in main table
        			assesmentMastService.updateMobileAndEmailForMobileMainTable(searchDto.getMobileno(), searchDto.geteMail(),
        					assesmentOwnerDtlEntity.getProAssoId(), new Date());
        		} catch (Exception e) {
        			LOGGER.error(e.getMessage());
        			throw new FrameworkException("error occurs while updateMobileAndEmailForMobile ", e);
        		}
        		
        	}
        }
        //ProvisionalAssesmentOwnerDtlEntity provisionalAssesmentOwnerDtlEntity = null;
        List<ProvisionalAssesmentOwnerDtlEntity> ownerDtlEntityList = provisionalAssesmentMstService
                .getPrimaryOwnerDetailByPropertyNo(searchDto.getOrgId(), searchDto.getProertyNo());
        if (CollectionUtils.isNotEmpty(ownerDtlEntityList)) {
        	for(ProvisionalAssesmentOwnerDtlEntity provisionalAssesmentOwnerDtlEntity : ownerDtlEntityList) {
        		
        		//provisionalAssesmentOwnerDtlEntity = ownerDtlEntityList.get(0);
        		if(StringUtils.isBlank(searchDto.geteMail()) && StringUtils.isNotBlank(provisionalAssesmentOwnerDtlEntity.geteMail())) {
        			searchDto.seteMail(provisionalAssesmentOwnerDtlEntity.geteMail());
        		}
        		try {
        			// update Mobile And Email in provisional table
        			count = assesmentMastService.updateMobileAndEmailForMobile(searchDto.getMobileno(), searchDto.geteMail(),
        					provisionalAssesmentOwnerDtlEntity.getProAssoId(), new Date());
        			if (count > 0) {
        				responseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
        				responseDTO.setHttpstatus(HttpStatus.OK);
        			} else {
        				responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
        			}
        		} catch (Exception e) {
        			LOGGER.error(e.getMessage());
        			throw new FrameworkException("error occurs while updateMobileAndEmailForMobile ", e);
        		}
        	}
        } else {
            responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMsg("No record found to update");
        }
        LOGGER.info("End -->  " + this.getClass().getSimpleName() + " updateMobileAndEmailForMobile method");
        return responseDTO;
    }
    
    
    @Override
    @POST
    @Transactional
    @Path("/getPropertyMposReceipt")
    @Produces(MediaType.APPLICATION_JSON)
    public ChallanReceiptPrintDTO knockOffAmountToConfirmAmount(@RequestBody BillPaymentDetailDto request) {
    	ChallanReceiptPrintDTO updateBillMasterAmountPaidForMPOSReceipt = updateBillMasterAmountPaidForMPOSReceipt(request.getAssmtDto().getAssNo(), request.getMosPayableAmount(),request.getOrgId(), 0L, "", null, null);
    	if(request != null && StringUtils.isNotBlank(request.getMobileNo())) {
    		updateBillMasterAmountPaidForMPOSReceipt.setMobileNumber(request.getMobileNo());
    		ProperySearchDto searchDto = new ProperySearchDto();
    		searchDto.setMobileno(request.getMobileNo());
    		searchDto.setProertyNo(request.getAssmtDto().getAssNo());
    		searchDto.setOrgId(request.getOrgId());
    		Organisation organisation = organisationService.getOrganisationById(request.getOrgId());
    		if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.APP_NAME.ASCL)) {
    			updateMobileAndEmailForMobile(searchDto);    			
    		}
    	}
         return updateBillMasterAmountPaidForMPOSReceipt;
    }
    
    public ChallanReceiptPrintDTO updateBillMasterAmountPaidForMPOSReceipt(final String propertyNo, Double amount,
            final Long orgId, final Long userId, String ipAddress, Date manualReceptDate,String flatNo) {// Advance Amount Pending
    	LOGGER.info("Method parameters of updateBillMasterAmountPaid"+" "+"propno"+" "+propertyNo +" "+"orgid"+ orgId);
    	List<BillReceiptPostingDTO> result = new ArrayList<>();
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		final Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		final Organisation org = new Organisation();
		final double actualPayAmt = amount;
		org.setOrgid(orgId);
		List<TbBillMas> billMasData = null;
		LookUp billMethod = null;
		LookUp billingMethodLookUp = null;
		try {
			billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV",
					org);
		} catch (Exception e) {
		}
		int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(propertyNo, orgId);
		if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
				&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
			Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propertyNo, orgId);
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
					org);
		}
		if (billingMethodLookUp != null
				&& StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)) {
			if (count > 0) {
				// Form Main Bill table
				billMasData = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(propertyNo, orgId, flatNo);
			} else {
				// From Provisional Bill Table
				billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessment(propertyNo, orgId);
			}
		} else {
			if (count > 0) {
				// Form Main Bill table
				billMasData = propertyMainBillService.fetchNotPaidBillForAssessment(propertyNo, orgId);
			} else {
				// From Provisional Bill Table
				billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessment(propertyNo, orgId);
			}
		}

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
				MainetConstants.STATUS.ACTIVE);
		if ((billMasData != null) && !billMasData.isEmpty()) {
			propertyBRMSService.fetchInterstRate(billMasData, org, deptId);
			LookUp penalIntLookUp = null;
			try {
				penalIntLookUp = CommonMasterUtility.getValueFromPrefixLookUp("PIT", "ENV", org);
			} catch (Exception e) {
				LOGGER.error("No Prefix found for ENV(PIT)");
			}
			if (penalIntLookUp != null && StringUtils.isNotBlank(penalIntLookUp.getOtherField())
					&& StringUtils.equals(penalIntLookUp.getOtherField(), MainetConstants.FlagY)) {
				
				billMasterCommonService.calculatePenaltyInterest(billMasData, org, deptId, MainetConstants.FlagY,
						manualReceptDate, MainetConstants.FlagN, MainetConstants.FlagY, userId);
				 
			} else {
				billMasterCommonService.calculateMultipleInterest(billMasData, org, deptId, MainetConstants.Y_FLAG,
						null);
			}
			claculateDemandNoticeGenCharges(billMasData, org, deptId, userId);
			Long finYearId = iFinancialYear.getFinanceYearId(new Date());
			PropertyPenltyDto currentFinYearPenaltyDto = propertyPenltyService.calculateExistingSurcharge(propertyNo,
					finYearId, orgId);
			BillDisplayDto surCharge = propertyService.calculateSurcharge(org, deptId, billMasData, propertyNo,
					MainetConstants.Property.SURCHARGE, finYearId, manualReceptDate);
			if (currentFinYearPenaltyDto == null && surCharge != null && actualPayAmt > 0
					&& surCharge.getTotalTaxAmt().doubleValue() > 0) {
				double pendingSurcharge = surCharge.getTotalTaxAmt().doubleValue();
				amount -= surCharge.getTotalTaxAmt().doubleValue();
				double amountPaidSurcharge = actualPayAmt - amount;
				pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
				/*
				 * if (amount >= 0) { propertyPenltyService.savePropertyPenlty(propertyNo,
				 * surCharge.getFinYearId(), orgId, userId, ipAddress,
				 * surCharge.getTotalTaxAmt().doubleValue(), pendingSurcharge,null); }
				 */
			} else {
				if (currentFinYearPenaltyDto != null && currentFinYearPenaltyDto.getPendingAmount() > 0) {
					surCharge = new BillDisplayDto();
					LookUp taxSubCatLookup = CommonMasterUtility.getHieLookupByLookupCode(
							MainetConstants.Property.SURCHARGE, PrefixConstants.LookUpPrefix.TAC, 2, org.getOrgid());
					final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
							MainetConstants.Property.propPref.RCPT, MainetConstants.Property.propPref.CAA, org);

					List<TbTaxMasEntity> taxList = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(),
							deptId, taxAppAtBill.getLookUpId(), taxSubCatLookup.getLookUpId());
					surCharge.setTotalTaxAmt(new BigDecimal(currentFinYearPenaltyDto.getPendingAmount()));
					surCharge.setTaxId(taxList.get(0).getTaxId());
					surCharge.setTaxCategoryId(taxList.get(0).getTaxCategory1());
					double pendingSurcharge = currentFinYearPenaltyDto.getPendingAmount();
					amount -= currentFinYearPenaltyDto.getPendingAmount();
					double amountPaidSurcharge = actualPayAmt - amount;
					pendingSurcharge = pendingSurcharge - amountPaidSurcharge;
					if (pendingSurcharge < 0) {
						pendingSurcharge = 0.0;
					}
					currentFinYearPenaltyDto.setPendingAmount(pendingSurcharge);
					/*
					 * propertyPenltyService.updatePropertyPenalty(currentFinYearPenaltyDto,
					 * ipAddress, userId);
					 */
				}
			}

			final TbBillMas last = billMasData.get(billMasData.size() - 1);
			List<BillReceiptPostingDTO> rebateTaxList = new ArrayList<BillReceiptPostingDTO>();
			List<BillDisplayDto> rebateDtoList = null;
			if (last.getBmPaidFlag().equals(MainetConstants.PAY_STATUS.NOT_PAID)) {

				Date currDate = new Date();
				if(manualReceptDate != null) {
					currDate = manualReceptDate;
				}
				Long payFinYearId = iFinancialYear.getFinanceYearId(currDate);
				FinancialYear currentFinYearDto = ApplicationContextProvider.getApplicationContext()
						.getBean(IFinancialYearService.class).getFinincialYearsById(payFinYearId, orgId);
				Long noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYear(propertyNo,
						currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
				Organisation organisation = new Organisation();
				organisation.setOrgid(orgId);
				LookUp rebate = null;
				try {
					rebate = CommonMasterUtility.getValueFromPrefixLookUp("RBT", "ENV", organisation);

				} catch (Exception e) {
					LOGGER.error("No prefix found for ENV(RBT)");
				}
				String paymentMethodType = null;
				double receiptAmount = 0.0;
				Long rebateReceivedCount = 0L;
				if(rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
						&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
					paymentMethodType = billMasterCommonService.getFullPaymentOrHalfPayRebate(billMasData,org);
					BigDecimal totalReceiptAmount = iReceiptEntryService.getReceiptAmountPaidByPropNoOrFlatNo(propertyNo, flatNo, org, deptId);
					if(totalReceiptAmount != null) {
						receiptAmount = totalReceiptAmount.doubleValue();
					}
					rebateReceivedCount = propertyBillPaymentService.getRebateReceivedCount(propertyNo, flatNo,
							currDate, currentFinYearDto, organisation, deptId);
					last.setBmToatlRebate(0.0);
				}
				if (noOfBillsPaidInCurYear == null || noOfBillsPaidInCurYear <= 0) {
					LookUp billDeletionInactive = null;
					try {
						billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", organisation);
					} catch (Exception e) {
						LOGGER.error("No prefix found for ENV - BDI");
					}
					if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
							&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
						noOfBillsPaidInCurYear = receiptRepository.getCountOfbillPaidBetweenCurFinYearAdvance(
								propertyNo, currentFinYearDto.getFaFromDate(), currentFinYearDto.getFaToDate(), orgId);
					}
				}
				if ((rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
						&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)
						&& billMasterCommonService.checkRebateAppl(billMasData,org) && rebateReceivedCount <= 0 && last.getBmYear().equals(finYearId))
						|| (rebate == null && ((last.getBmToatlRebate() > 0)
								|| (last.getBmToatlRebate() == 0 && noOfBillsPaidInCurYear == null
										|| noOfBillsPaidInCurYear == 0))
								&& last.getBmLastRcptamt() == 0.0)) {

					rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasData, org, deptId,
							manualReceptDate, paymentMethodType);
					double rebateAmount = 0.0;

					// #107759 By Arun
					Double bmTotalOutStandingAmt = 0.0;
					LookUp userChargesLookUp = null;
					try {
						userChargesLookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.EUC,
								MainetConstants.ENV, organisation);
					} catch (Exception e) {
						LOGGER.error("No prefix found for ENV - EUC");
					}
					for (BillDisplayDto billDisplayDto : rebateDtoList) {
						rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
					}

					if (CollectionUtils.isNotEmpty(rebateDtoList)) {
						double roundedRebateAmt = Math.round(rebateAmount);
						if (last.getBmToatlRebate() > 0) {
							roundedRebateAmt = last.getBmToatlRebate();
						}
						// Exclude user charges
						if (userChargesLookUp != null
								&& MainetConstants.Y_FLAG.equals(userChargesLookUp.getOtherField())) {
							for (TbBillDet billDet : last.getTbWtBillDet()) {
								LookUp taxCat = CommonMasterUtility.getHierarchicalLookUp(billDet.getTaxCategory(),
										organisation);
								if (StringUtils.equals(taxCat.getLookUpCode(), MainetConstants.FlagN)) {
									for (BillDisplayDto billDisplayDto : rebateDtoList) {
										TbTaxMas taxMas = tbTaxMasService.findTaxDetails(orgId,
												billDisplayDto.getParentTaxCode());
										if (billDet.getTaxId().equals(taxMas.getTaxId())) {
											bmTotalOutStandingAmt = bmTotalOutStandingAmt
													+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
										}
									}
								} else {
									bmTotalOutStandingAmt = bmTotalOutStandingAmt
											+ (billDet.getBdPrvBalArramt() + billDet.getBdCurBalTaxamt());
								}
							}
							bmTotalOutStandingAmt = bmTotalOutStandingAmt - roundedRebateAmt;
						} else {
							bmTotalOutStandingAmt = last.getBmTotalOutstanding() - roundedRebateAmt;
						}
						double paidAmount = amount.doubleValue();
						if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
								&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY)) {
							paidAmount = paidAmount + receiptAmount;
							bmTotalOutStandingAmt = billMasterCommonService.getFullPayableOutstanding(billMasData, organisation,null);
						}
						if ((paidAmount >= bmTotalOutStandingAmt)) {
							Long iterator = 1l;
							double checkAmt = 0d;
							for (BillDisplayDto rebateDto : rebateDtoList) {
								BillReceiptPostingDTO rebateTax = new BillReceiptPostingDTO();
								// details.put(rebateDto.getTaxId(), rebateDto.getTotalTaxAmt().doubleValue());

								billDetails.put(rebateDto.getTaxId(), null);
								rebateTax.setBillMasId(last.getBmIdno());
								double correctTax = 0;
								if(Utility.isEnvPrefixAvailable(organisation, "SKDCL")) {
									correctTax = rebateDto.getTotalTaxAmt().doubleValue();
								}else {
									correctTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
								}
								double roundedTax = Math.round(rebateDto.getTotalTaxAmt().doubleValue());
								checkAmt += roundedTax;
								if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && (rebateDtoList.size() == iterator)) {
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

						}
						else if (rebate != null && StringUtils.isNotBlank(rebate.getOtherField())
								&& StringUtils.equals(rebate.getOtherField(), MainetConstants.FlagY) && last.getBmYear().equals(finYearId)) {
							Map<String, Object> checkHalfPaymentRebateAppl = propertyService.checkHalfPaymentRebateAppl(billMasData,
									org, deptId, manualReceptDate, paymentMethodType, amount.doubleValue(),receiptAmount);
							Date secondSemStartDate = propertyBillPaymentService.getSecondSemStartDate(propertyNo,
									flatNo, currDate, currentFinYearDto, organisation, deptId);
							if((boolean) checkHalfPaymentRebateAppl.get("halfPayApplicable") && Utility.compareDate(currDate,secondSemStartDate)) {
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
									if (!Utility.isEnvPrefixAvailable(organisation, "SKDCL") && (rebateDtoList.size() == iterator)) {
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
						}else {
							last.setBmToatlRebate(0.0);
						}
					}

				}

				double totalDemand = last.getBmTotalOutstanding();

				billMasData.forEach(billMas -> {
					billMas.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
				});
				if (propertyService.checkBifurcationMethodApplicable(org)) {
					result = billMasterCommonService.updateBifurcationMethodBillData(billMasData, amount.doubleValue(),
							details, billDetails, org, rebateDetails, manualReceptDate);
				}else if(Utility.isEnvPrefixAvailable(organisation, "KIF")) {
					List<BillReceiptPostingDTO> interestResult = new ArrayList<>();
					interestResult = billMasterCommonService.updateBillDataForInterest(billMasData, amount.doubleValue(), details,
							billDetails, org, rebateDetails, manualReceptDate);
						result = billMasterCommonService.updateBillData(billMasData, last.getExcessAmount(), details,
								billDetails, org, rebateDetails, manualReceptDate);
					result.addAll(interestResult);
				}
				else {
					result = billMasterCommonService.updateBillData(billMasData, amount.doubleValue(), details,
							billDetails, org, rebateDetails, manualReceptDate);
				}
				billMasData.get(billMasData.size() - 1).setBmLastRcptamt(actualPayAmt);

				if (CollectionUtils.isNotEmpty(rebateTaxList)) {
					for (BillReceiptPostingDTO rebateTax : rebateTaxList) {
						rebateTax.setRebateDetails(rebateDetails);
						result.add(rebateTax);
					}

				}
				if (!result.isEmpty()) { // for total demand in receipt master
					result.get(0).setTotalDemand(totalDemand);
				}
				if (surCharge != null && actualPayAmt > 0 && surCharge.getTotalTaxAmt().doubleValue() > 0) {
					BillReceiptPostingDTO surchargeRecDto = new BillReceiptPostingDTO();
					surchargeRecDto.setPayableAmount(surCharge.getTotalTaxAmt().doubleValue());
					surchargeRecDto.setTaxId(surCharge.getTaxId());
					surchargeRecDto.setTaxAmount(surCharge.getTotalTaxAmt().doubleValue());
					surchargeRecDto.setTaxCategory(surCharge.getTaxCategoryId());
					final String taxCode = CommonMasterUtility.getHierarchicalLookUp(surCharge.getTaxCategoryId(), org)
							.getLookUpCode();
					surchargeRecDto.setTaxCategoryCode(taxCode);
					surchargeRecDto.setTotalDetAmount(surCharge.getTotalTaxAmt().doubleValue());
					result.add(surchargeRecDto);
				}
				
				if (last.getExcessAmount() > 0) {// Excess Payment
					final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
					details.put(advanceTaxId, last.getExcessAmount());// to add advance amt into receipt det
					propertyService.setAdnavcePayDetail(result, org, last.getExcessAmount(), advanceTaxId);
				}
			} else {// Payment is pure Advance Payment(No dues are pending)
				final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
				details.put(advanceTaxId, amount);
				propertyService.setAdnavcePayDetail(result, org, amount, advanceTaxId);
			}
		} else {// Payment is pure Advance Payment(No dues are pending)
			LOGGER.info("no bills found");
			final Long advanceTaxId = billMasterCommonService.getAdvanceTaxId(orgId, null, deptId);
			details.put(advanceTaxId, amount);
			propertyService.setAdnavcePayDetail(result, org, amount, advanceTaxId);
		}
		LOGGER.info("updateBillMasterAmountPaid method executrd succesfully");
		
		
		
		String taxDesc = null;
		double totalPayable = 0d;
        double totalReceived = 0d;
        double totalPayableArrear = 0d;
        double totalPayableCurrent = 0d;
        double totalReceivedArrear = 0d;
        double totalreceivedCurrent = 0d;
        double earlyPaymentDiscount = 0d;
        Long earlyPaymentTaxId = 0L;
		ChallanReportDTO challanReport = null;
		Map<Long, ChallanReportDTO> taxdto = new HashMap<>();
		final ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		
		 LookUp receiptForSkdcl = null;
	        try {
	            receiptForSkdcl = CommonMasterUtility.getValueFromPrefixLookUp("RPF", "ENV", org);

	        } catch (Exception e) {
	            LOGGER.error("No prefix found for ENV(RPF)");
	        }
		LookUp roundOffNotReq = null;
        try {
            roundOffNotReq = CommonMasterUtility.getValueFromPrefixLookUp("RNR", "ENV", org);

        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(RNR)");
        }
		 List<String> taxesNotAppForRec = Arrays.asList(PrefixConstants.TAX_CATEGORY.REBATE,
                 PrefixConstants.TAX_CATEGORY.EXEMPTION, PrefixConstants.TAX_CATEGORY.ADVANCE);
		  for (final BillReceiptPostingDTO billDto : result) {
    		  TbTaxMas taxMaster = tbTaxMasService.findById(billDto.getTaxId(), orgId);
              LookUp earlyPaymentLookUp = CommonMasterUtility.getHierarchicalLookUp(taxMaster.getTaxCategory2(),
                      taxMaster.getOrgid());
              if (earlyPaymentLookUp != null && !StringUtils.equals(earlyPaymentLookUp.getLookUpCode(), "EPD")) {
                  if (billDto.getTaxCategoryCode() == null
                          || !taxesNotAppForRec.contains(billDto.getTaxCategoryCode())) {
                      challanReport = taxdto.get(billDto.getTaxId());
                      if (challanReport == null) {
                          challanReport = new ChallanReportDTO();
                      }
                      if (billDto.getTotalDetAmount() != null) {
                          if (billDto.getArrearAmount() != null) {
                              if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                      && StringUtils.equals(roundOffNotReq.getOtherField(),
                                              MainetConstants.FlagY)) {
                                  challanReport.setAmountPayableArrear(
                                          challanReport.getAmountPayableArrear() + billDto.getTotalDetAmount());
                              } else {
                                  challanReport.setAmountPayableArrear(Math.round(
                                          challanReport.getAmountPayableArrear() + billDto.getTotalDetAmount()));
                              }
                              totalPayableArrear += billDto.getTotalDetAmount();
                              challanReport.setAmountPayablArrearCurr(
                                      challanReport.getAmountPayablArrearCurr() + billDto.getTotalDetAmount());
                              challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                          } else {
                              if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                      && StringUtils.equals(roundOffNotReq.getOtherField(),
                                              MainetConstants.FlagY)) {
                                  challanReport.setAmountPayableCurrent(
                                          challanReport.getAmountPayableCurrent() + billDto.getTotalDetAmount());
                              } else {
                                  challanReport.setAmountPayableCurrent(Math.round(
                                          challanReport.getAmountPayableCurrent() + billDto.getTotalDetAmount()));
                              }
                              totalPayableCurrent += billDto.getTotalDetAmount();
                              challanReport.setAmountPayablArrearCurr(
                                      challanReport.getAmountPayablArrearCurr() + billDto.getTotalDetAmount());
                          }
                          totalPayable += billDto.getTotalDetAmount();
                          challanReport.setAmountPayable(
                                  challanReport.getAmountPayable() + billDto.getTotalDetAmount());
                          challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                      } else {
                          challanReport.setAmountPayable(0d);
                      }
                      if (billDto.getArrearAmount() != null) {
                          if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                  && StringUtils.equals(roundOffNotReq.getOtherField(), MainetConstants.FlagY)) {
                              challanReport.setAmountReceivedArrear(
                                      challanReport.getAmountReceivedArrear() + billDto.getTaxAmount());
                          } else {
                              challanReport.setAmountReceivedArrear(Math
                                      .round(challanReport.getAmountReceivedArrear() + billDto.getTaxAmount()));
                          }

                          totalReceivedArrear += billDto.getTaxAmount();
                          challanReport.setAmountReceivedArrearCurr(
                                  challanReport.getAmountReceivedArrearCurr() + billDto.getTaxAmount());
                          challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                      } else {
                          if (roundOffNotReq != null && StringUtils.isNotBlank(roundOffNotReq.getOtherField())
                                  && StringUtils.equals(roundOffNotReq.getOtherField(), MainetConstants.FlagY)) {
                              challanReport.setAmountReceivedCurrent(
                                      challanReport.getAmountReceivedCurrent() + billDto.getTaxAmount());
                          } else {
                              challanReport.setAmountReceivedCurrent(Math
                                      .round(challanReport.getAmountReceivedCurrent() + billDto.getTaxAmount()));
                          }
                          totalreceivedCurrent += billDto.getTaxAmount();
                          challanReport.setAmountReceivedArrearCurr(
                                  challanReport.getAmountReceivedArrearCurr() + billDto.getTaxAmount());
                          challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                      }
                      challanReport.setAmountReceived(challanReport.getAmountReceived() + billDto.getTaxAmount());
                      totalReceived += billDto.getTaxAmount();
                      taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(billDto.getTaxId(),
                              orgId);
                      challanReport.setDetails(taxDesc);
                      taxdto.put(billDto.getTaxId(), challanReport);
                  }
                  if (billDto.getTaxCategoryCode() != null
                          && billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.ADVANCE)) {
                      if (billDto.getTaxAmount() > 1) {
                          printDTO.setAdvOrExcessAmt(
                                  Math.round(printDTO.getAdvOrExcessAmt() + billDto.getTaxAmount()));
                          if (receiptForSkdcl != null
                                  && StringUtils.equals(MainetConstants.FlagY, receiptForSkdcl.getOtherField())) {
                              challanReport = new ChallanReportDTO();
                              taxDesc = tbTaxMasService.findTaxDescByTaxIdAndOrgId(billDto.getTaxId(),
                                      orgId);
                              challanReport.setDetails(taxDesc);
                              challanReport.setAmountPayablArrearCurr(0);
                              challanReport.setAmountReceivedArrearCurr(billDto.getTaxAmount());
                              challanReport.setDisplaySeq(taxMaster.getTaxDisplaySeq());
                              taxdto.put(billDto.getTaxId(), challanReport);
                          }
                      }
                  }
                  if (billDto.getTaxCategoryCode() != null
                          && (billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.REBATE)
                                  || billDto.getTaxCategoryCode().equals(PrefixConstants.TAX_CATEGORY.EXEMPTION))
                          && billDto.getTotalDetAmount() != null) {
                      printDTO.setRebateAmount(printDTO.getRebateAmount() + billDto.getTotalDetAmount());
                  }
              }
              if (earlyPaymentLookUp != null && StringUtils.equals(earlyPaymentLookUp.getLookUpCode(), "EPD")) {
                  earlyPaymentDiscount = earlyPaymentDiscount + billDto.getTaxAmount();
                  earlyPaymentTaxId = billDto.getTaxId();
              }
    	}
		  
	        printDTO.setTotalAmountPayable(Math.round(totalPayable));
	        printDTO.setTotalReceivedAmount(Math.round(totalReceived));
	        printDTO.setTotalPayableArrear(Math.round(totalPayableArrear));
	        printDTO.setTotalPayableCurrent(Math.round(totalPayableCurrent));
	        printDTO.setTotalReceivedArrear(Math.round(totalReceivedArrear));
	        printDTO.setTotalreceivedCurrent(Math.round(totalreceivedCurrent));
	        printDTO.setEarlyPaymentDiscount(Math.round(earlyPaymentDiscount));
	        printDTO.setAmountPayable(printDTO.getTotalAmountPayable() + printDTO.getRebateAmount());
	        Date date = new Date();
	        
	        Timestamp ts = new Timestamp(date.getTime());
	        long timeStamp = ts.getTime();
	        printDTO.setReferenceNo(String.valueOf(timeStamp));
	         double amountPayable = 0;
	         double amountPayableArrear = 0;
	         double amountPayableCurrent = 0;
	         double amountReceived = 0;
	         double amountReceivedArrear = 0;
	         double amountReceivedCurrent = 0;
	         double amountReceivedArrearCurr = 0;
	         double amountPayablArrearCurr = 0;
	        List<ChallanReportDTO> collect = taxdto.values().stream().collect(Collectors.toList());
	        List<ChallanReportDTO> finalList = new ArrayList<ChallanReportDTO>();
	        ChallanReportDTO interest = new ChallanReportDTO();
	        for (ChallanReportDTO string : collect) {
	        	if(string.getDetails().contains("Interest")) {
	        		interest.setDetails("Interset");
	        		amountPayable = amountPayable + string.getAmountPayable();
	        		amountPayableArrear = amountPayableArrear + string.getAmountPayableArrear();
	        		amountPayableCurrent = amountPayableCurrent + string.getAmountPayableCurrent();
	        		amountReceived = amountReceived + string.getAmountReceived();
	        		amountReceivedArrear = amountReceivedArrear + string.getAmountReceivedArrear();
	        		amountReceivedCurrent = amountReceivedCurrent + string.getAmountReceivedCurrent();
	        		amountReceivedArrearCurr = amountReceivedArrearCurr + string.getAmountReceivedArrearCurr();
	        		amountPayablArrearCurr = amountPayablArrearCurr + string.getAmountPayablArrearCurr();
	        	}else {
	        		finalList.add(string);
	        	}
				
			}
	        interest.setDetails("Interset");
	        interest.setAmountPayable(amountPayable);
	        interest.setAmountPayableArrear(amountPayableArrear);
	        interest.setAmountPayableCurrent(amountPayableCurrent);
	        interest.setAmountReceived(amountReceived);
	        interest.setAmountReceivedArrear(amountReceivedArrear);
	        interest.setAmountReceivedCurrent(amountReceivedCurrent);
	        interest.setAmountReceivedArrearCurr(amountReceivedArrearCurr);
	        interest.setAmountPayablArrearCurr(amountPayablArrearCurr);
	        finalList.add(interest);
	        printDTO.getPaymentList().addAll(finalList);
	        
		return printDTO;
    }
    private void claculateDemandNoticeGenCharges(List<TbBillMas> billMasData, Organisation org, Long deptId,Long userId) {
    	propertyBRMSService.fetchAllChargesApplAtReceipt(billMasData.get(0).getPropNo(), billMasData, org, deptId, MainetConstants.FlagY,userId,null);
    }
    
    @Transactional(readOnly = true)
   	@Override
       @Consumes("application/json")
       @POST
       @ApiOperation(value = "getBillPaidStatus", notes = "getBillPaidStatus", response = Object.class)
       @Path("/getBillPaidStatus")
    @Produces(MediaType.APPLICATION_JSON)
   	public Map<String, Double> getBillPaidStatusOfProperty(@RequestBody PropertyInputDto inputDto) {
   		Map<String, Double> response = new HashMap<String, Double>();
   		if(StringUtils.isBlank(inputDto.getPropertyNo())) {
   			String propNo = assesmentMstRepository.getPropNoByOldPropNo(inputDto.getOldPropertyNo(), inputDto.getOrgId());
   			inputDto.setPropertyNo(propNo);
   		}
   		int count = iAssessmentMastDao.getCountWhetherMaxBmIdExistInMainBill(inputDto.getPropertyNo(), inputDto.getOrgId());
   		List<TbBillMas> billMasData = null;
   		if (count > 0) {
   			// Form Main Bill table
   			billMasData = propertyMainBillService.fetchNotPaidBillForAssessment(inputDto.getPropertyNo(), inputDto.getOrgId());
   		} else {
   			// From Provisional Bill Table
   			billMasData = iProvisionalBillService.fetchNotPaidBillForProAssessment(inputDto.getPropertyNo(), inputDto.getOrgId());
   		}
   		if(CollectionUtils.isNotEmpty(billMasData)) {
   			AtomicDouble balance = new AtomicDouble(0);
   			billMasData.get(billMasData.size()-1).getTbWtBillDet().forEach(det ->{
   				balance.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
   			});
   			response.put("UnPaid", balance.doubleValue());
   		}else {
   			BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(inputDto.getPropertyNo(), inputDto.getOrgId(),null);
   			if(advanceAmt != null && advanceAmt.getCurrentYearTaxAmt().doubleValue() > 0) {
   				response.put("Advance", advanceAmt.getCurrentYearTaxAmt().doubleValue());
   			}else {
   				response.put("Paid", 0.0);
   			}
   		}
   		return response;
   	}

	
	@Override
	@POST
	@Path("/saveAssessmentAfterObjection")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean saveAssessmentAfterObjection(ObjectionDetailsDto inputDto) {
		LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " saveAssessmentAfterObjection() method");
		ProvisionalAssesmentMstDto assesmentMstDto = new ProvisionalAssesmentMstDto();
		List<ProvisionalAssesmentDetailDto> provisionalAssesmentDetailDtoList = new ArrayList<>();
		try {
			//get data from provisional assessment history
			ProAssMstHisEntity assMstHisEntity = provisionalAssesmentMstRepository.fetchPropertyHistByPropNo(inputDto.getObjectionReferenceNumber());
			if(assMstHisEntity != null) {
				List<ProAssDetailHisEntity> assDetailHisEntityList = provisionalAssesmentMstRepository.fetchPropertyDetailHist(assMstHisEntity.getProAssId());
				List<ProvisionalAssesmentFactorDtlDto> factorDtlDtoList = new ArrayList<>();
				for(ProAssDetailHisEntity assDetailHisEntity : assDetailHisEntityList) {
					List<ProAssFactlHisEntity> assFactlHisEntityList = provisionalAssesmentMstRepository.fetchPropertyFactorDetailHist(assDetailHisEntity.getProAssdId());
					for(ProAssFactlHisEntity assFactlHisEntity : assFactlHisEntityList) {
						ProvisionalAssesmentFactorDtlDto factorDtlDto = new ProvisionalAssesmentFactorDtlDto();
						BeanUtils.copyProperties(assFactlHisEntity, factorDtlDto);
						factorDtlDtoList.add(factorDtlDto);
					}
					ProvisionalAssesmentDetailDto assesmentDetailDto = new ProvisionalAssesmentDetailDto();
					assesmentDetailDto.setProvisionalAssesmentFactorDtlDtoList(factorDtlDtoList);
					BeanUtils.copyProperties(assDetailHisEntity, assesmentDetailDto);
					provisionalAssesmentDetailDtoList.add(assesmentDetailDto);
				}
				assesmentMstDto.setProvisionalAssesmentDetailDtoList(provisionalAssesmentDetailDtoList);
				List<ProAssOwnerHisEntity> ownerHisEntityList = provisionalAssesmentMstRepository.fetchPropertyOwnerDetailHist(assMstHisEntity.getProAssId());
				List<ProvisionalAssesmentOwnerDtlDto> provisionalAssesmentOwnerDtlDtoList = new ArrayList<>(0);
				for(ProAssOwnerHisEntity ownerHisEntity : ownerHisEntityList) {
					ProvisionalAssesmentOwnerDtlDto ownerDtlDto = new ProvisionalAssesmentOwnerDtlDto();
					BeanUtils.copyProperties(ownerHisEntity, ownerDtlDto);
					provisionalAssesmentOwnerDtlDtoList.add(ownerDtlDto);
				}
				assesmentMstDto.setProvisionalAssesmentOwnerDtlDtoList(provisionalAssesmentOwnerDtlDtoList);
				BeanUtils.copyProperties(assMstHisEntity, assesmentMstDto);
			}
			List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();
			provAssMstDtoList.add(assesmentMstDto);
			
			//maintain assessment history
			ProvisionalAssesmentMstDto assesmentMastDtoHist = assesmentMastService.fetchAssessmentMasterByPropNoWithKey(assesmentMstDto.getOrgId(),
					inputDto.getObjectionReferenceNumber());
			maintainHistory(assesmentMastDtoHist, inputDto.getUserId(), inputDto.getLgIpMac());
			
			//update assessment from history
			LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " saveAndUpdateAssessmentMastAfterObjection() method");
			assesmentMastService.saveAndUpdateAssessmentMastAfterObjection(assesmentMstDto, provAssMstDtoList, assesmentMstDto.getOrgId(),
					inputDto.getUserId(), inputDto.getLgIpMac(), MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG);
			LOGGER.info("End -> " + this.getClass().getSimpleName() + " saveAndUpdateAssessmentMastAfterObjection() method");
			
			//get data from provisional bill history
			List<TbBillMas> billMasDtoList = new ArrayList<>();
			List<TbBillDet> billDetDtoList = new ArrayList<>();
			List<ProBillMstHisEntity> billMstHisEntityList = provisionalBillRepository.fetchPropertyBillHistListByProAssId(assesmentMstDto.getProAssId());
			for(ProBillMstHisEntity billMstHisEntity : billMstHisEntityList) {
				TbBillMas billMasDto = new TbBillMas();
				List<ProBillDetHisEntity> billDetailHistList = provisionalBillRepository.fetchPropertyBillDetailHist(billMstHisEntity.getBmIdno());
				for(ProBillDetHisEntity billDetHisEntity : billDetailHistList) {
					TbBillDet billDetDto = new TbBillDet();
					BeanUtils.copyProperties(billDetHisEntity, billDetDto);
					billDetDtoList.add(billDetDto);
				}
				billMasDto.setTbWtBillDet(billDetDtoList);
				BeanUtils.copyProperties(billMstHisEntity, billMasDto);
				billMasDtoList.add(billMasDto);
			}
			
			//maintain bill histry
			List<TbBillMas> tbBillMasList = new ArrayList<>();
			List<TbBillDet> tbBillDetDtoList = new ArrayList<>();
			MainBillMasEntity mainBillMasEntity = null;
			for(TbBillMas billMas : billMasDtoList) {
				mainBillMasEntity = propertyMainBillRepository.fetchBillFromBmIdNo(billMas.getBmIdno());
				if(mainBillMasEntity != null) {
					TbBillMas tbBillMas = new TbBillMas();
					List<MainBillDetEntity> billDetEntityList = propertyMainBillDetRepository.getBillDetailsByBmIdno(mainBillMasEntity);
					for(MainBillDetEntity billDetEntity : billDetEntityList) {
						TbBillDet tbBillDet = new TbBillDet();
						BeanUtils.copyProperties(billDetEntity, tbBillDet);
						tbBillDet.setBmIdno(mainBillMasEntity.getBmIdno());
						tbBillDetDtoList.add(tbBillDet);
					}
					tbBillMas.setTbWtBillDet(tbBillDetDtoList);
					BeanUtils.copyProperties(mainBillMasEntity, tbBillMas);
					tbBillMasList.add(tbBillMas);
				}
			}
			LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " copyDataFromMainBillDetailToHistory() method");
			propertyMainBillService.copyDataFromMainBillDetailToHistory(tbBillMasList, MainetConstants.Transaction.Mode.ADD, inputDto.getUserId(), inputDto.getLgIpMac());
			LOGGER.info("End -> " + this.getClass().getSimpleName() + " copyDataFromMainBillDetailToHistory() method");
			
			//update main bill from history
			LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " saveAndUpdateMainBillAfterObjection() method");
			propertyMainBillService.saveAndUpdateMainBillAfterObjection(billMasDtoList, assesmentMstDto.getOrgId(), assesmentMstDto.getCreatedBy(),
					inputDto.getObjectionReferenceNumber(), inputDto.getLgIpMac());
			LOGGER.info("End -> " + this.getClass().getSimpleName() + " saveAndUpdateMainBillAfterObjection() method");
		}catch(Exception e) {
			LOGGER.error("Exception occured while saving data after objection "+ e.getMessage());
			return false;
		}
		LOGGER.info("End -> " + this.getClass().getSimpleName() + " saveAssessmentAfterObjection() method");
		return true;
	}
	
	@Transactional
	private void maintainHistory(ProvisionalAssesmentMstDto assesmentMastDto, Long empId, String lgIpMac) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " maintainHistory() method");
		AssesmentMastHistEntity mastHistEntity = new AssesmentMastHistEntity();
		mastHistEntity.setMnAssId(assesmentMastDto.getProAssId());
		assesmentMastDto.setCreatedBy(empId);
		assesmentMastDto.setCreatedDate(assesmentMastDto.getCreatedDate());
		assesmentMastDto.setLgIpMac(lgIpMac);
		try {
			auditService.createHistory(assesmentMastDto, mastHistEntity);
        } catch (Exception ex) {
            LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + assesmentMastDto, ex);
        }
			

		for (ProvisionalAssesmentDetailDto detailEntity : assesmentMastDto.getProvisionalAssesmentDetailDtoList()) {
			AssesmentDetailHistEntity detailHisEntity = new AssesmentDetailHistEntity();
			detailHisEntity.setMnAssId(assesmentMastDto.getProAssId());
			detailHisEntity.setProAssdId(detailEntity.getProAssdId());
			detailEntity.setCreatedDate(detailEntity.getCreatedDate());
			detailEntity.setCreatedBy(empId);
			detailEntity.setLgIpMac(lgIpMac);
			try {
				auditService.createHistory(detailEntity, detailHisEntity);
	        } catch (Exception ex) {
	            LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + detailEntity, ex);
	        }
			
			detailEntity.getProvisionalAssesmentFactorDtlDtoList().forEach(factor -> {
				AssesmentFactorDetailHistEntity assesmentFactorDetailHistEntity = new AssesmentFactorDetailHistEntity();
				assesmentFactorDetailHistEntity.setMnAssdId(detailEntity.getProAssdId());
				assesmentFactorDetailHistEntity.setCreatedDate(factor.getCreatedDate());
				assesmentFactorDetailHistEntity.setCreatedBy(empId);
				assesmentFactorDetailHistEntity.setLgIpMac(lgIpMac);
				try {
					auditService.createHistory(factor, assesmentFactorDetailHistEntity);
		        } catch (Exception ex) {
		            LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + factor, ex);
		        }
			});

			detailEntity.getRoomDetailsDtoList().forEach(room -> {
				AssessmentRoomDetailHistEntity assessmentRoomDetailHistEntity = new AssessmentRoomDetailHistEntity();
				assessmentRoomDetailHistEntity.setMnAssdId(detailEntity.getProAssdId());
				assessmentRoomDetailHistEntity.setCreatedDate(room.getCreatedDate());
				assessmentRoomDetailHistEntity.setCreatedBy(empId);
				try {
					auditService.createHistory(room, assessmentRoomDetailHistEntity);
		        } catch (Exception ex) {
		            LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + room, ex);
		        }
			});
		}
		for (ProvisionalAssesmentOwnerDtlDto ownerEntity : assesmentMastDto.getProvisionalAssesmentOwnerDtlDtoList()) {
			AssesmentOwnerDtlHistEntity ownerDtlHistEntity = new AssesmentOwnerDtlHistEntity();
			ownerDtlHistEntity.setMnAssId(assesmentMastDto.getProAssId());
			ownerDtlHistEntity.setProAssoId(ownerEntity.getProAssoId());
			ownerEntity.setCreatedDate(ownerEntity.getCreatedDate());
			ownerEntity.setCreatedBy(empId);
			ownerEntity.setLgIpMac(lgIpMac);
			try {
				auditService.createHistory(ownerEntity, ownerDtlHistEntity);
	        } catch (Exception ex) {
	            LOGGER.error(MainetConstants.Property.validation.HISTORY_MASG + ownerEntity, ex);
	        }
		}
		LOGGER.info("End--> " + this.getClass().getSimpleName() + " maintainHistory() method");
	}
	
	@Transactional
    @Override
    @POST
    @Path("/getFlatList/{propNo}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFlatList(@PathParam(value = "propNo") String propNo,
            @PathParam(value = "orgId") Long orgId) {
		List<String> flatList=new ArrayList<String>();
		flatList = propertyService.getFlatListByRefNo(propNo, orgId);
        
        return flatList;
    }

	@Path("/fetchBillData/{propertyNo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@Transactional
	public CommonBillResponseDto getBillOutstandingForMpos(@PathParam("propertyNo") String propertyNo) {
		CommonBillResponseDto responseDto = new CommonBillResponseDto();
		try {
			List<ProvisionalAssesmentMstDto> assesmentList = null;
			if (propertyNo != null) {
				assesmentList = assesmentMastService.getAllYearAssesment(null, propertyNo);
			} else {
				responseDto.setStatus(1);
				responseDto.setReason("Please send the required data");
				return responseDto;
			}
			if (CollectionUtils.isNotEmpty(assesmentList)) {
				ProvisionalAssesmentMstDto assesmentMstDto = assesmentList.get(assesmentList.size() - 1);
				Organisation organisation = organisationService.getOrganisationById(assesmentMstDto.getOrgId());
				Map<String, String> info = new HashMap<String, String>();
				info.put("CircleName", organisation.getONlsOrgname());
				String fullOwnerName = getFullOwnerName(assesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList(), organisation);
				info.put("CustomerName", fullOwnerName);
				responseDto.setChargeslipInfo(info);
				responseDto.setBillIdentifier(assesmentMstDto.getAssNo());
				responseDto.setHouseNo(assesmentMstDto.getTppPlotNo());
				responseDto.setZone(CommonMasterUtility
						.getHierarchicalLookUp(assesmentMstDto.getAssWard1(), organisation).getDescLangFirst());
				responseDto.setWard(CommonMasterUtility
						.getHierarchicalLookUp(assesmentMstDto.getAssWard2(), organisation).getDescLangFirst());
				responseDto.setMohalla(CommonMasterUtility
						.getHierarchicalLookUp(assesmentMstDto.getAssWard3(), organisation).getDescLangFirst());
				responseDto.setPropertyType(CommonMasterUtility
						.getHierarchicalLookUp(assesmentMstDto.getAssPropType1(), organisation).getDescLangFirst());
				AtomicDouble totalArv = new AtomicDouble(0);
				for (ProvisionalAssesmentDetailDto assesmentMstDetailDto : assesmentMstDto
						.getProvisionalAssesmentDetailDtoList()) {
					totalArv.addAndGet(assesmentMstDetailDto.getAssdRv());
					
				}
				responseDto.setArv(totalArv.doubleValue());
				for (ProvisionalAssesmentOwnerDtlDto assesmentOwnDetailDto : assesmentMstDto
						.getProvisionalAssesmentOwnerDtlDtoList()) {
					if(MainetConstants.Property.PRIMARY_OWN.equalsIgnoreCase(assesmentOwnDetailDto.getAssoOType())) {
						responseDto.setMobileNo(assesmentOwnDetailDto.getAssoMobileno());
					}
				}
				List<TbBillMas> billMasList = propertyMainBillService
						.fetchNotPaidBillForAssessment(assesmentMstDto.getAssNo(), assesmentMstDto.getOrgId());

				/*List<Long> payModeIdsList = receiptRepository.getPayModeIdsByPropertyNo(assesmentMstDto.getAssNo());
					if(CollectionUtils.isNotEmpty(payModeIdsList)) { 
						ArrayList<Long>paymodeIdsAsPerMpos = getPaymodeIdsAsPerMpos(payModeIdsList, organisation);
						responseDto.setAllowedPaymentModes(paymodeIdsAsPerMpos);
 				}*/
				ArrayList<Long> paymodeIdsAsPerMpos = new ArrayList<>();
				paymodeIdsAsPerMpos.add(Long.valueOf(1L));
				paymodeIdsAsPerMpos.add(Long.valueOf(2L));
				paymodeIdsAsPerMpos.add(Long.valueOf(10L));
				paymodeIdsAsPerMpos.add(Long.valueOf(11L));
				paymodeIdsAsPerMpos.add(Long.valueOf(12L));
				paymodeIdsAsPerMpos.add(Long.valueOf(13L));
				responseDto.setAllowedPaymentModes(paymodeIdsAsPerMpos);
				responseDto.setRebateAmount(0.0);
				responseDto.setTotalInterest(0.0);
				responseDto.setArrears(0.0);
				responseDto.setCurrentDemand(0.0);
				responseDto.setTotalDemand(0.0);
				responseDto.setTotalAmountToBePaid(0.0);
				BillPaymentDetailDto billPaymentDetailDto = propertyBillPaymentService.getBillPaymentDetail(assesmentMstDto.getAssOldpropno(),
						assesmentMstDto.getAssNo(), assesmentMstDto.getOrgId(), null, null, null, null);
				if(billPaymentDetailDto.getTotalRebate().doubleValue() > 0) {
					responseDto.setRebateAmount(billPaymentDetailDto.getTotalRebate().doubleValue()*100);
		        }
				if (CollectionUtils.isNotEmpty(billMasList)) {
					TbBillMas billMas = billMasList.get(billMasList.size() - 1);
					AtomicDouble totalPending = new AtomicDouble(0);
					billMas.getTbWtBillDet().forEach(det -> {
						totalPending.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
						final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), organisation).getLookUpCode();
						if (PrefixConstants.TAX_CATEGORY.INTERST.equals(taxCode)) {
		                	responseDto.setTotalInterest((det.getBdPrvBalArramt() + det.getBdCurBalTaxamt())*100);
		                }else {
		                	responseDto.setArrears(det.getBdPrvBalArramt()*100);
							responseDto.setCurrentDemand(det.getBdCurBalTaxamt()*100);
		                }

					});
					responseDto.setBillAmount(totalPending.doubleValue() * 100);
					responseDto.setTotalDemand(responseDto.getArrears() + responseDto.getCurrentDemand());
					responseDto.setTotalAmountToBePaid((responseDto.getTotalDemand() + responseDto.getTotalInterest())-responseDto.getRebateAmount());
				} else {
					responseDto.setBillAmount(0.0D);
					responseDto.setStatus(1);
					responseDto.setReason("No Dues are Pending");
					return responseDto;
				}

			} else {
				responseDto.setStatus(1);
				responseDto.setReason("Property details not found");
				return responseDto;
			}
			responseDto.setStatus(0);
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setStatus(1);
			responseDto.setReason("exception while fetching details" + e.getMessage());
		}

		return responseDto;
	}
	
	private String getFullOwnerName(List<ProvisionalAssesmentOwnerDtlDto> ownerList, Organisation organisation) {
		StringBuilder ownerFullName = new StringBuilder();
		int ownerSize = 1;
		for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
            if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                if (StringUtils.isEmpty(ownerFullName.toString())) {
                    ownerFullName.append(owner.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    }
                    if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
                        ownerFullName.append(MainetConstants.WHITE_SPACE);
                        ownerFullName.append(owner.getAssoGuardianName());
                    }
                } else {
                    ownerFullName.append(owner.getAssoOwnerName());
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                        LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                organisation);
                        ownerFullName.append(reltaionLookUp.getDescLangFirst());
                    } 
                    ownerFullName.append(MainetConstants.WHITE_SPACE);
                    ownerFullName.append(owner.getAssoGuardianName());
                }
                if (ownerSize < ownerList.size()) {
                    ownerFullName.append("," + " ");
                }
                ownerSize = ownerSize + 1;
            } else {
                ownerFullName.append(owner.getAssoOwnerName());
                ownerFullName.append(MainetConstants.WHITE_SPACE);
                if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                    LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                            organisation);
                    ownerFullName.append(reltaionLookUp.getDescLangFirst());
                } else {
                    ownerFullName.append("Contact person - ");
                }
                ownerFullName.append(MainetConstants.WHITE_SPACE);
                ownerFullName.append(owner.getAssoGuardianName());
                if (ownerSize < ownerList.size()) {
                    ownerFullName.append("," + " ");
                }
            }
            owner.getAssoOwnerName();
        }
		return ownerFullName.toString();
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto, Organisation organisation) {
        LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");


        //offline.setAmountToPay(Double.toString(billPayDto.getTotalPaidAmt()));
        offline.setUserId(getMPOSEmpId());
        offline.setOrgId(organisation.getOrgid());
        offline.setLgIpMac("MPOS");
        offline.setNewHouseNo(billPayDto.getNewHouseNo());
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setPropNoConnNoEstateNoL("Property No.");
        offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
        Long financeYearId = iFinancialYear.getFinanceYearId(new Date());
        FinancialYear finincialYearsById = iFinancialYear.getFinincialYearsById(financeYearId, organisation.getOrgid());
        offline.setFaYearId(String.valueOf(financeYearId));
        offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
        offline.setFinYearStartDate(finincialYearsById.getFaFromDate());
        offline.setFinYearEndDate(finincialYearsById.getFaToDate());
        offline.setParentPropNo(billPayDto.getParentPropNo());
        // offline.setEmailId(billPayDto.get);
        offline.setApplicantName(billPayDto.getOwnerFullName());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        offline.setPayeeName(billPayDto.getOwnerFullName());
        offline.setApplNo(billPayDto.getApplNo());
        offline.setApplicantAddress(billPayDto.getAddress());
        offline.setUniquePrimaryId(billPayDto.getPropNo());
        if(org.apache.commons.lang.StringUtils.isNotBlank(billPayDto.getParentPropNo())) {
        	offline.setUniquePrimaryId(billPayDto.getParentPropNo());
        }
        offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
            try {
                 ServiceMaster service = serviceMaster.getServiceMaster(billPayDto.getServiceId(), organisation.getOrgid());
                if (service != null) {
                    offline.setServiceId(service.getSmServiceId());
                }
            } catch (Exception exception) {
                LOGGER.error("No service available for service short code PBP");
            }
        
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setPlotNo(billPayDto.getPlotNo());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), organisation)
                .getLookUpCode());
        offline.setManualReceiptNo(null);
        offline.setManualReeiptDate(null);
        offline.setUsageType(billPayDto.getUsageType1());
        if (billPayDto.getWard1() != null) {
            offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
        }
        if (billPayDto.getWard2() != null) {
            offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
        }
        if (billPayDto.getWard3() != null) {
            offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
        }
        if (billPayDto.getWard4() != null) {
            offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
        }
        if (billPayDto.getWard5() != null) {
            offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
        }
        // offline.setBillDetails(billPayDto.getBillDetails());
        offline.setReferenceNo(billPayDto.getOldpropno());
        offline.setPdRv(billPayDto.getPdRv());
        offline.setParentPropNo(billPayDto.getParentPropNo());

        //setting parshad ward zone details
        if ((billPayDto.getAssmtDto().getAssParshadWard1() != null) && (billPayDto.getAssmtDto().getAssParshadWard1() > 0)) {
        	offline.setParshadWard1(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard1(), organisation)
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard2() != null) && (billPayDto.getAssmtDto().getAssParshadWard2() > 0)) {
        	offline.setParshadWard2(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard2(), organisation)
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard3() != null) && (billPayDto.getAssmtDto().getAssParshadWard3() > 0)) {
        	offline.setParshadWard3(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard3(), organisation)
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard4() != null) && (billPayDto.getAssmtDto().getAssParshadWard4() > 0)) {
        	offline.setParshadWard4(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard4(), organisation)
                    .getLookUpDesc());
        }
        if ((billPayDto.getAssmtDto().getAssParshadWard5() != null) && (billPayDto.getAssmtDto().getAssParshadWard5() > 0)) {
        	offline.setParshadWard5(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard5(), organisation)
                    .getLookUpDesc());
        }
        
        
        if (billPayDto.getAssmtDto() != null) {
            offline.setUniquePropertyId(billPayDto.getAssmtDto().getUniquePropertyId());
        }
        
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = iChallanService
                    .InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master
                    .getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
            // setSuccessMessage(getAppSession().getMessage("prop.save.self") + asseMstDto.getApmApplicationId());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
            		"");
            // Defect#114092 - After submitting the form sequence of Tax Description is
            // getting changed on generated Receipt
            if (printDto.getPaymentList() != null && !printDto.getPaymentList().isEmpty()) {
            	//#149921
            	if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)){
            		printDto.getPaymentList().sort(Comparator.comparing(
    						ChallanReportDTO::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
            	}else {
            		printDto.getPaymentList().sort(Comparator.comparing(ChallanReportDTO::getDetails));
            	}
            	
            }


        }
        LOGGER.info("End--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");
    }

   
   
	@POST
	@Path("/updateBillDataForMPOS")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	@Override
	public Map<String, String> updateBillDataForMPOS(@RequestBody PaymentDetailForMPOSDto mposDto) {
		Map<String, String> response = new HashMap<String, String>();
		try {
			Organisation organisation = new Organisation();
			BillPaymentDetailDto billPaymentDetail = propertyBillPaymentService.getBillPaymentDetail(null,
					mposDto.getBillIdentifier(), null, null, null, null, null);
			organisation.setOrgid(billPaymentDetail.getOrgId());
			CommonChallanDTO offline = new CommonChallanDTO();
			double payableAmount = 0.00;
			Map<Long, String> pineLabModeWithIds = getPineLabModeWithIds();
			String paymentModeValue = pineLabModeWithIds.get(mposDto.getPaymentMode());
			LookUp paymentModeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(paymentModeValue, PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, organisation);
			mposDto.setPaymentMode(paymentModeLookUp.getLookUpId());
			LookUp payModeLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(mposDto.getPaymentMode(),
					organisation);
			offline.setPosPayMode(payModeLookUp.getLookUpDesc());
			offline.setPosTxnId(String.valueOf(mposDto.getPlTransactionId()));
			if (StringUtils.equals(PrefixConstants.PaymentMode.CASH, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getCashModeInfo().getCashAmount();

			} else if (StringUtils.equals(PrefixConstants.PaymentMode.CHEQUE, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getChequeModeInfo().getChequeAmount();
				offline.setCbBankId(mposDto.getChequeModeInfo().getChequeBank());
				offline.setBmChqDDNo(mposDto.getChequeModeInfo().getChequeNumber());
				Date chequeDate = convertStringToDate(mposDto);
				offline.setBmChqDDDate(chequeDate);
				offline.setRdV2(String.valueOf(mposDto.getChequeModeInfo().getChequeMicr()));
			}else if (StringUtils.equals(PrefixConstants.PaymentMode.CARD, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getCardModeInfo().getCardAmount();
				offline.setBmChqDDNo(Long.valueOf(mposDto.getCardModeInfo().getCardAcquirerMid()));
				offline.setBmDrawOn(mposDto.getCardModeInfo().getCardMaskedNumber());
				offline.setRdV2(mposDto.getCardModeInfo().getCardTid());
				offline.setRdV3(mposDto.getCardModeInfo().getCardHolderName());
				offline.setRdV4(mposDto.getCardModeInfo().getCardApprovalCode());
				offline.setRdV5(mposDto.getCardModeInfo().getCardAcquirer());
			}else if(StringUtils.equals(PrefixConstants.PaymentMode.WALLET, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getWalletModeInfo().getWalletTxnAmount();
				offline.setBmDrawOn(mposDto.getWalletModeInfo().getWalletMaskPan());
				offline.setBmChqDDNo(Long.valueOf(mposDto.getWalletModeInfo().getWalletMid()));
				offline.setRdV2(String.valueOf(mposDto.getWalletModeInfo().getWalletTid()));
				offline.setRdV3(mposDto.getWalletModeInfo().getWalletCardHolderName());
				offline.setRdV4(mposDto.getWalletModeInfo().getWalletRrn());
				offline.setRdV5(mposDto.getWalletModeInfo().getWalletMobileNumber());
			}else if(StringUtils.equals(PrefixConstants.PaymentMode.UPI, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getQrBasedModeInfo().getQrAmount();
			}else if(StringUtils.equals(PrefixConstants.PaymentMode.BHARATQR, payModeLookUp.getLookUpCode())) {
				payableAmount = mposDto.getQrBasedModeInfo().getQrAmount();
				offline.setRdV2(String.valueOf(mposDto.getQrBasedModeInfo().getQrTranactionId()));
				offline.setRdV3(mposDto.getQrBasedModeInfo().getQrHost());
				
			}

			if (payableAmount <= 0) {
				response.put("status", "1");
				response.put("reason", "amount should be greater than '0'");
				return response;
			}
			offline.setAmountToPay(String.valueOf(payableAmount/100));
			offline.setOnlineOfflineCheck("P");
			offline.setOfflinePaymentText("PCU");
			offline.setPayModeIn(mposDto.getPaymentMode());
			Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			setChallanDToandSaveChallanData(offline, details, billDetails, billPaymentDetail, organisation);
			if (StringUtils.isNotBlank(mposDto.getMobileNo())) {
				int updatedMobileNoCount = mainAssessmentOwnerRepository.updateMobileForMPOS(mposDto.getMobileNo(),
						billPaymentDetail.getAssmtDto().getProAssId(), MainetConstants.Property.PRIMARY_OWN);
			}
			response.put("status", "0");
			response.put("reason", null);
		} catch (Exception e) {
			response.put("status", "1");
			response.put("reason", e.getMessage());
		}
		return response;
	}

	private Date convertStringToDate(PaymentDetailForMPOSDto mposDto) throws ParseException {
		Date chequeDate = null;
		StringBuilder dateValue = new StringBuilder();
		char[] charArray = mposDto.getChequeModeInfo().getChequeDate().toCharArray();
		dateValue.append(charArray[0]);
		dateValue.append(charArray[1]);
		dateValue.append("/");
		dateValue.append(charArray[2]);
		dateValue.append(charArray[3]);
		dateValue.append("/");
		dateValue.append(charArray[4]);
		dateValue.append(charArray[5]);
		dateValue.append(charArray[6]);
		dateValue.append(charArray[7]);
		String dateString = dateValue.toString();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		chequeDate = formatter.parse(dateString);
		return chequeDate;
	}

	@POST
	@Path("/reversePaymentDetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@Transactional
	public Map<String, String> reversePaymentForMPOS(@RequestBody ReversalPaymentForMPOSDto mposDto) {
		Map<String, String> response = new HashMap<String, String>();
		TrasactionReversalDTO trasactionReversalDTO = new TrasactionReversalDTO();
		try {
			Long orgId = assesmentMstRepository.getOrgIdByPropertyNo(mposDto.getBillIdentifier());
			Date transactionDate  = null;
			try {
				if (StringUtils.isNotBlank(mposDto.getReversalTime())) {
					String dateInString = mposDto.getReversalTime();
					String[] split = dateInString.split("-");
					char[] charArray = split[2].toCharArray();
					StringBuilder dateValue = new StringBuilder();
					StringBuilder date = new StringBuilder();
					dateValue.append(charArray[0]);
					dateValue.append(charArray[1]);
					String dateString = dateValue.toString();
					
					date.append(dateString);
					date.append("/");
					date.append(split[1]);
					date.append("/");
					date.append(split[0]);
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					transactionDate = formatter.parse(date.toString());
				}else {
					response.put("status", "1");
					response.put("reason","Transaction date cannot be null");
					return response;
				}
			} catch (Exception e) {
				response.put("status", "1");
				response.put("reason","Exception while converting string to date format" + e.getMessage());
				LOGGER.error("No Prefix found for ENV : ARBD");
				return response;
			}
			trasactionReversalDTO.setTransactionDate(transactionDate);
			//trasactionReversalDTO.setTransactionNo(mposDto.getPlTransactionId());
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
			List<TbServiceReceiptMasBean> receiptList = commonReversalEntry.getReceiptByDeptAndDateAndTransactionId(trasactionReversalDTO, orgId, deptId,String.valueOf(mposDto.getPlTransactionId()));
			if(CollectionUtils.isNotEmpty(receiptList)) {
				Long mposEmpId = getMPOSEmpId();
				reverseBill(receiptList.get(0), orgId, mposEmpId, "MPOS",deptId);
				response.put("status", "0");
				response.put("reason", null);
			}else {
				response.put("status", "1");
				response.put("reason", "No receipt found for provided input");
			}
		} catch (Exception e) {
			response.put("status", "1");
			response.put("reason", e.getMessage());
		}
		return response;
	}
	
	@Transactional
	private void reverseBill(TbServiceReceiptMasBean receiptMasBean,Long orgid,Long empId,String ipAddress,Long deptId) {
		BillPaymentService dynamicServiceInstance = null;
        String serviceClassName = null;
    
        final String deptCode = departmentService.getDeptCode(deptId);
        serviceClassName =messageSource.getMessage(
                MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
                StringUtils.EMPTY, Locale.ENGLISH);
        dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                BillPaymentService.class);
        
     VoucherPostDTO receiptReversalDTO = dynamicServiceInstance.reverseBill(receiptMasBean,orgid,empId,ipAddress);
     
     List<TbServiceReceiptMasBean> ReceiptMasBeandtoList = new ArrayList<TbServiceReceiptMasBean>();
     List<TbServiceReceiptMasBean> rebateReceiptList = iReceiptEntryService.findReceiptByReceiptDateType(receiptMasBean.getRmRcptid(), orgid, receiptMasBean.getRmDate(), receiptMasBean.getDpDeptId(), "RB");
     ReceiptMasBeandtoList.add(receiptMasBean);
     ReceiptMasBeandtoList.addAll(rebateReceiptList);
     ReceiptMasBeandtoList.forEach(ReceiptMasBeandto ->{
    	 iCommonReversalEntry.updateReceipt(ReceiptMasBeandto, orgid,empId);
     });
     
	}
	
	private Long getMPOSEmpId() {
		Long empId=0L;
		
		List<Employee> allMPOSEmployee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeDAO.class).getAllMPOSEmployee("MPOS", "MACHINE", "MPOS");
		if(CollectionUtils.isNotEmpty(allMPOSEmployee)) {
			empId = allMPOSEmployee.get(0).getEmpId();
		}
		return empId;
	}
	
	private Map<Long, String> getPineLabModeWithIds(){
		Map<Long, String> modeIdWithValueForPineLab = new HashMap<Long, String>();
		modeIdWithValueForPineLab.put(1L, PrefixConstants.PaymentMode.CASH);
		modeIdWithValueForPineLab.put(2L, PrefixConstants.PaymentMode.CARD);
		modeIdWithValueForPineLab.put(10L, PrefixConstants.PaymentMode.CHEQUE);
		modeIdWithValueForPineLab.put(11L, PrefixConstants.PaymentMode.WALLET);
		modeIdWithValueForPineLab.put(12L, PrefixConstants.PaymentMode.UPI);
		modeIdWithValueForPineLab.put(13L, PrefixConstants.PaymentMode.BHARATQR);
		return modeIdWithValueForPineLab;
	}
	
	@Override
    @POST
    @Path("/getPropertyDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public CommonChallanDTO getPropertyDetails(@RequestBody CommonChallanDTO request) throws Exception {
        Organisation organisation = new Organisation();
        organisation.setOrgid(request.getOrgId()); 
    	//PayTM fetch Property Block
        if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_ASCL)) {
        	return null;
        }else {
        	CommonChallanDTO offline = new CommonChallanDTO();
        	 BillPaymentDetailDto billPayDto = propertyBillPaymentService.getBillPaymentDetail(null,
                    request.getPropNoConnNoEstateNoV(), request.getOrgId(), null, null, null, null);
        	 offline.setOrgId(request.getOrgId());
             offline.setPropNoConnNoEstateNoV(request.getPropNoConnNoEstateNoV());
             offline.setUniquePrimaryId(request.getPropNoConnNoEstateNoV());
             offline.setPropNoConnNoEstateNoL(ApplicationSession.getInstance().getMessage("propertydetails.PropertyNo."));
        	 offline.setPdRv(billPayDto.getPdRv());
        	 offline.setApplicantAddress(billPayDto.getAddress());
        	 offline.setNewHouseNo(billPayDto.getNewHouseNo());
        	 offline.setUsageType(billPayDto.getUsageType1());
        	 offline.setPlotNo(billPayDto.getPlotNo());
        	 offline.setApplicantName(billPayDto.getOwnerFullName());
             offline.setApplicantFullName(billPayDto.getOwnerFullName());
             offline.setPayeeName(billPayDto.getOwnerFullName());
             offline.setFaYearId(iFinancialYearService.getFinanceYearId(new Date()).toString());
             if (billPayDto.getWard1() != null) {
                 offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
             }
             if (billPayDto.getWard2() != null) {
                 offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
             }
             if (billPayDto.getWard3() != null) {
                 offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
             }
             if (billPayDto.getWard4() != null) {
                 offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
             }
             if (billPayDto.getWard5() != null) {
                 offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
             }
             if ((billPayDto.getAssmtDto().getAssParshadWard1() != null) && (billPayDto.getAssmtDto().getAssParshadWard1() > 0)) {
             	offline.setParshadWard1(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard1(), organisation)
                         .getLookUpDesc());
             }
             if ((billPayDto.getAssmtDto().getAssParshadWard2() != null) && (billPayDto.getAssmtDto().getAssParshadWard2() > 0)) {
             	offline.setParshadWard2(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard2(), organisation)
                         .getLookUpDesc());
             }
             if ((billPayDto.getAssmtDto().getAssParshadWard3() != null) && (billPayDto.getAssmtDto().getAssParshadWard3() > 0)) {
             	offline.setParshadWard3(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard3(), organisation)
                         .getLookUpDesc());
             }
             if ((billPayDto.getAssmtDto().getAssParshadWard4() != null) && (billPayDto.getAssmtDto().getAssParshadWard4() > 0)) {
             	offline.setParshadWard4(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard4(), organisation)
                         .getLookUpDesc());
             }
             if ((billPayDto.getAssmtDto().getAssParshadWard5() != null) && (billPayDto.getAssmtDto().getAssParshadWard5() > 0)) {
             	offline.setParshadWard5(CommonMasterUtility.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard5(), organisation)
                         .getLookUpDesc());
             }
             
             
            return offline;
        }
     }

	
	@Override
	public List<PropertyDetailResponseDTO> getPropertyDeatilForMpos(@RequestBody PropertyDetailRequestDTO searchDTO) {
		ProperySearchDto properySearchDto = new ProperySearchDto();
		List<PropertyDetailResponseDTO> responseListDTO = new ArrayList<>();
		PropertyDetailResponseDTO responseDTO;
		Organisation organisation = organisationService.getActiveOrgByUlbShortCode(MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ);
		if (StringUtils.isNotBlank(searchDTO.getMobileNo())) {
			properySearchDto.setOrgId(organisation.getOrgid());
			properySearchDto.setMobileno(searchDTO.getMobileNo());
		} else {
			LookUp zone = CommonMasterUtility.getHieLookupByLookupCode(searchDTO.getZone(),
					MainetConstants.Property.propPref.WZB,MainetConstants.NUMBERS.ONE, organisation.getOrgid());
			LookUp ward = CommonMasterUtility.getHieLookupByLookupCode(searchDTO.getWard(),
					MainetConstants.Property.propPref.WZB,MainetConstants.NUMBERS.TWO, organisation.getOrgid());
			LookUp mohalla = CommonMasterUtility.getHieLookupByLookupCode(searchDTO.getMohalla(),
					MainetConstants.Property.propPref.WZB,MainetConstants.NUMBERS.THREE, organisation.getOrgid());
			if(zone!=null && ward!=null && mohalla!=null ) {
				properySearchDto.setOrgId(organisation.getOrgid());
				properySearchDto.setAssWard1(zone.getLookUpId());
				properySearchDto.setAssWard2(ward.getLookUpId());
				properySearchDto.setAssWard3(mohalla.getLookUpId());
				properySearchDto.setHouseNo(searchDTO.getHouseNo());
			}
		}
		List<ProperySearchDto> results = viewPropertyDetailsService.searchPropertyDetailsForAll(properySearchDto, null,
				null, null, null);
		for (ProperySearchDto dto : results) {
			responseDTO = new PropertyDetailResponseDTO();
			responseDTO.setPropertyNo(dto.getProertyNo());
			responseDTO.setOldPropertyNo(dto.getOldPid());
			responseDTO.setMobileNo(dto.getMobileno());
			responseDTO.setOwnerName(dto.getOwnerName());
			responseDTO.setGuardianName(dto.getGuardianName());
			responseDTO.setZone(CommonMasterUtility.getHierarchicalLookUp(dto.getAssWard1(), organisation).getDescLangFirst());
			responseDTO.setWard(CommonMasterUtility.getHierarchicalLookUp(dto.getAssWard2(), organisation).getDescLangFirst());
			responseDTO.setMohalla(CommonMasterUtility.getHierarchicalLookUp(dto.getAssWard3(), organisation).getDescLangFirst());
			responseDTO.setAddress(dto.getAddress());
			responseDTO.setHouseNo(dto.getHouseNo());
			responseListDTO.add(responseDTO);
		}
		return responseListDTO;
	}
	
}
