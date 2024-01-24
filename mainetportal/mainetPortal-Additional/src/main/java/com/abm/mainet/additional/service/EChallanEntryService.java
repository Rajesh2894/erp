/**
 * 
 */
package com.abm.mainet.additional.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additional.dto.EChallanMasterDto;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;

/**
 * @author cherupelli.srikanth
 *
 */
public interface EChallanEntryService {



	public List<EChallanMasterDto> searchRaidDetailsList(String raidNo, String offenderName, Date challanFromDate,
			Date challanToDate, String offenderMobNo, Long orgid);
	
	 EChallanMasterDto getEChallanMasterByOrgidAndChallanId(Long orgid, Long challanId);
	
	 ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long challanId, Long orgId);
	 
	 boolean saveEChallanEntry(EChallanMasterDto challanMasterDto);
	 
	 List<DocumentDetailsVO> getDocumentUploadedByRefNoAndDeptId(String raidNo, Long orgId);

	 
}
