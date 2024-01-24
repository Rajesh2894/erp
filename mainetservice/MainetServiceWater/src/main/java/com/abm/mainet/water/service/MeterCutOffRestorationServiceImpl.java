package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.MeterCutOffRestorationRepository;
import com.abm.mainet.water.dao.WaterReconnectionRepository;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWaterCutRestoration;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;
import com.abm.mainet.water.dto.TbMeterMas;
import com.abm.mainet.water.repository.TbMeterMasJpaRepository;

/**
 * @author Arun.Chavda
 *
 */
@Service
public class MeterCutOffRestorationServiceImpl implements MeterCutOffRestorationService {

    @Resource
    private MeterCutOffRestorationRepository meterCutOffRestorationRepository;

    @Resource
    private WaterReconnectionRepository waterReconnectionRepository;

    @Resource
    private TbMeterMasJpaRepository meterMasJpaRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.MeterCutOffRestorationService#getMeterDetails(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public TbMeterMasEntity getMeterDetails(final Long consumerId, final Long orgId) {
        return meterCutOffRestorationRepository.getMeterDetails(consumerId, orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.MeterCutOffRestorationService#saveCutOffRestorationDetails(com.abm.mainet.water.dto.
     * MeterCutOffRestorationDTO)
     */
    @Override
    @Transactional
    public void saveCutOffRestorationDetails(final MeterCutOffRestorationDTO meterCutOffRes, final TbMeterMas meterMasDTO,
            final String newMeterFlag, final String cutOffResFlag, final String meterStatus) {

        final TbWaterCutRestoration meterCutResEntity = new TbWaterCutRestoration();
        final Date date = Utility.stringToDate(meterCutOffRes.getCutResDate());
        meterCutResEntity.setMmCutResDate(date);
        meterCutResEntity.setMmCutResRemark(meterCutOffRes.getCutResRemark());
        meterCutResEntity.setMmCutResRead(meterCutOffRes.getCutResRead());
        meterCutResEntity.setCsIdn(meterCutOffRes.getCsIdn());
        meterCutResEntity.setLangId(meterCutOffRes.getLangId());
        meterCutResEntity.setLgIpMac(meterCutOffRes.getLgIpMac());
        meterCutResEntity.setLmodDate(meterCutOffRes.getLmodDate());
        meterCutResEntity.setOrgId(meterCutOffRes.getOrgId());
        meterCutResEntity.setUserId(meterCutOffRes.getUserId());
        meterCutResEntity.setMmMtnid(meterCutOffRes.getMmMtnid());
        meterCutResEntity.setMmCutResFlag(cutOffResFlag);

        if (MainetConstants.MENU.Y.equals(newMeterFlag)) {
            final TbMeterMasEntity meterMasEntity = new TbMeterMasEntity();
            meterMasEntity.setMmStatus(meterStatus);
            meterMasEntity.setMaxMeterRead(meterMasDTO.getMaxMeterRead());
            meterMasEntity.setMmOwnership(meterMasDTO.getMmOwnership());
            meterMasEntity.setMmMtrno(meterMasDTO.getMmMtrno());
            meterMasEntity.setMmMtrcost(meterMasDTO.getMmMtrcost());
            meterMasEntity.setMmMtrmake(meterMasDTO.getMmMtrmake());
            meterMasEntity.setMmInstallDate(new Date());
            final TbKCsmrInfoMH tbCsmrInfo = new TbKCsmrInfoMH();
            tbCsmrInfo.setCsIdn(meterCutOffRes.getCsIdn());
            meterMasEntity.setTbCsmrInfo(tbCsmrInfo);
            meterMasEntity.setLangId(meterCutOffRes.getLangId());
            meterMasEntity.setLgIpMac(meterCutOffRes.getLgIpMac());
            meterMasEntity.setLmoddate(meterCutOffRes.getLmodDate());
            meterMasEntity.setOrgid(meterCutOffRes.getOrgId());
            meterMasEntity.setUserId(meterCutOffRes.getUserId());
            final TbMeterMasEntity masEntity = meterMasJpaRepository.save(meterMasEntity);
            if (null != masEntity) {
                meterCutResEntity.setMmMtnid(masEntity.getMmMtnid());
            }

        } else {

            if (MainetConstants.MENU.Y.equals(meterCutOffRes.getMeteredFlag())) {
                waterReconnectionRepository.updatedMeterStatusOfMeterMaster(meterCutOffRes.getCsIdn(), meterCutOffRes.getOrgId(),
                        meterStatus);
            }
        }

        waterReconnectionRepository.updatedBillingStatusOfCSMRInfo(meterCutOffRes.getCsIdn(), meterCutOffRes.getOrgId(),
                meterCutOffRes.getMeterBillingFlag());

        meterCutOffRestorationRepository.saveCutOffRestorationDetails(meterCutResEntity);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.MeterCutOffRestorationService#getPreviousCutOffDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetails(final Long meterId, final Long consumerId,
            final Long orgId, final String cutOffResFlag) {
        final List<MeterCutOffRestorationDTO> waterMeterCutResHits = meterCutOffRestorationRepository
                .getPreviousMeterCutOffDetails(meterId, consumerId, orgId, cutOffResFlag);
        return waterMeterCutResHits;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.MeterCutOffRestorationService#getNonMeterPreviousDetails(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterCutOffRestorationDTO> getNonMeterPreviousDetails(final Long consumerId, final Long orgId,
            final String cutOffResFlag) {
        return meterCutOffRestorationRepository.getNonMeterPreviousDetails(consumerId, orgId, cutOffResFlag);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, TbWaterCutRestoration> getCutOffReadingDataValues(final List<Long> csIdn, final long orgid) {
        final Map<Long, TbWaterCutRestoration> cutOff = new HashMap<>(0);
        final List<TbWaterCutRestoration> cutRestoration = meterCutOffRestorationRepository.getCutOffReadingDataValues(csIdn,
                orgid);
        if ((cutRestoration != null) && !cutRestoration.isEmpty()) {
            cutRestoration.forEach(cutRestorationCon -> {
                cutOff.put(cutRestorationCon.getCsIdn(), cutRestorationCon);
            });
        }
        return cutOff;
    }

	@Override
	public TbWaterCutRestoration getCutOffRestore(long csIdn, long orgid) {
		// TODO Auto-generated method stub
		TbWaterCutRestoration tbWaterCutRestoration = null;
		List<Long> csIdnList = new ArrayList<Long>();
		csIdnList.add(csIdn);
		final List<TbWaterCutRestoration> cutRestoration = meterCutOffRestorationRepository.getCutOffReadingDataValues(csIdnList,
                orgid);
		tbWaterCutRestoration = cutRestoration != null && cutRestoration.size() > 0 ? cutRestoration.get(0) : null;
		
		return tbWaterCutRestoration;
	}
	
	/*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.MeterCutOffRestorationService#getPreviousMeterCutOffDetailsOnCsIdn(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetailsOnCsIdn( final Long consumerId,
            final Long orgId, final String cutOffResFlag) {
        final List<MeterCutOffRestorationDTO> waterMeterCutResHits = meterCutOffRestorationRepository
                .getPreviousMeterCutOffDetailsOnCsIdn( consumerId, orgId, cutOffResFlag);
        return waterMeterCutResHits;
    }

}
