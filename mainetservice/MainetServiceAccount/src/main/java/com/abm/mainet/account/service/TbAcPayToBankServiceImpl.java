package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.TbAcPayToBankEntity;
import com.abm.mainet.account.domain.TbAcPayToBankHistoryEntity;
import com.abm.mainet.account.dto.TbAcPayToBank;
import com.abm.mainet.account.mapper.TbAcPayToBankServiceMapper;
import com.abm.mainet.account.repository.TbAcPayToBankJpaRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * Implementation of TbAcPayToBankService
 */
@Component
public class TbAcPayToBankServiceImpl implements TbAcPayToBankService {

    @Resource
    private TbAcPayToBankJpaRepository tbAcPayToBankJpaRepository;

    @Resource
    private TbAcPayToBankServiceMapper tbAcPayToBankServiceMapper;
    @Resource
    private AuditService auditService;

    private static Logger LOGGER = Logger.getLogger(TbAcPayToBankServiceImpl.class);

    @Override
    @Transactional
    public TbAcPayToBank findById(final Long ptbId) {
        final TbAcPayToBankEntity tbAcPayToBankEntity = tbAcPayToBankJpaRepository.findOne(ptbId);

        final TbAcPayToBank tbAcPayToBank = tbAcPayToBankServiceMapper
                .mapTbAcPayToBankEntityToTbAcPayToBank(tbAcPayToBankEntity);

        tbAcPayToBank.setVmVendorid(tbAcPayToBankEntity.getTbAcVendormaster().getVmVendorid());
        if (tbAcPayToBankEntity.getBankMaster() != null && tbAcPayToBankEntity.getBankMaster().getBankId() != null) {
            tbAcPayToBank.setBankId(tbAcPayToBankEntity.getBankMaster().getBankId());
            tbAcPayToBank.setPtbBankbranch(tbAcPayToBankEntity.getBankMaster().getBank());
        }
        return tbAcPayToBank;
    }

