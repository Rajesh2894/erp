package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.water.dao.WaterExceptionalGapDAO;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.WaterExceptionGapEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterExceptionGapDTO;

/**
 * @author Rahul.Yadav
 *
 */
@Service
public class WaterExceptionalGapServiceImpl implements WaterExceptionalGapService {

    @Autowired
    private WaterExceptionalGapDAO waterExceptionalGapDAO;

    @Resource
    private WaterServiceMapper waterServiceMapper;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.service.WaterExceptionalGapService#
     * getWaterDataForExceptionGap(com.abm.mainet.water.dto.TbCsmrInfoDTO, long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<WaterExceptionGapDTO> getWaterDataForExceptionGap(final TbCsmrInfoDTO waterDTO, final long billingFrequency,
            final String finYearId, final String meterDesc) {

        final List<TbKCsmrInfoMH> waterEntity = waterExceptionalGapDAO.getWaterDataForExceptionGap(waterDTO,
                billingFrequency, Long.valueOf(finYearId), meterDesc);
        final List<WaterExceptionGapDTO> beans = new ArrayList<>(0);
        WaterExceptionGapDTO dto = null;
        if ((waterEntity != null) && !waterEntity.isEmpty()) {
            for (final TbKCsmrInfoMH entity : waterEntity) {
                dto = new WaterExceptionGapDTO();
                dto.setCcnNumber(entity.getCsCcn());
                dto.setCsName(entity.getCsName() + MainetConstants.WHITE_SPACE
                        + (entity.getCsMname() != null ? entity.getCsMname() : MainetConstants.BLANK)
                        + MainetConstants.WHITE_SPACE
                        + entity.getCsLname());
                dto.setCsIdn(entity.getCsIdn());
                beans.add(dto);
            }
        }
        return beans;
    }

    @Override
    @Transactional
    public void saveAndUpdateExceptionalData(final List<WaterExceptionGapDTO> gapDto, final long orgId, final Long empId,
            final String macAddress, final String reason, final String addEditFlag) {
        if (MainetConstants.Common_Constant.ACTIVE_FLAG.equals(addEditFlag)) {
            for (final WaterExceptionGapDTO dto : gapDto) {
                if (MainetConstants.Common_Constant.YES.equals(dto.getMgapActive())) {
                    saveOrUpdateExceptionalEntry(orgId, empId, macAddress, reason, dto);
                }
            }
        } else {
            for (final WaterExceptionGapDTO dto : gapDto) {
                saveOrUpdateExceptionalEntry(orgId, empId, macAddress, reason, dto);
            }
        }
    }

    private void saveOrUpdateExceptionalEntry(final long orgId, final Long empId, final String macAddress, final String reason,
            final WaterExceptionGapDTO dto) {
        final WaterExceptionGapEntity entity = new WaterExceptionGapEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setOrgId(orgId);
        entity.setMgapRemark(reason);
        waterExceptionalGapDAO.saveAndUpdateExceptionalData(entity, macAddress, empId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WaterExceptionGapDTO> editExceptionGapEntry(final WaterExceptionGapDTO editGap, final long meterType) {
        final List<Object[]> result = waterExceptionalGapDAO.fetchEditExceptionGapEntry(editGap, meterType);
        final List<WaterExceptionGapDTO> beans = new ArrayList<>(0);
        WaterExceptionGapDTO dto = null;
        WaterExceptionGapEntity entity = null;
        if ((result != null) && !result.isEmpty()) {
            for (final Object[] data : result) {
                dto = new WaterExceptionGapDTO();
                entity = (WaterExceptionGapEntity) data[4];
                BeanUtils.copyProperties(entity, dto);
                dto.setCcnNumber(data[0].toString());
                dto.setCsName(data[1].toString() + MainetConstants.WHITE_SPACE
                        + (data[2] != null ? data[2].toString() : MainetConstants.BLANK) + MainetConstants.WHITE_SPACE
                        + data[3].toString());
                beans.add(dto);
            }
        }
        return beans;
    }

    @Override
    @Transactional
    public Map<Long, Long> fetchForExceptionGap(final List<Long> csIdn, final long orgid,
            final String status) {
        final List<WaterExceptionGapEntity> exceptionEntity = waterExceptionalGapDAO.fetchForExceptionGap(csIdn, orgid, status);
        final Map<Long, Long> exceptionalGap = new HashMap<>(0);
        Long days = null;
        if (exceptionEntity != null && !exceptionEntity.isEmpty()) {
            for (final WaterExceptionGapEntity gap : exceptionEntity) {
                days = exceptionalGap.get(gap.getCsIdn());
                if (days == null) {
                    days = 0l;
                }
                final long difference = (gap.getMgapFrom().getTime() - gap.getMgapTo().getTime()) / 86400000;
                days += Math.abs(difference + 1);
                exceptionalGap.put(gap.getCsIdn(), days);
                gap.setMgapActive(MainetConstants.NO);
            }
        }
        return exceptionalGap;
    }

}
