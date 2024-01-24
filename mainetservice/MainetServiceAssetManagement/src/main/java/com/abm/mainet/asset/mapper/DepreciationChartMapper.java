/**
 * 
 */
package com.abm.mainet.asset.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetDepreciationChart;
import com.abm.mainet.asset.domain.AssetDepreciationChartRev;
import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;

/**
 * @author satish.rathore
 *
 */
@Component
public class DepreciationChartMapper {

    public static AssetDepreciationChart mapToEntity(AssetDepreciationChartDTO depChartDto) {
        AssetDepreciationChart depChartEntity = new AssetDepreciationChart();
        BeanUtils.copyProperties(depChartDto, depChartEntity);
        AssetInformation astInfo = new AssetInformation();
        astInfo.setAssetId(depChartDto.getAssetId());
        if (depChartDto.getAssetDeprChartId() != null) {
            depChartEntity.setAssetDepretnId(depChartDto.getAssetDeprChartId());
        }
        depChartEntity.setSalvageValue(depChartDto.getSalvageValue());
        depChartEntity.setChartOfDepretn(depChartDto.getChartOfDepre());
        depChartEntity.setOriginalUsefulLife(depChartDto.getOriUseYear());
        depChartEntity.setRemark(depChartDto.getRemark());
        depChartEntity.setCreatedBy(depChartDto.getCreatedBy());
        depChartEntity.setCreationDate(depChartDto.getCreationDate());
        depChartEntity.setUpdatedBy(depChartDto.getUpdatedBy());
        depChartEntity.setUpdatedDate(depChartDto.getUpdatedDate());
        depChartEntity.setLgIpMac(depChartDto.getLgIpMac());
        depChartEntity.setLgIpMacUpd(depChartDto.getLgIpMacUpd());//
        depChartEntity.setAccumulDeprDate(depChartDto.getInitialAccumulDeprDate());
        depChartEntity.setAccumDepreAmount(depChartDto.getInitialAccumDepreAmount());
        depChartEntity.setAccumuDepAc(depChartDto.getAccumuDepAc());
        if (depChartDto.getDeprApplicable() != null && depChartDto.getDeprApplicable() == true) {
            depChartEntity.setDeprApplicable("Y");
        } else {
            depChartEntity.setDeprApplicable("N");
        }
        depChartEntity.setAssetId(astInfo);
        return depChartEntity;

    }

    public static AssetDepreciationChartRev mapToEntityRev(AssetDepreciationChartDTO depChartDto) {
        AssetDepreciationChartRev depChartEntity = new AssetDepreciationChartRev();
        AssetInformation astInfo = new AssetInformation();
        BeanUtils.copyProperties(depChartDto, depChartEntity);
        astInfo.setAssetId(depChartDto.getAssetId());
        if (depChartDto.getAssetDeprChartId() != null) {
            depChartEntity.setAssetDepretnId(depChartDto.getAssetDeprChartId());
        }
        depChartEntity.setSalvageValue(depChartDto.getSalvageValue());
        depChartEntity.setChartOfDepretn(depChartDto.getChartOfDepre());
        depChartEntity.setOriginalUsefulLife(depChartDto.getOriUseYear());
        depChartEntity.setRemark(depChartDto.getRemark());
        depChartEntity.setCreatedBy(depChartDto.getCreatedBy());
        depChartEntity.setCreationDate(depChartDto.getCreationDate());
        depChartEntity.setUpdatedBy(depChartDto.getUpdatedBy());
        depChartEntity.setUpdatedDate(depChartDto.getUpdatedDate());
        depChartEntity.setLgIpMac(depChartDto.getLgIpMac());
        depChartEntity.setLgIpMacUpd(depChartDto.getLgIpMacUpd());//
        depChartEntity.setAccumulDeprDate(depChartDto.getInitialAccumulDeprDate());
        depChartEntity.setAccumDepreAmount(depChartDto.getInitialAccumDepreAmount());
        depChartEntity.setAccumuDepAc(depChartDto.getAccumuDepAc());
        if (depChartDto.getDeprApplicable()) {
            depChartEntity.setDeprApplicable("Y");
        } else {
            depChartEntity.setDeprApplicable("N");
        }
        depChartEntity.setAssetId(astInfo);
        return depChartEntity;

    }

