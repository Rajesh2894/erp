/**
 * 
 */
package com.abm.mainet.additionalservices.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.dto.EChallanItemDetailsDto;
import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author divya.marshettiwar
 *
 */
public interface EChallanEntryService {

	void saveEChallanEntry(EChallanMasterDto challanMasterDto,List<EChallanItemDetailsDto> challanItemDetailsDtoList, List<Long> removeItemIdsList);
	
	public String generateChallanNumber(Organisation org,List<EChallanItemDetailsDto> challanItemDetailsDtoList);
	
	public EChallanMasterDto getEChallanMasterByOrgidAndChallanId(Long orgid, Long challanId);
	
	public String generateRaidNumber(Organisation org,List<EChallanItemDetailsDto> challanItemDetailsDtoList);
	
	public List<EChallanMasterDto> searchChallanDetailsList(String challanNo,String raidNo,
			String offenderName, Date challanFromDate, Date challanToDate,
			String offenderMobNo, String challanType, Long orgid);
	
	public List<EChallanMasterDto> searchRaidDetailsList(String raidNo, String offenderName, Date challanFromDate,
			Date challanToDate, String offenderMobNo, String challanType, Long orgid);
	
	ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long challanId, Long orgId);

	boolean findFromReceiptMaster(Long orgid, Long raidNo);

	void updatePaymentStatus(Long challanId, String status, Long orgId);


}
