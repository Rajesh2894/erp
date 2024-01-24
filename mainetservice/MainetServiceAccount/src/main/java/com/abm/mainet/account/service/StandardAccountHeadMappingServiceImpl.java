package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.StandardAccountHeadDto;
import com.abm.mainet.account.repository.StandardAccountHeadMappingRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants.StandardAccountHeadMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dao.AccountHeadPrimaryAccountCodeMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.repository.AcPrimaryCodeMasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * Implementation of BankAccountService
 */
@Component
@Transactional
public class StandardAccountHeadMappingServiceImpl implements StandardAccountHeadMappingService {

    @Resource
    private AcPrimaryCodeMasterJpaRepository acPrimaryCodeMasterJpaRepository;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterDao accountHeadPrimaryAccountCodeMasterDao;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.StandardAccountHeadMappingService#create(com.abm.mainetservice.account.bean.
     * StandardAccountHeadDto)
     */
    @Resource
    private StandardAccountHeadMappingRepository standardHeadMappingRepository;

    @Override
    @Transactional
    public StandardAccountHeadDto createMapping(final StandardAccountHeadDto accountHeadDto) {

        for (final StandardAccountHeadDto accountHead : accountHeadDto.getPrimaryHeadMappingList()) {

            standardHeadMappingRepository.updateStandardMappingData(accountHead.getPrimaryHeadId(),
                    accountHeadDto.getAccountType(),
                    accountHead.getStatus(), accountHeadDto.getAccountSubType());

        }

        return accountHeadDto;
    }

    @Override
    @Transactional
    public StandardAccountHeadDto updateMapping(final StandardAccountHeadDto accountHeadDto) {

        standardHeadMappingRepository.updateData(accountHeadDto.getStatus(), accountHeadDto.getPrimaryHeadId());

        return accountHeadDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.StandardAccountHeadMappingService#getAccountHead(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<StandardAccountHeadDto> getAccountHead(final Long accountType, final Long accountSubType,
            final Organisation organisation) {

        final Long defaultOrgId = organisation.getOrgid();
        final List<AccountHeadPrimaryAccountCodeMasterEntity> entityList = standardHeadMappingRepository
                .findAllByAccountTypeId(accountType, accountSubType, defaultOrgId);
        final List<StandardAccountHeadDto> list = new ArrayList<>();
        StandardAccountHeadDto dto = null;
        if (entityList != null) {
            for (final AccountHeadPrimaryAccountCodeMasterEntity entity : entityList) {

                dto = new StandardAccountHeadDto();
                dto.setAccountType(entity.getCpdIdAccountType());
                if ((entity.getCpdIdAccountType() != null) && (entity.getCpdIdPayMode() != null)) {
                    dto.setAccountTypeDesc(getAccountTypeDesc(entity.getCpdIdAccountType(), organisation));
                    dto.setAccountSubTypeDesc(
                            getAccountSubTypeDesc(entity.getCpdIdAccountType(), entity.getCpdIdPayMode(), organisation));
                }
                dto.setPrimaryHeadId(entity.getPrimaryAcHeadId());
                dto.setCodeDescription(
                        entity.getPrimaryAcHeadCompcode() + MainetConstants.SEPARATOR + entity.getPrimaryAcHeadDesc());
                if (entity.getCpdIdBanktype() != null) {
                    dto.setStatus(entity.getCpdIdBanktype());
                    dto.setStatusDescription(getAccountStatsusDesc(entity.getCpdIdBanktype(), organisation));
                }

                dto.setPayMode(entity.getCpdIdPayMode());
                list.add(dto);
            }
        }
        return list;
    }

    private String getAccountStatsusDesc(final Long statusId, final Organisation organisation) {
        String desc = MainetConstants.CommonConstants.BLANK;
        final List<LookUp> status = CommonMasterUtility.getListLookup(AccountPrefix.ACN.name(), organisation);
        for (final LookUp lk : status) {
            if (lk.getLookUpId() == statusId) {
                desc = lk.getDescLangFirst();
                break;
            }
        }
        return desc;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findAccountSubType(final Long primaryHeadId, final Long accountType, final Long accountSubType,
            final Long lookUpStatusId,
            final Long orgId) {

        final AccountHeadPrimaryAccountCodeMasterEntity entity = standardHeadMappingRepository.findEntity(primaryHeadId,
                accountType,
                accountSubType, lookUpStatusId, orgId);
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findStatusPaymode(final Long accountType, final Long payModeId, final Long lookUpStatusId, final Long orgId) {

        final AccountHeadPrimaryAccountCodeMasterEntity entity = standardHeadMappingRepository.findStatusPaymode(accountType,
                payModeId,
                lookUpStatusId, orgId);
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }

    private String getAccountTypeDesc(final Long cpdIdAccountType, final Organisation organisation) {
        String desc = MainetConstants.CommonConstants.BLANK;
        final List<LookUp> accountType = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(), organisation);
        for (final LookUp lk : accountType) {
            if (lk.getLookUpId() == cpdIdAccountType) {
                desc = lk.getDescLangFirst();
                break;
            }
        }
        return desc;
    }

    private String getAccountSubTypeDesc(final Long cpdIdAccountType, final Long cpdIdPayMode, final Organisation organisation) {
        String desc = MainetConstants.CommonConstants.BLANK;
        final Long orgId = organisation.getOrgid();
        final Long accountType = cpdIdAccountType;
        final List<LookUp> acountType = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(), organisation);
        for (final LookUp lookUp : acountType) {
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, orgId,
                                cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.AccountJournalVoucherEntry.BK)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(PrefixConstants.BAT, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals("BAI")) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(PrefixConstants.ITP, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.TbAcVendormaster.VN)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(PrefixConstants.VNT, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.TbAcVendormaster.IN)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.IVT, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.AccountBillEntry.DP)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.DTY, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.AD)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.ATY, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.AST, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.SD)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(PrefixConstants.TDS, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
            if (lookUp.getLookUpId() == accountType) {
                if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.LO)) {
                    if (cpdIdPayMode != null) {
                        desc = (CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.LNT, orgId, cpdIdPayMode));
                        break;
                    }
                }
            }
        }
        return desc;
    }

    @Override
    @Transactional
    public StandardAccountHeadDto getDetailsUsingprimaryHeadId(final Long primaryHeadId, final Organisation organisation,
            final int langId) {

        final StandardAccountHeadDto dto = new StandardAccountHeadDto();
        final AccountHeadPrimaryAccountCodeMasterEntity entity = standardHeadMappingRepository.findOne(primaryHeadId);
        final Long orgId = organisation.getOrgid();
        if (entity.getCpdIdAccountType() != null) {
            dto.setAccountType(entity.getCpdIdAccountType());
            final Long accountType = entity.getCpdIdAccountType();
            final List<LookUp> acountType = CommonMasterUtility.getListLookup(AccountPrefix.SAM.name(), organisation);
            for (final LookUp lookUp : acountType) {
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setPayMode(entity.getCpdIdPayMode());
                            dto.setViewPayModeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                                    orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.AccountJournalVoucherEntry.BK)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setBankType(entity.getCpdIdPayMode());
                            dto.setViewBankTypeDesc(
                                    CommonMasterUtility.findLookUpDesc(PrefixConstants.BAT, orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals("BAI")) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setBankAccountIntType(entity.getCpdIdPayMode());
                            dto.setViewBankIntTypeDesc(
                                    CommonMasterUtility.findLookUpDesc(PrefixConstants.ITP, orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.TbAcVendormaster.VN)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setVendorType(entity.getCpdIdPayMode());
                            dto.setViewVendorTypeDesc(
                                    CommonMasterUtility.findLookUpDesc(PrefixConstants.VNT, orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.TbAcVendormaster.IN)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setInvestmentType(entity.getCpdIdPayMode());
                            dto.setViewInvestmentTypeDesc(CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.IVT,
                                    orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.AccountBillEntry.DP)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setDepositType(entity.getCpdIdPayMode());
                            dto.setViewDepositTypeDesc(CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.DTY, orgId,
                                    entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.AD)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setAdvanceType(entity.getCpdIdPayMode());
                            dto.setViewAdvanceTypeDesc(CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.ATY, orgId,
                                    entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setAsset(entity.getCpdIdPayMode());
                            dto.setViewAssetDesc(CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.AST, orgId,
                                    entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.SD)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setStatutoryDeduction(entity.getCpdIdPayMode());
                            dto.setViewStatutoryDeductionDesc(
                                    CommonMasterUtility.findLookUpDesc(PrefixConstants.TDS, orgId, entity.getCpdIdPayMode()));
                        }
                    }
                }
                if (lookUp.getLookUpId() == accountType) {
                    if (lookUp.getLookUpCode().equals(StandardAccountHeadMapping.LO)) {
                        if (entity.getCpdIdPayMode() != null) {
                            dto.setLoans(entity.getCpdIdPayMode());
                            dto.setViewLoansDesc(CommonMasterUtility.findLookUpDesc(StandardAccountHeadMapping.LNT, orgId,
                                    entity.getCpdIdPayMode()));
                        }
                    }
                }
            }
        }
        if ((entity.getCpdIdPayMode() != null) && (entity.getCpdIdAccountType() != null)) {
            dto.setAccountSubType(entity.getCpdIdPayMode());

            final String accouTypeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.TbAcVendormaster.SAM,
                    entity.getOrgid(),
                    entity.getCpdIdAccountType());
            dto.setCpdAccountSubTypeCode(accouTypeCode);
        }
        if (entity.getCpdIdBanktype() != null) {
            dto.setStatus(entity.getCpdIdBanktype());
        }
        dto.setPrimaryHeadId(entity.getPrimaryAcHeadId());

        return dto;
    }
}
