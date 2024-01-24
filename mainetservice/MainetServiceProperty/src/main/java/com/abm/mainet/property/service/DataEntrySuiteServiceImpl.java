/**
* 
*/
package com.abm.mainet.property.service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TbTaxMasJpaRepository;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.domain.TbAsTryEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetailsByRecordcode;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetailsNew;
import com.abm.mainet.property.dto.ArrayOfNajoolPlotDetailsByRecordcode;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;
import com.abm.mainet.property.repository.MainAssessmentOwnerRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.TbAsTryRepository;
import com.abm.mainet.property.utility.PropertyRestClient;

import io.swagger.annotations.Api;

/**
 * @author kemburu.jagadeesh
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.DataEntrySuiteService")
@Api(value = "/dataEntrySuite")
@Path("/dataEntrySuite")
public class DataEntrySuiteServiceImpl implements DataEntrySuiteService {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.property.service.DataEntrySuiteService#saveDataEntryForm()
     */

    @Autowired
    private PrimaryPropertyService primaryPropertyService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    AssesmentMastService assesmentMastService;

    @Autowired
    PropertyMainBillService propertyMainBillService;

    @Autowired
    IFinancialYearService iFinancialYearService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Resource
    private TbTaxMasJpaRepository tbTaxMasJpaRepository;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private TbAsTryRepository tbAsTryRepository;

    @Resource
    private ILocationMasService iLocationMasService;
    @Autowired
    private IAssessmentMastDao assessmentMastDao;

    @Autowired
    private MainAssessmentOwnerRepository assessmentOwnerRepository;
    
    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @SuppressWarnings("unlikely-arg-type")
	@Override
    @Transactional
    public void saveDataEntryForm(List<TbBillMas> billMasList, ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            Long lastFinId, Long empId, Long processId) {
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>(0);
        Organisation org = new Organisation(provisionalAssesmentMstDto.getOrgId());
        List<TbBillMas> newBillMasList = null;
        if (billMasList != null && !billMasList.isEmpty()) {
            newBillMasList = billMasterCommonService.generateBillForDataEntry(billMasList, org);
        }
        if (processId == null || CommonMasterUtility
                .getNonHierarchicalLookUpObject(processId, org)
                .getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
        	
        	//97207 - In case of individual billing multiple entries inside master table in combination with flat no
        	if(provisionalAssesmentMstDto.getBillMethod()!=null && CommonMasterUtility
                    .getNonHierarchicalLookUpObject(provisionalAssesmentMstDto.getBillMethod(), org)
                    .getLookUpCode().equals(MainetConstants.FlagI)) {
        		assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDESIndividualBill(provisionalAssesmentMstDto,
                        provisionalAssesmentMstDto.getOrgId(), empId,
                        provisionalAssesmentMstDto.getLgIpMac());
        		
				primaryPropertyService.savePropertyMasterForIndividualBill(provisionalAssesmentMstDto,
						provisionalAssesmentMstDto.getOrgId(), empId);
				
        	}else {
        		
					if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.UPI)) {
						provisionalAssesmentMstDto.setUniquePropertyId(
							selfAssessmentService.uniquePropertyId(provisionalAssesmentMstDto, org));
					}
        			assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDES(provisionalAssesmentMstDto,
                    provisionalAssesmentMstDto.getOrgId(), empId,
                    provisionalAssesmentMstDto.getLgIpMac());
        			
        			primaryPropertyService.savePropertyMaster(provisionalAssesmentMstDto,
                            provisionalAssesmentMstDto.getOrgId(),
                            empId);
        	}
        	
            if (newBillMasList != null && !newBillMasList.isEmpty()) {
                propertyMainBillService.SaveAndUpdateMainBillOnlyForDES(newBillMasList, provisionalAssesmentMstDto.getOrgId(),
                        empId,
                        provisionalAssesmentMstDto.getAssNo(),
                        provisionalAssesmentMstDto.getLgIpMac());
            }
            

        } else {
            // in work flow case
            iProvisionalAssesmentMstService.saveProvisionalAssessmentForDataEntry(
                    provisionalAssesmentMstDto, provisionalAssesmentMstDto.getOrgId(),
                    empId,
                    lastFinId, null);

            if (newBillMasList != null && !newBillMasList.isEmpty()) {
                iProvisionalBillService.saveAndUpdateProvisionalBill(newBillMasList, provisionalAssesmentMstDto.getOrgId(),
                        empId, provisionalAssesmentMstDto.getAssNo(), null, provBillList,
                        provisionalAssesmentMstDto.getLgIpMac());
            }

        }

    }

    @Override
    @Transactional
    public List<TbBillMas> createProvisionalBillForDataEntry(Long deptId, Long scheduleId, Long orgId, Long finYearId, String flatNo) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        FinancialYear fincialYear = iFinancialYearService.getFinincialYearsById(finYearId, orgId);
        List<BillingScheduleDetailEntity> scheduleList = billingScheduleService.getSchListFromschIdTillCurDate(
                scheduleId, orgId, fincialYear.getFaFromDate());
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, org);
        final List<TbTaxMas> taxMaster = tbTaxMasService.findAllTaxesForBillGeneration(
                org.getOrgid(), deptId,
                chargeApplicableAt.getLookUpId(), null);
        BillingScheduleDetailEntity billSchDet = billingScheduleService
                .getSchedulebySchFromDate(org.getOrgid(), new Date());
        List<TbBillMas> billMasList = new ArrayList<>();
        List<String> taxesNotAppForDES = Arrays.asList(PrefixConstants.TAX_CATEGORY.REBATE,
                PrefixConstants.TAX_CATEGORY.EXEMPTION, PrefixConstants.TAX_CATEGORY.ADVANCE);
        List<String> taxAppList = Arrays.asList(MainetConstants.ChargeApplicableAt.RECEIPT,
                MainetConstants.ChargeApplicableAt.SCRUTINY, MainetConstants.ChargeApplicableAt.APPLICATION);
        scheduleList.forEach(shedule -> {
            TbBillMas billMas = new TbBillMas();
            List<TbBillDet> billDetList = new ArrayList<>();
            taxMaster.forEach(tax -> {
                final String taxCode = CommonMasterUtility
                        .getHierarchicalLookUp(tax.getTaxCategory1(), org)
                        .getLookUpCode();
                final String taxAppAt = CommonMasterUtility
                        .getHierarchicalLookUp(tax.getTaxApplicable(), org)
                        .getLookUpCode();
                if (!taxesNotAppForDES.contains(taxCode) && !taxAppList.contains(taxAppAt)) {
                    TbBillDet billDet = new TbBillDet();
                    billDet.setTaxId(tax.getTaxId());
                    billDet.setTaxCategory(tax.getTaxCategory1());
                    billDet.setCollSeq(tax.getCollSeq());
                    billDet.setDisplaySeq(tax.getTaxDisplaySeq());
                    billDetList.add(billDet);
                    billMas.setTbWtBillDet(billDetList);
                }
            });
            Long finYear = iFinancialYearService.getFinanceYearId(shedule.getBillFromDate());
            billMas.setBmYear(finYear);
            billMas.setBmBilldt(shedule.getBillFromDate());
            billMas.setGenFlag(MainetConstants.FlagN);
            if (billSchDet.getSchDetId().equals(shedule.getSchDetId())) {
                billMas.setBmBilldt(new Date());
                billMas.setGenFlag(MainetConstants.FlagY);
            }
            billMas.setBmFromdt(shedule.getBillFromDate());
            billMas.setBmTodt(shedule.getBillToDate());
            billMas.setIntFrom(shedule.getBillFromDate());
            billMas.setIntTo(shedule.getBillToDate());
            billMas.setBmPaidFlag(MainetConstants.FlagN);
            billMas.setBmGenDes(MainetConstants.FlagY);
            Date dueDate = getDueDateForSch(shedule.getBillFromDate(), shedule.getCalFromDate(), shedule.getNoOfDays(),
                    shedule.getBillFromDate(), shedule.getBillToDate(), org.getOrgid());
            billMas.setBmDuedate(dueDate);
            if(StringUtils.isNotBlank(flatNo)) {
            	billMas.setFlatNo(flatNo);
            }
            billMasList.add(billMas);

        });
        return billMasList;
    }
    
    private StringBuilder createTaxId(TbBillDet det) {
		StringBuilder taxId = new StringBuilder();
		taxId.append("1111");
		taxId.append(det.getTaxId().toString());
		return taxId;
	}

    private Date getDueDateForSch(Date finYearStrDate, Long calFrom, Long noOfDay, Date schStartDate, Date schEndDate,
            Long orgId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        String lookCode = CommonMasterUtility.getNonHierarchicalLookUpObject(calFrom, org).getLookUpCode();
        Calendar cal = Calendar.getInstance();
        if (lookCode.equals(MainetConstants.Property.DueDatePerf.BGD)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.FYS)) {
            cal.setTime(finYearStrDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SSD)) {
            cal.setTime(schStartDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        } else if (lookCode.equals(MainetConstants.Property.DueDatePerf.SED)) {
            cal.setTime(schEndDate);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(noOfDay.toString()));
            return cal.getTime();
        }
        return new Date();
    }

    @POST
    @Path("/getScheduleListForArrEntry")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<LookUp> getScheduleListForArrEntry(PropertyInputDto propertyInputDto) {
        return setScheduleListForArrEntry(propertyInputDto.getAcquisitionDate(), propertyInputDto.getOrgId(),
                propertyInputDto.getFinYearId());
    }

    @Override
    @Transactional
    public List<LookUp> setScheduleListForArrEntry(Date acqDate, Long orgId, Long finYearId) {
        List<LookUp> list = new ArrayList<>(0);
        AtomicInteger count = new AtomicInteger(0);
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        FinancialYear fincialYear = iFinancialYearService.getFinincialYearsById(finYearId, orgId);
        List<LookUp> allSchList = selfAssessmentService.getAllBillscheduleByOrgid(org);
        List<BillingScheduleDetailEntity> scheduleList = billingScheduleService.getSchListFromGivenDateTillCurDate(
                acqDate,
                orgId, fincialYear.getFaFromDate());
        scheduleList.forEach(newSch -> {
            if (count.intValue() <= MainetConstants.NUMBERS.SIX) {
                allSchList.stream().filter(sch -> newSch.getSchDetId().equals(sch.getLookUpId()))
                        .forEach(sch -> {
                            list.add(sch);
                           // count.addAndGet(1);
                        });
            }
        });

        Collections.reverse(list);
        return list;
    }

    @Override
    @Transactional
    public List<FinancialYear> getFinYearListForDataEntry(Date acqDate, Long orgId) {
        Long finYearId = iFinancialYear.getFinanceYearId(acqDate);
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(orgId, finYearId, new Date());
        return financialYearList;
    }

    void callWorkFlow(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long serviceid, Long deptId) {
        ApplicationMetadata applicationData = new ApplicationMetadata();
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicantDetailDto.setOrgId(provisionalAssesmentMstDto.getOrgId());
        applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
        applicationData.setReferenceId(provisionalAssesmentMstDto.getAssNo());
        applicationData.setIsCheckListApplicable(
                (provisionalAssesmentMstDto.getDocs() != null && !provisionalAssesmentMstDto.getDocs().isEmpty()) ? true
                        : false);
        applicationData.setOrgId(provisionalAssesmentMstDto.getOrgId());
        applicantDetailDto.setServiceId(serviceid);
        applicantDetailDto.setDepartmentId(deptId);
        applicantDetailDto.setUserId(provisionalAssesmentMstDto.getCreatedBy());
        applicantDetailDto.setOrgId(provisionalAssesmentMstDto.getOrgId());
        applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
        applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
        applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
        applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
        applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
        commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
    }

    @POST
    @Path("/saveDataEntryAndCallWorkFlow")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public ProperySearchDto saveDataEntryAndCallWorkFlow(@RequestBody SelfAssessmentSaveDTO selfAssessmentSaveDTO) {
        ProperySearchDto propNoDto = new ProperySearchDto();
        selfAssessmentSaveDTO.getProvisionalMas().setFlag(MainetConstants.FlagD);
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                selfAssessmentSaveDTO.getProvisionalMas().getOrgId());
        if (service != null) {
            selfAssessmentSaveDTO.getProvisionalMas().setSmServiceId(service.getSmServiceId());
        }
        saveDataEntryForm(selfAssessmentSaveDTO.getBillMasList(), selfAssessmentSaveDTO.getProvisionalMas(),
                selfAssessmentSaveDTO.getFinYear(), selfAssessmentSaveDTO.getEmpId(), service.getSmProcessId());
        callWorkFlow(selfAssessmentSaveDTO.getProvisionalMas(), selfAssessmentSaveDTO.getProvisionalMas().getSmServiceId(),
                selfAssessmentSaveDTO.getDeptId());
        propNoDto.setProertyNo(selfAssessmentSaveDTO.getProvisionalMas().getAssNo());
        return propNoDto;
    }

    @POST
    @Path("/DisplayArrearBillForEntry")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public SelfAssessmentSaveDTO displayArrearBillForEntry(PropertyInputDto propertyInputDto) {
        SelfAssessmentSaveDTO dto = new SelfAssessmentSaveDTO();
        List<TbBillMas> billList = createProvisionalBillForDataEntry(propertyInputDto.getDeptId(),
                propertyInputDto.getScheduleId(), propertyInputDto.getOrgId(), propertyInputDto.getFinYearId(),null);
        List<FinancialYear> financialYearList = getFinYearListForDataEntry(propertyInputDto.getAcquisitionDate(),
                propertyInputDto.getOrgId());
        List<Long> finYearIdList = financialYearList.stream()
                .map(FinancialYear::getFaYear)
                .collect(Collectors.toList());
        dto.setBillMasList(billList);
        dto.setFinYearList(finYearIdList);
        return dto;
    }

    @POST
    @Path("/getFinYearOfDataEntryAssessment")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public Long getFinYearOfDataEntryAssessment() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return iFinancialYear.getFinanceYearId(calendar.getTime());
    }

    @POST
    @Path("/getCurrentFinYearId")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public Long getCurrentFinYearId() {
        return iFinancialYear.getFinanceYearId(new Date());
    }

    @POST
    @Path("/mapDescriptionAndFactor")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public SelfAssessmentSaveDTO mapDescriptionAndFactor(SelfAssessmentSaveDTO selfAssessmentSaveDTO) {

        Organisation org = new Organisation();
        org.setOrgid(selfAssessmentSaveDTO.getProvisionalMas().getOrgId());
        ProvisionalAssesmentMstDto assDto = selfAssessmentSaveDTO.getProvisionalMas();
        assDto.setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(assDto.getAssOwnerType(), org).getDescLangFirst());
        String ownerTypeCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(assDto.getAssOwnerType(), org).getLookUpCode();
        // this.setAssMethod(CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS).getLookUpCode());
        if (MainetConstants.Property.SO.equals(ownerTypeCode) || MainetConstants.Property.JO.equals(ownerTypeCode)) {
            for (ProvisionalAssesmentOwnerDtlDto dto : assDto.getProvisionalAssesmentOwnerDtlDtoList()) {
                dto.setProAssGenderId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getGenderId(), org).getDescLangFirst());
                dto.setProAssRelationId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getRelationId(), org).getDescLangFirst());
            }
        } else {
            ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
            ownerDto.setGenderId(null);
            ownerDto.setRelationId(null);
            ownerDto.setAssoAddharno(null);
        }
        assDto.setProAssdRoadfactorDesc(CommonMasterUtility
                .getNonHierarchicalLookUpObject(assDto.getPropLvlRoadType(), org).getDescLangFirst());

        if (assDto.getAssLandType() != null) {
            assDto.setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assDto.getAssLandType(), org).getDescLangFirst());

        }

        assDto.setAssWardDesc1(
                CommonMasterUtility.getHierarchicalLookUp(assDto.getAssWard1(), org).getDescLangFirst());

        if (assDto.getAssWard2() != null) {

            assDto.setAssWardDesc2(
                    CommonMasterUtility.getHierarchicalLookUp(assDto.getAssWard2(), org).getDescLangFirst());
        }

        if (assDto.getAssWard3() != null) {

            assDto.setAssWardDesc3(
                    CommonMasterUtility.getHierarchicalLookUp(assDto.getAssWard3(), org).getDescLangFirst());
        }

        if (assDto.getAssWard4() != null) {

            assDto.setAssWardDesc4(
                    CommonMasterUtility.getHierarchicalLookUp(assDto.getAssWard4(), org).getDescLangFirst());
        }

        if (assDto.getAssWard5() != null) {

            assDto.setAssWardDesc5(
                    CommonMasterUtility.getHierarchicalLookUp(assDto.getAssWard5(), org).getDescLangFirst());
        }
        if (assDto.getTaxCollEmp() != null) {
            Employee emp = iEmployeeService.findEmployeeByIdAndOrgId(assDto.getTaxCollEmp(),
                    selfAssessmentSaveDTO.getProvisionalMas().getOrgId());
            if (emp != null) {
                assDto
                        .setTaxCollEmpDesc(emp.getEmpname() + " " + emp.getEmplname() + "-" + emp.getDesignation().getDsgname());
            }
        }

        for (ProvisionalAssesmentDetailDto detaildto : assDto.getProvisionalAssesmentDetailDtoList()) {
            if (detaildto.getAssdBuildupArea() != null) {
                /*
                 * for (Map.Entry<Long, String> entry : this.getFinancialYearMap().entrySet()) { if
                 * (entry.getKey().toString().equals(detaildto.getFaYearId().toString())) {
                 * detaildto.setProFaYearIdDesc(entry.getValue()); }
                 */
                SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                detaildto.setProAssdConstructionDate(formatter.format(detaildto.getAssdYearConstruction()));
                detaildto.setProAssdUsagetypeDesc(
                        CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype1(), org).getDescLangFirst());

                if (detaildto.getAssdUsagetype2() != null) {
                    detaildto.setProAssdUsagetypeDesc2(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype2(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype3() != null) {
                    detaildto.setProAssdUsagetypeDesc3(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype3(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype4() != null) {
                    detaildto.setProAssdUsagetypeDesc4(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype4(), org).getDescLangFirst());
                }
                if (detaildto.getAssdUsagetype5() != null) {
                    detaildto.setProAssdUsagetypeDesc5(
                            CommonMasterUtility.getHierarchicalLookUp(detaildto.getAssdUsagetype5(), org).getDescLangFirst());
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

                detaildto.setProFloorNo(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdFloorNo(), org)
                                .getDescLangFirst());
                detaildto.setProAssdConstruTypeDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdConstruType(), org)
                                .getDescLangFirst());
                detaildto.setProAssdOccupancyTypeDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(detaildto.getAssdOccupancyType(), org)
                                .getDescLangFirst());
                /* } */
                for (ProvisionalAssesmentFactorDtlDto provisionalAssesmentFactorDtlDto : selfAssessmentSaveDTO
                        .getFactorDtlDtoList()) {
                    if (provisionalAssesmentFactorDtlDto.getAssfFactorValueId() != null) {
                        provisionalAssesmentFactorDtlDto.setProAssfFactorValueDesc(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorValueId(), org)
                                .getDescLangFirst());
                        provisionalAssesmentFactorDtlDto.setProAssfFactorIdDesc(
                                CommonMasterUtility
                                        .getNonHierarchicalLookUpObject(provisionalAssesmentFactorDtlDto.getAssfFactorId(), org)
                                        .getDescLangFirst());
                    }
                }
            }
        }

        assDto.setAssCorrAddress(assDto.getAssAddress());
        assDto.setAssCorrPincode(assDto.getAssPincode());
        assDto.setAssCorrEmail(assDto.getAssEmail());
        if (selfAssessmentSaveDTO.getFactorDtlDtoList() != null && !selfAssessmentSaveDTO.getFactorDtlDtoList().isEmpty()) {
            selfAssessmentService.setFactorMappingToAssDto(selfAssessmentSaveDTO.getFactorDtlDtoList(), assDto);
        }
        return selfAssessmentSaveDTO;
    }

    @POST
    @Path("/getTaxCollectorList/{deptId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getTaxCollectorList(@PathParam(value = "deptId") Long deptId,
            @PathParam(value = "orgId") Long orgId) {
        List<Employee> empList = iEmployeeService.findAllEmployeeByDept(orgId,
                deptId);
        List<LookUp> lookupList = new ArrayList<>();
        if (empList != null && !empList.isEmpty()) {
            empList.forEach(emp -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(emp.getEmpId());
                String empDesc = emp.getEmpname() + " " + emp.getEmplname();
                String empDescReg = emp.getEmpname() + " " + emp.getEmplname();
                if (emp.getDesignation() != null && emp.getDesignation().getDsgname() != null) {
                    empDesc = empDesc + "-" + emp.getDesignation().getDsgname();
                    empDescReg = empDescReg + "-" + emp.getDesignation().getDsgname();
                }
                lookUp.setDescLangFirst(empDesc);
                lookUp.setDescLangSecond(empDescReg);
                lookupList.add(lookUp);
            });
        }
        return lookupList;
    }

    @POST
    @Path("/getAllTaxesForBillGeneration/{deptId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getAllTaxesForBillGeneration(@PathParam(value = "deptId") Long deptId,
            @PathParam(value = "orgId") Long orgId) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, org);
        List<TbTaxMasEntity> taxList = tbTaxMasJpaRepository.findAllTaxesForBillGeneration(orgId, deptId,
                chargeApplicableAt.getLookUpId());
        List<LookUp> lookupList = new ArrayList<>();
        if (taxList != null && !taxList.isEmpty()) {
            taxList.forEach(tax -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(tax.getTaxId());
                lookUp.setDescLangFirst(tax.getTaxDesc());
                lookUp.setDescLangSecond(tax.getTaxDesc());
                lookupList.add(lookUp);
            });
        }
        return lookupList;
    }

    @POST
    @Path("/findDistrictByLandType")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<LookUp> findDistrictByLandType(LandTypeApiDetailRequestDto dto) {
        List<LookUp> districtList = new ArrayList<>();
        List<Object[]> district = tbAsTryRepository.findDistrictByLandType(dto.getLandType());

        for (final Object[] listObj : district) {
            LookUp lookup = new LookUp();
            if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null") && listObj[0] != null
                    && listObj[1] != null) {
                lookup.setDescLangFirst(listObj[0].toString());
                lookup.setDescLangSecond(listObj[0].toString());
                lookup.setLookUpId(listObj[1] == null ? null : Long.valueOf(listObj[1].toString()));
            }
            districtList.add(lookup);
        }
        return districtList;
    }

    @POST
    @Path("/getTehsilListByDistrict")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<LookUp> getTehsilListByDistrict(LandTypeApiDetailRequestDto dto) {
        List<LookUp> tehsilList = new ArrayList<>();
        List<Object[]> tehsil = tbAsTryRepository.findTehsilListByDistrict(dto.getDistrictId(), dto.getLandType());
        for (final Object[] listObj : tehsil) {
            LookUp lookup = new LookUp();
            if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null")) {
                lookup.setDescLangFirst(listObj[0].toString());
                lookup.setDescLangSecond(listObj[0].toString());
                lookup.setLookUpCode(listObj[1].toString());
            }
            tehsilList.add(lookup);
        }

        return tehsilList;

    }

    @POST
    @Path("/getVillageListByTehsil")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<LookUp> getVillageListByTehsil(LandTypeApiDetailRequestDto dto) {
        List<LookUp> villageList = new ArrayList<>();
        List<Object[]> village = tbAsTryRepository.findVillageListByTehsil(dto.getTehsilId(), dto.getDistrictId(),
                dto.getLandType());
        for (final Object[] listObj : village) {
            LookUp lookup = new LookUp();
            if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null")) {
                lookup.setDescLangFirst(listObj[0].toString());
                lookup.setDescLangSecond(listObj[0].toString());
                lookup.setLookUpCode(listObj[1].toString());
                lookup.setOtherField(listObj[2].toString());
            }
            villageList.add(lookup);
        }
        return villageList;
    }

    @POST
    @Path("/getMohallaListByVillageId")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getMohallaListByVillageId(LandTypeApiDetailRequestDto dto) {
        List<LookUp> mohallaList = new ArrayList<>();
        List<Object[]> mohalla = tbAsTryRepository.findMohallaListByVillageId(dto.getVillageId(), dto.getTehsilId(),
                dto.getDistrictId(), dto.getLandType());
        for (final Object[] listObj : mohalla) {
            LookUp lookup = new LookUp();
            if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null") && listObj[0] != null
                    && listObj[1] != null) {
                lookup.setDescLangFirst(listObj[0].toString());
                lookup.setDescLangSecond(listObj[0].toString());
                lookup.setLookUpCode(listObj[1].toString());
            }
            mohallaList.add(lookup);
        }
        return mohallaList;

    }

    @POST
    @Path("/getStreetListByMohallaId")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getStreetListByMohallaId(LandTypeApiDetailRequestDto dto) {
        List<LookUp> streetList = new ArrayList<>();
        List<Object[]> street = tbAsTryRepository.findStreetListByMohallaId(dto.getMohallaId(), dto.getVillageId(),
                dto.getTehsilId(), dto.getDistrictId(), dto.getLandType());
        for (final Object[] listObj : street) {
            LookUp lookup = new LookUp();
            if (!listObj[0].toString().equals("null") && !listObj[1].toString().equals("null") && listObj[0] != null
                    && listObj[1] != null) {
                lookup.setDescLangFirst(listObj[0].toString());
                lookup.setDescLangSecond(listObj[0].toString());
                lookup.setLookUpCode(listObj[1].toString());
            }

            streetList.add(lookup);
        }
        return streetList;

    }

    @POST
    @Path("/getKhasraDetails")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayOfKhasraDetails getKhasraDetails(LandTypeApiDetailRequestDto dto) {
        String riNo = tbAsTryRepository.findRiDetailsByDistrictTehsilVillageID(dto.getLandType(), dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId());
        TbAsTryEntity tbAsTryEntity = new TbAsTryEntity();
        tbAsTryEntity.setTryDisCode(dto.getDistrictId());
        tbAsTryEntity.setTryTehsilCode(dto.getTehsilId());
        tbAsTryEntity.setTryRiCode(riNo);
        tbAsTryEntity.setTryVillCode(dto.getVillageId());
        String key = ApplicationSession.getInstance().getMessage("property.khasraDetailsApiKey");
        String encodedKey = URLEncoder.encode(key);
        String url = ApplicationSession.getInstance().getMessage("property.khasraDetailsApiURL",
                new Object[] { tbAsTryEntity.getTryDisCode(), tbAsTryEntity.getTryTehsilCode(), tbAsTryEntity.getTryRiCode(),
                        tbAsTryEntity.getTryVillCode(), dto.getKhasraNo(),
                        ApplicationSession.getInstance().getMessage("property.khasraDetailsApiDeptId"), encodedKey });
        ArrayOfKhasraDetails response = (ArrayOfKhasraDetails) PropertyRestClient.getLandTypeDetails(ArrayOfKhasraDetails.class,
                url);
        return response;
    }

    @POST
    @Path("/getNajoolDetails")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public ArrayOfPlotDetails getNajoolDetails(LandTypeApiDetailRequestDto dto) {
        String recordCode = tbAsTryRepository.findRecordDetailsByDistrictTehsilVillageMohallaStreetID(dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId(), dto.getMohallaId(), dto.getStreetNo());
        String url = ApplicationSession.getInstance().getMessage("property.najoolDetailsApi",
                new Object[] { dto.getPlotNo(), recordCode });
        ArrayOfPlotDetails response = (ArrayOfPlotDetails) PropertyRestClient.getLandTypeDetails(ArrayOfPlotDetails.class, url);
        return response;
    }

    @POST
    @Path("/getDiversionDetails")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public ArrayOfDiversionPlotDetails getDiversionDetails(LandTypeApiDetailRequestDto dto) {
        String recordCode = tbAsTryRepository.findRecordDetailsByDistrictTehsilVillageMohallaStreetID(dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId(), dto.getMohallaId(), dto.getStreetNo());
        String url = ApplicationSession.getInstance().getMessage("property.diversionDetailsApi",
                new Object[] { dto.getPlotNo(), recordCode });
        ArrayOfDiversionPlotDetails response = (ArrayOfDiversionPlotDetails) PropertyRestClient
                .getLandTypeDetails(ArrayOfDiversionPlotDetails.class, url);
        return response;
    }

    @Override
    @Transactional
    public boolean validateDataEntrySuite(String propNo, Long orgId, Long serviceId) {

        /*
         * int countPro = provisionalAssesmentMstRepository.getCountOfAssessmentWithoutDES(propNo, orgId, serviceId); if (countPro
         * <= 0) { int countAss = assesmentMstRepository.getCountOfAssessmentWithoutDES(propNo, orgId, serviceId); if (countAss <=
         * 0) { int receiptcount = iReceiptEntryService.getCountOfReceiptByRefNo(propNo, orgId); if (receiptcount <= 0) { int
         * proBillCount = propertyMainBillService.getCountOfBillWithoutDESByPropNo(propNo, orgId); if (proBillCount <= 0) { int
         * billCount = iProvisionalBillService.getCountOfBillWithoutDESByPropNo(propNo, orgId); if (billCount <= 0) { return true;
         * } } } } }
         */
        int countPro = iProvisionalAssesmentMstService.validateProperty(propNo, orgId, serviceId);
        if (countPro <= 0) {
            return true;
        }
        return false;
    }

    @Transactional
    public void updateDataEntryForm(List<TbBillMas> billMasList, ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            Long orgId, Long empId, String ipAddress, String dataFrom) {
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<TbBillMas> newBillMasList = null;
        
        
        if (billMasList != null && !billMasList.isEmpty()) {
        Long firstBillBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(billMasList.get(0).getPropNo());
        	if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
        		billMasList.forEach(billMas ->{
            		if(billMas.getBmIdno() == firstBillBmIdNo) {
            			List<Long> taxIdList = new ArrayList<Long>();
            			billMas.getTbWtBillDet().forEach(det ->{
            				StringBuilder taxId = createTaxId(det);
                			List<TbBillDet> billDetList = billMas.getTbWtBillDet().stream().filter(billDet -> billDet.getTaxId().equals(Long.valueOf(taxId.toString()))).collect(Collectors.toList());
                			if(CollectionUtils.isNotEmpty(billDetList)) {
                				det.setBdPrvArramt(billDetList.get(0).getBdCsmp().doubleValue());
                				det.setBdPrvBalArramt(billDetList.get(0).getBdCsmp().doubleValue());
                			}
                			taxIdList.add(Long.valueOf(taxId.toString()));
            			});
                    	
                    	if(CollectionUtils.isNotEmpty(taxIdList)) {
                    		taxIdList.forEach(taxId ->{
                    			Iterator<TbBillDet> iterator1 = billMas.getTbWtBillDet().iterator();
                            	while(iterator1.hasNext()) {
                            		TbBillDet det = iterator1.next();
                            		if(det.getTaxId().equals(taxId)) {
                            			iterator1.remove();
                            		}
                            	}
                    		});
                    	}
            		}
            		
            		
            	});
        	}
            newBillMasList = billMasterCommonService.generateBillForDataEntry(billMasList, org);
        }
        if (dataFrom.equals(MainetConstants.Property.PROVISIONAL_TABLE)) {
            if (provisionalAssesmentMstDto.getAssNo() != null && !provisionalAssesmentMstDto.getAssNo().isEmpty()) {
                iProvisionalAssesmentMstService.deleteDetailInDESEdit(provisionalAssesmentMstDto,
                        provisionalAssesmentMstDto.getOrgId(),
                        empId, provisionalAssesmentMstDto.getLgIpMac());
            }
            iProvisionalAssesmentMstService.updateProvisionalAssessmentForSingleAssessment(provisionalAssesmentMstDto, orgId,
                    empId, ipAddress);
            if (billMasList != null && !billMasList.isEmpty()) {
                iProvisionalBillService.saveAndUpdateProvisionalBill(newBillMasList, orgId,
                        empId,
                        provisionalAssesmentMstDto.getAssNo(), null, null, ipAddress);
            }
        } else if (dataFrom.equals(MainetConstants.Property.MAIN_TABLE)) {
			if (provisionalAssesmentMstDto.getAssNo() != null && !provisionalAssesmentMstDto.getAssNo().isEmpty()) {

				assesmentMastService.deleteDetailInDESEdit(provisionalAssesmentMstDto,
						provisionalAssesmentMstDto.getOrgId(), empId, provisionalAssesmentMstDto.getLgIpMac());
			}
            assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDES(provisionalAssesmentMstDto, orgId, empId,
                    ipAddress);

            if (billMasList != null && !billMasList.isEmpty()) {
                propertyMainBillService.SaveAndUpdateMainBillOnlyForDES(newBillMasList, orgId, empId,
                        provisionalAssesmentMstDto.getAssNo(),
                        ipAddress);
            }
            primaryPropertyService.savePropertyMaster(provisionalAssesmentMstDto,
                    provisionalAssesmentMstDto.getOrgId(),
                    empId);
        }
    }

    @POST
    @Path("/getKhasraNoList")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getKhasraNoList(LandTypeApiDetailRequestDto dto) {
        String riNo = tbAsTryRepository.findRiDetailsByDistrictTehsilVillageID(dto.getLandType(), dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId());
        String vsrNo = tbAsTryRepository.findVsrNoByDistrictTehsilVillageRiCode(dto.getDistrictId(), dto.getTehsilId(), riNo,
                dto.getVillageId());
        dto.setVillageCode(vsrNo);
        String key = ApplicationSession.getInstance().getMessage("property.khasraDetailsApiKey");
        String encodedKey = URLEncoder.encode(key);
        String url = ApplicationSession.getInstance().getMessage("property.khasraNoListApi",
                new Object[] { vsrNo, dto.getKhasraNo(),
                        ApplicationSession.getInstance().getMessage("property.khasraDetailsApiDeptId"), encodedKey });

        List<LookUp> khasraNoList = new ArrayList<>();

        ArrayOfKhasraDetailsNew response = (ArrayOfKhasraDetailsNew) PropertyRestClient.getLandTypeDetails(
                ArrayOfKhasraDetailsNew.class,
                url);
        if (response != null && !response.getKhasraDetailsNew().isEmpty()
                && response.getKhasraDetailsNew().get(0).getKhasra_No() != null) {
            response.getKhasraDetailsNew().forEach(obj -> {
                LookUp lookUp = new LookUp();
                lookUp.setDescLangFirst(obj.getKhasra_No());
                lookUp.setLookUpDesc(obj.getKhasra_No());
                khasraNoList.add(lookUp);

            });
        }
        return khasraNoList;
    }

    @POST
    @Path("/getNajoolPlotList")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getNajoolPlotList(LandTypeApiDetailRequestDto dto) {
        String recordCode = tbAsTryRepository.findRecordDetailsByDistrictTehsilVillageMohallaStreetID(dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId(), dto.getMohallaId(), dto.getStreetNo());
        String url = ApplicationSession.getInstance().getMessage("property.najoolPlotList",
                new Object[] { recordCode, dto.getPlotNo() });
        List<LookUp> najoolNoList = new ArrayList<>();
        ArrayOfNajoolPlotDetailsByRecordcode response = (ArrayOfNajoolPlotDetailsByRecordcode) PropertyRestClient
                .getLandTypeDetails(ArrayOfNajoolPlotDetailsByRecordcode.class, url);

        if (response != null) {
            response.getNajoolPlotDetailsByRecordcode().forEach(obj -> {
                LookUp lookUp = new LookUp();
                lookUp.setDescLangFirst(obj.getPlotno());
                lookUp.setLookUpDesc(obj.getPlotno());
                najoolNoList.add(lookUp);
            });
        }
        return najoolNoList;
    }

    @POST
    @Path("/getDiversionPlotList")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    @Transactional
    public List<LookUp> getDiversionPlotList(LandTypeApiDetailRequestDto dto) {
        String recordCode = tbAsTryRepository.findRecordDetailsByDistrictTehsilVillageMohallaStreetID(dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId(), dto.getMohallaId(), dto.getStreetNo());
        String url = ApplicationSession.getInstance().getMessage("property.diversionPlotList",
                new Object[] { recordCode, dto.getPlotNo() });
        List<LookUp> diversionNoList = new ArrayList<>();
        ArrayOfDiversionPlotDetailsByRecordcode response = (ArrayOfDiversionPlotDetailsByRecordcode) PropertyRestClient
                .getLandTypeDetails(ArrayOfDiversionPlotDetailsByRecordcode.class, url);

        if (response != null) {
            response.getDiversionPlotDetailsByRecordcode().forEach(obj -> {
                LookUp lookUp = new LookUp();
                lookUp.setDescLangFirst(obj.getPlotno());
                lookUp.setLookUpDesc(obj.getPlotno());
                diversionNoList.add(lookUp);
            });
        }
        return diversionNoList;
    }

    @Transactional
    @Override
    @POST
    @Path("/getYearListForDES")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Long, String> getYearListForDES() throws Exception {
        Map<Long, String> finYearMap = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Long lastYear = iFinancialYear.getFinanceYearId(calendar.getTime());
        String lastYearStr = Utility.getFinancialYearFromDate(calendar.getTime());
        finYearMap.put(lastYear, lastYearStr);
        Long currentYear = iFinancialYear.getFinanceYearId(new Date());
        String currentYearStr = Utility.getFinancialYearFromDate(new Date());
        finYearMap.put(currentYear, currentYearStr);
        return finYearMap;
    }

    @Transactional
    @Override
    @POST
    @Path("/getLocationList/{orgId}/{deptId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LookUp> getLocationList(@PathParam(value = "orgId") Long orgId, @PathParam(value = "deptId") Long deptId) {
        List<LookUp> locList = new ArrayList<>();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
                deptId);
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        return locList;
    }

    @POST
    @Path("/getVsrNo")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Override
    public LandTypeApiDetailRequestDto getVsrNo(LandTypeApiDetailRequestDto dto) {
        String riNo = tbAsTryRepository.findRiDetailsByDistrictTehsilVillageID(dto.getLandType(), dto.getDistrictId(),
                dto.getTehsilId(), dto.getVillageId());
        String vsrNo = tbAsTryRepository.findVsrNoByDistrictTehsilVillageRiCode(dto.getDistrictId(), dto.getTehsilId(), riNo,
                dto.getVillageId());
        dto.setVillageCode(vsrNo);
        return dto;
    }

    @Override
    @WebMethod(exclude = true)
    @Transactional
    public List<LookUp> getTaxDescription(Organisation org, Long deptId) {
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.NewWaterServiceConstants.BILL,
                PrefixConstants.NewWaterServiceConstants.CAA, org);
        final List<TbTaxMas> taxesMaster = tbTaxMasService.findAllTaxesForBillPayment(
                org.getOrgid(), deptId,
                chargeApplicableAt.getLookUpId());

        List<LookUp> taxMasLookup = new ArrayList<>();
        if (taxesMaster != null && !taxesMaster.isEmpty()) {
            taxesMaster.forEach(taxMas -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpCode(taxMas.getTaxCode());
                lookUp.setLookUpId(taxMas.getTaxId());
                lookUp.setExtraStringField1(CommonMasterUtility.getHierarchicalLookUp(taxMas.getTaxCategory1(), org)
						.getLookUpCode());
                lookUp.setLookUpParentId(taxMas.getTaxCategory1());
                lookUp.setExtraStringField2(String.valueOf(taxMas.getCollSeq()));
                String desc = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(taxMas.getTaxDescId(), org)
                        .getLookUpDesc();
                if (desc != null && !desc.isEmpty()) {
                    lookUp.setOtherField(desc);
                }
                taxMasLookup.add(lookUp);
            });
        }
        return taxMasLookup;
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

    @POST
    @Path("/getDeptIdByServiceShortName/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Long getDeptIdByServiceShortName(@PathParam(value = "orgId") Long orgId) {
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                orgId);
        return service.getTbDepartment().getDpDeptid();
    }

    @Override
    public ProvisionalAssesmentMstDto getDataEntryByPropNoOrOldPropNo(long orgId, String assNo, String assOldpropno) {
        ProvisionalAssesmentMstDto dto = null;
        try {
            AssesmentMastEntity entity = assessmentMastDao.fetchDataEntryByAssNoOrOldPropNo(orgId, assNo, assOldpropno);
            if (entity != null) {
                dto = new ProvisionalAssesmentMstDto();
                BeanUtils.copyProperties(entity, dto);
            }
        } catch (Exception exception) {
            throw new FrameworkException(
                    "Error Occured While fetching the Assessment Details by PropNo and OldPropNo ", exception);
        }
        return dto;
    }

    @Override
    public ProvisionalAssesmentOwnerDtlDto getOwnerDetailByPropNo(String assNo) {
        ProvisionalAssesmentOwnerDtlDto dto = null;
        try {
            AssesmentOwnerDtlEntity entity = assessmentOwnerRepository.fetchOwnerDetailByPropNo(assNo);
            if (entity != null) {
                dto = new ProvisionalAssesmentOwnerDtlDto();
                BeanUtils.copyProperties(entity, dto);
            }
        } catch (Exception exception) {
            throw new FrameworkException(
                    "Error Occured While fetching the owner Details by PropNo  ", exception);
        }
        return dto;
    }

	@Override
	@Transactional
	public boolean validateDataEntrySuiteWithFlatNo(String propNo, String flatNo, long orgid, Long smServiceId) {
		int countPro = iProvisionalAssesmentMstService.validatePropertyWithFlat(propNo,flatNo, orgid, smServiceId);
        if (countPro <= 0) {
            return true;
        }
        return false;
	}
	
	@Override
	public Map<Long, String> getConfiguredYearListForDES(String year, Long orgId) throws Exception {
		Date date = Utility.convertStringToDate(Integer.parseInt(MainetConstants.Common_Constant.NUMBER.ZERO_ONE),
				Integer.parseInt(MainetConstants.Common_Constant.NUMBER.ZERO_FOUR), Integer.parseInt(year));
		Map<Long, String> finYearMap = new LinkedHashMap<>();
		Long finYear = iFinancialYear.getFinanceYearId(date);
		List<FinancialYear> finList = iFinancialYear.getFinanceYearListAfterGivenDate(orgId, finYear, new Date());
		finList.forEach(years -> {
			String lastYearStr = null;
			try {
				lastYearStr = Utility.getFinancialYearFromDate(years.getFaFromDate());
			} catch (Exception e) {
			}
			finYearMap.put(years.getFaYear(), lastYearStr);
		});
		return finYearMap;
	}
	
}