    @Override
    @Transactional
    public List<TbAcPayToBank> findAll() {
        final Iterable<TbAcPayToBankEntity> entities = tbAcPayToBankJpaRepository.findAll();
        final List<TbAcPayToBank> beans = new ArrayList<>();
        for (final TbAcPayToBankEntity tbAcPayToBankEntity : entities) {
            beans.add(tbAcPayToBankServiceMapper.mapTbAcPayToBankEntityToTbAcPayToBank(tbAcPayToBankEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public TbAcPayToBank save(final TbAcPayToBank tbAcPayToBank) {
        return update(tbAcPayToBank);
    }

    @Override
    @Transactional
    public TbAcPayToBank create(final TbAcPayToBank tbAcPayToBank) {

        final TbAcPayToBankEntity tbAcPayToBankEntity = new TbAcPayToBankEntity();

        tbAcPayToBankServiceMapper.mapTbAcPayToBankToTbAcPayToBankEntity(tbAcPayToBank, tbAcPayToBankEntity);

        tbAcPayToBankEntity.setPtbStatus(MainetConstants.STATUS.ACTIVE);

        final BankMasterEntity bankMaster = new BankMasterEntity();
        if (tbAcPayToBank.getBankId() != null) {
            bankMaster.setBankId(tbAcPayToBank.getBankId());
            tbAcPayToBankEntity.setBankMaster(bankMaster);
        }

        final TbAcVendormasterEntity tbAcVendormaster = new TbAcVendormasterEntity();
        tbAcVendormaster.setVmVendorid(tbAcPayToBank.getVmVendorid());
        tbAcPayToBankEntity.setTbAcVendormaster(tbAcVendormaster);

        final TbAcPayToBankEntity tbAcPayToBankEntitySaved = tbAcPayToBankJpaRepository.save(tbAcPayToBankEntity);
        return tbAcPayToBankServiceMapper.mapTbAcPayToBankEntityToTbAcPayToBank(tbAcPayToBankEntitySaved);
    }

    @Override
    @Transactional
    public TbAcPayToBank update(final TbAcPayToBank tbAcPayToBank) {
        final TbAcPayToBankEntity tbAcPayToBankEntity = tbAcPayToBankJpaRepository.findOne(tbAcPayToBank.getPtbId());
        tbAcPayToBankServiceMapper.mapTbAcPayToBankToTbAcPayToBankEntity(tbAcPayToBank, tbAcPayToBankEntity);

        final BankMasterEntity bankMaster = new BankMasterEntity();
        if (tbAcPayToBank.getBankId() != null) {
            bankMaster.setBankId(tbAcPayToBank.getBankId());
            tbAcPayToBankEntity.setBankMaster(bankMaster);
        }

        final TbAcVendormasterEntity tbAcVendormaster = new TbAcVendormasterEntity();
        tbAcVendormaster.setVmVendorid(tbAcPayToBank.getVmVendorid());
        tbAcPayToBankEntity.setTbAcVendormaster(tbAcVendormaster);

        final AccountHeadSecondaryAccountCodeMasterEntity secondaryAccountCodeMaster = new AccountHeadSecondaryAccountCodeMasterEntity();
        secondaryAccountCodeMaster.setSacHeadId(tbAcPayToBank.getSacHeadId());
        tbAcPayToBankEntity.setSecondaryAccountCodeMaster(secondaryAccountCodeMaster);
        if (tbAcPayToBank.getPtbBankAcNo() != null) {
            tbAcPayToBankEntity.setPtbBankAcNo(tbAcPayToBank.getPtbBankAcNo());
        }
        if (tbAcPayToBank.getPtbBsrcode() != null) {
            tbAcPayToBankEntity.setPtbBsrcode(tbAcPayToBank.getPtbBsrcode());
        }
        final TbAcPayToBankHistoryEntity tbAcPayToBankHistoryEntity = new TbAcPayToBankHistoryEntity();
        try {
            auditService.createHistory(tbAcPayToBankEntity, tbAcPayToBankHistoryEntity);
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + tbAcPayToBankEntity, ex);
        }

        final TbAcPayToBankEntity tbAcPayToBankEntitySaved = tbAcPayToBankJpaRepository.save(tbAcPayToBankEntity);
        return tbAcPayToBankServiceMapper.mapTbAcPayToBankEntityToTbAcPayToBank(tbAcPayToBankEntitySaved);
    }

    @Override
    @Transactional
    public void delete(final Long ptbId) {
        tbAcPayToBankJpaRepository.delete(ptbId);
    }

    public TbAcPayToBankJpaRepository getTbAcPayToBankJpaRepository() {
        return tbAcPayToBankJpaRepository;
    }

    public void setTbAcPayToBankJpaRepository(final TbAcPayToBankJpaRepository tbAcPayToBankJpaRepository) {
        this.tbAcPayToBankJpaRepository = tbAcPayToBankJpaRepository;
    }

    public TbAcPayToBankServiceMapper getTbAcPayToBankServiceMapper() {
        return tbAcPayToBankServiceMapper;
    }

    public void setTbAcPayToBankServiceMapper(final TbAcPayToBankServiceMapper tbAcPayToBankServiceMapper) {
        this.tbAcPayToBankServiceMapper = tbAcPayToBankServiceMapper;
    }

    @Override
    @Transactional
    public List<TbAcPayToBank> getBankTDSData(final Long orgid) {

        final List<TbAcPayToBank> beans = new ArrayList<>(0);
        TbAcPayToBank account = null;

        final List<TbAcPayToBankEntity> entity = tbAcPayToBankJpaRepository.getBankTDSData(orgid);

        for (final TbAcPayToBankEntity data : entity) {
            account = new TbAcPayToBank();
            account.setPtbId(data.getPtbId());
            beans.add(account);
        }
        return beans;
    }

    @Override
    @Transactional
    public boolean isCombinationExists(final Long ptbTdsType, final Long orgId, final String status) {
        final List<TbAcPayToBankEntity> entity = tbAcPayToBankJpaRepository.isCombinationExists(ptbTdsType, orgId,
                status);
        if (entity.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<TbAcPayToBank> getTdsTypeData(final Long tdsType, final Long orgid, final Long defaultOrgid) {
        final List<TbAcPayToBank> list = new ArrayList<>();
        final List<TbAcPayToBankEntity> entity = tbAcPayToBankJpaRepository.getTdsTypeData(tdsType, orgid);
        if ((entity != null) && !entity.isEmpty()) {
            for (final TbAcPayToBankEntity tbAcPayToBankEntity : entity) {
                final TbAcPayToBank dto = new TbAcPayToBank();
                dto.setPtbId(tbAcPayToBankEntity.getPtbId());
                dto.setVmVendorid(tbAcPayToBankEntity.getTbAcVendormaster().getVmVendorid());
                dto.setTdsTypeName(CommonMasterUtility.findLookUpDesc(PrefixConstants.TDS, defaultOrgid,
                        tbAcPayToBankEntity.getPtbTdsType()));
                dto.setVendorCode(tbAcPayToBankEntity.getTbAcVendormaster().getVmVendorcode()
                        + MainetConstants.SEPARATOR + tbAcPayToBankEntity.getTbAcVendormaster().getVmVendorname());
                dto.setPtbBankAcNo(tbAcPayToBankEntity.getPtbBankAcNo());
                dto.setStatusName(CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.ACN, orgid,
                        tbAcPayToBankEntity.getPtbStatus()));
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBSRCodeCombinationExists(final Long bankId, final String ptbBsrcode) {
        final List<TbAcPayToBankEntity> entity = tbAcPayToBankJpaRepository.isBSRCodeCombinationExists(bankId,
                ptbBsrcode);
        if (entity.isEmpty()) {
            return false;
        }
        return true;
    }

}
