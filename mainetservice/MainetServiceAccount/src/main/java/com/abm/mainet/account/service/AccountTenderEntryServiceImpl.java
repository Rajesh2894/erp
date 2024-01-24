
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountTenderEntryDao;
import com.abm.mainet.account.domain.AccountTenderDetEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountTenderDetBean;
import com.abm.mainet.account.dto.AccountTenderEntryBean;
import com.abm.mainet.account.repository.AccountTenderEntryJpaRepository;
import com.abm.mainet.account.utility.AccountWorkflowUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.TenderEntryAuthorization;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class AccountTenderEntryServiceImpl implements AccountTenderEntryService {

    private static final Logger LOGGER = Logger.getLogger(AccountTenderEntryServiceImpl.class);

    @Resource
    private AccountTenderEntryJpaRepository accountTenderEntryJparepository;

    @Resource
    private AccountTenderEntryDao accountTenderEntryDao;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private AccountDepositService accountDepositService;

    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;

    @Autowired
    private IWorkflowExecutionService workflowExecutionService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    private final String TB_AC_TENDER_MASTER = "TB_AC_TENDER_MASTER";

    private final String TR_TENDER_NO = "TR_TENDER_NO";

    private final String AUT_PREFIX_OTHER_FIELD_VALUE = "Please enter AUT Prefix - discription Bill Entry (BE) other field/value is : 'Y' or 'N'";

    @Autowired
    private TbFinancialyearJpaRepository finacialyearJpaRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTenderEntryService#findBy(java.lang. Long, java.lang.Long)
     */
    @Override
    @Transactional
    public AccountTenderEntryEntity findById(final Long trTenderId) {
        final AccountTenderEntryEntity entity = accountTenderEntryJparepository.findById(trTenderId);
        Hibernate.initialize(entity.getListOfTbAcTenderDet());
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTenderEntryService#update(com.abm. mainet.account.dto.AccountTenderEntryBean)
     */

    private String generateTenderNumber(final Long OrgId) {
        final Long tenderNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                TB_AC_TENDER_MASTER, TR_TENDER_NO, OrgId, MainetConstants.RECEIPT_MASTER.Reset_Type, null);
        return tenderNumber.toString();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTenderEntryService#create(com.abm. mainet.account.dto.AccountTenderEntryBean)
     */
    @Override
    @Transactional
    public AccountTenderEntryBean create(final AccountTenderEntryBean tenderEntryBean) throws Exception {
        final AccountTenderEntryEntity tender = new AccountTenderEntryEntity();

        if (tenderEntryBean.getWorkFlowIdentityFlag() == "Y") {
            AccountTenderEntryEntity entity = accountTenderEntryJparepository.findByTenderNo(tenderEntryBean.getTrTenderNo(),
                    tenderEntryBean.getOrgid());
            tenderEntryBean.setTrTenderId(entity.getTrTenderId());
        }
        BeanUtils.copyProperties(tenderEntryBean, tender);
        if (tenderEntryBean.getTrTenderNo() != null && !tenderEntryBean.getTrTenderNo().isEmpty()) {
            tender.setTrTenderNo(tenderEntryBean.getTrTenderNo());
        } else {
            final String tenderEntryNo = generateTenderNumber(tenderEntryBean.getOrgid());
            tender.setTrTenderNo(
                    tenderEntryBean.getOrgShortName() + "/WO/" + tenderEntryBean.getTrTenderDate() + "/" + tenderEntryNo);
            tenderEntryBean.setTrTenderNo(tender.getTrTenderNo());
        }
        ServiceMaster service = serviceMasterService.getServiceByShortName("WO", tenderEntryBean.getOrgid());
        if (service == null)
            return null;
        WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
                service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null, null);
        if (workflowMas != null) {
            try {

                if (tenderEntryBean.getWorkFlowIdentityFlag() == "Y") {
                    WorkflowProcessParameter processParameter = AccountWorkflowUtility
                            .prepareInitAccountWorkOrderEntryUpdateProcessParameter(tenderEntryBean, workflowMas,
                                    tenderEntryBean.getTrTenderNo());
                    workflowExecutionService.updateWorkflow(processParameter);
                } else {
                    WorkflowProcessParameter processParameter = AccountWorkflowUtility
                            .prepareInitAccountWorkOrderEntryProcessParameter(tenderEntryBean, workflowMas,
                                    tenderEntryBean.getTrTenderNo());
                    workflowExecutionService.initiateWorkflow(processParameter);
                }
            } catch (Exception e) {
                LOGGER.error(
                        "Unsuccessful initiation of task for application : " + tenderEntryBean.getTrTenderNo());
                throw new Exception(
                        "Unsuccessful initiation of task for application : " + tenderEntryBean.getTrTenderNo());
            }
        }
        if ((tenderEntryBean.getTrTenderId() != null) && (tenderEntryBean.getTrTenderId() > 0L)) {
            tenderEntryBean.setTrTenderId(tenderEntryBean.getTrTenderId());
        }
        final Department dep = new Department();
        dep.setDpDeptid(tenderEntryBean.getDpDeptid());
        tender.setTbDepartment(dep);

        final TbComparamDetEntity tenderType = new TbComparamDetEntity();
        tenderType.setCpdId(tenderEntryBean.getTrTypeCpdId());
        tender.setTbComparamDet(tenderType);

        final TbAcVendormasterEntity vendor = new TbAcVendormasterEntity();
        vendor.setVmVendorid(tenderEntryBean.getVmVendorid());
        tender.setTbVendormaster(vendor);

        tender.setTrTenderDate(UtilityService.convertStringDateToDateFormat(tenderEntryBean.getTrTenderDate()));

        tender.setTrProposalDate(UtilityService.convertStringDateToDateFormat(tenderEntryBean.getTrProposalDate()));

        if (tenderEntryBean.getTrEmdAmt() != null && !tenderEntryBean.getTrEmdAmt().isEmpty()) {
            tender.setTrEmdAmt(Long.valueOf(tenderEntryBean.getTrEmdAmt()));
        }

        tender.setTrEntryDate(new Date());

        final BigDecimal tenderAmt = new BigDecimal(tenderEntryBean.getTrTenderAmount());
        tender.setTrTenderAmount(tenderAmt);

        final List<AccountTenderDetEntity> tendeDetailsList = new ArrayList<>();

        for (final AccountTenderDetBean tenderDetail : tenderEntryBean.getTenderDetList()) {

            final AccountTenderDetEntity tenderDetEntity = new AccountTenderDetEntity();

            if ((tenderDetail.getTrTenderidDet() != null) && (tenderDetail.getTrTenderidDet() > 0L)) {
                tenderDetEntity.setTrTenderidDet(tenderDetail.getTrTenderidDet());
            }

            tenderDetEntity.setSacHeadId(tenderDetail.getSacHeadId());

            tenderDetEntity.setBudgetaryProv(tenderDetail.getBudgetaryProv());
            tenderDetEntity.setBalanceProv(tenderDetail.getBalanceProv());
            tenderDetEntity.setTenderDetailAmt(new BigDecimal(tenderDetail.getTrTenderAmount()));

            tenderDetEntity.setOrgid(tenderEntryBean.getOrgid());

            tenderDetEntity.setLangId(tenderEntryBean.getLangId());

            tenderDetEntity.setCreatedDate(tenderEntryBean.getCreatedDate());

            tenderDetEntity.setLgIpMac(tenderEntryBean.getLgIpMac());

            tenderDetEntity.setCreatedBy(tenderEntryBean.getCreatedBy());

            tenderDetEntity.setTbAcTenderMaster(tender);
            tendeDetailsList.add(tenderDetEntity);
        }
        tender.setListOfTbAcTenderDet(tendeDetailsList);

        final Organisation organisation = new Organisation();
        organisation.setOrgid(tenderEntryBean.getOrgid());

        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(TenderEntryAuthorization.BE,
                PrefixConstants.AccountJournalVoucherEntry.AUT, organisation);
        final String isMakerChecker = lkp.getOtherField();
        if (isMakerChecker == null || isMakerChecker.isEmpty()) {
            throw new NullPointerException(AUT_PREFIX_OTHER_FIELD_VALUE + " orgid is : " + tenderEntryBean.getOrgid());
        }
        if (isMakerChecker.equals(MainetConstants.MENU.Y)) {
            tender.setAuthorisedBy(tenderEntryBean.getAuthorisedBy());
            tender.setAuthDate(tenderEntryBean.getAuthDate());
            if ((tenderEntryBean.getAuthRemark() != null) && !tenderEntryBean.getAuthRemark().isEmpty()) {
                tender.setAuthRemark(tenderEntryBean.getAuthRemark());
            }
            if ((tender.getAuthStatus() != null) && !tender.getAuthStatus().isEmpty()) {
                tender.setAuthStatus(tender.getAuthStatus());
            } else {
                tender.setAuthStatus(MainetConstants.MENU.N);
            }
        } else {
            tender.setAuthorisedBy(tenderEntryBean.getAuthorisedBy());
            tender.setAuthDate(tenderEntryBean.getAuthDate());
            tender.setAuthStatus(MainetConstants.MENU.Y);
        }
        accountTenderEntryJparepository.save(tender);
        return tenderEntryBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTenderEntryService#isCombinationExists( java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean isCombinationExists(final Long fundId, final Long functionId, final Long fieldId, final Long pacId,
            final Long sacId) {

        return accountTenderEntryDao.isCombinationExists(fundId, functionId, fieldId, pacId, sacId);
    }

    @SuppressWarnings("unused")
    @Override
    @Transactional
    public List<AccountTenderEntryBean> findAll(final Long orgId) {
        final List<AccountTenderEntryEntity> entities = accountTenderEntryJparepository.findAll(orgId);

        final List<AccountTenderEntryBean> beanList = new ArrayList<>();
        for (final AccountTenderEntryEntity accountTenderEntryEntity : entities) {
            final AccountTenderEntryBean bean = new AccountTenderEntryBean();
            BeanUtils.copyProperties(accountTenderEntryEntity, bean);
            bean.setTrTenderId(accountTenderEntryEntity.getTrTenderId());
            bean.setTrTenderNo(accountTenderEntryEntity.getTrTenderNo());
            bean.setTrEntryDate(UtilityService.convertDateToDDMMYYYY(accountTenderEntryEntity.getTrTenderDate()));
            bean.setDpDeptid(accountTenderEntryEntity.getTbDepartment().getDpDeptid());
            bean.setTrTenderAmount(accountTenderEntryEntity.getTrTenderAmount().toString());

            final BigDecimal emdAmount = accountDepositService.getEmdAmount(accountTenderEntryEntity.getTrEmdAmt(),
                    orgId);
            if (emdAmount != null) {
                bean.setTrEmdAmt(emdAmount.toString());
            }

            if ((accountTenderEntryEntity.getAuthStatus() != null)
                    && !accountTenderEntryEntity.getAuthStatus().isEmpty()) {
                final String status = accountTenderEntryEntity.getAuthStatus();
                if (status.equals(MainetConstants.MENU.Y)) {
                    bean.setAuthStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
                }
                if (status.equals(MainetConstants.MENU.N)) {
                    bean.setAuthStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
                }
            }

            if (accountTenderEntryEntity.getTbVoucherEntry() != null
                    && (Long.valueOf(accountTenderEntryEntity.getTbVoucherEntry().getVouId()) != null)) {
                Long workOrderStatusFlag = accountTenderEntryEntity.getTbVoucherEntry().getVouId();
                if (workOrderStatusFlag != null) {
                    bean.setStatusCodeValue("Y");
                }
            }

            final List<AccountTenderDetEntity> prBudgetCodeIdList = accountTenderEntryEntity.getListOfTbAcTenderDet();
            final List<AccountTenderDetBean> childlist = new ArrayList<>();
            for (final AccountTenderDetEntity accountTenderDetEntity : prBudgetCodeIdList) {
                final AccountTenderDetBean dto = new AccountTenderDetBean();
                dto.setSacHeadId(accountTenderDetEntity.getSacHeadId());
                childlist.add(dto);
            }
            bean.setTenderDetList(childlist);
            beanList.add(bean);
        }
        return beanList;
    }

    @Override
    @Transactional
    public Map<Long, Long> findDepositTypeEmdData(final Long vmVendorid, final Long emdId, final Long orgId) {
        final Map<Long, Long> depositCodeMap = new LinkedHashMap<>();
        final List<Object[]> depositCodeList = accountTenderEntryJparepository.findDepositTypeEmdData(vmVendorid, emdId,
                orgId);
        for (final Object[] objects : depositCodeList) {
            if (objects != null) {
                depositCodeMap.put((Long.valueOf(objects[0].toString())), (Long.valueOf(objects[1].toString())));
            }
        }
        return depositCodeMap;
    }

    @Override
    @Transactional
    public List<AccountTenderEntryBean> findByAllGridSearchData(final String trTenderNo, final Long vmVendorid,
            final Long trTypeCpdId, final Long sacHeadId, final String trTenderAmount, final String statusId,
            final Long orgId) {
        final List<AccountTenderEntryEntity> entities = accountTenderEntryDao.findByAllGridSearchData(trTenderNo,
                vmVendorid, trTypeCpdId, sacHeadId, trTenderAmount, statusId, orgId);
        final List<AccountTenderEntryBean> beanList = new ArrayList<>();
        for (final AccountTenderEntryEntity accountTenderEntryEntity : entities) {

            final AccountTenderEntryBean bean = new AccountTenderEntryBean();
            BeanUtils.copyProperties(accountTenderEntryEntity, bean);
            bean.setTrTenderId(accountTenderEntryEntity.getTrTenderId());
            bean.setTrTenderNo(accountTenderEntryEntity.getTrTenderNo());
            bean.setTrEntryDate(UtilityService.convertDateToDDMMYYYY(accountTenderEntryEntity.getTrTenderDate()));
            bean.setDpDeptid(accountTenderEntryEntity.getTbDepartment().getDpDeptid());
            bean.setTrTenderAmount(accountTenderEntryEntity.getTrTenderAmount().toString());

            final BigDecimal emdAmount = accountDepositService.getEmdAmount(accountTenderEntryEntity.getTrEmdAmt(),
                    orgId);
            if (emdAmount != null) {
                bean.setTrEmdAmt(emdAmount.toString());
            }

            if ((accountTenderEntryEntity.getAuthStatus() != null)
                    && !accountTenderEntryEntity.getAuthStatus().isEmpty()) {
                final String status = accountTenderEntryEntity.getAuthStatus();
                if (status.equals(MainetConstants.MENU.Y)) {
                    bean.setAuthStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
                }
                if (status.equals(MainetConstants.MENU.N)) {
                    bean.setAuthStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
                }
            }
            if (accountTenderEntryEntity.getTbVoucherEntry() != null
                    && (Long.valueOf(accountTenderEntryEntity.getTbVoucherEntry().getVouId()) != null)) {
                Long workOrderStatusFlag = accountTenderEntryEntity.getTbVoucherEntry().getVouId();
                if (workOrderStatusFlag != null) {
                    bean.setStatusCodeValue("Y");
                }
            }
            final List<AccountTenderDetEntity> prBudgetCodeIdList = accountTenderEntryEntity.getListOfTbAcTenderDet();

            final List<AccountTenderDetBean> childlist = new ArrayList<>();
            for (final AccountTenderDetEntity accountTenderDetEntity : prBudgetCodeIdList) {
                final AccountTenderDetBean dto = new AccountTenderDetBean();
                dto.setSacHeadId(accountTenderDetEntity.getSacHeadId());
                childlist.add(dto);
            }
            bean.setTenderDetList(childlist);
            beanList.add(bean);
        }
        return beanList;
    }

    @Override
    @Transactional
    public AccountTenderEntryEntity findByTenderNo(String trTenderNo, Long orgId) {
        final AccountTenderEntryEntity entity = accountTenderEntryJparepository.findByTenderNo(trTenderNo, orgId);
        Hibernate.initialize(entity.getListOfTbAcTenderDet());
        return entity;
    }

}
