package com.abm.mainet.property.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WorkFlow.BpmpDelayPeriods;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.MainBillDetHistEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.MainBillMasHistEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDetailDto;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class PropertyAuthorizationServiceImpl implements PropertyAuthorizationService {

    /**
     * 
     */
	private static final long serialVersionUID = 7452617995391237633L;
    
    private static final Logger LOGGER = Logger.getLogger(PropertyAuthorizationServiceImpl.class);

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private BillMasterCommonService billMasterCommonService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private AssesmentMstRepository assesmentMstRepository;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IObjectionDetailsService iObjectionDetailsService;

    @Autowired
    private PropertyNoticeService propertyNoticeService;

    @Autowired
    private PropertyBillGenerationService propertyBillGenerationService;

    @Autowired
    private IOrganisationService organisationService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private CFCAttechmentRepository cfcAttechmentRepository;
    
    @Autowired
    private ServiceMasterService serviceMaster;
    
    @Autowired
    private com.abm.mainet.property.repository.PropertyMainBillRepository PropertyMainBillRepository;
    
    /*
     * Save Authorization without Edit
     */
    @Override
    @Transactional
    public void SaveAuthorizationData(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            List<TbBillMas> billMasList, WorkflowTaskAction workFlowActionDto,

            Long orgId, Employee emp, Long deptId,
            int langId, Long serviceId) {
    	Organisation org = new Organisation();
    	org.setOrgid(orgId);
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        String clmAprStatus="";
        // save data into Main if last level Authorizer with Approved decision
        if (!provAssDtoList.isEmpty()
                && provAssDtoList.get(0).getAssAutStatus().equals(MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG)) {
            authStatus = MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG;
        }
        boolean lastCheckerTask = iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId());
        String proBillFlag = "";
        if(CollectionUtils.isNotEmpty(billMasList)) {
        	TbBillMas billMas = billMasList.get(0);
        	if(billMas != null && billMas.getBmGenDes() != null) {
        		proBillFlag = billMas.getBmGenDes();
        	}
        }
		if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) && lastCheckerTask) {
            iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,emp.getEmpId(),proAssDto);
            iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(billMasList,MainetConstants.FlagW);
            assesmentMastService.saveAssesmentMastByDto(provAssDtoList, orgId, emp.getEmpId(),
                    authStatus, proAssDto.getLgIpMac());
            
            if((!Utility.isEnvPrefixAvailable(org, "PSCL")) || (!StringUtils.equals(MainetConstants.Y_FLAG, proBillFlag))) {
            	 List<MainBillMasEntity> mainBillList = propertyMainBillService.saveAndUpdateMainBill(billMasList, orgId,
                         emp.getEmpId(),
                         authStatus, proAssDto.getLgIpMac());
                 List<MainBillMasEntity> mainBillListForDupBill = new ArrayList<MainBillMasEntity>();
                 provAssDtoList.forEach(assMastDto ->{
                 	mainBillList.forEach(maainBill ->{
                 		if(assMastDto.getFaYearId().equals(maainBill.getBmYear())) {
                 			mainBillListForDupBill.add(maainBill);
                     	}
                 	});
                 	
                 });
                 new Thread(() -> propertyBillGenerationService.saveDuplicateBill(mainBillListForDupBill, proAssDto, langId, orgId)).start();
                 iProvisionalBillService.deleteProvisionalBillsById(billMasList);
            }
           
            primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
            clmAprStatus=MainetConstants.FlagY;
        }else if (StringUtils.isNotBlank(workFlowActionDto.getDecision())
				&& workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
        	Long finYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(new Date());
        	ProvisionalAssesmentMstDto currentYearAssesmentDto= assesmentMastService.getCurrentYearAssesment(finYearId, proAssDto.getAssNo(), proAssDto.getApmApplicationId());
        	if(currentYearAssesmentDto != null) {
        		List<ProvisionalAssesmentMstDto> provList = new ArrayList<ProvisionalAssesmentMstDto>();
            	provList.add(currentYearAssesmentDto);
            	assesmentMastService.saveAssesmentMastByDto(provList, orgId, emp.getEmpId(),
                        authStatus, proAssDto.getLgIpMac());
        	}
        	
			List<TbBillMas> billHistList = getBillHistoryByApplicationId(proAssDto.getAssNo(),
					proAssDto.getApmApplicationId());
        	if(CollectionUtils.isNotEmpty(billHistList)) {
        		List<MainBillMasEntity> mainBillList = propertyMainBillService.saveAndUpdateMainBill(billHistList, orgId,
                        emp.getEmpId(),
                        authStatus, proAssDto.getLgIpMac());
        	}
        	iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,emp.getEmpId(),proAssDto);
            iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(billMasList,MainetConstants.FlagW);
        	iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
            iProvisionalBillService.deleteProvisionalBillsById(billMasList);
            clmAprStatus=MainetConstants.FlagN;
		}
        //Decentralized checklist to update clmAprStatus 
		if (lastCheckerTask && StringUtils.isNotBlank(clmAprStatus)) {
			ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
			if (StringUtils.equals(service.getSmCheckListReq(), MainetConstants.FlagN)) {
				cfcAttechmentRepository.updateClmAprStatus(clmAprStatus, orgId, proAssDto.getApmApplicationId());
			}
		}
        callWorkflow(proAssDto.getApmApplicationId(), workFlowActionDto, orgId, emp, serviceId);
    }

    @Override
    @Transactional
    public void callWorkflow(Long apmAppId, WorkflowTaskAction workFlowActionDto, Long orgId,
            Employee emp, Long serviceId) {
        List<Long> attachementId = iChecklistVerificationService.fetchAttachmentIdByAppid(apmAppId, orgId);
        workFlowActionDto.setAttachementId(attachementId);
        LOGGER.info("Started work flow");
        iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, UserSession.getCurrent().getOrganisation().getOrgid(),
                serviceId);
        LOGGER.info("Ended work flow");
    }

    /**
     * 
     * Saving Data in Case of Authorization with Edit(Changes by authorizer) 1)first copy data from provisional to History 2)save
     * new calculated data into provisional 3)If authorizer is last level then save new Data to Main Table
     * @return
     * 
     */
    @Override
    @Transactional
    public List<Long> SaveAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto, List<Long> finYearList, List<TbBillMas> billMasList,
            List<TbBillMas> oldBillMasList, WorkflowTaskAction workFlowActionDto, Employee emp, Long deptId, int langId,
            List<BillReceiptPostingDTO> demandLevelRebate, BillDisplayDto earlyPayRebate,
            BillDisplayDto advanceDto, BillDisplayDto surCharge) {
        final Organisation org = new Organisation();
        Long orgId = proAssDto.getOrgId();
        List<Long> bmIds = null;
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
        org.setOrgid(orgId);
        Map<Long, Long> assIdMap = null;

        // copy data from provisional to History
        iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,emp.getEmpId(),proAssDto);
        iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(oldBillMasList,MainetConstants.FlagW);
        
        //121613 - No entry inside main and provisional tables if workflow is rejected
		if (StringUtils.isNotBlank(workFlowActionDto.getDecision())
				&& workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
			iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
			iProvisionalBillService.deleteProvisionalBillsById(oldBillMasList);
		} else {
        LookUp billDeletionInactive = null;
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", org);
        } catch (Exception e) {

        }
        if ((!Utility.isEnvPrefixAvailable(org, "SKDCL")) && (billDeletionInactive == null
                || StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                || StringUtils.equals(billDeletionInactive.getOtherField(),
                        MainetConstants.FlagN))) {
            iReceiptEntryService.inActiveAllRebetReceiptByAppNo(orgId, proAssDto.getApmApplicationId(), deptId,
                    MainetConstants.Property.REBATE_DELETE_REMARK,
                    emp.getEmpId());
        }
        // Bill knock off
        setUniqueIdentiFromOldBillToNewBill(billMasList, oldBillMasList);
        if (proAssDto.getBillPaidAmt() > 0) {
            final TbBillMas last = billMasList.get(billMasList.size() - 1);
            if (earlyPayRebate != null && earlyPayRebate.getTotalTaxAmt() != null) {
                final Map<Long, Double> details = new HashMap<>(0);
                final Map<Long, Long> billDetails = new HashMap<>(0);
                List<BillReceiptPostingDTO> earlyPayRebateList = billMasterCommonService.updateBillData(billMasList,
                        earlyPayRebate.getTotalTaxAmt().doubleValue(), details, billDetails, org,
                        null, null);
                earlyPayRebateList.forEach(rebate -> {// as there is always one early Payment rebate
                    rebate.setTaxId(earlyPayRebate.getTaxId());
                    rebate.setRmNarration(earlyPayRebate.getTaxDesc());
                });
                selfAssessmentService.saveDemandLevelRebate(proAssDto, emp.getEmpId(), deptId, oldBillMasList, earlyPayRebateList,
                        org,
                        provBillList);
            }
            final Map<Long, Double> details = new HashMap<>(0);
            final Map<Long, Long> billDetails = new HashMap<>(0);
            if (surCharge != null && proAssDto.getBillPaidAmt() > 0 && surCharge.getTotalTaxAmt().doubleValue() > 0) {
                proAssDto.setBillPaidAmt(proAssDto.getBillPaidAmt() - surCharge.getTotalTaxAmt().doubleValue());
            }

            billMasterCommonService.updateBillData(billMasList, proAssDto.getBillPaidAmt(), details, billDetails,
                    org, null, null);

            double advAmtFromActualPay = last.getExcessAmount();

            if (advAmtFromActualPay > 0) {
                if (advanceDto != null && advanceDto.getTotalTaxAmt() != null && advanceDto.getTotalTaxAmt().doubleValue() > 0) {
                    advAmtFromActualPay += advanceDto.getTotalTaxAmt().doubleValue();
                }
                asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), advAmtFromActualPay, null, emp.getUserId());
                selfAssessmentService.savePureAdvancePayment(proAssDto, emp.getUserId(), deptId, org, proAssDto.getLgIpMac(),
                        advAmtFromActualPay);
            } else {
                if (advanceDto != null && advanceDto.getTotalTaxAmt() != null && advanceDto.getTotalTaxAmt().doubleValue() > 0) {
                    billMasterCommonService.updateBillData(billMasList, advanceDto.getTotalTaxAmt().doubleValue(), details,
                            billDetails, org, null, null);
                    double newAdvAmt = last.getExcessAmount();
                    asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), newAdvAmt, null, emp.getUserId());
                    selfAssessmentService.savePureAdvancePayment(proAssDto, emp.getUserId(), deptId, org, proAssDto.getLgIpMac(),
                            advAmtFromActualPay);
                }
            }
        }
        // save new change data into provisional
        boolean lastTaskInChecker = iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId());
		if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
					&& lastTaskInChecker) {
        	
        	String noticePeriod = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.SNC, org)
                    .getOtherField();
            addInactiveEntryInObjection(proAssDto, deptId, emp, proAssDto.getLgIpMac(), noticePeriod);
            NoticeMasterDto noticeMasDto = propertyNoticeService.saveNoticeApplicableForObjection(proAssDto.getAssNo(),
                    proAssDto.getApmApplicationId(), proAssDto.getSmServiceId(), orgId,
                    emp,
                    "SP", MainetConstants.Property.propPref.SNC, deptId,proAssDto.getFlatNo());         
        	setSplNotDate( noticeMasDto,  proAssDto);            
            // If authorizer is last level then save new Data to Main Table
            proAssDto.setAssStatus(MainetConstants.Property.AssStatus.SPEC_NOTICE);
            proAssDto.setAssAutDate(new Date());
            assIdMap = assesmentMastService.saveAndUpdateAssessmentMast(proAssDto, provAssDtoList,
                    orgId, emp.getEmpId(), MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG);
            String proBillFlag = "";
            if(CollectionUtils.isNotEmpty(billMasList)) {
            	TbBillMas billMas = billMasList.get(0);
            	if(billMas != null && billMas.getBmGenDes() != null) {
            		proBillFlag = billMas.getBmGenDes();
            	}
            }
            if((!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))|| (!StringUtils.equals(MainetConstants.Y_FLAG, proBillFlag))) {
            List<MainBillMasEntity> mainBillList = propertyMainBillService.saveAndUpdateMainBillWithKeyGen(billMasList, orgId,
                        emp.getEmpId(), proAssDto.getAssNo(),
                        assIdMap, proAssDto.getLgIpMac());
            new Thread(() -> propertyBillGenerationService.saveDuplicateBill(mainBillList, proAssDto, langId, orgId)).start();
            iProvisionalBillService.deleteProvisionalBillsById(oldBillMasList);
            }
            primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
                                           
            //97303
            List<File> filesForAttach = new ArrayList<File>();
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.DWE)) {
				List<SpecialNoticeReportDto> specNotDtoList = setDtoForSpecialNotPrinting(noticeMasDto, proAssDto,
						noticePeriod,UserSession.getCurrent().getOrganisation());
				Map<String, Object> map = new HashMap<>();
				String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
						+ MainetConstants.FILE_PATH_SEPARATOR;
				String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;
				map.put("SUBREPORT_DIR", subReportSource);
				map.put("imgPath", imgpath);
				String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
				String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport"
						+ MainetConstants.FILE_PATH_SEPARATOR + jrxmlName;
				String fileName = ReportUtility.generateReportFromCollectionUtility(null, null, jrxmlFileLocation, map,
						specNotDtoList, UserSession.getCurrent().getEmployee().getEmpId());
			
				if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
					if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
					} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE,
								MainetConstants.QUAD_FORWARD_SLACE);
					}
					final File file = new File(
							Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR + fileName);					
					filesForAttach.add(file);
				}
			}
            						
            ///
            // D#104615 here send Message of SpecialNoticeGeneration.html template with property no, notice due date, ORGNAME
            sendSmsAndMail(org, noticeMasDto, proAssDto, langId, emp,filesForAttach);
            workFlowActionDto.setIsObjectionAppealApplicable(true);
			workFlowActionDto.setSignalExpiryDelay(noticePeriod);
			workFlowActionDto.setSignalExpiryDelayUnit(BpmpDelayPeriods.DAY);
        } else {
            List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
            assIdMap = iProvisionalAssesmentMstService.updateProvisionalAssessment(proAssDto, provAssDtoList,
                    orgId, emp.getEmpId(), MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG, finYearList, provAssEntList);
            bmIds = iProvisionalBillService.saveAndUpdateProvisionalBill(billMasList, orgId, emp.getEmpId(), proAssDto.getAssNo(),
                    assIdMap, null, proAssDto.getLgIpMac());
        }
        //Decentralized checklist to update clmAprStatus 
        if (lastTaskInChecker) {
        	String clmAprStatus="";
        	if(StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.APPROVED)) {
        		clmAprStatus=MainetConstants.FlagY;
        	}else if(StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.REJECTED)) {
        		clmAprStatus=MainetConstants.FlagN;
        	}
			ServiceMaster service = serviceMaster.getServiceMaster(proAssDto.getSmServiceId(), orgId);
			if (StringUtils.equals(service.getSmCheckListReq(), MainetConstants.FlagN)) {
				cfcAttechmentRepository.updateClmAprStatus(clmAprStatus, orgId, proAssDto.getApmApplicationId());
			}
		}
        selfAssessmentService.saveDemandLevelRebate(proAssDto, emp.getEmpId(), deptId, oldBillMasList, demandLevelRebate, org,
                provBillList);
        }
        callWorkflow(proAssDto.getApmApplicationId(), workFlowActionDto, orgId, emp, proAssDto.getSmServiceId());
        return bmIds;
    }
    
	public void setSplNotDate(NoticeMasterDto noticeMasDto, ProvisionalAssesmentMstDto proAssDto) {
		Calendar notDuedt = Calendar.getInstance();
		notDuedt.setTime(noticeMasDto.getNotDuedt());
		notDuedt.set(Calendar.HOUR, 0);
		notDuedt.set(Calendar.MINUTE, 0);
		notDuedt.set(Calendar.SECOND, 0);
		notDuedt.set(Calendar.HOUR_OF_DAY, 0);
		proAssDto.setSplNotDueDate(notDuedt.getTime());
	}
    
	public List<SpecialNoticeReportDto> setDtoForSpecialNotPrinting(NoticeMasterDto notMaster,
			ProvisionalAssesmentMstDto assMast, String noticePeriod, Organisation org) {

		List<SpecialNoticeReportDto> specNotDtoList = new ArrayList<>();
		Department department = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
				MainetConstants.FlagA);

		String deptName = department.getDpDeptdesc();

		SpecialNoticeReportDto speNotDto = new SpecialNoticeReportDto();

		if (deptName != null) {
			speNotDto.setDeptNameL(deptName);
		}
		speNotDto.setOrgName(org.getONlsOrgname());
		speNotDto.setOrgNameReg(org.getoNlsOrgnameMar());
		speNotDto.setLetterHeading("Special Notice");

		speNotDto.setNoticeNoL("Notice No");
		speNotDto.setSpecialNoticeDueDays(noticePeriod);
		speNotDto.setNoticeNoV(notMaster.getNotNo().toString());
		speNotDto.setNoticeDateL("Notice Date");

		LocalDate notDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MainetConstants.DATE_FRMAT);
		String notDueDateStr = notDate.format(formatter);
		speNotDto.setNoticeDateV(notDueDateStr);
		speNotDto.setTo("To");
		
		String name = "";
		String ownerRegName="";
		if (assMast.getBillMethod() != null && MainetConstants.FlagI.equals(
				CommonMasterUtility.getNonHierarchicalLookUpObject(assMast.getBillMethod(), org).getLookUpCode())) {
			for (ProvisionalAssesmentDetailDto det : assMast.getProvisionalAssesmentDetailDtoList()) {
				name += det.getOccupierName() + "/";
				ownerRegName+=det.getOccupierNameReg()+ "/";
			}
		} else {
			List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMast.getProvisionalAssesmentOwnerDtlDtoList();
			for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
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
		speNotDto.setAddressV(assMast.getAssAddress() + ", " + assMast.getAssPincode());

		final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, org);

		if (assMast.getAssWard1() != null && lookupList.size() >= 1) {
			speNotDto.setWard1L(lookupList.get(0).getLookUpDesc());// need to change label from prefix for
																	// hierarchical
			speNotDto.setWard1V(
					CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard1(), org).getDescLangFirst());
		}
		if (assMast.getAssWard2() != null && lookupList.size() >= 2) {
			speNotDto.setWard2L(lookupList.get(1).getLookUpDesc());
			speNotDto.setWard2V(
					CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard2(), org).getDescLangFirst());
		}
		if (assMast.getAssWard3() != null && lookupList.size() >= 3) {
			speNotDto.setWard3L(lookupList.get(2).getLookUpDesc());
			speNotDto.setWard3V(
					CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard3(), org).getDescLangFirst());
		}
		if (assMast.getAssWard4() != null && lookupList.size() >= 4) {
			speNotDto.setWard4L(lookupList.get(3).getLookUpDesc());
			speNotDto.setWard4V(
					CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard4(), org).getDescLangFirst());
		}
		if (assMast.getAssWard5() != null && lookupList.size() >= 5) {
			speNotDto.setWard5L(lookupList.get(4).getLookUpDesc());
			speNotDto.setWard5V(
					CommonMasterUtility.getHierarchicalLookUp(assMast.getAssWard5(), org).getDescLangFirst());
		}
		if (assMast.getFirstAssessmentDate() != null) {
			String assDate = Utility.dateToString(assMast.getFirstAssessmentDate(), MainetConstants.DATE_FORMAT_UPLOAD);
        	speNotDto.setFirstAssessmentDate(assDate);
		}

		if (assMast.getTppSurveyNumber() != null) {
			speNotDto.setSurveyNo(assMast.getTppSurveyNumber());
		}
		if (assMast.getTppPlotNo() != null) {
			speNotDto.setPlotNo(assMast.getTppPlotNo());
		}
		long numberOfYears=0;
		if (assMast.getAssAcqDate() != null) {
			LocalDate accqDate = assMast.getAssAcqDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
		List<SpecialNoticeReportDetailDto> specNotDetDtoList = new ArrayList<>();

		for (ProvisionalAssesmentDetailDto det : assMast.getProvisionalAssesmentDetailDtoList()) {
			SpecialNoticeReportDetailDto specNotDetDto = new SpecialNoticeReportDetailDto();
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
            
			LookUp constructionClass = CommonMasterUtility.getNonHierarchicalLookUpObject(det.getAssdConstruType(),
					org);
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
			if (det.getCarpetArea() != null) {
				specNotDetDto.setCarpetArea(det.getCarpetArea().toString());
				totalCarpetArea += det.getCarpetArea();
			}
			if (det.getActualRent() != null) {
				specNotDetDto.setActualRent(det.getActualRent().toString());
			}
			if (assMast.getPropLvlRoadType() != null) {
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

		return specNotDtoList;
	}

    @Override
    @Transactional
    public void SaveAuthorizationWithEditForDES(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto,
            List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList, WorkflowTaskAction workFlowActionDto,
            Long orgId, Employee emp, Long deptId,
            int langId, Long serviceId, String ipAddress) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<TbBillMas> newBillMasList = null;
        if (billMasList != null && !billMasList.isEmpty()) {
            newBillMasList = billMasterCommonService.generateBillForDataEntry(billMasList, org);
        }

        // copy data from provisional to History
        iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,emp.getEmpId(),proAssDto);
        iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(oldBillMasList,MainetConstants.FlagW);
        iReceiptEntryService.inActiveAllRebetReceiptByAppNo(orgId, proAssDto.getApmApplicationId(), deptId,
                MainetConstants.Property.REBATE_DELETE_REMARK,
                emp.getEmpId());
        // Bill knock off
        if (newBillMasList != null && !newBillMasList.isEmpty()) {
            setUniqueIdentiFromOldBillToNewBill(newBillMasList, oldBillMasList);
            if (proAssDto.getBillPaidAmt() > 0) {
                final TbBillMas last = newBillMasList.get(newBillMasList.size() - 1);
                final Map<Long, Double> details = new HashMap<>(0);
                final Map<Long, Long> billDetails = new HashMap<>(0);
                billMasterCommonService.updateBillData(newBillMasList, proAssDto.getBillPaidAmt(), details, billDetails, org,
                        null, null);
                double oldAdvAmt = asExecssAmtService.getAdvanceAmount(proAssDto.getAssNo(), orgId);
                double newAdvAmt = last.getExcessAmount();

                if (newAdvAmt > 0 || oldAdvAmt > 0) {
                    if (newAdvAmt < 0) {
                        newAdvAmt = 0;
                    }
                    if (newAdvAmt > oldAdvAmt) {
                        double advAmt = newAdvAmt - oldAdvAmt;
                        asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), advAmt, null, emp.getUserId());
                    } else if (newAdvAmt < oldAdvAmt) {
                        asExecssAmtService.inactiveAllAdvPayEnrtyByPropNo(proAssDto.getAssNo(), orgId);
                        if (Math.abs(newAdvAmt) > 0) {
                            asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), Math.abs(newAdvAmt), null,
                                    emp.getUserId());
                        }
                    }
                }
            }
        }
        // save new change data into provisional
        iProvisionalAssesmentMstService.updateProvisionalAssessmentForSingleAssessment(proAssDto, orgId,
                emp.getEmpId(), ipAddress);
        iProvisionalBillService.saveAndUpdateProvisionalBill(newBillMasList, orgId, emp.getEmpId(), proAssDto.getAssNo(),
                null, null, proAssDto.getLgIpMac());

        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) &&
                iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
            // If authorizer is last level then save new Data to Main Table
            proAssDto.setAssAutStatus(MainetConstants.Property.AuthStatus.AUTH);
            proAssDto.setAssStatus(MainetConstants.Property.AssStatus.NORMAL);
            assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDES(proAssDto, orgId, emp.getEmpId(), ipAddress);
            propertyMainBillService.SaveAndUpdateMainBillOnlyForDES(newBillMasList, orgId, emp.getEmpId(),
                    MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG, proAssDto.getLgIpMac());
            primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
            iProvisionalBillService.deleteProvisionalBillsById(newBillMasList);
            sendMail(proAssDto, org, langId, emp.getEmpId());

        }
    }

    @Override
    public void sendMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId, Long userId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
        dto.setMobnumber(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
        dto.setUserId(userId);
        String type=null;
        ServiceMaster service = serviceMasterRepository.getServiceMaster(provAsseMstDto.getSmServiceId(),
                provAsseMstDto.getOrgId());
        if (langId == MainetConstants.MARATHI) {
            dto.setServName(service.getSmServiceNameMar());
        } else {
            dto.setServName(service.getSmServiceName());
        }
        if (provAsseMstDto.getApmApplicationId() != null) {
            dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        }
        if (provAsseMstDto.getRemarks() != null) {
            dto.setRemarks(provAsseMstDto.getRemarks());
        }
        if( MainetConstants.WorkFlow.Decision.APPROVED.equals(provAsseMstDto.getDecision())) {
        	type=PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
        }
        if( MainetConstants.WorkFlow.Decision.REJECTED.equals(provAsseMstDto.getDecision())) {
        	type=PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED_MSG;
        }
        dto.setPropertyNo(provAsseMstDto.getAssNo());
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "SelfAssessmentAuthorization.html",
        		type, dto, organisation, langId);
    }

    @Override
    @Transactional
    public void saveNewPropertyRegAuthorization(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto,
            WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp, Long deptId,
            int langId, Long serviceId) {
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        // save data into Main if last level Authorizer with Approved decision
        if (!provAssDtoList.isEmpty()
                && provAssDtoList.get(0).getAssAutStatus().equals(MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG)) {
            authStatus = MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG;
        }
        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
                && iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
            assesmentMastService.saveAssesmentMastByDto(provAssDtoList, orgId, emp.getEmpId(),
                    authStatus, proAssDto.getLgIpMac());
            primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
            Organisation org = new Organisation();
            org.setOrgid(orgId);
            sendMail(proAssDto, org, langId, emp.getEmpId());
        }
    }

    @Override
    @Transactional
    public void addInactiveEntryInObjection(ProvisionalAssesmentMstDto proAssDto, Long deptId, Employee emp, String ipAddress,
            String noticePeriod) {
        ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();
        objectionDetailsDto.setApmApplicationId(proAssDto.getApmApplicationId());
        objectionDetailsDto.setObjectionReferenceNumber(proAssDto.getAssNo());
        objectionDetailsDto.setObjectionDeptId(deptId);
        objectionDetailsDto.setServiceId(proAssDto.getSmServiceId());
        objectionDetailsDto.setObjectionStatus(MainetConstants.STATUS.INACTIVE);
        objectionDetailsDto.setObjectionDetails(MainetConstants.Objection.OBJ_DETAIL_INACTIVE);
        objectionDetailsDto.setOrgId(proAssDto.getOrgId());
        objectionDetailsDto
                .setObjTime(TimeUnit.MILLISECONDS.convert(Long.valueOf(noticePeriod), TimeUnit.DAYS));

        iObjectionDetailsService.saveAndUpateObjectionMaster(objectionDetailsDto, emp.getEmpId(), ipAddress);
    }

    @Override
    @Transactional
    public void saveNewPropertyRegAuthorizationWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto,
            List<Long> finYearList,
            WorkflowTaskAction workFlowActionDto,
            Long orgId, Employee emp, Long deptId,
            int langId, Long serviceId) {
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        // copy data from provisional to History
        iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provAssDtoList,emp.getEmpId(),proAssDto);
        List<ProvisionalAssesmentMstEntity> provAssEntList = new ArrayList<>();
        iProvisionalAssesmentMstService.updateProvisionalAssessment(proAssDto, provAssDtoList,
                orgId, emp.getEmpId(), MainetConstants.Property.AuthStatus.AUTH, finYearList, provAssEntList);

        if (workFlowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) &&
                iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
            // If authorizer is last level then save new Data to Main Table
            assesmentMastService.saveAssesmentMastByEntity(provAssEntList, orgId, emp.getEmpId(),
                    MainetConstants.Property.AuthStatus.AUTH, proAssDto.getLgIpMac());
            primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provAssDtoList);
            sendMail(proAssDto, org, langId, emp.getEmpId());
        }
    }

    @Override
    @Transactional
    public Map<Long, BillDisplayDto> getRebete(Long applicationNo,
            Long orgId, Long deptId) {
        List<TbServiceReceiptMasEntity> rebeteList = iReceiptEntryService.getRebateByAppNo(orgId, applicationNo, deptId);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        rebeteList.forEach(rebete -> {
            rebete.getReceiptFeeDetail().forEach(det -> {
                BillDisplayDto existDto = taxWiseRebate.get(det.getTaxId());
                if (existDto == null) {
                    BillDisplayDto dto = new BillDisplayDto();
                    TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(det.getTaxId(), orgId);
                    dto.setDisplaySeq(taxMas.getTaxDisplaySeq());
                    dto.setTaxCategoryId(taxMas.getTaxCategory1());
                    dto.setTaxDesc(taxMas.getTaxDesc());
                    dto.setTaxId(det.getTaxId());
                    dto.setTotalTaxAmt(det.getRfFeeamount());
                    dto.setCurrentYearTaxAmt(det.getRfFeeamount());
                    dto.setArrearsTaxAmt(new BigDecimal(0));
                    taxWiseRebate.put(det.getTaxId(), dto);
                } else {
                    existDto.setTotalTaxAmt(
                            BigDecimal.valueOf(existDto.getTotalTaxAmt().doubleValue() + det.getRfFeeamount().doubleValue()));
                    existDto.setCurrentYearTaxAmt(BigDecimal
                            .valueOf(existDto.getCurrentYearTaxAmt().doubleValue() + det.getRfFeeamount().doubleValue()));
                }
            });
        });
        return taxWiseRebate;

    }

    @Override
    @Transactional
    public void saveUploadedFile(ProvisionalAssesmentMstDto proAssDto,
            Long orgId, Employee emp, Long deptId,
            int langId, Long serviceId) {
        RequestDTO reqDto = new RequestDTO();
        reqDto.setApplicationId(proAssDto.getApmApplicationId());
        reqDto.setDeptId(deptId);
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(emp.getEmpId());
        fileUploadService.doFileUpload(proAssDto.getDocs(), reqDto);
    }

    /*
     * copy all primary key of old bill into new bill
     */
    @Override
    @Transactional
    public void setUniqueIdentiFromOldBillToNewBill(List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList) {
        List<Long> deleteTaxes = new ArrayList<>(0);
        oldBillMasList.forEach(oldBill -> billMasList.stream()
                .filter(newBill -> newBill.getBmFromdt().equals(oldBill.getBmFromdt()))
                .forEach(newBill -> {
                    newBill.setBmIdno(oldBill.getBmIdno());
                    newBill.setBmNo(oldBill.getBmNo());
                    newBill.setAssId(oldBill.getAssId());
                    newBill.setPropNo(oldBill.getPropNo());
                    newBill.setLmoddate(oldBill.getLmoddate());
                    newBill.setLgIpMac(oldBill.getLgIpMac());
                    newBill.setUserId(oldBill.getUserId());
                    for (TbBillDet oldDet : oldBill.getTbWtBillDet()) {
                        boolean found = false;
                        for (TbBillDet newDet : newBill.getTbWtBillDet()) {
                            if (newDet.getTaxId().equals(oldDet.getTaxId())) {
                                newDet.setBdBilldetid(oldDet.getBdBilldetid());
                                newDet.setLmoddate(oldDet.getLmoddate());
                                newDet.setLgIpMac(oldDet.getLgIpMac());
                                newDet.setUserId(oldDet.getUserId());
                                newDet.setBdCurTaxamtAuth(oldDet.getBdCurTaxamt());// extra columns for Account Posting
                                newDet.setBdCurBalTaxamtAuth(oldDet.getBdCurBalTaxamt());
                                newDet.setBdPrvArramtAuth(oldDet.getBdPrvArramt());
                                newDet.setBdPrvBalArramtAuth(oldDet.getBdPrvBalArramt());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            if (!deleteTaxes.contains(oldDet.getBdBilldetid())) {
                                deleteTaxes.add(oldDet.getBdBilldetid());
                            }
                        }
                    }
                }));
        if (!deleteTaxes.isEmpty()) {
        	try {
        		iProvisionalBillService.deleteDetailsTaxes(deleteTaxes);
        	}catch (Exception e) {
        		LOGGER.error("Exception while deleting bill detail" + e);
			}
        }
    }

    @Override
    public ProvisionalAssesmentMstDto getAssesmentMstDtoForDisplay(List<ProvisionalAssesmentMstDto> assMasDtoList) {
        final List<ProvisionalAssesmentDetailDto> provAssDetDtoList = new ArrayList<>();
        final List<ProvisionalAssesmentOwnerDtlDto> provAssOwnDtoList = new ArrayList<>();
        ProvisionalAssesmentMstDto assMstDtoNew = new ProvisionalAssesmentMstDto();
        if (CollectionUtils.isNotEmpty(assMasDtoList)) {
        ProvisionalAssesmentMstDto assMstDto = assMasDtoList.get(assMasDtoList.size() - 1);
        BeanUtils.copyProperties(assMstDto, assMstDtoNew);
        assMasDtoList.forEach(provAssMast -> {
            provAssMast.getProvisionalAssesmentDetailDtoList().forEach(provDetEnt -> {
                ProvisionalAssesmentDetailDto provDetDto = new ProvisionalAssesmentDetailDto();
                final List<ProvisionalAssesmentFactorDtlDto> provAssFactDtoList = new ArrayList<>();
                BeanUtils.copyProperties(provDetEnt, provDetDto);
                provDetEnt.getProvisionalAssesmentFactorDtlDtoList().forEach(proAssFactEnt -> {
                    ProvisionalAssesmentFactorDtlDto proAssFactDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(proAssFactEnt, proAssFactDto);
                    provAssFactDtoList.add(proAssFactDto);
                });
                provDetDto.setProvisionalAssesmentFactorDtlDtoList(provAssFactDtoList);
                
                //97207 By arun
                final List<PropertyRoomDetailsDto> roomDetailList = new ArrayList<>();
                provDetEnt.getRoomDetailsDtoList().forEach(roomDetail ->{
                	PropertyRoomDetailsDto roomDetailDto=new PropertyRoomDetailsDto();
                	BeanUtils.copyProperties(roomDetail, roomDetailDto);
                	roomDetailList.add(roomDetailDto);
                });
                provDetDto.setRoomDetailsDtoList(roomDetailList);
                //
                
                provAssDetDtoList.add(provDetDto);
            });
            provAssMast.getProvisionalAssesmentOwnerDtlDtoList().forEach(provOwnEnt -> {
                ProvisionalAssesmentOwnerDtlDto provAssOwnDto = new ProvisionalAssesmentOwnerDtlDto();
                BeanUtils.copyProperties(provOwnEnt, provAssOwnDto);
                provAssOwnDtoList.add(provAssOwnDto);
            });
        });
        }
        assMstDtoNew.setProvisionalAssesmentDetailDtoList(provAssDetDtoList);
        assMstDtoNew.setProvisionalAssesmentOwnerDtlDtoList(provAssOwnDtoList);
        return assMstDtoNew;
    }

    @Transactional
    public void saveAmalgamationAuthorizationWithEdit(
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, ProvisionalAssesmentMstDto oldProvisionalAssesmentMstDt,
            WorkflowTaskAction workflowActionDto,
            long orgId, Employee emp, Long deptId, int languageId, Long serviceId, List<Long> finYearList, String editflag) {
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        if ("Y".equals(editflag)) {
            authStatus = MainetConstants.Property.AuthStatus.AUTH_WITH_CHNG;
        }
        List<ProvisionalAssesmentMstDto> provList = new ArrayList<>(0);
        provList.add(oldProvisionalAssesmentMstDt);
        if (workflowActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED) &&
                iWorkFlowTypeService.isLastTaskInCheckerTaskList(workflowActionDto.getTaskId())) {
            iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provList,emp.getEmpId(),provisionalAssesmentMstDto);
            provList.clear();

            List<String> childPropNoList = new ArrayList<>();
            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(det -> {
                if (det.getPropNo() != null && !det.getPropNo().equals(provisionalAssesmentMstDto.getAssNo())) {
                    childPropNoList.add(det.getPropNo());
                }
            });

            // If authorizer is last level then save new Data to Main Table
            List<ProvisionalAssesmentMstEntity> childProp = iProvisionalAssesmentMstService
                    .getListOfPropertyByListOfPropNos(orgId, childPropNoList);
            childProp.forEach(childPropNo -> {
                List<AssesmentMastEntity> childMainMst = assesmentMastService.getAssessmentMstByPropNo(orgId,
                        childPropNo.getAssNo());
                updateInactiveAmalgamatedProp(childMainMst, provisionalAssesmentMstDto);
                primaryPropertyService.updatePrimayPropertyInactive(orgId, childPropNo.getAssNo(), emp.getEmpId());
            });
            provList.add(provisionalAssesmentMstDto);
            assesmentMastService.saveAndUpdateAssessmentMast(provisionalAssesmentMstDto, provList,
                    orgId, emp.getEmpId(), authStatus);
            primaryPropertyService.savePropertyMaster(provisionalAssesmentMstDto, orgId, emp.getEmpId());

            iProvisionalAssesmentMstService.deleteProvisionalAssessWithDtoById(provList);// delete parent from provisional

            iProvisionalAssesmentMstService.deleteProvisionalAssessWithEntById(childProp);// delete all child from provisional
            Organisation org = new Organisation();
            org.setOrgid(orgId);
			provisionalAssesmentMstDto.setDecision(workflowActionDto.getDecision());
            sendMail(provisionalAssesmentMstDto, org, languageId, emp.getEmpId());
        } else {
            iProvisionalAssesmentMstService.copyDataFromProvisionalPropDetailToHistory(provList,emp.getEmpId(),provisionalAssesmentMstDto);
            iProvisionalAssesmentMstService.saveProvisionalAssessmentFromDtoForAmlg(provisionalAssesmentMstDto,
                    orgId, emp.getEmpId(), null);
        }
    }

    private void updateInactiveAmalgamatedProp(List<AssesmentMastEntity> childMainMst,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        childMainMst.forEach(mst -> {
            mst.setAssActive(MainetConstants.STATUS.INACTIVE);
            mst.setUpdatedBy(provisionalAssesmentMstDto.getCreatedBy());
            mst.setUpdatedDate(provisionalAssesmentMstDto.getCreatedDate());
            mst.setLgIpMacUpd(provisionalAssesmentMstDto.getLgIpMac());
            mst.setParentProp(provisionalAssesmentMstDto.getAssNo());
            assesmentMstRepository.save(mst);
        });
    }

    @Override
    @Transactional
    public void SaveApprovalData(List<ProvisionalAssesmentMstDto> provAssDtoList, ProvisionalAssesmentMstDto proAssDto,
            WorkflowTaskAction workFlowActionDto,
            Long orgId, Employee emp, Long deptId,
            int langId) {
        String authStatus = MainetConstants.Property.AuthStatus.AUTH;
        workFlowActionDto.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        assesmentMastService.saveAssesmentMastByDto(provAssDtoList, orgId, emp.getEmpId(),
                authStatus, proAssDto.getLgIpMac());
        primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());

        callWorkflow(proAssDto.getApmApplicationId(), workFlowActionDto, orgId, emp, proAssDto.getSmServiceId());
    }

    @Override
    @Transactional
    public List<Long> SaveApprovalDataWithEdit(List<ProvisionalAssesmentMstDto> provAssDtoList,
            ProvisionalAssesmentMstDto proAssDto,
            List<Long> finYearList,
            List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList, WorkflowTaskAction workFlowActionDto, Employee emp,
            Long deptId, List<BillReceiptPostingDTO> demandLevelRebate, BillDisplayDto earlyPayRebate,
            BillDisplayDto advanceDto, BillDisplayDto surCharge,int langId) {
        final Organisation org = new Organisation();
        Long orgId = proAssDto.getOrgId();
        List<Long> bmIds = null;
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
        org.setOrgid(orgId);
        Map<Long, Long> assIdMap = null;

        // copy data from provisional to History
        iReceiptEntryService.inActiveAllRebetReceiptByAppNo(orgId, proAssDto.getApmApplicationId(), deptId,
                MainetConstants.Property.REBATE_DELETE_REMARK,
                emp.getEmpId());
        // Bill knock off
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			setUniqueIdentiFromOldBillToNewBillSkdcl(billMasList, oldBillMasList);
		} else {
			setUniqueIdentiFromOldBillToNewBill(billMasList, oldBillMasList);
		}
        if (proAssDto.getBillPaidAmt() > 0) {
            final TbBillMas last = billMasList.get(billMasList.size() - 1);
            if (earlyPayRebate != null && earlyPayRebate.getTotalTaxAmt() != null && earlyPayRebate.getTotalTaxAmt().doubleValue() >0) {
                final Map<Long, Double> details = new HashMap<>(0);
                final Map<Long, Long> billDetails = new HashMap<>(0);
                List<BillReceiptPostingDTO> earlyPayRebateList = billMasterCommonService.updateBillData(billMasList,
                        earlyPayRebate.getTotalTaxAmt().doubleValue(), details, billDetails, org,
                        null, null);
                earlyPayRebateList.forEach(rebate -> {// as there is always one early Payment rebate
                    rebate.setTaxId(earlyPayRebate.getTaxId());
                    rebate.setRmNarration(earlyPayRebate.getTaxDesc());
                });
                selfAssessmentService.saveDemandLevelRebate(proAssDto, emp.getEmpId(), deptId, oldBillMasList, earlyPayRebateList,
                        org,
                        provBillList);
            }
            final Map<Long, Double> details = new HashMap<>(0);
            final Map<Long, Long> billDetails = new HashMap<>(0);
            if (surCharge != null && proAssDto.getBillPaidAmt() > 0 && surCharge.getTotalTaxAmt().doubleValue() > 0) {
                proAssDto.setBillPaidAmt(proAssDto.getBillPaidAmt() - surCharge.getTotalTaxAmt().doubleValue());
            }
            billMasterCommonService.updateBillData(billMasList, proAssDto.getBillPaidAmt(), details, billDetails, org, null,
                    null);
            double advAmtFromActualPay = last.getExcessAmount();

            if (advAmtFromActualPay > 0) {
                if (advanceDto != null && advanceDto.getTotalTaxAmt() != null && advanceDto.getTotalTaxAmt().doubleValue() > 0) {
                    advAmtFromActualPay += advanceDto.getTotalTaxAmt().doubleValue();
                }
                asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), advAmtFromActualPay, null, emp.getUserId());
                selfAssessmentService.savePureAdvancePayment(proAssDto, emp.getUserId(), deptId, org, proAssDto.getLgIpMac(),
                        advAmtFromActualPay);
            } else {
                if (advanceDto != null && advanceDto.getTotalTaxAmt() != null && advanceDto.getTotalTaxAmt().doubleValue() > 0) {
                    billMasterCommonService.updateBillData(billMasList, advanceDto.getTotalTaxAmt().doubleValue(), details,
                            billDetails, org, null, null);
                    double newAdvAmt = last.getExcessAmount();
                    asExecssAmtService.addEntryInExcessAmt(orgId, proAssDto.getAssNo(), newAdvAmt, null, emp.getUserId());
                    selfAssessmentService.savePureAdvancePayment(proAssDto, emp.getUserId(), deptId, org, proAssDto.getLgIpMac(),
                            advAmtFromActualPay);
                }
            }
        }
        NoticeMasterDto noticeMasDto =null;
		if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.SNR)) {
        noticeMasDto = propertyNoticeService.saveNoticeApplicableForObjection(proAssDto.getAssNo(),
				proAssDto.getApmApplicationId(), proAssDto.getSmServiceId(), orgId, emp, "SP",
				MainetConstants.Property.propPref.SNC, deptId, proAssDto.getFlatNo());
        proAssDto.setSplNotDueDate(Utility.removeTimeFromDatestatic(noticeMasDto.getNotDuedt()));
		}
		
        workFlowActionDto.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        assIdMap = assesmentMastService.saveAndUpdateAssessmentMast(proAssDto, provAssDtoList,
                orgId, emp.getEmpId(), MainetConstants.Property.AuthStatus.AUTH);
        propertyMainBillService.saveAndUpdateMainBillWithKeyGen(billMasList, orgId, emp.getEmpId(), proAssDto.getAssNo(),
                assIdMap, proAssDto.getLgIpMac());
        primaryPropertyService.savePropertyMaster(proAssDto, orgId, emp.getEmpId());
        selfAssessmentService.saveDemandLevelRebate(proAssDto, emp.getEmpId(), deptId, oldBillMasList, demandLevelRebate, org,
                provBillList);
        
        ///118973
		if (Utility.isEnvPrefixAvailable(org, PrefixConstants.ENV.SNR)) {
					
			List<File> filesForAttach = new ArrayList<File>();
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.DWE)) {
				String noticePeriod = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.SNC, org)
	                    .getOtherField();
				List<SpecialNoticeReportDto> specNotDtoList = setDtoForSpecialNotPrinting(noticeMasDto, proAssDto,
						noticePeriod,UserSession.getCurrent().getOrganisation());
				Map<String, Object> map = new HashMap<>();
				String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
						+ MainetConstants.FILE_PATH_SEPARATOR;
				String imgpath = Filepaths.getfilepath() + MainetConstants.IMAGES + MainetConstants.FILE_PATH_SEPARATOR;
				map.put("SUBREPORT_DIR", subReportSource);
				map.put("imgPath", imgpath);
				String jrxmlName = "PropertyTaxSpecialNotice.jrxml";
				String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport"
						+ MainetConstants.FILE_PATH_SEPARATOR + jrxmlName;
				String fileName = ReportUtility.generateReportFromCollectionUtility(null, null, jrxmlFileLocation, map,
						specNotDtoList, UserSession.getCurrent().getEmployee().getEmpId());

				if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
					if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
					} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
						fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE,
								MainetConstants.QUAD_FORWARD_SLACE);
					}
					final File file = new File(
							Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR + fileName);
					filesForAttach.add(file);
				}
			}
			sendSmsAndMail(org, noticeMasDto, proAssDto, langId, emp, filesForAttach);
		}
        //
		workFlowActionDto.setDecisionFavorFlag(MainetConstants.FlagD);
		LOGGER.info("updated decision favou flag as D="+workFlowActionDto.getDecisionFavorFlag() );
        callWorkflow(proAssDto.getApmApplicationId(), workFlowActionDto, orgId, emp, proAssDto.getSmServiceId());
        return bmIds;
    }

    private void sendSmsAndMail(Organisation organisation, NoticeMasterDto noticeMasterDto, ProvisionalAssesmentMstDto proAssDto,
            int langId, Employee emp, List<File> filesForAttach) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        
		if (proAssDto.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
				.getNonHierarchicalLookUpObject(proAssDto.getBillMethod(), organisation).getLookUpCode())) {
			dto.setEmail(proAssDto.getProvisionalAssesmentDetailDtoList().get(0).getOccupierEmail());
			dto.setMobnumber(proAssDto.getProvisionalAssesmentDetailDtoList().get(0).getOccupierMobNo());
		} else {
			dto.setEmail(proAssDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).geteMail());
			dto.setMobnumber(proAssDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
		}
        dto.setPropertyNo(proAssDto.getAssNo());
        dto.setOrgName(emp.getOrganisation().getONlsOrgname());
        dto.setNoticeDate(Utility.dateToString(new Date()));
        dto.setNoticeNo(noticeMasterDto.getNotNo());
        dto.setDueDt(Utility.dateToString(noticeMasterDto.getNotDuedt()));
        dto.setUserId(emp.getEmpId());
        dto.setFilesForAttach(filesForAttach);
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "SpecialNoticeGeneration.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, organisation, langId);
    }
    
	@Override
	@Transactional
	public void setUniqueIdentiFromOldBillToNewBillSkdcl(List<TbBillMas> billMasList, List<TbBillMas> oldBillMasList) {
		List<Long> deleteTaxes = new ArrayList<>(0);
		oldBillMasList.forEach(oldBill -> billMasList.stream()
				.filter(newBill -> newBill.getBmFromdt().equals(oldBill.getBmFromdt())).forEach(newBill -> {
					newBill.setBmIdno(oldBill.getBmIdno());
					newBill.setBmNo(oldBill.getBmNo());
					newBill.setAssId(oldBill.getAssId());
					newBill.setPropNo(oldBill.getPropNo());
					newBill.setLmoddate(oldBill.getLmoddate());
					newBill.setLgIpMac(oldBill.getLgIpMac());
					newBill.setUserId(oldBill.getUserId());
					for (TbBillDet oldDet : oldBill.getTbWtBillDet()) {
						boolean found = false;
						for (TbBillDet newDet : newBill.getTbWtBillDet()) {
							if (newDet.getTaxId().equals(oldDet.getTaxId())) {
								newDet.setBdBilldetid(oldDet.getBdBilldetid());
								newDet.setLmoddate(oldDet.getLmoddate());
								newDet.setLgIpMac(oldDet.getLgIpMac());
								newDet.setUserId(oldDet.getUserId());
								newDet.setBdCurTaxamtAuth(oldDet.getBdCurTaxamt());// extra columns for Account Posting
								newDet.setBdCurBalTaxamtAuth(oldDet.getBdCurBalTaxamt());
								newDet.setBdPrvArramtAuth(oldDet.getBdPrvArramt());
								newDet.setBdPrvBalArramtAuth(oldDet.getBdPrvBalArramt());
								found = true;
								break;
							}
						}
						if (!found) {
							if (!deleteTaxes.contains(oldDet.getBdBilldetid())) {
								deleteTaxes.add(oldDet.getBdBilldetid());
							}
						}
					}
				}));
		if (!deleteTaxes.isEmpty()) {
			propertyMainBillService.deleteMainBillDetById(deleteTaxes);
		}
	}

	@Override
	public List<TbBillMas> getBillHistoryByApplicationId(String propNo, Long applicationId) {
		List<TbBillMas> billMasList = new ArrayList<TbBillMas>();
		List<MainBillMasHistEntity> billMasHistList = PropertyMainBillRepository.getMainBillHistoryByApplicationId(propNo, applicationId);
		if(CollectionUtils.isNotEmpty(billMasHistList)) {
			billMasHistList.forEach(billMasHist ->{
				TbBillMas billMas = new TbBillMas();
				BeanUtils.copyProperties(billMasHist, billMas);
				List<MainBillDetHistEntity> billDetHistList = PropertyMainBillRepository
						.getMainBillDetHistByBmIdNo(billMasHist.getBmIdnoHistId(), billMasHist.getBmIdno(),
								billMasHist.getApmApplicationId());
				if(CollectionUtils.isNotEmpty(billDetHistList)) {
					billDetHistList.forEach(billDetHist ->{
						TbBillDet det = new TbBillDet();
						BeanUtils.copyProperties(billDetHist, det);
						billMas.getTbWtBillDet().add(det);
					});
				}
				
				billMasList.add(billMas);
			});
		}
		return billMasList;
	}

}
