package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AdvanceEntryDao;
import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.mapper.AdvanceEntryServiceMapper;
import com.abm.mainet.account.repository.AdvanceEntryRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

@Component
public class AdvanceEntryServiceImpl implements AdvanceEntryService {

    private final String TB_AC_ADVANCE = "TB_AC_ADVANCE";
    private final String PAY_ADVANCE_NO = "PAY_ADVANCE_NO";

    @Resource
    private AdvanceEntryServiceMapper advanceEntryServiceMapper;
    @Resource
    private AdvanceEntryRepository advanceEntryRepository;
    @Resource
    private AdvanceEntryDao advanceEntryDao;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Override
    @Transactional
    public List<AdvanceEntryDTO> findByAllGridSearchData(final Long advanceNumber, final Date advDate, final String name,
            final BigDecimal advAmount,
            final Long advanceType, final String cpdIdStatus, final Long orgId,Long deptId) {

        final List<AdvanceEntryEntity> entities = advanceEntryDao.findByGridAllData(advanceNumber, advDate, name, advAmount,
                advanceType, cpdIdStatus, orgId,deptId);
        final List<AdvanceEntryDTO> bean = new ArrayList<>();
        for (final AdvanceEntryEntity advanceEntryEntity : entities) {
            bean.add(advanceEntryServiceMapper.mapAdvanceEntryEntityToAdvanceEntryDTO(advanceEntryEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public AdvanceEntryDTO saveAdvanceEntryFormData(final AdvanceEntryDTO advanceEntryDTO, final Organisation orgid,
            final int langId)
            throws ParseException {

        final Long orgId = orgid.getOrgid();
        final AdvanceEntryEntity advanceEntryEntity = new AdvanceEntryEntity();
        if (advanceEntryDTO.getAdvanceDate() == null) {
            advanceEntryDTO.setPrAdvEntryDate(new Date());
        } else {
            final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            advanceEntryDTO.setPrAdvEntryDate(sdf.parse(advanceEntryDTO.getAdvanceDate()));
        }

        if (advanceEntryDTO.getAdvanceNumber() == null) {
            final Long advNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(), TB_AC_ADVANCE,
                    PAY_ADVANCE_NO,
                    orgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, null);
            advanceEntryDTO.setPrAdvEntryNo(advNumber);
        } else {
            advanceEntryDTO.setPrAdvEntryNo(advanceEntryDTO.getAdvanceNumber());
        }

        advanceEntryDTO.setPayAdvParticulars(advanceEntryDTO.getPartOfAdvance());

        if ((advanceEntryDTO.getPaymentOrderDateDup() != null) && !advanceEntryDTO.getPaymentOrderDateDup().isEmpty()) {
            final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            advanceEntryDTO.setPaymentOrderDate(sdf.parse(advanceEntryDTO.getPaymentOrderDateDup()));
        }
        if ((advanceEntryDTO.getPaymentDateDup() != null) && !advanceEntryDTO.getPaymentDateDup().isEmpty()) {
            final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            advanceEntryDTO.setPaymentDate(sdf.parse(advanceEntryDTO.getPaymentDateDup()));
        }
        if ((advanceEntryDTO.getPaymentOrderNo() != null) && !advanceEntryDTO.getPaymentOrderNo().isEmpty()) {
            advanceEntryDTO.setPayAdvSettlementNumber(Long.valueOf(advanceEntryDTO.getPaymentOrderNo()));
        }
        advanceEntryDTO.setSeas_Deas(advanceEntryDTO.getPaymentAmount());
        advanceEntryDTO.setAdv_Flg(AccountConstants.M.toString());

        advanceEntryServiceMapper.mapAdvanceEntryDTOToAdvanceEntryEntity(advanceEntryDTO, advanceEntryEntity);

        advanceEntryRepository.save(advanceEntryEntity);

        return advanceEntryDTO;
    }

    @Override
    @Transactional
    public AdvanceEntryDTO getDetailsByUsingPrAdvEntryId(final AdvanceEntryDTO advanceEntryDTO, final Organisation ordid,
            final int langId) {

        final Long prAdvEntryId = advanceEntryDTO.getPrAdvEntryId();
        final AdvanceEntryEntity advanceEntryEntity = advanceEntryRepository.findOne(prAdvEntryId);

        advanceEntryDTO.setPrAdvEntryId(advanceEntryEntity.getPrAdvEntryId());
        advanceEntryDTO.setAdvanceNumber(advanceEntryEntity.getPrAdvEntryNo());
        advanceEntryDTO.setAdvanceDate(Utility.dateToString(advanceEntryEntity.getPrAdvEntryDate()));

        if(advanceEntryEntity.getDeptId() != null && advanceEntryEntity.getDeptId() > 0) {
        	advanceEntryDTO.setDeptId(advanceEntryEntity.getDeptId());
        }
        if (advanceEntryEntity.getAdvanceAmount() != null) {
            BigDecimal amount = advanceEntryEntity.getAdvanceAmount();
            amount = amount.setScale(2, RoundingMode.CEILING);
            advanceEntryDTO.setAdvanceAmount(amount.toString());
        }
        if (advanceEntryEntity.getBalanceAmount() != null) {
            BigDecimal amount1 = advanceEntryEntity.getBalanceAmount();
            amount1 = amount1.setScale(2, RoundingMode.CEILING);
            advanceEntryDTO.setBalanceAmount(amount1.toString());
        }
        advanceEntryDTO.setPartOfAdvance(advanceEntryEntity.getPayAdvParticulars());

        advanceEntryDTO.setPaymentNumber(advanceEntryEntity.getPaymentNumber().toString());
        advanceEntryDTO.setPaymentDate(advanceEntryEntity.getPaymentDate());
        advanceEntryDTO.setPaymentDateDup(UtilityService.convertDateToDDMMYYYY(advanceEntryEntity.getPaymentDate()));

        if (advanceEntryEntity.getPayAdvSettlementNumber() != null)
            advanceEntryDTO.setPaymentOrderNo(advanceEntryEntity.getPayAdvSettlementNumber().toString());
        advanceEntryDTO.setPaymentOrderDate(advanceEntryEntity.getPaymentOrderDate());
        advanceEntryDTO.setPaymentOrderDateDup(Utility.dateToString(advanceEntryEntity.getPaymentOrderDate()));

        advanceEntryDTO.setPacHeadId(advanceEntryEntity.getPacHeadId());
        advanceEntryDTO.setVendorId(advanceEntryEntity.getVendorId());

        if (advanceEntryEntity.getAdvanceTypeId() != null) {
            advanceEntryDTO.setAdvanceTypeId(advanceEntryEntity.getAdvanceTypeId());
            advanceEntryDTO.setAdvanceTypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.StandardAccountHeadMapping.ATY,
                    advanceEntryEntity.getOrgid(), advanceEntryEntity.getAdvanceTypeId()));
        }

        return advanceEntryDTO;
    }

    @Override
    @Transactional
    public Long getAdvAccountTypeId(final Long billTypeId, final Long accountHeadId, final Long superOrgId, final Long orgId) {
        final Long accountHeadTypeId = advanceEntryRepository.getAdvAccountTypeId( accountHeadId, superOrgId, orgId);

        return accountHeadTypeId;
    }

    @Override
    @Transactional
    public Map<Long, String> getBudgetHeadAllData(final Long acountSubType, final Organisation organisation, final int langId) {

        final Long orgId = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
        final Long statusId = statusLookup.getLookUpId();
        final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.StandardAccountHeadMapping.AD,
                PrefixConstants.TbAcVendormaster.SAM, langId, organisation);
        final Long advanceTypeId = advanceLookup.getLookUpId();
        final Map<Long, String> budgtHeadId = new LinkedHashMap<>();
        final List<Object[]> prBudgetCode = advanceEntryRepository.getAdvBudgetHeadIds(statusId, advanceTypeId, acountSubType,
                orgId);
        for (final Object[] objects : prBudgetCode) {
            budgtHeadId.put((Long) objects[0], (String) objects[1]);
        }
        return budgtHeadId;
    }

    @Override
    @Transactional
    public Long getAdvAccountTypeIdByOrgIdAndId(Long id, Long orgId) {
        final Long accountHeadTypeId = advanceEntryRepository.getAdvAccountTypeIdByOrgIdAndId(id, orgId);

        return accountHeadTypeId;
    }

}
