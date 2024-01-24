
package com.abm.mainet.account.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountLiabilityBookingDetEntity;
import com.abm.mainet.account.domain.AccountLiabilityBookingEntity;
import com.abm.mainet.account.domain.AccountTenderDetEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountLiabilityBookingBean;
import com.abm.mainet.account.dto.AccountLiabilityBookingDetBean;
import com.abm.mainet.account.service.AccountLiabilityBookingService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/** @author tejas.kotekar */
@Controller
@RequestMapping("/AccountLiabilityBooking.html")
public class AccountLiabilityBookingController extends AbstractController {

    private final String JSP_FORM = "AccountLiabilityBooking/form";

    private final String JSP_LIST = "AccountLiabilityBooking/list";

    private final String JSP_LIST_SAVED = "AccountLiabilityBooking/listSaved";

    private final String SAVE_ACTION_CREATE = "AccountLiabilityBooking?create";

    private static String LIABILITY_BOOKING_BEAN = "liabilityBookingBean";

    @Resource
    private AccountFundMasterService tbAcFundMasterService;

    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService tbAcPrimaryheadMasterService;

    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;

    @Resource
    private TbFinancialyearService tbFinancialyearService;

    private final String TENDER_DET_LIST = "tenderDetList";
    private final String TENDER_NO_MAP = "tenderNoMap";
    private final String PAC_MAP = "pacMap";
    private final String SAC_MAP = "sacMap";
    private final String FUND_MAP = "fundMap";
    private final String FUNC_MAP = "funcMap";
    private final String FIELD_MAP = "fieldMap";
    private final String FIN_YEAR_MAP = "finYearMap";

    private static Logger logger = Logger.getLogger(AccountLiabilityBookingController.class);

    @Resource
    AccountLiabilityBookingService liabilityBookingService;

    public AccountLiabilityBookingController() {
        super(AccountLiabilityBookingController.class, LIABILITY_BOOKING_BEAN);
    }

    public void populateModel(final Model model, final AccountLiabilityBookingBean liabilityBookingBean,
            final FormMode formMode) {

        model.addAttribute(LIABILITY_BOOKING_BEAN, liabilityBookingBean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfTenderNo(model);
        }
    }

    public void populateListOfTenderNo(final Model model) {

        logger.info("Action :Populate List Of Tender No");

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Map<Long, String> tenderNoMap = new HashMap<>();

        final List<AccountTenderEntryEntity> tenderList = liabilityBookingService.getListOfTenderDetails(orgId);
        if ((tenderList != null) && !tenderList.isEmpty()) {
            for (final AccountTenderEntryEntity tender : tenderList) {
                tenderNoMap.put(tender.getTrTenderId(), tender.getTrTenderNo());
            }
            model.addAttribute(TENDER_NO_MAP, tenderNoMap);
        }
    }

    @RequestMapping()
    public String showListForm(final Model model) {
        log("Action 'list'");
        final AccountLiabilityBookingBean liabilityBookingBean = new AccountLiabilityBookingBean();
        populateModel(model, liabilityBookingBean, FormMode.CREATE);
        return JSP_LIST;
    }

    @RequestMapping(params = "sacHeadItemsList")
    public @ResponseBody Map<Long, String> sacHeadData(@RequestParam("pacHeadId") final String primaryCode,
            final HttpServletRequest request,
            final Model model) {

        Map<Long, String> lookup = new HashMap<>(0);
        lookup = tbAcSecondaryheadMasterService.findAllById(Long.valueOf(primaryCode));
        return lookup;
    }

