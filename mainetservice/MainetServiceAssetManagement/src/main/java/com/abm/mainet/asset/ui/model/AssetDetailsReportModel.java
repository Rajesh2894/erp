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

import com.abm.mainet.asset.service.IAssetDetailsReportService;
import com.abm.mainet.asset.ui.dto.AssetDetailsReportDto;
import com.abm.mainet.asset.ui.dto.AssetInformationReportDto;
import com.abm.mainet.asset.ui.dto.ReportDetailsListDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author satish.rathore
 *
 */

/*
 * Defect #5054 Help document is not displayed after clicking on help icon.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetDetailsReportModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6665048773853711901L;

    /**
     * 
     */
    @Autowired
    private IAssetDetailsReportService assetDetailsReportService;

    private AssetDetailsReportDto astDetRepDto = new AssetDetailsReportDto();
    private AssetInformationReportDto infoReport = new AssetInformationReportDto();
    private List<ReportDetailsListDTO> reportList = new ArrayList<>();
    final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

    public AssetDetailsReportDto getAstDetRepDto() {
        return astDetRepDto;
    }

    public void setAstDetRepDto(AssetDetailsReportDto astDetRepDto) {
        this.astDetRepDto = astDetRepDto;
    }

    /**
     * @return the infoReport
     */
    public AssetInformationReportDto getInfoReport() {
        return infoReport;
    }

    /**
     * @param infoReport the infoReport to set
     */
    public void setInfoReport(AssetInformationReportDto infoReport) {
        this.infoReport = infoReport;
    }

    /**
     * @return the reportList
     */
    public List<ReportDetailsListDTO> getReportList() {
        return reportList;
    }

    /**
     * @param reportList the reportList to set
     */
    public void setReportList(List<ReportDetailsListDTO> reportList) {
        this.reportList = reportList;
    }

    public AssetDetailsReportDto detailReport(final Long assetGroup, final Long assetType, final Long assetClass1,
            final Long assetClass2, final Integer langId) {

        return assetDetailsReportService.findDetailReport(assetGroup, assetType, assetClass1, assetClass2, orgId, langId);

    }

    public List<ReportDetailsListDTO> registerOfMovableReport(Long assetClass1, Long faYearId, Long prefixId) {
        return assetDetailsReportService.registerOfMovableReport(assetClass1, orgId, faYearId, prefixId);
    }

}
