/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.water.dto.WaterPenaltyDto;

/**
 * @author cherupelli.srikanth
 *
 */
public interface IWaterPenaltyService {

    void saveWaterPenalty(WaterPenaltyDto waterPenaltyDto);

    WaterPenaltyDto getWaterPenaltyByCCNOByFinId(String ccnNo, Long finYearId, Long orgId);

    List<WaterPenaltyDto> getWaterPenaltyByconIds(List<Long> conIds, Long orgId);

    void updateWaterPenalty(WaterPenaltyDto waterPenaltyDto);
    
    WaterPenaltyDto getWaterPenaltyByBmIdNoAndOrgId(Long bmIdNo, Long orgId);
    
    WaterPenaltyDto getWaterPenaltyHistoryByBmIdNoAndOrgId(Long bmIdNo, Long orgId);
    
    List<WaterPenaltyDto> getWaterPenaltyHistoryByCsIdnoAndOrgId(String csIdNo, Long orgId);
    
    void inactivePenalty(Long bmIdno,Long orgId);
    
    List<WaterPenaltyDto> getWaterPenaltyByCsIdnoAndOrgIdAndBmIdno(String csIdNo, Long orgId,Long bmIdno);
    
    public List<WaterPenaltyDto> getWaterPenaltyCurrBillAmountByCsIdnoAndOrgId(String csIdNo, Long orgId);

	List<WaterPenaltyDto> getWaterPenaltyByBmNoIds(List<TbBillMas> billMasList, long orgid);
}