    @RequestMapping(params = "getTenderDetails")
    public String getTenderDetails(final Model model, final HttpServletRequest request,
            @RequestParam("tenderId") final Long tenderId)
            throws Exception {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final AccountLiabilityBookingBean liabilityBookingBean = new AccountLiabilityBookingBean();
        Boolean isLiability;
        isLiability = liabilityBookingService.isLiabilityExists(tenderId, orgId);
        final AccountLiabilityBookingEntity libEntity = liabilityBookingService.getLiabilityNoByTenderId(tenderId, orgId);
        if (isLiability == false) {
            model.addAttribute(MainetConstants.DISABLED, true);
        } else {
            getLiabilityDetails(model, liabilityBookingBean, libEntity, request);
        }
        final List<AccountTenderEntryEntity> tenderDetList = liabilityBookingService.getTenderDetailsByTenderId(tenderId, orgId);

        AccountLiabilityBookingDetBean detDto = null;
        final List<AccountLiabilityBookingDetBean> listDetDto = new ArrayList<>();
        final Map<Long, String> sacMap = null;
        final Map<Long, String> financeMap = null;
        if ((tenderDetList != null) && !tenderDetList.isEmpty()) {
            for (final AccountTenderEntryEntity tenderList : tenderDetList) {

                liabilityBookingBean.setTrTenderId(tenderList.getTrTenderId());
                liabilityBookingBean.setTrTenderNo(tenderList.getTrTenderNo());
                liabilityBookingBean.setTrEntryDate(UtilityService.convertDateToDDMMYYYY(tenderList.getTrEntryDate()));
                liabilityBookingBean.setDpDept(tenderList.getTbDepartment().getDpDeptdesc());
                liabilityBookingBean.setTrType(tenderList.getTbComparamDet().getCpdDesc());
                liabilityBookingBean.setTrTenderAmount(Long.valueOf(tenderList.getTrTenderAmount().toString()));
                liabilityBookingBean.setTrEmdAmt(Long.valueOf(tenderList.getTrEmdAmt().toString()));
                liabilityBookingBean.setVmVendorCode(tenderList.getTbVendormaster().getVmVendorcode());
                liabilityBookingBean.setVendorName(tenderList.getTbVendormaster().getVmVendorname());

                if (isLiability == false) {
                    for (final AccountTenderDetEntity detEntity : tenderList.getListOfTbAcTenderDet()) {
                        detDto = new AccountLiabilityBookingDetBean();
                        listDetDto.add(detDto);
                    }
                    liabilityBookingBean.setDetList(listDetDto);
                    final Long defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
                    model.addAttribute(TENDER_DET_LIST, listDetDto);
                    model.addAttribute(PAC_MAP, tbAcPrimaryheadMasterService.getPrimaryHeadCodeLastLevels(defaultOrgId));
                    model.addAttribute(SAC_MAP, sacMap);
                    model.addAttribute(FUND_MAP, tbAcFundMasterService.getFundMasterLastLevels(defaultOrgId));
                    model.addAttribute(FUNC_MAP, tbAcFunctionMasterService.getFunctionMasterLastLevels(defaultOrgId));
                    model.addAttribute(FIELD_MAP, tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
                    model.addAttribute(FIN_YEAR_MAP, financeMap);
                }
            }
        }
        populateModel(model, liabilityBookingBean, FormMode.CREATE);

        return JSP_FORM;
    }

    public void getLiabilityDetails(final Model model, final AccountLiabilityBookingBean liabilityBookingBean,
            final AccountLiabilityBookingEntity libEntity, final HttpServletRequest request) throws Exception {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Date liabilityEntryDate = null;
        String lbEntryDate = null;
        liabilityBookingBean.setLbLiabilityId(libEntity.getLbLiabilityId());
        liabilityBookingBean.setLbLiabilityNo(libEntity.getLbLiabilityNo());
        liabilityEntryDate = libEntity.getLbEntryDate();
        final SimpleDateFormat format = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        lbEntryDate = format.format(liabilityEntryDate);
        liabilityBookingBean.setLbEntryDate(lbEntryDate);
        AccountLiabilityBookingDetBean liabilityDetDto = null;
        final Map<Long, String> sacMap = null;
        Map<Long, String> financeMap = null;
        final List<AccountLiabilityBookingDetBean> listDetailDto = new ArrayList<>();
        final List<AccountLiabilityBookingDetEntity> liablityDetList = liabilityBookingService
                .getLiabilityDetailsByLiabilityId(libEntity.getLbLiabilityId(), orgId);
        if ((liablityDetList != null) && !liablityDetList.isEmpty()) {
            for (final AccountLiabilityBookingDetEntity detEntity : liablityDetList) {

                liabilityDetDto = new AccountLiabilityBookingDetBean();
                liabilityDetDto.setLbLiabilityDetId(detEntity.getLbLiabilityDetId());
                liabilityDetDto.setLiabilityAmount(detEntity.getLiabilityAmount());
                liabilityDetDto.setFaYearid(detEntity.getFaYearid());
                financeMap = tbFinancialyearService.getAllFinincialYear();
                listDetailDto.add(liabilityDetDto);
            }
        }
        liabilityBookingBean.setDetList(listDetailDto);
        final Long defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        model.addAttribute(TENDER_DET_LIST, listDetailDto);
        model.addAttribute(PAC_MAP, tbAcPrimaryheadMasterService.getPrimaryHeadCodeLastLevels(defaultOrgId));
        model.addAttribute(SAC_MAP, sacMap);
        model.addAttribute(FUND_MAP, tbAcFundMasterService.getFundMasterLastLevels(defaultOrgId));
        model.addAttribute(FUNC_MAP, tbAcFunctionMasterService.getFunctionMasterLastLevels(defaultOrgId));
        model.addAttribute(FIELD_MAP, tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
        model.addAttribute(FIN_YEAR_MAP, financeMap);
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String createLiability(@Valid final AccountLiabilityBookingBean liabilityBookingBean,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        if (!bindingResult.hasErrors()) {
            liabilityBookingBean.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            liabilityBookingBean.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            liabilityBookingBean.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            liabilityBookingBean.setCreatedDate(new Date());
            liabilityBookingBean.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final AccountLiabilityBookingBean liabilityBookingBeanCreated = liabilityBookingService
                    .createLiabilityBookingEntry(liabilityBookingBean);
            model.addAttribute(LIABILITY_BOOKING_BEAN, liabilityBookingBeanCreated);
            populateModel(model, liabilityBookingBean, FormMode.CREATE);
        } else {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, liabilityBookingBean, FormMode.CREATE);
            return JSP_FORM;
        }
        return JSP_LIST_SAVED;
    }

}
