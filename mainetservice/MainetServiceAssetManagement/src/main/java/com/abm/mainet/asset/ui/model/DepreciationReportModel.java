/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IDepreciationReportService;
import com.abm.mainet.asset.ui.dto.DepreciationReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class DepreciationReportModel extends AbstractFormModel {

	@Autowired
	private IDepreciationReportService reportService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2312554128589638748L;

	private List<DepreciationReportDTO> reportDTOList = new ArrayList<>();
	private DepreciationReportDTO depReportDTO = new DepreciationReportDTO();

	/**
	 * @return the reportDTOList
	 */
	public List<DepreciationReportDTO> getReportDTOList() {
		return reportDTOList;
	}

	/**
	 * @param reportDTOList
	 *            the reportDTOList to set
	 */
	public void setReportDTOList(List<DepreciationReportDTO> reportDTOList) {
		this.reportDTOList = reportDTOList;
	}

	/**
	 * @return the depReportDTO
	 */
	public DepreciationReportDTO getDepReportDTO() {
		return depReportDTO;
	}

	/**
	 * @param depReportDTO
	 *            the depReportDTO to set
	 */
	public void setDepReportDTO(DepreciationReportDTO depReportDTO) {
		this.depReportDTO = depReportDTO;
	}

	@Override
	public boolean saveForm() {
		boolean status = false;

		return status;
	}

	/**
	 * find Asset Valuation Details details by Asset Id
	 * 
	 * @param orgId
	 * @param assetId
	 * @return list of DepreciationReportDTO with All details records if found else
	 *         return null.
	 */
	public List<DepreciationReportDTO> getDetailsByAssetId(final Long assetId, final Long orgId) {
		return reportService.findByAssetId(assetId, orgId);
	}
}
