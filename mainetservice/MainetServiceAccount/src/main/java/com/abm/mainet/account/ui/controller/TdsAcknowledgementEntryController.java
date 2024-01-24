package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.VoucherTemplateDTO;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.service.VoucherTemplateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/*
 * Object: Deduction Register(Story-1833) Author By:- Ajay Kumar
 * Date: 19-02-2018
 */
@Controller
@RequestMapping("/TdsAcknowledgementEntry.html")
public class TdsAcknowledgementEntryController extends AbstractController {
    private final static String PAYMENT_ENTRY_DTO = "paymentEntryDto";
    private static final String JSP_LIST = "TdsAcknowledgementEntry/list";
    private static final String JSP_FORM = "TdsAcknowledgementEntry/form";
    private static final String REPORT_FORM = "TdsAckPaymentEntry/Report";
    private static final String TEMPLATE_EXIST_FLAG = "templateExistFlag";
    private static final String TDS_LIST = "tdsList";
    private static final String QTR_LIST = "qtrList";
    @Resource
    private DepartmentService departmentService;
    @Resource
    private VoucherTemplateService voucherTemplateService;
    @Resource
    private PaymentEntrySrevice paymentEntryService;
    private List<PaymentDetailsDto> chList = null;

    public TdsAcknowledgementEntryController() {
        super(TdsAcknowledgementEntryController.class, PAYMENT_ENTRY_DTO);
        log("TdsAcknowledgementEntryController created.");
    }

    public void populateModel(final Model model, final PaymentEntryDto paymentEntryDto, final String modeCreate) {
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        populateTdsTypeList(model);
        populateQtrIdList(model);
        if (modeCreate.equals(MODE_CREATE)) {
            model.addAttribute(MODE, MODE_CREATE);
        } else {
            model.addAttribute(MODE, MODE_VIEW);
        }
        checkTemplate(model);
    }

    // populate for tdstype list
    public void populateTdsTypeList(final Model model) {
        final List<LookUp> tdsLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.TDS,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(TDS_LIST, tdsLookUpList);
    }

    // populate for quarter list
    public void populateQtrIdList(final Model model) {
        final List<LookUp> tdsLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.QDT,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(QTR_LIST, tdsLookUpList);
    }

    // populate for check template
    public void checkTemplate(final Model model) {
        final VoucherTemplateDTO postDTO = new VoucherTemplateDTO();
        postDTO.setTemplateType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PN.toString(),
                AccountPrefix.MTP.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        postDTO.setVoucherType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PV.toString(),
                AccountPrefix.VOT.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        postDTO.setDepartment(departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
        postDTO.setTemplateFor(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), UserSession.getCurrent().getOrganisation().getOrgid()));
        final boolean existTempalte = voucherTemplateService.isTemplateExist(postDTO,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!existTempalte) {
            model.addAttribute(TEMPLATE_EXIST_FLAG, AccountConstants.N.toString());
        } else {
            model.addAttribute(TEMPLATE_EXIST_FLAG, AccountConstants.Y.toString());
        }
    }

    @RequestMapping()
    public String index(final Model model) {
        chList = new ArrayList<>();
        chList.clear();
        PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        populateModel(model, paymentEntryDto, MODE_CREATE);
        return JSP_LIST;
    }

    @RequestMapping(params = "tdsPaymentDetails", method = RequestMethod.POST)
    public @ResponseBody List getLedgerDetails(final PaymentEntryDto bean, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String fromDate = bean.getFromDate();
        String toDate = bean.getToDate();
        final Long paymentTypeFlag = 2L;
        chList = new ArrayList<>();
        chList.clear();
        chList = paymentEntryService.getPaymentDetails(orgId, Utility.stringToDate(fromDate),
                Utility.stringToDate(toDate), paymentTypeFlag);
        return chList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetCode-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(PAYMENT_ENTRY_DTO, chList);
        return response;
    }

    @RequestMapping(params = "tdsAckPaymentForm", method = RequestMethod.POST)
    public String formForUpdate(final Model model, @RequestParam("paymentId") final Long paymentId,
            @RequestParam("qrtId") final Long qrtId, @RequestParam("paymentDate") final String paymentDate,
            @RequestParam("MODE") final String MODE) throws Exception {
        log("Action 'formForUpdate'");
        PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        paymentEntryDto.setPaymentId(paymentId);
        paymentEntryDto.setQtrId(qrtId);
        paymentEntryDto.setPaymentEntryDate(paymentDate);
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return JSP_FORM;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String TdsAcknowledgementPaymentEntry(@Valid final PaymentEntryDto paymentEntrydto,
            final Model model, final HttpServletRequest request) throws ParseException {
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        paymentEntrydto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        paymentEntrydto.setCreatedDate(new Date());
        paymentEntrydto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        paymentEntrydto.setSuccessfulFlag(MainetConstants.MASTER.Y);
        paymentEntrydto.setLanguageId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
        paymentEntrydto.setLgIpMacAddress(Utility.getClientIpAddress(request));
        paymentEntrydto.setOrganisation(organisation);
        paymentEntrydto.setPaymentDetailsDto(paymentEntrydto.getPaymentDetailsDto());
        paymentEntryService.createTdsAckPaymentEntry(paymentEntrydto);
        request.getSession().setAttribute(MainetConstants.DirectPaymentEntry.PAYMENT_LIST, paymentEntrydto);
        return MainetConstants.MENU.Y;
    }

    @RequestMapping(params = "tdsAckpaymentReportForm", method = RequestMethod.POST)
    public String formForView(final Model model, @RequestParam("paymentId") final Long paymentId,
            @RequestParam("MODE") final String MODE) throws Exception {
        log("Action 'formForView'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final PaymentEntryDto paymentEntryDto = paymentEntryService.findTdsAckPaymentDetailsById(paymentId, orgId);
        model.addAttribute(PAYMENT_ENTRY_DTO, paymentEntryDto);
        populateModel(model, paymentEntryDto, MODE_VIEW);
        return REPORT_FORM;
    }
}
