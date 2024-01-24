/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;

/**
 * @author cherupelli.srikanth
 * @since 29 July 2020
 */
@WebService
public interface IViewWaterDetailsService {

	ViewCsmrConnectionDTO getWaterViewByConnectionNumber(String connectionNum, Long orgId,String oldConnectionNum);
	
	List<LookUp> getCollectionDetails(String connectionNo, Organisation org,Long deptId);
	
	ViewCsmrConnectionDTO getWaterViewDet(TbCsmrInfoDTO infoDto);
	
	List<LookUp> getCollectionViewDetails(TbCsmrInfoDTO infoDto);
	
	ChallanReceiptPrintDTO getRevenueReceiptDetails(TbCsmrInfoDTO requestDto);

	ViewCsmrConnectionDTO getWaterViewByConnectionNumberForSkdcl(String connectionNum, Long orgId,
			String OldConnectionNum);
}
