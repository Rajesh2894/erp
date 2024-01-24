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
import com.abm.mainet.account.dto.AccountBudgetReappropriationTrMasterBean;
import com.abm.mainet.account.dto.ReappropriationOfBudgetAuthorizationDTO;
import com.abm.mainet.account.mapper.AccountBudgetReappropriationTrMasterServiceMapper;
import com.abm.mainet.account.mapper.ReappropriationOfBudgetAuthorizationServiceMapper;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.account.repository.BudgetReappropriationRepository;
import com.abm.mainet.account.repository.BudgetReappropriationTransactionRepository;
import com.abm.mainet.account.utility.AccountWorkflowUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Component
public class ReappropriationOfBudgetAuthorizationServiceImpl implements ReappropriationOfBudgetAuthorizationService {

    @Resource
    private AccountBudgetReappropriationMasterDao accountBudgetReappropriationMasterDao;
    @Resource
    private ReappropriationOfBudgetAuthorizationServiceMapper reappropriationOfBudgetAuthorizationServiceMapper;
    @Resource
    private BudgetReappropriationRepository budgetReappropriationRepository;
    @Resource
    private BudgetReappropriationTransactionRepository accountBudgetReappropriationTrMasterJpaRepository;
    @Resource
    private BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;
    @Resource
    private BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;
    @Resource
    private AccountBudgetReappropriationTrMasterServiceMapper accountBudgetReappropriationTrMasterServiceMapper;
    @Autowired
    private ServiceMasterService serviceMasterService;
    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;
    @Autowired
    private IWorkflowExecutionService workflowExecutionService;
    @Autowired
    private IWorkflowRequestService workflowRequestService;
    @Autowired
	private IWorkflowTypeDAO iWorkflowTypeDAO;
    @Resource
	private DepartmentService departmentService;
    

    private static Logger LOGGER = Logger.getLogger(AccountBudgetReappropriationMasterServiceImpl.class);

    @Override
    @Transactional
    public List<ReappropriationOfBudgetAuthorizationDTO> findAllGridData(final String budgIdentifyFlag, final Long orgId) {
        final List<AccountBudgetReappropriationMasterEntity> entities = budgetReappropriationRepository
                .findBudgetReappropriationMastersByOrgId(budgIdentifyFlag, orgId);
        final List<ReappropriationOfBudgetAuthorizationDTO> dto = new ArrayList<>();
        for (final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity : entities) {
            dto.add(reappropriationOfBudgetAuthorizationServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanEntityToReappropriationOfBudgetAuthorizationDTO(
                            accountBudgetReappropriationMasterEntity));
        }
        return dto;
    }

    @Override
    @Transactional
    public List<ReappropriationOfBudgetAuthorizationDTO> findByAuthorizationGridData(
            final Date frmDate, final Date todate, final Long cpdBugtypeId, final String status, final String budgIdentifyFlag,
            final Long orgId) {
        final List<AccountBudgetReappropriationMasterEntity> entities = accountBudgetReappropriationMasterDao
                .findByAuthorizationGridData(frmDate, todate, cpdBugtypeId, status, budgIdentifyFlag, orgId);
        final List<ReappropriationOfBudgetAuthorizationDTO> dto = new ArrayList<>();
        for (final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity : entities) {
            dto.add(reappropriationOfBudgetAuthorizationServiceMapper
                    .mapAccountBudgetReappropriationMasterBeanEntityToReappropriationOfBudgetAuthorizationDTO(
                            accountBudgetReappropriationMasterEntity));
        }
        return dto;
    }

    @Override
    @Transactional
    public ReappropriationOfBudgetAuthorizationDTO saveBudgetReappAuthorizationFormData(
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappAuthorization, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);

