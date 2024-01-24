
package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.TransactionTrackingDto;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.TransactionTrackingService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author tejas.kotekar
 *
 */
@Controller
@RequestMapping("/TransactionTracking.html")
public class TransactionTrackingController extends AbstractController {

    private final String JSP_LIST = "TransactionTracking/list";
    private final String JSP_FORM = "TransactionTracking/form";
    private final String REPORT_FORM = "TransactionTracking/report";
    private final String REPORT_FORM_DAY = "TransactionTracking/dayReport";
    private final String REPORT_VOUCHER = "TransactionTracking/voucherReport";
    private static final Logger LOGGER = Logger.getLogger(TransactionTrackingController.class);
    private final static String TRANSACTION_TRACKING_DTO = "transactionTrackingDto";

    List<TransactionTrackingDto> masterDtoList = null;
    List<TransactionTrackingDto> masterDtoMonthWiseList = null;

    @Resource
    private TransactionTrackingService transactionTrackingService;
    @Resource
    private AccountBillEntryService billEntryService;

    @Resource
    private TbFinancialyearService financialyearService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    public TransactionTrackingController() {
        super(TransactionTrackingController.class, TRANSACTION_TRACKING_DTO);
        LOGGER.info("Transaction tracking controller created");
    }

    @RequestMapping()
    public String populateGridList(final Model model, final HttpServletRequest httpServletRequest) {
        log("Action 'form'");
        masterDtoList = new ArrayList<>();
        final TransactionTrackingDto transactionTrackingDto = new TransactionTrackingDto();
        masterDtoList.clear();
        populateModel(model, transactionTrackingDto, MODE_CREATE);
        return JSP_LIST;
    }

    /**
     * @param model
     * @param transactionTrackingDto
     * @param modeCreate
     */
    private void populateModel(final Model model, final TransactionTrackingDto transactionTrackingDto, final String modeCreate) {
        model.addAttribute(TRANSACTION_TRACKING_DTO, transactionTrackingDto);
        populateAccountHeads(model);
    }

