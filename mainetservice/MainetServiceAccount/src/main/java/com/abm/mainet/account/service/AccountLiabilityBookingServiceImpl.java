package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountLiabilityBookingDao;
import com.abm.mainet.account.domain.AccountLiabilityBookingDetEntity;
import com.abm.mainet.account.domain.AccountLiabilityBookingEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountLiabilityBookingBean;
import com.abm.mainet.account.dto.AccountLiabilityBookingDetBean;
import com.abm.mainet.account.repository.AccountLiabilityBookingJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UtilityService;

/** @author tejas.kotekar */

@Service
public class AccountLiabilityBookingServiceImpl implements AccountLiabilityBookingService {

    @Resource
    AccountLiabilityBookingJpaRepository liabilityBookingJpaRepository;

    @Resource
    AccountLiabilityBookingDao liabilityBookingDao;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    private final String TB_AC_LIABILITY_BOOKING = "TB_AC_LIABILITY_BOOKING";
    private final String LB_LIABILITY_NO = "LB_LIABILITY_NO";

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountLiabilityBookingService#getListOfTenderNo(java.lang.Long) This method gets the
     * list of all the tender entries done for particular Organization based on orgId passed.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountTenderEntryEntity> getListOfTenderDetails(final Long orgId) {
        return liabilityBookingJpaRepository.getListOfTenderDetails(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountLiabilityBookingService#getTenderDetailsByTenderId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountTenderEntryEntity> getTenderDetailsByTenderId(final Long tenderId, final Long orgId) {
        return liabilityBookingJpaRepository.getTenderDetailsByTenderId(tenderId, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountLiabilityBookingService#createLiabilityBookingEntry(com.abm.mainet.account.dto.
     * AccountLiabilityBookingBean)
     */
    @Override
    @Transactional
    public AccountLiabilityBookingBean createLiabilityBookingEntry(final AccountLiabilityBookingBean liabilityBookingBean) {

        final AccountLiabilityBookingEntity liabilityBookingEntity = new AccountLiabilityBookingEntity();
        setLiabilityEntry(liabilityBookingBean, liabilityBookingEntity);
        liabilityBookingJpaRepository.save(liabilityBookingEntity);
        return liabilityBookingBean;
    }

    private void setLiabilityEntry(final AccountLiabilityBookingBean liabilityBookingBean,
            final AccountLiabilityBookingEntity liabilityBookingEntity) {

        final AccountTenderEntryEntity tenderEntity = new AccountTenderEntryEntity();
        AccountLiabilityBookingDetEntity liabilityBookingDetEntity = null;

        if ((liabilityBookingBean.getLbLiabilityId() != null) && (liabilityBookingBean.getLbLiabilityId() > 0L)) {
            liabilityBookingEntity.setLbLiabilityId(liabilityBookingBean.getLbLiabilityId());
            liabilityBookingEntity.setLbLiabilityNo(liabilityBookingBean.getLbLiabilityNo());
        } else {
            final Long liabilityNo = generateLiabilityNumber(liabilityBookingBean.getOrgid());
            liabilityBookingEntity.setLbLiabilityNo(liabilityNo);
        }
        tenderEntity.setTrTenderId(liabilityBookingBean.getTrTenderId());
        liabilityBookingEntity.setTbAcTenderMaster(tenderEntity);
        liabilityBookingEntity
                .setLbEntryDate(UtilityService.convertStringDateToDateFormat(liabilityBookingBean.getLbEntryDate()));
        liabilityBookingEntity.setOrgid(liabilityBookingBean.getOrgid());
        liabilityBookingEntity.setCreatedBy(liabilityBookingBean.getCreatedBy());
        liabilityBookingEntity.setCreatedDate(liabilityBookingBean.getCreatedDate());
        liabilityBookingEntity.setLangId(liabilityBookingBean.getLangId());

        final List<AccountLiabilityBookingDetBean> liabilityDetList = liabilityBookingBean.getDetList();
        final List<AccountLiabilityBookingDetEntity> liabilityDetailList = new ArrayList<>(0);

        if ((liabilityDetList != null) && !liabilityDetList.isEmpty()) {
            for (final AccountLiabilityBookingDetBean detBean : liabilityDetList) {
                liabilityBookingDetEntity = new AccountLiabilityBookingDetEntity();

                if ((liabilityBookingBean.getLbLiabilityId() != null) && (liabilityBookingBean.getLbLiabilityId() > 0L)) {
                    liabilityBookingDetEntity.setLiabilityAmount(detBean.getLiabilityAmount());
                    liabilityBookingDetEntity.setFaYearid(detBean.getFaYearid());
                }
                if ((detBean.getLbLiabilityDetId() != null) && (detBean.getLbLiabilityDetId() > 0l)) {
                    liabilityBookingDetEntity.setLbLiabilityDetId(detBean.getLbLiabilityDetId());
                }
                liabilityBookingDetEntity.setOrgid(liabilityBookingBean.getOrgid());
                liabilityBookingDetEntity.setCreatedBy(liabilityBookingBean.getCreatedBy());
                liabilityBookingDetEntity.setCreatedDate(liabilityBookingBean.getCreatedDate());
                liabilityBookingDetEntity.setLangId(liabilityBookingBean.getLangId());
                liabilityBookingDetEntity.setTbAcLiabilityBooking(liabilityBookingEntity);
                liabilityDetailList.add(liabilityBookingDetEntity);
            }
        }
        liabilityBookingEntity.setListOfLiabilityBookingDet(liabilityDetailList);
    }

    private Long generateLiabilityNumber(final Long orgId) {
        final Long liabilityNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                TB_AC_LIABILITY_BOOKING,
                LB_LIABILITY_NO,
                orgId, MainetConstants.RECEIPT_MASTER.Reset_Type, null);
        return liabilityNumber;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isLiabilityExists(final Long tenderId, final Long orgId) {
        return liabilityBookingDao.isLiabilityExists(tenderId, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountLiabilityBookingService#getLiabilityNoByTenderId(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public AccountLiabilityBookingEntity getLiabilityNoByTenderId(final Long tenderId, final Long orgId) {
        return liabilityBookingJpaRepository.getLiabilityNoByTenderId(tenderId, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountLiabilityBookingService#getLiabilityDetailsByLiabilityId(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountLiabilityBookingDetEntity> getLiabilityDetailsByLiabilityId(final Long liabilityId, final Long orgId) {
        return liabilityBookingJpaRepository.getLiabilityDetailsByLiabilityId(liabilityId, orgId);
    }

}
