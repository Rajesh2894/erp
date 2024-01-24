/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.ui.dto.PensionEligibilityCriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.PensionSchemeMasterDto;
import com.abm.mainet.socialsecurity.ui.dto.ViewDtoList;

/**
 * @author satish.rathore
 *
 */
public interface IPensionSchemeMasterService {

    public boolean savePensionDetails(Long orgId, Long empId, String macAddress, PensionSchemeMasterDto pensionSchemeMasterDto);

    public List<ViewDtoList> getAllData(Long orgId, Long depId, Long status);

    public PensionSchemeMasterDto getOneDetails(Long id, Long orgId, String modeType);

    public List<LookUp> filterCriteria(List<LookUp> lookup, List<PensionEligibilityCriteriaDto> criteriaDto);

    public void updatePensionDetails(Long orgId, Long empId, String macAddress, PensionSchemeMasterDto pensionSchemeMasterDto,
            Set<Long> deletedBatchIdSet, Set<Long> updateBatchSet);

    void entityToHistoryDetails(SocialSecuritySchemeMaster entity);
    
     public int findServiceId(Long serviceId, Long orgId); 
     
    /* public List<PensionEligibilityCriteriaDto> findfactorApplicableId (Long factorApplicableId,Long orgId); */
     
    
    public int findfactorApplicable(Long factorApplicableId,Long criteriaId,String rangeFrom,
            String rangeTo,BigDecimal amt,Long orgId);
    
    public void inactiveScheme(Long schmeMsId,Long orgId);

    public int findfactorApplicablewithoutamt(Long factorApplicableId, Long criteriaId, String rangeFrom, String rangeTo, Long orgId);

	public List<Object[]> findAllActiveServicesNotAddedInSchemeMaster(Long orgId, Long depId, Long activeStatusId,
			String string);

	String getPrefixOtherValue(Long codId, Long orgId);

    
     
  
    
    

}
