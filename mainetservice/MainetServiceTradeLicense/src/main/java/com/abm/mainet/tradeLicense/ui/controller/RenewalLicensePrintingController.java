package com.abm.mainet.tradeLicense.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicensePrintingModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.RENEWAL_LICENSE_PRINTING)
public class RenewalLicensePrintingController extends AbstractFormController<RenewalLicensePrintingModel> {

    @Autowired
    private ITradeLicenseApplicationService tradeLicenseApplicationService;

    @Autowired
    private IRenewalLicenseApplicationService renewalLicenseApplicationService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @RequestMapping(method = RequestMethod.POST, params = "showDetails")
    public ModelAndView viewRenewalLicenseHistory(final HttpServletRequest httpServletRequest,
            @RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId) throws Exception {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final RenewalLicensePrintingModel model = getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        RenewalMasterDetailDTO renewalMasterDetailDTO = renewalLicenseApplicationService
                .getRenewalLicenseDetailsByApplicationId(applicationId);
        model.getTradeDetailDTO().setRenewalMasterDetailDTO(renewalMasterDetailDTO);
        TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
                .getTradeDetailsByTrdId(renewalMasterDetailDTO.getTrdId(), orgId);
        model.setTradeDetailDTO(tradeMasterDetailDTO);
        /*
         * Date licenseIssuanceDate = tradeMasterDetailDTO.getTrdLicisdate(); if (licenseIssuanceDate != null) {
         */
        model.setIssuanceDateDesc(Utility.dateToString(new Date()));

        // model.getTradeDetailDTO().getRenewalMasterDetailDTO().setRenewalFromDateDesc(Utility.dateToString(renewalMasterDetailDTO.getTreLicfromDate()));
        // model.getTradeDetailDTO().getRenewalMasterDetailDTO().setRenewalTodDateDesc(Utility.dateToString(renewalMasterDetailDTO.getTreLictoDate()));
        model.setFromDateDesc(Utility.dateToString(renewalMasterDetailDTO.getTreLicfromDate()));
        model.setToDateDesc(Utility.dateToString(renewalMasterDetailDTO.getTreLictoDate()));
        model.setViewMode(MainetConstants.FlagV);
        Long finYearId = iFinancialYear.getFinanceYearId(renewalMasterDetailDTO.getTreLicfromDate());
        FinancialYear finYear = iFinancialYear.getFinincialYearsById(finYearId, orgId);
        String financialYear = Utility.getFinancialYearFromDate(finYear.getFaFromDate());
        model.setFinYear(financialYear);

        Long referenceNo = tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroId();
        model.setDocumentList(checklistVerificationService.getDocumentUploadedByRefNo(referenceNo.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid()));

        List<CFCAttachment> imgList = checklistVerificationService.getDocumentUploadedByRefNo(
                model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);
        model.setDocumentList(imgList);
        if (!imgList.isEmpty() && imgList != null) {
            model.setImagePath(getPropImages(imgList.get(0)));
        }
        return new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_REPORT_FORMAT, MainetConstants.FORM_NAME,
                model);

    }

    private String getPropImages(final CFCAttachment attachDocs) {

        new ArrayList<String>();
        final UUID uuid = UUID.randomUUID();
        final String randomUUIDString = uuid.toString();
        final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR + UserSession.getCurrent().getOrganisation().getOrgid()
                + MainetConstants.FILE_PATH_SEPARATOR + randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR
                + "PROPERTY";
        final String path1 = attachDocs.getAttPath();
        final String name = attachDocs.getAttFname();
        final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
                FileNetApplicationClient.getInstance());
        return data;
    }
}
