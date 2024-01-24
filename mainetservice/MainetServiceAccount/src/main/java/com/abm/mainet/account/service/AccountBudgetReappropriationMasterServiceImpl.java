
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetReappropriationMasterDao;
import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;
import com.abm.mainet.account.domain.AccountBudgetReappropriationTrMasterEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.account.dto.AccountBudgetReappropriationTrMasterBean;
import com.abm.mainet.account.mapper.AccountBudgetReappropriationMasterServiceMapper;
import com.abm.mainet.account.mapper.AccountBudgetReappropriationTrMasterServiceMapper;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.account.repository.BudgetReappropriationRepository;
import com.abm.mainet.account.repository.BudgetReappropriationTransactionRepository;
import com.abm.mainet.account.utility.AccountWorkflowUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetReappropriationMasterServiceImpl implements AccountBudgetReappropriationMasterService {

    @Resource
    private BudgetReappropriationRepository budgetReappropriationRepository;
    @Resource
    private BudgetReappropriationTransactionRepository accountBudgetReappropriationTrMasterJpaRepository;
    @Resource
    private AccountBudgetReappropriationMasterServiceMapper accountBudgetReappropriationMasterServiceMapper;
    @Resource
    private AccountBudgetReappropriationTrMasterServiceMapper accountBudgetReappropriationTrMasterServiceMapper;
    @Resource
    private BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;
    @Resource
    private BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;
    @Resource
    private AccountBudgetReappropriationMasterDao accountBudgetReappropriationMasterDao;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;
    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;
    
    @Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;
    
    @Resource
	private DepartmentService departmentService;
    @Autowired
	private IAttachDocsDao iAttachDocsDao;
    

    private static Logger LOGGER = Logger.getLogger(AccountBudgetReappropriationMasterServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetReappropriationMasterBean> findByFinancialId(final Long faYearid, final String budgIdentifyFlag,
            final Long orgId) {
        final List<AccountBudgetReappropriationMasterEntity> entities = budgetReappropriationRepository.findByFinancialId(
                faYearid,
                budgIdentifyFlag, orgId);
        final List<AccountBudgetReappropriationMasterBean> bean = new ArrayList<>();
        for (final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity : entities) {
            bean.add(accountBudgetReappropriationMasterServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanEntityToAccountBudgetReappropriationMasterBean(
                            accountBudgetReappropriationMasterEntity));
        }
        return bean;
    }

    @Override
    @Transactional(readOnly = true)
	public List<AccountBudgetReappropriationMasterBean> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
			final Long dpDeptid, final Long prBudgetCodeid, final String budgIdentifyFlag, final Long fieldId,
			final Long orgId) {
		final List<AccountBudgetReappropriationMasterEntity> entities = accountBudgetReappropriationMasterDao
				.findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid, budgIdentifyFlag,fieldId, orgId);
		final List<AccountBudgetReappropriationMasterBean> bean = new ArrayList<>();
		for (final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity : entities) {
			bean.add(accountBudgetReappropriationMasterServiceMapper
					.mapAccountBudgetReappropriationMasterBeanEntityToAccountBudgetReappropriationMasterBean(
							accountBudgetReappropriationMasterEntity));
		}
		return bean;
	}

    @Override
    @Transactional
    public AccountBudgetReappropriationMasterBean saveBudgetReappropriationFormData(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        if (tbAcBudgetReappropriation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            reappropriationRevenueStoredData(tbAcBudgetReappropriation, LanguageId, Organisation);
        } else if (tbAcBudgetReappropriation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            reappropriationExpenditureStoredData(tbAcBudgetReappropriation, LanguageId, Organisation);
        }
        return tbAcBudgetReappropriation;
    }

    /**
     * @param tbAcBudgetReappropriation
     * @throws ParseException
     */
    private void reappropriationExpenditureStoredData(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        AccountBudgetReappropriationMasterBean acBudgetReappropriation = null;
        final List<AccountBudgetReappropriationMasterBean> acBudgetReappropriationList = new ArrayList<>();
        AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved = null;
        AccountBudgetReappropriationTrMasterEntity accountBudgetReappropriationTrMasterEntitySaved = null;
        acBudgetReappropriation = new AccountBudgetReappropriationMasterBean();
        if ((tbAcBudgetReappropriation.getPaAdjid() != null) && (tbAcBudgetReappropriation.getPaAdjid() > 0l)) {
            acBudgetReappropriation.setPaAdjid(tbAcBudgetReappropriation.getPaAdjid());
        }
        if (tbAcBudgetReappropriation.getBudgetTranRefNo() != null) {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        String budgTranRefNo = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
        if (tbAcBudgetReappropriation.getPaAdjid() == null) {
        	//Need to configure here
			SequenceConfigMasterDTO configMasterDTO = null;
	        configMasterDTO = seqGenFunctionUtility.loadSequenceData(tbAcBudgetReappropriation.getOrgid(), deptId,
	                MainetConstants.AccountBudgetProjectedExpenditure.TB_AC_PROJECTEDPROVISIONADJ, MainetConstants.AccountBudgetProjectedExpenditure.RP_TRNNO);
	        if (configMasterDTO.getSeqConfigId() == null) {
            budgTranRefNo = generateBudgetTranRefNumber(tbAcBudgetReappropriation.getOrgid(),
                    tbAcBudgetReappropriation.getFaYearid()); 
            budgTranRefNo=tbAcBudgetReappropriation.getOrgShortName()+"/RP/"+Utility.dateToString(new Date()) + "/" + budgTranRefNo;
	        }else {
	        	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
	        	 budgTranRefNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
	        }
	        acBudgetReappropriation.setBudgetTranRefNo(budgTranRefNo);
            tbAcBudgetReappropriation.setBudgetTranRefNo(budgTranRefNo);       
        } else {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        ServiceMaster service = serviceMasterService.getServiceByShortName("BR", tbAcBudgetReappropriation.getOrgid());
     // This condition is for if service is active then only workflow code should be execute. Added by srikanth.
        Organisation org = new Organisation();
        org.setOrgid(tbAcBudgetReappropriation.getOrgid());
        WorkflowMas workflowMas=null;
        LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), org);
        if (serviceActiveLookUp != null && !StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
            /*WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
                    service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
                    new BigDecimal(tbAcBudgetReappropriation.getBugprojExpBeanList().get(0).getExpenditureAmt()), null,
                    tbAcBudgetReappropriation.getWorkFlowLevel1(), tbAcBudgetReappropriation.getWorkFlowLevel2(),
                    tbAcBudgetReappropriation.getWorkFlowLevel3(), tbAcBudgetReappropriation.getWorkFlowLevel4(),
                    tbAcBudgetReappropriation.getWorkFlowLevel5());*/
        	
        	List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
					service.getTbDepartment().getDpDeptid(), service.getSmServiceId());
			if(CollectionUtils.isNotEmpty(worKFlowList)) {
			for(WorkflowMas mas:worKFlowList) {
			   if(mas.getStatus().equalsIgnoreCase("Y")) {
				if(mas.getToAmount()!=null ) {
					workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
							null, null, tbAcBudgetReappropriation.getWorkFlowLevel1(),
							tbAcBudgetReappropriation.getWorkFlowLevel2(), tbAcBudgetReappropriation.getWorkFlowLevel3(),
							tbAcBudgetReappropriation.getWorkFlowLevel4(), tbAcBudgetReappropriation.getWorkFlowLevel5());
					break;
				}else {
					workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
							tbAcBudgetReappropriation.getWorkFlowLevel1(), tbAcBudgetReappropriation.getWorkFlowLevel2(),
							tbAcBudgetReappropriation.getWorkFlowLevel3(), tbAcBudgetReappropriation.getWorkFlowLevel4(),
							tbAcBudgetReappropriation.getWorkFlowLevel5());
					break;
				}
			 }
			}
		}else {
			 throw new FrameworkException("Workflow Not Found");
		}
            if (workflowMas != null) {
                try {
                    WorkflowProcessParameter processParameter = AccountWorkflowUtility
                            .prepareInitAccountBudgetReappropriationProcessParameter(tbAcBudgetReappropriation, workflowMas,
                                    budgTranRefNo);
                    if (tbAcBudgetReappropriation.getAuthFlag() == null || tbAcBudgetReappropriation.getAuthFlag().isEmpty()) {
                    	/*By discussed with Srikant*/
                    	acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
                        TaskAssignment assignment = new TaskAssignment();
                        assignment.setActorId(tbAcBudgetReappropriation.getUserId().toString());
                        assignment.setOrgId(tbAcBudgetReappropriation.getOrgid());
                        assignment.setUrl("ReappropriationOfBudgetAuthorization.html");
                        assignment.setDeptId(deptId);
                        processParameter.setRequesterTaskAssignment(assignment);
                        workflowExecutionService.initiateWorkflow(processParameter);
                    } else {
                        workflowExecutionService.updateWorkflow(processParameter);
                    }
                } catch (Exception e) {
                    LOGGER.error("Unsuccessful initiation/updation of task for application : " + budgTranRefNo + e);
                }
            }
        }

        acBudgetReappropriation.setOrgid(tbAcBudgetReappropriation.getOrgid());
        acBudgetReappropriation.setLangId(tbAcBudgetReappropriation.getLangId());
        acBudgetReappropriation.setUserId(tbAcBudgetReappropriation.getUserId());
        acBudgetReappropriation.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        acBudgetReappropriation.setPaEntrydate(new Date());
        acBudgetReappropriation.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        acBudgetReappropriation.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        acBudgetReappropriation.setLgIpMac(tbAcBudgetReappropriation.getLgIpMac());
        acBudgetReappropriation.setLgIpMacUpd(tbAcBudgetReappropriation.getLgIpMacUpd());
        acBudgetReappropriation.setFaYearid(tbAcBudgetReappropriation.getFaYearid());
        acBudgetReappropriation.setCpdBugtypeId(tbAcBudgetReappropriation.getCpdBugtypeId());
        acBudgetReappropriation.setCpdBugSubTypeId(tbAcBudgetReappropriation.getCpdBugSubTypeId());
        acBudgetReappropriation.setBudgetIdentifyFlag(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG);
        
        //updating authorisation flag. If there is no workflow and no authorization then authorization is 'Y' else 'N'
        boolean makerChecker = isMakerChecker(org);
        if(makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	 acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
        }else if(!makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)){
        	 acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
        }
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALUE,
                PrefixConstants.REV_PREFIX, LanguageId, Organisation);
        acBudgetReappropriation.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : tbAcBudgetReappropriation
                .getBugprojExpBeanList()) {
            reappropriationExpenditureStoredDataFirstTable(
                    acBudgetReappropriation,
                    acBudgetReappropriationList,
                    accountBudgetProjectedExpenditureBean, Organisation);
        }
        for (final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterSaved : acBudgetReappropriationList) {
            accountBudgetReappropriationMasterEntitySaved = new AccountBudgetReappropriationMasterEntity();
            accountBudgetReappropriationMasterServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanToAccountBudgetReappropriationMasterEntity(
                            accountBudgetReappropriationMasterSaved, accountBudgetReappropriationMasterEntitySaved);
            /*Defect #96460*/
            accountBudgetReappropriationMasterEntitySaved.setFieldId(tbAcBudgetReappropriation.getFieldId());
            accountBudgetReappropriationMasterEntitySaved = budgetReappropriationRepository
                    .save(accountBudgetReappropriationMasterEntitySaved);
            tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntitySaved.getPaAdjid());
        }
        final List<AccountBudgetReappropriationTrMasterBean> tbacBudgetReappropriationList = new ArrayList<>();
        if(makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	tbAcBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
       }else if(!makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)){
    	   tbAcBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
       }
        for (final AccountBudgetProjectedExpenditureBean acBudgetProjectedExpenditureBean : tbAcBudgetReappropriation
                .getBugprojExpBeanList1()) {
            reappropriationExpenditureStoredDataSecondTable(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntitySaved,
                    tbacBudgetReappropriationList,
                    acBudgetProjectedExpenditureBean, Organisation);
        }
        for (final AccountBudgetReappropriationTrMasterBean accountBudgetReappropriationTrMasterSaved : tbacBudgetReappropriationList) {
            accountBudgetReappropriationTrMasterEntitySaved = new AccountBudgetReappropriationTrMasterEntity();
            accountBudgetReappropriationTrMasterServiceMapper.mapTbAcProjectedprovisionadjTrToTbAcProjectedprovisionadjTrEntity(
                    accountBudgetReappropriationTrMasterSaved, accountBudgetReappropriationTrMasterEntitySaved);
            accountBudgetReappropriationTrMasterEntitySaved = accountBudgetReappropriationTrMasterJpaRepository
                    .save(accountBudgetReappropriationTrMasterEntitySaved);
        }
    }

    /**
     * @param tbAcBudgetReappropriation
     * @param accountBudgetReappropriationMasterEntitySaved
     * @param tbAcBudgetReappropriationList2
     * @param accountBudgetProjectedExpenditureBean1
     */
    private void reappropriationExpenditureStoredDataSecondTable(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved,
            final List<AccountBudgetReappropriationTrMasterBean> acBudgetReappropriationList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean, final Organisation Organisation) {
        AccountBudgetReappropriationTrMasterBean acBudgetReappropriationTr = null;
        acBudgetReappropriationTr = new AccountBudgetReappropriationTrMasterBean();
        acBudgetReappropriationTr.setOrgid(tbAcBudgetReappropriation.getOrgid());
        acBudgetReappropriationTr.setLangId(tbAcBudgetReappropriation.getLangId());
        acBudgetReappropriationTr.setUserId(tbAcBudgetReappropriation.getUserId());
        acBudgetReappropriationTr.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        acBudgetReappropriationTr.setLgIpMac(tbAcBudgetReappropriation.getLgIpMac());
        acBudgetReappropriationTr.setLgIpMacUpd(tbAcBudgetReappropriation.getLgIpMacUpd());
        acBudgetReappropriationTr.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        acBudgetReappropriationTr.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        if (accountBudgetReappropriationMasterEntitySaved.getPaAdjid() != null) {
            acBudgetReappropriationTr.setPaAdjid(accountBudgetReappropriationMasterEntitySaved.getPaAdjid());
        }
        if (accountBudgetProjectedExpenditureBean.getPaAdjidTr() != null) {
            acBudgetReappropriationTr.setPaAdjidTr(accountBudgetProjectedExpenditureBean.getPaAdjidTr());
        }
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic() != null) {
            acBudgetReappropriationTr.setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic());
        }
        final Long orgId = tbAcBudgetReappropriation.getOrgid();
        final Long faYearid = tbAcBudgetReappropriation.getFaYearid();
        acBudgetReappropriationTr.setAuthFlag(MainetConstants.MENU.N);
        acBudgetReappropriationTr.setDpDeptid(accountBudgetProjectedExpenditureBean.getDpDeptid());
        final String budgCodeid = accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstants.BLANK);
        acBudgetReappropriationTr.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        acBudgetReappropriationTr.setProvisionOldamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        acBudgetReappropriationTr.setTransferAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
        acBudgetReappropriationTr.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getRevisedEstamt()));
        acBudgetReappropriationTr.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getPrBalanceAmt()));
        
        //If there is no workflow and no authorization then evised amount should be updated immediately
        if (StringUtils.equals(tbAcBudgetReappropriation.getAuthFlag(), MainetConstants.FlagY)) {
                final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal expenditureAmt = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        acBudgetReappropriationTr.setAuthFlag(MainetConstants.MENU.Y);
                        if (objects[2] == null) {
                            final BigDecimal expDefaultValue = new BigDecimal(0);
                            expenditureAmt = expDefaultValue;
                        }
                        if (objects[2] != null) {
                            expenditureAmt = new BigDecimal(objects[2].toString());
                            expenditureAmt = expenditureAmt.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prExpenditureid = acBudgetReappropriationTr.getPrExpenditureid();
                        BigDecimal revisedEstamt = acBudgetReappropriationTr.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        if (expenditureAmt == null) {
                            // final BigDecimal balanceAmount = revisedEstamt;
                            accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid,
                                    prExpenditureid,
                                    revisedEstamt.toString(), orgId);
                        } else {
                            // final BigDecimal balanceAmount = revisedEstamt.subtract(expenditureAmt);
                            accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid,
                                    prExpenditureid,
                                    revisedEstamt.toString(), orgId);
                        }
                    }
                }
            
        }
        acBudgetReappropriationList.add(acBudgetReappropriationTr);
    }

    /**
     * @param tbAcBudgetReappropriation2
     * @param tbAcBudgetReappropriationList1
     * @param accountBudgetProjectedExpenditureBean
     */
    private void reappropriationExpenditureStoredDataFirstTable(
            final AccountBudgetReappropriationMasterBean tbAccountBudgetReappropriation,
            final List<AccountBudgetReappropriationMasterBean> accountBudgetReappropriationList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean, final Organisation Organisation) {
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExp() != null) {
            tbAccountBudgetReappropriation.setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExp());
        }
        final Long orgId = tbAccountBudgetReappropriation.getOrgid();
        final Long faYearid = tbAccountBudgetReappropriation.getFaYearid();
        final String budgCodeid = accountBudgetProjectedExpenditureBean.getPrExpBudgetCode();
        tbAccountBudgetReappropriation.setDpDeptid(accountBudgetProjectedExpenditureBean.getDpDeptid());
        tbAccountBudgetReappropriation.setRemark(accountBudgetProjectedExpenditureBean.getExpRemark());
        tbAccountBudgetReappropriation
                .setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));
        tbAccountBudgetReappropriation
                .setProvisionOldamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        tbAccountBudgetReappropriation.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getPrBalanceAmt()));
        if (accountBudgetProjectedExpenditureBean.getExpenditureAmt() != null) {
            tbAccountBudgetReappropriation
                    .setTransferAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
        }
        tbAccountBudgetReappropriation
                .setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getRevisedEstamt()));

        //If there is no workflow and no authorization then evised amount should be updated immediately
        if (StringUtils.equals(tbAccountBudgetReappropriation.getAuthFlag(), MainetConstants.FlagY)) {
                final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal expenditureAmt = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        tbAccountBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
                        if (objects[2] == null) {
                            final BigDecimal expDefaultValue = new BigDecimal(0);
                            expenditureAmt = expDefaultValue;
                        }
                        if (objects[2] != null) {
                            expenditureAmt = new BigDecimal(objects[2].toString());
                            expenditureAmt = expenditureAmt.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prExpenditureid = tbAccountBudgetReappropriation.getPrExpenditureid();
                        BigDecimal revisedEstamt = tbAccountBudgetReappropriation.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        if (expenditureAmt == null) {
                            // final BigDecimal balanceAmount = revisedEstamt;
                            accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid,
                                    prExpenditureid,
                                    revisedEstamt.toString(), orgId);
                        } else {
                            // final BigDecimal balanceAmount = revisedEstamt.subtract(expenditureAmt);
                            accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid,
                                    prExpenditureid,
                                    revisedEstamt.toString(), orgId);
                        }
                    }
                }
            
        }
        accountBudgetReappropriationList.add(tbAccountBudgetReappropriation);
    }

    /**
     * @param tbAcBudgetReappropriation
     * @throws ParseException
     */
    private void reappropriationRevenueStoredData(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        AccountBudgetReappropriationMasterBean acBudgetReappropriation = null;
        final List<AccountBudgetReappropriationMasterBean> tbAcBudgetReappropriationList = new ArrayList<>();
        AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved = null;
        AccountBudgetReappropriationTrMasterEntity accountBudgetReappropriationTrMasterEntitySaved = null;
        acBudgetReappropriation = new AccountBudgetReappropriationMasterBean();
        if ((tbAcBudgetReappropriation.getPaAdjid() != null) && (tbAcBudgetReappropriation.getPaAdjid() > 0l)) {
            acBudgetReappropriation.setPaAdjid(tbAcBudgetReappropriation.getPaAdjid());
        }
        if (tbAcBudgetReappropriation.getBudgetTranRefNo() != null) {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        String budgTranRefNo = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
        if (tbAcBudgetReappropriation.getPaAdjid() == null) {
        	SequenceConfigMasterDTO configMasterDTO = null;
	        configMasterDTO = seqGenFunctionUtility.loadSequenceData(tbAcBudgetReappropriation.getOrgid(), deptId,
	        		MainetConstants.AccountBudgetProjectedExpenditure.TB_AC_PROJECTEDPROVISIONADJ, MainetConstants.AccountBudgetProjectedExpenditure.RP_TRNNO);
	        if (configMasterDTO.getSeqConfigId() == null) {
            budgTranRefNo = generateBudgetTranRefNumber(tbAcBudgetReappropriation.getOrgid(),
                    tbAcBudgetReappropriation.getFaYearid());
            budgTranRefNo=tbAcBudgetReappropriation.getOrgShortName()+"/RP/"+Utility.dateToString(new Date()) + "/" + budgTranRefNo;
	        }else {
	        	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
	        	 budgTranRefNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
	        }
	        acBudgetReappropriation.setBudgetTranRefNo(budgTranRefNo);
            tbAcBudgetReappropriation.setBudgetTranRefNo(budgTranRefNo);
        } else {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        ServiceMaster service = serviceMasterService.getServiceByShortName("BR", tbAcBudgetReappropriation.getOrgid());
        Organisation org = new Organisation();
        org.setOrgid(tbAcBudgetReappropriation.getOrgid());
        LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), org);
        WorkflowMas workflowMas=null;
        if (serviceActiveLookUp != null && !StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
            /*WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
                    service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
                    new BigDecimal(tbAcBudgetReappropriation.getBugprojRevBeanList().get(0).getPrCollected()), null,
                    tbAcBudgetReappropriation.getWorkFlowLevel1(), tbAcBudgetReappropriation.getWorkFlowLevel2(),
                    tbAcBudgetReappropriation.getWorkFlowLevel3(), tbAcBudgetReappropriation.getWorkFlowLevel4(),
                    tbAcBudgetReappropriation.getWorkFlowLevel5());*/
        	List<WorkflowMas> worKFlowList = iWorkflowTypeDAO.getAllWorkFlows(service.getOrgid(),
					service.getTbDepartment().getDpDeptid(), service.getSmServiceId());
			if(CollectionUtils.isNotEmpty(worKFlowList)) {
			for(WorkflowMas mas:worKFlowList) {
			   if(mas.getStatus().equalsIgnoreCase("Y")) {
				if(mas.getToAmount()!=null ) {
					workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
							null, null, tbAcBudgetReappropriation.getWorkFlowLevel1(),
							tbAcBudgetReappropriation.getWorkFlowLevel2(), tbAcBudgetReappropriation.getWorkFlowLevel3(),
							tbAcBudgetReappropriation.getWorkFlowLevel4(), tbAcBudgetReappropriation.getWorkFlowLevel5());
					break;
				}else {
					workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
							tbAcBudgetReappropriation.getWorkFlowLevel1(), tbAcBudgetReappropriation.getWorkFlowLevel2(),
							tbAcBudgetReappropriation.getWorkFlowLevel3(), tbAcBudgetReappropriation.getWorkFlowLevel4(),
							tbAcBudgetReappropriation.getWorkFlowLevel5());
					break;
				}
			 }
			}
		}else {
			 throw new FrameworkException("Workflow Not Found");
		}
            
            
            
            if (workflowMas != null) {
                try {
                    WorkflowProcessParameter processParameter = AccountWorkflowUtility
                            .prepareInitAccountBudgetReappropriationProcessParameter(tbAcBudgetReappropriation, workflowMas,
                                    budgTranRefNo);
                    if (tbAcBudgetReappropriation.getAuthFlag() == null || tbAcBudgetReappropriation.getAuthFlag().isEmpty()) {
                    	/*By discussed with Srikant*/
                    	acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
                        TaskAssignment assignment = new TaskAssignment();
                        assignment.setActorId(tbAcBudgetReappropriation.getUserId().toString());
                        assignment.setOrgId(tbAcBudgetReappropriation.getOrgid());
                        assignment.setUrl("ReappropriationOfBudgetAuthorization.html");
                        assignment.setDeptId(deptId);
                        processParameter.setRequesterTaskAssignment(assignment);
                        workflowExecutionService.initiateWorkflow(processParameter);
                    } else {
                        workflowExecutionService.updateWorkflow(processParameter);
                    }
                } catch (Exception e) {
                    LOGGER.error("Unsuccessful initiation/updation of task for application : " + budgTranRefNo + e);
                }
            }
        }

        acBudgetReappropriation.setOrgid(tbAcBudgetReappropriation.getOrgid());
        acBudgetReappropriation.setLangId(tbAcBudgetReappropriation.getLangId());
        acBudgetReappropriation.setUserId(tbAcBudgetReappropriation.getUserId());
        acBudgetReappropriation.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        acBudgetReappropriation.setPaEntrydate(new Date());
        acBudgetReappropriation.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        acBudgetReappropriation.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        acBudgetReappropriation.setLgIpMac(tbAcBudgetReappropriation.getLgIpMac());
        acBudgetReappropriation.setLgIpMacUpd(tbAcBudgetReappropriation.getLgIpMacUpd());
        acBudgetReappropriation.setFaYearid(tbAcBudgetReappropriation.getFaYearid());
        acBudgetReappropriation.setCpdBugtypeId(tbAcBudgetReappropriation.getCpdBugtypeId());
        acBudgetReappropriation.setCpdBugSubTypeId(tbAcBudgetReappropriation.getCpdBugSubTypeId());
        acBudgetReappropriation.setBudgetIdentifyFlag(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG);
        //updating authorisation flag. If there is no work flow and no authorization then authorization is 'Y' else 'N'
        boolean makerChecker = isMakerChecker(org);
        if(makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	 acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
        }else if(!makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)){
        	 acBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
        }
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALUE,
                PrefixConstants.REV_PREFIX, LanguageId, Organisation);
        acBudgetReappropriation.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : tbAcBudgetReappropriation
                .getBugprojRevBeanList()) {
            reappropriationRevenueDataStoredFirstTable(
                    acBudgetReappropriation,
                    tbAcBudgetReappropriationList,
                    accountBudgetProjectedRevenueEntryBean, Organisation);
        }
        for (final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterSaved : tbAcBudgetReappropriationList) {
            accountBudgetReappropriationMasterEntitySaved = new AccountBudgetReappropriationMasterEntity();
            accountBudgetReappropriationMasterServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanToAccountBudgetReappropriationMasterEntity(
                            accountBudgetReappropriationMasterSaved, accountBudgetReappropriationMasterEntitySaved);
            /*Defect #96460*/
            accountBudgetReappropriationMasterEntitySaved.setFieldId(tbAcBudgetReappropriation.getFieldId());
            accountBudgetReappropriationMasterEntitySaved = budgetReappropriationRepository
                    .save(accountBudgetReappropriationMasterEntitySaved);
            tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntitySaved.getPaAdjid());
        }
        if(makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	tbAcBudgetReappropriation.setAuthFlag(MainetConstants.MENU.N);
       }else if(!makerChecker && StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)){
    	   tbAcBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
       }
        final List<AccountBudgetReappropriationTrMasterBean> acBudgetReappropriationList = new ArrayList<>();
        for (final AccountBudgetProjectedRevenueEntryBean acBudgetProjectedRevenueEntryBean : tbAcBudgetReappropriation
                .getBugprojRevBeanList1()) {
            reappropriationRevenueTrDataStoredSecondTable(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntitySaved,
                    acBudgetReappropriationList,
                    acBudgetProjectedRevenueEntryBean, Organisation);
        }
        for (final AccountBudgetReappropriationTrMasterBean accountBudgetReappropriationTrMasterSaved : acBudgetReappropriationList) {
            accountBudgetReappropriationTrMasterEntitySaved = new AccountBudgetReappropriationTrMasterEntity();
            accountBudgetReappropriationTrMasterServiceMapper.mapTbAcProjectedprovisionadjTrToTbAcProjectedprovisionadjTrEntity(
                    accountBudgetReappropriationTrMasterSaved, accountBudgetReappropriationTrMasterEntitySaved);
            accountBudgetReappropriationTrMasterEntitySaved = accountBudgetReappropriationTrMasterJpaRepository
                    .save(accountBudgetReappropriationTrMasterEntitySaved);
        }
    }

    /**
     * @param tbAcBudgetReappropriation
     * @param accountBudgetReappropriationMasterEntitySaved
     * @param tbAcBudgetReappropriationList1
     * @param accountBudgetProjectedRevenueEntryBean1
     */
    private void reappropriationRevenueTrDataStoredSecondTable(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved,
            final List<AccountBudgetReappropriationTrMasterBean> tbaccountBudgetReappropriationList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean,
            final Organisation Organisation) {
        AccountBudgetReappropriationTrMasterBean tbAcBudgetReappropriationTr = null;
        tbAcBudgetReappropriationTr = new AccountBudgetReappropriationTrMasterBean();
        tbAcBudgetReappropriationTr.setOrgid(tbAcBudgetReappropriation.getOrgid());
        tbAcBudgetReappropriationTr.setLangId(tbAcBudgetReappropriation.getLangId());
        tbAcBudgetReappropriationTr.setUserId(tbAcBudgetReappropriation.getUserId());
        tbAcBudgetReappropriationTr.setLgIpMac(tbAcBudgetReappropriation.getLgIpMac());
        tbAcBudgetReappropriationTr.setLgIpMacUpd(tbAcBudgetReappropriation.getLgIpMacUpd());
        tbAcBudgetReappropriationTr.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        tbAcBudgetReappropriationTr.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        tbAcBudgetReappropriationTr.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        if (accountBudgetReappropriationMasterEntitySaved.getPaAdjid() != null) {
            tbAcBudgetReappropriationTr.setPaAdjid(accountBudgetReappropriationMasterEntitySaved.getPaAdjid());
        }
        if (accountBudgetProjectedRevenueEntryBean.getPaAdjidTr() != null) {
            tbAcBudgetReappropriationTr.setPaAdjidTr(accountBudgetProjectedRevenueEntryBean.getPaAdjidTr());
        }
        if (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null) {
            tbAcBudgetReappropriationTr.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic());
        }
        final Long orgId = tbAcBudgetReappropriation.getOrgid();
        final Long faYearid = tbAcBudgetReappropriation.getFaYearid();
        tbAcBudgetReappropriationTr.setAuthFlag(MainetConstants.MENU.N);
        final String budgCodeid = accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstants.BLANK);
        tbAcBudgetReappropriationTr.setDpDeptid(accountBudgetProjectedRevenueEntryBean.getDpDeptid());
        tbAcBudgetReappropriationTr.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        tbAcBudgetReappropriationTr.setProvisionOldamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getOrginalEstamt()));
        tbAcBudgetReappropriationTr.setTransferAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrCollected()));
        tbAcBudgetReappropriationTr.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getRevisedEstamt()));
        tbAcBudgetReappropriationTr.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrProjected()));

        /*final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.AccountBudgetReappropriationMaster.RPR,
                PrefixConstants.AccountJournalVoucherEntry.AUT, Organisation);
        final String isMakerChecker = lkp.getOtherField();*/
        if (StringUtils.equals(tbAcBudgetReappropriation.getAuthFlag(), MainetConstants.FlagY)) {
                final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal prCollected = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        tbAcBudgetReappropriationTr.setAuthFlag(MainetConstants.MENU.Y);
                        if (objects[2] == null) {
                            final BigDecimal collectedDefvalue = new BigDecimal(0);
                            prCollected = collectedDefvalue;
                        }
                        if (objects[2] != null) {
                            prCollected = new BigDecimal(objects[2].toString());
                            prCollected = prCollected.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prProjectionid = tbAcBudgetReappropriationTr.getPrProjectionid();
                        BigDecimal revisedEstamt = tbAcBudgetReappropriationTr.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        if (prCollected == null) {
                            // final BigDecimal projectedAmount = revisedEstamt;
                            accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid,
                                    prProjectionid,
                                    revisedEstamt.toString(), orgId);
                        } else {
                            // final BigDecimal projectedAmount = revisedEstamt.subtract(prCollected);
                            accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid,
                                    prProjectionid,
                                    revisedEstamt.toString(), orgId);
                        }
                    }
                }
            
        }
        tbaccountBudgetReappropriationList.add(tbAcBudgetReappropriationTr);
    }

    /**
     * @param tbAcBudgetReappropriation1
     * @param tbAcBudgetReappropriationList
     * @param accountBudgetProjectedRevenueEntryBean
     */
    private void reappropriationRevenueDataStoredFirstTable(
            final AccountBudgetReappropriationMasterBean tbAccountBudgetReappropriation,
            final List<AccountBudgetReappropriationMasterBean> tbAcBudgetReappropriationList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean,
            final Organisation Organisation) {
        if ((accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev() != null)
                || (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null)) {
            tbAccountBudgetReappropriation.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev());
        }
        tbAccountBudgetReappropriation.setFieldId(accountBudgetProjectedRevenueEntryBean.getFieldId());
        final Long faYearid = tbAccountBudgetReappropriation.getFaYearid();
        final String budgCodeid = accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode();
        tbAccountBudgetReappropriation.setDpDeptid(accountBudgetProjectedRevenueEntryBean.getDpDeptid());
        tbAccountBudgetReappropriation.setRemark(accountBudgetProjectedRevenueEntryBean.getRemark());
        tbAccountBudgetReappropriation
                .setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
        tbAccountBudgetReappropriation
                .setProvisionOldamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getOrginalEstamt()));
        tbAccountBudgetReappropriation.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrProjected()));
        if (accountBudgetProjectedRevenueEntryBean.getPrCollected() != null) {
            tbAccountBudgetReappropriation
                    .setTransferAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrCollected()));
        }
        tbAccountBudgetReappropriation
                .setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getRevisedEstamt()));
        final Long orgId = tbAccountBudgetReappropriation.getOrgid();

      /*  final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.AccountBudgetReappropriationMaster.RPR,
                PrefixConstants.AccountJournalVoucherEntry.AUT, Organisation);
        final String isMakerChecker = lkp.getOtherField();*/
        if (StringUtils.equals(tbAccountBudgetReappropriation.getAuthFlag(), MainetConstants.FlagY)) {
                final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal prCollected = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        tbAccountBudgetReappropriation.setAuthFlag(MainetConstants.MENU.Y);
                        if (objects[2] == null) {
                            final BigDecimal collectedDefvalue = new BigDecimal(0);
                            prCollected = collectedDefvalue;
                        }
                        if (objects[2] != null) {
                            prCollected = new BigDecimal(objects[2].toString());
                            prCollected = prCollected.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prProjectionid = tbAccountBudgetReappropriation.getPrProjectionid();
                        BigDecimal revisedEstamt = tbAccountBudgetReappropriation.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        if (prCollected == null) {
                            // final BigDecimal projectedAmount = revisedEstamt;
                            accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid,
                                    prProjectionid,
                                    revisedEstamt.toString(), orgId);
                        } else {
                            // final BigDecimal projectedAmount = revisedEstamt.subtract(prCollected);
                            accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid,
                                    prProjectionid,
                                    revisedEstamt.toString(), orgId);
                        }
                    }
                }
            
        }
        tbAcBudgetReappropriationList.add(tbAccountBudgetReappropriation);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetReappropriationMasterBean getDetailsUsingBudgetReappropriationId(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final Long PaAdjid = tbAcBudgetReappropriation.getPaAdjid();
        final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity = budgetReappropriationRepository
                .findOne(PaAdjid);
        if (accountBudgetReappropriationMasterEntity.getBudgetTranRefNo() != null) {
            tbAcBudgetReappropriation.setBudgetTranRefNo(accountBudgetReappropriationMasterEntity.getBudgetTranRefNo());
        }
        if(accountBudgetReappropriationMasterEntity.getFieldId()!= null) {
        	tbAcBudgetReappropriation.setFieldId(accountBudgetReappropriationMasterEntity.getFieldId());
        }
        final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity = accountBudgetReappropriationTrMasterJpaRepository
                .findByReappData(PaAdjid);
        
        if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingReappropriationRevenueData(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        } else if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingReappropriationExpenditureData(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        }
        return tbAcBudgetReappropriation;
    }

    /**
     * @param tbAcBudgetReappropriation
     * @param accountBudgetReappropriationMasterEntity
     * @param accountBudgetReappropriationTrMasterEntity
     */
    private void gettingReappropriationExpenditureData(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity,
            final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity) {
        tbAcBudgetReappropriation.setFaYearid(accountBudgetReappropriationMasterEntity.getFaYearid());
        tbAcBudgetReappropriation.setCpdBugtypeId(accountBudgetReappropriationMasterEntity.getCpdBugtypeId());
        tbAcBudgetReappropriation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                accountBudgetReappropriationMasterEntity.getOrgid(), accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        tbAcBudgetReappropriation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                accountBudgetReappropriationMasterEntity.getOrgid(), accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        if (accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugSubTypeId(accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId());
            tbAcBudgetReappropriation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetReappropriation.setRemark(accountBudgetReappropriationMasterEntity.getRemark());
        tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntity.getPaAdjid());
        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();

        bean.setDpDeptid(accountBudgetReappropriationMasterEntity.getDepartment());
        bean.setFieldId(accountBudgetReappropriationMasterEntity.getFieldId());

        if (accountBudgetReappropriationMasterEntity.getPrExpenditureid() != null) {
            bean.setPrExpenditureidExp(accountBudgetReappropriationMasterEntity.getPrExpenditureid());
        }
        bean.setExpRemark(accountBudgetReappropriationMasterEntity.getRemark());
        if ((accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrExpBudgetCode(
                    accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }

        BigDecimal provisionOldamt = accountBudgetReappropriationMasterEntity.getProvisionOldamt();
        if (provisionOldamt != null) {
            provisionOldamt = provisionOldamt.setScale(2, RoundingMode.CEILING);
            bean.setOrginalEstamt(provisionOldamt.toString());
        }
        BigDecimal originalEstAmount = accountBudgetReappropriationMasterEntity.getOrgRevBalamt();
        originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
        bean.setPrBalanceAmt(originalEstAmount.toString());
        BigDecimal newOrgRevAmount = accountBudgetReappropriationMasterEntity.getNewOrgRevAmount();
        newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
        bean.setRevisedEstamt(newOrgRevAmount.toString());
        if (accountBudgetReappropriationMasterEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetReappropriationMasterEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            bean.setExpenditureAmt(transferAmount.toString());
        }

        if (accountBudgetReappropriationMasterEntity.getLangId() != 0) {
            tbAcBudgetReappropriation.setLangId(Long.valueOf(accountBudgetReappropriationMasterEntity.getLangId()));
        }
        tbAcBudgetReappropriation.setUserId(accountBudgetReappropriationMasterEntity.getUserId());
        tbAcBudgetReappropriation.setCreatedDate(Utility.dateToString(accountBudgetReappropriationMasterEntity.getLmoddate()));
        tbAcBudgetReappropriation.setLgIpMac(accountBudgetReappropriationMasterEntity.getLgIpMac());

        tbAcBudgetExpList.add(bean);
        tbAcBudgetReappropriation.setBugprojExpBeanList(tbAcBudgetExpList);
        final List<AccountBudgetProjectedExpenditureBean> tbAccountBudgetExpList = new ArrayList<>();
        for (final AccountBudgetReappropriationTrMasterEntity accountBudgetReappTrMasterEntity : accountBudgetReappropriationTrMasterEntity) {
            gettingReappropriationExpenditureSecondTableData(
                    tbAccountBudgetExpList,
                    accountBudgetReappTrMasterEntity);
        }
        tbAcBudgetReappropriation.setBugprojExpBeanList1(tbAccountBudgetExpList);
    }

    /**
     * @param tbAcBudgetExpList1
     * @param accountBudgetReappTrMasterEntity
     */
    private void gettingReappropriationExpenditureSecondTableData(
            final List<AccountBudgetProjectedExpenditureBean> tbAccountBudgetExpList,
            final AccountBudgetReappropriationTrMasterEntity accountBudgetReappTrMasterEntity) {
        final AccountBudgetProjectedExpenditureBean expbean = new AccountBudgetProjectedExpenditureBean();

        expbean.setDpDeptid(accountBudgetReappTrMasterEntity.getDepartment());
        expbean.setFieldId(accountBudgetReappTrMasterEntity.getTbAcProjectedprovisionadj().getFieldId());

        if ((accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            expbean.setPrExpBudgetCode(accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        if (accountBudgetReappTrMasterEntity.getPaAdjidTr() != null) {
            expbean.setPaAdjidTr(accountBudgetReappTrMasterEntity.getPaAdjidTr());
        }
        if (accountBudgetReappTrMasterEntity.getPrExpenditureid() != null) {
            expbean.setPrExpenditureidExpDynamic(accountBudgetReappTrMasterEntity.getPrExpenditureid());
        }
        BigDecimal originalEstAmount = accountBudgetReappTrMasterEntity.getProvisionOldamt();
        originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
        expbean.setOrginalEstamt(originalEstAmount.toString());
        BigDecimal prBalanceAmt = accountBudgetReappTrMasterEntity.getOrgRevBalamt();
        prBalanceAmt = prBalanceAmt.setScale(2, RoundingMode.CEILING);
        expbean.setPrBalanceAmt(prBalanceAmt.toString());
        BigDecimal newOrgRevAmount = accountBudgetReappTrMasterEntity.getNewOrgRevAmount();
        newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
        expbean.setRevisedEstamt(newOrgRevAmount.toString());
        if (accountBudgetReappTrMasterEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetReappTrMasterEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            expbean.setExpenditureAmt(transferAmount.toString());
        }
        tbAccountBudgetExpList.add(expbean);
    }

    /**
     * @param tbAcBudgetReappropriation
     * @param accountBudgetReappropriationMasterEntity
     * @param accountBudgetReappropriationTrMasterEntity
     */
    private void gettingReappropriationRevenueData(
            final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity,
            final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity) {
        tbAcBudgetReappropriation.setFaYearid(accountBudgetReappropriationMasterEntity.getFaYearid());
        tbAcBudgetReappropriation.setCpdBugtypeId(accountBudgetReappropriationMasterEntity.getCpdBugtypeId());
        tbAcBudgetReappropriation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                accountBudgetReappropriationMasterEntity.getOrgid(), accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        tbAcBudgetReappropriation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                accountBudgetReappropriationMasterEntity.getOrgid(), accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        if (accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugSubTypeId(accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId());
            tbAcBudgetReappropriation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntity.getPaAdjid());
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();

        bean.setDpDeptid(accountBudgetReappropriationMasterEntity.getDepartment());
        bean.setFieldId(accountBudgetReappropriationMasterEntity.getFieldId());

        if (accountBudgetReappropriationMasterEntity.getPrProjectionid() != null) {
            bean.setPrProjectionidRev(accountBudgetReappropriationMasterEntity.getPrProjectionid());
        }

        bean.setRemark(accountBudgetReappropriationMasterEntity.getRemark());
        if ((accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrRevBudgetCode(
                    accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }

        BigDecimal provisionOldamt = accountBudgetReappropriationMasterEntity.getProvisionOldamt();
        if (provisionOldamt != null) {
            provisionOldamt = provisionOldamt.setScale(2, RoundingMode.CEILING);
            bean.setOrginalEstamt(provisionOldamt.toString());
        }
        BigDecimal originalEstAmount = accountBudgetReappropriationMasterEntity.getOrgRevBalamt();
        originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
        bean.setPrProjected(originalEstAmount.toString());
        BigDecimal newOrgRevAmount = accountBudgetReappropriationMasterEntity.getNewOrgRevAmount();
        newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
        bean.setRevisedEstamt(newOrgRevAmount.toString());
        if (accountBudgetReappropriationMasterEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetReappropriationMasterEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            bean.setPrCollected(transferAmount.toString());
        }

        if (accountBudgetReappropriationMasterEntity.getLangId() != 0) {
            tbAcBudgetReappropriation.setLangId(Long.valueOf(accountBudgetReappropriationMasterEntity.getLangId()));
        }
        tbAcBudgetReappropriation.setUserId(accountBudgetReappropriationMasterEntity.getUserId());
        tbAcBudgetReappropriation.setCreatedDate(Utility.dateToString(accountBudgetReappropriationMasterEntity.getLmoddate()));
        tbAcBudgetReappropriation.setLgIpMac(accountBudgetReappropriationMasterEntity.getLgIpMac());

        tbAcBudgetRevenueList.add(bean);
        tbAcBudgetReappropriation.setBugprojRevBeanList(tbAcBudgetRevenueList);
        final List<AccountBudgetProjectedRevenueEntryBean> acBudgetReappropriationList = new ArrayList<>();
        for (final AccountBudgetReappropriationTrMasterEntity accountBudgetReappTrMasterEntity : accountBudgetReappropriationTrMasterEntity) {
            gettingReappropriationRevenueSecondTableData(
                    acBudgetReappropriationList,
                    accountBudgetReappTrMasterEntity);
        }

        tbAcBudgetReappropriation.setBugprojRevBeanList1(acBudgetReappropriationList);
    }

    /**
     * @param tbAcBudgetReappropriationList1
     * @param accountBudgetReappTrMasterEntity
     */
    private void gettingReappropriationRevenueSecondTableData(
            final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetReappropriationList,
            final AccountBudgetReappropriationTrMasterEntity accountBudgetReappTrMasterEntity) {
        final AccountBudgetProjectedRevenueEntryBean revbean = new AccountBudgetProjectedRevenueEntryBean();

        revbean.setDpDeptid(accountBudgetReappTrMasterEntity.getDepartment());
        revbean.setFieldId(accountBudgetReappTrMasterEntity.getTbAcProjectedprovisionadj().getFieldId());

        if ((accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            revbean.setPrRevBudgetCode(accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }

        if (accountBudgetReappTrMasterEntity.getPrProjectionid() != null) {
            revbean.setPrProjectionidRevDynamic(accountBudgetReappTrMasterEntity.getPrProjectionid());
        }

        if (accountBudgetReappTrMasterEntity.getPaAdjidTr() != null) {
            revbean.setPaAdjidTr(accountBudgetReappTrMasterEntity.getPaAdjidTr());
        }
        BigDecimal originalEstAmount = accountBudgetReappTrMasterEntity.getProvisionOldamt();
        originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
        revbean.setOrginalEstamt(originalEstAmount.toString());
        BigDecimal prProjected = accountBudgetReappTrMasterEntity.getOrgRevBalamt();
        prProjected = prProjected.setScale(2, RoundingMode.CEILING);
        revbean.setPrProjected(prProjected.toString());
        BigDecimal newOrgRevAmount = accountBudgetReappTrMasterEntity.getNewOrgRevAmount();
        newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
        revbean.setRevisedEstamt(newOrgRevAmount.toString());
        if (accountBudgetReappTrMasterEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetReappTrMasterEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            revbean.setPrCollected(transferAmount.toString());
        }
        tbAcBudgetReappropriationList.add(revbean);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetReappropriationMasterBean> findBudgetReappropriationMastersByOrgId(final Long orgId,
            final String budgIdentifyFlag) {
        final List<AccountBudgetReappropriationMasterEntity> entities = budgetReappropriationRepository
                .findBudgetReappropriationMastersByOrgId(budgIdentifyFlag, orgId);
        final List<AccountBudgetReappropriationMasterBean> bean = new ArrayList<>();
        for (final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity : entities) {
            bean.add(accountBudgetReappropriationMasterServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanEntityToAccountBudgetReappropriationMasterBean(
                            accountBudgetReappropriationMasterEntity));
        }
        return bean;
    }

    private String generateBudgetTranRefNumber(final Long orgId, Long faYearid) {

        final Long billNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                "TB_AC_PROJECTEDPROVISIONADJ",
                "RP_TRNNO", orgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, faYearid);
        return billNumber.toString();
    }
    
    private boolean isMakerChecker(final Organisation org) {
		final long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RPR",
				AccountPrefix.AUT.toString(), org.getOrgid());
		if (lookUpId !=0 && lookUpId > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void updateUploadReappropriationDeletedRecords(List<Long> removeFileById, Long updatedBy) {
		// TODO Auto-generated method stub
		iAttachDocsDao.updateRecord(removeFileById, updatedBy, MainetConstants.RnLCommon.Flag_D);
	}
}
