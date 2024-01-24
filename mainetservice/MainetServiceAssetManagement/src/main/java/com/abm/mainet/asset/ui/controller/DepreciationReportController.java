/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.ui.dto.DepreciationReportDTO;
import com.abm.mainet.asset.ui.model.DepreciationReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;

/**
 * @author sarojkumar.yadav
 *
 */
@Controller
@RequestMapping(MainetConstants.AssetManagement.DEPRECIATION_REPORT)
public class DepreciationReportController extends AbstractFormController<DepreciationReportModel> {

    @Autowired
    private IAssetInformationService infoService;
    @Autowired
    private IOrganisationService iOrganisationService;

    /**
     * @param model
     * @param httpServletRequest
     * @return this method returns the index page whose binding with the url of the request mapping
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("AssetSearch.htm");
        return index();
    }

    /**
     * find Asset Valuation Details details by Asset Id
     * 
     * @param orgId
     * @param assetId
     * @return list of DepreciationReportDTO with All details records if found else return null.
     */
    @RequestMapping(method = RequestMethod.POST, params = "getDetailsByAssetId")
    public ModelAndView getDetailsByAssetId(final HttpServletRequest request,
            @RequestParam(value = "assetId", required = false) final Long assetId, final Model model) {
        bindModel(request);
        final Integer langId = (int) ApplicationSession.getInstance().getLangId();
        final DepreciationReportModel reportModel = this.getModel();
        final AssetInformationDTO infoDTO = infoService.getInfo(assetId);
        final AssetPurchaseInformationDTO purchaseDTO = infoService.getPurchaseInfo(assetId);
        final Long orgId = infoDTO.getOrgId();
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        final List<DepreciationReportDTO> dtoList = reportModel.getDetailsByAssetId(assetId, orgId);
        if (dtoList != null && !dtoList.isEmpty()) {
            reportModel.setReportDTOList(dtoList);
        }
        reportModel.getDepReportDTO().setAssetName(infoDTO.getAssetName());
        reportModel.getDepReportDTO().setSerialNo(infoDTO.getSerialNo());
        reportModel.getDepReportDTO().setAssetClass1(infoDTO.getAssetClass1());
        reportModel.getDepReportDTO().setAssetClass2(infoDTO.getAssetClass2());
        reportModel.getDepReportDTO().setDetails(infoDTO.getDetails());
        if (langId != null && langId == 1) {
            reportModel.getDepReportDTO().setAssetClass1Desc(CommonMasterUtility
                    .getHierarchicalLookUp(infoDTO.getAssetClass1(), organisation).getDescLangFirst());
            reportModel.getDepReportDTO().setAssetClass2Desc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(infoDTO.getAssetClass2(), organisation).getDescLangFirst());
        } else {
            reportModel.getDepReportDTO().setAssetClass1Desc(CommonMasterUtility
                    .getHierarchicalLookUp(infoDTO.getAssetClass1(), organisation).getDescLangSecond());
            reportModel.getDepReportDTO().setAssetClass2Desc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(infoDTO.getAssetClass2(), organisation).getDescLangSecond());
        }

        if (purchaseDTO != null) {
            reportModel.getDepReportDTO().setDateOfAcquisition(purchaseDTO.getDateOfAcquisition());
            reportModel.getDepReportDTO().setCostOfAcquisition(purchaseDTO.getCostOfAcquisition());
            reportModel.getDepReportDTO().setPurchaseBookValue(purchaseDTO.getInitialBookValue());
        }
        this.getModel().setCommonHelpDocs("AssetSearch.html");
        return new ModelAndView("DepreciationReport", MainetConstants.FORM_NAME, reportModel);
    }
}
