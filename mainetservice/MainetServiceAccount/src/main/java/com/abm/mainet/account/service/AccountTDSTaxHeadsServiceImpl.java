package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountTDSTaxHeadsDao;
import com.abm.mainet.account.domain.AccountTDSTaxHeadsEntity;
import com.abm.mainet.account.dto.AccountTDSTaxHeadsBean;
import com.abm.mainet.account.dto.AccountTDSTaxHeadsDto;
import com.abm.mainet.account.mapper.AccountTDSTaxHeadsServiceMapper;
import com.abm.mainet.account.repository.AccountTDSTaxHeadsJpaRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * Implementation of TbAcTdsTaxheadsService
 */
@Component
@Transactional
public class AccountTDSTaxHeadsServiceImpl implements AccountTDSTaxHeadsService {

    @Resource
    private AccountTDSTaxHeadsJpaRepository tbAcTdsTaxheadsJpaRepository;

    @Resource
    AccountTDSTaxHeadsDao accountTDSTaxHeadsDao;

    @Resource
    private AccountTDSTaxHeadsServiceMapper tbAcTdsTaxheadsServiceMapper;

    @Resource
    private BudgetHeadRepository budgetCodeRepository;

    @Resource
    private BudgetCodeService budgetCodeService;

    @Override
    @Transactional(readOnly = true)
    public AccountTDSTaxHeadsBean findById(final Long tdsId, final Long orgId) {
        final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntity = tbAcTdsTaxheadsJpaRepository.findOne(tdsId, orgId);
        return tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsEntityToTbAcTdsTaxheads(tbAcTdsTaxheadsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTDSTaxHeadsBean> findAll(final Long orgId) {

        final Iterable<AccountTDSTaxHeadsEntity> entities = tbAcTdsTaxheadsJpaRepository.findAll(orgId);
        List<AccountTDSTaxHeadsBean> beans = null;
        if (entities != null) {
            beans = new ArrayList<>();
            for (final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntity : entities) {
                beans.add(tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsEntityToTbAcTdsTaxheads(tbAcTdsTaxheadsEntity));
            }
        }
        return beans;
    }

    @Override
    public AccountTDSTaxHeadsBean save(final AccountTDSTaxHeadsBean tbAcTdsTaxheads) {
        return update(tbAcTdsTaxheads);
    }

    @Override
    @Transactional
    public AccountTDSTaxHeadsBean create(final AccountTDSTaxHeadsBean tbAcTdsTaxheads, final Organisation org) {
        AccountTDSTaxHeadsBean tbAcTdsTaxheadsBean = null;
        final List<AccountTDSTaxHeadsBean> tbAcTdsTaxheadsEntityList = new ArrayList<>();
        AccountTDSTaxHeadsEntity taxHeadsEntitySaved = null;
        String statusFlag = null;
        final List<AccountTDSTaxHeadsDto> list = tbAcTdsTaxheads.getTaxHeadsDtoList();
        if ((list != null) && !list.isEmpty()) {
            for (final AccountTDSTaxHeadsDto taxDto : list) {
                tbAcTdsTaxheadsBean = new AccountTDSTaxHeadsBean();
                tbAcTdsTaxheadsBean.setTdsEntrydate(tbAcTdsTaxheads.getTdsEntrydate());
                if ((taxDto.getTdsId() != null) && (taxDto.getTdsId() > 0l)) {
                    tbAcTdsTaxheadsBean.setTdsId(taxDto.getTdsId());
                }
                tbAcTdsTaxheadsBean.setOrgid(tbAcTdsTaxheads.getOrgid());
                tbAcTdsTaxheadsBean.setLangId(tbAcTdsTaxheads.getLangId());
                tbAcTdsTaxheadsBean.setUserId(tbAcTdsTaxheads.getUserId());
                tbAcTdsTaxheadsBean.setLmoddate(tbAcTdsTaxheads.getLmoddate());
                tbAcTdsTaxheadsBean.setLgIpMac(tbAcTdsTaxheads.getLgIpMac());
                statusFlag = getStatusFlagByCpdId(taxDto.getStatus(), org);
                tbAcTdsTaxheadsBean.setTdsStatusFlg(statusFlag);
                tbAcTdsTaxheadsBean.setBudgetCodeId(taxDto.getBudgetCodeId());
                tbAcTdsTaxheadsBean.setCpdIdDeductionType(tbAcTdsTaxheads.getCpdIdDeductionType());
                tbAcTdsTaxheadsEntityList.add(tbAcTdsTaxheadsBean);
            }
        }
        if ((tbAcTdsTaxheadsEntityList != null) && !tbAcTdsTaxheadsEntityList.isEmpty()) {
            for (final AccountTDSTaxHeadsBean taxHeadBeanSaved : tbAcTdsTaxheadsEntityList) {
                taxHeadsEntitySaved = new AccountTDSTaxHeadsEntity();
                tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsToTbAcTdsTaxheadsEntity(taxHeadBeanSaved, taxHeadsEntitySaved);
                taxHeadsEntitySaved = tbAcTdsTaxheadsJpaRepository.save(taxHeadsEntitySaved);
            }
        }
        return tbAcTdsTaxheads;
    }

    private String getStatusFlagByCpdId(final Long cpdId, final Organisation org) {
        final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(cpdId, org);
        return lookUp.getLookUpCode();
    }

    @Override
    @Transactional
    public AccountTDSTaxHeadsBean update(final AccountTDSTaxHeadsBean tbAcTdsTaxheads) {
        final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntity = tbAcTdsTaxheadsJpaRepository.findOne(tbAcTdsTaxheads.getTdsId());
        tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsToTbAcTdsTaxheadsEntity(tbAcTdsTaxheads, tbAcTdsTaxheadsEntity);
        final AccountTDSTaxHeadsEntity tbAcTdsTaxheadsEntitySaved = tbAcTdsTaxheadsJpaRepository.save(tbAcTdsTaxheadsEntity);
        return tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsEntityToTbAcTdsTaxheads(tbAcTdsTaxheadsEntitySaved);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTDSTaxHeadsService# getDetailsUsingTdsID
     * (com.abm.mainet.account.dto.AccountTDSTaxHeadsBean)
     */
    @Override
    @Transactional(readOnly = true)
    public AccountTDSTaxHeadsBean getDetailsUsingTdsID(final AccountTDSTaxHeadsBean tdsTaxHeadsMasterBean) {

        final AccountTDSTaxHeadsBean tdsTaxHeadsMaster = new AccountTDSTaxHeadsBean();
        final Long tdsId = tdsTaxHeadsMasterBean.getTdsId();
        final AccountTDSTaxHeadsEntity tdsEntity = tbAcTdsTaxheadsJpaRepository.findOne(tdsId);
        tdsTaxHeadsMaster.setTdsEntrydate(tdsEntity.getTdsEntrydate());
        return tdsTaxHeadsMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTDSTaxHeadsService# getSaHeadItemsList(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHeadSecondaryAccountCodeMasterEntity> getSaHeadItemsList(final Long pacHeadId, final Long orgId) {
        return tbAcTdsTaxheadsJpaRepository.getSaHeadItemsList(pacHeadId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTDSTaxHeadsBean> getTdsMappingData(final Long orgId, final Long accountHeadId, final Long tdsTypeId,
            final String status) {
        List<AccountTDSTaxHeadsBean> beans = null;
        final List<AccountTDSTaxHeadsEntity> tbAcTdsTaxheadsEntity = accountTDSTaxHeadsDao.getTDSDetails(orgId, accountHeadId,
                tdsTypeId, status);
        if (tbAcTdsTaxheadsEntity != null) {
            beans = new ArrayList<>();
            for (final AccountTDSTaxHeadsEntity tdsTaxheadsEntity : tbAcTdsTaxheadsEntity) {
                beans.add(tbAcTdsTaxheadsServiceMapper.mapTbAcTdsTaxheadsEntityToTbAcTdsTaxheads(tdsTaxheadsEntity));
            }
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountTDSTaxHeadsService#getTdsTypeCount(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Long getTdsTypeCount(final Long tdsTypeCpdId) {
        return tbAcTdsTaxheadsJpaRepository.getTdsTypeCount(tdsTypeCpdId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getBudgetHeadDescription(final Long headTypeId, final Long budgetCodeId, final Long superOrgId,
            final Long orgId) {

        String budgetHead = null;
        final List<Object[]> budgetHeadList = budgetCodeRepository.fetchBudgetHeads(null, headTypeId, budgetCodeId, superOrgId,
                orgId);
        for (final Object[] deductionDet : budgetHeadList) {
            budgetHead = deductionDet[1].toString();
        }
        return budgetHead;
    }

}
