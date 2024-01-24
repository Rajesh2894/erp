package com.abm.mainet.common.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDetailsDTO;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceivableDemandEntryService;
import com.abm.mainet.common.ui.model.ReceivableDemandEntryModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author satish.kadu
 *
 */
@Controller
@RequestMapping("/ReceivableDemandEntry.html")
public class ReceivableDemandEntryController extends AbstractFormController<ReceivableDemandEntryModel> {

    private static final Logger logger = Logger.getLogger(ReceivableDemandEntryController.class);

    @Autowired
    private ILocationMasService locationService;

    @Resource
    private TbServicesMstService tbServicesMstService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private TbTaxMasService taxMasService;

    @Autowired
    private IReceivableDemandEntryService receivableDemandEntryService;

    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("ReceivableDemandEntry.html");
        intitialSetupReceivableDemandEntry();
        return defaultResult();
    }

    /**
     * @param request
     * @return Receivable Demand Entry Form
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.OPEN_DEMAND_ENTRY_FORM)
    public ModelAndView addReceivableDemandEntry(final HttpServletRequest request) {
        sessionCleanup(request);
        intitialSetupReceivableDemandEntry();
        this.getModel().getReceivableDemandEntryDto().setLocName(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng());
        String wardIdnPattern = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocArea() + MainetConstants.ReceivableDemandEntry.IDN;
        this.getModel().getReceivableDemandEntryDto().setWardIdnPattern(wardIdnPattern);
        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param faYearid
     * @param deptId
     * @param serviceId
     * @param request
     * @return Receivable Demand Entry Summary With Selected Criteria Filter
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.SEARCH_DEMAND_ENTRY_FORM)
    public ModelAndView searchReceivableDemandEntry(@RequestParam("refNumber") String refNumber, @RequestParam("billNo") String billNo, final HttpServletRequest request) {
        intitialSetupReceivableDemandEntry();
        String ward = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocArea();
        ward = ward + MainetConstants.ReceivableDemandEntry.IDN + MainetConstants.operator.PERCENTILE;
        Long locId = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId();
        this.getModel().setReceivableDemandEntryDtosList(receivableDemandEntryService.searchSupplementaryBillInfo(refNumber, billNo, UserSession.getCurrent().getOrganisation().getOrgid(), ward, locId));
        return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_SEARCH, MainetConstants.FORM_NAME, this.getModel());

    }

    /**
     * @param id
     * @param model
     * @return Receivable Demand Entry Form In View Mode
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.VIEW_DEMAND_ENTRY_FORM)
    public ModelAndView viewReceivableDemandEntry(@RequestParam("id") final Long id, final Model model) {
        intitialSetupReceivableDemandEntry();
        this.getModel().setReceivableDemandEntryDto(receivableDemandEntryService.getById(id));
        this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
        this.getModel().getReceivableDemandEntryDto().setLocName(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng());
        this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().forEach(tr -> {
            TbTaxMas tax = taxMasService.findTaxByTaxIdAndOrgId(tr.getTaxId(), tr.getOrgid());
            tr.setTaxCategory1(tax.getTaxCategory1());
            tr.setTaxCategory2(tax.getTaxCategory2());
        });
        List<TbTaxMas> taxList = getFilteredTaxList();
        setAccountCodeListSliWise(model, taxList);

        return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_VIEW, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param id
     * @param model
     * @return Receivable Demand Entry Form In Edit Mode
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.EDIT_DEMAND_ENTRY_FORM)
    public ModelAndView editReceivableDemandEntry(@RequestParam("id") final Long id, final Model model) {

        intitialSetupReceivableDemandEntry();
        this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
        this.getModel().setReceivableDemandEntryDto(receivableDemandEntryService.getById(id));
        if (this.getModel().getReceivableDemandEntryDto().getReceiptId() == null) {
            int dueDates = Integer.parseInt(CommonMasterUtility.getDefaultValue("SBD").getLookUpCode().toString());
            Calendar c = Calendar.getInstance();
            c.setTime(this.getModel().getReceivableDemandEntryDto().getCreatedDate());
            c.add(Calendar.DAY_OF_MONTH, dueDates);
            this.getModel().setDeletedDemandList(this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().stream().filter(tr -> tr.getIsDeleted().equals("Y")).collect(Collectors.toList()));
            this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().removeAll(this.getModel().getDeletedDemandList());
            if (!(new Date().before(this.getModel().getReceivableDemandEntryDto().getCreatedDate()) || new Date().after(c.getTime()))) {
                if (this.getModel().getReceivableDemandEntryDto().getCustomerDetails().getApplicationDate() != null) {
                    c.setTime(this.getModel().getReceivableDemandEntryDto().getCustomerDetails().getApplicationDate());
                    c.add(Calendar.DAY_OF_MONTH, dueDates);
                    if (!(new Date().before(this.getModel().getReceivableDemandEntryDto().getCustomerDetails().getApplicationDate()) || new Date().after(c.getTime()))) {
                        this.getModel().getReceivableDemandEntryDto().setNewIdn(MainetConstants.ReceivableDemandEntry.NEW_IDN);
                    } else {
                        this.getModel().getReceivableDemandEntryDto().setNewIdn(MainetConstants.ReceivableDemandEntry.OLD_IDN);
                    }
                } else {
                    this.getModel().getReceivableDemandEntryDto().setNewIdn(MainetConstants.ReceivableDemandEntry.OLD_IDN);
                }
                this.getModel().getReceivableDemandEntryDto().setLocName(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng());
                this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().forEach(tr -> {
                    TbTaxMas tax = taxMasService.findTaxByTaxIdAndOrgId(tr.getTaxId(), tr.getOrgid());
                    tr.setTaxCategory1(tax.getTaxCategory1());
                    tr.setTaxCategory2(tax.getTaxCategory2());
                });
                List<TbTaxMas> taxList = getFilteredTaxList();
                List<Long> taxCat = new ArrayList<>();

                if (!taxList.isEmpty()) {
                    taxList.forEach(tr -> {
                        taxCat.add(tr.getTaxCategory1());
                        this.getModel().getTaxSubCat().add(tr.getTaxCategory2());
                    });
                }
                model.addAttribute(MainetConstants.ReceivableDemandEntry.TAX_CATEGORY, taxCat);
                setAccountCodeListSliWise(model, taxList);
                String wardIdnPattern = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocArea() + MainetConstants.ReceivableDemandEntry.IDN;
                this.getModel().getReceivableDemandEntryDto().setWardIdnPattern(wardIdnPattern);
                return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_EDIT, MainetConstants.FORM_NAME, this.getModel());
            } else {
                model.addAttribute(MainetConstants.ReceivableDemandEntry.FORM_ERROR, this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_DUE_DATE_OVER) + this.getModel().getReceivableDemandEntryDto().getBillNo());
                return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_SEARCH, MainetConstants.FORM_NAME, this.getModel());
            }
        } else {
            model.addAttribute(MainetConstants.ReceivableDemandEntry.FORM_ERROR,
                    this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.BILL_PAID) + this.getModel().getReceivableDemandEntryDto().getBillNo());
            return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_SEARCH, MainetConstants.FORM_NAME, this.getModel());
        }

    }

    private void intitialSetupReceivableDemandEntry() {
        this.getModel().getDeptList().add(tbDepartmentService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonConstants.ACTIVE,
                MainetConstants.DEPT_SHORT_NAME.WATER));
    }

    private List<TbTaxMas> getFilteredTaxList() {
        Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL, PrefixConstants.LookUp.CHARGE_MASTER_CAA,
                UserSession.getCurrent().getOrganisation()).getLookUpId();
        List<TbTaxMas> taxList = taxMasService.getAllTaxForSupplementryBill(applicableAtId, this.getModel().getReceivableDemandEntryDto().getDeptId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        return taxList;
    }

    private void setAccountCodeListSliWise(final Model model, List<TbTaxMas> taxList) {
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
        if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
            this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.ACTIVE);
            final List<AccountHeadSecondaryAccountCodeMasterEntity> entity = getAccountHeadCode(taxList);
            if (entity != null) {
                model.addAttribute(MainetConstants.ReceivableDemandEntry.ACCOUNT_HEAD_CODE, entity);
            } else {
                this.getModel().addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.ACCOUNT_HEAD_NOT_FOUND));
            }
        } else {
            this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.INACTIVE);
        }
    }

    /**
     * @param referanceNumber
     * @param deptId
     * @param serviceId
     * @param refFlag
     * @param model
     * @return
     */
    @RequestMapping(params = MainetConstants.ReceivableDemandEntry.GET_TAX_DETAILS, method = RequestMethod.POST)
    public ModelAndView getTaxDetails(@RequestParam("refNumber") final String referanceNumber, @RequestParam("deptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId, @RequestParam("refFlag") final String refFlag, final Model model) {

        ReceivableDemandEntryDTO receivableDemandEntryDto = this.getModel().getReceivableDemandEntryDto();
        receivableDemandEntryDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        receivableDemandEntryDto.setServiceId(serviceId);
        receivableDemandEntryDto.setDeptId(deptId);
        receivableDemandEntryDto.setRefNumber(referanceNumber);
        receivableDemandEntryDto.setRefFlag(refFlag);
        receivableDemandEntryDto.setLocName(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng());
        receivableDemandEntryDto.setBillDate(new Date());

        List<TbTaxMas> taxList = getFilteredTaxList();
        receivableDemandEntryDto.setTaxCount(taxList.size());
        if (taxList.isEmpty()) {
            return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
        }
        List<Long> taxCat = new ArrayList<>();
        if (!taxList.isEmpty()) {
            taxList.forEach(tr -> {
                taxCat.add(tr.getTaxCategory1());
                this.getModel().getTaxSubCat().add(tr.getTaxCategory2());
            });
        }
        model.addAttribute(MainetConstants.ReceivableDemandEntry.TAX_CATEGORY, taxCat);
        String wardIdnPattern = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocArea() + MainetConstants.ReceivableDemandEntry.IDN;
        this.getModel().getReceivableDemandEntryDto().setWardIdnPattern(wardIdnPattern);
        if (MainetConstants.ReceivableDemandEntry.NEW_IDN.equals(refFlag)) {
            this.getModel().getReceivableDemandEntryDto().setNewCust(true);
            this.getModel().getReceivableDemandEntryDto().setNewIdn(MainetConstants.ReceivableDemandEntry.NEW_IDN);
        } else {
            this.getModel().getReceivableDemandEntryDto().setNewIdn(MainetConstants.ReceivableDemandEntry.OLD_IDN);
            receivableDemandEntryDto = receivableDemandEntryService.getByRefNoOrAppNo(receivableDemandEntryDto);
            if (receivableDemandEntryDto != null && receivableDemandEntryDto.getCustomerDetails() != null) {
                this.getModel().setReceivableDemandEntryDto(receivableDemandEntryDto);

            } else {
                if (MainetConstants.ReceivableDemandEntry.REFERANACE_NUMBER_WISE == refFlag) {
                    this.getModel()
                            .addValidationError(this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.NO_CUSTOMER_FOUND_REF_NO_WISE) + referanceNumber);
                } else {
                    this.getModel().addValidationError(
                            this.getModel().getAppSession().getMessage(MainetConstants.ReceivableDemandEntry.NO_CUSTOMER_FOUND_APPLICATION_NO_WISE) + referanceNumber);
                }
            }
        }
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
        if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
            this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.ACTIVE);
        } else {
            this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.INACTIVE);
        }
        model.addAttribute(MainetConstants.ReceivableDemandEntry.CUSTOMER, receivableDemandEntryDto.getCustomerDetails().getfName());
        receivableDemandEntryDto.setServiceId(serviceId);
        receivableDemandEntryDto.setDeptId(deptId);
        return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param model
     * @param deptId
     * @return return department Service List
     */
    @RequestMapping(params = MainetConstants.ReceivableDemandEntry.REFRESH_SERVICE_DATA, method = RequestMethod.POST)
    public @ResponseBody List<TbServicesMst> refreshServiceData(final Model model, @RequestParam("deptId") final Long deptId) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbServicesMst> serviceMstList = tbServicesMstService.findByDeptId(deptId, org.getOrgid());
        this.getModel().setTaxlist(taxMasService.findAllByTaxOrgId(null, UserSession.getCurrent().getOrganisation().getOrgid(), deptId));
        return serviceMstList;
    }

    /**
     * @param taxList
     * @return
     */
    @RequestMapping(params = MainetConstants.ReceivableDemandEntry.GET_ALL_ACCOUNT_HEAD)
    public @ResponseBody List<AccountHeadSecondaryAccountCodeMasterEntity> getAccountHeadCode(List<TbTaxMas> taxList) {
        final String sliDefaultValue = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI).getLookUpCode();
        if (MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE.equals(sliDefaultValue)) {
            List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadCodesList = new ArrayList<>();
            final List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadCodes = secondaryheadMasterService
                    .getSecondaryHeadcodesForTax(UserSession.getCurrent().getOrganisation().getOrgid());
            Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL, PrefixConstants.LookUp.CHARGE_MASTER_CAA,
                    UserSession.getCurrent().getOrganisation()).getLookUpId();
            List<Long> accHead = new ArrayList<>();
            if (MainetConstants.MODE_VIEW.equals(this.getModel().getSaveMode())) {
                taxList.forEach(tr -> {
                    accHead.add(taxMasService.fetchSacHeadIdForSupplementryBill(UserSession.getCurrent().getOrganisation().getOrgid(), tr.getTaxId(), applicableAtId));
                });

            } else {
                taxList.forEach(tr -> {
                    accHead.add(taxMasService.fetchSacHeadIdForReceiptDet(UserSession.getCurrent().getOrganisation().getOrgid(), tr.getTaxId(), MainetConstants.STATUS.ACTIVE));
                });
            }
            for (AccountHeadSecondaryAccountCodeMasterEntity sacHead : secondaryHeadCodes) {
                if (accHead.contains(sacHead.getSacHeadId())) {
                    secondaryHeadCodesList.add(sacHead);
                }
            }

            return secondaryHeadCodesList;
        } else {
            return null;
        }
    }

    /**
     * @param taxCategory
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.GET_TAX_SUBCATEGORY)
    public @ResponseBody List<LookUp> gettaxSubCategoryList(@RequestParam("taxCategory") long taxCategory, final HttpServletRequest httpServletRequest) {
        List<LookUp> lookuplist1 = new ArrayList<>();
        CommonMasterUtility.getChildLookUpsFromParentId(taxCategory).forEach(tr -> {
            this.getModel().getTaxSubCat().forEach(tax -> {
                if (tr.getLookUpId() == tax) {
                    lookuplist1.add(tr);
                }
            });
        });
        return lookuplist1;
    }

    @RequestMapping(params = MainetConstants.ReceivableDemandEntry.GET_ACCOUNT_HEAD, method = RequestMethod.POST)
    public @ResponseBody List<AccountHeadSecondaryAccountCodeMasterEntity> getAccountHeadList(final Model model, @RequestParam("deptId") final Long deptId,
            @RequestParam("taxCategory") final Long taxCategory, @RequestParam("taxSubCategory") final Long taxSubCategory) {
        Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL, PrefixConstants.LookUp.CHARGE_MASTER_CAA,
                UserSession.getCurrent().getOrganisation()).getLookUpId();
        List<TbTaxMas> taxList = taxMasService.getAllTaxForSupplementryBill(applicableAtId, this.getModel().getReceivableDemandEntryDto().getDeptId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        List<TbTaxMas> filterTaxList = new ArrayList<>();
        for (TbTaxMas tax : taxList) {
            if (tax.getTaxCategory1() != null && tax.getTaxCategory2() != null) {
                if (tax.getTaxCategory1().equals(taxCategory) && tax.getTaxCategory2().equals(taxSubCategory)) {
                    filterTaxList.add(tax);
                }
            }
        }
        List<AccountHeadSecondaryAccountCodeMasterEntity> accHeadcodeList = getAccountHeadCode(filterTaxList);
        return accHeadcodeList;
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.ReceivableDemandEntry.PRINT_CHALLAN)
    public ModelAndView printReceivableDemandEntry(@RequestParam(value = "id", required = false) Long id, final HttpServletRequest request) {
        logger.info("Start the PrintChallan()");
        try {
            bindModel(request);
            final Organisation org = UserSession.getCurrent().getOrganisation();
            ReceivableDemandEntryDTO receivableDemandEntryDto = this.getModel().getReceivableDemandEntryDto();
            // get Data from table by id
            if (id != null) {
                this.getModel().setReceivableDemandEntryDto(receivableDemandEntryService.getById(id));
            } else {
                this.getModel().setReceivableDemandEntryDto(receivableDemandEntryService.getByRefNoOrAppNo(receivableDemandEntryDto));
            }
            receivableDemandEntryDto = this.getModel().getReceivableDemandEntryDto();
            // SLI Status
            LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
            if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
                this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.ACTIVE);
            } else {
                this.getModel().getReceivableDemandEntryDto().setSliStatus(MainetConstants.Common_Constant.INACTIVE);
            }

            // setting Invoice Data
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                TbLocationMas wardLocation = locationService.findById(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId());
                String wardAddress = replaceNull(wardLocation.getLocNameEng()) + MainetConstants.WHITE_SPACE + replaceNull(wardLocation.getLocAddress());
                receivableDemandEntryDto.setWardAddress(wardAddress);
            }
            String district = CommonMasterUtility.getCPDDescription(org.getOrgCpdIdDis(),PrefixConstants.D2KFUNCTION.ENGLISH_DESC);
            String state =  CommonMasterUtility.getCPDDescription(org.getOrgCpdIdState(), PrefixConstants.D2KFUNCTION.ENGLISH_DESC);
            // String division = CommonMasterUtility.getCPDDescription(org.getOrgCpdIdDiv(),
            // PrefixConstants.D2KFUNCTION.ENGLISH_DESC);
            String orgAddress = replaceNull(org.getOrgAddress()) + MainetConstants.WHITE_SPACE + district + MainetConstants.WHITE_SPACE
                    + state;
            receivableDemandEntryDto.setOrgAddress(orgAddress);
            receivableDemandEntryDto.setOrgGSTIN(org.getOrgGstNo());
            receivableDemandEntryDto.setLocName(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng());
            RequestDTO custDTO = receivableDemandEntryDto.getCustomerDetails();
            String custAddress = replaceNull(custDTO.getAreaName()) + MainetConstants.WHITE_SPACE + replaceNull(custDTO.getBldgName()) + MainetConstants.WHITE_SPACE
                    + replaceNull(custDTO.getHouseComplexName()) +
                    MainetConstants.WHITE_SPACE + replaceNull(custDTO.getRoadName()) + MainetConstants.WHITE_SPACE + replaceNull(custDTO.getCityName())
                    + MainetConstants.WHITE_SPACE + (custDTO.getPincodeNo() == null ? "" : custDTO.getPincodeNo());

            receivableDemandEntryDto.setCustFullAddress(custAddress);
            final String amountInWords = Utility.convertBiggerNumberToWord(new BigDecimal(receivableDemandEntryDto.getBillAmount().toString()));
            receivableDemandEntryDto.setBillAmountStr(amountInWords);
            String deptName = tbDepartmentService.findDepartmentById(receivableDemandEntryDto.getDeptId()).getDpDeptdesc();
            receivableDemandEntryDto.setDeptName(deptName);
            // List<LookUp> taxDescriptionList = CommonMasterUtility.getNextLevelData(PrefixConstants.TAC_PREFIX,
            // MainetConstants.ReceivableDemandEntry.SECOND_LEVEL, orgId);
            Long applicableAtId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.SUPPLEMENTRY_BILL, PrefixConstants.LookUp.CHARGE_MASTER_CAA,
                    UserSession.getCurrent().getOrganisation()).getLookUpId();
            receivableDemandEntryDto.getRcvblDemandList().forEach(tr -> {
                Long taxId = tr.getTaxId();
                Long trOrgId = tr.getOrgid();
                String taxDesc = taxMasService.findTaxByTaxIdAndOrgId(taxId, trOrgId).getTaxDesc();
                tr.setTaxName(taxDesc);
                /*
                 * taxDescriptionList.forEach(lk -> { if (lk.getLookUpId() == tr.getTaxCategory2()) { lk.getLookUpDesc()); } });
                 */
                // NOTE: tr.getSacHeadId() will get only inCase Of SLI is Live
                Long sacHeadId = taxMasService.fetchSacHeadIdForSupplementryBill(UserSession.getCurrent().getOrganisation().getOrgid(), tr.getTaxId(), applicableAtId);
                String accCode = secondaryheadMasterService.findByAccountHead(sacHeadId);
                if (StringUtils.isNotEmpty(accCode)) {
                    accCode = accCode.replaceAll(MainetConstants.HYPHEN, MainetConstants.BLANK);
                    accCode = accCode.substring(11, 20);
                    tr.setAccHead(accCode);

                }
            });
            this.getModel().setReceivableDemandEntryDto(receivableDemandEntryDto);
            return new ModelAndView(MainetConstants.ReceivableDemandEntry.RECEIVABLE_DEMAND_JSP_FORM_PRINT_CHALLAN, MainetConstants.FORM_NAME, this.getModel());
        } catch (final Exception e) {
            logger.error("Exception while printing Receivable Demand challan :", e);
        }
        return null;
    }

    private String replaceNull(String name) {
        if (name == null) {
            name = MainetConstants.BLANK;
        }
        return name;
    }

    @RequestMapping(params = "deletedDemandDetailRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRow") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        ReceivableDemandEntryDetailsDTO detDto = this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().get(deletedRowCount);
        if (detDto != null) {
            detDto.setIsDeleted(MainetConstants.FlagY);
            detDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            detDto.setUpdatedDate(new Date());
            detDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        }
        this.getModel().getDeletedDemandList().add(detDto);
        this.getModel().getReceivableDemandEntryDto().getRcvblDemandList().remove(deletedRowCount);
    }

}
