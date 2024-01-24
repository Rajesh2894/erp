package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.BillDeletionService;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;

@Controller
@RequestMapping({ "/PropertyBillDeletion.html" })
public class PropertyBillDeletionController extends AbstractFormController<NewPropertyRegistrationModel> {

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Resource
    private PropertyBillPaymentService propertyBillPaymentService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private BillDeletionService billDeletionService;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Autowired
    private TbTaxMasService tbTaxMasService;

    @Autowired
    SecondaryheadMasterService secondaryheadMasterService;
    
    @Autowired
    private IEmployeeWardZoneMappingService employeeWardZoneMappingService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        getModel().setCommonHelpDocs("DataEntrySuiteDeleteBill.html");
        return defaultResult();
    }

    @RequestMapping(params = "searchPropetryForDelete", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, @RequestParam(value = "propNo") String propNo)
            throws Exception {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ModelAndView mv = null;

        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDES = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().setDataFrom(MainetConstants.FlagP);
        List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getDataEntryPropDetailFromProvAssByPropNo(orgId, propNo, "A");
        if (provAssDtoList == null || provAssDtoList.isEmpty()) {
            getModel().setDataFrom(MainetConstants.FlagM);
            provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNo(orgId, propNo);
        }

        List<TbBillMas> billMas = propertyMainBillService
                .fetchBillByPropNo(propNo, orgId);

        if (provAssDtoList != null && !provAssDtoList.isEmpty()) {

            if (billMas != null && !billMas.isEmpty()) {

                model.setBillMasList(billMas);
                List<TbBillMas> tbBillMasList = model.getBillMasList();
                BigInteger[] validateBillDeletion = billDeletionService.validateBillDeletion(propNo,
                        UserSession.getCurrent().getOrganisation().getOrgid(), tbBillMasList);
                if (validateBillDeletion[0] != BigInteger.valueOf(0)) {

                    mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

                    model.addValidationError(
                            ApplicationSession.getInstance().getMessage("Receipt Exist Please delete Receipt first"));
                    return mv;

                } else if (validateBillDeletion[1] != BigInteger.valueOf(0)) {
                    mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

                    model.addValidationError(
                            ApplicationSession.getInstance().getMessage("Provisual assessment done cant delete"));
                    return mv;
                } else {

                    getModel().setOrgId(orgId);

                    final ProvisionalAssesmentMstDto provisionalAssesmentMstDto = propertyAuthorizationService
                            .getAssesmentMstDtoForDisplay(provAssDtoList);
					if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "EWZ") && provisionalAssesmentMstDto != null && !(employeeWardZoneMappingService.checkWardZoneMappingFlag(
							UserSession.getCurrent().getEmployee().getEmpId(),
							UserSession.getCurrent().getOrganisation().getOrgid(),
							provisionalAssesmentMstDto.getAssWard1(), provisionalAssesmentMstDto.getAssWard2(),
							provisionalAssesmentMstDto.getAssWard3(), provisionalAssesmentMstDto.getAssWard4(),
							provisionalAssesmentMstDto.getAssWard5()))) {
						
						 mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
		                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		                    model.addValidationError(
		                            ApplicationSession.getInstance().getMessage("You cannot access this property. Beacause this belongs to another ward zone"));
		                    return mv;
					}
                    Long deptId = serviceDES.getTbDepartment().getDpDeptid();

                    getModel().setDeptId(deptId);
                    LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            provisionalAssesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                    getModel().setOwnershipPrefix(ownerType.getLookUpCode());

                    /*
                     * List<TbBillMas> billMasList = iProvisionalBillService
                     * .getProvisionalBillMasByPropertyNo(provisionalAssesmentMstDto.getAssNo(), orgId);
                     */

                    /* if (billMasList == null || billMasList.isEmpty()) */
                    List<TbBillMas> billMasList = propertyMainBillService
                            .fetchBillByPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);

                    if (billMasList == null || billMasList.isEmpty()) {
                        mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

                        model.addValidationError(ApplicationSession.getInstance().getMessage("property.validMsg.billNotPresent"));
                        return mv;
                    }
                    this.getModel().setBillMasList(billMasList);
                    this.getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
                    final List<FinancialYear> compFinYearList = iFinancialYear.getAllFinincialYear();
                    String financialYear = null;
                    for (final FinancialYear finYearTemp : compFinYearList) {
                        financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                        getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
                    }

                    List<LookUp> list = dataEntrySuiteService.setScheduleListForArrEntry(
                            getModel().getProvisionalAssesmentMstDto().getAssAcqDate(),
                            UserSession.getCurrent().getOrganisation().getOrgid(),
                            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());

                    BillingScheduleDetailEntity billSchDet = billingScheduleService
                            .getSchedulebySchFromDate(orgId, billMasList.get(0).getBmFromdt());
                    List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(
                            UserSession.getCurrent().getOrganisation(),
                            deptId);
                    getModel().setTaxMasterList(taxDescription);
                    getModel().setScheduleForArrEntry(list);
                    getModel().setSchduleId(billSchDet.getSchDetId());

                }

            }

            else {
                mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

                model.addValidationError(ApplicationSession.getInstance().getMessage("property.validMsg.billNotPresent"));
                return mv;
            }
        } else {
            mv = new ModelAndView("PropertyBillDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

            model.addValidationError(ApplicationSession.getInstance().getMessage("property.validMsg.validPropNo"));
            return mv;

        }

        return new ModelAndView("PropertyBillDeletionView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "deleteBill", method = RequestMethod.POST)
    public ModelAndView deleteBill(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        if (getModel().getDataFrom().equals(MainetConstants.FlagP)) {

            model.getBillMasList().get(0).setBmRemarks(model.getTbBillMas().getBmRemarks());

            iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(model.getBillMasList(),MainetConstants.FlagD);

            iProvisionalBillService.deleteProvisionalBillsById(model.getBillMasList());

            voucherPostingCall(model);

        } else if (getModel().getDataFrom().equals(MainetConstants.FlagM)) {

            model.getBillMasList().get(0).setBmRemarks(model.getTbBillMas().getBmRemarks());

            propertyMainBillService.copyDataFromMainBillDetailToHistory(model.getBillMasList(),null,UserSession.getCurrent().getEmployee().getEmpId(),model.getClientIpAddress());

            propertyMainBillService.deleteMainBillWithDtoById(model.getBillMasList());

            voucherPostingCall(model);

        }
        return jsonResult(JsonViewObject.successResult(
                ApplicationSession.getInstance().getMessage("property.deleteBill.successMsg")));
    }

    public void voucherPostingCall(NewPropertyRegistrationModel model) {
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        VoucherPostExternalDTO dto = new VoucherPostExternalDTO();

        Organisation org = UserSession.getCurrent().getOrganisation();

        String activeFlag = null;
        final LookUp accountActive = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, org);
        if (accountActive != null) {
            activeFlag = accountActive.getDefaultVal();
        }

        TbBillMas tbBillMas = model.getBillMasList().get(0);

        List<TbBillDet> tbWtBillDet = tbBillMas.getTbWtBillDet();

        dto.setVoucherDate(Utility.dateToString(new Date()));

        dto.setVousubTypeCode("BDL");

        dto.setDepartmentCode("AS");

        dto.setUlbCode(org.getOrgShortNm());

        dto.setVoucherReferenceNo(tbBillMas.getBmNo());

        dto.setVoucherReferenceDate(Utility.dateToString(tbBillMas.getBmBilldt()));

        dto.setNarration(model.getTbBillMas().getBmRemarks());

        dto.setLocationDescription("1-1");

        String assoOwnerName = this.getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().get(0)
                .getAssoOwnerName();

        dto.setPayerOrPayee(assoOwnerName);

        dto.setCreatedBy(String.valueOf(UserSession.getCurrent().getEmployee().getEmpId()));

        dto.setEntryCode("EXS");

        dto.setPayModeCode("T");

        dto.setEntryFlag(null);

        FinancialYear financialYear = iFinancialYearService.getFinincialYearsById(tbBillMas.getBmYear(), orgId);

        // dto.setFinancialYear(String.valueOf(Utility.getYearByDate(financialYear.getFaFromDate()))
        // .concat(MainetConstants.HYPHEN).concat(String.valueOf(Utility.getYearByDate(financialYear.getFaToDate()))));

        List<VoucherPostDetailExternalDTO> voucherExtDetails = new ArrayList<>();

        String acHeadCode = null;

        final Long currDemandId = CommonMasterUtility.getValueFromPrefixLookUp("CYR", "DMC", org).getLookUpId();
        final Long previousIncomeId = CommonMasterUtility.getValueFromPrefixLookUp("PPI", "DMC", org).getLookUpId();

        for (TbBillDet tbWtBill : tbWtBillDet) {

            VoucherPostDetailExternalDTO voucherPostDetailExternalDTO = new VoucherPostDetailExternalDTO();

            if (tbWtBill.getBdCurTaxamt() > 0) {

                voucherPostDetailExternalDTO.setVoucherAmount(new BigDecimal(tbWtBill.getBdCurTaxamt()));

                voucherPostDetailExternalDTO.setAccountHeadFlag(null);

                Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
                voucherPostDetailExternalDTO.setDemandTypeCode(null);

                if (finYearId == tbBillMas.getBmYear()) {

                    acHeadCode = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                            tbTaxMasService.fetchSacHeadIdForReceiptDetByDemandClass(orgId, tbWtBill.getTaxId(), "A",
                                    currDemandId));
                } else {

                    acHeadCode = secondaryheadMasterService.findSacHeadCodeBySacHeadId(tbTaxMasService
                            .fetchSacHeadIdForReceiptDetByDemandClass(orgId, tbWtBill.getTaxId(), "A", previousIncomeId));

                }

                voucherPostDetailExternalDTO.setAcHeadCode(acHeadCode);

                voucherExtDetails.add(voucherPostDetailExternalDTO);

            }

            dto.setVoucherExtDetails(voucherExtDetails);
        }

        if (PrefixConstants.IsLookUp.STATUS.YES.equals(activeFlag)) {

            billDeletionService.externalVoucherPostingInAccount(orgId, dto);
        }
    }

    @RequestMapping(params = "backToDeleteSearchPage", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
        NewPropertyRegistrationModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }

}
