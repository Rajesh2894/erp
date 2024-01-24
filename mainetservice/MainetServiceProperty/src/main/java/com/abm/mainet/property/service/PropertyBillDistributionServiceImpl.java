package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dto.NoticeGenSearchDto;

/**
 * @author cherupelli.srikanth
 * @since 07 May 2021
 */
@Service
public class PropertyBillDistributionServiceImpl implements PropertyBillDistributionService {

	@Autowired
	private PropertyBillGenerationService PropertyBillGenerationService;

	@Autowired
	private IAssessmentMastDao iAssessmentMastDao;
	
	@Override
	public List<NoticeGenSearchDto> getAllBillsForDistributionDateUpdation(NoticeGenSearchDto searchDto) {
		 List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();
		List<Object[]> propNoObjList = PropertyBillGenerationService.getPropNoListBySearchCriteria(searchDto,null,null);
		List<String> propNoList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(propNoObjList)) {
			propNoObjList.forEach(value -> {
				propNoList.add(value[0].toString());
			});
			
			Map<String, Object[]> distrbtionBillMap = getLastYearBillByPropNo(propNoList, searchDto.getOrgId(),searchDto.getAction());
			if(MapUtils.isNotEmpty(distrbtionBillMap)) {
				propNoObjList.forEach(propNo ->{
					Object[] billDetail = distrbtionBillMap.get(propNo[0].toString());
					if(billDetail != null) {
						NoticeGenSearchDto notGen = new NoticeGenSearchDto();
						notGen.setPropertyNo(propNo[0].toString());
						notGen.setOwnerName(propNo[2].toString());
						notGen.setBillNo(billDetail[1].toString());
						Date billDate = (Date) billDetail[2];
						notGen.setBillDate(Utility.dateToString(billDate));
						notGen.setBmIdNo(Long.valueOf(billDetail[3].toString()));
						if(propNo[4] != null) {
							notGen.setFlatNo(propNo[4].toString());
						}
						if(StringUtils.equals(searchDto.getAction(), MainetConstants.FlagE)) {
							notGen.setBillDistribDate((Date) billDetail[4]);
						}
						Date billYear = (Date) billDetail[5];
						try {
							notGen.setFinYear(Utility.getFinancialYearFromDate(billYear));
						} catch (Exception e) {
							e.printStackTrace();
						}
						notGenShowList.add(notGen);
					}
				});
			}else {
				if(StringUtils.equals(searchDto.getSpecNotSearchType(), MainetConstants.FlagS)) {
					NoticeGenSearchDto notGen = new NoticeGenSearchDto();
					notGen.setCheckStatus(MainetConstants.FlagY);
					notGen.setPropertyNo(propNoObjList.get(0).toString());
					notGenShowList.add(notGen);
				}
			}
		}
		return notGenShowList;
	}

	  private Map<String, Object[]> getLastYearBillByPropNo(List<String> propNoList, Long orgId, String action){
	    	Map<String, Object[]> lastBillMap = new HashMap<String, Object[]>();
	    	List<Object[]> lastYearBill = null;
	    	if(StringUtils.equals(action, MainetConstants.FlagA)) {
	    	    lastYearBill = iAssessmentMastDao.getAllBillsForBillDistribution(propNoList, orgId);
	    	}else
	    	{
	    		lastYearBill = iAssessmentMastDao.getAllBillsForBillDistributionForupdation(propNoList, orgId);
	    	}
	    	if(CollectionUtils.isNotEmpty(lastYearBill)) {
	    		lastYearBill.forEach(lastBill ->{
		    		lastBillMap.put(lastBill[0].toString(), lastBill);
		    	});
	    	}
	    	return lastBillMap;
	    }
}
