package com.abm.mainet.property.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.objection.domain.NoticeMasterEntity;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.repository.NoticeMasterRepository;
import com.abm.mainet.cfc.objection.service.NoticeMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationSession;
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
public class PropertyNoticeServiceImpl implements PropertyNoticeService {

    /**
     * 
     */
	private static final Logger LOGGER = Logger.getLogger(PropertyNoticeServiceImpl.class);
	
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
    private NoticeMasterService noticeMasterService;

    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;
    
    @Autowired
    private PropertyBRMSService propertyBRMSService;
    
    @Autowired
    private NoticeMasterRepository noticeMasterRepository;
    
    @Override
    public List<String> saveNoticeMaster(List<NoticeGenSearchDto> notGenDtoList, Organisation orgId, Long empId, Long notTypeId,
            Date notDueDate, Long finYearId) {
        final String ipAddress = Utility.getMacAddress();
        List<String> propList = new ArrayList<>();
        List<AssessNoticeMasterEntity> notEntList = new ArrayList<>();
        int langId = Utility.getDefaultLanguageId(orgId);
        Long deptId = departmentService.getDepartmentIdByDeptCode("AS");
        LookUp notice = CommonMasterUtility.getNonHierarchicalLookUpObject(notTypeId, orgId);

        // This method is called in thread
		/*
		 * notGenDtoList.forEach(notDto -> { saveNoticeMaster(orgId, empId, notTypeId,
		 * notDueDate, finYearId, ipAddress, propList, notEntList, deptId, notDto); });
		 */
        
       
        
		final ExecutorService executorService = Executors.newFixedThreadPool(
				Integer.valueOf(ApplicationSession.getInstance().getMessage("bill.thread.pool.size")));
		notGenDtoList.forEach(notDto -> {

			executorService.execute(new Runnable() {
				public void run() {
					LOGGER.info(
							String.format("starting thread before task thread %s", Thread.currentThread().getName()));
					saveNoticeMaster(orgId, empId, notTypeId, notDueDate, finYearId, ipAddress, propList, notEntList,
							deptId, notDto);
					LOGGER.info(String.format("starting thread after task thread %s after",
							Thread.currentThread().getName()));
				}
			});

		});


		/*
		 * assessNoticeMasterRepository.save(notEntList); notGenDtoList.forEach(notDto
		 * -> { sendSmsAndMail(orgId, langId, notice, notDto, notDueDate, empId,null);
		 * });
		 */
        return propList;
    }

	/**
	 * @param orgId
	 * @param empId
	 * @param notTypeId
	 * @param notDueDate
	 * @param finYearId
	 * @param ipAddress
	 * @param propList
	 * @param notEntList
	 * @param deptId
	 * @param notDto
	 */
	private void saveNoticeMaster(Organisation orgId, Long empId, Long notTypeId, Date notDueDate, Long finYearId,
			final String ipAddress, List<String> propList, List<AssessNoticeMasterEntity> notEntList, Long deptId,
			NoticeGenSearchDto notDto) {
		if (notDto.getGenNotCheck().equals(MainetConstants.Y_FLAG)) {
			int langId = Utility.getDefaultLanguageId(orgId);
			LookUp notice = CommonMasterUtility.getNonHierarchicalLookUpObject(notTypeId, orgId);
		    AssessNoticeMasterEntity notMastEnt = new AssessNoticeMasterEntity();
		    notMastEnt.setMnAssNo(notDto.getPropertyNo());
		    notMastEnt.setCreationDate(new Date());
		    notMastEnt.setCreatedBy(empId);
		    notMastEnt.setCpdNottyp(notTypeId);
		    notMastEnt.setOrgid(orgId.getOrgid());
		    notMastEnt.setLgIpMac(ipAddress);
		    notMastEnt.setMnDuedt(notDueDate);
		    notMastEnt.setMnNotdt(new Date());
		    notMastEnt.setMnNotno(Long.valueOf(getNoticeNo(orgId.getOrgid(), finYearId, notTypeId)));
		    notDto.setNoticeNo(notMastEnt.getMnNotno());
		    propList.add(notMastEnt.getMnAssNo());
		    
		    if(Utility.isEnvPrefixAvailable(orgId, "ASCL")) {
		    	try {
		    		List<TbBillMas> billMasList = propertyMainBillService.fetchNotPaidBillForAssessment(notDto.getPropertyNo(), orgId.getOrgid());
		            if(CollectionUtils.isNotEmpty(billMasList)) {
		            	propertyBRMSService.fetchAllChargesApplAtReceipt(notDto.getPropertyNo(), billMasList, orgId, deptId,
		                        null, empId,MainetConstants.FlagY);
					propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId.getOrgid(), empId,
							MainetConstants.Property.AuthStatus.AUTH, ipAddress);
		            }
		    	}catch(Exception e){
		    		LOGGER.error("Exception while calculating demand notice charges"+e);
		    	}
		    }
		    notEntList.add(notMastEnt);
		    assessNoticeMasterRepository.save(notMastEnt);
		    sendSmsAndMail(orgId, langId, notice, notDto, notDueDate, empId,null);
		}
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
    public void saveListOfNoticeApplicableForObjection(List<NoticeGenSearchDto> notDtoList, Long orgId, Employee emp,
            String notType,
            String notDueDatePrefix, Long deptId) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        Calendar cal = Calendar.getInstance();
        List<String> propNoList = new ArrayList<>();

