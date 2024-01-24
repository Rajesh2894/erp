/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.asset.ui.dto.DepreciationReportDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IDepreciationReportService {

	/**
	 * find Asset Valuation Details details by Asset Id
	 * 
	 * @param orgId
	 * @param assetId
	 * @return list of DepreciationReportDTO with All details records if found else
	 *         return null.
	 */
	public List<DepreciationReportDTO> findByAssetId(final Long assetId, final Long orgId);

}
