package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafMasEntity;
import com.abm.mainet.account.dto.TbAcChequebookleafMas;
import com.abm.mainet.account.mapper.TbAcChequebookleafMasServiceMapper;
import com.abm.mainet.account.repository.TbAcChequebookleafDetJpaRepository;
import com.abm.mainet.account.repository.TbAcChequebookleafMasJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * Implementation of TbAcChequebookleafMasService
 */
@Service
public class TbAcChequebookleafMasServiceImpl implements TbAcChequebookleafMasService {

    @Resource
    private TbAcChequebookleafMasJpaRepository tbAcChequebookleafMasJpaRepository;

    @Resource
    private TbAcChequebookleafDetJpaRepository acChequebookleafDetJpaRepository;

    @Resource
    private TbAcChequebookleafMasServiceMapper tbAcChequebookleafMasServiceMapper;

    @Override
    @Transactional(readOnly = true)
    public TbAcChequebookleafMas findById(final Long chequebookId) {
        final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity = tbAcChequebookleafMasJpaRepository.findOne(chequebookId);
        return tbAcChequebookleafMasServiceMapper
                .mapTbAcChequebookleafMasEntityToTbAcChequebookleafMas(tbAcChequebookleafMasEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbAcChequebookleafMas> findAll() {

        final Iterable<TbAcChequebookleafMasEntity> entities = tbAcChequebookleafMasJpaRepository.findAll();
        final List<TbAcChequebookleafMas> beans = new ArrayList<>();
        for (final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity : entities) {
            beans.add(tbAcChequebookleafMasServiceMapper
                    .mapTbAcChequebookleafMasEntityToTbAcChequebookleafMas(tbAcChequebookleafMasEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public TbAcChequebookleafMas create(final TbAcChequebookleafMas tbAcChequebookleafMas) {
        final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity = new TbAcChequebookleafMasEntity();

        if ((tbAcChequebookleafMas.getChkbookRtnDatetemp() != null) && !tbAcChequebookleafMas.getChkbookRtnDatetemp().isEmpty()) {
            final Date chkbookRtnDate = Utility.stringToDate(tbAcChequebookleafMas.getChkbookRtnDatetemp());
            tbAcChequebookleafMas.setChkbookRtnDate(chkbookRtnDate);
        }
        if ((tbAcChequebookleafMas.getRcptChqbookDatetemp() == null)
                || tbAcChequebookleafMas.getRcptChqbookDatetemp().isEmpty()) {
            tbAcChequebookleafMas.setRcptChqbookDate(new Date());
        } else {
            final Date rcptChqbookDate = Utility.stringToDate(tbAcChequebookleafMas.getRcptChqbookDatetemp());
            tbAcChequebookleafMas.setRcptChqbookDate(rcptChqbookDate);
        }

        tbAcChequebookleafMasServiceMapper.mapTbAcChequebookleafMasToTbAcChequebookleafMasEntity(tbAcChequebookleafMas,
                tbAcChequebookleafMasEntity);

        final List<TbAcChequebookleafDetEntity> detailsList = new ArrayList<>(0);

        TbAcChequebookleafDetEntity details = null;
        for (int i = Integer.valueOf(tbAcChequebookleafMasEntity.getFromChequeNo()); i <= Integer
                .valueOf(tbAcChequebookleafMasEntity.getToChequeNo()); i++) {
            details = new TbAcChequebookleafDetEntity();
            details.setOrgid(tbAcChequebookleafMasEntity.getOrgid());
            details.setLmoddate(tbAcChequebookleafMasEntity.getLmoddate());
            details.setUserId(tbAcChequebookleafMasEntity.getUserId());
            details.setLgIpMac(tbAcChequebookleafMasEntity.getLgIpMac());
            details.setTbAcChequebookleafMas(tbAcChequebookleafMasEntity);
            int length = tbAcChequebookleafMasEntity.getFromChequeNo().length();
            String finalValue = addLeadingZeroes(length, i);
            details.setChequeNo(finalValue);
            details.setCpdIdstatus(CommonMasterUtility
                    .getValueFromPrefixLookUp(AccountConstants.NOT_ISSUED.getValue(), AccountPrefix.CLR.toString())
                    .getLookUpId());
            detailsList.add(details);
        }
        tbAcChequebookleafMasEntity.setListOfTbAcChequebookleafDet(detailsList);
        final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntitySaved = tbAcChequebookleafMasJpaRepository
                .save(tbAcChequebookleafMasEntity);

        return tbAcChequebookleafMasServiceMapper
                .mapTbAcChequebookleafMasEntityToTbAcChequebookleafMas(tbAcChequebookleafMasEntitySaved);
    }

    public static String addLeadingZeroes(int size, int value) {
        return String.format("%0" + size + "d", value);
    }

    @Override
    @Transactional
    public TbAcChequebookleafMas update(final TbAcChequebookleafMas tbAcChequebookleafMas) {
        final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntity = tbAcChequebookleafMasJpaRepository
                .findOne(tbAcChequebookleafMas.getChequebookId());
        tbAcChequebookleafMasServiceMapper.mapTbAcChequebookleafMasToTbAcChequebookleafMasEntity(tbAcChequebookleafMas,
                tbAcChequebookleafMasEntity);
        final TbAcChequebookleafMasEntity tbAcChequebookleafMasEntitySaved = tbAcChequebookleafMasJpaRepository
                .save(tbAcChequebookleafMasEntity);
        return tbAcChequebookleafMasServiceMapper
                .mapTbAcChequebookleafMasEntityToTbAcChequebookleafMas(tbAcChequebookleafMasEntitySaved);
    }

    @Override
    @Transactional
    public void delete(final Long chequebookId) {
        tbAcChequebookleafMasJpaRepository.delete(chequebookId);
    }

    public TbAcChequebookleafMasJpaRepository getTbAcChequebookleafMasJpaRepository() {
        return tbAcChequebookleafMasJpaRepository;
    }

    public void setTbAcChequebookleafMasJpaRepository(
            final TbAcChequebookleafMasJpaRepository tbAcChequebookleafMasJpaRepository) {
        this.tbAcChequebookleafMasJpaRepository = tbAcChequebookleafMasJpaRepository;
    }

    public TbAcChequebookleafMasServiceMapper getTbAcChequebookleafMasServiceMapper() {
        return tbAcChequebookleafMasServiceMapper;
    }

    public void setTbAcChequebookleafMasServiceMapper(
            final TbAcChequebookleafMasServiceMapper tbAcChequebookleafMasServiceMapper) {
        this.tbAcChequebookleafMasServiceMapper = tbAcChequebookleafMasServiceMapper;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.TbAcChequebookleafMasService#getChequeData(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbAcChequebookleafMas> getChequeData(final Long bmBankid,
            final Long orgid) {

        final List<TbAcChequebookleafMas> beans = new ArrayList<>(0);
        TbAcChequebookleafMas account = null;
        final List<TbAcChequebookleafMasEntity> entity = tbAcChequebookleafMasJpaRepository.getChequeData(bmBankid, orgid);
        for (final TbAcChequebookleafMasEntity data : entity) {
            account = new TbAcChequebookleafMas();
            account.setChequebookId(data.getChequebookId());
            account.setFromChequeNo(data.getFromChequeNo());
            account.setToChequeNo(data.getToChequeNo());
            account.setCheckbookLeave(data.getCheckbookLeave());
            beans.add(account);
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.TbAcChequebookleafMasService#getChequeBookCount(java.lang.Long, java.lang.Long,
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Long getChequeBookCount(final Long bmBankid, final Long orgid, final String fromChequeNo, final String toChequeNo) {
        final Long count = tbAcChequebookleafMasJpaRepository.getChequeBookCount(bmBankid, orgid, fromChequeNo, toChequeNo);
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.TbAcChequebookleafMasService#getIssuedChequeNumbers(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbAcChequebookleafDetEntity> getIssuedChequeNumbers(final Long bankAcId, final Long statusId) {

        return tbAcChequebookleafMasJpaRepository.getChequeNumbers(bankAcId, statusId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getChequeRangeByBankAccountId(final Long bankAccountId, final Long orgId) {

        final List<Object[]> chequeRangeList = tbAcChequebookleafMasJpaRepository.getChequeRangeByBankAccountId(bankAccountId,
                orgId);
        final Map<Long, String> chequeRangeMap = new HashMap<>();
        if ((chequeRangeList != null) && !chequeRangeList.isEmpty()) {
            for (final Object[] chequeRange : chequeRangeList) {
                chequeRangeMap.put((Long) chequeRange[0], chequeRange[1] + MainetConstants.HYPHEN + chequeRange[2]);
            }
        }
        return chequeRangeMap;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.TbAcChequebookleafMasService#getChequeUtilizationDetails(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getChequeUtilizationDetails(final Long chequeBookId, final Long orgId) {

        final List<Object[]> chequeDetailsList = tbAcChequebookleafMasJpaRepository.getChequeUtilizationDetails(chequeBookId,
                orgId);
        return chequeDetailsList;
    }

    @Override
    @Transactional
    public List<TbAcChequebookleafDetEntity> getChequeNumbers(final Long bankAcId) {
        return tbAcChequebookleafMasJpaRepository.getChequeNumbers(bankAcId);
    }

}
