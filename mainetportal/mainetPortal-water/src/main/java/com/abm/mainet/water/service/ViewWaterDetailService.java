package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;

/**
 * @author Bhagyashri.dongardive
 *
 */
public interface ViewWaterDetailService {

	public List<WaterDataEntrySearchDTO> searchPropertyDetails(WaterDataEntrySearchDTO searchDto);

	ViewCsmrConnectionDTO getWaterViewByConnectionNumber(TbCsmrInfoDTO infoDto);

	List<LookUp> getCollectionDetails(TbCsmrInfoDTO infoDto);

	ChallanReceiptPrintDTO getRevenueReceiptDetails(TbCsmrInfoDTO infoDto);
}