    public static AssetDepreciationChartDTO mapToDTO(AssetDepreciationChart depChartEntity) {
        AssetDepreciationChartDTO depChartDto = new AssetDepreciationChartDTO();
        BeanUtils.copyProperties(depChartEntity, depChartDto);
        AssetInformation astInfo = depChartEntity.getAssetId();
        depChartDto.setSalvageValue(depChartEntity.getSalvageValue());
        depChartDto.setChartOfDepre(depChartEntity.getChartOfDepretn());
        depChartDto.setOriUseYear(depChartEntity.getOriginalUsefulLife());
        depChartDto.setRemark(depChartEntity.getRemark());
        depChartDto.setCreatedBy(depChartEntity.getCreatedBy());
        depChartDto.setCreationDate(depChartEntity.getCreationDate());
        depChartDto.setUpdatedBy(depChartEntity.getUpdatedBy());
        depChartDto.setUpdatedDate(depChartEntity.getUpdatedDate());
        depChartDto.setLgIpMac(depChartEntity.getLgIpMac());
        depChartDto.setLgIpMacUpd(depChartEntity.getLgIpMacUpd());//
        depChartDto.setInitialAccumDepreAmount(depChartEntity.getAccumDepreAmount());
        depChartDto.setInitialAccumulDeprDate(depChartEntity.getAccumulDeprDate());
        depChartDto.setAccumuDepAc(depChartEntity.getAccumuDepAc());
        depChartDto.setAssetId(astInfo.getAssetId());
        depChartDto.setAssetDeprChartId(depChartEntity.getAssetDepretnId());
        if (depChartEntity.getDeprApplicable().equals("Y")) {
            depChartDto.setDeprApplicable(true);
        } else {
            depChartDto.setDeprApplicable(false);
        }
        return depChartDto;

    }

    public static AssetDepreciationChartDTO mapToDTORev(AssetDepreciationChartRev depChartEntity) {
        AssetDepreciationChartDTO depChartDto = new AssetDepreciationChartDTO();
        BeanUtils.copyProperties(depChartEntity, depChartDto);
        AssetInformation astInfo = depChartEntity.getAssetId();
        depChartDto.setSalvageValue(depChartEntity.getSalvageValue());
        depChartDto.setChartOfDepre(depChartEntity.getChartOfDepretn());
        depChartDto.setOriUseYear(depChartEntity.getOriginalUsefulLife());
        depChartDto.setRemark(depChartEntity.getRemark());
        depChartDto.setCreatedBy(depChartEntity.getCreatedBy());
        depChartDto.setCreationDate(depChartEntity.getCreationDate());
        depChartDto.setUpdatedBy(depChartEntity.getUpdatedBy());
        depChartDto.setUpdatedDate(depChartEntity.getUpdatedDate());
        depChartDto.setLgIpMac(depChartEntity.getLgIpMac());
        depChartDto.setLgIpMacUpd(depChartEntity.getLgIpMacUpd());//
        depChartDto.setInitialAccumDepreAmount(depChartEntity.getAccumDepreAmount());
        depChartDto.setInitialAccumulDeprDate(depChartEntity.getAccumulDeprDate());
        depChartDto.setAccumuDepAc(depChartEntity.getAccumuDepAc());
        depChartDto.setAssetId(astInfo.getAssetId());
        depChartDto.setAssetDeprChartId(depChartEntity.getAssetDepretnId());
        if (depChartEntity.getDeprApplicable().equals("Y")) {
            depChartDto.setDeprApplicable(true);
        } else {
            depChartDto.setDeprApplicable(false);
        }
        return depChartDto;
    }

    public static AssetDepreciationChartDTO resetDepreciationChart(AssetDepreciationChartDTO chartDTO) {
        chartDTO.setSalvageValue(null);
        chartDTO.setChartOfDepre(null);
        chartDTO.setOriUseYear(null);
        chartDTO.setRemark(null);
        chartDTO.setCreatedBy(null);
        chartDTO.setCreationDate(null);
        chartDTO.setUpdatedBy(null);
        chartDTO.setUpdatedDate(null);
        chartDTO.setLgIpMac(null);
        chartDTO.setLgIpMacUpd(null);
        chartDTO.setInitialAccumDepreAmount(null);
        chartDTO.setInitialAccumulDeprDate(null);
        chartDTO.setAccumuDepAc(null);
        chartDTO.setDeprApplicable(false);
        return chartDTO;
    }
}
