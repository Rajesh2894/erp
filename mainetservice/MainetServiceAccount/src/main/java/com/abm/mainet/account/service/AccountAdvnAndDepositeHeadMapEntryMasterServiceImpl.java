package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountAdvnAndDepostieMasterDao;
import com.abm.mainet.account.domain.AccountDepositeAndAdvnMasterEntity;
import com.abm.mainet.account.dto.AccountDepositeAndAdvnHeadsMappingEntryMasterBean;
import com.abm.mainet.account.dto.DeasMasterEntryDto;
import com.abm.mainet.account.repository.TbAcAdvanceHeadMappingMasterJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 * Implementation of TbAcFunctionMasterService
 */
@Component
public class AccountAdvnAndDepositeHeadMapEntryMasterServiceImpl implements AccountAdvnAndDepositeHeadMapEntryMasterService {

    @Resource
    private TbAcAdvanceHeadMappingMasterJpaRepository tbAcAdvnHeadMappingJpaRepository;

    @Resource
    private AccountAdvnAndDepostieMasterDao accountAdvnAndDepostieMasterDao;

    @Transactional
    @Override
    public void save(final AccountDepositeAndAdvnHeadsMappingEntryMasterBean headsMapEntryBean, final Long orgId,
            final int langId, final Long empId,
            final Long cpdId, String ipMacAddress) {

        final TbComparamDetEntity prefixForHDM = new TbComparamDetEntity();
        prefixForHDM.setCpdId(headsMapEntryBean.getMappingType());
        final TbComparamDetEntity prefixForDepAdvType = new TbComparamDetEntity();

        Long prefixId = null;

        if (headsMapEntryBean.getAdvancedType() != 0) {
            prefixId = headsMapEntryBean.getAdvancedType();
        } else {
            prefixId = headsMapEntryBean.getDepositeType();
        }
        prefixForDepAdvType.setCpdId(prefixId);

        final List<AccountDepositeAndAdvnMasterEntity> listOfEntity = new ArrayList<>();

        iterateAndSetEntites(headsMapEntryBean, orgId, langId, empId, cpdId, prefixForHDM, prefixForDepAdvType, listOfEntity,
                ipMacAddress);

        tbAcAdvnHeadMappingJpaRepository.save(listOfEntity);
    }

    /**
     * @param headsMapEntryBean
     * @param orgId
     * @param langId
     * @param empId
     * @param cpdId
     * @param prefixForHDM
     * @param prefixForDepAdvType
     * @param listOfEntity
     */
    private void iterateAndSetEntites(
            final AccountDepositeAndAdvnHeadsMappingEntryMasterBean headsMapEntryBean,
            final Long orgId, final int langId, final Long empId, final Long cpdId,
            final TbComparamDetEntity prefixForHDM,
            final TbComparamDetEntity prefixForDepAdvType,
            final List<AccountDepositeAndAdvnMasterEntity> listOfEntity, final String ipMacAddress) {
        AccountDepositeAndAdvnMasterEntity entity;
        AccountFunctionMasterEntity functionMasterEntity;
        AccountFieldMasterEntity fieldMasterEntity;
        AccountFundMasterEntity fundMasterEntity;
        AccountHeadPrimaryAccountCodeMasterEntity primaryCodeEntity;
        AccountHeadSecondaryAccountCodeMasterEntity secondaryCodeEntity;
        final Date currentDate = new Date();
        for (final DeasMasterEntryDto dto : headsMapEntryBean.getListOfDto()) {
            entity = new AccountDepositeAndAdvnMasterEntity();
            entity.setTbComparamDetHdm(prefixForHDM);
            entity.setTbComparamDetDtyAty(prefixForDepAdvType);

            if (headsMapEntryBean.getDeptId() != 0) {
                entity.setDept(headsMapEntryBean.getDeptId());
            }

            fundMasterEntity = new AccountFundMasterEntity();
            fundMasterEntity.setFundId(dto.getFundId());
            entity.setFundMasterEntity(fundMasterEntity);

            functionMasterEntity = new AccountFunctionMasterEntity();
            functionMasterEntity.setFunctionId(dto.getFunctionId());
            entity.setFunctionMasterEntity(functionMasterEntity);

            fieldMasterEntity = new AccountFieldMasterEntity();
            fieldMasterEntity.setFieldId(dto.getFieldId());
            entity.setFieldMasterEntity(fieldMasterEntity);

            primaryCodeEntity = new AccountHeadPrimaryAccountCodeMasterEntity();
            primaryCodeEntity.setPrimaryAcHeadId(dto.getPrimaryCodeId());
            entity.setPrimaryCodeEntity(primaryCodeEntity);

            secondaryCodeEntity = new AccountHeadSecondaryAccountCodeMasterEntity();
            secondaryCodeEntity.setSacHeadId(dto.getSecondaryCodeId());
            entity.setSecondaryCodeEntity(secondaryCodeEntity);

            entity.setRemarkDesc(dto.getRemark());
            entity.setStatus(MainetConstants.MASTER.Y);
            entity.setOrgid(orgId);
            entity.setUserId(empId);
            entity.setLangId(langId);
            entity.setLmoddate(currentDate);
            entity.setLgIpMac(ipMacAddress);

            final TbComparamDetEntity det = new TbComparamDetEntity();
            det.setCpdId(cpdId);
            entity.setTbComparamDet(det);
            if (dto.getEntityId() != null) {
                entity.setDampId(dto.getEntityId());
            }
            listOfEntity.add(entity);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeasMasterEntryDto> searchRecordUsingRequestParam(final long orgid, final Long mappingType,
            final Long advncOrDepType,
            final Long deptId) {

        final List<AccountDepositeAndAdvnMasterEntity> listOfEntity = accountAdvnAndDepostieMasterDao
                .getDepositeAndAdvncMappingEntityList(orgid, mappingType, advncOrDepType, deptId);
        final List<DeasMasterEntryDto> dtoList = new ArrayList<>();
        DeasMasterEntryDto dto = null;
        for (final AccountDepositeAndAdvnMasterEntity entity : listOfEntity) {
            dto = new DeasMasterEntryDto();

            dto.setFundId(entity.getFundMasterEntity().getFundId());
            dto.setFieldId(entity.getFieldMasterEntity().getFieldId());
            dto.setFunctionId(entity.getFunctionMasterEntity().getFunctionId());
            dto.setPrimaryCodeId(entity.getPrimaryCodeEntity().getPrimaryAcHeadId());
            dto.setSecondaryCodeId(entity.getSecondaryCodeEntity().getSacHeadId());

            dto.setFundCode(entity.getFundMasterEntity().getFundCompositecode() + MainetConstants.SEPARATOR
                    + entity.getFundMasterEntity().getFundDesc());
            dto.setFieldCode(entity.getFieldMasterEntity().getFieldCompcode() + MainetConstants.SEPARATOR
                    + entity.getFieldMasterEntity().getFieldDesc());
            dto.setFunctionCode(entity.getFunctionMasterEntity().getFunctionCompcode() + MainetConstants.SEPARATOR
                    + entity.getFunctionMasterEntity().getFunctionDesc());
            dto.setPrimaryCode(entity.getPrimaryCodeEntity().getPrimaryAcHeadCompcode() + MainetConstants.SEPARATOR
                    + entity.getPrimaryCodeEntity().getPrimaryAcHeadDesc());
            dto.setSecondaryCode(entity.getSecondaryCodeEntity().getSacHeadDesc());
            dto.setRemark(entity.getRemarkDesc());
            dto.setStatus(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            dto.setEntityId(entity.getDampId());
            dtoList.add(dto);
        }

        return dtoList;
    }

}