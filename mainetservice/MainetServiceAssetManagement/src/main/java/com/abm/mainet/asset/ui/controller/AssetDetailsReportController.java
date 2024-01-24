/**
 * 
 */
package com.abm.mainet.asset.ui.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.asset.service.IAssetDetailsReportService;
import com.abm.mainet.asset.ui.dto.AssetDetailsReportDto;
import com.abm.mainet.asset.ui.dto.AssetInformationReportDto;
import com.abm.mainet.asset.ui.dto.ReportDetailsListDTO;
import com.abm.mainet.asset.ui.model.AssetDetailsReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author satish.rathore
 *
 */
@Controller
@RequestMapping(MainetConstants.AssetManagement.ASSET_DETAILS_REPORT)
public class AssetDetailsReportController extends AbstractFormController<AssetDetailsReportModel> {

    private static final String REPORT_TYPE_LOOKUPS = "reportTypeLookUps";

    @Autowired
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private IAssetDetailsReportService reportService;

    /**
     * @param model
     * @param httpServletRequest
     * @return this method returns the index page whose binding with the url of the request mapping
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        List<LookUp> reportList = CommonMasterUtility.getLookUps(MainetConstants.AssetManagement.ASR,
                UserSession.getCurrent().getOrganisation());
        reportList = reportList.stream()
                .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.FlagD))
                .collect(Collectors.toList());
        this.getModel().setCommonHelpDocs("AssetDetailsReport.html");
        model.addAttribute(REPORT_TYPE_LOOKUPS, reportList);
        return index();
    }

    /**
     * @param assetGroup
     * @param assetType
     * @param assetClass1
     * @param assetClass2
     * @param request
     * @param model
     * @return this method returns the view of report if it is find otherwise blank report
     */
    @RequestMapping(method = RequestMethod.POST, params = "detailReport")
    public ModelAndView findReport(@RequestParam(value = "assetGroup", required = false) final Long assetGroup,
            @RequestParam(value = "assetType", required = false) final Long assetType,
            @RequestParam(value = "assetClass1", required = false) final Long assetClass1,
            @RequestParam(value = "assetClass2", required = false) final Long assetClass2, final HttpServletRequest request,
            final ModelMap model) {
        final Integer langId = (int) ApplicationSession.getInstance().getLangId();
        AssetDetailsReportDto astDetRepDto = this.getModel().detailReport(assetGroup, assetType, assetClass1,
                assetClass2, langId);
        if (astDetRepDto != null)
            model.addAttribute("reportDto", astDetRepDto);
        else {
            model.addAttribute("validationError", AccountConstants.Y.getValue());
        }
        return new ModelAndView("showDetailReport", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = "registerOfImmovable")
    public ModelAndView registerOfImmovableReport(@RequestParam("assetClass2") final Long assetClass2,
            @RequestParam("faYearId") final Long faYearId, @RequestParam("assetCodeselected") final String assetCodeselected,
            final HttpServletRequest request, final ModelMap model) {

        bindModel(request);
        final AssetDetailsReportModel reportModel = this.getModel();
        Long orgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final Integer langId = (int) ApplicationSession.getInstance().getLangId();
        // final Long prefixId = reportService.getPrefixIdByPrefixCode(orgId, "CLS", "IMO");
        orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TbFinancialyear financiaYear = tbFinancialyearService.findYearById(faYearId, orgId);

        final Long assetId = reportService.getImmovableAssetIdByAssetCode(orgId, assetClass2, assetCodeselected);

        if (assetId != null && assetId.intValue() != 0) {
            AssetInformationReportDto reportDTO = reportService.getPrimaryDetails(assetId, orgId, langId);
            if (reportDTO != null) {
                reportModel.setInfoReport(reportDTO);
            }

            /*
             * this will find using from and to date and only that financial year which is in between final final
             * List<ReportDetailsListDTO> reportList = reportService.registerImmovableReport(assetId, orgId, langId,
             * financiaYear.getFaFromDate(), financiaYear.getFaToDate());
             */
            final List<ReportDetailsListDTO> reportList = reportService.registerImmovableReport(assetId, orgId, langId,
                    null, financiaYear.getFaToDate());

            if (reportList != null && !reportList.isEmpty()) {
                reportModel.setReportList(reportList);
            }
        } else {
            model.addAttribute("validationError", AccountConstants.Y.getValue());
        }

        return new ModelAndView("RegisterOfImmovableProperty", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = "onReportType")
    public ModelAndView homePageOnReportType(@RequestParam("reportTypeCode") final String reportTypeCode,
            final ModelMap model) {
        Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        Map<Long, String> financialMap = tbFinancialyearService.getAllFinincialYear();
        List<LookUp> reportList = CommonMasterUtility.getLookUps(MainetConstants.AssetManagement.ASR,
                UserSession.getCurrent().getOrganisation());
        reportList = reportList.stream()
                .filter(look -> look.getOtherField() != null && look.getLookUpCode().equals(reportTypeCode))
                .collect(Collectors.toList());
        List<LookUp> prifixList1 = CommonMasterUtility.getListLookup("ACL", UserSession.getCurrent().getOrganisation());
        List<LookUp> imoPrefixList = prifixList1.stream()
                .filter(look -> look.getOtherField() != null && look.getOtherField().equalsIgnoreCase("IMO"))
                .collect(Collectors.toList());
        List<LookUp> prifixList = prifixList1.stream()
                .filter(look -> look.getOtherField() != null && look.getOtherField().equalsIgnoreCase("MOV"))
                .collect(Collectors.toList());
        if (reportTypeCode.equals("ROL")) {
            imoPrefixList = imoPrefixList.stream().filter(list -> list.getDescLangFirst().equalsIgnoreCase("Land"))
                    .collect(Collectors.toList());
        }
        String showPage = reportList.get(0).getDescLangFirst();
        model.addAttribute("reportType", reportTypeCode);
        model.addAttribute("financialMap", financialMap);
        model.addAttribute("prifixList", prifixList);
        model.addAttribute("imoPrefixList", imoPrefixList);
        return new ModelAndView(showPage, MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = "registerOfMovable")
    public ModelAndView registerOfMovableReport(@RequestParam("assetClass1") final Long assetClass1,
            @RequestParam("faYearId") final Long faYearId, final HttpServletRequest request, final ModelMap model) {
        Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        List<LookUp> prifixListASC = CommonMasterUtility.getListLookup("ASC", UserSession.getCurrent().getOrganisation());
        List<ReportDetailsListDTO> reportDetailsListDTO = this.getModel().registerOfMovableReport(assetClass1,
                faYearId, prifixListASC.get(0).getLookUpId());
        if (reportDetailsListDTO != null && !reportDetailsListDTO.isEmpty())
            model.addAttribute("reportDetailsListDTO", reportDetailsListDTO);
        else {
            model.addAttribute("validationError", AccountConstants.Y.getValue());
        }
        return new ModelAndView("RegisterOfMovableProperty", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = "landRegisterReport")
    public ModelAndView landRegistrationReport(@RequestParam("assetClass2") final Long assetClass2,
            @RequestParam("faYearId") final Long faYearId, @RequestParam("assetCodeselected") final String assetCodeselected,
            final HttpServletRequest request, final ModelMap model) {
        bindModel(request);
        final AssetDetailsReportModel reportModel = this.getModel();
        Long orgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final Integer langId = ApplicationSession.getInstance().getLangId();
        // final Long prefixId = reportService.getPrefixIdByPrefixCode(orgId, "CLS", "L");
        orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TbFinancialyear financiaYear = tbFinancialyearService.findYearById(faYearId, orgId);

        final Long assetId = reportService.getLandAssetIdByAssetCode(orgId, assetClass2, assetCodeselected);

        if (assetId != null && assetId.intValue() != 0) {
            AssetInformationReportDto reportDTO = reportService.getPrimaryDetails(assetId, orgId, langId);
            if (reportDTO != null) {
                reportModel.setInfoReport(reportDTO);
            }

            /*
             * this will find using from and to date and only that financial year which is in between final
             * List<ReportDetailsListDTO> reportList = reportService.registerLandReport(assetId, orgId, langId,
             * financiaYear.getFaFromDate(), financiaYear.getFaToDate());
             */

            final List<ReportDetailsListDTO> reportList = reportService.registerLandReport(assetId, orgId, langId,
                    null, financiaYear.getFaToDate());

            if (reportList != null && !reportList.isEmpty()) {
                reportModel.setReportList(reportList);
            }
        } else {
            model.addAttribute("validationError", AccountConstants.Y.getValue());
        }
        return new ModelAndView("landRegistrationReport", MainetConstants.FORM_NAME, reportModel);
    }

    @RequestMapping(method = RequestMethod.POST, params = "populateList")
    public @ResponseBody List<AssetInformationDTO> populateList(@RequestParam("assetClass2") final Long assetClass2,
            final HttpServletRequest request, final ModelMap model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<AssetInformationDTO> astlist = reportService.getAssetCodeByCategory(assetClass2, orgId);
        return astlist;

    }
}