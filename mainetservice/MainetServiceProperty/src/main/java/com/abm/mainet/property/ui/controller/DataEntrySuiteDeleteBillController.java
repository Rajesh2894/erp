package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;
import com.google.common.util.concurrent.AtomicDouble;

@Controller
@RequestMapping({ "/DataEntrySuiteDeleteBill.html" })
public class DataEntrySuiteDeleteBillController extends AbstractFormController<NewPropertyRegistrationModel> {

	
	private static final Logger LOGGER = Logger.getLogger(DataEntrySuiteDeleteBillController.class);
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
    
    @Resource
	private TbFinancialyearService financialyearService;
    
    @Autowired
    private AsExecssAmtService  asExecssAmtService;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        getModel().setCommonHelpDocs("DataEntrySuiteDeleteBill.html");
        this.getModel().setFinYearMasterList(iFinancialYear.getAllFinincialYear());
        
        final List<FinancialYear> finYearList = financialyearService
				.findAllFinYearByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		String financialYear = null;
		for (final FinancialYear finYearTemp : finYearList) {
			try {
				financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				this.getModel().getFinYearData().put(finYearTemp.getFaYear(), financialYear);
			} catch (final Exception e) {
				//LOGGER.error("error in finYear list", e);
			}
		}
        return defaultResult();
    }

	@RequestMapping(params = "searchPropetryForDelete", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			@RequestParam(value = "propNo") String propNo, @RequestParam(value = "finId") Long finId,
			@RequestParam(value = "oldPropNo") String oldPropNo) throws Exception {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ModelAndView mv = null;
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDES = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().setDataFrom(MainetConstants.FlagP);
        List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getDataEntryPropDetailFromProvAssByPropNoAndOldPropNo(orgId, propNo, "A",oldPropNo);
        if (provAssDtoList == null || provAssDtoList.isEmpty()) {
            getModel().setDataFrom(MainetConstants.FlagM);
            provAssDtoList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, propNo, oldPropNo);
        }
        if (provAssDtoList != null && !provAssDtoList.isEmpty()) {
        	/*  boolean validate = dataEntrySuiteService.validateDataEntrySuite(propNo,
                    UserSession.getCurrent().getOrganisation().getOrgid(), serviceDES.getSmServiceId());*/
           
                getModel().setOrgId(orgId);
                final ProvisionalAssesmentMstDto provisionalAssesmentMstDto = propertyAuthorizationService
                        .getAssesmentMstDtoForDisplay(provAssDtoList);
                Long deptId = serviceDES.getTbDepartment().getDpDeptid();
                getModel().setDeptId(deptId);
                LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        provisionalAssesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                getModel().setOwnershipPrefix(ownerType.getLookUpCode());

                List<TbBillMas> billMasList = null;
                if(finId != null && finId > 0) {
                	billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNoAndFinId(provisionalAssesmentMstDto.getAssNo(), orgId, finId);
                }else {
                	billMasList = iProvisionalBillService
                            .getProvisionalBillMasByPropertyNo(provisionalAssesmentMstDto.getAssNo(), orgId);
                }

                if (billMasList == null || billMasList.isEmpty()) {
                	 if(finId != null && finId > 0) {
                		 billMasList = propertyMainBillService.fetchAllBillByPropNoAndFinId(provisionalAssesmentMstDto.getAssNo(), orgId, finId);
                     }else {
                    	 billMasList = propertyMainBillService.fetchAllBillByPropNo(provisionalAssesmentMstDto.getAssNo(), orgId);
                     }
                }
                if (billMasList == null || billMasList.isEmpty()) {
                    model.addValidationError(ApplicationSession.getInstance().getMessage("property.validMsg.billNotPresent"));
                    mv = new ModelAndView("DataEntrySuiteDeleteBillValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                    return mv;
                }
                this.getModel().setBillMasList(billMasList);
                if (!this.getModel().checkPayDetail(billMasList)) {
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
                List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(UserSession.getCurrent().getOrganisation(),
                        deptId);
                getModel().setTaxMasterList(taxDescription);
                getModel().setScheduleForArrEntry(list);
                getModel().setSchduleId(billSchDet.getSchDetId());

            } else {
                model.addValidationError(ApplicationSession.getInstance().getMessage("Bill payment have been done. Cannot delete bills"));
                mv = new ModelAndView("DataEntrySuiteDeleteBillValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }
        } else {
            model.addValidationError(ApplicationSession.getInstance().getMessage("property.validMsg.validPropNo"));
            mv = new ModelAndView("DataEntrySuiteDeleteBillValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        return new ModelAndView("DataEntryOutStandingView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "deleteBill", method = RequestMethod.POST)
    public ModelAndView deleteBill(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        LookUp billDeletionInactive = null;
        try {
        	billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", UserSession.getCurrent().getOrganisation());
        }catch (Exception e) {
        	
		}
        model.getBillMasList().forEach(billMas ->{
        	billMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        	billMas.setUpdatedDate(new Date());
        	billMas.setLgIpMacUpd(model.getClientIpAddress());
        });
        if (getModel().getDataFrom().equals(MainetConstants.FlagP)) {
            iProvisionalBillService.copyDataFromProvisionalBillDetailToHistory(model.getBillMasList(),MainetConstants.FlagD);
            iProvisionalBillService.deleteProvisionalBillsById(model.getBillMasList());
			if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
					&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
				List<Long> propPrimKeyList = new ArrayList<Long>();
	            propPrimKeyList.add(this.getModel().getProvisionalAssesmentMstDto().getProAssId());
            }
        } else if (getModel().getDataFrom().equals(MainetConstants.FlagM)) {
            propertyMainBillService.copyDataFromMainBillDetailToHistory(model.getBillMasList(),MainetConstants.FlagD,UserSession.getCurrent().getEmployee().getEmpId(),model.getClientIpAddress());
            propertyMainBillService.deleteMainBillWithDtoById(model.getBillMasList());
            if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
					&& StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
            
            }
        }
       // List<Long> receiptIdTofetchRebateRec = new ArrayList<Long>();        
		try {
			List<TbServiceReceiptMasEntity> inactiveReceiptList = new ArrayList<TbServiceReceiptMasEntity>();
			if (CollectionUtils.isNotEmpty(this.getModel().getBillMasList())
					&& CollectionUtils.isNotEmpty(this.getModel().getReceiptDetails())) {
				for (TbBillMas billMas : this.getModel().getBillMasList()) {

					for (TbServiceReceiptMasEntity payDetail : this.getModel().getReceiptDetails()) {
						
						String payModeCode = CommonMasterUtility
								.getNonHierarchicalLookUpObject(payDetail.getReceiptModeDetail().get(0).getCpdFeemode(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();

						if (((StringUtils.equals(payDetail.getReceiptTypeFlag(), MainetConstants.FlagA)
								&& StringUtils.equals(MainetConstants.FlagT, payModeCode))
								|| (StringUtils.equals(payDetail.getReceiptTypeFlag(), "RB")
										&& checkDemandRebate(payDetail, billMas)))
								&& Utility.comapreDates(payDetail.getRmDate(), billMas.getBmBilldt())) {
							inactiveReceiptList.add(payDetail);
						}

					}

				}

			}
			AtomicDouble advanceAmount = new AtomicDouble(0);
			if (CollectionUtils.isNotEmpty(inactiveReceiptList)) {
				inactiveReceiptList.forEach(receiptMas ->{
					receiptMas.getReceiptFeeDetail().forEach(feeDetail ->{
						if(feeDetail.getBmIdNo() != null) {
							advanceAmount.addAndGet(feeDetail.getRfFeeamount().doubleValue());
						}
					});
				});
			}

			double adjustedAdvanceAmt = advanceAmount.doubleValue();
			List<AsExcessAmtEntity> advanceAmountList = asExecssAmtService.getExcessAmtEntByActivePropNo(model.getBillMasList().get(0).getPropNo(), UserSession.getCurrent().getOrganisation().getOrgid());
			if(CollectionUtils.isNotEmpty(advanceAmountList)) {
				for (AsExcessAmtEntity asExcessAmtEntity : advanceAmountList) {
					if(adjustedAdvanceAmt > 0) {
						double returnAdjAmt = asExcessAmtEntity.getAdjAmt() - adjustedAdvanceAmt;
						adjustedAdvanceAmt = adjustedAdvanceAmt - asExcessAmtEntity.getAdjAmt();
						if(returnAdjAmt <= 0) {
							asExcessAmtEntity.setAdjAmt(0);
						}else {
							asExcessAmtEntity.setAdjAmt(returnAdjAmt);
						}
						asExcessAmtEntity.setExcadjFlag(MainetConstants.FlagN);
						asExecssAmtService.saveAndUpdateAsExecssAmt(asExcessAmtEntity,
								UserSession.getCurrent().getOrganisation().getOrgid(),
								UserSession.getCurrent().getEmployee().getEmpId());
					}
				}
			}
			ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class)
					.inActiveReceiptByReceiptList(UserSession.getCurrent().getOrganisation().getOrgid(),
							inactiveReceiptList, "inactive at the time if bill deletion event",
							UserSession.getCurrent().getEmployee().getEmpId());

		} catch (Exception e) {
			LOGGER.error("Error occured when inactivating receipt");
		}


        return jsonResult(JsonViewObject.successResult(
                ApplicationSession.getInstance().getMessage("property.deleteBill.successMsg")));
    }

    @RequestMapping(params = "backToDeleteSearchPage", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
        NewPropertyRegistrationModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }
    
    private boolean checkDemandRebate(TbServiceReceiptMasEntity receiptMas,TbBillMas billMas) {
    	boolean RebateApplicab = false;
    	List<TbSrcptFeesDetEntity> rebateReceiptList = receiptMas.getReceiptFeeDetail().stream().filter(fee -> fee.getBmIdNo().equals(billMas.getBmIdno())).collect(Collectors.toList());
    	
    	if(CollectionUtils.isNotEmpty(rebateReceiptList)) {
    		RebateApplicab = true;
    	}
    	return RebateApplicab;
    }

}