        if (tbAcBudgetReappAuthorization.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            reappropriationRevenueStoredData(tbAcBudgetReappAuthorization, LanguageId, Organisation);
        } else if (tbAcBudgetReappAuthorization.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            reappropriationExpenditureStoredData(tbAcBudgetReappAuthorization, LanguageId, Organisation);
        }
        return tbAcBudgetReappAuthorization;
    }

    /**
     * @param tbAcBudgetReappropriation
     * @throws ParseException
     */
    private void reappropriationExpenditureStoredData(
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        ReappropriationOfBudgetAuthorizationDTO acBudgetReappropriation = null;
        final List<ReappropriationOfBudgetAuthorizationDTO> acBudgetReappropriationList = new ArrayList<>();
        AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved = null;
        AccountBudgetReappropriationTrMasterEntity accountBudgetReappropriationTrMasterEntitySaved = null;
        acBudgetReappropriation = new ReappropriationOfBudgetAuthorizationDTO();
        if ((tbAcBudgetReappropriation.getPaAdjid() != null) && (tbAcBudgetReappropriation.getPaAdjid() > 0l)) {
            acBudgetReappropriation.setPaAdjid(tbAcBudgetReappropriation.getPaAdjid());
        }
        if (tbAcBudgetReappropriation.getBudgetTranRefNo() != null) {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        ServiceMaster service = serviceMasterService.getServiceByShortName("BR", tbAcBudgetReappropriation.getOrgid());
        // This condition is for if service is active then only workflow code should be execute. Added by srikanth.
        Organisation org = new Organisation();
        org.setOrgid(tbAcBudgetReappropriation.getOrgid());
        LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), org);
        if (serviceActiveLookUp != null && !StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	 WorkflowMas workflowMas =null;
        	  /* WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
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
                            .prepareInitAccountReappropriationBudgetProcessParameter(tbAcBudgetReappropriation, workflowMas,
                                    tbAcBudgetReappropriation.getBudgetTranRefNo());
                    if (tbAcBudgetReappropriation.getApproved() == null || tbAcBudgetReappropriation.getApproved().isEmpty()) {
                        TaskAssignment assignment = new TaskAssignment();
                        assignment.setActorId(tbAcBudgetReappropriation.getUserId().toString());
                        assignment.setOrgId(tbAcBudgetReappropriation.getOrgid());
                        assignment.setUrl("ReappropriationOfBudgetAuthorization.html");
                        // assignment.setDeptId(tbAcBudgetReappropriation.getDepartmentDesc());
                        processParameter.setRequesterTaskAssignment(assignment);
                        workflowExecutionService.initiateWorkflow(processParameter);
                    } else {
                        workflowExecutionService.updateWorkflow(processParameter);
                    }
                } catch (Exception e) {
                    LOGGER.error("Unsuccessful initiation/updation of task for application : "
                            + tbAcBudgetReappropriation.getBudgetTranRefNo() + e);
                }
            }
        }

        acBudgetReappropriation.setOrgid(tbAcBudgetReappropriation.getOrgid());
        acBudgetReappropriation.setLangId(tbAcBudgetReappropriation.getLangId());
        acBudgetReappropriation.setUserId(tbAcBudgetReappropriation.getUserId());
        acBudgetReappropriation.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        acBudgetReappropriation.setPaEntrydate(new Date());
        // acBudgetReappropriation.setApprovedBy(tbAcBudgetReappropriation.getApprovedBy());
        acBudgetReappropriation.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        // acBudgetReappropriation.setUpdatedDate(new Date());
        acBudgetReappropriation.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        acBudgetReappropriation.setLgIpMac(tbAcBudgetReappropriation.getLgIpMac());
        acBudgetReappropriation.setLgIpMacUpd(tbAcBudgetReappropriation.getLgIpMacUpd());
        acBudgetReappropriation.setFaYearid(tbAcBudgetReappropriation.getFaYearid());
        acBudgetReappropriation.setCpdBugtypeId(tbAcBudgetReappropriation.getCpdBugtypeId());
        acBudgetReappropriation.setCpdBugSubTypeId(tbAcBudgetReappropriation.getCpdBugSubTypeId());
        // acBudgetReappropriation.setAuthFlag(tbAcBudgetReappropriation.getApproved());
        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            acBudgetReappropriation.setAuthFlag(tbAcBudgetReappropriation.getApproved());
            acBudgetReappropriation.setApprovedBy(tbAcBudgetReappropriation.getApprovedBy());
        } else {
            acBudgetReappropriation.setActualTaskId(tbAcBudgetReappropriation.getActualTaskId());
            acBudgetReappropriation.setAuthFlag(MainetConstants.N_FLAG);
            acBudgetReappropriation.setApprovedBy(null);
        }

        acBudgetReappropriation.setBudgetIdentifyFlag(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG);
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALUE,
                PrefixConstants.REV_PREFIX, LanguageId, Organisation);
        acBudgetReappropriation.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : tbAcBudgetReappropriation
                .getBugprojExpBeanList()) {
            reappropriationExpenditureStoredDataFirstTable(
                    acBudgetReappropriation,
                    acBudgetReappropriationList,
                    accountBudgetProjectedExpenditureBean);
        }
        for (final ReappropriationOfBudgetAuthorizationDTO accountBudgetReappropriationMasterSaved : acBudgetReappropriationList) {
            accountBudgetReappropriationMasterEntitySaved = new AccountBudgetReappropriationMasterEntity();
            reappropriationOfBudgetAuthorizationServiceMapper
                    .mapReappropriationOfBudgetAuthorizationDTOToAccountBudgetReappropriationMasterEntity(
                            accountBudgetReappropriationMasterSaved, accountBudgetReappropriationMasterEntitySaved);
            /*Defect #96460*/
            accountBudgetReappropriationMasterEntitySaved.setFieldId(tbAcBudgetReappropriation.getFieldId());
            accountBudgetReappropriationMasterEntitySaved = budgetReappropriationRepository
                    .save(accountBudgetReappropriationMasterEntitySaved);
        }
        final List<AccountBudgetReappropriationTrMasterBean> tbacBudgetReappropriationList = new ArrayList<>();
        for (final AccountBudgetProjectedExpenditureBean acBudgetProjectedExpenditureBean : tbAcBudgetReappropriation
                .getBugprojExpBeanList1()) {
            reappropriationExpenditureStoredDataSecondTable(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntitySaved,
                    tbacBudgetReappropriationList,
                    acBudgetProjectedExpenditureBean);
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
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved,
            final List<AccountBudgetReappropriationTrMasterBean> acBudgetReappropriationList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean) {
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
        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            acBudgetReappropriationTr.setAuthFlag(tbAcBudgetReappropriation.getApproved());
        } else {
            acBudgetReappropriationTr.setAuthFlag(MainetConstants.N_FLAG);
        }
        acBudgetReappropriationTr.setDpDeptid(accountBudgetProjectedExpenditureBean.getDpDeptid());
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
        final String budgCodeid = accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstants.BLANK);
        acBudgetReappropriationTr.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        acBudgetReappropriationTr.setProvisionOldamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        acBudgetReappropriationTr.setTransferAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
        acBudgetReappropriationTr.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getRevisedEstamt()));
        acBudgetReappropriationTr.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getPrBalanceAmt()));

        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            if (tbAcBudgetReappropriation.getApproved().equals(MainetConstants.MENU.Y)) {
                final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal expenditureAmt = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        if (objects[2] == null) {
                            final BigDecimal expDefaultValue = new BigDecimal(0);
                            expenditureAmt = expDefaultValue;
                        }
                        if (objects[2] != null) {
                            expenditureAmt = new BigDecimal(objects[2].toString());
                            expenditureAmt = expenditureAmt.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prExpenditureid = acBudgetReappropriationTr.getPrExpenditureid();
                        // Revised amount should be calculated based on updated amount which is coming from database. Defect No: 29299
                        BigDecimal revisedEstamt = new BigDecimal(0);
                        if(objects[0] != null) {
                    	   Double reappropAmount = new Double(objects[0].toString());
                            revisedEstamt = BigDecimal.valueOf(reappropAmount).subtract(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
                       }else {
                    	   revisedEstamt = acBudgetReappropriationTr.getNewOrgRevAmount();
                       }
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
        }
        acBudgetReappropriationList.add(acBudgetReappropriationTr);
    }

    /**
     * @param tbAcBudgetReappropriation2
     * @param tbAcBudgetReappropriationList1
     * @param accountBudgetProjectedExpenditureBean
     */
    private void reappropriationExpenditureStoredDataFirstTable(
            final ReappropriationOfBudgetAuthorizationDTO tbAccountBudgetReappropriation,
            final List<ReappropriationOfBudgetAuthorizationDTO> accountBudgetReappropriationList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean) {
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

        if (tbAccountBudgetReappropriation.getActualTaskId() == null) {
            if (tbAccountBudgetReappropriation.getAuthFlag().equals(MainetConstants.MENU.Y)) {
                final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal expenditureAmt = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        if (objects[2] == null) {
                            final BigDecimal expDefaultValue = new BigDecimal(0);
                            expenditureAmt = expDefaultValue;
                        }
                        if (objects[2] != null) {
                            expenditureAmt = new BigDecimal(objects[2].toString());
                            expenditureAmt = expenditureAmt.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prExpenditureid = tbAccountBudgetReappropriation.getPrExpenditureid();
                        // Revised amount should be calculated based on updated amount which is coming from database. Defect No: 29299
                        BigDecimal revisedEstamt = new BigDecimal(0);
                        if(objects[0] != null) {
                        	Double reappropAmount = new Double(objects[0].toString());
                             revisedEstamt = new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()).add(BigDecimal.valueOf(reappropAmount));
                        }else {
                        	revisedEstamt = tbAccountBudgetReappropriation.getNewOrgRevAmount();
                        }
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
        }
        accountBudgetReappropriationList.add(tbAccountBudgetReappropriation);
    }

    /**
     * @param tbAcBudgetReappropriation
     * @throws ParseException
     */
    private void reappropriationRevenueStoredData(
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation, final int LanguageId,
            final Organisation Organisation)
            throws ParseException {
        ReappropriationOfBudgetAuthorizationDTO acBudgetReappropriation = null;
        final List<ReappropriationOfBudgetAuthorizationDTO> tbAcBudgetReappropriationList = new ArrayList<>();
        AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved = null;
        AccountBudgetReappropriationTrMasterEntity accountBudgetReappropriationTrMasterEntitySaved = null;
        acBudgetReappropriation = new ReappropriationOfBudgetAuthorizationDTO();
        if ((tbAcBudgetReappropriation.getPaAdjid() != null) && (tbAcBudgetReappropriation.getPaAdjid() > 0l)) {
            acBudgetReappropriation.setPaAdjid(tbAcBudgetReappropriation.getPaAdjid());
        }
        if (tbAcBudgetReappropriation.getBudgetTranRefNo() != null) {
            acBudgetReappropriation.setBudgetTranRefNo(tbAcBudgetReappropriation.getBudgetTranRefNo());
        }

        ServiceMaster service = serviceMasterService.getServiceByShortName("BR", tbAcBudgetReappropriation.getOrgid());
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
     // This condition is for if service is active then only workflow code should be execute. Added by srikanth.
        Organisation org = new Organisation();
        org.setOrgid(tbAcBudgetReappropriation.getOrgid());
        LookUp serviceActiveLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getSmServActive(), org);
        if (serviceActiveLookUp != null && !StringUtils.equals(serviceActiveLookUp.getLookUpCode(), MainetConstants.FlagI)) {
        	WorkflowMas workflowMas=null;
           /* WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
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
                            .prepareInitAccountReappropriationBudgetProcessParameter(tbAcBudgetReappropriation, workflowMas,
                                    tbAcBudgetReappropriation.getBudgetTranRefNo());
                    if (tbAcBudgetReappropriation.getApproved() == null || tbAcBudgetReappropriation.getApproved().isEmpty()) {
                        TaskAssignment assignment = new TaskAssignment();
                        assignment.setActorId(tbAcBudgetReappropriation.getUserId().toString());
                        assignment.setOrgId(tbAcBudgetReappropriation.getOrgid());
                        assignment.setUrl("ReappropriationOfBudgetAuthorization.html");
                        // assignment.setDeptId(tbAcBudgetReappropriation.getDepartmentDesc());
                        assignment.setDeptId(deptId);
                        processParameter.setRequesterTaskAssignment(assignment);
                        workflowExecutionService.initiateWorkflow(processParameter);
                    } else {
                        workflowExecutionService.updateWorkflow(processParameter);
                    }
                } catch (Exception e) {
                    LOGGER.error("Unsuccessful initiation/updation of task for application : "
                            + tbAcBudgetReappropriation.getBudgetTranRefNo() + e);
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
        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            acBudgetReappropriation.setAuthFlag(tbAcBudgetReappropriation.getApproved());
            acBudgetReappropriation.setApprovedBy(tbAcBudgetReappropriation.getApprovedBy());
        } else {
            acBudgetReappropriation.setActualTaskId(tbAcBudgetReappropriation.getActualTaskId());
            acBudgetReappropriation.setAuthFlag(MainetConstants.N_FLAG);
            acBudgetReappropriation.setApprovedBy(null);
        }
        acBudgetReappropriation.setBudgetIdentifyFlag(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG);
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALUE,
                PrefixConstants.REV_PREFIX, LanguageId, Organisation);
        acBudgetReappropriation.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : tbAcBudgetReappropriation
                .getBugprojRevBeanList()) {
            reappropriationRevenueDataStoredFirstTable(
                    acBudgetReappropriation,
                    tbAcBudgetReappropriationList,
                    accountBudgetProjectedRevenueEntryBean);
        }
        for (final ReappropriationOfBudgetAuthorizationDTO accountBudgetReappropriationMasterSaved : tbAcBudgetReappropriationList) {
            accountBudgetReappropriationMasterEntitySaved = new AccountBudgetReappropriationMasterEntity();
            reappropriationOfBudgetAuthorizationServiceMapper
                    .mapReappropriationOfBudgetAuthorizationDTOToAccountBudgetReappropriationMasterEntity(
                            accountBudgetReappropriationMasterSaved, accountBudgetReappropriationMasterEntitySaved);
            /*Defect #96460*/
            accountBudgetReappropriationMasterEntitySaved.setFieldId(tbAcBudgetReappropriation.getFieldId());
            accountBudgetReappropriationMasterEntitySaved = budgetReappropriationRepository
                    .save(accountBudgetReappropriationMasterEntitySaved);
        }
        final List<AccountBudgetReappropriationTrMasterBean> acBudgetReappropriationList = new ArrayList<>();
        for (final AccountBudgetProjectedRevenueEntryBean acBudgetProjectedRevenueEntryBean : tbAcBudgetReappropriation
                .getBugprojRevBeanList1()) {
            reappropriationRevenueTrDataStoredSecondTable(
                    tbAcBudgetReappropriation,
                    accountBudgetReappropriationMasterEntitySaved,
                    acBudgetReappropriationList,
                    acBudgetProjectedRevenueEntryBean);
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
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntitySaved,
            final List<AccountBudgetReappropriationTrMasterBean> tbaccountBudgetReappropriationList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean) {
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
        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            tbAcBudgetReappropriationTr.setAuthFlag(tbAcBudgetReappropriation.getApproved());
        } else {
            tbAcBudgetReappropriationTr.setAuthFlag(MainetConstants.N_FLAG);
        }
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

        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            if (tbAcBudgetReappropriation.getApproved().equals(MainetConstants.MENU.Y)) {
                final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal prCollected = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        if (objects[2] == null) {
                            final BigDecimal collectedDefvalue = new BigDecimal(0);
                            prCollected = collectedDefvalue;
                        }
                        if (objects[2] != null) {
                            prCollected = new BigDecimal(objects[2].toString());
                            prCollected = prCollected.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prProjectionid = tbAcBudgetReappropriationTr.getPrProjectionid();
                        
                        // Revised amount should be calculated based on updated amount which is coming from database. Defect No: 29299
                        BigDecimal revisedEstamt = new BigDecimal(0);
                        if(objects[0] != null) {
                        	Double reappropAmount = new Double(objects[0].toString());
                             revisedEstamt = BigDecimal.valueOf(reappropAmount).subtract(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrCollected()));
                        }else {
                        	revisedEstamt = tbAcBudgetReappropriationTr.getNewOrgRevAmount();
                        }
                        
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
        }
        tbaccountBudgetReappropriationList.add(tbAcBudgetReappropriationTr);
    }

    /**
     * @param tbAcBudgetReappropriation1
     * @param tbAcBudgetReappropriationList
     * @param accountBudgetProjectedRevenueEntryBean
     */
    private void reappropriationRevenueDataStoredFirstTable(
            final ReappropriationOfBudgetAuthorizationDTO tbAccountBudgetReappropriation,
            final List<ReappropriationOfBudgetAuthorizationDTO> tbAcBudgetReappropriationList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean) {
        if ((accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev() != null)
                || (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null)) {
            tbAccountBudgetReappropriation.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev());
        }

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

        if (tbAccountBudgetReappropriation.getActualTaskId() == null) {
            if (tbAccountBudgetReappropriation.getAuthFlag().equals(MainetConstants.MENU.Y)) {
                final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                        Long.valueOf(budgCodeid), orgId);
                BigDecimal prCollected = null;
                if (entities != null) {
                    for (final Object[] objects : entities) {
                        if (objects[2] == null) {
                            final BigDecimal collectedDefvalue = new BigDecimal(0);
                            prCollected = collectedDefvalue;
                        }
                        if (objects[2] != null) {
                            prCollected = new BigDecimal(objects[2].toString());
                            prCollected = prCollected.setScale(2, RoundingMode.CEILING);
                        }
                        final Long prProjectionid = tbAccountBudgetReappropriation.getPrProjectionid();
                        
                        // Revised amount should be calculated based on updated amount which is coming from database. Defect No: 29299
                        BigDecimal revisedEstamt = new BigDecimal(0);
                        if(objects[0] != null) {
                        	Double reappropAmount = new Double(objects[0].toString());
                             revisedEstamt = new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrCollected()).add(BigDecimal.valueOf(reappropAmount));
                        }else {
                        	revisedEstamt = tbAccountBudgetReappropriation.getNewOrgRevAmount();
                        }
                        
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
        }
        tbAcBudgetReappropriationList.add(tbAccountBudgetReappropriation);
    }

    @Override
    @Transactional
    public ReappropriationOfBudgetAuthorizationDTO getDetailsUsingBudgetReappAuthorizationId(
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappAuthorization, final int LanguageId,
            final Organisation Organisation) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final Long PaAdjid = tbAcBudgetReappAuthorization.getPaAdjid();
        final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity = budgetReappropriationRepository
                .findOne(PaAdjid);
        if (accountBudgetReappropriationMasterEntity.getBudgetTranRefNo() != null) {
            tbAcBudgetReappAuthorization.setBudgetTranRefNo(accountBudgetReappropriationMasterEntity.getBudgetTranRefNo());
        }
        final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity = accountBudgetReappropriationTrMasterJpaRepository
                .findByReappData(PaAdjid);
        if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingReappropriationRevenueData(
                    tbAcBudgetReappAuthorization,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        } else if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingReappropriationExpenditureData(
                    tbAcBudgetReappAuthorization,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        }
        return tbAcBudgetReappAuthorization;
    }

    /**
     * @param tbAcBudgetReappropriation
     * @param accountBudgetReappropriationMasterEntity
     * @param accountBudgetReappropriationTrMasterEntity
     */
    private void gettingReappropriationExpenditureData(
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity,
            final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity) {
        tbAcBudgetReappropriation.setFaYearid(accountBudgetReappropriationMasterEntity.getFaYearid());
        if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugtypeId(accountBudgetReappropriationMasterEntity.getCpdBugtypeId());
            tbAcBudgetReappropriation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
            tbAcBudgetReappropriation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        }
        if (accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugSubTypeId(accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId());
            tbAcBudgetReappropriation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntity.getPaAdjid());
        if ((accountBudgetReappropriationMasterEntity.getAuthFlag() != null)
                && !accountBudgetReappropriationMasterEntity.getAuthFlag().isEmpty()) {
            tbAcBudgetReappropriation.setApproved(accountBudgetReappropriationMasterEntity.getAuthFlag());
            tbAcBudgetReappropriation.setApprovedDup(accountBudgetReappropriationMasterEntity.getAuthFlag());
        }
        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();

        if (accountBudgetReappropriationMasterEntity.getDepartment() != null) {
            bean.setDpDeptid(accountBudgetReappropriationMasterEntity.getDepartment());
        }
        bean.setExpRemark(accountBudgetReappropriationMasterEntity.getRemark());
        if ((accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrExpBudgetCode(
                    accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }

        if (accountBudgetReappropriationMasterEntity.getPrExpenditureid() != null) {
            bean.setPrExpenditureidExp(accountBudgetReappropriationMasterEntity.getPrExpenditureid());
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

        if ((accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            expbean.setPrExpBudgetCode(accountBudgetReappTrMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        if (accountBudgetReappTrMasterEntity.getPrExpenditureid() != null) {
            expbean.setPrExpenditureidExpDynamic(accountBudgetReappTrMasterEntity.getPrExpenditureid());
        }
        if (accountBudgetReappTrMasterEntity.getPaAdjidTr() != null) {
            expbean.setPaAdjidTr(accountBudgetReappTrMasterEntity.getPaAdjidTr());
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
            final ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation,
            final AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity,
            final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity) {
        tbAcBudgetReappropriation.setFaYearid(accountBudgetReappropriationMasterEntity.getFaYearid());
        if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugtypeId(accountBudgetReappropriationMasterEntity.getCpdBugtypeId());
            tbAcBudgetReappropriation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
            tbAcBudgetReappropriation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugtypeId()));
        }
        if (accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetReappropriation.setCpdBugSubTypeId(accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId());
            tbAcBudgetReappropriation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetReappropriationMasterEntity.getOrgid(),
                    accountBudgetReappropriationMasterEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetReappropriation.setPaAdjid(accountBudgetReappropriationMasterEntity.getPaAdjid());
        if ((accountBudgetReappropriationMasterEntity.getAuthFlag() != null)
                && !accountBudgetReappropriationMasterEntity.getAuthFlag().isEmpty()) {
            tbAcBudgetReappropriation.setApproved(accountBudgetReappropriationMasterEntity.getAuthFlag());
            tbAcBudgetReappropriation.setApprovedDup(accountBudgetReappropriationMasterEntity.getAuthFlag());
        }
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();

        bean.setRemark(accountBudgetReappropriationMasterEntity.getRemark());
        if ((accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrRevBudgetCode(
                    accountBudgetReappropriationMasterEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        if (accountBudgetReappropriationMasterEntity.getDepartment() != null) {
            bean.setDpDeptid(accountBudgetReappropriationMasterEntity.getDepartment());
        }
        if (accountBudgetReappropriationMasterEntity.getPrProjectionid() != null) {
            bean.setPrProjectionidRev(accountBudgetReappropriationMasterEntity.getPrProjectionid());
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
    @Transactional
    public ReappropriationOfBudgetAuthorizationDTO populateBudgetReappropriationWorkFlowData(
            ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappAuthorization, int LanguageId,
            Organisation Organisation, long actualTaskId) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        // final Long PaAdjid = tbAcBudgetReappAuthorization.getPaAdjid();
        tbAcBudgetReappAuthorization.setActualTaskId(actualTaskId);
        AccountBudgetReappropriationMasterEntity accountBudgetReappropriationMasterEntity = budgetReappropriationRepository
                .findByReappWorkFlowDataByBudgetTranRefNo(tbAcBudgetReappAuthorization.getBudgetTranRefNo(),
                        tbAcBudgetReappAuthorization.getOrgid());
        tbAcBudgetReappAuthorization.setFieldId(accountBudgetReappropriationMasterEntity.getFieldId());
        if (accountBudgetReappropriationMasterEntity.getBudgetTranRefNo() != null) {
            tbAcBudgetReappAuthorization.setBudgetTranRefNo(accountBudgetReappropriationMasterEntity.getBudgetTranRefNo());
        }
        final List<AccountBudgetReappropriationTrMasterEntity> accountBudgetReappropriationTrMasterEntity = accountBudgetReappropriationTrMasterJpaRepository
                .findByReappData(accountBudgetReappropriationMasterEntity.getPaAdjid());
        if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingReappropriationRevenueData(
                    tbAcBudgetReappAuthorization,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        } else if (accountBudgetReappropriationMasterEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingReappropriationExpenditureData(
                    tbAcBudgetReappAuthorization,
                    accountBudgetReappropriationMasterEntity,
                    accountBudgetReappropriationTrMasterEntity);
        }
        return tbAcBudgetReappAuthorization;
    }

    @Override
    @Transactional
    public void forUpdateRevisedEstmtDataInBudgetExpRevTable(
            ReappropriationOfBudgetAuthorizationDTO dto, int languageId, Organisation organisation) throws ParseException {
        // In workflow, events last level only voucher posting is required.
        if (dto.getActualTaskId() != null) {
            WorkflowRequest workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
                    dto.getBudgetTranRefNo(),
                    dto.getOrgid());
            if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
                    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                dto.setWorkFlowFinLevIdFlag(MainetConstants.Y_FLAG);
                final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                        PrefixConstants.PREFIX, languageId, organisation);
                final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                        PrefixConstants.PREFIX, languageId, organisation);
                if (dto.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
                    for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : dto
                            .getBugprojRevBeanList()) {
                        reapprWorkFlowRevenueDataStoredFirstTable(dto, accountBudgetProjectedRevenueEntryBean);
                    }
                    for (final AccountBudgetProjectedRevenueEntryBean acBudgetProjectedRevenueEntryBean : dto
                            .getBugprojRevBeanList1()) {
                        reappWorkFlowRevenueTrDataStoredSecondTable(dto, acBudgetProjectedRevenueEntryBean);
                        budgetReappropriationRepository.updateDetailsTrTableAuthFlagStatus(
                                acBudgetProjectedRevenueEntryBean.getPaAdjidTr(), dto.getOrgid());
                    }
                    budgetReappropriationRepository.updateMasterTableAuthFlagStatus(dto.getPaAdjid(), dto.getOrgid(),
                            dto.getApprovedBy());
                    // reappropriationRevenueStoredData(dto, languageId, organisation);
                } else if (dto.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
                    for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : dto
                            .getBugprojExpBeanList()) {
                        reappWorkFlowExpenditureStoredDataFirstTable(dto, accountBudgetProjectedExpenditureBean);
                    }
                    for (final AccountBudgetProjectedExpenditureBean acBudgetProjectedExpenditureBean : dto
                            .getBugprojExpBeanList1()) {
                        reappWorkFlowExpenditureStoredDataSecondTable(dto, acBudgetProjectedExpenditureBean);
                        budgetReappropriationRepository.updateDetailsTrTableAuthFlagStatus(
                                acBudgetProjectedExpenditureBean.getPaAdjidTr(), dto.getOrgid());
                    }
                    budgetReappropriationRepository.updateMasterTableAuthFlagStatus(dto.getPaAdjid(), dto.getOrgid(),
                            dto.getApprovedBy());
                    // reappropriationExpenditureStoredData(dto, languageId, organisation);
                }
            }
        }
    }

    private void reappWorkFlowRevenueTrDataStoredSecondTable(ReappropriationOfBudgetAuthorizationDTO tbAcBudgetReappropriation,
            AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean) {

        AccountBudgetReappropriationTrMasterBean tbAcBudgetReappropriationTr = null;
        tbAcBudgetReappropriationTr = new AccountBudgetReappropriationTrMasterBean();
        tbAcBudgetReappropriationTr.setOrgid(tbAcBudgetReappropriation.getOrgid());
        tbAcBudgetReappropriationTr.setLangId(tbAcBudgetReappropriation.getLangId());
        tbAcBudgetReappropriationTr.setUserId(tbAcBudgetReappropriation.getUserId());
        tbAcBudgetReappropriationTr.setLmoddate(tbAcBudgetReappropriation.getLmoddate());
        tbAcBudgetReappropriationTr.setUpdatedBy(tbAcBudgetReappropriation.getUpdatedBy());
        tbAcBudgetReappropriationTr.setUpdatedDate(tbAcBudgetReappropriation.getUpdatedDate());
        if (tbAcBudgetReappropriation.getActualTaskId() == null) {
            tbAcBudgetReappropriationTr.setAuthFlag(tbAcBudgetReappropriation.getApproved());
        } else {
            tbAcBudgetReappropriationTr.setAuthFlag(MainetConstants.N_FLAG);
        }
        if (accountBudgetProjectedRevenueEntryBean.getPaAdjidTr() != null) {
            tbAcBudgetReappropriationTr.setPaAdjidTr(accountBudgetProjectedRevenueEntryBean.getPaAdjidTr());
        }
        if (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null) {
            tbAcBudgetReappropriationTr.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic());
        }

        final Long orgId = tbAcBudgetReappropriation.getOrgid();
        final Long faYearid = tbAcBudgetReappropriation.getFaYearid();
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

        final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        BigDecimal prCollected = null;
        if (entities != null) {
            for (final Object[] objects : entities) {
              if(objects[4]==accountBudgetProjectedRevenueEntryBean.getDpDeptid()) {
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
                    accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                            revisedEstamt.toString(), orgId);
                } else {
                    // final BigDecimal projectedAmount = revisedEstamt.subtract(prCollected);
                    accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                            revisedEstamt.toString(), orgId);
                }
            }
        }
       }      
    }

    private void reapprWorkFlowRevenueDataStoredFirstTable(ReappropriationOfBudgetAuthorizationDTO dto,
            AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean) {
        if ((accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev() != null)
                || (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null)) {
            dto.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRev());
        }
        final Long faYearid = dto.getFaYearid();
        final String budgCodeid = accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode();
        dto.setDpDeptid(accountBudgetProjectedRevenueEntryBean.getDpDeptid());
        dto.setRemark(accountBudgetProjectedRevenueEntryBean.getRemark());
        dto.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
        dto.setProvisionOldamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getOrginalEstamt()));
        dto.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrProjected()));
        if (accountBudgetProjectedRevenueEntryBean.getPrCollected() != null) {
            dto.setTransferAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getPrCollected()));
        }
        dto.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getRevisedEstamt()));
        final Long orgId = dto.getOrgid();
        final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        BigDecimal prCollected = null;
        if (entities != null) {
            for (final Object[] objects : entities) {
            if(objects[4]==accountBudgetProjectedRevenueEntryBean.getDpDeptid()) {
                if (objects[2] == null) {
                    final BigDecimal collectedDefvalue = new BigDecimal(0);
                    prCollected = collectedDefvalue;
                }
                if (objects[2] != null) {
                    prCollected = new BigDecimal(objects[2].toString());
                    prCollected = prCollected.setScale(2, RoundingMode.CEILING);
                }
                final Long prProjectionid = dto.getPrProjectionid();
                BigDecimal revisedEstamt = dto.getNewOrgRevAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                if (prCollected == null) {
                    // final BigDecimal projectedAmount = revisedEstamt;
                    accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                            revisedEstamt.toString(), orgId);
                } else {
                    // final BigDecimal projectedAmount = revisedEstamt.subtract(prCollected);
                    accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                            revisedEstamt.toString(), orgId);
                }
            }
        }
       }
    }

    private void reappWorkFlowExpenditureStoredDataSecondTable(ReappropriationOfBudgetAuthorizationDTO dto,
            AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean) {

        AccountBudgetReappropriationTrMasterBean acBudgetReappropriationTr = null;
        acBudgetReappropriationTr = new AccountBudgetReappropriationTrMasterBean();
        acBudgetReappropriationTr.setOrgid(dto.getOrgid());
        acBudgetReappropriationTr.setLangId(dto.getLangId());
        acBudgetReappropriationTr.setUserId(dto.getUserId());
        acBudgetReappropriationTr.setLmoddate(dto.getLmoddate());
        acBudgetReappropriationTr.setUpdatedBy(dto.getUpdatedBy());
        acBudgetReappropriationTr.setUpdatedDate(dto.getUpdatedDate());
        if (dto.getActualTaskId() == null) {
            acBudgetReappropriationTr.setAuthFlag(dto.getApproved());
        } else {
            acBudgetReappropriationTr.setAuthFlag(MainetConstants.N_FLAG);
        }
        acBudgetReappropriationTr.setDpDeptid(accountBudgetProjectedExpenditureBean.getDpDeptid());
        if (accountBudgetProjectedExpenditureBean.getPaAdjidTr() != null) {
            acBudgetReappropriationTr.setPaAdjidTr(accountBudgetProjectedExpenditureBean.getPaAdjidTr());
        }
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic() != null) {
            acBudgetReappropriationTr.setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic());
        }
        final Long orgId = dto.getOrgid();
        final Long faYearid = dto.getFaYearid();
        final String budgCodeid = accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstants.BLANK);
        acBudgetReappropriationTr.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        acBudgetReappropriationTr.setProvisionOldamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        acBudgetReappropriationTr.setTransferAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
        acBudgetReappropriationTr.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getRevisedEstamt()));
        acBudgetReappropriationTr.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getPrBalanceAmt()));

        final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        BigDecimal expenditureAmt = null;
        if (entities != null) {
            for (final Object[] objects : entities) {
             if(objects[4]==accountBudgetProjectedExpenditureBean.getDpDeptid()) {
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
                    accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                            revisedEstamt.toString(), orgId);
                } else {
                    // final BigDecimal balanceAmount = revisedEstamt.subtract(expenditureAmt);
                    accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                            revisedEstamt.toString(), orgId);
                }
            }
        }
      }
    }

    private void reappWorkFlowExpenditureStoredDataFirstTable(ReappropriationOfBudgetAuthorizationDTO dto,
            AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean) {
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExp() != null) {
            dto.setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExp());
        }
        final Long orgId = dto.getOrgid();
        final Long faYearid = dto.getFaYearid();
        final String budgCodeid = accountBudgetProjectedExpenditureBean.getPrExpBudgetCode();
        dto.setDpDeptid(accountBudgetProjectedExpenditureBean.getDpDeptid());
        dto.setRemark(accountBudgetProjectedExpenditureBean.getExpRemark());
        dto.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));
        dto.setProvisionOldamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        dto.setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getPrBalanceAmt()));
        if (accountBudgetProjectedExpenditureBean.getExpenditureAmt() != null) {
            dto.setTransferAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpenditureAmt()));
        }
        dto.setNewOrgRevAmount(new BigDecimal(accountBudgetProjectedExpenditureBean.getRevisedEstamt()));
        final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        BigDecimal expenditureAmt = null;
        if (entities != null) {
            for (final Object[] objects : entities) {
              if(objects[4]==accountBudgetProjectedExpenditureBean.getDpDeptid()) {
                if (objects[2] == null) {
                    final BigDecimal expDefaultValue = new BigDecimal(0);
                    expenditureAmt = expDefaultValue;
                }
                if (objects[2] != null) {
                    expenditureAmt = new BigDecimal(objects[2].toString());
                    expenditureAmt = expenditureAmt.setScale(2, RoundingMode.CEILING);
                }
                final Long prExpenditureid = dto.getPrExpenditureid();
                BigDecimal revisedEstamt = dto.getNewOrgRevAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                if (expenditureAmt == null) {
                    // final BigDecimal balanceAmount = revisedEstamt;
                    accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                            revisedEstamt.toString(), orgId);
                } else {
                    // final BigDecimal balanceAmount = revisedEstamt.subtract(expenditureAmt);
                    accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                            revisedEstamt.toString(), orgId);
                }
            }
        }
    }
  }
}
