/*
 * Created on 6 Jun 2016 ( Time 16:22:31 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.integration.acccount.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dao.TbAcCodingstructureMasDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.mapper.AccountFundMasterServiceMapper;
import com.abm.mainet.common.integration.acccount.mapper.TbAcCodingstructureMasServiceMapper;
import com.abm.mainet.common.integration.acccount.repository.TbAcCodingstructureDetJpaRepository;
import com.abm.mainet.common.integration.acccount.repository.TbAcCodingstructureMasJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * Implementation of TbAcCodingstructureMasService
 */
@Component
@Transactional
public class TbAcCodingstructureMasServiceImpl implements TbAcCodingstructureMasService {

    @Resource
    private TbAcCodingstructureMasJpaRepository tbAcCodingstructureMasJpaRepository;

    @Resource
    private TbAcCodingstructureMasServiceMapper tbAcCodingstructureMasServiceMapper;

    @Resource
    private TbAcCodingstructureDetJpaRepository tbAcCodingstructureDetJpaRepository;

    @Resource
    private AccountFundMasterServiceMapper accountFundMasterServiceMapper;

    @Resource
    private TbAcCodingstructureMasDao tbAcCodingstructureMasDao;

    @Override
    @Transactional(readOnly=true)
    public TbAcCodingstructureMas findById(final Long codcofId) {
        final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity = tbAcCodingstructureMasJpaRepository.findOne(codcofId);
        return tbAcCodingstructureMasServiceMapper
                .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntity);
    }

    @Override
    @Transactional(readOnly=true)
    public List<TbAcCodingstructureMas> findAll() {
        final Iterable<TbAcCodingstructureMasEntity> entities = tbAcCodingstructureMasJpaRepository.findAll();
        final List<TbAcCodingstructureMas> beans = new ArrayList<>();
        for (final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity : entities) {
            beans.add(tbAcCodingstructureMasServiceMapper
                    .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntity));
        }
        return beans;
    }

    @Override
    @Transactional(readOnly=true)
    public List<TbAcCodingstructureMas> findAllWithOrgId(final Long orgid) {
        final Iterable<TbAcCodingstructureMasEntity> entities = tbAcCodingstructureMasJpaRepository.findAllUsingOrgId(orgid);
        final List<TbAcCodingstructureMas> beans = new ArrayList<>();
        for (final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity : entities) {
            beans.add(tbAcCodingstructureMasServiceMapper
                    .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntity));
        }
        return beans;
    }

    @Override
    public TbAcCodingstructureMas save(final TbAcCodingstructureMas tbAcCodingstructureMas) {
        return update(tbAcCodingstructureMas);
    }

    @Override
    @Transactional
    public TbAcCodingstructureMas create(final TbAcCodingstructureMas tbAcCodingstructureMas) {
        final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity = new TbAcCodingstructureMasEntity();
        TbAcCodingstructureMasEntity tbAcCodingstructureMasEntitySaved = new TbAcCodingstructureMasEntity();
        if ((tbAcCodingstructureMas.getDefineOnflag() == null) || tbAcCodingstructureMas.getDefineOnflag().isEmpty()) {
            tbAcCodingstructureMas.setDefineOnflag(MainetConstants.N_FLAG);
        }
        if ((tbAcCodingstructureMas.getComAppflag() != null) && !tbAcCodingstructureMas.getComAppflag().isEmpty()) {
            tbAcCodingstructureMas.setComAppflag(tbAcCodingstructureMas.getComAppflag());
        } else {
            tbAcCodingstructureMas.setComAppflag(MainetConstants.FlagA);
        }
        tbAcCodingstructureMasServiceMapper.mapTbAcCodingstructureMasToTbAcCodingstructureMasEntity(tbAcCodingstructureMas,
                tbAcCodingstructureMasEntity);
        List<TbAcCodingstructureDetEntity> detailsList = new ArrayList<>(0);
        detailsList = tbAcCodingstructureMasEntity.getTbAcCodingstructureDetEntity();
        tbAcCodingstructureMasEntity.setTbAcCodingstructureDetEntity(null);
        tbAcCodingstructureMasEntitySaved = tbAcCodingstructureMasJpaRepository.save(tbAcCodingstructureMasEntity);

        for (final TbAcCodingstructureDetEntity details : detailsList) {
            details.setOrgId(tbAcCodingstructureMasEntity.getOrgid());
            details.setLmodDate(tbAcCodingstructureMasEntity.getLmoddate());
            details.setUserId(tbAcCodingstructureMasEntity.getUserId());
            details.setLgIpMac(tbAcCodingstructureMasEntity.getLgIpMac());
            details.setTbAcCodingstructureMasEntity(tbAcCodingstructureMasEntitySaved.getCodcofId());
            tbAcCodingstructureDetJpaRepository.save(details);
        }

        return tbAcCodingstructureMasServiceMapper
                .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntitySaved);
    }

    @Override
    @Transactional
    public TbAcCodingstructureMas update(final TbAcCodingstructureMas tbAcCodingstructureMas) {
        final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity = tbAcCodingstructureMasJpaRepository
                .findOne(tbAcCodingstructureMas.getCodcofId());
        if (tbAcCodingstructureMas.getTestCodNoLevel() != null) {
            tbAcCodingstructureMas.setCodNoLevel(tbAcCodingstructureMas.getTestCodNoLevel());
        }
        if ((tbAcCodingstructureMas.getDefineOnflag() == null) || tbAcCodingstructureMas.getDefineOnflag().isEmpty()) {
            tbAcCodingstructureMas.setDefineOnflag(MainetConstants.N_FLAG);
        }
        tbAcCodingstructureMasServiceMapper.mapTbAcCodingstructureMasToTbAcCodingstructureMasEntity(tbAcCodingstructureMas,
                tbAcCodingstructureMasEntity);
        TbAcCodingstructureMasEntity tbAcCodingstructureMasEntitySaved = tbAcCodingstructureMasJpaRepository
                .save(tbAcCodingstructureMasEntity);
        List<TbAcCodingstructureDetEntity> detailsList = new ArrayList<>(0);
        detailsList = tbAcCodingstructureMasEntity.getTbAcCodingstructureDetEntity();
        tbAcCodingstructureMasEntity.setTbAcCodingstructureDetEntity(null);
        tbAcCodingstructureMasEntitySaved = tbAcCodingstructureMasJpaRepository.save(tbAcCodingstructureMasEntity);
        for (final TbAcCodingstructureDetEntity details : detailsList) {
            details.setUpdatedBy(tbAcCodingstructureMasEntity.getUpdatedBy());
            details.setUpdatedDate(tbAcCodingstructureMasEntity.getUpdatedDate());
            details.setLmodDate(tbAcCodingstructureMasEntity.getLmoddate());
            details.setLgIpMac(tbAcCodingstructureMasEntity.getLgIpMac());
            details.setLgIpMacUpd(tbAcCodingstructureMasEntity.getLgIpMacUpd());
            details.setTbAcCodingstructureMasEntity(tbAcCodingstructureMasEntity.getCodcofId());
            tbAcCodingstructureDetJpaRepository.save(details);
        }

        return tbAcCodingstructureMasServiceMapper
                .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntitySaved);
    }

    @Override
    @Transactional
    public void delete(final Long codcofId) {
        tbAcCodingstructureMasJpaRepository.delete(codcofId);
    }

    public TbAcCodingstructureMasJpaRepository getTbAcCodingstructureMasJpaRepository() {
        return tbAcCodingstructureMasJpaRepository;
    }

    public void setTbAcCodingstructureMasJpaRepository(
            final TbAcCodingstructureMasJpaRepository tbAcCodingstructureMasJpaRepository) {
        this.tbAcCodingstructureMasJpaRepository = tbAcCodingstructureMasJpaRepository;
    }

    public TbAcCodingstructureMasServiceMapper getTbAcCodingstructureMasServiceMapper() {
        return tbAcCodingstructureMasServiceMapper;
    }

    public void setTbAcCodingstructureMasServiceMapper(
            final TbAcCodingstructureMasServiceMapper tbAcCodingstructureMasServiceMapper) {
        this.tbAcCodingstructureMasServiceMapper = tbAcCodingstructureMasServiceMapper;
    }

    @Override
    @Transactional(readOnly=true)
    public TbAcCodingstructureMasEntity findConfigurationMasterEntiry(final Long compDet, final Long orgId,
            final String activeStatusCode) {
        return tbAcCodingstructureMasJpaRepository.findConfigurationMasterEntiry(compDet, orgId, activeStatusCode);
    }

    @Override
    @Transactional(readOnly=true)
    public List<AccountFundMasterBean> getFundMasterList(final Long cpdFundId) {

        final List<AccountFundMasterEntity> entities = tbAcCodingstructureMasJpaRepository.getFundMasterForMapping(cpdFundId);
        final List<AccountFundMasterBean> beans = new ArrayList<>();
        Boolean isParentEntity = false;
        for (final AccountFundMasterEntity tbAcCodingstructureMasEntity : entities) {
            if (tbAcCodingstructureMasEntity.getFundParentId() == null) {
                isParentEntity = true;
            } else {
                isParentEntity = false;
            }
            beans.add(accountFundMasterServiceMapper.mapTbAcFundMasterEntityToTbAcFundMaster(tbAcCodingstructureMasEntity,
                    isParentEntity));
        }
        return beans;

    }

    @Override
    @Transactional(readOnly=true)
    public List<AccountFundMasterBean> getActiveFundMasterList(final Long cpdFundId) {
        final List<AccountFundMasterBean> beans = new ArrayList<>();
        final Long activeStatusId = getActiveStatusId();
        if (activeStatusId != null) {
            final List<AccountFundMasterEntity> entities = tbAcCodingstructureMasJpaRepository.getActiveFundMasterForMapping(
                    cpdFundId,
                    activeStatusId);
            Boolean isParentEntity = false;
            for (final AccountFundMasterEntity tbAcCodingstructureMasEntity : entities) {
                if (tbAcCodingstructureMasEntity.getFundParentId() == null) {
                    isParentEntity = true;
                } else {
                    isParentEntity = false;
                }
                beans.add(accountFundMasterServiceMapper.mapTbAcFundMasterEntityToTbAcFundMaster(tbAcCodingstructureMasEntity,
                        isParentEntity));
            }
        }
        return beans;

    }

    public Long getActiveStatusId() {
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.LookUp.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }

    @Override
    @Transactional(readOnly=true)
    public List<TbAcCodingstructureMas> findByAllGridSearchData(final Long comCpdId, final Long orgId) {
        final List<TbAcCodingstructureMasEntity> entiry = tbAcCodingstructureMasDao.findByAllGridSearchData(comCpdId, orgId);
        final List<TbAcCodingstructureMas> beans = new ArrayList<>();
        for (final TbAcCodingstructureMasEntity tbAcCodingstructureMasEntity : entiry) {
            beans.add(tbAcCodingstructureMasServiceMapper
                    .mapTbAcCodingstructureMasEntityToTbAcCodingstructureMas(tbAcCodingstructureMasEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public boolean checkDefaultFlagIsExists(final String comCpdIdCode, final Long orgId, final String defaultFlag) {
        final List<TbAcCodingstructureMasEntity> list = tbAcCodingstructureMasJpaRepository.checkDefaultFlagIsExists(comCpdIdCode,
                orgId, defaultFlag);
        if ((list != null) && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly=true)
    public List<LookUp> queryAccountHeadByChartOfAccount(final Long param1, final Long param2, final Long orgId) {
    	List<Object[]> results =null;
        results = tbAcCodingstructureMasJpaRepository.queryAccountHeadByChartOfAccount(param1, param2,
                orgId);
        if ((results == null) || results.isEmpty()) {
        	results=tbAcCodingstructureMasJpaRepository.queryAccountHeadByChartOfAccount(param1, param2,
        			ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        	if ((results == null) || results.isEmpty()) {
            throw new NullPointerException("No records found for Account Head By ChartOfAccount [cpdId1=" + param1 + ",cpdId2="
                    + param2 + ",orgId=" + orgId);
        	}
        }
        final List<LookUp> list = new ArrayList<>();
        for (final Object[] objects : results) {
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId((long) objects[0]);
            if (objects[1] != null) {
                lookUp.setDescLangFirst((String) objects[1]);
            }
            list.add(lookUp);
        }
        return list;
    }

    @Override
    @Transactional
    public List<AccountFundMasterBean> getFundMasterActiveStatusList(final Long orgid, final Organisation org,
            final Long cpdFundId, final int langId) {

        final List<AccountFundMasterEntity> entities = tbAcCodingstructureMasJpaRepository.getFundMasterForDefaultorgMapping(
                cpdFundId,
                orgid);
        final List<AccountFundMasterBean> beans = new ArrayList<>();
        Boolean isParentEntity = false;
        for (final AccountFundMasterEntity tbAcCodingstructureMasEntity : entities) {

            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, org);
            final Long lookUpStatusId = statusLookup.getLookUpId();

            if (tbAcCodingstructureMasEntity.getFundParentId() == null) {
                isParentEntity = true;
            } else {
                isParentEntity = false;
            }
            if (lookUpStatusId.equals(tbAcCodingstructureMasEntity.getFundStatusCpdId())) {
                beans.add(accountFundMasterServiceMapper.mapTbAcFundMasterEntityToTbAcFundMaster(tbAcCodingstructureMasEntity,
                        isParentEntity));
            }
        }
        return beans;

    }

}