    public void populateAccountHeads(final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        final Map<Long, String> accountHeadMap = new HashMap<>();
        final List<Object[]> accountHeadsList = transactionTrackingService.getTransactedAccountHeads(orgId);
        if ((accountHeadsList != null) && !accountHeadsList.isEmpty()) {
            for (final Object[] accountHead : accountHeadsList) {
                accountHeadMap.put((Long) accountHead[0], (String) accountHead[1]);
            }
            model.addAttribute(MainetConstants.TransactionTracking.ACCOUNT_HEAD, accountHeadMap);
        }
        model.addAttribute(MainetConstants.TransactionTracking.FINANCE_YEAR_MAP,
                secondaryheadMasterService.getAllFinincialYear(orgId, langId));
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody JQGridResponse<TransactionTrackingDto> gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(AccountConstants.PAGE.getValue()));
        final JQGridResponse<TransactionTrackingDto> response = new JQGridResponse<>();
        response.setRows(masterDtoList);
        response.setTotal(masterDtoList.size());
        response.setRecords(masterDtoList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterDtoList);
        return response;
    }

    @RequestMapping(params = "searchTransactionData", method = RequestMethod.POST)
    public String searchTransactionDetails(
            @RequestParam("faYearid") final Long faYearid, final Model model) {
        Date fromDate = null;
        Date toDate = null;
        TransactionTrackingDto transactionTrackingDto1 = null;
        masterDtoList = new ArrayList<>();
        masterDtoList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> dateList = financialyearService.getFromDateAndToDateByFinancialYearId(faYearid);

        if (dateList != null && !dateList.isEmpty()) {
            for (Object[] dateLists : dateList) {
                fromDate = (Date) dateLists[0];
                toDate = (Date) dateLists[1];
                break;
            }
        }
        if (fromDate != null && toDate != null) {
            transactionTrackingDto1 = transactionTrackingService
                    .getTransactionTrackingTrialBalance(orgId, fromDate, toDate, faYearid);
        }

        if (transactionTrackingDto1 != null) {
            populateModel(model, transactionTrackingDto1, MODE_CREATE);
            model.addAttribute(TRANSACTION_TRACKING_DTO, transactionTrackingDto1);
            return JSP_FORM;
        } else {
            populateModel(model, new TransactionTrackingDto(), MODE_CREATE);
            model.addAttribute(TRANSACTION_TRACKING_DTO, new TransactionTrackingDto());
            return JSP_FORM;

        }
    }

    @RequestMapping(params = "getMonthWiseGridData")
    public @ResponseBody JQGridResponse<TransactionTrackingDto> getMonthWiseGridData(final HttpServletRequest request,
            final Model model) {

        final int page = Integer.parseInt(request.getParameter(AccountConstants.PAGE.getValue()));
        final JQGridResponse<TransactionTrackingDto> response = new JQGridResponse<>();
        response.setRows(masterDtoMonthWiseList);
        response.setTotal(masterDtoMonthWiseList.size());
        response.setRecords(masterDtoMonthWiseList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, masterDtoMonthWiseList);
        return response;
    }

    @RequestMapping(params = "getMonthWiseData", method = RequestMethod.POST)
    public @ResponseBody List<TransactionTrackingDto> getMonthWiseData(@RequestParam("budgetCodeId") final Long budgetCodeId)
            throws ParseException {
        masterDtoMonthWiseList = new ArrayList<>();
        masterDtoMonthWiseList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
        final Long finYearId = financialYear.getFaYear();
        final TransactionTrackingDto transactionTrackingDto = transactionTrackingService.getMonthWiseDetails(budgetCodeId, orgId,
                finYearId, UserSession.getCurrent().getOrganisation());
        masterDtoMonthWiseList.add(transactionTrackingDto);
        return masterDtoMonthWiseList;
    }

    @RequestMapping(params = "findHeadWiseTransactions", method = RequestMethod.POST)
    public String findHeadWiseTransactions(
            @RequestParam("accountHead") final String accountHead, @RequestParam("faYearid") final Long faYearid,
            @RequestParam("openingDr") BigDecimal openingDr, @RequestParam("openingCr") BigDecimal openingCr,
            final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TransactionTrackingDto transactionTrackingDto = transactionTrackingService.findHeadWiseBalance(orgId, accountHead,
                faYearid, openingDr, openingCr);

        transactionTrackingDto.setOpeningCrAmount(openingCr);
        transactionTrackingDto.setOpeningDrAmount(openingDr);
        model.addAttribute(TRANSACTION_TRACKING_DTO, transactionTrackingDto);

        return REPORT_FORM;

    }

    @RequestMapping(params = "findDayWiseTransactions", method = RequestMethod.POST)
    public String findDayWiseTransactions(
            @RequestParam("accountHead") final String accountHead, @RequestParam("faYearid") final Long faYearid,
            @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
            @RequestParam("openDr") final BigDecimal openDr, @RequestParam("openCr") final BigDecimal openCr, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TransactionTrackingDto transactionTrackingDto = transactionTrackingService.findDayWiseBalance(orgId, accountHead,
                faYearid, fromDate, toDate);
        transactionTrackingDto.setOpeningCrAmount(openCr);
        transactionTrackingDto.setOpeningDrAmount(openDr);
        model.addAttribute(TRANSACTION_TRACKING_DTO, transactionTrackingDto);

        return REPORT_FORM_DAY;

    }

    @RequestMapping(params = "findVoucherWiseTransactions", method = RequestMethod.POST)
    public String findVoucherWiseTransactions(
            @RequestParam("accountHead") final String accountHead,
            @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
            @RequestParam("faYearid") final Long faYearid, @RequestParam("voucherDate") final String voucherDate,
            @RequestParam("openDr") final BigDecimal openDr, @RequestParam("openCr") final BigDecimal openCr,
            final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TransactionTrackingDto transactionTrackingDto = transactionTrackingService.findVoucherWiseTransactions(orgId, accountHead,
                fromDate);
        transactionTrackingDto.setFromDate(voucherDate);
        transactionTrackingDto.setToDate(toDate);
        transactionTrackingDto.setFaYearid(faYearid);
        transactionTrackingDto.setAccountHead(accountHead);
        transactionTrackingDto.setVoucherDate(fromDate);
        transactionTrackingDto.setOpeningCrAmount(openCr);
        transactionTrackingDto.setOpeningDrAmount(openDr);

        model.addAttribute(TRANSACTION_TRACKING_DTO, transactionTrackingDto);

        return REPORT_VOUCHER;

    }

}
