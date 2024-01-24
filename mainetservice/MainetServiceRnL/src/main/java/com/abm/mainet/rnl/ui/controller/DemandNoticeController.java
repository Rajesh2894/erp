package com.abm.mainet.rnl.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.DemandNoticeDTO;
import com.abm.mainet.rnl.dto.ReportDTO;
import com.abm.mainet.rnl.service.IDemandNoticeService;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.ui.model.DemandNoticeModel;

@Controller
@RequestMapping(value = { "/NoticeBillGenerate.html", "DemandNotice.html" })
public class DemandNoticeController extends AbstractFormController<DemandNoticeModel> {

    @Autowired
    IContractAgreementService contractAgreementService;

    @Autowired
    IDemandNoticeService demandNoticeService;

    @RequestMapping()
    public String index(final HttpServletRequest request, final ModelMap model) {
        getModel().bind(request);
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String INDEX_PAGE = null;
        if (request.getRequestURI().contains(MainetConstants.RnLCommon.DEMAND_NOTICE)) {
            ReportDTO reportDTO = null;
            this.getModel().setReportDTO(reportDTO);
            this.getModel().setReportDTOList(null);
            final Long statusId = CommonMasterUtility.getIdFromPrefixLookUpDesc(MainetConstants.Common_Constant.ACTIVE,
                    MainetConstants.ContractAgreement.VSS, 1, UserSession.getCurrent().getOrganisation());
            long venTypeId = CommonMasterUtility
                    .getLookUpFromPrefixLookUpValue(MainetConstants.RnLCommon.CONTRACTOR_CPD_VALUE, PrefixConstants.VNT, 1,
                            UserSession.getCurrent().getOrganisation())
                    .getLookUpId();
            List<Object[]> list = contractAgreementService.getVenderList(orgId, venTypeId, statusId);
            List<LookUp> venderlookupList = new ArrayList<>();
            LookUp look = null;
            for (final Object[] oneVender : list) {
                look = new LookUp();
                look.setLookUpId(Long.parseLong(oneVender[0].toString()));
                look.setDescLangFirst(oneVender[1].toString());
                venderlookupList.add(look);
            }
            this.getModel().setVenderlookupList(venderlookupList);
            // make contractNo list by DEPT wise
            TbDepartment tbDepartment = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                    .findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A,
                            MainetConstants.RnLCommon.RentLease);
            // flag-> E DEPT wise filter
            final List<ContractMappingDTO> contractMapDet = ApplicationContextProvider.getApplicationContext()
                    .getBean(IEstateContractMappingService.class).findContractDeptWise(orgId, tbDepartment,
                            MainetConstants.CommonConstants.E);
            List<LookUp> contractLookupList = new ArrayList<>();
            for (ContractMappingDTO contract : contractMapDet) {
                LookUp contractLookup = new LookUp();
                contractLookup.setLookUpId(contract.getContId());
                contractLookup.setLookUpDesc(contract.getContractNo());
                contractLookup.setDescLangFirst(contract.getContractNo());
                contractLookupList.add(contractLookup);
            }
            this.getModel().setContractLookupList(contractLookupList);
            model.addAttribute("headerName", "Notice Printing");
            model.addAttribute("resetPage", "NoticePrinting.html");
            INDEX_PAGE = MainetConstants.RnLCommon.DEMAND_NOTICE_SUMMARY;
        } else if (request.getRequestURI().contains("Generate")) {
            model.addAttribute("testPage", "Notice Generation");
            model.addAttribute("resetPage", "NoticeBillGenerate.html");
            INDEX_PAGE = "noticeBillGeneration";
        }
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, this.getModel());
        return INDEX_PAGE;
    }

    @RequestMapping(params = "searchNoticePrint", method = { RequestMethod.POST })
    public ModelAndView searchNoticePrint(@RequestParam("vendorId") Long vendorId, @RequestParam("contractId") Long contractId,
            HttpServletRequest request) {
        this.getModel().bind(request);
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        DemandNoticeModel model = this.getModel();
        ReportDTO reportDTO = model.getReportDTO();
        reportDTO.setVendorId(vendorId);
        reportDTO.setContractId(contractId);
        model.setReportDTO(reportDTO);
        // fetch result for print bill notice

        List<ReportDTO> results = demandNoticeService.fetchDemandNoticeReportByCondition(new Date(), vendorId, contractId, orgId,
                false);
        this.getModel().setReportDTOList(results);
        return new ModelAndView(MainetConstants.RnLCommon.DEMAND_NOTICE_VALIDN, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(params = "printNotice", method = { RequestMethod.POST })
    public ModelAndView printRenewalremainderNotice(@RequestParam("contractId") Long contractId,
            @RequestParam("lang") String lang, HttpServletRequest request) {
        this.getModel().bind(request);
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // fetch due bill data till date
        // here true means fetch due details
        List<ReportDTO> reportDTOList = demandNoticeService.fetchDemandNoticeReportByCondition(new Date(), null, contractId,
                orgId, true);
        // get taxes for contract and CGST and SGST
        String financialYear = Utility.getCurrentFinancialYear();
        // doing this to get data for arrears
        String temp[] = financialYear.split(MainetConstants.HYPHEN);
        Date previousFinancialEndDate = Utility.stringToDate("31/03/" + temp[0]);
        List<DemandNoticeDTO> demandNoticeDTOList = demandNoticeService.fetchDemandNoticeBillData(contractId,
                previousFinancialEndDate, orgId);
        BigDecimal finalTotalAmt = new BigDecimal(0.00);
        // make final AMT
        for (DemandNoticeDTO demand : demandNoticeDTOList) {
            finalTotalAmt = finalTotalAmt.add(demand.getTotalAmt());
        }
        String finalTotalAmtInWord = Utility.convertBiggerNumberToWord(finalTotalAmt);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setFinalTotalAmt(finalTotalAmt);
        reportDTO.setFinalTotalAmtInWord(finalTotalAmtInWord);
        reportDTO.setTenantName(reportDTOList.get(0).getTenantName());
        reportDTO.setContractNo(reportDTOList.get(0).getContractNo());
        reportDTO.setContractDate(reportDTOList.get(0).getContractDate());
        reportDTO.setPropertyName(demandNoticeDTOList.get(0).getPropertyNameAndAddress());
        reportDTO.setPreviousFinancialEndDate(Utility.dateToString(previousFinancialEndDate, MainetConstants.DATE_FORMAT_UPLOAD));
        reportDTO.setReceiptDate(Utility.dateToString(new Date(), MainetConstants.DATE_FORMAT_UPLOAD));
        reportDTO.setFinancialYear(ApplicationSession.getInstance().getMessage("rnl.demand.report.financialYear",
                new Object[] { Utility.getCurrentFinancialYear() }));
        this.getModel().setReportDTO(reportDTO);
        this.getModel().setReportDTOList(reportDTOList);
        this.getModel().setDemandNoticeDTOList(demandNoticeDTOList);
        this.getModel().setReportLang(lang);
        return new ModelAndView(MainetConstants.RnLCommon.DEMAND_NOTICE_PRINT, MainetConstants.FORM_NAME, this.getModel());
    }
}