        LookUp lookUp = CommonMasterUtility.getDefaultValueByOrg(notDueDatePrefix,
                org);
        if (lookUp.getLookUpCode().equals(MainetConstants.Property.SPC_NOT_DUE_DATE.GENERATION_DATE)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(lookUp.getOtherField()));
        }
        Long notTypeId = CommonMasterUtility.getValueFromPrefixLookUp(notType, MainetConstants.Property.propPref.NTY,
                org).getLookUpId();
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());

        List<NoticeMasterDto> notMasterList = new ArrayList<>();
        notDtoList.forEach(notDto -> {
            NoticeMasterDto notMas = new NoticeMasterDto();
            notMas.setRefNo(notDto.getPropertyNo());
            notMas.setNotDuedt(cal.getTime());
            notMas.setDpDeptid(deptId);
            notMas.setNotNo(String.valueOf(notDto.getNoticeNo()));
            notMasterList.add(notMas);
            propNoList.add(notDto.getPropertyNo());
        });
        List<String> NoticeNoList = noticeMasterService.saveListOfNoticeMaster(notMasterList, orgId, emp.getEmpId(),
                emp.getEmppiservername(), notTypeId,
                finYearId);

        assesmentMstRepository.updateAssessFlagOfPropList(orgId, propNoList, MainetConstants.Property.AssStatus.SPEC_NOTICE);
        Date dueDate = Utility.removeTimeFromDatestatic(cal.getTime());
        assesmentMstRepository.updateAssessSplNoticeDueDate(orgId,propNoList,dueDate);

        Iterator<NoticeGenSearchDto> NoticeGenSearch = notDtoList.iterator();
        Iterator<String> NoticeNo = NoticeNoList.iterator();

        while (NoticeGenSearch.hasNext() && NoticeNo.hasNext()) {
            String noticeNo = NoticeNo.next();

            if (!noticeNo.isEmpty()) {
                NoticeGenSearch.next().setNoticeNo(Long.valueOf(noticeNo));
            }

        }

    }

    @Transactional
    @Override
    public NoticeMasterDto saveNoticeApplicableForObjection(String propNo, Long appNo, Long serviceId, Long orgId, Employee emp,
            String notType,
            String notDueDatePrefix, Long deptId,String flatNo) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        Calendar cal = Calendar.getInstance();
        LookUp lookUp = CommonMasterUtility.getDefaultValueByOrg(notDueDatePrefix,
                org);
        if (lookUp.getLookUpCode().equals(MainetConstants.Property.SPC_NOT_DUE_DATE.GENERATION_DATE)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(lookUp.getOtherField()));
        }
        Long notTypeId = CommonMasterUtility.getValueFromPrefixLookUp(notType, MainetConstants.Property.propPref.NTY,
                org).getLookUpId();
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());

        NoticeMasterDto notMas = new NoticeMasterDto();
        notMas.setRefNo(propNo);		
        notMas.setApmApplicationId(appNo);
        notMas.setFlatNo(flatNo);
        notMas.setNotDuedt(cal.getTime());
        notMas.setDpDeptid(deptId);
        notMas.setSmServiceId(serviceId);
        String notNo = noticeMasterService.saveNoticeMaster(notMas, orgId, emp.getEmpId(),
                emp.getEmppiservername(), notTypeId,
                finYearId);
        notMas.setNotNo(notNo);
        // assesmentMstRepository.updateAssessFlagOfProperty(orgId, propNo, MainetConstants.Property.AssStatus.SPEC_NOTICE);
        return notMas;
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
                        notGen.setEmailId(owner.geteMail());
                    }
                }
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGen.setEmailId(assMas.getAssEmail());

                notGen.setEmailId(assMas.getAssesmentOwnerDetailEntityList().get(0).geteMail());
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
                notGen.setGenNotCheck(MainetConstants.Y_FLAG);
                notGen.setEmailId(assMas.getAssEmail());
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Transactional
    @Override
    public List<NoticeGenSearchDto> fetchAllSpecialNoticePropBySearchCriteria(NoticeGenSearchDto specNotSearchDto, Long orgId) {
        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
        List<AssesmentMastEntity> assMasList = iAssessmentMastDao.fetchAllObjNotPropBySearchCriteria(orgId,
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
                notGen.setGenNotCheck(MainetConstants.N_FLAG);
                notGen.setEmailId(assMas.getAssEmail());
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
            assMasList = assesmentMstRepository.getAllObjNotPropByPropNo(orgId, propNoList);
        } else if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().equals("")) {
            List<String> oldNoList = new ArrayList<>();
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                oldNoList.add(s);
            }
            assMasList = assesmentMstRepository.getAllObjNotPropOldPropNo(orgId, oldNoList);

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
                notGen.setGenNotCheck(MainetConstants.N_FLAG);
                notGen.setEmailId(assMas.getAssEmail());
                notGenShowList.add(notGen);

            });
        }
        return notGenShowList;
    }

    @Transactional
    @Override
    public List<SpecialNoticeReportDto> setDtoForSpecialNotPrinting(List<NoticeGenSearchDto> notGenShowList, Organisation org) {
        List<SpecialNoticeReportDto> specNotDtoList = new ArrayList<>();
        Department department = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A");

        String deptName = department.getDpDeptdesc();
        Long dpDeptid = department.getDpDeptid();
        notGenShowList.forEach(dto -> {

            if (dto.getGenNotCheck() != null && !dto.getGenNotCheck().isEmpty()) {
                if (dto.getGenNotCheck().equals("Y")) {
                    List<AssesmentMastEntity> assMstList = assesmentMstRepository.getAssessmentMstByPropNo(org.getOrgid(),
                            dto.getPropertyNo());
                    AssesmentMastEntity assMast = assMstList.get(assMstList.size() - 1);
                    SpecialNoticeReportDto speNotDto = new SpecialNoticeReportDto();

                    if (deptName != null) {
                        speNotDto.setDeptNameL(deptName);
                    }
                    speNotDto.setOrgName(org.getONlsOrgname());
                    speNotDto.setOrgNameReg(org.getoNlsOrgnameMar());
                    speNotDto.setLetterHeading("Special Notice");

                    speNotDto.setNoticeNoL("Notice No");
                    String  noticePeriod= CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.SNC, org)
                            .getOtherField();
                    speNotDto.setSpecialNoticeDueDays(noticePeriod);
                    NoticeMasterDto notMaster = new NoticeMasterDto();
                    if (StringUtils.isEmpty(dto.getNoticeEntry())) {
                        notMaster = noticeMasterService.getMaxNoticeByRefNo(org.getOrgid(), dto.getPropertyNo(),
                                dpDeptid);
                    } else {
                        notMaster = noticeMasterService.getNoticeByNoticeNo(org.getOrgid(), dto.getNoticeNo() + "");
                    }

                    speNotDto.setNoticeNoV(notMaster.getNotNo().toString());
                    speNotDto.setNoticeDateL("Notice Date");
                    LocalDate notDate = notMaster.getNotDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainetConstants.DATE_FRMAT);
                    String notDueDateStr = notDate.format(formatter);
                    speNotDto.setNoticeDateV(notDueDateStr);
                    speNotDto.setTo("To");
                    List<AssesmentOwnerDtlEntity> ownerList = assMast.getAssesmentOwnerDetailEntityList();
                    dto.setEmailId(ownerList.get(0).geteMail());
                    String name = "";
                    String ownerRegName="";
					if (assMast.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
							.getNonHierarchicalLookUpObject(assMast.getBillMethod(), org).getLookUpCode())) {
						for (AssesmentDetailEntity det : assMast.getAssesmentDetailEntityList()) {
							name += det.getOccupierName() + "/";
							ownerRegName+=det.getOccupierNameReg()+ "/";
						}
					}
					else {
						for (AssesmentOwnerDtlEntity owner : ownerList) {
							name += owner.getAssoOwnerName() + "/";
							ownerRegName+=owner.getAssoOwnerNameReg()+ "/";
						}
					}
                    speNotDto.setName(name.substring(0, name.length() - 1));
					if (StringUtils.isNotEmpty(ownerRegName)) {
						speNotDto.setOwnerNameReg(ownerRegName.substring(0, ownerRegName.length() - 1));
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
                    String notDueDate = sm.format(notMaster.getNotDuedt());
                    speNotDto.setNoticeDueDateV(notDueDate);
                    speNotDto.setAssessmentDateV(acqDate);
                    speNotDto.setAddressL("Address");
                    
                    StringBuilder address = new StringBuilder();
                    address.append(assMast.getAssAddress());
                    if(StringUtils.isNotBlank(dto.getLocationName())) {
                    	address.append(", ");
                    	address.append(dto.getLocationName());
                    }
                    if(assMast.getAssPincode() != null) {
                    	address.append(", ");
                    	address.append(assMast.getAssPincode());
                    }
                    speNotDto
                            .setAddressV(address.toString());
                    
                    LocalDate specialNotDate = notDate.plusDays(Long.valueOf(noticePeriod)).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
                    
                    LocalDate nextOrSameThursday = specialNotDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
    				Date specialNotDueDate = Date.from(nextOrSameThursday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    				Date specialNotDuedateWithTime = DateUtils.addHours(specialNotDueDate,
    						Integer.valueOf(MainetConstants.Common_Constant.NUMBER.ELEVEN));
    				speNotDto.setDueDateAfterSpecialNot(specialNotDuedateWithTime);
					

                    final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB,
                            org);

                    if (assMast.getAssWard1() != null && lookupList.size() >= 1) {
                        speNotDto.setWard1L(lookupList.get(0).getLookUpDesc());// need to change label from prefix for
                                                                               // hierarchical
                        speNotDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard1(), org)
                                .getDescLangFirst());
                    }
                    if (assMast.getAssWard2() != null && lookupList.size() >= 2) {
                        speNotDto.setWard2L(lookupList.get(1).getLookUpDesc());
                        speNotDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard2(), org)
                                .getDescLangFirst());
                    }
                    if (assMast.getAssWard3() != null && lookupList.size() >= 3) {
                        speNotDto.setWard3L(lookupList.get(2).getLookUpDesc());
                        speNotDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard3(), org)
                                .getDescLangFirst());
                    }
                    if (assMast.getAssWard4() != null && lookupList.size() >= 4) {
                        speNotDto.setWard4L(lookupList.get(3).getLookUpDesc());
                        speNotDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard4(), org)
                                .getDescLangFirst());
                    }
                    if (assMast.getAssWard5() != null && lookupList.size() >= 5) {
                        speNotDto.setWard5L(lookupList.get(4).getLookUpDesc());
                        speNotDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard5(), org)
                                .getDescLangFirst());
                    }
                    if(assMast.getFirstAssessmentDate()!=null) {
                    	String assDate = Utility.dateToString(assMast.getFirstAssessmentDate(), MainetConstants.DATE_FORMAT_UPLOAD);
                    	speNotDto.setFirstAssessmentDate(assDate);
                    }
                    
                    if(assMast.getTppSurveyNumber()!=null) {
                    	speNotDto.setSurveyNo(assMast.getTppSurveyNumber());
                    }                   
                    if(assMast.getTppPlotNo()!=null) {
                    	speNotDto.setPlotNo(assMast.getTppPlotNo());
                    }
                    long numberOfYears=0;
                    if (assMast.getAssAcqDate() != null) {
                        LocalDate accqDate = assMast.getAssAcqDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        //Timestamp finToDateStamp = new Timestamp(financialYear.getFaToDate().getTime());
                        //Date finToDate = new Date(finToDateStamp.getTime());
                        Date finToDate = new Date();
                        LocalDate finanToDate = finToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        numberOfYears = Period.between(accqDate, finanToDate).getYears();
                        speNotDto.setPropertyAge(String.valueOf(numberOfYears));
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
                    double totalCarpetArea = 0;
                    speNotDto.setIllegalFlag(MainetConstants.FlagN);
                    List<SpecialNoticeReportDetailDto> specNotDetDtoList = new ArrayList<>();

                    for (AssesmentDetailEntity det : assMast.getAssesmentDetailEntityList()) {
                        SpecialNoticeReportDetailDto specNotDetDto = new SpecialNoticeReportDetailDto();
                        if(StringUtils.isNotBlank(det.getLegal()) && StringUtils.equalsIgnoreCase("Illegal", det.getLegal())) {
                        	speNotDto.setIllegalFlag(MainetConstants.FlagY);
                		}
                        if (det.getAssdUnitNo() != null) {
                            specNotDetDto.setIdNoV(det.getAssdUnitNo().toString());
                        }
                        if (det.getAssdAlv() != null) {
                            specNotDetDto.setAlvV(det.getAssdAlv().toString());
                        } else {
                            specNotDetDto.setAlvV(String.valueOf(0.00));
                        }
                        if (det.getAssdRv() != null) {
                            specNotDetDto.setRvV(det.getAssdRv().toString());
                        } else {

                            specNotDetDto.setRvV(String.valueOf(0.00));
                        }
                        specNotDetDto.setPropertyAge(String.valueOf(numberOfYears));
                        specNotDetDto.setShastiAmount("");
                        
                        LookUp constructionClass = CommonMasterUtility.getNonHierarchicalLookUpObject(det.getAssdConstruType(), org);
						specNotDetDto.setConstructionClassV(constructionClass.getDescLangFirst());
						specNotDetDto.setConstructionClassReg(constructionClass.getDescLangSecond());
                        if (det.getAssdUsagetype1() != null) {
							LookUp usageType = CommonMasterUtility.getHierarchicalLookUp(det.getAssdUsagetype1(), org);
							specNotDetDto.setUsageType1V(usageType.getDescLangFirst());
							specNotDetDto.setUsageType1Reg(usageType.getDescLangSecond());
                            specNotDetDto.setAssessAreaV(det.getAssdBuildupArea().toString());
                        }

                        if (det.getAssdFloorNo() != null) {
                        	LookUp floor = CommonMasterUtility.getNonHierarchicalLookUpObject(det.getAssdFloorNo(), org);
							specNotDetDto.setFloorV(floor.getDescLangFirst());
							specNotDetDto.setFloorReg(floor.getDescLangSecond());
                        }
                        if(det.getCarpetArea()!=null) {
                        	specNotDetDto.setCarpetArea(det.getCarpetArea().toString());
                        	totalCarpetArea+=det.getCarpetArea();
                        }
                        if(det.getActualRent()!=null) {
                        	specNotDetDto.setActualRent(det.getActualRent().toString());
                        }
                        if(assMast.getPropLvlRoadType()!=null) {
							LookUp roadType = CommonMasterUtility
									.getNonHierarchicalLookUpObject(assMast.getPropLvlRoadType(), org);
							specNotDetDto.setRoadType(roadType.getDescLangFirst());
							specNotDetDto.setRoadTypeReg(roadType.getDescLangSecond());
                        }
						if (det.getFlatNo() != null) {
							specNotDetDto.setFlatNo(det.getFlatNo());
						}

                        if (det.getAssdAlv() != null)
                            totalAlv = det.getAssdAlv() + totalAlv;
                        if (det.getAssdRv() != null)
                            totalRv = det.getAssdRv() + totalRv;
                        if (det.getAssdBuildupArea() != null)
                            totalArea = det.getAssdBuildupArea() + totalArea;
                        specNotDetDtoList.add(specNotDetDto);
                    }

                    speNotDto.setTotal("Total");
                    speNotDto.setTotalAlv(Double.toString(totalAlv));
                    speNotDto.setTotalRv(Double.toString(totalRv));
                    speNotDto.setTotalArea(Double.toString(totalArea));
                    speNotDto.setTotalcarpetArea(Double.toString(totalCarpetArea));
                    speNotDto.setFooterName("Assessor & Collector");
                    speNotDto.setFooterBody(
                            "If you have any objection, then you may file petition denying liability in whole or part in, before the due date of the special notice.");
                    speNotDto.setSpecNotDetDtoList(specNotDetDtoList);
                    specNotDtoList.add(speNotDto);

                }
            }
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
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        List<AssesmentMastEntity> assMasList = null;
        if (specNotSearchDto.getPropertyNo() != null && !specNotSearchDto.getPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getPropertyNo().split(MainetConstants.operator.COMMA);
            for (String s : propNo) {
                propNoList.add(s);
            }
        }
        if (specNotSearchDto.getOldPropertyNo() != null && !specNotSearchDto.getOldPropertyNo().isEmpty()) {
            String[] propNo = specNotSearchDto.getOldPropertyNo().split(MainetConstants.operator.COMMA);
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

		if (CollectionUtils.isNotEmpty(assMasList)) {
			if (Utility.isEnvPrefixAvailable(org, "ASCL") && specNotSearchDto.getFromAmount() > 0 && specNotSearchDto.getToAmount() > 0) {
				List<Map<String, Object>> outstandingAmountByPropNoList = getOutstandingAmountByPropNoList(orgid, specNotSearchDto, assMasList);
				assMasList.forEach(assMas -> {
					outstandingAmountByPropNoList.forEach(outstandingAmountByPropNo ->{
						if(outstandingAmountByPropNo.get("propNo").toString().equals(assMas.getAssNo())) {
							Double outStandingAmount = Double.valueOf(outstandingAmountByPropNo.get(assMas.getAssNo()).toString());
							Long propFinYearId = 0L;
							if(outstandingAmountByPropNo.get("finYear") != null) {
								propFinYearId = (Long) outstandingAmountByPropNo.get("finYear");
							}
							Date dueDate = null;
							if(outstandingAmountByPropNo.get("dueDate") != null) {
								dueDate = (Date) outstandingAmountByPropNo.get("dueDate");
							}
							if ((outStandingAmount != null && outStandingAmount >= specNotSearchDto.getFromAmount()
									&& outStandingAmount <= specNotSearchDto.getToAmount())
									&& (propFinYearId.equals(finYearId))) {
								NoticeGenSearchDto notGen = new NoticeGenSearchDto();
								notGen.setPropertyNo(assMas.getAssNo());
								notGen.setApplicationId(assMas.getApmApplicationId());
								notGen.setAssId(assMas.getProAssId());
								if (StringUtils.isNotBlank(assMas.getAssOldpropno()))
									notGen.setOldPropertyNo(assMas.getAssOldpropno());
								notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgid));
								List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
								for (AssesmentOwnerDtlEntity owner : ownerList) {
									if (owner.getAssoOType() != null
											&& owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
										notGen.setOwnerName(owner.getAssoOwnerName());
										notGen.setMobileNo(owner.getAssoMobileno());
									}
								}
								notGen.setGenNotCheck(MainetConstants.Y_FLAG);
								if(CollectionUtils.isNotEmpty(assMas.getAssesmentOwnerDetailEntityList())) {
									notGen.setEmailId(assMas.getAssesmentOwnerDetailEntityList().get(0).geteMail());
								}
								notGenShowList.add(notGen);
							}
						}
					});

				});
			} else {
				List<Map<String, Object>> outstandingAmountByPropNoList = getOutstandingAmountByPropNoList(orgid, specNotSearchDto, assMasList);
				assMasList.forEach(assMas -> {
					outstandingAmountByPropNoList.forEach(outstandingAmountByPropNo ->{
                       if(outstandingAmountByPropNo.get("propNo").toString().equals(assMas.getAssNo())) {
                    		Long propFinYearId = 0L;
        					if(outstandingAmountByPropNo.get("finYear") != null) {
        						propFinYearId = (Long) outstandingAmountByPropNo.get("finYear");
        					}
        					Date dueDate = null;
        					if(outstandingAmountByPropNo.get("dueDate") != null) {
        						dueDate = (Date) outstandingAmountByPropNo.get("dueDate");
        					}
        					if ((propFinYearId.equals(finYearId))) {
        						NoticeGenSearchDto notGen = new NoticeGenSearchDto();
        						notGen.setPropertyNo(assMas.getAssNo());
        						notGen.setApplicationId(assMas.getApmApplicationId());
        						notGen.setAssId(assMas.getProAssId());
        						if (StringUtils.isNotBlank(assMas.getAssOldpropno()))
        							notGen.setOldPropertyNo(assMas.getAssOldpropno());
        						notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgid));
        						List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();
        						for (AssesmentOwnerDtlEntity owner : ownerList) {
        							if (owner.getAssoOType() != null
        									&& owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {
        								notGen.setOwnerName(owner.getAssoOwnerName());
        								notGen.setMobileNo(owner.getAssoMobileno());
        							}
        						}
        						notGen.setGenNotCheck(MainetConstants.Y_FLAG);
        						if(CollectionUtils.isNotEmpty(assMas.getAssesmentOwnerDetailEntityList())) {
        							notGen.setEmailId(assMas.getAssesmentOwnerDetailEntityList().get(0).geteMail());
        						}
        						notGenShowList.add(notGen);
        					}else {
        						if(specNotSearchDto.getSpecNotSearchType().equals("SM")) {
        							NoticeGenSearchDto notGen = new NoticeGenSearchDto();
            						if(!propFinYearId.equals(finYearId)) {
            							notGen.setCurrentBillNotExistFlag(MainetConstants.FlagY);
            						}
            						if(dueDate != null && Utility.compareDate(new Date(),dueDate)) {
            							notGen.setDueDateNotCrossFlag(MainetConstants.FlagY);
            						}
            						notGenShowList.add(notGen);
        						}
        					}
						}
					});

				});
			}

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
        List<String> propNoList = saveNoticeMaster(notGenSearchDtoList, org, empId, noticeType, cal.getTime(), finYearId);
        assesmentMstRepository.updateAssessFlagOfPropList(org.getOrgid(), propNoList, typelookUp.getLookUpCode());
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
            String[] propNo = specNotSearchDto.getOldPropertyNo().split(MainetConstants.operator.COMMA);
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
                if (StringUtils.isNotBlank(assMas.getAssOldpropno()))
                    notGen.setOldPropertyNo(assMas.getAssOldpropno());
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
    public List<PrintBillMaster> getNoticePrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation,
            Long notType) {
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
                    printDto.setFromDate(UtilityService.convertDateToDDMMYYYY(mainbill.getBmFromdt()));
                    printDto.setToDate(UtilityService.convertDateToDDMMYYYY(mainbill.getBmTodt()));
                    printDto.setYearL("Year From " + printDto.getFromDate() + " To "
                            + printDto.getToDate());
                    printDto.setPropNoV(mainbill.getPropNo());
                    printDto.setOldPropNoV(assessment.getAssOldpropno());
                    final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB,
                            organisation);

                    if (assessment.getAssWard1() != null) {
                        printDto.setWard1L(lookupList.get(0).getLookUpDesc());// need to change label from prefix for hierarchical
                        printDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard1(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard2() != null) {
                        printDto.setWard2L(lookupList.get(1).getLookUpDesc());
                        printDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard2(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard3() != null) {
                        printDto.setWard3L(lookupList.get(2).getLookUpDesc());
                        printDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard3(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard4() != null) {
                        printDto.setWard4L(lookupList.get(3).getLookUpDesc());
                        printDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard4(), organisation)
                                .getDescLangFirst());
                    }
                    if (assessment.getAssWard5() != null) {
                        printDto.setWard5L(lookupList.get(4).getLookUpDesc());
                        printDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(assessment.getAssWard5(), organisation)
                                .getDescLangFirst());
                    }
                    AtomicDouble totalAlv = new AtomicDouble(0);
                    AtomicDouble totalRv = new AtomicDouble(0);
                    AtomicDouble totalcv = new AtomicDouble(0);

                    List<AssesmentDetailEntity> assesmentDetailEntityList = assessment.getAssesmentDetailEntityList();

                    if (assesmentDetailEntityList != null && !assesmentDetailEntityList.isEmpty()) {
                        assesmentDetailEntityList.forEach(detail -> {
                            if (detail.getAssdAlv() != null)
                                totalAlv.getAndAdd(detail.getAssdAlv());
                            if (detail.getAssdRv() != null)
                                totalRv.getAndAdd(detail.getAssdRv());
                            if (detail.getAssdCv() != null)
                                totalcv.addAndGet(detail.getAssdCv());
                        });
                    }
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
                        printDetails
                                .setTaxNameV(tbTaxMasService.findTaxByTaxIdAndOrgId(billdet.getTaxId(), organisation.getOrgid())
                                        .getTaxDesc());
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
                    printDto.setBalExcessAdjTotalV(
                            asExecssAmtService.getAdvanceAmount(mainbill.getPropNo(), organisation.getOrgid()));
                    // printDto.setTotPayableV(Double.toString(printDto.getTotTatAmt()));
                    printDto.setTotPayableV(printDto.getTotTatAmt());
                    printDto.setAmtToWordsV(Utility.convertNumberToWord(Double
                            .valueOf(printDto.getTotTatAmt())));

                    LookUp typelookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(notType,
                            organisation);

                    LookUp demandlookUp = CommonMasterUtility.getValueFromPrefixLookUp("DN", "NTY",
                            organisation);
                    AssessNoticeMasterEntity demandnotice = assessNoticeMasterRepository
                            .getNoticesDetailsByPropNo(organisation.getOrgid(), mainbill.getPropNo(), demandlookUp.getLookUpId());
                    if (typelookUp.getLookUpCode().equals("DN")) {
                        LookUp lookup = CommonMasterUtility.getDefaultValueByOrg("DNC",
                                organisation);
                        printDto.setNoOfDays(lookup.getOtherField());
                        printDto.setBillNoV(demandnotice.getMnNotno().toString());
                        printDto.setBillDateV(UtilityService.convertDateToDDMMYYYY(demandnotice.getMnNotdt()));
                        printDto.setBillDueDateV(UtilityService.convertDateToDDMMYYYY(demandnotice.getMnDuedt()));
                    } else if (typelookUp.getLookUpCode().equals("WN")) {
                        AssessNoticeMasterEntity warrannotice = assessNoticeMasterRepository
                                .getNoticesDetailsByPropNo(organisation.getOrgid(), mainbill.getPropNo(), notType);
                        LookUp lookup = CommonMasterUtility.getDefaultValueByOrg("WNC",
                                organisation);
                        printDto.setNoOfDays(lookup.getOtherField());
                        printDto.setBillNoV(warrannotice.getMnNotno().toString());
                        printDto.setBillDateV(UtilityService.convertDateToDDMMYYYY(warrannotice.getMnNotdt()));
                        printDto.setBillDueDateV(UtilityService.convertDateToDDMMYYYY(warrannotice.getMnDuedt()));
                        printDto.setDemandNoV(demandnotice.getMnNotno().toString());
                        printDto.setDemandDateV(UtilityService.convertDateToDDMMYYYY(demandnotice.getMnNotdt()));
                        printDto.setDemandDueDateV(UtilityService.convertDateToDDMMYYYY(demandnotice.getMnDuedt()));
                    }
                    printDto.setWarrantOfficer(ApplicationSession.getInstance().getMessage("warrant.notice.officerName"));
                    printDto.setAddressOfWarrantOff(ApplicationSession.getInstance().getMessage("warrant.notice.officerAddress"));
                    printDto.setFooterName(ApplicationSession.getInstance().getMessage("notice.footer"));
                    // printDto.setFooterSing(footerSing);
                    printDto.setBottomNotePath(ApplicationSession.getInstance().getMessage("notice.bottomNote"));
                    printDto.setOrgType(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(organisation.getOrgCpdId(), organisation)
                            .getLookUpDesc());
                    printMas.add(printDto);
                });
            }
        }
        return printMas;
    }

    @Override
    public void sendSmsAndMail(Organisation organisation, int langId, LookUp noticeDesc,
            NoticeGenSearchDto notDto, Date notDueDate, Long userId,List<File> filesForAttach) {
        String url = "";
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(notDto.getEmailId());
        dto.setMobnumber(notDto.getMobileNo());
        dto.setPropertyNo(notDto.getPropertyNo());
        dto.setNoticeDate(Utility.dateToString(new Date()));
        dto.setNoticeNo(notDto.getNoticeNo().toString());
        dto.setDueDt(Utility.dateToString(notDueDate));
        dto.setUserId(userId);
        dto.setFilesForAttach(filesForAttach);
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
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, organisation, langId);
    }
    
	private List<Map<String, Object>> getOutstandingAmountByPropNoList(Long orgid, NoticeGenSearchDto specNotSearchDto,
			List<AssesmentMastEntity> assMasList) {
		List<Map<String, Object>> OutstandingAmountByPropNoList = new ArrayList<Map<String,Object>>();
		List<String>   propertyNoList = new ArrayList<String>();
		assMasList.forEach(assDto -> {
			propertyNoList.add(assDto.getAssNo());
		});
		List<Object[]> propNoListByOutstandingWise = propertyMainBillRepository.fetchAllBillByPropNoBetweenOutStanding(
				propertyNoList, orgid);
		if(CollectionUtils.isNotEmpty(propNoListByOutstandingWise)) {
			propNoListByOutstandingWise.forEach(propNo ->{
				Map<String, Object> OutstandingAmountByPropNo = new HashMap<String, Object>();
				if(propNo[1] != null) {
					OutstandingAmountByPropNo.put(propNo[0].toString(), propNo[1]);
				}else {
					OutstandingAmountByPropNo.put(propNo[0].toString(), 0.0);
				}
				OutstandingAmountByPropNo.put("finYear", propNo[2]);
				OutstandingAmountByPropNo.put("dueDate", propNo[3]);
				OutstandingAmountByPropNo.put("propNo", propNo[0]);
				OutstandingAmountByPropNoList.add(OutstandingAmountByPropNo);
			});
		}
		return OutstandingAmountByPropNoList;
	}
	
	@Override
	@Transactional
	public ObjectionDetailsDto getObjectionDetailByRefNo(Long orgId, String objectionReferenceNumber, String deptCode) {
		ObjectionDetailsDto objection = new ObjectionDetailsDto();
		List<AssesmentOwnerDtlEntity> assmentOwnerDtlList = assesmentMstRepository.getPrimaryOwnerDetailByPropertyNo(orgId, objectionReferenceNumber);
		for(AssesmentOwnerDtlEntity obj : assmentOwnerDtlList) {
			
			objection.setMobileNo(obj.getAssoMobileno());
			objection.seteMail(obj.geteMail());
			objection.setGender(obj.getGenderId());
			objection.setUid(obj.getAssoAddharno());
			objection.setfName(obj.getAssoOwnerName());
			break;
		}
		List<String> propNoList = new ArrayList<>();
		propNoList.add(objectionReferenceNumber);
		List<AssesmentMastEntity> assemntList = assesmentMstRepository.getAllObjNotPropByPropNo(orgId, propNoList);
		if(assemntList != null && assemntList.size() > 0) {
			AssesmentMastEntity entity = assemntList.get(0);
			objection.setLocId(entity.getLocId());
			objection.setAddress(entity.getAssAddress());
			
			Long dpDeptId = departmentService.getDepartmentIdByDeptCode(deptCode);
			NoticeMasterDto noticeDto = noticeMasterService.getMaxNoticeByRefNo(orgId, objectionReferenceNumber, dpDeptId);
			objection.setNoticeNo(noticeDto.getNotNo());
			
		}
		
		return objection;
	}

	@Override
	public List<String> fetchPropertyDemandNoticeofCurrentYear(Long noticeType, Long orgid, Date fromDate,
			Date toDate) {
		List<String> propNoList = assessNoticeMasterRepository.fetchPropertyDemandNoticeofCurrentYear(noticeType, orgid, fromDate, toDate);
		return propNoList;
	}

}
