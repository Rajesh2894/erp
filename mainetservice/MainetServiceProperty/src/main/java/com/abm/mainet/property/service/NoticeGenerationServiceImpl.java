package com.abm.mainet.property.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssessNoticeMasterEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillDetails;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.SpecialNoticeReportDetailDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.AssessNoticeMasterRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class NoticeGenerationServiceImpl implements NoticeGenerationService {

    /**
     * 
     */
    private static final long serialVersionUID = -3508333446670514067L;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private AssessNoticeMasterRepository assessNoticeMasterRepository;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IAssessmentMastDao iAssessmentMastDao;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Override
    @Transactional
    public List<String> saveNoticeMaster(List<NoticeGenSearchDto> notGenDtoList, Organisation org, Long empId, Long notTypeId,
            Date notDueDate, Long finYearId) {
        final String ipAddress = Utility.getMacAddress();
        List<String> propList = new ArrayList<>();
        int langId = Utility.getDefaultLanguageId(org);
        LookUp notice = CommonMasterUtility.getNonHierarchicalLookUpObject(notTypeId, org);
        notGenDtoList.forEach(notDto -> {
            if (notDto.getGenNotCheck().equals("Y")) {
                AssessNoticeMasterEntity notMastEnt = new AssessNoticeMasterEntity();
                notMastEnt.setMnAssNo(notDto.getPropertyNo());
                notMastEnt.setCreationDate(new Date());
                notMastEnt.setCreatedBy(empId);
                notMastEnt.setCpdNottyp(notTypeId);
                notMastEnt.setOrgid(org.getOrgid());
                notMastEnt.setLgIpMac(ipAddress);
                notMastEnt.setMnDuedt(notDueDate);
                notMastEnt.setMnNotdt(new Date());
                notMastEnt.setMnNotno(Long.valueOf(getNoticeNo(org.getOrgid(), finYearId, notTypeId)));
                assessNoticeMasterRepository.save(notMastEnt);
                propList.add(notMastEnt.getMnAssNo());
                sendSmsAndMail(notMastEnt, org, langId, notice, notDto);
            }
        });
        return propList;
    }

    @Transactional
    @Override
    public void saveSpecialNoticeGeneration(List<NoticeGenSearchDto> notGenDtoList, Long orgId, Long empId) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        Calendar cal = Calendar.getInstance();
        LookUp lookUp = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.SNC,
                org);
        if (lookUp.getLookUpCode().equals(MainetConstants.Property.SPC_NOT_DUE_DATE.GENERATION_DATE)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(lookUp.getOtherField()));
        }
        Long notTypeId = CommonMasterUtility.getValueFromPrefixLookUp("SP", MainetConstants.Property.propPref.NTY,
                org).getLookUpId();
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        List<String> propNoList = saveNoticeMaster(notGenDtoList, org, empId, notTypeId, cal.getTime(), finYearId);
        assesmentMstRepository.updateAssessFlagOfPropList(orgId, propNoList, MainetConstants.Property.AssStatus.SPEC_NOTICE);
    }

    @Transactional
    @Override
    public List<NoticeGenSearchDto> getAllPropWithAuthChangeByPropNo(NoticeGenSearchDto specNotSearchDto, Long orgId) {

        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = null;
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().equals("")) {
            List<String> propNoList = new ArrayList<>();
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);

            for (String s : propNo) {
                propNoList.add(s);
            }
            assMasList = assesmentMstRepository.getAllPropWithAuthChangeByPropNo(orgId, propNoList);
        } else if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().equals("")) {
            List<String> oldNoList = new ArrayList<>();
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
            assMasList = assesmentMstRepository.getAllPropWithAuthChangeByOldPropNo(orgId, oldNoList);

        }
        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgId));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                        notGen.setMobileNo(owner.getAssoMobileno());
                    }
                }
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGen.setEmailId(assMas.getAssEmail());
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Transactional
    @Override
    public List<NoticeGenSearchDto> fetchAssDetailBySearchCriteria(NoticeGenSearchDto specNotSearchDto, Long orgId) {

        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = iAssessmentMastDao.fetchAssDetailBySearchCriteria(orgId, specNotSearchDto);
        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgId));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                        notGen.setMobileNo(owner.getAssoMobileno());
                    }
                }
                notGen.setEmailId(assMas.getAssEmail());
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Transactional
    @Override
    public List<NoticeGenSearchDto> fetchAllSpecialNoticePropBySearchCriteria(NoticeGenSearchDto specNotSearchDto, Long orgId) {
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = iAssessmentMastDao.fetchAllSpecialNoticePropBySearchCriteria(orgId,
                specNotSearchDto);
        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgId));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                        notGen.setMobileNo(owner.getAssoMobileno());
                    }
                }
                notGen.setEmailId(assMas.getAssEmail());
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    private String getNoticeNo(Long orgId, Long finYearId, Long notTypeCpdType) {
        String fifthPar = notTypeCpdType.toString() + finYearId.toString();
        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                MainetConstants.Property.TB_AS_PRO_ASSE_MAST,
                MainetConstants.Property.PRO_PROP_NO, orgId,
                MainetConstants.FlagC, Long.valueOf(fifthPar));
        final String propNo = sequence.toString();
        final String paddingPropNo = String.format(MainetConstants.CommonMasterUi.PADDING_SIX, Integer.parseInt(propNo));
        return orgId.toString().concat(paddingPropNo);
    }

    @Transactional
    @Override
    public List<NoticeGenSearchDto> getAllSpecialNoticeProperty(NoticeGenSearchDto specNotSearchDto, Long orgId) {

        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = null;
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().equals("")) {
            List<String> propNoList = new ArrayList<>();
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s);
            }
            assMasList = assesmentMstRepository.getAllSpecNotPropByPropNo(orgId, propNoList);
        } else if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().equals("")) {
            List<String> oldNoList = new ArrayList<>();
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
            assMasList = assesmentMstRepository.getAllSpecNotPropByOldPropNo(orgId, oldNoList);

        }
        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgId));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                    }
                }
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Transactional
    @Override
    public List<SpecialNoticeReportDto> setDtoForSpecialNotPrinting(List<NoticeGenSearchDto> notGenShowList, Organisation org) {
        List<SpecialNoticeReportDto> specNotDtoList = new ArrayList<>();
        notGenShowList.forEach(dto -> {
            List<AssesmentMastEntity> assMstList = assesmentMstRepository.getAssessmentMstByPropNo(org.getOrgid(),
                    dto.getPropertyNo());
            AssesmentMastEntity assMast = assMstList.get(assMstList.size() - 1);
            SpecialNoticeReportDto speNotDto = new SpecialNoticeReportDto();
            String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A").getDpDeptdesc();
            if (deptName != null) {
                speNotDto.setDeptNameL(deptName);
            }
            speNotDto.setOrgName(org.getONlsOrgname());
            speNotDto.setLetterHeading("Special Notice");

            speNotDto.setNoticeNoL("Notice No");
            AssessNoticeMasterEntity notMaster = assessNoticeMasterRepository.getNoticeMasterByPropNo(dto.getPropertyNo(),
                    org.getOrgid());
            speNotDto.setNoticeNoV(notMaster.getMnNotno().toString());
            speNotDto.setNoticeDateL("Notice Date");
            LocalDate notDate = notMaster.getMnNotdt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainetConstants.DATE_FRMAT);
            String notDueDateStr = notDate.format(formatter);
            speNotDto.setNoticeDateV(notDueDateStr);
            speNotDto.setTo("To");
            List<AssesmentOwnerDtlEntity> ownerList = assMast.getAssesmentOwnerDetailEntityList();
            for (AssesmentOwnerDtlEntity owner : ownerList) {
                if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                    speNotDto.setName(owner.getAssoOwnerName());
                }
            }
            speNotDto.setSubjectL("Subject");
            speNotDto.setNameTitle("Sir/Madam");
            speNotDto.setNoticeDueDateL("Notice Due Date");
            speNotDto.setSubjectV("Special Notice Regrading Property Assessment and Revised Assessment");
            speNotDto.setReferenceL("Reference");
            speNotDto.setPropNoL("Property No");
            speNotDto.setPropNoV(assMast.getAssNo());
            speNotDto.setAssessmentDateL("Assessment Date");
            SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
            String acqDate = sm.format(assMast.getAssAcqDate());
            String notDueDate = sm.format(notMaster.getMnDuedt());
            speNotDto.setNoticeDueDateV(notDueDate);
            speNotDto.setAssessmentDateV(acqDate);
            speNotDto.setAddressL("Address");
            speNotDto.setAddressV(assMast.getAssAddress() + ", " + dto.getLocationName() + ", " + assMast.getAssPincode());
            if (assMast.getAssWard1() != null) {
                speNotDto.setWard1L("Zone");
                speNotDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard1(), org.getOrgid())
                        .getDescLangFirst());
            }
            if (assMast.getAssWard2() != null) {
                speNotDto.setWard2L("Ward");
                speNotDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard2(), org.getOrgid())
                        .getDescLangFirst());
            }

            speNotDto.setLetterBody(
                    "As per above reference you are here by informed that your property has been Assessed/Ressessed, the details of which are as below");
            speNotDto.setPropDetailsL("Property Details");
            speNotDto.setIdNoL("Flat No/Id No");
            speNotDto.setUsageType1L("Usage Type");
            speNotDto.setConstructionClassL("Construction Class");
            speNotDto.setAssessAreaL("Build Up Area");
            speNotDto.setFloorL("Floor");
            speNotDto.setAlvL("ALV");
            speNotDto.setRvL("RV");
            double totalAlv = 0;
            double totalRv = 0;
            double totalArea = 0;
            List<SpecialNoticeReportDetailDto> specNotDetDtoList = new ArrayList<>();

            for (AssesmentDetailEntity det : assMast.getAssesmentDetailEntityList()) {
                SpecialNoticeReportDetailDto specNotDetDto = new SpecialNoticeReportDetailDto();
                if (det.getAssdUnitNo() != null) {
                    specNotDetDto.setIdNoV(det.getAssdUnitNo().toString());
                }
                specNotDetDto.setAlvV(det.getAssdAlv().toString());
                specNotDetDto.setRvV(det.getAssdRv().toString());
                specNotDetDto
                        .setConstructionClassV(CommonMasterUtility.getNonHierarchicalLookUpObject(det.getAssdConstruType())
                                .getDescLangFirst());
                if (det.getAssdUsagetype1() != null) {
                    specNotDetDto
                            .setUsageType1V(
                                    CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1()).getDescLangFirst());
                    specNotDetDto.setAssessAreaV(det.getAssdBuildupArea().toString());
                }
                specNotDetDto.setFloorV(CommonMasterUtility.getNonHierarchicalLookUpObject(det.getAssdFloorNo())
                        .getDescLangFirst());
                totalAlv = det.getAssdAlv() + totalAlv;
                totalRv = det.getAssdRv() + totalRv;
                totalArea = det.getAssdBuildupArea() + totalArea;
                specNotDetDtoList.add(specNotDetDto);
            }

            speNotDto.setTotal("Total");
            speNotDto.setTotalAlv(Double.toString(totalAlv));
            speNotDto.setTotalRv(Double.toString(totalRv));
            speNotDto.setTotalArea(Double.toString(totalArea));
            speNotDto.setFooterName("Assessor & Collector");
            speNotDto.setFooterBody(
                    "If you have any objection, then you may file petition denying liability in whole or part in, before the due date of the special notice.");
            speNotDto.setSpecNotDetDtoList(specNotDetDtoList);
            specNotDtoList.add(speNotDto);
        });
        return specNotDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeGenSearchDto> fetchPropertyAfterDueDate(NoticeGenSearchDto specNotSearchDto, long orgid) {
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        List<String> propNoList = new ArrayList<>();
        List<String> oldNoList = new ArrayList<>();
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = null;
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s);
            }
        }
        if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
        }
        Long noticeType = null;
        LookUp typelookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(specNotSearchDto.getNoticeType(), org);
        if (typelookUp.getLookUpCode().equals("DN")) {
            assMasList = iAssessmentMastDao.getAllPropertyAfterDueDate(orgid, propNoList, oldNoList, specNotSearchDto);
        } else {
            if (typelookUp.getLookUpCode().equals("WN")) {
                noticeType = CommonMasterUtility.getValueFromPrefixLookUp("DN", "NTY", org).getLookUpId();
            } else if (typelookUp.getLookUpCode().equals("WEN")) {
                noticeType = CommonMasterUtility.getValueFromPrefixLookUp("WN", "NTY", org).getLookUpId();
            }
            assMasList = iAssessmentMastDao.getNoticesAfterDueDate(orgid, propNoList, oldNoList, specNotSearchDto, noticeType);
        }

        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgid));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                        notGen.setMobileNo(owner.getAssoMobileno());
                    }
                }
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGen.setEmailId(assMas.getAssEmail());
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Override
    @Transactional
    public void saveDemandAndWarrantNoticeGeneration(List<NoticeGenSearchDto> notGenSearchDtoList, long orgid, Long empId,
            Long noticeType) {
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        Calendar cal = Calendar.getInstance();
        LookUp lookUp = null;

        LookUp typelookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(noticeType, org);
        if (typelookUp.getLookUpCode().equals("DN")) {
            lookUp = CommonMasterUtility.getDefaultValueByOrg("DNC",
                    org);
        } else if (typelookUp.getLookUpCode().equals("WN")) {
            lookUp = CommonMasterUtility.getDefaultValueByOrg("WNC",
                    org);
        } else if (typelookUp.getLookUpCode().equals("WEN")) {
            lookUp = CommonMasterUtility.getDefaultValueByOrg("WEC",
                    org);
        }
        if (lookUp.getLookUpCode().equals("GD")) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(lookUp.getOtherField()));
        }
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        saveNoticeMaster(notGenSearchDtoList, org, empId, noticeType, cal.getTime(), finYearId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeGenSearchDto> fetchPropertyDemandNoticePrint(NoticeGenSearchDto specNotSearchDto, long orgid) {
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        List<String> propNoList = new ArrayList<>();
        List<String> oldNoList = new ArrayList<>();
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = null;
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s);
            }
        }
        if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
        }
        assMasList = iAssessmentMastDao.fetchPropertyDemandNoticePrint(orgid, propNoList, oldNoList, specNotSearchDto);
        if (assMasList != null) {
            assMasList.forEach(assMas -> {
                NoticeGenSearchDto notGen = new NoticeGenSearchDto();
                notGen.setPropertyNo(assMas.getAssNo());
                notGen.setApplicationId(assMas.getApmApplicationId());
                notGen.setAssId(assMas.getProAssId());
                notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgid));
                List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                    }
                }
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGenShowList.add(notGen);
            });
        }
        return notGenShowList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrintBillMaster> getNoticePrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation) {
        List<PrintBillMaster> printMas = new ArrayList<>(0);
        final String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A")
                .getDpDeptdesc();
        List<String> propId = new ArrayList<>();
        List<Long> billId = new ArrayList<>();
        notGenSearchDtoList.stream()
                .filter(dto -> MainetConstants.NewWaterServiceConstants.YES.equals(dto.getGenNotCheck()))
                .forEach(dto -> {
                    propId.add(dto.getPropertyNo());
                });
        List<Object[]> bill = propertyMainBillRepository.fetchCurrentBillByPropNo(propId);
        if (bill != null && !bill.isEmpty()) {
            bill.forEach(value -> {
                billId.add(Long.valueOf(value[1].toString()));
            });
            List<MainBillMasEntity> mainBill = propertyMainBillRepository.fetchBillsFromBmIdNo(billId);
            if (mainBill != null && !mainBill.isEmpty()) {
                mainBill.forEach(mainbill -> {
                    AssesmentMastEntity assessment = assesmentMstRepository.fetchAssessmentMasterByPropNo(organisation.getOrgid(),
                            mainbill.getPropNo());

                    PrintBillMaster printDto = new PrintBillMaster();
                    printDto.setDeptNameL(deptName);
                    printDto.setOrgName(organisation.getONlsOrgname());
                    printDto.setYearL("Year From " + UtilityService.convertDateToDDMMYYYY(mainbill.getBmFromdt()) + " To "
                            + UtilityService.convertDateToDDMMYYYY(mainbill.getBmTodt()));
                    printDto.setBillNoV(mainbill.getBmNo());
                    printDto.setPropNoV(mainbill.getPropNo());
                    printDto.setBillDateV(UtilityService.convertDateToDDMMYYYY(mainbill.getBmBilldt()));
                    printDto.setOldPropNoV(assessment.getAssOldpropno());
                    if (assessment.getAssWard1() != null) {
                        printDto.setWard1L("ZONE");// need to change label from prefix for hierarchical
                        printDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard1(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard2() != null) {
                        printDto.setWard2L("WARD");
                        printDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard2(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard3() != null) {
                        printDto.setWard3L("BLOCK");
                        printDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard3(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard4() != null) {
                        printDto.setWard4L("ROUTE");
                        printDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard4(), organisation)
                                .getDescLangFirst());
                    }
                    AtomicDouble totalAlv = new AtomicDouble(0);
                    AtomicDouble totalRv = new AtomicDouble(0);
                    AtomicDouble totalcv = new AtomicDouble(0);
                    assessment.getAssesmentDetailEntityList().forEach(detail -> {
                        totalAlv.getAndAdd(detail.getAssdAlv());
                        totalRv.getAndAdd(detail.getAssdRv());
                        totalcv.addAndGet(detail.getAssdCv());
                    });
                    printDto.setTotAlvV(totalAlv.get());
                    printDto.setTotRvV(totalRv.get());
                    printDto.setCvV(totalcv.get());
                    AssesmentOwnerDtlEntity owner = assessment.getAssesmentOwnerDetailEntityList().get(0);
                    StringBuilder name = new StringBuilder(owner.getAssoOwnerName());
                    if (owner.getRelationId() != null) {
                        name.append(" " + CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(), organisation)
                                .getDescLangFirst());
                        name.append(" " + owner.getAssoGuardianName());
                    }
                    printDto.setNameV(name.toString());
                    String locName = iLocationMasService.getLocationNameById(assessment.getLocId(), organisation.getOrgid());
                    printDto.setAddressV(assessment.getAssAddress() + ", " + locName + ", " + assessment.getAssPincode());
                    AtomicDouble totalArrear = new AtomicDouble(0);
                    AtomicDouble totalCurrent = new AtomicDouble(0);
                    mainbill.getBillDetEntityList().forEach(billdet -> {
                        PrintBillDetails printDetails = new PrintBillDetails();
                        printDetails.setTaxNameV(
                                tbTaxMasService.findTaxByTaxIdAndOrgId(billdet.getTaxId(), organisation.getOrgid()).getTaxDesc());
                        totalArrear.addAndGet(billdet.getBdPrvArramt());
                        printDetails.setArrearsAmountV(billdet.getBdPrvArramt());
                        printDetails.setCurrentAmountV(billdet.getBdCurTaxamt());
                        totalCurrent.addAndGet(billdet.getBdCurTaxamt());
                        printDetails.setTotalAmountV(billdet.getBdCurTaxamt() + billdet.getBdPrvArramt());
                        printDto.getPrintBillDetails().add(printDetails);
                    });
                    printDto.setTotArrBill(totalArrear.doubleValue());
                    printDto.setTotCurrBill(totalCurrent.doubleValue());
                    printDto.setTotTatAmt(totalArrear.doubleValue() + totalCurrent.doubleValue());
                    printDto.setAdjAmtCurrentV(0d);
                    printDto.setAjdAmtArrearsV(0d);
                    printDto.setAdjTotalAmtV(0d);

                    printDto.setExcessAdjTotalV(0d);

                    // printDto.setTotPayableV(Double.toString(printDto.getTotTatAmt()));
                    printDto.setTotPayableV(printDto.getTotTatAmt());
                    printDto.setAmtToWordsV(Utility.convertNumberToWord(Double
                            .valueOf(printDto.getTotTatAmt())));

                    printDto.setBillDueDateV(UtilityService.convertDateToDDMMYYYY(mainbill.getBmDuedate()));
                    printDto.setFooterName("Accessor & Collector OR Dy. Commissionar (City)");
                    // printDto.setFooterSing(footerSing);
                    printDto.setBottomNotePath(
                            "Note:The bill due date is 15(Fifteen) days from the date of acceptance of this bill from the citizen.");
                    printDto.setOrgType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(organisation.getOrgCpdId())
                            .getLookUpDesc());
                    printMas.add(printDto);
                });
            }
        }
        return printMas;
    }

    private void sendSmsAndMail(final AssessNoticeMasterEntity notMastEnt, Organisation organisation, int langId,
            LookUp noticeDesc,
            NoticeGenSearchDto notDto) {
        String url = "";
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(notDto.getEmailId());
        dto.setMobnumber(notDto.getMobileNo());
        dto.setPropertyNo(notMastEnt.getMnAssNo());
        dto.setNoticeNo(notMastEnt.getMnNotno().toString());
        dto.setNoticeDate(Utility.dateToString(notMastEnt.getMnNotdt()));
        dto.setDueDt(Utility.dateToString(notMastEnt.getMnDuedt()));
        dto.setUserId(notMastEnt.getCreatedBy());
        String noticType = "";
        if (langId == MainetConstants.MARATHI) {
            noticType = noticeDesc.getDescLangSecond();
        } else {
            noticType = noticeDesc.getDescLangFirst();
        }
        dto.setServName(noticType);
        if ("SP".equals(noticeDesc.getLookUpCode())) {
            url = "SpecialNoticeGeneration.html";
        } else {
            url = "PropertyDemandNoticeGeneration.html";
        }
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, url,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, langId);
    }

}
